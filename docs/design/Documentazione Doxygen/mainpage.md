# Documentazione tecnica del progetto

## Visione d'insieme

Il progetto realizza un'applicazione Java e JavaFX per la gestione di un torneo di calcio a 5.

Le funzionalita principali sono:

- gestione di squadre e giocatori;
- gestione delle partite;
- visualizzazione della classifica;
- salvataggio e caricamento dell'archivio del torneo.

## Architettura

L'architettura e organizzata nei seguenti package:

- `it.unisa.torneo`
  Bootstrap dell'applicazione JavaFX.
- `it.unisa.torneo.view`
  Controller JavaFX della finestra principale e dei dialog FXML.
- `it.unisa.torneo.controller`
  Facciata applicativa tra GUI e modello.
- `it.unisa.torneo.model`
  Classi del dominio del torneo.
- `it.unisa.torneo.persistence`
  Servizio di persistenza dell'archivio.

## Flusso principale

1. `Main` carica `main-view.fxml` e `app.css`.
2. `InterfacciaUtente` configura tabelle, dialog e azioni utente.
3. `TorneoController` inoltra le richieste al modello.
4. `Torneo` aggiorna dati e classifica.
5. `ArchivioTorneoService` salva o ricostruisce l'archivio quando richiesto.

## Persistenza

La persistenza e isolata in `ArchivioTorneoService`.

Il file di archivio salva:

- squadre;
- giocatori;
- partite.

La classifica non viene salvata come dato indipendente: viene ricalcolata al caricamento a partire dai risultati delle partite.

## Diagrammi e documenti di supporto

La documentazione di design del progetto e disponibile in:

- `docs/design/design.md`

La sorgente del diagramma di classe e disponibile in:

- `docs/design/UMLDiagrams/Class Diagrams/Class_Diagram_Progetto_ing_del_Software.cdg`

## Generazione della documentazione

Per rigenerare la documentazione HTML:

```powershell
doxygen "docs/design/Documentazione Doxygen/Doxyfile"
```
