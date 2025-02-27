/**
 * 
 */
package Presentacion.ProductoJPA;

import javax.swing.JFrame;

import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.GUIMSG;
import Presentacion.Controller.IGUI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoAlimentacion;
import Negocio.ProductoJPA.TProductoSouvenirs;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUIListarProducto extends JFrame implements IGUI {


	private JButton botonCancelar;

	private JPanel mainPanel;

	public GUIListarProducto(List<TProducto> list) {
		super("LISTAR PRODUCTOS");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 630;
		int alto = 330;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		int tmp = Evento.LISTAR_PRODUCTOS_OK ;
		if(list == null || list.isEmpty()) {tmp = Evento.LISTAR_PRODUCTOS_KO;}
		else
			this.iniGUI(list);
		
		Context context = new Context(tmp, list);
		this.actualizar(context);
		
	}


	private static final long serialVersionUID = 1L;
	public void iniGUI(List<TProducto> datos) {
		
		// Panel principal
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// Tabla
		String[] nombreColumnas = {"ID", "Nombre", "Stock", "Precio", "idMarca","Activo","Tipo Producto","Peso" ,"Precio por Kilo", "Tipo", "Descripcion"};
        String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

		int i = 0;
		for (TProducto t : datos) {
			tablaDatos[i][0] = t.getId().toString();
			tablaDatos[i][1] = t.getNombre();
			tablaDatos[i][2] = t.getStock().toString();
			tablaDatos[i][3] = t.getPrecio().toString();
			tablaDatos[i][4] = t.getIdMarca().toString();
			tablaDatos[i][5] = t.getActivo() ? "Si" : "No";
			
			
			String tip = "Alimentacion";
			
			if(t.getTipoProducto() == 1) tip = "Sourvenirs";
			
			
			
			tablaDatos[i][6] = tip;
			
			
			
			tablaDatos[i][7] = "";
			tablaDatos[i][8] = "";
			tablaDatos[i][9] = "";
			
			tablaDatos[i][10] = "";
			
			if(t instanceof TProductoSouvenirs){
				tablaDatos[i][10] = ((TProductoSouvenirs) t).getDescripcion();
			}
			else if(t instanceof TProductoAlimentacion){
				tablaDatos[i][7] = ""+((TProductoAlimentacion) t).getPeso();
				tablaDatos[i][8] = ""+((TProductoAlimentacion) t).getPrecioKilo();
				tablaDatos[i][9] = ((TProductoAlimentacion) t).getTipo();
			}

			i++;
		}
		
        JTable tabla =  ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, tablaDatos); 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(750, 250)); 
        mainPanel.add(scroll);
		
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        mainPanel.add(panelBotones);

        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIListarProducto.this.setVisible(false);
            	ApplicationController.getInstance().manageRequest(new Context(Evento.PRODUCTO_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setResizable(true);	
		
	}


	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.LISTAR_PRODUCTOS_KO:
			this.setVisible(false);
			GUIMSG.showMessage("No hay PRODUCTOS", "LISTAR PRODUCTOS", true);
			break;
		case  Evento.LISTAR_PRODUCTOS_OK:
			this.setVisible(true);
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PRODUCTOS", true);
			break;
		
	}
}
	
}