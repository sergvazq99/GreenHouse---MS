package Presentacion.Planta;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.GUIMSG;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

import javax.swing.JPanel;

public class GUIMostarPlantasPorID extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIMostarPlantasPorID() {
		super("Mostrar Planta Por ID");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 300;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}
	
	public void initGUI() {
		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    this.setContentPane(mainPanel);
	    this.setVisible(true);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	    
	    JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("MOSTRAR PLANTA", 1, 10, 80, 20, Color.BLACK);
	    msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
	    mainPanel.add(msgIntroIDCabecera);

	    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    JPanel panelID = new JPanel();
	    //PANEL DE TIPO DE LA PLANTA

	    mainPanel.add(panelID);

 		
 		JLabel labelID = ComponentsBuilder.createLabel("ID de la planta: ", 10, 100, 80, 20,Color.BLACK);
 		panelID.add(labelID);
 		JTextField textID = new JTextField(25);
 		panelID.add(textID);
 		
 		//PANEL DE LOS BOTONES
 		JPanel panelBotones = new JPanel();
 		mainPanel.add(panelBotones);
 		
 		//BOTON DE ACEPTAR
 		JButton botonAceptar = new JButton("Aceptar");
 		
 		
 		botonAceptar.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				
 				
 				try {
					int idtmp = Integer.parseInt(textID.getText());
 						
					ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_PLANTA_POR_ID,idtmp));
					setVisible(false);
				
 				} catch (Exception e1) {
 					GUIMSG.showMessage("Formato incorrecto", "MOSTRAR PLANTA", true);
 				}
 			}
 		});
 		
 		panelBotones.add(botonAceptar);

	}


	@Override
	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.MOSTRAR_PLANTA_POR_ID_KO:
			GUIMSG.showMessage("Planta no registrada", "MOSTRAR PLANTA", true);
			break;
		case  Evento.MOSTRAR_PLANTA_POR_ID_OK:
			if((TPlanta) context.getDatos() instanceof TPlantaFrutal ){
				GUIMSG.showMessage(context.getDatos().toString(), "MOSTRAR PLANTA", false);
			}
			else if((TPlanta) context.getDatos() instanceof TPlantaNoFrutal )
			GUIMSG.showMessage(context.getDatos().toString(), "MOSTRAR PLANTA", false);
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "MOSTRAR PLANTA", true);
			break;
		}
	}
}