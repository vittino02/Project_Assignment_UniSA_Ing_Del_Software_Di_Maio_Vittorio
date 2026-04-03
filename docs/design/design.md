Diagramma di classe



Il diagramma di classe descrive la struttura statica del sistema. La classe centrale del modello è `Torneo`, che coordina la gestione delle squadre, delle partite e della classifica. Le classi `Squadra`, `Giocatore` e `Partita` rappresentano le principali entità del dominio.

La classifica è modellata separatamente tramite le classi `Classifica` e `VoceClassifica`, in modo da mantenere distinti i dati strutturali del torneo dai dati derivati dai risultati delle partite.



Per ridurre l’accoppiamento tra interfaccia e modello, sono state introdotte anche le classi `TorneoController` e `InterfacciaUtente`, che consentono di separare la logica applicativa dalla presentazione.



Associazioni del diagramma di classe



Le associazioni presenti nel diagramma di classe derivano dalla struttura del dominio del problema e dalle scelte di progettazione adottate per separare modello, controllo e interfaccia utente.



L’associazione tra `InterfacciaUtente` e `TorneoController` indica che l’interfaccia non accede direttamente alle classi del dominio, ma delega al controller la gestione delle operazioni richieste dall’utente. Questa scelta riduce l’accoppiamento tra parte grafica e logica applicativa e rende più chiara la separazione delle responsabilità.



L’associazione tra `TorneoController` e `Torneo` rappresenta il fatto che il controller usa il modello principale del sistema come punto di accesso alle operazioni di gestione del torneo. In questo modo il controller coordina le richieste provenienti dall’interfaccia, mentre il modello mantiene i dati e le regole del dominio.



L’associazione tra `Torneo` e `Squadra` esprime che un torneo contiene un insieme di squadre registrate. La molteplicità `1 --- 0..\\\*` indica che un singolo torneo può gestire più squadre. Questa relazione è stata introdotta perché il torneo è il contenitore logico delle squadre partecipanti.



L’associazione tra `Torneo` e `Partita` indica che il torneo mantiene l’elenco delle partite disputate. Anche in questo caso la molteplicità `1 --- 0..\\\*` riflette il fatto che un torneo può contenere più partite. Tale relazione è necessaria perché la classifica viene calcolata a partire dai risultati delle partite registrate.



L’associazione tra `Torneo` e `Classifica` rappresenta il fatto che il torneo possiede una classifica corrente. La relazione `1 --- 1` è stata scelta perché, nel modello adottato, ogni torneo ha una sola classifica associata, aggiornata in base alle squadre e alle partite presenti.



L’associazione tra `Squadra` e `Giocatore` descrive la composizione della rosa di una squadra. La molteplicità è stata impostata in modo da riflettere il vincolo del dominio secondo cui ogni squadra è composta da cinque giocatori. Questa relazione è importante perché i giocatori non sono gestiti in modo indipendente dal contesto della squadra, ma come parte della sua struttura interna.



Le due associazioni tra `Partita` e `Squadra`, denominate `squadraCasa` e `squadraOspite`, rappresentano i due ruoli distinti che una squadra può assumere all’interno di una partita. Non è stata usata una sola associazione generica, perché nel dominio è importante distinguere la squadra di casa dalla squadra ospite.



L’associazione tra `Classifica` e `VoceClassifica` indica che la classifica è costituita da più voci, una per ciascuna squadra. Questa scelta consente di separare la struttura della classifica dal resto del modello e di rappresentare ogni riga come un oggetto autonomo.



Infine, l’associazione tra `VoceClassifica` e `Squadra` esprime che ogni voce della classifica è riferita a una specifica squadra. Tale relazione è necessaria per collegare il punteggio memorizzato nella voce alla squadra a cui appartiene.



Nel complesso, le associazioni introdotte cercano di rappresentare solo i legami strutturali realmente utili alla comprensione del sistema, evitando dipendenze superflue e mantenendo una separazione chiara tra le diverse responsabilità progettuali.



Diagramma di sequenza: Inserimento squadra



Questo diagramma di sequenza descrive il flusso relativo all’inserimento di una nuova squadra. L’amministratore inserisce i dati tramite l’interfaccia utente, che inoltra la richiesta al controller. Il controller crea la nuova squadra, ne inizializza i dati e delega al modello `Torneo` l’aggiunta della squadra al torneo.

Al termine dell’operazione il sistema aggiorna la classifica e restituisce un messaggio di conferma all’utente.



Diagramma di sequenza: Inserimento squadra



Questo diagramma di sequenza descrive il flusso relativo

all’inserimento di una nuova squadra. L’amministratore inserisce

i dati tramite l’interfaccia utente, che inoltra la richiesta al

