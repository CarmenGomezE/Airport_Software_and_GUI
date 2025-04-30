package src.gui.operador;

import java.awt.Dimension;
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
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.usuario.Operador;
import src.gui.ConfiguracionUI;

/**
 * La clase NotificacionesOperadorGUI representa la ventana del operador desde
 * la que este puede
 * consultar las notificaciones que ha recibido.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class NotificacionesOperadorGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;
    /** Modelo de lista por defecto */
    private DefaultListModel<String> model;

    /**
     * Constructor de la ventana para gestionar los estados de los vuelos.
     * 
     * @param manager           AirportManager.
     * @param operadorGUI       Ventana principal del operador.
     * @param operadorAerolinea Operador.
     */
    public NotificacionesOperadorGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;
        setTitle("Panel de notificaciones del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                opGUI.setVisible(true);
            }
        });

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Notificaciones de " + operador.getNombre() + ":");

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
        JButton botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar notificaciones");

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

    /**
     * Método que actualiza la lista de notificaciones.
     */
    private void actualizarNotificaciones() {
        model.clear();
        for (Notificacion notificacion : operador.getNotificaciones()) {
            model.addElement(notificacion.getMensaje());
        }
    }
}
