package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

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

  IntfzInfoLibro infoLibro = new IntfzInfoLibro();

  JPanel panel = new JPanel();
  JPanel[] jPanelA = {panel};

  JTable myBiblioTable;
  JLabel lblPortada;
  DefaultTableModel modelT;
  JScrollPane scrollPane;

  String urlPortada = "";

  public IntfzBiblioteca() {}

  public void iniciar() {
    setTitle("¿Lo he leído? - Mi IntfzLibreria");
    getContentPane().setLayout(new GridLayout(1, 10));

    MenuUsuario menuUsuario = new MenuUsuario(panel, this, false);

    panel.setLayout(null);

    String[] cabecera = {
      " ",
      "Titulo",
      "Autor",
      "Saga",
      "Paginas",
      "PaginasT",
      "Capitulos",
      "CapitulosT",
      "Nota",
      "Releido"
    };
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
            // if (column == 4 || column == 6 || column == 8) return true;
            return false;
          }
        };
    myBiblioTable = new ColorEstadoTabla();
    myBiblioTable.setBounds(0, 0, 1200, 825);

    rellenarTabla();

    myBiblioTable.setModel(modelT);
    myBiblioTable.getColumnModel().getColumn(0).setMaxWidth(10);
    myBiblioTable.getColumnModel().getColumn(4).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(5).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(6).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(7).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(8).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(9).setWidth(10);
    myBiblioTable.setRowHeight(35);
    myBiblioTable.getTableHeader().setReorderingAllowed(false);
    // myBiblioTable.setShowVerticalLines(false);

    TableRowSorter<TableModel> modelOrdenado = new TableRowSorter<TableModel>(modelT);
    myBiblioTable.setRowSorter(modelOrdenado);
    myBiblioTable.setBorder(null);

    DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
    Alinear.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < myBiblioTable.getColumnCount(); i++) {
      myBiblioTable.getColumnModel().getColumn(i).setCellRenderer(Alinear);
    }
    lblPortada = new JLabel();
    lblPortada.setBounds(800 - 164, 500 - 256, 329, 512);
    lblPortada.setVisible(false);
    panel.add(lblPortada);

    scrollPane = new JScrollPane(myBiblioTable);
    scrollPane.setBounds(200, 100, 1200, 825);
    scrollPane.setBorder(null);
    panel.add(scrollPane);

    irLibro();

    JButton btnRecargar = new JButton("Recargar");
    btnRecargar.setBounds(1300, 925, 100, 20);
    panel.add(btnRecargar);
    btnRecargar.addActionListener(
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
    Dimension minimo = new Dimension();
    minimo.setSize(329, 512);
    setMinimumSize(minimo);
    setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
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

      String releido =
          regBiblio.getBoolean("Releido")
              ? "Leído " + regBiblio.getInteger("VecesReleido") + " veces"
              : "No";

      String nota = null;
      if (regBiblio.getInteger("Nota") != null) {
        int valorNota = regBiblio.getInteger("Nota");
        Float notaFloat = (float) valorNota / 10;
        nota = notaFloat.toString();
        nota = (nota.contains(".0") ? nota.substring(0, nota.indexOf(".")) : nota);
      }

      Document libro = (Document) regBiblio.get("Libro");
      String Paginas =
          libro.getInteger("Paginas") == null ? "???" : libro.getInteger("Paginas").toString();
      String Capitulos =
          libro.getInteger("Capitulos") == null ? "???" : libro.getInteger("Capitulos").toString();
      Object[] aux = {
        regBiblio.getString("Estado"),
        libro.getString("Titulo"),
        libro.getString("Autor"),
        libro.getString("Saga"),
        regBiblio.getInteger("Paginas"),
        Paginas,
        regBiblio.getInteger("Capitulos"),
        Capitulos,
        nota,
        releido
      };
      modelT.addRow(aux);
    }
  }

  public void añadirPortada() {
    try {
      URL url = new URL(urlPortada);
      Image portada = ImageIO.read(url);
      ImageIcon portadaIco = new ImageIcon(portada);
      Icon icono =
          new ImageIcon(
              portadaIco
                  .getImage()
                  .getScaledInstance(
                      lblPortada.getWidth(), lblPortada.getHeight(), Image.SCALE_DEFAULT));
      lblPortada.setIcon(icono);
      lblPortada.setBorder(null);
      repaint();

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(
          null, "Lo Sentimos, no es posible mostrar la portada de este ejemplar" + urlPortada);
    }
  }

  public void irLibro() {
    myBiblioTable.addMouseListener(
        new MouseListener() {
          int fila = 0;
          int columna = 0;

          @Override
          public void mouseClicked(MouseEvent evt) {
            fila = myBiblioTable.rowAtPoint(evt.getPoint());
            columna = myBiblioTable.columnAtPoint(evt.getPoint());
            if (evt.getClickCount() == 2) {
              if ((fila > -1) && (columna == 1)) {
                String titulo = modelT.getValueAt(fila, columna).toString();
                Document libro = collecLibro.find(eq("Titulo", titulo)).first();
                infoLibro.iniciar(libro);
                infoLibro.tabbed.setSelectedIndex(infoLibro.tabbed.getTabCount() - 3);
              } else {
                JOptionPane.showMessageDialog(
                    myBiblioTable,
                    "Por favor, selecciona el titulo del libro para acceder a la informacion y alterar el estado",
                    "Pulse en Celda Titulo",
                    JOptionPane.INFORMATION_MESSAGE);
              }
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {
            fila = myBiblioTable.rowAtPoint(e.getPoint());
            columna = myBiblioTable.columnAtPoint(e.getPoint());

            if ((fila > -1) && (columna == 1)) {
              String titulo = modelT.getValueAt(fila, columna).toString();
              Document portadaLibro = collecLibro.find(eq("Titulo", titulo)).first();
              urlPortada = portadaLibro.getString("PortadaURL");
              añadirPortada();
              lblPortada.setVisible(true);
            } else {
              JOptionPane.showMessageDialog(
                  myBiblioTable,
                  "Por favor, mantel pulsado el titulo del libro para visualizar su portada",
                  "Presione en Celda Titulo",
                  JOptionPane.INFORMATION_MESSAGE);
            }

            añadirPortada();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            lblPortada.setVisible(false);
            lblPortada.setIcon(null);
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            /*final JDialog frame = new JDialog(parentFrame, frameTitle, true);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);*/
          }

          @Override
          public void mouseExited(MouseEvent e) {}
        });
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, null, null, null, myBiblioTable, null, null);
  }

  public void crearComponentes() {}
}
