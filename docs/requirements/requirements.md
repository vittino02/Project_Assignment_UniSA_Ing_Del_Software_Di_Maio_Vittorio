1. Scopo del Sistema
Il Sistema ha come scopo: il supportare la gestione di un torneo di Calcio a 5, consentendo di memorizzare e aggiornale le informazione relative a squadre, giocatori e partite, e consente la possibilità di visualizzare la classifica del torneo.
2.Requisiti Funzionali
Funzionalità individuali
IF-1.1 Il sistema deve consentire l’inserimento di una nuova squadra nel torneo. Priorità:[Alto]
IF-1.2 Il sistema deve consentire la modifica delle informazioni di una squadra precedentemente inserita, incluso il nome. Priorità:[Alto]
IF-1.3 Il sistema deve consentire la visualizzazione dell’elenco delle squadre registrate. Priorità:[Alto]
IF-1.4 Il sistema deve consentire l’inserimento di una nuova partita. Priorità:[Alto]
IF-1.5 Il sistema deve consentire la modifica delle informazioni di una partita precedentemente inserita. Priorità:[Alta]
IF-1.6 Il sistema deve consentire la cancellazione di una partita precedentemente inserita. Priorità:[Alto]
IF-1.7 Il sistema deve consentire la visualizzazione della classifica corrente del torneo. Priorità:[Alto]
IF-1.8 In caso di modifica di un’informazione rilevante, il sistema deve aggiornare automaticamente le informazioni collegate (Aggiornare la classifica nel caso di modifiche nelle partite). Priorità:[Alto]
Busness Flow
BF-0 Vista la mancanza di attori esterni, e visto che il software è per un organizzatore di tornei, i requisiti di tipo BF non sono necessari.
Esigenze di dati e informazioni
DF-1.1 Il sistema deve memorizzare, per ogni squadra, il nome della squadra. Priorità:[Alto]
DF-1.2 Il nome di ogni squadra deve essere diverso dai nomi delle altre squadre. Priorità:[Alto]
DF-1.3 Il sistema deve memorizzare, per ogni giocatore, nome, cognome e data di nascita. Priorità:[Medio]
DF-1.4 Ciascun giocatore deve essere identificato dalla combinazione di nome, cognome e data di nascita. Priorità:[Medio]
DF-1.5 Ogni squadra deve essere formata da 5 giocatori. Priorità:[Alto]
DF-1.6 Per ogni partita il sistema deve registrare la data in cui si è svolta la partita. Priorità:[Alto]
DF-1.7 Per ogni partita il sistema deve registrare le due squadre che hanno disputato la partita. Priorità:[Alto]
DF-1.8 Per ogni partita il sistema deve registrare il numero di goal segnati da ciascuna squadra. Priorità:[Alto]
DF-1.9 La classifica deve riportare il numero di punti di ciascuna squadra. Priorità:[Alto]
DF-1.10 La classifica deve essere ordinata in ordine decrescente di punteggio. Priorità:[Alto]
DF-1.11 Per ciascuna partita le due squadre registrate devono essere distinte. Priorità:[Alto]
DF-1.12 Il numero di goal segnati da ciascuna squadra deve essere un intero maggiore o uguale a zero. Priorità:[Alto]
Interfaccia Utente
UI-1.1 Il sistema deve fornire una schermata per la gestione delle squadre. Priorità:[Alto]
UI-1.2 Il sistema deve fornire una schermata per l’inserimento e la modifica di una squadra. Priorità:[Alto]
UI-1.3 Il sistema deve fornire una schermata per la gestione delle partite. Priorità:[Alto]
UI-1.4 Il sistema deve fornire una schermata per l’inserimento, la modifica e la cancellazione di una partita. Priorità:[Alto]
UI-1.5 Il sistema deve fornire una schermata dedicata alla visualizzazione della classifica corrente. Priorità:[Alto]
UI-1.6 In caso di inserimento di dati non validi, il sistema deve mostrare messaggi di errore chiari e comprensibili. Priorità:[Alto]
Interfacce con sistemi esterni
IS-1 Il sistema deve interagire con il file system locale per salvare l’archivio del torneo su file. Priorità:[Medio]
IS-2 Il sistema deve interagire con il file system locale per caricare un archivio precedentemente salvato. Priorità:[Medio]
3 Requisiti Non-Funzionali
Ulteriori vincoli
FC-1 Il sistema deve mantenere la coerenza dei dati dopo operazioni di modifica o cancellazione. Priorità:[Alto]
FC-2 Il sistema deve essere sufficientemente comprensibile da poter essere utilizzato tramite una GUI semplice. Priorità:[Alto]
FC-3 Il sistema deve mantenere i dati persistenti tra esecuzioni diverse dell’applicazione. Priorità:[Medio]
FC-4 Il sistema deve segnalare input non validi senza compromettere la correttezza dell’archivio. Priorità:[Alto]
4 Attori
Amministratore del torneo
5 Casi D'Uso
UC-01
Nome: Inserire squadra
Attori coinvolti: Amministratore di Torneo

