/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakIf01BerichtUActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakIf01BerichtUAction subject = new MaakIf01BerichtUAction(berichtenDao);

    @Test
    public void test() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("1234");
        ii01Bericht.setDoelPartijCode("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelPartijCode());
        Assert.assertEquals("5678", if01Bericht.getBronPartijCode());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("U", if01Bericht.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

}
