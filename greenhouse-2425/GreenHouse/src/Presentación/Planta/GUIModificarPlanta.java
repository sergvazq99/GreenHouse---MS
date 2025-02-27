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
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

import javax.swing.JPanel;



public class GUIModificarPlanta extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	TPlanta planta;
	
	public GUIModificarPlanta(TPlanta planta) {
		super("Modificar Planta");
		this.planta = planta;
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
	    
	    JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("MODIFICAR PLANTA", 1, 10, 80, 20, Color.BLACK);
	    msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
	    mainPanel.add(msgIntroIDCabecera);

	    mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    
	    
	    
	    //PANEL DE TIPO DE LA PLANTA
	    JPanel panelID = new JPanel();
	    JPanel paneltipo = new JPanel();
	    JPanel panelnombre = new JPanel();
	    JPanel panelnombreCientifico = new JPanel();
	    JPanel panelinvernadero = new JPanel();
	    
	    JPanel panelnombreFruta = new JPanel();
	    JPanel panelmaduracion = new JPanel();
	    JPanel panelhoja = new JPanel();
	    JPanel panelActivo = new JPanel();
	    

	    mainPanel.add(panelID);
 		mainPanel.add(paneltipo);
 		mainPanel.add(panelnombre);
 		mainPanel.add(panelnombreCientifico);
 		mainPanel.add(panelinvernadero);
 		
	    
	    mainPanel.add(panelnombreFruta);
 		mainPanel.add(panelmaduracion);
 		mainPanel.add(panelhoja);
 		 mainPanel.add(panelActivo);
 		
 		JLabel labelID = ComponentsBuilder.createLabel("ID: ", 10, 100, 80, 20,Color.BLACK);
 		panelID.add(labelID);
 		JTextField textID = new JTextField(25);
 		panelID.add(textID);
 		
 		JLabel labeltipo = ComponentsBuilder.createLabel("Tipo: ", 10, 100, 80, 20,Color.BLACK);
 		paneltipo.add(labeltipo);
 		JTextField texttipo = new JTextField(25);
 		paneltipo.add(texttipo);
 		
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
 		
 		
 		
 		JLabel labelTPlanta = ComponentsBuilder.createLabel("Estado: ", 10, 100, 80, 20,Color.BLACK);
 		panelActivo.add(labelTPlanta);

 		JComboBox<String> tipoPlanta = new JComboBox<String>();
 		tipoPlanta.addItem("Activo");
 		tipoPlanta.addItem("No Activo");
 		tipoPlanta.setPreferredSize(new Dimension(250, 25));
 		panelActivo.add(tipoPlanta);
 		
 		JLabel labelnombreFruta = ComponentsBuilder.createLabel("Nombre de la fruta: ", 10, 100, 80, 20,Color.BLACK);
 		panelnombreFruta.add(labelnombreFruta);
 		JTextField textnombreFruta = new JTextField(25);
 		panelnombreFruta.add(textnombreFruta);
 		
 		
 		JLabel labelmaduracion = ComponentsBuilder.createLabel("Maduracion: ", 10, 100, 80, 20,Color.BLACK);
 		panelmaduracion.add(labelmaduracion);
 		JTextField textmaduracion = new JTextField(25);
 		panelmaduracion.add(textmaduracion);
 		
 		JLabel labelhoja = ComponentsBuilder.createLabel("Hoja de la planta ", 10, 100, 80, 20,Color.BLACK);
 		panelhoja.add(labelhoja);
 		JTextField texthoja = new JTextField(25);
 		panelhoja.add(texthoja);
 		
 		
		
		//PANEL DE LOS BOTONES
 		JPanel panelBotones = new JPanel();
 		mainPanel.add(panelBotones);
 		
 		//BOTON DE ACEPTAR
 		JButton botonAceptar = new JButton("Modificar");
 		JButton botonBuscar = new JButton("Buscar");
 		
 		
 		
 		panelBotones.add(botonAceptar);
 		panelBotones.add(botonBuscar);
 		botonBuscar.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				
 			try{
 				int id = Integer.parseInt(textID.getText());
 				TPlanta tmp = new TPlanta();
 				tmp.set_id(id);
 				ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PLANTA,tmp));
				setVisible(false);
 			}
 			catch(Exception ex){
 				GUIMSG.showMessage("Formato incorrecto", "MODIFICAR PLANTA", true);
 			
 			}
 		}});
 		
 		
 		botonAceptar.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				
 				
 				try {
 					planta.set_nombre(textNombre.getText());
 				
 					planta.set_nombre_cientifico(textNombreCientifico.getText());
 		
 					int idInvernadero = Integer.parseInt(textinvernadero.getText());
 					planta.set_id_invernadero(idInvernadero);
 
 					if(tipoPlanta.getSelectedIndex() == 0)
 						planta.setActivo(true);
 					else
 						planta.setActivo(false);
	 					
	 						
	 					
	 					if(planta instanceof TPlantaFrutal){
	 						((TPlantaFrutal) planta).set_maduracion(textmaduracion.getText());
	 						((TPlantaFrutal) planta).set_nombre_fruta(textnombreFruta.getText());
	 						
	 						if(((TPlantaFrutal) planta).get_maduracion().isEmpty()||((TPlantaFrutal) planta).get_nombre_fruta().isEmpty()
	 							|| planta.get_nombre().isEmpty() || planta.get_nombre_cientifico().isEmpty()){
	 	 						GUIMSG.showMessage("Rellene los campos", "MODIFICAR PLANTA", true);
	 	 					}
	 						else{
	 							
	 							ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PLANTA,planta));
							setVisible(false);
	 					}
	 		
	 						
	 					}
	 					else{
	 		
	 						((TPlantaNoFrutal) planta).set_tipo_hoja(texthoja.getText());
	 						
	 						if(((TPlantaNoFrutal) planta).get_tipo_hoja().isEmpty()
	 								||planta.get_nombre().isEmpty() || planta.get_nombre_cientifico().isEmpty()){
	 	 						GUIMSG.showMessage("Rellene los campos", "MODIFICAR PLANTA", true);
	 	 					}
	 						else{
	 							
	 							ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PLANTA,planta));
							setVisible(false);
	 					}
	

 					}
				
 				} catch (Exception e1) {
 					GUIMSG.showMessage("Formato incorrecto", "MOSTRAR PLANTA", true);
 				}
 			}
 		});
 		
 	
 		
	    
		if(planta == null){
			tipoPlanta.setEnabled(false);
			texttipo.setEnabled(false);
			textNombre.setEnabled(false);
			textNombreCientifico.setEnabled(false);
			textinvernadero.setEnabled(false);
			textnombreFruta.setEnabled(false);
			textmaduracion.setEnabled(false);
			texthoja.setEnabled(false);
			botonAceptar.setEnabled(false);
			
			
		}
		else{
			textID.setText(""+planta.get_id());
			textNombre.setText(planta.get_nombre());
			textNombreCientifico.setText(planta.get_nombre_cientifico());
			textinvernadero.setText(""+planta.get_id_invernadero());
			
			tipoPlanta.setEnabled(true);
			textID.setEnabled(false);
			texttipo.setEnabled(false);
			textNombre.setEnabled(true);
			textNombreCientifico.setEnabled(true);
			textinvernadero.setEnabled(true);
			botonAceptar.setEnabled(true);
			botonBuscar.setEnabled(false);
			if(!planta.getActivo())
				tipoPlanta.setSelectedIndex(1);
	
		
			
			if(planta instanceof TPlantaFrutal){
				
				texttipo.setText("Frutal");
				textnombreFruta.setText(((TPlantaFrutal)planta).get_nombre_fruta());
				textmaduracion.setText(((TPlantaFrutal)planta).get_maduracion());
				
				textnombreFruta.setEnabled(true);
				textmaduracion.setEnabled(true);
				texthoja.setEnabled(false);
			}
			else{
				texthoja.setEnabled(true);
				texttipo.setText("No Frutal");
				texthoja.setText(((TPlantaNoFrutal)planta).get_tipo_hoja());
				textnombreFruta.setEnabled(false);
				textmaduracion.setEnabled(false);
			}
			
		}
 		
	}



	@Override
	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.MODIFICAR_PLANTA_OK:
			GUIMSG.showMessage("Se realizo la modificacion correctamente", "MODIFICAR PLANTA", false);
			break;
		case  Evento.MODIFICAR_PLANTA_KO:
			if((int) context.getDatos() == -2){
				GUIMSG.showMessage("Invernadero incorrecto", "MODIFICAR PLANTA", true);
			}
			else if((int) context.getDatos() == -3)
				GUIMSG.showMessage("No se encontro la planta", "MODIFICAR PLANTA", true);
			else
			GUIMSG.showMessage("No se pudo realizar la modificacion", "MODIFICAR PLANTA", true);
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "MODIFICAR PLANTA", true);
			break;
		
	}
	}
}