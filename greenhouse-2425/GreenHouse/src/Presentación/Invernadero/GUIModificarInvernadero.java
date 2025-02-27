package Presentacion.Invernadero;

import javax.swing.JFrame;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.Invernadero.TInvernadero;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIModificarInvernadero extends JFrame implements IGUI {
	private JTextField textNombre;
	private JTextField textSustrato;
	private JTextField textTipoIluminacion;
	private JTextField textId;

	public GUIModificarInvernadero() {
		super("Modificar Invernadero");
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
		JLabel msgIntro = new JLabel("Introduzca el ID del Invernadero a modificar", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del Invernadero
		JLabel labelId = new JLabel("ID:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 1;
		mainPanel.add(labelId, gbc);
		textId = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId, gbc);

		// Titulo
		gbc.gridwidth = 2; // Dos columnas para el t�tulo
		JLabel msgDatos = new JLabel("Introduzca los datos del Invernadero", JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(msgDatos, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Nombre
		JLabel labelNombre = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(labelNombre, gbc);
		textNombre = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textNombre, gbc);

		// Sustrato
		JLabel labelSustrato = new JLabel("Sustrato:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		mainPanel.add(labelSustrato, gbc);
		textSustrato = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textSustrato, gbc);

		// Tipo de Iluminacion
		JLabel labelTipoIluminacion = new JLabel("Tipo de Iluminacion:");
		gbc.gridx = 0;
		gbc.gridy = 5;
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

					Integer id = Integer.parseInt(textId.getText());
					String nombre = textNombre.getText();
					String sustrato = textSustrato.getText();
					String iluminacion = textTipoIluminacion.getText();

					TInvernadero invernadero = new TInvernadero();
					invernadero.setId(id != null ? id : null);
					invernadero.setNombre(nombre != null ? nombre : "");
					invernadero.setSustrato(sustrato != null ? sustrato : "");
					invernadero.setTipo_iluminacion(iluminacion != null ? iluminacion : "");

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.MODIFICAR_INVERNADERO, invernadero));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIModificarInvernadero.this, "Error en el formato de los datos",
							"Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		panelBotones.add(botonAceptar);

		// BOTON CANCELAR
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIModificarInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.MODIFICAR_INVERNADERO_OK) {

			JOptionPane.showMessageDialog(this, "Invernadero con id " + resultado + " modificado correctamente",
					"Exito", JOptionPane.INFORMATION_MESSAGE);
			GUIModificarInvernadero.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
		} else if (context.getEvento() == Evento.MODIFICAR_INVERNADERO_KO) {

			switch (resultado) {
			case -2:
				JOptionPane.showMessageDialog(this, "Error: el ID proporcionado debe ser mayor o igual a 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -23:
				JOptionPane.showMessageDialog(this, "Error: No existe un Invernadero con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -24:
				JOptionPane.showMessageDialog(this, "Error: No existe un Invernadero con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -25:
				JOptionPane.showMessageDialog(this, "Error: Ya existe un Invernadero con el nombre proporcionado.",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al modificar el invernadero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}

	}
}