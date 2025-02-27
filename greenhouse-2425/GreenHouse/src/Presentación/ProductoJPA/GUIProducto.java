package Presentacion.ProductoJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIProducto extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JPanel j;
	private JButton bAlta;
	private JButton bBaja;
	private JButton bModificar;
	private JButton bMostrarPorId;
	private JButton bListar;
	private JButton bListarPorTipo;
	private JButton bListarPorMarca;
	private JButton bListarPorVenta;
	private JButton backButton;
	
		public GUIProducto(){
			super("BGANOS");
			Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
			int ancho = 1000;
			int alto = 650;
			int x = (pantalla.width - ancho) / 2;
			int y = (pantalla.height - alto) / 2;
			this.setBounds(x, y, ancho, alto);
			this.setLayout(null);
			j = new JPanel();
			this.setResizable(false);
			//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			initGUI();
			this.setVisible(true);
		}

		public void initGUI() {
			

			JLabel label = ComponentsBuilder.createLabel("Producto", 250, 30, 500, 50, Color.BLACK);
			this.add(label);
			
			//ALTA PLANTA
			bAlta = ComponentsBuilder.createButton("Alta Producto", 125, 100, 225, 100);
			bAlta.addActionListener(new ActionListener() {
						
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_PRODUCTO_VISTA, null));
				}
			});
			bAlta.setVisible(true);
			this.add(bAlta);
			
			//BAJA PLANTA
			bBaja = ComponentsBuilder.createButton("Baja Producto", 375, 100, 225, 100);
			bBaja.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_PRODUCTO_VISTA, null));
				}
			});
			bBaja.setVisible(true);
			this.add(bBaja);
			
			//MODIFICAR PLANTA
			bModificar = ComponentsBuilder.createButton("Modificar Producto", 625, 100, 225, 100);
			bModificar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PRODUCTO_VISTA, null));
				}
			});
			bModificar.setVisible(true);
			this.add(bModificar);
			
			//MOSTRAR PLANTA POR ID
			bMostrarPorId = ComponentsBuilder.createButton("Mostar Producto Por Id", 125, 250, 225, 100);
			bMostrarPorId.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_PRODUCTO_POR_ID_VISTA, null));
				}
			});
			bMostrarPorId.setVisible(true);
			this.add(bMostrarPorId);
			
			//LISTAR PLANTAS
			bListar = ComponentsBuilder.createButton("Listar Producto", 375, 250, 225, 100);
			bListar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS, null));
				}
			});
			bListar.setVisible(true);
			this.add(bListar);
			
			//LISTAR PLANTAS POR TIPO
			bListarPorTipo = ComponentsBuilder.createButton("Listar Producto Por Tipo", 625, 250, 225, 100);
			bListarPorTipo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_POR_TIPO_VISTA, null));
				}
			});
			bListarPorTipo.setVisible(true);
			this.add(bListarPorTipo);
			
			//LISTAR PLANTAS POR INVERNADERO
			bListarPorMarca = ComponentsBuilder.createButton("Listar Producto Por Marca", 125, 400, 225, 100);
			bListarPorMarca.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_POR_MARCA_VISTA, null));
				}
			});
			bListarPorMarca.setVisible(true);
			this.add(bListarPorMarca);
			
			
			//LISTAR PLANTAS POR INVERNADERO
			bListarPorVenta = ComponentsBuilder.createButton("Listar Producto Por Venta", 375, 400, 225, 100);
			bListarPorVenta.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PRODUCTOS_POR_VENTA_VISTA, null));
				}
			});
			bListarPorVenta.setVisible(true);
			this.add(bListarPorVenta);
			
			
			
	
			backButton = ComponentsBuilder.createBackButton();
			backButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			
					ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL,null));
					dispose();
				}
			}); 
			backButton.setVisible(true);
			this.add(backButton);
			
			getContentPane().add(j);

		}

		public void actualizar(Context context) {
		}
	}