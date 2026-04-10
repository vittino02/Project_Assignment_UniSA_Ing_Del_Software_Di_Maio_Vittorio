
package it.unisa.torneo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartitaTest {

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

    @Test
    void costruttoreValidoInizializzaCorrettamenteLaPartita() {
        Squadra casa = creaSquadra("Falchi");
        Squadra ospite = creaSquadra("Leoni");
        LocalDate data = LocalDate.of(2026, 4, 7);

        Partita partita = new Partita(data, casa, ospite, 3, 1);

        assertEquals(data, partita.getData());
        assertEquals(casa, partita.getSquadraCasa());
        assertEquals(ospite, partita.getSquadraOspite());
        assertEquals(3, partita.getGoalCasa());
        assertEquals(1, partita.getGoalOspite());
    }

    @Test
    void isPareggioRestituisceTrueSeIGolSonoUguali() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                creaSquadra("Falchi"),
                creaSquadra("Leoni"),
                2,
                2
        );

        assertTrue(partita.isPareggio());
    }

    @Test
    void isPareggioRestituisceFalseSeIGolSonoDiversi() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                creaSquadra("Falchi"),
                creaSquadra("Leoni"),
                2,
                1
        );

        assertFalse(partita.isPareggio());
    }

    @Test
    void getPuntiCasaEGetPuntiOspiteFunzionanoInCasoDiVittoriaCasa() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                creaSquadra("Falchi"),
                creaSquadra("Leoni"),
                4,
                1
        );

        assertEquals(3, partita.getPuntiCasa());
        assertEquals(0, partita.getPuntiOspite());
    }

    @Test
    void getPuntiCasaEGetPuntiOspiteFunzionanoInCasoDiPareggio() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                creaSquadra("Falchi"),
                creaSquadra("Leoni"),
                1,
                1
        );

        assertEquals(1, partita.getPuntiCasa());
        assertEquals(1, partita.getPuntiOspite());
    }

    @Test
    void getPuntiCasaEGetPuntiOspiteFunzionanoInCasoDiVittoriaOspite() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                creaSquadra("Falchi"),
                creaSquadra("Leoni"),
                0,
                2
        );

        assertEquals(0, partita.getPuntiCasa());
        assertEquals(3, partita.getPuntiOspite());
    }

    @Test
    void aggiornaDatiModificaCorrettamenteLaPartita() {
        Squadra falchi = creaSquadra("Falchi");
        Squadra leoni = creaSquadra("Leoni");
        Squadra tigri = creaSquadra("Tigri");
        Squadra aquile = creaSquadra("Aquile");

        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                1,
                0
        );

        partita.aggiornaDati(
                LocalDate.of(2026, 4, 8),
                tigri,
                aquile,
                2,
                3
        );

        assertEquals(LocalDate.of(2026, 4, 8), partita.getData());
        assertEquals(tigri, partita.getSquadraCasa());
        assertEquals(aquile, partita.getSquadraOspite());
        assertEquals(2, partita.getGoalCasa());
        assertEquals(3, partita.getGoalOspite());
    }

    @Test
    void costruttoreLanciaEccezioneSeDataENulla() {
        Squadra casa = creaSquadra("Falchi");
        Squadra ospite = creaSquadra("Leoni");

        assertThrows(IllegalArgumentException.class, () ->
                new Partita(null, casa, ospite, 1, 0));
    }

    @Test
    void costruttoreLanciaEccezioneSeSquadraCasaENulla() {
        Squadra ospite = creaSquadra("Leoni");

        assertThrows(IllegalArgumentException.class, () ->
                new Partita(LocalDate.of(2026, 4, 7), null, ospite, 1, 0));
    }

    @Test
    void costruttoreLanciaEccezioneSeSquadraOspiteENulla() {
        Squadra casa = creaSquadra("Falchi");

        assertThrows(IllegalArgumentException.class, () ->
                new Partita(LocalDate.of(2026, 4, 7), casa, null, 1, 0));
    }

    @Test
    void costruttoreLanciaEccezioneSeLeSquadreCoincidono() {
        Squadra squadra = creaSquadra("Falchi");

        assertThrows(IllegalArgumentException.class, () ->
                new Partita(LocalDate.of(2026, 4, 7), squadra, squadra, 1, 0));
    }

    @Test
    void costruttoreLanciaEccezioneSeGoalCasaENegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Partita(
                        LocalDate.of(2026, 4, 7),
                        creaSquadra("Falchi"),
                        creaSquadra("Leoni"),
                        -1,
                        0
                ));
    }

    @Test
    void costruttoreLanciaEccezioneSeGoalOspiteENegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Partita(
                        LocalDate.of(2026, 4, 7),
                        creaSquadra("Falchi"),
                        creaSquadra("Leoni"),
                        1,
                        -1
                ));
    }
}