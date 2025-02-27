package Presentacion.SistemaDeRiego;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.FactoriaVistas.Evento;


@SuppressWarnings("serial")
public class GUIListarSistemaDeRiegoDelInvernadero extends JFrame implements IGUI {
    private JTextField invernaderoField;
    private JPanel mainPanel;
    private JTable tabla;
    private JButton botonCancelar;

    public GUIListarSistemaDeRiegoDelInvernadero() {
        super("Listar Sistemas de Riego del Invernadero");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 800;
        int alto = 400;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        // Panel Central
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(panelCentro);

        // Campo de entrada para el invernadero
        JLabel labelInvernadero = new JLabel("Ingrese el id del invernadero:");
        panelCentro.add(labelInvernadero);
        
        invernaderoField = new JTextField();
        invernaderoField.setPreferredSize(new Dimension(250, 30));
        panelCentro.add(invernaderoField);

        // Boton Buscar
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorInvernadero();
            }
        });
        panelCentro.add(botonBuscar);

        // Tabla 
        String[] nombreColumnas = { "ID", "Nombre", "Potencia Riego", "Cantidad Agua", "Frecuencia", "Activo", "Fabricante"};
        tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, null); 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(750, 250));
        mainPanel.add(scroll);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIListarSistemaDeRiegoDelInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.SISTEMA_RIEGO_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);
        mainPanel.add(panelBotones);

        this.setVisible(true);
    }

    private void buscarPorInvernadero() {
        String invernadero = invernaderoField.getText().trim();
        if (invernadero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un invernadero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idInvernadero = Integer.parseInt(invernadero); 
            ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_SISTEMAS_RIEGO_DE_INVERNADERO, idInvernadero));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido para el ID del invernadero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_SISTEMA_DE_RIEGO_DE_INVERNADERO_OK) {
            @SuppressWarnings("unchecked")
			Set<TSistemaDeRiego> sistemas = (Set<TSistemaDeRiego>) context.getDatos();
          
            String[][] datos = new String[sistemas.size()][7];
            int i = 0;
            for (TSistemaDeRiego sistema : sistemas) {
                datos[i++] = new String[]{
                    String.valueOf(sistema.getId()),
                    sistema.getNombre(),
                    String.valueOf(sistema.getPotenciaRiego()),
                    String.valueOf(sistema.getCantidad_agua()),
                    String.valueOf(sistema.getFrecuencia()),
                    sistema.getActivo() ? "Si" : "No",
                    String.valueOf(sistema.getIdFabricante()),
                             	
                };
            }
            tabla.setModel(new javax.swing.table.DefaultTableModel(datos, new String[] { "ID", "Nombre", "Potencia Riego", "Cantidad Agua", "Frecuencia", "Activo", "Fabricante"}));
            ComponentsBuilder.adjustColumnWidths(tabla);
        } else if (context.getEvento() == Evento.LISTAR_SISTEMA_DE_RIEGO_DE_INVERNADERO_KO) {
            JOptionPane.showMessageDialog(this, "Error al listar sistemas de riego del invernadero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
