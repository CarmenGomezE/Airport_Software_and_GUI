package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.usuario.Gestor;

public class NotificacionesGestorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final GestorGUI geGUI;
    private final Gestor gestor;
    private DefaultListModel<String> model;
    private JCheckBox cambioEstadoVuelo;
    private JCheckBox olvidoContrasena;
    private JCheckBox solicitudVueloProgramado;
    private JCheckBox facturaEmitida;
    private JCheckBox solicitudCompartirVuelo;
    private JCheckBox opcionesReprogramarVuelo;

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
        JLabel titulo = new JLabel("Notificaciones de " + gestor.getNombre() + ":", JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        model = new DefaultListModel<>();

        JList<String> listaNotificaciones = new JList<>(model);
        listaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNotificaciones.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(listaNotificaciones);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);


        /**Seleccionar tipo notificaciones que quiere recibir*/
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
        JPanel panelCheck = new JPanel(new GridLayout(6,1));
        panelCheck.add(cambioEstadoVuelo);
        panelCheck.add(olvidoContrasena);
        panelCheck.add(solicitudCompartirVuelo);
        panelCheck.add(facturaEmitida);
        panelCheck.add(opcionesReprogramarVuelo);
        panelCheck.add(solicitudVueloProgramado);
        panelCheck.setBackground(ConfiguracionUI.COLOR_FONDO);
        

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
        panel.add(panelCheck);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(botonPanel);
        
        actualizarNotificaciones();

        setVisible(true);
    }

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

