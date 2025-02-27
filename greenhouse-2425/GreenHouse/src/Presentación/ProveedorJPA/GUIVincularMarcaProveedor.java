/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TMarcaProveedor;

@SuppressWarnings("serial")
public class GUIVincularMarcaProveedor extends JFrame implements IGUI {
	private JTextField textId_Proveedor;
	private JTextField textId_Marca;

	public GUIVincularMarcaProveedor() {
		super("Vincular Marca a Proveedor");
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
		JLabel msgIntro = new JLabel("Introduzca los IDs del Proveedor y la Marca", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del Invernadero
		JLabel labelIdProveedor = new JLabel("ID Proveedor:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 1; // Columna 0

		mainPanel.add(labelIdProveedor, gbc);
		textId_Proveedor = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId_Proveedor, gbc);

		// ID del Invernadero
		JLabel labelIdMarca = new JLabel("ID Marca:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 2; // Columna 0
		mainPanel.add(labelIdMarca, gbc);
		textId_Marca = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId_Marca, gbc);

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
					String idTexto1 = textId_Proveedor.getText();
					Integer idProveedor = idTexto1 != null ? Integer.parseInt(idTexto1) : 0;

					String idTexto2 = textId_Marca.getText();
					Integer idMarca = idTexto2 != null ? Integer.parseInt(idTexto2) : 0;

					TMarcaProveedor mp = new TMarcaProveedor();
					mp.setIdProveedor(idProveedor);
					mp.setIdMarca(idMarca);

					ApplicationController.getInstance().manageRequest(new Context(Evento.VINCULAR_MARCA_PROVEEDOR, mp));

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIVincularMarcaProveedor.this,
							"Error en el formato del ID o campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonAceptar);

		// Boton Cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIVincularMarcaProveedor.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.VINCULAR_MARCA_PROVEEDOR_OK) {

			JOptionPane.showMessageDialog(this, "Proveedor y Marca vinculados correctamente", "Exito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.VINCULAR_MARCA_PROVEEDOR_KO) {

			switch (resultado) {
			case -1:
				JOptionPane.showMessageDialog(this, "Error al vincular el Proveedor y la Marca.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -2:
				JOptionPane.showMessageDialog(this, "Error: Los IDs tienen que ser mayores que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor introducido no Existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Error: La Marca introducida no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor y la Marca ya se encuentran vinculados.",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
}