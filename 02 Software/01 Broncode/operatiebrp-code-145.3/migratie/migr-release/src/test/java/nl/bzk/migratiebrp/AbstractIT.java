/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Properties;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.runtime.VoiscMailbox;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext jtaContext;
    private static GenericXmlApplicationContext mailboxContext;
    private static GenericXmlApplicationContext routeringContext;
    private static GenericXmlApplicationContext brpContext;
    private static GenericXmlApplicationContext voiscContext;
    private static GenericXmlApplicationContext iscContext;
    private static GenericXmlApplicationContext syncContext;
    private static GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOGGER.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket mailboxDatabasePort = new ServerSocket(0)) {
            LOGGER.info("Configuring mailbox to port: " + mailboxDatabasePort.getLocalPort());
            portProperties.setProperty("test.mailbox.port", Integer.toString(mailboxDatabasePort.getLocalPort()));
        }
        try (ServerSocket routeringPort = new ServerSocket(0)) {
            LOGGER.info("Configuring routering to port: " + routeringPort.getLocalPort());
            portProperties.setProperty("test.routering.port", Integer.toString(routeringPort.getLocalPort()));
        }
        try (ServerSocket messagebrokerPort = new ServerSocket(0)) {
            LOGGER.info("Configuring messagebroker to port: " + messagebrokerPort.getLocalPort());
            portProperties.setProperty("test.messagebroker.port", Integer.toString(messagebrokerPort.getLocalPort()));
        }

        databaseContext = startContext("DATABASE", portProperties, "classpath:test-embedded-database.xml");
        jtaContext = startContext("JTA", portProperties, "classpath:test-embedded-jta.xml");

        final Properties mailboxProperties = new Properties();
        mailboxProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
        mailboxProperties.setProperty("mailbox.factory.type", "memory");
        mailboxProperties.setProperty("mailbox.jmx.serialize.port", "0");
        mailboxContext = startContext("MAILBOX", mailboxProperties, "classpath:tools-mailbox.xml");

        final Properties routeringProperties = new Properties();
        routeringProperties.setProperty("routering.activemq.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        routeringProperties.setProperty("routering.activemq.data.directory", "target/activemq-data");
        routeringProperties.setProperty("routering.activemq.kahadb.directory", "target/activemq-kahadb");
        routeringProperties.setProperty("routering.activemq.scheduler.directory", "target/activemq-scheduler");
        routeringProperties.setProperty("routering.jmx.serialize.port", "0");
        routeringContext = startContext("ROUTERING", routeringProperties, "classpath:routering-runtime.xml");

        brpContext = startContext("BRP", portProperties, "classpath:test-embedded-brp.xml");

        final Properties syncProperties = new Properties();
        syncProperties.setProperty("sync.queue.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        syncProperties.setProperty("brp.queue.url", "tcp://localhost:" + portProperties.getProperty("test.messagebroker.port"));
        syncProperties.setProperty("atomikos.base.dir", "target/atomikos");
        syncProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));
        syncProperties.setProperty("synchronisatie.jmx.serialize.port", "0");
        syncContext = startContext("SYNCHRONISATIE", syncProperties, "classpath:synchronisatie.xml");

        final Properties voiscProperties = new Properties();
        voiscProperties.setProperty("mailbox.host", "localhost");
        voiscProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
        voiscProperties.setProperty("mailbox.ssl.keystore.password", "changeit");
        voiscProperties.setProperty("mailbox.ssl.key.password", "changeit");
        voiscProperties.setProperty("mailbox.ssl.truststore.password", "changeit");
        voiscProperties.setProperty("routering.activemq.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        voiscProperties.setProperty("voisc.jmx.serialize.port", "0");
        voiscProperties.setProperty("voisc.hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        voiscProperties.setProperty("atomikos.base.dir", "target/atomikos");
        voiscProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));
        voiscContext = startVoisc(startContext("VOISC", voiscProperties, "classpath:voisc-runtime.xml"));

        final Properties iscProperties = new Properties();
        iscProperties.setProperty("routering.activemq.url", "tcp://localhost:" + portProperties.getProperty("test.routering.port"));
        iscProperties.setProperty("jbpm.scheduler.idlewaittime", "1000");
        iscProperties.setProperty("isc.jmx.serialize.port", "0");
        iscProperties.setProperty("test.database.port", portProperties.getProperty("test.database.port"));
        iscContext = startContext("ISC", iscProperties, "classpath:isc-runtime.xml");

        final Properties testProperties = new Properties(portProperties);
        testProperties.setProperty("mailbox.host", "localhost");
        testProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
        testProperties.setProperty("mailbox.ssl.keystore.password", "changeit");
        testProperties.setProperty("mailbox.ssl.key.password", "changeit");
        testProperties.setProperty("mailbox.ssl.truststore.password", "changeit");
        testContext = startContext("TEST", testProperties, "classpath:test-context.xml");
    }

    private static GenericXmlApplicationContext startVoisc(final GenericXmlApplicationContext context) {
        // LOGGER.info("startVoisc: SSL");
        // configureSsl(context);
        LOGGER.info("startVoisc: CHECK SSL");
        final VoiscMailbox voiscMailbox = context.getBean(VoiscMailbox.class);
        try {
            final Connection mailboxConnection = voiscMailbox.connectToMailboxServer();
            voiscMailbox.logout(mailboxConnection);
        } catch (final VoiscMailboxException ce) {
            throw new IllegalArgumentException("Kan geen verbinding maken met de mailbox", ce);
        }

        final Map<String, RegisterService> registerServices = context.getBeansOfType(RegisterService.class);
        for (final RegisterService<?> registerService : registerServices.values()) {
            registerService.refreshRegister();
        }

        LOGGER.info("startVoisc: JOBS");
        final Scheduler scheduler = context.getBean("scheduler", Scheduler.class);
        try {
            scheduler.start();
        } catch (final SchedulerException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("startVoisc: LISTENERS");
        final DefaultMessageListenerContainer voiscBerichtListenerContainer =
                context.getBean("voiscBerichtListenerContainer", DefaultMessageListenerContainer.class);
        voiscBerichtListenerContainer.start();

        return context;
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @AfterClass
    public static void stopTestContext() {
        closeContext("TEST", testContext);
        closeContext("ISC", iscContext);
        closeContext("VOISC", voiscContext);
        closeContext("SYNCHRONISATIE", syncContext);
        closeContext("BRP", brpContext);
        closeContext("ROUTERING", routeringContext);
        closeContext("MAILBOX", mailboxContext);
        closeContext("JTA", jtaContext);
        closeContext("DATABASE", databaseContext);
    }

    private static GenericXmlApplicationContext startContext(final String name, final Properties properties, final String configuration) {
        LOGGER.info("Starten {} ...", name);

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(configuration);
        context.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", properties));
        context.refresh();

        LOGGER.info("{} gestart", name);

        return context;
    }

    private static void closeContext(final String name, final Closeable context) {
        if (context != null) {
            try {
                LOGGER.info("Stoppen {} context ...", name);
                context.close();
                LOGGER.info("{} context gestopt", name);
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten {} context", name, e);
            }
        }
    }

    // ***********************************
    // ***********************************
    // MAILBOX
    // ***********************************
    // ***********************************

    @Autowired
    private MailboxRepository mailboxRepository;
    @Autowired
    private MailboxClient mailboxClient;

    protected void verstuurBerichtViaMailbox(final String messageId, final String correlatieId, final String verzendendeMailbox,
                                             final String ontvangendeMailbox,
                                             final String bericht) {

//        final Collection<Mailbox> alleMailboxen = mailboxRepository.getAllMailboxen();
//        alleMailboxen.forEach(mailbox -> LOGGER.info("Mailbox {} gevonden voor partij {}", mailbox.getMailboxnr(), mailbox.getPartijcode()));
        final Mailbox mailboxVerzender = mailboxRepository.getMailboxByNummer(verzendendeMailbox);
        LOGGER.info("Verwerk uitgaand bericht voor mailbox: {}", mailboxVerzender);
        Assert.assertNotNull("Geen mailbox gegevens gevonden voor verzendende mailbox: " + verzendendeMailbox, mailboxVerzender);
        final Mailbox mailboxOntvanger = mailboxRepository.getMailboxByNummer(ontvangendeMailbox);
        Assert.assertNotNull("Geen mailbox gegevens gevonden voor ontvangende mailbox: " + ontvangendeMailbox, mailboxOntvanger);

        // Genereer ID
        final nl.bzk.migratiebrp.voisc.database.entities.Bericht mailboxBericht = new nl.bzk.migratiebrp.voisc.database.entities.Bericht();
        mailboxBericht.setMessageId(messageId);
        mailboxBericht.setCorrelationId(correlatieId);
        mailboxBericht.setBerichtInhoud(bericht);
        mailboxBericht.setOriginator(verzendendeMailbox);
        mailboxBericht.setRecipient(ontvangendeMailbox);
        mailboxBericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);

        // SSL verbinding opbouwen
        try (final Connection mailboxConnection = mailboxClient.connect()) {

            try {
                // Inloggen op de Mailbox
                mailboxClient.logOn(mailboxConnection, mailboxVerzender);

                // Versturen berichten naar Mailbox
                mailboxClient.putMessage(mailboxConnection, mailboxBericht);
            } catch (final VoaException e) {
                throw new RuntimeException("Fout bij versturen bericht.", e);
            } finally {
                // Logout
                try {
                    mailboxClient.logOff(mailboxConnection);
                } catch (final VoaException e) {
                    throw new RuntimeException("Fout bij uitloggen na versturen bericht.", e);
                }
            }
        }
    }

    // ***********************************
    // ***********************************
    // Databases
    // ***********************************
    // ***********************************

    @Autowired
    @Named("brpDataSource")
    private DataSource brpDataSource;

    @Autowired
    private JtaTransactionManager transactionManager;

    protected <T> T executeInTransaction(final TransactionCallback<T> work) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(work);
    }

    protected int countPersonenInBrp() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(brpDataSource);
        return jdbcTemplate.queryForObject("select count(*) from kern.pers where srt=1", Integer.class);
    }

    // ***********************************
    // ***********************************
    // Queues
    // ***********************************
    // ***********************************

    private JmsTemplate brpJmsTemplate;

    @Autowired
    @Named(value = "brpConnectionFactory")
    public void setBrpConnectionFactory(final ConnectionFactory connectionFactory) {
        brpJmsTemplate = new JmsTemplate(connectionFactory);
        brpJmsTemplate.setReceiveTimeout(10000);
    }

    protected Message expectBrpMessage(final String destinationName) {
        Message message;
        try {
            message = brpJmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected String getContent(final Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("BerichtType niet ondersteund");
        }
    }
}
