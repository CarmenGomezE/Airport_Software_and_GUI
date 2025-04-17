package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import java.util.*;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.aeropuerto.Temporada;
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
	     JPanel nombrePanel = new JPanel();
	     JLabel labelNombre = new JLabel("Nombre: ");
	     labelNombre.setFont(new Font("Arial", Font.PLAIN, 18));
	     nombrePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     nombrePanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     nombrePanel.add(labelNombre);

	     panel.add(nombrePanel);
	     panel.add(Box.createRigidArea(new Dimension(0, 10)));
	     
	     JTextField campoNombre = new JTextField();
	     campoNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	     campoNombre.setFont(new Font("Arial", Font.PLAIN, 16));
	     panel.add(campoNombre);
	     panel.add(Box.createRigidArea(new Dimension(0, 30)));
	     
         /** CÓDIGO*/
	     JPanel codigoPanel = new JPanel();
         JLabel labelCodigo = new JLabel("Código: ");
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
         
         /** CIUDAD  */
         JPanel ciudadPanel = new JPanel();
         JLabel labelCiudad = new JLabel("Ciudad más cercana: ");
         labelCiudad.setFont(new Font("Arial", Font.PLAIN, 18));
         ciudadPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     ciudadPanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     ciudadPanel.add(labelCiudad);
	     
         panel.add(ciudadPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCiudad = new JTextField();
         campoCiudad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCiudad.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCiudad);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /**PAÍS */
         JPanel paisPanel = new JPanel();
         JLabel labelPais = new JLabel("País: ");
         labelPais.setFont(new Font("Arial", Font.PLAIN, 18));
         paisPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     paisPanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     paisPanel.add(labelPais);
	     
         panel.add(paisPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoPais = new JTextField();
         campoPais.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoPais.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoPais);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DISTANCIA A LA CIUDAD MÁS CERCANA */
         JPanel distanciaPanel = new JPanel();
         JLabel labelCiudadCercana = new JLabel("Distancia a la ciudad más cercana:");
         labelCiudadCercana.setFont(new Font("Arial", Font.PLAIN, 18));
         distanciaPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     distanciaPanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     distanciaPanel.add(labelCiudadCercana);
	     
         panel.add(distanciaPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoCiudadCercana = new JTextField();
         campoCiudadCercana.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCiudadCercana.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoCiudadCercana);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DIRECCIÓN RELATIVA A LA CIUDAD */
         JPanel direccionPanel = new JPanel();
         JLabel labelDireccion = new JLabel("Dirección relativa a la ciudad: ");
         labelDireccion.setFont(new Font("Arial", Font.PLAIN, 18));
         direccionPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     direccionPanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     direccionPanel.add(labelDireccion);
	     
         panel.add(direccionPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoDireccion = new JTextField();
         campoDireccion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoDireccion.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoDireccion);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /** DIFERENCIA HORARIA */
         JPanel diferenciaPanel = new JPanel();
         JLabel labelDiferencia = new JLabel("Diferencia horaria: ");
         labelDiferencia.setFont(new Font("Arial", Font.PLAIN, 18));
         diferenciaPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	     diferenciaPanel.setBackground(ConfiguracionUI.COLOR_FONDO); 
	     diferenciaPanel.add(labelDiferencia);
	     
         panel.add(diferenciaPanel);
         panel.add(Box.createRigidArea(new Dimension(0, 10)));

         JTextField campoDiferencia = new JTextField();
         campoDiferencia.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoDiferencia.setFont(new Font("Arial", Font.PLAIN, 16));
         panel.add(campoDiferencia);
         panel.add(Box.createRigidArea(new Dimension(0, 30)));
         
         /**AÑADIR TEMPORADA*/
         JCheckBox agregarTemporada = new JCheckBox("Añadir temporada");
         agregarTemporada.setBackground(ConfiguracionUI.COLOR_FONDO);
         agregarTemporada.setFont(new Font("Arial", Font.BOLD, 16));
         panel.add(agregarTemporada);
         panel.add(Box.createRigidArea(new Dimension(0, 20)));
         
         JPanel temporadaPanel = new JPanel();
         temporadaPanel.setLayout(new BoxLayout(temporadaPanel, BoxLayout.Y_AXIS));
         temporadaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
         temporadaPanel.setVisible(false);
         
         JTextField campoInicio = new JTextField();
         campoInicio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoInicio.setFont(new Font("Arial", Font.PLAIN, 16));
         temporadaPanel.add(new JLabel("Fecha de inicio (YYYY-MM-DD)"));
         temporadaPanel.add(campoInicio);
         temporadaPanel.add(Box.createRigidArea(new Dimension(0,10)));
         
         JTextField campoFin = new JTextField();
         campoFin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoFin.setFont(new Font("Arial", Font.PLAIN, 16));
         temporadaPanel.add(new JLabel("Fecha de fin (YYYY-MM-DD)"));
         temporadaPanel.add(campoFin);
         temporadaPanel.add(Box.createRigidArea(new Dimension(0,10)));
         
         JTextField campoApertura = new JTextField();
         campoApertura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoApertura.setFont(new Font("Arial", Font.PLAIN, 16));
         temporadaPanel.add(new JLabel("Horario de apertura (HH:mm)"));
         temporadaPanel.add(campoApertura);
         temporadaPanel.add(Box.createRigidArea(new Dimension(0,10)));
         
         JTextField campoCierre = new JTextField();
         campoCierre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
         campoCierre.setFont(new Font("Arial", Font.PLAIN, 16));
         temporadaPanel.add(new JLabel("Horario de cierre (HH:mm)"));
         temporadaPanel.add(campoCierre);
         temporadaPanel.add(Box.createRigidArea(new Dimension(0,10)));
         
         panel.add(temporadaPanel);
         panel.add(Box.createRigidArea(new Dimension(0,20)));
         
         agregarTemporada.addActionListener(e -> {
        	    temporadaPanel.setVisible(agregarTemporada.isSelected());
        	    panel.revalidate();
        	    panel.repaint();
        	});

         
         /**Barra para bajar y poder ver todo el contenido*/
         JScrollPane scrollPane = new JScrollPane(panel);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Hace que el scroll sea más suave
         add(scrollPane);
         
         /**Boton guardar*/
         JButton botonGuardar = new JButton("Guardar");
         botonGuardar.setFont(new Font("Arial", Font.BOLD, 16));
         botonGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel.add(botonGuardar);
         panel.add(Box.createRigidArea(new Dimension(0, 20)));
         
         botonGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = campoNombre.getText().trim();
				String codigo = campoCodigo.getText().trim();
				String ciudad = campoCiudad.getText().trim();
				String pais = campoPais.getText().trim();
				String distanciaTexto = campoCiudadCercana.getText().trim();
				String direccionTexto = campoDireccion.getText().trim();
				String diferenciaTexto = campoDiferencia.getText().trim();
				
				if (nombre.isEmpty() || codigo.isEmpty() || ciudad.isEmpty() || distanciaTexto.isEmpty() || 
					        direccionTexto.isEmpty() || diferenciaTexto.isEmpty() || pais.isEmpty()) {

					        JOptionPane.showMessageDialog(null, 
					            "Por favor, rellena todos los campos antes de guardar.", 
					            "Campos vacíos", 
					            JOptionPane.WARNING_MESSAGE);
				return;
				}
				
				try {
					double distancia = Double.parseDouble(distanciaTexto);
					DireccionRelativaCiudad direccion;
					
					switch(direccionTexto.toLowerCase()) {
						
					case "norte":
						direccion = DireccionRelativaCiudad.NORTE;
						break;
						
					case "sur":
						direccion = DireccionRelativaCiudad.SUR;
						break;
						
					case "este":
						direccion = DireccionRelativaCiudad.ESTE;
						break;
						
					case "oeste":
						direccion = DireccionRelativaCiudad.OESTE;
						break;
						
					default: 
						JOptionPane.showMessageDialog(null, 
			                    "Dirección inválida. Debe ser: Norte, Sur, Este u Oeste.", 
			                    "Error en Dirección", 
			                    JOptionPane.ERROR_MESSAGE);
			                return;
					
					}
					
					int horas = Integer.parseInt(diferenciaTexto);
				      ZoneOffset diferencia = ZoneOffset.ofHours(horas);
		
					
					gestor.configurarAeropuertoPropioNombre(nombre);
					gestor.configurarAeropuertoPropioCodigo(codigo);
					gestor.configurarAeropuertoPropioCiudad(ciudad);
					gestor.configurarAeropuertoPropioPais(pais);
					gestor.configurarAeropuertoPropioDistanciaCiudad(distancia);
					gestor.configurarAeropuertoPropioDireccionCiudad(direccion);
					gestor.configurarAeropuertoPropioDiferenciaHoraria(diferencia);
					
					if (agregarTemporada.isSelected()) {
						String fechaInicio =campoInicio.getText().trim();
						String fechaFin = campoFin.getText().trim();
						String horarioApertura = campoApertura.getText().trim();
						String horarioCierre = campoCierre.getText().trim();
						List<Temporada> temporadas = new ArrayList<>();
						
						Temporada nuevaTemporada = new Temporada(fechaInicio, fechaFin, horarioApertura, horarioCierre);
						temporadas.add(nuevaTemporada);
						
						gestor.configurarAeropuertoPropioNumTemporadas(gestor.getAeropuerto().getNumTemporadas() + 1);
						gestor.configurarAeropuertoPropioTemporadas(temporadas);
						
					}
					
					JOptionPane.showMessageDialog(null, 
						    "¡Configuración guardada correctamente!", 
						    "Éxito", 
						    JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				catch (NumberFormatException | DateTimeParseException ex) {
			        JOptionPane.showMessageDialog(null, 
			        		 "Por favor, introduce valores válidos. Revisa números y fechas (YYYY-MM-DD, HH:mm).", 
			        	        "Error de formato", 
			            JOptionPane.ERROR_MESSAGE);
			    }
				
				
				
				 
				
		}
        	 
         });
         setVisible(true);
             
	}
	 
	}
