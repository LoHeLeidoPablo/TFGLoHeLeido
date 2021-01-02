package IntfzLibreria;

import java.awt.Color;

import java.awt.Font;

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
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

public class DualAxisChart extends ApplicationFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecLibro = DDBB.getCollection("Libro");
  MongoCollection<Document> collecDetBiblio = DDBB.getCollection("DetallesBiblioteca");

  final String gCaps = "Grafica Capitulos";
  final String gPags = "Grafica Paginas";

  /*  int rangoCaps1 = 0;
  int rangoCaps2 = 0;
  int rangoCaps3 = 0;
  int rangoCaps4 = 0;
  int rangoCaps5 = 0;
  int rangoCaps6 = 0;
  String[] capsRang = {"1 - 25", "26 - 50", "51 - 75", "76 - 100", "101 +", "???"};*/

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

  // float[] notaPags = new float[6];

  public DualAxisChart(String titel) {
    super(titel);

    final JFreeChart chart = createChart();
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(600, 450));
    setContentPane(chartPanel);
  }

  /*  public void cuentaCapitulos() { // Arreglar orden bucle

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", "Pablo")).iterator();

    while (biblioteca.hasNext()) {
      Document detLibro = biblioteca.next();
      Document libroDetLibro = (Document) detLibro.get("Libro");

      Integer capitulos = libroDetLibro.getInteger("Capitulos");

      if (capitulos == null) {
        rangoCaps6++;
      } else if (capitulos < 26) {
        rangoCaps1++;
      } else if (capitulos < 51) {
        rangoCaps2++;
      } else if (capitulos < 76) {
        rangoCaps3++;
      } else if (capitulos < 101) {
        rangoCaps4++;
      } else {
        rangoCaps5++;
      }
    }
  }*/

  public int[] cuentaPaginas() {

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", "Pablo")).iterator();

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
    // mediaNotasPaginas();
    int[] totalTitulos =
        new int[] {rangoPags1, rangoPags2, rangoPags3, rangoPags4, rangoPags5, rangoPags6};
    return totalTitulos;
  }

  public float[] mediaNotasPaginas() {

    MongoCursor<Document> biblioteca = collecDetBiblio.find(eq("Usuario", "Pablo")).iterator();
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

  /*public int[] run() { // Datos corrsepondientes a las barras

  cuentaPaginas();
  //  cuentaCapitulos();

  int[] run =
      new int[] {
         {rangoCaps1, rangoCaps2, rangoCaps3, rangoCaps4, rangoCaps5, rangoCaps6},

            rangoPags1, rangoPags2, rangoPags3, rangoPags4, rangoPags5, rangoPags6
          };
      return run;
    }
  */

  final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

  private CategoryDataset createRunDataset2() {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    int[] totalTitulos = cuentaPaginas();

    for (int i = 0; i < pagsRang.length; i++) {
      dataset.addValue(totalTitulos[i], gPags + " Pags", pagsRang[i]);
    }
    return dataset;
  }

  private CategoryDataset createRunRateDataset2() {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    // int[] run = run() [1];
    float[] notaPags = mediaNotasPaginas();

    for (int i = 0; i < pagsRang.length; i++) {
      dataset.addValue(notaPags[i], gPags + " Nota", pagsRang[i]);
    }
    return dataset;
  }

  /*private CategoryDataset createRunDataset1() {
    int[] run = run()[0];

    for (int i = 0; i < capsRang.length; i++) {
      dataset.addValue(run[i], gCaps + " Caps", capsRang[i]);
    }
    return dataset;
  }

  private CategoryDataset createRunRateDataset1() {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    int[] run = run()[0];
    float num = 0; // Correspondinete a los puntos de las lineas

    for (int i = 0; i < capsRang.length; i++) {
      num += run[i];
      dataset.addValue(num / (i + 1), gCaps + " Nota", capsRang[i]);

    }
    return dataset;
  }*/

  private JFreeChart createChart() {

    /*
    // PrimerGrafico
    final CategoryDataset dataset1 = createRunDataset1();
    final NumberAxis rangeAxis1 = new NumberAxis("Nº Total de Titulos");
    rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    final BarRenderer renderer1 = new BarRenderer();
    renderer1.setSeriesPaint(0, Color.GREEN);
    renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    final CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
    subplot1.setDomainGridlinesVisible(true);

    final CategoryDataset runrateDataset1 = createRunRateDataset1();
    final ValueAxis axis2 = new NumberAxis("Nota");
    subplot1.setRangeAxis(1, axis2);
    subplot1.setDataset(1, runrateDataset1);
    subplot1.mapDatasetToRangeAxis(1, 1);
    final CategoryItemRenderer runrateRenderer1 = new LineAndShapeRenderer();
    runrateRenderer1.setSeriesPaint(0, Color.RED);

    subplot1.setForegroundAlpha(0.7f);
    subplot1.setRenderer(0, renderer1);
    subplot1.setRenderer(1, runrateRenderer1);*/

    final CategoryDataset dataset2 = createRunDataset2();
    final NumberAxis rangeAxis2 = new NumberAxis("Nº Total de Titulos");
    rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    final BarRenderer renderer2 = new BarRenderer();
    renderer2.setSeriesPaint(0, new Color(10,200,255));
    renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    final CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
    subplot2.setDomainGridlinesVisible(true);

    final CategoryDataset runrateDataset2 = createRunRateDataset2();
    final ValueAxis axis3 = new NumberAxis("Nota");
    subplot2.setRangeAxis(1, axis3);
    subplot2.setDataset(1, runrateDataset2);
    subplot2.mapDatasetToRangeAxis(1, 1);
    final CategoryItemRenderer runrateRenderer2 = new LineAndShapeRenderer();
    runrateRenderer2.setSeriesPaint(0, Color.RED);

    subplot2.setForegroundAlpha(0.7f);
    subplot2.setRenderer(0, renderer2);
    subplot2.setRenderer(1, runrateRenderer2);

    final CategoryAxis domainAxis = new CategoryAxis("Nº Paginas" /* / Nº Capitulos*/);
    final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);

    //  plot.add(subplot1, 1);
    plot.add(subplot2, 1);

    final JFreeChart chart =
        new JFreeChart("Biblioteca", new Font("SansSerif", Font.BOLD, 18), plot, true);
    return chart;
  }

  public static void main(final String[] args) {

    final String title = "Valoración por Numero de Capitulos";
    final DualAxisChart chart = new DualAxisChart(title);

    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }
}
