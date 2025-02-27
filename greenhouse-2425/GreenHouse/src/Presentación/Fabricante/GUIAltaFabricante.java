package Presentacion.Fabricante;

import javax.swing.JFrame;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteExtranjero;
import Negocio.Fabricante.TFabricanteLocal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GUIAltaFabricante extends JFrame implements IGUI {

	private JTextField textNombre;
	private JTextField textCodFabricante;
	private JTextField textTelefono;
	private JTextField textImpuestos;
	private JTextField textSubvenciones;
	private JTextField textPaisOrigen;
	private JTextField textAranceles;
	private Boolean tLocal = false;

	public GUIAltaFabricante() {
		super("Alta Fabricante");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 440;
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
		gbc.gridwidth = 2; // Dos columnas para el t�tulo
		JLabel msgIntro = new JLabel("Introduzca los datos del fabricante", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Nombre del fabricante
		JLabel labelNombre = new JLabel("Nombre:");
		gbc.gridx = 0;
		mainPanel.add(labelNombre, gbc);
		textNombre = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textNombre, gbc);

		// Código de fabricante
		JLabel labelPotenciaRiego = new JLabel("Código de Fabricante:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(labelPotenciaRiego, gbc);
		textCodFabricante = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textCodFabricante, gbc);

		// Teléfono
		JLabel labelCantidadAgua = new JLabel("Teléfono:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(labelCantidadAgua, gbc);
		textTelefono = new JTextField(20);
		gbc.gridx = 1;
		mainPanel.add(textTelefono, gbc);

		// Panel de botones local/extranjero
		JPanel localExtranjero = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(localExtranjero, gbc);

		// Local
		JPanel local = new JPanel(new GridLayout(2, 2, 0, 18));
		local.setVisible(false);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(local, gbc);

		// Impuestos
		JLabel labelImpuestos = new JLabel("Impuestos:");
		local.add(labelImpuestos);
		textImpuestos = new JTextField(20);
		local.add(textImpuestos);

		// Subvenciones
		JLabel labelSubvenciones = new JLabel("Subvenciones:");
		local.add(labelSubvenciones, gbc);
		textSubvenciones = new JTextField(20);
		local.add(textSubvenciones, gbc);

		// Extranjero
		JPanel extranjero = new JPanel(new GridLayout(2, 2, 0, 18));
		extranjero.setVisible(false);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(extranjero, gbc);

		// Pais de Origen
		JLabel labelOrigen = new JLabel("Pais de Origen:");
		extranjero.add(labelOrigen, gbc);
		textPaisOrigen = new JTextField(20);
		extranjero.add(textPaisOrigen, gbc);

		// Aranceles
		JLabel labelAranceles = new JLabel("Aranceles:");
		extranjero.add(labelAranceles, gbc);
		textAranceles = new JTextField(20);
		extranjero.add(textAranceles, gbc);

		// Boton local
		JButton bLocal = new JButton("Local");
		bLocal.addActionListener(a -> {
			tLocal = true;
			local.setVisible(true);
			extranjero.setVisible(false);
		});
		localExtranjero.add(bLocal);

		// Boton extranjero
		JButton bExtranjero = new JButton("Extranjero");
		bExtranjero.addActionListener(a -> {
			tLocal = false;
			local.setVisible(false);
			extranjero.setVisible(true);
		});
		localExtranjero.add(bExtranjero);

		// Panel de botones aceptar/cancelar
		JPanel okCancel = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(okCancel, gbc);

		// Boton Aceptar
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(a -> {
			try {
				TFabricante fabricante;
				if (tLocal) {
					fabricante = new TFabricanteLocal();
					((TFabricanteLocal) fabricante).setImpuesto(Integer.parseInt(textImpuestos.getText()));
					((TFabricanteLocal) fabricante).setSubvencion(Integer.parseInt(textSubvenciones.getText()));

				} else {
					fabricante = new TFabricanteExtranjero();
					((TFabricanteExtranjero) fabricante).setAranceles(Integer.parseInt(textAranceles.getText()));
					((TFabricanteExtranjero) fabricante).setPaisDeOrigen(textPaisOrigen.getText());
				}
				fabricante.setActivo(true);
				fabricante.setCodFabricante(textCodFabricante.getText());
				fabricante.setNombre(textNombre.getText());
				fabricante.setTelefono(textTelefono.getText());
				ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_FABRICANTE, fabricante));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(GUIAltaFabricante.this, "Error en el formato de los datos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		okCancel.add(botonAceptar);

		// Boton Cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(a -> {
			GUIAltaFabricante.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.FABRICANTE_VISTA, null));
		});
		okCancel.add(botonCancelar);

		setVisible(true);

	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();

		if (context.getEvento() == Evento.ALTA_FABRICANTE_OK)

			JOptionPane.showMessageDialog(this, "Fabricante dado de alta correctamente con id: " + resultado, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		else if (context.getEvento() == Evento.ALTA_FABRICANTE_KO)
			switch (resultado) {
			case -2:
				JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -3:
				JOptionPane.showMessageDialog(this, "Ya existe un fabricante con el mismo codigo y está activo.",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -4:
				JOptionPane.showMessageDialog(this, "Telefono invalido.", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "El fabricante que estas intentando modificar es local.",
						"Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -6:
				JOptionPane.showMessageDialog(this, "El fabricante que estas intentando modificar es extranjero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al dar de alta el fabricante.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}

	}

}