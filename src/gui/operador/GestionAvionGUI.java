package src.gui.operador;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.Operador;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionAvionGUI representa la ventana del operador desde la que este
 * da de alta
 * nuevos aviones para su aerolínea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class GestionAvionGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;

    /**
     * Constructor de la ventana para gestionar los estados de los vuelos.
     * 
     * @param manager           AirportManager.
     * @param operadorGUI       Ventana principal del operador.
     * @param operadorAerolinea Operador.
     */
    public GestionAvionGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;

        setTitle("Panel de gestión de aviones del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = ConfiguracionUI.crearPanelPrincipal();

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI
                .crearTitulo("Dar de alta avión para la aerolínea " + operador.getAerolinea().getNombre());

        JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);

        JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

        JTextField campoMarca = ConfiguracionUI.crearCampoTexto();
        campoMarca.setPreferredSize(new Dimension(250, 5));
        JTextField campoModelo = ConfiguracionUI.crearCampoTexto();
        campoModelo.setPreferredSize(new Dimension(250, 5));
        JTextField campoAlto = ConfiguracionUI.crearCampoTexto();
        campoAlto.setPreferredSize(new Dimension(250, 5));
        JTextField campoAncho = ConfiguracionUI.crearCampoTexto();
        campoAncho.setPreferredSize(new Dimension(250, 5));
        JTextField campoLargo = ConfiguracionUI.crearCampoTexto();
        campoLargo.setPreferredSize(new Dimension(250, 5));
        JTextField campoAutonomia = ConfiguracionUI.crearCampoTexto();
        campoAutonomia.setPreferredSize(new Dimension(250, 5));
        JTextField campoAnoCompra = ConfiguracionUI.crearCampoTexto();
        campoAnoCompra.setPreferredSize(new Dimension(250, 5));
        JTextField campoFechaRevision = ConfiguracionUI.crearCampoTexto();
        campoFechaRevision.setPreferredSize(new Dimension(250, 5));
        JTextField campoMatricula = ConfiguracionUI.crearCampoTexto();
        campoMatricula.setPreferredSize(new Dimension(250, 5));

        String[] tipos = { "Pasajeros", "Mercancías" };
        JComboBox<String> tipoAvion = new JComboBox<>(tipos);

        JTextField campoCapacidadPasajeros = ConfiguracionUI.crearCampoTexto();
        campoCapacidadPasajeros.setPreferredSize(new Dimension(250, 5));
        JTextField campoCapacidadCarga = ConfiguracionUI.crearCampoTexto();
        JCheckBox checkPeligroso = new JCheckBox("¿Mercancía peligrosa?");
        JCheckBox checkTemperatura = new JCheckBox("¿Control de temperatura?");

        formulario.add(ConfiguracionUI.crearCampoConLabel("Marca:", campoMarca));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Modelo:", campoModelo));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Alto (m):", campoAlto));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Ancho (m):", campoAncho));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Largo (m):", campoLargo));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Autonomía (km):", campoAutonomia));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Año de compra:", campoAnoCompra));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Fecha última revisión (dd/MM/yyyy):", campoFechaRevision));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Matrícula:", campoMatricula));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Tipo de avión:", tipoAvion));

        JPanel panelOpcional = new JPanel();
        panelOpcional.setLayout(new BoxLayout(panelOpcional, BoxLayout.Y_AXIS));
        panelOpcional.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelOpcional.add(ConfiguracionUI.crearCampoConLabel("Capacidad máxima de carga (kg):", campoCapacidadCarga));
        panelOpcional.setPreferredSize(new Dimension(250, 100));
        formulario.add(panelOpcional);

        tipoAvion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelOpcional.removeAll();
                if (tipoAvion.getSelectedItem().equals("Pasajeros")) {
                    panelOpcional.add(ConfiguracionUI.crearCampoConLabel("Capacidad máxima de pasajeros:",
                            campoCapacidadPasajeros));
                } else {
                    panelOpcional.add(
                            ConfiguracionUI.crearCampoConLabel("Capacidad máxima de carga (kg):", campoCapacidadCarga));
                    panelOpcional.add(checkPeligroso, BorderLayout.EAST);
                    panelOpcional.add(checkTemperatura, BorderLayout.EAST);
                }
                panelOpcional.revalidate();
                panelOpcional.repaint();
            }
        });

        JButton botonRegistrar = ConfiguracionUI.crearBotonCentrado("Dar de alta avión");

        botonRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String marca = campoMarca.getText().trim();
                    String modelo = campoModelo.getText().trim();
                    double alto = Double.parseDouble(campoAlto.getText().trim());
                    double ancho = Double.parseDouble(campoAncho.getText().trim());
                    double largo = Double.parseDouble(campoLargo.getText().trim());
                    double autonomia = Double.parseDouble(campoAutonomia.getText().trim());
                    int anoCompra = Integer.parseInt(campoAnoCompra.getText().trim());
                    Date fechaRevision = new SimpleDateFormat("dd/MM/yyyy").parse(campoFechaRevision.getText().trim());
                    String matricula = campoMatricula.getText().trim();

                    if (tipoAvion.getSelectedItem().equals("Pasajeros")) {
                        int capacidad = Integer.parseInt(campoCapacidadPasajeros.getText().trim());
                        operador.darDeAltaAvionPasajeros(marca, modelo, alto, ancho, largo, autonomia,
                                anoCompra, fechaRevision, matricula, capacidad);
                    } else {
                        double carga = Double.parseDouble(campoCapacidadCarga.getText().trim());
                        boolean peligrosa = checkPeligroso.isSelected();
                        boolean temperatura = checkTemperatura.isSelected();
                        operador.darDeAltaAvionMercancias(marca, modelo, alto, ancho, largo, autonomia,
                                anoCompra, fechaRevision, matricula, carga, peligrosa, temperatura);
                    }

                    JOptionPane.showMessageDialog(null, "Avión registrado correctamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en formato numérico.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Fecha inválida. Usa formato dd/MM/yyyy.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(formulario);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(botonRegistrar, BorderLayout.SOUTH);

        add(panelPrincipal);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                opGUI.setVisible(true);
            }
        });

        setVisible(true);
    }

}
