/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.lo3vermooier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser voor LO3 berichten.
 */
public final class LO3BerichtParser {

    private static final int MINIMUM_LENGTE = 12;
    private static final int LENGTE_KEY_HEADER = 8;
    private static final int LENGTE_AKTENUMMER_HEADER = 7;
    private static final int LENGTE_BERICHTNUMMER_HEADER = 4;
    private static final int LENGTE_DATUM_HEADER = 8;
    private static final int LENGTE_FOUTREDEN_HEADER = 1;
    private static final int LENGTE_GEMEENTE_HEADER = 4;
    private static final int LENGTE_HERHALING_HEADER = 1;
    private static final int LENGTE_GEZOCHTE_PERSOON_HEADER = 1;
    private static final int LENGTE_STATUS_HEADER = 1;
    private static final int LENGTE_DATUMTIJD_HEADER = 17;
    private static final int LENGTE_ANUMMER_HEADER = 10;

    private static final int LENGTE_BERICHTLENGTE = 5;
    private static final int LENGTE_CATEGORIENUMMER = 2;
    private static final int LENGTE_CATEGORIELENGTE = 3;
    private static final int LENGTE_ELEMENTNUMMER = 4;
    private static final int LENGTE_ELEMENTLENGTE = 3;

    private static final int LENGTE_RUBRIEK_ELEMENT = 2;
    private static final String RUBRIEK_ELEMENT_SEPARATOR = ".";

    private static final String OUD_ANUMMER = "oud anummer";
    private static final String DATUM_TIJD = "datum/tijd";
    private static final String DATUM = "datum";
    private static final String STATUS = "status";
    private static final String ANUMMER = "anummer";
    private static final String DATUM_GELDIGHEID = "datum geldigheid";
    private static final String GEZOCHTE_PERSOON = "gezochte persoon";
    private static final String AKTENUMMER = "aktenummer";
    private static final String HERHALING = "herhaling";
    private static final String GEMEENTE = "gemeente";
    private static final String FOUTREDEN = "foutreden";

    private final List<Regel> regels = new ArrayList<>();

    /**
     * Constructor.
     * @param bericht bericht
     */
    public LO3BerichtParser(final String bericht) {
        final String berichtRestant = parseKop(bericht);
        parseInhoud(berichtRestant);
    }

    /**
     * Print.
     */
    public void print() {
        int categorie = 0;
        int groep = 0;
        for (int i = 0; i < regels.size(); ++i) {
            boolean spacer = false;
            final Regel regel = regels.get(i);
            final Matcher matcher = Pattern.compile("([0-9]{2}).([0-9]{2}).([0-9]{2})").matcher(regel.naam);

            if (matcher.matches()) {
                final int newCategorie = Integer.parseInt(matcher.group(1));
                final int newGroep = Integer.parseInt(matcher.group(2));
                if (newCategorie != categorie) {
                    spacer = true;
                } else if (newGroep < groep) {
                    spacer = true;
                }
                categorie = newCategorie;
                groep = newGroep;
            }
            if (spacer) {
                System.out.println("");
            }
            System.out.format("%03d %s %s%n", regel.lengte, regel.naam, regel.waarde);
        }
    }

