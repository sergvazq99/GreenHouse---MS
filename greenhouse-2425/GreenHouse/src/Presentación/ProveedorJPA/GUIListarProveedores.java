/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JTable;

import Negocio.ProveedorJPA.TProveedor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class GUIListarProveedores extends JFrame implements IGUI {
	
	public GUIListarProveedores(Set<TProveedor> datos) {
		super("Mostrar todos los Proveedores");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 800;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI(datos);
	}

	public void initGUI(Set<TProveedor> datos) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Tabla
		String[] nombreColumnas = { "ID", "Nombre", "CIF", "Telefono", "Activo"};
		String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

		int i = 0;
		for (TProveedor proveedor : datos) {
			tablaDatos[i][0] = proveedor.getId().toString();
			tablaDatos[i][1] = proveedor.getNombre();
			tablaDatos[i][2] = proveedor.getCIF();
			tablaDatos[i][3] = proveedor.getTelefono();
			tablaDatos[i][4] = proveedor.getActivo() ? "Si" : "No";

			i++;
		}

		JTable tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, tablaDatos);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(750, 250));
		mainPanel.add(scroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel de botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonCancelar = new JButton("Volver");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIListarProveedores.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}
	
	public void actualizar(Context context) {

	}
}