package Presentacion.Invernadero;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

@SuppressWarnings("serial")
public class GUITresFechasMasVendidas extends JFrame implements IGUI {
	private JTextField idText;
	private JPanel mainPanel;
	private JTable tabla;
	private JButton botonCancelar;

	public GUITresFechasMasVendidas() {
		super("Listar las tres fechas más vendidas de un invernadero");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 800;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniGUI();
	}

	public void iniGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		// Panel Central
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(panelCentro);

		// Campo de entrada para el invernadero
		JLabel labelInvernadero = new JLabel("Ingrese el id del Invernadero:");
		panelCentro.add(labelInvernadero);

		idText = new JTextField();
		idText.setPreferredSize(new Dimension(250, 30));
		panelCentro.add(idText);

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
		String[] nombreColumnas = { "Id", "Fecha" };
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
				GUITresFechasMasVendidas.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);
		mainPanel.add(panelBotones);

		this.setVisible(true);
	}

	private void buscarPorInvernadero() {
		String id = idText.getText().trim();
		try {
			if (Integer.parseInt(id) <= 0) {
				JOptionPane.showMessageDialog(this, "Error: Los IDs tienen que ser mayores o iguales que 0.", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			int idSR = Integer.parseInt(id);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO, idSR));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID valido para el Invernadero.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO_OK) {
			Set<Date> fechas = (Set<Date>) context.getDatos();

			String[][] datos = new String[fechas.size()][2];
			int i = 0;

			for (Date fecha : fechas) {
				datos[i][0] = String.valueOf(i + 1);
				datos[i][1] = fecha.toString();
				i++;
			}
			tabla.setModel(new javax.swing.table.DefaultTableModel(datos, new String[] { "Id", "Fecha" }));
			ComponentsBuilder.adjustColumnWidths(tabla);
		} else if (context.getEvento() == Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO_KO) {
			JOptionPane.showMessageDialog(this, "Error al listar las tres fechas más vendidas de un invernadero.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
