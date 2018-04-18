/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map BRP database betrokkenheden.
 */
@Component
public final class BrpRelatieMapper {

    private final BrpRelatieInhoudMapper relatieMapper;
    private final BrpIdentificatienummersMapper brpIdentificatienummersMapper;
    private final BrpGeslachtsaanduidingMapper brpGeslachtsaanduidingMapper;
    private final BrpGeboorteMapper brpGeboorteMapper;
    private final BrpSamengesteldeNaamMapper brpSamengesteldeNaamMapper;
    private final BrpOuderlijkGezagMapper brpOuderlijkGezagMapper;
    private final BrpOuderMapper brpOuderMapper;
    private final BrpBetrokkenheidIdentiteitMapper brpBetrokkenheidIdentiteitMapper;

    private final BrpIstOuder1Mapper brpIstOuder1Mapper;
    private final BrpIstOuder2Mapper brpIstOuder2Mapper;
    private final BrpIstHuwelijkOfGpMapper brpIstHuwelijkOfGpMapper;
    private final BrpIstKindMapper brpIstKindMapper;
    private final BrpIstGezagsverhoudingMapper brpIstGezagsverhoudingMapper;


    /**
     * Constructor voor BrpRelatieMapper
     * @param brpGeslachtsaanduidingMapper mapper voor geslachtsaanduiding
     * @param relatieMapper mapper voor relatie
     * @param brpIdentificatienummersMapper mapper voor identificatienummers
     * @param brpGeboorteMapper mapper voor geboorte
     * @param brpSamengesteldeNaamMapper mapper voor samengestelde naam
     * @param brpOuderlijkGezagMapper mapper voor ouderlijk gezag
     * @param brpOuderMapper mapper voor ouder
     * @param brpBetrokkenheidIdentiteitMapper mapper voor betrokkenheid identiteit
     * @param brpIstGezagsverhoudingMapper mapper voor IST gezagsverhouding
     * @param brpIstOuder1Mapper mapper voor IST ouder 1
     * @param brpIstKindMapper mapper voor IST king
     * @param brpIstOuder2Mapper mapper voor IST ouder 2
     * @param brpIstHuwelijkOfGpMapper mapper voor IST huwelijk/geregistreerd partnerschap
     */
    @Inject
    public BrpRelatieMapper(BrpGeslachtsaanduidingMapper brpGeslachtsaanduidingMapper, BrpRelatieInhoudMapper relatieMapper,
                            BrpIdentificatienummersMapper brpIdentificatienummersMapper, BrpGeboorteMapper brpGeboorteMapper,
                            BrpSamengesteldeNaamMapper brpSamengesteldeNaamMapper, BrpOuderlijkGezagMapper brpOuderlijkGezagMapper,
                            BrpOuderMapper brpOuderMapper, BrpBetrokkenheidIdentiteitMapper brpBetrokkenheidIdentiteitMapper,
                            BrpIstGezagsverhoudingMapper brpIstGezagsverhoudingMapper, BrpIstOuder1Mapper brpIstOuder1Mapper, BrpIstKindMapper brpIstKindMapper,
                            BrpIstOuder2Mapper brpIstOuder2Mapper, BrpIstHuwelijkOfGpMapper brpIstHuwelijkOfGpMapper) {
        this.brpGeslachtsaanduidingMapper = brpGeslachtsaanduidingMapper;
        this.relatieMapper = relatieMapper;
        this.brpIdentificatienummersMapper = brpIdentificatienummersMapper;
        this.brpGeboorteMapper = brpGeboorteMapper;
        this.brpSamengesteldeNaamMapper = brpSamengesteldeNaamMapper;
        this.brpOuderlijkGezagMapper = brpOuderlijkGezagMapper;
        this.brpOuderMapper = brpOuderMapper;
        this.brpBetrokkenheidIdentiteitMapper = brpBetrokkenheidIdentiteitMapper;
        this.brpIstGezagsverhoudingMapper = brpIstGezagsverhoudingMapper;
        this.brpIstOuder1Mapper = brpIstOuder1Mapper;
        this.brpIstKindMapper = brpIstKindMapper;
        this.brpIstOuder2Mapper = brpIstOuder2Mapper;
        this.brpIstHuwelijkOfGpMapper = brpIstHuwelijkOfGpMapper;
    }

