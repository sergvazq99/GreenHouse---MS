package Presentacion.VentaJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.VentaJPA.TVenta;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIVentaJPA extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private JButton buttonAbrirVenta;
	private JButton buttonModificarVenta;
	private JButton buttonMostrarVenta;
	private JButton buttonListarVentas;
	private JButton buttonDevolverVenta;
	private JButton buttonVentasPorEmpleadoDeCaja;
	private JButton backButton;
	private JPanel panel;
	private TVenta tVenta;

	public GUIVentaJPA() {
		super("Ventas");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		panel = new JPanel();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		this.setVisible(true);
	}

	public void initGUI() {
		tVenta = new TVenta();
		JLabel label = ComponentsBuilder.createLabel("Venta", 250, 30, 500, 50, Color.BLACK);
		this.add(label);

		// ABRIR VENTA
		buttonAbrirVenta = ComponentsBuilder.createButton("Abrir", 100, 100, 185, 100);
		buttonAbrirVenta.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.ABRIR_VENTA_VISTA, tVenta));
		});
		buttonAbrirVenta.setVisible(true);
		this.add(buttonAbrirVenta);

		// MODIFICAR VENTA
		buttonModificarVenta = ComponentsBuilder.createButton("Modificar", 300, 100, 185, 100);
		buttonModificarVenta.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_VENTAS_VISTA, tVenta));
		});
		buttonModificarVenta.setVisible(true);
		this.add(buttonModificarVenta);

		// MOSTRAR VENTA
		buttonMostrarVenta = ComponentsBuilder.createButton("Mostrar por ID", 500, 100, 185, 100);
		buttonMostrarVenta.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_VENTA_POR_ID_VISTA, tVenta));
		});
		buttonMostrarVenta.setVisible(true);
		this.add(buttonMostrarVenta);

		// LISTAR VENTAS
		buttonListarVentas = ComponentsBuilder.createButton("Listar", 700, 100, 185, 100);
		buttonListarVentas.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_VENTAS, tVenta));
		});
		buttonListarVentas.setVisible(true);
		this.add(buttonListarVentas);

		// LISTAR VENTAS POR EMPLEADO DE CAJA
		buttonVentasPorEmpleadoDeCaja = ComponentsBuilder.createButton("Ventas por Empleado", 100, 250, 185, 100);
		buttonVentasPorEmpleadoDeCaja.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.VENTAS_POR_EMPLEADO_DE_CAJA_VISTA, tVenta));
		});
		buttonVentasPorEmpleadoDeCaja.setVisible(true);
		this.add(buttonVentasPorEmpleadoDeCaja);

		// DEVOLVER VENTA
		buttonDevolverVenta = ComponentsBuilder.createButton("Devolver", 300, 250, 185, 100);
		buttonDevolverVenta.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.DEVOLVER_VENTA_VISTA, tVenta));
		});
		buttonDevolverVenta.setVisible(true);
		this.add(buttonDevolverVenta);

		// BOTON DE VOLVER
		backButton = ComponentsBuilder.createBackButton();
		backButton.addActionListener(a -> {
			GUIVentaJPA.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
			dispose();
		});
		backButton.setVisible(true);
		this.add(backButton);

		getContentPane().add(panel);
	}

	@Override
	public void actualizar(Context context) {
	}

}
