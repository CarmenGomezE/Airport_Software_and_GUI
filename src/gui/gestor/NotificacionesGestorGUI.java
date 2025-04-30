package src.gui.gestor;

import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import src.airportmanager.app.AirportManager;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase NotificacionesGestorGUI representa la ventana de las notificaciones
 * del gestor.
 */
public class NotificacionesGestorGUI extends JFrame {
    /** Identificador único para la serialización del objeto */
    private static final long serialVersionUID = 1L;
    /** Referencia al gestor de la interfaz gráfica */
    private final GestorGUI geGUI;
    /** Gestor principal de la lógica de la aplicación */
    private final Gestor gestor;
    /** Modelo de datos para representar la lista de notificaciones */
    private DefaultListModel<String> model;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de cambio de estado de
     * vuelo
     */
    private JCheckBox cambioEstadoVuelo;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de olvido de contraseña
     */
    private JCheckBox olvidoContrasena;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de solicitud de vuelo
     * programado
     */
    private JCheckBox solicitudVueloProgramado;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de facturas emitidas
     */
    private JCheckBox facturaEmitida;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de solicitudes de
     * compartir vuelo
     */
    private JCheckBox solicitudCompartirVuelo;
    /**
     * Checkbox para habilitar o deshabilitar notificaciones de opciones para
     * reprogramar vuelo
     */
    private JCheckBox opcionesReprogramarVuelo;

    /**
     * Constructor de la clase NotificacionesGestorGUI
     * 
     * @param manager   el manager
     * @param gestorGUI el gestorGUI
     * @param gestorAe  gestor del aeropuerto
     */
    public NotificacionesGestorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestorAe) {
        this.geGUI = gestorGUI;
        this.gestor = gestorAe;
        setTitle("Panel de notificaciones del Gestor");
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
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Notificaciones de " + gestor.getNombre() + ":");
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        model = new DefaultListModel<>();

        JList<String> listaNotificaciones = new JList<>(model);
        listaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNotificaciones.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(listaNotificaciones);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);

        /** Seleccionar tipo notificaciones que quiere recibir */
        cambioEstadoVuelo = new JCheckBox("Cambio estado vuelo");
        cambioEstadoVuelo.setSelected(true);
        olvidoContrasena = new JCheckBox("Olvido contraseña");
        olvidoContrasena.setSelected(true);
        solicitudCompartirVuelo = new JCheckBox("Solicitud compartir vuelo");
        solicitudCompartirVuelo.setSelected(true);
        facturaEmitida = new JCheckBox("Factura emitida");
        facturaEmitida.setSelected(true);
        opcionesReprogramarVuelo = new JCheckBox("Opciones reprogramar vuelo");
        opcionesReprogramarVuelo.setSelected(true);
        solicitudVueloProgramado = new JCheckBox("Solicitud vuelo programado");
        solicitudVueloProgramado.setSelected(true);
        JPanel panelCheck = new JPanel(new GridLayout(6, 1));
        panelCheck.add(cambioEstadoVuelo);
        panelCheck.add(olvidoContrasena);
        panelCheck.add(solicitudCompartirVuelo);
        panelCheck.add(facturaEmitida);
        panelCheck.add(opcionesReprogramarVuelo);
        panelCheck.add(solicitudVueloProgramado);
        panelCheck.setBackground(ConfiguracionUI.COLOR_FONDO);

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
        panel.add(panelCheck);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(botonPanel);

        actualizarNotificaciones();

        setVisible(true);
    }

    /**
     * Método auxiliar para actualizar las notificaciones
     */
    private void actualizarNotificaciones() {
        model.clear();
        for (Notificacion notificacion : gestor.getNotificaciones()) {

            if (solicitudVueloProgramado.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO) {
                model.addElement(notificacion.getMensaje());
            }

            if (cambioEstadoVuelo.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.CAMBIO_ESTADO_VUELO) {
                model.addElement(notificacion.getMensaje());
            }

            if (olvidoContrasena.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.OLVIDO_CONTRASENA) {
                model.addElement(notificacion.getMensaje());
            }

            if (facturaEmitida.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.FACTURA_EMITIDA) {
                model.addElement(notificacion.getMensaje());
            }

            if (solicitudCompartirVuelo.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.SOLICITUD_COMPARTIR_VUELO) {
                model.addElement(notificacion.getMensaje());
            }

            if (opcionesReprogramarVuelo.isSelected() &&
                    notificacion.getTipoNotificacion() == TipoNotificacion.OPCIONES_REPROGRAMAR_VUELO) {
                model.addElement(notificacion.getMensaje());
            }
        }
    }

}
