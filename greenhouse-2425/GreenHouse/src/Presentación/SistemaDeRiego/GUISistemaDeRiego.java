package Presentacion.SistemaDeRiego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.SistemaDeRiego.TSistemaDeRiego;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


@SuppressWarnings("serial")
public class GUISistemaDeRiego extends JFrame implements IGUI {

    private JButton buttonAltaSistemaRiego;
    private JButton buttonBajaSistemaRiego;
    private JButton buttonModificarSistemaRiego;
    private JButton buttonMostrarSistemaRiego;
    private JButton buttonListarSistemasRiego;
    private JButton buttonListarSistemasRiegoPorFabricante;
    private JButton buttonListarSistemasRiegoEnInvernadero;
    private JButton backButton;
    private JPanel panel;

    private TSistemaDeRiego tSistemaRiego;

    public GUISistemaDeRiego() {
        super("Sistema de Riego");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 1000;
        int alto = 525;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        panel = new JPanel();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
        this.setVisible(true);
    }

    public void initGUI() {
        tSistemaRiego = new TSistemaDeRiego();
        JLabel label = ComponentsBuilder.createLabel("Sistema de Riego", 250, 30, 500, 50, Color.BLACK);
        this.add(label);

        // ALTA SISTEMA DE RIEGO
        buttonAltaSistemaRiego = ComponentsBuilder.createButton("Alta", 100, 100, 185, 100);
        buttonAltaSistemaRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_SISTEMA_RIEGO_VISTA, tSistemaRiego));
            }
        });
        buttonAltaSistemaRiego.setVisible(true);
        this.add(buttonAltaSistemaRiego);

        // BAJA SISTEMA DE RIEGO
        buttonBajaSistemaRiego = ComponentsBuilder.createButton("Baja", 300, 100, 185, 100);
        buttonBajaSistemaRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_SISTEMA_RIEGO_VISTA, tSistemaRiego));
            }
        });
        buttonBajaSistemaRiego.setVisible(true);
        this.add(buttonBajaSistemaRiego);

        // MODIFICAR SISTEMA DE RIEGO
        buttonModificarSistemaRiego = ComponentsBuilder.createButton("Modificar", 500, 100, 185, 100);
        buttonModificarSistemaRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_SISTEMA_RIEGO_VISTA, tSistemaRiego));
            }
        });
        buttonModificarSistemaRiego.setVisible(true);
        this.add(buttonModificarSistemaRiego);

        // MOSTRAR SISTEMA DE RIEGO
        buttonMostrarSistemaRiego = ComponentsBuilder.createButton("Mostrar por ID", 700, 100, 185, 100);
        buttonMostrarSistemaRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_SISTEMA_RIEGO_POR_ID_VISTA, tSistemaRiego));
            }
        });
        buttonMostrarSistemaRiego.setVisible(true);
        this.add(buttonMostrarSistemaRiego);

        // LISTAR SISTEMAS DE RIEGO
        buttonListarSistemasRiego = ComponentsBuilder.createButton("Listar", 100, 250, 185, 100);
        buttonListarSistemasRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_SISTEMAS_RIEGO, tSistemaRiego));
            }
        });
        buttonListarSistemasRiego.setVisible(true);
        this.add(buttonListarSistemasRiego);

        // LISTAR SISTEMAS DE RIEGO POR FABRICANTE
        buttonListarSistemasRiegoPorFabricante = ComponentsBuilder.createButton("Listar por Fabricante", 500, 250, 185, 100);
        buttonListarSistemasRiegoPorFabricante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_SISTEMAS_RIEGO_POR_FABRICANTE_VISTA, tSistemaRiego));
            }
        });
        buttonListarSistemasRiegoPorFabricante.setVisible(true);
        this.add(buttonListarSistemasRiegoPorFabricante);
        
        // LISTAR SISTEMAS DE RIEGO EN INVERNADERO
        buttonListarSistemasRiegoEnInvernadero = ComponentsBuilder.createButton("Listar en invernadero", 300, 250, 185, 100);
        buttonListarSistemasRiegoEnInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_SISTEMAS_RIEGO_INVERNADERO_VISTA, tSistemaRiego));
            }
        });
        buttonListarSistemasRiegoEnInvernadero.setVisible(true);
        this.add(buttonListarSistemasRiegoEnInvernadero);

        // BOTON DE VOLVER
        backButton = ComponentsBuilder.createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISistemaDeRiego.this.setVisible(false);
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
