package it.unisa.torneo;

import it.unisa.torneo.controller.TorneoController;
import it.unisa.torneo.model.Torneo;
import it.unisa.torneo.view.InterfacciaUtente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @brief Bootstrap JavaFX dell'applicazione.
 *
 * La classe avvia la piattaforma JavaFX, carica la view principale da FXML,
 * applica il foglio di stile condiviso e collega la GUI al controller
 * applicativo iniziale.
 */
public class Main extends Application {

    /**
     * @brief Avvia la finestra principale dell'applicazione.
     *
     * @param[in] stage stage principale JavaFX
     * @throws IOException se il file FXML principale non puo essere caricato
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(
                        Main.class.getResource("/it/unisa/torneo/view/main-view.fxml"),
                        "File FXML principale non trovato."
                )
        );

        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        Main.class.getResource("/it/unisa/torneo/view/app.css"),
                        "Foglio di stile principale non trovato."
                ).toExternalForm()
        );
        InterfacciaUtente interfacciaUtente = loader.getController();
        interfacciaUtente.setController(new TorneoController(new Torneo()));

        stage.setTitle("Gestione torneo calcio a 5");
        stage.setMinWidth(960);
        stage.setMinHeight(680);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @brief Entry point dell'applicazione JavaFX.
     *
     * @param[in] args argomenti da linea di comando
     */
    public static void main(String[] args) {
        launch(args);
    }
}
