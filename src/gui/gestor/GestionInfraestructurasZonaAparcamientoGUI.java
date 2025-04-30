package src.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionInfraestructurasZonaAparcamiento representa la ventana
 * gestión de infraestructura (Zona de aparcamiento) de gestor
 */
public class GestionInfraestructurasZonaAparcamientoGUI extends JFrame {
	/** Identificador de versión de clase */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase GestionInfraestructuraZonaAparcamientoGUI
	 * 
	 * @param manager   el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor    el gestor
	 */
	public GestionInfraestructurasZonaAparcamientoGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		setTitle("Panel de gestión de infraestructuras (Zona de aparcamiento) del Gestor");
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
		JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Zona de aparcamiento");

		JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
		panelPrincipal.add(cabecera, BorderLayout.NORTH);

		JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

		JTextField campoLargo = ConfiguracionUI.crearCampoTexto();
		campoLargo.setPreferredSize(new Dimension(250, 5));
		JTextField campoCoste = ConfiguracionUI.crearCampoTexto();
		campoCoste.setPreferredSize(new Dimension(250, 5));
		JTextField campoAncho = ConfiguracionUI.crearCampoTexto();
		campoAncho.setPreferredSize(new Dimension(250, 5));
		JTextField campoPuertas = ConfiguracionUI.crearCampoTexto();
		campoAncho.setPreferredSize(new Dimension(250, 5));

		formulario.add(ConfiguracionUI.crearCampoConLabel("Coste por hora de uso", campoCoste));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Dimensión plaza ancho", campoAncho));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Dimensión plaza largo", campoLargo));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Puertas de embarque", campoPuertas));

		JButton botonAgregarZona = ConfiguracionUI.crearBotonCentrado("Agregar zona de aparcamiento");
		botonAgregarZona.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String costeTexto = campoCoste.getText().trim();
				String largoTexto = campoLargo.getText().trim();
				String anchoTexto = campoAncho.getText().trim();
				String puertasTexto = campoPuertas.getText().trim();

				if (costeTexto.isEmpty() || largoTexto.isEmpty() || anchoTexto.isEmpty() || puertasTexto.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Por favor, rellena todos los campos antes de guardar.",
							"Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					double largo = Double.parseDouble(largoTexto);
					double coste = Double.parseDouble(costeTexto);
					double ancho = Double.parseDouble(anchoTexto);

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

					gestor.darDeAltaZonaAparcamiento(ancho, coste, largo, puertas);
					
					JOptionPane.showMessageDialog(null,
							"¡Zona de aparcamiento dada de alta correctamente!",
							"Éxito",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,
							"Altura o coste no tienen un formato válido.",
							"Error de formato numérico",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		panelPrincipal.add(formulario);
		panelPrincipal.add(botonAgregarZona, BorderLayout.SOUTH);

		add(panelPrincipal);
		setVisible(true);
	}

}
