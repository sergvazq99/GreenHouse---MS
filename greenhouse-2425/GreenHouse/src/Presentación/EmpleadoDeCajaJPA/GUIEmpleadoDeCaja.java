package Presentacion.EmpleadoDeCajaJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIEmpleadoDeCaja extends JFrame implements IGUI {

	private JPanel mainPanel;
	TEmpleadoDeCaja tEmpleadoDeCaja;
	private JButton bAlta;
	private JButton bBaja;
	private JButton bModificar;
	private JButton bMostrar;
	private JButton bListar;
	private JButton bListarPorNombre;
	private JButton bListarPorId;
	private JButton bListarPorTurno;
	private JButton bAtras;
	public GUIEmpleadoDeCaja() {
		super("Empleado de Caja");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
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

		tEmpleadoDeCaja = new TEmpleadoDeCaja();
		JLabel label = ComponentsBuilder.createLabel("Empleado de Caja", 250, 30, 500, 50, Color.BLACK);
		this.add(label);

		// Alta
		bAlta = ComponentsBuilder.createButton("Alta Empleado De Caja", 100, 100, 185, 100);
		bAlta.setVisible(true);
		this.add(bAlta);
		bAlta.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.ALTA_EMPLEADO_DE_CAJA_VISTA, tEmpleadoDeCaja));
		});

		// Baja
		bBaja = ComponentsBuilder.createButton("Baja Empleado de caja", 300, 100, 185, 100);
		bBaja.setVisible(true);
		this.add(bBaja);
		bBaja.addActionListener(a -> {

			this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.BAJA_EMPLEADO_DE_CAJA_VISTA, tEmpleadoDeCaja));

		});

		// Modificar
		//bModificar = ComponentsBuilder.createButton("Modificar Empleado de caja", 500, 100, 185, 100);
		bModificar = ComponentsBuilder.createButton("Modificar Empleado de caja", 100, 250, 250, 100);
		bModificar.setVisible(true);
		this.add(bModificar);
		bModificar.addActionListener(a -> {

			GUIEmpleadoDeCaja.this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.MODIFICAR_EMPLEADO_DE_CAJA_VISTA, tEmpleadoDeCaja));

		});

		// Mostrar
		bMostrar = ComponentsBuilder.createButton("Mostrar Empleado de Caja", 700, 100, 200, 100);
		bMostrar.setVisible(true);
		this.add(bMostrar);
		bMostrar.addActionListener(a -> {

			GUIEmpleadoDeCaja.this.setVisible(false);
			ApplicationController.getInstance()
					.manageRequest(new Context(Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID_VISTA, tEmpleadoDeCaja));

		});

		// Listar
		//bListar = ComponentsBuilder.createButton("Listar", 100, 250, 185, 100);
		bListar = ComponentsBuilder.createButton("Listar", 500, 100, 185, 100);
		bListar.setVisible(true);
		this.add(bListar);
		bListar.addActionListener(a -> {

			GUIEmpleadoDeCaja.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA));
																											
		});

		// ListarPorNombre
		//bListarPorNombre = ComponentsBuilder.createButton("Listar Por Nombre", 300, 250, 185, 100);
		bListarPorNombre = ComponentsBuilder.createButton("Listar Por Nombre", 435, 250, 185, 100);
		bListarPorNombre.setVisible(true);
		this.add(bListarPorNombre);
		bListarPorNombre.addActionListener(a -> {
			GUIEmpleadoDeCaja.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_VISTA));// vista
																														// o
																														// directamente
																														// evento?
		});
		
		//ListarPorTurno
		bListarPorTurno = ComponentsBuilder.createButton("Listar Por Turno", 720, 250, 185, 100);
		bListarPorTurno.setVisible(true);
		this.add(bListarPorTurno);
		bListarPorTurno.addActionListener(a -> {

			GUIEmpleadoDeCaja.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_TURNO_VISTA));// vista
																														// o
																														// directamente
																														// evento?
		});
		
		//ListarPorId
		 /*
				bListarPorId = ComponentsBuilder.createButton("Listar Por Id", 700, 250, 185, 100);
				bListarPorId.setVisible(true);
				this.add(bListarPorId);
				bListarPorId.addActionListener(a -> {
					GUIEmpleadoDeCaja.this.setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID_VISTA));
																																
				});*/

		// Atras
				bAtras = ComponentsBuilder.createBackButton();
				bAtras.setVisible(true);
				this.add(bAtras);
				bAtras.addActionListener(a -> {

					GUIEmpleadoDeCaja.this.setVisible(false);
					ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
					dispose();
				});

				getContentPane().add(mainPanel);

			}

	@Override
	public void actualizar(Context context) {
	}
}
