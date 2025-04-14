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

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);
        
        JLabel titulo = new JLabel("Proponer un vuelo de " + operador.getAerolinea().getNombre(), JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel izquierda = new JPanel(new GridLayout(22, 1, 10, 10));
        izquierda.setBackground(ConfiguracionUI.COLOR_FONDO);

        JTextField campoCodigo = new JTextField();
        JTextField campoFecha = new JTextField();
        JTextField campoFechaInicio = new JTextField();
        JTextField campoFechaFin = new JTextField();
        JTextField campoHora = new JTextField();

        JComboBox<TipoCarga> comboCarga = new JComboBox<>(TipoCarga.values());
        JComboBox<TipoModoEmbarque> comboEmbarque = new JComboBox<>(TipoModoEmbarque.values());
        JComboBox<Periodicidad> comboPeriodicidad = new JComboBox<>(Periodicidad.values());
        JComboBox<TipoVuelo> comboTipo = new JComboBox<>(TipoVuelo.values());

        Font labelFont = new Font("Arial", Font.PLAIN, 20);

        izquierda.add(new JLabel("Código:")).setFont(labelFont);
        izquierda.add(campoCodigo);
        izquierda.add(new JLabel("Fecha (YYYY-MM-DD):")).setFont(labelFont);
        izquierda.add(campoFecha);
        izquierda.add(new JLabel("Fecha Inicio (YYYY-MM-DD):")).setFont(labelFont);
        izquierda.add(campoFechaInicio);
        izquierda.add(new JLabel("Fecha Fin (YYYY-MM-DD):")).setFont(labelFont);
        izquierda.add(campoFechaFin);
        izquierda.add(new JLabel("Hora (HH:mm):")).setFont(labelFont);
        izquierda.add(campoHora);
        izquierda.add(new JLabel("Tipo de Carga:")).setFont(labelFont);
        izquierda.add(comboCarga);
        izquierda.add(new JLabel("Modo Embarque:")).setFont(labelFont);
        izquierda.add(comboEmbarque);
        izquierda.add(new JLabel("Periodicidad:")).setFont(labelFont);
        izquierda.add(comboPeriodicidad);
        izquierda.add(new JLabel("Tipo de Vuelo:")).setFont(labelFont);
        izquierda.add(comboTipo);

        JPanel derecha = new JPanel(new GridLayout(10, 1, 10, 10));
        derecha.setBackground(ConfiguracionUI.COLOR_FONDO);

        JComboBox<Avion> comboAvion = new JComboBox<>(operador.getAerolinea().getAviones().toArray(new Avion[0]));
        JComboBox<Aeropuerto> comboDestino = new JComboBox<>(manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));
        Aeropuerto aeropuertoSalida = operador.getAeropuerto();

        JLabel avion = new JLabel("Avión:");
        avion.setFont(labelFont);
        derecha.add(avion); 
        derecha.add(comboAvion);
        JLabel ae = new JLabel("Aeropuerto Destino:");
        ae.setFont(labelFont);
        derecha.add(ae); 
        derecha.add(comboDestino);

        diasPanel = new JPanel(new GridLayout(2, 4));
        diasPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        diasPanel.setBorder(BorderFactory.createTitledBorder("Días Recurrentes"));
        diasSemanaChecks = new JCheckBox[DayOfWeek.values().length];
        for (int i = 0; i < diasSemanaChecks.length; i++) {
            diasSemanaChecks[i] = new JCheckBox(DayOfWeek.of(i + 1).toString());
            diasSemanaChecks[i].setFont(labelFont);
            diasPanel.add(diasSemanaChecks[i]);
        }
        diasPanel.setVisible(false);
        derecha.add(diasPanel);

        comboPeriodicidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selected = comboPeriodicidad.getSelectedItem();
                if (selected instanceof Periodicidad) {
                    diasPanel.setVisible(((Periodicidad) selected) == Periodicidad.RECURRENTE);
                }
            }
        });

        JCheckBox checkEscala = new JCheckBox("Incluir escala");
        checkEscala.setBackground(ConfiguracionUI.COLOR_FONDO);
        checkEscala.setFont(labelFont);
        derecha.add(checkEscala);

        escalaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        escalaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        escalaPanel.setBorder(BorderFactory.createTitledBorder("Escala (opcional)"));

        comboEscalaAeropuerto = new JComboBox<>(manager.getAeropuerto().getAeropuertosDestino().toArray(new Aeropuerto[0]));
        comboEscalaAeropuerto.setFont(labelFont);

        campoHoraLlegadaEscala = new JTextField("13:00");
        campoHoraLlegadaEscala.setFont(labelFont);

        campoHoraSalidaEscala = new JTextField("14:30");
        campoHoraSalidaEscala.setFont(labelFont);

        escalaPanel.add(new JLabel("Aeropuerto Escala:")).setFont(labelFont);
        escalaPanel.add(comboEscalaAeropuerto);
        escalaPanel.add(new JLabel("Hora Llegada:")).setFont(labelFont);
        escalaPanel.add(campoHoraLlegadaEscala);
        escalaPanel.add(new JLabel("Hora Salida:")).setFont(labelFont);
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

        JButton boton = new JButton("✅ Programar Vuelo");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(boton, BorderLayout.SOUTH);
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha, BorderLayout.EAST);

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
                        escala = new Escala(aeropuertoSalida, aeEscala, horaLlegada, horaSalida);
                    }

                    operador.proponerVuelo(codigo, fecha, fechaInicio, fechaFin, hora,
                            diasSeleccionados, carga, embarque, periodicidad, tipo,
                            avion, destino, aeropuertoSalida, escala);

                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Vuelo propuesto correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ProgramarVueloGUI.this, "Error al programar vuelo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
