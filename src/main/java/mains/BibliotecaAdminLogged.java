package mains;

import IntfzLibreria.IntfzBiblioteca;
import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzLogin;
import IntfzLibreria.IntfzPrincipal;

public class BibliotecaAdminLogged {
  public static void main(String[] args) {
    IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
    IntfzBiblioteca ventana = new IntfzBiblioteca();
   IntfzLogin.id_Usuario = "Admin";
    ventana.iniciar(intfzPrincipal);
  }
}
