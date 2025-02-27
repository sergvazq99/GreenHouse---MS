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
import javax.swing.JTextField;

import Negocio.Invernadero.TTiene;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIDesvincularSRInvernadero extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextField textId_Invernadero;
	private JTextField textId_SisRiego;

	public GUIDesvincularSRInvernadero() {
		super("Desvincular Sistema de Riego de Invernadero");
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
		JLabel msgIntro = new JLabel("Introduzca los IDs del Invernadero y sistema de Riego", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del Invernadero
		JLabel labelIdInvernadero = new JLabel("ID Invernadero:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 1; // Columna 0

		mainPanel.add(labelIdInvernadero, gbc);
		textId_Invernadero = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId_Invernadero, gbc);

		// ID del Invernadero
		JLabel labelIdSR = new JLabel("ID Sistema de Riego:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 2; // Columna 0
		mainPanel.add(labelIdSR, gbc);
		textId_SisRiego = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId_SisRiego, gbc);

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
					String idTexto1 = textId_Invernadero.getText();
					Integer idInvernadero = idTexto1 != null ? Integer.parseInt(idTexto1) : 0;

					String idTexto2 = textId_SisRiego.getText();
					Integer idSisRiego = idTexto2 != null ? Integer.parseInt(idTexto2) : 0;

					TTiene tiene = new TTiene();
					tiene.setId_Invernadero(idInvernadero);
					tiene.setId_SistemasDeRiego(idSisRiego);

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.DESVINCULAR_SISTEMA_RIEGO_DE_INVERNADERO, tiene));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIDesvincularSRInvernadero.this, "Error en el formato del ID o campos vacios",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonAceptar);

		// Boton Cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIDesvincularSRInvernadero.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.DESVINCULAR_SISTEMA_RIEGO_DE_INVERNADERO_OK) {

			JOptionPane.showMessageDialog(this, "Invernadero y Sistema de riego se han desvinculados correctamente",
					"Exito", JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.DESVINCULAR_SISTEMA_RIEGO_DE_INVERNADERO_KO) {

			switch (resultado) {
			case -2:
				JOptionPane.showMessageDialog(this, "Error: Los IDs tienen que ser mayores o iguales que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: El Invernadero introducido no Existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Error: El Sistema de Riego introducido no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this,
						"Error: El Invernadero Y el Sistemad de Riego no se encuentran vinculados.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}

	}
}