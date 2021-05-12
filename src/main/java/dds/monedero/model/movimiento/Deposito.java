package dds.monedero.model.movimiento;

import java.time.LocalDate;

import dds.monedero.model.Cuenta;

public class Deposito extends Movimiento {

  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  public boolean fueDepositado(LocalDate fecha) {
    return esDeLaFecha(fecha);
  }

  public double calcularValor(Cuenta cuenta){
    return cuenta.getSaldo() + getMonto();
  }

  @Override
  public boolean isDeposito() {
    return true;
  }
  
}
