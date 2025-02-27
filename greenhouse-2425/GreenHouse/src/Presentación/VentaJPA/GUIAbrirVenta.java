package Presentacion.VentaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.VentaJPA.TCarrito;
import Negocio.VentaJPA.TLineaVenta;
import Negocio.VentaJPA.TVenta;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUIAbrirVenta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textId;
	private JTextField textCantidad;
	private JTextField textQuitar;
	private JTextField textEmpleado;
	private JTable tabla;
	JComboBox<String> pagoBox;
	private TCarrito tCarrito;
	String[] nombreColumnas = { "Id Producto", "Cantidad" };

	public GUIAbrirVenta() {
		super("Abrir Venta");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 700;
		int alto = 700;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {

		TVenta tVenta = new TVenta();
		tCarrito = new TCarrito(tVenta, new LinkedHashSet<>());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		// Cabecera
		JPanel CabeceraID = new JPanel();
		CabeceraID.setBorder(new EmptyBorder(5, 5, 5, 5));
		CabeceraID.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(CabeceraID);
		JLabel CabeceraProd = ComponentsBuilder
				.createLabel("Introduzca el ID del producto que desee agregar a la venta ", 1, 10, 75, 20, Color.BLACK);
		CabeceraProd.setAlignmentX(CENTER_ALIGNMENT);
		CabeceraID.add(CabeceraProd);

		// id Prod
		JPanel panelID = new JPanel();
		panelID.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelID.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelID);

		panelID.add(new JLabel("Id del Producto: "));
		textId = new JTextField(15);
		panelID.add(textId);

		// cantidad Prod
		JPanel panelCantidad = new JPanel();
		panelCantidad.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCantidad.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelCantidad);

		panelCantidad.add(new JLabel("Cantidad: "));
		textCantidad = new JTextField(15);
		panelCantidad.add(textCantidad);

		// aniadir boton
		JPanel panelAniadirButton = new JPanel();
		panelAniadirButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelAniadirButton.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelAniadirButton);

		JButton botonAnadirEntrada = new JButton("Añadir");
		botonAnadirEntrada.setBounds(75, 50, 100, 100);
		botonAnadirEntrada.addActionListener(a -> {
			try {
				int numCant = Integer.parseInt(textCantidad.getText());
				int numId = Integer.parseInt(textId.getText());

				if (checkNum(numCant) || checkNum(numId)) {
					if (textId.getText().isEmpty() || textCantidad.getText().isEmpty()) {
						JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Error en el formato de datos", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						int idProd = Integer.parseInt(textId.getText());
						int cantidad = Integer.parseInt(textCantidad.getText());
						TLineaVenta lVenta = new TLineaVenta();
						lVenta.setIdPoducto(idProd);
						lVenta.setCantidad(cantidad);
						boolean modificacionLineaFactura = false;

						for (TLineaVenta linea : tCarrito.getLineaVenta()) {
							if (linea.getIdProducto() == idProd) {
								linea.setCantidad(linea.getCantidad() + cantidad);
								modificacionLineaFactura = true;
							}
						}
						if (!modificacionLineaFactura)
							tCarrito.getLineaVenta().add(lVenta);
						update();
					}
				} else
					JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Error en el formato de datos", "Error",
							JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Los datos no son correctos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		panelAniadirButton.add(botonAnadirEntrada);

		// *************************Eliminar***********************************

		JLabel msgEliminar = ComponentsBuilder.createLabel(
				"Introduzca el ID del Producto que desea eliminar de la Venta ", 1, 150, 75, 20, Color.BLACK);
		msgEliminar.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgEliminar);

		// id quitar
		JPanel panelQuitar = new JPanel();
		panelQuitar.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelQuitar.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelQuitar);

		panelQuitar.add(new JLabel("Id Producto: "));
		textQuitar = new JTextField(15);
		panelQuitar.add(textQuitar);

		// quitar cantidad
		JPanel panelCantidadOut = new JPanel();
		panelCantidadOut.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCantidadOut.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelCantidadOut);

		panelCantidadOut.add(new JLabel("Cantidad a Quitar:"));
		JTextField cantidadOuttxt = new JTextField(15);
		panelCantidadOut.add(cantidadOuttxt);

		// quitar boton
		JPanel panelQuitarButton = new JPanel();
		panelQuitarButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelQuitarButton.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelQuitarButton);

		JButton botonQuitarEntrada = new JButton("Quitar");
		botonQuitarEntrada.setBounds(75, 50, 100, 100);
		botonQuitarEntrada.addActionListener(a -> {
			try {
				int numCant = Integer.parseInt(cantidadOuttxt.getText());
				int numId = Integer.parseInt(textQuitar.getText());

				if (checkNum(numCant) || checkNum(numId)) {

					if (textQuitar.getText().isEmpty() || cantidadOuttxt.getText().isEmpty()) {
						JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Error en el formato de datos", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						int idProd = Integer.parseInt(textQuitar.getText());
						int cantidad = Integer.parseInt(cantidadOuttxt.getText());
						boolean correct = true;
						boolean encontrado = false;
						Set<TLineaVenta> lVentas = tCarrito.getLineaVenta();
						TLineaVenta quitarLV = new TLineaVenta();
						for (TLineaVenta lVenta : lVentas) {
							if (lVenta.getIdProducto() == idProd) {
								encontrado = true;
								if (cantidad > lVenta.getCantidad())
									correct = false;
								else {
									int cantidadTotal = lVenta.getCantidad() - cantidad;
									if (cantidadTotal == 0)
										quitarLV = lVenta;
									else
										lVenta.setCantidad(cantidadTotal);
								}
							}
						}
						if (!correct)
							JOptionPane.showMessageDialog(this, "No hay entradas suficientes para quitar", "Error",
									JOptionPane.ERROR_MESSAGE);
						if (!encontrado)
							JOptionPane.showMessageDialog(this, "Producto con id: " + idProd + " no esta en el carrito",
									"Error", JOptionPane.ERROR_MESSAGE);
						else {
							if (quitarLV != null)
								lVentas.remove(quitarLV);
							tCarrito.setLineaVenta(lVentas);
							update();
						}

					}
				} else
					JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Error en el formato de datos", "Error",
							JOptionPane.ERROR_MESSAGE);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Los datos no son correctos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelQuitarButton.add(botonQuitarEntrada);

		tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, null);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(750, 250));
		add(scroll);

		// forma de pago
		JPanel panelPago = new JPanel();
		panelPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelPago.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelPago);

		String[] pago = { "Visa", "Efectivo", "Master Card", "Bitcoin" };
		panelPago.add(new JLabel("Forma de pago: "));
		pagoBox = new JComboBox<String>(new DefaultComboBoxModel<>(pago));
		panelPago.add(pagoBox);

		// id del empleado
		JPanel panelEmp = new JPanel();
		panelEmp.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelEmp.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelEmp);

		panelEmp.add(new JLabel("Id Empleado: "));
		textEmpleado = new JTextField(15);
		panelEmp.add(textEmpleado);

		// botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(200, 50, 100, 100);
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));

			}
		});
		panelBotones.add(botonCancelar);

		JButton botonCerrar = new JButton("Cerrar Venta");
		botonCerrar.setBounds(75, 50, 100, 100);
		botonCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int idEmp = Integer.parseInt(textEmpleado.getText());
					if (!checkNum(idEmp))
						JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Los datos no son correctos", "Error",
								JOptionPane.ERROR_MESSAGE);
					else if (textEmpleado.getText().isEmpty())
						JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Falta la id del Empleado", "Error",
								JOptionPane.ERROR_MESSAGE);
					else {

						tVenta.setIdEmpleado(Integer.parseInt(textEmpleado.getText()));
						tVenta.setFormaDePago((String) pagoBox.getSelectedItem());
						tCarrito.setVenta(tVenta);
						ApplicationController.getInstance().manageRequest(new Context(Evento.CERRAR_VENTA, tCarrito));
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIAbrirVenta.this, "Los datos no son correctos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonCerrar);
		this.setVisible(true);
		this.setResizable(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.CERRAR_VENTA_OK) {
			JOptionPane.showMessageDialog(this,
					"Venta con id " + (Integer) context.getDatos() % 100000000 + " cerrada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.CERRAR_VENTA_KO) {
			int error = (Integer) context.getDatos() / 10000000;
			int arg = (Integer) context.getDatos() % 10000000;
			switch (-error) {
			case -2:
				JOptionPane.showMessageDialog(this, "No existe el Empleado con id: " + arg, "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "El Emplado con id: " + arg + " esta dado de baja", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "El carrito esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "No existe el Producto con id: " + arg, "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -6:
				JOptionPane.showMessageDialog(this, "El Producto con id: " + arg + " esta dado de baja", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -7:
				JOptionPane.showMessageDialog(this, "El Producto con id: " + arg + " no tiene suficiente stock",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al cerrar la venta.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}

	private void update() {
		String[][] datos = new String[tCarrito.getLineaVenta().size()][nombreColumnas.length];
		int i = 0;
		for (TLineaVenta v : tCarrito.getLineaVenta()) {
			datos[i][0] = v.getIdProducto().toString();
			datos[i][1] = v.getCantidad().toString();
			i++;
		}
		tabla.setModel(new DefaultTableModel(datos, nombreColumnas));
	}

	private Boolean checkNum(int num) {
		return num > 0;
	}
}
