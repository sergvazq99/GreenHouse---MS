package Presentacion.Invernadero;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.Invernadero.TInvernadero;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

@SuppressWarnings("serial")
public class GUIInvernadero extends JFrame implements IGUI {

	private TInvernadero invernadero;
	private JButton buttonAltaInvernadero;
    private JButton buttonBajaInvernadero;
    private JButton buttonModificarInvernadero;
    
    private JButton buttonMostrarInvernaderoPorID;
    private JButton buttonListarInvernadero;
    private JButton buttonListarInvernaderoPorSistemasRiego;
    private JButton buttonVincularSistemasRiegoAInvernadero;
    private JButton buttonDesincularSistemasRiegoAInvernadero;

    private JButton backButton;
    private JPanel panel;

	public GUIInvernadero() throws HeadlessException {
		 super("Invernadero");
	        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	        int ancho = 1000;
	        int alto = 650;
	        int x = (pantalla.width - ancho) / 2;
	        int y = (pantalla.height - alto) / 2;
	        this.setBounds(x, y, ancho, alto);
	        this.setLayout(null);
	        panel = new JPanel();
	        this.setResizable(false);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        iniGUI();
	        this.setVisible(true);
	}

	public void iniGUI() {

		invernadero = new TInvernadero();
        JLabel label = ComponentsBuilder.createLabel("Invernaderos", 250, 30, 500, 50, Color.BLACK);
        this.add(label);

        // ALTA_INVERNADERO_VISTA
        buttonAltaInvernadero = ComponentsBuilder.createButton("Alta", 100, 100, 185, 100);
        buttonAltaInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonAltaInvernadero.setVisible(true);
        this.add(buttonAltaInvernadero);

        // BAJA_INVERNADERO_VISTA
        buttonBajaInvernadero = ComponentsBuilder.createButton("Baja", 300, 100, 185, 100);
        buttonBajaInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonBajaInvernadero.setVisible(true);
        this.add(buttonBajaInvernadero);

        // MODIFICAR_INVERNADERO_VISTA
        buttonModificarInvernadero = ComponentsBuilder.createButton("Modificar", 500, 100, 185, 100);
        buttonModificarInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonModificarInvernadero.setVisible(true);
        this.add(buttonModificarInvernadero);

        // MOSTRAR_INVERNADERO_POR_ID_VISTA
        buttonMostrarInvernaderoPorID = ComponentsBuilder.createButton("Mostrar por ID", 700, 100, 185, 100);
        buttonMostrarInvernaderoPorID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_INVERNADERO_POR_ID_VISTA, invernadero));
            }
        });
        buttonMostrarInvernaderoPorID.setVisible(true);
        this.add(buttonMostrarInvernaderoPorID);

        // LISTAR_INVERNADEROS
        buttonListarInvernadero = ComponentsBuilder.createButton("Listar", 100, 250, 185, 100);
        buttonListarInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_INVERNADEROS, invernadero));
            }
        });
        buttonListarInvernadero.setVisible(true);
        this.add(buttonListarInvernadero);

        // LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_VISTA
        buttonListarInvernaderoPorSistemasRiego = ComponentsBuilder.createButton("Listar por SR", 300, 250, 185, 100);
        buttonListarInvernaderoPorSistemasRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_VISTA, invernadero));
            }
        });
        buttonListarInvernaderoPorSistemasRiego.setVisible(true);
        this.add(buttonListarInvernaderoPorSistemasRiego);
        
        // VINCULAR_SISTEMA_RIEGO_INVERNADERO_VISTA
        buttonVincularSistemasRiegoAInvernadero = ComponentsBuilder.createButton("Vincular a un SR", 500, 250, 185, 100);
        buttonVincularSistemasRiegoAInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.VINCULAR_SISTEMA_RIEGO_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonVincularSistemasRiegoAInvernadero.setVisible(true);
        this.add(buttonVincularSistemasRiegoAInvernadero);
        
     // DESVINCULAR_SISTEMA_RIEGO_INVERNADERO_VISTA
        buttonDesincularSistemasRiegoAInvernadero = ComponentsBuilder.createButton("Desvincular de un SR", 700, 250, 185, 100);
        buttonDesincularSistemasRiegoAInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.DESVINCULAR_SISTEMA_RIEGO_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonDesincularSistemasRiegoAInvernadero.setVisible(true);
        this.add(buttonDesincularSistemasRiegoAInvernadero);
        
     // Calcular Las Tres Fechas Mas Vendidas De Un Invernadero
        buttonDesincularSistemasRiegoAInvernadero = ComponentsBuilder.createButton("3 Fechas Más Vendidas", 400, 400, 185, 100);
        buttonDesincularSistemasRiegoAInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO_VISTA, invernadero));
            }
        });
        buttonDesincularSistemasRiegoAInvernadero.setVisible(true);
        this.add(buttonDesincularSistemasRiegoAInvernadero);

        // BOT�N DE VOLVER
        backButton = ComponentsBuilder.createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIInvernadero.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
                dispose();
            }
        });
        backButton.setVisible(true);
        this.add(backButton);

        getContentPane().add(panel);
	}

	public void actualizar(Context context) {
	}
}