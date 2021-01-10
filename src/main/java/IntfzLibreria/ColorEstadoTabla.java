package IntfzLibreria;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorEstadoTabla extends JTable {

  /**
   * Esta clase se encarga de pintar la primera columna de la JTabla de IntfzBiblioteca Dependiendo
   * del estado de los libros se pintan de un color u otro Coloreamos tambien la letra para hacerlo
   * "invisible" y que solo quede el el rectangulo liso
   */
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
      // En caso de no pertenercer a ningun estado lo deja del color del panel de la IntfzBiblioteca
      tablaColor.setBackground(intfzBiblioteca.panelBiblioteca.getBackground());
      tablaColor.setForeground(intfzBiblioteca.panelBiblioteca.getForeground());
    }
    return tablaColor;
  }
}
