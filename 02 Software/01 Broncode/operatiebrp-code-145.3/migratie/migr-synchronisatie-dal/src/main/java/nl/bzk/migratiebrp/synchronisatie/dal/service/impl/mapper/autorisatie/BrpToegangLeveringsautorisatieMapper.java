/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsautorisatieHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map toegang leveringsautorisatie van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpToegangLeveringsautorisatieMapper extends
        AbstractBrpMapper<ToegangLeveringsautorisatieHistorie, BrpToegangLeveringsautorisatieInhoud> {

    /**
     * Map een database entiteit toegang leveringsautorisatie naar een BRP conversie model object.
     * @param toegangLeveringsautorisatieHistorieSet database entiteit
     * @return conversie model object
     */
    public BrpStapel<BrpToegangLeveringsautorisatieInhoud> mapToegangLeveringsautorisatie(
            final Set<ToegangLeveringsautorisatieHistorie> toegangLeveringsautorisatieHistorieSet) {
        return map(toegangLeveringsautorisatieHistorieSet, null);
    }

    @Override
    protected BrpToegangLeveringsautorisatieInhoud mapInhoud(
            final ToegangLeveringsautorisatieHistorie historie,
            final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpToegangLeveringsautorisatieInhoud(
                historie.getAfleverpunt(),
                BrpMapperUtil.mapDatum(historie.getDatumIngang()),
                BrpMapperUtil.mapDatum(historie.getDatumEinde()),
                historie.getIndicatieGeblokkeerd(),
                historie.getNaderePopulatiebeperking());
    }
}
