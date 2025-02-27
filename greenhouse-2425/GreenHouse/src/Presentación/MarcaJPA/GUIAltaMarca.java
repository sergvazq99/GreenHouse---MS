package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JTextField;

import Negocio.MarcaJPA.TMarca;

import javax.swing.JDialog;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAltaMarca extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JTextField jTextField;

	private JDialog jDialog;

	private JButton botonAceptar;

	private JButton botonCancelar;

	private JPanel mainPanel;

	private JLabel msgIntroIDCabecera;

	public GUIAltaMarca() {
		super("Alta marca");
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

		msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca los datos de la Marca que desea dar de alta", 1,
				10, 80, 20, Color.BLACK);
		msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIDCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// Campo para introducir el nombre
		JLabel labelNombre = new JLabel("Nombre de la marca: ");
		labelNombre.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelNombre);

		JTextField textNombre = new JTextField(20);
		textNombre.setMaximumSize(textNombre.getPreferredSize());
		mainPanel.add(textNombre);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Campo para introducir el país de origen
		JLabel labelPaisOrigen = new JLabel("Pais de origen: ");
		labelPaisOrigen.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelPaisOrigen);

		JTextField textPaisOrigen = new JTextField(20);
		textPaisOrigen.setMaximumSize(textPaisOrigen.getPreferredSize());
		mainPanel.add(textPaisOrigen);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		// BOTON ACEPTAR (GUARDAR LOS DATOS DEL ALTA)
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					// nombre y país de origen
					String nombre = textNombre.getText();
					String paisOrigen = textPaisOrigen.getText();

					ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_MARCA,
							new TMarca(0, nombre != null ? nombre : "", paisOrigen != null ? paisOrigen : "", true)));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIAltaMarca.this, "Error en el formato de los datos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		panelBotones.add(botonAceptar);

		// BOTON CANCELAR
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIAltaMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);
	
		this.setVisible(true);
		this.setResizable(true);
	
	}

	public void actualizar(Context context) {
		int res = (int) context.getDatos();
		
		if(context.getEvento() == Evento.ALTA_MARCA_OK) {
			JOptionPane.showMessageDialog(this, "Marca dada de alta correctamente con id " + res, "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIAltaMarca.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));

		} else if (context.getEvento() == Evento.ALTA_MARCA_KO) {
			switch (res) {
            case -2:
                JOptionPane.showMessageDialog(this, "Marca ya existente con dicho nombre activa", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "Error en la transacción", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -24:
                JOptionPane.showMessageDialog(this, "Marca ya existente con ese nombre inactiva,\nponer los mismos datos que la inactiva (nombre y país de origen)", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al dar de alta la marca", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
		}

	}
}