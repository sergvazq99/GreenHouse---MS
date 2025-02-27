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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Negocio.Factura.TFactura;
import Negocio.Factura.TFacturaConEntradas;
import Negocio.Factura.TLineaFactura;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;


public class GUIMostrarFacturaID extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	private JTable tablaDatos;
	
	private JLabel mensajeFactura;
	
	private JScrollPane scroll;
	
	public GUIMostrarFacturaID() {
		super("Mostrar Factura");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 700;
        int alto = 700;
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

        JLabel msgIntroIDCabecera = ComponentsBuilder
                .createLabel("Introduzca el ID de la Factura a mostrar ", 1, 10, 80, 20, Color.BLACK);
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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JPanel mensaje = new JPanel();
		mainPanel.add(mensaje);

		mensajeFactura = ComponentsBuilder.createLabel("", 10, 100, 80, 20, Color.BLACK);
		mensaje.add(mensajeFactura);
        
        String[] nombreColumnas = { "Id Factura", "ID Entrada", "Cantidad", "Precio" };
		List<String[]> datosColumnas = new ArrayList<String[]>();
		
		tablaDatos = ComponentsBuilder.createTable(0, 4, nombreColumnas, datosColumnas.toArray(new String[][] {}));
		scroll = new JScrollPane(tablaDatos);
		scroll.setBounds(50, 115, 900, 288);
		mainPanel.add(scroll);

        JPanel panelBotones = new JPanel();
        mainPanel.add(panelBotones);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds(75, 50, 100, 100);
        botonAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	Integer id_Factura = Integer.parseInt(id.getText());
                    ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_FACTURA_POR_ID, 
                    		!id.getText().isEmpty()? id_Factura: 0));

                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(GUIMostrarFacturaID.this, "Los datos no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panelBotones.add(botonAceptar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(200, 50, 100, 100);
        botonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIMostrarFacturaID.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.FACTURA_VISTA, null));

            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
        this.setResizable(true);
	}

	@Override
	public void actualizar(Context context) {
		TFacturaConEntradas resultado = (TFacturaConEntradas) context.getDatos();
		int msg = -3;
		if(resultado != null && resultado.gettFactura() != null && resultado.gettFactura().getid() != null)
			msg = resultado.gettFactura().getid();
        if (context.getEvento() == Evento.MOSTRAR_FACTURA_POR_ID_OK) {
        	
        	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            
            TFactura factura = resultado.gettFactura();
            String activo = factura.getActivo() ? "Si" : "No";
            String fecha = df.format(factura.getFechaCompra());
            String datosFactura = "Precio total: " + factura.getPrecioTotal() + "; Fecha de compra: " + fecha + "; Activo: " + activo;
            
            mensajeFactura.setText(datosFactura); // Actualiza el mensaje sin crear un nuevo JLabel
            
            // Actualiza los datos de la tabla
            DefaultTableModel model = (DefaultTableModel) tablaDatos.getModel();
            model.setRowCount(0); // Limpia las filas anteriores
            
            for (TLineaFactura t : resultado.gettLineaFactura()) {
                String[] datos = new String[4];
                datos[0] = t.getidFactura().toString();
                datos[1] = t.getidEntrada().toString();
                datos[2] = t.getCantidad().toString();
                datos[3] = "" + t.getPrecio();
                model.addRow(datos); // Agrega las nuevas filas
            }
            
            scroll.revalidate();
            scroll.repaint();

        } else if (context.getEvento() == Evento.MOSTRAR_FACTURA_POR_ID_KO) {
        	
            switch (msg) {
            case -1:
                JOptionPane.showMessageDialog(this, "Se ha producido un error interno", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al mostrar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
	}
}