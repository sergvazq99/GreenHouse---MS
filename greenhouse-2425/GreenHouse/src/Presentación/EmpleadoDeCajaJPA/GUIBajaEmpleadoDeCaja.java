/**
 * 
 */
package Presentacion.EmpleadoDeCajaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.Fabricante.GUIBajaFabricante;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JDialog;
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

public class GUIBajaEmpleadoDeCaja extends JFrame implements IGUI {

	private JTextField textId;

	public GUIBajaEmpleadoDeCaja() {
		super("Baja Empleado de Caja");
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
		JLabel msgIntro = new JLabel("Introduzca el ID del empleado a dar de baja", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del empleado
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

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.BAJA_EMPLEADO_DE_CAJA, idSistema));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIBajaEmpleadoDeCaja.this, "Error en el formato del ID", "Error",
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
				GUIBajaEmpleadoDeCaja.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
			}
		});

		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
	    int resultado = (int) context.getDatos();

	    if (context.getEvento() == Evento.BAJA_EMPLEADO_DE_CAJA_OK) {
	        JOptionPane.showMessageDialog(this, "Empleado de caja " + resultado + " dado de baja correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	    } else if (context.getEvento() == Evento.BAJA_EMPLEADO_DE_CAJA_KO) {

	        switch (resultado) {
	            case -4:
	                JOptionPane.showMessageDialog(this, "Error: El ID del empleado es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
	                break;
	            case -403:
	                JOptionPane.showMessageDialog(this, "Error: El empleado ya está inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
	                break;
	            case -404:
	                JOptionPane.showMessageDialog(this, "Error: El empleado especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
	                break;
	            default:
	                JOptionPane.showMessageDialog(this, "Error desconocido al dar de baja el empleado de caja.", "Error", JOptionPane.ERROR_MESSAGE);
	                break;
	        }
	    }
	}
}