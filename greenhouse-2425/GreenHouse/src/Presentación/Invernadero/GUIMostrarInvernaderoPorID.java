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
public class GUIMostrarInvernaderoPorID extends JFrame implements IGUI {

	private JTextField textId; // Campo para introducir el ID del sistema de riego

	public GUIMostrarInvernaderoPorID() {
		super("Mostrar Invernadero por ID");
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
		// Panel principal con GridBagLayout para mayor control sobre la alineacion y el
		// centrado
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre los componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2; // Toma dos columnas para el titulo
		JLabel msgIntro = new JLabel("Introduzca el ID del Invernadero a mostrar", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Campo para el ID del Invernadero
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
							.manageRequest(new Context(Evento.MOSTRAR_INVERNADERO_POR_ID, idTexto));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIMostrarInvernaderoPorID.this, "Error en el formato del ID",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelBotones.add(botonAceptar);

		// Boton de cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMostrarInvernaderoPorID.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.MOSTRAR_INVERNADERO_POR_ID_OK) {
			TInvernadero invernadero = (TInvernadero) context.getDatos();
			String texto = "ID: " + invernadero.getId() + "\nNombre: " + invernadero.getNombre() + "\nSustrato: "
					+ invernadero.getSustrato() + "\nTipo de Iluminacion: " + invernadero.getTipo_iluminacion()
					+ "\nActivo: " + invernadero.isActivo();

			JOptionPane.showMessageDialog(this, texto, "Invernadero", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
		} else if (context.getEvento() == Evento.MOSTRAR_INVERNADERO_POR_ID_KO) {

			JOptionPane.showMessageDialog(this, "No existe el Invernadero con el ID proporcionado", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}