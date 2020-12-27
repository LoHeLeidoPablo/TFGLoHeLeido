package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Map;

import static IntfzLibreria.IntfzLogin.id_Usuario;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

public class MenuUsuario extends JFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");

  IntfzInfoLibro infoLibro = new IntfzInfoLibro();
  IntfzLogin intfzLogin = new IntfzLogin();
  IntfzMiBiblioteca intfzMiBiblioteca = new IntfzMiBiblioteca();
  IntfzMiCuenta intfzMiCuenta = new IntfzMiCuenta();
  IntfzPrincipal intfzPrincipal = new IntfzPrincipal();
  IntfzRegistro intfzRegistro = new IntfzRegistro();
  IntfzRegLibro intfzRegLibro = new IntfzRegLibro();

  JPanel panelMenuUsuario;
  JPanel panelBusqueda;

  JLabel lblTituloProyecto;
  JLabel lblUsuario = new JLabel();
  JLabel lblPortada = new JLabel();
  JLabel lblRegLibro =
      new JLabel("No encunetra el libro que busca en nuestra Libreria, pulse aqui para añadirlo.");
  JTextField txtBuscadorP;
  JTextField txtBuscador;
  JList<String> lstCoincidencias = new JList<String>();
  DefaultListModel dlm = new DefaultListModel();
  JScrollPane scrollPaneResultados = new JScrollPane(lstCoincidencias);

  JComboBox<String> jcbTemas;
  JComboBox<String> jcbElementos;

  JButton btnLogIn = new JButton("Iniciar Sesion");
  JButton btnCuenta = new JButton("Mi Cuenta");
  JButton btnBiblioteca = new JButton("Mi biblioteca");
  JButton btnLogOut = new JButton("Cerrar Sesion");
  JButton btnClose = new JButton("Salir");

  JButton btnEscape;

  String colorTema;
  Boolean visiblePanelMUsuario = true;
  Boolean visiblePanelBuscador = true;
  Font fuenteis = new Font("Book Antiqua", 3, 45);
  Font fuenteUsu = new Font("Book Antiqua", 1, 22);
  Font font = lblRegLibro.getFont();

  String elemento = "Titulo";

  MongoCursor<Document> coleccion;

  Interfaz interfazActiva;
  JPanel[] jPanelA = {panelMenuUsuario, panelBusqueda};
  JLabel[] jLabelA = {lblTituloProyecto, lblUsuario};
  JButton[] jButtonA = {btnLogIn, btnCuenta, btnBiblioteca, btnLogOut, btnClose};

  public MenuUsuario(JPanel jpanel, Interfaz interfazActiva, Boolean panelUsuarioEsDesplegable) {
    this.interfazActiva = interfazActiva;

    String[] colores = {
      "Claro",
      "Oscuro",
      "Amarillo Oscuro",
      "Rojo Oscuro",
      "Verde Oscuro",
      "Azul Oscuro",
      "Naranja Claro",
      "Rojo Claro",
      "Verde Claro",
      "Azul Claro",
    };

    lblTituloProyecto = new JLabel("¿Lo he leído?");
    lblTituloProyecto.setBounds(20, 18, 310, 54);
    lblTituloProyecto.setFont(fuenteis);
    jpanel.add(lblTituloProyecto);

    txtBuscador = new JTextField("Buscar por ISBN, Titulo, Autor, Serie, Saga, Autor...");
    txtBuscador.setBounds(375, 30, 850, 30);
    txtBuscador.setFocusable(false);
    jpanel.add(txtBuscador);

    panelBusqueda = new JPanel();
    panelBusqueda.setBounds(txtBuscador.getX(), 15, txtBuscador.getWidth(), 500);
    panelBusqueda.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    panelBusqueda.setLayout(null);
    panelBusqueda.hide();
    jpanel.add(panelBusqueda);

    txtBuscadorP = new JTextField();
    txtBuscadorP.setBounds(100, 15, 500, 25);
    panelBusqueda.add(txtBuscadorP);
    String[] elementos = {"ISBN", "Titulo", "Autor", "Saga"};
    jcbElementos = new JComboBox<>(elementos);
    jcbElementos.setBounds(
        txtBuscadorP.getX() + txtBuscadorP.getWidth(),
        txtBuscadorP.getY(),
        75,
        txtBuscadorP.getHeight());
    jcbElementos.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            elemento = jcbElementos.getSelectedItem().toString();
            queryLike();
          }
        });
    panelBusqueda.add(jcbElementos);
    btnEscape = new JButton();
    btnEscape.setBounds(5, 5, 25, 25);
    panelBusqueda.add(btnEscape);
    ImageIcon portadaIco = new ImageIcon("src/main/resources/close.png");
    Icon icono =
        new ImageIcon(
            portadaIco
                .getImage()
                .getScaledInstance(
                    btnEscape.getWidth() + 5, btnEscape.getHeight() + 10, Image.SCALE_DEFAULT));
    btnEscape.setIcon(icono);

    scrollPaneResultados.setBounds(
        50, 75, (panelBusqueda.getWidth() - 100) / 2, panelBusqueda.getHeight() - 100);
    lstCoincidencias.setBounds(
        0, 0, scrollPaneResultados.getWidth(), scrollPaneResultados.getHeight());
    scrollPaneResultados.setBackground(panelBusqueda.getBackground());

    lstCoincidencias.setBackground(panelBusqueda.getBackground());
    panelBusqueda.add(scrollPaneResultados);

    lblRegLibro.setBounds(200, 475, 600, 20);
    lblRegLibro.setForeground(Color.blue);
    panelBusqueda.add(lblRegLibro);
    if (IntfzLogin.id_Usuario.equals("Invitado")) {
      lblRegLibro.setText(
          "Si no encuentras el libro que buscas, unete a 'Lo He Leído', para registrarlo");
      abrirRegUsuario();
    } else {
      abrirRegLibro();
    }

    lblUsuario.setText(IntfzLogin.UsuCuenta.getString("Nombre"));
    lblUsuario.setText(lblUsuario.getText() == null ? "Invitado" : lblUsuario.getText());
    lblUsuario.setFont(fuenteUsu);
    lblUsuario.setBounds(1400, 23, 100, 27);
    jpanel.add(lblUsuario);

    if (panelUsuarioEsDesplegable) {

      panelMenuUsuario = new JPanel();
      panelMenuUsuario.setBounds(1350, 35, 200, 155);
      panelMenuUsuario.setLayout(null);
      panelMenuUsuario.setOpaque(false);
      panelMenuUsuario.hide();
      jpanel.add(panelMenuUsuario);

      btnLogIn.setBounds(10, 10, 180, 20);
      panelMenuUsuario.add(btnLogIn);
      panelMenuUsuario.add(btnCuenta);
      panelMenuUsuario.add(btnBiblioteca);
      panelMenuUsuario.add(btnLogOut);
      panelMenuUsuario.add(btnClose);
      lblUsuario.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              panelMenuUsuario.show();
              panelMenuUsuario.setVisible(visiblePanelMUsuario);
              visiblePanelMUsuario = !visiblePanelMUsuario;
            }
          });

      jcbTemas = new JComboBox<String>(colores);
      panelMenuUsuario.add(jcbTemas);
      jcbTemas.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              colorTema = jcbTemas.getSelectedItem().toString();
              interfazActiva.cambioTema(colorTema);
            }
          });
      btnLog();
    }

    // crearComponentes(jpanel);
    despliegePaneles();
    botonesUsuario();
    busqueda();
    mostrarLibro();
  }

  public void btnLog() {
    if (lblUsuario == null) {
    } else {
      if ("Invitado".equals(lblUsuario.getText())) {
        btnLogOut.setVisible(false);
        btnCuenta.setVisible(false);
        btnBiblioteca.setVisible(false);
        btnClose.setBounds(10, 40, 180, 20);
        jcbTemas.setBounds(10, 70, 180, 20);

      } else {
        btnLogIn.setVisible(false);
        btnCuenta.setBounds(10, 10, 180, 20);
        btnBiblioteca.setBounds(10, 40, 180, 20);
        btnLogOut.setBounds(10, 70, 180, 20);
        btnClose.setBounds(10, 100, 180, 20);
        jcbTemas.setBounds(10, 130, 180, 20);
      }
    }
  }

  public void despliegePaneles() {
    txtBuscador.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            panelBusqueda.show();
            panelBusqueda.setVisible(true);
            txtBuscador.setVisible(false);
            jcbElementos.setSelectedIndex(1);
          }
        });
  }

  public void botonesUsuario() {

    btnEscape.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            panelBusqueda.setVisible(false);
            txtBuscador.setVisible(true);
            txtBuscadorP.setText("");
            lblPortada.setVisible(false);
          }
        });

    btnLogIn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            IntfzLogin ventana = new IntfzLogin();
            ventana.iniciar();
          }
        });

    btnCuenta.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // disposeAll();
            intfzPrincipal.setState(JFrame.ICONIFIED);
            intfzMiCuenta.iniciar(intfzPrincipal);
          }
        });

    btnBiblioteca.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // disposeAll();

            intfzMiBiblioteca.iniciar(intfzPrincipal);
          }
        });

    btnLogOut.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            disposeAll();
            lblUsuario.setText("Invitado");
            intfzPrincipal.iniciar();
            btnLogIn.setVisible(true);
            btnLog();
          }
        });

    btnClose.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
  }

  public void abrirRegLibro() {
    lblRegLibro.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            panelBusqueda.setVisible(false);
            txtBuscador.setVisible(true);
            intfzRegLibro.iniciar();
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            lblRegLibro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Font subrayado = lblRegLibro.getFont();
            Map attributes = subrayado.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblRegLibro.setFont(subrayado.deriveFont(attributes));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            lblRegLibro.setFont(font);
          }
        });
  }

  public void abrirRegUsuario() {
    lblRegLibro.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            panelBusqueda.setVisible(false);
            txtBuscador.setVisible(true);
            intfzRegistro.iniciar();
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            lblRegLibro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Font subrayado = lblRegLibro.getFont();
            Map attributes = subrayado.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblRegLibro.setFont(subrayado.deriveFont(attributes));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            lblRegLibro.setFont(font);
          }
        });
  }

  public void busqueda() {
    EscuchaTexto busqueda = new EscuchaTexto();
    javax.swing.text.Document campo = txtBuscadorP.getDocument();
    campo.addDocumentListener(busqueda);
  }

  private class EscuchaTexto implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
      queryLike();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      queryLike();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      // Este no es necesario para el funcionamiento de la app
    }
  }

  public void queryLike() {
    dlm.clear();
    coleccion = collecLibro.find().sort(ascending(elemento)).iterator();
    while (coleccion.hasNext()) {
      Document libroColec = coleccion.next();
      String dato;
      if (libroColec
          .getString(elemento)
          .toLowerCase()
          .contains(txtBuscadorP.getText().toLowerCase())) {
        if (elemento.equals("ISBN")) {
          dato = libroColec.getString(elemento);
          dlm.addElement(dato);
        } else if (elemento.equals("Saga") || elemento.equals("Autor")) {
          dato = libroColec.getString(elemento);
          if (dlm.contains(dato)) {
          } else {
            dlm.addElement(dato);
          }

        } else {
          dato = libroColec.getString(elemento);
          dlm.addElement(dato);
        }
      }
    }
    lstCoincidencias.setModel(dlm);
  }

  public void añadirPortada() {
    String elemento = jcbElementos.getSelectedItem().toString();
    lblPortada.setText("Portada no encontrada");
    lblPortada.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    lblPortada.setBounds(
        scrollPaneResultados.getX() + scrollPaneResultados.getWidth() + 50,
        scrollPaneResultados.getY(),
        scrollPaneResultados.getWidth() - 50, // 25
        scrollPaneResultados.getHeight());
    panelBusqueda.add(lblPortada);
    String enlace = lstCoincidencias.getSelectedValue().toString();
    Document portadaLibro = collecLibro.find(eq(elemento, enlace)).first();
    String urlPortada = portadaLibro.getString("PortadaURL");
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

  public void mostrarLibro() {
    // TODO Funciona pero debe seleccionarse con el raton, usar las fleachas no cambia la portad
    lstCoincidencias.addMouseListener(
        new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            lstCoincidencias = (JList) evt.getSource();
            if (!jcbElementos.getSelectedItem().equals("Autor")) {
              lblPortada.setVisible(true);
              añadirPortada();
            } else {
              lblPortada.setIcon(null);
              lblPortada.setText("Este elemento no tiene Portada");
              lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
            }
            if (evt.getClickCount() == 2) {
              String enlace = lstCoincidencias.getSelectedValue().toString();
              Document libro = collecLibro.find(eq(elemento, enlace)).first();

              if (elemento.equals("ISBN")) {
                String isbn = enlace.substring(0, enlace.indexOf(" "));
                Document libroISBN = collecLibro.find(eq(elemento, isbn)).first();
                infoLibro.iniciar(libroISBN);
              }
              if (elemento.equals("Titulo")) {
                infoLibro.iniciar(libro);
              }
              if (elemento.equals("Autor")) {
                Document libroAutor = collecLibro.find(eq(elemento, enlace)).first();
              }
              if (elemento.equals("Saga")) {
                infoLibro.iniciar(libro);
                infoLibro.tabbed.setSelectedIndex(infoLibro.tabbed.getTabCount() - 1);
              }
            }
          }
        });
  }

  public void disposeAll() {

    if (intfzPrincipal.isShowing()) {
      intfzPrincipal.dispose();
    }

    if (intfzMiBiblioteca.isShowing()) intfzMiBiblioteca.dispose();

    if (intfzMiCuenta.isShowing()) intfzMiCuenta.dispose();

    if (intfzRegistro.isShowing()) intfzRegistro.dispose();

    if (intfzRegLibro.isShowing()) intfzRegLibro.dispose();
  }

  public void crearComponentes(JPanel jpanel) {
    for (JPanel jPanelE : jPanelA) {
      jpanel.add(jPanelE);
      jPanelE.setVisible(false);
    }
    for (JLabel jLabel : jLabelA) {
      jpanel.add(jLabel);
    }
    for (JButton jButton : jButtonA) {
      panelMenuUsuario.add(jButton);
    }
  } // No Funciona

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, jLabelA, null, null, null, null, null);
  } // Esto no funciona
}
