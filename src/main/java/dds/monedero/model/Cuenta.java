package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.model.movimiento.Deposito;
import dds.monedero.model.movimiento.Extraccion;
import dds.monedero.model.movimiento.Movimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
    validarMonto(cuanto);
    validarCantidadDepositos();

    new Deposito(LocalDate.now(), cuanto).agregateA(this);
  }

  public void sacar(double cuanto) {
    validarMonto(cuanto);
    validarSaldo(cuanto);
    validarLimite(cuanto);
    new Extraccion(LocalDate.now(), cuanto).agregateA(this);
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(extraccionDeLaFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  private Predicate<Movimiento> extraccionDeLaFecha(LocalDate fecha) {
    return movimiento -> !movimiento.isDeposito() && movimiento.esDeLaFecha(fecha);
  }

  
  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  private long cantidadDeDepositos() {
    return getMovimientos().stream().filter(Movimiento::isDeposito).count();
  }

  private void validarCantidadDepositos() {
    if (cantidadDeDepositos() >= 3) {
      throw new MaximaCantidadDepositosException(3);
    }
  }

  private void validarLimite(double cuanto) {
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(1000, limite);
    }
  }

  private void validarSaldo(double cuanto) {
    if (getSaldo() - cuanto < 0) throw new SaldoMenorException(getSaldo());
  }

  private void validarMonto(double monto){
    if(monto < 0) throw new MontoNegativoException(monto);
  }

}
