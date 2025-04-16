package src.airportmanager.gui.controlador;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.ControladorAereo;

public class BuscarVueloControladorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
    private ControladorGUI conGUI;
    private ControladorAereo controlador;

	public BuscarVueloControladorGUI(AirportManager manager, ControladorGUI controladorGUI, ControladorAereo controladorAe) {
		this.conGUI=controladorGUI;
		this.controlador= controladorAe;
		
        setTitle("Panel de buscar vuelos del Controlador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	conGUI.setVisible(true);
            }
        });
        
        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);
        
        /** Hacer la ventana visible */
        setVisible(true);
	}
}
