package Presentacion.Fabricante;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteLocal;

@SuppressWarnings("serial")
public class GUIListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero extends JFrame implements IGUI {

	private Set<TFabricante> listaFabricantes;
	private String[] nombreColumnas = { "ID", "Nombre", "Cod. Fabricante", "Teléfono", "Tipo" };
	private JTextField idText;
	private JPanel mainPanel;
	private JTable tabla;
	private JButton botonCancelar;

	public GUIListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero() {
		super("Mostrar Fabricante");
		listaFabricantes = new HashSet<TFabricante>();
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
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

				if (idText.getText().isEmpty())
					JOptionPane.showMessageDialog(GUIListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero.this,
							"Por favor, ingrese un id.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				else {
					ApplicationController.getInstance()
							.manageRequest(new Context(
									Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO,
									Integer.parseInt(idText.getText())));
					idText.setText("");
				}
			}
		});
		panelCentro.add(botonBuscar);

		// Tabla
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
				GUIListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.FABRICANTE_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);
		mainPanel.add(panelBotones);

		this.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO_OK) {

			listaFabricantes = (Set<TFabricante>) context.getDatos();
			String[][] tablaDatos = new String[listaFabricantes.size()][nombreColumnas.length];

			int i = 0;
			for (TFabricante sistema : listaFabricantes) {
				tablaDatos[i][0] = sistema.getId().toString();
				tablaDatos[i][1] = sistema.getNombre();
				tablaDatos[i][2] = sistema.getCodFabricante();
				tablaDatos[i][3] = sistema.getTelefono();
				tablaDatos[i][4] = (sistema instanceof TFabricanteLocal) ? "Local" : "Extranjero";
				i++;
			}
			tabla.setModel(new DefaultTableModel(tablaDatos, nombreColumnas));
		} else if (context
				.getEvento() == Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO_KO) {
			if (context.getDatos() == null) {
				JOptionPane.showMessageDialog(this, "El invernadero no existe", "Error", JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "Error al tratar de listar los Fabricantes", "Error",
						JOptionPane.ERROR_MESSAGE);
		}
	}
}