package src.gui.gestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Vuelo;
import src.gui.AirportManagerGUI;
import src.gui.ConfiguracionUI;

/**
 * La clase GestorGUI representa la ventana principal del gestor.
 */
public class GestorGUI extends JFrame {
    /** Identificador único para la serialización del objeto */
    private static final long serialVersionUID = 1L;
    /** Referencia al gestor principal de lógica de la aplicación */
    private Gestor AeGestor;
    /** Modelo de datos para la lista de vuelos */
    private DefaultListModel<String> modeloLista;
    /** Componente gráfico que muestra la lista de vuelos */
    private JList<String> listaVuelos;
    /** Referencia al gestor de aeropuertos */
    private AirportManager AeManager;
    /** ComboBox que permite seleccionar un usuario del sistema */
    private JComboBox<String> comboUsuarios;
    /** ComboBox que permite seleccionar una infraestructura disponible */
    private JComboBox<String> comboInfra;
    /** Nombre del usuario actualmente seleccionado */
    private String usuarioSeleccionado;
    /** Nombre de la infraestructura actualmente seleccionada */
    private String infraestructuraSeleccionada;
    /** Formateador de date time */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor de la clase GestorGUI
     * 
     * @param AirportGUI el airportManager
     * @param manager    el manager
     * @param gestor     el gestor
     */
    public GestorGUI(AirportManagerGUI AirportGUI, AirportManager manager, Gestor gestor) {
        this.AeGestor = gestor;
        this.AeManager = manager;

        setTitle("Panel de Gestor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AirportGUI.setVisible(true);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        /** Titulo de la lista de vuelos */
        JLabel listaVuelosTitulo = ConfiguracionUI.crearTitulo("Lista de vuelos (" + formattedDate + ")");

        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);
        /** Lista de vuelos */
        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaVuelos = new JList<>(modeloLista);
        listaVuelos.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                String texto = value.toString();

                if (texto.contains("Esperando pista durante más de 10 min")) {
                    label.setForeground(Color.RED);
                } else if (texto.contains("Retrasado")) {
                    label.setForeground(Color.ORANGE);
                } else if (texto.contains("Salida en menos de 1 hora")) {
                    label.setForeground(Color.BLUE);
                } else {
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });
        JScrollPane scrollPane = new JScrollPane(listaVuelos);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        /** Botones de la lista */
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonBuscarVuelos = ConfiguracionUI.crearBotonCentrado("🔎 Buscar vuelos");
        panelBotones.add(botonBuscarVuelos);
        botonBuscarVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new BuscarVueloGestorGUI(manager, GestorGUI.this, AeGestor);
            }
        });

        JButton botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar vuelos");
        panelBotones.add(botonRefrescar);
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLista();
            }
        });
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO */
        JPanel panelDerecho = new JPanel(new GridLayout(10, 1, 10, 10));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        /** Título del operador */
        JLabel titulo = ConfiguracionUI.crearTitulo(manager.getAeropuerto().getNombre());
        panelDerecho.add(titulo);

        /** Botones */
        JPanel subPanelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonNotificaciones = ConfiguracionUI.crearBotonCentrado("🔔 Notificaciones");
        subPanelBotones.add(botonNotificaciones);
        botonNotificaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new NotificacionesGestorGUI(manager, GestorGUI.this, gestor);
            }
        });
        JButton botonCerrarSesion = ConfiguracionUI.crearBotonCentrado("Cerrar sesión");
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
                    AirportGUI.setVisible(true);
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);

        JPanel subPanelInfraestructura = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonGestionInfraestructura = ConfiguracionUI.crearBotonCentrado("Gestión de infraestructura");
        subPanelInfraestructura.add(botonGestionInfraestructura);

        String[] infras = { "Hangar", "Terminal", "Finger", "Zona de aparcamiento", "Pista de despegue",
                "Pista de aterrizaje", "Aerolínea", "Aeropuerto de destino" };
        comboInfra = new JComboBox<>(infras);
        comboInfra.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                infraestructuraSeleccionada = (String) comboInfra.getSelectedItem();

            }

        });

        subPanelInfraestructura.add(comboInfra);
        subPanelInfraestructura.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelInfraestructura);
        botonGestionInfraestructura.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (infraestructuraSeleccionada == null) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, selecciona la infraestructura que desea gestionar.",
                            "Campos vacíos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                switch (infraestructuraSeleccionada) {

                    case "Terminal":
                        setVisible(true);
                        new GestionInfraestructurasTerminalGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Hangar":
                        setVisible(true);
                        new GestionInfraestructurasHangarGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Finger":
                        setVisible(true);
                        new GestionInfraestructurasFingerGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Zona de aparcamiento":
                        setVisible(true);
                        new GestionInfraestructurasZonaAparcamientoGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Pista de despegue":
                        setVisible(true);
                        new GestionInfraestructuraPistaDespegueGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Pista de aterrizaje":
                        setVisible(true);
                        new GestionInfraestructuraPistaAterrizajeGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Aerolínea":
                        setVisible(true);
                        new GestionInfraestructuraAerolineaGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Aeropuerto de destino":
                        setVisible(true);
                        new GestionInfraestructuraAeropuertoDestinoGUI(manager, GestorGUI.this, gestor);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null,
                                "Por favor, selecciona la infraestructura que desea gestionar.",
                                "Campos vacíos",
                                JOptionPane.WARNING_MESSAGE);
                        return;

                }

            }

        });

        JButton botonConfiguracionAeropuerto = ConfiguracionUI.crearBotonCentrado("Configuración del aeropuerto");
        panelDerecho.add(botonConfiguracionAeropuerto);
        botonConfiguracionAeropuerto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new ConfiguracionAeropuertoGUI(manager, GestorGUI.this, gestor);
            }
        });

        JPanel subPanelUsuarios = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonGestionUsuarios = ConfiguracionUI.crearBotonCentrado("Gestión usuarios");
        subPanelUsuarios.add(botonGestionUsuarios);

        String[] usuarios = { "Controlador", "Operador de la aerolínea" };
        comboUsuarios = new JComboBox<>(usuarios);
        comboUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioSeleccionado = (String) comboUsuarios.getSelectedItem();
            }
        });
        subPanelUsuarios.add(comboUsuarios);
        subPanelUsuarios.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelUsuarios);
        botonGestionUsuarios.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, selecciona el usuario que desea gestionar.",
                            "Campos vacíos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                switch (usuarioSeleccionado) {

                    case "Controlador":
                        setVisible(true);
                        new GestionUsuariosControladorGUI(manager, GestorGUI.this, gestor);
                        break;

                    case "Operador de la aerolínea":
                        setVisible(true);
                        new GestionUsuariosOperadorGUI(manager, GestorGUI.this, gestor);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null,
                                "Por favor, selecciona el usuario que desea gestionar.",
                                "Campos vacíos",
                                JOptionPane.WARNING_MESSAGE);
                        return;

                }
            }

        });

        JButton botonEstadisticas = ConfiguracionUI.crearBotonCentrado("Estadísticas de la infraestructura");
        panelDerecho.add(botonEstadisticas);
        botonEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new EstadisticasGestorGUI(manager, GestorGUI.this, gestor);
            }
        });

        JButton botonGestionVuelos = ConfiguracionUI.crearBotonCentrado("Gestión de vuelos");
        panelDerecho.add(botonGestionVuelos);
        botonGestionVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new GestionVueloGUI(manager, GestorGUI.this, gestor);
            }
        });

        JButton botonGestionFacturas = ConfiguracionUI.crearBotonCentrado("Gestión de facturas");
        panelDerecho.add(botonGestionFacturas);
        botonGestionFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new GestionFacturasGUI(manager, GestorGUI.this, gestor);
            }
        });

        JButton botonVuelosAceptados = ConfiguracionUI.crearBotonCentrado("Ver información del aeropuerto propio");
        panelDerecho.add(botonVuelosAceptados);
        botonVuelosAceptados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new InfoAeropuertoPropioGUI(manager, GestorGUI.this, gestor);
            }
        });
        panelDerecho.add(Box.createVerticalStrut(10));

        /** Añadir al panel principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Visible */
        setVisible(true);
    }

    /**
     * Método auxiliar para actualizar la lista de vuelos.
     */
    private void actualizarLista() {
        modeloLista.clear();
        List<Vuelo> vuelos = AeManager.getAeropuerto().getVuelosAceptados();
        LocalDate fecha = LocalDate.now();

        if (vuelos.isEmpty()) {
            modeloLista.addElement("No hay vuelos programados.");
            return;
        }

        for (Vuelo v : vuelos) {
            if (v.getFecha().equals(fecha)) {
                if (v.getEstado().equals(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE)) {
                    Duration tiempo = Duration.between(
                            v.getHistorialEstadoVuelo().getHoraCambio(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE),
                            LocalTime.now());
                    if (tiempo.toMinutes() >= 10) {
                        modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter)
                                + " | Estado: " + v.getEstado()
                                + " | Esperando pista durante más de 10 min");
                    } else {
                        modeloLista
                                .addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: "
                                        + v.getEstado());
                    }
                } else if (v.getEstado().equals(EstadoVuelo.RETRASADO)) {
                    modeloLista.addElement(
                            "Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: " + v.getEstado()
                                    + " | Retrasado");
                } else if (v.getEstado().equals(EstadoVuelo.OPERATIVO)) {
                    Duration tiempo = Duration.between(LocalTime.now(), v.getHora());
                    if (tiempo.toMinutes() <= 60) {
                        modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter)
                                + " | Estado: " + v.getEstado()
                                + " | Salida en menos de 1 hora");
                    } else {
                        modeloLista
                                .addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: "
                                        + v.getEstado());
                    }
                } else {
                    modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: "
                            + v.getEstado());
                }
                AeManager.verificarYActualizarVuelo(v);
            }
        }
    }
}