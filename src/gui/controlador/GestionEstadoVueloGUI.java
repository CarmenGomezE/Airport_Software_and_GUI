package src.gui.controlador;

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Vuelo;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionEstadoVueloGUI representa la ventana del controlador desde la
 * que este puede cambiar
 * los estados de los vuelos que le corresponden.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class GestionEstadoVueloGUI extends JFrame {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /**
     * Contructor de la clase GestionEstadoVueloGUI.
     * 
     * @param manager     Aplicación.
     * @param conGUI      Interfaz principal del controlador.
     * @param controlador Controlador de la interfaz.
     */
    public GestionEstadoVueloGUI(AirportManager manager, ControladorGUI conGUI, ControladorAereo controlador) {
        setTitle("Panel de gestión de estados de vuelo del Controlador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                conGUI.setVisible(true);
            }
        });

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Gestionar el estado de los vuelos");

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        /** CÓDIGO DE VUELO */
        JLabel labelCodigo = new JLabel("Código de vuelo:");
        labelCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
        labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelCodigo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField campoCodigo = new JTextField();
        campoCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoCodigo.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(campoCodigo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        /** NUEVO ESTADO */
        JLabel labelEstado = new JLabel("Nuevo estado:");
        labelEstado.setFont(new Font("Arial", Font.PLAIN, 18));
        labelEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelEstado);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] estados = { "Esperando despegue", "Esperando pista de despegue", "Esperando pista de aterrizaje",
                "Esperando aterrizaje", "Aterrizaje", "Avión al hangar", "Operativo" };
        JComboBox<String> comboEstados = new JComboBox<>(estados);
        comboEstados.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comboEstados.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(comboEstados);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        /** BOTÓN CAMBIAR ESTADO */
        JButton botonCambiar = ConfiguracionUI.crearBotonCentrado("Cambiar estado");

        panel.add(botonCambiar);

        /** ÁREA DE TEXTO */
        JTextArea textAreaMensajes = new JTextArea(5, 30);
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

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(contenedorMensajes);

        botonCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                String estadoSeleccionado = (String) comboEstados.getSelectedItem();

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

                EstadoVuelo nuevoEstado = null;
                try {
                    nuevoEstado = convertirTextoAEstadoVuelo(estadoSeleccionado);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Estado no válido seleccionado.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    controlador.cambiarEstadoVuelo(vuelo, nuevoEstado);
                    String mensaje = controlador.getMensajeGUI();
                    System.out.println(mensaje);
                    textAreaMensajes.setText(mensaje);
                    if (mensaje != null && !mensaje.trim().isEmpty()) {
                        contenedorMensajes.setVisible(true);
                        contenedorMensajes.revalidate();
                        contenedorMensajes.repaint();
                    }
                    JOptionPane.showMessageDialog(null, "Estado cambiado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al cambiar el estado del vuelo: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /** Visible */
        setVisible(true);
    }

    /**
     * Método para convertir un String a la clase EstadoVuelo.
     * 
     * @param texto Texto que se quiere convertir.
     * @return El String convertido a EstadoVuelo.
     */
    private EstadoVuelo convertirTextoAEstadoVuelo(String texto) {
        switch (texto) {
            case "Esperando despegue":
                return EstadoVuelo.ESPERANDO_DESPEGUE;
            case "Esperando pista de despegue":
                return EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE;
            case "Esperando pista de aterrizaje":
                return EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE;
            case "Esperando aterrizaje":
                return EstadoVuelo.ESPERANDO_ATERRIZAJE;
            case "Aterrizaje":
                return EstadoVuelo.ATERRIZAJE;
            case "Avión al hangar":
                return EstadoVuelo.AVION_A_HANGAR;
            case "Operativo":
                return EstadoVuelo.OPERATIVO;
            default:
                throw new IllegalArgumentException("Criterio no válido: " + texto);
        }
    }
}
