package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jfree.ui.RefineryUtilities;

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
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

public class IntfzCuenta extends JFrame implements Interfaz {

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

  JLabel lblPrestamo = new JLabel("Prestamos");

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
  JLabel[] lblDiasRestantes = {
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

  JLabel lblEstadisticas = new JLabel("Estadisticas");
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

  JButton btnRecargar = new JButton("Actualizar Datos");

  int conteoLeyendo = 0;
  int conteoLeido = 0;
  int conteoAbandonado = 0;
  int conteoQuieroLeer = 0;
  int conteoTotal = 0;

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

  Color verde = new Color(64, 161, 67);
  //  Color papiro = new Color(232, 218, 189);

  public IntfzCuenta() {}

  public void iniciar() {
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

    btnRecargar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            crearComponentes();
          }
        });

    irLibroPrestado(panelPrestamo1, 0);
    irLibroPrestado(panelPrestamo2, 1);
    irLibroPrestado(panelPrestamo3, 2);
    irLibroPrestado(panelPrestamo4, 3);
    irLibroPrestado(panelPrestamo5, 4);
    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    getContentPane().add(panel);
    setResizable(false);
    pack();
    setSize(1600, 1000);
    setVisible(true);
  }

  public void crearComponentes() {
    crearComponentesPrestamo();
    crearComponentesEstadistica();
    btnRecargar.setBounds(750, 75, 150, 20);
    panel.add(btnRecargar);
  }

  public void crearComponentesPrestamo() {
    panelPrestamo.setBounds(10, 100, 430, 850);
    panel.add(panelPrestamo);

    lblPrestamo.setBounds(375, 80, 100, 20);
    panel.add(lblPrestamo);

    for (JLabel jLabel : lblTitulo) {
      jLabel.setBounds(125, 10, 285, 20);
    }
    for (JLabel jLabel : lblAutor) {
      jLabel.setBounds(125, 50, 285, 20);
    }
    for (JLabel jLabel : lblSaga) {
      jLabel.setBounds(125, 90, 285, 20);
    }
    for (JLabel jLabel : lblDiasRestantes) {
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

    mostrarPrestamo();
    int i = -1;
    for (JButton jButton : btnDevolver) {
      i++;
      devolverPrestamo(jButton, i);
    }
  }

  public void añadirPortada(String urlPortada, int posi) {
    for (JLabel jLabel : lblPortada) {
      jLabel.setBounds(10, 10, 100, 150);
      jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
      jLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
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
      lblPortada[posi].setText("");
      repaint();

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(
          null, "Lo Sentimos, no es posible mostrar la portada de este ejemplar" + urlPortada);
    }
  }

  public void mostrarPrestamo() {

    MongoCursor<Document> prestamosUsuario =
        collecDetPrestamo
            .find(eq("Usuario", IntfzLogin.id_Usuario))
            .sort(descending("f_devolucion"))
            .limit(5)
            .iterator();
    int pos = 0;
    int dias = 0;
    while (prestamosUsuario.hasNext()) {
      Document prestamo = prestamosUsuario.next();
      Document libro = (Document) prestamo.get("Libro");
      dias = calculoDias(prestamo.getDate("f_devolucion"));
      añadirPortada(libro.getString("PortadaURL"), pos);
      lblTitulo[pos].setText(libro.getString("Titulo"));
      lblAutor[pos].setText(libro.getString("Autor"));
      lblSaga[pos].setText(libro.getString("Saga") + " " + libro.getInteger("Tomo"));
      lblDiasRestantes[pos].setText("Te Quedan " + dias + " dias");
      if (dias > 19) {
        lblDiasRestantes[pos].setForeground(verde);
      } else if (dias < 11) {
        lblDiasRestantes[pos].setForeground(Color.RED);
      } else {
        lblDiasRestantes[pos].setForeground(Color.BLACK);
      }
      pos++;
    }
    if (pos < 5) {
      lblPortada[pos].setText("Portada");
      lblPortada[pos].setIcon(null);
      lblTitulo[pos].setText("Titulo Prestamo" + (pos + 1));
      lblAutor[pos].setText("Autor Prestamo" + (pos + 1));
      lblSaga[pos].setText("Saga Prestamo" + (pos + 1));
      lblDiasRestantes[pos].setForeground(lblTitulo1.getForeground());
      lblDiasRestantes[pos].setText("Te Quedan X dias");
    }
  }

  public void devolverPrestamo(JButton jButton, int posicion) {
    jButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MongoCursor<Document> prestamos =
                collecDetPrestamo.find(eq("Usuario", id_Usuario)).iterator();

            while (prestamos.hasNext()) {
              Document libroPrestado = prestamos.next();
              Document libro = (Document) libroPrestado.get("Libro");
              String titulo = libro.getString("Titulo");

              if (titulo.equals(lblTitulo[posicion].getText())) {
                collecDetPrestamo.findOneAndDelete(libroPrestado);
                break;
              }
            }
            mostrarPrestamo();
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

  ///////// Prestamo Terminado || Queda Estadisticas
  private void crearComponentesEstadistica() {
    panelEstadisticas.setBounds(550, 100, 1000, 850);
    panelEstadisticas.setBorder(BorderFactory.createLineBorder(Color.black));
    panel.add(panelEstadisticas);

    lblEstadisticas.setBounds(550, 80, 100, 20);
    panel.add(lblEstadisticas);

    coloresEstadistica();
    tiempoRegistrado();
    grafico();
  }

  private void tiempoRegistrado() {
    lblAntiguedad.setBounds(550, 30, 450, 30);
    panelEstadisticas.add(lblAntiguedad);
    lblAntiguedad.setFont(antiguedad);
    Document tiempoRegistro =
        collecUsuario
            .find(eq("Nombre", IntfzLogin.id_Usuario))
            .projection(include("fCreacionCuenta"))
            .first();
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
    if (incluirFin) dias++;

    return dias;
  }

  private void coloresEstadistica() {

    lblVerde.setBackground(verde);
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

    if (conteoTotal > 0) {
      valorLeyendoVerde = conteoLeyendo * 500 / conteoTotal;
      valorLeidoAzul = conteoLeido * 500 / conteoTotal;
      valorAbandonadoRojo = conteoAbandonado * 500 / conteoTotal;
      valorQuieroGris = conteoQuieroLeer * 500 / conteoTotal;
    }

    lblVerde.setBounds(30, 30, valorLeyendoVerde, 30);
    lblAzul.setBounds(
        lblVerde.getX() + valorLeyendoVerde, lblVerde.getY(), valorLeidoAzul, lblVerde.getHeight());
    lblRojo.setBounds(
        lblAzul.getX() + valorLeidoAzul,
        lblVerde.getY(),
        valorAbandonadoRojo,
        lblVerde.getHeight());
    lblGris.setBounds(
        lblRojo.getX() + valorAbandonadoRojo,
        lblVerde.getY(),
        valorQuieroGris,
        lblVerde.getHeight());

    lblLeyendo.setText("Leyendo: " + conteoLeyendo);
    lblLeidos.setText("Leidos: " + conteoLeido);
    lblAbandonados.setText("Abandonados: " + conteoAbandonado);
    lblQuieroLeer.setText("Quiero Leer: " + conteoQuieroLeer);
    lblTotalGuardados.setText("Todos los Libros: " + conteoTotal);

    lblLeyendo.setBounds(40, 70, 75, 20);
    lblLeidos.setBounds(
        lblLeyendo.getX() + lblLeyendo.getWidth(),
        lblLeyendo.getY(),
        lblLeyendo.getWidth(),
        lblLeyendo.getHeight());
    lblAbandonados.setBounds(
        lblLeidos.getX() + lblLeyendo.getWidth(), lblLeyendo.getY(), 100, lblLeyendo.getHeight());
    lblQuieroLeer.setBounds(
        lblAbandonados.getX() + lblAbandonados.getWidth() + 10,
        lblLeyendo.getY(),
        lblAbandonados.getWidth(),
        lblLeyendo.getHeight());
    lblTotalGuardados.setBounds(
        lblQuieroLeer.getX() + lblAbandonados.getWidth(),
        lblLeyendo.getY(),
        lblAbandonados.getWidth() + 40,
        lblLeyendo.getHeight());

    lblLeyendo.setForeground(lblVerde.getBackground());
    lblLeidos.setForeground(lblAzul.getBackground());
    lblAbandonados.setForeground(lblRojo.getBackground());
    lblQuieroLeer.setForeground(lblGris.getBackground());

    panelEstadisticas.add(lblVerde);
    panelEstadisticas.add(lblAzul);
    panelEstadisticas.add(lblRojo);
    panelEstadisticas.add(lblGris);

    panelEstadisticas.add(lblLeyendo);
    panelEstadisticas.add(lblLeidos);
    panelEstadisticas.add(lblAbandonados);
    panelEstadisticas.add(lblQuieroLeer);
    panelEstadisticas.add(lblTotalGuardados);
  }

  public void grafico() {
    try {

      String title = "Valoración por Numero de Capitulos";
      GraficoLibrosNotas chart = new GraficoLibrosNotas(title);

      chart.pack();
      RefineryUtilities.centerFrameOnScreen(chart);
      chart.setVisible(true);

      /*       DefaultXYDataset dataset = new DefaultXYDataset();
      dataset.addSeries(
          "firefox",
          new double[][] {
            {2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017},
            {25, 29.1, 32.1, 32.9, 31.9, 25.5, 20.1, 18.4, 15.3, 11.4, 9.5}
          });
      dataset.addSeries(
          "ie",
          new double[][] {
            {2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017},
            {67.7, 63.1, 60.2, 50.6, 41.1, 31.8, 27.6, 20.4, 17.3, 12.3, 8.1}
          });
      dataset.addSeries(
          "chrome",
          new double[][] {
            {2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017},
            {0.2, 6.4, 14.6, 25.3, 30.1, 34.3, 43.2, 47.3, 58.4}
          });

      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
      renderer.setSeriesPaint(0, Color.ORANGE);
      renderer.setSeriesPaint(1, Color.BLUE);
      renderer.setSeriesPaint(2, Color.GREEN);
      renderer.setSeriesStroke(0, new BasicStroke(2));
      renderer.setSeriesStroke(1, new BasicStroke(2));
      renderer.setSeriesStroke(2, new BasicStroke(2));

      JFreeChart chart =
          ChartFactory.createXYLineChart(
              "Browser Quota",
              "Year",
              "Quota",
              dataset,
              PlotOrientation.VERTICAL,
              true,
              false,
              false);
      chart.getXYPlot().getRangeAxis().setRange(0, 100);
      ((NumberAxis) chart.getXYPlot().getRangeAxis())
          .setNumberFormatOverride(new DecimalFormat("#'%'"));
      chart.getXYPlot().setRenderer(renderer);

      BufferedImage image = chart.createBufferedImage(600, 400);
      ImageIO.write(image, "png", new File("xy-chart.png"));
      JLabel imagen = new JLabel(new ImageIcon(image));
      imagen.setBounds(100, 300, 600, 300);

      panelEstadisticas.add(imagen);*/

      /*  conteoLeyendo =
          (int)
              collecDetBiblio.countDocuments(
                  and(eq("Estado", "Leyendo"), eq("Usuario", id_Usuario)));
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

      DefaultPieDataset pieDataset = new DefaultPieDataset();

      pieDataset.setValue("Leyendo " + conteoLeyendo, conteoLeyendo);
      pieDataset.setValue("Leído " + conteoLeido, conteoLeido);
      pieDataset.setValue("Abandonado " + conteoAbandonado, conteoAbandonado);
      pieDataset.setValue("Quiero Leer " + conteoQuieroLeer, conteoQuieroLeer);

      JFreeChart chart = ChartFactory.createPieChart("Biblioteca", pieDataset, false, true, false);
      chart.setBackgroundPaint(panel.getBackground());
      ChartPanel panelGrafico = new ChartPanel(chart);
      panelGrafico.setBounds(100, 350, 700, 400);
      panelEstadisticas.add(panelGrafico);*/

    } catch (Exception e) {
    }
  }

  public void cambioTema(String color) {
    Temas.cambioTema(color, jPanelA, jLabelsA, null, btnDevolver, null, null, null);
  }
}
