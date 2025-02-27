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
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoAlimentacion;
import Negocio.ProductoJPA.TProductoSouvenirs;

import javax.swing.JPanel;


public class GUIListarProductoPorTipo extends JFrame implements IGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel msgIntroIDCabecera;

	private JPanel mainPanel;

	public GUIListarProductoPorTipo() {
		super("Listar Productos Por Tipo");
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
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca los datos requeridos" , 1, 10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JLabel labelTipoProducto = new JLabel("Tipo del Producto: ");
		labelTipoProducto.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelTipoProducto);

 		JComboBox<String> tipoProducto = new JComboBox<String>();
 		tipoProducto.addItem("Alimentacion");
 		tipoProducto.addItem("Souvenirs");
 		tipoProducto.setMaximumSize(tipoProducto.getPreferredSize());
 		
 		mainPanel.add(tipoProducto);
 		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		// BOTON ACEPTAR (GUARDAR LOS DATOS DEL ALTA)
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {


				try {
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_POR_TIPO,  tipoProducto.getSelectedIndex()));
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIListarProductoPorTipo.this, "Error en el formato de los datos", "Error",
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
		case Evento.LISTAR_PRODUCTOS_POR_TIPO_KO:
			GUIMSG.showMessage("No existe PRDUCTOS del tipo seleccionado", "LISTAR PRODUCTOS POR TIPO", true);
			ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
			break;
		case  Evento.LISTAR_PRODUCTOS_POR_TIPO_OK:
			
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_VISTA,context.getDatos()));
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PRODCUTOS POR TIPO", true);
			break;
		
	}
	}
}