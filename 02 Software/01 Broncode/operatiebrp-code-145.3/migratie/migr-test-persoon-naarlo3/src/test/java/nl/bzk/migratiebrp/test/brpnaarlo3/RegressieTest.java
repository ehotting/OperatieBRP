/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3;

import java.io.File;

public class RegressieTest extends BrpNaarLo3ConversieTestConfiguratie {

    @Override
    public boolean useMemoryDS() {
        //return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./regressie");
    }
}
