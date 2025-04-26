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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.VueloProgramado;

public class GestionVueloGUI extends JFrame{ 
	private static final long serialVersionUID = 1L;
	private final GestorGUI geGUI;
	private final Gestor gestor;
	private DefaultListModel<String> modeloLista;
    private JList<String> listaVuelos;
    private JTextField campoCodigo;
    private JTextArea textAreaMensajes;
	
	public GestionVueloGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;
		this.geGUI = gestorGUI;
		
        setTitle("Panel de gestión de vuelos del Gestor");
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

        /** PANEL IZQUIERDO */
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(500, 600));
        panelIzquierdo.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel listaVuelosTitulo = new JLabel("Propuestas de vuelo recibidas", JLabel.CENTER);
        listaVuelosTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaVuelosTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaVuelos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaVuelos);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = new JButton("🔄 Refrescar vuelos propuestos");
        panelBotones.add(botonRefrescar);
        botonRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLista();
            }
        });

        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        /** PANEL DERECHO */
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(500, 600));
        panelDerecho.setBackground(ConfiguracionUI.COLOR_FONDO);

        JLabel titulo = new JLabel("Gestor: " + gestor.getNombre(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(titulo);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel labelCodigo = new JLabel("Código de vuelo:");
        labelCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(labelCodigo);

        campoCodigo = new JTextField();
        campoCodigo.setMaximumSize(new Dimension(1000, 800));
        campoCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(campoCodigo);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botonGestionar = new JButton("Gestionar vuelo");
        botonGestionar.setMaximumSize(new Dimension(1000, 800));
        botonGestionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(botonGestionar);

        panelDerecho.add(Box.createVerticalGlue());
        
     // Crear el área de texto y su scroll
        textAreaMensajes = new JTextArea(5, 30); 
        textAreaMensajes.setEditable(false);  
        textAreaMensajes.setLineWrap(true);  
        textAreaMensajes.setWrapStyleWord(true); 

        JScrollPane scrollPaneMensajes = new JScrollPane(textAreaMensajes);
        // No usamos setPreferredSize
        scrollPaneMensajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPaneMensajes.setMaximumSize(new Dimension(600, 200)); // ajustable

        // Contenedor del mensaje
        JPanel contenedorMensajes = new JPanel();
        contenedorMensajes.setLayout(new BoxLayout(contenedorMensajes, BoxLayout.Y_AXIS));
        contenedorMensajes.setOpaque(false);
        contenedorMensajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorMensajes.add(Box.createRigidArea(new Dimension(0, 10)));
        contenedorMensajes.add(scrollPaneMensajes);
        contenedorMensajes.setVisible(false);

        // Añadir al panel derecho
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20))); // espaciado
        panelDerecho.add(contenedorMensajes);

        /** Acción aceptar */
        botonGestionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                VueloProgramado vuelo = gestor.buscarVueloPropuestoPorCodigo(codigo);
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "No se encontró ningún vuelo con ese código.");
                    return;
                }

                try {
                    gestor.gestionarVuelos(vuelo);
                    String mensaje = gestor.getMensajeGUI();
                    System.out.println(mensaje);
                    textAreaMensajes.setText(mensaje);
                    contenedorMensajes.setVisible(true);
                    contenedorMensajes.revalidate();
                    contenedorMensajes.repaint();
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Vuelo gestionado correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GestionVueloGUI.this, "Error al gestionar: " + ex.getMessage());
                }
            } 
        });

        /** Añadir los paneles al principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Hacer la ventana visible */
        setVisible(true);
    }

	private void actualizarLista() {
	    modeloLista.clear();
	    List<VueloProgramado> vuelos = gestor.getVuelosPropuestos();

	    if (vuelos.isEmpty()) {
	        modeloLista.addElement("No hay vuelos propuestos pendientes de gestionar.");
	        return;
	    }
        for (VueloProgramado v : vuelos) {
            modeloLista.addElement("Vuelo: " + v.getCodigo() + " | Aerolinea: " + v.getAvion().getAerolinea().getNombre() + " | Fecha/Hora: " + v.getFecha() + "/" + v.getHora());
        }
	}
}