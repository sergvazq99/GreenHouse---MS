package Presentacion.Entrada;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;

import Negocio.Entrada.TEntrada;

public class GUIAltaEntrada extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel msgIntroIDCabecera;

	private JPanel mainPanel;

	public GUIAltaEntrada() {
		super("Alta entrada");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		msgIntroIDCabecera = ComponentsBuilder
				.createLabel("Introduzca los datos de la Entrada que desea dar de alta", 1, 10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// Campo para introducir la fecha de la entrada
		JLabel labelFecha = new JLabel("Fecha (AAAA-MM-DD): ");
		labelFecha.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelFecha);

		JTextField textFecha = new JTextField(20);
		textFecha.setMaximumSize(textFecha.getPreferredSize());
		textFecha.setMaximumSize(textFecha.getPreferredSize());
		mainPanel.add(textFecha);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para introducir el precio de la entrada
		JLabel labelPrecio = new JLabel("Precio: ");
		labelPrecio.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelPrecio);

		JTextField textPrecio = new JTextField(20);
		textPrecio.setMaximumSize(textPrecio.getPreferredSize());
		mainPanel.add(textPrecio);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para introducir el stock de las entradas
		JLabel labelStock = new JLabel("Cantidad disponible: ");
		labelStock.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelStock);

		JTextField textStock = new JTextField(20);
		textStock.setMaximumSize(textStock.getPreferredSize());
		mainPanel.add(textStock);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para introducir el id del invernadero asociado
		JLabel labelIdInvernadero = new JLabel("ID del invernadero: ");
		labelIdInvernadero.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelIdInvernadero);

		JTextField textIdInvernadero = new JTextField(20);
		textIdInvernadero.setMaximumSize(textIdInvernadero.getPreferredSize());
		mainPanel.add(textIdInvernadero);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		// BOTON ACEPTAR (GUARDAR LOS DATOS DEL ALTA)
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//GUIAltaEntrada.this.setVisible(false);

				try {
					// fecha, precio, stock, id_invernadero
					Date fecha = Date.valueOf(textFecha.getText());
					Float precio = Float.parseFloat(textPrecio.getText());
					Integer stock = Integer.parseInt(textStock.getText());
					Integer idInvernadero = Integer.parseInt(textIdInvernadero.getText());

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.ALTA_ENTRADA,
									new TEntrada(fecha != null ? fecha : null, precio != null ? precio : 0,
											!textStock.getText().isEmpty() ? stock : 0,
											!textIdInvernadero.getText().isEmpty() ? idInvernadero : 0, true)));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIAltaEntrada.this, "Error en el formato de los datos", "Error",
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
				GUIAltaEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);

	}

	@Override
	public void actualizar(Context context) {

		int res = (int) context.getDatos();

		if (context.getEvento() == Evento.ALTA_ENTRADA_OK) {
			JOptionPane.showMessageDialog(this, "Entrada dada de alta correctamente con id " + res, "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIAltaEntrada.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));

		} else if (context.getEvento() == Evento.ALTA_ENTRADA_KO) {

			switch (res) {
				
			case -21:
				JOptionPane.showMessageDialog(this, "Error: el id de invernadero no está activo", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
				
			case -20:
				JOptionPane.showMessageDialog(this, "Error: el id de invernadero no existe", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
				
			case -48:
				JOptionPane.showMessageDialog(this, "Error: ya existe una entrada con dicha fecha asociada a un invernadero", "Error", JOptionPane.ERROR_MESSAGE);
				break;
				
			case -50:
				JOptionPane.showMessageDialog(this, "Error: ya existe la entrada con dicha fecha", "Error", JOptionPane.ERROR_MESSAGE);
				break;
				
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			}

		}
	}
}