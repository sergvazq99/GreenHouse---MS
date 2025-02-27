package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.Entrada.GUIModificarEntrada;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTextField;

import Negocio.Entrada.TEntrada;
import Negocio.MarcaJPA.TMarca;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class GUIModificarMarca extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton botonAceptar;

	private JButton botonCancelar;

	private JPanel mainPanel;

	private JLabel msgId;

	private JLabel msgDatosLeft;

	private JDialog jDialog;

	private JTextField jTextField;

	public GUIModificarMarca() {
		super("Modificar marca");
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
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// para introducir primero el id
		msgId = new JLabel("Introduzca el id de la marca: ");
		msgId.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(msgId);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para el id de la marca
		JLabel labelID = new JLabel("ID: ");
		labelID.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelID);

		JTextField textID = new JTextField(20);
		textID.setMaximumSize(textID.getPreferredSize());
		mainPanel.add(textID);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// para introducir el resto de datos, después del id
		msgDatosLeft = new JLabel("Introduzca el resto de datos: ");
		msgDatosLeft.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(msgDatosLeft);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para el nombre de la marca
		JLabel labelNombre = new JLabel("Nombre: ");
		labelNombre.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelNombre);

		JTextField textNombre = new JTextField(20);
		textNombre.setMaximumSize(textNombre.getPreferredSize());
		mainPanel.add(textNombre);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Campo para el nombre de la marca
		JLabel labelPaisOrigen = new JLabel("País de origen: ");
		labelPaisOrigen.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelPaisOrigen);

		JTextField textPaisOrigen = new JTextField(20);
		textPaisOrigen.setMaximumSize(textPaisOrigen.getPreferredSize());
		mainPanel.add(textPaisOrigen);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(75, 50, 100, 100);

		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					// Integer id_marca = Integer.parseInt(textID.getText());

					// nombre, pais de origen
					Integer id = Integer.parseInt(textID.getText());
					String nombre = textNombre.getText();
					String paisOrigen = textPaisOrigen.getText();

					TMarca marca = new TMarca();
					marca.setId(id != null ? id : null);
					marca.setNombre(nombre != null ? nombre : "");
					marca.setPais(paisOrigen != null ? paisOrigen : "");

					ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_MARCA, marca));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIModificarMarca.this, "Error en el formato de los datos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panelBotones.add(botonAceptar);

		// Botón cancelar
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIModificarMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);

	}

	public void actualizar(Context context) {
		int res = (int) context.getDatos();

		if (context.getEvento() == Evento.MODIFICAR_MARCA_OK) {
			JOptionPane.showMessageDialog(this, "Marca modificada correctamente con id " + res, "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIModificarMarca.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));

		} else if (context.getEvento() == Evento.MODIFICAR_MARCA_KO) {
			switch (res) {
			case -4:
				JOptionPane.showMessageDialog(this, "Nombre no válido", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -7:
				JOptionPane.showMessageDialog(this, "La marca no existe", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case -5:
				JOptionPane.showMessageDialog(this, "Ya existe marca activa con el mismo nombre", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case -8:
				JOptionPane.showMessageDialog(this, "La marca no se puede dar de baja porque está inactiva", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(this, "Error desconocido al modificar la marca", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		}

	}
}