    /**
     * Parse kop.
     * @param bericht bericht
     * @return restant
     */
    private String parseKop(final String bericht) {

        if (bericht.length() >= MINIMUM_LENGTE) {
            regels.add(new Regel("key", LENGTE_KEY_HEADER, bericht.substring(0, LENGTE_KEY_HEADER)));
            final Regel berichtnummer =
                    new Regel(
                            "berichtnummer",
                            LENGTE_BERICHTNUMMER_HEADER,
                            bericht.substring(LENGTE_KEY_HEADER, LENGTE_KEY_HEADER + LENGTE_BERICHTNUMMER_HEADER));
            regels.add(berichtnummer);
            String berichtRestant = bericht.substring(MINIMUM_LENGTE, bericht.length());

            switch (berichtnummer.getWaarde()) {
                case "Af01":
                case "Af11":
                case "Jf01":
                case "Tf01":
                    regels.add(new Regel(FOUTREDEN, LENGTE_FOUTREDEN_HEADER, berichtRestant.substring(0, LENGTE_FOUTREDEN_HEADER)));
                    regels.add(
                            new Regel(
                                    GEMEENTE,
                                    LENGTE_GEMEENTE_HEADER,
                                    berichtRestant.substring(LENGTE_FOUTREDEN_HEADER, LENGTE_FOUTREDEN_HEADER + LENGTE_GEMEENTE_HEADER)));
                    regels.add(
                            new Regel(
                                    ANUMMER,
                                    LENGTE_ANUMMER_HEADER,
                                    berichtRestant.substring(
                                            LENGTE_FOUTREDEN_HEADER
                                                    + LENGTE_GEMEENTE_HEADER,
                                            LENGTE_FOUTREDEN_HEADER + LENGTE_GEMEENTE_HEADER + LENGTE_ANUMMER_HEADER)));
                    berichtRestant =
                            berichtRestant.substring(LENGTE_FOUTREDEN_HEADER + LENGTE_GEMEENTE_HEADER + LENGTE_ANUMMER_HEADER, berichtRestant.length());
                    break;
                case "Ag01":
                case "Ag11":
                case "Ag21":
                case "Ag31":
                case "Ha01":
                case "Sv01":
                    regels.add(new Regel(STATUS, LENGTE_STATUS_HEADER, berichtRestant.substring(0, LENGTE_STATUS_HEADER)));
                    regels.add(
                            new Regel(DATUM, LENGTE_DATUM_HEADER, berichtRestant.substring(LENGTE_STATUS_HEADER, LENGTE_STATUS_HEADER + LENGTE_DATUM_HEADER)));
                    berichtRestant = berichtRestant.substring(LENGTE_STATUS_HEADER + LENGTE_DATUM_HEADER, berichtRestant.length());
                    break;
                case "Ap01":
                case "Av01":
                case "Iv01":
                case "Iv11":
                case "Iv21":
                case "Ji01":
                case "Jv01":
                case "Lq01":
                case "Tv01":
                    regels.add(new Regel(HERHALING, LENGTE_HERHALING_HEADER, berichtRestant.substring(0, LENGTE_HERHALING_HEADER)));
                    berichtRestant = berichtRestant.substring(LENGTE_HERHALING_HEADER, berichtRestant.length());
                    break;
                case "Gv01":
                case "Gv02":
                    regels.add(new Regel(ANUMMER, LENGTE_ANUMMER_HEADER, berichtRestant.substring(0, LENGTE_ANUMMER_HEADER)));
                    berichtRestant = berichtRestant.substring(LENGTE_ANUMMER_HEADER, berichtRestant.length());
                    break;
                case "Ib01":
                case "Jb01":
                    regels.add(new Regel(HERHALING, LENGTE_HERHALING_HEADER, berichtRestant.substring(0, LENGTE_HERHALING_HEADER)));
                    regels.add(
                            new Regel(
                                    STATUS,
                                    LENGTE_STATUS_HEADER,
                                    berichtRestant.substring(LENGTE_HERHALING_HEADER, LENGTE_HERHALING_HEADER + LENGTE_STATUS_HEADER)));
                    regels.add(
                            new Regel(
                                    DATUM,
                                    LENGTE_DATUM_HEADER,
                                    berichtRestant.substring(
                                            LENGTE_HERHALING_HEADER + LENGTE_STATUS_HEADER,
                                            LENGTE_HERHALING_HEADER + LENGTE_STATUS_HEADER + LENGTE_DATUM_HEADER)));
                    berichtRestant =
                            berichtRestant.substring(LENGTE_HERHALING_HEADER + LENGTE_STATUS_HEADER + LENGTE_DATUM_HEADER, berichtRestant.length());
                    break;
                case "Lg01":
                    regels.add(new Regel(DATUM_TIJD, LENGTE_DATUMTIJD_HEADER, berichtRestant.substring(0, LENGTE_DATUMTIJD_HEADER)));
                    regels.add(
                            new Regel(
                                    ANUMMER,
                                    LENGTE_ANUMMER_HEADER,
                                    berichtRestant.substring(LENGTE_DATUMTIJD_HEADER, LENGTE_DATUMTIJD_HEADER + LENGTE_ANUMMER_HEADER)));
                    regels.add(
                            new Regel(
                                    OUD_ANUMMER,
                                    LENGTE_ANUMMER_HEADER,
                                    berichtRestant.substring(
                                            LENGTE_DATUMTIJD_HEADER
                                                    + LENGTE_ANUMMER_HEADER,
                                            LENGTE_DATUMTIJD_HEADER + LENGTE_ANUMMER_HEADER + LENGTE_ANUMMER_HEADER)));
                    berichtRestant =
                            berichtRestant.substring(LENGTE_DATUMTIJD_HEADER + LENGTE_ANUMMER_HEADER + LENGTE_ANUMMER_HEADER, berichtRestant.length());
                    break;
                case "Tb01":
                    regels.add(new Regel(HERHALING, LENGTE_HERHALING_HEADER, berichtRestant.substring(0, LENGTE_HERHALING_HEADER)));
                    regels.add(
                            new Regel(
                                    GEZOCHTE_PERSOON,
                                    LENGTE_GEZOCHTE_PERSOON_HEADER,
                                    berichtRestant.substring(LENGTE_HERHALING_HEADER, LENGTE_HERHALING_HEADER + LENGTE_GEZOCHTE_PERSOON_HEADER)));
                    regels.add(
                            new Regel(
                                    AKTENUMMER,
                                    LENGTE_AKTENUMMER_HEADER,
                                    berichtRestant.substring(
                                            LENGTE_HERHALING_HEADER
                                                    + LENGTE_GEZOCHTE_PERSOON_HEADER,
                                            LENGTE_HERHALING_HEADER + LENGTE_GEZOCHTE_PERSOON_HEADER + LENGTE_AKTENUMMER_HEADER)));
                    berichtRestant =
                            berichtRestant.substring(
                                    LENGTE_HERHALING_HEADER + LENGTE_GEZOCHTE_PERSOON_HEADER + LENGTE_AKTENUMMER_HEADER, berichtRestant.length());
                    break;
                case "Wa01":
                    regels.add(new Regel(HERHALING, LENGTE_HERHALING_HEADER, berichtRestant.substring(0, LENGTE_HERHALING_HEADER)));
                    regels.add(
                            new Regel(
                                    ANUMMER,
                                    LENGTE_ANUMMER_HEADER,
                                    berichtRestant.substring(LENGTE_HERHALING_HEADER, LENGTE_HERHALING_HEADER + LENGTE_ANUMMER_HEADER)));
                    regels.add(
                            new Regel(
                                    DATUM_GELDIGHEID,
                                    LENGTE_DATUM_HEADER,
                                    berichtRestant.substring(
                                            LENGTE_HERHALING_HEADER + LENGTE_ANUMMER_HEADER,
                                            LENGTE_HERHALING_HEADER + LENGTE_ANUMMER_HEADER + LENGTE_DATUM_HEADER)));
                    berichtRestant =
                            berichtRestant.substring(LENGTE_HERHALING_HEADER + LENGTE_ANUMMER_HEADER + LENGTE_DATUM_HEADER, berichtRestant.length());
                    break;
                case "Wa11":
                    regels.add(new Regel(ANUMMER, LENGTE_ANUMMER_HEADER, berichtRestant.substring(0, LENGTE_ANUMMER_HEADER)));
                    regels.add(
                            new Regel(
                                    DATUM_GELDIGHEID,
                                    LENGTE_DATUM_HEADER,
                                    berichtRestant.substring(LENGTE_ANUMMER_HEADER, LENGTE_ANUMMER_HEADER + LENGTE_DATUM_HEADER)));
                    berichtRestant = berichtRestant.substring(LENGTE_ANUMMER_HEADER + LENGTE_DATUM_HEADER, berichtRestant.length());
                    break;
                case "La01":
                case "Jf21":
                case "Jf31":
                case "Ng01":
                case "Sv11":
                case "Tf11":
                case "Xa01":
                    // Geen kop
                    break;
                default:
                    throw new IllegalArgumentException("Onbekend berichtnummer: " + berichtnummer.getWaarde());
            }
            return berichtRestant;
        } else {
            throw new IllegalArgumentException("Bericht is te kort");
        }
    }

