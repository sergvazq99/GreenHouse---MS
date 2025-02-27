package Presentacion.VentaJPA;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.VentaJPA.TVenta;
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

public class GUIModificarVenta extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private JTextField textID;
	private JTextField textEmp;
	private JComboBox<String> pagoBox;
	String[] pago = { "Visa", "Efectivo", "Master Card", "Bitcoin" };

	public GUIModificarVenta() {
		super("Modificar Venta");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
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
		JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca el ID de la Venta que quiere modificar ",
				1, 10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// id venta
		JPanel panelID = new JPanel();
		mainPanel.add(panelID);
		panelID.add(new JLabel("ID Venta: "));

		textID = new JTextField(20);
		panelID.add(textID);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// id empleado
		JPanel panelEmpleado = new JPanel();
		mainPanel.add(panelEmpleado);
		panelEmpleado.add(new JLabel("ID Empleado: "));

		textEmp = new JTextField(20);
		panelEmpleado.add(textEmp);

		// forma pago
		JPanel panelFormaPago = new JPanel();
		mainPanel.add(panelFormaPago);
		panelFormaPago.add(new JLabel("Forma de Pago: "));

		pagoBox = new JComboBox<String>(pago);
		panelFormaPago.add(pagoBox);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(75, 50, 100, 100);
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int idVenta = Integer.parseInt(textID.getText());
					int idProd = Integer.parseInt(textEmp.getText());
					if (checkNum(idVenta) && checkNum(idProd)) {
						TVenta venta = new TVenta();
						venta.setId(idVenta);
						venta.setFormaDePago((String) pagoBox.getSelectedItem());
						venta.setIdEmpleado(idProd);
						ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_VENTAS, venta));

					} else {
						JOptionPane.showMessageDialog(GUIModificarVenta.this, "Los datos no son correctos", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIModificarVenta.this, "Los datos no son correctos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		panelBotones.add(botonAceptar);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(200, 50, 100, 100);
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIModificarVenta.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}

	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.MODIFICAR_VENTAS_OK) {

			JOptionPane.showMessageDialog(this, "Venta modificada correctamente con id " + resultado, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.MODIFICAR_VENTAS_KO) {

			switch (resultado) {
			case -2:
				JOptionPane.showMessageDialog(this, "No existe la Venta con id: " + textID.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "La Venta con id: " + textID.getText() + "esta dada de baja",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "No existe Empleado con id: " + textEmp.getText(), "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al modificar la venta.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}

	private Boolean checkNum(int num) {
		return num > 0;
	}
}