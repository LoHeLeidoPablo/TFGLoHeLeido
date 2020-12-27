package mains;

import IntfzLibreria.IntfzLogin;
import IntfzLibreria.IntfzCuenta;
import IntfzLibreria.IntfzPrincipal;

public class CuentaAdminLogged {
  public static void main(String[] args) {
    IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
    IntfzCuenta ventana = new IntfzCuenta();
   IntfzLogin.id_Usuario = "Admin";
    ventana.iniciar(intfzPrincipal);
  }
}
