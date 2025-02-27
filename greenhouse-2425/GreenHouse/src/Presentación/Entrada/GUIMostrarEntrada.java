package Presentacion.Entrada;

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

import Negocio.Entrada.TEntrada;

import javax.swing.JPanel;

public class GUIMostrarEntrada extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JLabel textIdEntrada;

	private JTextField id;

	private JPanel mainPanel;

	public GUIMostrarEntrada() {
		super("Mostar entrada");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		//this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		
        // Panel principal con GridBagLayout para mayor control sobre la alineacion y el centrado
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre los componentes
        this.setContentPane(mainPanel);

		// Título
        gbc.gridwidth = 2; // Toma dos columnas para el titulo
        JLabel msgIntro = new JLabel("Introduzca el ID de la entrada a mostrar", JLabel.CENTER);
        mainPanel.add(msgIntro, gbc);
        
        // Resetear para los campos
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        // Campo para el id de la entrada
        textIdEntrada = new JLabel("ID: ");
        gbc.gridx = 0; // Columna 0
        mainPanel.add(textIdEntrada, gbc);
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
					Integer id_entrada = Integer.parseInt(id.getText());
					// Vamos a tratar el error de campos nulos
					//GUIMostrarEntrada.this.setVisible(false);
					ApplicationController.getInstance().manageRequest(
							new Context(Evento.MOSTRAR_ENTRADA_POR_ID, !id.getText().isEmpty() ? id_entrada : 0));
					setVisible(false);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(GUIMostrarEntrada.this, "Error en el formato del ID", "Error",
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
				GUIMostrarEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));
			}
		});

		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);

	}

	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.MOSTRAR_ENTRADA_OK) {
			TEntrada entrada = (TEntrada) context.getDatos();
			String texto = "ID: " + entrada.getId() + "\nId invernadero: " + entrada.getIdInvernadero() + "\nFecha: "
					+ entrada.getFecha() + "\nPrecio: " + entrada.getPrecio() + "\nStock: " + entrada.getStock()
					+ "\nActivo: " + (entrada.getActivo() ? "Si" : "No");

			JOptionPane.showMessageDialog(this, texto, "Entrada", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));

		} else if (context.getEvento() == Evento.MOSTRAR_ENTRADA_KO) {
			JOptionPane.showMessageDialog(this, "No existe entrada con ID: " + ((TEntrada) context.getDatos()).getId(),
					"Error", JOptionPane.ERROR_MESSAGE);

			 this.setVisible(false);
			 ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_ENTRADA_POR_ID_VISTA, null));

		}


	}
}