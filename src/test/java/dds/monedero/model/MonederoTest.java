package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void cuentaParteEnCero(){
    assertEquals(0, cuenta.getSaldo());
  }

  @Test
  void cuentaConMontoInicial(){
    
    Cuenta cuenta = new Cuenta(100000);

    assertEquals(100000, cuenta.getSaldo());
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(3856, cuenta.getSaldo());
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    cuenta.poner(90);
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    cuenta.poner(5000);
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

  @Test
  void movimiento(){
    cuenta.agregarMovimiento(LocalDate.now(), 100, true);
    cuenta.agregarMovimiento(LocalDate.now(), 50, false);
    assertEquals(cuenta.getMontoExtraidoA(LocalDate.now()), 50);
  }

}