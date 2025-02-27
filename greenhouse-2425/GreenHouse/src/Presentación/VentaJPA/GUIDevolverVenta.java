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
import Negocio.VentaJPA.TVentaConProductos;

public class GUIDevolverVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private Set<TLineaVenta> datos;
	String[] nombreColumnas = { "Id Producto", "Cantidad", "Precio" };
	private TLineaVenta lv;
	private JTable tabla;
	private JTextField idText;
	private JTextField cantText;
	private JTextField prodText;
	private JScrollPane scroll;

	public GUIDevolverVenta() {
		super("Devolver Venta");
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
		lv = new TLineaVenta();
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

		// tabla
		JPanel mensaje = new JPanel();
		mainPanel.add(mensaje);

		List<String[]> datosColumnas = new ArrayList<String[]>();

		tabla = ComponentsBuilder.createTable(0, 4, nombreColumnas, datosColumnas.toArray(new String[][] {}));
		scroll = new JScrollPane(tabla);
		mainPanel.add(scroll);

		// devolucion
		JPanel panelDevolucion = new JPanel();

		panelDevolucion.add(new JLabel("Id producto"));
		prodText = new JTextField(10);
		panelDevolucion.add(prodText);

		panelDevolucion.add(new JLabel("Cantidad"));
		cantText = new JTextField(10);
		panelDevolucion.add(cantText);

		mainPanel.add(panelDevolucion);

		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.setBounds(75, 50, 100, 100);
		botonBuscar.addActionListener(a -> {
			try {
				Integer id_Factura = Integer.parseInt(idText.getText());
				ApplicationController.getInstance().manageRequest(
						new Context(Evento.MOSTRAR_VENTA_POR_ID, !idText.getText().isEmpty() ? id_Factura : 0));

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(GUIDevolverVenta.this, "Los datos no son correctos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		panelID.add(botonBuscar);

		JButton botonDevolver = new JButton("Devolver");
		botonDevolver.setBounds(75, 50, 100, 100);
		botonDevolver.addActionListener(a -> {
			try {
				int idProd = Integer.parseInt(prodText.getText());
				int cant = Integer.parseInt(cantText.getText());

				if (!checkNum(idProd) || !checkNum(cant))
					JOptionPane.showMessageDialog(GUIDevolverVenta.this, "Error en el formato de los datos", "Error",
							JOptionPane.ERROR_MESSAGE);
				else {
					lv.setIdPoducto(idProd);
					lv.setCantidad(cant);
					lv.setIdVenta(Integer.parseInt(idText.getText()));

					ApplicationController.getInstance().manageRequest(new Context(Evento.DEVOLVER_VENTA, lv));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(GUIDevolverVenta.this, "Error en el formato de los datos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		panelBotones.add(botonDevolver);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(200, 50, 100, 100);
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIDevolverVenta.this.setVisible(false);
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
			update();
		} else if (context.getEvento() == Evento.MOSTRAR_VENTA_POR_ID_KO) {
			if (context.getDatos() == null) {
				JOptionPane.showMessageDialog(this, "No existe la Venta con id: " + idText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "Error al tratar de listar las Ventas", "Error",
						JOptionPane.ERROR_MESSAGE);

		} else if (context.getEvento() == Evento.DEVOLVER_VENTA_OK) {
			JOptionPane.showMessageDialog(this, "Devolución realizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			for (TLineaVenta lVenta : datos)
				if (lVenta.getIdProducto() == lv.getIdProducto()) {
					lVenta.setCantidad(lVenta.getCantidad() - lv.getCantidad());
					lv = lVenta;
				}
			if (lv.getCantidad() == 0)
				datos.remove(lv);

			update();
		} else if (context.getEvento() == Evento.DEVOLVER_VENTA_KO) {
			switch ((Integer) context.getDatos()) {
			case -2:
				JOptionPane.showMessageDialog(this, "No existe la Venta con id: " + idText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "No existe el Producto con id: " + prodText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Carrito vacio", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "No se puede devolver más de lo que tenia la Venta", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido: " + idText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private void update() {
		String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

		int i = 0;
		for (TLineaVenta lv : datos) {
			if (lv.getCantidad() != 0) {
				tablaDatos[i][0] = lv.getIdProducto().toString();
				tablaDatos[i][1] = lv.getCantidad().toString();
				tablaDatos[i][2] = lv.getPrecio().toString();
				i++;
			}
		}

		tabla.setModel(new DefaultTableModel(tablaDatos, nombreColumnas));
	}

	private Boolean checkNum(int num) {
		return num > 0;
	}
}