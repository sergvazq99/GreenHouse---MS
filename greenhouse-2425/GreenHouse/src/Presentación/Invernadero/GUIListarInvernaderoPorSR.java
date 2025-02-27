package Presentacion.Invernadero;

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

import Negocio.Invernadero.TInvernadero;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUIListarInvernaderoPorSR extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField idText;
	private JPanel mainPanel;
	private JTable tabla;
	private JButton botonCancelar;

	public GUIListarInvernaderoPorSR() {
		super("Listar Invernaderos que implementan un Sistema de Riego");
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
		JLabel labelInvernadero = new JLabel("Ingrese el id del Sistema de Riego:");
		panelCentro.add(labelInvernadero);

		idText = new JTextField();
		idText.setPreferredSize(new Dimension(250, 30));
		panelCentro.add(idText);

		// Boton Buscar
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarPorSistemaRiego();
			}
		});
		panelCentro.add(botonBuscar);

		// Tabla
		String[] nombreColumnas = { "ID", "Nombre", "Sustrato", "Tipo de Iluminacion", "Activo" };
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
				GUIListarInvernaderoPorSR.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);
		mainPanel.add(panelBotones);

		this.setVisible(true);
	}

	private void buscarPorSistemaRiego() {
		String id = idText.getText().trim();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese un Sistemad e Riego.", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			int idSR = Integer.parseInt(id);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO, idSR));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID valido para el Sistema de Riego.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_OK) {
			Set<TInvernadero> invernaderos = (Set<TInvernadero>) context.getDatos();

			String[][] datos = new String[invernaderos.size()][7];
			int i = 0;
			for (TInvernadero invernadero : invernaderos) {
				String activo = invernadero.isActivo() ? "Si" : "No";
				datos[i++] = new String[] { String.valueOf(invernadero.getId()), invernadero.getNombre(),
						invernadero.getSustrato(), invernadero.getTipo_iluminacion(), activo

				};
			}
			tabla.setModel(new javax.swing.table.DefaultTableModel(datos,
					new String[] { "ID", "Nombre", "Sustrato", "Tipo de Iluminacion", "Activo" }));
			ComponentsBuilder.adjustColumnWidths(tabla);
		} else if (context.getEvento() == Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_KO) {
			JOptionPane.showMessageDialog(this, "Error al listar los Invernaderosd e un Sistema de Riego.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}