package ssgs;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthenticationManager {
    private Map<String, String> userCredentials;

    public AuthenticationManager() {
        // Inizializzazione delle credenziali degli utenti
        userCredentials = new HashMap<>();
        userCredentials.put("admin", "password");
        userCredentials.put("user", "password");
    }

    public boolean authenticate(String username, String password) {
        // Verifica se le credenziali fornite corrispondono a quelle memorizzate
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public static void main(String[] args) {
        // Creazione di un'istanza di AuthenticationManager
        AuthenticationManager authManager = new AuthenticationManager();

        // Scanner per l'input dell'utente
        try (Scanner scanner = new Scanner(System.in)) {
            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Inserisci il nome utente: ");
                String username = scanner.nextLine();
                System.out.print("Inserisci la password: ");
                String password = scanner.nextLine();
                loggedIn = authManager.authenticate(username, password);
                if (loggedIn) {
                    System.out.println("Accesso consentito");
                } else {
                    System.out.println("Credenziali non valide. Riprova");
                }
            }
        }
    }
}
