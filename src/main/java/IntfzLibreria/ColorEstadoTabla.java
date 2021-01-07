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

    if (columIndex == 0) {

      String estado = this.getValueAt(rowIndex, columIndex).toString();

      if (estado.equals("Leyendo")) {
        tablaColor.setBackground(new Color(64, 161, 67));
        tablaColor.setForeground(new Color(64, 161, 67));
      } else if (estado.equals("Terminado")) {
        tablaColor.setBackground(Color.BLUE);
        tablaColor.setForeground(Color.BLUE);
      } else if (estado.equals("XinTerminar")) {
        tablaColor.setBackground(Color.RED);
        tablaColor.setForeground(Color.RED);
      } else if (estado.equals("Pendiente")) {
        tablaColor.setBackground(Color.GRAY);
        tablaColor.setForeground(Color.GRAY);
      }
    } else {
      tablaColor.setBackground(intfzBiblioteca.panelBiblioteca.getBackground());
      tablaColor.setForeground(intfzBiblioteca.panelBiblioteca.getForeground());
    }
    return tablaColor;
  }
}
