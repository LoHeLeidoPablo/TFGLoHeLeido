package mains;

import IntfzLibreria.IntfzBiblioteca;
import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzPrincipal;

public class Biblioteca {
  public static void main(String[] args) {
    IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
    IntfzBiblioteca ventana = new IntfzBiblioteca();
    ventana.iniciar(intfzPrincipal);
  }
}
