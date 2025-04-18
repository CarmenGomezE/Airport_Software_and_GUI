package src.airportmanager.gui.gestor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class EstadisticasGestorGUI extends JFrame{

	private final Gestor gestor;
	private final GestorGUI gestorGUI;
	private final AirportManager manager;
	private DefaultListModel<String> model;
	private JButton botonRefrescar; 

	public EstadisticasGestorGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestorGUI = gestorGUI;
		this.gestor = gestor;
		this.manager = manager;

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

		JLabel titulo = new JLabel("Estadísticas del Aeropuerto", JLabel.CENTER);
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		titulo.setFont(new Font("Arial", Font.BOLD, 28));
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

		/**Boton refrescar*/
		JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar = new JButton("🔄 Refrescar estadísticas"); 
		botonRefrescar.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar.setFont(botonRefrescar.getFont().deriveFont(16f));
		botonRefrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarEstadisticas();
				/**Despues de refrescar que salga un boton de actualizado durante unos segundos para que aunque las estadisticas no cambien haga algo*/
				botonRefrescar.setText("✅ Actualizado");
				botonRefrescar.setEnabled(false); 

				new Timer(2000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						botonRefrescar.setText("🔄 Refrescar estadísticas");
						botonRefrescar.setEnabled(true); 
					}
				}).start();
			}
		});
		botonPanel.add(botonRefrescar);
		panel.add(botonPanel);

		setVisible(true);
	}

	private void actualizarEstadisticas() {

		model.clear();

		/**FINGERS*/
		model.addElement("FINGERS");
		gestor.getAeropuerto().getFingers().forEach(f -> {
			model.addElement("FING" + f.getId() + " - " + String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMedioDiarioFinger(f)) + " horas");
		});
		model.addElement("\n");

		/**PUERTAS DE EMBARQUE*/
		model.addElement("PUERTAS DE EMBARQUE");
		gestor.getAeropuerto().getPuertasEmbarque().forEach(p -> {
			model.addElement("EMB" + p.getId() + " - " + String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMedioDiarioPuertaEmbarque(p)) + " horas");
		});
		model.addElement("\n");

		/**ZONAS DE APARCAMIENTO*/
		model.addElement("ZONAS DE APARCAMIENTO");
		gestor.getAeropuerto().getZonasAparcamiento().forEach(z -> {
			model.addElement("ZON" + z.getId() + "-" + String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMedioDiarioZonaAparcamiento(z)) + " horas");
		});
		model.addElement("\n");

		/**HANGARES*/
		model.addElement("HANGARES");
		gestor.getAeropuerto().getHangares().forEach(h -> {
			model.addElement("HANG" + h.getId() + "-" + String.format("%.1f", gestor.getEstadisticasAeropuerto().getUsoMedioDiarioHangar(h)) + " horas");
		});
		model.addElement("\n");
	}
}

