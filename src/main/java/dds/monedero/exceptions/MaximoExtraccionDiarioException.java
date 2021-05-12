package dds.monedero.exceptions;

public class MaximoExtraccionDiarioException extends RuntimeException {
  public MaximoExtraccionDiarioException(double limiteActual, double limite) {
    super("No puede extraer mas de $ " + limite
    + " diarios, límite: " + limiteActual);
  }
}