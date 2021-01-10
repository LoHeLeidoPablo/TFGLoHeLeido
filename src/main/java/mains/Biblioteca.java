package mains;

import IntfzLibreria.IntfzBiblioteca;
import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzLogin;
import IntfzLibreria.IntfzPrincipal;

public class Biblioteca {
  public static void main(String[] args) {
    IntfzBiblioteca ventana = new IntfzBiblioteca();
    IntfzLogin.id_Usuario = "Pablo";
    ventana.iniciar();
  }
}
