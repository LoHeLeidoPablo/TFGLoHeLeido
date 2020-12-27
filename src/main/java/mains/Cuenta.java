package mains;

import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzPrincipal;

public class Cuenta {
  public static void main(String[] args) {
    IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
    IntfzCuenta ventana = new IntfzCuenta();
    ventana.iniciar(intfzPrincipal);
  }
}
