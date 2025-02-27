package Presentacion.ProveedorJPA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.ProveedorJPA.TProveedor;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

@SuppressWarnings("serial")
public class GUIProveedor extends JFrame implements IGUI{

	private TProveedor proveedor;

	private JButton buttonAltaProveedor;
    private JButton buttonBajaProveedor;
    private JButton buttonModificarProveedor;
    private JButton buttonMostrarProveedorPorID;
    private JButton buttonListarProveedor;
    private JButton buttonListarProveedorPorMarca;
    private JButton buttonVincularProveedorMarca;
    private JButton buttonDesvincularProveedorMarca;

    private JButton backButton;
    private JPanel panel;

	public GUIProveedor() {
		super("Proveedor");
		 Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	        int ancho = 1000;
	        int alto = 500;
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

		JLabel label = ComponentsBuilder.createLabel("PROVEEDORES", 250, 30, 500, 50, Color.BLACK);
        this.add(label);

        // ALTA_PROVEEDOR_VISTA
        buttonAltaProveedor = ComponentsBuilder.createButton("Alta", 100, 100, 185, 100);
        buttonAltaProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_PROVEEDOR_VISTA, proveedor));
            }
        });
        buttonAltaProveedor.setVisible(true);
        this.add(buttonAltaProveedor);

        // BAJA_PROVEEDOR_VISTA
        buttonBajaProveedor = ComponentsBuilder.createButton("Baja", 300, 100, 185, 100);
        buttonBajaProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.BAJA_PROVEEDOR_VISTA, proveedor));
            }
        });
        buttonBajaProveedor.setVisible(true);
        this.add(buttonBajaProveedor);

        // MODIFICAR_PROVEEDOR_VISTA
        buttonModificarProveedor = ComponentsBuilder.createButton("Modificar", 500, 100, 185, 100);
        buttonModificarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_PROVEEDOR_VISTA, proveedor));
            }
        });
        buttonModificarProveedor.setVisible(true);
        this.add(buttonModificarProveedor);

        // MOSTRAR_PROVEEDOR_POR_ID_VISTA
        buttonMostrarProveedorPorID = ComponentsBuilder.createButton("Mostrar por ID", 700, 100, 185, 100);
        buttonMostrarProveedorPorID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MOSTRAR_PROVEEDORES_POR_ID_VISTA, proveedor));
            }
        });
        buttonMostrarProveedorPorID.setVisible(true);
        this.add(buttonMostrarProveedorPorID);

        // LISTAR_PROVEEDORS
        buttonListarProveedor = ComponentsBuilder.createButton("Listar", 100, 250, 185, 100);
        buttonListarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PROVEEDORES, proveedor));
            }
        });
        buttonListarProveedor.setVisible(true);
        this.add(buttonListarProveedor);

        // LISTAR_PROVEEDORS_POR_SISTEMA_RIEGO_VISTA
        buttonListarProveedorPorMarca = ComponentsBuilder.createButton("Listar por Marca", 300, 250, 185, 100);
        buttonListarProveedorPorMarca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.LISTAR_PROVEEDORES_DE_MARCA_VISTA, proveedor));
            }
        });
        buttonListarProveedorPorMarca.setVisible(true);
        this.add(buttonListarProveedorPorMarca);
        
        // VINCULAR_SISTEMA_RIEGO_PROVEEDOR_VISTA
        buttonVincularProveedorMarca = ComponentsBuilder.createButton("Vincular Marca", 500, 250, 185, 100);
        buttonVincularProveedorMarca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.VINCULAR_MARCA_PROVEEDOR_VISTA, proveedor));
            }
        });
        buttonVincularProveedorMarca.setVisible(true);
        this.add(buttonVincularProveedorMarca);
        
     // DESVINCULAR_SISTEMA_RIEGO_PROVEEDOR_VISTA
        buttonDesvincularProveedorMarca = ComponentsBuilder.createButton("Desvincular Marca", 700, 250, 185, 100);
        buttonDesvincularProveedorMarca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.DESVINCULAR_MARCA_PROVEEDOR_VISTA, proveedor));
            }
        });
        buttonDesvincularProveedorMarca.setVisible(true);
        this.add(buttonDesvincularProveedorMarca);
        

        // BOT�N DE VOLVER
        backButton = ComponentsBuilder.createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GUIProveedor.this.setVisible(false);
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
		// Aquí se podría actualizar la vista si fuera necesario
	}
}
