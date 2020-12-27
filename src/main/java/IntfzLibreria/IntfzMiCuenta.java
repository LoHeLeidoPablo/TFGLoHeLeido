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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import static IntfzLibreria.IntfzLogin.id_Usuario;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class IntfzMiCuenta extends JFrame implements Interfaz {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetPrestamo = DDBB.getCollection("DetallesPrestamo");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  MenuUsuario menuUsuario;
  IntfzInfoLibro intfzInfoLibro = new IntfzInfoLibro();

  JPanel panel = new JPanel();
  JPanel panelPrestamo = new JPanel();
  JPanel panelPrestamo1 = new JPanel();
  JPanel panelPrestamo2 = new JPanel();
  JPanel panelPrestamo3 = new JPanel();
  JPanel panelPrestamo4 = new JPanel();
  JPanel panelPrestamo5 = new JPanel();
  JPanel panelEstadisticas = new JPanel();

  JLabel lblPortada1 = new JLabel("Portada");
  JLabel lblTitulo1 = new JLabel("Titulo Prestamo 1");
  JLabel lblAutor1 = new JLabel("Autor Prestamo 1");
  JLabel lblSaga1 = new JLabel("Saga Prestamo 1");
  JLabel lblDiasRestantes1 = new JLabel("Te Quedan X dias");
  JButton btnDevolver1 = new JButton("Devolver");

  JLabel lblPortada2 = new JLabel("Portada");
  JLabel lblTitulo2 = new JLabel("Titulo Prestamo 2");
  JLabel lblAutor2 = new JLabel("Autor Prestamo 2");
  JLabel lblSaga2 = new JLabel("Saga Prestamo 2");
  JLabel lblDiasRestantes2 = new JLabel("Te Quedan X dias");
  JButton btnDevolver2 = new JButton("Devolver");

  JLabel lblPortada3 = new JLabel("Portada");
  JLabel lblTitulo3 = new JLabel("Titulo Prestamo 3");
  JLabel lblAutor3 = new JLabel("Autor Prestamo 3");
  JLabel lblSaga3 = new JLabel("Saga Prestamo 3");
  JLabel lblDiasRestantes3 = new JLabel("Te Quedan X dias");
  JButton btnDevolver3 = new JButton("Devolver");

  JLabel lblPortada4 = new JLabel("Portada");
  JLabel lblTitulo4 = new JLabel("Titulo Prestamo 4");
  JLabel lblAutor4 = new JLabel("Autor Prestamo 4");
  JLabel lblSaga4 = new JLabel("Saga Prestamo 4");
  JLabel lblDiasRestantes4 = new JLabel("Te Quedan X dias");
  JButton btnDevolver4 = new JButton("Devolver");

  JLabel lblPortada5 = new JLabel("Portada");
  JLabel lblTitulo5 = new JLabel("Titulo Prestamo 5");
  JLabel lblAutor5 = new JLabel("Autor Prestamo 5");
  JLabel lblSaga5 = new JLabel("Saga Prestamo 5");
  JLabel lblDiasRestantes5 = new JLabel("Te Quedan X dias");
  JButton btnDevolver5 = new JButton("Devolver");

  JLabel[] lblPortada = {lblPortada1, lblPortada2, lblPortada3, lblPortada4, lblPortada5};
  JLabel[] lblTitulo = {lblTitulo1, lblTitulo2, lblTitulo3, lblTitulo4, lblTitulo5};
  JLabel[] lblAutor = {lblAutor1, lblAutor2, lblAutor3, lblAutor4, lblAutor5};
  JLabel[] lblSaga = {lblSaga1, lblSaga2, lblSaga3, lblSaga4, lblSaga5};
  JLabel[] lblRestante = {
    lblDiasRestantes1, lblDiasRestantes2, lblDiasRestantes3, lblDiasRestantes4, lblDiasRestantes5
  };
  JButton[] btnDevolver = {btnDevolver1, btnDevolver2, btnDevolver3, btnDevolver4, btnDevolver5};

  JLabel lblAntiguedad = new JLabel("Fecha de Registro");
  Font antiguedad = new Font(lblAntiguedad.getFont().getFamily(), 0, 25);

  JLabel[] jLabelsA = {
    lblPortada1,
    lblPortada2,
    lblPortada3,
    lblPortada3,
    lblPortada4,
    lblPortada5,
    lblTitulo1,
    lblTitulo2,
    lblTitulo3,
    lblTitulo4,
    lblTitulo5,
    lblAutor1,
    lblAutor2,
    lblAutor3,
    lblAutor4,
    lblAutor4,
    lblAutor5,
    lblSaga1,
    lblSaga2,
    lblSaga2,
    lblSaga3,
    lblSaga3,
    lblSaga4,
    lblSaga5,
    lblAntiguedad
  };

  int conteoLeyendo = 0;
  int conteoLeido = 0;
  int conteoAbandonado = 0;
  int conteoQuieroLeer = 0;
  int conteoTotal = 0;

  JLabel lblVerde = new JLabel();
  JLabel lblAzul = new JLabel();
  JLabel lblRojo = new JLabel();
  JLabel lblGris = new JLabel();

  JLabel lblLeyendo = new JLabel();
  JLabel lblLeidos = new JLabel();
  JLabel lblAbandonados = new JLabel();
  JLabel lblQuieroLeer = new JLabel();
  JLabel lblTotalGuardados = new JLabel();

  JLabel lblCapitulosTotales = new JLabel("Capitulos: " + 100.000);
  JLabel lblColecciones = new JLabel("Colecciones: " + 1.000);
  JLabel lblAutores = new JLabel("Autores: " + 100);
  JLabel lblNotaMedia = new JLabel("Nota Media: " + 7.84);

  int valorLeyendoVerde = 0;
  int valorLeidoAzul = 0;
  int valorAbandonadoRojo = 0;
  int valorQuieroGris = 0;

  JPanel[] jPanelA = {
    panel,
    panelPrestamo,
    panelPrestamo1,
    panelPrestamo2,
    panelPrestamo3,
    panelPrestamo4,
    panelPrestamo5,
    panelEstadisticas
  };

  JComponent[] jComponentA = {panelPrestamo, panelEstadisticas};

  public IntfzMiCuenta() {
  }

  public void iniciar(JFrame intfzPrincipal) {
    setTitle("¿Lo he leído? - Mi Cuenta");
    getContentPane().setLayout(new GridLayout(1, 10));

    menuUsuario = new MenuUsuario(panel, this, false);

    panel.setLayout(null);
    panelPrestamo.setLayout(new GridLayout(5, 1));
    panelEstadisticas.setLayout(null);
    panelPrestamo1.setLayout(null);
    panelPrestamo2.setLayout(null);
    panelPrestamo3.setLayout(null);
    panelPrestamo4.setLayout(null);
    panelPrestamo5.setLayout(null);
    crearComponentes();

    irLibroPrestado(panelPrestamo1, 0);
    irLibroPrestado(panelPrestamo2, 1);
    irLibroPrestado(panelPrestamo3, 2);
    irLibroPrestado(panelPrestamo4, 3);
    irLibroPrestado(panelPrestamo5, 4);
    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    final JDialog frame = new JDialog(intfzPrincipal, this.getTitle(), true);
    frame.getContentPane().add(panel);
    frame.setResizable(false);
    frame.pack();
    frame.setSize(1600, 1000);
    frame.setVisible(true);
  }

  public void crearComponentes() {
    crearComponentesPrestamo();
    crearComponentesEstadistica();
    mostrarPrestamo();
  }

  public void crearComponentesPrestamo() {
    panelPrestamo.setBounds(10, 100, 430, 850);
    panel.add(panelPrestamo);

    for (JLabel jLabel : lblPortada) {
      jLabel.setBounds(10, 10, 100, 150);
      jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      jLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    for (JLabel jLabel : lblTitulo) {
      jLabel.setBounds(125, 10, 285, 20);
    }
    for (JLabel jLabel : lblAutor) {
      jLabel.setBounds(125, 50, 285, 20);
    }
    for (JLabel jLabel : lblSaga) {
      jLabel.setBounds(125, 90, 285, 20);
    }
    for (JLabel jLabel : lblRestante) {
      jLabel.setBounds(125, 135, 160, 20);
    }
    for (JButton jButton : btnDevolver) {
      jButton.setBounds(310, 135, 100, 20);
    }

    panelPrestamo1.add(lblPortada1);
    panelPrestamo1.add(lblTitulo1);
    panelPrestamo1.add(lblAutor1);
    panelPrestamo1.add(lblSaga1);
    panelPrestamo1.add(lblDiasRestantes1);
    panelPrestamo1.add(btnDevolver1);
    panelPrestamo1.setBorder(BorderFactory.createLineBorder(Color.black));
    panelPrestamo.add(panelPrestamo1);

    panelPrestamo2.add(lblPortada2);
    panelPrestamo2.add(lblTitulo2);
    panelPrestamo2.add(lblAutor2);
    panelPrestamo2.add(lblSaga2);
    panelPrestamo2.add(lblDiasRestantes2);
    panelPrestamo2.add(btnDevolver2);
    panelPrestamo2.setBorder(BorderFactory.createLineBorder(Color.black));
    panelPrestamo.add(panelPrestamo2);

    panelPrestamo3.add(lblPortada3);
    panelPrestamo3.add(lblTitulo3);
    panelPrestamo3.add(lblAutor3);
    panelPrestamo3.add(lblSaga3);
    panelPrestamo3.add(lblDiasRestantes3);
    panelPrestamo3.add(btnDevolver3);
    panelPrestamo3.setBorder(BorderFactory.createLineBorder(Color.black));
    panelPrestamo.add(panelPrestamo3);

    panelPrestamo4.add(lblPortada4);
    panelPrestamo4.add(lblTitulo4);
    panelPrestamo4.add(lblAutor4);
    panelPrestamo4.add(lblSaga4);
    panelPrestamo4.add(lblDiasRestantes4);
    panelPrestamo4.add(btnDevolver4);
    panelPrestamo4.setBorder(BorderFactory.createLineBorder(Color.black));
    panelPrestamo.add(panelPrestamo4);

    panelPrestamo5.add(lblPortada5);
    panelPrestamo5.add(lblTitulo5);
    panelPrestamo5.add(lblAutor5);
    panelPrestamo5.add(lblSaga5);
    panelPrestamo5.add(lblDiasRestantes5);
    panelPrestamo5.add(btnDevolver5);
    panelPrestamo5.setBorder(BorderFactory.createLineBorder(Color.black));
    panelPrestamo.add(panelPrestamo5);
  }

  private void mostrarPrestamo() {
    MongoCursor<Document> prestamosUsuario =
        collecDetPrestamo
            .find(and(eq("Usuario", IntfzLogin.id_Usuario), eq("Prestado", true)))
            .sort(ascending())
            .iterator();
    int pos = -1;
    int dias = 0;
    while (prestamosUsuario.hasNext()) {
      Document prestamo = prestamosUsuario.next();
      pos++;
      Document libro = (Document) prestamo.get("Libro");
      // TODO No Funciona.AHora solo falla esta linea -->
      //  dias = calculoDias(libro.getDate("f_devolucion"));
      añadirPortada(libro.getString("PortadaURL"), pos);
      lblTitulo[pos].setText(libro.getString("Titulo"));
      lblAutor[pos].setText(libro.getString("Autor"));
      lblSaga[pos].setText(libro.getString("Saga") + " " + libro.getInteger("Tomo"));
      lblRestante[pos].setText("Te Quedan " + dias + " dias");
      if (dias > 19) {
        lblRestante[pos].setForeground(Color.GREEN);
      } else if (dias < 11) {
        lblRestante[pos].setForeground(Color.RED);
      } else {
        lblRestante[pos].setForeground(Color.BLACK);
      }
    }
  }

  public void añadirPortada(String urlPortada, int posi) {
    try {
      URL url = new URL(urlPortada);
      Image portada = ImageIO.read(url);
      ImageIcon portadaIco = new ImageIcon(portada);
      Icon icono =
          new ImageIcon(
              portadaIco
                  .getImage()
                  .getScaledInstance(
                      lblPortada[posi].getWidth(),
                      lblPortada[posi].getHeight(),
                      Image.SCALE_DEFAULT));
      lblPortada[posi].setIcon(icono);
      lblPortada[posi].setBorder(null);
      repaint();

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(
          null, "Lo Sentimos, no es posible mostrar la portada de este ejemplar" + urlPortada);
    }
  }

  public void devolverPrestamo(JButton jButton) {
    jButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // TODO Preguntar como saber a que boton me estoy refiriendo
          }
        });
  }

  public void irLibroPrestado(JPanel jPanel, int posicion) {
    jPanel.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (menuUsuario.panelBusqueda.isVisible() == false) {
              Document libro =
                  collecLibro.find(eq("Titulo", lblTitulo[posicion].getText().toString())).first();
              if (lblPortada[posicion].getIcon() == null) {
                JOptionPane.showMessageDialog(
                    null, "Este Panel no contiene ningun prestamo", "Sin Perstamo", 0);
              } else {
                intfzInfoLibro.dispose();
                intfzInfoLibro.iniciar(libro);
              }
            }
          }
        });
  }

  private void crearComponentesEstadistica() {
    panelEstadisticas.setBounds(550, 100, 1000, 850);
    panelEstadisticas.setBorder(BorderFactory.createLineBorder(Color.black));
    panel.add(panelEstadisticas);
    coloresEstadistica();
    tiempoRegistrado();
  }

  private void tiempoRegistrado() {
    lblAntiguedad.setBounds(550, 30, 450, 30);
    panelEstadisticas.add(lblAntiguedad);
    lblAntiguedad.setFont(antiguedad);
    Document tiempoRegistro =
        collecUsuario.find(eq("Nombre", IntfzLogin.id_Usuario)).projection(include("fCreacionCuenta")).first();
    lblAntiguedad.setText("Antiguedad en Lo he Leído: X días ");

    if (tiempoRegistro != null) {
      Date regUSuario = tiempoRegistro.getDate("fCreacionCuenta");
      int dias = Math.abs(calculoDias(regUSuario));
      lblAntiguedad.setText("Antiguedad en 'Lo he Leído': " + dias + " días ");

      if (dias > 365) {
        int anios = dias / 365;
        dias = Math.abs(dias - (anios * 365));
        lblAntiguedad.setText("Antiguedad en Lo he Leído: " + dias + " días ");
      }
    }
  }

  private int calculoDias(Date f_Devolucion) {

    Calendar devolucion = Calendar.getInstance();
    devolucion.setTime(f_Devolucion);
    devolucion.set(Calendar.HOUR, 0);
    devolucion.set(Calendar.HOUR_OF_DAY, 0);
    devolucion.set(Calendar.MINUTE, 0);
    devolucion.set(Calendar.SECOND, 0);

    Calendar actual = Calendar.getInstance();
    actual.set(Calendar.HOUR, 0);
    actual.set(Calendar.HOUR_OF_DAY, 0);
    actual.set(Calendar.MINUTE, 0);
    actual.set(Calendar.SECOND, 0);

    long actualMS = actual.getTimeInMillis();
    long devolucionMS = devolucion.getTimeInMillis();

    int dias = (int) ((devolucionMS - actualMS) / 86400000);

    // Determinar si es necesario incluir el dia de devolución o no
    Boolean incluirFin = false;
    if (incluirFin) {
      dias++;
    }

    return dias;
  }

  private void coloresEstadistica() {

    lblVerde.setBackground(Color.GREEN);
    lblVerde.setOpaque(true);
    lblVerde.setBorder(BorderFactory.createLineBorder(lblVerde.getBackground(), 2));
    lblAzul.setBackground(Color.BLUE);
    lblAzul.setOpaque(true);
    lblAzul.setBorder(BorderFactory.createLineBorder(lblAzul.getBackground(), 2));
    lblRojo.setBackground(Color.RED);
    lblRojo.setOpaque(true);
    lblRojo.setBorder(BorderFactory.createLineBorder(lblRojo.getBackground(), 2));
    lblGris.setBackground(Color.GRAY);
    lblGris.setOpaque(true);
    lblGris.setBorder(BorderFactory.createLineBorder(lblGris.getBackground(), 2));

    MongoCursor<Document> countLeyendo =
        collecDetBiblio.find(and(eq("Estado", "Leyendo"), eq("Usuario", IntfzLogin.id_Usuario))).iterator();
    while (countLeyendo.hasNext()) {
      Document coley = countLeyendo.next();
      conteoLeyendo++;
    }
    MongoCursor<Document> countLeido =
        collecDetBiblio.find(and(eq("Estado", "Leído"), eq("Usuario", IntfzLogin.id_Usuario))).iterator();
    while (countLeido.hasNext()) {
      Document colei = countLeido.next();
      conteoLeido++;
    }
    MongoCursor<Document> countAbandonado =
        collecDetBiblio.find(and(eq("Estado", "Abandonado"), eq("Usuario", IntfzLogin.id_Usuario))).iterator();
    while (countAbandonado.hasNext()) {
      Document coab = countAbandonado.next();
      conteoAbandonado++;
    }
    MongoCursor<Document> countQuiero =
        collecDetBiblio
            .find(and(eq("Estado", "Quiero Leer"), eq("Usuario", IntfzLogin.id_Usuario)))
            .iterator();
    while (countQuiero.hasNext()) {
      Document coqui = countQuiero.next();
      conteoQuieroLeer++;
    }

    conteoTotal = conteoLeyendo + conteoLeido + conteoAbandonado + conteoQuieroLeer;

    if (conteoTotal == 0) {
    } else {
      double vLV = ((((double) conteoLeyendo * 100) / conteoTotal) * 500) / 100;
      double vLA = ((((double) conteoLeido * 100) / conteoTotal) * 500) / 100;
      double vAR = ((((double) conteoAbandonado * 100) / conteoTotal) * 500) / 100;
      double vQG = ((((double) conteoQuieroLeer * 100) / conteoTotal) * 500) / 100;

      valorLeyendoVerde = (int) Math.round(vLV);
      valorLeidoAzul = (int) Math.round(vLA);
      valorAbandonadoRojo = (int) Math.round(vAR);
      valorQuieroGris = (int) Math.round(vQG);
    }

    lblVerde.setBounds(30, 30, valorLeyendoVerde, 30);
    lblAzul.setBounds(lblVerde.getX() + valorLeyendoVerde, 30, valorLeidoAzul, 30);
    lblRojo.setBounds(lblAzul.getX() + valorLeidoAzul, 30, valorAbandonadoRojo, 30);
    lblGris.setBounds(lblRojo.getX() + valorAbandonadoRojo, 30, valorQuieroGris, 30);

    panelEstadisticas.add(lblVerde);
    panelEstadisticas.add(lblAzul);
    panelEstadisticas.add(lblRojo);
    panelEstadisticas.add(lblGris);

    lblLeyendo.setText("Leyendo: " + conteoLeyendo);
    lblLeidos.setText("Leidos: " + conteoLeido);
    lblAbandonados.setText("Abandonados: " + conteoAbandonado);
    lblQuieroLeer.setText("Quiero Leer: " + conteoQuieroLeer);
    lblTotalGuardados.setText("Libros Guardados: " + conteoTotal);

    lblLeyendo.setBounds(50, 80, 200, 20);
    lblLeyendo.setForeground(lblVerde.getBackground());
    panelEstadisticas.add(lblLeyendo);
    lblLeidos.setBounds(50, 100, 200, 20);
    lblLeidos.setForeground(lblAzul.getBackground());
    panelEstadisticas.add(lblLeidos);
    lblAbandonados.setBounds(50, 120, 200, 20);
    lblAbandonados.setForeground(lblRojo.getBackground());
    panelEstadisticas.add(lblAbandonados);
    lblQuieroLeer.setBounds(50, 140, 200, 20);
    lblQuieroLeer.setForeground(lblGris.getBackground());
    panelEstadisticas.add(lblQuieroLeer);
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, jLabelsA, null, btnDevolver, null, null, null);
  }
}