    /**
     * Map de BRP database betrokkenheden naar het BRP conversiemodel.
     * @param ikBetrokkenheidSet de 'ik'-betrokkenheid vanuit de te converteren persoon
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return BRP conversie model
     */
    public List<BrpRelatie> map(final Set<Betrokkenheid> ikBetrokkenheidSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final List<BrpRelatie> result = new ArrayList<>();

        for (final Betrokkenheid ikBetrokkenheid : ikBetrokkenheidSet) {
            final Relatie relatie = ikBetrokkenheid.getRelatie();

            final BrpSoortRelatieCode soortRelatieCode = BrpMapperUtil.mapBrpSoortRelatieCode(relatie.getSoortRelatie());
            final BrpSoortBetrokkenheidCode rolCode = BrpMapperUtil.mapBrpSoortBetrokkenheidCode(ikBetrokkenheid.getSoortBetrokkenheid());
            final BrpStapel<BrpRelatieInhoud> relatieStapel = relatieMapper.map(relatie.getRelatieHistorieSet(), brpOnderzoekMapper);

            if ((AbstractBrpAttribuutMetOnderzoek.equalsWaarde(BrpSoortRelatieCode.HUWELIJK, soortRelatieCode)
                    || AbstractBrpAttribuutMetOnderzoek.equalsWaarde(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, soortRelatieCode))
                    && relatieStapel == null) {
                // De volledige verbintenis wordt wordt, dus we voegen helemaal niets toe
                continue;
            }

            final List<BrpBetrokkenheid> betrokkenheden = bepaalBetrokkenhedenAnderePersonen(brpOnderzoekMapper, ikBetrokkenheid, relatie);

            if (!betrokkenheden.isEmpty()) {
                // Door MR kunnen alle betrokkenheden zijn ontkend
                final BrpRelatie.Builder relatieBuilder =
                        new BrpRelatie.Builder(relatie.getId(), soortRelatieCode, rolCode, new LinkedHashMap<>());
                relatieBuilder.betrokkenheden(betrokkenheden);
                relatieBuilder.relatieStapel(relatieStapel);
                relatieBuilder.ikBetrokkenheid(mapIkBetrokkenheid(ikBetrokkenheid, brpOnderzoekMapper));
                mapIstStapels(relatieBuilder, relatie.getStapels());
                result.add(relatieBuilder.build());
            }
        }

        return result;
    }

