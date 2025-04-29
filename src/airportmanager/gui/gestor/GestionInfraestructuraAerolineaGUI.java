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
import src.airportmanager.exceptions.AerolineaYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

/**
 * La clase GestioNinfraestructuraAerolineaGUI representa la ventana de gestión de infraestructuras: Aerolínea del gestor.
 */
public class GestionInfraestructuraAerolineaGUI extends JFrame{
	/** Identificador de versión de clase para la serialización*/
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase GestionInfraestructuraAerolineaGUI
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
	public GestionInfraestructuraAerolineaGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		 setTitle("Panel de gestión de infraestructuras (Aerolínea) del Gestor");
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
         JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Aerolinea");
         
         JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
         
         JTextField campoNombre =ConfiguracionUI.crearCampoTexto();
         campoNombre.setPreferredSize(new Dimension(250, 5));
         JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
         campoCodigo.setPreferredSize(new Dimension(250,5));
        
         formulario.add(ConfiguracionUI.crearCampoConLabel("Nombre", campoNombre));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Código", campoCodigo));
         
         JButton botonAgregarAerolinea = ConfiguracionUI.crearBotonCentrado("Agregar aerolínea");
         botonAgregarAerolinea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = campoNombre.getText().trim();
				String codigo = campoCodigo.getText().trim();
				
				if (nombre.isEmpty() || codigo.isEmpty()) {
				    JOptionPane.showMessageDialog(null, 
				        "Por favor, rellena todos los campos antes de guardar.", 
				        "Campos vacíos", 
				        JOptionPane.WARNING_MESSAGE);
				    return;
				}
				
				try {
					gestor.darDeAltaAerolinea(nombre, codigo);
					JOptionPane.showMessageDialog(null, 
						    "¡Aerolínea " + nombre + " dada de alta correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);

				} catch (AerolineaYaExisteException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, 
	                        "Error: la aerolínea ya existe.", 
	                        "Aeroínea ya existente", 
	                        JOptionPane.ERROR_MESSAGE);
				}
			}
        	 
         });
         
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarAerolinea, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);
         
	}
	
}
