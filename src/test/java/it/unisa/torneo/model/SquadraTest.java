package it.unisa.torneo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class SquadraTest {
    private Squadra squadra;
    private Giocatore g1;
    private Giocatore g2;
    private Giocatore g3;
    private Giocatore g4;
    private Giocatore g5;
    private Giocatore g6;
    
    @BeforeEach
    void setUp() {
        squadra = new Squadra("Delfini");

        g1 = new Giocatore("Vittorio", "Di Maio", LocalDate.of(2000, 1, 1));
        g2 = new Giocatore("Luca", "Laoreana", LocalDate.of(2000, 2, 2));
        g3 = new Giocatore("Vincenzo", "Salerno", LocalDate.of(2000, 3, 3));
        g4 = new Giocatore("Nicoletta", "Santangelo", LocalDate.of(2000, 4, 4));
        g5 = new Giocatore("Giuseppe", "Salenro", LocalDate.of(2000, 5, 5));
        g6 = new Giocatore("Leonardo", "Rizzo", LocalDate.of(2000, 6, 6));
    }


private List<Giocatore> listaCompleta() {
        return new ArrayList<>(List.of(g1, g2, g3, g4, g5));
    }

    @Test
    void costruttoreValidoInizializzaCorrettamenteLaSquadra() {
        assertEquals("Delfini", squadra.getNome());
        assertTrue(squadra.getGiocatori().isEmpty());
        assertFalse(squadra.isCompleta());
    }
    
@Test
    void costruttoreLanciaEccezioneSeNomeENullo() {
        assertThrows(IllegalArgumentException.class, () -> new Squadra(null));
    }

    @Test
    void costruttoreLanciaEccezioneSeNomeEVuoto() {
        assertThrows(IllegalArgumentException.class, () -> new Squadra("   "));
    }

    @Test
    void aggiungiGiocatoreAggiungeCorrettamenteUnGiocatore() {
        squadra.aggiungiGiocatore(g1);

        assertEquals(1, squadra.getGiocatori().size());
        assertTrue(squadra.getGiocatori().contains(g1));
        assertFalse(squadra.isCompleta());
    }

    @Test
    void aggiungiGiocatoreLanciaEccezioneSeGiocatoreENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiungiGiocatore(null));
    }

    @Test
    void aggiungiGiocatoreRendeCompletaLaSquadraAlQuintoGiocatore() {
        squadra.aggiungiGiocatore(g1);
        squadra.aggiungiGiocatore(g2);
        squadra.aggiungiGiocatore(g3);
        squadra.aggiungiGiocatore(g4);
        squadra.aggiungiGiocatore(g5);

        assertEquals(5, squadra.getGiocatori().size());
        assertTrue(squadra.isCompleta());
    }

    @Test
    void aggiungiGiocatoreLanciaEccezioneSeSiSuperanoCinqueGiocatori() {
        squadra.aggiungiGiocatore(g1);
        squadra.aggiungiGiocatore(g2);
        squadra.aggiungiGiocatore(g3);
        squadra.aggiungiGiocatore(g4);
        squadra.aggiungiGiocatore(g5);

        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiungiGiocatore(g6));
    }

    @Test
    void rimuoviGiocatoreRimuoveCorrettamenteIlGiocatore() {
        squadra.aggiornaDati("Delfini", listaCompleta());

        squadra.rimuoviGiocatore(g3);

        assertEquals(4, squadra.getGiocatori().size());
        assertFalse(squadra.getGiocatori().contains(g3));
        assertFalse(squadra.isCompleta());
    }

    @Test
    void rimuoviGiocatoreLanciaEccezioneSeGiocatoreENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.rimuoviGiocatore(null));
    }

    @Test
    void rimuoviGiocatoreLanciaEccezioneSeGiocatoreNonAppartieneAllaSquadra() {
        squadra.aggiornaDati("Delfini", listaCompleta());

        assertThrows(IllegalArgumentException.class, () ->
                squadra.rimuoviGiocatore(g6));
    }

    @Test
    void sostituisciGiocatoreSostituisceCorrettamenteIlGiocatore() {
        squadra.aggiornaDati("Delfini", listaCompleta());

        squadra.sostituisciGiocatore(g3, g6);

        assertEquals(5, squadra.getGiocatori().size());
        assertFalse(squadra.getGiocatori().contains(g3));
        assertTrue(squadra.getGiocatori().contains(g6));
        assertTrue(squadra.isCompleta());
    }

    @Test
    void sostituisciGiocatoreLanciaEccezioneSeVecchioENuovoSonoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.sostituisciGiocatore(null, g6));

        assertThrows(IllegalArgumentException.class, () ->
                squadra.sostituisciGiocatore(g1, null));
    }

    @Test
    void sostituisciGiocatoreLanciaEccezioneSeIlGiocatoreVecchioNonEsiste() {
        squadra.aggiornaDati("Delfini", listaCompleta());

        assertThrows(IllegalArgumentException.class, () ->
                squadra.sostituisciGiocatore(g6, g3));
    }

    @Test
    void aggiornaDatiModificaNomeERosaCorrettamente() {
        List<Giocatore> nuovaLista = new ArrayList<>(List.of(g2, g3, g4, g5, g6));

        squadra.aggiornaDati("Leoni", nuovaLista);

        assertEquals("Leoni", squadra.getNome());
        assertEquals(5, squadra.getGiocatori().size());
        assertTrue(squadra.getGiocatori().contains(g6));
        assertFalse(squadra.getGiocatori().contains(g1));
        assertTrue(squadra.isCompleta());
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeNomeENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiornaDati(null, listaCompleta()));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeNomeEVuoto() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiornaDati("   ", listaCompleta()));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeListaGiocatoriENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiornaDati("Falchi", null));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeGiocatoriSonoMenoDiCinque() {
        List<Giocatore> listaIncompleta = new ArrayList<>(List.of(g1, g2, g3, g4));

        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiornaDati("Delfini", listaIncompleta));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeGiocatoriSonoPiuDiCinque() {
        List<Giocatore> listaTroppoGrande = new ArrayList<>(List.of(g1, g2, g3, g4, g5, g6));

        assertThrows(IllegalArgumentException.class, () ->
                squadra.aggiornaDati("Delfini", listaTroppoGrande));
    }
}
