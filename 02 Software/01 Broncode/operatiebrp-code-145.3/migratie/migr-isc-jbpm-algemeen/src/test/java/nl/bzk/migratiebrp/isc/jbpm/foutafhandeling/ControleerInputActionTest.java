/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import org.junit.Test;

public class ControleerInputActionTest {

    private FoutafhandelingPaden maakPaden() {
        final FoutafhandelingPaden result = new FoutafhandelingPaden();
        result.put("end", "Afbreken", true, false);
        result.put("restart", "Opnieuw", false, false);
        return result;
    }

    @Test
    public void testGeenInput() {
        final Map<String, Object> result = new ControleerInputAction().execute(Collections.<String, Object>emptyMap());

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_BEHEERDER));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_PF));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_VB));
    }

    @Test
    public void testBeheerderStandaard() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.TRUE);
        params.put("restart", "restart");
        params.put("foutafhandelingPaden", maakPaden());

        final Map<String, Object> result = new ControleerInputAction().execute(params);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(null, result.get(FoutafhandelingConstants.RESTART));
    }

    @Test
    public void testAutomatischEnd() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.FALSE);
        params.put("restart", "end");
        params.put("foutafhandelingPaden", maakPaden());

        final Map<String, Object> result = new ControleerInputAction().execute(params);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(Boolean.TRUE, result.get(FoutafhandelingConstants.INDICATIE_PF));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_VB));

    }

    @Test
    public void testAutomatischRestart() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.FALSE);
        params.put("restart", "restart");
        params.put("foutafhandelingPaden", maakPaden());

        final Map<String, Object> result = new ControleerInputAction().execute(params);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_PF));
        Assert.assertEquals(Boolean.FALSE, result.get(FoutafhandelingConstants.INDICATIE_VB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeParameters() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.FALSE);
        params.put("andereParameterDanVerwacht", maakPaden());

        new ControleerInputAction().execute(params);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAutomatischGeenPaden() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.FALSE);
        params.put("restart", "end");

        new ControleerInputAction().execute(params);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAutomatischOnbekendPad() {
        final Map<String, Object> params = new HashMap<>();
        params.put("indicatieBeheerder", Boolean.FALSE);
        params.put("restart", "onbekend");
        params.put("foutafhandelingPaden", maakPaden());

        new ControleerInputAction().execute(params);
    }
}
