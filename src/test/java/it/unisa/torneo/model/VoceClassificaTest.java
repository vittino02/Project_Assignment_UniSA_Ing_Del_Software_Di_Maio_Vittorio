package it.unisa.torneo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoceClassificaTest {

    private Squadra squadra;

    @BeforeEach
    void setUp() {
        squadra = new Squadra("Delfini");
        squadra.aggiornaDati("Delfini", List.of(
        new Giocatore("Vittorio", "Di Maio", LocalDate.of(2000, 1, 1)),
        new Giocatore("Luca", "Laoreana", LocalDate.of(2000, 2, 2)),
        new Giocatore("Vincenzo", "Salerno", LocalDate.of(2000, 3, 3)),
        new Giocatore("Nicoletta", "Santangelo", LocalDate.of(2000, 4, 4)),
        new Giocatore("Giuseppe", "Salenro", LocalDate.of(2000, 5, 5))
                ));
    }

    @Test
    void costruttoreValidoInizializzaCorrettamenteLaVoce() {
        VoceClassifica voce = new VoceClassifica(squadra, 3);

        assertEquals(squadra, voce.getSquadra());
        assertEquals(3, voce.getPunti());
    }

    @Test
    void incrementaPuntiAumentaCorrettamenteIlPunteggio() {
        VoceClassifica voce = new VoceClassifica(squadra, 1);

        voce.incrementaPunti(3);

        assertEquals(4, voce.getPunti());
    }

    @Test
    void setPuntiAggiornaCorrettamenteIlPunteggio() {
        VoceClassifica voce = new VoceClassifica(squadra, 1);

        voce.setPunti(7);

        assertEquals(7, voce.getPunti());
    }

    @Test
    void costruttoreLanciaEccezioneSeSquadraENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                new VoceClassifica(null, 0));
    }

    @Test
    void costruttoreLanciaEccezioneSePuntiSonoNegativi() {
        assertThrows(IllegalArgumentException.class, () ->
                new VoceClassifica(squadra, -1));
    }

    @Test
    void incrementaPuntiLanciaEccezioneSeValoreENegativo() {
        VoceClassifica voce = new VoceClassifica(squadra, 0);

        assertThrows(IllegalArgumentException.class, () ->
                voce.incrementaPunti(-1));
    }

    @Test
    void setPuntiLanciaEccezioneSePuntiSonoNegativi() {
        VoceClassifica voce = new VoceClassifica(squadra, 0);

        assertThrows(IllegalArgumentException.class, () ->
                voce.setPunti(-1));
    }
}
