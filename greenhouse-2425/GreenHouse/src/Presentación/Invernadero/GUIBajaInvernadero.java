package Presentacion.Invernadero;

import javax.swing.JFrame;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class GUIBajaInvernadero extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textId;

	public GUIBajaInvernadero() {
		super("Baja Invernadero");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
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
		gbc.gridwidth = 2;
		JLabel msgIntro = new JLabel("Introduzca el ID del Invernadero a dar de baja", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del Invernadero
		JLabel labelId = new JLabel("ID:");
		gbc.gridx = 0; // Columna 0
		mainPanel.add(labelId, gbc);
		textId = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId, gbc);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panelBotones, gbc);

		// Boton Aceptar
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String idTexto = textId.getText();
					Integer idInvernadero = Integer.parseInt(idTexto);

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.BAJA_INVERNADERO, idInvernadero));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIBajaInvernadero.this, "Error en el formato del ID", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonAceptar);

		// Boton Cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIBajaInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.BAJA_INVERNADERO_OK) {

			JOptionPane.showMessageDialog(this, "Invernadero con id " + resultado + " dado de baja correctamente", "Exito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.BAJA_INVERNADERO_KO) {

			switch (resultado) {
			case -24:
				JOptionPane.showMessageDialog(this,
						"Error: invernadero no puede darse de baja si tiene plantas, entradas o sistemas de riego activos y vinculados.",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -23:
				JOptionPane.showMessageDialog(this, "Error: El Invernadero especificado no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al eliminar el Invernadero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
}