/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("synchronisatie-brp-mapper-beans.xml")
public class BrpIstHuwelijkOfGpMapperTest {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
            new Partij("Bierum", "000008"),
            SoortAdministratieveHandeling.GBA_INITIELE_VULLING,
            new Timestamp(System.currentTimeMillis()));

    @Inject
    private BrpIstHuwelijkOfGpMapper mapper;

    @Test
    public void testMap() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "05", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);

        stapelVoorkomen.setSoortDocument(new SoortDocument("doc_naam", "doc_omschrijving"));
        stapelVoorkomen.setPredicaat(Predicaat.J);
        stapelVoorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(Geslachtsaanduiding.VROUW);
        stapelVoorkomen.setAdellijkeTitel(AdellijkeTitel.H);
        stapelVoorkomen.setSoortRelatie(SoortRelatie.HUWELIJK);

        stapel.addStapelVoorkomen(stapelVoorkomen);
        stapels.add(stapel);
        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> result = mapper.map(stapels);
        assertNotNull(result);
        assertEquals(1, result.size());
        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> resultStapel = result.get(0);
        assertNotNull(resultStapel);
        assertEquals(1, resultStapel.size());
        final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> brpGroep = resultStapel.get(0);
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = brpGroep.getInhoud();
        final BrpIstRelatieGroepInhoud relatieInhoud = inhoud.getRelatie();
        assertNotNull(relatieInhoud);
        // wel gemapped want standaard groep
        assertEquals("doc_naam", inhoud.getStandaardGegevens().getSoortDocument().getWaarde());
        // wel gemapped want gerelateerde groep
        assertNotNull(relatieInhoud.getPredicaatCode());
        assertNotNull(relatieInhoud.getAdellijkeTitelCode());
        assertEquals(Predicaat.J.toString(), relatieInhoud.getPredicaatCode().getWaarde());
        assertEquals(AdellijkeTitel.H.toString(), relatieInhoud.getAdellijkeTitelCode().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), relatieInhoud.getPredicaatCode().getGeslachtsaanduiding().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), relatieInhoud.getAdellijkeTitelCode().getGeslachtsaanduiding().getWaarde());
        // wel gemapped want huwelijk of gp groep
        assertEquals(SoortRelatie.HUWELIJK.getCode(), inhoud.getSoortRelatieCode().getWaarde());
    }

    @Test
    public void testMapVerkeerdeStapelType() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "01", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        stapel.addStapelVoorkomen(stapelVoorkomen);
        stapels.add(stapel);
        assertEquals(Collections.emptyList(), mapper.map(stapels));
    }

    @Test
    public void testMapJuisteStapelType() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "05", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        stapel.addStapelVoorkomen(stapelVoorkomen);
        stapels.add(stapel);
        assertNotNull(mapper.map(stapels));
    }

    @Test(expected = NullPointerException.class)
    public void testMapNull() {
        mapper.map(null);
    }

    @Test
    public void testMapEmpty() {
        assertEquals(Collections.emptyList(), mapper.map(Collections.emptySet()));
    }
}
