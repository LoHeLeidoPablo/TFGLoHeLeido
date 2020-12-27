package mains;

import IntfzLibreria.IntfzActLibro;
import IntfzLibreria.IntfzLogin;
import org.bson.Document;

import java.util.Date;

public class ActLibro {
  public static void main(String[] args) {
    IntfzActLibro ventana = new IntfzActLibro();
    IntfzLogin.id_Usuario = "Admin";
    Document libro = new Document();
    libro.put("ISBN", "5558882220");
    libro.put("Titulo", "Titulo Actualizacion");
    libro.put("Autor", "Autor Actualizacion");
    libro.put("Saga", "Saga Actualizacion");
    libro.put("Tomo", 5);
    libro.put("Paginas", 555);
    libro.put("Capitulos", 55);
    libro.put("f_publicacion", new Date());
    libro.put("Generos", null);
    libro.put(
        "Sinopsis",
        "PRUEBAPRU EBAP RUEBAPRU  EBAPRUEBAPRUEBAPRUE    BAPRUEBAPRUEBAPRUEBAPRUEBAPRU  EBAPRUEBAPRUEBAPR UEBAPRUEBAPRUEBAPRUEBA PRU BAPRUEBAPRUEBAPRUEB APRUEBA PRUEBAPRUEBA RUEBAPRUEBAPRU EBAPRUEBAPRUEBAP RUEBAPRUEBAPRUEBAPR UEBAPRUE BAPRUEBAPRUEB APRUEBAPRUEB  APRUEBAPRUE BAPRUEBAPRUEBAPRUEBA PRUEBAPRUEBAPRUE BAPRUEBAPRUEBAPR UEBAPRUEBAPR UEBAPRUEBAPRUEBA PRUEBAPRUEBAPRUEBAPRU EBAPRUEBAPRUEBA");
    libro.put("f_registro", new Date());
    libro.put("PortadaURL", null);
    ventana.iniciar(libro);
  }
}
