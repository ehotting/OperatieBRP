/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.perf.levering.bericht.TestBericht;
import nl.bzk.migratiebrp.test.perf.levering.environment.TestEnvironment;
import nl.bzk.migratiebrp.util.common.Kopieer;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

import org.apache.commons.io.IOUtils;

/**
 * Test casus: processen.
 */
public final class ProcessenTestCasus extends TestCasus {

    private static final int MINUTES = 60;
    private static final int MILLISECONDS = 1000;
    private static final String FILENAME_BERICHTEN_ZIP = "berichten.zip";
    private static final String KAN_INPUT_NIET_LEZEN_EXCEPTIE = "Kan input niet lezen.";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isFile()
                    && !file.getName().contains(".DS_Store")
                    && !file.getName().contains(FILENAME_BERICHTEN_ZIP)
                    && !file.getName().contains("properties");
        }
    };
    private final File inputDirectory;
    private final File outputDirectory;
    private final TestEnvironment environment;
    private Date started;
    private long aantal;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder output folder
     * @param expectedFolder expected folder
     * @param input input
     * @param environment environment
     */
    protected ProcessenTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final File input,
            final TestEnvironment environment) {
        super(thema, naam, outputFolder, expectedFolder);
        inputDirectory = input;
        outputDirectory = new File(outputFolder, inputDirectory.getName());
        this.environment = environment;
    }

    /**
     * Geef de waarde van name.
     * @return name
     */
    public String getName() {
        return inputDirectory.getName();
    }

    /**
     * Geef de waarde van output directory.
     * @return output directory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Geef de waarde van aantal.
     * @return aantal
     */
    public long getAantal() {
        return aantal;
    }

    /**
     * Geef de waarde van started.
     * @return started
     */
    public Date getStarted() {
        return Kopieer.utilDate(started);
    }

    @Override
    public ProcessenTestResultaat run() {
        final ProcessenTestResultaat result = new ProcessenTestResultaat(getThema(), getNaam());

        LOG.info("Executing test case: " + inputDirectory.getName());
        environment.beforeTestCase();

        final List<Runnable> inputs = new ArrayList<>();

        final List<File> files = Arrays.asList(inputDirectory.listFiles(FILE_FILTER));
        Collections.sort(files);
        for (final File file : files) {
            inputs.add(new BestandVerwerker(file, environment));
        }

        final File berichtenZip = new File(inputDirectory, FILENAME_BERICHTEN_ZIP);
        if (berichtenZip.exists()) {
            final ZipFile zipFile;
            try {
                zipFile = new ZipFile(berichtenZip);
            } catch (final IOException e) {
                throw new RuntimeException("Kan zipfile niet openen.");
            }

            final Enumeration<? extends ZipEntry> entryEnumeration = zipFile.entries();
            while (entryEnumeration.hasMoreElements()) {
                final ZipEntry entry = entryEnumeration.nextElement();
                inputs.add(new ZipEntryVerwerker(zipFile, entry, environment, berichtenZip.getParent()));
            }
        }

        started = new Date();
        final long start = System.currentTimeMillis();
        aantal = 0;

        try {
            LOG.info("Start: " + started);

            final ExecutorService executorService = Executors.newFixedThreadPool(16);

            for (final Runnable input : inputs) {
                aantal++;
                executorService.execute(input);
            }

            executorService.shutdown();
            while (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                LOG.error("Verzenden berichten ...");
            }

            LOG.info("Beëindigen test case ...");
            result.setAantal(aantal);
            LOG.info("Aantal: {}", aantal);

            LOG.info("Wachten op voltooien van het verzenden van leveringsberichten...");
            environment.afterTestCase(aantal, started);

            LOG.info("Beëindigd op: " + new Date());

            result.setDuration(System.currentTimeMillis() - start);
            final long seconds = result.getDuration() / MILLISECONDS;
            final long duurUren = TimeUnit.SECONDS.toHours(seconds);
            final long duurMinuten = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * MINUTES;
            final long duurSeconde = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * MINUTES;
            LOG.info("Totale duur (seconde): " + seconds + " (" + duurUren + " uren " + duurMinuten + " minuten " + duurSeconde + " seconde)");

            final BigDecimal speed = BigDecimal.valueOf(aantal).divide(BigDecimal.valueOf(seconds), 2, RoundingMode.HALF_DOWN);

            LOG.info("Leveringsberichten per seconde: " + speed);

            if (environment.verifieerBeeindigdeLeveringsberichten(aantal)) {
                result.setResultaat(new TestStap(TestStatus.OK));
            } else {
                result.setResultaat(new TestStap(TestStatus.NOK));
                final Foutmelding foutmelding =
                        new Foutmelding("Niet alle berichten zijn correct verwerkt. Raadpleeg het log voor meer details.", new IllegalStateException());
                result.setFoutmelding(foutmelding);
            }

        } catch (final Exception e) {
            LOG.info("Exception in test case: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - start);
            result.setAantal(aantal);

            final Foutmelding fout = new Foutmelding("Onverwachte fout", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.ISC);

            result.setResultaat(new TestStap(TestStatus.EXCEPTIE, e.getMessage(), htmlFout, null));
        }

        LOG.info("Test case '" + inputDirectory.getName() + "': " + result.getResultaat().getStatus() + " -> " + result.getResultaat().getOmschrijving());

        return result;
    }

    /**
     * Abstracte verwerkerklasse van runnable voor uitvoer.
     */
    private abstract static class AbstractVerwerker implements Runnable {
        private static final int AANTAL_HERHALINGEN = 10;
        private final TestEnvironment environment;

        /**
         * Default constructor.
         * @param environment De Environment.
         */
        public AbstractVerwerker(final TestEnvironment environment) {
            this.environment = environment;
        }

        protected abstract TestBericht read();

        @Override
        public final void run() {
            final TestBericht testBericht = read();
            try {
                new Herhaal(MILLISECONDS, AANTAL_HERHALINGEN, Strategie.LINEAIR).herhaal(new Runnable() {
                    @Override
                    public void run() {
                        environment.verzendBericht(testBericht);
                    }
                });
            } catch (final HerhaalException e) {
                throw new RuntimeException(e);
            }

        }

    }

    /**
     * Implementatie verwerkerklasse voor losse bestanden.
     */
    private static final class BestandVerwerker extends AbstractVerwerker {

        private final File file;

        /**
         * Default constructor.
         * @param file Het te gebruiken bestand.
         * @param environment De Environment.
         */
        public BestandVerwerker(final File file, final TestEnvironment environment) {
            super(environment);
            this.file = file;
        }

        @Override
        public TestBericht read() {
            LOG.info(" - (file) " + file.getName());
            try {
                return new TestBericht(IOUtils.toString(new FileInputStream(file)), file.getName(), file.getParent());
            } catch (final IOException e) {
                throw new RuntimeException(KAN_INPUT_NIET_LEZEN_EXCEPTIE, e);
            }

        }
    }

    /**
     * Implementatie verwerkerklasse voor zip-file.
     */
    private static final class ZipEntryVerwerker extends AbstractVerwerker {

        private final ZipFile zipFile;
        private final ZipEntry entry;
        private final String zipDir;

        /**
         * Default constructor.
         * @param zipFile Het te gebruiken zip-bestand.
         * @param entry De te gebruiken zip-entry.
         * @param environment De Environment.
         * @param zipDir De te gebruiken zip-dir.
         */
        public ZipEntryVerwerker(final ZipFile zipFile, final ZipEntry entry, final TestEnvironment environment, final String zipDir) {
            super(environment);
            this.zipFile = zipFile;
            this.entry = entry;
            this.zipDir = zipDir;
        }

        @Override
        public TestBericht read() {
            LOG.info(" - (zip) " + entry.getName());
            try {
                return new TestBericht(IOUtils.toString(zipFile.getInputStream(entry)), entry.getName(), zipDir);
            } catch (final IOException e) {
                throw new RuntimeException(KAN_INPUT_NIET_LEZEN_EXCEPTIE, e);
            }

        }
    }

}
