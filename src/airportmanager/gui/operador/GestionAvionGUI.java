package src.airportmanager.gui.operador;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;

/**
 * La clase GestionAvionGUI representa la ventana del operador desde la que este da de alta
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
     * @param manager				AirportManager.
     * @param operadorGUI			Ventana principal del operador.
     * @param operadorAerolinea		Operador.
     */
    public GestionAvionGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;

        setTitle("Panel de gestión de aviones del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        panelPrincipal.setBackground(ConfiguracionUI.COLOR_FONDO);
        
        /** TÍTULO */
        JLabel titulo = new JLabel("Dar de alta avión para la aerolínea "+operador.getAerolinea().getNombre(), JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        
        JPanel cabecera = new JPanel();
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setBackground(ConfiguracionUI.COLOR_FONDO);
        cabecera.add(titulo);
        cabecera.add(Box.createRigidArea(new Dimension(0, 20)));

        panelPrincipal.add(cabecera, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(7, 2, 20, 30));
        formulario.setBackground(ConfiguracionUI.COLOR_FONDO);

        // Campos
        JTextField campoMarca = crearCampoTexto();
        campoMarca.setPreferredSize(new Dimension(250, 5));
        JTextField campoModelo = crearCampoTexto();
        campoModelo.setPreferredSize(new Dimension(250, 5));
        JTextField campoAlto = crearCampoTexto();
        campoAlto.setPreferredSize(new Dimension(250, 5));
        JTextField campoAncho = crearCampoTexto();
        campoAncho.setPreferredSize(new Dimension(250, 5));
        JTextField campoLargo = crearCampoTexto();
        campoLargo.setPreferredSize(new Dimension(250, 5));
        JTextField campoAutonomia = crearCampoTexto();
        campoAutonomia.setPreferredSize(new Dimension(250, 5));
        JTextField campoAnoCompra = crearCampoTexto();
        campoAnoCompra.setPreferredSize(new Dimension(250, 5));
        JTextField campoFechaRevision = crearCampoTexto();
        campoFechaRevision.setPreferredSize(new Dimension(250, 5));
        JTextField campoMatricula = crearCampoTexto();
        campoMatricula.setPreferredSize(new Dimension(250, 5));

        String[] tipos = { "Pasajeros", "Mercancías" };
        JComboBox<String> tipoAvion = new JComboBox<>(tipos);

        JTextField campoCapacidadPasajeros = crearCampoTexto();
        campoCapacidadPasajeros.setPreferredSize(new Dimension(250, 5));
        JTextField campoCapacidadCarga = crearCampoTexto();
        JCheckBox checkPeligroso = new JCheckBox("¿Mercancía peligrosa?");
        JCheckBox checkTemperatura = new JCheckBox("¿Control de temperatura?");

        formulario.add(crearCampoConLabel("Marca:", campoMarca));
        formulario.add(crearCampoConLabel("Modelo:", campoModelo));
        formulario.add(crearCampoConLabel("Alto (m):", campoAlto));
        formulario.add(crearCampoConLabel("Ancho (m):", campoAncho));
        formulario.add(crearCampoConLabel("Largo (m):", campoLargo));
        formulario.add(crearCampoConLabel("Autonomía (km):", campoAutonomia));
        formulario.add(crearCampoConLabel("Año de compra:", campoAnoCompra));
        formulario.add(crearCampoConLabel("Fecha última revisión (dd/MM/yyyy):", campoFechaRevision));
        formulario.add(crearCampoConLabel("Matrícula:", campoMatricula));
        formulario.add(crearCampoConLabel("Tipo de avión:", tipoAvion));
        
        JPanel panelOpcional = new JPanel();
        panelOpcional.setLayout(new BoxLayout(panelOpcional, BoxLayout.Y_AXIS));
        panelOpcional.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelOpcional.add(crearCampoConLabel("Capacidad máxima de carga (kg):", campoCapacidadCarga));
        panelOpcional.setPreferredSize(new Dimension(250, 100));
        formulario.add(panelOpcional); 
        
        tipoAvion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelOpcional.removeAll();
                if (tipoAvion.getSelectedItem().equals("Pasajeros")) {
                    panelOpcional.add(crearCampoConLabel("Capacidad máxima de pasajeros:", campoCapacidadPasajeros));
                } else {
                    panelOpcional.add(crearCampoConLabel("Capacidad máxima de carga (kg):", campoCapacidadCarga));
                    panelOpcional.add(checkPeligroso, BorderLayout.EAST);
                    panelOpcional.add(checkTemperatura, BorderLayout.EAST);
                }
                panelOpcional.revalidate();
                panelOpcional.repaint();
            }
        });

        JButton botonRegistrar = new JButton("Dar de alta avión");
        botonRegistrar.setFont(new Font("Arial", Font.BOLD, 18));
        botonRegistrar.setBackground(new Color(200, 230, 255));

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

    /** 
     * Método que crea campos con título.
     */
    private JPanel crearCampoConLabel(String textoLabel, JComponent campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        JLabel label = new JLabel(textoLabel);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);
        return panel;
    }
    
    /** 
     * Método que crea campos de texto.
     */
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(200, 30));
        return campo;
    }
}
