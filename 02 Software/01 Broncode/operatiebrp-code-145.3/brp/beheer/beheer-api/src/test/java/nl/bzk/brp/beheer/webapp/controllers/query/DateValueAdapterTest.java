/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import org.junit.Assert;
import org.junit.Test;

public class DateValueAdapterTest {

    private final DateValueAdapter subject = new DateValueAdapter();

    @Test
    public void test() {
        Assert.assertNotNull(subject.adaptValue("2012"));
        Assert.assertNotNull(subject.adaptValue("201201"));
        Assert.assertNotNull(subject.adaptValue("20120101"));
        Assert.assertNotNull(subject.adaptValue("2012010113"));
        Assert.assertNotNull(subject.adaptValue("201201011330"));
        Assert.assertNotNull(subject.adaptValue("20120101133003"));
        Assert.assertNotNull(subject.adaptValue("20120101133003123"));
    }

}
