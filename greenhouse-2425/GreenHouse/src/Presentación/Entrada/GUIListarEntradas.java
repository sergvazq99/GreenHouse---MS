package Presentacion.Entrada;

import javax.swing.JFrame;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Negocio.Entrada.TEntrada;

import javax.swing.JOptionPane;

public class GUIListarEntradas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton botonCancelar;

	private JPanel mainPanel;

	public GUIListarEntradas(Set<TEntrada> listaEntradas) {
		super("Mostrar todas las entradas");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 630;
		int alto = 330;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI((Set<TEntrada>) listaEntradas);
	}

	
	private void initGUI(Set<TEntrada> listaEntradas) {
		
		// Panel principal
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// Tabla
		String[] nombreColumnas = { "ID","Fecha","Precio","Stock","Activo", "Id Invernadero" };
        String[][] tablaDatos = new String[listaEntradas.size()][nombreColumnas.length];

		int i = 0;
		for (TEntrada t : listaEntradas) {
			tablaDatos[i][0] = t.getId().toString();
			tablaDatos[i][1] = t.getFecha().toString();
			tablaDatos[i][2] = t.getPrecio().toString();
			tablaDatos[i][3] = t.getStock().toString();
			tablaDatos[i][4] = t.getActivo() ? "Si" : "No";
			tablaDatos[i][5] = t.getIdInvernadero().toString();

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
            	GUIListarEntradas.this.setVisible(false);
            	ApplicationController.getInstance().manageRequest(new Context(Evento.ENTRADA_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
        this.setResizable(true);	
		
	}


	@Override
	public void actualizar(Context context) {
		if(context.getEvento() == Evento.LISTAR_ENTRADA_OK) {
            JOptionPane.showMessageDialog(this, "Entradas listadas correctamente", "exito", JOptionPane.INFORMATION_MESSAGE);
		} else if(context.getEvento() == Evento.LISTAR_ENTRADA_KO) {
            JOptionPane.showMessageDialog(this, "Error al listar entradas", "Error", JOptionPane.INFORMATION_MESSAGE);

		}

	}
}