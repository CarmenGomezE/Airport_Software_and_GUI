package src.airportmanager.gui.gestor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.factura.EstadoFactura;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Gestor;

public class GestionFacturasGUI extends JFrame {
	private final Gestor gestor;
	private final GestorGUI gestorGUI;

	private JComboBox<String> comboAerolineas;

	private String aerolineaSeleccionada;
	private DefaultListModel<String> model;
	private JButton botonRefrescar;

	public GestionFacturasGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		this.gestorGUI = gestorGUI;
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

		JLabel titulo = new JLabel("Facturación", JLabel.CENTER);
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		titulo.setFont(new Font("Arial", Font.BOLD, 28));
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
		/**BOTON REFRESCAR*/
		JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar = new JButton("🔄 Refrescar facturas"); 
		botonRefrescar.setBackground(ConfiguracionUI.COLOR_FONDO);
		botonRefrescar.setFont(botonRefrescar.getFont().deriveFont(16f));
		botonRefrescar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verListaFacturas();
				/**Despues de refrescar que salga un boton de actualizado durante unos segundos para que aunque las facturas no cambien haga algo*/
				botonRefrescar.setText("✅ Actualizado");
				botonRefrescar.setEnabled(false); 

				new Timer(2000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						botonRefrescar.setText("🔄 Refrescar facturas");
						botonRefrescar.setEnabled(true); 
					}
				}).start();
			}
		});
		botonPanel.add(botonRefrescar);
		panel.add(botonPanel);

		
		/**CAJITA CON LAS FACTURAS*/
		model = new DefaultListModel<>();
		JList<String> listaFacturas = new JList<>(model);
		listaFacturas.setFont(new Font("Monospaced", Font.PLAIN, 14));
		listaFacturas.setVisibleRowCount(10);
		
		JScrollPane scrollPane = new JScrollPane(listaFacturas);
		scrollPane.setPreferredSize(new Dimension(800, 200));

		panel.add(scrollPane);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		verListaFacturas();
		
		/**AEROLINEAS*/
		JPanel aerolineaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		aerolineaPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
	
		seleccionAerolineas(aerolineaPanel);
		aerolineaPanel.add(comboAerolineas);
		aerolineaPanel.add(Box.createRigidArea(new Dimension(200, 0)));
		
		/**BOTON EMITIR FACTURA*/
		JButton botonEmitirFactura = new JButton("Emitir factura");
		botonEmitirFactura.setFont(new Font("Arial", Font.BOLD, 16));
        botonEmitirFactura.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
	
	private void verListaFacturas() {
		model.clear();
		Map<String, EstadoFactura> facturasEstado = gestor.cargarFacturas();
		
		for (Map.Entry<String, EstadoFactura> entry : facturasEstado.entrySet()) {
			String linea = "Factura ID: " + entry.getKey() + "| Estado: " + entry.getValue();
			model.addElement(linea);
		}
	}
	
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
	
		
	
