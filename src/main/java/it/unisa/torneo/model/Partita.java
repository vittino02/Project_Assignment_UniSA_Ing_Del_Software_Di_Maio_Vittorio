/**
 * @file Partita.java
 * @brief Rappresenta una partita del torneo.
 *
 * Una partita è definita da data, squadra di casa, squadra ospite
 * e numero di goal segnati dalle due squadre.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

import java.time.LocalDate;

/**
 * @brief Rappresenta la partita con goal e squadre in campo
 */

public class Partita {

    private LocalDate data;
    private Squadra squadraCasa;
    private Squadra squadraOspite;
    private int goalCasa;
    private int goalOspite;

    /**
     * @brief Costruisce una nuova partita.
     *
     * @param[in] data data della partita
     * @param[in] squadraCasa squadra di casa
     * @param[in] squadraOspite squadra ospite
     * @param[in] goalCasa goal segnati dalla squadra di casa
     * @param[in] goalOspite goal segnati dalla squadra ospite
     * @pre data != null
     * @pre squadraCasa != null
     * @pre squadraOspite != null
     * @pre squadraCasa != squadraOspite
     * @pre goalCasa >= 0
     * @pre goalOspite >= 0
     * @post la partita viene inizializzata con i dati forniti
     */
    public Partita(LocalDate data, Squadra squadraCasa, Squadra squadraOspite, int goalCasa, int goalOspite) {
        validaDati(data, squadraCasa, squadraOspite, goalCasa, goalOspite);

        this.data = data;
        this.squadraCasa = squadraCasa;
        this.squadraOspite = squadraOspite;
        this.goalCasa = goalCasa;
        this.goalOspite = goalOspite;
    }

    /**
     * @brief Aggiorna i dati della partita.
     *
     * @param[in] data nuova data della partita
     * @param[in] squadraCasa nuova squadra di casa
     * @param[in] squadraOspite nuova squadra ospite
     * @param[in] goalCasa nuovi goal della squadra di casa
     * @param[in] goalOspite nuovi goal della squadra ospite
     * @pre data != null
     * @pre squadraCasa != null
     * @pre squadraOspite != null
     * @pre squadraCasa != squadraOspite
     * @pre goalCasa >= 0
     * @pre goalOspite >= 0
     * @post i dati della partita vengono aggiornati
     */
    public void aggiornaDati(LocalDate data, Squadra squadraCasa, Squadra squadraOspite, int goalCasa, int goalOspite) {
        validaDati(data, squadraCasa, squadraOspite, goalCasa, goalOspite);

        this.data = data;
        this.squadraCasa = squadraCasa;
        this.squadraOspite = squadraOspite;
        this.goalCasa = goalCasa;
        this.goalOspite = goalOspite;
    }

    /**
     * @brief Verifica se la partita è terminata in pareggio.
     *
     * @return true se le due squadre hanno segnato lo stesso numero di goal, false altrimenti
     */
    public boolean isPareggio() {
        return goalCasa == goalOspite;
    }

    /**
     * @brief Restituisce i punti ottenuti dalla squadra di casa.
     *
     * @return 3 in caso di vittoria, 1 in caso di pareggio, 0 in caso di sconfitta
     */
    public int getPuntiCasa() {
        if (goalCasa > goalOspite) {
            return 3;
        }
        if (goalCasa == goalOspite) {
            return 1;
        }
        return 0;
    }

    /**
     * @brief Restituisce i punti ottenuti dalla squadra ospite.
     *
     * @return 3 in caso di vittoria, 1 in caso di pareggio, 0 in caso di sconfitta
     */
    public int getPuntiOspite() {
        if (goalOspite > goalCasa) {
            return 3;
        }
        if (goalCasa == goalOspite) {
            return 1;
        }
        return 0;
    }

    /**
     * @brief Restituisce la data della partita.
     *
     * @return data della partita
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @brief Restituisce la squadra di casa.
     *
     * @return squadra di casa
     */
    public Squadra getSquadraCasa() {
        return squadraCasa;
    }

    /**
     * @brief Restituisce la squadra ospite.
     *
     * @return squadra ospite
     */
    public Squadra getSquadraOspite() {
        return squadraOspite;
    }

    /**
     * @brief Restituisce i goal segnati dalla squadra di casa.
     *
     * @return goal della squadra di casa
     */
    public int getGoalCasa() {
        return goalCasa;
    }

    /**
     * @brief Restituisce i goal segnati dalla squadra ospite.
     *
     * @return goal della squadra ospite
     */
    public int getGoalOspite() {
        return goalOspite;
    }

    /**
     * @brief Verifica la validità dei dati della partita.
     *
     * @param[in] data data della partita
     * @param[in] squadraCasa squadra di casa
     * @param[in] squadraOspite squadra ospite
     * @param[in] goalCasa goal della squadra di casa
     * @param[in] goalOspite goal della squadra ospite
     */
    private void validaDati(LocalDate data, Squadra squadraCasa, Squadra squadraOspite, int goalCasa, int goalOspite) {
        if (data == null) {
            throw new IllegalArgumentException("La data non può essere null.");
        }
        if (squadraCasa == null || squadraOspite == null) {
            throw new IllegalArgumentException("Le squadre non possono essere null.");
        }
        if (squadraCasa == squadraOspite) {
            throw new IllegalArgumentException("Una squadra non può giocare contro sé stessa.");
        }
        if (goalCasa < 0 || goalOspite < 0) {
            throw new IllegalArgumentException("I goal non possono essere negativi.");
        }
    }
}