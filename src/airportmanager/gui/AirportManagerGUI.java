package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.*;
import src.airportmanager.usuario.*;

public class AirportManagerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton botonLogin;
    private JButton botonRestablecer;
    private AirportManager airportManager;

    public AirportManagerGUI(AirportManager manager) {
        this.airportManager = manager;
        ConfiguracionUI.applyLookAndFeel(); 

        setTitle("Airport Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); 

        getContentPane().setBackground(ConfiguracionUI.COLOR_FONDO);

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10)); 
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 30));

        /** PANEL IZQUIERDO (Título y avión) */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(400, getHeight())); 
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        int screenHeight = getHeight(); 
        int spaceHeight = screenHeight / 2 - 50; 
        panelIzquierdo.add(Box.createVerticalStrut(spaceHeight));

        JLabel titulo1 = new JLabel("Airport", JLabel.CENTER);  
        titulo1.setFont(ConfiguracionUI.FUENTE_TITULO);
        titulo1.setForeground(Color.BLACK);
        panelIzquierdo.add(titulo1);

        JLabel titulo2 = new JLabel("Manager", JLabel.CENTER);  
        titulo2.setFont(ConfiguracionUI.FUENTE_TITULO);
        titulo2.setForeground(Color.BLACK);
        panelIzquierdo.add(titulo2);
        

        /** PANEL DERECHO (login) */
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridBagLayout());
        panelDerecho.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        
        String[] roles = {"Gestor del aeropuerto", "Controlador aéreo", "Operador de aerolínea"};
        comboBox = new JComboBox<>(roles);
        comboBox.setPreferredSize(new Dimension(400, 60)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(comboBox, gbc);

        usuarioField = new JTextField();
        usuarioField.setPreferredSize(new Dimension(400, 40));
        usuarioField.setVisible(false);  
        gbc.gridy = 1;
        panelDerecho.add(usuarioField, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setFont(ConfiguracionUI.FUENTE_TEXTO);
        gbc.gridy = 2;
        panelDerecho.add(passwordField, gbc);

        botonLogin = new JButton("Login");
        botonLogin.setEnabled(false); 
        botonLogin.setPreferredSize(new Dimension(200, 60));
        gbc.gridy = 3;
        panelDerecho.add(botonLogin, gbc);
        
        botonRestablecer = new JButton("He olvidado mi contraseña");
        botonRestablecer.setEnabled(true); 
        botonRestablecer.setPreferredSize(new Dimension(300, 30));
        botonRestablecer.setBackground(ConfiguracionUI.COLOR_FONDO);
        botonRestablecer.setForeground(Color.WHITE);
        botonRestablecer.setOpaque(true);
        botonRestablecer.setBorderPainted(false);
        gbc.gridy = 4;
        panelDerecho.add(botonRestablecer, gbc);
        botonRestablecer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String role = (String) comboBox.getSelectedItem();
            	String usuario = usuarioField.getText();

                try {
                    if (role != null) {
                        switch (role) {
                            case "Gestor del aeropuerto":
                                Gestor gestor = airportManager.getGestor();
                            	gestor.heOlvidadoContrasena();
                            	JOptionPane.showMessageDialog(AirportManagerGUI.this, "La nueva contraseña es:"+ gestor.getContrasena());
                                break;

                            case "Controlador aéreo":
                                ControladorAereo controlador = (ControladorAereo) airportManager.getGestor().buscarUsuarioPorDni(usuario);
                                controlador.heOlvidadoContrasena();
                                JOptionPane.showMessageDialog(AirportManagerGUI.this, "La nueva contraseña es:"+ controlador.getContrasena());
                                break;

                            case "Operador de aerolínea":
                                Operador operador = (Operador) airportManager.getGestor().buscarUsuarioPorDni(usuario);
                                operador.heOlvidadoContrasena();
                                JOptionPane.showMessageDialog(AirportManagerGUI.this, "La nueva contraseña es:"+ operador.getContrasena());
                                break;

                            default:
                                JOptionPane.showMessageDialog(AirportManagerGUI.this, "Rol no válido.");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AirportManagerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) comboBox.getSelectedItem();
                
                String usuario = usuarioField.getText();
                char[] password = passwordField.getPassword();
                String passwordStr = new String(password); 

                try {
                    if (role != null) {
                        switch (role) {
                            case "Gestor del aeropuerto":
                                Gestor gestor = airportManager.getGestor();
                            	gestor.iniciarSesion(passwordStr);
                            	dispose();
                                new GestorGUI(manager);
                                break;

                            case "Controlador aéreo":
                                ControladorAereo controlador = (ControladorAereo) airportManager.getGestor().buscarUsuarioPorDni(usuario);
                                controlador.iniciarSesion(usuario, passwordStr);
                                dispose();
                                new ControladorGUI(manager);
                                break;

                            case "Operador de aerolínea":
                                Operador operador = (Operador) airportManager.getGestor().buscarUsuarioPorDni(usuario);
                                operador.iniciarSesion(usuario, passwordStr);
                                dispose();
                                new OperadorGUI(manager);
                                break;

                            default:
                                JOptionPane.showMessageDialog(AirportManagerGUI.this, "Rol no válido.");
                        }
                    }
                } catch (UsuarioNoEncontradoException | ContrasenaInvalidaException ex) {
                    JOptionPane.showMessageDialog(AirportManagerGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(panelIzquierdo, BorderLayout.WEST); 
        panel.add(panelDerecho, BorderLayout.CENTER); 

        add(panel);  
    }

    private void validateLogin() {
        String selectedRole = (String) comboBox.getSelectedItem();
        boolean enable = passwordField.getPassword().length > 0; 

        if (!selectedRole.equals("Gestor del aeropuerto")) {
            enable = enable && !usuarioField.getText().isEmpty();
        }

        botonLogin.setEnabled(enable); 
    }    
}
