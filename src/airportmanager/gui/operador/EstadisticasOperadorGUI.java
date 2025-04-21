package src.airportmanager.gui.operador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import src.airportmanager.AirportManager;
import src.airportmanager.gui.ConfiguracionUI;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.vuelo.Vuelo;
import src.airportmanager.usuario.Operador;

/**
 * La clase EstadisticasOperadorGUI representa la ventana del operador desde la que este puede consultar
 * las estadísticas de los vuelos de su aerolínea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasOperadorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final OperadorGUI opGUI;
    private final Operador operador;
    private final AirportManager manager;

    private DefaultListModel<String> model;
    private JComboBox<Month> mesComboBox;
    private JComboBox<ZoneId> franjaComboBox;
    private JComboBox<Vuelo> vueloComboBox;

    private Month mesSeleccionado;
    private ZoneId franjaSeleccionada;
    private Vuelo vueloSeleccionado;

    public EstadisticasOperadorGUI(AirportManager manager, OperadorGUI operadorGUI, Operador operadorAerolinea) {
        this.opGUI = operadorGUI;
        this.operador = operadorAerolinea;
        this.manager = manager;

        setTitle("Panel de estadísticas del Operador");
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 300, 40, 300));
        panel.setBackground(ConfiguracionUI.COLOR_FONDO);
        add(panel);

        JLabel titulo = new JLabel("Estadísticas para " + operador.getNombre() + ":", JLabel.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        model = new DefaultListModel<>();
        actualizarEstadisticas();

        JList<String> listaNotificaciones = new JList<String>(model);
        listaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNotificaciones.setVisibleRowCount(10);
        JScrollPane scrollPane = new JScrollPane(listaNotificaciones);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);

        crearComponentesDeSeleccion(panel);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setBackground(ConfiguracionUI.COLOR_FONDO);
        JButton botonRefrescar = new JButton("🔄 Refrescar estadísticas");
        botonRefrescar.setBackground(ConfiguracionUI.COLOR_FONDO);
        botonRefrescar.setFont(botonRefrescar.getFont().deriveFont(16f));
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

    private void crearComponentesDeSeleccion(JPanel panel) {
        JPanel seleccionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        seleccionPanel.setBackground(ConfiguracionUI.COLOR_FONDO);

        mesComboBox = new JComboBox<Month>(Month.values());
        mesComboBox.setPreferredSize(new Dimension(200, 30));
        mesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mesSeleccionado = (Month) mesComboBox.getSelectedItem();
            }
        });

        ZoneId[] franjas = obtenerFranjasHorariasDisponibles();
        franjaComboBox = new JComboBox<ZoneId>(franjas);
        franjaComboBox.setPreferredSize(new Dimension(200, 30));
        franjaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                franjaSeleccionada = (ZoneId) franjaComboBox.getSelectedItem();
            }
        });

        Vuelo[] vuelos = obtenerVuelosDisponibles();
        vueloComboBox = new JComboBox<Vuelo>(vuelos);
        vueloComboBox.setPreferredSize(new Dimension(200, 30));
        vueloComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueloSeleccionado = (Vuelo) vueloComboBox.getSelectedItem();
            }
        });

        seleccionPanel.add(new JLabel("Mes:"));
        seleccionPanel.add(mesComboBox);

        seleccionPanel.add(new JLabel("Franja Horaria:"));
        seleccionPanel.add(franjaComboBox);

        seleccionPanel.add(new JLabel("Vuelo:"));
        seleccionPanel.add(vueloComboBox);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(seleccionPanel);
    }

    private Vuelo[] obtenerVuelosDisponibles() {
        ArrayList<Vuelo> lista = new ArrayList<Vuelo>();
        List<Vuelo> aceptados = manager.getAeropuerto().getVuelosAceptados();

        for (int i = 0; i < aceptados.size(); i++) {
            Vuelo v = aceptados.get(i);
            if (v.getAvion().getAerolinea().equals(operador.getAerolinea())) {
                lista.add(v);
            }
        }

        Vuelo[] array = new Vuelo[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i);
        }

        return array;
    }

    private ZoneId[] obtenerFranjasHorariasDisponibles() {
        List<AeropuertoDestino> destinos = manager.getAeropuerto().getAeropuertosDestino();
        Set<ZoneId> zonas = new HashSet<ZoneId>();

        for (int i = 0; i < destinos.size(); i++) {
            zonas.add(destinos.get(i).getDiferenciaHoraria());
        }

        ArrayList<ZoneId> ordenadas = new ArrayList<ZoneId>(zonas);

        ZoneId[] array = new ZoneId[ordenadas.size()];
        for (int i = 0; i < ordenadas.size(); i++) {
            array[i] = ordenadas.get(i);
        }

        return array;
    }

    private void actualizarEstadisticas() {
        model.clear();

        model.addElement("Número de vuelos en hora: " + operador.getEstadisticasOperador().numeroVuelosEnHora());
        model.addElement("Número de vuelos retrasados: " + operador.getEstadisticasOperador().numeroVuelosRetrasados());

        if (mesSeleccionado != null) {
            double tiempoMes = operador.getEstadisticasOperador().getTiempoMedioRetrasoPorMes(mesSeleccionado);
            model.addElement("Tiempo medio de retraso en " + mesSeleccionado.name() + ": " + tiempoMes + " minutos");
        }

        if (franjaSeleccionada != null) {
            double tiempoFranja = operador.getEstadisticasOperador().getTiempoMedioRetrasoPorFranjaHoraria(franjaSeleccionada);
            model.addElement("Tiempo medio de retraso en franja horaria " + franjaSeleccionada + ": " + tiempoFranja + " minutos");
        }

        if (vueloSeleccionado != null) {
            double tiempoVuelo = operador.getEstadisticasOperador().getTiempoMedioRetrasoPorVuelo(vueloSeleccionado);
            model.addElement("Tiempo medio de retraso para vuelo " + vueloSeleccionado + ": " + tiempoVuelo + " minutos");
        }
    }
}
