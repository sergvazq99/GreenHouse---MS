package Presentacion.Factura;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Negocio.Factura.TFactura;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class GUIListarFactura extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;

	private JButton botonCancelar;

	public GUIListarFactura(Set<TFactura> datos) {
		super("Listar Todas las Facturas");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 900;
        int alto = 450;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI(datos);
	}

	public void initGUI(Set<TFactura> datos) {
		mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel panelID = new JPanel();
        mainPanel.add(panelID);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel panelBotones = new JPanel();
        mainPanel.add(panelBotones);

        botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(200, 50, 100, 100);
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIListarFactura.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.FACTURA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        String[] nombreColumnas = {"ID", "Precio Total", "Fecha de Compra", "Activo"};
        List<String[]> datosColumnas = new ArrayList<String[]>();
        

        for (TFactura factura : datos) {
        	String[] datosFila = new String[4];
        	datosFila[0] = factura.getid().toString();
            datosFila[1] = factura.getPrecioTotal().toString();
            datosFila[2] = factura.getFechaCompra().toString();
            String activo = "No";
            if(factura.getActivo())
            	activo = "Si";
            datosFila[3] = activo;
			datosColumnas.add(datosFila);
        }
        
        JTable tabla = ComponentsBuilder.createTable(datos.size(), 4, nombreColumnas, datosColumnas.toArray(new String[][] {}));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(50, 115, 800, 288);
        this.add(scroll);

        this.setVisible(true);
        this.setResizable(true);
	}

	@Override
	public void actualizar(Context context) {
	}
}