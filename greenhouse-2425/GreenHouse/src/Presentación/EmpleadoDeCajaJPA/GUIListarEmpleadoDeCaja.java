/**
 * 
 */
package Presentacion.EmpleadoDeCajaJPA;

import javax.swing.JFrame;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoParcial;;


public class GUIListarEmpleadoDeCaja extends JFrame implements IGUI {
	
	private JDialog jDialog;
	private JLabel jLabel;
	private JPanel jPanel;
	private JButton jButton;
	private JTextField jTextField;
	
	
	public GUIListarEmpleadoDeCaja(Set<TEmpleadoDeCaja> listaEmpleados) {
		super("Mostrar todos los Empleados de Caja");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1500;
		int alto = 600;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI(listaEmpleados);
	}
	
	public void initGUI(Set<TEmpleadoDeCaja> listaEmpleados) {
		System.out.println(listaEmpleados);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Tabla
		String[] nombreColumnas = { 
		        "ID", "Nombre", "Apellido", "DNI", "Teléfono", "Sueldo", 
		        "ID Turno", "Tipo", "Activo", "Horas", "Precio Hora", "Sueldo Base", "Complementos" 
		    };
		    String[][] tablaDatos = new String[listaEmpleados.size()][nombreColumnas.length];

		    int i = 0;
		    for (TEmpleadoDeCaja empleado : listaEmpleados) {
		        tablaDatos[i][0] = empleado.getID().toString();
		        tablaDatos[i][1] = empleado.getNombre();
		        tablaDatos[i][2] = empleado.getApellido();
		        tablaDatos[i][3] = empleado.getDNI();
		        tablaDatos[i][4] = empleado.getTelefono().toString();
		        tablaDatos[i][5] = empleado.getSueldo().toString();
		        tablaDatos[i][6] = empleado.getId_Turno().toString();
		        tablaDatos[i][7] = (empleado instanceof TEmpleadoParcial) ? "Parcial" : "Completo";
		        tablaDatos[i][8] = empleado.getActivo() ? "Sí" : "No";

		        if (empleado instanceof TEmpleadoParcial) {
		            TEmpleadoParcial parcial = (TEmpleadoParcial) empleado;
		            tablaDatos[i][9] = String.valueOf(parcial.getHoras());
		            tablaDatos[i][10] = String.valueOf(parcial.getPrecio_h());
		            tablaDatos[i][11] = "N/A";
		            tablaDatos[i][12] = "N/A";
		        } else if (empleado instanceof TEmpleadoCompleto) {
		            TEmpleadoCompleto completo = (TEmpleadoCompleto) empleado;
		            tablaDatos[i][9] = "N/A";
		            tablaDatos[i][10] = "N/A";
		            tablaDatos[i][11] = String.valueOf(completo.getSueldo_Base());
		            tablaDatos[i][12] = String.valueOf(completo.getComplementos());
		        }

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

				JButton botonCancelar = new JButton("Cancelar");
				botonCancelar.addActionListener(a -> {
					GUIListarEmpleadoDeCaja.this.setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
				});
				panelBotones.add(botonCancelar);
		
				this.setVisible(true);
				this.setResizable(true);
	}
	
	
	
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_OK) {
			JOptionPane.showMessageDialog(this, "Empleados listados correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.LISTAR_EMPLEADOS_DE_CAJA_KO) {
			JOptionPane.showMessageDialog(this, "Error al tratar de listar los Empleados", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}