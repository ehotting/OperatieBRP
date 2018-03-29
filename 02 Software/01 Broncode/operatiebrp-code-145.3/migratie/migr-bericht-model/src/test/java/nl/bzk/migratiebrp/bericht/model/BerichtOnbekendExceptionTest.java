/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import org.junit.Test;

public class BerichtOnbekendExceptionTest {

    @Test(expected = BerichtOnbekendException.class)
    public void testExceptie() throws BerichtOnbekendException {

        throw new BerichtOnbekendException("Bericht onbekend");
    }

    @Test(expected = BerichtOnbekendException.class)
    public void testExceptieMetNullPointer() throws BerichtOnbekendException {

        throw new BerichtOnbekendException("Bericht onbekend", new NullPointerException());
    }

}