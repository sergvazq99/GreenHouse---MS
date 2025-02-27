/**
 * 
 */
package Presentacion.ProductoJPA;

import javax.swing.JFrame;

import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.GUIMSG;
import Presentacion.Controller.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JPanel;

public class GUIListarProductoPorVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;


	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel labelID;

	private JTextField textID;

	private JPanel mainPanel;

	
	public GUIListarProductoPorVenta() {
		super("Listar Productos Por Venta");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 700;
		int alto = 300;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniGUI();

	}
	
	public void iniGUI() {
		

		// Panel principal
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		JLabel msgIntroIdCabecera = ComponentsBuilder
				.createLabel("Introduzca los datos requeridos", 1, 10, 80, 20, Color.BLACK);
		msgIntroIdCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIdCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		labelID = new JLabel("ID de la venta:");
		labelID.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelID);

		textID = new JTextField(20);
		textID.setMaximumSize(textID.getPreferredSize());
		mainPanel.add(textID);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		// BOTON ACEPTAR (PROCESAR LA BAJA)
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int id = Integer.parseInt(textID.getText());
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_POR_VENTA, id));
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIListarProductoPorVenta.this, "Error en el formato de los datos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		
		});
		panelBotones.add(botonAceptar);

		// BOTON CANCELAR
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	
	}


	public void actualizar(Context context) {
		
		switch(context.getEvento()) {
		case Evento.LISTAR_PRODUCTOS_POR_VENTA_KO:
			GUIMSG.showMessage("No existe la venta", "LISTAR PRODUCTOS POR VENTA", true);
			ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
			break;
		case  Evento.LISTAR_PRODUCTOS_POR_VENTA_OK:
			
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_VISTA,context.getDatos()));
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PRODUCTOS POR VENTA", true);
			break;
		
	}
	}
}