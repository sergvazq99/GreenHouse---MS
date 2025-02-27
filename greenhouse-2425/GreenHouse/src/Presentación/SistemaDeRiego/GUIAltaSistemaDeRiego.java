package Presentacion.SistemaDeRiego;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;
import Presentacion.Controller.ApplicationController;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

@SuppressWarnings("serial")
public class GUIAltaSistemaDeRiego extends JFrame implements IGUI {

    private JTextField textNombre;
    private JTextField textPotenciaRiego;
    private JTextField textCantidadAgua;
    private JTextField textFrecuencia;
    private JTextField textIdFabricante;

    public GUIAltaSistemaDeRiego() {
        super("Alta Sistema de Riego");
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

    private void initGUI() {
        // Panel principal 
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margenes entre componentes
        this.setContentPane(mainPanel);

        // Titulo
        gbc.gridwidth = 2; // Dos columnas para el titulo
        JLabel msgIntro = new JLabel("Introduzca los datos del sistema de riego", JLabel.CENTER);
        mainPanel.add(msgIntro, gbc);

        // Resetear para los campos
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Nombre del sistema de riego
        JLabel labelNombre = new JLabel("Nombre:");
        gbc.gridx = 0; 
        mainPanel.add(labelNombre, gbc);
        textNombre = new JTextField(20);
        gbc.gridx = 1; 
        mainPanel.add(textNombre, gbc);

        // Potencia de riego
        JLabel labelPotenciaRiego = new JLabel("Potencia de Riego:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelPotenciaRiego, gbc);
        textPotenciaRiego = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textPotenciaRiego, gbc);

        // Cantidad de agua
        JLabel labelCantidadAgua = new JLabel("Cantidad de Agua:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(labelCantidadAgua, gbc);
        textCantidadAgua = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textCantidadAgua, gbc);

        // Frecuencia de riego
        JLabel labelFrecuencia = new JLabel("Frecuencia:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(labelFrecuencia, gbc);
        textFrecuencia = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textFrecuencia, gbc);

        // ID del fabricante
        JLabel labelIdFabricante = new JLabel("ID del Fabricante:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(labelIdFabricante, gbc);
        textIdFabricante = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textIdFabricante, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        mainPanel.add(panelBotones, gbc);

        // Boton Aceptar
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = textNombre.getText();
                    Integer potenciaRiego = Integer.parseInt(textPotenciaRiego.getText());
                    Integer cantidadAgua = Integer.parseInt(textCantidadAgua.getText());
                    Integer frecuencia = Integer.parseInt(textFrecuencia.getText());
                    Integer idFabricante = Integer.parseInt(textIdFabricante.getText());

                    TSistemaDeRiego sistemaDeRiego = new TSistemaDeRiego();
                    sistemaDeRiego.setNombre(nombre);
                    sistemaDeRiego.setPotenciaRiego(potenciaRiego);
                    sistemaDeRiego.setCantidad_agua(cantidadAgua);
                    sistemaDeRiego.setFrecuencia(frecuencia);
                    sistemaDeRiego.setIdFabricante(idFabricante);
                    sistemaDeRiego.setActivo(true);

                    ApplicationController.getInstance().manageRequest(new Context(Evento.ALTA_SISTEMA_RIEGO, sistemaDeRiego));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GUIAltaSistemaDeRiego.this, "Error en el formato de los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBotones.add(botonAceptar);

        // Boton Cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
        	@Override
 	        public void actionPerformed(ActionEvent e) {
        		GUIAltaSistemaDeRiego.this.setVisible(false);
 	            ApplicationController.getInstance().manageRequest(new Context(Evento.SISTEMA_RIEGO_VISTA, null));
 	        }
        });
        panelBotones.add(botonCancelar);

        this.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
    	int resultado = (int) context.getDatos();
        if (context.getEvento() == Evento.ALTA_SISTEMA_DE_RIEGO_OK) {
        	
            JOptionPane.showMessageDialog(this, "Sistema de riego dado de alta correctamente con id: " + resultado , "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (context.getEvento() == Evento.ALTA_SISTEMA_DE_RIEGO_KO) {
        	
            switch (resultado) {
            case -3:
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Error: Ya existe un sistema de riego con el mismo nombre y está activo.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -511:
                JOptionPane.showMessageDialog(this, "Error: El fabricante asociado no está activo.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -404:
                JOptionPane.showMessageDialog(this, "Error: El fabricante especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al modificar el sistema de riego.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
}
