package co.edu.uniquindio.reservacionAlojamientos.modelo;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String contrasena;
    private String codigoActivacion;
    private boolean activo;
}
