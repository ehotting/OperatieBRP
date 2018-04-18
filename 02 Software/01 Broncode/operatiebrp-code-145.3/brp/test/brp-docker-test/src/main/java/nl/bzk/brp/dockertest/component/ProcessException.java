/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;

/**
 * Exception class voor fouten die optreden bij uitvoer van OS proces.
 */
final class ProcessException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message de foutmessage
     */
    ProcessException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param e de cause
     */
    ProcessException(final IOException e) {
        super(e);
    }

    /**
     * Constructor.
     *
     * @param message de foutmessage
     * @param e de cause
     */
    ProcessException(final String message, final Exception e) {
        super(message, e);
    }
}
