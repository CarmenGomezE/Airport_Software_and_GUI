package src.airportmanager.gui.controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.AirportManagerGUI;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Vuelo;

public class ControladorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private ControladorAereo controladorAereo;
    private DefaultListModel<String> modeloLista;
	private JList<String> listaVuelos;
	private AirportManager AeManager;

    public ControladorGUI(AirportManagerGUI AirportGUI, AirportManager manager, ControladorAereo controlador) {
    	this.controladorAereo = controlador;
    	this.AeManager = manager;
    	
        setTitle("Panel de Controlador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	AirportGUI.setVisible(true);
            }
        });

        /** PANEL PRINCIPAL */
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        /** Titulo de la lista de vuelos */
        JLabel listaVuelosTitulo = new JLabel("Lista de vuelos (" + formattedDate + ")", JLabel.CENTER);
        listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

        /** Lista de vuelos */
        modeloLista = new DefaultListModel<>();
        actualizarLista();
        
        listaVuelos = new JList<>(modeloLista);
        listaVuelos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String texto = value.toString();

                if (texto.contains("Esperando pista durante más de 10 min")) {
                    label.setForeground(Color.RED);
                } else if (texto.contains("Retrasado")) {
                    label.setForeground(Color.ORANGE);
                } else if (texto.contains("Salida en menos de 1 hora")) {
                    label.setForeground(Color.BLUE);
                } else {
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });
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
                new BuscarVueloControladorGUI(manager, ControladorGUI.this, controladorAereo);
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
        JLabel titulo = new JLabel("Controlador de la Terminal 1", JLabel.CENTER);
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
                new NotificacionesControladorGUI(manager, ControladorGUI.this, controladorAereo);
            }
        });
        
        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        subPanelBotones.add(botonCerrarSesion);
        botonCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(ControladorGUI.this,
                        "¿Estás seguro de que quieres cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                    AirportGUI.setVisible(true);
                }
            }
        });
        subPanelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);
        panelDerecho.add(subPanelBotones);

        JButton botonGestionEstadoVuelo = new JButton("Gestión de estados de los vuelos");
        panelDerecho.add(botonGestionEstadoVuelo);
        botonGestionEstadoVuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                new src.airportmanager.gui.controlador.GestionEstadoVueloGUI(manager, ControladorGUI.this, controladorAereo);
            }
        });
        
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
            	if (v.getEstado().equals(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE)) {
            		Duration tiempo = Duration.between(v.getHistorialEstadoVuelo().getHoraCambio(EstadoVuelo.ESPERANDO_PISTA_DE_ATERRIZAJE), LocalTime.now());
            		if (tiempo.toMinutes() >= 10) {
            			modeloLista.addElement("Vuelo: "+v+" | Hora: "+v.getHora()+" | Estado: "+ v.getEstado()+" | Esperando pista durante más de 10 min");
            		}
            	} else if (v.getEstado().equals(EstadoVuelo.RETRASADO)) {
                	modeloLista.addElement("Vuelo: "+v+" | Hora: "+v.getHora()+" | Estado: "+ v.getEstado() +" | Retrasado");
            	} else if (v.getEstado().equals(EstadoVuelo.OPERATIVO)) {
            		Duration tiempo = Duration.between(LocalTime.now(), v.getHora());
            		if (tiempo.toMinutes() <= 60) {
            			modeloLista.addElement("Vuelo: "+v+" | Hora: "+v.getHora()+" | Estado: "+ v.getEstado()+" | Salida en menos de 1 hora");
            		}
            	} else {
                	modeloLista.addElement("Vuelo: "+v+" | Hora: "+v.getHora()+" | Estado: "+ v.getEstado());
                }
            }  
        }
	}
}