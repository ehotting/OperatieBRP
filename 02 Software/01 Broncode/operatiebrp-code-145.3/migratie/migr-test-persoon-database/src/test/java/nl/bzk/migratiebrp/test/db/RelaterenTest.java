/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.io.File;
import java.io.FilenameFilter;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;

public class RelaterenTest extends DbConversieTestConfiguratie {

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getInputFolder()
     */
    @Override
    public File getInputFolder() {
        return new File("./relateren");
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.db.DbConversieTestConfiguratie#getThemaFilter()
     */
    @Override
    public FilenameFilter getThemaFilter() {
        return new BaseFilter(FilterType.DIRECTORY);
//        return new EqualsFilter("JanPaul", FilterType.DIRECTORY);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.dal.runner.TestConfiguratie#getCasusFilter()
     */
    @Override
    public FilenameFilter getCasusFilter() {
        return new BaseFilter(FilterType.FILE);
//        return new StartsWithFilter("HUWL", FilterType.FILE);
    }
}
