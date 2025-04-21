package src.airportmanager.gui.operador;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;

/**
 * La clase BuscarVueloOperadorGUI representa la ventana del operador desde la que este busca vuelos 
 * por unos determinados criterios.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class BuscarVueloOperadorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
    private OperadorGUI opGUI;
    private Operador operador;

	public BuscarVueloOperadorGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
		this.opGUI=operadorGUI;
		this.operador= operadorAerolinea;
		
        setTitle("Panel de buscar vuelos del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
				opGUI.setVisible(true);
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
