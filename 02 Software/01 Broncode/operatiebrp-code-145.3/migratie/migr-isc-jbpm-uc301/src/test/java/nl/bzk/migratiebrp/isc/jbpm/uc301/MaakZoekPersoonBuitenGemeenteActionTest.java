/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakZoekPersoonBuitenGemeenteActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakZoekPersoonBuitenGemeenteAction subject = new MaakZoekPersoonBuitenGemeenteAction(berichtenDao);

    @Test
    public void test() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("1234");
        ii01Bericht.setDoelPartijCode("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "1234567891");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek =
                (ZoekPersoonOpActueleGegevensVerzoekBericht) berichtenDao.leesBericht((Long) result.get("zoekPersoonBuitenGemeenteBericht"));
        Assert.assertNotNull(verzoek);
        Assert.assertEquals("1234567891", verzoek.getBsn());
        Assert.assertNull(verzoek.getAanvullendeZoekcriteria());
    }
}
