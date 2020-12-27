package mains;

import IntfzLibreria.IntfzBiblioteca;
import IntfzLibreria.IntfzLogin;
import IntfzLibreria.IntfzPrincipal;

public class BibliotecaPabloLogged {
  public static void main(String[] args) {
    IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
    IntfzBiblioteca ventana = new IntfzBiblioteca();
   IntfzLogin.id_Usuario = "Pablo";
    ventana.iniciar(intfzPrincipal);
  }
}
