/**
 * @file TorneoController.java
 * @brief Controller applicativo del sistema di gestione del torneo.
 *
 * Questa classe coordina le richieste provenienti dall'interfaccia utente
 * e delega al modello le operazioni sul torneo.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.controller;

import it.unisa.torneo.model.Classifica;
import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.Torneo;

import java.time.LocalDate;
import java.util.List;

/**
 * @brief Rappresenta òa classe controller che fa tra intermediara tra interfaccia e torneo.
 */

public class TorneoController {

    private final Torneo torneo;

    /**
     * @brief Costruisce un controller associato a un torneo.
     *
     * @param[in] torneo torneo gestito dal controller
     * @pre torneo != null
     * @post il controller è associato al torneo passato come parametro
     */
    public TorneoController(Torneo torneo) {
        if (torneo == null) {
            throw new IllegalArgumentException("Il torneo non può essere null.");
        }
        this.torneo = torneo;
    }

    /**
     * @brief Inserisce una nuova squadra nel torneo.
     *
     * @param[in] nome nome della squadra
     * @param[in] giocatori lista dei giocatori della squadra
     * @pre nome != null
     * @pre giocatori != null
     * @post se i dati sono validi, una nuova squadra viene aggiunta al torneo
     */
    public void inserisciSquadra(String nome, List<Giocatore> giocatori) {
        Squadra squadra = new Squadra(nome);
        squadra.aggiornaDati(nome, giocatori);
        torneo.aggiungiSquadra(squadra);
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
     * @post la squadra viene aggiornata con i nuovi dati
     */
    public void modificaSquadra(Squadra squadra, String nuovoNome, List<Giocatore> nuoviGiocatori) {
        torneo.modificaSquadra(squadra, nuovoNome, nuoviGiocatori);
    }

    /**
     * @brief Restituisce l'elenco delle squadre del torneo.
     *
     * @return lista delle squadre registrate
     */
    public List<Squadra> getSquadre() {
        return torneo.getSquadre();
    }

    /**
     * @brief Inserisce una nuova partita nel torneo.
     *
     * @param[in] data data della partita
     * @param[in] squadraCasa squadra di casa
     * @param[in] squadraOspite squadra ospite
     * @param[in] goalCasa goal della squadra di casa
     * @param[in] goalOspite goal della squadra ospite
     * @pre data != null
     * @pre squadraCasa != null
     * @pre squadraOspite != null
     * @pre squadraCasa != squadraOspite
     * @pre goalCasa >= 0
     * @pre goalOspite >= 0
     * @post la partita viene aggiunta al torneo
     */
    public void inserisciPartita(LocalDate data, Squadra squadraCasa, Squadra squadraOspite,
                                 int goalCasa, int goalOspite) {
        Partita partita = new Partita(data, squadraCasa, squadraOspite, goalCasa, goalOspite);
        torneo.aggiungiPartita(partita);
    }

    /**
     * @brief Modifica una partita esistente.
     *
     * @param[in] partitaVecchia partita da modificare
     * @param[in] partitaNuova nuova partita
     * @pre partitaVecchia != null
     * @pre partitaNuova != null
     * @post la partita viene aggiornata nel torneo
     */
    public void modificaPartita(Partita partitaVecchia, Partita partitaNuova) {
        torneo.modificaPartita(partitaVecchia, partitaNuova);
    }

    /**
     * @brief Rimuove una partita dal torneo.
     *
     * @param[in] partita partita da rimuovere
     * @pre partita != null
     * @post la partita viene rimossa dal torneo
     */
    public void rimuoviPartita(Partita partita) {
        torneo.rimuoviPartita(partita);
    }

    /**
     * @brief Restituisce l'elenco delle partite registrate.
     *
     * @return lista delle partite del torneo
     */
    public List<Partita> getPartite() {
        return torneo.getPartite();
    }

    /**
     * @brief Restituisce la classifica corrente del torneo.
     *
     * @return classifica corrente
     */
    public Classifica getClassifica() {
        return torneo.getClassifica();
    }
}