Precondizioni:
-Il sistema è avviato.
-L'amministratore si trova nella sezione di gestione squadre.

Postcondizione:
-Una nuova squadra viene registrata nel sistema con i relativi giocatori.

Flusso principale:
1. L’amministratore seleziona l’opzione di inserimento squadra.
2. Il sistema richiede i dati della squadra.
3. L’amministratore inserisce il nome della squadra.
4. L’amministratore inserisce i dati dei 5 giocatori.
5. Il sistema verifica la validità dei dati inseriti.
6. Il sistema registra la nuova squadra.

Flussi di eventi alternativi:
3.A. Se il nome della squadra è già presente, il sistema segnala l’errore e richiede un nome differente.
4.A. Se i giocatori inseriti non sono esattamente 5, il sistema segnala l’errore e non completa l’operazione.
4.B. Se uno o più dati dei giocatori non sono validi, il sistema segnala l’errore e richiede la correzione.

UC-02
Nome: Modificare squadra
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- Esiste almeno una squadra registrata.

Postcondizioni:
- I dati della squadra selezionata vengono aggiornati nel sistema.

Flusso principale:
1. L’amministratore visualizza l’elenco delle squadre.
2. L’amministratore seleziona una squadra da modificare.
3. Il sistema mostra i dati correnti della squadra.
4. L’amministratore modifica uno o più dati della squadra.
5. Il sistema verifica la validità dei nuovi dati.
6. Il sistema salva le modifiche.

Flussi di eventi alternativi:
4.A. Se il nuovo nome scelto è già associato a un’altra squadra, il sistema segnala l’errore.
4.B. Se dopo la modifica la squadra non rispetta il vincolo dei 5 giocatori, il sistema segnala l’errore e non salva.

UC-03
Nome: Visualizzare elenco squadre
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.

Postcondizioni:
- L’elenco delle squadre registrate viene mostrato all’amministratore.

Flusso principale:
1. L’amministratore accede alla sezione squadre.
2. Il sistema mostra l’elenco delle squadre registrate.

Flussi di eventi alternativi:
1.A. Se non sono presenti squadre registrate, il sistema mostra un elenco vuoto o un messaggio informativo.

UC-04
Nome: Inserire partita
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- Sono presenti almeno due squadre registrate.

Postcondizioni:
- La nuova partita viene registrata.
- La classifica viene aggiornata.

Flusso principale:
1. L’amministratore seleziona l’opzione di inserimento partita.
2. Il sistema richiede i dati della partita.
3. L’amministratore inserisce data, squadre partecipanti e goal segnati.
4. Il sistema verifica la validità dei dati inseriti.
5. Il sistema registra la partita.
6. Il sistema aggiorna automaticamente la classifica.

Flussi di eventi alternativi:
4.A. Se le due squadre selezionate coincidono, il sistema segnala l’errore.
4.B. Se i goal inseriti non sono validi, il sistema segnala l’errore.
4.C. Se mancano dati obbligatori, il sistema segnala l’errore e non completa l’operazione.

UC-05
Nome: Modificare partita
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- Esiste almeno una partita registrata.

Postcondizioni:
- La partita selezionata viene aggiornata.
- La classifica viene aggiornata automaticamente.

Flusso principale:
1. L’amministratore visualizza l’elenco delle partite.
2. L’amministratore seleziona una partita da modificare.
3. Il sistema mostra i dati correnti della partita.
4. L’amministratore modifica uno o più dati della partita.
5. Il sistema verifica la validità dei nuovi dati.
6. Il sistema salva le modifiche.
7. Il sistema aggiorna automaticamente la classifica.

Flussi di eventi alternativi:
5.A. Se i nuovi dati non sono validi, il sistema segnala l’errore e non salva.

UC-06
Nome: Cancellare partita
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- Esiste almeno una partita registrata.

Postcondizioni:
- La partita selezionata viene eliminata dal sistema.
- La classifica viene aggiornata automaticamente.

