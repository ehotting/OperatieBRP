/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import java.util.Map;

/**
 * Interface die een bean implementeerd als deze door de Spring decision handler aangeroepen kan worden.
 */
public interface SpringDecision {

    /**
     * Execute.
     * @param parameters input parameters
     * @return decision (Naam van de transitie die gevolgd moet worden)
     */
    String execute(Map<String, Object> parameters);
}
