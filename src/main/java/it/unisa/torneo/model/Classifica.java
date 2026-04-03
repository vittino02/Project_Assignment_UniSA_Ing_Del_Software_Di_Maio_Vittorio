/**
 * @file Classifica.java
 * @brief Rappresenta la classifica corrente del torneo.
 *
 * La classifica è costituita da un insieme di voci, una per ogni squadra,
 * ed è aggiornata a partire dalle partite registrate.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @brief Rappresenta la Classifica con le varie posizioni
 */

public class Classifica {

    private final List<VoceClassifica> voci;

    /**
     * @brief Costruisce una classifica vuota.
     *
     * @post la classifica non contiene alcuna voce
     */
    public Classifica() {
        this.voci = new ArrayList<>();
    }

    /**
     * @brief Aggiorna la classifica a partire da squadre e partite del torneo.
     *
     * @param[in] squadre elenco delle squadre del torneo
     * @param[in] partite elenco delle partite del torneo
     * @pre squadre != null
     * @pre partite != null
     * @post la classifica contiene una voce per ogni squadra
     * @post il punteggio di ogni squadra è coerente con le partite registrate
     */
    public void aggiorna(List<Squadra> squadre, List<Partita> partite) {
        if (squadre == null || partite == null) {
            throw new IllegalArgumentException("Squadre e partite non possono essere null.");
        }

        voci.clear();

        for (Squadra squadra : squadre) {
            voci.add(new VoceClassifica(squadra, 0));
        }

        for (Partita partita : partite) {
            VoceClassifica voceCasa = trovaVoce(partita.getSquadraCasa());
            VoceClassifica voceOspite = trovaVoce(partita.getSquadraOspite());

            if (voceCasa != null) {
                voceCasa.incrementaPunti(partita.getPuntiCasa());
            }
            if (voceOspite != null) {
                voceOspite.incrementaPunti(partita.getPuntiOspite());
            }
        }

        ordinaPerPunteggio();
    }

    /**
     * @brief Ordina la classifica in ordine decrescente di punteggio.
     *
     * @post le voci sono ordinate per punti decrescenti
     */
    public void ordinaPerPunteggio() {
        voci.sort(Comparator.comparingInt(VoceClassifica::getPunti).reversed());
    }

    /**
     * @brief Restituisce le voci della classifica.
     *
     * @return copia della lista delle voci di classifica
     */
    public List<VoceClassifica> getVoci() {
        return new ArrayList<>(voci);
    }

    /**
     * @brief Cerca la voce di classifica associata a una squadra.
     *
     * @param[in] squadra squadra da cercare
     * @return voce associata alla squadra, oppure null se non trovata
     */
    private VoceClassifica trovaVoce(Squadra squadra) {
        for (VoceClassifica voce : voci) {
            if (voce.getSquadra() == squadra) {
                return voce;
            }
        }
        return null;
    }
}