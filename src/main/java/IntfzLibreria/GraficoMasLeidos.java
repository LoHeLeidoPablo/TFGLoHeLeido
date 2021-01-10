package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import static IntfzLibreria.IntfzLogin.id_Usuario;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class GraficoMasLeidos extends ApplicationFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");

  IntfzCuenta intfzCuenta = new IntfzCuenta();

  String[] titulos = new String[10];
  int[] repeticiones = new int[10];

  public GraficoMasLeidos(String titulo) {
    super(titulo);

    final JFreeChart chart = crearChart();
    final ChartPanel chartPanel = new ChartPanel(chart);
    chart.setBackgroundPaint(new Color(232, 218, 189));
    chartPanel.setPreferredSize(new Dimension(600, 450));
    setContentPane(chartPanel);
    try {
      BufferedImage image = chart.createBufferedImage(600, 400);
      ImageIO.write(image, "png", new File("src/main/resources/GraficoMasLeidos.png"));
    } catch (Exception e) {
    }
  }

  // Con este metodo recogemos los libros que más veces se han leido
  public String[] losMasLeidos() {
    MongoCursor<Document> biblioteca =
        collecDetBiblio
            .find(eq("Usuario", "Pablo"))
            .sort(descending("VecesReleido"))
            .limit(10)
            .iterator();
    int i = 0;
    while (biblioteca.hasNext()) {
      Document detLibro = biblioteca.next();
      Document libroDetLibro = (Document) detLibro.get("Libro");
      if (libroDetLibro.getString("Titulo") == null) {
      } else {
        titulos[i] = libroDetLibro.getString("Titulo");
      }
      i++;
    }
    return titulos;
  }

  // Con este metodo recogemos el numero de veces que se han releido los titulo obtenidos en el
  // metodo anterior
  public int[] losMasLeidosNumeric() {
    MongoCursor<Document> biblioteca =
        collecDetBiblio
            .find(eq("Usuario", id_Usuario))
            .sort(descending("VecesReleido"))
            .limit(10)
            .iterator();
    int i = 0;
    while (biblioteca.hasNext()) {
      Document detLibro = biblioteca.next();
      repeticiones[i] = detLibro.getInteger("VecesReleido");
      i++;
    }
    return repeticiones;
  }

  // Ahora los pintamos en nuestro Grafico
  final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
  private CategoryDataset crearTitulosDataset() {

    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    String[] titulos = losMasLeidos();
    int[] repeticiones = losMasLeidosNumeric();
    for (int i = 0; i < titulos.length; i++) {
      dataset.addValue(repeticiones[i], "Libro", titulos[i]);
    }
    return dataset;
  }

 // Este metodo es el que crea el grafico en le que luego se muestran los datos obtenidos
  private JFreeChart crearChart() {
    CategoryDataset titulosDataset = crearTitulosDataset();
    NumberAxis ejeTitulo = new NumberAxis("Veces Releido");
    ejeTitulo.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    BarRenderer renderer = new BarRenderer();
    renderer.setSeriesPaint(0, new Color(255, 0, 0));
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    CategoryAxis ejePrinipal = new CategoryAxis("");
    CategoryPlot subplot = new CategoryPlot(titulosDataset, null, ejeTitulo, renderer);
    subplot.setDomainGridlinesVisible(true);

    subplot.setForegroundAlpha(0.7f);
    subplot.setRenderer(0, renderer);
    CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(ejePrinipal);

    plot.add(subplot, 1);

    final JFreeChart chart =
        new JFreeChart(
            "Libros Más Leidos", new Font("Bookman Old Style", Font.BOLD, 18), plot, false);
    CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

    return chart;
  }
}
