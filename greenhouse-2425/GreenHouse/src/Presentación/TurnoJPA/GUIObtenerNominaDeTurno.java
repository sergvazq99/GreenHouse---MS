package Presentacion.TurnoJPA;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
public class GUIObtenerNominaDeTurno extends JFrame implements IGUI {

	private JTextField textId;
    private JTabbedPane tabbedPane;

    public GUIObtenerNominaDeTurno() {
        super("Obtener Nómina de Turno");
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
        JLabel msgIntro = new JLabel("Seleccione el horario del turno", JLabel.CENTER);
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
            Integer idTurno = Integer.parseInt(textId.getText());
            ApplicationController.getInstance().manageRequest(new Context(Evento.OBTENER_NOMINA_DE_TURNO, idTurno));
        });
        panelBotones.add(botonAceptar);

        // Botón de cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(a -> {
            GUIObtenerNominaDeTurno.this.setVisible(false);
            ApplicationController.getInstance().manageRequest(new Context(Evento.TURNO_VISTA, null));
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.OBTENER_NOMINA_DE_TURNO_OK) {
            //TTurno turno = (TTurno) context.getDatos();
        	Float nomina = (Float) context.getDatos();

            // Crear una nueva pestaña con los resultados
            tabbedPane = new JTabbedPane();
            JPanel resultadoPanel = new JPanel();
            resultadoPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            // Mostrar el horario y la nómina total del turno
            /*JLabel labelHorario = new JLabel("Horario: " + turno.getHorario());
            gbc.gridx = 0;
            gbc.gridy = 0;
            resultadoPanel.add(labelHorario, gbc);*/

            JLabel labelNomina = new JLabel("Nómina Total: " + nomina);
            gbc.gridy = 1;
            resultadoPanel.add(labelNomina, gbc);

            tabbedPane.addTab("Resultado Nómina", resultadoPanel);
            this.getContentPane().add(tabbedPane);
            this.validate();
        } else if (context.getEvento() == Evento.OBTENER_NOMINA_DE_TURNO_KO) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la nómina para el horario especificado", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
