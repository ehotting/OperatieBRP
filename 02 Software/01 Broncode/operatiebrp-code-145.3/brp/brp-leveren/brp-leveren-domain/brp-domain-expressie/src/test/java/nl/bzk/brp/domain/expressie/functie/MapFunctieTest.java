/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;

/**
 */
public class MapFunctieTest {


    @Test
    public final void testFunctieAanroepen() throws ExpressieException {
        TestUtils.assertExceptie("MAP(10)", "Syntax error \"mismatched input ')' expecting ','\" op positie: 7");
    }
}