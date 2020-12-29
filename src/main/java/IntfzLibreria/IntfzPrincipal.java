package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.*;

public class IntfzPrincipal extends JFrame implements Interfaz {

  MenuUsuario menuUsuario;
  IntfzInfoLibro intfzInfoLibro = new IntfzInfoLibro();

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");

  static final int NUMERO_LABELS = 12;

  JLabel[] lblsPortadas;
  JLabel[] lblsTitulos;
  String[] ultimosTitulos;

  Document libro;

  JPanel panel = new JPanel();
  JPanel[] jPanelA = {panel};

  public IntfzPrincipal() {
    this.setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    inicializarLabels();
  }

  private void inicializarLabels() {
    lblsPortadas = new JLabel[NUMERO_LABELS];
    lblsTitulos = new JLabel[NUMERO_LABELS];
    for (int i = 0; i < NUMERO_LABELS; i++) {
      lblsPortadas[i] = new JLabel("Portada" + i + ": 164 * 256");
      lblsTitulos[i] = new JLabel("Titulo Portada" + i);
    }
  }

  public void iniciar() {
    setTitle("¿Lo he leído?");
    getContentPane().setLayout(new GridLayout(1, 10));
    menuUsuario = new MenuUsuario(panel, this, true);
    crearComponentes();
    panel.setLayout(null);
    int poslbl = -1;
    for (JLabel jLabel : lblsPortadas) {
      poslbl++;
      irLibro(jLabel, poslbl);
    }
    for (JLabel jLabel : lblsTitulos) {
      irLibroT(jLabel);
    }

    lblsPortadas[0].setBounds(37, 100, 210, 332);
    lblsTitulos[0].setBounds(
        lblsPortadas[0].getX(),
        lblsPortadas[0].getY() + lblsPortadas[0].getHeight() + 15,
        lblsPortadas[0].getWidth(),
        25);
    int portadasPorFila = 6;
    for (int i = 1; i < NUMERO_LABELS; i++) {
      if (i % portadasPorFila == 0) {
        lblsPortadas[i].setBounds(
            lblsPortadas[0].getX(),
            lblsTitulos[i - portadasPorFila].getY() + 75,
            lblsPortadas[0].getWidth(),
            lblsPortadas[0].getHeight());

      } else {
        lblsPortadas[i].setBounds(
            lblsPortadas[i - 1].getX() + lblsPortadas[0].getWidth() + 50,
            lblsPortadas[i - 1].getY(),
            lblsPortadas[0].getWidth(),
            lblsPortadas[0].getHeight());
      }
      lblsTitulos[i].setBounds(
          lblsPortadas[i].getX(),
          lblsPortadas[i].getY() + lblsPortadas[i].getHeight() + 15,
          lblsPortadas[0].getWidth(),
          lblsTitulos[0].getHeight());
    }

    ultimosAgregados();
    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(1600, 1000);
    setVisible(true);
  }

  public void ultimosAgregados() {
    ultimosTitulos = new String[NUMERO_LABELS];
    MongoCursor<Document> ultimoAgregados =
        collecLibro
            .find()
            .sort(descending("f_registro"))
            .projection(include("Titulo", "PortadaURL"))
            .limit(12)
            .iterator();
    int pos = -1;
    String urlPortada = new String();
    while (ultimoAgregados.hasNext()) {
      pos++;
      Document titulos = ultimoAgregados.next();
      ultimosTitulos[pos] = new String(titulos.get("Titulo").toString());
      lblsTitulos[pos].setText(ultimosTitulos[pos]);
      lblsTitulos[pos].setBorder(null);
      urlPortada = titulos.getString("PortadaURL");
      try {
        URL url = new URL(urlPortada);
        Image portada = ImageIO.read(url);
        ImageIcon portadaIco = new ImageIcon(portada);
        Icon icono =
            new ImageIcon(
                portadaIco
                    .getImage()
                    .getScaledInstance(
                        lblsPortadas[0].getWidth(),
                        lblsPortadas[0].getHeight(),
                        Image.SCALE_SMOOTH));
        lblsPortadas[pos].setIcon(icono);
        lblsPortadas[pos].setText("");
        lblsPortadas[pos].setBorder(null);

        repaint();
      } catch (Exception ex) {
        lblsPortadas[pos].setText(titulos.get("Titulo").toString());
      }
    }
  }

  private void irLibro(JLabel jLabel, int posicion) {
    jLabel.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (menuUsuario.panelBusqueda.isVisible() == false && menuUsuario.panelMenuUsuario.isVisible() == false) {
              libro = collecLibro.find(eq("Titulo", ultimosTitulos[posicion].toString())).first();
              intfzInfoLibro.dispose();
              intfzInfoLibro.iniciar(libro);
            }
          }
        });
  }

  private void irLibroT(JLabel jLabel) {
    jLabel.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (menuUsuario.panelBusqueda.isVisible() == false) {
              libro = collecLibro.find(eq("Titulo", jLabel.getText())).first();
              intfzInfoLibro.dispose();
              intfzInfoLibro.iniciar(libro);
            }
          }
        });
  }

  public void crearComponentes() {
    for (JLabel jLabel : lblsPortadas) {
      panel.add(jLabel);
      jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      jLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    for (JLabel jLabel : lblsTitulos) {
      panel.add(jLabel);
      jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      jLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, lblsTitulos, null, null, null, null, null);
  }
}
