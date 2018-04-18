/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;


/**
 * The persistent class for the his_betr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_betr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"betr"}))
public class BetrokkenheidHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_betr_id_generator", sequenceName = "kern.seq_his_betr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_betr_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to Betrokkenheid
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "betr", nullable = false, updatable = false)
    private Betrokkenheid betrokkenheid;

    /**
     * JPA default constructor.
     */
    protected BetrokkenheidHistorie() {}

    /**
     * Maak een nieuwe betrokkenheid ouder historie.
     *
     * @param betrokkenheid betrokkenheid
     */
    public BetrokkenheidHistorie(final Betrokkenheid betrokkenheid) {
        setBetrokkenheid(betrokkenheid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van BetrokkenheidHistorie.
     *
     * @param id de nieuwe waarde voor id van BetrokkenheidHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van betrokkenheid van BetrokkenheidHistorie.
     *
     * @return de waarde van betrokkenheid van BetrokkenheidHistorie
     */
    public Betrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Zet de waarden voor betrokkenheid van BetrokkenheidHistorie.
     *
     * @param betrokkenheid de nieuwe waarde voor betrokkenheid van BetrokkenheidHistorie
     */
    public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
        ValidationUtils.controleerOpNullWaarden("betrokkenheid mag niet null zijn", betrokkenheid);
        this.betrokkenheid = betrokkenheid;
    }
}
