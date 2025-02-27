package Presentacion.Invernadero;

import javax.swing.JFrame;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Invernadero.TInvernadero;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GUIAltaInvernadero extends JFrame implements IGUI {

	private JTextField textNombre;
	private JTextField textSustrato;
	private JTextField textTipoIluminacion;

	public GUIAltaInvernadero() {
		super("Alta Invernadero");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniGUI();
	}

	public void iniGUI() {
		// Panel principal
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2; // Dos columnas para el t�tulo
		JLabel msgIntro = new JLabel("Introduzca los datos del Invernadero", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Nombre
		JLabel labelNombre = new JLabel("Nombre:");
		gbc.gridx = 0;
		mainPanel.add(labelNombre, gbc);
		textNombre = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textNombre, gbc);

		// Sustrato
		JLabel labelSustrato = new JLabel("Sustrato:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(labelSustrato, gbc);
		textSustrato = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textSustrato, gbc);

		// Tipo de Iluminacion
		JLabel labelTipoIluminacion = new JLabel("Tipo de Iluminacion:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(labelTipoIluminacion, gbc);
		textTipoIluminacion = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textTipoIluminacion, gbc);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panelBotones, gbc);
		// BOTON ACEPTAR (GUARDAR LOS DATOS DEL ALTA)
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = textNombre.getText();
					String sustrato = textSustrato.getText();
					String iluminacion = textTipoIluminacion.getText();

					ApplicationController.getInstance().manageRequest(
							new Context(Evento.ALTA_INVERNADERO, new TInvernadero(nombre != null ? nombre : "",
									sustrato != null ? sustrato : "", iluminacion != null ? iluminacion : "")));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIAltaInvernadero.this, "Error en el formato de los datos", "Error",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		panelBotones.add(botonAceptar);

		// BOTON CANCELAR
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIAltaInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.ALTA_INVERNADERO_OK) {

			JOptionPane.showMessageDialog(this, "Invernadero dado de alta correctamente con id: " + resultado, "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIAltaInvernadero.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
		} else if (context.getEvento() == Evento.ALTA_INVERNADERO_KO) {

			switch (resultado) {
			case -3:
				JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -23:
				JOptionPane.showMessageDialog(this, "Error: Ya existe un Invernadero con el mismo nombre.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al modificar el Invernadero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
}