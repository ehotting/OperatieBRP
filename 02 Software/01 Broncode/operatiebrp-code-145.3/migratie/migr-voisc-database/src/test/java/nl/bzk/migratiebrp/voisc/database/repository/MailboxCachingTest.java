/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import javax.inject.Inject;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MailboxCachingTest extends AbstractRepositoryTest {

    @Inject
    private MailboxRepository mailboxRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = "voiscTransactionManager")
    public void test() throws CentraleMailboxException {
        final Mailbox mailbox = mailboxRepository.getMailboxByPartijcode("051801");
        Assert.assertNotNull(mailbox);

        mailbox.setMailboxpwd("Blablabla");
        mailboxRepository.save(mailbox);

        final Mailbox mailbox2 = mailboxRepository.getMailboxByNummer(mailbox.getMailboxnr());
        Assert.assertEquals(mailbox.getMailboxpwd(), mailbox2.getMailboxpwd());

    }
}
