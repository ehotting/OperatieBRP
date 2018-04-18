/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.lang.reflect.Field;

import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.SynchronisatieVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SynchroniseerNaarBrpServiceTest {

    private final SyncParameters syncParameters = new SyncParameters();

    @Mock
    private SynchronisatieVerwerker initieleVullingSynchronisatieVerwerker;
    @Mock
    private SynchronisatieVerwerker synchronisatieVerwerker;
    @Mock
    private PlService plService;

    private SynchroniseerNaarBrpService subject;

    @Before
    public void setupSynchronisatieParameters() throws Exception {
        SynchronisatieLogging.init();
        subject = new SynchroniseerNaarBrpService(syncParameters,initieleVullingSynchronisatieVerwerker,synchronisatieVerwerker,plService);
    }

    @Test
    public void testInitieleVulling() throws Exception {
        syncParameters.setInitieleVulling(true);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-msg-id");
        verzoek.setLo3PersoonslijstAlsTeletexString("data");
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        Mockito.when(initieleVullingSynchronisatieVerwerker.verwerk(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class), Matchers.any(Lo3Bericht.class))).thenReturn(antwoord);

        Assert.assertSame(antwoord, subject.verwerkBericht(verzoek));

        Mockito.verify(initieleVullingSynchronisatieVerwerker).verwerk(Matchers.eq(verzoek), Matchers.<Lo3Bericht>any());
        Mockito.verifyNoMoreInteractions(initieleVullingSynchronisatieVerwerker, synchronisatieVerwerker, plService);
    }

    @Test
    public void testSynchronisatie() throws Exception {
        syncParameters.setInitieleVulling(false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-msg-id");
        verzoek.setLo3PersoonslijstAlsTeletexString("data");
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        Mockito.when(synchronisatieVerwerker.verwerk(Matchers.eq(verzoek), Matchers.<Lo3Bericht>any())).thenReturn(antwoord);

        Assert.assertSame(antwoord, subject.verwerkBericht(verzoek));

        Mockito.verify(synchronisatieVerwerker).verwerk(Matchers.eq(verzoek), Matchers.<Lo3Bericht>any());
        Mockito.verifyNoMoreInteractions(initieleVullingSynchronisatieVerwerker, synchronisatieVerwerker, plService);
    }

    @Test
    public void testSynchronisatieVerwerkerException() throws Exception {
        syncParameters.setInitieleVulling(false);

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("verzoek-msg-id");
        verzoek.setLo3PersoonslijstAlsTeletexString("data");

        final BrpPersoonslijst kandidaat = new BrpPersoonslijstBuilder().build();
        final SynchronisatieVerwerkerException exception = new SynchronisatieVerwerkerException(StatusType.ONDUIDELIJK, kandidaat);

        Mockito.when(synchronisatieVerwerker.verwerk(Matchers.eq(verzoek), Matchers.<Lo3Bericht>any())).thenThrow(exception);
        final Kandidaat dummyKandidaat = new Kandidaat();
        dummyKandidaat.setPersoonId(1);
        dummyKandidaat.setVersie(1);
        dummyKandidaat.setLo3PersoonslijstAlsTeletexString("LO3PL");
        Mockito.when(plService.converteerKandidaten(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(new Kandidaat[]{dummyKandidaat});

        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.ONDUIDELIJK, antwoord.getStatus());
        Assert.assertEquals(1, antwoord.getKandidaten().size());

        Mockito.verify(synchronisatieVerwerker).verwerk(Matchers.eq(verzoek), Matchers.<Lo3Bericht>any());
        Mockito.verify(plService).persisteerLogging(Matchers.<Lo3Bericht>any());
        Mockito.verify(plService).converteerKandidaten(Matchers.anyListOf(BrpPersoonslijst.class));

        Mockito.verifyNoMoreInteractions(initieleVullingSynchronisatieVerwerker, synchronisatieVerwerker, plService);
    }
}
