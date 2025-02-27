package Presentacion.VentaJPA;

import javax.swing.JFrame;

import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.VentaJPA.TLineaVenta;
import Negocio.VentaJPA.TVenta;
import Negocio.VentaJPA.TVentaConProductos;

public class GUIMostrarVentaPorId extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private Set<TLineaVenta> datos;
	String[] nombreColumnas = { "Id Producto", "Cantidad", "Precio" };
	private JTable tabla;
	private JTextField idText;
	private JLabel mensajeVenta;
	private JScrollPane scroll;

	public GUIMostrarVentaPorId() {
		super("Mostrar Venta");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 700;
		int alto = 700;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// cabecera
		JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca el ID de la Venta a mostrar ", 1, 10, 80,
				20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		// id venta
		JPanel panelID = new JPanel();
		mainPanel.add(panelID);
		panelID.add(new JLabel("Id de la Venta: "));
		
		idText = new JTextField(20);
		idText.setEditable(true);
		panelID.add(idText);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// datos venta
		JPanel mensaje = new JPanel();
		mainPanel.add(mensaje);

		mensajeVenta = new JLabel();
		mensaje.add(mensajeVenta);

		// tabla
		List<String[]> datosColumnas = new ArrayList<String[]>();

		tabla = ComponentsBuilder.createTable(0, 4, nombreColumnas, datosColumnas.toArray(new String[][] {}));
		tabla.setAlignmentX(CENTER_ALIGNMENT);
		scroll = new JScrollPane(tabla);
		scroll.setBounds(50, 115, 900, 288);
		mainPanel.add(scroll);

		// botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.setBounds(75, 50, 100, 100);
		botonBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idVenta = Integer.parseInt(idText.getText());
					
					if(checkNum(idVenta))
					ApplicationController.getInstance().manageRequest(
							new Context(Evento.MOSTRAR_VENTA_POR_ID, !idText.getText().isEmpty() ? idVenta : 0));
					else
						JOptionPane.showMessageDialog(GUIMostrarVentaPorId.this, "Los datos no son correctos", "Error",
								JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIMostrarVentaPorId.this, "Los datos no son correctos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		panelID.add(botonBuscar);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(200, 50, 100, 100);
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMostrarVentaPorId.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));

			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.MOSTRAR_VENTA_POR_ID_OK) {

			TVentaConProductos res = (TVentaConProductos) context.getDatos();
			datos = res.getLineasVenta();

			mensajeVenta
					.setText("Perecio Total: " + res.getVenta().getPrecioTotal() + " | Empleado que realizo la venta: "
							+ res.getVenta().getIdEmpleado() + " | Forma de Pago: " + res.getVenta().getFormaPago()
							+ " | Fecha: " + res.getVenta().getFecha() + " | Activo: " + (res.getVenta().getActivo() ? "Si" : "NO"));

			String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

			int i = 0;
			for (TLineaVenta lv : datos) {
				tablaDatos[i][0] = lv.getIdProducto().toString();
				tablaDatos[i][1] = lv.getCantidad().toString();
				tablaDatos[i][2] = lv.getPrecio().toString();
				i++;
			}

			tabla.setModel(new DefaultTableModel(tablaDatos, nombreColumnas));
		} else if (context.getEvento() == Evento.MOSTRAR_VENTA_POR_ID_KO) {
			if (((TVenta)context.getDatos()).getId() < 0) {
				JOptionPane.showMessageDialog(this, "No existe la Venta con id: " + idText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "Error al mostrar la Venta", "Error",
						JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Boolean checkNum(int num) {
		return num > 0;
	}
}