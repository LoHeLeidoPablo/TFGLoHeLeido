package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.mongodb.client.model.Filters.eq;

public class IntfzBiblioteca extends JFrame implements Interfaz {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  JPanel panel = new JPanel();
  JPanel[] jPanelA = {panel};

  JTable myBiblioTable;
  DefaultTableModel modelT;
  JScrollPane scrollPane;

  public IntfzBiblioteca() {}

  public void iniciar(JFrame intfzPrincipal) {
    setTitle("¿Lo he leído? - Mi IntfzLibreria");
    getContentPane().setLayout(new GridLayout(1, 10));
    MenuUsuario menuUsuario = new MenuUsuario(panel, this, false);

    panel.setLayout(null);

    // JTable MyBibilio
    String[] cabecera = {"Estado", "Conteo", "Titulo", "Paginas", "Capitulos"};
    modelT = new DefaultTableModel(cabecera, 0);
    myBiblioTable = new ColorEstadoTabla();
    // myBiblioTable.setBounds(200, 100, 1200, 825);
    myBiblioTable.setBounds(0, 0, 1200, 825);

    for (int i = 0; i >= cabecera.length; i++) {
      myBiblioTable
          .getColumnModel()
          .getColumn(i)
          .setMaxWidth(myBiblioTable.getWidth() / cabecera.length);
    }

    rellenarTabla();

    myBiblioTable.setModel(modelT);
    myBiblioTable.getColumnModel().getColumn(0).setMaxWidth(10);
    myBiblioTable.getColumnModel().getColumn(1).setMaxWidth(12);
    myBiblioTable.setRowHeight(35);

    scrollPane = new JScrollPane(myBiblioTable);
    scrollPane.setBounds(200, 100, 1200, 825);
    panel.add(scrollPane);

    intfzPrincipal.setState(JFrame.ICONIFIED);
    // Empaquetado, tamaño y visualizazion
    getContentPane().add(panel);
    setResizable(false);
    pack();
    setSize(1600, 1000);
    setVisible(true);
  }

  public void rellenarTabla() {

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", "Pablo")).iterator();
    int i = 0;
    while (biblioteca.hasNext()) {
      Document regBiblio = biblioteca.next();
      i++;
      Document libro = (Document) regBiblio.get("Libro");
      libro.getString("Titulo");
      Object[] aux = {
        regBiblio.getString("Estado"),
        i,
        libro.getString("Titulo"),
        regBiblio.getInteger("Paginas"),
        regBiblio.getInteger("Capitulos")
      };
      modelT.addRow(aux);
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, null, null, null, myBiblioTable, null, null);
  }

  public void crearComponentes() {}
}
