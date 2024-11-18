package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BilleteraVirtual {
    private double saldo;
    public boolean tieneSuficienteSaldo(double monto) {
        return saldo >= monto;
    }

    // Método para restar el saldo
    public boolean restarSaldo(double monto) throws Exception {
        if (tieneSuficienteSaldo(monto)) {
            saldo -= monto; // Restar el monto
            return true;
        } else {
            throw new Exception("Saldo insuficiente para realizar la reserva, recargue su billetera .");
        }
    }
    public void sumarSaldo(double cantidad) {
        if (cantidad > 0) {
            this.saldo += cantidad;
        } else {
            throw new IllegalArgumentException("La cantidad a sumar debe ser positiva.");
        }
    }

}
