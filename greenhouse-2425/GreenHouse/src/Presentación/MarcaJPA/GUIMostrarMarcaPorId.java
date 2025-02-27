package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import Negocio.Entrada.TEntrada;
import Negocio.MarcaJPA.TMarca;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class GUIMostrarMarcaPorId extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton botonAceptar;

	private JButton botonCancelar;

	private JLabel textIdMarca;

	private JTextField id;

	private JPanel mainPanel;

	private JDialog jDialog;

	public GUIMostrarMarcaPorId() {
		super("Mostrar marca");
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
		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre los componentes
		this.setContentPane(mainPanel);

		// Título
		gbc.gridwidth = 2; // Toma dos columnas para el titulo
		JLabel msgIntro = new JLabel("Introduzca el ID de la marca", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Campo para el id de la marca
		textIdMarca = new JLabel("ID: ");
		gbc.gridx = 0; // Columna 0
		mainPanel.add(textIdMarca, gbc);
		id = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(id, gbc);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2; // Los botones ocuparon dos columnas
		gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones
		mainPanel.add(panelBotones, gbc);

		// Boton de aceptar
		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(75, 50, 100, 100);

		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Integer id_marca = Integer.parseInt(id.getText());

					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.MOSTRAR_MARCA, !id.getText().isEmpty() ? id_marca : 0));
					setVisible(false);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIMostrarMarcaPorId.this, "Error en el formato del ID", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		panelBotones.add(botonAceptar);

		// Boton de cancelar
		botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(200, 50, 100, 100);

		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMostrarMarcaPorId.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
			}
		});

		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);

	}

	public void actualizar(Context context) {
		TMarca marca = (TMarca) context.getDatos();
		if (context.getEvento() == Evento.MOSTRAR_MARCA_OK) {
			
			String texto = "ID: " + marca.getId() + "\nNombre: " + marca.getNombre() + "\nPais de origen: "
					+ marca.getPais() + "\nActivo: " + (marca.getActivo() ? "Si" : "No");

			JOptionPane.showMessageDialog(this, texto, "Marca", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));

		} else if (context.getEvento() == Evento.MOSTRAR_MARCA_KO) {
			switch (marca.getId()) {
			case -1:
				JOptionPane.showMessageDialog(this, "Error al mostrar la marca", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -2:
				JOptionPane.showMessageDialog(this, "Error: El id debe ser mayor que 0", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Error: La marca no existe", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				break;
			}
			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_MARCA_VISTA, null));

		}
	}
}