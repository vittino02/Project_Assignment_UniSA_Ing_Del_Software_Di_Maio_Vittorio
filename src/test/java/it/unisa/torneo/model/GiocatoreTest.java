package it.unisa.torneo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GiocatoreTest {

    private Giocatore giocatore;
    private LocalDate data;

    @BeforeEach
    void setUp() {
        data = LocalDate.of(2000, 1, 15);
        giocatore = new Giocatore("Emilio", "Renna", data);
    }

    @Test
    void costruttoreValidoInizializzaCorrettamenteIlGiocatore() {
        assertEquals("Emilio", giocatore.getNome());
        assertEquals("Renna", giocatore.getCognome());
        assertEquals(data, giocatore.getDataNascita());
    }

    @Test
    void aggiornaDatiModificaCorrettamenteIlGiocatore() {
        LocalDate nuovaData = LocalDate.of(2001, 5, 20);

        giocatore.aggiornaDati("Luca", "Laurenti", nuovaData);

        assertEquals("Luca", giocatore.getNome());
        assertEquals("Laurenti", giocatore.getCognome());
        assertEquals(nuovaData, giocatore.getDataNascita());
    }

    @Test
    void costruttoreLanciaEccezioneSeNomeENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Giocatore(null, "Renna", data));
    }

    @Test
    void costruttoreLanciaEccezioneSeNomeEVuoto() {
        assertThrows(IllegalArgumentException.class, () ->
                new Giocatore("   ", "Renna", data));
    }

    @Test
    void costruttoreLanciaEccezioneSeCognomeENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Giocatore("Emilio", null, data));
    }

    @Test
    void costruttoreLanciaEccezioneSeCognomeEVuoto() {
        assertThrows(IllegalArgumentException.class, () ->
                new Giocatore("Emilio", "   ", data));
    }

    @Test
    void costruttoreLanciaEccezioneSeDataNascitaENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                new Giocatore("Emilio", "Renna", null));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeNomeENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                giocatore.aggiornaDati(null, "Laurenti", data));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeCognomeENullo() {
        assertThrows(IllegalArgumentException.class, () ->
                giocatore.aggiornaDati("Luca", null, data));
    }

    @Test
    void aggiornaDatiLanciaEccezioneSeDataENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                giocatore.aggiornaDati("Luca", "Laurenti", null));
    }
}