package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.MarcaJPA.TMarca;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;


public class GUIListaMarcasPorProveedor extends JFrame implements IGUI {


	
	private static final long serialVersionUID = 1L;

	private JLabel labelProveedor;

	private JButton botonBuscar;

	private JTextField fieldProveedor;

	private JPanel mainPanel;
	
	private JTable tabla;
	
	private JDialog jDialog;

	public GUIListaMarcasPorProveedor() {
        super("Listar marcas por proveedor");
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

        // Campo de marca para el proveedor
        labelProveedor = new JLabel("Ingrese el id del proveedor:");
        panelCentro.add(labelProveedor);
        
        fieldProveedor = new JTextField();
        fieldProveedor.setPreferredSize(new Dimension(250, 30));
        panelCentro.add(fieldProveedor);

        // Boton Buscar
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorProveedor();
            }
        });
        panelCentro.add(botonBuscar);
        
        // Tabla
		String[] nombreColumnas = { "ID","Nombre","Pais de origen","Activo" };
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
                GUIListaMarcasPorProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);
        mainPanel.add(panelBotones);

        this.setVisible(true);
        
	}
	
	
	private void buscarPorProveedor() {
		String proveedor = fieldProveedor.getText().trim();
		
		if(proveedor.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Ingrese un proveedor", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			int idProveedor = Integer.parseInt(proveedor);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_MARCAS_POR_PROVEEDOR, idProveedor));
			
		}catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un numero valido para el ID de proveedor", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	

	public void actualizar(Context context) {

		if(context.getEvento() == Evento.LISTAR_MARCAS_POR_PROVEEDOR_OK) {
			@SuppressWarnings("unchecked")
			Set<TMarca> marcas = (Set<TMarca>) context.getDatos();
			String[][] datos = new String[marcas.size()][3];
			int i = 0;
			
			for(TMarca marca : marcas) {
				datos[i++] = new String[] {
						String.valueOf(marca.getId()),
						String.valueOf(marca.getNombre()),
						String.valueOf(marca.getPais()),
						marca.getActivo() ? "Si" : "No"
				};
			}
			
			tabla.setModel(new DefaultTableModel(datos, new String[] {"ID", "Nombre", "Pais de origen", "Activo"} ));
			ComponentsBuilder.adjustColumnWidths(tabla);
			
		} else if(context.getEvento() == Evento.LISTAR_MARCAS_POR_PROVEEDOR_KO) {
            JOptionPane.showMessageDialog(this, "No hay ninguna marca vinculada al proveedor indicado", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}