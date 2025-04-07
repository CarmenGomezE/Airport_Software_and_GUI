package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import src.airportmanager.AirportManager;

public class OperadorGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    public OperadorGUI(AirportManager manager) {
        setTitle("Panel de Operador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setResizable(false);
        setLocationRelativeTo(null);

        /**PANEL PRINCIPAL*/
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO*/
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        /** Titulo de la lista de vuelos*/
        JLabel listaVuelosTitulo = new JLabel("Lista de vuelos (" + formattedDate + ")", JLabel.CENTER);
        listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

        /** Lista de vuelos*/
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Vuelo MAD - BCN");
        modeloLista.addElement("Vuelo BCN - VAL");
        modeloLista.addElement("Vuelo MAD - LIS");
        modeloLista.addElement("Vuelo VLC - PMI");

        /** Subir y bajar lista*/
        JList<String> lista = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(400, 250));
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);

        /** Botones de la lista*/
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(new JButton("🔎 Buscar vuelos"));
        panelBotones.add(new JButton("🔄 Refrescar vuelos"));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO*/
        JPanel panelDerecho = new JPanel(new GridLayout(9, 1, 10, 10));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        /** Título del operador*/
        JLabel titulo = new JLabel("Operador de la aerolínea Iberia", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelDerecho.add(titulo);

        /** Botones*/
        JPanel subPanelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        subPanelBotones.add(new JButton("🔔 Notificaciones"));
        JButton botonCerrarSesion= new JButton("Cerrar sesión");
        subPanelBotones.add(botonCerrarSesion);
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(OperadorGUI.this, 
                    "¿Estás seguro de que quieres cerrar sesión?", 
                    "Cerrar sesión", 
                    JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);

        panelDerecho.add(new JButton("Gestión de estados de los vuelos"));
        panelDerecho.add(new JButton("Gestión de aviones"));
        panelDerecho.add(new JButton("Programar un vuelo"));
        panelDerecho.add(new JButton("Compartir un vuelo"));
        panelDerecho.add(new JButton("Estadísticas de vuelos"));
        panelDerecho.add(new JButton("Alternativas a los vuelos"));

        panelDerecho.add(Box.createVerticalStrut(10)); 

        /** Añadir al panel principal*/
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /**Visible*/
        setVisible(true);
    }

}
