/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore("Niet voor testuitvoer, alleen lokaal gebruiken om een bericht op de queue te zetten")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class SyncRequestQueueUtil {

    @Inject
    private JmsTemplate jmsTemplate;

    /*@Test*/
    public void test() throws UnsupportedEncodingException {
        final byte[] lg01 = readResourceAsBytes("Simpel01-8172387435.txt");
        // GBACharacterSet.convertTeletexByteArrayToString(lg01);
        final String lg01Bericht = new String(lg01, EncodingConstants.CHARSET_NAAM);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(lg01Bericht);
            }
        });
    }

    private byte[] readResourceAsBytes(final String resource) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (
                final InputStream is = SyncRequestQueueUtil.class.getResourceAsStream(resource)) {
            final byte[] buffer = new byte[4096];
            int length;
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }

        return baos.toByteArray();
    }
}