    private String formatRubriek(final String rubriek) {
        return rubriek.substring(0, LENGTE_RUBRIEK_ELEMENT)
                + RUBRIEK_ELEMENT_SEPARATOR
                + rubriek.substring(LENGTE_RUBRIEK_ELEMENT, LENGTE_RUBRIEK_ELEMENT + LENGTE_RUBRIEK_ELEMENT)
                + RUBRIEK_ELEMENT_SEPARATOR
                + rubriek.substring(
                LENGTE_RUBRIEK_ELEMENT + LENGTE_RUBRIEK_ELEMENT, LENGTE_RUBRIEK_ELEMENT + LENGTE_RUBRIEK_ELEMENT + LENGTE_RUBRIEK_ELEMENT);
    }

    private void parseInhoud(final String inhoud) {
        final List<Regel> categorien = new ArrayList<>();
        String restant = inhoud.substring(LENGTE_BERICHTLENGTE);

        // categorien verzamelen
        while (restant.length() > 0) {
            final String categorie = restant.substring(0, LENGTE_CATEGORIENUMMER);
            final int lengte = Integer.parseInt(restant.substring(LENGTE_CATEGORIENUMMER, LENGTE_CATEGORIENUMMER + LENGTE_CATEGORIELENGTE));
            categorien.add(
                    new Regel(
                            categorie,
                            lengte,
                            restant.substring(LENGTE_CATEGORIENUMMER + LENGTE_CATEGORIELENGTE, LENGTE_CATEGORIENUMMER + LENGTE_CATEGORIELENGTE + lengte)));
            restant = restant.substring(LENGTE_CATEGORIENUMMER + LENGTE_CATEGORIELENGTE + lengte, restant.length());
        }

        // elementen verzamelen
        for (int i = 0; i < categorien.size(); i++) {
            String categorie = categorien.get(i).waarde;
            while (categorie.length() > 0) {
                final String element = categorie.substring(0, LENGTE_ELEMENTNUMMER);
                final int lengte = Integer.parseInt(categorie.substring(LENGTE_ELEMENTNUMMER, LENGTE_ELEMENTNUMMER + LENGTE_ELEMENTLENGTE));
                regels.add(
                        new Regel(
                                formatRubriek(categorien.get(i).naam + element),
                                lengte,
                                categorie.substring(LENGTE_ELEMENTNUMMER + LENGTE_ELEMENTLENGTE, LENGTE_ELEMENTNUMMER + LENGTE_ELEMENTLENGTE + lengte)));
                categorie = categorie.substring(LENGTE_ELEMENTNUMMER + LENGTE_ELEMENTLENGTE + lengte, categorie.length());
            }
        }
    }

    /**
     * Regel.
     */
    private static class Regel {
        private final String naam;
        private final int lengte;
        private final String waarde;

        /**
         * Default constructor.
         * @param naam Naam van de regel
         * @param lengte Lengte van de regel
         * @param waarde Waarde van de regel
         */
        public Regel(final String naam, final int lengte, final String waarde) {
            this.naam = naam;
            this.lengte = lengte;
            this.waarde = waarde;
        }

        /**
         * Geeft de waarde van naam.
         * @return de waarde van naam
         */
        public String getNaam() {
            return naam;
        }

        /**
         * Geeft de waarde van lengte.
         * @return de waarde van lengte
         */
        public int getLengte() {
            return lengte;
        }

        /**
         * Geeft de waarde van waarde.
         * @return de waarde van waarde
         */
        public String getWaarde() {
            return waarde;
        }
    }

}
