/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapt de BrpOuder stapel uit het migratie model op de BetrokkenheidOuder historie in het operationele BRP model.
 */
public final class BetrokkenheidOuderMapper extends AbstractHistorieMapperStrategie<BrpOuderInhoud, BetrokkenheidOuderHistorie, Betrokkenheid> {

    /**
     * Maakt een BetrokkenheidOuderMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public BetrokkenheidOuderMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final BetrokkenheidOuderHistorie historie, final Betrokkenheid entiteit) {
        entiteit.addBetrokkenheidOuderHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BetrokkenheidOuderHistorie mapHistorischeGroep(final BrpOuderInhoud groepInhoud, final Betrokkenheid entiteit) {
        final BetrokkenheidOuderHistorie result = new BetrokkenheidOuderHistorie(entiteit);
        result.setIndicatieOuderUitWieKindIsGeboren(BrpBoolean.unwrap(groepInhoud.getIndicatieOuderUitWieKindIsGeboren()));

        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getIndicatieOuderUitWieKindIsGeboren(),
                Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN);

        return result;
    }
}
