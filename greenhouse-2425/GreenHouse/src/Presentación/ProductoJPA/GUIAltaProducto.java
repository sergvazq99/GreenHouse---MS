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
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoAlimentacion;
import Negocio.ProductoJPA.TProductoSouvenirs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUIAltaProducto extends JFrame implements IGUI {

	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel msgIntroIDCabecera;

	private JPanel mainPanel;
	
	private static final long serialVersionUID = 1L;

	public GUIAltaProducto(){
		super("Alta Producto");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 700;
		int alto = 750;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca los datos del Producto" , 1, 10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JLabel labelNombre = new JLabel("Nombre: ");
		labelNombre.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelNombre);

		JTextField textNombre = new JTextField(20);
		textNombre.setMaximumSize(textNombre.getPreferredSize());
		mainPanel.add(textNombre);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
	
				JLabel labelPrecio = new JLabel("Precio: ");
				labelPrecio.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelPrecio);

				JTextField textPrecio = new JTextField(20);
				textPrecio.setMaximumSize(textPrecio.getPreferredSize());
				mainPanel.add(textPrecio);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));


				JLabel labelStock = new JLabel("Stock: ");
				labelStock.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelStock);

				JTextField textStock = new JTextField(20);
				textStock.setMaximumSize(textStock.getPreferredSize());
				mainPanel.add(textStock);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

				
				JLabel labelidMarca = new JLabel("Id de la Marca: ");
				labelidMarca.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelidMarca);

				JTextField textidMarca = new JTextField(20);
				textidMarca.setMaximumSize(textidMarca.getPreferredSize());
				mainPanel.add(textidMarca);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				
				
				JLabel labelTipoProducto = new JLabel("Tipo del Producto: ");
				labelTipoProducto.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelTipoProducto);

		 		JComboBox<String> tipoProducto = new JComboBox<String>();
		 		tipoProducto.addItem("Alimentacion");
		 		tipoProducto.addItem("Souvenirs");
		 		tipoProducto.setMaximumSize(tipoProducto.getPreferredSize());
		 		
		 		mainPanel.add(tipoProducto);
		 		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
						 		
		 		
		 		JLabel labelPeso = new JLabel("Peso: ");
		 		labelPeso.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelPeso);

				JTextField textPeso = new JTextField(20);
				textPeso.setMaximumSize(textPeso.getPreferredSize());
				mainPanel.add(textPeso);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				
				
				JLabel labelPrecioKilo = new JLabel("Precio Kilo: ");
				labelPrecioKilo.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelPrecioKilo);

				JTextField textPrecioKilo = new JTextField(20);
				textPrecioKilo.setMaximumSize(textPrecioKilo.getPreferredSize());
				mainPanel.add(textPrecioKilo);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				
				
				JLabel labelTipo = new JLabel("Tipo: ");
				labelTipo.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelTipo);

				JTextField textTipo = new JTextField(20);
				textTipo.setMaximumSize(textTipo.getPreferredSize());
				mainPanel.add(textTipo);

				mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
				
				
				JLabel labelDescripcion = new JLabel("Descripcion: ");
				labelDescripcion.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelDescripcion);

				JTextField textDescripcion = new JTextField(20);
				textDescripcion.setMaximumSize(textDescripcion.getPreferredSize());
				mainPanel.add(textDescripcion);

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
							String nombre = textNombre.getText();
							Double precio = Double.parseDouble(textPrecio.getText());
							Integer stock = Integer.parseInt(textStock.getText());
							Integer idMarca = Integer.parseInt(textidMarca.getText());
							int tip = 0;
							
							TProducto p;
							
							if(tipoProducto.getSelectedIndex() == 0){ //Alimentacion
								
								String tipo = textTipo.getText();
								Double peso = Double.parseDouble(textPeso.getText());
								Double precioKilo = Double.parseDouble(textPrecioKilo.getText());
								
								p = new TProductoAlimentacion(nombre,precio, stock, idMarca, tip, tipo, peso, precioKilo);
								
							}
							else{//Souvenir
								tip = 1;
								String descripcion = textDescripcion.getText();
								
								p = new TProductoSouvenirs(nombre,precio, stock, idMarca, tip, descripcion);
								
							}
							
							ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_PRODUCTO, p));
							setVisible(false);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(GUIAltaProducto.this, "Error en el formato de los datos", "Error",
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
				
				textDescripcion.setEnabled(false);
				
				
		 		tipoProducto.addItemListener(new ItemListener(){

					@Override
					public void itemStateChanged(ItemEvent e) {
						
						
						if(e.getStateChange() == ItemEvent.SELECTED){
							
							String selected = (String) tipoProducto.getSelectedItem();
		                    if (selected.equals("Alimentacion")) {
		                    	
		                    	textDescripcion.setText("");
		                    	textDescripcion.setEnabled(false);
		                    	
		                    	textPrecioKilo.setEditable(true);
		                    	textPeso.setEditable(true);
		                    	textTipo.setEditable(true);
		                    	
		                    } 
		                    else {
		                    	textDescripcion.setEnabled(true);
		                    	
		                    	textPrecioKilo.setEditable(false);
		                    	textPeso.setEditable(false);
		                    	textTipo.setEditable(false);
		                    	textPrecioKilo.setText("");
		                    	textTipo.setText("");
		                    	textPeso.setText("");
		                    }
							
							
						}
						
					}
		        });
				
				
				
	}

	public void actualizar(Context context) {
		
		switch(context.getEvento()) {
		case Evento.ALTA_PRODUCTO_OK:
		case Evento.ALTA_PRODUCTO_ALIMENTACION_OK:
		case Evento.ALTA_PRODUCTO_SOUVENIRS_OK:
			
			GUIMSG.showMessage("Producto dado de alta con ID: " + context.getDatos(), "ALTA PRODUCTO", false);
			break;
		case  Evento.ALTA_PRODUCTO_KO:
		case Evento.ALTA_PRODUCTO_ALIMENTACION_KO:
		case Evento.ALTA_PRODUCTO_SOUVENIRS_KO:

			if((int) context.getDatos() == -2){
				GUIMSG.showMessage("Marca inexistente o inactivado", "ALTA PRODUCTO", true);
			}
			else if((int) context.getDatos() == -8) 
			{
				GUIMSG.showMessage("Producto reactivado", "ALTA PRODUCTO", false);
			}
			else
			GUIMSG.showMessage("Nombre duplicado", "ALTA PRODUCTO", true);
			break;
			
		default:
			GUIMSG.showMessage("Error inesperado", "ALTA PRODUCTO", true);
			break;
		}
		
		ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
	
	}
}