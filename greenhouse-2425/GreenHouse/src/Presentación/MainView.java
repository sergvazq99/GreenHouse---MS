package Presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class MainView extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;
    // Definición de los botones
    private JButton buttonEntrada;
    private JButton buttonFabricante;
    private JButton buttonInvernadero;
    private JButton buttonFactura;
    private JButton buttonPlanta;
    private JButton buttonSistRiego;
    private JButton buttonEmpleadoCaja;
    private JButton buttonTurno;
    private JButton buttonMarca;
    private JButton buttonProducto;
    private JButton buttonProveedor;
    private JButton buttonVenta;
    private JLabel label;

    public MainView() {
        super("BGanos - Gestión de Módulos");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 1000;
        int frameHeight = 850;
        int posX = (screenSize.width - frameWidth) / 2;
        int posY = (screenSize.height - frameHeight) / 2;
        this.setBounds(posX, posY, frameWidth, frameHeight);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        this.getContentPane().setBackground(new Color(250, 250, 250)); // Fondo más claro
        this.setVisible(true);
    }

    private void initializeComponents() {
        label = ComponentsBuilder.createLabel("Selecciona un módulo para gestionar", 50, 40, 900, 50, Color.DARK_GRAY);
        this.add(label);

        // Botón de Entrada
        buttonEntrada = ComponentsBuilder.createButton("Entrada", 100, 120, 185, 90);
        buttonEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));
            }
        });
        this.add(buttonEntrada);

        // Botón de Fabricante
        buttonFabricante = ComponentsBuilder.createButton("Fabricante", 407, 120, 185, 90);
        buttonFabricante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.FABRICANTE_VISTA, null));
            }
        });
        this.add(buttonFabricante);

        // Botón de Invernadero
        buttonInvernadero = ComponentsBuilder.createButton("Invernadero", 715, 120, 185, 90);
        buttonInvernadero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.INVERNADERO_VISTA, null));
            }
        });
        this.add(buttonInvernadero);

        // Botón de Factura
        buttonFactura = ComponentsBuilder.createButton("Factura", 100, 290, 185, 90);
        buttonFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.FACTURA_VISTA, null));
            }
        });
        this.add(buttonFactura);

        // Botón de Planta
        buttonPlanta = ComponentsBuilder.createButton("Planta", 407, 290, 185, 90);
        buttonPlanta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.PLANTA_VISTA, null));
            }
        });
        this.add(buttonPlanta);

        // Botón de Sistema de Riego
        buttonSistRiego = ComponentsBuilder.createButton("Sistema de Riego", 715, 290, 185, 90);
        buttonSistRiego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.SISTEMA_RIEGO_VISTA, null));
            }
        });
        this.add(buttonSistRiego);

        // Nueva fila de botones para las entidades JPA
        // Botón de Empleado de Caja JPA
        buttonEmpleadoCaja = ComponentsBuilder.createButton("Empleado de Caja JPA", 100, 460, 185, 90);
        buttonEmpleadoCaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
            }
        });
        this.add(buttonEmpleadoCaja);

        // Botón de Turno JPA
        buttonTurno = ComponentsBuilder.createButton("Turno JPA", 407, 460, 185, 90);
        buttonTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
            }
        });
        this.add(buttonTurno);

        // Botón de Marca JPA
        buttonMarca = ComponentsBuilder.createButton("Marca JPA", 715, 460, 185, 90);
        buttonMarca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
            }
        });
        this.add(buttonMarca);

        // Segunda nueva fila de botones para las entidades JPA
        // Botón de Producto JPA
        buttonProducto = ComponentsBuilder.createButton("Producto  JPA", 100, 630, 185, 90);
        buttonProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
            }
        });
        this.add(buttonProducto);

        // Botón de Proveedor JPA
        buttonProveedor = ComponentsBuilder.createButton("Proveedor  JPA", 407, 630, 185, 90);
        buttonProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.PROVEEDOR_VISTA, null));
            }
        });
        this.add(buttonProveedor);

        // Botón de Venta JPA
        buttonVenta = ComponentsBuilder.createButton("Venta  JPA", 715, 630, 185, 90);
        buttonVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));
            }
        });
        this.add(buttonVenta);
    }

    @Override
    public void actualizar(Context res) {
        // Implementar actualización si es necesario
    }
}
