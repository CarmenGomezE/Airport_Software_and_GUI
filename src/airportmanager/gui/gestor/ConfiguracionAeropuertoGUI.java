package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.aeropuerto.Temporada;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

/**
 * La clase ConfiguracionAeropuertoGUI representa la ventana de la configuración del aeropuerto del gestor.
 */
public class ConfiguracionAeropuertoGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final GestorGUI gestorGUI;
    private final Gestor gestor;

    /**
     * Constructor de la clase ConfiguracionAeropuertoGUI
     * @param manager el manager
     * @param gestorGUI el gestorGUI
     * @param gestor el gestor
     */
    public ConfiguracionAeropuertoGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
        this.gestor = gestor;
        this.gestorGUI = gestorGUI;

        setTitle("Panel de configuración del aeropuerto del Gestor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                gestorGUI.setVisible(true);
            }
        });

        JPanel panelPrincipal = ConfiguracionUI.crearPanelPrincipal();
        JLabel titulo = ConfiguracionUI.crearTitulo("Configuración del aeropuerto");

        JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
        panelPrincipal.add(cabecera, BorderLayout.NORTH);

        JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

        JTextField campoNombre = ConfiguracionUI.crearCampoTexto();
        JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
        JTextField campoCiudad = ConfiguracionUI.crearCampoTexto();
        JTextField campoPais = ConfiguracionUI.crearCampoTexto();
        JTextField campoDistancia = ConfiguracionUI.crearCampoTexto();
        JTextField campoDireccion = ConfiguracionUI.crearCampoTexto();
        JTextField campoDiferencia = ConfiguracionUI.crearCampoTexto();

        formulario.add(ConfiguracionUI.crearCampoConLabel("Nombre", campoNombre));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Código", campoCodigo));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Ciudad más cercana:", campoCiudad));
        formulario.add(ConfiguracionUI.crearCampoConLabel("País", campoPais));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Distancia a la ciudad más cercana", campoDistancia));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Dirección relativa a la ciudad", campoDireccion));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Diferencia horaria", campoDiferencia));

        JCheckBox checkTemporada = new JCheckBox("Agregar temporada");
        formulario.add(checkTemporada);

        JScrollPane scroll = new JScrollPane(formulario);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Panel para los campos de temporada (inicialmente oculto)
        JTextField campoInicio = ConfiguracionUI.crearCampoTexto();
        JTextField campoFin = ConfiguracionUI.crearCampoTexto();
        JTextField campoApertura = ConfiguracionUI.crearCampoTexto();
        JTextField campoCierre = ConfiguracionUI.crearCampoTexto();

        JPanel temporadaPanel = new JPanel();
        temporadaPanel.setLayout(new BoxLayout(temporadaPanel, BoxLayout.Y_AXIS));
        temporadaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        temporadaPanel.setVisible(false);
        temporadaPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        temporadaPanel.add(ConfiguracionUI.crearCampoConLabel("Fecha de inicio (YYYY-MM-DD)", campoInicio));
        temporadaPanel.add(ConfiguracionUI.crearCampoConLabel("Fecha de fin (YYYY-MM-DD)", campoFin));
        temporadaPanel.add(ConfiguracionUI.crearCampoConLabel("Horario de apertura (HH:mm)", campoApertura));
        temporadaPanel.add(ConfiguracionUI.crearCampoConLabel("Horario de cierre (HH:mm)", campoCierre));

        checkTemporada.addActionListener(e -> {
            temporadaPanel.setVisible(checkTemporada.isSelected());
            temporadaPanel.revalidate();
            temporadaPanel.repaint();
        });

        JButton botonGuardar = ConfiguracionUI.crearBotonCentrado("Guardar");

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim();
                String codigo = campoCodigo.getText().trim();
                String ciudad = campoCiudad.getText().trim();
                String pais = campoPais.getText().trim();
                String distanciaTexto = campoDistancia.getText().trim();
                String direccionTexto = campoDireccion.getText().trim();
                String diferenciaTexto = campoDiferencia.getText().trim();

                if (nombre.isEmpty() || codigo.isEmpty() || ciudad.isEmpty() || distanciaTexto.isEmpty() ||
                        direccionTexto.isEmpty() || diferenciaTexto.isEmpty() || pais.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, rellena todos los campos antes de guardar.",
                            "Campos vacíos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    double distancia = Double.parseDouble(distanciaTexto);
                    DireccionRelativaCiudad direccion;

                    switch (direccionTexto.toLowerCase()) {
                        case "norte":
                            direccion = DireccionRelativaCiudad.NORTE;
                            break;
                        case "sur":
                            direccion = DireccionRelativaCiudad.SUR;
                            break;
                        case "este":
                            direccion = DireccionRelativaCiudad.ESTE;
                            break;
                        case "oeste":
                            direccion = DireccionRelativaCiudad.OESTE;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,
                                    "Dirección inválida. Debe ser: Norte, Sur, Este u Oeste.",
                                    "Error en Dirección",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                    }

                    int horas = Integer.parseInt(diferenciaTexto);
                    ZoneOffset diferencia = ZoneOffset.ofHours(horas);

                    gestor.configurarAeropuertoPropioNombre(nombre);
                    gestor.configurarAeropuertoPropioCodigo(codigo);
                    gestor.configurarAeropuertoPropioCiudad(ciudad);
                    gestor.configurarAeropuertoPropioPais(pais);
                    gestor.configurarAeropuertoPropioDistanciaCiudad(distancia);
                    gestor.configurarAeropuertoPropioDireccionCiudad(direccion);
                    gestor.configurarAeropuertoPropioDiferenciaHoraria(diferencia);

                    if (checkTemporada.isSelected()) {
                        String fechaInicio = campoInicio.getText().trim();
                        String fechaFin = campoFin.getText().trim();
                        String horarioApertura = campoApertura.getText().trim();
                        String horarioCierre = campoCierre.getText().trim();
                        List<Temporada> temporadas = new ArrayList<>();

                        Temporada nuevaTemporada = new Temporada(fechaInicio, fechaFin, horarioApertura, horarioCierre);
                        temporadas.add(nuevaTemporada);

                        gestor.configurarAeropuertoPropioNumTemporadas(gestor.getAeropuerto().getNumTemporadas() + 1);
                        gestor.configurarAeropuertoPropioTemporadas(temporadas);
                    }

                    JOptionPane.showMessageDialog(null,
                            "¡Configuración guardada correctamente!",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException | DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, introduce valores válidos. Revisa números y fechas (YYYY-MM-DD, HH:mm).",
                            "Error de formato",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel centroConTemporada = new JPanel(new BorderLayout());
        centroConTemporada.setBackground(ConfiguracionUI.COLOR_FONDO);
        centroConTemporada.add(scroll, BorderLayout.CENTER);
        centroConTemporada.add(temporadaPanel, BorderLayout.SOUTH);

        panelPrincipal.add(centroConTemporada, BorderLayout.CENTER);
        panelPrincipal.add(botonGuardar, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }
}

