package Presentacion.Entrada;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.Entrada.TEntrada;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUILIstarEntradasPorInvernadero extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JLabel labelInvernadero;

	private JButton botonBuscar;

	private JTextField fieldInvernadero;

	private JPanel mainPanel;
	
	private JTable tabla;

	
	public GUILIstarEntradasPorInvernadero() {
        super("Listar entradas por invernadero");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 630;
		int alto = 330;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}
	
	public void initGUI() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        // Panel Central
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        mainPanel.add(panelCentro);

        // Campo de entrada para el invernadero
        labelInvernadero = new JLabel("Ingrese el id del invernadero:");
        panelCentro.add(labelInvernadero);
        
        fieldInvernadero = new JTextField();
        fieldInvernadero.setPreferredSize(new Dimension(250, 30));
        panelCentro.add(fieldInvernadero);

        // Boton Buscar
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorInvernadero();
            }
        });
        panelCentro.add(botonBuscar);
        
        // Tabla
		String[] nombreColumnas = { "ID","Fecha","Precio","Stock","Activo", "Id Invernadero" };
		tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, null); 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(750, 250));
        mainPanel.add(scroll);
        
        // Botón cancelar
        JPanel panelBotones = new JPanel();
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILIstarEntradasPorInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);
        mainPanel.add(panelBotones);

        this.setVisible(true);
        
        
	}

	
	private void buscarPorInvernadero() {
		String invernadero = fieldInvernadero.getText().trim();
		
		if(invernadero.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Ingrese un invernadero", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;

		}
		
		try {
			int idInvernadero = Integer.parseInt(invernadero);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_ENTRADAS_POR_INVERNADERO, idInvernadero));
			
		}catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un numero valido para el ID de invernadero", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}

	@Override
	public void actualizar(Context context) {
		if(context.getEvento() == Evento.LISTAR_ENTRADAS_POR_INVERNADERO_OK) {
			@SuppressWarnings("unchecked")
			Set<TEntrada> entradas = (Set<TEntrada>) context.getDatos();
			String[][] datos = new String[entradas.size()][5];
			int i = 0;
			
			for(TEntrada entrada : entradas) {
				datos[i++] = new String[] {
						String.valueOf(entrada.getId()),
						String.valueOf(entrada.getIdInvernadero()),
						String.valueOf(entrada.getFecha()),
						String.valueOf(entrada.getPrecio()),
						String.valueOf(entrada.getStock()),
						entrada.getActivo() ? "Si" : "No"
				};
			}
			
			tabla.setModel(new DefaultTableModel(datos, new String[] {"ID", "ID invernadero", "Fecha", "Precio", "Stock", "Activo"} ));
			ComponentsBuilder.adjustColumnWidths(tabla);
			
		} else if(context.getEvento() == Evento.LISTAR_ENTRADAS_POR_INVERNADERO_KO) {
            JOptionPane.showMessageDialog(this, "Error al listar entradas", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		

	}
}