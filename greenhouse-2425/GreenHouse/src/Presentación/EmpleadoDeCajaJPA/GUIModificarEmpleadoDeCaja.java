package Presentacion.EmpleadoDeCajaJPA;

import javax.swing.*;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Command.Context;
import Presentacion.Controller.IGUI;
import Presentacion.FactoriaVistas.Evento;

import java.awt.*;
import java.awt.event.ActionEvent;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoParcial;

public class GUIModificarEmpleadoDeCaja extends JFrame implements IGUI {

    private JTextField textId, textNombre, textApellido, textDNI, textTelefono, textIdTurno;
    private JTextField textSueldoBase, textComplemento, textPrecioHora, textHoras;
    private Boolean tCompleto = false;
    private Boolean tParcial = false;

    public GUIModificarEmpleadoDeCaja() {
        super("Modificar Empleado De Caja");

        // Configuración de la ventana
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 800, alto = 600;
        int x = (pantalla.width - ancho) / 2;
        int y = (pantalla.height - alto) / 2;
        this.setBounds(x, y, ancho, alto);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // Inicializar la interfaz gráfica
        initGUI();
    }

    private void initGUI() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setContentPane(mainPanel);

        // Título
        JLabel titleLabel = new JLabel("Modificar Empleado de Caja", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de contenido centrado
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Tipo de empleado
        JPanel panelTEmpleado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTEmpleado = new JLabel("Tipo de Empleado: ");
        JComboBox<String> tipoEmpleado = new JComboBox<>(new String[]{"Elige Tipo", "Completo", "Parcial"});
        tipoEmpleado.setPreferredSize(new Dimension(200, 25));
        panelTEmpleado.add(labelTEmpleado);
        panelTEmpleado.add(tipoEmpleado);
        contentPanel.add(panelTEmpleado);

        // Campos generales
        contentPanel.add(createInputPanel("ID: ", textId = new JTextField(15)));
        contentPanel.add(createInputPanel("Nombre: ", textNombre = new JTextField(15)));
        contentPanel.add(createInputPanel("Apellido: ", textApellido = new JTextField(15)));
        contentPanel.add(createInputPanel("DNI: ", textDNI = new JTextField(15)));
        contentPanel.add(createInputPanel("Teléfono: ", textTelefono = new JTextField(15)));
       // contentPanel.add(createInputPanel("Sueldo: ", textSueldo = new JTextField(15)));
        contentPanel.add(createInputPanel("ID del turno: ", textIdTurno = new JTextField(15)));

        // Panel de empleado completo
        JPanel E_Completo = new JPanel(new GridLayout(2, 2, 10, 10));
        E_Completo.setBorder(BorderFactory.createTitledBorder("Empleado Completo"));
        E_Completo.add(new JLabel("Sueldo Base: "));
        E_Completo.add(textSueldoBase = new JTextField(15));
        E_Completo.add(new JLabel("Complementos: "));
        E_Completo.add(textComplemento = new JTextField(15));
        E_Completo.setVisible(false);
        contentPanel.add(E_Completo);

        // Panel de empleado parcial
        JPanel E_Parcial = new JPanel(new GridLayout(2, 2, 10, 10));
        E_Parcial.setBorder(BorderFactory.createTitledBorder("Empleado Parcial"));
        E_Parcial.add(new JLabel("Precio Hora: "));
        E_Parcial.add(textPrecioHora = new JTextField(15));
        E_Parcial.add(new JLabel("Horas: "));
        E_Parcial.add(textHoras = new JTextField(15));
        E_Parcial.setVisible(false);
        contentPanel.add(E_Parcial);

        // Acción del selector de tipo de empleado
        tipoEmpleado.addActionListener(e -> {
            String option = (String) tipoEmpleado.getSelectedItem();
            tCompleto = "Completo".equals(option);
            tParcial = "Parcial".equals(option);
            E_Completo.setVisible(tCompleto);
            E_Parcial.setVisible(tParcial);
        });

        // Panel de botones
        JPanel okCancel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(this::handleAceptar);
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(this::handleCancelar);
        okCancel.add(botonAceptar);
        okCancel.add(botonCancelar);
        contentPanel.add(okCancel);

        this.setVisible(true);
    }

    private JPanel createInputPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void handleAceptar(ActionEvent e) {
        try {
            TEmpleadoDeCaja empleado;
            if (tCompleto) {
                empleado = new TEmpleadoCompleto();
                empleado.setTipo(0);
                ((TEmpleadoCompleto) empleado).setSueldo_Base(Double.parseDouble(textSueldoBase.getText()));
                ((TEmpleadoCompleto) empleado).setComplementos(Double.parseDouble(textComplemento.getText()));
            } else {
                empleado = new TEmpleadoParcial();
                empleado.setTipo(1);
                ((TEmpleadoParcial) empleado).setPrecio_h(Double.parseDouble(textPrecioHora.getText()));
                ((TEmpleadoParcial) empleado).setHoras(Double.parseDouble(textHoras.getText()));
            }

            empleado.setID(Integer.parseInt(textId.getText()));
            empleado.setNombre(textNombre.getText());
            empleado.setApellido(textApellido.getText());
            empleado.setDNI(textDNI.getText());
            empleado.setTelefono(Integer.parseInt(textTelefono.getText()));
            empleado.setSueldo(0.0);
            empleado.setId_Turno(Integer.parseInt(textIdTurno.getText()));

            ApplicationController.getInstance().manageRequest(new Context(Evento.MODIFICAR_EMPLEADO_DE_CAJA, empleado));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancelar(ActionEvent e) {
        this.setVisible(false);
        ApplicationController.getInstance().manageRequest(new Context(Evento.EMPLEADO_DE_CAJA_VISTA, null));
    }

    @Override
    public void actualizar(Context context) {
        int resultado = (int) context.getDatos();

        if (context.getEvento() == Evento.MODIFICAR_EMPLEADO_DE_CAJA_OK) {
            JOptionPane.showMessageDialog(this, "Empleado de caja modificado correctamente con ID: " + resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.MODIFICAR_EMPLEADO_DE_CAJA_KO) {
            switch (resultado) {
                case -4:
                    JOptionPane.showMessageDialog(this, "Error: El nombre del empleado es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case -501:
                    JOptionPane.showMessageDialog(this, "Error: Ya existe un empleado con el mismo DNI y no es el actual.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case -506:
                    JOptionPane.showMessageDialog(this, "Error: No se puede cambiar de tipo al Empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case -115:
                    JOptionPane.showMessageDialog(this, "Error: El turno especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case -114:
                    JOptionPane.showMessageDialog(this, "Error: El turno especificado está inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case -404:
                    JOptionPane.showMessageDialog(this, "Error: El empleado especificado no existe o está inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Error desconocido al modificar el empleado de caja.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}
