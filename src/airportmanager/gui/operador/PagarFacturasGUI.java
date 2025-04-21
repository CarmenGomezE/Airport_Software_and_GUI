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

import es.uam.eps.padsof.telecard.*;
import src.airportmanager.AirportManager;
import src.airportmanager.factura.Factura;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.usuario.Operador;

/**
 * La clase PagarFacturasGUI representa la ventana del operador desde la que este paga las facturas
 * que el gestor ha emitido para su aerolinea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class PagarFacturasGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private OperadorGUI opGUI;
    private Operador operador;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaFacturas;
    private JTextField campoCodigo;
    private JTextField campoTarjeta;
    
	public PagarFacturasGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
		this.opGUI=operadorGUI;
		this.operador=operadorAerolinea;
		
        setTitle("Panel pago de facturas del Operador");
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

        JLabel listaFacturasTitulo = new JLabel("Facturas de la aerolínea "+operador.getAerolinea().getNombre(), JLabel.CENTER);
        listaFacturasTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelIzquierdo.add(listaFacturasTitulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        actualizarLista();

        listaFacturas = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaFacturas);
        panelIzquierdo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(ConfiguracionUI.COLOR_FONDO);

        JButton botonRefrescar = new JButton("🔄 Refrescar facturas");
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
        
        JLabel labelTarjeta = new JLabel("Número de tarjeta:");
        labelTarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(labelTarjeta);
        
        campoTarjeta = new JTextField();
        campoTarjeta.setMaximumSize(new Dimension(1000, 800));
        campoTarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(campoTarjeta);

        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botonPagar = new JButton("Pagar factura");
        botonPagar.setMaximumSize(new Dimension(1000, 800));
        botonPagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(botonPagar);

        panelDerecho.add(Box.createVerticalGlue());
        

        /** Acción pagar */
        botonPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText().trim();
                String tarjeta = campoTarjeta.getText().trim();

                if (codigo.isEmpty() || tarjeta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Introduce el código de la factura y el número de tarjeta.");
                    return;
                }

                Factura factura = operador.getAerolinea().buscarFacturaPorCodigo(codigo);
                if (factura == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna factura con ese código.");
                    return;
                }

                try {
                    TeleChargeAndPaySystem.isValidCardNumber(tarjeta);
                    TeleChargeAndPaySystem.charge(tarjeta, factura.getInvoiceIdentifier(), factura.getPrice());
                    factura.marcarComoPagada();
                    JOptionPane.showMessageDialog(null, "Factura pagada correctamente.");
                } catch (InvalidCardNumberException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Número de tarjeta inválido.");
                } catch (FailedInternetConnectionException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Conexión a Internet fallida.");
                } catch (OrderRejectedException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El pago fue rechazado.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error desconocido: " + ex.getMessage());
                }
            }
        });

        /** Añadir los paneles al principal */
        panel.add(panelIzquierdo, BorderLayout.WEST);
        panel.add(panelDerecho, BorderLayout.CENTER);
        
        /** Visible */
        setVisible(true);
	}
	 private void actualizarLista() {
	        modeloLista.clear();
	        List<Factura> facturas = operador.getAerolinea().getFacturas();
	        for (Factura f : facturas) {
	            modeloLista.addElement("Factura: " + f.getInvoiceIdentifier() + " | Estado: " + f.getEstado() + " | Precio: " + f.getPrice());
	        }
	    }
}
