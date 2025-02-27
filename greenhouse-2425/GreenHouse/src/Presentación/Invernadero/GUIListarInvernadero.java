package Presentacion.Invernadero;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import Negocio.Invernadero.TInvernadero;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIListarInvernadero extends JFrame implements IGUI {

	public GUIListarInvernadero(Set<TInvernadero> datos) {
		super("Mostrar todos los Invernaderos");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 800;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniGUI(datos);
	}

	public void iniGUI(Set<TInvernadero> datos) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Tabla
		String[] nombreColumnas = { "ID", "Nombre", "Sustrato", "Tipo de Iluminacion", "Activo"};
		String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

		int i = 0;
		for (TInvernadero invernadero : datos) {
			tablaDatos[i][0] = invernadero.getId().toString();
			tablaDatos[i][1] = invernadero.getNombre();
			tablaDatos[i][2] = invernadero.getSustrato();
			tablaDatos[i][3] = invernadero.getTipo_iluminacion();
			tablaDatos[i][4] = invernadero.isActivo() ? "Si" : "No";

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
				GUIListarInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}

	@Override
	public void actualizar(Context context) {

	}
}