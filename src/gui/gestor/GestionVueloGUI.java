package src.gui.gestor;

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.VueloProgramado;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionVueloGUI representa la ventana de gestion de vuelos de
 * gestor.
 */
public class GestionVueloGUI extends JFrame {
    /** Identificador único para la serialización del objeto */
    private static final long serialVersionUID = 1L;
    /** Referencia al gestor de la interfaz gráfica (GUI) */
    private final GestorGUI geGUI;
    /** Referencia al gestor principal de lógica del sistema */
    private final Gestor gestor;
    /** Modelo de datos para la lista de vuelos */
    private DefaultListModel<String> modeloLista;
    /** Componente gráfico que muestra la lista de vuelos */
    private JList<String> listaVuelos;
    /** Campo de texto para ingresar el código del vuelo */
    private JTextField campoCodigo;
    /** Área de texto para mostrar mensajes al usuario */
    private JTextArea textAreaMensajes;
    /**
     * Checkbox para indicar si se desea cambiar el rango de minutos entre vuelos
     */
    private JCheckBox cambiarRangoMinutos;
    /**
     * Checkbox para indicar si se desea cambiar las horas máximas de reprogramación
     */
    private JCheckBox cambiarHorasMaxReprogramacion;
    /**
     * Checkbox para indicar si se desea cambiar el tiempo para aceptar una
     * propuesta
     */
    private JCheckBox cambiarTiempoAceptarPropuesta;
    /**
     * Checkbox para indicar si se desea cambiar los días mínimos de antelación para
     * vuelos
     */
    private JCheckBox cambiarDiasMinAntelacion;
    /** Valor actual del rango mínimo de minutos entre vuelos */
    private int rangoMinutosVuelo;
    /** Valor actual de las horas máximas permitidas para reprogramar un vuelo */
    private int horasMaxReprogramacionVuelo;
    /**
     * Tiempo actual (en minutos) disponible para aceptar una propuesta de
     * reprogramación
     */
    private int tiempoAceptarPropuestaVuelo;
    /** Días mínimos de antelación requeridos para programar un vuelo */
    private int diasAntelacionMinVuelo;

    /**
     * Constructor de la clase GestionVuelosGUI
     * 
     * @param manager   el manager
     * @param gestorGUI el gestorGUI
     * @param gestor    el gestor
     */
    public GestionVueloGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
        this.gestor = gestor;
        this.geGUI = gestorGUI;

