/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;

/**
 * Overlijden converteerder.
 */
public final class BrpOverlijdenConverteerder extends AbstractBrpCategorieConverteerder<Lo3OverlijdenInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpOverlijdenConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpCategorieConverteerder#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3OverlijdenInhoud> bepaalConverteerder(final B inhoud) {
        if (inhoud instanceof BrpOverlijdenInhoud) {
            return (BrpGroepConverteerder<B, Lo3OverlijdenInhoud>) new OverlijdenConverteerder(attribuutConverteerder);
        }

        throw new IllegalArgumentException("BrpOverlijdenConverteerder bevat geen Groep converteerder voor: " + inhoud);
    }

    /**
     * Converteerder die weet hoe je een Lo3OverlijdenInhoud rij moet aanmaken en hoe een BrpOverlijdenInhoud omgezet
     * moet worden naar Lo3OverlijdenInhoud.
     */
    @Requirement({Requirements.CAP001,
            Requirements.CAP001_BL01,
            Requirements.CAP001_BL02,
            Requirements.CGR08,
            Requirements.CGR08_BL01,
            Requirements.CGR08_BL02,
            Requirements.CGR08_BL03})
    public static final class OverlijdenConverteerder extends AbstractBrpGroepConverteerder<BrpOverlijdenInhoud, Lo3OverlijdenInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public OverlijdenConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3OverlijdenInhoud maakNieuweInhoud() {
            return new Lo3OverlijdenInhoud(null, null, null);
        }

        @Override
        public Lo3OverlijdenInhoud vulInhoud(
                final Lo3OverlijdenInhoud lo3Inhoud,
                final BrpOverlijdenInhoud brpInhoud,
                final BrpOverlijdenInhoud brpVorigeInhoud) {

            final Lo3OverlijdenInhoud.Builder builder = new Lo3OverlijdenInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.setDatum(null);
                builder.setGemeenteCode(null);
                builder.setLandCode(null);
            } else {
                builder.setDatum(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatum()));

                final Lo3GemeenteLand locatie =
                        getAttribuutConverteerder().converteerLocatie(
                                brpInhoud.getGemeenteCode(),
                                brpInhoud.getBuitenlandsePlaats(),
                                brpInhoud.getBuitenlandseRegio(),
                                brpInhoud.getLandOfGebiedCode(),
                                brpInhoud.getOmschrijvingLocatie());

                builder.setGemeenteCode(locatie.getGemeenteCode());
                builder.setLandCode(locatie.getLandCode());
            }

            return builder.build();
        }

    }
}
