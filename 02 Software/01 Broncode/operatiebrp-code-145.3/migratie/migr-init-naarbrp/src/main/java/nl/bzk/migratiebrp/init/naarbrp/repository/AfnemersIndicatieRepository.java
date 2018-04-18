/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;

/**
 * Repository voor het lezen van Autorisatie-berichten uit de GBA-V database.
 */
public interface AfnemersIndicatieRepository {

    /**
     * Laad de initiele vulling autorisatie tabel met de berichten die naar de BRP database gestuurd
     * moeten worden.
     */
    void laadInitVullingAfnIndTable();

    /**
     * Zoekt in de initiele vulling tabel naar afnemerindicaties met de aangegeven
     * ConversieResultaat status. Deze regels worden doorgegeven aan de verwerker.
     * @param zoekConversieResultaat de resultaat/status code voor de te selecteren berichten.
     * @param verwerker om de berichten mee te verwerken.
     * @param batchGrootte de grootte van te verwerken batches
     * @return Indicatie of er nog mee te verwerken berichten zijn
     */
    boolean verwerkAfnemerindicaties(ConversieResultaat zoekConversieResultaat, BerichtVerwerker<AfnemersindicatiesBericht> verwerker, int batchGrootte);

    /**
     * Update de status/resultaat van de conversie voor een groep afnemers.
     * @param aNummers De a-nummers
     * @param conversieResultaat De nieuwe status/resultaat waarde
     */
    void updateAfnemersIndicatiesBerichtStatus(List<String> aNummers, ConversieResultaat conversieResultaat);

}
