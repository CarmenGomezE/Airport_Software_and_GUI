package src.airportmanager.gui.controlador;

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
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.usuario.ControladorAereo; 

/**
 * La clase NotificacionesControladorGUI representa la ventana del controlador desde la que este puede 
 * consultar las notificaciones que ha recibido.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class NotificacionesControladorGUI extends JFrame {
	/** Número de versión de la clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz principal del controlador */
    private final ControladorGUI conGUI; 
    /** Controlador de la interfaz */
    private final ControladorAereo controlador;
    /** Modelo */
    private DefaultListModel<String> model;

    /**
     * Contructor de la clase NotificacionesControladorGUI.
     * 
     * @param manager 		Aplicación.
     * @param conGUI		Interfaz principal del controlador.
     * @param controlador   Controlador de la interfaz.
     */
    public NotificacionesControladorGUI(AirportManager manager, ControladorGUI conGUI, ControladorAereo controlador) {
        this.conGUI = conGUI;
        this.controlador = controlador;
        setTitle("Panel de notificaciones del Controlador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                conGUI.setVisible(true);
            }
        });

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = new JLabel("Notificaciones de " + controlador.getNombre() + ":", JLabel.CENTER);
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
        JButton botonRefrescar = new JButton("🔄 Refrescar notificaciones");
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
        for (Notificacion notificacion : controlador.getNotificaciones()) {
            model.addElement(notificacion.getMensaje()); 
        }
    }
}
