package Presentacion.TurnoJPA;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class GUIAltaTurno extends JFrame implements IGUI {

    private JComboBox<String> comboTurno;

    public GUIAltaTurno() {
        super("Alta Turno");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 400;
        int alto = 300;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }

    public void initGUI() {

        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre componentes
        this.setContentPane(mainPanel);

        // Titulo
        gbc.gridwidth = 2;
        JLabel msgIntro = new JLabel("Seleccione el turno", JLabel.CENTER);
        mainPanel.add(msgIntro, gbc);

        // Resetear para los campos
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // ComboBox para seleccionar el turno
        JLabel labelTurno = new JLabel("Turno:");
        gbc.gridx = 0;
        mainPanel.add(labelTurno, gbc);
        comboTurno = new JComboBox<>(new String[] { "Mañana", "Mediodía", "Tarde", "Noche" });
        gbc.gridx = 1;
        mainPanel.add(comboTurno, gbc);

        // Panel de botones aceptar/cancelar
        JPanel okCancel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(okCancel, gbc);

        // Boton Aceptar
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(a -> {
        	TTurno turno = new TTurno();
            String turnoSeleccionado = (String) comboTurno.getSelectedItem();
            turno.setHorario(turnoSeleccionado);
            if (turnoSeleccionado != null) {
                ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_TURNO, turno));
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un turno.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        okCancel.add(botonAceptar);

        // Boton Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(a -> {
            GUIAltaTurno.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
        });
        okCancel.add(botonCancelar);

        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        int resultado = (int) context.getDatos();

        if (context.getEvento() == Evento.ALTA_TURNO_OK)
            JOptionPane.showMessageDialog(this, "Turno dado de alta correctamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        else if (context.getEvento() == Evento.ALTA_TURNO_KO)
            switch (resultado) {
            case -2:
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "El turno especificado ya existe.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al dar de alta el turno.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
    }
}
