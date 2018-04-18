/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.test.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.springframework.stereotype.Component;

/**
 * DBUnit.
 */
@Component
public final class DBUnitBrpUtil extends AbstractDBUnitUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String KERN_SCHEMA = "kern";
    private static final String AUTORISATIE_SCHEMA = "autaut";
    private static final String IST_SCHEMA = "ist";
    private static final String CONVERSIE_SCHEMA = "conv";
    private static final String PROTOCOLLERING_SCHEMA = "prot";
    private static final String LOGGING_SCHEMA = "verconv";
    private static final String BERICHT_SCHEMA = "ber";
    private static final String MIGBLOK_SCHEMA = "migblok";

    private static final TruncateBrpTableOperation TRUNCATE_TABLE_OPERATION = new TruncateBrpTableOperation();

    // Statische Stamtabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als 'Stamgegevens -
    // Statisch'
    private static final String[] STATISCHE_STAMGEGEVENS = {"aandinhingvermissingreisdoc",
            "aang",
            "adellijketitel",
            "bijhaard",
            "element",
            "srtadres",
            "geslachtsaand",
            "naamgebruik",
            "naderebijhaard",
            "predicaat",
            "rdneinderelatie",
            "rdnwijzverblijf",
            "rol",
            "srtactie",
            "srtadmhnd",
            "koppelvlak",
            "stelsel",
            "burgerzakenmodule",
            "categorieadmhnd",
            "srtbetr",
            "srtelement",
            "srtindicatie",
            "srtmelding",
            "srtmigratie",
            "srtpers",
            "srtrelatie",
            "statusonderzoek",
            "statuslevadmhnd",
            "bijhresultaat",
            "richting",
            "srtber",
            "verwerkingsresultaat",};

    // Dynamische stamtabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als
    // 'Stamgegevens Dynamisch'
    // (volgorde is belangrijk vanwege constraints op de tabellen):
    private static final String[] DYNAMISCHE_STAMGEGEVENS = {"autvanafgifteblpersnr",
            "auttypevanafgiftereisdoc",
            "landgebied",
            "nation",
            "plaats",
            "rdnverknlnation",
            "rdnverliesnlnation",
            "gem",
            "his_partij",
            "his_partijrol",
            "his_partijvrijber",
            "partijrol",
            "partij",
            "srtpartij",
            "aandverblijfsr",
            "srtactiebrongebruik",
            "srtdoc",
            "srtnlreisdoc",
            "voorvoegsel",};

    // Reguliere tabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als
    // 'Operationele gegevens - Dynamisch'
    // (volgorde is belangrijk vanwege constraints op de tabellen):
    private static final String[] OPERATIONELE_TABELLEN = {"his_betr",
            "his_gegeveninonderzoek",
            "his_ouderouderlijkgezag",
            "his_ouderouderschap",
            "his_persuitslkiesr",
            "his_persbijhouding",
            "his_persvoornaam",
            "his_persverificatie",
            "his_persverblijfsr",
            "his_perssamengesteldenaam",
            "his_persreisdoc",
            "his_persadres",
            "his_perspk",
            "his_persoverlijden",
            "his_persnation",
            "his_persinschr",
            "his_persindicatie",
            "his_persmigratie",
            "his_persids",
            "his_persnrverwijzing",
            "his_persgeslnaamcomp",
            "his_persgeslachtsaand",
            "his_persgeboorte",
            "his_persdeelneuverkiezingen",
            "his_persnaamgebruik",
            "his_persafgeleidadministrati",
            "his_persverstrbeperking",
            "his_persblpersnr",
            "his_partijbijhouding",
            "his_onderzoek",
            "his_relatie",
            "admhndgedeblokkeerderegel",
            "gegeveninonderzoek",
            "onderzoek",
            "perscache",
            "persverstrbeperking",
            "betr",
            "persvoornaam",
            "persgeslnaamcomp",
            "persindicatie",
            "persnation",
            "persreisdoc",
            "persverificatie",
            "persadres",
            "persblpersnr",
            "relatie",
            "pers",
            "actiebron",
            "actie",
            "doc",
            "admhnd",};

    private static final String[] AUTORISATIE_STAMTABELLEN = {"effectafnemerindicaties", "protocolleringsniveau", "srtdienst",};

    private static final String[] AUTORISATIE_OP_TABELLEN = {"his_dienst",
            "his_dienstattendering",
            "his_dienstbundel",
            "his_dienstsel",
            "his_dienstzoeken",
            "his_levsautorisatie",
            "his_persafnemerindicatie",
            "his_toegangbijhautorisatie",
            "his_bijhautorisatiesrtadmhnd",
            "his_bijhautorisatie",
            "his_toeganglevsautorisatie",
            "his_bijhouderfiatuitz",
            "bijhouderfiatuitz",
            "dienst",
            "dienstbundelgroepattr",
            "dienstbundelgroep",
            "dienstbundello3rubriek",
            "dienstbundel",
            "persafnemerindicatie",
            "toeganglevsautorisatie",
            "levsautorisatie",
            "toegangbijhautorisatie",
            "bijhautorisatiesrtadmhnd",
            "bijhautorisatie",};

    private static final String[] CONVERSIE_TABELLEN = {"convaangifteadresh",
            "convadellijketitelpredikaat",
            "convaandinhingvermissingreis",
            "convrdnontbindinghuwelijkger",
            "convrnideelnemer",
            "convrdnopschorting",
            "convsrtnlreisdoc",
            "convvoorvoegsel",
            "convrdnbeeindigennation",
            "convrdnopnamenation",};

    private static final String[] PROTOCOLLERING_OP_TABELLEN = {"levsaantekpers", "levsaantek", "scopepatroonelement", "scopepatroon",};

    private static final String[] LOGGING_STAMGEGEVENS = {"lo3berbron", "lo3categoriemelding", "lo3severity", "lo3srtmelding",};

    private static final String[] LOGGING_TABELLEN = {"lo3melding", "lo3voorkomen", "lo3ber", "lo3aandouder",};

    private static final String[] IST_TABELLEN = {"autorisatietabel", "stapelvoorkomen", "stapelrelatie", "stapel",};

    private static final String[] MIGBLOK_TABELLEN = {"blokkering", "rdnblokkering"};

    private final DataSource dataSource;

    private List<String> dynamischeStamtabellen;

    private List<String> operationeleTabellen;

    private List<String> autorisatieStamTabellen;

    private List<String> autorisatieOpTabellen;

    private List<String> conversieTabellen;

    private List<String> leveringStamTabellen;

    private List<String> protocolleringOpTabellen;

    private List<String> loggingTabellen;

    private List<String> istTabellen;

    private List<String> migblokTabellen;

    /**
     * Constructor.
     * @param dataSource datasource
     */
    @Inject
    public DBUnitBrpUtil(@Named("syncDalDataSource") final DataSource dataSource) throws DataSetException {
        this.dataSource = dataSource;
        initTabellen();
    }

    /**
     * Geef de waarde van schemas.
     * @return schemas
     */
    public static List<String> getSchemas() {
        return Arrays.asList(
                KERN_SCHEMA,
                IST_SCHEMA,
                AUTORISATIE_SCHEMA,
                PROTOCOLLERING_SCHEMA,
                LOGGING_SCHEMA,
                CONVERSIE_SCHEMA,
                BERICHT_SCHEMA,
                MIGBLOK_SCHEMA);
    }

    public static List<String> getOperationeleTabellen() {
        return Arrays.asList(OPERATIONELE_TABELLEN);
    }

    /**
     * Geef de lijst met stamtabellen.
     * @param schema het schema waarvoor de stamtabellen moet worden gegeven
     * @return de stamtabellen
     */
    public static List<String> getStamtabellen(final String schema) {
        final List<String> stamtabellen;
        switch (schema) {
            case KERN_SCHEMA:
                stamtabellen = new ArrayList<>(DYNAMISCHE_STAMGEGEVENS.length + STATISCHE_STAMGEGEVENS.length);
                stamtabellen.addAll(Arrays.asList(DYNAMISCHE_STAMGEGEVENS));
                stamtabellen.addAll(Arrays.asList(STATISCHE_STAMGEGEVENS));
                break;
            case AUTORISATIE_SCHEMA:
                stamtabellen = Arrays.asList(AUTORISATIE_STAMTABELLEN);
                break;
            case LOGGING_SCHEMA:
                stamtabellen = Arrays.asList(LOGGING_STAMGEGEVENS);
                break;
            case CONVERSIE_SCHEMA:
                stamtabellen = Arrays.asList(CONVERSIE_TABELLEN);
                break;
            default:
                stamtabellen = new ArrayList<>();
                break;
        }

        return stamtabellen;
    }

    @Override
    protected Connection createSqlConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Leegt de database en reset de stamgegevens.
     * @param testClass testclass waaruit deze methode wordt aangeroepen
     * @param log logger waarin gelogd kan worden
     */
    public void resetDB(final Class<?> testClass, final Logger log) {
        resetDB(testClass, log, true);
    }

    /**
     * Leegt de database en indien gewenst wordt de stamgegevens ook gereset.
     * @param testClass testclass waaruit deze methode wordt aangeroepen
     * @param log logger waarin gelogd kan worden
     * @param resetStamgegevens true als de stamgegevens gereset moet worden
     */
    public void resetDB(final Class<?> testClass, final Logger log, final boolean resetStamgegevens) {
        log.info("Preparing database");
        try {
            final IDatabaseConnection connection = createConnection();

            try {
                log.info("Resetting sequences");
                resetSequences(connection);
                if (resetStamgegevens) {
                    log.info("Truncating all tables");
                    truncateTables(connection);
                    log.info("Inserting stamgegevens kern");
                    insert(connection, testClass, "/sql/data/brpStamgegevens-kern.xml");
                    log.info("Inserting stamgegevens autaut");
                    insert(connection, testClass, "/sql/data/brpStamgegevens-autaut.xml");
                    // log.info("Inserting stamgegevens lev");
                    // insert(testClass, "/sql/data/brpStamgegevens-lev.xml");
                    log.info("Inserting stamgegevens conv");
                    insert(connection, testClass, "/sql/data/brpStamgegevens-conv.xml");
                    log.info("Inserting stamgegevens verconv");
                    insert(connection, testClass, "/sql/data/brpStamgegevens-verconv.xml");
                    log.info("Resetting specific sequences");
                    setStamgegevensSequences(connection);
                } else {
                    log.info("Truncating tables no stamtabellen");
                    truncateTablesNoStamtabellen(connection);
                }
            } finally {
                connection.close();
            }
        } catch (
                DatabaseUnitException
                        | SQLException e) {
            throw new IllegalStateException("Kan database niet intialiseren.", e);
        }
    }

    @Override
    public void setInMemory() {
        super.setInMemory();
    }

    /**
     * Reset alle sequences behorend bij de operationele en log tabellen. De nieuwe beginwaarde van deze sequences wordt
     * op 1 gezet.
     */
    @Override
    public void resetSequences(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        final RestartBrpSequenceOperation restartBrpSequenceOperation = new RestartBrpSequenceOperation(1);
        restartBrpSequenceOperation.execute(connection, operationeleTabellen);
        restartBrpSequenceOperation.execute(connection, loggingTabellen);
        restartBrpSequenceOperation.execute(connection, istTabellen);
        restartBrpSequenceOperation.execute(connection, protocolleringOpTabellen);
        restartBrpSequenceOperation.execute(connection, autorisatieOpTabellen);
        restartBrpSequenceOperation.execute(connection, migblokTabellen);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnitUtil#setStamgegevensSequences(org.dbunit.database.
     * IDatabaseConnection)
     */
    @Override
    public void setStamgegevensSequences(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        // DBUnit verhoogt de sequence niet bij insert van stamgegegevens. De sequence moet dus opgehoogt worden, anders
        // gaat het bij een insert mis.
        setSpecificSequence(connection, KERN_SCHEMA + '.' + "his_partij");
    }

    private void setSpecificSequence(final IDatabaseConnection connection, final String tableName) throws SQLException, DatabaseUnitException {
        final List<String> databaseNames = new ArrayList<>();
        databaseNames.add(tableName);
        final PreparedStatement preparedStatement = connection.getConnection().prepareStatement("SELECT id from " + tableName + " order by id desc");
        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                final long maxId = rs.getLong(1);
                final long sequenceId = maxId + 1;
                new RestartBrpSequenceOperation(sequenceId).execute(connection, databaseNames);
            }
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException s) {
                LOGGER.error("Kon preparedStatement niet sluiten");
            }
        }
    }

    /**
     * Truncate bepaalde tabellen.
     * @param tabellen tabel namen
     * @throws DatabaseUnitException bij fouten
     * @throws SQLException bij fouten
     */
    public void truncateNamedTables(final String... tabellen) throws SQLException, DatabaseUnitException {
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), Arrays.asList(tabellen));
    }

    /**
     * Truncate alle tabellen met uitzondering van de statische stamtabellen.
     */
    @Override
    public void truncateTables(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        TRUNCATE_TABLE_OPERATION.execute(connection, migblokTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, istTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, loggingTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, operationeleTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, conversieTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, autorisatieOpTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, autorisatieStamTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, protocolleringOpTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, leveringStamTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, dynamischeStamtabellen);
    }

    private void truncateTablesNoStamtabellen(final IDatabaseConnection connection) throws SQLException, DatabaseUnitException {
        TRUNCATE_TABLE_OPERATION.execute(connection, migblokTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, istTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, loggingTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, operationeleTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, autorisatieOpTabellen);
        TRUNCATE_TABLE_OPERATION.execute(connection, protocolleringOpTabellen);
    }

    private void initTabellen() throws DataSetException {
        operationeleTabellen = new ArrayList<>();
        // statischeStamtabellen = new ArrayList<>();
        dynamischeStamtabellen = new ArrayList<>();
        autorisatieStamTabellen = new ArrayList<>();
        autorisatieOpTabellen = new ArrayList<>();
        conversieTabellen = new ArrayList<>();
        leveringStamTabellen = new ArrayList<>();
        protocolleringOpTabellen = new ArrayList<>();
        // loggingStamtabellen = new ArrayList<>();
        loggingTabellen = new ArrayList<>();
        istTabellen = new ArrayList<>();
        migblokTabellen = new ArrayList<>();

        for (final String tableName : OPERATIONELE_TABELLEN) {
            final String qualifiedTableName = KERN_SCHEMA + '.' + tableName;
            operationeleTabellen.add(qualifiedTableName);
        }

        for (final String tableName : DYNAMISCHE_STAMGEGEVENS) {
            final String qualifiedTableName = KERN_SCHEMA + '.' + tableName;
            dynamischeStamtabellen.add(qualifiedTableName);
        }

        for (final String tableName : AUTORISATIE_STAMTABELLEN) {
            final String qualifiedTableName = AUTORISATIE_SCHEMA + '.' + tableName;
            autorisatieStamTabellen.add(qualifiedTableName);
        }

        for (final String tableName : AUTORISATIE_OP_TABELLEN) {
            final String qualifiedTableName = AUTORISATIE_SCHEMA + '.' + tableName;
            autorisatieOpTabellen.add(qualifiedTableName);
        }

        for (final String tableName : CONVERSIE_TABELLEN) {
            final String qualifiedTableName = CONVERSIE_SCHEMA + '.' + tableName;
            conversieTabellen.add(qualifiedTableName);
        }

        for (final String tableName : PROTOCOLLERING_OP_TABELLEN) {
            final String qualifiedTableName = PROTOCOLLERING_SCHEMA + '.' + tableName;
            protocolleringOpTabellen.add(qualifiedTableName);
        }

        for (final String tableName : LOGGING_TABELLEN) {
            final String qualifiedTableName = LOGGING_SCHEMA + '.' + tableName;
            loggingTabellen.add(qualifiedTableName);
        }

        for (final String tableName : IST_TABELLEN) {
            final String qualifiedTableName = IST_SCHEMA + '.' + tableName;
            istTabellen.add(qualifiedTableName);
        }

        for (final String tableName : MIGBLOK_TABELLEN) {
            final String qualifiedTableName = MIGBLOK_SCHEMA + '.' + tableName;
            migblokTabellen.add(qualifiedTableName);
        }

    }
}
