package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
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
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.exceptions.UsuarioYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;

import src.airportmanager.usuario.Gestor;

/**
 * La clase GestionUsuariosOperadorGUI representa la ventana de gstión de usuarios (Operador de la aerolínea) del gestor
 */
public class GestionUsuariosOperadorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;

	/**
	 * Constructor de la clase GestionUsuariosOperadorGUI
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
	public GestionUsuariosOperadorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) { 
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
         JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de usuarios: Operador");
         
         JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
         
         JTextField campoNombre = ConfiguracionUI.crearCampoTexto();
         campoNombre.setPreferredSize(new Dimension(250, 5));
         JTextField campoDni =ConfiguracionUI.crearCampoTexto();
         campoDni.setPreferredSize(new Dimension(250, 5));
         JTextField campoAerolinea = ConfiguracionUI.crearCampoTexto();
         campoAerolinea.setPreferredSize(new Dimension(250,5));
         
         formulario.add(ConfiguracionUI.crearCampoConLabel("Nombre:", campoNombre));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Dni:", campoDni));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Aerolínea", campoAerolinea));
         
         JButton botonAgregarControlador = ConfiguracionUI.crearBotonCentrado("Agregar operador de la aerolínea");
            
         botonAgregarControlador.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombre = campoNombre.getText().trim();
				String dni = campoDni.getText().trim();
				String aerolineaNombre = campoAerolinea.getText().trim();
				
				if (nombre.isEmpty() || dni.isEmpty() || aerolineaNombre.isEmpty()) { 

					        JOptionPane.showMessageDialog(null, 
					            "Por favor, rellena todos los campos antes de guardar.", 
					            "Campos vacíos", 
					            JOptionPane.WARNING_MESSAGE);
				return;
			   }
			   
				Aerolinea aerolinea = gestor.buscarAerolineaPorNombre(aerolineaNombre);
				if (aerolinea == null) {
					   JOptionPane.showMessageDialog(null, 
					            "Error, esa aerolínea no existe", 
					            "Aerolínea inexistente", 
					            JOptionPane.WARNING_MESSAGE);
					   return;
				   }
			   try {
				gestor.darDeAltaOperador(nombre, dni, aerolinea);
			} catch (UsuarioYaExisteException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
                        "Error: el operador ya está dado de alta en el sistema.", 
                        "Operador ya existente", 
                        JOptionPane.ERROR_MESSAGE);
			}catch(FormatoDniIncorrectoException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
                        "Error: el dni del operador tiene que tener 8 dígitos y una letra.", 
                        "Formato de dni incorrecto", 
                        JOptionPane.ERROR_MESSAGE);
			}
			
			
			   JOptionPane.showMessageDialog(null, 
					    "¡Operador dado de alta correctamente en la aerolínea "+ aerolinea.getNombre() + "!", 
					    "Éxito", 
					    JOptionPane.INFORMATION_MESSAGE);
			
			}
        	 
         });
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarControlador, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);

	}

}
