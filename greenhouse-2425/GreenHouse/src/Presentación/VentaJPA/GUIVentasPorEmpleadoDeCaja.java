package Presentacion.VentaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.VentaJPA.TVenta;

public class GUIVentasPorEmpleadoDeCaja extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private Set<TVenta> datos;
	private String[] nombreColumnas = { "ID", "Precio Total", "Forma de Pago", "Fecha", "Activo" };
	private JTextField idText;
	private JPanel mainPanel;
	private JTable tabla;
	private JButton botonCancelar;

	public GUIVentasPorEmpleadoDeCaja() {
		super("Ventas efectuadas por un Empleado");
		datos = new HashSet<TVenta>();
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		// Panel Central
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(panelCentro);

		// id Empleado
		JLabel labelInvernadero = new JLabel("Id del Empleado:");
		panelCentro.add(labelInvernadero);

		idText = new JTextField(20);
		panelCentro.add(idText);

		// Boton Buscar
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int idEmpleado = Integer.parseInt(idText.getText());
					if (checkNum(idEmpleado)) {
						if (idText.getText().isEmpty())
							JOptionPane.showMessageDialog(GUIVentasPorEmpleadoDeCaja.this, "Por favor, ingrese un id.",
									"Advertencia", JOptionPane.WARNING_MESSAGE);
						else
							ApplicationController.getInstance().manageRequest(new Context(
									Evento.VENTAS_POR_EMPLEADO_DE_CAJA, Integer.parseInt(idText.getText())));
					} else
						JOptionPane.showMessageDialog(GUIVentasPorEmpleadoDeCaja.this, "Error en el formato de datos",
								"Advertencia", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIVentasPorEmpleadoDeCaja.this, "Error en el formato de datos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelCentro.add(botonBuscar);

		// Tabla
		tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, null);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(750, 250));
		mainPanel.add(scroll);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIVentasPorEmpleadoDeCaja.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);
		mainPanel.add(panelBotones);

		this.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.VENTAS_POR_EMPLEADO_DE_CAJA_OK) {

			datos = (Set<TVenta>) context.getDatos();
			String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

			int i = 0;
			for (TVenta venta : datos) {
				tablaDatos[i][0] = venta.getId().toString();
				tablaDatos[i][1] = venta.getPrecioTotal().toString();
				tablaDatos[i][2] = venta.getFormaPago().toString();
				tablaDatos[i][3] = venta.getFecha().toString();
				tablaDatos[i][4] = venta.getActivo() ? "Si" : "No";
				i++;
			}

			tabla.setModel(new DefaultTableModel(tablaDatos, nombreColumnas));
		} else if (context.getEvento() == Evento.VENTAS_POR_EMPLEADO_DE_CAJA_KO) {
			if (context.getDatos() == null) {
				JOptionPane.showMessageDialog(this, "No existe el Empleado con id: " + idText.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "Error al tratar de listar las Ventas", "Error",
						JOptionPane.ERROR_MESSAGE);
		}
	}

	private Boolean checkNum(int num) {
		return num > 0;
	}
}