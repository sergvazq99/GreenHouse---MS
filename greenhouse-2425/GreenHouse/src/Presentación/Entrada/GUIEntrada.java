package Presentacion.Entrada;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.Entrada.TEntrada;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIEntrada extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton bAltaEntrada;
	private JButton bBajaEntrada;
	private JButton bModificarEntrada;
	private JButton bMostrarEntrada;
	private JButton bListarEntradas;
	private JButton bListarEntradasPorInvernadero;
	private JButton backButton;

	private JPanel panel;

	private TEntrada tEntrada;

	public GUIEntrada() {

		super("BGanos");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = 1000;
		int alto = 525;
		int x = (pantalla.width - ancho) / 2;
		int y = (pantalla.height - alto) / 2;
		this.setBounds(x, y, ancho, alto);
		this.setLayout(null);
		panel = new JPanel();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		this.setVisible(true);

	}

	private void initGUI() {
		tEntrada = new TEntrada();
		JLabel label = ComponentsBuilder.createLabel("Entrada", 250, 30, 500, 50, Color.BLACK);
		this.add(label);

		// ALTA ENTRADA
		bAltaEntrada = ComponentsBuilder.createButton("Alta entrada", 70, 120, 250, 100);
		bAltaEntrada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_ENTRADA_VISTA, tEntrada));
			}
		});
		
		bAltaEntrada.setVisible(true);
		this.add(bAltaEntrada);
		
		// BAJA ENTRADA
		bBajaEntrada = ComponentsBuilder.createButton("Baja entrada", 370, 120, 250, 100);
		bBajaEntrada.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_ENTRADA_VISTA, tEntrada));
			}
		});
		
		bBajaEntrada.setVisible(true);
		this.add(bBajaEntrada);
		
		
		// MODIFICAR ENTRADA
		bModificarEntrada = ComponentsBuilder.createButton("Modificar entrada", 670, 120, 250, 100);
		bModificarEntrada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_ENTRADA_VISTA, tEntrada));
			}

		});
		bModificarEntrada.setVisible(true);
		this.add(bModificarEntrada);
		
		// MOSTRAR ENTRADA
		bMostrarEntrada = ComponentsBuilder.createButton("Mostrar entrada", 70, 290, 250, 100);
		bMostrarEntrada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_ENTRADA_POR_ID_VISTA, tEntrada));
			}

		});
		bMostrarEntrada.setVisible(true);
		this.add(bMostrarEntrada);
		
		// LISTAR ENTRADAS
		bListarEntradas = ComponentsBuilder.createButton("Listar entradas", 370, 290, 250, 100);
		bListarEntradas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_ENTRADAS, tEntrada));
			}

		});
		bListarEntradas.setVisible(true);
		this.add(bListarEntradas);
		
		
		// LISTAR ENTRADAS POR INVERNADERO
//		bListarEntradasPorInvernadero = ComponentsBuilder.createButton("Listar entradas por invernadero", 715, 290, 200, 100);
		bListarEntradasPorInvernadero = ComponentsBuilder.createButton("Listar entradas por invernadero", 670, 290, 250, 100);
		bListarEntradasPorInvernadero.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEntrada.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_ENTRADAS_POR_INVERNADERO_VISTA, tEntrada));
			}

		});
		bListarEntradasPorInvernadero.setVisible(true);
		this.add(bListarEntradasPorInvernadero);

		// BOTON DE VOLVER
        backButton = ComponentsBuilder.createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIEntrada.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.VISTA_PRINCIPAL, null));
                dispose();
            }
        });
        backButton.setVisible(true);
        this.add(backButton);

        getContentPane().add(panel);
	}

	@Override
	public void actualizar(Context context) {

	}
}
