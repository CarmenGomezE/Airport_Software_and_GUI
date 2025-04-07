package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;

import src.airportmanager.AirportManager;

public class ControladorGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    public ControladorGUI(AirportManager manager) {
        setTitle("Panel de Controlador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setResizable(false);
        setLocationRelativeTo(null);

        /**PANEL PRINCIPAL*/
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /**Visible*/
        setVisible(true);
    }
}