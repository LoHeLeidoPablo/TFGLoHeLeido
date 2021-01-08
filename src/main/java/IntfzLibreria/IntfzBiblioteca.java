package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import static IntfzLibreria.IntfzLogin.id_Usuario;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
// import static java.awt.event.KeyEvent.VK_F5;

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

  JPanel panelBiblioteca = new JPanel();
  JPanel[] jPanelA = {panelBiblioteca};

  JTable myBiblioTable;
  DefaultTableModel modelT;
  JScrollPane scrollPane;
  JLabel lblPortada;

  JLabel lblLeyendo = new JLabel();
  JLabel lblLeidos = new JLabel();
  JLabel lblAbandonados = new JLabel();
  JLabel lblQuieroLeer = new JLabel();
  JLabel lblTotal = new JLabel();

  int conteoLeyendo = 0;
  int conteoLeido = 0;
  int conteoAbandonado = 0;
  int conteoQuieroLeer = 0;
  int conteoTotal = 0;

  public IntfzBiblioteca() {
    cambioTema("Papiro");
    setIconImage(new ImageIcon("src/main/resources/appIcon.png").getImage());
    // recargar();
  }

  public void iniciar() {
    setTitle("¿Lo he leído? - Mi IntfzLibreria");
    getContentPane().setLayout(new GridLayout(1, 10));

    MenuUsuario menuUsuario = new MenuUsuario(panelBiblioteca, this, false);

    panelBiblioteca.setLayout(null);

    lblPortada = new JLabel();
    lblPortada.setBounds(636, 244, 329, 512);
    lblPortada.setVisible(false);
    panelBiblioteca.add(lblPortada);

    String[] cabecera = {
      " ",
      "Portada",
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
    myBiblioTable.setModel(modelT);
    myBiblioTable.getColumnModel().getColumn(0).setMaxWidth(10);
    myBiblioTable.getColumnModel().getColumn(1).setMaxWidth(50);
    myBiblioTable.getColumnModel().getColumn(5).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(6).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(7).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(8).setMaxWidth(70);
    myBiblioTable.getColumnModel().getColumn(9).setMaxWidth(70);
    myBiblioTable.setRowHeight(35);
    myBiblioTable.getTableHeader().setReorderingAllowed(false);
    myBiblioTable.setBackground(new Color(232, 218, 189));
    myBiblioTable.setShowVerticalLines(false);

    // Al permitir la ordenacion por columnas el proceso de mostrado de informacion de libro no
    // realiza correctamente su cometido
    /*TableRowSorter<TableModel> modelOrdenado = new TableRowSorter<TableModel>(modelT);
    myBiblioTable.setRowSorter(modelOrdenado);*/
    myBiblioTable.setBorder(null);

    DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
    Alinear.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < myBiblioTable.getColumnCount(); i++) {
      myBiblioTable.getColumnModel().getColumn(i).setCellRenderer(Alinear);
    }
    rellenarTabla();
    leyendaTabla();
    scrollPane = new JScrollPane(myBiblioTable);
    scrollPane.setBounds(50, 100, 1500, 825);
    myBiblioTable.setBounds(0, 0, scrollPane.getWidth(), scrollPane.getWidth());
    scrollPane.setBackground(new Color(232, 218, 189));
    scrollPane.setBorder(null);
    panelBiblioteca.add(scrollPane);

    irLibro();

    JButton btnRecargar = new JButton("Recargar");
    btnRecargar.setBounds(1450, 925, 100, 20);
    panelBiblioteca.add(btnRecargar);
    btnRecargar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            rellenarTabla();
            leyendaTabla();
            menuUsuario.cerrarBusqueda();
          }
        });

    // recargar();

    // Empaquetado, tamaño y visualizazion
    getContentPane().add(panelBiblioteca);
    setResizable(false);
    pack();
    Dimension minimo = new Dimension();
    minimo.setSize(329, 512);
    setMinimumSize(minimo);
    setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
    setSize(1600, 1000);
    setVisible(true);
  }

  /* private void recargar() {
    EventoRecargaF5 evtReF5 = new EventoRecargaF5();
    addKeyListener(evtReF5);
  }

  class EventoRecargaF5 implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

      System.out.println("Prueba de funcionamiento T");
      // rellenarTabla();
      leyendaTabla();

    }

    @Override
    public void keyPressed(KeyEvent e) {
      System.out.println("Prueba de funcionamiento P");
      // rellenarTabla();
      leyendaTabla();
    }

    @Override
    public void keyReleased(KeyEvent e) {
      System.out.println("Prueba de funcionamiento R");
      // rellenarTabla();
      leyendaTabla();
    }
  }*/

  public void rellenarTabla() {
    modelT.setRowCount(0);
    MongoCursor<Document> biblioteca =
        collecDetBiblio
            .find(eq("Usuario", id_Usuario))
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
        libro.getString("PortadaURL"),
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

  public void leyendaTabla() {

    conteoLeyendo =
        (int)
            collecDetBiblio.countDocuments(and(eq("Estado", "Leyendo"), eq("Usuario", id_Usuario)));
    conteoLeido =
        (int)
            collecDetBiblio.countDocuments(
                and(eq("Estado", "Terminado"), eq("Usuario", id_Usuario)));
    conteoAbandonado =
        (int)
            collecDetBiblio.countDocuments(
                and(eq("Estado", "XinTerminar"), eq("Usuario", id_Usuario)));
    conteoQuieroLeer =
        (int)
            collecDetBiblio.countDocuments(
                and(eq("Estado", "Pendiente"), eq("Usuario", id_Usuario)));

    conteoTotal = conteoLeyendo + conteoLeido + conteoAbandonado + conteoQuieroLeer;

    lblLeyendo.setText("Leyendo: " + conteoLeyendo);
    lblLeidos.setText("Leidos: " + conteoLeido);
    lblAbandonados.setText("Abandonados: " + conteoAbandonado);
    lblQuieroLeer.setText("Quiero Leer: " + conteoQuieroLeer);
    lblTotal.setText("Total de Libros en la biblioteca: " + conteoTotal);

    lblLeyendo.setBounds(125, 925, 100, 20);

    lblQuieroLeer.setBounds(
        lblLeyendo.getX() + lblLeyendo.getWidth() + 25,
        lblLeyendo.getY(),
        lblLeyendo.getWidth(),
        lblLeyendo.getHeight());

    lblLeidos.setBounds(
        lblQuieroLeer.getX() + lblLeyendo.getWidth() + 50,
        lblLeyendo.getY(),
        lblLeyendo.getWidth(),
        lblLeyendo.getHeight());

    lblAbandonados.setBounds(
        lblLeidos.getX() + lblQuieroLeer.getWidth() + 25,
        lblLeyendo.getY(),
        lblLeyendo.getWidth(),
        lblLeyendo.getHeight());

    lblTotal.setBounds(
        lblAbandonados.getX() + lblLeyendo.getWidth() + 50,
        lblLeyendo.getY(),
        200,
        lblLeyendo.getHeight());

    lblLeyendo.setForeground(new Color(64, 161, 67));
    lblLeidos.setForeground(Color.BLUE);
    lblAbandonados.setForeground(Color.RED);
    lblQuieroLeer.setForeground(Color.GRAY);

    panelBiblioteca.add(lblLeyendo);
    panelBiblioteca.add(lblLeidos);
    panelBiblioteca.add(lblAbandonados);
    panelBiblioteca.add(lblQuieroLeer);
    panelBiblioteca.add(lblTotal);
  }

  public void añadirPortada(String urlPortada) {
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
              if ((fila > -1) && (columna == 2)) {
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
              añadirPortada(modelT.getValueAt(fila, columna).toString());
              lblPortada.setVisible(true);
            }
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            lblPortada.setVisible(false);
            lblPortada.setIcon(null);
          }

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, null, null, null, null, myBiblioTable, null, null, null);
  }

  public void crearComponentes() {}
}
