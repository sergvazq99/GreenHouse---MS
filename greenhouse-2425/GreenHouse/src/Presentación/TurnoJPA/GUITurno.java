package Presentacion.TurnoJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

@SuppressWarnings("serial")
public class GUITurno extends JFrame implements IGUI {

    private JButton bAlta;
    private JButton bBaja;
    private JButton bModificar;
    private JButton bMostrar;
    private JButton bListar;
    private JButton bObtenerNomina;
    private JButton bAtras;
    private JPanel mainPanel;

    public GUITurno() {
        super("Turno");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 800;
        int alto = 650;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        mainPanel = new JPanel();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
        this.setVisible(true);
    }

    private void initGUI() {

        JLabel label = ComponentsBuilder.createLabel("Gestión de Turnos", 250, 30, 300, 50, Color.BLACK);
        this.add(label);

        // Alta Turno
        bAlta = ComponentsBuilder.createButton("Alta Turno", 100, 100, 185, 100);
        bAlta.setVisible(true);
        this.add(bAlta);
        bAlta.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_TURNO_VISTA, null));
        });

        // Baja Turno
        bBaja = ComponentsBuilder.createButton("Baja Turno", 300, 100, 185, 100);
        bBaja.setVisible(true);
        this.add(bBaja);
        bBaja.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_TURNO_VISTA, null));
        });

        // Modificar Turno
        bModificar = ComponentsBuilder.createButton("Modificar Turno", 500, 100, 185, 100);
        bModificar.setVisible(true);
        this.add(bModificar);
        bModificar.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_TURNO_VISTA, null));
        });

        // Mostrar Turno
        bMostrar = ComponentsBuilder.createButton("Mostrar Turno", 100, 250, 185, 100);
        bMostrar.setVisible(true);
        this.add(bMostrar);
        bMostrar.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_TURNO_VISTA, null));
        });

        // Listar Turnos
        bListar = ComponentsBuilder.createButton("Listar Turnos", 300, 250, 185, 100);
        bListar.setVisible(true);
        this.add(bListar);
        bListar.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_TURNO));
        });

        // Obtener Nómina del Turno
        bObtenerNomina = ComponentsBuilder.createButton("Obtener Nómina \nde Turno", 100, 400, 300, 100);
        bObtenerNomina.setVisible(true);
        this.add(bObtenerNomina);
        bObtenerNomina.addActionListener(a -> {
            this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.OBTENER_NOMINA_DE_TURNO_VISTA));
        });

        // Botón Atrás
        bAtras = ComponentsBuilder.createBackButton();
        bAtras.setVisible(true);
        this.add(bAtras);
        bAtras.addActionListener(a -> {
            GUITurno.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
            dispose();
        });

        getContentPane().add(mainPanel);
    }

    @Override
    public void actualizar(Context context) {
        // Aquí se podría actualizar la vista si fuera necesario
    }
}

