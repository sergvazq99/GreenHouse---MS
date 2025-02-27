package Presentacion.Fabricante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.Fabricante.TFabricante;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

@SuppressWarnings("serial")
public class GUIFabricante extends JFrame implements IGUI {

	private TFabricante tFabricante;
	private JButton bAlta;
	private JButton bBaja;
	private JButton bModificar;
	private JButton bMostrar;
	private JButton bListar;
	private JButton bListarLocal;
	private JButton bListarExtranjero;
	private JButton bAtras;
	private JPanel mainPanel;

	public GUIFabricante() {
		super("Fabricante");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		mainPanel = new JPanel();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		this.setVisible(true);
	}

	private void initGUI() {

		tFabricante = new TFabricante();
		JLabel label = ComponentsBuilder.createLabel("Fabricante", 250, 30, 500, 50, Color.BLACK);
		this.add(label);

		// Alta
		bAlta = ComponentsBuilder.createButton("Alta Fabricante", 100, 100, 185, 100);
		bAlta.setVisible(true);
		this.add(bAlta);
		bAlta.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_FABRICANTE_VISTA, tFabricante));

		});

		// Baja
		bBaja = ComponentsBuilder.createButton("Baja Fabricante", 300, 100, 185, 100);
		bBaja.setVisible(true);
		this.add(bBaja);
		bBaja.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_FABRICANTE_VISTA, tFabricante));

		});

		// Modificar
		bModificar = ComponentsBuilder.createButton("Modificar Fabricante", 500, 100, 185, 100);
		bModificar.setVisible(true);
		this.add(bModificar);
		bModificar.addActionListener(a -> {

			GUIFabricante.this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.MODIFICAR_FABRICANTE_VISTA, tFabricante));

		});

		// Mostrar
		bMostrar = ComponentsBuilder.createButton("Mostrar Fabricante", 700, 100, 185, 100);
		bMostrar.setVisible(true);
		this.add(bMostrar);
		bMostrar.addActionListener(a -> {

			GUIFabricante.this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.MOSTRAR_FABRICANTE_POR_ID_VISTA, tFabricante));

		});

		// Listar
		bListar = ComponentsBuilder.createButton("Listar", 100, 250, 185, 100);
		bListar.setVisible(true);
		this.add(bListar);
		bListar.addActionListener(a -> {

			GUIFabricante.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_FABRICANTES));

		});

		// Listar local
		bListarLocal = ComponentsBuilder.createButton("Listar Locales", 300, 250, 185, 100);
		bListarLocal.setVisible(true);
		this.add(bListarLocal);
		bListarLocal.addActionListener(a -> {

			GUIFabricante.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_FABRICANTES_LOCALES));
		});

		// Listar extranjero
		bListarExtranjero = ComponentsBuilder.createButton("Listar Extranjeros", 500, 250, 185, 100);
		bListarExtranjero.setVisible(true);
		this.add(bListarExtranjero);
		bListarExtranjero.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_FABRICANTES_EXTRANJEROS));

		});

		// Query
		bListarExtranjero = ComponentsBuilder.createButton("Listar Por Invernadero", 700, 250, 185, 100);
		bListarExtranjero.setVisible(true);
		this.add(bListarExtranjero);
		bListarExtranjero.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(
					new Context(Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO_VISTA));

		});

		// Atras
		bAtras = ComponentsBuilder.createBackButton();
		bAtras.setVisible(true);
		this.add(bAtras);
		bAtras.addActionListener(a -> {

			GUIFabricante.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
			dispose();
		});

		getContentPane().add(mainPanel);

	}

	@Override
	public void actualizar(Context context) {
	}
}