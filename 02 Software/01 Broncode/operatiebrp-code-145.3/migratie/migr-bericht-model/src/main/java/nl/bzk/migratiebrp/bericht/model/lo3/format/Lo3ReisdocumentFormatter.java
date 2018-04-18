/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Format een Lo3 reisdocument.
 */
public final class Lo3ReisdocumentFormatter implements Lo3CategorieFormatter<Lo3ReisdocumentInhoud> {

    @Override
    public void format(final Lo3ReisdocumentInhoud categorie, final Lo3Formatter formatter) {
        formatter.element(Lo3ElementEnum.ELEMENT_3510, Lo3Format.format(categorie.getSoortNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3520, Lo3Format.format(categorie.getNummerNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3530, Lo3Format.format(categorie.getDatumUitgifteNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3540, Lo3Format.format(categorie.getAutoriteitVanAfgifteNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3550, Lo3Format.format(categorie.getDatumEindeGeldigheidNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3560, Lo3Format.format(categorie.getDatumInhoudingVermissingNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3570, Lo3Format.format(categorie.getAanduidingInhoudingVermissingNederlandsReisdocument()));
        formatter.element(Lo3ElementEnum.ELEMENT_3610, Lo3Format.format(categorie.getSignalering()));
    }

}
