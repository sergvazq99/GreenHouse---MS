/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TProveedor;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class GUIMostrarProveedorPorId extends JFrame implements IGUI {
	private JTextField textId; // Campo para introducir el ID del sistema de riego

	public GUIMostrarProveedorPorId() {
		super("Mostrar Proveedor por ID");
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
		// Panel principal con GridBagLayout para mayor control sobre la alineacion y el
		// centrado
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre los componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2; // Toma dos columnas para el titulo
		JLabel msgIntro = new JLabel("Introduzca el ID del Proveedor a mostrar", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Campo para el ID del Proveedor
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
		gbc.gridwidth = 2; // Los botones ocupar�n dos columnas
		gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones
		mainPanel.add(panelBotones, gbc);

		// Bot�n de aceptar
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idTexto = Integer.parseInt(textId.getText());

					// Crear un contexto con el evento de mostrar y el ID del sistema
					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.MOSTRAR_PROVEEDORES_POR_ID, idTexto));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIMostrarProveedorPorId.this, "Error en el formato del ID", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonAceptar);

		// Boton de cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMostrarProveedorPorId.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	public void actualizar(Context context) {
		TProveedor proveedor = (TProveedor) context.getDatos();

		if (context.getEvento() == Evento.MOSTRAR_PROVEEDORES_POR_ID_OK) {
			proveedor = (TProveedor) context.getDatos();
			String texto = "ID: " + proveedor.getId() + "\nNombre: " + proveedor.getNombre() + "\nCIF: "
					+ proveedor.getCIF() + "\nTelefono: " + proveedor.getTelefono() + "\nActivo: "
					+  (proveedor.getActivo() ? "Si" : "No");

			JOptionPane.showMessageDialog(this, texto, "Proveedor", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
		} else if (context.getEvento() == Evento.MOSTRAR_PROVEEDORES_POR_ID_KO) {
			switch (proveedor.getId()) {
			case -1:
				JOptionPane.showMessageDialog(this, "Error al mostrar el Proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -2:
				JOptionPane.showMessageDialog(this, "Error: El id debe ser mayor que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				break;
			}
		}
	}
}