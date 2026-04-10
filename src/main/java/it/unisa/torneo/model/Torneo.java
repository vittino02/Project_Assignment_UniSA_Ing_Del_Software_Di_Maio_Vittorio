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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @brief Aggregato principale del dominio del torneo.
 *
 * La classe mantiene squadre, partite e classifica corrente, applica i vincoli
 * di coerenza principali e ricalcola la classifica dopo ogni modifica.
 */
public class Torneo {

    private final List<Squadra> squadre;
    private final List<Partita> partite;
    private final Classifica classifica;

    /**
     * @brief Costruisce un torneo vuoto.
     *
     * @post il torneo non contiene squadre ne partite
     * @post la classifica e inizializzata
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
     * @pre la squadra e completa
     * @pre non esiste gia una squadra con lo stesso nome
     * @post la squadra viene aggiunta al torneo
     */
    public void aggiungiSquadra(Squadra squadra) {
        if (squadra == null) {
            throw new IllegalArgumentException("La squadra non puo essere null.");
        }
        if (!squadra.isCompleta()) {
            throw new IllegalArgumentException("La squadra deve contenere esattamente 5 giocatori.");
        }
        if (esisteNomeSquadra(squadra.getNome(), null)) {
            throw new IllegalArgumentException("Esiste gia una squadra con lo stesso nome.");
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
     * @pre il nuovo nome non e gia usato da un'altra squadra
     * @post la squadra viene aggiornata con i nuovi dati
     */
    public void modificaSquadra(Squadra squadra, String nuovoNome, List<Giocatore> nuoviGiocatori) {
        if (squadra == null) {
            throw new IllegalArgumentException("La squadra da modificare non puo essere null.");
        }
        if (!squadre.contains(squadra)) {
            throw new IllegalArgumentException("La squadra non appartiene al torneo.");
        }
        if (nuovoNome == null || nuovoNome.isBlank()) {
            throw new IllegalArgumentException("Il nome della squadra non e valido.");
        }
        if (nuoviGiocatori == null) {
            throw new IllegalArgumentException("La lista dei giocatori non puo essere null.");
        }
        if (esisteNomeSquadra(nuovoNome, squadra)) {
            throw new IllegalArgumentException("Esiste gia un'altra squadra con lo stesso nome.");
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
            throw new IllegalArgumentException("La partita non puo essere null.");
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
            throw new IllegalArgumentException("La partita non puo essere null.");
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
     * @post la classifica e coerente con squadre e partite registrate
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
     * @brief Sostituisce l'intero contenuto del torneo con dati gia validati.
     *
     * @param[in] nuoveSquadre nuove squadre del torneo
     * @param[in] nuovePartite nuove partite del torneo
     * @pre nuoveSquadre != null
     * @pre nuovePartite != null
     * @post il torneo contiene soltanto i dati passati come parametro
     * @post la classifica viene ricalcolata
     */
    public void sostituisciDati(List<Squadra> nuoveSquadre, List<Partita> nuovePartite) {
        if (nuoveSquadre == null || nuovePartite == null) {
            throw new IllegalArgumentException("Squadre e partite non possono essere null.");
        }

        validaSquadre(nuoveSquadre);
        validaPartite(nuoveSquadre, nuovePartite);

        squadre.clear();
        squadre.addAll(nuoveSquadre);

        partite.clear();
        partite.addAll(nuovePartite);

        aggiornaClassifica();
    }

    /**
     * @brief Verifica se esiste gia una squadra con un dato nome.
     *
     * @param[in] nome nome da cercare
     * @param[in] esclusa eventuale squadra da escludere dal controllo
     * @return true se esiste gia una squadra con quel nome, false altrimenti
     */
    private boolean esisteNomeSquadra(String nome, Squadra esclusa) {
        for (Squadra s : squadre) {
            if (s != esclusa && s.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @brief Verifica che la lista delle squadre sia coerente.
     *
     * @param[in] nuoveSquadre elenco delle squadre da controllare
     */
    private void validaSquadre(List<Squadra> nuoveSquadre) {
        Set<String> nomiSquadre = new HashSet<>();

        for (Squadra squadra : nuoveSquadre) {
            if (squadra == null) {
                throw new IllegalArgumentException("La lista delle squadre contiene elementi null.");
            }
            if (!squadra.isCompleta()) {
                throw new IllegalArgumentException("Ogni squadra deve contenere esattamente 5 giocatori.");
            }

            String nomeNormalizzato = squadra.getNome().trim().toLowerCase(Locale.ROOT);
            if (!nomiSquadre.add(nomeNormalizzato)) {
                throw new IllegalArgumentException("Le squadre devono avere nomi univoci.");
            }
        }
    }

    /**
     * @brief Verifica che tutte le partite facciano riferimento alle squadre del torneo.
     *
     * @param[in] nuoveSquadre elenco delle squadre valide
     * @param[in] nuovePartite elenco delle partite da controllare
     */
    private void validaPartite(List<Squadra> nuoveSquadre, List<Partita> nuovePartite) {
        for (Partita partita : nuovePartite) {
            if (partita == null) {
                throw new IllegalArgumentException("La lista delle partite contiene elementi null.");
            }
            if (!nuoveSquadre.contains(partita.getSquadraCasa()) || !nuoveSquadre.contains(partita.getSquadraOspite())) {
                throw new IllegalArgumentException("Ogni partita deve usare squadre presenti nel torneo.");
            }
        }
    }
}
