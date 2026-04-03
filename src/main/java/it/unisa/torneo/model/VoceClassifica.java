/**
 * @file VoceClassifica.java
 * @brief Rappresenta una riga della classifica.
 *
 * Ogni voce associa una squadra al suo punteggio corrente.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

/**
 * @brief Rappresenta le varie voci della classifica
 */

public class VoceClassifica {

    private Squadra squadra;
    private int punti;

    /**
     * @brief Costruisce una nuova voce di classifica.
     *
     * @param[in] squadra squadra associata alla voce
     * @param[in] punti punteggio iniziale
     * @pre squadra != null
     * @pre punti >= 0
     * @post la voce viene inizializzata con squadra e punteggio forniti
     */
    public VoceClassifica(Squadra squadra, int punti) {
        if (squadra == null) {
            throw new IllegalArgumentException("La squadra non può essere null.");
        }
        if (punti < 0) {
            throw new IllegalArgumentException("I punti non possono essere negativi.");
        }

        this.squadra = squadra;
        this.punti = punti;
    }

    /**
     * @brief Incrementa il punteggio della squadra.
     *
     * @param[in] valore valore da aggiungere ai punti correnti
     * @pre valore >= 0
     * @post il punteggio viene aumentato del valore specificato
     */
    public void incrementaPunti(int valore) {
        if (valore < 0) {
            throw new IllegalArgumentException("Il valore da aggiungere non può essere negativo.");
        }

        this.punti += valore;
    }

    /**
     * @brief Imposta il punteggio della squadra.
     *
     * @param[in] punti nuovo punteggio
     * @pre punti >= 0
     * @post il punteggio viene aggiornato
     */
    public void setPunti(int punti) {
        if (punti < 0) {
            throw new IllegalArgumentException("I punti non possono essere negativi.");
        }

        this.punti = punti;
    }

    /**
     * @brief Restituisce la squadra associata alla voce.
     *
     * @return squadra associata alla voce
     */
    public Squadra getSquadra() {
        return squadra;
    }

    /**
     * @brief Restituisce il punteggio associato alla squadra.
     *
     * @return punteggio corrente
     */
    public int getPunti() {
        return punti;
    }
}