Flusso principale:
1. L’amministratore visualizza l’elenco delle partite.
2. L’amministratore seleziona una partita da cancellare.
3. Il sistema richiede conferma dell’operazione.
4. L’amministratore conferma la cancellazione.
5. Il sistema elimina la partita.
6. Il sistema aggiorna automaticamente la classifica.

Flussi alternativi / eccezioni:
4.A. Se l’amministratore annulla la conferma, il sistema non elimina la partita.

UC-07
Nome: Visualizzare classifica
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.

Postcondizioni:
- La classifica corrente del torneo viene mostrata all’utente.

Flusso principale:
1. L’utente seleziona l’opzione di visualizzazione della classifica.
2. Il sistema calcola o recupera la classifica corrente.
3. Il sistema mostra la classifica ordinata in ordine decrescente di punteggio.

Flussi di eventi alternativi:
1.A. Se non sono presenti partite registrate, il sistema mostra una classifica vuota o iniziale.

UC-08
Nome: Salvare archivio
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- Sono presenti dati da salvare.

Postcondizioni:
- L’archivio del torneo viene salvato su file.

Flusso principale:
1. L’amministratore seleziona l’opzione di salvataggio.
2. Il sistema avvia la procedura di salvataggio.
3. Il sistema salva i dati del torneo su file.
4. Il sistema conferma l’avvenuto salvataggio.

Flussi di eventi alternativi:
3.A. Se si verifica un errore durante il salvataggio, il sistema segnala l’errore.

UC-09
Nome: Caricare archivio
Attore partecipante: Amministratore del torneo

Precondizioni:
- Il sistema è avviato.
- È disponibile un file di archivio valido.

Postcondizioni:
- I dati del torneo vengono caricati nel sistema.

Flusso principale:
1. L’amministratore seleziona l’opzione di caricamento.
2. Il sistema avvia la procedura di caricamento.
3. Il sistema legge il file selezionato.
4. Il sistema ricostruisce i dati del torneo.
5. Il sistema mostra i dati caricati.

Flussi di eventi alternativi:
3.A. Se il file non è valido o non è leggibile, il sistema segnala l’errore.

 classifica.
UI-1.6 Il sistema deve consentire all’utente di avviare il salvataggio e il caricamento dei dati tramite elementi della GUI.
UI-1.7 In caso di input non valido, il sistema deve mostrare un messaggio di errore chiaro e comprensibile.
=======
1\. Scopo del Sistema

Il Sistema ha come scopo: il supportare la gestione di un torneo di Calcio a 5, consentendo di memorizzare e aggiornale le informazione relative a squadre, giocatori e partite, e consente la possibilità di visualizzare la classifica del torneo.

2.Requisiti Funzionali

Funzionalità individuali

IF-1.1 Il sistema deve consentire l’inserimento di una nuova squadra nel torneo. Priorità:\[Alto]

IF-1.2 Il sistema deve consentire la modifica delle informazioni di una squadra precedentemente inserita, incluso il nome. Priorità:\[Alto]

IF-1.3 Il sistema deve consentire la visualizzazione dell’elenco delle squadre registrate. Priorità:\[Alto]

IF-1.4 Il sistema deve consentire l’inserimento di una nuova partita. Priorità:\[Alto]

IF-1.5 Il sistema deve consentire la modifica delle informazioni di una partita precedentemente inserita. Priorità:\[Alta]

IF-1.6 Il sistema deve consentire la cancellazione di una partita precedentemente inserita. Priorità:\[Alto]

IF-1.7 Il sistema deve consentire la visualizzazione della classifica corrente del torneo. Priorità:\[Alto]

IF-1.8 In caso di modifica di un’informazione rilevante, il sistema deve aggiornare automaticamente le informazioni collegate (Aggiornare la classifica nel caso di modifiche nelle partite). Priorità:\[Alto]

Busness Flow

BF-0 Vista la mancanza di attori esterni, e visto che il software è per un organizzatore di tornei, i requisiti di tipo BF non sono necessari.

Esigenze di dati e informazioni

DF-1.1 Il sistema deve memorizzare, per ogni squadra, il nome della squadra. Priorità:\[Alto]

DF-1.2 Il nome di ogni squadra deve essere diverso dai nomi delle altre squadre. Priorità:\[Alto]

DF-1.3 Il sistema deve memorizzare, per ogni giocatore, nome, cognome e data di nascita. Priorità:\[Medio]

DF-1.4 Ciascun giocatore deve essere identificato dalla combinazione di nome, cognome e data di nascita. Priorità:\[Medio]

DF-1.5 Ogni squadra deve essere formata da 5 giocatori. Priorità:\[Alto]

DF-1.6 Per ogni partita il sistema deve registrare la data in cui si è svolta la partita. Priorità:\[Alto]

