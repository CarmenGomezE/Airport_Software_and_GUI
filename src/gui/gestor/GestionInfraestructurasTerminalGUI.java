package src.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.TerminalYaExisteException;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionInfraestructurasTerminalGUI representa la ventana de
 * infraestructura (terminal) del gestor
 */
public class GestionInfraestructurasTerminalGUI extends JFrame {
	/** Identificador de versión de clase para la serialización */
	private static final long serialVersionUID = 1L;
	/** Tipo seleccionado */
	private String tipoSeleccionado;

	/**
	 * Constructor de la clase GestionInfraestructuraTerminalGUI
	 * 
	 * @param manager   el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor    el gestor
	 */
	public GestionInfraestructurasTerminalGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		setTitle("Panel de gestión de infraestructura (terminal) del Gestor");
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
		JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Terminal");
		JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);

		panelPrincipal.add(cabecera, BorderLayout.NORTH);

		JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

		String[] tipos = { "Pasajeros", "Mercancías" };
		JComboBox<String> tipoTerminal = new JComboBox<>(tipos);

		JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
		JTextField campoAforo = ConfiguracionUI.crearCampoTexto();
		JTextField campoPuertasEmbarque = ConfiguracionUI.crearCampoTexto();

		formulario.add(ConfiguracionUI.crearCampoConLabel("Código", campoCodigo));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Tipo", tipoTerminal));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Puertas de embarque", campoPuertasEmbarque));

		JPanel panelOpcional = new JPanel();
		panelOpcional.setLayout(new BoxLayout(panelOpcional, BoxLayout.Y_AXIS));
		panelOpcional.setBackground(ConfiguracionUI.COLOR_FONDO);
		panelOpcional.setPreferredSize(new Dimension(250, 100));
		formulario.add(panelOpcional);

		tipoTerminal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tipoSeleccionado = (String) tipoTerminal.getSelectedItem();

				panelOpcional.removeAll();

				if (tipoSeleccionado.equals("Pasajeros")) {
					panelOpcional.add(ConfiguracionUI.crearCampoConLabel("Aforo", campoAforo));

				}

				panelOpcional.revalidate();
				panelOpcional.repaint();
			}
		});

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

		JButton botonAgregarTerminal = ConfiguracionUI.crearBotonCentrado("Agregar terminal");

		botonAgregarTerminal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String codigo = campoCodigo.getText().trim();
				String aforoTexto = campoAforo.getText().trim();
				String puertasTexto = campoPuertasEmbarque.getText().trim();

				if (tipoSeleccionado == null || codigo.isEmpty() || puertasTexto.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Por favor, rellena todos los campos",
							"Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

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

				if (tipoSeleccionado.equals("Pasajeros")) {
					if (aforoTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Por favor, rellena el aforo",
								"Campos vacíos",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					int aforo = Integer.parseInt(aforoTexto);
					try {
						gestor.darDeAltaTerminal(codigo, "Pasajeros", aforo, puertas);
						JOptionPane.showMessageDialog(null,
								"¡Terminal dada de alta correctamente!",
								"Éxito",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (TerminalYaExisteException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Error: la terminal ya existe.",
								"Terminal ya existente",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				else {

					try {
						gestor.darDeAltaTerminal(codigo, "Mercancías", 0, puertas);
						JOptionPane.showMessageDialog(null,
								"¡Terminal dada de alta correctamente!",
								"Éxito",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (TerminalYaExisteException e1) {

						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Error: la terminal ya existe.",
								"Terminal ya existente",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		});

		JButton botonCrearPuerta = new JButton("Crear puerta");
		botonCrearPuerta.setMaximumSize(new Dimension(150, 30));
		botonCrearPuerta.setAlignmentX(Component.CENTER_ALIGNMENT);

		botonCrearPuerta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				PuertaEmbarque p = new PuertaEmbarque();
				gestor.getAeropuerto().addPuertaDeEmbarque(p);
				JOptionPane.showMessageDialog(null,
						"¡Puerta con id: " + p.getId() + " creada correctamente",
						"Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			}

		});

		panelBotones.add(Box.createVerticalStrut(10));
		panelBotones.add(botonCrearPuerta);
		panelBotones.add(Box.createVerticalStrut(10));
		panelBotones.add(botonAgregarTerminal);
		panelBotones.add(Box.createVerticalStrut(10));

		panelPrincipal.add(formulario);
		panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

		add(panelPrincipal);

		setVisible(true);

	}

}
