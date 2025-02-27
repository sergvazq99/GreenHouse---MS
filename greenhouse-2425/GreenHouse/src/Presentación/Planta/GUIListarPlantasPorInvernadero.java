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
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.Planta.TPlanta;

import javax.swing.JPanel;


public class GUIListarPlantasPorInvernadero extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIListarPlantasPorInvernadero(Set<TPlanta> datos) {
		super("Listar Plantas Por Invernadero");
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
	    
	    JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Seleccione el invernadero", 1, 10, 80, 20, Color.BLACK);
	    msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
	    mainPanel.add(msgIntroIDCabecera);
		
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    
	    //PANEL DE TIPO DE LA PLANTA
	    JPanel panelinvernadero = new JPanel();
	    mainPanel.add(panelinvernadero);
	    
 		JLabel labelID = ComponentsBuilder.createLabel("ID del invernadero: ", 10, 100, 80, 20,Color.BLACK);
 		panelinvernadero.add(labelID);
 		JTextField textID = new JTextField(25);
 		panelinvernadero.add(textID);
	    
 		//PANEL DE LOS BOTONES
 		JPanel panelBotones = new JPanel();
 		mainPanel.add(panelBotones);
 		
		//BOTON DE ACEPTAR
 		JButton botonAceptar = new JButton("Aceptar");
 		
 		
 		botonAceptar.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				setVisible(false);
 				
 				try {
 					
 					int id = Integer.parseInt(textID.getText());
 	

                    ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PLANTAS_DE_INVERNADERO,id));
                
 			
 				} catch (Exception e1) {
 					GUIMSG.showMessage("Formato incorrecto", "LISTAR PLANTAS POR INVERNADERO", true);
 				}
 			}
 		});
 		
 		panelBotones.add(botonAceptar);
	}

	@Override
	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.LISTAR_PLANTAS_DE_INVERNADERO_KO:
			GUIMSG.showMessage("No existe plantas en el invernadero seleccionado o el invernadero seleccionado no existe", "LISTAR PLANTAS POR INVERNADERO", true);
			break;
		case  Evento.LISTAR_PLANTAS_DE_INVERNADERO_OK:
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PLANTAS_VISTA,context.getDatos()));
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PLANTAS POR INVERNADERO", true);
			break;
		
	}

		
	}
}