DF-1.7 Per ogni partita il sistema deve registrare le due squadre che hanno disputato la partita. Priorità:\[Alto]

DF-1.8 Per ogni partita il sistema deve registrare il numero di goal segnati da ciascuna squadra. Priorità:\[Alto]

DF-1.9 La classifica deve riportare il numero di punti di ciascuna squadra. Priorità:\[Alto]

DF-1.10 La classifica deve essere ordinata in ordine decrescente di punteggio. Priorità:\[Alto]

DF-1.11 Per ciascuna partita le due squadre registrate devono essere distinte. Priorità:\[Alto]

DF-1.12 Il numero di goal segnati da ciascuna squadra deve essere un intero maggiore o uguale a zero. Priorità:\[Alto]

Interfaccia Utente

UI-1.1 Il sistema deve fornire una schermata per la gestione delle squadre. Priorità:\[Alto]

UI-1.2 Il sistema deve fornire una schermata per l’inserimento e la modifica di una squadra. Priorità:\[Alto]

UI-1.3 Il sistema deve fornire una schermata per la gestione delle partite. Priorità:\[Alto]

UI-1.4 Il sistema deve fornire una schermata per l’inserimento, la modifica e la cancellazione di una partita. Priorità:\[Alto]

UI-1.5 Il sistema deve fornire una schermata dedicata alla visualizzazione della classifica corrente. Priorità:\[Alto]

UI-1.6 In caso di inserimento di dati non validi, il sistema deve mostrare messaggi di errore chiari e comprensibili. Priorità:\[Alto]

Interfacce con sistemi esterni

IS-1 Il sistema deve interagire con il file system locale per salvare l’archivio del torneo su file. Priorità:\[Medio]

IS-2 Il sistema deve interagire con il file system locale per caricare un archivio precedentemente salvato. Priorità:\[Medio]

3 Requisiti Non-Funzionali

Ulteriori vincoli

FC-1 Il sistema deve mantenere la coerenza dei dati dopo operazioni di modifica o cancellazione. Priorità:\[Alto]

FC-2 Il sistema deve essere sufficientemente comprensibile da poter essere utilizzato tramite una GUI semplice. Priorità:\[Alto]

FC-3 Il sistema deve mantenere i dati persistenti tra esecuzioni diverse dell’applicazione. Priorità:\[Medio]

FC-4 Il sistema deve segnalare input non validi senza compromettere la correttezza dell’archivio. Priorità:\[Alto]

4 Attori

Amministratore del torneo

5 Casi D'Uso

UC-01

Nome: Inserire squadra

Attori coinvolti: Amministratore di Torneo



Precondizioni:

\-Il sistema è avviato.

\-L'amministratore si trova nella sezione di gestione squadre.



Postcondizione:

\-Una nuova squadra viene registrata nel sistema con i relativi giocatori.



Flusso principale:

1\. L’amministratore seleziona l’opzione di inserimento squadra.

2\. Il sistema richiede i dati della squadra.

3\. L’amministratore inserisce il nome della squadra.

4\. L’amministratore inserisce i dati dei 5 giocatori.

5\. Il sistema verifica la validità dei dati inseriti.

6\. Il sistema registra la nuova squadra.



Flussi di eventi alternativi:

3.A. Se il nome della squadra è già presente, il sistema segnala l’errore e richiede un nome differente.

4.A. Se i giocatori inseriti non sono esattamente 5, il sistema segnala l’errore e non completa l’operazione.

4.B. Se uno o più dati dei giocatori non sono validi, il sistema segnala l’errore e richiede la correzione.



UC-02

Nome: Modificare squadra

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- Esiste almeno una squadra registrata.



Postcondizioni:

\- I dati della squadra selezionata vengono aggiornati nel sistema.



Flusso principale:

1\. L’amministratore visualizza l’elenco delle squadre.

2\. L’amministratore seleziona una squadra da modificare.

3\. Il sistema mostra i dati correnti della squadra.

4\. L’amministratore modifica uno o più dati della squadra.

5\. Il sistema verifica la validità dei nuovi dati.

6\. Il sistema salva le modifiche.



Flussi di eventi alternativi:

4.A. Se il nuovo nome scelto è già associato a un’altra squadra, il sistema segnala l’errore.

4.B. Se dopo la modifica la squadra non rispetta il vincolo dei 5 giocatori, il sistema segnala l’errore e non salva.



UC-03

Nome: Visualizzare elenco squadre

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.



Postcondizioni:

\- L’elenco delle squadre registrate viene mostrato all’amministratore.



