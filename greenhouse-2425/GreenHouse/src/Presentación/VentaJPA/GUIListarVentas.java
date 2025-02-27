package Presentacion.VentaJPA;

import javax.swing.JFrame;

import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Negocio.VentaJPA.TVenta;

public class GUIListarVentas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIListarVentas(Set<TVenta> datos) {
		super("Mostrar todas las ventas");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI(datos);
	}

	public void initGUI(Set<TVenta> datos) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// tabla
		String[] nombreColumnas = { "ID", "Precio Total", "Forma de Pago", "Empleado", "Fecha", "Activo" };
		String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

		int i = 0;
		for (TVenta venta : datos) {
			tablaDatos[i][0] = venta.getId().toString();
			tablaDatos[i][1] = venta.getPrecioTotal().toString();
			tablaDatos[i][2] = venta.getFormaPago().toString();
			tablaDatos[i][3] = venta.getIdEmpleado().toString();
			tablaDatos[i][4] = venta.getFecha().toString();
			tablaDatos[i][5] = venta.getActivo() ? "Si" : "No";
			i++;
		}

		JTable tabla = ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, tablaDatos);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(750, 250));
		mainPanel.add(scroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// botones
		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIListarVentas.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.VENTA_VISTA, null));
			}
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);
		this.setResizable(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.LISTAR_VENTAS_OK) {
			JOptionPane.showMessageDialog(this, "Ventana listada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.LISTAR_VENTAS_KO) {
			JOptionPane.showMessageDialog(this, "Error al tratar de listar las ventas", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}