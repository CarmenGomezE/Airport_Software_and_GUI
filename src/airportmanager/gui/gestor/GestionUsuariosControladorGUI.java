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
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.exceptions.UsuarioYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.usuario.Gestor;

/**
 * La clase GestionUsuariosControladorGUI representa la ventana de gestión usuarios (Controlador) del gestor
 */
public class GestionUsuariosControladorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;

	/**
	 * Constructor de la clase GestionUsuariosControladorGUI
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
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
	     
	     JPanel panelPrincipal = ConfiguracionUI.crearPanelPrincipal();
         
         /** TÍTULO */
         JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de usuarios: Controlador");
         
         JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
         
         JTextField campoNombre = ConfiguracionUI.crearCampoTexto();
         campoNombre.setPreferredSize(new Dimension(250, 5));
         JTextField campoDni = ConfiguracionUI.crearCampoTexto();
         campoDni.setPreferredSize(new Dimension(250, 5));
         JTextField campoContrasena = ConfiguracionUI.crearCampoTexto();
         campoContrasena.setPreferredSize(new Dimension(250,5));
         
         JTextField campoTerminal = ConfiguracionUI.crearCampoTexto();
         campoTerminal.setPreferredSize(new Dimension(250, 5));
         
         formulario.add(ConfiguracionUI.crearCampoConLabel("Nombre:", campoNombre));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Dni:", campoDni));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Contraseña:", campoContrasena));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Terminal:", campoTerminal));
         
         JButton botonAgregarControlador = ConfiguracionUI.crearBotonCentrado("Agregar controlador");
            
         botonAgregarControlador.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombre = campoNombre.getText().trim();
				String dni = campoDni.getText().trim();
				String contrasena = campoContrasena.getText().trim();
				String terminalCodigo = campoTerminal.getText().trim();

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
				JOptionPane.showMessageDialog(null, 
				        "¡Controlador dado de alta correctamente!", 
				        "Éxito", 
				        JOptionPane.INFORMATION_MESSAGE);

			} catch (UsuarioYaExisteException e1) {
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
                        "Error: el controlador ya está dado de alta en el sistema.", 
                        "Controlador ya existente", 
                        JOptionPane.ERROR_MESSAGE);
			}catch(FormatoDniIncorrectoException e1) {
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
                        "Error: el dni del controlador tiene que ser de 8 dígitos y una letra.", 
                        "Formato de dni incorrecto", 
                        JOptionPane.ERROR_MESSAGE);
				
			}
			
			}
        	 
         });
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarControlador, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);

	}
	
}
