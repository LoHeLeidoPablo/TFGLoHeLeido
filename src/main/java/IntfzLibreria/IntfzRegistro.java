package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Date;

public class IntfzRegistro extends JFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecAuth = DDBB.getCollection("Auth");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  IntfzLogin intfzLogin = new IntfzLogin();

  JPanel panel = new JPanel();

  JLabel lblTituloProyecto = new JLabel("Unete a Nosotros");
  JLabel lblUsuario = new JLabel("Usuario:");
  JLabel lblObUsuario = new JLabel("Obligatorio");
  JLabel lblEmail = new JLabel("Email:");
  JLabel lblObEmail = new JLabel("Obligatorio");
  JLabel lblPassword = new JLabel("Contraseña:");
  JLabel lblObPassword = new JLabel("La contraseña debe contener al menos 8 caracteres");
  JLabel lblPassword2 = new JLabel("Contraseña:");
  JLabel lblObPassword2 = new JLabel("La contraseña debe coincidir con la otra contraseña");

  JTextField txtUsuario = new JTextField("");
  JTextField txtEmail = new JTextField("");
  JPasswordField txtPassword = new JPasswordField("");
  JPasswordField txtRepPassword = new JPasswordField("");
  JButton btnRegistro = new JButton("Registrar");

  String contra1;
  String contra2;
  Boolean existe;
  Boolean obligatorios;

  JCheckBox cbVerPasswd = new JCheckBox("Mostrar Contraseñas");
  JLabel[] jLabelObli = {lblObUsuario, lblObEmail, lblObPassword, lblObPassword2};
  JComponent[] jComponentA = {
    lblTituloProyecto,
    lblUsuario,
    lblEmail,
    lblPassword,
    lblPassword,
    lblPassword2,
    lblObUsuario,
    lblObEmail,
    lblObPassword,
    lblObPassword2,
    btnRegistro,
    txtUsuario,
    txtEmail,
    txtPassword,
    txtRepPassword,
    cbVerPasswd
  };

  Font fuenteObligatoria = new Font(lblUsuario.getFont().getFamily(), Font.ITALIC, 9);

  public IntfzRegistro() {
    this.setResizable(false);
    this.setLocation(100, 100);
  }
  // TODO Implementar correo Email
  public void iniciar() {
    setTitle("Registrar - ¿Lo he leído?");
    getContentPane().setLayout(new GridLayout(1, 10));
    crearComponentes();
    mostrarContraseña(txtPassword);
    mostrarContraseña(txtRepPassword);
    rescribirCampo(txtUsuario, lblObUsuario);
    rescribirCampo(txtEmail, lblObEmail);
    rescribirCampo(txtPassword, lblObPassword);
    rescribirCampo(txtRepPassword, lblObPassword2);

    panel.setLayout(null);

    lblTituloProyecto.setBounds(15, 10, 250, 25);
    Font fuenteis = new Font("Consola", 3, 22);
    lblTituloProyecto.setFont(fuenteis);

    lblUsuario.setBounds(20, 50, 100, 15);
    txtUsuario.setBounds(20, 65, 235, 20);
    lblObUsuario.setBounds(20, 85, 160, 15);

    lblEmail.setBounds(20, 110, 100, 15);
    txtEmail.setBounds(20, 125, 235, 20);
    lblObEmail.setBounds(20, 145, 160, 15);

    lblPassword.setBounds(20, 170, 100, 15);
    txtPassword.setBounds(20, 185, 235, 20);
    lblObPassword.setBounds(10, 205, 255, 15);

    lblPassword2.setBounds(20, 230, 100, 15);
    txtRepPassword.setBounds(20, 245, 235, 20);
    lblObPassword2.setBounds(10, 265, 255, 15);

    for (JLabel jLabel : jLabelObli) {
      jLabel.setVisible(false);
      jLabel.setForeground(Color.red);
      jLabel.setFont(fuenteObligatoria);
    }

    cbVerPasswd.setBounds(20, 285, 230, 20);
    cbVerPasswd.setBackground(panel.getBackground());
    mostrarContraseña(txtPassword);
    mostrarContraseña(txtRepPassword);

    btnRegistro.setBounds(10, 315, 255, 25);
    btnRegistro.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              creacionUsuario();
            } catch (ParseException parseException) {
              parseException.printStackTrace();
            }
          }
        });

    getContentPane().add(panel);

    // Empaquetado, tamaño y visualizazion
    pack();
    setSize(285, 385);
    setVisible(true);
  }

  public void creacionUsuario() throws ParseException {
    if (obligatorios() == true) {
      if (existeUsuario() == false) {
        Document auth = new Document();
        auth.put("Nombre", txtUsuario.getText());
        auth.put("Email", txtEmail.getText());
        auth.put("Contraseña", txtPassword.getText());
        collecAuth.insertOne(auth);

        Document usuario = new Document();
        usuario.put("Nombre", txtUsuario.getText());
        usuario.put("Email", txtEmail.getText());
        usuario.put("fCreacionCuenta", new Date());
        usuario.put("NPrestados", 0);
        // usuario.put("Tema", "Claro");
        collecUsuario.insertOne(usuario);
        mensajeEmergente(1);
        panel.setVisible(false);
        dispose();
        intfzLogin.iniciar();
      } else {
        mensajeEmergente(2);
      }
    }
  }

  public boolean obligatorios() {
    contra1 = txtPassword.getText();
    contra2 = txtRepPassword.getText();
    int i = 0;
    obligatorios = true;
    if (!txtUsuario.getText().isEmpty()) {
    } else {
      lblObUsuario.setVisible(true);
      i++;
    }

    if (!txtEmail.getText().isEmpty()) {
    } else {
      lblObEmail.setVisible(true);
      i++;
    }
    if (txtPassword.getText().length() >= 2) {

    } else {
      lblObPassword.setVisible(true);
      i++;
    }

    if (contra1.equals(contra2)) {
    } else {
      lblObPassword2.setVisible(true);
      i++;
    }

    if (i == 0) {
      obligatorios = true;
    } else {
      obligatorios = false;
    }
    return obligatorios;
  }

  public boolean existeUsuario() {
    existe = false;
    Document existeReg =
        collecAuth
            .find(or(eq("Nombre", txtUsuario.getText()), eq("Email", txtEmail.getText())))
            .first();
    if (existeReg != null) {
      existe = true;
    }
    return existe;
  }

  public void rescribirCampo(JTextField jTextField, JLabel jLabel) {
    jTextField.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            jLabel.setVisible(false);
          }
        });
  }

  public void mostrarContraseña(JPasswordField jPasswordField) {
    cbVerPasswd.addItemListener(
        new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              jPasswordField.setEchoChar((char) 0);
              cbVerPasswd.setText("Ocultar Contraseñas");
            } else {
              jPasswordField.setEchoChar('*');
              cbVerPasswd.setText("Mostrar Contraseñas");
            }
          }
        });
  }

  public void mensajeEmergente(int mensaje) {
    if (mensaje == 1) {
      JOptionPane.showMessageDialog(
          null,
          "Usuario Registrado Correctamente",
          "Registro Completado",
          JOptionPane.INFORMATION_MESSAGE);
    } else if (mensaje == 2) {
      JOptionPane.showMessageDialog(
          null,
          "Ya existe un usuario con este Nombre o correo electronico",
          "Registro Fallido",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void crearComponentes() {
    for (JComponent jComponent : jComponentA) {
      panel.add(jComponent);
    }
  }
}
