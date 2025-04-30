package src.gui.gestor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.exceptions.PistaYaExisteException;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.Gestor;
import src.gui.ConfiguracionUI;

/**
 * La clase GestionINfraestructuraPistaDespegueGUI representa la ventana de
 * gestión de infraestructura (Pista de despegue) del gestor
 */
public class GestionInfraestructuraPistaDespegueGUI extends JFrame {
	/** Identificador de versión de clase para la serialización */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase GestionInfraestructuraPistaDespegueGUI
	 * 
	 * @param manager   el manager
	 * @param gestorGUI el gestorGUI
	 * @param gestor    el gestor
	 */
	public GestionInfraestructuraPistaDespegueGUI(AirportManager manager, GestorGUI gestorGUI, Gestor gestor) {
		setTitle("Panel de gestión de infraestructura (Pista de despegue) del gestor");
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
		JLabel titulo = ConfiguracionUI.crearTitulo("Gestión de infraestructura: Pista de despegue");

		JPanel cabecera = ConfiguracionUI.crearCabeceraPanel(titulo);
		panelPrincipal.add(cabecera, BorderLayout.NORTH);

		JPanel formulario = ConfiguracionUI.crearPanelTipoFormulario();

		JTextField campoCodigo = ConfiguracionUI.crearCampoTexto();
		campoCodigo.setPreferredSize(new Dimension(250, 5));
		JTextField campoLongitud = ConfiguracionUI.crearCampoTexto();
		campoLongitud.setPreferredSize(new Dimension(250, 5));

		formulario.add(ConfiguracionUI.crearCampoConLabel("Código", campoCodigo));
		formulario.add(ConfiguracionUI.crearCampoConLabel("Longitud", campoLongitud));

		JButton botonAgregarPistaDespegue = ConfiguracionUI.crearBotonCentrado("Agregar pista de despegue");

		botonAgregarPistaDespegue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String codigo = campoCodigo.getText().trim();
				String longitudTexto = campoLongitud.getText().trim();

				if (codigo.isEmpty() || longitudTexto.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Por favor, rellena todos los campos antes de guardar.",
							"Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				double longitud = Double.parseDouble(longitudTexto);

				try {
					gestor.darDeAltaPista(codigo, longitud, TipoPista.DESPEGUE);
					JOptionPane.showMessageDialog(null,
							"¡Pista de despegue dada de alta correctamente!",
							"Éxito",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (PistaYaExisteException e1) {

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Error: la pista de despegue ya existe.",
							"Pista de despegeue ya existente",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		panelPrincipal.add(formulario);
		panelPrincipal.add(botonAgregarPistaDespegue, BorderLayout.SOUTH);

		add(panelPrincipal);
		setVisible(true);

	}

}
