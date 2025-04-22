package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class GestionInfraestructurasHangarGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	private JRadioButton pasajeros;
	private JRadioButton mercancias;

	public GestionInfraestructurasHangarGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		setTitle("Panel de gestión de infraestructura (hangar) del Gestor");
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
        JLabel titulo = new JLabel("Gestión infraestructura: hangar", JLabel.CENTER);
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
        
        JPanel tipoPanel = new JPanel();
        tipoPanel.setLayout(new BoxLayout(tipoPanel, BoxLayout.X_AXIS)); // Para que estén en línea
        tipoPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        
        pasajeros = new JRadioButton("Pasajeros");
        pasajeros.setPreferredSize(new Dimension(250, 5));
        mercancias = new JRadioButton("Mercancías");
        mercancias.setPreferredSize(new Dimension(250, 5));
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(mercancias);
        grupo.add(pasajeros);
        
        JLabel labelTipo = new JLabel("Tipo");
        labelTipo.setAlignmentY(Component.LEFT_ALIGNMENT);
        tipoPanel.add(labelTipo);
        tipoPanel.add(pasajeros);
        tipoPanel.add(Box.createRigidArea(new Dimension(0,20)));
        tipoPanel.add(mercancias);
        formulario.add(tipoPanel);

        panelPrincipal.add(formulario);
        add(panelPrincipal);
       

	     
	     
	     
	     setVisible(true);
		
	}
	
	 
}
