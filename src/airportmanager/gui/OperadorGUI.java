package src.airportmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import src.airportmanager.AirportManager;

public class OperadorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy"); 
    
        public OperadorGUI(AirportManager manager) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH); 
            setResizable(false);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setBackground(ConfiguracionUI.COLOR_FONDO); 
            panel.setLayout(new BorderLayout());
            add(panel);

            // Crear los paneles
            JPanel panelIzquierdo = new JPanel();
            panelIzquierdo.setLayout(new BorderLayout());
            panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO); // Fondo de color azul claro
            panelIzquierdo.setPreferredSize(new Dimension(getWidth() / 2, getHeight())); 

            JPanel panelDerecho = new JPanel();
            panelDerecho.setLayout(new GridLayout(6, 1, 10, 10)); // Usamos GridLayout para tener botones bien organizados
            panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO); // Fondo de color azul claro
            panelIzquierdo.setPreferredSize(new Dimension(getWidth() / 2, getHeight())); 
            
            // Crear la etiqueta de lista de vuelos con la fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            String formattedDate = LocalDate.now().format(formatter);
            JLabel listaVuelosTitulo = new JLabel("Lista de vuelos (" + formattedDate + ")", JLabel.CENTER);
            listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 20));
            listaVuelosTitulo.setForeground(Color.BLACK);

            // Agregar la etiqueta al panel izquierdo
            panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

            // Botón con campana
            JButton botonCampana = new JButton("🔔");
            botonCampana.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            botonCampana.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
            
            // Botón Cerrar sesión
            JButton botonCerrarSesion = new JButton("Cerrar sesión");
            botonCerrarSesion.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            botonCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            botonCerrarSesion.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
       
            JButton boton1 = new JButton("Gestión de estados de los vuelos");
            boton1.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            boton1.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            boton1.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
            
            JButton boton2 = new JButton("Gestión de aviones");
            boton2.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            boton2.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            boton2.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
            
            JButton boton3 = new JButton("Programar un vuelo");
            boton3.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            boton3.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            boton3.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
            
            JButton boton4 = new JButton("Compartir un vuelo");
            boton4.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            boton4.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            boton4.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado
     
            JButton botonFinal = new JButton("Estadísticas de vuelos");
            botonFinal.setBackground(ConfiguracionUI.COLOR_BOTON_LOGIN);
            botonFinal.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrar el botón
            botonFinal.setPreferredSize(new Dimension(250, 60));  // Hacer el botón más alargado

            // Añadir los botones al panel
            panelDerecho.add(botonCampana);
            panelDerecho.add(Box.createVerticalStrut(20));  // Espaciado entre los botones
            panelDerecho.add(botonCerrarSesion);
            panelDerecho.add(Box.createVerticalStrut(20));
            panelDerecho.add(boton1);
            panelDerecho.add(Box.createVerticalStrut(20));
            panelDerecho.add(boton2);
            panelDerecho.add(Box.createVerticalStrut(20));
            panelDerecho.add(boton3);
            panelDerecho.add(Box.createVerticalStrut(20));
            panelDerecho.add(boton4);
            panelDerecho.add(Box.createVerticalStrut(20));
            panelDerecho.add(botonFinal);

            add(panelIzquierdo, BorderLayout.WEST);
            add(panelDerecho, BorderLayout.EAST);

            // Configuración básica de la ventana principal
            setTitle("Operador de Aerolínea");
            setSize(800, 600); // Establecer el tamaño de la ventana principal
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); // Centrar la ventana
            setVisible(true);
        }

        public static void main(String[] args) {
            new OperadorGUI(new AirportManager());
        }
    }