Flusso principale:

1\. L’amministratore accede alla sezione squadre.

2\. Il sistema mostra l’elenco delle squadre registrate.



Flussi di eventi alternativi:

1.A. Se non sono presenti squadre registrate, il sistema mostra un elenco vuoto o un messaggio informativo.



UC-04

Nome: Inserire partita

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- Sono presenti almeno due squadre registrate.



Postcondizioni:

\- La nuova partita viene registrata.

\- La classifica viene aggiornata.



Flusso principale:

1\. L’amministratore seleziona l’opzione di inserimento partita.

2\. Il sistema richiede i dati della partita.

3\. L’amministratore inserisce data, squadre partecipanti e goal segnati.

4\. Il sistema verifica la validità dei dati inseriti.

5\. Il sistema registra la partita.

6\. Il sistema aggiorna automaticamente la classifica.



Flussi di eventi alternativi:

4.A. Se le due squadre selezionate coincidono, il sistema segnala l’errore.

4.B. Se i goal inseriti non sono validi, il sistema segnala l’errore.

4.C. Se mancano dati obbligatori, il sistema segnala l’errore e non completa l’operazione.



UC-05

Nome: Modificare partita

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- Esiste almeno una partita registrata.



Postcondizioni:

\- La partita selezionata viene aggiornata.

\- La classifica viene aggiornata automaticamente.



Flusso principale:

1\. L’amministratore visualizza l’elenco delle partite.

2\. L’amministratore seleziona una partita da modificare.

3\. Il sistema mostra i dati correnti della partita.

4\. L’amministratore modifica uno o più dati della partita.

5\. Il sistema verifica la validità dei nuovi dati.

6\. Il sistema salva le modifiche.

7\. Il sistema aggiorna automaticamente la classifica.



Flussi di eventi alternativi:

5.A. Se i nuovi dati non sono validi, il sistema segnala l’errore e non salva.



UC-06

Nome: Cancellare partita

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- Esiste almeno una partita registrata.



Postcondizioni:

\- La partita selezionata viene eliminata dal sistema.

\- La classifica viene aggiornata automaticamente.



Flusso principale:

1\. L’amministratore visualizza l’elenco delle partite.

2\. L’amministratore seleziona una partita da cancellare.

3\. Il sistema richiede conferma dell’operazione.

4\. L’amministratore conferma la cancellazione.

5\. Il sistema elimina la partita.

6\. Il sistema aggiorna automaticamente la classifica.



Flussi alternativi / eccezioni:

4.A. Se l’amministratore annulla la conferma, il sistema non elimina la partita.



UC-07

Nome: Visualizzare classifica

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.



Postcondizioni:

\- La classifica corrente del torneo viene mostrata all’utente.



Flusso principale:

1\. L’utente seleziona l’opzione di visualizzazione della classifica.

2\. Il sistema calcola o recupera la classifica corrente.

3\. Il sistema mostra la classifica ordinata in ordine decrescente di punteggio.



Flussi di eventi alternativi:

1.A. Se non sono presenti partite registrate, il sistema mostra una classifica vuota o iniziale.



UC-08

Nome: Salvare archivio

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- Sono presenti dati da salvare.



Postcondizioni:

\- L’archivio del torneo viene salvato su file.



Flusso principale:

1\. L’amministratore seleziona l’opzione di salvataggio.

2\. Il sistema avvia la procedura di salvataggio.

3\. Il sistema salva i dati del torneo su file.

4\. Il sistema conferma l’avvenuto salvataggio.



Flussi di eventi alternativi:

3.A. Se si verifica un errore durante il salvataggio, il sistema segnala l’errore.



UC-09

Nome: Caricare archivio

Attore partecipante: Amministratore del torneo



Precondizioni:

\- Il sistema è avviato.

\- È disponibile un file di archivio valido.



Postcondizioni:

\- I dati del torneo vengono caricati nel sistema.



Flusso principale:

1\. L’amministratore seleziona l’opzione di caricamento.

2\. Il sistema avvia la procedura di caricamento.

3\. Il sistema legge il file selezionato.

4\. Il sistema ricostruisce i dati del torneo.

5\. Il sistema mostra i dati caricati.



Flussi di eventi alternativi:

3.A. Se il file non è valido o non è leggibile, il sistema segnala l’errore.



&#x20;classifica.

UI-1.6 Il sistema deve consentire all’utente di avviare il salvataggio e il caricamento dei dati tramite elementi della GUI.

UI-1.7 In caso di input non valido, il sistema deve mostrare un messaggio di errore chiaro e completo.
