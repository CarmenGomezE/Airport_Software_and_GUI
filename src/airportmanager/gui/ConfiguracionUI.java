package src.airportmanager.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ConfiguracionUI {
	
    // Color de fondo azul clarito y azul oscuro para el botón
    public static final Color COLOR_FONDO = new Color(135, 206, 235); // Azul clarito
    public static final Color COLOR_PANEL = new Color(173, 216, 230); // Azul clarito
    public static final Color COLOR_TEXTO = Color.BLACK;
    public static final Color COLOR_INPUT = Color.WHITE;
    public static final Color COLOR_DESPLEGABLE = Color.LIGHT_GRAY;
    public static final Color COLOR_BOTON_LOGIN = new Color(70, 130, 180); // Azul más oscuro para el botón

    public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 80);
    public static final Font FUENTE_SUBTITULO = new Font("Arial", Font.BOLD, 22);
    public static final Font FUENTE_TEXTO = new Font("Verdana", Font.PLAIN, 16);
    public static final Font FUENTE_BOTON = new Font("Tahoma", Font.BOLD, 20);
    public static final Font FUENTE_CAMPOS = new Font("SansSerif", Font.PLAIN, 14);

    // Se quitan los bordes de los campos de texto y comboBox
    public static final Border BORDE_VACIO = BorderFactory.createEmptyBorder();

    // Métodos para crear componentes
    public static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_TEXTO);
        etiqueta.setForeground(COLOR_TEXTO);
        return etiqueta;
    }

    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_BOTON_LOGIN);  // Azul más oscuro para el botón
        boton.setForeground(Color.BLACK);  // Color de texto blanco
        boton.setFocusPainted(false);
        boton.setBorder(BORDE_VACIO);  // Se quita el borde
        return boton;
    }

    public static JTextField crearCampoTexto(int columnas) {
        JTextField campo = new JTextField(columnas);
        campo.setFont(FUENTE_CAMPOS);
        campo.setBackground(COLOR_INPUT);
        campo.setBorder(BORDE_VACIO);  // Se quita el borde
        return campo;
    }

    public static JTextArea crearAreaTexto(int filas, int columnas) {
        JTextArea area = new JTextArea(filas, columnas);
        area.setFont(FUENTE_CAMPOS);
        area.setBackground(COLOR_INPUT);
        area.setBorder(BORDE_VACIO);  // Se quita el borde
        return area;
    }

    public static JPanel crearPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PANEL);  // Azul clarito
        panel.setBorder(BORDE_VACIO);  // Se quita el borde
        return panel;
    }

    public static <T> JComboBox<T> crearComboBox(T[] elementos) {
        JComboBox<T> comboBox = new JComboBox<>(elementos);
        comboBox.setFont(FUENTE_CAMPOS);
        comboBox.setBackground(COLOR_DESPLEGABLE);
        comboBox.setBorder(BORDE_VACIO);  // Se quita el borde
        return comboBox;
    }

    // Para establecer Look and Feel global
    public static void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JLabel crearTitulo(String mensajeTitulo) {
  
    	JLabel titulo = new JLabel(mensajeTitulo, JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        return titulo;
    }
    
    public static JPanel crearCampoConLabel(String textoLabel, JComponent campo) {
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
    
    public static JTextField crearCampoTexto() {
		JTextField campo = new JTextField();
		campo.setPreferredSize(new Dimension(200, 30));
		return campo;
	}
    
    public static JButton crearBotonCentrado(String mensajeBoton) {
    	JButton boton = new JButton(mensajeBoton);
    	boton.setFont(new Font("Arial", Font.BOLD, 16));
    	boton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	return boton;
    }
    
    public static JPanel crearPanelTipoFormulario() {
    	JPanel panel = new JPanel(new GridLayout(7, 2, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        return panel;
    }
    
    public static JPanel crearCabeceraPanel (JLabel titulo) {
    	JPanel cabecera = new JPanel();
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setBackground(ConfiguracionUI.COLOR_FONDO);
        cabecera.add(titulo);
        cabecera.add(Box.createRigidArea(new Dimension(0, 20)));
        return cabecera;
    }
    
    public static JPanel crearPanelPrincipal() {
    	JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        return panel;
    }
    
    public static JCheckBox crearCheckBox(String mensajeCheckBox) {
    	JCheckBox check = new JCheckBox(mensajeCheckBox);
    	check.setBackground(ConfiguracionUI.COLOR_FONDO);
        check.setFont(new Font("Arial", Font.BOLD, 16));
        return check;
    }
	
    
    
}
