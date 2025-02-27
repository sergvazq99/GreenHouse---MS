package Presentacion.Fabricante;

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
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GUIBajaFabricante extends JFrame implements IGUI {

	private JTextField textId;

	public GUIBajaFabricante() {
		super("Baja Fabricante");
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
		// Panel principal
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2;
		JLabel msgIntro = new JLabel("Introduzca el ID del fabricante a dar de baja", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del sistema de riego
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
					Integer idSistema = Integer.parseInt(textId.getText());

					ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_FABRICANTE, idSistema));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIBajaFabricante.this, "Error en el formato del ID", "Error",
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
				GUIBajaFabricante.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.FABRICANTE_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();

		if (context.getEvento() == Evento.BAJA_FABRICANTE_OK)

			JOptionPane.showMessageDialog(this, "Fabricante dado de baja correctamente con id: " + resultado, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		else if (context.getEvento() == Evento.BAJA_FABRICANTE_KO)
			switch (resultado) {
			case -2:
				JOptionPane.showMessageDialog(this, "El fabricante ya esta dado de baja.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "El fabricante especificado no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "No puedes dar de baja a un fabricante con Sistemas de Riego asociados.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al dar de baja el fabricante.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
	}
}