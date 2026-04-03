/**
 * @file Torneo.java
 * @brief Modello principale del torneo di calcio a 5.
 *
 * Questa classe gestisce l'insieme delle squadre registrate,
 * l'insieme delle partite disputate e la classifica corrente del torneo.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

import java.util.ArrayList;
import java.util.List;

/**
 *@brief  Rapprestenta la classe torne e gestisce squadre partite e classifica
 */

public class Torneo {

    private final List<Squadra> squadre;
    private final List<Partita> partite;
    private final Classifica classifica;

    /**
     * @brief Costruisce un torneo vuoto.
     *
     * @post il torneo non contiene squadre né partite
     * @post la classifica è inizializzata
     */
    public Torneo() {
        this.squadre = new ArrayList<>();
        this.partite = new ArrayList<>();
        this.classifica = new Classifica();
    }

    /**
     * @brief Aggiunge una nuova squadra al torneo.
     *
     * @param[in] squadra squadra da aggiungere
     * @pre squadra != null
     * @pre la squadra è completa
     * @pre non esiste già una squadra con lo stesso nome
     * @post la squadra viene aggiunta al torneo
     */
    public void aggiungiSquadra(Squadra squadra) {
        if (squadra == null) {
            throw new IllegalArgumentException("La squadra non può essere null.");
        }
        if (!squadra.isCompleta()) {
            throw new IllegalArgumentException("La squadra deve contenere esattamente 5 giocatori.");
        }
        if (esisteNomeSquadra(squadra.getNome(), null)) {
            throw new IllegalArgumentException("Esiste già una squadra con lo stesso nome.");
        }

        squadre.add(squadra);
        aggiornaClassifica();
    }

    /**
     * @brief Modifica una squadra esistente.
     *
     * @param[in] squadra squadra da modificare
     * @param[in] nuovoNome nuovo nome della squadra
     * @param[in] nuoviGiocatori nuova lista dei giocatori
     * @pre squadra != null
     * @pre nuovoNome != null
     * @pre nuoviGiocatori != null
     * @pre la squadra appartiene al torneo
     * @pre il nuovo nome non è già usato da un'altra squadra
     * @post la squadra viene aggiornata con i nuovi dati
     */
    public void modificaSquadra(Squadra squadra, String nuovoNome, List<Giocatore> nuoviGiocatori) {
        if (squadra == null) {
            throw new IllegalArgumentException("La squadra da modificare non può essere null.");
        }
        if (!squadre.contains(squadra)) {
            throw new IllegalArgumentException("La squadra non appartiene al torneo.");
        }
        if (nuovoNome == null || nuovoNome.isBlank()) {
            throw new IllegalArgumentException("Il nome della squadra non è valido.");
        }
        if (nuoviGiocatori == null) {
            throw new IllegalArgumentException("La lista dei giocatori non può essere null.");
        }
        if (esisteNomeSquadra(nuovoNome, squadra)) {
            throw new IllegalArgumentException("Esiste già un'altra squadra con lo stesso nome.");
        }

        squadra.aggiornaDati(nuovoNome, nuoviGiocatori);
        aggiornaClassifica();
    }

    /**
     * @brief Restituisce l'elenco delle squadre registrate.
     *
     * @return copia della lista delle squadre del torneo
     */
    public List<Squadra> getSquadre() {
        return new ArrayList<>(squadre);
    }

    /**
     * @brief Aggiunge una nuova partita al torneo.
     *
     * @param[in] partita partita da aggiungere
     * @pre partita != null
     * @pre le squadre della partita appartengono al torneo
     * @post la partita viene aggiunta al torneo
     * @post la classifica viene aggiornata
     */
    public void aggiungiPartita(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere null.");
        }
        if (!squadre.contains(partita.getSquadraCasa()) || !squadre.contains(partita.getSquadraOspite())) {
            throw new IllegalArgumentException("Le squadre della partita devono appartenere al torneo.");
        }

        partite.add(partita);
        aggiornaClassifica();
    }

    /**
     * @brief Modifica una partita esistente.
     *
     * @param[in] partitaVecchia partita da modificare
     * @param[in] partitaNuova nuova versione della partita
     * @pre partitaVecchia != null
     * @pre partitaNuova != null
     * @pre la partita vecchia appartiene al torneo
     * @pre le squadre della nuova partita appartengono al torneo
     * @post la partita vecchia viene sostituita con la nuova
     * @post la classifica viene aggiornata
     */
    public void modificaPartita(Partita partitaVecchia, Partita partitaNuova) {
        if (partitaVecchia == null || partitaNuova == null) {
            throw new IllegalArgumentException("Le partite non possono essere null.");
        }

        int index = partite.indexOf(partitaVecchia);
        if (index < 0) {
            throw new IllegalArgumentException("La partita da modificare non appartiene al torneo.");
        }
        if (!squadre.contains(partitaNuova.getSquadraCasa()) || !squadre.contains(partitaNuova.getSquadraOspite())) {
            throw new IllegalArgumentException("Le squadre della nuova partita devono appartenere al torneo.");
        }

        partite.set(index, partitaNuova);
        aggiornaClassifica();
    }

    /**
     * @brief Rimuove una partita dal torneo.
     *
     * @param[in] partita partita da rimuovere
     * @pre partita != null
     * @pre la partita appartiene al torneo
     * @post la partita viene rimossa
     * @post la classifica viene aggiornata
     */
    public void rimuoviPartita(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere null.");
        }
        if (!partite.remove(partita)) {
            throw new IllegalArgumentException("La partita da rimuovere non appartiene al torneo.");
        }

        aggiornaClassifica();
    }

    /**
     * @brief Restituisce l'elenco delle partite registrate.
     *
     * @return copia della lista delle partite del torneo
     */
    public List<Partita> getPartite() {
        return new ArrayList<>(partite);
    }

    /**
     * @brief Aggiorna la classifica corrente del torneo.
     *
     * @post la classifica è coerente con squadre e partite registrate
     */
    public void aggiornaClassifica() {
        classifica.aggiorna(squadre, partite);
    }

    /**
     * @brief Restituisce la classifica corrente del torneo.
     *
     * @return classifica corrente
     */
    public Classifica getClassifica() {
        return classifica;
    }

    /**
     * @brief Verifica se esiste già una squadra con un dato nome.
     *
     * @param[in] nome nome da cercare
     * @param[in] esclusa eventuale squadra da escludere dal controllo
     * @return true se esiste già una squadra con quel nome, false altrimenti
     */
    private boolean esisteNomeSquadra(String nome, Squadra esclusa) {
        for (Squadra s : squadre) {
            if (s != esclusa && s.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }
}