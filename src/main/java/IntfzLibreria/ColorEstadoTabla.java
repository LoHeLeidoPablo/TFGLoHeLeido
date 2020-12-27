package IntfzLibreria;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorEstadoTabla extends JTable {

  IntfzBiblioteca intfzBiblioteca = new IntfzBiblioteca();

  @Override
  public Component prepareRenderer(
      TableCellRenderer tableCellRenderer, int rowIndex, int columIndex) {

    Component tablaColor = super.prepareRenderer(tableCellRenderer, rowIndex, columIndex);

    if (getValueAt(rowIndex, columIndex).getClass().equals(String.class) && columIndex == 0) {

      String estado = this.getValueAt(rowIndex, columIndex).toString();

      if (estado.equals("Leyendo")) {
        tablaColor.setBackground(Color.GREEN);
        tablaColor.setForeground(Color.GREEN);
      } else if (estado.equals("Leído")) {
        tablaColor.setBackground(Color.BLUE);
        tablaColor.setForeground(Color.BLUE);
      } else if (estado.equals("Abandonado")) {
        tablaColor.setBackground(Color.RED);
        tablaColor.setForeground(Color.RED);
      } else if (estado.equals("Quiero Leer")) {
        tablaColor.setBackground(Color.GRAY);
        tablaColor.setForeground(Color.GRAY);
      }
    } else {
      tablaColor.setBackground(intfzBiblioteca.panel.getBackground());
      tablaColor.setForeground(intfzBiblioteca.panel.getForeground());
    }

    return tablaColor;
  }
}
