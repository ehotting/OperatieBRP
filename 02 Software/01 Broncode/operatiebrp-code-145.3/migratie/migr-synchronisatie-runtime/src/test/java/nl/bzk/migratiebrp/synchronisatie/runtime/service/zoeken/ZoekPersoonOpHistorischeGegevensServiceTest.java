/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpHistorischeGegevensVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonOpHistorischeGegevensServiceTest {

    @Mock
    private PersoonService persoonDalService;

    @Mock
    private ZoekPersoonFilter filter;

    @InjectMocks
    private ZoekPersoonOpHistorischeGegevensService subject;

    @Test
    public void testPreoperties() {
        Assert.assertEquals(ZoekPersoonOpHistorischeGegevensVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertEquals("ZoekPersoonOpHistorischeGegevensService", subject.getServiceNaam());
    }

    @Test
    public void test() throws BerichtSyntaxException {
        final ZoekPersoonOpHistorischeGegevensVerzoekBericht verzoek = new ZoekPersoonOpHistorischeGegevensVerzoekBericht();
        verzoek.setANummer("1234567890");
        verzoek.setBsn("123456789");
        verzoek.setGeslachtsnaam("Petersen");
        verzoek.setAanvullendeZoekcriteria("00000");

        final List<Persoon> personen = new ArrayList<>();
        personen.add(new Persoon(SoortPersoon.INGESCHREVENE));

        final List<GevondenPersoon> gevondenPersonen = new ArrayList<>();
        gevondenPersonen.add(new GevondenPersoon(1L, "1234567890", "1900"));

        Mockito.when(persoonDalService.zoekPersonenOpHistorischeGegevens("1234567890", "123456789", "Petersen")).thenReturn(personen);
        Mockito.when(filter.filter(personen, "00000")).thenReturn(gevondenPersonen);

        // Execute
        final ZoekPersoonAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEVONDEN, antwoord.getResultaat());
        Assert.assertEquals(Long.valueOf(1), antwoord.getPersoonId());
        Assert.assertEquals("1234567890", antwoord.getAnummer());
        Assert.assertEquals("1900", antwoord.getGemeente());
    }
}
