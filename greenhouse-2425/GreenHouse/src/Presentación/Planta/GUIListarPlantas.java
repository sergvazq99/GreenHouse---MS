package Presentacion.Planta;

import javax.swing.JFrame;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.GUIMSG;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Set;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUIListarPlantas extends JFrame implements IGUI {
	
	public GUIListarPlantas(Set<TPlanta> plantas) {
		
		this.initGUI(plantas);
		int tmp = Evento.LISTAR_PLANTAS_OK ;
		if(plantas == null || plantas.isEmpty()) {tmp = Evento.LISTAR_PLANTAS_KO;}
		
		Context context = new Context(tmp, plantas);
		this.actualizar(context);
	}

	private static final long serialVersionUID = 1L;
	
	private static final String headers[] = {"ID", "Nombre", "N.Cientifico", "ID Invernadero", "Tipo", "N.Fruta", "Maduracion", "Hoja"};
	
	private JTable table;
	private DefaultTableModel model;

	public void initGUI(Set<TPlanta> lista) {
    	this.setTitle("LISTAR PLANTAS");
    	this.setVisible(false);
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
		// TABLE MODEL
    	this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
    		public boolean isCellEditable(int row, int col) { return false; }
    	};
    	this.model.setColumnCount(0);
    	for (String s : headers)
    		this.model.addColumn(s);
    	
    	// MAIN TABLE
    	this.table = new JTable(this.model);
    	
    	this.table.setPreferredSize(new Dimension(950, 500));
  
		// BUTTONS PANEL
	
		mainPanel.add(new JScrollPane(this.table), BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(950, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		switch(context.getEvento()) {
		case Evento.LISTAR_PLANTAS_KO:
			this.setVisible(false);
			GUIMSG.showMessage("No hay plantas", "LISTAR PLANTAS", true);
			break;
		case  Evento.LISTAR_PLANTAS_OK:
		
			this.model.setRowCount(0);
			
			for (TPlanta tp : ((Set<TPlanta>) context.getDatos())) {
				String id = "" + tp.get_id(), nombre = tp.get_nombre(), cientifico = "" + tp.get_nombre_cientifico(), invernadero = "" + tp.get_id_invernadero();
				String fruta = "",mad = "", hoja = ""; 
				String tip = "Frutal";
				if(tp.get_tipo() == 1){tip = "No Frutal";}
				
				if(tp instanceof TPlantaFrutal){
					fruta = "" + ((TPlantaFrutal)tp).get_nombre_fruta();
					mad = "" + ((TPlantaFrutal)tp).get_maduracion();
				}
				else{
					
					hoja = "" + ((TPlantaNoFrutal)tp).get_tipo_hoja();
				}
							
				if (tp.getActivo()) {
					id = this.toBold(id);
					nombre = this.toBold(nombre);
					cientifico = this.toBold(cientifico);
					invernadero = this.toBold(invernadero);
					fruta =  this.toBold(fruta);
					mad  =  this.toBold(mad);
					hoja  =  this.toBold(hoja);
					tip  =  this.toBold(tip);
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
					table.getColumnModel().getColumn(1).setCellRenderer(renderer);
				}
				this.model.addRow(new Object[] {id, nombre, cientifico, invernadero, tip,fruta, mad, hoja});
			}
			this.setVisible(true);
			
			break;
		default:
			GUIMSG.showMessage("ERROR INESPERADO", "LISTAR PLANTAS", true);
			break;
		
	}
	}
	
	private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }
}