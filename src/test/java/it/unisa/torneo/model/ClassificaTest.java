package it.unisa.torneo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassificaTest {

    private Classifica classifica;
    private Squadra falchi;
    private Squadra leoni;
    private Squadra tigri;

    @BeforeEach
    void setUp() {
        classifica = new Classifica();
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
        for (VoceClassifica voce : classifica.getVoci()) {
            if (voce.getSquadra() == squadra) {
                return voce.getPunti();
            }
        }
        fail("Squadra non trovata in classifica");
        return -1;
    }

    @Test
    void aggiornaConListeVuoteProduceClassificaVuota() {
        classifica.aggiorna(new ArrayList<>(), new ArrayList<>());

        assertTrue(classifica.getVoci().isEmpty());
    }

    @Test
    void aggiornaConUnaPartitaVintaDallaSquadraDiCasaAssegnaPuntiCorretti() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                2,
                0
        );

        classifica.aggiorna(List.of(falchi, leoni), List.of(partita));

        assertEquals(3, puntiDi(falchi));
        assertEquals(0, puntiDi(leoni));
    }

    @Test
    void aggiornaConPareggioAssegnaUnPuntoAPerSquadra() {
        Partita partita = new Partita(
                LocalDate.of(2026, 4, 7),
                falchi,
                leoni,
                1,
                1
        );

        classifica.aggiorna(List.of(falchi, leoni), List.of(partita));

        assertEquals(1, puntiDi(falchi));
        assertEquals(1, puntiDi(leoni));
    }

    @Test
    void aggiornaConPiuPartiteOrdinaLaClassificaInOrdineDecrescenteDiPunti() {
        Partita p1 = new Partita(LocalDate.of(2026, 4, 7), falchi, leoni, 2, 0); // Falchi 3
        Partita p2 = new Partita(LocalDate.of(2026, 4, 8), falchi, tigri, 1, 0); // Falchi 6
        Partita p3 = new Partita(LocalDate.of(2026, 4, 9), tigri, leoni, 3, 1);  // Tigri 3

        classifica.aggiorna(List.of(falchi, leoni, tigri), List.of(p1, p2, p3));

        List<VoceClassifica> voci = classifica.getVoci();

        assertEquals(falchi, voci.get(0).getSquadra());
        assertEquals(6, voci.get(0).getPunti());

        assertEquals(tigri, voci.get(1).getSquadra());
        assertEquals(3, voci.get(1).getPunti());

        assertEquals(leoni, voci.get(2).getSquadra());
        assertEquals(0, voci.get(2).getPunti());
    }

    @Test
    void aggiornaCreaUnaVocePerOgniSquadra() {
        classifica.aggiorna(List.of(falchi, leoni, tigri), List.of());

        assertEquals(3, classifica.getVoci().size());
    }

    @Test
    void aggiornaLanciaEccezioneSeListaSquadreENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                classifica.aggiorna(null, List.of()));
    }

    @Test
    void aggiornaLanciaEccezioneSeListaPartiteENulla() {
        assertThrows(IllegalArgumentException.class, () ->
                classifica.aggiorna(List.of(falchi, leoni), null));
    }
}