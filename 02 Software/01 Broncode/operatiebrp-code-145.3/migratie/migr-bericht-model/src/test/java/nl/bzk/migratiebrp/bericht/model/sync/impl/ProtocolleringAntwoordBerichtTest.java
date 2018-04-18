/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;

import org.junit.Assert;
import org.junit.Test;

public class ProtocolleringAntwoordBerichtTest {
    private static final String FOUTMELDING = "Foutmelding";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testAntwoordBericht() throws BerichtInhoudException {
        final ProtocolleringAntwoordBericht bericht = new ProtocolleringAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());

        final ProtocolleringAntwoord antwoord = new ProtocolleringAntwoord(1);
        bericht.addProtocolleringAntwoord(antwoord);

        antwoord.setStatus(StatusType.FOUT);
        antwoord.setFoutmelding(FOUTMELDING);

        final String xml = bericht.format();
        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(ProtocolleringAntwoordBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
        Assert.assertEquals(StatusType.FOUT, bericht.getProtocolleringAntwoord().get(0).getStatus());
        Assert.assertEquals(FOUTMELDING, bericht.getProtocolleringAntwoord().get(0).getFoutmelding());
    }
}