    private List<BrpBetrokkenheid> bepaalBetrokkenhedenAnderePersonen(
            final BrpOnderzoekMapper brpOnderzoekMapper,
            final Betrokkenheid ikBetrokkenheid,
            final Relatie relatie) {
        final List<BrpBetrokkenheid> betrokkenheden = new ArrayList<>();
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            final SoortBetrokkenheid ikSoortBetrokkenheid = ikBetrokkenheid.getSoortBetrokkenheid();
            if (!betrokkenheid.getId().equals(ikBetrokkenheid.getId())
                    && (ikSoortBetrokkenheid != SoortBetrokkenheid.OUDER
                    || !betrokkenheid.getSoortBetrokkenheid().equals(ikSoortBetrokkenheid))) {
                final BrpBetrokkenheid brpBetrokkenheid = mapBetrokkenheid(ikBetrokkenheid, betrokkenheid, brpOnderzoekMapper);
                betrokkenheden.add(brpBetrokkenheid);
            }
        }
        return betrokkenheden;
    }

    private void mapIstStapels(final BrpRelatie.Builder relatieBuilder, final Set<Stapel> istStapels) {
        relatieBuilder.istOuder1Stapel(brpIstOuder1Mapper.map(istStapels));
        relatieBuilder.istOuder2Stapel(brpIstOuder2Mapper.map(istStapels));
        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels = brpIstHuwelijkOfGpMapper.map(istStapels);
        if (!istHuwelijkOfGpStapels.isEmpty()) {
            relatieBuilder.istHuwelijkOfGpStapel(istHuwelijkOfGpStapels.get(0));
        }
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = brpIstKindMapper.map(istStapels);
        if (!istKindStapels.isEmpty()) {
            relatieBuilder.istKindStapel(istKindStapels.get(0));
        }
        relatieBuilder.istGezagsverhoudingStapel(brpIstGezagsverhoudingMapper.map(istStapels));
    }

    private BrpBetrokkenheid mapIkBetrokkenheid(final Betrokkenheid ikBetrokkenheid, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpSoortBetrokkenheidCode rol = BrpMapperUtil.mapBrpSoortBetrokkenheidCode(ikBetrokkenheid.getSoortBetrokkenheid());
        final BrpStapel<BrpIdentiteitInhoud>
                identiteitStapel =
                brpBetrokkenheidIdentiteitMapper.map(ikBetrokkenheid.getBetrokkenheidHistorieSet(), brpOnderzoekMapper);
        return new BrpBetrokkenheid(rol, null, null, null, null, null, null, identiteitStapel);
    }

    private BrpBetrokkenheid mapBetrokkenheid(
            final Betrokkenheid ikBetrokkenheid,
            final Betrokkenheid betrokkenheid,
            final BrpOnderzoekMapper brpOnderzoekMapper) {
        // Rol
        final BrpSoortBetrokkenheidCode rol = BrpMapperUtil.mapBrpSoortBetrokkenheidCode(betrokkenheid.getSoortBetrokkenheid());

        // Betrokkenheid
        final BrpStapel<BrpIdentiteitInhoud> identiteitStapel =
                brpBetrokkenheidIdentiteitMapper.map(betrokkenheid.getBetrokkenheidHistorieSet(), brpOnderzoekMapper);
        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel =
                brpOuderlijkGezagMapper.map(betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet(), brpOnderzoekMapper);

        final BrpStapel<BrpOuderInhoud> ouderStapel;
        if (SoortBetrokkenheid.OUDER == ikBetrokkenheid.getSoortBetrokkenheid()) {
            ouderStapel = brpOuderMapper.map(ikBetrokkenheid.getBetrokkenheidOuderHistorieSet(), brpOnderzoekMapper);
        } else {
            ouderStapel = brpOuderMapper.map(betrokkenheid.getBetrokkenheidOuderHistorieSet(), brpOnderzoekMapper);
        }

        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel;
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;

        // Persoon
        final Persoon betrokkenPersoon = betrokkenheid.getPersoon();
        if (betrokkenPersoon == null) {
            // Dit kan bij een 'onbekende' ouder.
            identificatienummersStapel = null;
            geslachtsaanduidingStapel = null;
            geboorteStapel = null;
            samengesteldeNaamStapel = null;
        } else {
            identificatienummersStapel = brpIdentificatienummersMapper.map(betrokkenPersoon.getPersoonIDHistorieSet(), brpOnderzoekMapper);
            geslachtsaanduidingStapel = brpGeslachtsaanduidingMapper.map(betrokkenPersoon.getPersoonGeslachtsaanduidingHistorieSet(), brpOnderzoekMapper);
            geboorteStapel = brpGeboorteMapper.map(betrokkenPersoon.getPersoonGeboorteHistorieSet(), brpOnderzoekMapper);
            samengesteldeNaamStapel = brpSamengesteldeNaamMapper.map(betrokkenPersoon.getPersoonSamengesteldeNaamHistorieSet(), brpOnderzoekMapper);
        }

        return new BrpBetrokkenheid(
                rol,
                identificatienummersStapel,
                geslachtsaanduidingStapel,
                geboorteStapel,
                ouderlijkGezagStapel,
                samengesteldeNaamStapel,
                ouderStapel,
                identiteitStapel);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpRelatieInhoudMapper extends AbstractBrpMapper<RelatieHistorie, BrpRelatieInhoud> {

        @Override
        protected BrpRelatieInhoud mapInhoud(final RelatieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
            final OnderzoekElement onderzoekElement = OnderzoekElement.bepaalOnderzoekElement(historie.getRelatie().getSoortRelatie());

            final BrpDatum datumAanvang =
                    BrpMapperUtil.mapDatum(historie.getDatumAanvang(), brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.datumAanvang, true));
            final BrpGemeenteCode gemeenteCodeAanvang;
            gemeenteCodeAanvang =
                    BrpMapperUtil.mapBrpGemeenteCode(
                            historie.getGemeenteAanvang(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.gemeenteCodeAanvang, true));
            final BrpString woonplaatsnaamAanvang =
                    BrpString.wrap(
                            historie.getWoonplaatsnaamAanvang(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.woonplaatsnaamAanvang, true));
            final BrpString buitenlandsePlaatsAanvang;
            buitenlandsePlaatsAanvang =
                    BrpString.wrap(
                            historie.getBuitenlandsePlaatsAanvang(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.buitenlandsePlaatsAanvang, true));
            final BrpString buitenlandseRegioAanvang;
            buitenlandseRegioAanvang =
                    BrpString.wrap(
                            historie.getBuitenlandseRegioAanvang(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.buitenlandseRegioAanvang, true));
            final BrpLandOfGebiedCode landOfGebiedCodeAanvang;
            final Lo3Onderzoek onderzoekLandOfGebiedCodeAanvang =
                    brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.landOfGebiedCodeAanvang, true);
            landOfGebiedCodeAanvang = BrpMapperUtil.mapBrpLandOfGebiedCode(historie.getLandOfGebiedAanvang(), onderzoekLandOfGebiedCodeAanvang);
            final BrpString omschrijvingLocatieAanvang;
            omschrijvingLocatieAanvang =
                    BrpString.wrap(
                            historie.getOmschrijvingLocatieAanvang(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.omschrijvingLocatieAanvang, true));
            final BrpRedenEindeRelatieCode redenEindeRelatieCode;
            redenEindeRelatieCode =
                    BrpMapperUtil.mapBrpRedenEindeRelatieCode(
                            historie.getRedenBeeindigingRelatie(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.redenBeeindigingRelatie, true));
            final BrpDatum datumEinde =
                    BrpMapperUtil.mapDatum(historie.getDatumEinde(), brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.datumEinde, true));
            final BrpGemeenteCode gemeenteCodeEinde;
            gemeenteCodeEinde =
                    BrpMapperUtil.mapBrpGemeenteCode(
                            historie.getGemeenteEinde(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.gemeenteCodeEinde, true));
            final BrpString woonplaatsnaamEinde =
                    BrpString.wrap(
                            historie.getWoonplaatsnaamEinde(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.woonplaatsnaamEinde, true));
            final BrpString buitenlandsePlaatsEinde;
            buitenlandsePlaatsEinde =
                    BrpString.wrap(
                            historie.getBuitenlandsePlaatsEinde(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.buitenlandsePlaatsEinde, true));
            final BrpString buitenlandseRegioEinde;
            buitenlandseRegioEinde =
                    BrpString.wrap(
                            historie.getBuitenlandseRegioEinde(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.buitenlandseRegioEinde, true));
            final BrpLandOfGebiedCode landOfGebiedCodeEinde;
            final Lo3Onderzoek onderzoekLandOfGebiedCodeEinde = brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.landOfGebiedCodeEinde, true);
            landOfGebiedCodeEinde = BrpMapperUtil.mapBrpLandOfGebiedCode(historie.getLandOfGebiedEinde(), onderzoekLandOfGebiedCodeEinde);

            final BrpString omschrijvingLocatieEinde;
            omschrijvingLocatieEinde =
                    BrpString.wrap(
                            historie.getOmschrijvingLocatieEinde(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, onderzoekElement.omschrijvingLocatieEinde, true));
            return new BrpRelatieInhoud(
                    datumAanvang,
                    gemeenteCodeAanvang,
                    woonplaatsnaamAanvang,
                    buitenlandsePlaatsAanvang,
                    buitenlandseRegioAanvang,
                    landOfGebiedCodeAanvang,
                    omschrijvingLocatieAanvang,
                    redenEindeRelatieCode,
                    datumEinde,
                    gemeenteCodeEinde,
                    woonplaatsnaamEinde,
                    buitenlandsePlaatsEinde,
                    buitenlandseRegioEinde,
                    landOfGebiedCodeEinde,
                    omschrijvingLocatieEinde);
        }
    }

    /**
     * Interne class voor het bepalen van de juiste Element velden voor de relatie t.b.v. onderzoek.
     */
    private static final class OnderzoekElement {
        private final Element datumAanvang;
        private final Element gemeenteCodeAanvang;
        private final Element woonplaatsnaamAanvang;
        private final Element buitenlandsePlaatsAanvang;
        private final Element buitenlandseRegioAanvang;
        private final Element landOfGebiedCodeAanvang;
        private final Element omschrijvingLocatieAanvang;
        private final Element redenBeeindigingRelatie;
        private final Element datumEinde;
        private final Element gemeenteCodeEinde;
        private final Element woonplaatsnaamEinde;
        private final Element buitenlandsePlaatsEinde;
        private final Element buitenlandseRegioEinde;
        private final Element landOfGebiedCodeEinde;
        private final Element omschrijvingLocatieEinde;

        private OnderzoekElement(
                final Element datumAanvang,
                final Element gemeenteCodeAanvang,
                final Element woonplaatsnaamAanvang,
                final Element buitenlandsePlaatsAanvang,
                final Element buitenlandseRegioAanvang,
                final Element landOfGebiedCodeAanvang,
                final Element omschrijvingLocatieAanvang,
                final Element redenBeeindigingRelatie,
                final Element datumEinde,
                final Element gemeenteCodeEinde,
                final Element woonplaatsnaamEinde,
                final Element buitenlandsePlaatsEinde,
                final Element buitenlandseRegioEinde,
                final Element landOfGebiedCodeEinde,
                final Element omschrijvingLocatieEinde) {
            this.datumAanvang = datumAanvang;
            this.gemeenteCodeAanvang = gemeenteCodeAanvang;
            this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
            this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
            this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
            this.landOfGebiedCodeAanvang = landOfGebiedCodeAanvang;
            this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
            this.redenBeeindigingRelatie = redenBeeindigingRelatie;
            this.datumEinde = datumEinde;
            this.gemeenteCodeEinde = gemeenteCodeEinde;
            this.woonplaatsnaamEinde = woonplaatsnaamEinde;
            this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
            this.buitenlandseRegioEinde = buitenlandseRegioEinde;
            this.landOfGebiedCodeEinde = landOfGebiedCodeEinde;
            this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        }

        private static OnderzoekElement bepaalOnderzoekElement(final SoortRelatie soortRelatie) {
            final OnderzoekElement result;

            switch (soortRelatie) {
                case GEREGISTREERD_PARTNERSCHAP:
                    result = createOnderzoekElementGeregistreerdPartnerschap();
                    break;
                case HUWELIJK:
                    result = createOnderzoekElementHuwelijk();
                    break;
                case FAMILIERECHTELIJKE_BETREKKING:
                    result = new OnderzoekElement(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                    break;
                default:
                    throw new IllegalStateException("Onbekende of onjuiste soort relatie");
            }

            return result;
        }

        private static OnderzoekElement createOnderzoekElementHuwelijk() {
            OnderzoekElement result;
            result =
                    new OnderzoekElement(
                            Element.HUWELIJK_DATUMAANVANG,
                            Element.HUWELIJK_GEMEENTEAANVANGCODE,
                            Element.HUWELIJK_WOONPLAATSNAAMAANVANG,
                            Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG,
                            Element.HUWELIJK_BUITENLANDSEREGIOAANVANG,
                            Element.HUWELIJK_LANDGEBIEDAANVANGCODE,
                            Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG,
                            Element.HUWELIJK_REDENEINDECODE,
                            Element.HUWELIJK_DATUMEINDE,
                            Element.HUWELIJK_GEMEENTEEINDECODE,
                            Element.HUWELIJK_WOONPLAATSNAAMEINDE,
                            Element.HUWELIJK_BUITENLANDSEPLAATSEINDE,
                            Element.HUWELIJK_BUITENLANDSEREGIOEINDE,
                            Element.HUWELIJK_LANDGEBIEDEINDECODE,
                            Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE);
            return result;
        }

        private static OnderzoekElement createOnderzoekElementGeregistreerdPartnerschap() {
            OnderzoekElement result;
            result =
                    new OnderzoekElement(
                            Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG,
                            Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE,
                            Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG,
                            Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG,
                            Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG,
                            Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE,
                            Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG,
                            Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE,
                            Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE,
                            Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE,
                            Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE,
                            Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE,
                            Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE,
                            Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE,
                            Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE);
            return result;
        }
    }
}
