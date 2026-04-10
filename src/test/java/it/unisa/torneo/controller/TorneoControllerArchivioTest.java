package it.unisa.torneo.controller;

import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.Torneo;
import it.unisa.torneo.model.VoceClassifica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TorneoControllerArchivioTest {

    @TempDir
    Path tempDir;

    @Test
    void salvaECaricaArchivioRipristinanoSquadrePartiteEClassifica() throws Exception {
        Path archivio = tempDir.resolve("torneo.torneo");

        TorneoController controller = new TorneoController(new Torneo());
        controller.inserisciSquadra("Falchi", creaGiocatori("F"));
        controller.inserisciSquadra("Leoni", creaGiocatori("L"));

        Squadra falchi = controller.getSquadre().get(0);
        Squadra leoni = controller.getSquadre().get(1);
        controller.inserisciPartita(LocalDate.of(2026, 4, 9), falchi, leoni, 4, 2);
        controller.salvaArchivio(archivio);

        TorneoController controllerCaricato = new TorneoController(new Torneo());
        controllerCaricato.caricaArchivio(archivio);

        assertEquals(2, controllerCaricato.getSquadre().size());
        assertEquals(1, controllerCaricato.getPartite().size());

        List<VoceClassifica> classifica = controllerCaricato.getClassifica().getVoci();
        assertEquals("Falchi", classifica.get(0).getSquadra().getNome());
        assertEquals(3, classifica.get(0).getPunti());
        assertEquals("Leoni", classifica.get(1).getSquadra().getNome());
        assertEquals(0, classifica.get(1).getPunti());
    }

    private List<Giocatore> creaGiocatori(String prefisso) {
        return List.of(
                new Giocatore(prefisso + "1", "Rossi", LocalDate.of(2000, 1, 1)),
                new Giocatore(prefisso + "2", "Bianchi", LocalDate.of(2000, 2, 2)),
                new Giocatore(prefisso + "3", "Verdi", LocalDate.of(2000, 3, 3)),
                new Giocatore(prefisso + "4", "Neri", LocalDate.of(2000, 4, 4)),
                new Giocatore(prefisso + "5", "Gialli", LocalDate.of(2000, 5, 5))
        );
    }
}
