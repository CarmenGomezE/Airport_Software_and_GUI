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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.usuario.Gestor;

public class GestionInfraestructurasFingerGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private final GestorGUI gestorGUI;
	private final Gestor gestor;
	
	public GestionInfraestructurasFingerGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.gestorGUI = gestorGUI;
		
		 setTitle("Panel de gestión de infraestructuras (Finger) del Gestor");
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
         JLabel titulo = new JLabel("Gestión de infraestructuras: Finger", JLabel.CENTER);
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
         
         JTextField campoAltura =crearCampoTexto();
         campoAltura.setPreferredSize(new Dimension(250, 5));
         JTextField campoCoste = crearCampoTexto();
         campoCoste.setPreferredSize(new Dimension(250,5));
         JTextField campoPuertasEmbarque = crearCampoTexto();
         campoPuertasEmbarque.setPreferredSize(new Dimension(250,5));
        
         formulario.add(crearCampoConLabel("Altura máxima del avión", campoAltura));
         formulario.add(crearCampoConLabel("Coste por hora de uso", campoCoste));
         formulario.add(crearCampoConLabel("Puertas de embarque", campoPuertasEmbarque)); 
         
         JButton botonAgregarFinger = new JButton("Agregar finger");
         botonAgregarFinger.setFont(new Font("Arial", Font.BOLD, 16));
         botonAgregarFinger.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         botonAgregarFinger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				String alturaTexto = campoAltura.getText().trim();
				String costeTexto = campoCoste.getText().trim();
				String puertasTexto = campoPuertasEmbarque.getText().trim();

				if (alturaTexto.isEmpty() || costeTexto.isEmpty() || puertasTexto.isEmpty()) {
				    JOptionPane.showMessageDialog(null, 
				        "Por favor, rellena todos los campos antes de guardar.", 
				        "Campos vacíos", 
				        JOptionPane.WARNING_MESSAGE);
				    return;
				}

				try {
				    double altura = Double.parseDouble(alturaTexto);
				    double coste = Double.parseDouble(costeTexto);

				    String[] partes = puertasTexto.split(",");
				    List<PuertaEmbarque> puertas = new ArrayList<>();

				    for (String parte : partes) {
				        parte = parte.trim();
				        if (parte.matches("P\\d+")) {
				            int id = Integer.parseInt(parte.substring(1));
				            PuertaEmbarque puerta = gestor.obtenerPuertaEmbarquePorId(id);
				            if (puerta != null) {
				                puertas.add(puerta);
				            } else {
				                JOptionPane.showMessageDialog(null, 
				                    "No se encontró la puerta de embarque con ID: " + id, 
				                    "Error de datos", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, 
				                "Formato inválido en puertas de embarque. Usa el formato 'P1, P2, ...'", 
				                "Error de formato", 
				                JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				    }

				    gestor.darDeAltaFinger(altura, coste, puertas);

				} catch (NumberFormatException e1) {
				    JOptionPane.showMessageDialog(null, 
				        "Altura o coste no tienen un formato válido.", 
				        "Error de formato numérico", 
				        JOptionPane.ERROR_MESSAGE);
				}
				
				 JOptionPane.showMessageDialog(null, 
						    "¡Finger dado de alta correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);

			}
        	 
         });
         
         panelPrincipal.add(formulario);
         panelPrincipal.add(botonAgregarFinger, BorderLayout.SOUTH);
        
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
