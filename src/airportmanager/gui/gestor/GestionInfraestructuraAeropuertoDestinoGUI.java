package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import src.airportmanager.exceptions.AeropuertoYaExisteException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class GestionInfraestructuraAeropuertoDestinoGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	
	public GestionInfraestructuraAeropuertoDestinoGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		setTitle("Panel de gestión de infraestructura (aeropuerto de destino) del Gestor");
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
	    
	    JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        
        /** TÍTULO */
        JLabel titulo = new JLabel("Gestión infraestructura: Aeropuerto de destino", JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        
        JPanel cabecera = new JPanel();
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.setBackground(ConfiguracionUI.COLOR_FONDO);
        cabecera.add(titulo);
        cabecera.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(cabecera, BorderLayout.NORTH);
        
        JPanel formulario = new JPanel(new GridLayout(7, 2, 20, 30));
        formulario.setBackground(ConfiguracionUI.COLOR_FONDO);
        
        JTextField campoNombreFichero = crearCampoTexto();
        campoNombreFichero.setPreferredSize(new Dimension(250, 5));
        formulario.add(crearCampoConLabel("Nombre fichero", campoNombreFichero));
        
        JButton botonAgregarAeropuertoDestino= new JButton("Agregar aeropuerto destino");
        botonAgregarAeropuertoDestino.setFont(new Font("Arial", Font.BOLD, 16));
        botonAgregarAeropuertoDestino.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        botonAgregarAeropuertoDestino.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreFichero = campoNombreFichero.getText().trim();
                if (nombreFichero.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "Por favor, rellena el nombre del fichero", 
                        "Campos vacíos", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    gestor.darDeAltaAeropuertoDestino(nombreFichero);
                   
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error de entrada/salida al procesar el archivo.", 
                        "Error IO", 
                        JOptionPane.ERROR_MESSAGE);
                } catch (TemporadasSolapadasException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error: hay temporadas solapadas en el aeropuerto.", 
                        "Temporadas solapadas", 
                        JOptionPane.ERROR_MESSAGE);
                }catch(AeropuertoYaExisteException e1) {
                	e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error: el aeropuerto de destino ya existe.", 
                        "Aeropuerto de destino ya existente", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
                JOptionPane.showMessageDialog(null, 
    				    "¡Aeropuerto de destino dado de alta correctamente!", 
    				    "Éxito", 
    				    JOptionPane.INFORMATION_MESSAGE);
    			
            }
        });

        panel.add(formulario);
        panel.add(botonAgregarAeropuertoDestino, BorderLayout.SOUTH);
       
        add(panel);
 	     
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