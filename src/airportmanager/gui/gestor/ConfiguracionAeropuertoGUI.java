package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class ConfiguracionAeropuertoGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	
	public ConfiguracionAeropuertoGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		 setTitle("Panel de configuración del aeropuerto del Gestor");
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
	     
	     JLabel titulo = new JLabel("Configuración del aeropuerto", JLabel.CENTER);
	     titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	     titulo.setFont(new Font("Arial", Font.BOLD, 28));
	     panel.add(titulo);
	     panel.add(Box.createRigidArea(new Dimension(0, 40)));
	     
	     /** NOMBRE*/
	     JLabel labelNombre = new JLabel("Nombre: ");
	     labelNombre.setFont(new Font("Arial", Font.PLAIN, 18));
	     labelNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
	     panel.add(labelNombre);
	     panel.add(Box.createRigidArea(new Dimension(0, 10)));

	     JTextField campoNombre = new JTextField();
	     campoNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	     campoNombre.setFont(new Font("Arial", Font.PLAIN, 16));
	     panel.add(campoNombre);
	     panel.add(Box.createRigidArea(new Dimension(0, 30)));    
	     
         /** CÓDIGO*/
         JLabel labelCodigo = new JLabel("Código: ");
         labelCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
         labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelCodigo);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCodigo = new JTextField();
         campoCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCodigo.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCodigo);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** CIUDAD MÁS CERCANA Y PAÍS */
         JLabel labelCiudadPais = new JLabel("Ciudad más cercana y país: ");
         labelCiudadPais.setFont(new Font("Arial", Font.PLAIN, 18));
         labelCiudadPais.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelCiudadPais);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCiudadPais = new JTextField();
         campoCiudadPais.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCiudadPais.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCiudadPais);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DISTANCIA A LA CIUDAD MÁS CERCANA */
         JLabel labelCiudadCercana = new JLabel("Distancia a la ciudad más cercana:");
         labelCiudadCercana.setFont(new Font("Arial", Font.PLAIN, 18));
         labelCiudadCercana.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelCiudadCercana);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCiudadCercana = new JTextField();
         campoCiudadCercana.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCiudadCercana.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCiudadCercana);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DIRECCIÓN RELATIVA A LA CIUDAD */
         JLabel labelDireccion = new JLabel("Dirección relativa a la ciudad: ");
         labelDireccion.setFont(new Font("Arial", Font.PLAIN, 18));
         labelDireccion.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelDireccion);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoDireccion = new JTextField();
         campoDireccion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoDireccion.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoDireccion);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DIFERENCIA HORARIA */
         JLabel labelDiferencia = new JLabel("Diferencia horaria: ");
         labelDiferencia.setFont(new Font("Arial", Font.PLAIN, 18));
         labelDiferencia.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelDiferencia);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoDiferencia = new JTextField();
         campoDiferencia.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoDiferencia.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoDiferencia);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** HORARIOS DE OPERACIÓN EN VERANO */
         JLabel labelVerano = new JLabel("Horarios de operación en verano: ");
         labelVerano.setFont(new Font("Arial", Font.PLAIN, 18));
         labelVerano.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(labelVerano);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoVerano = new JTextField();
         campoVerano.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoVerano.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoVerano);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         setVisible(true);
             
	}
	 
}
