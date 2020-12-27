package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntfzPntCarga extends JFrame {

  IntfzPrincipal intfzPrincipal = new IntfzPrincipal();

  JPanel panel = new JPanel();
  private JLabel lbltituloProyecto = new JLabel("¿Lo he leído?");
  private JProgressBar progreso = new JProgressBar(0, 100);
  private JTextArea txtAdescripcion =
      new JTextArea(
          "Sin acceso a internet, por favor pulsa el botón Recargar una vez que recuperes la conexión, para volver a intentarlo");
  private JButton btnRecargar = new JButton("Recargar");
  private JButton btnCerrar = new JButton("Salir");

  private Timer t;
  private ActionListener accion;
  private int n = 0;
  Font fuentet = new Font("", 3, 40);
  Font fuentei = new Font("", 3, 25);

  public IntfzPntCarga() {
    this.setResizable(false);
    this.setUndecorated(true);
    this.setLocationRelativeTo(null);
    conexion();
    t = new Timer(100, accion);
    t.start();
  }

  public void iniciar() {
    getContentPane().setLayout(new GridLayout(1, 10));
    panel.setLayout(null);

    lbltituloProyecto.setFont(fuentet);
    lbltituloProyecto.setBounds(50, 50, 300, 50);
    panel.add(lbltituloProyecto);

    progreso.setBounds(50, 125, 300, 50);
    progreso.setValue(0);
    progreso.setStringPainted(true);
    panel.add(progreso);

    txtAdescripcion.setBounds(30, 100, 340, 50);
    txtAdescripcion.setVisible(false);
    txtAdescripcion.setLineWrap(true);
    txtAdescripcion.setWrapStyleWord(true);
    txtAdescripcion.setEditable(false);
    txtAdescripcion.setBackground(panel.getBackground());
    panel.add(txtAdescripcion);

    btnRecargar.setBounds(63, 200, 100, 30);
    btnRecargar.setVisible(false);
    panel.add(btnRecargar);
    btnRecargar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            pantallalog();
          }
        });

    btnCerrar.setBounds(237, 200, 100, 30);
    btnCerrar.setVisible(false);
    panel.add(btnCerrar);
    btnCerrar.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });

    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(400, 250);
    setVisible(true);
  }

  public void conexion() {
    accion =
        new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            n += 1;
            progreso.setValue(n);

            if (progreso.getValue() == 100) {
              try {
                MongoClientURI uri =
                    new MongoClientURI(
                        "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

                MongoClient mongoClient = new MongoClient(uri);
                MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
                t.stop();
                panel.setVisible(false);
                dispose();
                intfzPrincipal.iniciar();
              } catch (Exception exception) {
                t.stop();
                pantallaError();
              }
            }
          }
        };
  }

  public void pantallalog() {
    lbltituloProyecto.setText("¿Lo he leído?");
    lbltituloProyecto.setBounds(50, 50, 300, 50);
    lbltituloProyecto.setFont(fuentet);
    txtAdescripcion.setVisible(false);
    btnRecargar.setVisible(false);
    btnCerrar.setVisible(false);
    progreso.setVisible(true);
    n = 0;
    conexion();
    t.start();
  }

  public void pantallaError() {
    lbltituloProyecto.setText("Sin Conexión a Internet");
    lbltituloProyecto.setBounds(30, 25, 340, 50);
    lbltituloProyecto.setFont(fuentei);

    progreso.setVisible(false);
    txtAdescripcion.setVisible(true);
    btnRecargar.setVisible(true);
    btnCerrar.setVisible(true);
  }
}
