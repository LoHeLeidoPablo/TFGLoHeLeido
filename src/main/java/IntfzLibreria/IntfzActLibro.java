package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.toedter.calendar.JDateChooser;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class IntfzActLibro extends JFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibros = DDBB.getCollection("Libro");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  IntfzInfoLibro intfzInfoLibro = new IntfzInfoLibro();

  private JPanel panelActLibro = new JPanel();
  private JPanel panelGenero = new JPanel();

  private JLabel lblPortada = new JLabel("Portada:");
  private JLabel lblISBN = new JLabel("ISBN*");
  private JLabel lblTitulo = new JLabel("Titulo*");
  private JLabel lblAutor = new JLabel("Autor*");
  private JLabel lblColeccion = new JLabel("Saga");
  private JLabel lblNColeccion = new JLabel("Tomo");
  private JLabel lblPaginas = new JLabel("Paginas");
  private JLabel lblCapitulos = new JLabel("Capitulos");
  private JLabel lblPublicacion = new JLabel("F. Publicación");
  private JLabel lblGeneros = new JLabel("Genero");
  private JLabel lblResumen = new JLabel("Resumen");
  private JLabel lblPortadaURL = new JLabel("URL de la Portada*");

  private JTextField txtISBN = new JTextField();
  private JTextField txtTitulo = new JTextField();
  private JTextField txtAutor = new JTextField();
  private JTextField txtColeccion = new JTextField();
  private JTextArea txtASinopsis = new JTextArea();
  private JTextField txtURL = new JTextField();
  private JSpinner spCapitulos = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
  private JSpinner spPaginas = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
  private JSpinner spNColeccion = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));

  private JCheckBox ch1 = new JCheckBox("Aventuras");
  private JCheckBox ch2 = new JCheckBox("Autobiografía");
  private JCheckBox ch3 = new JCheckBox("Ciencia Ficcion");
  private JCheckBox ch4 = new JCheckBox("Fantasia");
  private JCheckBox ch5 = new JCheckBox("Historica");
  private JCheckBox ch6 = new JCheckBox("Infantil");
  private JCheckBox ch7 = new JCheckBox("Romantica");
  private JCheckBox ch8 = new JCheckBox("Misterio");
  private JCheckBox ch9 = new JCheckBox("Negra");
  private JCheckBox ch10 = new JCheckBox("Policiaca");
  private JCheckBox ch11 = new JCheckBox("Comic/Manga");
  private JCheckBox ch12 = new JCheckBox("Otros");

  private JButton btnUpdateLibro = new JButton("Actualizar Libro");
  private JDateChooser datePublicacion = new JDateChooser();
  private JScrollPane scrollPane = new JScrollPane(txtASinopsis);

  JCheckBox[] jCheckBoxA = {ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10, ch11, ch12};
  String isbn = new String();
  Date fecha_reg = new Date();
  String usuario_reg = "Admin";

  JComponent[] jComponentA = {
    panelGenero,
    lblPortada,
    lblISBN,
    lblTitulo,
    lblAutor,
    lblColeccion,
    lblResumen,
    lblPublicacion,
    lblISBN,
    lblCapitulos,
    lblPaginas,
    lblNColeccion,
    lblPortadaURL,
    txtTitulo,
    txtAutor,
    txtISBN,
    txtColeccion,
    spCapitulos,
    spPaginas,
    spNColeccion,
    scrollPane,
    txtURL,
    btnUpdateLibro,
    datePublicacion
  };

  JLabel[] jLabelA = {
    lblPortada,
    lblTitulo,
    lblAutor,
    lblColeccion,
    lblNColeccion,
    lblPaginas,
    lblCapitulos,
    lblPublicacion,
    lblGeneros,
    lblResumen,
    lblPortadaURL
  };
  JPanel[] jPanelA = {panelActLibro, panelGenero};
  JTextField[] jTextFieldA = {txtISBN, txtTitulo, txtAutor, txtColeccion, txtURL};
  JButton[] jButtonA = {btnUpdateLibro};
  JSpinner[] jSpinnerA = {spNColeccion, spCapitulos, spPaginas};

  /**
   * Esta clase se nos muestra una interfaz que nos permite actualizar la informacion de los libros
   * que esta introducida incorrectamente
   */
  public IntfzActLibro() {
    this.setResizable(false);
    cambioTema("Papiro");
    setIconImage(new ImageIcon("src/main/resources/appIcon.png").getImage());
  }

  public void iniciar(Document actualizarLibro) {
    setTitle("Actualizar libro - ¿Lo he leído?");
    getContentPane().setLayout(new GridLayout(1, 10));
    crearComponentes();

    panelActLibro.setLayout(null);
    panelGenero.setLayout(null);

    lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
    lblPortada.setBorder(BorderFactory.createLineBorder(Color.black));
    lblPortada.setBounds(10, 30, 329, 512);
    btnUpdateLibro.setBounds(10, 550, 328, 30);

    lblISBN.setBounds(350, 45, 50, 20);
    txtISBN.setBounds(400, 45, 525, 20);
    lblTitulo.setBounds(350, 80, 50, 20);
    txtTitulo.setBounds(400, 80, 525, 20);
    lblAutor.setBounds(350, 115, 50, 20);
    txtAutor.setBounds(400, 115, 525, 20);
    lblColeccion.setBounds(350, 150, 50, 20);
    txtColeccion.setBounds(400, 150, 525, 20);

    lblNColeccion.setBounds(350, 185, 50, 20);
    spNColeccion.setBounds(400, 185, 45, 20);
    lblPaginas.setBounds(460, 185, 50, 20);
    spPaginas.setBounds(520, 185, 50, 20);
    lblCapitulos.setBounds(585, 185, 53, 20);
    spCapitulos.setBounds(650, 185, 45, 20);
    lblPublicacion.setBounds(715, 185, 100, 20);
    datePublicacion.setBounds(810, 185, 115, 20);

    panelGenero.setBounds(350, 215, 575, 85);
    panelGenero.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

    lblGeneros.setBounds(256, 4, 52, 15);
    panelGenero.add(lblGeneros);

    ch1.setBounds(5, 20, 134, 20);
    ch2.setBounds(5, 40, 134, 20);
    ch3.setBounds(5, 60, 134, 20);
    ch4.setBounds(140, 20, 134, 20);
    ch5.setBounds(140, 40, 134, 20);
    ch6.setBounds(140, 60, 134, 20);
    ch7.setBounds(275, 20, 134, 20);
    ch8.setBounds(275, 40, 134, 20);
    ch9.setBounds(275, 60, 134, 20);
    ch10.setBounds(410, 20, 134, 20);
    ch11.setBounds(410, 40, 134, 20);
    ch12.setBounds(410, 60, 134, 20);

    lblResumen.setBounds(350, 310, 100, 15);
    scrollPane.setBounds(350, 325, 575, 217);
    txtASinopsis.setBounds(0, 0, scrollPane.getWidth(), scrollPane.getHeight());
    scrollPane.setBackground(panelActLibro.getBackground());

    lblPortadaURL.setBounds(350, 550, 115, 30);
    txtURL.setBounds(465, 550, 460, 30);

    txtASinopsis.setLineWrap(true);
    txtASinopsis.setWrapStyleWord(true);
    txtASinopsis.setEditable(true);

    getContentPane().add(panelActLibro);

    mostrarInfoLibro(actualizarLibro);
    insertarP();
    vaciarURL();
    actualizacion();

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(950, 635);
    setVisible(true);
  }

  // Rellenamos la informacion del libro que queremos modificar
  public void mostrarInfoLibro(Document libro) {
    txtISBN.setText(libro.getString("ISBN"));
    isbn = libro.getString("ISBN");
    txtTitulo.setText(libro.getString("Titulo"));

    setTitle(
        txtTitulo == null ? "Interfaz de Actualización" : "Actualizar: " + txtTitulo.getText());

    txtAutor.setText(libro.getString("Autor"));
    txtColeccion.setText(libro.getString("Saga"));
    spNColeccion.setValue(libro.getInteger("Tomo"));
    if (libro.getInteger("Paginas") != null) spPaginas.setValue(libro.getInteger("Paginas"));
    if (libro.getInteger("Capitulos") != null) spCapitulos.setValue(libro.getInteger("Capitulos"));
    datePublicacion.setDate(libro.getDate("f_publicacion"));

    List<Document> lstGeneros = (List<Document>) libro.get("Generos");
    for (int k = 0, i = 0; k < jCheckBoxA.length; k++) {
      if (lstGeneros.contains(jCheckBoxA[k].getText())) {
        jCheckBoxA[k].setSelected(true);
        i++;
        if (i == lstGeneros.size()) break;
      }
    }
    txtASinopsis.setText(libro.getString("Sinopsis"));
    txtURL.setText(libro.getString("PortadaURL"));
    fecha_reg = libro.getDate("f_registro");
    usuario_reg = libro.getString("creadorDelRegistro");

    añadirPortada();
  }

  // Este metodo guarda los cambios de la informacion actualizada
  public void actualizacion() {
    btnUpdateLibro.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            sinEspacios();
            if (obligatorios() == true) {
              Integer Tomo = (Integer) spNColeccion.getValue();
              Integer Capitulo;
              Integer Pagina;

              Capitulo = spCapitulos.getValue().equals(0) ? null : (Integer) spCapitulos.getValue();
              Pagina = spPaginas.getValue().equals(0) ? null : (Integer) spPaginas.getValue();

              ArrayList<String> valoresCB = new ArrayList<String>();
              for (JCheckBox jCheckBox : jCheckBoxA) {
                if (jCheckBox.isSelected()) valoresCB.add(jCheckBox.getText());
              }
              try {
                DeleteResult del = collecLibros.deleteOne(eq("ISBN", isbn));
                Document libro = new Document();
                libro.put("ISBN", txtISBN.getText());
                libro.put("Titulo", txtTitulo.getText());
                libro.put("Autor", txtAutor.getText());
                libro.put("Saga", txtColeccion.getText());
                libro.put("Tomo", Tomo);
                libro.put("Paginas", Pagina);
                libro.put("Capitulos", Capitulo);
                libro.put("f_publicacion", datePublicacion.getDate());
                libro.put("Generos", valoresCB);
                libro.put("Sinopsis", txtASinopsis.getText());
                libro.put("f_registro", fecha_reg);
                libro.put("PortadaURL", txtURL.getText());
                libro.put("creadorDelRegistro", usuario_reg);
                collecLibros.insertOne(libro);
                intfzInfoLibro.iniciar(libro);
                mensajeEmergente(1);
                dispose();
              } catch (Exception ex) {
                mensajeEmergente(2);
              }
            }
          }
        });
  }

  // Comprueba que los datos obligatorios sigan se mantengan
  public boolean obligatorios() {
    int i = 0;
    if (txtISBN.getText().length() < 10 | txtISBN.getText().length() > 13) i++;
    if (txtTitulo.getText().isEmpty()) i++;
    if (txtAutor.getText().isEmpty()) i++;
    if (txtColeccion.getText().isEmpty()) {
      txtColeccion.setText(txtTitulo.getText());
      if (spNColeccion.getValue().equals(0)) spNColeccion.setValue(spNColeccion.getNextValue());
    }
    if (spPaginas.getValue().equals(0) & spCapitulos.getValue().equals(0)) i++;
    if (lblPortada.getIcon() == null) {
      txtURL.setText("https://edit.org/images/cat/portadas-libros-big-2019101610.jpg");
    }
    if (i > 0) {
      mensajeEmergente(3);
      return false;
    }
    return true;
  }

  // Este metodo lo usamos para evitar que se nos cuelen espacios indeseados en los campos
  public void sinEspacios() {
    txtISBN.setText(txtISBN.getText().trim());
    txtTitulo.setText(txtTitulo.getText().trim());
    txtAutor.setText(txtAutor.getText().trim());
    txtColeccion.setText(txtColeccion.getText().trim());
    txtURL.setText(txtURL.getText().trim());
  }

  public void crearComponentes() {
    for (JComponent jComponent : jComponentA) {
      panelActLibro.add(jComponent);
    }
    for (JCheckBox jCheckBox : jCheckBoxA) {
      panelGenero.add(jCheckBox);
    }
  }

  public void insertarP() {
    EscuchaPortada insertarP = new EscuchaPortada();
    javax.swing.text.Document url = txtURL.getDocument();
    url.addDocumentListener(insertarP);
  }

  private class EscuchaPortada implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
      añadirPortada();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      lblPortada.setIcon(null);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      // Este no es necesario para el funcionamiento de la app
    }
  }

  public void añadirPortada() {
    try {
      URL url = new URL(txtURL.getText());
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
          null, "Error: No se ha podido abrir la imagen de la URL => " + txtURL.getText());
    }
  }

  public void vaciarURL() {
    lblPortadaURL.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            txtURL.setText("");
          }
        });
  }

  public void mensajeEmergente(int mensaje) {
    if (mensaje == 1) {
      JOptionPane.showMessageDialog(
          null,
          "Libro actualizado Correctamente",
          "Actualización Completada",
          JOptionPane.INFORMATION_MESSAGE);
    } else if (mensaje == 2) {
      JOptionPane.showMessageDialog(
          null,
          "Ha ocurrido un fallo durante la actualización, porfavor intentelo de nuevo",
          "Actualización Fallido",
          JOptionPane.ERROR_MESSAGE);

    } else if (mensaje == 3) {
      JOptionPane.showMessageDialog(
          null,
          "El ISBN, el Titulo, el Autor, y las Paginas o los Capitulos son campos obligatorios",
          "Faltan Campos Obligatorios",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(
        color, jPanelA, jLabelA, jTextFieldA, jButtonA, jCheckBoxA, null, null, null, jSpinnerA);
  }
}
