package Presentacion.Factura;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import Negocio.Factura.TCarrito;
import Negocio.Factura.TLineaFactura;

import java.util.HashSet;


public class GUIAbrirFactura extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public GUIAbrirFactura() {
		initGUI();
	}

	public void initGUI() {

		TCarrito carrito = new TCarrito();
		carrito.setLineaFactura(new HashSet<TLineaFactura>());
        ApplicationController.getInstance().manageRequest(new Context(Evento.CERRAR_FACTURA_VISTA, carrito));
	}

	@Override
	public void actualizar(Context context) {
		int resultado = (int) context.getDatos();
        if (context.getEvento() == Evento.CERRAR_FACTURA_OK) {
        	
            JOptionPane.showMessageDialog(this, "Factura creada correctamente con id: " + resultado , "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.CERRAR_FACTURA_KO) {
        	
            switch (resultado) {
            case -1:
                JOptionPane.showMessageDialog(this, "Se ha producido un error interno", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al cerrar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
	}
}