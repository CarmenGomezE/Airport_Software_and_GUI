package src.airportmanager.gui.operador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import java.util.List;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.Aeropuerto;
import src.airportmanager.avion.Avion;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;
public class ProgramarVueloGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final OperadorGUI opGUI;
    private final Operador operador;
    private final AirportManager manager;

    private JPanel diasPanel;
    private JCheckBox[] diasSemanaChecks;

    private JPanel escalaPanel;
    private JComboBox<Aeropuerto> comboEscalaAeropuerto;
    private JTextField campoHoraLlegadaEscala;
    private JTextField campoHoraSalidaEscala;

    public ProgramarVueloGUI(AirportManager Aemanager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;
        this.manager = Aemanager;

        setTitle("Panel de programar vuelos del Operador");
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

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        // Título
        JLabel titulo = new JLabel("Proponer un vuelo de " + operador.getAerolinea().getNombre(), JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Panel izquierdo: detalles del vuelo
        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.setBackground(ConfiguracionUI.COLOR_FONDO);
        izquierda.setPreferredSize(new Dimension(650, getHeight())); 
        panel.add(izquierda, BorderLayout.WEST);

        // Campos del vuelo
        JTextField campoCodigo = new JTextField();
        JTextField campoFecha = new JTextField();
        JTextField campoFechaInicio = new JTextField();
        JTextField campoFechaFin = new JTextField();
        JTextField campoHora = new JTextField();

        JComboBox<TipoCarga> comboCarga = new JComboBox<>(TipoCarga.values());
        JComboBox<TipoModoEmbarque> comboEmbarque = new JComboBox<>(TipoModoEmbarque.values());
        JComboBox<Periodicidad> comboPeriodicidad = new JComboBox<>(Periodicidad.values());
        JComboBox<TipoVuelo> comboTipo = new JComboBox<>(TipoVuelo.values());

        // Añadir los campos a la izquierda
        izquierda.add(crearCampoConLabel("Código:", campoCodigo));
        izquierda.add(crearCampoConLabel("Fecha (YYYY-MM-DD):", campoFecha));
        izquierda.add(crearCampoConLabel("Fecha Inicio (YYYY-MM-DD):", campoFechaInicio));
        izquierda.add(crearCampoConLabel("Fecha Fin (YYYY-MM-DD):", campoFechaFin));
        izquierda.add(crearCampoConLabel("Hora (HH:mm):", campoHora));
        izquierda.add(crearCampoConLabel("Tipo de Carga:", comboCarga));
        izquierda.add(crearCampoConLabel("Modo Embarque:", comboEmbarque));
        izquierda.add(crearCampoConLabel("Periodicidad:", comboPeriodicidad));
        izquierda.add(crearCampoConLabel("Tipo de Vuelo:", comboTipo));

        // Panel derecho: información adicional
        JPanel derecha = new JPanel();
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        derecha.setBackground(ConfiguracionUI.COLOR_FONDO);
        derecha.setPreferredSize(new Dimension(650, getHeight())); 
        panel.add(derecha, BorderLayout.EAST);

        // Campos del avión y aeropuerto
        JComboBox<Avion> comboAvion = new JComboBox<>(operador.getAerolinea().getAviones().toArray(new Avion[0]));
        JComboBox<Aeropuerto> comboDestino = new JComboBox<>(manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));

        // Añadir los campos de avión y aeropuerto
        derecha.add(crearCampoConLabel("Avión:", comboAvion));
        derecha.add(crearCampoConLabel("Aeropuerto Destino:", comboDestino));

        // Panel para días recurrentes
        diasPanel = new JPanel(new GridLayout(2, 4));
        diasPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        diasPanel.setBorder(BorderFactory.createTitledBorder("Días Recurrentes"));
        diasSemanaChecks = new JCheckBox[DayOfWeek.values().length];
        for (int i = 0; i < diasSemanaChecks.length; i++) {
            diasSemanaChecks[i] = new JCheckBox(DayOfWeek.of(i + 1).toString());
            diasSemanaChecks[i].setFont(new Font("Arial", Font.PLAIN, 16));
            diasPanel.add(diasSemanaChecks[i]);
        }
        diasPanel.setVisible(false);
        derecha.add(diasPanel);

        // Mostrar días recurrentes solo si es necesario
        comboPeriodicidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodicidad selected = (Periodicidad) comboPeriodicidad.getSelectedItem();
                diasPanel.setVisible(selected == Periodicidad.RECURRENTE);
            }
        });

        // CheckBox para escala
        JCheckBox checkEscala = new JCheckBox("Incluir escala");
        checkEscala.setBackground(ConfiguracionUI.COLOR_FONDO);
        derecha.add(checkEscala);

        // Panel de escala
        escalaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        escalaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        escalaPanel.setBorder(BorderFactory.createTitledBorder("Escala (opcional)"));

        comboEscalaAeropuerto = new JComboBox<>(manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));
        campoHoraLlegadaEscala = new JTextField("13:00");
        campoHoraSalidaEscala = new JTextField("14:30");

        // Añadir los campos de escala
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
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean activo = checkEscala.isSelected();
                for (Component c : escalaPanel.getComponents()) {
                    c.setEnabled(activo);
                }
            }
        });

        derecha.add(escalaPanel);

        // Botón para programar el vuelo
        JButton boton = new JButton("✅ Programar Vuelo");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(boton, BorderLayout.SOUTH);

        // Acción del botón
        boton.addActionListener(new ActionListener() {
            @Override
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
                    Aeropuerto destino = (Aeropuerto) comboDestino.getSelectedItem();

                    List<DayOfWeek> diasSeleccionados = null;
                    if (periodicidad == Periodicidad.RECURRENTE) {
                        diasSeleccionados = new ArrayList<DayOfWeek>();
                        for (int i = 0; i < diasSemanaChecks.length; i++) {
                            if (diasSemanaChecks[i].isSelected()) {
                                diasSeleccionados.add(DayOfWeek.of(i + 1));
                            }
                        }
                        if (diasSeleccionados.isEmpty()) {
                            JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Selecciona al menos un día para los vuelos recurrentes.");
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

                    operador.proponerVuelo(codigo, fecha, fechaInicio, fechaFin, hora,
                            diasSeleccionados, carga, embarque, periodicidad, tipo,
                            avion, destino, manager.getAeropuerto(), escala);

                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Vuelo propuesto correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Error al programar vuelo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private JPanel crearCampoConLabel(String textoLabel, JComponent campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        JLabel label = new JLabel(textoLabel);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(campo);
        return panel;
    }
}