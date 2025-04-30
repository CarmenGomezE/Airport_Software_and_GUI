package src.gui.operador;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import java.util.List;

import src.airportmanager.app.AirportManager;
import src.airportmanager.app.aeropuerto.Aeropuerto;
import src.airportmanager.app.avion.Avion;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;
import src.gui.ConfiguracionUI;

/**
 * La clase ProgramarVueloGUI representa la ventana del operador desde la que
 * este programa
 * vuelos que después el gestor gestiona.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class ProgramarVueloGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;
    /** AirportManager */
    private final AirportManager manager;

    /** Panel de días de la semana */
    private JPanel diasPanel;
    /** ChechBok de dias de las semana */
    private JCheckBox[] diasSemanaChecks;
    /** Panel para las escalas */
    private JPanel escalaPanel;
    /** Combo box para seleccionar el aeropuerto */
    private JComboBox<Aeropuerto> comboEscalaAeropuerto;
    /** Hueco para introducir la hora de llegada de la escala */
    private JTextField campoHoraLlegadaEscala;
    /** Hueco para introducir la hora de salida de la escala */
    private JTextField campoHoraSalidaEscala;

    /**
     * Constructor de la clase ProgramarVueloGUI.
     * 
     * @param AeManager   El objeto AirportManager que gestiona los datos y
     *                    operaciones.
     * @param operadorGUI La interfaz gráfica del operador.
     * @param AeOperador  El operador que gestiona los vuelos.
     */
    public ProgramarVueloGUI(AirportManager AeManager, OperadorGUI operadorGUI, Operador AeOperador) {
        this.opGUI = operadorGUI;
        this.operador = AeOperador;
        this.manager = AeManager;

        setTitle("Panel de programar vuelos del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                opGUI.setVisible(true);
            }
        });

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        JLabel titulo = ConfiguracionUI.crearTitulo("Proponer un vuelo de " + operador.getAerolinea().getNombre());

        panel.add(titulo, BorderLayout.NORTH);

        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.setBackground(ConfiguracionUI.COLOR_FONDO);

        JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
        JTextField campoFecha = ConfiguracionUI.crearCampoTexto();
        JTextField campoFechaInicio = ConfiguracionUI.crearCampoTexto();
        JTextField campoFechaFin = ConfiguracionUI.crearCampoTexto();
        JTextField campoHora = ConfiguracionUI.crearCampoTexto();

        JComboBox<TipoCarga> comboCarga = new JComboBox<>(TipoCarga.values());
        JComboBox<TipoModoEmbarque> comboEmbarque = new JComboBox<>(TipoModoEmbarque.values());
        JComboBox<Periodicidad> comboPeriodicidad = new JComboBox<>(Periodicidad.values());
        JComboBox<TipoVuelo> comboTipo = new JComboBox<>(TipoVuelo.values());

        izquierda.add(ConfiguracionUI.crearCampoConLabel("Código:", campoCodigo));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Fecha (YYYY-MM-DD):", campoFecha));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Fecha Inicio (YYYY-MM-DD):", campoFechaInicio));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Fecha Fin (YYYY-MM-DD):", campoFechaFin));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Hora (HH:mm):", campoHora));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Tipo de Carga:", comboCarga));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Modo Embarque:", comboEmbarque));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Periodicidad:", comboPeriodicidad));
        izquierda.add(ConfiguracionUI.crearCampoConLabel("Tipo de Vuelo:", comboTipo));

        JPanel derecha = new JPanel();
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        derecha.setBackground(ConfiguracionUI.COLOR_FONDO);

        JComboBox<Avion> comboAvion = new JComboBox<>(operador.getAerolinea().getAviones().toArray(new Avion[0]));
        JComboBox<Aeropuerto> comboAeropuerto = new JComboBox<>(
                manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));

        derecha.add(ConfiguracionUI.crearCampoConLabel("Avión:", comboAvion));
        derecha.add(ConfiguracionUI.crearCampoConLabel("Aeropuerto Salida/Llegada:", comboAeropuerto));

        diasPanel = new JPanel(new GridLayout(2, 4));
        diasPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        diasPanel.setBorder(BorderFactory.createTitledBorder("Días Recurrentes"));

        diasSemanaChecks = new JCheckBox[DayOfWeek.values().length];
        for (int i = 0; i < diasSemanaChecks.length; i++) {
            diasSemanaChecks[i] = new JCheckBox(DayOfWeek.of(i + 1).toString());
            diasSemanaChecks[i].setFont(new Font("Arial", Font.PLAIN, 16));
            diasSemanaChecks[i].setEnabled(false);
            diasPanel.add(diasSemanaChecks[i]);
        }
        derecha.add(diasPanel);

        comboPeriodicidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Periodicidad selected = (Periodicidad) comboPeriodicidad.getSelectedItem();
                boolean activar = selected == Periodicidad.RECURRENTE;
                for (JCheckBox check : diasSemanaChecks) {
                    check.setEnabled(activar);
                }
            }
        });

        JCheckBox checkEscala = new JCheckBox("Incluir escala");
        checkEscala.setBackground(ConfiguracionUI.COLOR_FONDO);
        derecha.add(checkEscala);

        escalaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        escalaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        escalaPanel.setBorder(BorderFactory.createTitledBorder("Escala (opcional)"));

        comboEscalaAeropuerto = new JComboBox<>(
                manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));
        campoHoraLlegadaEscala = ConfiguracionUI.crearCampoTexto();
        campoHoraSalidaEscala = ConfiguracionUI.crearCampoTexto();

        escalaPanel.add(new JLabel("Aeropuerto Escala:"));
        escalaPanel.add(comboEscalaAeropuerto);
        escalaPanel.add(new JLabel("Hora Llegada:"));
        escalaPanel.add(campoHoraLlegadaEscala);
        escalaPanel.add(new JLabel("Hora Salida:"));
        escalaPanel.add(campoHoraSalidaEscala);

        for (Component c : escalaPanel.getComponents()) {
            c.setEnabled(false);
        }

        checkEscala.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean activo = checkEscala.isSelected();
                for (Component c : escalaPanel.getComponents()) {
                    c.setEnabled(activo);
                }
            }
        });

        derecha.add(escalaPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, izquierda, derecha);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(30);
        splitPane.setEnabled(false);
        splitPane.setBackground(ConfiguracionUI.COLOR_FONDO);
        splitPane.setBorder(null);
        panel.add(splitPane, BorderLayout.CENTER);

        JButton boton = ConfiguracionUI.crearBotonCentrado("✅ Programar Vuelo");

        panel.add(boton, BorderLayout.SOUTH);

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigo = campoCodigo.getText().trim();
                    LocalDate fecha = LocalDate.parse(campoFecha.getText().trim());
                    LocalDate fechaInicio = LocalDate.parse(campoFechaInicio.getText().trim());
                    LocalDate fechaFin = LocalDate.parse(campoFechaFin.getText().trim());
                    LocalTime hora = LocalTime.parse(campoHora.getText().trim());

                    TipoCarga carga = (TipoCarga) comboCarga.getSelectedItem();
                    TipoModoEmbarque embarque = (TipoModoEmbarque) comboEmbarque.getSelectedItem();
                    Periodicidad periodicidad = (Periodicidad) comboPeriodicidad.getSelectedItem();
                    TipoVuelo tipo = (TipoVuelo) comboTipo.getSelectedItem();
                    Avion avion = (Avion) comboAvion.getSelectedItem();
                    Aeropuerto aeropuerto = (Aeropuerto) comboAeropuerto.getSelectedItem();

                    List<DayOfWeek> diasSeleccionados = null;
                    if (periodicidad == Periodicidad.RECURRENTE) {
                        diasSeleccionados = new ArrayList<>();
                        for (int i = 0; i < diasSemanaChecks.length; i++) {
                            if (diasSemanaChecks[i].isSelected()) {
                                diasSeleccionados.add(DayOfWeek.of(i + 1));
                            }
                        }
                        if (diasSeleccionados.isEmpty()) {
                            JOptionPane.showMessageDialog(ProgramarVueloGUI.this,
                                    "Selecciona al menos un día para los vuelos recurrentes.");
                            return;
                        }
                    }

                    Escala escala = null;
                    if (checkEscala.isSelected()) {
                        Aeropuerto aeEscala = (Aeropuerto) comboEscalaAeropuerto.getSelectedItem();
                        LocalTime horaLlegada = LocalTime.parse(campoHoraLlegadaEscala.getText().trim());
                        LocalTime horaSalida = LocalTime.parse(campoHoraSalidaEscala.getText().trim());
                        escala = new Escala(manager.getAeropuerto(), aeEscala, horaLlegada, horaSalida);
                    }

                    if (tipo == TipoVuelo.LLEGADA) {
                        operador.proponerVuelo(codigo, fecha, fechaInicio, fechaFin, hora, diasSeleccionados, carga,
                                embarque, periodicidad,
                                tipo, avion, manager.getAeropuerto(), aeropuerto, escala);
                    } else {
                        operador.proponerVuelo(codigo, fecha, fechaInicio, fechaFin, hora, diasSeleccionados, carga,
                                embarque, periodicidad,
                                tipo, avion, aeropuerto, manager.getAeropuerto(), escala);
                    }
                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Vuelo propuesto correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this,
                            "Error al programar vuelo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

}
