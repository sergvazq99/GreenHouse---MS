package Presentacion.MarcaJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.MarcaJPA.TMarca;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class GUIMarca extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JButton bAltaMarca;
	private JButton bBajaMarca;
	private JButton bModificarMarca;
	private JButton bMostrarMarcas;
	private JButton bListarMarcas;
	private JButton bListarMarcasPorProveedor;
	private JButton backButton;

	private JPanel panel;

	private TMarca tMarca;

	public GUIMarca() {

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
		tMarca = new TMarca();
		JLabel label = ComponentsBuilder.createLabel("Marca", 250, 30, 500, 50, Color.BLACK);
		this.add(label);

		// ALTA MARCA
		bAltaMarca = ComponentsBuilder.createButton("Alta marca", 70, 120, 250, 100);
		bAltaMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_MARCA_VISTA, tMarca));
			}
		});

		bAltaMarca.setVisible(true);
		this.add(bAltaMarca);

		// BAJA MARCA
		bBajaMarca = ComponentsBuilder.createButton("Baja marca", 370, 120, 250, 100);
		bBajaMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_MARCA_VISTA, tMarca));
			}
		});

		bBajaMarca.setVisible(true);
		this.add(bBajaMarca);
		
		// MODIFICAR MARCA
		bModificarMarca = ComponentsBuilder.createButton("Modificar marca", 670, 120, 250, 100);
		bModificarMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_MARCA_VISTA, tMarca));
			}

		});
		bModificarMarca.setVisible(true);
		this.add(bModificarMarca);
		
		// MOSTRAR MARCA POR ID
		bMostrarMarcas = ComponentsBuilder.createButton("Mostrar marca", 70, 290, 250, 100);
		bMostrarMarcas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_MARCA_VISTA, tMarca));
			}

		});
		bMostrarMarcas.setVisible(true);
		this.add(bMostrarMarcas);
		
		// LISTAR MARCAS
		bListarMarcas = ComponentsBuilder.createButton("Listar marcas", 370, 290, 250, 100);
		bListarMarcas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_MARCAS, tMarca));
			}

		});
		bListarMarcas.setVisible(true);
		this.add(bListarMarcas);
		
		// LISTAR MARCAS POR PROVEEDOR
		bListarMarcasPorProveedor = ComponentsBuilder.createButton("Listar marcas por proveedor", 670, 290, 250, 100);
		bListarMarcasPorProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMarca.this.setVisible(false);
				ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_MARCAS_POR_PROVEEDOR_VISTA, tMarca));
			}

		});
		bListarMarcasPorProveedor.setVisible(true);
		this.add(bListarMarcasPorProveedor);
		
		// BOTON DE VOLVER
        backButton = ComponentsBuilder.createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIMarca.this.setVisible(false);
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
