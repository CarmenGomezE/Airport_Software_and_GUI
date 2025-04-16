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
import src.airportmanager.vuelo.Vuelo;

public class AlternativasVueloGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private OperadorGUI opGUI;
    private Operador operador;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaAlternativas;
    private JTextField campoCodigo;
    
	public AlternativasVueloGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
		this.opGUI=operadorGUI;
		this.operador= operadorAerolinea;
		
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

        JLabel listaAlternativasTitulo = new JLabel("Alternativas a los vuelos propuestos", JLabel.CENTER);
        listaAlternativasTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaAlternativasTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaAlternativas = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaAlternativas);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = new JButton("🔄 Refrescar alternativas");
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

        JLabel titulo = new JLabel("Operador: " + operador.getNombre(), JLabel.CENTER);
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

        JButton botonAceptar = new JButton("✅ Aceptar alternativa");
        botonAceptar.setMaximumSize(new Dimension(1000, 800));
        botonAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(botonAceptar);

        JButton botonRechazar = new JButton("❌ Rechazar alternativa");
        botonRechazar.setMaximumSize(new Dimension(1000, 800));
        botonRechazar.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                Vuelo vuelo= manager.buscarVueloPorCodigo(codigo);
                
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "No se encontró ninguna alternativa a ese vuelo.");
                    return;
                }

                try {
                    operador.aceptarVuelo(vuelo);
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Alternativa aceptada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Error al aceptar alternativa: " + ex.getMessage());
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
                Vuelo vuelo= manager.buscarVueloPorCodigo(codigo);
                
                if (vuelo == null) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "No se encontró ninguna alternativa a ese vuelo.");
                    return;
                }

                try {
                    operador.rechazarVuelo();
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Alternativa rechazada correctamente.");
                    actualizarLista();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AlternativasVueloGUI.this, "Error al rechazar alternativa: " + ex.getMessage());
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
        List<Vuelo> vuelos = operador.getAlternativas();
        for (Vuelo v : vuelos) {
            modeloLista.addElement("Vuelo: " + v.getCodigo() + " | Terminal: " + v.getTerminal() + " | Fecha/Hora: " + v.getFecha()+"/"+v.getHora());
        }
    }
}