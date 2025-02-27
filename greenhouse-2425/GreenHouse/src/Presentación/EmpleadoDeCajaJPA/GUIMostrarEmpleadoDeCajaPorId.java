package Presentacion.EmpleadoDeCajaJPA;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoParcial;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

@SuppressWarnings("serial")
public class GUIMostrarEmpleadoDeCajaPorId extends JFrame implements IGUI {

	private JTextField textId;

	public GUIMostrarEmpleadoDeCajaPorId() {
		super("Mostrar Empleado de Caja");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 600;
		int alto = 400;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}

	public void initGUI() {
		// Panel principal con GridBagLayout para mayor control sobre la
		// alineación y el centrado
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre los
													// componentes
		this.setContentPane(mainPanel);

		// Titulo
		gbc.gridwidth = 2; // Toma dos columnas para el título
		JLabel msgIntro = new JLabel("Introduzca el ID del empleado de caja a mostrar", JLabel.CENTER);
		mainPanel.add(msgIntro, gbc);

		// Resetear para los campos
		gbc.gridwidth = 1;
		gbc.gridy = 1;

		// Campo para el ID del empleado de caja
		JLabel labelId = new JLabel("ID:");
		gbc.gridx = 0; // Columna 0
		mainPanel.add(labelId, gbc);
		textId = new JTextField(20);
		gbc.gridx = 1; // Columna 1
		mainPanel.add(textId, gbc);

		// Panel de botones
		JPanel panelBotones = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2; // Los botones ocuparán dos columnas
		gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones
		mainPanel.add(panelBotones, gbc);

		// Boton de aceptar
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(a -> {
			try {
				// Crear un contexto con el evento de mostrar y el ID del
				// empleado
				ApplicationController.getInstance().manageRequest(
						new Context(Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID, Integer.parseInt(textId.getText())));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(GUIMostrarEmpleadoDeCajaPorId.this, "Error en el formato del ID", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		panelBotones.add(botonAceptar);

		// Botón de cancelar
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(a -> {
			GUIMostrarEmpleadoDeCajaPorId.this.setVisible(false);
			ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
		});
		panelBotones.add(botonCancelar);

		this.setVisible(true);

	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID_OK) {
			TEmpleadoDeCaja empleado = (TEmpleadoDeCaja) context.getDatos();

			String texto = "ID: " + empleado.getID() + "\n" + "Nombre: " + empleado.getNombre() + "\n" + "Apellido: "
					+ empleado.getApellido() + "\n" + "DNI: " + empleado.getDNI() + "\n" + "Teléfono: "
					+ empleado.getTelefono() + "\n" + "Sueldo: " + empleado.getSueldo() + "\n" + "ID Turno: "
					+ empleado.getId_Turno() + "\n" +

					(empleado instanceof TEmpleadoCompleto
							? "Sueldo Base: " + ((TEmpleadoCompleto) empleado).getSueldo_Base() + "\n"
									+ "Complementos: " + ((TEmpleadoCompleto) empleado).getComplementos() + "\n"
							: "Horas: " + ((TEmpleadoParcial) empleado).getHoras() + "\n" + "Precio por Hora: "
									+ ((TEmpleadoParcial) empleado).getPrecio_h() + "\n")
					+

					"Activo: " + (empleado.getActivo() ? "Sí" : "No");

			JOptionPane.showMessageDialog(this, texto, "Empleado de Caja", JOptionPane.INFORMATION_MESSAGE);
		} else if (context.getEvento() == Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID_KO) {
			JOptionPane.showMessageDialog(this, "No existe empleado de caja con el ID especificado.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		this.setVisible(false);
		ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
	}

}
