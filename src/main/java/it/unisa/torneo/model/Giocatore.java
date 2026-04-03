/**
 * @file Giocatore.java
 * @brief Rappresenta un giocatore del torneo.
 *
 * Un giocatore è descritto da nome, cognome e data di nascita.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

import java.time.LocalDate;

/**
 * @brief Rappresenta un giocatore con tutte le sue informazioni
 */
public class Giocatore {

    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    /**
     * @brief Costruisce un nuovo giocatore.
     *
     * @param[in] nome nome del giocatore
     * @param[in] cognome cognome del giocatore
     * @param[in] dataNascita data di nascita del giocatore
     * @pre nome != null
     * @pre cognome != null
     * @pre dataNascita != null
     * @post il giocatore viene inizializzato con i dati forniti
     */
    public Giocatore(String nome, String cognome, LocalDate dataNascita) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome del giocatore non è valido.");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("Il cognome del giocatore non è valido.");
        }
        if (dataNascita == null) {
            throw new IllegalArgumentException("La data di nascita non può essere null.");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    /**
     * @brief Aggiorna i dati del giocatore.
     *
     * @param[in] nome nuovo nome del giocatore
     * @param[in] cognome nuovo cognome del giocatore
     * @param[in] dataNascita nuova data di nascita del giocatore
     * @pre nome != null
     * @pre cognome != null
     * @pre dataNascita != null
     * @post i dati del giocatore vengono aggiornati
     */
    public void aggiornaDati(String nome, String cognome, LocalDate dataNascita) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome del giocatore non è valido.");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("Il cognome del giocatore non è valido.");
        }
        if (dataNascita == null) {
            throw new IllegalArgumentException("La data di nascita non può essere null.");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    /**
     * @brief Restituisce il nome del giocatore.
     *
     * @return nome del giocatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Restituisce il cognome del giocatore.
     *
     * @return cognome del giocatore
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Restituisce la data di nascita del giocatore.
     *
     * @return data di nascita del giocatore
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }
}