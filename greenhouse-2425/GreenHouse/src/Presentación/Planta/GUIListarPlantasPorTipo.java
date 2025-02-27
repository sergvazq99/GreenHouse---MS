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
import javax.swing.JComboBox;
import Negocio.Planta.TPlanta;

import javax.swing.JPanel;

public class GUIListarPlantasPorTipo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	String seleccion = "";

	public GUIListarPlantasPorTipo(Set<TPlanta> datos) {
		super("Listar Plantas Por Tipo");
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
	    
	    JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Seleccione el tipo de planta que desee", 1, 10, 80, 20, Color.BLACK);
	    msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
	    mainPanel.add(msgIntroIDCabecera);
	    
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    
	    //PANEL DE TIPO DE LA PLANTA
	    JPanel paneltipo = new JPanel();
	    
	    mainPanel.add(paneltipo);
	    
	    JLabel labelTPlanta = ComponentsBuilder.createLabel("Tipo de planta: ", 10, 100, 80, 20,Color.BLACK);
 		paneltipo.add(labelTPlanta);

 		JComboBox<String> tipoPlanta = new JComboBox<String>();
 		tipoPlanta.addItem("Frutal");
 		tipoPlanta.addItem("No Frutal");
 		tipoPlanta.setPreferredSize(new Dimension(250, 25));
 		paneltipo.add(tipoPlanta);
 		
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
 					
 					String selected = (String) tipoPlanta.getSelectedItem();
 					
                    if (selected.equals("Frutal")) {
             
                    	ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PLANTAS_POR_TIPO,"Frutal"));
                    } 
                    else {

                    	ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PLANTAS_POR_TIPO,"No Frutal"));
                    }
 			
 				} catch (Exception e1) {
 					
 				}
 			}
 		});
 		
 		panelBotones.add(botonAceptar);

	}



	@Override
	public void actualizar(Context context) {
		
		switch(context.getEvento()) {
		case Evento.LISTAR_PLANTAS_POR_TIPO_KO:
			GUIMSG.showMessage("No existe plantas del tipo seleccionado", "LISTAR PLANTAS POR TIPO", true);
			break;
		case  Evento.LISTAR_PLANTAS_POR_TIPO_OK:
			
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PLANTAS_VISTA,context.getDatos()));
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PLANTAS POR TIPO", true);
			break;
		
	}
	}
		
	
}