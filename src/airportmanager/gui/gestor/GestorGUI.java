package src.airportmanager.gui.gestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class GestorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private Gestor gestor;

    public GestorGUI(AirportManager manager, Gestor gestor) {
    	this.gestor=gestor;
    	
        setTitle("Panel de Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        /** Titulo de la lista de vuelos */
        JLabel listaVuelosTitulo = new JLabel("Lista de vuelos (" + formattedDate + ")", JLabel.CENTER);
        listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

        /** Lista de vuelos */
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Vuelo MAD - BCN | 06:45 | Iberia");
        modeloLista.addElement("Vuelo VLC - PMI | 07:10 | Ryanair");
        modeloLista.addElement("Vuelo AGP - SVQ | 07:30 | Vueling");
        modeloLista.addElement("Vuelo BCN - OVD | 07:45 | Volotea");
        modeloLista.addElement("Vuelo LIS - MAD | 08:00 | TAP Portugal");
        modeloLista.addElement("Vuelo PMI - LPA | 08:30 | Binter Canarias");
        modeloLista.addElement("Vuelo BIO - AGP | 08:55 | Air Europa");
        modeloLista.addElement("Vuelo SCQ - VLC | 09:10 | Iberia Express");
        modeloLista.addElement("Vuelo LCG - BCN | 09:35 | Vueling");
        modeloLista.addElement("Vuelo TFN - MAD | 10:00 | Iberia");
        modeloLista.addElement("Vuelo LPA - SCQ | 10:25 | Binter Canarias");
        modeloLista.addElement("Vuelo OVD - LIS | 10:50 | TAP Portugal");
        modeloLista.addElement("Vuelo SVQ - BIO | 11:05 | Air Nostrum");
        modeloLista.addElement("Vuelo BCN - VLC | 11:30 | Vueling");
        modeloLista.addElement("Vuelo MAD - LCG | 12:00 | Air Europa");
        modeloLista.addElement("Vuelo VLC - TFN | 12:15 | Ryanair");
        modeloLista.addElement("Vuelo AGP - MAD | 12:45 | Iberia Express");
        modeloLista.addElement("Vuelo LIS - OVD | 13:00 | Volotea");
        modeloLista.addElement("Vuelo SCQ - LPA | 13:25 | Binter Canarias");
        modeloLista.addElement("Vuelo BIO - BCN | 13:50 | Vueling");
        modeloLista.addElement("Vuelo MAD - SCQ | 14:10 | Iberia");
        modeloLista.addElement("Vuelo OVD - AGP | 14:35 | Ryanair");
        modeloLista.addElement("Vuelo TFN - LCG | 15:00 | Air Europa");
        modeloLista.addElement("Vuelo LPA - MAD | 15:20 | Iberia Express");
        modeloLista.addElement("Vuelo VLC - LIS | 15:45 | TAP Portugal");
        modeloLista.addElement("Vuelo BCN - TFN | 16:10 | Binter Canarias");
        modeloLista.addElement("Vuelo SVQ - LPA | 16:30 | Ryanair");
        modeloLista.addElement("Vuelo AGP - LCG | 16:55 | Vueling");
        modeloLista.addElement("Vuelo MAD - OVD | 17:20 | Air Europa");
        modeloLista.addElement("Vuelo LIS - BIO | 17:45 | TAP Portugal");
        modeloLista.addElement("Vuelo LPA - VLC | 18:00 | Iberia");
        modeloLista.addElement("Vuelo SCQ - BCN | 18:30 | Vueling");
        modeloLista.addElement("Vuelo OVD - SCQ | 18:55 | Ryanair");
        modeloLista.addElement("Vuelo BIO - LPA | 19:15 | Binter Canarias");
        modeloLista.addElement("Vuelo LCG - AGP | 19:40 | Iberia Express");
        modeloLista.addElement("Vuelo MAD - SVQ | 20:00 | Air Nostrum");
        modeloLista.addElement("Vuelo VLC - BIO | 20:25 | Volotea");
        modeloLista.addElement("Vuelo BCN - LIS | 20:50 | TAP Portugal");
        modeloLista.addElement("Vuelo TFN - SCQ | 21:10 | Binter Canarias");
        modeloLista.addElement("Vuelo LCG - OVD | 21:30 | Volotea");
        modeloLista.addElement("Vuelo LIS - SCQ | 21:45 | TAP Portugal");
        modeloLista.addElement("Vuelo SVQ - TFN | 22:00 | Vueling");
        modeloLista.addElement("Vuelo AGP - LIS | 22:20 | Ryanair");
        modeloLista.addElement("Vuelo BIO - VLC | 22:35 | Iberia Express");

        /** Subir y bajar lista */
        JList<String> lista = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(400, 250));
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);

        /** Botones de la lista */
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(new JButton("🔎 Buscar vuelos"));
        panelBotones.add(new JButton("🔄 Refrescar vuelos"));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO */
        JPanel panelDerecho = new JPanel(new GridLayout(9, 1, 10, 10));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        /** Título del operador */
        JLabel titulo = new JLabel("Gestor del Aeropuerto Adolfo Suarez Madrid Barajas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelDerecho.add(titulo);

        /** Botones */
        JPanel subPanelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        subPanelBotones.add(new JButton("🔔 Notificaciones"));
        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        subPanelBotones.add(botonCerrarSesion);
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(GestorGUI.this,
                        "¿Estás seguro de que quieres cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);

        panelDerecho.add(new JButton("Gestión de infraestructura"));
        panelDerecho.add(new JButton("Configuración del aeropuerto"));
        panelDerecho.add(new JButton("Gestión de usuarios"));
        panelDerecho.add(new JButton("Estadísticas de la infraestructura"));
        panelDerecho.add(new JButton("Gestión de vuelos"));
        panelDerecho.add(new JButton("Gestión de facturas"));

        panelDerecho.add(Box.createVerticalStrut(10));

        /** Añadir al panel principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Visible */
        setVisible(true);
    }
}
