package Presentacion.Factura;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.Factura.TCarrito;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIFactura extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private JButton botonAbrirFactura;
	private JButton botonDevolverFactura;
	private JButton botonModificarFactura;
	private JButton botonMostrarFactura;
	private JButton botonListarFacturas;
	private JButton botonVolver;
	private JPanel jpanel;
	
	public GUIFactura(){
		super("Factura");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		jpanel = new JPanel();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		this.setVisible(true);
	}
	
	public void initGUI() {
		JLabel label = ComponentsBuilder.createLabel("Factura", 250, 30, 500, 50, Color.BLACK);
		this.add(label);
		
		//ABRIR Factura
		botonAbrirFactura = ComponentsBuilder.createButton("Abrir Factura", 100, 100, 185, 100);
		botonAbrirFactura.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				TCarrito carrito = new TCarrito();
				ApplicationController.getInstance().manageRequest(new Context(Evento.ABRIR_FACTURA_VISTA, carrito));
			}

		});
		botonAbrirFactura.setVisible(true);
		this.add(botonAbrirFactura);
		
		//Devolver Factura
		botonDevolverFactura = ComponentsBuilder.createButton("Devolver Factura", 300, 100, 185, 100);
		botonDevolverFactura.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.DEVOLVER_FACTURA_VISTA, null));
			}

		});
		botonDevolverFactura.setVisible(true);
		this.add(botonDevolverFactura);
		
		//MODIFICAR Factura
		botonModificarFactura = ComponentsBuilder.createButton("Modificar Factura", 500, 100, 185, 100);
		botonModificarFactura.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_FACTURA_VISTA, null));
			}

		});
		botonModificarFactura.setVisible(true);
		this.add(botonModificarFactura);
		
		//MOSTRAR Factura
		botonMostrarFactura = ComponentsBuilder.createButton("Mostrar Factura", 700, 100, 185, 100);
		botonMostrarFactura.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_FACTURA_POR_ID_VISTA, null));
			}

		});
		botonMostrarFactura.setVisible(true);
		this.add(botonMostrarFactura);
		
		//LISTAR Facturas
		botonListarFacturas = ComponentsBuilder.createButton("Listar Facturas", 100, 250, 185, 100);
		botonListarFacturas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_FACTURAS, null));
			}

		});
		botonListarFacturas.setVisible(true);
		this.add(botonListarFacturas);
		
		//BACK BUTTON
		botonVolver = ComponentsBuilder.createBackButton();
		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIFactura.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL,null));
				dispose();
			}
		});
		botonVolver.setVisible(true);
		this.add(botonVolver);
		
		getContentPane().add(jpanel);


	}

	@Override
	public void actualizar(Context context) {
		
	}
}