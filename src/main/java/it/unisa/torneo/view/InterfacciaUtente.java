/**
 * @file InterfacciaUtente.java
 * @brief Gestisce l'interazione tra utente e applicazione.
 *
 * Questa classe rappresenta lo scheletro della parte di interfaccia utente.
 * Ha la responsabilità di mostrare le schermate principali, raccogliere gli input
 * dell'utente e invocare le operazioni del controller.
 *
 * @author Vittorio Di Maio
 * @version 1.0
 */
package it.unisa.torneo.view;

import it.unisa.torneo.controller.TorneoController;
import it.unisa.torneo.model.Classifica;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;

import java.util.List;

/**
 * @brief Rappresenta l'interfaccia per poter usare l'applicazione
 */

public class InterfacciaUtente {

    private final TorneoController controller;

    /**
     * @brief Costruisce l'interfaccia utente associata a un controller.
     *
     * @param[in] controller controller dell'applicazione
     * @pre controller != null
     * @post l'interfaccia utente è associata al controller passato
     */
    public InterfacciaUtente(TorneoController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Il controller non può essere null.");
        }
        this.controller = controller;
    }

    /**
     * @brief Mostra la schermata principale dell'applicazione.
     *
     * @post l'utente visualizza il menu principale del sistema
     */
    public void mostraSchermataPrincipale() {
        // TODO implementare
    }

    /**
     * @brief Mostra la schermata di gestione delle squadre.
     *
     * @post l'utente visualizza l'elenco delle squadre e le operazioni disponibili
     */
    public void mostraSchermataSquadre() {
        // TODO implementare
    }

    /**
     * @brief Mostra la schermata di gestione delle partite.
     *
     * @post l'utente visualizza l'elenco delle partite e le operazioni disponibili
     */
    public void mostraSchermataPartite() {
        // TODO implementare
    }

    /**
     * @brief Mostra la schermata della classifica.
     *
     * @post l'utente visualizza la classifica corrente del torneo
     */
    public void mostraSchermataClassifica() {
        // TODO implementare
    }

    /**
     * @brief Mostra l'elenco delle squadre.
     *
     * @param[in] squadre elenco delle squadre da visualizzare
     * @pre squadre != null
     */
    public void mostraSquadre(List<Squadra> squadre) {
        // TODO implementare
    }

    /**
     * @brief Mostra l'elenco delle partite.
     *
     * @param[in] partite elenco delle partite da visualizzare
     * @pre partite != null
     */
    public void mostraPartite(List<Partita> partite) {
        // TODO implementare
    }

    /**
     * @brief Mostra la classifica corrente.
     *
     * @param[in] classifica classifica da visualizzare
     * @pre classifica != null
     */
    public void mostraClassifica(Classifica classifica) {
        // TODO implementare
    }

    /**
     * @brief Mostra un messaggio informativo all'utente.
     *
     * @param[in] messaggio testo del messaggio
     * @pre messaggio != null
     */
    public void mostraMessaggio(String messaggio) {
        // TODO implementare
    }

    /**
     * @brief Mostra un messaggio di errore all'utente.
     *
     * @param[in] errore testo dell'errore
     * @pre errore != null
     */
    public void mostraErrore(String errore) {
        // TODO implementare
    }
}