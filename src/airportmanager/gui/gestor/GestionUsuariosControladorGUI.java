package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.UsuarioYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.usuario.Gestor;

public class GestionUsuariosControladorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;

	public GestionUsuariosControladorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		 setTitle("Panel de gestión de usuarios (Controlador) del Gestor");
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
	     
	     JPanel panelPrincipal = new JPanel(new BorderLayout());
         panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
         panelPrincipal.setBackground(ConfiguracionUI.COLOR_FONDO);
         
         /** TÍTULO */
         JLabel titulo = new JLabel("Gestión de usuarios: Controlador ", JLabel.CENTER);
         titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
         titulo.setFont(new Font("Arial", Font.BOLD, 28));
         
         JPanel cabecera = new JPanel();
         cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
         cabecera.setBackground(ConfiguracionUI.COLOR_FONDO);
         cabecera.add(titulo);
         cabecera.add(Box.createRigidArea(new Dimension(0, 20)));

         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = new JPanel(new GridLayout(7, 2, 20, 30));
         formulario.setBackground(ConfiguracionUI.COLOR_FONDO);
         
         JTextField campoNombre = crearCampoTexto();
         campoNombre.setPreferredSize(new Dimension(250, 5));
         JTextField campoDni =crearCampoTexto();
         campoDni.setPreferredSize(new Dimension(250, 5));
         JTextField campoContrasena = crearCampoTexto();
         campoContrasena.setPreferredSize(new Dimension(250,5));
         
         JTextField campoTerminal = crearCampoTexto();
         campoTerminal.setPreferredSize(new Dimension(250, 5));
         
         formulario.add(crearCampoConLabel("Nombre:", campoNombre));
         formulario.add(crearCampoConLabel("Dni:", campoDni));
         formulario.add(crearCampoConLabel("Contraseña:", campoContrasena));
         formulario.add(crearCampoConLabel("Terminal:", campoTerminal));
         
         JButton botonAgregarControlador = new JButton("Agregar controlador");
         botonAgregarControlador.setFont(new Font("Arial", Font.BOLD, 16));
         botonAgregarControlador.setAlignmentX(Component.CENTER_ALIGNMENT);
            
         botonAgregarControlador.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombre = campoNombre.getText().trim();
				String dni = campoDni.getText().trim();
				String contrasena = campoContrasena.getText().trim();
				String terminalCodigo = campoDni.getText().trim();

				if (nombre.isEmpty() || dni.isEmpty() || contrasena.isEmpty() || terminalCodigo.isEmpty()) { 

					        JOptionPane.showMessageDialog(null, 
					            "Por favor, rellena todos los campos antes de guardar.", 
					            "Campos vacíos", 
					            JOptionPane.WARNING_MESSAGE);
				return;
			   }
			   Terminal terminal = gestor.buscarTerminalPorCodigo(terminalCodigo);
			   if (terminal == null) {
				   JOptionPane.showMessageDialog(null, 
				            "Error, esa terminal no existe", 
				            "Terminal inexistente", 
				            JOptionPane.WARNING_MESSAGE);
				   return;
			   }
			   
			   try {
				gestor.darDeAltaControlador(nombre, dni, terminal);
			} catch (UsuarioYaExisteException e1) {
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
                        "Error: el controlador ya está dado de alta en el sistema.", 
                        "Controlador ya existente", 
                        JOptionPane.ERROR_MESSAGE);
			}
			   JOptionPane.showMessageDialog(null, 
					    "¡Controlador dado de alta correctamente!", 
					    "Éxito", 
					    JOptionPane.INFORMATION_MESSAGE);
			
			}
        	 
         });
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarControlador, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);

	}
	
	 private JPanel crearCampoConLabel(String textoLabel, JComponent campo) {
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
	
	private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(200, 30));
        return campo;
    }

}
