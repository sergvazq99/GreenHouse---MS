package Presentacion.SistemaDeRiego;

import javax.swing.*;


import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

@SuppressWarnings("serial")
public class GUIListarSistemasDeRiego extends JFrame implements IGUI {
  
    public GUIListarSistemasDeRiego(Set<TSistemaDeRiego> datos) {
        super("Mostrar todos los Sistemas de Riego");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 800;  
        int alto = 400;   
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI(datos);
    }

    private void initGUI(Set<TSistemaDeRiego> datos) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla 
        String[] nombreColumnas = { "ID", "Nombre", "Potencia Riego", "Cantidad Agua", "Frecuencia", "Activo", "Fabricante"};
        String[][] tablaDatos = new String[datos.size()][nombreColumnas.length];

        int i = 0;
        for (TSistemaDeRiego sistema : datos) {
            tablaDatos[i][0] = sistema.getId().toString();
            tablaDatos[i][1] = sistema.getNombre();
            tablaDatos[i][2] = sistema.getPotenciaRiego().toString();
            tablaDatos[i][3] = sistema.getCantidad_agua().toString();
            tablaDatos[i][4] = sistema.getFrecuencia().toString();
            tablaDatos[i][5] = sistema.getActivo() ? "Si" : "No";
            tablaDatos[i][6] = sistema.getIdFabricante().toString();
            i++;
        }

        JTable tabla =  ComponentsBuilder.createTable(0, nombreColumnas.length, nombreColumnas, tablaDatos); 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(750, 250)); 
        mainPanel.add(scroll);
            
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        mainPanel.add(panelBotones);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIListarSistemasDeRiego.this.setVisible(false);
                ApplicationController.getInstance().manageRequest(new Context(Evento.SISTEMA_RIEGO_VISTA, null));
            }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
        this.setResizable(true);
    }

   

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_SISTEMA_DE_RIEGO_OK) {
            JOptionPane.showMessageDialog(this, "Sistemas de riego listados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.LISTAR_SISTEMA_DE_RIEGO_KO) {
            JOptionPane.showMessageDialog(this, "Error al tratar de listar los sistemas de riego", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
