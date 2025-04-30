package src.gui.operador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.Vuelo;
import src.gui.ConfiguracionUI;

/**
 * La clase CompartirVueloGUI representa la ventana del operador desde la que
 * este solicita
 * compartir vuelos con otras aerolíneas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class CompartirVueloGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;

    /**
     * Constructor de la interfaz gráfica CompartirVueloGUI.
     * 
     * @param manager           El objeto AirportManager que gestiona los datos y
     *                          operaciones.
     * @param operadorGUI       La interfaz gráfica del operador.
     * @param operadorAerolinea El operador que gestiona los vuelos.
     */
    public CompartirVueloGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;

        setTitle("Panel compartir vuelos del Operador");
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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Solicitar compartir un vuelo");

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        /** CÓDIGO DE VUELO */
        JLabel labelCodigo = new JLabel("Código de vuelo:");
        labelCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
        labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelCodigo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField campoCodigo1 = new JTextField();
        campoCodigo1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoCodigo1.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(campoCodigo1);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        /** PORCENTAJE */
        JLabel labelEstado = new JLabel("Porcentaje de carga que se solicita:");
        labelEstado.setFont(new Font("Arial", Font.PLAIN, 18));
        labelEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelEstado);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField campoCodigo2 = new JTextField();
        campoCodigo2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoCodigo2.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(campoCodigo2);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        /** BOTÓN CAMBIAR ESTADO */
        JButton botonCambiar = ConfiguracionUI.crearBotonCentrado("Solicitar compartir el vuelo");

        panel.add(botonCambiar);

        botonCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo1.getText().trim();
                double porcentaje = 0;

                try {
                    porcentaje = Double.parseDouble(campoCodigo2.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido.", "Error de formato",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un código de vuelo.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Vuelo vuelo = manager.buscarVueloPorCodigo(codigo);
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un vuelo con ese código.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    operador.solicitarCompartirVuelo(vuelo, vuelo.getOperadorPrincipal(), porcentaje);
                    JOptionPane.showMessageDialog(null, "Estado cambiado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al cambiar el estado del vuelo: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /** Mostrar ventana */
        setVisible(true);
    }
}
