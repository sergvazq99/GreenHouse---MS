package Presentacion.TurnoJPA;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Presentacion.ComponentsBuilder.ComponentsBuilder;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Negocio.TurnoJPA.TTurno;

@SuppressWarnings("serial")
public class GUIListarTurnos extends JFrame implements IGUI {

    public GUIListarTurnos(Set<TTurno> listaTurnos) {
        super("Listar Turnos");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 800;
        int alto = 400;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI(listaTurnos);
    }

    public void initGUI(Set<TTurno> listaTurnos) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla
        String[] nombreColumnas = { "ID", "Horario", "Activo" };
        String[][] tablaDatos = new String[listaTurnos.size()][nombreColumnas.length];

        int i = 0;
        for (TTurno turno : listaTurnos) {
            tablaDatos[i][0] = turno.getId().toString();
            tablaDatos[i][1] = turno.getHorario();
            tablaDatos[i][2] = turno.isActivo() ? "Sí" : "No";
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
            GUIListarTurnos.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
        this.setResizable(true);
    }

    public void actualizar(Context context) {
        if (context.getEvento() == Evento.LISTAR_TURNO_OK) {
            JOptionPane.showMessageDialog(this, "Turnos listados correctamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.LISTAR_TURNO_KO) {
            JOptionPane.showMessageDialog(this, "Error al tratar de listar los Turnos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
