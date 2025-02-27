package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBajaMarca extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	
	private JButton botonAceptar;
	
	private JButton botonCancelar;

	private JDialog jDialog;

	private JPanel jPanel2;
	
	private JLabel labelID;

	private JTextField textID;


	public GUIBajaMarca() {
		super("Baja marca");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
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
		JLabel msgIntroIdCabecera = ComponentsBuilder
				.createLabel("Introduzca el ID de la marca que desea dar de baja", 1, 10, 80, 20, Color.BLACK);
		msgIntroIdCabecera.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(msgIntroIdCabecera);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Campo para introducir el ID de la entrada
		labelID = new JLabel("ID de la marca:");
		labelID.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(labelID);

		textID = new JTextField(20);
		textID.setMaximumSize(textID.getPreferredSize());
		mainPanel.add(textID);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// Panel para los botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);
		
		// BOTON ACEPTAR (PROCESAR LA BAJA)
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int id = Integer.parseInt(textID.getText());
					ApplicationController.getInstance()
							.manageRequest(new Context(Evento.BAJA_MARCA, id));
//					ApplicationController.getInstance()
//					.manageRequest(new Context(Evento.BAJA_MARCA, !textID.getText().isEmpty() ? id : 0));


					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(GUIBajaMarca.this, "Error en el formato de los datos", "Error",
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

				GUIBajaMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}
	
	
	
	public void actualizar(Context context) {

		int res = (int) context.getDatos();
		
		if(context.getEvento() == Evento.BAJA_MARCA_OK) {
			JOptionPane.showMessageDialog(this, "Marca dada de baja correctamente con id " + res, "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			GUIBajaMarca.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));

		} else if (context.getEvento() == Evento.BAJA_MARCA_KO) {
            switch (res) {
            case -2:
                JOptionPane.showMessageDialog(this, "La marca ya está dada de baja", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -4:
                JOptionPane.showMessageDialog(this, "El id debe ser un nº igual o mayor que 1", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -12:
                JOptionPane.showMessageDialog(this, "La marca está asociada a un proveedor", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -13:
                JOptionPane.showMessageDialog(this, "La marca tiene productos asociados", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
                
            case -3:
                JOptionPane.showMessageDialog(this, "La marca con dicho id no existe", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
                
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al dar de baja la marca", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
		}
		
	}
}