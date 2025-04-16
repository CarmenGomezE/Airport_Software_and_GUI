package src.airportmanager.gui.gestor;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class BuscarVueloGestorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
    private GestorGUI geGUI;
    private Gestor gestor;

	public BuscarVueloGestorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestorAe) {
		this.geGUI=gestorGUI;
		this.gestor= gestorAe;
		
        setTitle("Panel de buscar vuelos del Gestor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	geGUI.setVisible(true);
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
