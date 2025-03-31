package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.*;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.UsuarioConDni;

public class AirportManagerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton boton;
    private AirportManager airportManager;

    public AirportManagerGUI(AirportManager manager) {
        this.airportManager = manager;

        setTitle("Airport Manager");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 144, 255)); // Azul
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel etiqueta = new JLabel("Airport Manager");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 40));
        etiqueta.setForeground(Color.BLACK); // Texto negro
        panel.add(etiqueta, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridBagLayout());
        panelDerecho.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        String[] roles = {"Gestor del aeropuerto", "Controlador aéreo", "Operador de aerolínea"};
        comboBox = new JComboBox<>(roles);
        comboBox.setPreferredSize(new Dimension(400, 60));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 24));
        panelDerecho.add(comboBox, gbc);

        gbc.gridy++;
        usuarioField = new JTextField();
        usuarioField.setPreferredSize(new Dimension(400, 40));
        usuarioField.setFont(new Font("Arial", Font.PLAIN, 20));
        usuarioField.setVisible(false);
        panelDerecho.add(usuarioField, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        panelDerecho.add(passwordField, gbc);

        gbc.gridy++;
        boton = new JButton("Login");
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setEnabled(false);
        boton.setPreferredSize(new Dimension(200, 60));
        panelDerecho.add(boton, gbc);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) comboBox.getSelectedItem();
                usuarioField.setVisible(!selectedRole.equals("Gestor del aeropuerto"));
                validateLogin();
                panelDerecho.revalidate();
                panelDerecho.repaint();
            }
        });

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) comboBox.getSelectedItem();
                String usuario = usuarioField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (selectedRole.equals("Gestor del aeropuerto")) {
                        Gestor gestor = airportManager.getGestor();
                        if (gestor != null) {
                            gestor.iniciarSesion(password);
                        }
                    } else {
                        UsuarioConDni user = airportManager.getGestor().buscarUsuarioPorDni(usuario);
                        if (user != null) {
                            user.iniciarSesion(usuario, password); 
                        } else {
                            throw new UsuarioNoEncontradoException("Usuario no encontrado.");
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Bienvenido, " + selectedRole + ".");

                } catch (UsuarioNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ContrasenaInvalidaException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
