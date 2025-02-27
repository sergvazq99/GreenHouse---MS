package Presentacion.EmpleadoDeCajaJPA;

import javax.swing.*;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoParcial;

public class GUIListarEmpleadoDeCajaPorNombre extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JTextField nombreText;
    private JPanel mainPanel;
    private JTable tabla;
    private JScrollPane scrollPane;
    private String[] nombreColumnas = { 
            "ID", "Nombre", "Apellido", "DNI", "Teléfono", "Sueldo", "ID Turno", "Activo", 
            "Tipo", "Horas", "Precio Hora", "Sueldo Base", "Complementos" 
        };
    

    public GUIListarEmpleadoDeCajaPorNombre() {
        super("Listar Empleados de Caja por Nombre");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 1500;
        int alto = 600;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setResizable(false);
        initGUI();
    }

    public void initGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Encabezado
        JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Seleccione el nombre que desea", 1, 10, 80, 20, Color.BLACK);
        msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(msgIntroIDCabecera);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de entrada
        JPanel panelNombre = new JPanel();
        panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.X_AXIS));
        mainPanel.add(panelNombre);

        JLabel labelNombre = ComponentsBuilder.createLabel("Ingrese el nombre del empleado:", 10, 100, 80, 20, Color.BLACK);
        panelNombre.add(labelNombre);

        panelNombre.add(Box.createRigidArea(new Dimension(10, 0)));

        nombreText = new JTextField();
        nombreText.setMaximumSize(new Dimension(250, 30));
        panelNombre.add(nombreText);

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nombreText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(GUIListarEmpleadoDeCajaPorNombre.this,
                            "Por favor, inserta un nombre.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    ApplicationController.getInstance().manageRequest(
                            new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE, nombreText.getText()));
                }
            }
        });
        panelNombre.add(botonBuscar);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Inicializar tabla
        String[][] datosIniciales = new String[0][nombreColumnas.length];
        tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, datosIniciales);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(950, 400));
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        mainPanel.add(panelBotones);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIListarEmpleadoDeCajaPorNombre.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_OK) {
            @SuppressWarnings("unchecked")
            Set<TEmpleadoDeCaja> listaEmpleados = (Set<TEmpleadoDeCaja>) context.getDatos();

            String[][] datos = new String[listaEmpleados.size()][nombreColumnas.length];
            int i = 0;
            for (TEmpleadoDeCaja empleado : listaEmpleados) {
                if (empleado instanceof TEmpleadoParcial) {
                    TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
                    datos[i++] = new String[] {
                            String.valueOf(parcial.getID()),
                            parcial.getNombre(),
                            parcial.getApellido(),
                            parcial.getDNI(),
                            String.valueOf(parcial.getTelefono()),
                            String.valueOf(parcial.getSueldo()),
                            String.valueOf(parcial.getId_Turno()),
                            parcial.getActivo() ? "Sí" : "No",
                            "Parcial",
                            String.valueOf(parcial.getHoras()),
                            String.valueOf(parcial.getPrecio_h()),
                            "N/A",
                            "N/A"
                    };
                } else if (empleado instanceof TEmpleadoCompleto) {
                    TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
                    datos[i++] = new String[] {
                            String.valueOf(completo.getID()),
                            completo.getNombre(),
                            completo.getApellido(),
                            completo.getDNI(),
                            String.valueOf(completo.getTelefono()),
                            String.valueOf(completo.getSueldo()),
                            String.valueOf(completo.getId_Turno()),
                            completo.getActivo() ? "Sí" : "No",
                            "Completo",
                            "N/A",
                            "N/A",
                            String.valueOf(completo.getSueldo_Base()),
                            String.valueOf(completo.getComplementos())
                    };
                }
            }

            tabla.setModel(new javax.swing.table.DefaultTableModel(datos, nombreColumnas));
        } else if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_KO) {
            JOptionPane.showMessageDialog(this, "No existen empleados con el nombre especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error inesperado al listar empleados por nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
}
}
