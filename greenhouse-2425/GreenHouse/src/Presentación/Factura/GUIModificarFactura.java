package Presentacion.Factura;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Negocio.Factura.TFactura;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class GUIModificarFactura extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	public GUIModificarFactura() throws HeadlessException {
		super("Modificar Factura");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 630;
        int alto = 430;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
	}
	
	public void initGUI() {
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ID
        JLabel msgIntroIDCabecera = ComponentsBuilder.createLabel("Introduzca el ID de la Factura que quiere modificar ",
                1, 10, 80, 20, Color.BLACK);
        msgIntroIDCabecera.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(msgIntroIDCabecera);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel panelID = new JPanel();
        mainPanel.add(panelID);

        JLabel labelID = ComponentsBuilder.createLabel("ID Factura: ", 10, 100, 80, 20, Color.BLACK);
        panelID.add(labelID);

        JTextField id = new JTextField();
        id.setPreferredSize(new Dimension(250, 30));
        id.setEditable(true);
        panelID.add(id);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelFechaCompra = new JPanel();
        mainPanel.add(panelFechaCompra);

        JLabel labelFechaCompra = ComponentsBuilder.createLabel("Fecha de Compra", 10, 100, 200, 20, Color.BLACK);
        panelFechaCompra.add(labelFechaCompra);

        JTextField fechaCompra = new JTextField();
        fechaCompra.setPreferredSize(new Dimension(250, 30));
        fechaCompra.setEditable(true);
        panelFechaCompra.add(fechaCompra);
        
        JLabel labelFechaCompraHelper = ComponentsBuilder.createLabel("Formato: dd/MM/yyyy", 10, 100, 200, 20, Color.BLACK);
        panelFechaCompra.add(labelFechaCompraHelper);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelBotones = new JPanel();
        mainPanel.add(panelBotones);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds(75, 50, 100, 100);
        botonAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    TFactura factura = new TFactura();
                    String fechaInput = fechaCompra.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE);
                    LocalDate date = LocalDate.parse(fechaInput, formatter);
	            	Date fecha= Date.valueOf(date);
                    factura.setid(Integer.parseInt(id.getText()));
                    factura.setPrecioTotal((float) 0.0);
                    factura.setFechaCompra(fecha);
                    ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_FACTURA, factura));
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(GUIModificarFactura.this, "Los datos no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panelBotones.add(botonAceptar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(200, 50, 100, 100);
        botonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIModificarFactura.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.FACTURA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
        this.setResizable(true);
	}

	@Override
	public void actualizar(Context context) {
		
		int resultado = (int) context.getDatos();
        if (context.getEvento() == Evento.MODIFICAR_FACTURA_OK) {
        	
            JOptionPane.showMessageDialog(this, "Factura modificar correctamente la factura con id " + resultado , "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.MODIFICAR_FACTURA_KO) {
        	
            switch (resultado) {
            case -1:
                JOptionPane.showMessageDialog(this, "Se ha producido un error interno", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al modificar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
		
	}
}