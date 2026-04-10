package it.unisa.torneo.persistence;

import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.Torneo;
import it.unisa.torneo.model.VoceClassifica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArchivioTorneoServiceTest {

    @TempDir
    Path tempDir;

    private final ArchivioTorneoService service = new ArchivioTorneoService();

    @Test
    void salvaECaricaRipristinanoSquadrePartiteEClassifica() throws Exception {
        Path archivio = tempDir.resolve("torneo.torneo");
        Torneo torneo = creaTorneoConDati();

        service.salva(archivio, torneo);
        Torneo torneoCaricato = service.carica(archivio);

        assertEquals(2, torneoCaricato.getSquadre().size());
        assertEquals(1, torneoCaricato.getPartite().size());

        Squadra falchi = torneoCaricato.getSquadre().get(0);
        assertEquals("Falchi", falchi.getNome());
        assertEquals(5, falchi.getGiocatori().size());

        Partita partita = torneoCaricato.getPartite().get(0);
        assertEquals("Falchi", partita.getSquadraCasa().getNome());
        assertEquals("Leoni", partita.getSquadraOspite().getNome());
        assertEquals(4, partita.getGoalCasa());
        assertEquals(2, partita.getGoalOspite());

        List<VoceClassifica> classifica = torneoCaricato.getClassifica().getVoci();
        assertEquals("Falchi", classifica.get(0).getSquadra().getNome());
        assertEquals(3, classifica.get(0).getPunti());
        assertEquals("Leoni", classifica.get(1).getSquadra().getNome());
        assertEquals(0, classifica.get(1).getPunti());
    }

    @Test
    void salvaConPercorsoNullLanciaEccezione() {
        IllegalArgumentException eccezione = assertThrows(
                IllegalArgumentException.class,
                () -> service.salva(null, new Torneo())
        );

        assertEquals("Il percorso di salvataggio non puo essere null.", eccezione.getMessage());
    }

    @Test
    void salvaConTorneoNullLanciaEccezione() {
        IllegalArgumentException eccezione = assertThrows(
                IllegalArgumentException.class,
                () -> service.salva(tempDir.resolve("torneo.torneo"), null)
        );

        assertEquals("Il torneo non puo essere null.", eccezione.getMessage());
    }

    @Test
    void caricaConPercorsoNullLanciaEccezione() {
        IllegalArgumentException eccezione = assertThrows(
                IllegalArgumentException.class,
                () -> service.carica(null)
        );

        assertEquals("Il percorso di caricamento non puo essere null.", eccezione.getMessage());
    }

    @Test
    void caricaConFileInesistenteLanciaEccezione() {
        IOException eccezione = assertThrows(
                IOException.class,
                () -> service.carica(tempDir.resolve("mancante.torneo"))
        );

        assertEquals("Il file selezionato non esiste.", eccezione.getMessage());
    }

    @Test
    void caricaConArchivioNonValidoLanciaEccezione() throws Exception {
        Path archivio = tempDir.resolve("non-valido.torneo");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(archivio))) {
            outputStream.writeObject("contenuto non valido");
        }

        IOException eccezione = assertThrows(
                IOException.class,
                () -> service.carica(archivio)
        );

        assertEquals("Il file selezionato non contiene un archivio valido.", eccezione.getMessage());
    }

    private Torneo creaTorneoConDati() {
        Torneo torneo = new Torneo();
        Squadra falchi = new Squadra("Falchi");
        falchi.aggiornaDati("Falchi", creaGiocatori("F"));
        Squadra leoni = new Squadra("Leoni");
        leoni.aggiornaDati("Leoni", creaGiocatori("L"));

        torneo.aggiungiSquadra(falchi);
        torneo.aggiungiSquadra(leoni);
        torneo.aggiungiPartita(new Partita(LocalDate.of(2026, 4, 9), falchi, leoni, 4, 2));
        return torneo;
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
