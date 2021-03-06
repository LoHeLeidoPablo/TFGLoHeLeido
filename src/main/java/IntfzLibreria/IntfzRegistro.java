package IntfzLibreria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class IntfzRegistro extends JFrame {

  MongoClientURI uri =
      new MongoClientURI(
          "mongodb+srv://AdminUser:iReadIt@loheleido.idhnu.mongodb.net/LoHeLeidoDB?retryWrites=true&w=majority");

  MongoClient mongoClient = new MongoClient(uri);
  MongoDatabase DDBB = mongoClient.getDatabase("LoHeLeidoDB");
  MongoCollection<Document> collecAuth = DDBB.getCollection("Auth");
  MongoCollection<Document> collecUsuario = DDBB.getCollection("Usuario");

  JPanel panelRegistro = new JPanel();

  JLabel lblTituloProyecto = new JLabel("Unete a Nosotros");
  JLabel lblUsuario = new JLabel("Usuario:");
  JLabel lblObUsuario = new JLabel("Obligatorio");
  JLabel lblPassword = new JLabel("Contraseña:");
  JLabel lblObPassword = new JLabel("La contraseña debe contener al menos 8 caracteres");
  JLabel lblPassword2 = new JLabel("Repertir Contraseña:");
  JLabel lblObPassword2 = new JLabel("La contraseña debe coincidir con la otra contraseña");

  JTextField txtUsuario = new JTextField("");
  JPasswordField txtPassword = new JPasswordField("");
  JPasswordField txtRepPassword = new JPasswordField("");
  JButton btnRegistro = new JButton("Registrar");

  String contra1;
  String contra2;
  Boolean existe;
  Boolean obligatorios;

  JCheckBox cbVerPasswd = new JCheckBox("Mostrar Contraseñas");
  JLabel[] jLabelObli = {lblObUsuario, lblObPassword, lblObPassword2};
  JComponent[] jComponentA = {
    lblTituloProyecto,
    lblUsuario,
    lblPassword,
    lblPassword,
    lblPassword2,
    lblObUsuario,
    lblObPassword,
    lblObPassword2,
    btnRegistro,
    txtUsuario,
    txtPassword,
    txtRepPassword,
    cbVerPasswd
  };

  Font fuenteObligatoria = new Font(lblUsuario.getFont().getFamily(), Font.ITALIC, 9);

  public IntfzRegistro() {
    setIconImage(new ImageIcon("src/main/resources/appIcon.png").getImage());
    this.setResizable(false);
    this.setLocation(100, 100);
  }

  public void iniciar() {
    setTitle("Registrar - ¿Lo he leído?");
    getContentPane().setLayout(new GridLayout(1, 10));
    crearComponentes();
    mostrarContraseña(txtPassword);
    mostrarContraseña(txtRepPassword);
    rescribirCampo(txtUsuario, lblObUsuario);
    rescribirCampo(txtPassword, lblObPassword);
    rescribirCampo(txtRepPassword, lblObPassword2);

    panelRegistro.setLayout(null);

    lblTituloProyecto.setBounds(10, 10, 275, 25);
    Font fuentelogin = new Font("Bookman Old Style", 3, 22);
    lblTituloProyecto.setHorizontalAlignment(SwingConstants.CENTER);
    lblTituloProyecto.setFont(fuentelogin);

    lblUsuario.setBounds(20, 50, 100, 15);
    txtUsuario.setBounds(20, 65, 235, 20);
    lblObUsuario.setBounds(20, 85, 160, 15);

    lblPassword.setBounds(20, 110, 100, 15);
    txtPassword.setBounds(20, 125, 235, 20);
    lblObPassword.setBounds(20, 145, 235, 15);

    lblPassword2.setBounds(20, 170, 200, 15);
    txtRepPassword.setBounds(20, 185, 235, 20);
    lblObPassword2.setBounds(20, 205, 235, 15);

    for (JLabel jLabel : jLabelObli) {
      jLabel.setVisible(false);
      jLabel.setForeground(Color.red);
      jLabel.setFont(fuenteObligatoria);
    }

    cbVerPasswd.setBounds(20, 235, 230, 20);
    cbVerPasswd.setBackground(panelRegistro.getBackground());
    mostrarContraseña(txtPassword);
    mostrarContraseña(txtRepPassword);

    btnRegistro.setBounds(7, 260, 255, 25);

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

    // Empaquetado, tamaño y visualizazion
    getContentPane().add(panelRegistro);
    pack();
    setSize(285, 335);
    setVisible(true);
  }

  // Guarda los datos del usuario en la based de datos cada uno con sus datos correspondientes
  public void creacionUsuario() throws ParseException {
    if (obligatorios() == true) {
      if (existeUsuario() == false) {
        Document auth = new Document();
        auth.put("Nombre", txtUsuario.getText());
        auth.put("Contraseña", txtPassword.getText());
        collecAuth.insertOne(auth);

        Document usuario = new Document();
        usuario.put("Nombre", txtUsuario.getText());
        usuario.put("fCreacionCuenta", new Date());
        collecUsuario.insertOne(usuario);
        mensajeEmergente(1);
        panelRegistro.setVisible(false);
        dispose();
      } else {
        mensajeEmergente(2);
      }
    }
  }

  // Se asegura que los campos obligatorios se cumplan
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

    if (txtPassword.getText().length() >= 8) {
    } else {
      lblObPassword.setVisible(true);
      i++;
    }

    if (contra1.equals(contra2)) {
    } else {
      lblObPassword2.setVisible(true);
      i++;
    }

    obligatorios = i == 0 ? true : false;

    return obligatorios;
  }

  // Comprueba si ya existe un usuario con ese nombre
  public boolean existeUsuario() {
    existe = false;
    Document existeReg = collecAuth.find(eq("Nombre", txtUsuario.getText())).first();
    if (existeReg != null) existe = true;

    return existe;
  }

  // Elimina el mensaje de incumplimeiento de los parametros obligatorios
  public void rescribirCampo(JTextField jTextField, JLabel jLabel) {
    jTextField.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            jLabel.setVisible(false);
          }
        });
  }

  // Hace visible o invisible la contraseña
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
          "Ya existe un usuario con este Nombre",
          "Usuario ya existente",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void crearComponentes() {
    for (JComponent jComponent : jComponentA) {
      panelRegistro.add(jComponent);
    }
    panelRegistro.setBackground(new Color(232, 218, 189));
  }
}
