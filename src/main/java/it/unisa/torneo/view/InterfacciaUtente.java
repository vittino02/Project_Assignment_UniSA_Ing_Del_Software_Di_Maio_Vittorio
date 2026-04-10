package it.unisa.torneo.view;

import it.unisa.torneo.controller.TorneoController;
import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.VoceClassifica;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @brief Controller JavaFX della schermata principale.
 *
 * La classe coordina la vista principale, aggiorna le tabelle mostrate
 * all'utente e delega a `TorneoController` le operazioni applicative.
 */
public class InterfacciaUtente {

    private static final String SQUADRA_DIALOG_FXML = "/it/unisa/torneo/view/squadra-dialog.fxml";
    private static final String PARTITA_DIALOG_FXML = "/it/unisa/torneo/view/partita-dialog.fxml";

    @FXML
    private TableView<Squadra> squadreTable;

    @FXML
    private TableColumn<Squadra, String> squadraNomeColumn;

    @FXML
    private TableColumn<Squadra, Integer> squadraNumeroGiocatoriColumn;

    @FXML
    private TableView<Giocatore> giocatoriTable;

    @FXML
    private TableColumn<Giocatore, String> giocatoreNomeColumn;

    @FXML
    private TableColumn<Giocatore, String> giocatoreCognomeColumn;

    @FXML
    private TableColumn<Giocatore, String> giocatoreDataColumn;

    @FXML
    private TableView<Partita> partiteTable;

    @FXML
    private TableColumn<Partita, String> partitaDataColumn;

    @FXML
    private TableColumn<Partita, String> partitaCasaColumn;

    @FXML
    private TableColumn<Partita, Integer> partitaGoalCasaColumn;

    @FXML
    private TableColumn<Partita, Integer> partitaGoalOspiteColumn;

    @FXML
    private TableColumn<Partita, String> partitaOspiteColumn;

    @FXML
    private TableView<VoceClassifica> classificaTable;

    @FXML
    private TableColumn<VoceClassifica, Integer> classificaPosizioneColumn;

    @FXML
    private TableColumn<VoceClassifica, String> classificaSquadraColumn;

    @FXML
    private TableColumn<VoceClassifica, Integer> classificaPuntiColumn;

    @FXML
    private Label statoLabel;

    @FXML
    private Label totaleSquadreLabel;

    @FXML
    private Label totalePartiteLabel;

    @FXML
    private Label leaderLabel;

    private final ObservableList<Squadra> squadreData = FXCollections.observableArrayList();
    private final ObservableList<Giocatore> giocatoriData = FXCollections.observableArrayList();
    private final ObservableList<Partita> partiteData = FXCollections.observableArrayList();
    private final ObservableList<VoceClassifica> classificaData = FXCollections.observableArrayList();

    private TorneoController controller;
    private Path archivioCorrente;

    /**
     * @brief Inizializza le tabelle e i binding della schermata principale.
     */
    @FXML
    public void initialize() {
        configuraTabelle();
        collegaSelezioni();
        configuraPlaceholder();
        aggiornaStato("Applicazione pronta.");
    }

    /**
     * @brief Collega la schermata al controller applicativo.
     *
     * @param[in] controller controller dell'applicazione
     */
    public void setController(TorneoController controller) {
        this.controller = Objects.requireNonNull(controller, "Il controller non puo essere null.");
        aggiornaDati();
    }

    @FXML
    private void handleNuovaSquadra() {
        mostraDialogSquadra(null);
    }

    @FXML
    private void handleModificaSquadra() {
        Squadra squadraSelezionata = richiediSelezione(
                squadreTable,
                "Seleziona una squadra da modificare."
        );
        if (squadraSelezionata != null) {
            mostraDialogSquadra(squadraSelezionata);
        }
    }

    @FXML
    private void handleNuovaPartita() {
        if (richiediController().getSquadre().size() < 2) {
            mostraErrore("Dati insufficienti", "Servono almeno due squadre registrate per inserire una partita.");
            return;
        }

        mostraDialogPartita(null);
    }

    @FXML
    private void handleModificaPartita() {
        Partita partitaSelezionata = richiediSelezione(
                partiteTable,
                "Seleziona una partita da modificare."
        );
        if (partitaSelezionata != null) {
            mostraDialogPartita(partitaSelezionata);
        }
    }

    @FXML
    private void handleRimuoviPartita() {
        Partita partitaSelezionata = richiediSelezione(
                partiteTable,
                "Seleziona una partita da rimuovere."
        );
        if (partitaSelezionata == null) {
            return;
        }

        if (richiediConferma("Conferma rimozione", "Vuoi davvero rimuovere la partita selezionata?")) {
            eseguiOperazione(
                    () -> richiediController().rimuoviPartita(partitaSelezionata),
                    "Partita rimossa correttamente.",
                    true
            );
        }
    }

