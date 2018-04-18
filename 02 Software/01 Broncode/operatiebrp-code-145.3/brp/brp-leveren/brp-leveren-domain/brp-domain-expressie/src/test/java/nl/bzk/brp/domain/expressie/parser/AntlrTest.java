/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalLexer;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Assert;
import org.junit.Test;

public class AntlrTest {
    @Test
    public final void testAntlrBrp() {
        // arrange
        final String input = "(WAAR OF ONWAAR)";

        final Context initialContext = new Context();
        initialContext.declareer("persoon", ExpressieType.BRP_METAOBJECT);

        final CharStream cs = new ANTLRInputStream(input);
        final BRPExpressietaalLexer lexer = new BRPExpressietaalLexer(cs);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final BRPExpressietaalParser parser = new BRPExpressietaalParser(tokens);
        final ParserErrorListener errorListener = new ParserErrorListener();
        parser.addErrorListener(errorListener);

        // act
        final ParserRuleContext tree = parser.brp_expressie();
        final ExpressieVisitor visitor = new ExpressieVisitor(initialContext);
        final Expressie expressie = visitor.visit(tree);

        // assert
        Assert.assertEquals("Verwacht geen syntaxfouten", 0, parser.getNumberOfSyntaxErrors());
        Assert.assertEquals("WAAR OF ONWAAR", expressie.alsString());
    }
}
