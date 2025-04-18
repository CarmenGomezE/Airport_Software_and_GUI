package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.UsuarioConDni;
import src.airportmanager.vuelo.VueloProgramado;

public class GestionVueloGUI extends JFrame{ 
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	
	public GestionVueloGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		 setTitle("Panel de gestión de vuelos del Gestor");
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
	     
	     /** PANEL PRINCIPAL */
	        JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
	        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
	        add(panel);
	        
	        /** TÍTULO */
	        JLabel titulo = new JLabel("Gestión de vuelos", JLabel.CENTER);
	        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	        titulo.setFont(new Font("Arial", Font.BOLD, 28));
	        panel.add(titulo);
	        panel.add(Box.createRigidArea(new Dimension(0, 40)));

	        /** CÓDIGO DE VUELO */
	        JLabel labelCodigo = new JLabel("Código de vuelo");
	        labelCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
	        labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
	        panel.add(labelCodigo);
	        panel.add(Box.createRigidArea(new Dimension(0, 10)));

	        JTextField campoCodigo = new JTextField();
	        campoCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	        campoCodigo.setFont(new Font("Arial", Font.PLAIN, 16));
	        panel.add(campoCodigo);
	        panel.add(Box.createRigidArea(new Dimension(0, 30)));  
	        
	        /**Terminal*/  
	        JLabel labelTerminal = new JLabel("Terminal");
	        labelTerminal.setFont(new Font("Arial", Font.PLAIN, 18));
	        labelTerminal.setAlignmentX(Component.CENTER_ALIGNMENT);
	        panel.add(labelTerminal);
	        panel.add(Box.createRigidArea(new Dimension(0, 10)));

	        JTextField campoTerminal = new JTextField();
	        campoTerminal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	        campoTerminal.setFont(new Font("Arial", Font.PLAIN, 16));
	        panel.add(campoTerminal);
	        panel.add(Box.createRigidArea(new Dimension(0, 30)));
	        
	        /**Controlador*/
	        JLabel labelControlador = new JLabel("Dni del controlador");
	        labelControlador.setFont(new Font("Arial", Font.PLAIN, 18));
	        labelControlador.setAlignmentX(Component.CENTER_ALIGNMENT);
	        panel.add(labelControlador);
	        panel.add(Box.createRigidArea(new Dimension(0, 10)));

	        JTextField campoControlador = new JTextField();
	        campoControlador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	        campoControlador.setFont(new Font("Arial", Font.PLAIN, 16));
	        panel.add(campoControlador);
	        panel.add(Box.createRigidArea(new Dimension(0, 30)));
	        
	        /**Agregar vuelo*/
	        JButton botonAgregarVuelo = new JButton("Agregar vuelo");
	        botonAgregarVuelo.setFont(new Font("Arial", Font.BOLD, 16));	    
	        botonAgregarVuelo.setAlignmentX(CENTER_ALIGNMENT);
	        panel.add(botonAgregarVuelo);
	        
	        botonAgregarVuelo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String codigo = campoCodigo.getText().trim();
					String terminalCodigo = campoTerminal.getText().trim();
					String dniControlador = campoControlador.getText().trim();
					
					VueloProgramado vueloProgramado;
					
					UsuarioConDni controlador = gestor.buscarUsuarioPorDni(dniControlador);
					
					
				}
	        	
	        });
	        
	        setVisible(true);
	     
	}
	
	

}
