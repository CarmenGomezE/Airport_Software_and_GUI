package src.airportmanager.gui.operador;

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
import java.util.Map;

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
import javax.swing.JTextField;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;

/**
 * La clase AlternativasVueloGUI representa la ventana del operador desde la que este puede gestionar
 * las alternativas a vuelos que no se han podido programar.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class AlternativasVueloGUI extends JFrame {
	/** Identificador de version de clase */
    private static final long serialVersionUID = 1L;
    /** Interfaz de la ventana principal del operador */
    private final OperadorGUI opGUI;
    /** Operador */
    private final Operador operador;
    /** Modelo de la lista */
    private DefaultListModel<String> modeloLista;
    /** Lista de alternativas */
    private JList<String> listaAlternativas;
    /** Hueco para introducir el código del vuelo */
    private JTextField campoCodigo;
    
    /**
     * Constructor de la ventana AlternativasVueloGUI.
     * 
     * @param manager El objeto AirportManager que gestiona los datos y operaciones.
     * @param operadorGUI La interfaz gráfica del operador.
     * @param operadorAerolinea El operador que gestiona los vuelos.
     */
    public AlternativasVueloGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;
        
        setTitle("Panel de alternativas de vuelo de Operador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                opGUI.setVisible(true);
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

        JLabel listaAlternativasTitulo = ConfiguracionUI.crearTitulo("Alternativas a los vuelos propuestos");
        
        panelIzquierdo.add(listaAlternativasTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaAlternativas = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAlternativas);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = ConfiguracionUI.crearBotonCentrado("🔄 Refrescar alternativas");
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

        JLabel titulo = ConfiguracionUI.crearTitulo("Operador: " + operador.getNombre());
       
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

        JButton botonAceptar = ConfiguracionUI.crearBotonCentrado("✅ Aceptar alternativa");
        
        panelDerecho.add(botonAceptar);

        JButton botonRechazar = ConfiguracionUI.crearBotonCentrado("❌ Rechazar alternativa");
        
        panelDerecho.add(botonRechazar);

        panelDerecho.add(Box.createVerticalGlue());

        /** Acción aceptar */
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                Vuelo vuelo = operador.buscarAlternativaPorCodigo(codigo);
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "No se encontró ninguna alternativa con ese código.");
                    return;
                }

                try {
                    operador.aceptarVuelo(vuelo);
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Alternativa aceptada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Error al aceptar: " + ex.getMessage());
                }
            }
        });

        /** Acción rechazar */
        botonRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Introduce un código de vuelo.");
                    return;
                }

                VueloProgramado vuelo = operador.buscarVueloProgramadoPorCodigo(codigo);
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "No se encontró ninguna alternativa con ese código.");
                    return;
                }

                try {
                    operador.rechazarVuelo(vuelo);
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Alternativa rechazada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Error al rechazar: " + ex.getMessage());
                }
            }
        });

        /** Añadir los paneles al principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);

        /** Hacer la ventana visible */
        setVisible(true);
    }

    /**
     * Método que actualiza la lista de alternativas de vuelos pendientes.
     */
    private void actualizarLista() {
        modeloLista.clear();
        Map<VueloProgramado, List<Vuelo>> alternativas = operador.getAlternativasAgrupadas();

        if (alternativas.isEmpty()) {
            modeloLista.addElement("No hay alternativas pendientes.");
            return;
        }
        for (Map.Entry<VueloProgramado, List<Vuelo>> entry : alternativas.entrySet()) {
            VueloProgramado original = entry.getKey();
            List<Vuelo> propuestas = entry.getValue();
            modeloLista.addElement(" Alternativas para vuelo original: " + original.getCodigo());
            for (Vuelo v : propuestas) {
                modeloLista.addElement("Vuelo: " + v.getCodigo() + " | Terminal: " + v.getTerminal() + " | Fecha/Hora: " + v.getFecha() + "/" + v.getHora());
            }
        }
    }
}
