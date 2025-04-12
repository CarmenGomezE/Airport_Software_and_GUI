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

public class EstadisticasOperadorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private OperadorGUI opGUI;
    
	public EstadisticasOperadorGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
		this.opGUI=operadorGUI;
        setTitle("Panel de estadísticas del Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
				opGUI.setVisible(true);
            }
        });
        
        /** Visible */
        setVisible(true);
	}

}
