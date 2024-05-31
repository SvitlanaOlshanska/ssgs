package ssgs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArtifactList artifactList = null;
        boolean exitProgram = false;
        boolean loggedIn = false;

        AuthenticationManager authManager = new AuthenticationManager();

        try (Scanner input = new Scanner(System.in)) {
            while (!loggedIn) {
                System.out.print("Inserisci il nome utente: ");
                String nomeUtente = input.nextLine();
                System.out.print("Inserisci la password: ");
                String password = input.nextLine();

                if (authManager.authenticate(nomeUtente, password)) {
                    loggedIn = true;
                    System.out.println("Accesso consentito");
                } else {
                    System.out.println("Credenziali non valide. Riprova");
                }
            }

            // Creazione del file di testo per gli artefatti
            System.out.print("Inserisci il nome del file TXT su cui vuoi lavorare: ");
            String txtFileName = input.next();
            artifactList = new ArtifactListFile(txtFileName);

            // Creazione di un nuovo oggetto Scanner per il menu
            Scanner menuScanner = new Scanner(System.in);
            while (!exitProgram) {
                System.out.println("Menu:");
                System.out.println("1. Visualizza lista artefatti");
                System.out.println("2. Aggiungi artefatto");
                System.out.println("3. Modifica Artefatto");
                System.out.println("4. Elimina artefatto");
                System.out.println("5. Esci");

                int choice = menuScanner.nextInt();

                switch (choice) {
                    case 1:
                        displayArtifacts(artifactList);
                        break;
                    case 2:
                        addArtifact(menuScanner, artifactList);
                        break;
                    case 3:
                        editArtifact(menuScanner, artifactList);
                        break;
                    case 4:
                        deleteArtifact(menuScanner, artifactList);
                        break;
                    case 5:
                        exitProgram = true;
                        break;
                    default:
                        System.out.println("Scelta errata, riprova");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayArtifacts(ArtifactList artifactList) {
        System.out.println("Lista artefatti:");
        if (artifactList == null || artifactList.getAll().isEmpty())
            System.out.println("Vuota");
        else {
            for (Artifact artifact : artifactList.getAll()) {
                System.out.println("Artefatto ID - " + artifact.getId() + " " + artifact.getOrigin() + " "
                        + artifact.getName() + " " + artifact.getCreationPeriod());
            }
        }
    }

    private static void addArtifact(Scanner scanner, ArtifactList artifactList) {
        boolean idUnique = false;
        String id = "";

        while (!idUnique) {
            System.out.print("Inserisci id artefatto: ");
            id = scanner.next();

            if (artifactList == null || artifactList.getById(id) == null) {
                idUnique = true;
            } else {
                System.out.println("L'ID dell'artefatto è già presente. Reinserisci i dati:");
            }
        }

        System.out.print("Inserisci nome artefatto: ");
        String name = scanner.next();
        System.out.print("Inserisci origine artefatto: ");
        String origin = scanner.next();
        System.out.print("Inserisci il periodo di creazione: ");
        String creationPeriod = scanner.next();

        Artifact artifact = new Artifact(id, name, origin, creationPeriod);

        try {
            artifactList.add(artifact);
            System.out.println("Artefatto è stato aggiunto!");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage() + ". Reinserisci i dati.");
        }
    }

    private static void editArtifact(Scanner scanner, ArtifactList artifactList) {
        System.out.print("Inserisci Id artefatto che vuoi modificare: ");
        String id = scanner.next();
        Artifact existingArtifact = artifactList.getById(id);

        if (existingArtifact != null) {
            System.out.print("Modifica il nome: ");
            String name = scanner.next();
            System.out.print("Modifica l'origine: ");
            String origin = scanner.next();
            System.out.print("Modifica il periodo di creazione: ");
            String creationPeriod = scanner.next();

            Artifact updatedArtifact = new Artifact(id, name, origin, creationPeriod);
            artifactList.update(updatedArtifact);
            System.out.println("Artefatto è stato modificato!");
        } else {
            System.out.println("Artefatto con id indicato non è stato trovato");
        }
    }

    private static void deleteArtifact(Scanner scanner, ArtifactList artifactList) {
        System.out.print("Inserisci id artefatto che vuoi eliminare: ");
        String id = scanner.next();
        Artifact existingArtifact = artifactList.getById(id);

        if (existingArtifact != null) {
            artifactList.delete(id);
            System.out.println("Artefatto è stato eliminato!");
        } else {
            System.out.println("Artefatto con id indicato non è stato trovato");
        }
    }
}
