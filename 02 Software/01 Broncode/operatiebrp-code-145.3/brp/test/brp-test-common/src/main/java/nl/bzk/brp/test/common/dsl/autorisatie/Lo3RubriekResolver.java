/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;

/**
 * Lo3Rubriek resolver.
 */
public class Lo3RubriekResolver {

    static ThreadLocal<Function<Set<String>, List<Lo3Rubriek>>> instance = new ThreadLocal<>();

}
