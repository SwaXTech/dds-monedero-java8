package dds.monedero.model.movimiento;

import java.time.LocalDate;

import dds.monedero.model.Cuenta;

public class Extraccion extends Movimiento{

  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() - getMonto();
  }

  @Override
  public boolean isDeposito() {
    return false;
  }
  
}
