/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class VerbijfsrechtVoorwaardeRegelTest {

    @Inject
    private VerbijfsrechtVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    /**
     * Test of vertaalWaardeVanRubriek method, of class VerbijfsrechtVoorwaardeRegel.
     */
    @Test
    public void testVertaalWaardeVanRubriek() throws GbaVoorwaardeOnvertaalbaarExceptie {
        testUtil.testVoorwaarde("10.39.10 GA1 09", "Persoon.Verblijfsrecht.AanduidingCode E= \"09\"");
    }

    /**
     * Test of filter method, of class VerbijfsrechtVoorwaardeRegel.
     */
    @Test
    public void testFilterTrue() {
        instance.filter("10.39.10 GA1 09");
    }

    /**
     * Test of filter method, of class VerbijfsrechtVoorwaardeRegel.
     */
    @Test
    public void testFilterFalse() {
        instance.filter("10.39.20 GA1 09");
    }

}
