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

import java.awt.*;

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

  String[] titulos = new String[10];
  int[] repeticiones = new int[10];

  public GraficoMasLeidos(String titulo) {
    super(titulo);

    final JFreeChart chart = crearChart();
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(600, 450));
    setContentPane(chartPanel);
    /*    IntfzCuenta intfzCuenta = new IntfzCuenta();
    chartPanel.setBounds(0,500,600,450);
    intfzCuenta.panelEstadisticas.add(chartPanel);*/
  }

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
        System.out.println("Nulo");
      } else {
        titulos[i] = libroDetLibro.getString("Titulo");
      }
      i++;
    }
    return titulos;
  }

  public int[] losMasLeidosNumeric() {
    MongoCursor<Document> biblioteca =
        collecDetBiblio
            .find(eq("Usuario", "Pablo"))
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

  private JFreeChart crearChart() {

    CategoryDataset titulosDataset = crearTitulosDataset();
    NumberAxis ejeTitulo = new NumberAxis("Nº Total de Titulos");
    ejeTitulo.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    BarRenderer renderer = new BarRenderer();
    renderer.setSeriesPaint(0, new Color(255, 0, 0));
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    CategoryAxis ejePrinipal = new CategoryAxis("Los Más Leidos");
    CategoryPlot subplot = new CategoryPlot(titulosDataset, null, ejeTitulo, renderer);
    subplot.setDomainGridlinesVisible(true);

    subplot.setForegroundAlpha(0.7f);
    subplot.setRenderer(0, renderer);
    // ejePrinipal.setVerticalTickLabels(true); // Linea para escribir verticalmente las etiquetas
    CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(ejePrinipal);

    plot.add(subplot, 1);

    final JFreeChart chart =
        new JFreeChart("Los Más Leidos", new Font("SansSerif", Font.BOLD, 18), plot, false);
    CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    return chart;
  }

  public static void main(String[] args) {
    String title = "Valoración por Numero de Capitulos";
    GraficoMasLeidos chart = new GraficoMasLeidos(title);

    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }
}
