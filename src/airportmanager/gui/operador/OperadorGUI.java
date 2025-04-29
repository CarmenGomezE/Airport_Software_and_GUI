package src.airportmanager.gui.operador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.AirportManagerGUI;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Vuelo;

/**
 * La clase OperadorGUI representa la ventana principal del operador desde la que este puede acceder
 * a las ventanas desde las que realiza las acciones que le corresponden.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class OperadorGUI extends JFrame {
	/** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Operador del que es la ventana */
    private final Operador operadorAerolinea;
    /** Modelo de lista */
    private DefaultListModel<String> modeloLista;
    /** Lista de los vuelos diarios */
    private JList<String> listaVuelos;
    /** Airport manager */
    private AirportManager AeManager;
    /** Formateador de date time */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor de la clase OperadorGUI.
     * 
     * @param AirportGUI 	Ventana principal.
     * @param manager 		Airport manager
     * @param operador		Operador que abre la ventana.
     */
    public OperadorGUI(AirportManagerGUI AirportGUI, AirportManager manager, Operador operador) {
        operadorAerolinea = operador;
        AeManager = manager;
        setTitle("Panel de Operador");
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
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
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
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
                new BuscarVueloOperadorGUI(manager, OperadorGUI.this, operadorAerolinea);
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
        JPanel panelDerecho = new JPanel(new GridLayout(11, 1, 10, 10));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        /** Título del operador */
        JLabel titulo = ConfiguracionUI.crearTitulo("Operador de la aerolínea " + operador.getAerolinea().getNombre());
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelDerecho.add(titulo);

        /** Botones */
        JPanel subPanelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonNotificaciones = ConfiguracionUI.crearBotonCentrado("🔔 Notificaciones");
        subPanelBotones.add(botonNotificaciones);
        botonNotificaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new NotificacionesOperadorGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonCerrarSesion = ConfiguracionUI.crearBotonCentrado("Cerrar sesión");
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
                    AirportGUI.setVisible(true);
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);

        JButton botonGestionEstadoVuelo = ConfiguracionUI.crearBotonCentrado("Gestión de estados de los vuelos");
        panelDerecho.add(botonGestionEstadoVuelo);
        botonGestionEstadoVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GestionEstadoVueloGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonGestionAviones = ConfiguracionUI.crearBotonCentrado("Gestión de aviones");
        panelDerecho.add(botonGestionAviones);
        botonGestionAviones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GestionAvionGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonProgramarVuelo = ConfiguracionUI.crearBotonCentrado("Programar un vuelo");
        panelDerecho.add(botonProgramarVuelo);
        botonProgramarVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ProgramarVueloGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonCompartirVuelo = ConfiguracionUI.crearBotonCentrado("Compartir un vuelo");
        panelDerecho.add(botonCompartirVuelo);
        botonCompartirVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CompartirVueloGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonVerCompartirVuelo = ConfiguracionUI.crearBotonCentrado("Gestionar solicitudes de vuelos compartidos");
        panelDerecho.add(botonVerCompartirVuelo);
        botonVerCompartirVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SolicitudesCompartirVueloGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonEstadisticas = ConfiguracionUI.crearBotonCentrado("Estadísticas de vuelos");
        panelDerecho.add(botonEstadisticas);
        botonEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new EstadisticasOperadorGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonAlternativas = ConfiguracionUI.crearBotonCentrado("Alternativas a los vuelos");
        panelDerecho.add(botonAlternativas);
        botonAlternativas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AlternativasVueloGUI(manager, OperadorGUI.this, operadorAerolinea);
            }
        });

        JButton botonFacturacion = ConfiguracionUI.crearBotonCentrado("Pagar facturas aerolínea");
        panelDerecho.add(botonFacturacion);
        botonFacturacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new PagarFacturasGUI(manager, OperadorGUI.this, operadorAerolinea);
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
     * Método que actualiza la lista de vuelos diarios.
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
                    Duration tiempo = Duration.between(v.getHistorialEstadoVuelo().getHoraCambio(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE), LocalTime.now());
                    if (tiempo.toMinutes() >= 10) {
                        modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: " + v.getEstado() + " | Esperando pista durante más de 10 min");
                    }
                } else if (v.getEstado().equals(EstadoVuelo.RETRASADO)) {
                    modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter)+ " | Estado: " + v.getEstado() + " | Retrasado");
                } else if (v.getEstado().equals(EstadoVuelo.OPERATIVO)) {
                    Duration tiempo = Duration.between(LocalTime.now(), v.getHora());
                    if (tiempo.toMinutes() <= 60) {
                        modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: " + v.getEstado() + " | Salida en menos de 1 hora");
                    }
                } else {
                    modeloLista.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter) + " | Estado: " + v.getEstado());
                }
                if(v.getHistorialEstadoVuelo().getUltimoEstadoAutomatico()!=EstadoVuelo.VUELO) {
                	AeManager.verificarYActualizarVuelo(v);
                }
            }
        }
    }
}
