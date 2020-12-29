package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

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

  public void iniciar() {
    setTitle("¿Lo he leído? - Mi IntfzLibreria");
    getContentPane().setLayout(new GridLayout(1, 10));
    MenuUsuario menuUsuario = new MenuUsuario(panel, this, false);

    panel.setLayout(null);

    String[] cabecera = {" ", "Titulo", "Autor", "Saga", "Paginas", "Capitulos", "Nota", "Releido"};
    modelT =
        new DefaultTableModel(cabecera, 0) {
          @Override
          public Class getColumnClass(int columna) {
            if (columna > 3)
              return Integer.class; // Le dice al modelo que columnas son de tipo integer
            return String.class; // Si no, es String
          }

          @Override
          public boolean isCellEditable(int row, int column) {
            // Aquí devolvemos true o false según queramos que una celda
            // identificada por fila,columna (row,column), sea o no editable
            //  if (column == 3) return true;
            return false;
          }
        };
    myBiblioTable = new ColorEstadoTabla();
    myBiblioTable.setBounds(0, 0, 1200, 825);

    rellenarTabla();

    myBiblioTable.setModel(modelT);
    myBiblioTable.getColumnModel().getColumn(0).setMaxWidth(10);
    myBiblioTable.getColumnModel().getColumn(4).setMaxWidth(65);
    myBiblioTable.getColumnModel().getColumn(5).setMaxWidth(65);
    myBiblioTable.getColumnModel().getColumn(6).setMaxWidth(65);
    myBiblioTable.setRowHeight(35);
    // myBiblioTable.setRowSelectionAllowed(false);
    //  myBiblioTable.setColumnSelectionAllowed(false);
    myBiblioTable.getTableHeader().setReorderingAllowed(false);

    TableRowSorter<TableModel> modelOrdenado = new TableRowSorter<TableModel>(modelT);
    // modelOrdenado.getComparator(0);
    myBiblioTable.setRowSorter(modelOrdenado);
    // myBiblioTable.setCellSelectionEnabled(false);
    myBiblioTable.setBorder(null);

    DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
    Alinear.setHorizontalAlignment(SwingConstants.CENTER); // .LEFT .RIGHT .CENTER
    for (int i = 0; i < myBiblioTable.getColumnCount(); i++) {
      myBiblioTable.getColumnModel().getColumn(i).setCellRenderer(Alinear);
    }

    /*











    */

    scrollPane = new JScrollPane(myBiblioTable);
    scrollPane.setBounds(200, 100, 1200, 825);
    scrollPane.setBorder(null);
    panel.add(scrollPane);

    JButton recargar = new JButton("Actualizar");
    recargar.setBounds(500, 75, 100, 20);
    panel.add(recargar);
    recargar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

            rellenarTabla();
          }
        });

    // Empaquetado, tamaño y visualizazion
    getContentPane().add(panel);
    setResizable(false);
    pack();
    setSize(1600, 1000);
    setVisible(true);
  }

  public void rellenarTabla() {
    modelT.setRowCount(0);
    MongoCursor<Document> biblioteca =
        collecDetBiblio
            .find(eq("Usuario", "Pablo"))
            .sort(and(ascending("Estado"), ascending("titloOrden")))
            .iterator();
    int i = 0;
    while (biblioteca.hasNext()) {
      Document regBiblio = biblioteca.next();
      i++;
      String releido = "No";

      if (regBiblio.getBoolean("Releido"))
        releido = "Si -- Leído " + regBiblio.getInteger("VecesReleido") + " veces";

      Document libro = (Document) regBiblio.get("Libro");
      libro.getString("Titulo");
      Object[] aux = {
        regBiblio.getString("Estado"),
        libro.getString("Titulo"),
        libro.getString("Autor"),
        libro.getString("Saga"),
        regBiblio.getInteger("Paginas"),
        regBiblio.getInteger("Capitulos"),
        regBiblio.getDouble("Nota"),
        releido
      };
      modelT.addRow(aux);
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, null, null, null, myBiblioTable, null, null);
  }

  public void crearComponentes() {}
}
