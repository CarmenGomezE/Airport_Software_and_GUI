package src.gui.gestor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.factura.EstadoFactura;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionFacturasGUI representa la ventana de la gestión de facturas
 * del gestor.
 */
public class GestionFacturasGUI extends JFrame {
	/** Identificador de versión de clase */
	private static final long serialVersionUID = 1L;
	/** Gestor */
	private final Gestor gestor;
	/** Combo box de aerolineas */
	private JComboBox<String> comboAerolineas;
	/** Aerolinea seleccionada */
	private String aerolineaSeleccionada;
	/** Modelo de lista por defecto */
	private DefaultListModel<String> model;
	/** Boton refrescar */
	private JButton botonRefrescar;

	/**
	 * Constructor de la clase GestionFacturasGUI
	 * 
	 * @param manager   el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor    el gestor
	 */
	public GestionFacturasGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestor = gestor;

		setTitle("Panel de gestión de facturas del Gestor");
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

		JLabel titulo = ConfiguracionUI.crearTitulo("Facturación");
		panel.add(titulo);
		panel.add(Box.createRigidArea(new Dimension(0, 40)));

		/** FACTURAS */
		JPanel facturaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		facturaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);

		JLabel labelFactura = new JLabel("FACTURAS ");
		labelFactura.setFont(new Font("Arial", Font.BOLD, 25));
		facturaPanel.add(labelFactura);

		facturaPanel.add(Box.createRigidArea(new Dimension(50, 0)));

		panel.add(facturaPanel);
		/** BOTON REFRESCAR */
		JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar facturas");

		botonRefrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verListaFacturas();
			}
		});
		botonPanel.add(botonRefrescar);
		panel.add(botonPanel);

		/** CAJITA CON LAS FACTURAS */
		model = new DefaultListModel<>();
		JList<String> listaFacturas = new JList<>(model);
		listaFacturas.setFont(new Font("Monospaced", Font.PLAIN, 14));
		listaFacturas.setVisibleRowCount(10);

		JScrollPane scrollPane = new JScrollPane(listaFacturas);
		scrollPane.setPreferredSize(new Dimension(800, 200));

		panel.add(scrollPane);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		verListaFacturas();

		/** AEROLINEAS */
		JPanel aerolineaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		aerolineaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);

		seleccionAerolineas(aerolineaPanel);
		aerolineaPanel.add(comboAerolineas);
		aerolineaPanel.add(Box.createRigidArea(new Dimension(200, 0)));

		/** BOTON EMITIR FACTURA */
		JButton botonEmitirFactura = ConfiguracionUI.crearBotonCentrado("Emitir factura");

		botonEmitirFactura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Aerolinea aerolinea = gestor.buscarAerolineaPorNombre(aerolineaSeleccionada);
				if (aerolinea == null) {
					JOptionPane.showMessageDialog(null,
							"Por favor, selecciona la aerolínea antes de emitir la factura.",
							"Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;

				}
				gestor.emitirFactura(aerolinea);
				JOptionPane.showMessageDialog(null,
						"Factura emitida correctamente para la aerolínea " + aerolinea.getNombre(),
						"Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				verListaFacturas();
			}
		});

		aerolineaPanel.add(botonEmitirFactura);
		panel.add(aerolineaPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		setVisible(true);
	}

	/**
	 * Método auxiliar para ver la lista de las facturas
	 */
	private void verListaFacturas() {
		model.clear();
		Map<String, EstadoFactura> facturasEstado = gestor.cargarFacturas();

		for (Map.Entry<String, EstadoFactura> entry : facturasEstado.entrySet()) {
			String linea = "Factura ID: " + entry.getKey() + "| Estado: " + entry.getValue();
			model.addElement(linea);
		}
	}

	/**
	 * Método auxiliar para la selección de aerolíneas a la hora de emitir la
	 * factura.
	 * 
	 * @param aerolineaPanel el panel.
	 */
	private void seleccionAerolineas(JPanel aerolineaPanel) {
		List<String> listaAerolineas = new ArrayList<>();
		for (Aerolinea a : gestor.getAeropuerto().getAerolineas()) {
			if (a.getNombre() != null) {
				listaAerolineas.add(a.getNombre());
			}
		}
		String[] codigosAerolinea = listaAerolineas.toArray(new String[0]);

		aerolineaPanel.add(new JLabel("Aerolínea:"));
		comboAerolineas = new JComboBox<>(codigosAerolinea);
		comboAerolineas.setPreferredSize(new Dimension(150, 30));
		comboAerolineas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aerolineaSeleccionada = (String) comboAerolineas.getSelectedItem();
			}
		});
	}
}
