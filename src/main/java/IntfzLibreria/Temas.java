package IntfzLibreria;

import javax.swing.*;
import java.awt.*;

public class Temas extends JFrame {

  public static void cambioTema(
      String color,
      JPanel[] jPanelA,
      JLabel[] jlabelA,
      JTextField[] jTextFieldA,
      JButton[] jButtonA,
      /*JComboBox jComboBoxeA,*/
      JTable jTable,
      JList jList,
      JTextArea jTextArea) {

    JButton botoncolor = new JButton();
    JPanel panelcolor = new JPanel();
    JTable tablacolores = new JTable();
    Color colorFondoBoton = botoncolor.getBackground();
    Color colorFondoPanel = panelcolor.getBackground();
    Color colorHeaderTable = tablacolores.getTableHeader().getBackground();
    Color[] arrayColores = new Color[5];
    switch (color) {
      case "Claro":
        arrayColores[0] = colorFondoPanel;
        arrayColores[1] = Color.black;
        arrayColores[2] = Color.white;
        arrayColores[3] = colorFondoBoton;
        arrayColores[4] = colorHeaderTable;
        cambiarColorClaro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Oscuro":
        arrayColores[0] = Color.darkGray;
        arrayColores[1] = Color.white;
        arrayColores[2] = Color.gray;
        arrayColores[3] = Color.lightGray;
        arrayColores[4] = Color.blue;
        cambiarColorOscuro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Amarillo Oscuro":
        arrayColores[0] = Color.darkGray;
        arrayColores[1] = Color.yellow;
        arrayColores[2] = Color.gray;
        arrayColores[3] = Color.lightGray;
        arrayColores[4] = Color.blue;
        cambiarColorOscuro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Rojo Oscuro":
        arrayColores[0] = Color.darkGray;
        arrayColores[1] = Color.RED;
        arrayColores[2] = Color.gray;
        arrayColores[3] = Color.lightGray;
        arrayColores[4] = Color.blue;
        cambiarColorOscuro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Azul Oscuro":
        arrayColores[0] = Color.darkGray;
        arrayColores[1] = Color.BLUE;
        arrayColores[2] = Color.gray;
        arrayColores[3] = Color.lightGray;
        arrayColores[4] = Color.blue;
        cambiarColorOscuro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Verde Oscuro":
        arrayColores[0] = Color.darkGray;
        arrayColores[1] = Color.GREEN;
        arrayColores[2] = Color.gray;
        arrayColores[3] = Color.lightGray;
        arrayColores[4] = Color.blue;
        cambiarColorOscuro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Naranja Claro":
        arrayColores[0] = colorFondoPanel;
        arrayColores[1] = Color.orange;
        arrayColores[2] = Color.white;
        arrayColores[3] = colorFondoBoton;
        arrayColores[4] = colorHeaderTable;
        cambiarColorClaro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Rojo Claro":
        arrayColores[0] = colorFondoPanel;
        arrayColores[1] = Color.RED;
        arrayColores[2] = Color.white;
        arrayColores[3] = colorFondoBoton;
        arrayColores[4] = colorHeaderTable;
        cambiarColorClaro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Verde Claro":
        arrayColores[0] = colorFondoPanel;
        arrayColores[1] = Color.GREEN;
        arrayColores[2] = Color.white;
        arrayColores[3] = colorFondoBoton;
        arrayColores[4] = colorHeaderTable;
        cambiarColorClaro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
      case "Azul Claro":
        arrayColores[0] = colorFondoPanel;
        arrayColores[1] = Color.blue;
        arrayColores[2] = Color.white;
        arrayColores[3] = colorFondoBoton;
        arrayColores[4] = colorHeaderTable;
        cambiarColorClaro(
            jPanelA, jlabelA, jTextFieldA, jButtonA, jTable, jList, jTextArea, arrayColores);
        break;
    }
  }

  public static void cambiarColorClaro(
      JPanel[] jPanelA,
      JLabel[] jlabelA,
      JTextField[] jTextFieldA, /*JComboBox[] jComboBoxeA,*/
      JButton[] jButtonA,
      JTable jTable,
      JList jList,
      JTextArea jTextArea,
      Color[] colores) {
    if (null != jPanelA)
      for (JPanel jPanel : jPanelA) {
        jPanel.setBackground(colores[0]);
      }

    if (null != jlabelA)
      for (JLabel jLabel : jlabelA) {
        jLabel.setForeground(colores[1]);
      }
    if (null != jTextFieldA)
      for (JTextField jTextField : jTextFieldA) {
        jTextField.setForeground(colores[1]);
        jTextField.setBackground(colores[2]);
      }
    if (null != jButtonA)
      for (JButton jbutton : jButtonA) {
        jbutton.setForeground(colores[1]);
        jbutton.setBackground(colores[3]);
      }
    if (null == jTable) {
    } else {
      jTable.setForeground(colores[1]);
      jTable.getTableHeader().setForeground(colores[1]);
      jTable.getTableHeader().setBackground(colores[4]);
      jTable.setBackground(colores[2]);
    }

    if (null == jTextArea) {
    } else {
      jTextArea.setForeground(colores[1]);
      jTextArea.setBackground(colores[0]);
    }
    if (null == jList) {
    } else {
      jList.setForeground(colores[1]);
      jTextArea.setBackground(colores[0]);
    }
  }

  public static void cambiarColorOscuro(
      JPanel[] jPanelA,
      JLabel[] jlabelA,
      JTextField[] jTextFieldA, /*JComboBox[] jComboBoxeA,*/
      JButton[] jButtonA,
      JTable jTable,
      JList jList,
      JTextArea jTextArea,
      Color[] colores) {
    if (null != jPanelA)
      for (JPanel jPanel : jPanelA) {
        jPanel.setBackground(colores[0]);
      }

    if (null != jlabelA)
      for (JLabel jLabel : jlabelA) {
        jLabel.setForeground(colores[1]);
      }
    if (null != jTextFieldA)
      for (JTextField jTextField : jTextFieldA) {
        jTextField.setForeground(colores[1]);
        jTextField.setBackground(colores[2]);
      }
    if (null != jButtonA)
      for (JButton jbutton : jButtonA) {
        jbutton.setForeground(colores[1]);
        jbutton.setBackground(colores[2]);
      }
    if (null == jTable) {
    } else {
      jTable.setForeground(colores[1]);
      jTable.getTableHeader().setForeground(colores[1]);
      jTable.getTableHeader().setBackground(colores[2]);
      jTable.setBackground(colores[3]);
    }

    if (null == jTextArea) {
    } else {
      jTextArea.setForeground(colores[1]);
      jTextArea.setBackground(colores[0]);
    }
    if (null == jList) {
    } else {
      jList.setForeground(colores[1]);
      jList.setBackground(colores[0]);
    }
  }
}
