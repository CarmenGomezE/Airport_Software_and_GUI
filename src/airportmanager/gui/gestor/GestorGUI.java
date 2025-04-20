package src.airportmanager.gui.gestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.Vuelo;

public class GestorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private Gestor AeGestor;
    private DefaultListModel<String> modeloLista;
	private JList<String> listaVuelos;
	private AirportManager AeManager;

    public GestorGUI(AirportManager manager, Gestor gestor) {
    	this.AeGestor=gestor;
    	this.AeManager=manager;
    	
        setTitle("Panel de Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        /** Titulo de la lista de vuelos */
        JLabel listaVuelosTitulo = new JLabel("Lista de vuelos (" + formattedDate + ")", JLabel.CENTER);
        listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);
        /** Lista de vuelos */
        modeloLista = new DefaultListModel<>();
        actualizarLista();
        
        listaVuelos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaVuelos);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);


        /** Botones de la lista */
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonBuscarVuelos=new JButton("🔎 Buscar vuelos");
        panelBotones.add(botonBuscarVuelos);
        botonBuscarVuelos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                new BuscarVueloGestorGUI(manager, GestorGUI.this, AeGestor);
            }
        });
     
        JButton botonRefrescar= new JButton("🔄 Refrescar vuelos");
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonRefrescar);
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLista();
            }
        });
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO */
        JPanel panelDerecho = new JPanel(new GridLayout(9, 1, 10, 10));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        /** Título del operador */
        JLabel titulo = new JLabel("Gestor del Aeropuerto Adolfo Suarez Madrid Barajas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelDerecho.add(titulo);

        /** Botones */
        JPanel subPanelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonNotificaciones = new JButton("🔔 Notificaciones");
        subPanelBotones.add(botonNotificaciones);
        botonNotificaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                new NotificacionesGestorGUI(manager, GestorGUI.this, gestor);
            }
        });
        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        subPanelBotones.add(botonCerrarSesion);
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(GestorGUI.this,
                        "¿Estás seguro de que quieres cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);
        
        
        JButton botonGestionInfraestructura = new JButton("Gestión de infraestructura");
        panelDerecho.add(botonGestionInfraestructura);
        
        JButton botonConfiguracionAeropuerto = new JButton("Configuración del aeropuerto");
        panelDerecho.add(botonConfiguracionAeropuerto);
        botonConfiguracionAeropuerto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				new ConfiguracionAeropuertoGUI(manager, GestorGUI.this, gestor);
				
			}      	
        });
        
        JButton botonGestionUsuarios = new JButton("Gestión de usuarios");
        panelDerecho.add(botonGestionUsuarios);
        
        JButton botonEstadisticas = new JButton("Estadísticas de la infraestructura");
        panelDerecho.add(botonEstadisticas);
        botonEstadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				new EstadisticasGestorGUI(manager, GestorGUI.this, gestor);
				
			}
        	
        });
        
        JButton botonGestionVuelos = new JButton("Gestión de vuelos");
        panelDerecho.add(botonGestionVuelos);
        botonGestionVuelos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				new GestionVueloGUI(manager, GestorGUI.this, gestor);
				
			}      	
        });
        
        JButton botonGestionFacturas = new JButton("Gestión de facturas");
        panelDerecho.add(botonGestionFacturas);

        panelDerecho.add(Box.createVerticalStrut(10));

        /** Añadir al panel principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Visible */
        setVisible(true);
    }
    private void actualizarLista() {
	    modeloLista.clear();
	    List<Vuelo> vuelos = AeManager.getAeropuerto().getVuelosAceptados();
	    LocalDate fecha = LocalDate.now();

	    if (vuelos.isEmpty()) {
	        modeloLista.addElement("No hay vuelos programados.");
	        return;
	    }
        for (Vuelo v : vuelos) {
        	if (v.getFecha().equals(fecha)) {
        		modeloLista.addElement("Vuelo: "+v+" | Hora: "+v.getHora()+" | Estado: "+ v.getEstado());
        	}
        }
	}
}
