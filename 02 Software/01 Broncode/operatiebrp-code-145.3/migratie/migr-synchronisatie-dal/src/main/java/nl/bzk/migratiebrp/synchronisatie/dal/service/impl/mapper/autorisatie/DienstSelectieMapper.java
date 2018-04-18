/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstSelectieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Dienst Selectie entiteit.
 */
// @formatter:off
public final class DienstSelectieMapper extends
        AbstractHistorieMapperStrategie<BrpDienstSelectieInhoud, DienstSelectieHistorie, Dienst> {
    // @formatter:on

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory actie factory
     */
    public DienstSelectieMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstSelectieHistorie historie, final Dienst entiteit) {
        Set<DienstSelectieHistorie> historieSet = entiteit.getDienstSelectieHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstSelectieHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected DienstSelectieHistorie mapHistorischeGroep(final BrpDienstSelectieInhoud groepInhoud, final Dienst entiteit) {
        final DienstSelectieHistorie result = new DienstSelectieHistorie(entiteit);

        result.setSoortSelectie(groepInhoud.getSoortSelectie().getId());
        result.setEersteSelectieDatum(MapperUtil.mapBrpDatumToInteger(groepInhoud.getEersteSelectieDatum()));
        if (groepInhoud.getSelectiePeriodeInMaanden() != null) {
            result.setEenheidSelectieInterval(EenheidSelectieInterval.MAAND.getId());
            result.setSelectieInterval(groepInhoud.getSelectiePeriodeInMaanden().intValue());
        }

        result.setHistorievormSelectie(HistorieVorm.MATERIEEL_FORMEEL.getId());
        result.setIndicatieSelectieresultaatControleren(false);
        result.setLeverwijzeSelectie(entiteit.getLeverwijzeSelectie());
        result.setIndVerzVolBerBijWijzAfniNaSelectie(groepInhoud.getIndVerzendenVolledigBerichtBijPlaatsen());
        result.setLeverwijzeSelectie(groepInhoud.getLeverwijzeSelectie().getId());

        return result;
    }

}
