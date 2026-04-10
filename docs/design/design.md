Design del sistema
1. Obiettivo
Il progetto realizza un'applicazione Java per la gestione di un torneo di calcio a 5. Il sistema consente di:
registrare e modificare le squadre;
registrare, modificare e rimuovere le partite;
visualizzare la classifica aggiornata;
salvare e caricare l'archivio del torneo.
L'implementazione corrente utilizza JavaFX con FXML per la parte grafica e mantiene separati modello, controllo applicativo, persistenza e presentazione.
2. Architettura complessiva
L'architettura del progetto è suddivisa in cinque aree principali.
`it.unisa.torneo`  
Contiene la classe `Main`, che avvia l'applicazione JavaFX, carica il layout FXML principale e collega la GUI al controller applicativo.
`it.unisa.torneo.view`  
Contiene i controller JavaFX della schermata principale e dei dialog (`InterfacciaUtente`, `SquadraDialogController`, `PartitaDialogController`) e le risorse FXML/CSS.
`it.unisa.torneo.controller`  
Contiene `TorneoController`, che espone le operazioni applicative richieste dalla view e delega al modello la logica di dominio.
`it.unisa.torneo.model`  
Contiene le classi del dominio: `Torneo`, `Squadra`, `Giocatore`, `Partita`, `Classifica` e `VoceClassifica`.
`it.unisa.torneo.persistence`  
Contiene `ArchivioTorneoService`, responsabile del salvataggio e del caricamento dell'archivio.
Questa decomposizione consente di mantenere separate le responsabilità:
la view gestisce interazione, navigazione e validazione immediata dei form;
il controller applicativo offre una facciata unica verso il modello;
il modello incapsula regole e vincoli del dominio;
il servizio di persistenza gestisce esclusivamente il formato dell'archivio e la ricostruzione dei dati.
3. Diagramma di classe
Il diagramma di classe aggiornato è disponibile nel file:
`docs/design/UMLDiagrams/Class Diagrams/Class_Diagram_Progetto_ing_del_Software.cdg`
Il diagramma include le principali classi oggi presenti nell'applicazione:
`Main`
`InterfacciaUtente`
`SquadraDialogController`
`PartitaDialogController`
`TorneoController`
`ArchivioTorneoService`
`Torneo`
`Classifica`
`VoceClassifica`
`Squadra`
`Giocatore`
`Partita`
I file FXML e CSS non sono rappresentati come classi, poiché costituiscono risorse di configurazione della view e non elementi del modello a oggetti. In particolare:
`main-view.fxml`
`squadra-dialog.fxml`
`partita-dialog.fxml`
`app.css`
4. Responsabilità delle classi principali
4.1 Bootstrap
`Main`  
Avvia JavaFX, carica `main-view.fxml`, applica `app.css`, crea il `TorneoController` e lo inietta nel controller della schermata principale.
4.2 View
`InterfacciaUtente`  
È il controller FXML della finestra principale. Configura le tabelle, aggiorna i dati visualizzati, apre i dialog di inserimento/modifica e gestisce le operazioni di salvataggio e caricamento dell'archivio.
`SquadraDialogController`  
Gestisce il dialog per l'inserimento e la modifica delle squadre. Valida i campi del form e converte i valori inseriti in oggetti `Giocatore`.
`PartitaDialogController`  
Gestisce il dialog per l'inserimento e la modifica delle partite. Valida i campi del form e costruisce la nuova `Partita`.
4.3 Controller applicativo
`TorneoController`  
Svolge il ruolo di facciata tra GUI e modello. Espone le operazioni di gestione di squadre, partite, classifica e archivio senza esporre alla view i dettagli interni del modello.
4.4 Model
`Torneo`  
È il nucleo del dominio. Contiene l'elenco delle squadre, l'elenco delle partite e la classifica corrente. Applica i principali vincoli di coerenza e ricalcola la classifica dopo ogni modifica.
`Squadra`  
Rappresenta una squadra e la sua rosa di giocatori.
`Giocatore`  
Rappresenta un giocatore tramite nome, cognome e data di nascita.
`Partita`  
Rappresenta una partita tramite data, squadre coinvolte e goal segnati.
`Classifica`  
Mantiene le voci di classifica e le ordina in base ai punti.
`VoceClassifica`  
Collega una squadra al relativo punteggio corrente.
4.5 Persistenza
`ArchivioTorneoService`  
Salva e carica l'archivio del torneo. Serializza squadre e partite e ricostruisce il modello al caricamento.
5. Relazioni principali del diagramma di classe
Le relazioni più significative del diagramma sono le seguenti.
`Main` usa `InterfacciaUtente`, `TorneoController` e `Torneo` per costruire e inizializzare l'applicazione.
`InterfacciaUtente` mantiene un riferimento a `TorneoController` e utilizza i due dialog controller JavaFX per raccogliere i dati dei form.
`TorneoController` mantiene un riferimento a `Torneo` e a `ArchivioTorneoService`.
`Torneo` aggrega più `Squadra`, più `Partita` e una `Classifica`.
`Squadra` aggrega i `Giocatore` che compongono la rosa.
`Partita` referenzia due `Squadra`, con i ruoli distinti di squadra di casa e squadra ospite.
`Classifica` aggrega più `VoceClassifica`.
`VoceClassifica` referenzia una `Squadra`.
Le dipendenze tratteggiate eventualmente generate automaticamente dagli strumenti UML non vengono considerate centrali nella discussione progettuale, poiché molte di esse derivano semplicemente dall'uso di una classe come parametro o tipo di ritorno di un metodo. Per questo motivo l'attenzione è posta soprattutto sulle associazioni strutturali tra le classi.
6. Scelte di design
6.1 Separazione delle responsabilità
La GUI JavaFX non contiene la logica del torneo. I controller della view raccolgono i dati dell'utente e invocano `TorneoController`, che a sua volta delega al modello la logica di dominio.
Questa scelta evita che la logica applicativa venga dispersa nei metodi evento della GUI e rende il codice più semplice da comprendere, mantenere e testare.
6.2 JavaFX, FXML e CSS
La parte grafica è stata organizzata in modo dichiarativo:
il layout principale è descritto in `main-view.fxml`;
i dialog sono descritti in `squadra-dialog.fxml` e `partita-dialog.fxml`;
l'aspetto grafico è centralizzato in `app.css`.
Questa soluzione rende la view più leggibile e riduce l'accoppiamento tra struttura della GUI e logica Java.
6.3 Persistenza separata dal modello
Il salvataggio non è stato inserito direttamente dentro `Torneo`. La persistenza è stata isolata in `ArchivioTorneoService`, così il modello resta focalizzato sulle regole del dominio.
La classifica non viene salvata come dato autonomo, ma viene ricostruita a partire da squadre e partite durante il caricamento. In questo modo si evita di duplicare informazione derivata e si riduce il rischio di incoerenze.
6.4 Identità delle squadre nel caricamento
Durante il caricamento dell'archivio, il servizio ricostruisce prima le squadre e poi le partite, riutilizzando le stesse istanze di `Squadra` all'interno del nuovo `Torneo`.
Questa scelta è importante perché:
`Torneo` verifica che le squadre di una partita appartengano al torneo;
`Classifica` e `Partita` lavorano con riferimenti coerenti alle stesse istanze di dominio.
6.5 Coesione e accoppiamento
La progettazione mira a mantenere alta coesione e accoppiamento contenuto:
ogni classe ha una responsabilità principale ben identificabile;
il modello non dipende da JavaFX;
la view non accede direttamente alla persistenza;
la persistenza non contiene regole di business.
Non vengono riportate metriche numeriche di coesione o accoppiamento nel progetto; la valutazione è quindi qualitativa e basata sulla decomposizione adottata.
7. Diagrammi di sequenza
Nel repository sono presenti i diagrammi di sequenza relativi a:
inserimento squadra;
inserimento partita;
modifica squadra;
visualizzazione classifica.
Dal punto di vista logico, questi flussi restano validi anche nell'implementazione JavaFX corrente:
l'utente interagisce con la view;
la view usa `TorneoController`;
il controller usa il modello;
il modello aggiorna la classifica e restituisce i dati aggiornati alla view.
Nella versione attuale, la raccolta dei dati di input avviene tramite `InterfacciaUtente` e i dialog controller JavaFX, invece che tramite un'interfaccia testuale.
8. Commento ai diagrammi di sequenza
8.1 Inserimento squadra
Il diagramma di sequenza dell'inserimento squadra descrive il flusso che parte dall'interazione dell'utente con la view, prosegue con la raccolta dei dati anagrafici della squadra e dei cinque giocatori, e termina con la richiesta al `TorneoController` di inserire la nuova squadra nel modello. Il controller delega quindi al `Torneo` l'aggiornamento dell'archivio delle squadre e il ricalcolo della classifica.
8.2 Inserimento partita
Il diagramma di sequenza dell'inserimento partita rappresenta il caso in cui l'utente seleziona le due squadre, inserisce data e risultato e conferma l'operazione. La view costruisce i dati necessari, il controller crea la nuova `Partita` e la aggiunge al `Torneo`, che aggiorna poi la classifica in base al risultato registrato.
8.3 Modifica squadra
Il diagramma di sequenza della modifica squadra mostra l'interazione in cui l'utente seleziona una squadra esistente, modifica il nome o i giocatori e salva le nuove informazioni. Il `TorneoController` inoltra l'operazione al modello, che aggiorna l'oggetto `Squadra` e mantiene la coerenza complessiva dei dati.
8.4 Visualizzazione classifica
Il diagramma di sequenza della visualizzazione classifica rappresenta il flusso più semplice tra quelli considerati: la view richiede al controller la classifica corrente, il controller la recupera dal modello e restituisce alla GUI le informazioni da mostrare all'utente.
9. Documentazione Doxygen
La documentazione Doxygen è generata a partire dai file sorgente in `src/main/java`.
Sono documentati:
lo scopo delle classi;
le responsabilità dei metodi principali;
i parametri e i valori di ritorno;
le eccezioni di I/O nelle operazioni di persistenza più rilevanti.
La configurazione è nel file:
`docs/design/Documentazione Doxygen/Doxyfile`
L'output HTML viene generato nella cartella:
`docs/design/Documentazione Doxygen/html`
10. Considerazioni finali
La struttura attuale del progetto è coerente con un'architettura MVC estesa con un servizio di persistenza.
In particolare:
il modello resta indipendente dalla GUI;
la persistenza è isolata;
la classifica viene trattata come dato derivato;
la GUI JavaFX è organizzata tramite FXML, controller dedicati e CSS condiviso.
Nel complesso, la soluzione adottata risulta adeguata alle dimensioni del progetto e sufficientemente modulare da consentire future estensioni senza modificare in modo invasivo la logica di dominio.