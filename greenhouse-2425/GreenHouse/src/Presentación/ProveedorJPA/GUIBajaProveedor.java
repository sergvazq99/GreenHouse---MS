/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GUIBajaProveedor extends JFrame implements IGUI {
	private JTextField textId;

	public GUIBajaProveedor() {
		super("Baja Proveedor");
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
		JLabel msgIntro = new JLabel("Introduzca el ID del Proveedor a dar de baja", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del PROVEEDOR
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
					Integer idProveedor = Integer.parseInt(idTexto);

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.BAJA_PROVEEDOR, idProveedor));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIBajaProveedor.this, "Error en el formato del ID", "Error",
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
				GUIBajaProveedor.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}
	
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.BAJA_PROVEEDOR_OK) {

			JOptionPane.showMessageDialog(this, "Proveedor con id: " + resultado + " dado de baja correctamente", "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIBajaProveedor.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
		} else if (context.getEvento() == Evento.BAJA_PROVEEDOR_KO) {

			switch (resultado) {
			case -1:
				JOptionPane.showMessageDialog(this, "Error al dar de baja el Proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -2:
				JOptionPane.showMessageDialog(this, "Error: El id debe ser mayor que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: Proveedor ya está dado de Baja.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor está vinculado a una marca.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al dar de alta el Proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
}