/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

/**
 * Deze class representeert de inhoud van de groep BRP Bijzondere verbijfsrechtelijke positie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud extends AbstractBrpIndicatieGroepInhoud {
    /**
     * Maakt een BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud object.
     * @param indicatie BrpBoolean met daarin de indicatie waarde en onderzoek
     * @param migratieRedenOpnameNationaliteit de reden opname nationaliteit tbv migratie/conversie
     * @param migratieRedenBeeindigingNationaliteit de reden beeindiging nationaliteit tbv migratie/conversie
     */
    public BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud(
            @Element(name = "indicatie", required = true) final BrpBoolean indicatie,
            @Element(name = "migrRdnOpnameNation", required = false) final BrpString migratieRedenOpnameNationaliteit,
            @Element(name = "migrRdnBeeindigingNation", required = false) final BrpString migratieRedenBeeindigingNationaliteit) {
        super(indicatie, migratieRedenOpnameNationaliteit, migratieRedenBeeindigingNationaliteit);
    }
}
