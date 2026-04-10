package it.unisa.torneo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TorneoTest {

    private Torneo torneo;
    private Squadra falchi;
    private Squadra leoni;
    private Squadra tigri;

    @BeforeEach
    void setUp() {
        torneo = new Torneo();
        falchi = creaSquadra("Falchi");
        leoni = creaSquadra("Leoni");
        tigri = creaSquadra("Tigri");
    }

    private Squadra creaSquadra(String nome) {
        Squadra squadra = new Squadra(nome);
        squadra.aggiornaDati(nome, List.of(
                new Giocatore(nome + "1", "Rossi", LocalDate.of(2000, 1, 1)),
                new Giocatore(nome + "2", "Bianchi", LocalDate.of(2000, 2, 2)),
                new Giocatore(nome + "3", "Verdi", LocalDate.of(2000, 3, 3)),
                new Giocatore(nome + "4", "Neri", LocalDate.of(2000, 4, 4)),
                new Giocatore(nome + "5", "Gialli", LocalDate.of(2000, 5, 5))
        ));
        return squadra;
    }

    private int puntiDi(Squadra squadra) {
        for (VoceClassifica voce : torneo.getClassifica().getVoci()) {
            if (voce.getSquadra() == squadra) {
                return voce.getPunti();
            }
        }
        fail("Squadra non trovata in classifica");
        return -1;
    }

    @Test
    void costruttoreInizializzaCorrettamenteIlTorneo() {
        assertTrue(torneo.getSquadre().isEmpty());
        assertTrue(torneo.getPartite().isEmpty());
        assertNotNull(torneo.getClassifica());
    }

    @Test
    void aggiungiSquadraInserisceCorrettamenteUnaSquadra() {
        torneo.aggiungiSquadra(falchi);

        assertEquals(1, torneo.getSquadre().size());
        assertTrue(torneo.getSquadre().contains(falchi));
    }

    @Test
    void aggiungiSquadraLanciaEccezioneSeSquadraENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                torneo.aggiungiSquadra(null));
    }

    @Test
    void aggiungiSquadraLanciaEccezioneSeLaSquadraNonECompleta() {
        Squadra incompleta = new Squadra("Incompleta");

        assertThrows(IllegalArgumentException.class, () ->
                torneo.aggiungiSquadra(incompleta));
    }

    @Test
    void aggiungiSquadraLanciaEccezioneSeEsisteGiaUnaSquadraConLoStessoNome() {
        torneo.aggiungiSquadra(falchi);
        Squadra duplicata = creaSquadra("Falchi");

        assertThrows(IllegalArgumentException.class, () ->
                torneo.aggiungiSquadra(duplicata));
    }

    @Test
    void modificaSquadraAggiornaCorrettamenteNomeERosa() {
        torneo.aggiungiSquadra(falchi);

        List<Giocatore> nuovaRosa = List.of(
                new Giocatore("L1", "Rossi", LocalDate.of(2000, 1, 1)),
                new Giocatore("L2", "Bianchi", LocalDate.of(2000, 2, 2)),
                new Giocatore("L3", "Verdi", LocalDate.of(2000, 3, 3)),
                new Giocatore("L4", "Neri", LocalDate.of(2000, 4, 4)),
                new Giocatore("L5", "Gialli", LocalDate.of(2000, 5, 5))
        );

        torneo.modificaSquadra(falchi, "NuoviFalchi", nuovaRosa);

        assertEquals("NuoviFalchi", falchi.getNome());
        assertEquals(5, falchi.getGiocatori().size());
    }

    @Test
    void modificaSquadraLanciaEccezioneSeLaSquadraNonAppartieneAlTorneo() {
        assertThrows(IllegalArgumentException.class, () ->
                torneo.modificaSquadra(falchi, "NuovoNome", falchi.getGiocatori()));
    }

    @Test
    void aggiungiPartitaInserisceCorrettamenteLaPartitaEAggiornaLaClassifica() {
        torneo.aggiungiSquadra(falchi);
        torneo.aggiungiSquadra(leoni);

        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        torneo.aggiungiPartita(partita);

        assertEquals(1, torneo.getPartite().size());
        assertEquals(3, puntiDi(falchi));
        assertEquals(0, puntiDi(leoni));
    }

    @Test
    void aggiungiPartitaLanciaEccezioneSeLeSquadreNonAppartengonoAlTorneo() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        assertThrows(IllegalArgumentException.class, () ->
                torneo.aggiungiPartita(partita));
    }

    @Test
    void modificaPartitaAggiornaLaPartitaERicalcolaLaClassifica() {
        torneo.aggiungiSquadra(falchi);
        torneo.aggiungiSquadra(leoni);

        Partita vecchia = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        torneo.aggiungiPartita(vecchia);

        Partita nuova = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                1,
                1
        );

        torneo.modificaPartita(vecchia, nuova);

        assertEquals(1, torneo.getPartite().size());
        assertEquals(1, puntiDi(falchi));
        assertEquals(1, puntiDi(leoni));
    }

    @Test
    void rimuoviPartitaEliminaLaPartitaEAggiornaLaClassifica() {
        torneo.aggiungiSquadra(falchi);
        torneo.aggiungiSquadra(leoni);

        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        torneo.aggiungiPartita(partita);
        torneo.rimuoviPartita(partita);

        assertTrue(torneo.getPartite().isEmpty());
        assertEquals(0, puntiDi(falchi));
        assertEquals(0, puntiDi(leoni));
    }

    @Test
    void rimuoviPartitaLanciaEccezioneSeLaPartitaNonAppartieneAlTorneo() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        assertThrows(IllegalArgumentException.class, () ->
                torneo.rimuoviPartita(partita));
    }
}