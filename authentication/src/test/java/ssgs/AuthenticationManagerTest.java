package ssgs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationManagerTest {

    private AuthenticationManager authManager;

    @BeforeEach
    public void setUp() {
        // Inizializza il servizio di autenticazione prima di ogni test
        authManager = new AuthenticationManager();
    }

    @Test
    public void testAuthenticateWithValidCredentials() {
        // Testa l'autenticazione con credenziali valide
        assertTrue(authManager.authenticate("admin", "password"));
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        // Testa l'autenticazione con credenziali errate
        assertFalse(authManager.authenticate("admin", "wrong_password"));
    }

}
