package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException(int limite) {
    super("Ya excedio los " + limite + " depositos diarios");
  }

}