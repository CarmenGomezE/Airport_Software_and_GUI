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

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.PistaYaExisteException;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.Gestor;

public class GestionInfraestructuraPistaDespegueGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;


	public GestionInfraestructuraPistaDespegueGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		 setTitle("Panel de gestión de infraestructura (Pista de despegue) del gestor");
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
         JLabel titulo = new JLabel("Gestión de infraestructura: Pista de despegue ", JLabel.CENTER);
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
         
         JTextField campoCodigo = crearCampoTexto();
         campoCodigo.setPreferredSize(new Dimension(250, 5));
         JTextField campoLongitud =crearCampoTexto();
         campoLongitud.setPreferredSize(new Dimension(250, 5));
        
         
         formulario.add(crearCampoConLabel("Código", campoCodigo));
         formulario.add(crearCampoConLabel("Longitud", campoLongitud));
         
         JButton botonAgregarPistaDespegue = new JButton("Agregar pista de despegue");
         botonAgregarPistaDespegue.setFont(new Font("Arial", Font.BOLD, 16));
         botonAgregarPistaDespegue.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         botonAgregarPistaDespegue.addActionListener(new ActionListener() {

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
					gestor.darDeAltaPista(codigo, longitud, TipoPista.DESPEGUE);
				} catch (PistaYaExisteException e1) {
					
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, 
	                        "Error: la pista de despegue ya existe.", 
	                        "Pista de despegeue ya existente", 
	                        JOptionPane.ERROR_MESSAGE);
				}
				
				 JOptionPane.showMessageDialog(null, 
						    "¡Pista de despegue dada de alta correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);
				
			}
        	 
         });
         
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarPistaDespegue, BorderLayout.SOUTH);
        
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
