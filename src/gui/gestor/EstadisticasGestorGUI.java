package src.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import src.airportmanager.app.AirportManager;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase EstadisticasGestorGUI representa la ventana de las estadísticas del
 * aeropuerto del gestor.
 */
public class EstadisticasGestorGUI extends JFrame {
	/** Identificador de versión de clase */
	private static final long serialVersionUID = 1L;
	/** Gestor */
	private final Gestor gestor;
	/** Modelo de lista por defecto */
	private DefaultListModel<String> model;
	/** Botón refrescar */
	private JButton botonRefrescar;

	/**
	 * Constructor de la clase EstadisticasGestorGUI
	 * 
	 * @param manager   el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor    el gestor
	 */
	public EstadisticasGestorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;

		setTitle("Panel de estadísticas del Gestor");
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

		JLabel titulo = ConfiguracionUI.crearTitulo("Estadísticas del aeropuerto");
		panel.add(titulo);
		panel.add(Box.createRigidArea(new Dimension(0, 40)));

		model = new DefaultListModel<>();

		actualizarEstadisticas();

		JList<String> listaEstadisticas = new JList<>(model);
		listaEstadisticas.setFont(new Font("Arial", Font.PLAIN, 16));
		JScrollPane scrollPane = new JScrollPane(listaEstadisticas);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(scrollPane);

		/** Boton refrescar */
		JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar estadísticas");

		botonRefrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarEstadisticas();
			}
		});
		botonPanel.add(botonRefrescar);
		panel.add(botonPanel);

		setVisible(true);
	}

	/**
	 * Método auxiliar para actualizar las estadísticas
	 */
	private void actualizarEstadisticas() {

		model.clear();

		/** FINGERS */
		model.addElement("FINGERS");
		gestor.getAeropuerto().getFingers().forEach(f -> {
			model.addElement("FING" + f.getId() + " - "
					+ String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMensualFinger(f)) + " horas");
		});
		model.addElement("\n");

		/** PUERTAS DE EMBARQUE */
		model.addElement("PUERTAS DE EMBARQUE");
		gestor.getAeropuerto().getPuertasEmbarque().forEach(p -> {
			model.addElement("EMB" + p.getId() + " - "
					+ String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMensualPuertaEmbarque(p))
					+ " horas");
		});
		model.addElement("\n");

		/** ZONAS DE APARCAMIENTO */
		model.addElement("ZONAS DE APARCAMIENTO");
		gestor.getAeropuerto().getZonasAparcamiento().forEach(z -> {
			model.addElement("ZON" + z.getId() + "-"
					+ String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMensualZonaAparcamiento(z))
					+ " horas");
		});
		model.addElement("\n");

		/** HANGARES */
		model.addElement("HANGARES");
		gestor.getAeropuerto().getHangares().forEach(h -> {
			model.addElement("HANG" + h.getId() + "-"
					+ String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMensualHangar(h)) + " horas");
		});
		model.addElement("\n");
	}
}
