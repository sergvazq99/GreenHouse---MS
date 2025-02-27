package Presentacion.Fabricante;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteLocal;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIListarFabricantes extends JFrame implements IGUI {

	public GUIListarFabricantes(List<TFabricante> listaFabricantes) {
		super("Mostrar todos los Fabricantes");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 800;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI(listaFabricantes);

	}

	public void initGUI(List<TFabricante> listaFabricantes) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Tabla
		String[] nombreColumnas = { "ID", "Nombre", "Cod. Fabricante", "Teléfono", "Tipo", "Activo" };
		String[][] tablaDatos = new String[listaFabricantes.size()][nombreColumnas.length];

		int i = 0;
		for (TFabricante sistema : listaFabricantes) {
			tablaDatos[i][0] = sistema.getId().toString();
			tablaDatos[i][1] = sistema.getNombre();
			tablaDatos[i][2] = sistema.getCodFabricante();
			tablaDatos[i][3] = sistema.getTelefono();
			tablaDatos[i][4] = (sistema instanceof TFabricanteLocal) ? "Local" : "Extranjero";
			tablaDatos[i][5] = sistema.getActivo() ? "Sí" : "No";
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

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(a -> {
			GUIListarFabricantes.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.FABRICANTE_VISTA, null));
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.LISTAR_FABRICANTES_OK) {
			JOptionPane.showMessageDialog(this, "Fabricantes listados correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.LISTAR_FABRICANTES_KO) {
			JOptionPane.showMessageDialog(this, "Error al tratar de listar los Fabricantes", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}