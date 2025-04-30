package src.gui.operador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import src.airportmanager.app.AirportManager;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.SolicitudVueloCompartido;
import src.gui.ConfiguracionUI;

/**
 * La clase SolicitudesCompartirVueloGUI representa la ventana del operador
 * desde la que este gestiona
 * las solicituedes para compartir vuelos con otras aerolíneas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudesCompartirVueloGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;
    /** Lista de solicitudes */
    private DefaultListModel<String> modeloLista;
    /** Lista de la cadena de solicitudes */
    private JList<String> listaSolicitudes;
    /** Hueco para introducir el código de vuelo */
    private JTextField campoCodigo;

    /**
     * Constructor de la clase SolicitudesCompartirVueloGUI.
     * 
     * @param manager           El objeto AirportManager que gestiona los datos y
     *                          operaciones.
     * @param operadorGUI       La interfaz gráfica del operador.
     * @param operadorAerolinea El operador que gestiona los vuelos.
     */
    public SolicitudesCompartirVueloGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;
        setTitle("Panel solicitudes compartir vuelos del Operador");
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
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel listaSolicitudesTitulo = ConfiguracionUI.crearTitulo("Solicitudes de vuelo para compartir");

        panelIzquierdo.add(listaSolicitudesTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaSolicitudes = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaSolicitudes);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar solicitudes");
        panelBotones.add(botonRefrescar);
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLista();
            }
        });

        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO */
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel titulo = ConfiguracionUI.crearTitulo("Operador: " + operador.getNombre());

        panelDerecho.add(titulo);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel labelCodigo = new JLabel("Código de vuelo:");
        labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(labelCodigo);

        campoCodigo = new JTextField();
        campoCodigo.setMaximumSize(new Dimension(1000, 800));
        campoCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(campoCodigo);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botonAceptar = ConfiguracionUI.crearBotonCentrado("✅ Aceptar solicitud");

        panelDerecho.add(botonAceptar);

        JButton botonRechazar = ConfiguracionUI.crearBotonCentrado("❌ Rechazar solicitud");

        panelDerecho.add(botonRechazar);

        panelDerecho.add(Box.createVerticalGlue());

        /** Acción aceptar */
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                SolicitudVueloCompartido solicitud = operador.buscarSolicitudPorCodigo(codigo);
                if (solicitud == null) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "No se encontró ninguna solicitud para ese vuelo.");
                    return;
                }

                try {
                    operador.aceptarSolicitud(solicitud);
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "Solicitud aceptada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "Error al aceptar solicitud: " + ex.getMessage());
                }
            }
        });

        /** Acción rechazar */
        botonRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                SolicitudVueloCompartido solicitud = operador.buscarSolicitudPorCodigo(codigo);
                if (solicitud == null) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "No se encontró ninguna solicitud para ese vuelo.");
                    return;
                }

                try {
                    operador.rechazarSolicitud(solicitud);
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "Solicitud rechazada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SolicitudesCompartirVueloGUI.this,
                            "Error al rechazar solicitud: " + ex.getMessage());
                }
            }
        });

        /** Añadir los paneles al principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Hacer la ventana visible */
        setVisible(true);
    }

    /**
     * Método que actualiza la lista de solicitudes.
     */
    private void actualizarLista() {
        modeloLista.clear();
        List<SolicitudVueloCompartido> solicitudes = operador.getSolicitudesRecibidas();
        for (SolicitudVueloCompartido s : solicitudes) {
            modeloLista.addElement("Vuelo: " + s.getVueloOriginal().getCodigo() + " | De: "
                    + s.getOperadorSolicitante().getNombre() + " | Estado: " + s.getEstadoSolicitud());
        }
    }
}
