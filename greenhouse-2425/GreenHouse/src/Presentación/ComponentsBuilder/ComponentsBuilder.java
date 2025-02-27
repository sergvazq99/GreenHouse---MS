package Presentacion.ComponentsBuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ComponentsBuilder {
    
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 14);  // Fuente moderna
    
    

	public static void ajustarAnchoColumnas(JTable tabla) {
    	TableColumnModel modeloColumnas = tabla.getColumnModel();
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            int ancho = 0;

            // Inicializar anchoEncabezado antes de usarlo
            int anchoEncabezado = tabla.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(tabla, tabla.getColumnName(i), false, false, -1, i)
                .getPreferredSize().width;

            // Actualizar ancho para incluir el encabezado
            ancho = Math.max(ancho, anchoEncabezado);

            // Calcular el ancho de cada celda en la columna
            for (int j = 0; j < tabla.getRowCount(); j++) {
                int anchoCelda = tabla.getCellRenderer(j, i)
                    .getTableCellRendererComponent(tabla, tabla.getValueAt(j, i), false, false, j, i)
                    .getPreferredSize().width;
                ancho = Math.max(ancho, anchoCelda);
            }

            modeloColumnas.getColumn(i).setPreferredWidth(ancho + 10); // A�adir un margen extra
        }
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public static JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(DEFAULT_FONT);
        button.setBackground(new Color(33, 150, 243));  // Azul moderno
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(createRoundedBorder());
        button.setOpaque(true);  // Para resaltar los botones
        return button;
    }

    public static JButton createBackButton() {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon("imagen/back.png");
        Image newImg = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        button.setIcon(icon);
        button.setBounds(40, 25, 80, 60);
        button.setToolTipText("Atrás");
        button.setBackground(new Color(220, 220, 220));
        button.setFocusPainted(false);
        button.setBorder(createRoundedBorder());
        button.setText("←");
        return button;
    }

    public static JPanel createPanel(int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(new Color(250, 250, 250));  // Fondo m�s claro para contraste
        return panel;
    }

    public static JLabel createLabel(String text, int x, int y, int width, int height, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(color);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 18));  // Fuente m�s grande para mejor visibilidad
        return label;
    }


    public static JTable createTable(int filas, int columnas, String[] columns, String[][] datos) {
    	DefaultTableModel model = new DefaultTableModel(datos, columns) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public String getColumnName(int index) {
                return columns[index];
            }
        };

        JTable table = new JTable(model);

        table.setFont(DEFAULT_FONT);
        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);

        adjustColumnWidths(table);

        return table;
    }
    
    public static void adjustColumnWidths(JTable table) {
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            int width = 0;
            for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
                TableCellRenderer renderer = table.getCellRenderer(rowIndex, columnIndex);
                Component comp = table.prepareRenderer(renderer, rowIndex, columnIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }
            table.getColumnModel().getColumn(columnIndex).setPreferredWidth(width + 10);
        }
    }

    
    private static Border createRoundedBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        );
    }
}
