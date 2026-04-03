/**
 * @file Squadra.java
 * @brief Rappresenta una squadra del torneo.
 *
 * Una squadra è identificata dal nome ed è composta da cinque giocatori.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Rappresenta la squadra con la sua rosa di giocatori 
 */

public class Squadra {

    private String nome;
    private final List<Giocatore> giocatori;

    /**
     * @brief Costruisce una nuova squadra con il nome specificato.
     *
     * @param[in] nome nome iniziale della squadra
     * @pre nome != null
     * @pre nome non vuoto
     * @post la squadra viene creata con il nome assegnato e senza giocatori
     */
    public Squadra(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della squadra non è valido.");
        }

        this.nome = nome;
        this.giocatori = new ArrayList<>();
    }

    /**
     * @brief Aggiorna i dati della squadra.
     *
     * @param[in] nome nuovo nome della squadra
     * @param[in] giocatori nuova lista dei giocatori
     * @pre nome != null
     * @pre nome non vuoto
     * @pre giocatori != null
     * @pre giocatori.size() == 5
     * @post il nome della squadra viene aggiornato
     * @post la rosa della squadra viene sostituita con la nuova lista
     */
    public void aggiornaDati(String nome, List<Giocatore> giocatori) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della squadra non è valido.");
        }
        if (giocatori == null) {
            throw new IllegalArgumentException("La lista dei giocatori non può essere null.");
        }
        if (giocatori.size() != 5) {
            throw new IllegalArgumentException("Una squadra deve contenere esattamente 5 giocatori.");
        }

        this.nome = nome;
        this.giocatori.clear();
        this.giocatori.addAll(giocatori);
    }

    /**
     * @brief Aggiunge un giocatore alla squadra.
     *
     * @param[in] giocatore giocatore da aggiungere
     * @pre giocatore != null
     * @pre la squadra contiene meno di 5 giocatori
     * @post il giocatore viene aggiunto alla rosa
     */
    public void aggiungiGiocatore(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        if (giocatori.size() >= 5) {
            throw new IllegalArgumentException("La squadra contiene già 5 giocatori.");
        }

        giocatori.add(giocatore);
    }

    /**
     * @brief Rimuove un giocatore dalla squadra.
     *
     * @param[in] giocatore giocatore da rimuovere
     * @pre giocatore != null
     * @pre il giocatore appartiene alla squadra
     * @post il giocatore viene rimosso dalla rosa
     */
    public void rimuoviGiocatore(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        if (!giocatori.remove(giocatore)) {
            throw new IllegalArgumentException("Il giocatore non appartiene alla squadra.");
        }
    }

    /**
     * @brief Sostituisce un giocatore con un altro.
     *
     * @param[in] vecchio giocatore da sostituire
     * @param[in] nuovo nuovo giocatore
     * @pre vecchio != null
     * @pre nuovo != null
     * @pre vecchio appartiene alla squadra
     * @post il giocatore vecchio viene sostituito da quello nuovo
     */
    public void sostituisciGiocatore(Giocatore vecchio, Giocatore nuovo) {
        if (vecchio == null || nuovo == null) {
            throw new IllegalArgumentException("I giocatori non possono essere null.");
        }

        int index = giocatori.indexOf(vecchio);
        if (index < 0) {
            throw new IllegalArgumentException("Il giocatore da sostituire non appartiene alla squadra.");
        }

        giocatori.set(index, nuovo);
    }

    /**
     * @brief Restituisce il nome della squadra.
     *
     * @return nome della squadra
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Restituisce la rosa della squadra.
     *
     * @return copia della lista dei giocatori della squadra
     */
    public List<Giocatore> getGiocatori() {
        return new ArrayList<>(giocatori);
    }

    /**
     * @brief Verifica se la squadra è completa.
     *
     * @return true se la squadra contiene esattamente 5 giocatori, false altrimenti
     */
    public boolean isCompleta() {
        return giocatori.size() == 5;
    }
}