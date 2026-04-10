package it.unisa.torneo.controller;

import it.unisa.torneo.model.Classifica;
import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.Torneo;
import it.unisa.torneo.model.VoceClassifica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TorneoControllerTest {

    private Torneo torneo;
    private TorneoController controller;
    private List<Giocatore> giocatoriFalchi;
    private List<Giocatore> giocatoriLeoni;

    @BeforeEach
    void setUp() {
        torneo = new Torneo();
        controller = new TorneoController(torneo);

        giocatoriFalchi = creaGiocatori("F");
        giocatoriLeoni = creaGiocatori("L");
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

    private int puntiDi(Squadra squadra) {
        Classifica classifica = controller.getClassifica();
        for (VoceClassifica voce : classifica.getVoci()) {
            if (voce.getSquadra() == squadra) {
                return voce.getPunti();
            }
        }
        fail("Squadra non trovata in classifica");
        return -1;
    }

    @Test
    void inserisciSquadraAggiungeUnaNuovaSquadraAlTorneo() {
        controller.inserisciSquadra("Falchi", giocatoriFalchi);

        assertEquals(1, controller.getSquadre().size());
        assertEquals("Falchi", controller.getSquadre().get(0).getNome());
    }

    @Test
    void modificaSquadraAggiornaCorrettamenteLaSquadra() {
        controller.inserisciSquadra("Falchi", giocatoriFalchi);
        Squadra squadra = controller.getSquadre().get(0);

        controller.modificaSquadra(squadra, "NuoviFalchi", giocatoriLeoni);

        assertEquals("NuoviFalchi", squadra.getNome());
        assertEquals(5, squadra.getGiocatori().size());
    }

    @Test
    void inserisciPartitaAggiungePartitaEAggiornaClassifica() {
        controller.inserisciSquadra("Falchi", giocatoriFalchi);
        controller.inserisciSquadra("Leoni", giocatoriLeoni);

        Squadra falchi = controller.getSquadre().get(0);
        Squadra leoni = controller.getSquadre().get(1);

        controller.inserisciPartita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                3,
                1
        );

        assertEquals(1, controller.getPartite().size());
        assertEquals(3, puntiDi(falchi));
        assertEquals(0, puntiDi(leoni));
    }

    @Test
    void modificaPartitaAggiornaLaClassifica() {
        controller.inserisciSquadra("Falchi", giocatoriFalchi);
        controller.inserisciSquadra("Leoni", giocatoriLeoni);

        Squadra falchi = controller.getSquadre().get(0);
        Squadra leoni = controller.getSquadre().get(1);

        controller.inserisciPartita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                3,
                1
        );

        Partita vecchia = controller.getPartite().get(0);
        Partita nuova = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                2
        );

        controller.modificaPartita(vecchia, nuova);

        assertEquals(1, puntiDi(falchi));
        assertEquals(1, puntiDi(leoni));
    }

    @Test
    void rimuoviPartitaEliminaLaPartitaDalTorneo() {
        controller.inserisciSquadra("Falchi", giocatoriFalchi);
        controller.inserisciSquadra("Leoni", giocatoriLeoni);

        Squadra falchi = controller.getSquadre().get(0);
        Squadra leoni = controller.getSquadre().get(1);

        controller.inserisciPartita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                1,
                0
        );

        Partita partita = controller.getPartite().get(0);
        controller.rimuoviPartita(partita);

        assertTrue(controller.getPartite().isEmpty());
    }
}