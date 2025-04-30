package src.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.AeropuertoYaExisteException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionInfraestructuraAeropuertoDestinoGUI representa la ventana de
 * gestión de la infraestructura: Aeropuerto Destino
 */
public class GestionInfraestructuraAeropuertoDestinoGUI extends JFrame {
    /** Identificador de versión de clase */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase GestionInfraestructuraAeropuertoDestinoGUI
     * 
     * @param manager   el manager
     * @param gestorGUI el gestorGUI
     * @param gestor    el gestor
     */
    public GestionInfraestructuraAeropuertoDestinoGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
        setTitle("Panel de gestión de infraestructura (aeropuerto de destino) del Gestor");
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

        JPanel panel = ConfiguracionUI.crearPanelPrincipal();

        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Aeropuerto Destino");

        JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
        panel.add(cabecera, BorderLayout.NORTH);

        JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

        JTextField campoNombreFichero = ConfiguracionUI.crearCampoTexto();
        campoNombreFichero.setPreferredSize(new Dimension(250, 5));
        formulario.add(ConfiguracionUI.crearCampoConLabel("Nombre fichero", campoNombreFichero));

        JButton botonAgregarAeropuertoDestino = ConfiguracionUI.crearBotonCentrado("Agregar aeropuerto destino");

        botonAgregarAeropuertoDestino.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreFichero = campoNombreFichero.getText().trim();
                if (nombreFichero.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, rellena el nombre del fichero",
                            "Campos vacíos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    gestor.darDeAltaAeropuertoDestino(nombreFichero);
                    JOptionPane.showMessageDialog(null,
                            "¡Aeropuerto de destino dado de alta correctamente!",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error de entrada/salida al procesar el archivo.",
                            "Error IO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (TemporadasSolapadasException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error: hay temporadas solapadas en el aeropuerto.",
                            "Temporadas solapadas",
                            JOptionPane.ERROR_MESSAGE);
                } catch (AeropuertoYaExisteException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error: el aeropuerto de destino ya existe.",
                            "Aeropuerto de destino ya existente",
                            JOptionPane.ERROR_MESSAGE);
                }

                

            }
        });

        panel.add(formulario);
        panel.add(botonAgregarAeropuertoDestino, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);

    }

}