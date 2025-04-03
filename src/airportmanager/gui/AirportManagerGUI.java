package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.airportmanager.AirportManager;

public class AirportManagerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton boton;
    private AirportManager airportManager;

    public AirportManagerGUI(AirportManager manager) {
        this.airportManager = manager;
        ConfiguracionUI.applyLookAndFeel(); 

        setTitle("Airport Manager");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(ConfiguracionUI.COLOR_FONDO); 

        JPanel panel = ConfiguracionUI.crearPanel();

        JLabel etiqueta = new JLabel("Airport Manager");
        etiqueta.setFont(ConfiguracionUI.FUENTE_TITULO);
        etiqueta.setForeground(Color.BLACK);
        Box leftBox = Box.createHorizontalBox();
        leftBox.add(Box.createHorizontalStrut(50)); 
        leftBox.add(etiqueta);
        panel.add(etiqueta, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridBagLayout());
        panelDerecho.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        String[] roles = {"Gestor del aeropuerto", "Controlador aéreo", "Operador de aerolínea"};
        comboBox = ConfiguracionUI.crearComboBox(roles);
        comboBox.setPreferredSize(new Dimension(400, 60));
        panelDerecho.add(comboBox, gbc);

        gbc.gridy++;
        usuarioField = ConfiguracionUI.crearCampoTexto(20);
        usuarioField.setPreferredSize(new Dimension(400, 40));
        usuarioField.setVisible(false);
        panelDerecho.add(usuarioField, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setFont(ConfiguracionUI.FUENTE_TEXTO);
        panelDerecho.add(passwordField, gbc);

        gbc.gridy++;
        boton = ConfiguracionUI.crearBoton("Login");
        boton.setEnabled(false);
        boton.setPreferredSize(new Dimension(200, 60));
        panelDerecho.add(boton, gbc);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) comboBox.getSelectedItem();
                usuarioField.setVisible(!selectedRole.equals("Gestor del aeropuerto"));
                validateLogin();
                panelDerecho.revalidate();
                panelDerecho.repaint();
            }
        });

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        panel.add(panelDerecho, BorderLayout.EAST);
        add(panel);
    }

    private void validateLogin() {
        String selectedRole = (String) comboBox.getSelectedItem();
        boolean enable = !passwordField.getText().isEmpty();

        if (!selectedRole.equals("Gestor del aeropuerto")) {
            enable = enable && !usuarioField.getText().isEmpty();
        }
        boton.setEnabled(enable);
    }
}
