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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static IntfzLibreria.IntfzLogin.id_Usuario;
import javax.swing.*;
import java.awt.*;

import static com.mongodb.client.model.Filters.eq;

public class GraficoLibrosNotas extends ApplicationFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");

  final String gPags = "Grafica Paginas";

  int rangoPags1 = 0;
  int rangoPags2 = 0;
  int rangoPags3 = 0;
  int rangoPags4 = 0;
  int rangoPags5 = 0;
  int rangoPags6 = 0;
  String[] pagsRang = {"1 - 250", "251 - 500", "501 - 750", "751 - 1000", "1001 +", "???"};

  float rangoNotaPags1 = 0;
  float rangoNotaPags2 = 0;
  float rangoNotaPags3 = 0;
  float rangoNotaPags4 = 0;
  float rangoNotaPags5 = 0;
  float rangoNotaPags6 = 0;

  public GraficoLibrosNotas(String titulo) {
    super(titulo);

    final JFreeChart chart = crearChart();
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(600, 450));
    setContentPane(chartPanel);
/*    IntfzCuenta intfzCuenta = new IntfzCuenta();
    chartPanel.setBounds(0,500,600,450);
    intfzCuenta.panelEstadisticas.add(chartPanel);*/
  }

  public int[] cuentaPaginas() {

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", id_Usuario)).iterator();

    while (biblioteca.hasNext()) {
      Document detLibro = biblioteca.next();
      Document libroDetLibro = (Document) detLibro.get("Libro");

      Integer paginas = libroDetLibro.getInteger("Paginas");

      if (paginas == null) {
        rangoPags6++;
      } else if (paginas < 251) {
        rangoPags1++;
      } else if (paginas < 501) {
        rangoPags2++;
      } else if (paginas < 751) {
        rangoPags3++;
      } else if (paginas < 1001) {
        rangoPags4++;
      } else {
        rangoPags5++;
      }
    }

    int[] totalTitulos =
        new int[] {rangoPags1, rangoPags2, rangoPags3, rangoPags4, rangoPags5, rangoPags6};
    return totalTitulos;
  }

  public float[] mediaNotasPaginas() {

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", id_Usuario)).iterator();
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    while (biblioteca.hasNext()) {
      Document detLibro = biblioteca.next();
      Document libroDetLibro = (Document) detLibro.get("Libro");

      Integer paginas = libroDetLibro.getInteger("Paginas");

      if (paginas == null) {
        rangoNotaPags6 += (float) detLibro.getInteger("Nota") / 10;
        i6++;
      } else if (paginas < 251) {
        rangoNotaPags1 += (float) detLibro.getInteger("Nota") / 10;
        i1++;
      } else if (paginas < 501) {
        rangoNotaPags2 += (float) detLibro.getInteger("Nota") / 10;
        i2++;
      } else if (paginas < 751) {
        rangoNotaPags3 += (float) detLibro.getInteger("Nota") / 10;
        i3++;
      } else if (paginas < 1001) {
        rangoNotaPags4 += (float) detLibro.getInteger("Nota") / 10;
        i4++;
      } else {
        rangoNotaPags5 += (float) detLibro.getInteger("Nota") / 10;
        i5++;
      }
    }

    rangoNotaPags1 /= i1;
    rangoNotaPags2 /= i2;
    rangoNotaPags3 /= i3;
    rangoNotaPags4 /= i4;
    rangoNotaPags5 /= i5;
    rangoNotaPags6 /= i6;

    float[] notaPags =
        new float[] {
          rangoNotaPags1,
          rangoNotaPags2,
          rangoNotaPags3,
          rangoNotaPags4,
          rangoNotaPags5,
          rangoNotaPags6
        };
    return notaPags;
  }

  final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

  private CategoryDataset crearTitulosDataset() {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    int[] totalTitulos = cuentaPaginas();

    for (int i = 0; i < pagsRang.length; i++) {
      dataset.addValue(totalTitulos[i], gPags + " Pags", pagsRang[i]);
    }
    return dataset;
  }

  private CategoryDataset crearNotasDataset() {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    float[] notaPags = mediaNotasPaginas();

    for (int i = 0; i < pagsRang.length; i++) {
      dataset.addValue(notaPags[i], gPags + " Nota", pagsRang[i]);
    }
    return dataset;
  }

  private JFreeChart crearChart() {

    final CategoryDataset titulosDataset = crearTitulosDataset();
    final NumberAxis ejeTitulo = new NumberAxis("Nº Total de Titulos");
    ejeTitulo.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    final BarRenderer renderer = new BarRenderer();
    renderer.setSeriesPaint(0, new Color(10, 200, 255));
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    final CategoryPlot subplot = new CategoryPlot(titulosDataset, null, ejeTitulo, renderer);
    subplot.setDomainGridlinesVisible(true);

    final CategoryDataset notasDataset = crearNotasDataset();
    final ValueAxis ejeNota = new NumberAxis("Nota");
    subplot.setRangeAxis(1, ejeNota);
    subplot.setDataset(1, notasDataset);
    subplot.mapDatasetToRangeAxis(1, 1);
    final CategoryItemRenderer notaRender = new LineAndShapeRenderer();
    notaRender.setSeriesPaint(0, Color.RED);

    subplot.setForegroundAlpha(0.7f);
    subplot.setRenderer(0, renderer);
    subplot.setRenderer(1, notaRender);

    final CategoryAxis ejePrinipal = new CategoryAxis("Nº Paginas");
    final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(ejePrinipal);

    plot.add(subplot, 1);

    final JFreeChart chart =
        new JFreeChart("Biblioteca", new Font("SansSerif", Font.BOLD, 18), plot, true);
    return chart;
  }
}
