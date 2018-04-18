/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze enum type representeert de LO3 waarde voor reden opschorting bijhouding.
 */
public enum Lo3RedenOpschortingBijhoudingCodeEnum {

    /**
     * Overlijden.
     */
    OVERLIJDEN("O", "Overlijden"),

    /**
     * Emigratie.
     */
    EMIGRATIE("E", "Emigratie"),

    /**
     * Ministerieel besluit.
     */
    MINISTERIEEL_BESLUIT("M", "Bijzondere status"),

    /**
     * Fout.
     */
    FOUT("F", "Fout"),

    /**
     * Directe registratie in de RNI.
     */
    RNI("R", "RNI"),

    /**
     * Onbekend.
     */
    ONBEKEND(".", "Onbekend");


    private static final Map<String, Lo3RedenOpschortingBijhoudingCodeEnum> CODE_MAP = new HashMap<>();

    static {
        for (final Lo3RedenOpschortingBijhoudingCodeEnum value : Lo3RedenOpschortingBijhoudingCodeEnum.values()) {
            CODE_MAP.put(value.getCode(), value);
        }
    }

    private final String code;
    private String label;


    Lo3RedenOpschortingBijhoudingCodeEnum(final String code, final String label) {
        this.code = code;
        this.setLabel(label);
    }

    /**
     * Bestaat de gegeven code in de enumeratie.
     * @param code code
     * @return true, als de code bestaat, anders false
     */
    public static boolean containsCode(final String code) {
        return CODE_MAP.containsKey(code);
    }

    /**
     * Geef de enumeratie waarde voor de gegeven code.
     * @param code code
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3RedenOpschortingBijhoudingCodeEnum getByCode(final String code) {
        return CODE_MAP.get(code);
    }

    /**
     * Geef de enumeratie waarde voor (de code van) het gegeven element.
     * @param element element
     * @return de enumeratie waarde, null als de code niet gevonden kan worden
     */
    public static Lo3RedenOpschortingBijhoudingCodeEnum getByElement(final Lo3RedenOpschortingBijhoudingCode element) {
        return element == null ? null : getByCode(element.getWaarde());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de enumeratie als element.
     * @return element
     */
    public Lo3RedenOpschortingBijhoudingCode asElement() {
        return new Lo3RedenOpschortingBijhoudingCode(code);
    }

    /**
     * Is de enumeratie waarde gelijk aan (de code van) het element?
     * @param element element
     * @return true, als de waarde gelijk is, anders false
     */
    public boolean equalsElement(final Lo3RedenOpschortingBijhoudingCode element) {
        return Lo3Validatie.isElementGevuld(element) && code.equals(element.getWaarde());
    }

    /**
     * Geef de waarde van label.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Zet de waarde van label.
     * @param label the label to set
     */
    void setLabel(final String label) {
        this.label = label;
    }

}
