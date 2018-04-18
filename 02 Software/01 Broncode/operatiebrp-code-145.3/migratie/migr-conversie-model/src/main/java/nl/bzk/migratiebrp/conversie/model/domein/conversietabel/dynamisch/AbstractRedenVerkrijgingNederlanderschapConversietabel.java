/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 */
public abstract class AbstractRedenVerkrijgingNederlanderschapConversietabel extends
        AbstractAttribuutConversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> {

    /**
     * Maakt een VerblijfsrechtConversietabel object.
     * @param conversieLijst de lijst met alle verblijfstitel conversies
     */
    public AbstractRedenVerkrijgingNederlanderschapConversietabel(
            final List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3RedenNederlandschapCode voegOnderzoekToeLo3(final Lo3RedenNederlandschapCode input, final Lo3Onderzoek onderzoek) {
        final Lo3RedenNederlandschapCode resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new Lo3RedenNederlandschapCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3RedenNederlandschapCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpRedenVerkrijgingNederlandschapCode voegOnderzoekToeBrp(
            final BrpRedenVerkrijgingNederlandschapCode input,
            final Lo3Onderzoek onderzoek) {
        final BrpRedenVerkrijgingNederlandschapCode resultaat;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new BrpRedenVerkrijgingNederlandschapCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpRedenVerkrijgingNederlandschapCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
