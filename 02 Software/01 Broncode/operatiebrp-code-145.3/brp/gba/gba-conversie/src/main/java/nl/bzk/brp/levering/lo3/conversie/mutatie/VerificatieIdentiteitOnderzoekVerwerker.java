/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import org.springframework.stereotype.Component;

/**
 * Verwerk onderzoeken op de a-laag (identiteit groep) van verificatie.
 */
@Component
public class VerificatieIdentiteitOnderzoekVerwerker extends BasisIdentiteitOnderzoekVerwerker<Lo3InschrijvingInhoud> {

    /**
     * Default constructor.
     */
    public VerificatieIdentiteitOnderzoekVerwerker() {
        super(VerificatieMapper.IDENTITEIT_GROEP_ELEMENT);
    }

}
