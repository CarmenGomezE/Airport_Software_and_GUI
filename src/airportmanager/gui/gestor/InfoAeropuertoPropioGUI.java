package src.airportmanager.gui.gestor;

import java.util.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.Vuelo;
import src.airportmanager.terminal.*;

/**
 * La clase InfoAeropuertoPropioGUI representa la ventana de información del aeropuerto propio del gestor.
 */
public class InfoAeropuertoPropioGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final GestorGUI geGUI;
    private final AirportManager manager;
    private DefaultListModel<String> model;
    private JList<String> listaDatos;
    private JComboBox<String> selector;

    /**
     * Constructor de la clase InfoAeropuertoPropioGUI
     * @param manager el manager
     * @param gestorGUI el gestorGUI
     * @param gestorAe el gestor del aeropuerto
     */
    public InfoAeropuertoPropioGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestorAe) {
        this.geGUI = gestorGUI;
        this.manager = manager;
        setTitle("Panel de información del Gestor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                geGUI.setVisible(true);
            }
        });

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Información del aeropuerto " + manager.getAeropuerto().getNombre());
        
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        /** SELECTOR */
        selector = new JComboBox<>(new String[] {
                "Vuelos Aceptados", "Hangares", "Terminales", "Modos de Embarque",
                "Pistas", "Aerolíneas", "Aeropuertos de Destino", "Usuarios"
        });
        selector.setMaximumSize(new Dimension(300, 30));
        selector.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(selector);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        /** LISTA */
        model = new DefaultListModel<>();
        listaDatos = new JList<>(model);
        listaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaDatos.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(listaDatos);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);

        /** BOTÓN REFRESCAR */
        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        JButton botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar");
      
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatos();
            }
        });

        botonPanel.add(botonRefrescar);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(botonPanel);

        /** Escuchador del ComboBox para actualizar automáticamente */
        selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatos();
            }
        });

        setVisible(true);
        actualizarDatos(); // Carga inicial
    }

    /**
     * Método auxiliar para actualizar los datos del aeropuerto.
     */
    private void actualizarDatos() {
        model.clear();
        if (manager == null || manager.getAeropuerto() == null) {
            model.addElement("No hay información disponible.");
            return;
        }

        String seleccion = (String) selector.getSelectedItem();
        if (seleccion == null)
            return;

        switch (seleccion) {
            case "Vuelos Aceptados":
                if (manager.getAeropuerto().getVuelosAceptados() != null) {
                    for (Vuelo vuelo : manager.getAeropuerto().getVuelosAceptados()) {
                        model.addElement("Vuelo: " + vuelo.toString() +
                                " | Fecha: " + vuelo.getFecha() +
                                " | Hora: " + vuelo.getHora() +
                                " | Estado: " + vuelo.getEstado());
                    }
                }
                break;

            case "Hangares":
                if (manager.getAeropuerto().getHangares() != null) {
                    manager.getAeropuerto().getHangares().forEach(hangar -> model.addElement(
                            "Hangar: " + hangar.getId() + " con " + hangar.getNumeroMaxAviones() + " plazas"));
                }
                break;

            case "Terminales":
                if (manager.getAeropuerto().getTerminales() != null) {
                    manager.getAeropuerto().getTerminales()
                            .forEach(terminal -> model.addElement("Terminal: " + terminal.toString()
                                    + " con controladores: " + terminal.getControladores() + " y con puertas: "
                                    + terminal.getPuertasEmbarque()));
                }
                break;

            case "Modos de Embarque":
                List<ModoEmbarque> modos = new ArrayList<>();
                if (manager.getAeropuerto().getFingers() != null) {
                    modos.addAll(manager.getAeropuerto().getFingers());
                }
                if (manager.getAeropuerto().getZonasAparcamiento() != null) {
                    modos.addAll(manager.getAeropuerto().getZonasAparcamiento());
                }

                if (!modos.isEmpty()) {
                    modos.forEach(modo -> model.addElement("Modo de embarque: " + modo.getId()));
                } else {
                    model.addElement("No hay modos de embarque disponibles.");
                }

            case "Pistas":
                if (manager.getAeropuerto().getPistas() != null) {
                    manager.getAeropuerto().getPistas().forEach(pista -> model.addElement("Pista: " + pista.getCodigo()
                            + " de longitud: " + pista.getLongitud() + " y tipo: " + pista.getTipoPista()));
                }
                break;

            case "Aerolíneas":
                if (manager.getAeropuerto().getAerolineas() != null) {
                    manager.getAeropuerto().getAerolineas().forEach(aerolinea -> model.addElement(
                            "Aerolinea: " + aerolinea.getNombre() + " con código: " + aerolinea.getCodigo()));
                }
                break;

            case "Aeropuertos de Destino":
                if (manager.getAeropuerto().getAeropuertosDestino() != null) {
                    manager.getAeropuerto().getAeropuertosDestino()
                            .forEach(destino -> model.addElement("Aeropuerto de destino: " + destino.toString()));
                }
                break;

            case "Usuarios":
                if (manager.getUsuarios() != null) {
                    manager.getUsuarios().forEach(usuario -> model.addElement("Usuario: " + usuario.getNombre()));
                }
                break;

            default:
                model.addElement("No hay datos disponibles.");
                break;
        }
        if (model.isEmpty()) {
            model.addElement("No hay elementos para mostrar.");
        }
    }
}
