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

public class GUIListarEmpleadoDeCajaPorTurno extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    private JTextField turnoText;
    private JTable tabla;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private String[] nombreColumnas = { 
            "ID", "Nombre", "Apellido", "DNI", "Teléfono", "Sueldo", "ID Turno", "Activo", "Tipo",
            "Horas", "Precio Hora", "Sueldo Base", "Complementos" 
        };

    public GUIListarEmpleadoDeCajaPorTurno() {
        super("Listar Empleados de Caja por Turno");
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

        // Título
        JLabel msgIntroTurnoCabecera = ComponentsBuilder.createLabel("Seleccione el turno que desea", 1, 10, 80, 20, Color.BLACK);
        msgIntroTurnoCabecera.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(msgIntroTurnoCabecera);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de entrada
        JPanel panelTurno = new JPanel();
        panelTurno.setLayout(new BoxLayout(panelTurno, BoxLayout.X_AXIS));
        mainPanel.add(panelTurno);

        JLabel labelTurno = ComponentsBuilder.createLabel("Ingrese el ID del turno:", 10, 100, 80, 20, Color.BLACK);
        panelTurno.add(labelTurno);

        panelTurno.add(Box.createRigidArea(new Dimension(10, 0)));

        turnoText = new JTextField();
        turnoText.setMaximumSize(new Dimension(250, 30));
        panelTurno.add(turnoText);

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (turnoText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(GUIListarEmpleadoDeCajaPorTurno.this,
                            "Por favor, inserta un ID de turno.", "ERROR", JOptionPane.WARNING_MESSAGE);
                } else {
                    ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_TURNO, Integer.parseInt(turnoText.getText())));
                }
            }
        });
        panelTurno.add(botonBuscar);

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
                GUIListarEmpleadoDeCajaPorTurno.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_TURNO_OK) {
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
        } else if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_TURNO_KO) {
            JOptionPane.showMessageDialog(this, "No existen empleados para el turno indicado.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error inesperado al listar empleados por turno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
