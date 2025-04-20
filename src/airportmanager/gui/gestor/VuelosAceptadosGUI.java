package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.Vuelo;

public class VuelosAceptadosGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final GestorGUI geGUI;
    private final AirportManager manager;
    private final Gestor gestor;
    private DefaultListModel<String> model;

    public VuelosAceptadosGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestorAe) {
        this.geGUI = gestorGUI;
        this.gestor = gestorAe;
        this.manager=manager;
        setTitle("Panel de vuelos aceptados del Gestor");
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
        JLabel titulo = new JLabel("Vuelos aceptados de " + manager.getAeropuerto().getNombre() + ":", JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        model = new DefaultListModel<>();
        actualizarNotificaciones();

        JList<String> listaNotificaciones = new JList<>(model);
        listaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNotificaciones.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(listaNotificaciones);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        JButton botonRefrescar = new JButton("🔄 Refrescar vuelos aceptados");
        botonRefrescar.setBackground(ConfiguracionUI.COLOR_FONDO);
        botonRefrescar.setFont(botonRefrescar.getFont().deriveFont(16f));
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarNotificaciones();
            }
        });

        botonPanel.add(botonRefrescar);
        panel.add(botonPanel);

        setVisible(true);
    }

    private void actualizarNotificaciones() {
        model.clear();
        for (Vuelo vuelo : manager.getAeropuerto().getVuelosAceptados()) {
            model.addElement("Vuelo: "+vuelo.toString()+ " Fecha: "+vuelo.getFecha()+" Hora: "+vuelo.getHora()+" Estado: "+vuelo.getEstado()); 
        }
    }
}
