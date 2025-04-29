package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.usuario.Gestor;

/**
 * La clase GestionInfraestructuraFingerGUI representa la ventana de gestión de infraestructura (Finger) del gestor
 */
public class GestionInfraestructurasFingerGUI extends JFrame{
	/** Identificador de versión de clase */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la clase GestionInfraestructuraFingerGUI 
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
	public GestionInfraestructurasFingerGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		 setTitle("Panel de gestión de infraestructuras (Finger) del Gestor");
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
         JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Finger");
         
         JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
         panelPrincipal.add(cabecera, BorderLayout.NORTH);
         
         JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
         
         JTextField campoAltura = ConfiguracionUI.crearCampoTexto();
         campoAltura.setPreferredSize(new Dimension(250, 5));
         JTextField campoCoste = ConfiguracionUI.crearCampoTexto();
         campoCoste.setPreferredSize(new Dimension(250,5));
         JTextField campoPuertasEmbarque = ConfiguracionUI.crearCampoTexto();
         campoPuertasEmbarque.setPreferredSize(new Dimension(250,5));
        
         formulario.add(ConfiguracionUI.crearCampoConLabel("Altura máxima del avión", campoAltura));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Coste por hora de uso", campoCoste));
         formulario.add(ConfiguracionUI.crearCampoConLabel("Puertas de embarque", campoPuertasEmbarque)); 
         
         JButton botonAgregarFinger = ConfiguracionUI.crearBotonCentrado("Agregar finger");
         botonAgregarFinger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				String alturaTexto = campoAltura.getText().trim();
				String costeTexto = campoCoste.getText().trim();
				String puertasTexto = campoPuertasEmbarque.getText().trim();

				if (alturaTexto.isEmpty() || costeTexto.isEmpty() || puertasTexto.isEmpty()) {
				    JOptionPane.showMessageDialog(null, 
				        "Por favor, rellena todos los campos antes de guardar.", 
				        "Campos vacíos", 
				        JOptionPane.WARNING_MESSAGE);
				    return;
				}

				try {
				    double altura = Double.parseDouble(alturaTexto);
				    double coste = Double.parseDouble(costeTexto);

				    String[] partes = puertasTexto.split(",");
				    List<PuertaEmbarque> puertas = new ArrayList<>();

				    for (String parte : partes) {
				        parte = parte.trim();
				        if (parte.matches("P\\d+")) {
				            int id = Integer.parseInt(parte.substring(1));
				            PuertaEmbarque puerta = gestor.obtenerPuertaEmbarquePorId(id);
				            if (puerta != null) {
				                puertas.add(puerta);
				            } else {
				                JOptionPane.showMessageDialog(null, 
				                    "No se encontró la puerta de embarque con ID: " + id, 
				                    "Error de datos", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, 
				                "Formato inválido en puertas de embarque. Usa el formato 'P1, P2, ...'", 
				                "Error de formato", 
				                JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				    }

				    gestor.darDeAltaFinger(altura, coste, puertas);

				} catch (NumberFormatException e1) {
				    JOptionPane.showMessageDialog(null, 
				        "Altura o coste no tienen un formato válido.", 
				        "Error de formato numérico", 
				        JOptionPane.ERROR_MESSAGE);
				}
				
				 JOptionPane.showMessageDialog(null, 
						    "¡Finger dado de alta correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);

			}
        	 
         });
         
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarFinger, BorderLayout.SOUTH);
        
         add(panelPrincipal);
         setVisible(true);
		
	}
}
