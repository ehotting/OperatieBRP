/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.routering.runtime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX Implementatie.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.routering:name=ROUTERING", description = "JMX Service voor ROUTERING")
public final class JmxImpl implements Jmx, ApplicationContextAware {

    private static final int REDELIVERY_RECEIVE_TIMEOUT = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @return is routering gestart.
     */
    @ManagedAttribute(description = "Applicatie actief")
    public boolean isGestart() {
        return true;
    }

    @Override
    @ManagedOperation(description = "Applicatie afsluiten.")
    public void afsluiten() {
        LOGGER.info("Applicatie afsluiten (aanroep via JMX).");
        applicationContext.getBean(Main.BEAN_NAME, Main.class).stop();
    }

    @Override
    @ManagedOperation(description = "Berichten van een DLQ opnieuw aanbieden.")
    @ManagedOperationParameters(@ManagedOperationParameter(name = "queueNaam",
            description = "Queue naam (let op: NIET de DLQ naam, maar de 'gewone' queue naam)"))
    public long redeliverDlq(final String queueNaam) throws IOException, URISyntaxException, JMSException {
        final BrokerService broker = applicationContext.getBean("routeringCentrale", BrokerService.class);

        final ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(broker.getTransportConnectors().get(0).getConnectUri() + "?jms.prefetchPolicy.all=1");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");

        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return redeliverDlq(jmsTemplate, queueNaam + ".dlq", queueNaam);
    }

    private long redeliverDlq(final JmsTemplate jmsTemplate, final String from, final String to) {
        LOGGER.info("Redelivering from {} to {}", from, to);
        jmsTemplate.setReceiveTimeout(REDELIVERY_RECEIVE_TIMEOUT);
        Message message;

        final AtomicLong counter = new AtomicLong();
        while ((message = jmsTemplate.receive(from)) != null) {
            LOGGER.info("Redelivering message: {}", message);
            final Message receivedMessage = message;
            jmsTemplate.send(to, session -> receivedMessage);
            counter.incrementAndGet();
        }
        LOGGER.info("Redelivered {} messages from {} to {}", counter.get(), from, to);
        return counter.get();
    }

}
