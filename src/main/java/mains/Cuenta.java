package mains;

import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzLogin;

public class Cuenta {
  public static void main(String[] args) {
    IntfzCuenta ventana = new IntfzCuenta();
    IntfzLogin.id_Usuario = "Pablo";
    ventana.iniciar();
  }
}
