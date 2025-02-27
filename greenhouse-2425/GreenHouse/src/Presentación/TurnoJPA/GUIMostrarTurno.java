package Presentacion.TurnoJPA;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class GUIMostrarTurno extends JFrame implements IGUI {

    private JTextField textId;

    public GUIMostrarTurno() {
        super("Mostrar Turno");
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
        // Panel principal con GridBagLayout para mayor control sobre la alineación y el
        // centrado
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre los componentes
        this.setContentPane(mainPanel);

        // Titulo
        gbc.gridwidth = 2; // Toma dos columnas para el título
        JLabel msgIntro = new JLabel("Introduzca el ID del turno a mostrar", JLabel.CENTER);
        mainPanel.add(msgIntro, gbc);

        // Resetear para los campos
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Campo para el ID del turno
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
                // Crear un contexto con el evento de mostrar y el ID del turno
                ApplicationController.getInstance().manageRequest(
                        new Context(Evento.MOSTRAR_TURNO, Integer.parseInt(textId.getText())));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GUIMostrarTurno.this, "Error en el formato del ID", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBotones.add(botonAceptar);

        // Botón de cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(a -> {
            GUIMostrarTurno.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);

    }

    @Override
    public void actualizar(Context context) {

        if (context.getEvento() == Evento.MOSTRAR_TURNO_OK) {
            TTurno turno = (TTurno) context.getDatos();

            String texto = "ID: " + turno.getId() + "\nHorario: " + turno.getHorario() + "\nActivo: "
                    + (turno.isActivo() ? "Sí" : "No");

            JOptionPane.showMessageDialog(this, texto, "Turno", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.MOSTRAR_TURNO_KO) {
            JOptionPane.showMessageDialog(this, "No existe turno con el ID introducido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
