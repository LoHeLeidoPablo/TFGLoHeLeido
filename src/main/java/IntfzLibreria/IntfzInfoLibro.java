package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Updates.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IntfzInfoLibro extends JFrame implements Interfaz {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetPrestamo = DDBB.getCollection("DetallesPrestamo");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  JPanel panel = new JPanel();
  JPanel panelGenero = new JPanel();
  JPanel panelTecnico = new JPanel();
  JPanel panelEstado = new JPanel();
  JPanel panelSaga = new JPanel();
  JPanel panelAutor = new JPanel();
  JTabbedPane tabbed = new JTabbedPane();

  JLabel lblPortada = new JLabel("Portada");
  JLabel lblTitlo = new JLabel("Titulo");
  JLabel lblAutor = new JLabel("Autor");
  JLabel lblGeneros = new JLabel("Genero");
  JLabel lblResumen = new JLabel("Resumen");
  JTextArea txtASinopsis = new JTextArea(loreIpsum());
  JScrollPane scrollPaneResumen = new JScrollPane(txtASinopsis);

  JCheckBox ch1 = new JCheckBox("Aventuras");
  JCheckBox ch2 = new JCheckBox("Autobiografía");
  JCheckBox ch3 = new JCheckBox("Ciencia Ficcion");
  JCheckBox ch4 = new JCheckBox("Fantasia");
  JCheckBox ch5 = new JCheckBox("Historica");
  JCheckBox ch6 = new JCheckBox("Infantil");
  JCheckBox ch7 = new JCheckBox("Romantica");
  JCheckBox ch8 = new JCheckBox("Misterio");
  JCheckBox ch9 = new JCheckBox("Negra");
  JCheckBox ch10 = new JCheckBox("Policiaca");
  JCheckBox ch11 = new JCheckBox("Comic/Manga");
  JCheckBox ch12 = new JCheckBox("Otros");
  List<Document> lstGeneros;

  JLabel lblISBN = new JLabel("ISBN: ");
  JLabel lblCapitulos = new JLabel("Capitulos:");
  JLabel lblPaginas = new JLabel("Paginas:");
  JLabel lblColeccion = new JLabel("Saga: ");
  JLabel lblPublicacion = new JLabel("Fecha Publicacion:");

  JLabel lblEstado = new JLabel("Estado:");
  JLabel lblCapsLeidos = new JLabel("Caps leídos:");
  JLabel lblPagsLeidas = new JLabel("Pags leídas:");
  JLabel lblNota = new JLabel("Nota:");
  JLabel lblVecesRele = new JLabel("Veces");

  String[] estados = {"Sin Añadir", "Leyendo", "Leído", "Abandonado", "Quiero Leer"};
  JComboBox<String> jcbEstados = new JComboBox<String>(estados);
  JCheckBox jchReleido = new JCheckBox("Releido");
  JSpinner spVecesRele = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
  int valMaximoCaps = 50;
  JSpinner spCapL = new JSpinner(new SpinnerNumberModel(0, 0, valMaximoCaps, 1));
  JLabel lblCapTotales = new JLabel("/" + valMaximoCaps);
  int valMaximoPags = 500;
  JSpinner spPagL = new JSpinner(new SpinnerNumberModel(0, 0, valMaximoPags, 1));
  JLabel lblPagTotales = new JLabel("/" + valMaximoPags);
  JSpinner spNota = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10.0, 0.1));
  JButton btnUpdate = new JButton("Actualizar");
  JButton btnCancelar =
      new JButton(
          "Cancelar"); // Este boton devuelve los datos a como esta guardado en la base de datos |
  // si es nul lo devuelve al estado base

  Font fTitulo = new Font("Console", Font.BOLD, 40);
  Font fAutor = new Font("Console", Font.ITALIC, 30);
  Font fResumen = new Font("Console", Font.PLAIN, 20);
  Font fTResumen = new Font("Console", Font.PLAIN, 14);

  JButton btnPrestamo = new JButton("Prestar");
  JButton btnUpdateLibro = new JButton("Actualizar Libro");

  JList<String> lstSecuelas = new JList<String>();
  DefaultListModel listModelSaga = new DefaultListModel();
  JScrollPane scrollPaneSaga = new JScrollPane(lstSecuelas);

  JList<String> lstObras = new JList<String>();
  DefaultListModel listModelObras = new DefaultListModel();
  JScrollPane scrollPaneObras = new JScrollPane(lstObras);

  String colecc = new String();
  String autor = new String();
  String isbn = new String();
  String estado = new String();
  String urlPortada = new String();

  JPanel[] jPanelA = {panel, panelGenero, panelTecnico, panelEstado, panelSaga, panelAutor};
  JLabel[] jLabelA = {
    lblPortada,
    lblTitlo,
    lblAutor,
    lblResumen,
    lblGeneros,
    lblPublicacion,
    lblISBN,
    lblCapitulos,
    lblPaginas,
    lblColeccion,
    lblPublicacion,
    lblEstado,
    lblCapsLeidos,
    lblNota,
    lblCapTotales
  };
  JCheckBox[] jCheckBoxA = {ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10, ch11, ch12};
  JButton[] jButtonA = {btnPrestamo, btnUpdateLibro};
  JComponent[] jCompPprincipalA = {
    lblPortada, lblTitlo, lblAutor, panelGenero, lblResumen, scrollPaneResumen, tabbed
  };
  JComponent[] jCompPtecnicoA = {lblISBN, lblCapitulos, lblPaginas, lblColeccion, lblPublicacion};
  JComponent[] jCompPestadoA = {
    lblEstado,
    jcbEstados,
    jchReleido,
    lblVecesRele,
    spVecesRele,
    lblCapsLeidos,
    spCapL,
    lblCapTotales,
    lblPagsLeidas,
    spPagL,
    lblPagTotales,
    lblNota,
    spNota,
    btnUpdate,
    btnCancelar
  };

  public IntfzInfoLibro() {
    this.setResizable(false);
    repeticion();
    cambiarTomo();
  }

  public void iniciar(Document libro) {
    getContentPane().setLayout(new GridLayout(1, 15));
    crearComponentes();
    panel.setLayout(null);
    panelGenero.setLayout(null);
    panelTecnico.setLayout(null);
    panelEstado.setLayout(null);
    panelSaga.setLayout(null);
    panelAutor.setLayout(null);

    lblPortada.setBounds(10, 30, 329, 512);
    lblTitlo.setBounds(lblPortada.getX() + lblPortada.getWidth() + 11, 55, 950, 45);
    lblAutor.setBounds(lblTitlo.getX(), lblTitlo.getY() + lblTitlo.getHeight() + 20, 575, 35);
    lblResumen.setBounds(lblTitlo.getX(), 260, 100, 20);
    btnPrestamo.setBounds(
        lblPortada.getX(),
        lblPortada.getY() + lblPortada.getHeight() + 8,
        lblPortada.getWidth(),
        30);
    if (IntfzLogin.id_Usuario.equals("Admin")) {
      btnUpdateLibro.setBounds(btnPrestamo.getBounds());
      panel.add(btnUpdateLibro);
    }

    lblTitlo.setFont(fTitulo);
    lblAutor.setFont(fAutor);
    lblResumen.setFont(fResumen);

    panelGenero.setBounds(350, 160, 575, 85);
    lblGeneros.setBounds(256, 4, 52, 15);

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

    scrollPaneResumen.setBounds(350, 285, 950, 280);
    scrollPaneResumen.setBorder(null);
    txtASinopsis.setBounds(0, 0, scrollPaneResumen.getWidth(), scrollPaneResumen.getHeight());
    txtASinopsis.setLineWrap(true);
    txtASinopsis.setWrapStyleWord(true);
    txtASinopsis.setEditable(false);
    scrollPaneResumen.setBackground(panel.getBackground());
    txtASinopsis.setBackground(panel.getBackground());
    txtASinopsis.setFont(fTResumen);

    lblPortada.setBorder(BorderFactory.createLineBorder(Color.black));
    panelGenero.setBorder(BorderFactory.createLineBorder(Color.darkGray));

    tabbed.setBounds(950, 100, 350, 150);
    tabbed.addTab("Ficha Tecnica", panelTecnico);
    if (IntfzLogin.id_Usuario.equals("Invitado") || IntfzLogin.id_Usuario.equals("Admin")) {
    } else {
      tabbed.addTab("Estado", panelEstado);
      panel.add(btnPrestamo);
    }
    tabbed.addTab("Coleccion", panelSaga);
    tabbed.addTab("Autor", panelAutor);

    panelTecnico.setBounds(0, 0, tabbed.getWidth(), tabbed.getHeight());
    lblISBN.setBounds(10, 10, 420, 15);
    lblCapitulos.setBounds(
        lblISBN.getX(), lblISBN.getY() + 22, lblISBN.getWidth(), lblISBN.getHeight());
    lblPaginas.setBounds(
        lblISBN.getX(), lblCapitulos.getY() + 22, lblISBN.getWidth(), lblISBN.getHeight());
    lblColeccion.setBounds(
        lblISBN.getX(), lblPaginas.getY() + 22, lblISBN.getWidth(), lblISBN.getHeight());
    lblPublicacion.setBounds(
        lblISBN.getX(), lblColeccion.getY() + 22, lblISBN.getWidth(), lblISBN.getHeight());

    panelEstado.setBounds(panelTecnico.getBounds());
    lblEstado.setBounds(10, 10, 55, 20);
    jcbEstados.setBounds(70, 10, 100, 20);
    jchReleido.setBounds(210, 10, 75, 20);
    spVecesRele.setBounds(210, 40, 35, 20);
    lblVecesRele.setBounds(250, 40, 50, 20);

    lblCapsLeidos.setBounds(10, 40, 70, 20);
    spCapL.setBounds(
        lblCapsLeidos.getX() + lblCapsLeidos.getWidth() + 5, lblCapsLeidos.getY(), 50, 20);
    lblCapTotales.setBounds(spCapL.getX() + spCapL.getWidth(), lblCapsLeidos.getY(), 50, 20);

    lblPagsLeidas.setBounds(10, 70, 70, 20);
    spPagL.setBounds(lblPagsLeidas.getX() + lblPagsLeidas.getWidth() + 5, 70, 50, 20);
    lblPagTotales.setBounds(spPagL.getX() + spPagL.getWidth(), lblPagsLeidas.getY(), 50, 20);

    lblNota.setBounds(10, 100, 50, 20);
    lblNota.setHorizontalAlignment(SwingConstants.CENTER);

    spNota.setBounds(85, 100, 50, 20);
    btnUpdate.setBounds(190, 70, 145, 25);
    btnCancelar.setBounds(190, 95, 145, 20);

    spVecesRele.setEnabled(false);

    panelSaga.setBounds(panelTecnico.getBounds());
    scrollPaneSaga.setBounds(0, 0, panelSaga.getWidth(), panelSaga.getHeight() - 25);
    lstSecuelas.setBounds(0, 0, scrollPaneSaga.getWidth(), scrollPaneSaga.getHeight());

    panelAutor.setBounds(panelTecnico.getBounds());
    scrollPaneObras.setBounds(0, 0, panelAutor.getWidth(), panelAutor.getHeight() - 25);
    lstSecuelas.setBounds(0, 0, scrollPaneObras.getWidth(), scrollPaneObras.getHeight());

    scrollPaneSaga.setBackground(panel.getBackground());
    lstSecuelas.setBackground(panel.getBackground());
    scrollPaneObras.setBackground(panel.getBackground());
    lstObras.setBackground(panel.getBackground());

    getContentPane().add(panel);

    if (libro != null) {
      colecc = libro.getString("Saga");
      autor = libro.getString("Autor");
      isbn = libro.getString("ISBN");
      urlPortada = libro.getString("PortadaURL");
      mostrarInfoLibro(libro);
      prestarLibro(libro);
      actualizarEstado(libro);
      actualizar(libro);
      cancelarEstado();
    }

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(1350, 630);
    setVisible(true);
  }

  public String loreIpsum() {
    return " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris lacinia porttitor libero in commodo. Integer erat metus, condimentum ut mattis quis, pretium sit amet orci. Aenean faucibus eu lacus eget iaculis. Aenean placerat ultrices suscipit. Etiam venenatis nulla ut pharetra scelerisque. Suspendisse scelerisque efficitur elit, id consequat ex tempus at. Nam odio erat, gravida at ante in, pellentesque porttitor ante. Maecenas semper a turpis et euismod.\n"
        + "Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. + \"\\n\"\n"
        + " \"Integer sed ultricies sapien. Etiam scelerisque justo at dapibus gravida. Vestibulum sit amet nisi elit. Nam scelerisque magna nibh, feugiat dictum magna blandit at. Praesent dictum mi nec fermentum consequat. Maecenas molestie urna et lorem auctor mollis vitae mollis eros. Phasellus interdum tortor sed venenatis porta. Mauris hendrerit quis neque id aliquet. In a scelerisque urna, in fringilla tortor. Fusce in sodales enim, id iaculis leo. Nulla in laoreet urna, et sodales sem. ";
  }

  public void mostrarInfoLibro(Document libro) {
    Document estado =
        collecDetBiblio
            .find(and(eq("Usuario", IntfzLogin.id_Usuario), eq("Libro", libro)))
            .projection(
                include("Estado", "Paginas", "Capitulos", "Nota", "Releido", "VecesReleido"))
            .first();
    urlPortada = libro.getString("PortadaURL");
    añadirPortada();
    lblISBN.setText("ISBN: " + libro.getString("ISBN"));
    lblTitlo.setText(libro.getString("Titulo"));

    setTitle(lblTitlo == null ? "Info Libro" : lblTitlo.getText());

    lblAutor.setText(libro.getString("Autor"));
    lstGeneros = (List<Document>) libro.get("Generos");
    for (int k = 0, i = 0; k < jCheckBoxA.length; k++) {
      if (lstGeneros.contains(jCheckBoxA[k].getText())) {
        jCheckBoxA[k].setSelected(true);
        i++;
        if (i == lstGeneros.size()) break;
      }
    }
    txtASinopsis.setText(libro.getString("Sinopsis"));

    // Pestaña Tecnica
    lblColeccion.setText("Saga: " + libro.getString("Saga") + "  " + libro.getInteger("Tomo"));
    if (libro.getInteger("Capitulos") != null)
      lblCapitulos.setText("Capitulos: " + libro.getInteger("Capitulos"));
    if (libro.getInteger("Paginas") != null)
      lblPaginas.setText("Paginas: " + libro.getInteger("Paginas"));
    if (libro.getDate("f_publicacion") != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");
      lblPublicacion.setText("Fecha de Publicación: " + sdf.format(libro.getDate("f_publicacion")));
    }

    if (libro.getInteger("Capitulos") != null) {
      valMaximoCaps = libro.getInteger("Capitulos");
      spCapL.setModel(new SpinnerNumberModel(0, 0, valMaximoCaps, 1));
      lblCapTotales.setText("/" + valMaximoCaps);
    } else {
      spCapL.setModel(new SpinnerNumberModel(0, 0, 999, 1));
      lblCapTotales.setText("/???");
    }
    if (libro.getInteger("Paginas") != null) {
      valMaximoPags = libro.getInteger("Paginas");
      spPagL.setModel(new SpinnerNumberModel(0, 0, valMaximoPags, 1));
      lblPagTotales.setText("/" + valMaximoPags);
    } else {
      spPagL.setModel(new SpinnerNumberModel(0, 0, 999, 1));
      lblPagTotales.setText("/???");
    }

    // Pestaña Estado
    if (estado != null) {
      jcbEstados.setSelectedItem(nombrJcbEstado(estado.getString("Estado")));
      spCapL.setValue(estado.get("Capitulos"));
      spPagL.setValue(estado.get("Paginas"));

      if (estado.getInteger("Nota") != null) {
        int valorNota = estado.getInteger("Nota");
        Float nota = (float) valorNota / 10;
        spNota.setValue(nota);
      } else {
        spNota.setValue(0);
      }

      jchReleido.setSelected(estado.getBoolean("Releido"));
      if (jchReleido.isSelected() == true) spVecesRele.setEnabled(true);
      spVecesRele.setValue(estado.get("VecesReleido") != null ? estado.get("VecesReleido") : 0);

    } else {
      jcbEstados.setSelectedIndex(0);
      spCapL.setValue(0);
      spPagL.setValue(0);
      spNota.setValue(0);
      jchReleido.setSelected(false);
      spVecesRele.setValue(0);
    }

    // Panel Colleccion
    listModelSaga.clear();
    MongoCursor<Document> coleccion =
        collecLibro
            .find(eq("Saga", libro.getString("Saga")))
            .sort(ascending("Tomo"))
            .projection(include("Titulo", "Tomo"))
            .iterator();
    while (coleccion.hasNext()) {
      Document libroColec = coleccion.next();
      String collecNume =
          "Libro " + libroColec.getInteger("Tomo") + ":  " + libroColec.getString("Titulo");
      listModelSaga.addElement(collecNume);
    }
    lstSecuelas.setModel(listModelSaga);

    // Panel Autor
    listModelObras.clear();
    MongoCursor<Document> autor =
        collecLibro
            .find(eq("Autor", libro.getString("Autor")))
            .sort(ascending("Titulo"))
            .projection(include("Titulo"))
            .iterator();
    while (autor.hasNext()) {
      Document libroAutor = autor.next();
      String libroAutorString = libroAutor.getString("Titulo");
      listModelObras.addElement(libroAutorString);
    }
    lstObras.setModel(listModelObras);

    isbn = libro.getString("ISBN");
    leido();
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

  public void prestarLibro(Document libro) {
    btnPrestamo.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Document usuario = collecUsuario.find(eq("Nombre", IntfzLogin.id_Usuario)).first();
            if (usuario.getInteger("NPrestados") < 5) {
              Calendar calc_fecha = Calendar.getInstance();
              calc_fecha.setTime(new Date());
              calc_fecha.set(Calendar.DAY_OF_YEAR, calc_fecha.get(Calendar.DAY_OF_YEAR) + 30);
              Date f_devolucion = calc_fecha.getTime();
              try {
                Document prestamo = new Document();
                prestamo.put("Usuario", usuario.getString("Nombre"));
                prestamo.put("Libro", libro);
                prestamo.put("f_prestamo", new Date());
                prestamo.put("f_devolucion", f_devolucion);
                prestamo.put("Prestado", true);

                Document comproPrestamo = // ComprobarPrestamo
                    collecDetPrestamo
                        .find(
                            and(
                                eq("Usuario", prestamo.get("Usuario")),
                                eq("Libro", prestamo.get("Libro")),
                                eq("Prestado", true)))
                        .first();
                if (comproPrestamo == null) {
                  collecDetPrestamo.insertOne(prestamo);
                  collecUsuario.updateOne(
                      eq("Usuario", usuario.getString("Nombre")),
                      set("NPrestados", usuario.getInteger("NPrestados") + 1));
                  mensajeEmergente(1);
                } else {
                  mensajeEmergente(4);
                }
              } catch (Exception exception) {
                mensajeEmergente(3);
              }

            } else {
              mensajeEmergente(2);
            }
          }
        });
  }

  public void actualizar(Document libro) {
    btnUpdateLibro.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose(); // Cerrar la ventana y reabrirla con los datos cambiados o repintar
            IntfzActLibro intfzActLibro = new IntfzActLibro();
            intfzActLibro.iniciar(libro);
          }
        });
  }

  public void leido() {
    jcbEstados.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (jcbEstados.getSelectedItem().equals("Leído")) {
              spCapL.setValue(valMaximoCaps);
              spPagL.setValue(valMaximoPags);
            }
          }
        });
  }

  public void repeticion() {
    jchReleido.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            spVecesRele.setEnabled(jchReleido.isSelected() ? true : false);
          }
        });
  }

  public void actualizarEstado(Document libro) {
    btnUpdate.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

            String isbnLibro = lblISBN.getText().substring(lblISBN.getText().indexOf(" ") + 1);
            Integer Pags = Integer.parseInt(spPagL.getValue().toString());
            Integer Caps = Integer.parseInt(spCapL.getValue().toString());
            Integer Releido = Integer.parseInt(spVecesRele.getValue().toString());
            Float valorNota = Float.parseFloat(spNota.getValue().toString());
            int Nota = (valorNota == 10 ? 10 : (int) (valorNota * 10));
            Document libro = collecLibro.find(eq("ISBN", isbnLibro)).first();
            Document estadoLibro =
                collecDetBiblio
                    .find(and(eq("Usuario", IntfzLogin.id_Usuario), eq("Libro", libro)))
                    .first();
            if (estadoLibro != null) {
              if (jcbEstados.getSelectedItem().toString().equals("Sin Añadir")) {
                int confirmado =
                    JOptionPane.showConfirmDialog(
                        btnUpdate,
                        "Al dejar el Estado en 'Sin Añadir' esta eliminando este libro de su biblioteca personal. ¿Desea Continuar? ",
                        "Alerta",
                        JOptionPane.YES_NO_OPTION);

                if (JOptionPane.OK_OPTION == confirmado) {
                  DeleteResult delRegistro = collecDetBiblio.deleteOne(estadoLibro);
                }
              } else {

                try {
                  // Actualizar Registro
                  DeleteResult delRegistro = collecDetBiblio.deleteOne(estadoLibro);

                  Document estadoActualizado = new Document();
                  estadoActualizado.put("Usuario", IntfzLogin.id_Usuario);
                  estadoActualizado.put("Libro", libro);
                  estadoActualizado.put(
                      "Estado", nombrEstado(jcbEstados.getSelectedItem().toString()));
                  estadoActualizado.put("Paginas", Pags);
                  estadoActualizado.put("Capitulos", Caps);
                  if (jchReleido.isSelected()) {
                    estadoActualizado.put("Releido", true);
                    estadoActualizado.put("VecesReleido", Releido);
                  } else {
                    estadoActualizado.put("Releido", false);
                    estadoActualizado.put("VecesReleido", null);
                  }
                  if (Nota > 0) estadoActualizado.put("Nota", Nota);
                  estadoActualizado.put("titloOrden", lblTitlo.getText());

                  collecDetBiblio.insertOne(estadoActualizado);

                  JOptionPane.showMessageDialog(
                      null,
                      "Actualizado el Estado del Libro",
                      " Actualizado ",
                      JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                  JOptionPane.showMessageDialog(
                      null,
                      "No Se Ha Podido Actualizar el Estado del Libro",
                      "Actualición Interrumpida",
                      JOptionPane.INFORMATION_MESSAGE);
                }
              }
            } else {
              if (jcbEstados.getSelectedItem().toString().equals("Sin Añadir")) {
              } else {
                // Crear Registro
                try {
                  Document estado = new Document();
                  estado.put("Usuario", IntfzLogin.id_Usuario);
                  estado.put("Libro", libro);
                  estado.put("Estado", nombrEstado(jcbEstados.getSelectedItem().toString()));
                  estado.put("Paginas", Pags);
                  estado.put("Capitulos", Caps);
                  if (jchReleido.isSelected()) {
                    estado.put("Releido", true);
                    estado.put("VecesReleido", Releido);
                  } else {
                    estado.put("Releido", false);
                    estado.put("VecesReleido", null);
                  }

                  if (Nota > 0) estado.put("Nota", Nota);
                  estado.put("titloOrden", lblTitlo.getText());

                  collecDetBiblio.insertOne(estado);

                  mensajeEmergente(5);
                } catch (Exception ex) {
                  mensajeEmergente(6);
                }
              }
            }
          }
        });
  }

  public String nombrEstado(String estado) {

    if (estado.equals("Leído")) estado = "Terminado";

    if (estado.equals("Abandonado")) estado = "XinTerminar";

    if (estado.equals("Quiero Leer")) estado = "Pendiente";

    return estado;
  }

  public String nombrJcbEstado(String estado) {

    if (estado.equals("Terminado")) estado = "Leído";

    if (estado.equals("XinTerminar")) estado = "Abandonado";

    if (estado.equals("Pendiente")) estado = "Quiero Leer";

    return estado;
  }

  public void cancelarEstado() {
    btnCancelar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String isbnLibro = lblISBN.getText().substring(lblISBN.getText().indexOf(" ") + 1);
            Document libro = collecLibro.find(eq("ISBN", isbnLibro)).first();
            Document estado =
                collecDetBiblio
                    .find(and(eq("Usuario", IntfzLogin.id_Usuario), eq("Libro", libro)))
                    .first();
            if (estado != null) {
              jcbEstados.setSelectedItem(nombrJcbEstado(estado.getString("Estado")));
              spCapL.setValue(estado.get("Capitulos"));
              spPagL.setValue(estado.get("Paginas"));

              if (estado.getInteger("Nota") != null) {
                int valorNota = estado.getInteger("Nota");
                Float nota = (float) valorNota / 10;
                spNota.setValue(nota);
              } else {
                spNota.setValue(0);
              }

              jchReleido.setSelected(estado.getBoolean("Releido"));
              spVecesRele.setValue(jchReleido.isSelected() ? estado.get("VecesReleido") : 0);

            } else {
              jcbEstados.setSelectedIndex(0);
              spCapL.setValue(0);
              spPagL.setValue(0);
              spNota.setValue(0);
              jchReleido.setSelected(false);
              spVecesRele.setValue(0);
            }
          }
        });
  }

  public void cambiarTomo() {
    lstSecuelas.addMouseListener(
        new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            lstSecuelas = (JList) evt.getSource();
            if (evt.getClickCount() == 2) {
              int index = lstSecuelas.locationToIndex(evt.getPoint());
              int i = 0;
              MongoCursor<Document> otroLibro =
                  collecLibro.find(eq("Saga", colecc)).sort(ascending("Tomo")).iterator();
              while (otroLibro.hasNext()) {
                Document libroColec = otroLibro.next();
                if (i == index) {
                  mostrarInfoLibro(libroColec);
                  break;
                }
                i++;
              }
            }
            return;
          }
        });
    lstObras.addMouseListener(
        new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            lstObras = (JList) evt.getSource();
            if (evt.getClickCount() == 2) {
              int index = lstObras.locationToIndex(evt.getPoint());
              int i = 0;
              MongoCursor<Document> otroLibro =
                  collecLibro.find(eq("Autor", autor)).sort(ascending("Titulo")).iterator();
              while (otroLibro.hasNext()) {
                Document libroAutor = otroLibro.next();
                if (i == index) {
                  mostrarInfoLibro(libroAutor);
                  break;
                }
                i++;
              }
            }
            return;
          }
        });
  }

  public void mensajeEmergente(int mensaje) {
    if (mensaje == 1) {
      JOptionPane.showMessageDialog(
          null, "Libro Prestado Correctamente", "Libro Prestado", JOptionPane.INFORMATION_MESSAGE);
    }
    if (mensaje == 2) {
      JOptionPane.showMessageDialog(
          null,
          "Este usuario ya ha tomado prestado el limite maximo de 5 libros simultaneos ",
          "Advertencia",
          JOptionPane.INFORMATION_MESSAGE);
    }
    if (mensaje == 3) {
      JOptionPane.showMessageDialog(
          null,
          "El Prestamo no ha podido realizarse correctamente, por favor vuelva a intentarlo",
          "Prestamo Fallido",
          JOptionPane.INFORMATION_MESSAGE);
    }
    if (mensaje == 4) {
      JOptionPane.showMessageDialog(
          null,
          "Este Usuario ya tiene este libro en prestamo",
          " Ya Prestado ",
          JOptionPane.INFORMATION_MESSAGE);
    }
    if (mensaje == 5) {
      JOptionPane.showMessageDialog(
          null, "Libro agregado a la Biblioteca", " Agregdo ", JOptionPane.INFORMATION_MESSAGE);
    }
    if (mensaje == 6) {
      JOptionPane.showMessageDialog(null, "ERROR", " ERROR ", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, jLabelA, null, jButtonA, null, null, txtASinopsis);
  }

  public void crearComponentes() {
    for (JComponent jComponent : jCompPprincipalA) {
      panel.add(jComponent);
    }
    for (JCheckBox jCheckBox : jCheckBoxA) {
      panelGenero.add(jCheckBox);
      jCheckBox.setEnabled(false);
    }
    panelGenero.add(lblGeneros);
    for (JComponent jComponent : jCompPtecnicoA) {
      panelTecnico.add(jComponent);
    }
    for (JComponent jComponent : jCompPestadoA) {
      panelEstado.add(jComponent);
    }
    panelSaga.add(scrollPaneSaga);
    panelAutor.add(scrollPaneObras);
  }
}
