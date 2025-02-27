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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Negocio.Planta.TPlanta;
import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoAlimentacion;
import Negocio.ProductoJPA.TProductoSouvenirs;

import javax.swing.JPanel;

public class GUIModificarProducto extends JFrame implements IGUI {
	

	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel msgIntroIDCabecera;

	private JPanel mainPanel;
	
	private static final long serialVersionUID = 1L;

	private TProducto producto;

	public GUIModificarProducto(TProducto datos) {		
		super("Modificar Producto");
		producto = (TProducto) datos;
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 700;
		int alto = 750;
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

		msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca los datos del Producto" , 1, 10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JLabel labelId = new JLabel("Id del producto: ");
		labelId.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelId);

		JTextField textId = new JTextField(20);
		textId.setMaximumSize(textId.getPreferredSize());
		mainPanel.add(textId);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
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
				
				JLabel labelEstado = new JLabel("Estado: ");
				labelEstado.setAlignmentX(CENTER_ALIGNMENT);
				mainPanel.add(labelEstado);

		 		JComboBox<String> estado = new JComboBox<String>();
		 		estado.addItem("Activo");
		 		estado.addItem("No Activo");
		 		estado.setMaximumSize(estado.getPreferredSize());
		 		
		 		mainPanel.add(estado);
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
				botonAceptar = new JButton("Modificar");
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
							
							if(estado.getSelectedIndex() == 1)
		 						p.setActivo(false);
							
							p.setId(producto.getId());
							ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PRODUCTO, p));
							setVisible(false);
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(GUIModificarProducto.this, "Error en el formato de los datos", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					}

				});
				
			

				panelBotones.add(botonAceptar);

				// BOTON CANCELAR
				botonCancelar = new JButton("Buscar");
				botonCancelar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						try{
			 				int id = Integer.parseInt(textId.getText());
			 				TProducto tmp = new TProducto();
			 				tmp.setId(id);
			 				setVisible(false);
							ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PRODUCTO, tmp));
			 			}
			 			catch(Exception ex){
			 				GUIMSG.showMessage("Formato incorrecto", "MODIFICAR PRODUCTO", true);
			 			
			 			}
						
						
						
					}
				});
				panelBotones.add(botonCancelar);

				this.setVisible(true);
				this.setResizable(true);
				
				
				
				if(producto == null){
					botonAceptar.setEnabled(false);
					estado.setEnabled(false);
					textNombre.setEnabled(false);
					tipoProducto.setEnabled(false);
					textidMarca.setEnabled(false);
					textStock.setEnabled(false);
					textPrecio.setEnabled(false);
				 	textPrecioKilo.setEnabled(false);
                	textPeso.setEnabled(false);
                	textTipo.setEnabled(false);
					textDescripcion.setEnabled(false);
					
				}
				else{
					
					textNombre.setText(producto.getNombre());
					textId.setEnabled(false);
					textidMarca.setEnabled(false);
				
					textidMarca.setText(""+producto.getIdMarca());;
					textStock.setText(""+producto.getStock());
					textPrecio.setText(""+producto.getPrecio());
					
					botonAceptar.setEnabled(true);
					botonCancelar.setEnabled(false);
					estado.setSelectedIndex(0);
					if(!producto.getActivo())
						estado.setSelectedIndex(1);
						
					tipoProducto.setEnabled(false);
					
                    if (producto instanceof TProductoAlimentacion) {
                    	
                    	tipoProducto.setSelectedIndex(0);
                    	textDescripcion.setText("");
                    	textDescripcion.setEnabled(false);
                    	
                    	textPrecioKilo.setText(""+((TProductoAlimentacion) producto).getPrecioKilo());
                    	textPeso.setText(""+((TProductoAlimentacion) producto).getPeso());
                    	textTipo.setText(((TProductoAlimentacion) producto).getTipo());
                    	
                    } 
                    else if(producto instanceof TProductoSouvenirs) {
                    	
                    	tipoProducto.setSelectedIndex(1);
                    	textDescripcion.setText(((TProductoSouvenirs) producto).getDescripcion());;
                    	
                    	textPrecioKilo.setEnabled(false);
                    	textPeso.setEnabled(false);
                    	textTipo.setEnabled(false);
                    	textPrecioKilo.setText("");
                    	textTipo.setText("");
                    	textPeso.setText("");
                    }
					
				}
				
				
				
				
	}


	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.MODIFICAR_PRODUCTO_OK:
			GUIMSG.showMessage("Se realizo la modificacion correctamente", "MODIFICAR PRODUCTO", false);
			break;
		case  Evento.MODIFICAR_PRODUCTO_KO:
			if((int) context.getDatos() == -2){
				GUIMSG.showMessage("id de la Marca incorrecto", "MODIFICAR PRODUCTO", true);
			}
			else if((int) context.getDatos() == -3)
				GUIMSG.showMessage("No se encontro el producto", "MODIFICAR PRODUCTO", true);
			else if((int) context.getDatos() == -4)
				GUIMSG.showMessage("Nombre duplicado", "MODIFICAR PRODUCTO", true);
			else
			GUIMSG.showMessage("No se pudo realizar la modificacion", "MODIFICAR PRODUCTO", true);
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "MODIFICAR PRODUCTO", true);
			break;
		
	}
		ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
	}
}