    @FXML
    private void handleSalvaArchivio() {
        Path path = scegliArchivio("Salva archivio", true);
        if (path == null) {
            return;
        }

        eseguiOperazione(() -> {
            richiediController().salvaArchivio(path);
            archivioCorrente = path;
        }, "Archivio salvato in " + path.getFileName() + ".", false);
    }

    @FXML
    private void handleCaricaArchivio() {
        Path path = scegliArchivio("Carica archivio", false);
        if (path == null) {
            return;
        }

        eseguiOperazione(() -> {
            richiediController().caricaArchivio(path);
            archivioCorrente = path;
        }, "Archivio caricato da " + path.getFileName() + ".", true);
    }

    @FXML
    private void handleEsci() {
        Window window = getWindow();
        if (window instanceof Stage stage) {
            stage.close();
        }
    }

    /**
     * @brief Configura le colonne delle tabelle principali.
     */
    private void configuraTabelle() {
        squadraNomeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNome()));
        squadraNumeroGiocatoriColumn.setCellValueFactory(
                data -> new ReadOnlyObjectWrapper<>(data.getValue().getGiocatori().size())
        );
        squadreTable.setItems(squadreData);

        giocatoreNomeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNome()));
        giocatoreCognomeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCognome()));
        giocatoreDataColumn.setCellValueFactory(
                data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getDataNascita()))
        );
        giocatoriTable.setItems(giocatoriData);

        partitaDataColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getData())));
        partitaCasaColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSquadraCasa().getNome()));
        partitaGoalCasaColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getGoalCasa()));
        partitaGoalOspiteColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getGoalOspite()));
        partitaOspiteColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSquadraOspite().getNome()));
        partiteTable.setItems(partiteData);

        classificaPosizioneColumn.setCellValueFactory(
                data -> new ReadOnlyObjectWrapper<>(classificaTable.getItems().indexOf(data.getValue()) + 1)
        );
        classificaSquadraColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSquadra().getNome()));
        classificaPuntiColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPunti()));
        classificaTable.setItems(classificaData);
    }

    /**
     * @brief Collega la selezione delle squadre all'aggiornamento della rosa.
     */
    private void collegaSelezioni() {
        squadreTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> aggiornaGiocatori(newValue)
        );
    }

    /**
     * @brief Aggiorna tutti i dati mostrati nelle tabelle.
     */
    private void aggiornaDati() {
        TorneoController torneoController = richiediController();

        List<Squadra> squadre = torneoController.getSquadre();
        squadreData.setAll(squadre);
        partiteData.setAll(torneoController.getPartite());
        classificaData.setAll(torneoController.getClassifica().getVoci());
        aggiornaStatistiche();

        Squadra selezionata = squadreTable.getSelectionModel().getSelectedItem();
        if (selezionata != null && squadre.contains(selezionata)) {
            aggiornaGiocatori(selezionata);
        } else {
            giocatoriData.clear();
        }
    }

    /**
     * @brief Configura i messaggi placeholder delle tabelle.
     */
    private void configuraPlaceholder() {
        squadreTable.setPlaceholder(new Label("Nessuna squadra registrata."));
        giocatoriTable.setPlaceholder(new Label("Seleziona una squadra per vedere i giocatori."));
        partiteTable.setPlaceholder(new Label("Nessuna partita registrata."));
        classificaTable.setPlaceholder(new Label("La classifica verra aggiornata quando inserisci i dati."));
    }

    /**
     * @brief Aggiorna la tabella dei giocatori sulla base della squadra selezionata.
     *
     * @param[in] squadra squadra selezionata
     */
    private void aggiornaGiocatori(Squadra squadra) {
        if (squadra == null) {
            giocatoriData.clear();
            return;
        }

        giocatoriData.setAll(squadra.getGiocatori());
    }

    /**
     * @brief Aggiorna il riepilogo rapido mostrato in alto.
     */
    private void aggiornaStatistiche() {
        totaleSquadreLabel.setText(String.valueOf(squadreData.size()));
        totalePartiteLabel.setText(String.valueOf(partiteData.size()));

        if (classificaData.isEmpty()) {
            leaderLabel.setText("Nessuna");
            return;
        }

        VoceClassifica leader = classificaData.getFirst();
        leaderLabel.setText(leader.getSquadra().getNome() + " (" + leader.getPunti() + " pt)");
    }

    /**
     * @brief Mostra il dialog per inserimento o modifica di una squadra.
     *
     * @param[in] squadra squadra da modificare, oppure null per un nuovo inserimento
     */
    private void mostraDialogSquadra(Squadra squadra) {
        try {
            FXMLLoader loader = creaLoader(SQUADRA_DIALOG_FXML, "Dialog FXML della squadra non trovato.");

            DialogPane dialogPane = loader.load();
            SquadraDialogController dialogController = loader.getController();
            dialogController.setSquadraDaModificare(squadra);

            Dialog<ButtonType> dialog = creaDialog(
                    squadra == null ? "Inserisci squadra" : "Modifica squadra",
                    squadra == null ? "Nuova squadra" : "Modifica squadra",
                    dialogPane
            );
            dialogController.configureValidation(dialog);

            Optional<ButtonType> risultato = dialog.showAndWait();
            if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
                eseguiOperazione(
                        () -> dialogController.apply(richiediController()),
                        squadra == null ? "Squadra inserita correttamente." : "Squadra modificata correttamente.",
                        true
                );
            }
        } catch (IOException e) {
            mostraErrore("Interfaccia non disponibile", "Impossibile aprire il dialog della squadra.");
        }
    }

    /**
     * @brief Mostra il dialog per inserimento o modifica di una partita.
     *
     * @param[in] partita partita da modificare, oppure null per un nuovo inserimento
     */
    private void mostraDialogPartita(Partita partita) {
        try {
            FXMLLoader loader = creaLoader(PARTITA_DIALOG_FXML, "Dialog FXML della partita non trovato.");

            DialogPane dialogPane = loader.load();
            PartitaDialogController dialogController = loader.getController();
            dialogController.setContesto(richiediController().getSquadre(), partita);

            Dialog<ButtonType> dialog = creaDialog(
                    partita == null ? "Inserisci partita" : "Modifica partita",
                    partita == null ? "Nuova partita" : "Modifica partita",
                    dialogPane
            );
            dialogController.configureValidation(dialog);

            Optional<ButtonType> risultato = dialog.showAndWait();
            if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
                eseguiOperazione(
                        () -> dialogController.apply(richiediController()),
                        partita == null ? "Partita inserita correttamente." : "Partita modificata correttamente.",
                        true
                );
            }
        } catch (IOException e) {
            mostraErrore("Interfaccia non disponibile", "Impossibile aprire il dialog della partita.");
        }
    }

    /**
     * @brief Esegue un'operazione applicativa con gestione centralizzata degli errori.
     *
     * @param[in] operazione operazione da eseguire
     * @param[in] messaggioSuccesso messaggio di stato in caso di successo
     */
    private void eseguiOperazione(Operazione operazione, String messaggioSuccesso, boolean aggiornaVista) {
        try {
            operazione.esegui();
            if (aggiornaVista) {
                aggiornaDati();
            }
            aggiornaStato(messaggioSuccesso);
        } catch (IllegalArgumentException e) {
            mostraErrore("Operazione non completata", e.getMessage());
        } catch (IOException e) {
            mostraErrore("Errore di archivio", e.getMessage());
        }
    }

    /**
     * @brief Aggiorna il testo della barra di stato.
     *
     * @param[in] messaggio messaggio da mostrare
     */
    private void aggiornaStato(String messaggio) {
        statoLabel.setText(messaggio);
    }

    /**
     * @brief Mostra un messaggio informativo.
     *
     * @param[in] titolo titolo del dialog
     * @param[in] messaggio contenuto del dialog
     */
    private void mostraInformazione(String titolo, String messaggio) {
        mostraAlert(Alert.AlertType.INFORMATION, titolo, messaggio);
    }

    /**
     * @brief Mostra un messaggio di errore.
     *
     * @param[in] titolo titolo del dialog
     * @param[in] messaggio contenuto del dialog
     */
    private void mostraErrore(String titolo, String messaggio) {
        mostraAlert(Alert.AlertType.ERROR, titolo, messaggio);
    }

    /**
     * @brief Mostra un alert con owner e contenuto configurati.
     *
     * @param[in] tipo tipo di alert JavaFX
     * @param[in] titolo titolo e header del dialog
     * @param[in] messaggio contenuto del dialog
     */
    private void mostraAlert(Alert.AlertType tipo, String titolo, String messaggio) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titolo);
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        impostaOwner(alert);
        alert.showAndWait();
    }

    /**
     * @brief Imposta la finestra principale come owner del dialog.
     *
     * @param[in] dialog dialog a cui associare la finestra principale
     */
    private void impostaOwner(Dialog<?> dialog) {
        Window window = getWindow();
        if (window != null) {
            dialog.initOwner(window);
        }
    }

    /**
     * @brief Restituisce la finestra corrente.
     *
     * @return finestra principale, oppure null se non ancora disponibile
     */
    private Window getWindow() {
        if (statoLabel == null || statoLabel.getScene() == null) {
            return null;
        }
        return statoLabel.getScene().getWindow();
    }

    /**
     * @brief Crea il file chooser per salvataggio e caricamento.
     *
     * @param[in] titolo titolo del file chooser
     * @return file chooser configurato
     */
    private FileChooser creaFileChooser(String titolo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titolo);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivio torneo (*.torneo)", "*.torneo"));

        if (archivioCorrente != null) {
            Path absolutePath = archivioCorrente.toAbsolutePath();
            Path parent = absolutePath.getParent();
            if (parent != null && Files.isDirectory(parent)) {
                fileChooser.setInitialDirectory(parent.toFile());
            }
            fileChooser.setInitialFileName(absolutePath.getFileName().toString());
        } else {
            fileChooser.setInitialFileName("torneo.torneo");
        }

        return fileChooser;
    }

    /**
     * @brief Crea un FXMLLoader verificando la presenza della risorsa.
     *
     * @param[in] percorsoFxml percorso assoluto della risorsa FXML
     * @param[in] messaggioErrore messaggio usato se la risorsa non e presente
     * @return loader configurato
     */
    private FXMLLoader creaLoader(String percorsoFxml, String messaggioErrore) {
        return new FXMLLoader(
                Objects.requireNonNull(
                        getClass().getResource(percorsoFxml),
                        messaggioErrore
                )
        );
    }

    /**
     * @brief Crea un dialog standard con owner gia configurato.
     *
     * @param[in] titolo titolo della finestra
     * @param[in] header header del dialog
     * @param[in] dialogPane contenuto del dialog
     * @return dialog JavaFX pronto all'uso
     */
    private Dialog<ButtonType> creaDialog(String titolo, String header, DialogPane dialogPane) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(titolo);
        dialog.setHeaderText(header);
        dialog.setDialogPane(dialogPane);
        impostaOwner(dialog);
        return dialog;
    }

    /**
     * @brief Richiede la selezione di un elemento da una tabella.
     *
     * @param[in] table tabella da cui leggere la selezione
     * @param[in] messaggio messaggio mostrato se non e selezionato nulla
     * @return elemento selezionato, oppure null
     * @param <T> tipo della tabella
     */
    private <T> T richiediSelezione(TableView<T> table, String messaggio) {
        T elementoSelezionato = table.getSelectionModel().getSelectedItem();
        if (elementoSelezionato == null) {
            mostraInformazione("Selezione richiesta", messaggio);
        }
        return elementoSelezionato;
    }

    /**
     * @brief Mostra una conferma standard.
     *
     * @param[in] titolo titolo del dialog
     * @param[in] messaggio contenuto del dialog
     * @return true se l'utente conferma
     */
    private boolean richiediConferma(String titolo, String messaggio) {
        Alert conferma = new Alert(
                Alert.AlertType.CONFIRMATION,
                messaggio,
                new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE),
                ButtonType.CANCEL
        );
        conferma.setTitle(titolo);
        conferma.setHeaderText(titolo);
        impostaOwner(conferma);

        Optional<ButtonType> risultato = conferma.showAndWait();
        return risultato.isPresent() && risultato.get().getButtonData() == ButtonBar.ButtonData.OK_DONE;
    }

    /**
     * @brief Richiede un percorso di archivio per salvataggio o caricamento.
     *
     * @param[in] titolo titolo del file chooser
     * @param[in] salvataggio true per il salvataggio, false per il caricamento
     * @return percorso scelto, oppure null se annullato
     */
    private Path scegliArchivio(String titolo, boolean salvataggio) {
        FileChooser fileChooser = creaFileChooser(titolo);
        Window window = getWindow();
        java.io.File file = salvataggio
                ? fileChooser.showSaveDialog(window)
                : fileChooser.showOpenDialog(window);
        return file == null ? null : file.toPath();
    }

    /**
     * @brief Restituisce il controller configurato, se disponibile.
     *
     * @return controller applicativo
     */
    private TorneoController richiediController() {
        if (controller == null) {
            throw new IllegalStateException("Il controller non e stato configurato.");
        }
        return controller;
    }

    /**
     * @brief Interfaccia funzionale per operazioni che possono fallire.
     */
    @FunctionalInterface
    private interface Operazione {
        /**
         * @brief Esegue l'operazione applicativa incapsulata.
         *
         * @throws IOException se l'operazione fallisce per motivi di I/O
         */
        void esegui() throws IOException;
    }
}
