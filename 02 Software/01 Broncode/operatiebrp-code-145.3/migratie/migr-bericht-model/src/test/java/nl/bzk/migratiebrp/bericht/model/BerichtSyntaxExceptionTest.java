/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import org.junit.Test;

public class BerichtSyntaxExceptionTest {

    @Test(expected = BerichtSyntaxException.class)
    public void testExceptie() throws BerichtSyntaxException {

        throw new BerichtSyntaxException("Bericht syntax probleem.");
    }

    @Test(expected = BerichtSyntaxException.class)
    public void testExceptieMetNullPointer() throws BerichtSyntaxException {

        throw new BerichtSyntaxException("Bericht syntax probleem.", new NullPointerException());
    }

}
