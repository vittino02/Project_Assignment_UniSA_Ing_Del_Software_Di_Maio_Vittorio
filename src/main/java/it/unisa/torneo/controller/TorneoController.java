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
import it.unisa.torneo.persistence.ArchivioTorneoService;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

/**
 * @brief Facciata applicativa tra controller JavaFX e modello del torneo.
 *
 * La classe espone operazioni ad alto livello per gestione di squadre,
 * partite, classifica e archivio, delegando la logica di dominio a `Torneo`
 * e la persistenza a `ArchivioTorneoService`.
 */
public class TorneoController {

    private final Torneo torneo;
    private final ArchivioTorneoService archivioTorneoService;

    /**
     * @brief Costruisce un controller associato a un torneo.
     *
     * @param[in] torneo torneo gestito dal controller
     * @pre torneo != null
     * @post il controller e associato al torneo passato come parametro
     */
    public TorneoController(Torneo torneo) {
        this(torneo, new ArchivioTorneoService());
    }

    /**
     * @brief Costruisce un controller associato a un torneo e alla persistenza.
     *
     * @param[in] torneo torneo gestito dal controller
     * @param[in] archivioTorneoService service usata per salvataggio e caricamento
     */
    public TorneoController(Torneo torneo, ArchivioTorneoService archivioTorneoService) {
        if (torneo == null) {
            throw new IllegalArgumentException("Il torneo non puo essere null.");
        }
        if (archivioTorneoService == null) {
            throw new IllegalArgumentException("La service di persistenza non puo essere null.");
        }
        this.torneo = torneo;
        this.archivioTorneoService = archivioTorneoService;
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

    /**
     * @brief Salva l'archivio corrente del torneo su file.
     *
     * @param[in] path percorso di destinazione del file
     * @throws IllegalArgumentException se il percorso non e valido
     * @throws IOException se il file non puo essere scritto
     */
    public void salvaArchivio(Path path) throws IOException {
        archivioTorneoService.salva(path, torneo);
    }

    /**
     * @brief Carica un archivio da file e sostituisce i dati correnti del torneo.
     *
     * @param[in] path percorso del file da leggere
     * @throws IllegalArgumentException se il percorso non e valido
     * @throws IOException se il file non puo essere letto o non e valido
     */
    public void caricaArchivio(Path path) throws IOException {
        Torneo torneoCaricato = archivioTorneoService.carica(path);
        torneo.sostituisciDati(torneoCaricato.getSquadre(), torneoCaricato.getPartite());
    }
}
