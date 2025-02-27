package Presentacion.TurnoJPA;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class GUIModificarTurno extends JFrame implements IGUI {

    private JTextField textId;
    private JComboBox<String> comboHorario;

    public GUIModificarTurno() {
        super("Modificar Turno");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 600;
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
        JLabel msgIntro = new JLabel("Introduzca el ID del turno y seleccione el nuevo horario", JLabel.CENTER);
        mainPanel.add(msgIntro, gbc);

        // Resetear para los campos
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // ID del turno
        JLabel labelId = new JLabel("ID:");
        gbc.gridx = 0; // Columna 0
        mainPanel.add(labelId, gbc);
        textId = new JTextField(20);
        gbc.gridx = 1; // Columna 1
        mainPanel.add(textId, gbc);

        // ComboBox para seleccionar el nuevo horario
        JLabel labelHorario = new JLabel("Nuevo Horario:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelHorario, gbc);
        comboHorario = new JComboBox<>(new String[] { "Mañana", "Mediodía", "Tarde", "Noche" });
        gbc.gridx = 1;
        mainPanel.add(comboHorario, gbc);

        // Panel de botones aceptar/cancelar
        JPanel okCancel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(okCancel, gbc);

        // Boton Aceptar
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(a -> {
            try {
                Integer idTurno = Integer.parseInt(textId.getText());
                String nuevoHorario = (String) comboHorario.getSelectedItem();

                TTurno turno = new TTurno();
                turno.setId(idTurno);
                turno.setHorario(nuevoHorario);
                ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_TURNO, turno));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GUIModificarTurno.this, "Error en el formato del ID", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        okCancel.add(botonAceptar);

        // Boton Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(a -> {
            GUIModificarTurno.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
        });
        okCancel.add(botonCancelar);

        setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        int resultado = (int) context.getDatos();

        if (context.getEvento() == Evento.MODIFICAR_TURNO_OK)
            JOptionPane.showMessageDialog(this, "Turno modificado correctamente con id: " + resultado, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        else if (context.getEvento() == Evento.MODIFICAR_TURNO_KO)
            switch (resultado) {
            case -2:
                JOptionPane.showMessageDialog(this, "El turno seleccionado ya existe.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "El turno especificado no existe.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case -4:
                JOptionPane.showMessageDialog(this, "Los datos introducidos no son válidos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al modificar el turno.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
    }
}
