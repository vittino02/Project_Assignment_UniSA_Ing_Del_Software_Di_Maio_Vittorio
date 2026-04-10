package it.unisa.torneo.view;

import it.unisa.torneo.controller.TorneoController;
import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Squadra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Controller del dialog FXML per inserimento e modifica delle squadre.
 *
 * Il controller legge i campi del form, valida i dati inseriti e costruisce
 * gli oggetti `Giocatore` da inviare al controller applicativo.
 */
public class SquadraDialogController {

    @FXML
    private TextField nomeSquadraField;

    @FXML
    private TextField nomeGiocatore1Field;

    @FXML
    private TextField nomeGiocatore2Field;

    @FXML
    private TextField nomeGiocatore3Field;

    @FXML
    private TextField nomeGiocatore4Field;

    @FXML
    private TextField nomeGiocatore5Field;

    @FXML
    private TextField cognomeGiocatore1Field;

    @FXML
    private TextField cognomeGiocatore2Field;

    @FXML
    private TextField cognomeGiocatore3Field;

    @FXML
    private TextField cognomeGiocatore4Field;

    @FXML
    private TextField cognomeGiocatore5Field;

    @FXML
    private DatePicker dataGiocatore1Picker;

    @FXML
    private DatePicker dataGiocatore2Picker;

    @FXML
    private DatePicker dataGiocatore3Picker;

    @FXML
    private DatePicker dataGiocatore4Picker;

    @FXML
    private DatePicker dataGiocatore5Picker;

    private List<TextField> nomeGiocatoreFields;
    private List<TextField> cognomeGiocatoreFields;
    private List<DatePicker> dataGiocatorePickers;
    private Squadra squadraDaModificare;

    /**
     * @brief Inizializza le liste dei controlli del dialog.
     */
    @FXML
    public void initialize() {
        nomeGiocatoreFields = List.of(
                nomeGiocatore1Field,
                nomeGiocatore2Field,
                nomeGiocatore3Field,
                nomeGiocatore4Field,
                nomeGiocatore5Field
        );
        cognomeGiocatoreFields = List.of(
                cognomeGiocatore1Field,
                cognomeGiocatore2Field,
                cognomeGiocatore3Field,
                cognomeGiocatore4Field,
                cognomeGiocatore5Field
        );
        dataGiocatorePickers = List.of(
                dataGiocatore1Picker,
                dataGiocatore2Picker,
                dataGiocatore3Picker,
                dataGiocatore4Picker,
                dataGiocatore5Picker
        );
    }

    /**
     * @brief Imposta la squadra da modificare e precompila il dialog.
     *
     * @param[in] squadra squadra da modificare, oppure null per un nuovo inserimento
     */
    public void setSquadraDaModificare(Squadra squadra) {
        squadraDaModificare = squadra;

        if (squadra == null) {
            return;
        }

        nomeSquadraField.setText(squadra.getNome());
        List<Giocatore> giocatori = squadra.getGiocatori();
        for (int i = 0; i < giocatori.size() && i < 5; i++) {
            Giocatore giocatore = giocatori.get(i);
            nomeGiocatoreFields.get(i).setText(giocatore.getNome());
            cognomeGiocatoreFields.get(i).setText(giocatore.getCognome());
            dataGiocatorePickers.get(i).setValue(giocatore.getDataNascita());
        }
    }

    /**
     * @brief Collega la validazione del form al pulsante di conferma del dialog.
     *
     * @param[in] dialog dialog da configurare
     */
    public void configureValidation(Dialog<ButtonType> dialog) {
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                leggiNomeSquadra();
                leggiGiocatori();
            } catch (IllegalArgumentException e) {
                mostraErrore("Dati squadra non validi", e.getMessage());
                event.consume();
            }
        });
    }

    /**
     * @brief Applica i dati del form al controller applicativo.
     *
     * @param[in] controller controller applicativo
     */
    public void apply(TorneoController controller) {
        String nomeSquadra = leggiNomeSquadra();
        List<Giocatore> giocatori = leggiGiocatori();

        if (squadraDaModificare == null) {
            controller.inserisciSquadra(nomeSquadra, giocatori);
        } else {
            controller.modificaSquadra(squadraDaModificare, nomeSquadra, giocatori);
        }
    }

    /**
     * @brief Legge e valida il nome della squadra.
     *
     * @return nome della squadra
     */
    private String leggiNomeSquadra() {
        String nomeSquadra = nomeSquadraField.getText();
        if (nomeSquadra == null || nomeSquadra.isBlank()) {
            throw new IllegalArgumentException("Il nome della squadra e obbligatorio.");
        }
        return nomeSquadra.trim();
    }

    /**
     * @brief Legge e valida i cinque giocatori del form.
     *
     * @return lista dei giocatori inseriti
     */
    private List<Giocatore> leggiGiocatori() {
        List<Giocatore> giocatori = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String nome = leggiCampo(nomeGiocatoreFields.get(i), "Il nome del giocatore " + (i + 1) + " e obbligatorio.");
            String cognome = leggiCampo(cognomeGiocatoreFields.get(i), "Il cognome del giocatore " + (i + 1) + " e obbligatorio.");
            LocalDate dataNascita = dataGiocatorePickers.get(i).getValue();

            if (dataNascita == null) {
                throw new IllegalArgumentException("La data di nascita del giocatore " + (i + 1) + " e obbligatoria.");
            }

            giocatori.add(new Giocatore(nome, cognome, dataNascita));
        }

        return giocatori;
    }

    /**
     * @brief Legge il contenuto di un campo di testo.
     *
     * @param[in] field campo da leggere
     * @param[in] messaggio messaggio di errore in caso di valore mancante
     * @return testo normalizzato del campo
     */
    private String leggiCampo(TextField field, String messaggio) {
        String valore = field.getText();
        if (valore == null || valore.isBlank()) {
            throw new IllegalArgumentException(messaggio);
        }
        return valore.trim();
    }

    /**
     * @brief Mostra un alert di errore.
     *
     * @param[in] titolo titolo del dialog
     * @param[in] messaggio testo dell'errore
     */
    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        if (nomeSquadraField.getScene() != null) {
            alert.initOwner(nomeSquadraField.getScene().getWindow());
        }
        alert.showAndWait();
    }
}
