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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.usuario.Gestor;

/**
 * La clase GestionInfraestructurasHangarGUI representa la ventana de gestión de infraestructura (Hangar) del gestor.
 */
public class GestionInfraestructurasHangarGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	private JCheckBox mercaPeligrosa;
	private String tipoSeleccionado;

	/**
	 * Constructor de la clase GestionInfraestructuraHangarGUI
	 * @param manager el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor el gestor
	 */
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
	    
	    JPanel panelPrincipal = ConfiguracionUI.crearPanelPrincipal();
        
        /** TÍTULO */
        JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Hangar");
        
        JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        
        JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();
       
        String[] tipos = { "Pasajeros", "Mercancías" };
        JComboBox<String> tipoHangar = new JComboBox<>(tipos);
        JCheckBox checkPeligroso = new JCheckBox("¿Mercancía peligrosa?");
       
       JTextField campoNumeroAviones =ConfiguracionUI.crearCampoTexto();
       campoNumeroAviones.setPreferredSize(new Dimension(250, 5));
       JTextField campoDimensionAlto = ConfiguracionUI.crearCampoTexto();
       campoDimensionAlto.setPreferredSize(new Dimension(250,5));
       JTextField campoDimensionAncho = ConfiguracionUI.crearCampoTexto();
       campoDimensionAncho.setPreferredSize(new Dimension(250,5));
       JTextField campoDimensionLargo = ConfiguracionUI.crearCampoTexto();
       campoDimensionLargo.setPreferredSize(new Dimension(250,5));
       JTextField campoCoste = ConfiguracionUI.crearCampoTexto();
       campoCoste.setPreferredSize(new Dimension(250,5));
       
       
       formulario.add(ConfiguracionUI.crearCampoConLabel("Tipo", tipoHangar));
       formulario.add(ConfiguracionUI.crearCampoConLabel("Número máximo de aviones que puede alojar", campoNumeroAviones));
       formulario.add(ConfiguracionUI.crearCampoConLabel("Dimension alto", campoDimensionAlto));
       formulario.add(ConfiguracionUI.crearCampoConLabel("Dimension ancho", campoDimensionAncho));
       formulario.add(ConfiguracionUI.crearCampoConLabel("Dimension largo", campoDimensionLargo));
       formulario.add(ConfiguracionUI.crearCampoConLabel("Coste", campoCoste));
       
       JPanel panelOpcional = new JPanel();
       panelOpcional.setLayout(new BoxLayout(panelOpcional, BoxLayout.Y_AXIS));
       panelOpcional.setBackground(ConfiguracionUI.COLOR_FONDO);
       panelOpcional.setPreferredSize(new Dimension(250, 100));
       formulario.add(panelOpcional); 
       
       
       tipoHangar.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        tipoSeleccionado = (String) tipoHangar.getSelectedItem();
    	        
    	        // Limpia el panel opcional antes de añadir cosas nuevas
    	        panelOpcional.removeAll();

    	        if (tipoSeleccionado.equals("Mercancías")) {
    	            checkPeligroso.setBackground(ConfiguracionUI.COLOR_FONDO);
    	            panelOpcional.add(checkPeligroso);
    	        }

    	        // Actualiza la interfaz
    	        panelOpcional.revalidate();
    	        panelOpcional.repaint();
    	    }
    	});

      
       JButton botonAgregarHangar = ConfiguracionUI.crearBotonCentrado("Agregar hangar");
       
       botonAgregarHangar.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String numeroAvionesTexto = campoNumeroAviones.getText().trim();
			String dimensionAltoTexto = campoDimensionAlto.getText().trim();
			String dimensionAnchoTexto = campoDimensionAncho.getText().trim();
			String dimensionLargoTexto = campoDimensionLargo.getText().trim();
			String costeTexto = campoCoste.getText().trim();
			
			if (tipoSeleccionado == null || numeroAvionesTexto.isEmpty() || dimensionAltoTexto.isEmpty() || dimensionAnchoTexto.isEmpty() || dimensionLargoTexto.isEmpty() || costeTexto.isEmpty()) {
				JOptionPane.showMessageDialog(null, 
				        "Por favor, rellena todos los campos", 
				        "Campos vacíos", 
				        JOptionPane.WARNING_MESSAGE);
				    return;
			}
			
			int numeroAviones = Integer.parseInt(numeroAvionesTexto);
			double dimensionAlto = Double.parseDouble(dimensionAltoTexto);
			double dimensionAncho = Double.parseDouble(dimensionAnchoTexto);
			double dimensionLargo = Double.parseDouble(dimensionLargoTexto);
			double coste = Double.parseDouble(costeTexto);
			
			if (tipoSeleccionado.equals("Pasajeros")) {
				gestor.darDeAltaHangar("Pasajeros", numeroAviones, dimensionAncho, dimensionAlto, dimensionLargo, coste, false);
			}
			
			else {
				
				boolean peligrosa = checkPeligroso.isSelected();
				gestor.darDeAltaHangar("Mercancías", numeroAviones, dimensionAncho, dimensionAlto, dimensionLargo, coste, peligrosa);
				
			}
			
			
			JOptionPane.showMessageDialog(null, 
				    "¡Hangar dado de alta correctamente!", 
				    "Éxito", 
				    JOptionPane.INFORMATION_MESSAGE);
			
		}
    	   
       });
	
       panelPrincipal.add(formulario);
       panelPrincipal.add(botonAgregarHangar, BorderLayout.SOUTH);
      
       add(panelPrincipal);
	     
	     setVisible(true);
		
		
		
	}	 
}
