package co.edu.uniquindio.reservacionAlojamientos;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservacionAlojamientosTest {

    private ReservaPrincipal reservaPrincipal;

    @BeforeEach
    public void setUp() {
        reservaPrincipal = new ReservaPrincipal();
    }

    @Test
    public void testRegistrarUsuario() {
        assertDoesNotThrow(() -> {
            boolean result = reservaPrincipal.registrarUsuario("123", "wilder reyes", "3001234567", "wilder.reyes@hotmail.com", "123456");
            assertTrue(result);
        });
    }

    @Test
    public void testIniciarSesion() {
        assertDoesNotThrow(() -> {
            reservaPrincipal.registrarUsuario("123", "wilder reyes", "3001234567", "wilder.reyes@hotmail.com", "123456");
            reservaPrincipal.activarCuenta("wilder.reyes@hotmail.com", "AC-1234567890");
            Usuario usuario = reservaPrincipal.iniciarSesion("wilder.reyes@hotmail.com", "123456");
            assertNotNull(usuario);
        });
    }

    @Test
    public void testEnviarNotificacionesRegistro() {
        assertDoesNotThrow(() -> {
            reservaPrincipal.enviarNotificacionesRegistro("wilder.reyes@hotmail.com", "AC-1234567890");
        });
    }

    @Test
    public void testObtenerUsuarioEmail() throws Exception {
        String email = "bookyourestayalojamientos@gmail.com";
        reservaPrincipal.registrarUsuario("111111", "Pedro Florez", "3102584256", email, "12345678");
        Usuario usuario = reservaPrincipal.obtenerUsuarioEmail(email);
        assertNotNull(usuario);
        assertEquals(email, usuario.getEmail());
    }

    @Test
    public void testObtenerUsuarioCedula() throws Exception {
        String cedula = "12345";
        reservaPrincipal.registrarUsuario("12345", "Test User 3", "555555555", cedula, "12345678");
        Usuario usuario = reservaPrincipal.obtenerUsuarioCedula(cedula);
        assertNotNull(usuario);
        assertEquals(cedula, usuario.getEmail());
    }

    @Test
    public void registroUsuarioTest() {

        ReservaPrincipal reservaPrincipal = new ReservaPrincipal();

        assertDoesNotThrow(() -> {
            reservaPrincipal.registrarUsuario(
                    "12345",
                    "wilder reyes",
                    "1111", "wilder.reyes@hotmail.com",
                    "1111"
            );
        });

        assertThrows(Exception.class, () -> {
            reservaPrincipal.registrarUsuario(
                    "12345",
                    "wilder reyes",
                    "1111", "wilder.reyes@hotmail.com",
                    "1111"
            );
        });
    }
    @Test
    public void activarCuentaTest() {
        assertDoesNotThrow(() -> {
            reservaPrincipal.registrarUsuario("123", "wilder reyes", "3001234567", "wilder.reyes@hotmail.com", "123456");
            reservaPrincipal.activarCuenta("wilder.reyes@hotmail.com", "AC-1234567890");
            boolean usuario = reservaPrincipal.activarCuenta("wilder.reyes@hotmail.com", "123456");
            assertNotNull(usuario);
        });
    }
}