controller. Il controller crea la nuova squadra, ne inizializza i dati

e delega al modello `Torneo` l’aggiunta della squadra al torneo.

Al termine dell’operazione il sistema aggiorna la classifica e

restituisce un messaggio di conferma all’utente.



\## Diagramma di sequenza: Inserimento squadra



Questo diagramma di sequenza descrive il flusso relativo all’inserimento di una nuova squadra. L’amministratore inserisce i dati tramite l’interfaccia utente, che inoltra la richiesta al controller. Il controller crea la nuova squadra, ne inizializza i dati e delega al modello `Torneo` l’aggiunta della squadra al torneo.

Al termine dell’operazione il sistema aggiorna la classifica e restituisce un messaggio di conferma all’utente.



Diagramma di sequenza: Inserimento squadra



Questo diagramma di sequenza descrive il flusso relativo all’inserimento di una nuova squadra. L’amministratore inserisce i dati tramite l’interfaccia utente, che inoltra la richiesta al controller. Il controller crea la nuova squadra, ne inizializza i dati e delega al modello `Torneo` l’aggiunta della squadra al torneo.

Al termine dell’operazione il sistema aggiorna la classifica e restituisce un messaggio di conferma all’utente.



Scelte di design



La progettazione del sistema è stata sviluppata a partire dai requisiti funzionali individuati nella fase di analisi, con l’obiettivo di definire una decomposizione del software in moduli e classi caratterizzata da alta coesione e basso accoppiamento.



In accordo con i principi di progettazione illustrati nel corso, il sistema è stato suddiviso in tre aree principali:

\- model, che contiene le classi del dominio applicativo;

\- controller, che coordina le operazioni richieste dall’interfaccia;

\- view, che rappresenta l’interfaccia utente.



Questa scelta consente di separare le responsabilità principali del sistema. In particolare, le classi del package model rappresentano le entità fondamentali del dominio del problema: Torneo, Squadra, Giocatore, Partita, Classifica e VoceClassifica. La classe Torneo rappresenta il nucleo del sistema e coordina la gestione delle squadre, delle partite e della classifica. La classe Squadra è responsabile della gestione del nome della squadra e della rosa dei giocatori. La classe Partita rappresenta i dati relativi a una singola partita, mentre la classe Classifica gestisce il calcolo e l’ordinamento delle voci di classifica.



Per ridurre l’accoppiamento tra interfaccia e logica di dominio, è stata introdotta la classe TorneoController, che svolge il ruolo di mediatore tra la view e il model. In questo modo l’interfaccia utente non accede direttamente ai dettagli interni delle classi del dominio, ma utilizza il controller come punto di accesso alle funzionalità del sistema.



La classe InterfacciaUtente rappresenta invece lo scheletro della parte di presentazione del sistema. Essa ha il compito di mostrare le schermate principali, raccogliere i dati inseriti dall’utente e mostrare messaggi di conferma o di errore. Questa scelta consente di mantenere separata la logica di interazione con l’utente dalla logica applicativa.



Dal punto di vista della coesione, le classi definite risultano sufficientemente coese perché ciascuna è dedicata a un compito specifico e ben identificabile. Dal punto di vista dell’accoppiamento, si è cercato di limitare le dipendenze tra classi diverse, facendo in modo che i moduli si scambino solo le informazioni strettamente necessarie attraverso metodi pubblici.



Un’ulteriore scelta progettuale importante riguarda la classe Classifica. Invece di memorizzare i punti direttamente all’interno della classe Squadra, si è preferito introdurre una struttura separata composta da Classifica e VoceClassifica. Questa scelta permette di mantenere separati i dati strutturali della squadra dai dati derivati dalle partite disputate, migliorando la chiarezza del modello.



Per quanto riguarda la documentazione, le interfacce pubbliche delle classi sono state annotate con commenti compatibili con Doxygen, includendo descrizioni sintetiche dello scopo delle classi e dei metodi, parametri, valori di ritorno, precondizioni e postcondizioni. Questa scelta consente di mantenere allineati codice e documentazione, come richiesto nella fase di progettazione di dettaglio.



La decomposizione adottata mira a ottenere una buona coesione interna dei moduli e un accoppiamento contenuto tra moduli differenti. Le classi del dominio presentano una coesione elevata, poiché ogni classe è dedicata a un singolo aspetto del sistema: gestione del torneo, gestione delle squadre, rappresentazione dei giocatori, rappresentazione delle partite e calcolo della classifica. L’accoppiamento è stato ridotto introducendo il controller come intermediario tra interfaccia utente e modello, evitando che la view dipenda direttamente dai dettagli implementativi delle classi del dominio.

