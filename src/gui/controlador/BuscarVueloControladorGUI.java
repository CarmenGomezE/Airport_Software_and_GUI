package src.gui.controlador;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.CriterioBusqueda;
import src.airportmanager.vuelo.Vuelo;
import src.gui.ConfiguracionUI;

/**
 * La clase BuscarVueloControladorGUI representa la ventana del controlador
 * desde la que se realiza la acción de buscar un vuelo.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class BuscarVueloControladorGUI extends JFrame {
    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;
    /** Formateador de date time */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor de la clase BuscarVueloControladorGUI.
     * 
     * @param manager     Aplicación.
     * @param conGUI      Interfaz principal del controlador.
     * @param controlador Controlador de la interfaz.
     */
    public BuscarVueloControladorGUI(AirportManager manager, ControladorGUI conGUI, ControladorAereo controlador) {
        setTitle("Panel de buscar vuelos del Controlador");
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
        JLabel titulo = ConfiguracionUI.crearTitulo("Buscar en la lista de vuelos");

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        /** FILA DE BUSQUEDA */
        JPanel filaBusqueda = new JPanel();
        filaBusqueda.setLayout(new BoxLayout(filaBusqueda, BoxLayout.X_AXIS));
        filaBusqueda.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel labelCriterio = new JLabel("Criterio:");
        labelCriterio.setFont(new Font("Arial", Font.PLAIN, 18));
        filaBusqueda.add(labelCriterio);
        filaBusqueda.add(Box.createRigidArea(new Dimension(10, 0)));

        String[] criterios = { "Código", "Origen", "Destino", "Fecha", "Hora de salida", "Hora de llegada",
                "Terminal" };
        JComboBox<String> comboCriterios = new JComboBox<>(criterios);
        comboCriterios.setFont(new Font("Arial", Font.PLAIN, 16));
        comboCriterios.setMaximumSize(new Dimension(200, 35));
        filaBusqueda.add(comboCriterios);
        filaBusqueda.add(Box.createRigidArea(new Dimension(20, 0)));

        JTextField campoValor = new JTextField();
        campoValor.setFont(new Font("Arial", Font.PLAIN, 16));
        campoValor.setMaximumSize(new Dimension(300, 35));
        filaBusqueda.add(campoValor);
        filaBusqueda.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton botonBuscar = ConfiguracionUI.crearBotonCentrado("🔎 Buscar");

        filaBusqueda.add(botonBuscar);

        panel.add(filaBusqueda);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        /** AREA DE RESULTADOS */
        DefaultListModel<String> modeloResultados = new DefaultListModel<>();
        JList<String> listaResultados = new JList<>(modeloResultados);
        JScrollPane scrollResultados = new JScrollPane(listaResultados);
        panel.add(scrollResultados);

        /** ACCIÓN BOTÓN BUSCAR */
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterioSeleccionado = (String) comboCriterios.getSelectedItem();
                String valor = campoValor.getText().trim();

                if (valor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor para buscar.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    CriterioBusqueda criterio = convertirTextoACriterio(criterioSeleccionado);
                    List<Vuelo> resultados = controlador.buscarVuelo(criterio, valor);

                    modeloResultados.clear();
                    if (resultados.isEmpty()) {
                        modeloResultados.addElement("No se encontraron vuelos.");
                    } else {
                        for (Vuelo v : resultados) {
                            modeloResultados.addElement("Vuelo: " + v + " | Hora: " + v.getHora().format(formatter)
                                    + " | Estado: " + v.getEstado());
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Criterio no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /** Mostrar ventana */
        setVisible(true);
    }

    /**
     * Método que convierte el texto en criterio de búsqueda.
     * 
     * @param texto texto que hay que convertir en criterio de búsqueda
     * @return el criterio de búsqueda
     */
    private CriterioBusqueda convertirTextoACriterio(String texto) {
        switch (texto) {
            case "Código":
                return CriterioBusqueda.CODIGO;
            case "Origen":
                return CriterioBusqueda.ORIGEN;
            case "Destino":
                return CriterioBusqueda.DESTINO;
            case "Fecha":
                return CriterioBusqueda.FECHA;
            case "Hora de salida":
                return CriterioBusqueda.HORA_SALIDA;
            case "Hora de llegada":
                return CriterioBusqueda.HORA_LLEGADA;
            case "Terminal":
                return CriterioBusqueda.TERMINAL;
            default:
                throw new IllegalArgumentException("Criterio no válido: " + texto);
        }
    }
}
