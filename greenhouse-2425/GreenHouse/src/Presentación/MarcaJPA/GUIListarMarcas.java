package Presentacion.MarcaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.MarcaJPA.TMarca;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class GUIListarMarcas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;

	private JButton botonCancelar;

	private JTextField jTextField;

	private JLabel jLabel;

	private JDialog jDialog;

	public GUIListarMarcas(Set<TMarca> listaMarcas) {
		super("Mostrar todas las marcas");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 630;
		int alto = 330;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI((Set<TMarca>) listaMarcas);
	}

	private void initGUI(Set<TMarca> listaMarcas) {

		// Panel principal
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		// Tabla
		String[] nombreColumnas = { "ID", "Nombre", "Pais de Origen", "Activo" };
		String[][] tablaDatos = new String[listaMarcas.size()][nombreColumnas.length];

		int i = 0;
		for (TMarca m : listaMarcas) {
			tablaDatos[i][0] = m.getId().toString();
			tablaDatos[i][1] = m.getNombre().toString();
			tablaDatos[i][2] = m.getPais().toString();
			tablaDatos[i][3] = m.getActivo() ? "Si" : "No";

			i++;
		}

		JTable tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, tablaDatos);
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
          	GUIListarMarcas.this.setVisible(false);
          	ApplicationController.getInstance().manageRequest(new Context(Evento.MARCA_VISTA, null));
          }
      });
      panelBotones.add(botonCancelar);

      this.setVisible(true);
      this.setResizable(true);	
		

	}

	public void actualizar(Context context) {
		if(context.getEvento() == Evento.LISTAR_MARCAS_OK) {
            JOptionPane.showMessageDialog(this, "Marcas listadas correctamente", "exito", JOptionPane.INFORMATION_MESSAGE);
		} else if(context.getEvento() == Evento.LISTAR_MARCAS_KO) {
            JOptionPane.showMessageDialog(this, "Error al listar Marcas", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}


}