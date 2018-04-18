/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime;

import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.junit.Test;

public class StartupIT extends AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void test() throws InterruptedException {
        for (int i = 5; i > 0; i--) {
            LOGGER.info("Sleeping for " + i + " seconds...");
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
