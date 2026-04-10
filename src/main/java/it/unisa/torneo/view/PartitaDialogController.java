package it.unisa.torneo.view;

import it.unisa.torneo.controller.TorneoController;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

/**
 * @brief Controller del dialog FXML per inserimento e modifica delle partite.
 *
 * Il controller prepara il form della partita, valida la selezione delle
 * squadre e converte i valori inseriti in un oggetto `Partita`.
 */
public class PartitaDialogController {

    @FXML
    private DatePicker dataPartitaPicker;

    @FXML
    private ComboBox<Squadra> squadraCasaCombo;

    @FXML
    private ComboBox<Squadra> squadraOspiteCombo;

    @FXML
    private Spinner<Integer> goalCasaSpinner;

    @FXML
    private Spinner<Integer> goalOspiteSpinner;

    private Partita partitaDaModificare;

    /**
     * @brief Inizializza i controlli del dialog.
     */
    @FXML
    public void initialize() {
        goalCasaSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0));
        goalOspiteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0));

        StringConverter<Squadra> squadraConverter = new StringConverter<>() {
            @Override
            public String toString(Squadra squadra) {
                return squadra == null ? "" : squadra.getNome();
            }

            @Override
            public Squadra fromString(String string) {
                return null;
            }
        };

        squadraCasaCombo.setConverter(squadraConverter);
        squadraOspiteCombo.setConverter(squadraConverter);
    }

    /**
     * @brief Imposta il contesto del dialog.
     *
     * @param[in] squadre squadre disponibili
     * @param[in] partita partita da modificare, oppure null per un nuovo inserimento
     */
    public void setContesto(List<Squadra> squadre, Partita partita) {
        squadraCasaCombo.getItems().setAll(squadre);
        squadraOspiteCombo.getItems().setAll(squadre);
        partitaDaModificare = partita;

        if (partita == null) {
            return;
        }

        dataPartitaPicker.setValue(partita.getData());
        squadraCasaCombo.setValue(partita.getSquadraCasa());
        squadraOspiteCombo.setValue(partita.getSquadraOspite());
        goalCasaSpinner.getValueFactory().setValue(partita.getGoalCasa());
        goalOspiteSpinner.getValueFactory().setValue(partita.getGoalOspite());
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
                leggiData();
                leggiSquadraCasa();
                leggiSquadraOspite();
            } catch (IllegalArgumentException e) {
                mostraErrore("Dati partita non validi", e.getMessage());
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
        LocalDate data = leggiData();
        Squadra squadraCasa = leggiSquadraCasa();
        Squadra squadraOspite = leggiSquadraOspite();
        int goalCasa = goalCasaSpinner.getValue();
        int goalOspite = goalOspiteSpinner.getValue();

        if (partitaDaModificare == null) {
            controller.inserisciPartita(data, squadraCasa, squadraOspite, goalCasa, goalOspite);
        } else {
            controller.modificaPartita(
                    partitaDaModificare,
                    new Partita(data, squadraCasa, squadraOspite, goalCasa, goalOspite)
            );
        }
    }

    /**
     * @brief Legge e valida la data della partita.
     *
     * @return data selezionata
     */
    private LocalDate leggiData() {
        LocalDate data = dataPartitaPicker.getValue();
        if (data == null) {
            throw new IllegalArgumentException("La data della partita e obbligatoria.");
        }
        return data;
    }

    /**
     * @brief Legge e valida la squadra di casa.
     *
     * @return squadra di casa selezionata
     */
    private Squadra leggiSquadraCasa() {
        Squadra squadraCasa = squadraCasaCombo.getValue();
        if (squadraCasa == null) {
            throw new IllegalArgumentException("La squadra di casa e obbligatoria.");
        }
        return squadraCasa;
    }

    /**
     * @brief Legge e valida la squadra ospite.
     *
     * @return squadra ospite selezionata
     */
    private Squadra leggiSquadraOspite() {
        Squadra squadraOspite = squadraOspiteCombo.getValue();
        if (squadraOspite == null) {
            throw new IllegalArgumentException("La squadra ospite e obbligatoria.");
        }
        if (squadraOspite == squadraCasaCombo.getValue()) {
            throw new IllegalArgumentException("Le due squadre selezionate devono essere distinte.");
        }
        return squadraOspite;
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
        if (dataPartitaPicker.getScene() != null) {
            alert.initOwner(dataPartitaPicker.getScene().getWindow());
        }
        alert.showAndWait();
    }
}
