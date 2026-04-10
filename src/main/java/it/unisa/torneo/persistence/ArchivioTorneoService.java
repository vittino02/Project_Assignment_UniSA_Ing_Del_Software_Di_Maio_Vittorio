package it.unisa.torneo.persistence;

import it.unisa.torneo.model.Giocatore;
import it.unisa.torneo.model.Partita;
import it.unisa.torneo.model.Squadra;
import it.unisa.torneo.model.Torneo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @brief Gestisce il salvataggio e il caricamento dell'archivio del torneo.
 *
 * Il service serializza i dati strutturali del torneo, cioe squadre, giocatori
 * e partite. La classifica non viene persa come dato autonomo, ma viene
 * ricostruita al caricamento a partire dalle partite presenti nell'archivio.
 */
public class ArchivioTorneoService {

    /**
     * @brief Salva l'archivio del torneo su file.
     *
     * @param[in] path percorso di destinazione
     * @param[in] torneo torneo da salvare
     * @throws IllegalArgumentException se percorso o torneo sono null
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    public void salva(Path path, Torneo torneo) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Il percorso di salvataggio non puo essere null.");
        }
        if (torneo == null) {
            throw new IllegalArgumentException("Il torneo non puo essere null.");
        }

        Path absolutePath = path.toAbsolutePath();
        Path parent = absolutePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        ArchivioTorneo archivio = ArchivioTorneo.from(torneo);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(absolutePath))) {
            outputStream.writeObject(archivio);
        }
    }

    /**
     * @brief Carica un archivio del torneo da file.
     *
     * @param[in] path percorso del file da leggere
     * @return nuovo torneo ricostruito dal file
     * @throws IllegalArgumentException se il percorso e null
     * @throws IOException se si verifica un errore di lettura o il file non e valido
     */
    public Torneo carica(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Il percorso di caricamento non puo essere null.");
        }

        Path absolutePath = path.toAbsolutePath();
        if (!Files.exists(absolutePath)) {
            throw new IOException("Il file selezionato non esiste.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(absolutePath))) {
            Object contenuto = inputStream.readObject();
            if (!(contenuto instanceof ArchivioTorneo archivio)) {
                throw new IOException("Il file selezionato non contiene un archivio valido.");
            }
            return archivio.toTorneo();
        } catch (ClassNotFoundException e) {
            throw new IOException("Impossibile leggere il contenuto dell'archivio.", e);
        } catch (IllegalArgumentException e) {
            throw new IOException("Il file selezionato contiene dati non validi.", e);
        }
    }

    /**
     * @brief DTO serializzabile dell'intero archivio del torneo.
     */
    private record ArchivioTorneo(List<SquadraArchivio> squadre, List<PartitaArchivio> partite) implements Serializable {

        private static final long serialVersionUID = 1L;

        private static ArchivioTorneo from(Torneo torneo) {
            List<SquadraArchivio> squadreArchivio = new ArrayList<>();
            for (Squadra squadra : torneo.getSquadre()) {
                squadreArchivio.add(SquadraArchivio.from(squadra));
            }

            List<PartitaArchivio> partiteArchivio = new ArrayList<>();
            for (Partita partita : torneo.getPartite()) {
                partiteArchivio.add(PartitaArchivio.from(partita));
            }

            return new ArchivioTorneo(squadreArchivio, partiteArchivio);
        }

        private Torneo toTorneo() {
            Torneo torneo = new Torneo();
            Map<String, Squadra> squadrePerNome = new LinkedHashMap<>();

            for (SquadraArchivio squadraArchivio : squadre) {
                Squadra squadra = squadraArchivio.toSquadra();
                torneo.aggiungiSquadra(squadra);
                squadrePerNome.put(squadra.getNome(), squadra);
            }

            for (PartitaArchivio partitaArchivio : partite) {
                torneo.aggiungiPartita(partitaArchivio.toPartita(squadrePerNome));
            }

            return torneo;
        }
    }

    /**
     * @brief DTO serializzabile di una squadra.
     */
    private record SquadraArchivio(String nome, List<GiocatoreArchivio> giocatori) implements Serializable {

        private static final long serialVersionUID = 1L;

        private static SquadraArchivio from(Squadra squadra) {
            List<GiocatoreArchivio> giocatoriArchivio = new ArrayList<>();
            for (Giocatore giocatore : squadra.getGiocatori()) {
                giocatoriArchivio.add(GiocatoreArchivio.from(giocatore));
            }
            return new SquadraArchivio(squadra.getNome(), giocatoriArchivio);
        }

        private Squadra toSquadra() {
            Squadra squadra = new Squadra(nome);
            List<Giocatore> listaGiocatori = new ArrayList<>();
            for (GiocatoreArchivio giocatoreArchivio : giocatori) {
                listaGiocatori.add(giocatoreArchivio.toGiocatore());
            }
            squadra.aggiornaDati(nome, listaGiocatori);
            return squadra;
        }
    }

    /**
     * @brief DTO serializzabile di un giocatore.
     */
    private record GiocatoreArchivio(String nome, String cognome, LocalDate dataNascita) implements Serializable {

        private static final long serialVersionUID = 1L;

        private static GiocatoreArchivio from(Giocatore giocatore) {
            return new GiocatoreArchivio(giocatore.getNome(), giocatore.getCognome(), giocatore.getDataNascita());
        }

        private Giocatore toGiocatore() {
            return new Giocatore(nome, cognome, dataNascita);
        }
    }

    /**
     * @brief DTO serializzabile di una partita.
     */
    private record PartitaArchivio(LocalDate data, String squadraCasa, String squadraOspite,
                                   int goalCasa, int goalOspite) implements Serializable {

        private static final long serialVersionUID = 1L;

        private static PartitaArchivio from(Partita partita) {
            return new PartitaArchivio(
                    partita.getData(),
                    partita.getSquadraCasa().getNome(),
                    partita.getSquadraOspite().getNome(),
                    partita.getGoalCasa(),
                    partita.getGoalOspite()
            );
        }

        private Partita toPartita(Map<String, Squadra> squadrePerNome) {
            Squadra casa = squadrePerNome.get(squadraCasa);
            Squadra ospite = squadrePerNome.get(squadraOspite);

            if (casa == null || ospite == null) {
                throw new IllegalArgumentException("La partita fa riferimento a squadre non presenti nell'archivio.");
            }

            return new Partita(data, casa, ospite, goalCasa, goalOspite);
        }
    }
}
