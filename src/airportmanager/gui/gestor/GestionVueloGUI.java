package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

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
	     
	     JPanel panel = new JPanel();
	     panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  
	     panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
	     panel.setBackground(ConfiguracionUI.COLOR_FONDO);
	     add(panel);
	     
	     JLabel titulo = new JLabel("Gestión de vuelos", JLabel.CENTER);
	     titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	     titulo.setFont(new Font("Arial", Font.BOLD, 28));
	     panel.add(titulo);
	     panel.add(Box.createRigidArea(new Dimension(0, 40)));
	     
         /** CÓDIGO*/
	     JPanel codigoPanel = new JPanel();
         JLabel labelCodigo = new JLabel("Código del vuelo ");
         labelCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
         codigoPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     codigoPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
	     codigoPanel.add(labelCodigo);

         panel.add(codigoPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCodigo = new JTextField();
         campoCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCodigo.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCodigo);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** TERMINAL*/
	     JPanel terminalPanel = new JPanel();
         JLabel labelTerminal = new JLabel("Terminal ");
         labelTerminal.setFont(new Font("Arial", Font.PLAIN, 18));
         terminalPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     terminalPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
	     terminalPanel.add(labelTerminal);

         panel.add(terminalPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoTerminal = new JTextField();
         campoTerminal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoTerminal.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoTerminal);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** CONTROLADOR*/
	     JPanel controladorPanel = new JPanel();
         JLabel labelControlador = new JLabel("Dni del controlador ");
         labelControlador.setFont(new Font("Arial", Font.PLAIN, 18));
         controladorPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     controladorPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
	     controladorPanel.add(labelControlador);

         panel.add(controladorPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoControlador = new JTextField();
         campoControlador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoControlador.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoControlador);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         setVisible(true);
	     
	}
	
	

}
