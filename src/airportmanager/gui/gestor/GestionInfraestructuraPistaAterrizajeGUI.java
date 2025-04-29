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
import src.airportmanager.exceptions.PistaYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.Gestor;

/**
 * La clase GestionInfraestructuraPistaAterrizajeGUI representa la ventana de gestión de infraestructura (Pista de aterrizaje) del gestor 
 */
public class GestionInfraestructuraPistaAterrizajeGUI extends JFrame{
	/** Identificador de versión de clase para la serialización*/
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase GestionInfraestructuraPistaAterrizajeGUI
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
	public GestionInfraestructuraPistaAterrizajeGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		 setTitle("Panel de gestión de infraestructura (Pista de aterrizaje del gestor");
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
         JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Pista de aterrizaje");
         
         JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
         
         JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
         campoCodigo.setPreferredSize(new Dimension(250, 5));
         JTextField campoLongitud = ConfiguracionUI.crearCampoTexto();
         campoLongitud.setPreferredSize(new Dimension(250, 5));
         
         formulario.add(ConfiguracionUI.crearCampoConLabel("Código", campoCodigo));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Longitud", campoLongitud));
         
         JButton botonAgregarPistaAterrizaje = ConfiguracionUI.crearBotonCentrado("Agregar pista de aterrizaje");
         
         botonAgregarPistaAterrizaje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String codigo = campoCodigo.getText().trim();
				String longitudTexto = campoLongitud.getText().trim();
				
				if (codigo.isEmpty() || longitudTexto.isEmpty()) {
					JOptionPane.showMessageDialog(null, 
					        "Por favor, rellena todos los campos antes de guardar.", 
					        "Campos vacíos", 
					        JOptionPane.WARNING_MESSAGE);
					    return;
				}
				
				double longitud = Double.parseDouble(longitudTexto);
				
				try {
					gestor.darDeAltaPista(codigo, longitud, TipoPista.ATERRIZAJE);
					JOptionPane.showMessageDialog(null, 
						    "¡Pista de aterrizaje dada de alta correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);
				} catch (PistaYaExisteException e1) {
					
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, 
	                        "Error: la pista de aterrizaje ya existe.", 
	                        "Pista de aterrizaje ya existente", 
	                        JOptionPane.ERROR_MESSAGE);
				}	
			}
        	 
         });
         
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarPistaAterrizaje, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);
	}
}


