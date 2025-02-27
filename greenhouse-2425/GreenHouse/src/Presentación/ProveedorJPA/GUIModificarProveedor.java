/**
 * 
 */
package Presentacion.ProveedorJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JTextField;

import Negocio.ProveedorJPA.TProveedor;

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
import javax.swing.JPanel;
import javax.swing.JDialog;


@SuppressWarnings("serial")
public class GUIModificarProveedor extends JFrame implements IGUI {
	
	private JTextField textId;
	private JTextField textNombre;
	private JTextField textCIF;
	private JTextField textTelefono;

	public GUIModificarProveedor() {
		super("Modificar Proveedor");
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
		// Panel principal
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2; 
		JLabel msgIntro = new JLabel("Introduzca el ID del Proveeedor a modificar", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// ID del Proveedor
		JLabel labelId = new JLabel("ID:");
		gbc.gridx = 0; // Columna 0
		gbc.gridy = 1;
		mainPanel.add(labelId, gbc);
		textId = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId, gbc);

		// Titulo
		gbc.gridwidth = 2; 
		JLabel msgDatos = new JLabel("Introduzca los datos del Proveedor", JLabel.CENTER);
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

		// CIF
		JLabel labelCIF = new JLabel("CIF:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		mainPanel.add(labelCIF, gbc);
		textCIF = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textCIF, gbc);

		// Telefono
		JLabel labelTelefono = new JLabel("Telefono:");
		gbc.gridx = 0;
		gbc.gridy = 5;
		mainPanel.add(labelTelefono, gbc);
		textTelefono = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textTelefono, gbc);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(panelBotones, gbc);
		// BOTON ACEPTAR (GUARDAR LOS DATOS DEL PROVEEDOR MODIFICADO)
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					Integer id = Integer.parseInt(textId.getText());
					String nombre = textNombre.getText();
					String cif = textCIF.getText();
					String telefono = textTelefono.getText();

					TProveedor proveedor = new TProveedor();
					proveedor.setId(id != null ? id : null);
					proveedor.setNombre(nombre != null ? nombre : "");
					proveedor.setCIF(cif != null ? cif : "");
					proveedor.setTelefono(telefono != null ? telefono : "");

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.MODIFICAR_PROVEEDORES, proveedor));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIModificarProveedor.this, "Error en el formato de los datos",
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
				GUIModificarProveedor.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
		if (context.getEvento() == Evento.MODIFICAR_PROVEEDORES_OK) {

			JOptionPane.showMessageDialog(this, "Proveedor con id: "+ resultado + " modificado correctamente " , "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIModificarProveedor.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
		} else if (context.getEvento() == Evento.MODIFICAR_PROVEEDORES_KO) {

			switch (resultado) {
			case -1:
				JOptionPane.showMessageDialog(this, "Error al modificar el Proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -2:
				JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: El formato del Telefono es incorrecto.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Error: El Proveedor no existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "Error: Ya existe un proveedor con el CIF proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al modificar el Proveedor.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
}