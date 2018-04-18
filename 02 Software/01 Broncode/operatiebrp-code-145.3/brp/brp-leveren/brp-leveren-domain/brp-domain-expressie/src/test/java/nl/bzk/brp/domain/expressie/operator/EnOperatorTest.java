/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import static nl.bzk.brp.domain.expressie.util.TestUtils.ONBEKEND;
import static nl.bzk.brp.domain.expressie.util.TestUtils.ONWAAR;
import static nl.bzk.brp.domain.expressie.util.TestUtils.WAAR;

import java.util.Arrays;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EnOperatorTest {

    private static final String OPERATOR = "EN";

    private final String operandLinks;
    private final String operandRechts;
    private final String resultaat;
    private final String inverseResultaat;

    public EnOperatorTest(final String op1, final String op2, final String resultaat, final String inverseResultaat) {
        operandLinks = op1;
        operandRechts = op2;
        this.resultaat = resultaat;
        this.inverseResultaat = inverseResultaat;
    }

    @Parameterized.Parameters(name = "{index}: {0} EN {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"WAAR", "WAAR", WAAR, WAAR},
                {"WAAR", "ONWAAR", ONWAAR, ONWAAR},
                {"ONWAAR", "ONWAAR", ONWAAR, ONWAAR},

                // onbekend
                {"WAAR", "NULL", ONBEKEND, ONBEKEND},
                {"ONWAAR", "NULL", ONWAAR, ONWAAR},
        });
    }

    @Test
    public void testStandaardExpressie() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandLinks, operandRechts, OPERATOR);
        final String verwachtResultaat = resultaat;

        TestUtils.testEvaluatie(expressie, verwachtResultaat);
    }

    @Test
    public void testOperandsOmgedraaid() throws ExpressieException {
        final String expressie = String.format("%1$s %3$s %2$s", operandRechts, operandLinks, OPERATOR);
        final String verwachtResultaat = inverseResultaat;

        TestUtils.testEvaluatie(expressie, verwachtResultaat);
    }
}
