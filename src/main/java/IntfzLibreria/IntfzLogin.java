package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;

public class IntfzLogin extends JFrame {
  public static String id_Usuario = "Invitado";
  public static Document UsuCuenta = new Document();

  IntfzPrincipal intfzPrincipal = new IntfzPrincipal();

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecAuth = DDBB.getCollection("Auth");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  JPanel panel = new JPanel();

  JLabel lblTituloProyecto = new JLabel("¿Lo he leído?");
  JLabel lblUsuario = new JLabel("Usuario:");
  JLabel lblPassword = new JLabel("Contraseña:");
  JLabel lblRegistro = new JLabel("Crear Nueva Cuenta");

  JTextField txtUsuario = new JTextField();
  JPasswordField txtPassword = new JPasswordField();
  JButton btnLogIn = new JButton("Iniciar Sesión");
  JCheckBox cbVerPasswd = new JCheckBox("Mostrar Contraseña");

  JComponent[] jComponentA = {
    lblTituloProyecto,
    lblUsuario,
    txtUsuario,
    lblPassword,
    txtPassword,
    cbVerPasswd,
    lblRegistro,
    btnLogIn
  };

  Font font = lblRegistro.getFont();

  public IntfzLogin() {
    this.setResizable(false);
    this.setLocation(100, 100);
  }

  public void iniciar() {
    setTitle("Iniciar Sesión - ¿Lo he leído?");
    getContentPane().setLayout(new GridLayout(1, 10));
    crearComponentes();
    existeUsuario();
    panel.setLayout(null);

    lblTituloProyecto.setBounds(65, 10, 170, 25);
    Font fuenteis = new Font("Consola", 3, 22);
    lblTituloProyecto.setFont(fuenteis);

    lblUsuario.setBounds(50, 50, 100, 15);
    txtUsuario.setBounds(50, 65, 200, 20);
    lblPassword.setBounds(50, 100, 100, 15);
    txtPassword.setBounds(50, 115, 200, 20);
    txtPassword.setEchoChar('*');

    cbVerPasswd.setBounds(50, 140, 175, 20);
    cbVerPasswd.setBackground(panel.getBackground());
    cbVerPasswd.addItemListener(
        new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              txtPassword.setEchoChar((char) 0);
              cbVerPasswd.setText("Ocultar Contraseña");
            } else {
              txtPassword.setEchoChar('*');
              cbVerPasswd.setText("Mostrar Contraseña");
            }
          }
        });

    btnLogIn.setBounds(50, 170, 200, 25);
    panel.add(btnLogIn);

    lblRegistro.setBounds(75, 205, 150, 15);
    lblRegistro.setForeground(Color.blue);
    lblRegistro.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            panel.setVisible(false);
            dispose();
            IntfzRegistro ventana = new IntfzRegistro();
            ventana.iniciar();
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            lblRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Font subrayado = lblRegistro.getFont();
            Map attributes = subrayado.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            lblRegistro.setFont(subrayado.deriveFont(attributes));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            lblRegistro.setFont(font);
          }
        });

    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(300, 260);
    setVisible(true);
  }

  public void existeUsuario() {
    btnLogIn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Document usuAuth =
                collecAuth
                    .find(
                        and(
                            or(
                                eq("Nombre", txtUsuario.getText()),
                                eq("Email", txtUsuario.getText())),
                            eq("Contraseña", txtPassword.getText())))
                    .first();
            if (usuAuth != null) {
              UsuCuenta =
                  collecUsuario
                      .find(
                          and(
                              eq("Nombre", usuAuth.getString("Nombre")),
                              eq("Email", usuAuth.getString("Email"))))
                      .first();
              if (UsuCuenta == null) {
                UsuCuenta.put("Nombre", usuAuth.getString("Nombre"));
                UsuCuenta.put("Email", usuAuth.getString("Email"));
                UsuCuenta.put("fCreacionCuenta", new Date());
                UsuCuenta.put("NPrestados", 0);
                // UsuCuenta.put("Tema", "Claro");
                collecUsuario.insertOne(UsuCuenta);
              }
              id_Usuario = UsuCuenta.getString("Nombre");
              dispose();
              intfzPrincipal.iniciar();
            } else {
              JOptionPane.showMessageDialog(
                  null,
                  "Credenciales Incorrectas, por favor vuelve a intentarlo",
                  "Advertencia",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        });
    // TODO Relanzar Principal para que aparezca el usuario que acaba de iniciar sesion

    /*    menuUsuario = new Libreria.MenuUsuario(intfzPrincipal.panel,intfzPrincipal.jFramePrincipal,null);
       menuUsuario.repaint();
       intfzPrincipal.panel.repaint();
       intfzPrincipal.cerrarVentana();
       intfzPrincipal.jFramePrincipal.setVisible(false);
    intfzPrincipal.jFramePrincipal.disable();*/
  }

  public void crearComponentes() {
    for (JComponent jComponent : jComponentA) {
      panel.add(jComponent);
    }
  }
}
