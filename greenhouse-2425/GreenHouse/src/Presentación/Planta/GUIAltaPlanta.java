package Presentacion.Planta;

import javax.swing.JFrame;



import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.GUIMSG;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

import javax.swing.JPanel;

public class GUIAltaPlanta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIAltaPlanta() {
		super("Alta Planta");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
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
	    
	    JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("ALTA PLANTA", 1, 10, 80, 20, Color.BLACK);
	    msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
	    mainPanel.add(msgIntroIDCabecera);

	    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    
	    //PANEL DE TIPO DE LA PLANTA
	    JPanel paneltipo = new JPanel();
	    JPanel panelnombre = new JPanel();
	    JPanel panelnombreCientifico = new JPanel();
	    JPanel panelinvernadero = new JPanel();
	    
	    JPanel panelnombreFruta = new JPanel();
	    JPanel panelmaduracion = new JPanel();
	    JPanel panelhoja = new JPanel();
	    


 		mainPanel.add(paneltipo);
 		mainPanel.add(panelnombre);
 		mainPanel.add(panelnombreCientifico);
 		mainPanel.add(panelinvernadero);
 		
	    
	    mainPanel.add(panelnombreFruta);
 		mainPanel.add(panelmaduracion);
 		mainPanel.add(panelhoja);

 		JLabel labelTPlanta = ComponentsBuilder.createLabel("Tipo de planta: ", 10, 100, 80, 20,Color.BLACK);
 		paneltipo.add(labelTPlanta);

 		JComboBox<String> tipoPlanta = new JComboBox<String>();
 		tipoPlanta.addItem("Frutal");
 		tipoPlanta.addItem("No Frutal");
 		tipoPlanta.setPreferredSize(new Dimension(250, 25));
 		paneltipo.add(tipoPlanta);
 		
 		
 		JLabel labelnombre = ComponentsBuilder.createLabel("Nombre: ", 10, 100, 80, 20,Color.BLACK);
 		panelnombre.add(labelnombre);
 		JTextField textNombre = new JTextField(25);
 		panelnombre.add(textNombre);
 	
 		JLabel labelnombrecientifico = ComponentsBuilder.createLabel("Nombre Cientifico: ", 10, 100, 80, 20,Color.BLACK);
 		panelnombreCientifico.add(labelnombrecientifico);
 		JTextField textNombreCientifico = new JTextField(25);
 		panelnombreCientifico.add(textNombreCientifico);
 		
 		
 		JLabel labelinvernadero = ComponentsBuilder.createLabel("Id del invernadero: ", 10, 100, 80, 20,Color.BLACK);
 		panelinvernadero.add(labelinvernadero);
 		JTextField textinvernadero = new JTextField(25);
 		panelinvernadero.add(textinvernadero);
 		
 		
 		JLabel labelnombreFruta = ComponentsBuilder.createLabel("Nombre de la fruta: ", 10, 100, 80, 20,Color.BLACK);
 		panelnombreFruta.add(labelnombreFruta);
 		JTextField textnombreFruta = new JTextField(25);
 		panelnombreFruta.add(textnombreFruta);
 		
 		
 		JLabel labelmaduracion = ComponentsBuilder.createLabel("Maduracion: ", 10, 100, 80, 20,Color.BLACK);
 		panelmaduracion.add(labelmaduracion);
 		JTextField textmaduracion = new JTextField(25);
 		panelmaduracion.add(textmaduracion);
 		
 		JLabel labelhoja = ComponentsBuilder.createLabel("Hoja de la planta: ", 10, 100, 80, 20,Color.BLACK);
 		panelhoja.add(labelhoja);
 		JTextField texthoja = new JTextField(25);
 		panelhoja.add(texthoja);
 		
 		texthoja.setEnabled(false);
 		
 		tipoPlanta.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					String selected = (String) tipoPlanta.getSelectedItem();
                    if (selected.equals("Frutal")) {
                    	texthoja.setText("");
                    	texthoja.setEnabled(false);
                    	textmaduracion.setEnabled(true);
                    	textnombreFruta.setEnabled(true);
                    } 
                    else {

                    	textmaduracion.setEnabled(false);
                    	textnombreFruta.setEnabled(false);
                    	texthoja.setEnabled(true);
                    }
					
					
				}
				
			}
        });
 		

 		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
 		
 		//PANEL DE LOS BOTONES
 		JPanel panelBotones = new JPanel();
 		mainPanel.add(panelBotones);
 		
 		//BOTON DE ACEPTAR
 		JButton botonAceptar = new JButton("Aceptar");
 		
 		panelBotones.add(botonAceptar);
 		botonAceptar.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				GUIAltaPlanta.this.setVisible(false);
 				Integer tipo = 1;
 				try {
 					
 					String nombre = textNombre.getText();
 					String nombreCientifico = textNombreCientifico.getText();
 					Integer idInvernadero = Integer.parseInt(textinvernadero.getText());
 			
	 					if(tipoPlanta.getSelectedItem() == "Frutal") {
	 						
	 						String fruta = textnombreFruta.getText();
	 						String mad = textmaduracion.getText();
	 						tipo = 0;
	 						
	 						if(fruta.isEmpty()||mad.isEmpty()||nombre.isEmpty()||nombreCientifico.isEmpty()) 
	 							GUIMSG.showMessage("Rellene los campos", "ALTA PLANTA", true);
	 						
	 						else{
	 							TPlantaFrutal frutal = new TPlantaFrutal(nombre, nombreCientifico,tipo, idInvernadero, fruta, mad);
	 						
	 							ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_PLANTA,frutal));
	 						}
	 						
	 					}else if (tipoPlanta.getSelectedItem() == "No Frutal") {
	 						
	 						String hoja = texthoja.getText();
	 						if(hoja.isEmpty()||nombre.isEmpty()||nombreCientifico.isEmpty()) GUIMSG.showMessage("Rellene los campos", "ALTA PLANTA", true);
	 						else{
	 							TPlantaNoFrutal NOfrutal = new TPlantaNoFrutal(nombre, nombreCientifico,tipo, idInvernadero, hoja);
	 						
	 							ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_PLANTA, NOfrutal));
	 						}
	 						
	 					}
 					
 				} catch (Exception e1) {
 					GUIMSG.showMessage(e1.getLocalizedMessage(), "ALTA PLANTA", true);
 				}
 			}
 		});
	}

	@Override
	public void actualizar(Context context) {
		
		
		switch(context.getEvento()) {
		case Evento.ALTA_PLANTA_OK:
			GUIMSG.showMessage("Planta dado de alta con ID: " + context.getDatos(), "ALTA PLANTA", false);
			break;
		case  Evento.ALTA_PLANTA_KO:
			GUIMSG.showMessage("No se pudo dar de alta a la planta", "ALTA PLANTA", true);
			break;
		default:
			GUIMSG.showMessage("Error inesperado", "ALTA PLANTA", true);
			break;
		}
		
		
		
	}
}