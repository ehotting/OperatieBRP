/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.excel;

import java.io.InputStream;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;

/**
 * Excel adapter.
 */
public interface ExcelAdapter {

    /**
     * Lees een excel bestand in.
     * @param excelBestand excel bestand
     * @return excel data
     * @throws ExcelAdapterException bij excel fouten
     * @throws Lo3SyntaxException bij verkeerde LO3 syntax
     */
    List<ExcelData> leesExcelBestand(InputStream excelBestand) throws ExcelAdapterException, Lo3SyntaxException;
}