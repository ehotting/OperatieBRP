/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP land code.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpLandOfGebiedCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Nederland.
     */
    public static final BrpLandOfGebiedCode NEDERLAND = new BrpLandOfGebiedCode("6030");
    private static final int LENGTE_CODE = 4;
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpLandOfGebiedCode object.
     * @param waarde de BRP waarde die een land identificeert binnen BRP
     */
    public BrpLandOfGebiedCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpLandOfGebiedCode object met onderzoek.
     * @param waarde de BRP code die een land identificeert binnen BRP
     * @param onderzoek het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpLandOfGebiedCode(@Element(name = "waarde") final String waarde, @Element(name = "onderzoek") final Lo3Onderzoek onderzoek) {
        super(waarde, LENGTE_CODE, onderzoek);
    }

    /**
     * Unwrap een BrpLandOfGebiedCode object om de code waarde terug te krijgen.
     * @param attribuut De BrpLandOfGebiedCode, mag null zijn.
     * @return Een Short code, of null als de BrpLandOfGebiedCode null was.
     */
    public static String unwrap(final BrpLandOfGebiedCode attribuut) {
        return (String) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(attribuut);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde")
    public String getWaarde() {
        return unwrap(this);
    }

    /**
     * Geeft een kopie van het attribuut terug zonder onderzoek.
     * @return Geeft een kopie van het attribuut terug zonder onderzoek
     */
    public BrpLandOfGebiedCode verwijderOnderzoek() {
        if (getWaarde() == null) {
            return null;
        }
        return new BrpLandOfGebiedCode(getWaarde());
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpLandOfGebiedCode.
     * @param waarde de code van het land/gebied
     * @param onderzoek het Lo3 onderzoek
     * @return BrpLandOfGebiedCode object met daarin waarde en onderzoek
     */
    public static BrpLandOfGebiedCode wrap(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpLandOfGebiedCode(waarde, onderzoek);
    }
}