        setTitle("Panel de gestión de vuelos del Gestor");
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
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel listaVuelosTitulo = ConfiguracionUI.crearTitulo("Propuestas de vuelo recibidas");
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaVuelos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaVuelos);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = new JButton("🔄 Refrescar vuelos propuestos");
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

        JLabel titulo = ConfiguracionUI.crearTitulo("Gestor: " + gestor.getNombre());
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

        JButton botonGestionar = new JButton("Gestionar vuelo");
        botonGestionar.setMaximumSize(new Dimension(1000, 800));
        botonGestionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(botonGestionar);

        panelDerecho.add(Box.createVerticalGlue());

        textAreaMensajes = new JTextArea(5, 30);
        textAreaMensajes.setEditable(false);
        textAreaMensajes.setLineWrap(true);
        textAreaMensajes.setWrapStyleWord(true);

        JScrollPane scrollPaneMensajes = new JScrollPane(textAreaMensajes);
        scrollPaneMensajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPaneMensajes.setMaximumSize(new Dimension(600, 200));

        JPanel contenedorMensajes = new JPanel();
        contenedorMensajes.setLayout(new BoxLayout(contenedorMensajes, BoxLayout.Y_AXIS));
        contenedorMensajes.setOpaque(false);
        contenedorMensajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorMensajes.add(Box.createRigidArea(new Dimension(0, 10)));
        contenedorMensajes.add(scrollPaneMensajes);
        contenedorMensajes.setVisible(false);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDerecho.add(contenedorMensajes);

        /** Cambiar rangos y tiempos */
        cambiarRangoMinutos = new JCheckBox("Cambiar rango minutos disponibilidad");
        cambiarRangoMinutos.setOpaque(false);
        cambiarRangoMinutos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel rangoMinutos = new JPanel();
        rangoMinutos.setLayout(new BoxLayout(rangoMinutos, BoxLayout.Y_AXIS));
        rangoMinutos.setOpaque(false);
        rangoMinutos.setAlignmentX(Component.CENTER_ALIGNMENT);
        rangoMinutos.setVisible(false);

        JTextField campoRangoMinutos = new JTextField();
        campoRangoMinutos.setMaximumSize(new Dimension(300, 30));
        rangoMinutos.add(campoRangoMinutos);

        cambiarHorasMaxReprogramacion = new JCheckBox("Cambiar horas máximas para la reprogramación");
        cambiarHorasMaxReprogramacion.setOpaque(false);
        cambiarHorasMaxReprogramacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel horasMaxReprogramacion = new JPanel();
        horasMaxReprogramacion.setLayout(new BoxLayout(horasMaxReprogramacion, BoxLayout.Y_AXIS));
        horasMaxReprogramacion.setOpaque(false);
        horasMaxReprogramacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        horasMaxReprogramacion.setVisible(false);

        JTextField campoHorasMaxReprogramacion = new JTextField();
        campoHorasMaxReprogramacion.setMaximumSize(new Dimension(300, 30));
        horasMaxReprogramacion.add(campoHorasMaxReprogramacion);

        cambiarTiempoAceptarPropuesta = new JCheckBox("Cambiar tiempo aceptar propuesta");
        cambiarTiempoAceptarPropuesta.setOpaque(false);
        cambiarTiempoAceptarPropuesta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel tiempoAceptarPropuesta = new JPanel();
        tiempoAceptarPropuesta.setLayout(new BoxLayout(tiempoAceptarPropuesta, BoxLayout.Y_AXIS));
        tiempoAceptarPropuesta.setOpaque(false);
        tiempoAceptarPropuesta.setAlignmentX(Component.CENTER_ALIGNMENT);
        tiempoAceptarPropuesta.setVisible(false);

        JTextField campoTiempoAceptarPropuesta = new JTextField();
        campoTiempoAceptarPropuesta.setMaximumSize(new Dimension(300, 30));
        tiempoAceptarPropuesta.add(campoTiempoAceptarPropuesta);

        cambiarDiasMinAntelacion = new JCheckBox("Cambiar días mínimos de antelación");
        cambiarDiasMinAntelacion.setOpaque(false);
        cambiarDiasMinAntelacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel diasMinAntelacion = new JPanel();
        diasMinAntelacion.setLayout(new BoxLayout(diasMinAntelacion, BoxLayout.Y_AXIS));
        diasMinAntelacion.setOpaque(false);
        diasMinAntelacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        diasMinAntelacion.setVisible(false);

        JTextField campoDiasMinAntelacion = new JTextField();
        campoDiasMinAntelacion.setMaximumSize(new Dimension(300, 30));
        diasMinAntelacion.add(campoDiasMinAntelacion);

        panelDerecho.add(cambiarRangoMinutos);
        panelDerecho.add(rangoMinutos);

        panelDerecho.add(cambiarHorasMaxReprogramacion);
        panelDerecho.add(horasMaxReprogramacion);

        panelDerecho.add(cambiarTiempoAceptarPropuesta);
        panelDerecho.add(tiempoAceptarPropuesta);

        panelDerecho.add(cambiarDiasMinAntelacion);
        panelDerecho.add(diasMinAntelacion);

        cambiarRangoMinutos.addActionListener(e -> {
            rangoMinutos.setVisible(cambiarRangoMinutos.isSelected());
            panelDerecho.revalidate();
            panelDerecho.repaint();
        });

        cambiarHorasMaxReprogramacion.addActionListener(e -> {
            horasMaxReprogramacion.setVisible(cambiarHorasMaxReprogramacion.isSelected());
            panelDerecho.revalidate();
            panelDerecho.repaint();
        });

        cambiarTiempoAceptarPropuesta.addActionListener(e -> {
            tiempoAceptarPropuesta.setVisible(cambiarTiempoAceptarPropuesta.isSelected());
            panelDerecho.revalidate();
            panelDerecho.repaint();
        });

        cambiarDiasMinAntelacion.addActionListener(e -> {
            diasMinAntelacion.setVisible(cambiarDiasMinAntelacion.isSelected());
            panelDerecho.revalidate();
            panelDerecho.repaint();
        });

        /** Acción aceptar */
        botonGestionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                String rangoMinutosTexto = campoRangoMinutos.getText().trim();
                String horasMaxReprogramacionTexto = campoHorasMaxReprogramacion.getText().trim();
                String tiempoAceptarPropuestaTexto = campoTiempoAceptarPropuesta.getText().trim();
                String diasMinAntelacionTexto = campoDiasMinAntelacion.getText().trim();

                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                VueloProgramado vuelo = gestor.buscarVueloPropuestoPorCodigo(codigo);
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "No se encontró ningún vuelo con ese código.");
                    return;
                }
                try {
                    if (cambiarRangoMinutos.isSelected()) {
                        if (rangoMinutosTexto.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Por favor, escribe el rango de minutos que deseas asignar.",
                                    "Campos vacíos",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        rangoMinutosVuelo = Integer.parseInt(rangoMinutosTexto);
                        Gestor.setRangoMinutosMaximoCambioVuelo(rangoMinutosVuelo);
                    }

                    if (cambiarHorasMaxReprogramacion.isSelected()) {
                        if (horasMaxReprogramacionTexto.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Por favor, escribe las horas máximas para la reprogramación del vuelo que deseas asignar.",
                                    "Campos vacíos",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        horasMaxReprogramacionVuelo = Integer.parseInt(horasMaxReprogramacionTexto);
                        Gestor.setHorasMaxReprogramacionVuelo(horasMaxReprogramacionVuelo);
                    }

                    if (cambiarTiempoAceptarPropuesta.isSelected()) {
                        if (tiempoAceptarPropuestaTexto.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Por favor, escribe el tiempo máximo que tiene el operador para aceptar la propuesta.",
                                    "Campos vacíos",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        tiempoAceptarPropuestaVuelo = Integer.parseInt(tiempoAceptarPropuestaTexto);
                        gestor.setTiempoAceptarPropuesta(tiempoAceptarPropuestaVuelo);
                    }

                    if (cambiarDiasMinAntelacion.isSelected()) {
                        if (diasMinAntelacionTexto.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Por favor, escribe los días mínimos de antelación que tiene el operador para proponer un vuelo",
                                    "Campos vacíos",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        diasAntelacionMinVuelo = Integer.parseInt(diasMinAntelacionTexto);
                        Gestor.setAntelacionMinimaDias(diasAntelacionMinVuelo);
                    }

                    gestor.gestionarVuelos(vuelo);
                    String mensaje = gestor.getMensajeGUI();
                    System.out.println(mensaje);
                    textAreaMensajes.setText(mensaje);
                    contenedorMensajes.setVisible(true);
                    contenedorMensajes.revalidate();
                    contenedorMensajes.repaint();
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Vuelo gestionado correctamente.");
                    actualizarLista();

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this,
                            "Por favor, introduce un número válido en los campos.", "Formato inválido",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Error al gestionar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
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
     * Método auxiliar para actualiar la lista de vuelos propuestos
     */
    private void actualizarLista() {
        modeloLista.clear();
        List<VueloProgramado> vuelos = gestor.getVuelosPropuestos();

        if (vuelos.isEmpty()) {
            modeloLista.addElement("No hay vuelos propuestos pendientes de gestionar.");
            return;
        }
        for (VueloProgramado v : vuelos) {
            modeloLista.addElement("Vuelo: " + v.getCodigo() + " | Aerolinea: "
                    + v.getAvion().getAerolinea().getNombre() + " | Fecha/Hora: " + v.getFecha() + "/" + v.getHora());
        }
    }

}
