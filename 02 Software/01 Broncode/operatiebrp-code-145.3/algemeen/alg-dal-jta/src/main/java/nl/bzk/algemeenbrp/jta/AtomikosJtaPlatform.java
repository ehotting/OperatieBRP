/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta;

import com.atomikos.icatch.jta.UserTransactionManager;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

/**
 * Atomikos specifieke implementatie van de de abstracte JTA platform uit Hibernate.
 */
public final class AtomikosJtaPlatform extends AbstractJtaPlatform {

    private static final long serialVersionUID = 1L;
    private final UserTransactionManager atomikosUserTransactionManager;

    /**
     * Default constructor.
     */
    public AtomikosJtaPlatform() {
        atomikosUserTransactionManager = new UserTransactionManager();

        /*
        * De Transaction Service moet op dit punt eigenlijk al opgestart zijn. Als dit niet het geval is,
        * is hoogstwaarschijnlijk de volgorde van het laden van de config van de entitymanager en de transaction service
        * verkeerd. Het hier laten starten van de transaction service leidt in dat geval tot verschillende
        * gestarte transaction services. Dit kan vervolgens resulteren in verschillende transaction managers
        * die elkaars context niet zien.
        */
        atomikosUserTransactionManager.setStartupTransactionService(false);
    }

    @Override
    protected TransactionManager locateTransactionManager() {
        return atomikosUserTransactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return atomikosUserTransactionManager;
    }
}
