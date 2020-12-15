package ec.edu.ups.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ec.edu.ups.tobar.EN.ENTicket;
import ec.edu.ups.tobar.EN.ENVehiculo;
import ec.edu.ups.tobar.Gestion.GestionPaqueaderaRemoto;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VTNIngresoVehiculo extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigoT;
	private JTextField txtFecha;
	private JTextField txtHora;
	private JTextField txtPlaca;
	private JTextField txtMarca;
	private JTextField txtDescripcion;
	private JTable tblVehiculos;
	private GestionPaqueaderaRemoto gestionParqueadero;

	/**
	 * Launch the application.
	 */
	public void conectar() throws Exception {
		try {
			final Hashtable<String, Comparable> jndiProperties = new Hashtable<String, Comparable>();

			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProperties.put("jboss.naming.client.ejb.context", true);

			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			jndiProperties.put(Context.SECURITY_PRINCIPAL, "ejb");
			jndiProperties.put(Context.SECURITY_CREDENTIALS, "cuenca");

			final Context context = new InitialContext(jndiProperties);

			final String lookupName = "ejb:/Parqueadero/TicVeiON!ec.edu.ups.tobar.Gestion.GestionPaqueaderaRemoto";

			this.gestionParqueadero = (GestionPaqueaderaRemoto) context.lookup(lookupName);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		}
	}

	public void optimizar() {

		txtPlaca.requestFocus();
		deshabilitarTextos();
		// Codigo automatico
		String codigo = "0000000" + this.gestionParqueadero.obtenerCodigo();
		int cant = codigo.length();
		txtCodigoT.setText(codigo.substring(cant - 8, cant));

		// Fecha Automatica
		Calendar c = new GregorianCalendar();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
		String anio = Integer.toString(c.get(Calendar.YEAR));
		txtFecha.setText(anio + "-" + mes + "-" + dia);
		// hora Automatica
		horaAutomatica();

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// VTNIngresoVehiculo inVehiculo = new VTNIngresoVehiculo();

				try {
					VTNIngresoVehiculo frame = new VTNIngresoVehiculo();
					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
					System.out.print("" + e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VTNIngresoVehiculo() {
		try {
			this.conectar();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel jpanel2 = new JPanel();
		contentPane.add(jpanel2, BorderLayout.CENTER);
		jpanel2.setLayout(null);

		JLabel lblCodigo = new JLabel("CODIGO");
		lblCodigo.setBounds(24, 11, 65, 23);
		jpanel2.add(lblCodigo);

		txtCodigoT = new JTextField();
		txtCodigoT.setBounds(99, 12, 126, 20);
		jpanel2.add(txtCodigoT);
		txtCodigoT.setColumns(10);

		JLabel lblFechaActual = new JLabel("FECHA ACTUAL");
		lblFechaActual.setBounds(237, 15, 86, 14);
		jpanel2.add(lblFechaActual);

		JLabel lblHoraIngreso = new JLabel("HORA INGRESO");
		lblHoraIngreso.setBounds(237, 53, 86, 14);
		jpanel2.add(lblHoraIngreso);

		txtFecha = new JTextField();
		txtFecha.setBounds(333, 12, 123, 20);
		jpanel2.add(txtFecha);
		txtFecha.setColumns(10);

		txtHora = new JTextField();
		txtHora.setBounds(333, 50, 97, 20);
		jpanel2.add(txtHora);
		txtHora.setColumns(10);

		JLabel lblDatosVehiculo = new JLabel("DATOS VEHICULO");
		lblDatosVehiculo.setBounds(181, 91, 171, 23);
		jpanel2.add(lblDatosVehiculo);

		JLabel lblPlaca = new JLabel("PLACA");
		lblPlaca.setBounds(24, 118, 46, 14);
		jpanel2.add(lblPlaca);

		JLabel lblMarca = new JLabel("MARCA");
		lblMarca.setBounds(24, 152, 46, 14);
		jpanel2.add(lblMarca);

		JLabel lblDescripcion = new JLabel("DESCRIPCION");
		lblDescripcion.setBounds(24, 191, 86, 14);
		jpanel2.add(lblDescripcion);

		final JLabel lblMensajes = new JLabel("");
		lblMensajes.setBounds(24, 226, 462, 23);
		jpanel2.add(lblMensajes);

		txtPlaca = new JTextField();

		// Placa
		txtPlaca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				ENVehiculo v = new ENVehiculo();
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					String plac = txtPlaca.getText();
					System.out.println("d" + plac.length());
					if (plac.length() < 7) {
						lblMensajes.setText("Error en el formato de incompleto");
					} else {
						if (plac.substring(3, 4).equals("-")) {
							horaAutomatica();
							if (gestionParqueadero.placaRepetida(txtPlaca.getText())) {
								if (txtPlaca.getText().length() > 6) {
		                            v.setVePlaca(plac);
		                            txtPlaca.setText("" + gestionParqueadero.obtenerUnVehiculo(v).getVePlaca());
		                            txtDescripcion.setText("" + gestionParqueadero.obtenerUnVehiculo(v).getVeMarca());
		                            txtMarca.setText("" + gestionParqueadero.obtenerUnVehiculo(v).getVeDescripcion());
		                            lblMensajes.setText("Información ya registrada");
								} else {
									limpiarTabla(tblVehiculos);
									listarporPlaca();
								}
							} else {
								txtMarca.setText("");
								txtDescripcion.setText("");
							}
							txtMarca.requestFocus();
						} else {
							lblMensajes.setText("Error en el formato de la placa, Ejemplo 'ABC-1234'");
						}
					}
				}

			}
		});

		txtPlaca.setBounds(126, 115, 143, 20);
		jpanel2.add(txtPlaca);
		txtPlaca.setColumns(10);

		txtMarca = new JTextField();
		txtMarca.setBounds(126, 149, 182, 20);
		jpanel2.add(txtMarca);
		txtMarca.setColumns(10);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(126, 188, 182, 20);
		jpanel2.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		tblVehiculos = new JTable();
		tblVehiculos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "N", "CODIGO", "FECHA", "HORA", "PLACA", "MARCA", "DESCRIPCION" }));
		tblVehiculos.setBounds(24, 340, 477, 144);
		jpanel2.add(tblVehiculos);

		JButton btnGuardar = new JButton("GUARDAR");

		optimizar();
		listar();

		btnGuardar.addActionListener(new ActionListener() {

			// PARA EL BOTON GUARADAR
			public void actionPerformed(ActionEvent e) {
				if (txtPlaca.getText().isEmpty() == false && txtMarca.getText().isEmpty() == false
						&& txtDescripcion.getText().isEmpty() == false) {
					horaAutomatica();
					ENTicket t = new ENTicket();
					ENVehiculo v = new ENVehiculo();
					t.setTiFechaActual(txtFecha.getText());
					t.setTiHoraIngreso(txtHora.getText());
					t.setTiCodigo(txtCodigoT.getText());
					v.setVePlaca(txtPlaca.getText());
					v.setVeDescripcion(txtDescripcion.getText());
					v.setVeMarca(txtMarca.getText());
					t.setVehiculoveID(v);
					gestionParqueadero.guardarVehiculo(v);
					gestionParqueadero.guardarTicket(t);
					JOptionPane.showMessageDialog(null, "VEHÍCULO INGRESADO");
					limpiarTabla(tblVehiculos);
					listar();
					txtPlaca.setText("");
					txtMarca.setText("");
					txtDescripcion.setText("");
					optimizar();
					lblMensajes.setText("Vehículo ingresado");

				} else {
					lblMensajes.setText("Vehículo no ingresado, los datos del vehículo están vacíos.");
					txtPlaca.requestFocus();
				}

			}
		});

		btnGuardar.setBounds(41, 276, 123, 52);
		jpanel2.add(btnGuardar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(193, 276, 115, 52);
		jpanel2.add(btnCancelar);

		JButton btnCobrar = new JButton("COBRAR");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				InterfazCobrar cbr = new InterfazCobrar();
				cbr.setVisible(true);
			}
		});
		btnCobrar.setBounds(333, 276, 117, 52);
		jpanel2.add(btnCobrar);

	}

	public void horaAutomatica() {
		Calendar c = new GregorianCalendar();
		int hora = c.get(Calendar.HOUR_OF_DAY);
		int minuto = c.get(Calendar.MINUTE);
		int segundo = c.get(Calendar.SECOND);
		txtHora.setText(hora + ":" + minuto + ":" + segundo);
	}

	public void deshabilitarTextos() {
		txtCodigoT.setEditable(false);
		txtFecha.setEditable(false);
		txtHora.setEditable(false);
	}

	public void listar() {
		this.limpiarTabla(tblVehiculos);

		Object fila[] = new Object[gestionParqueadero.listarTicket()[0].length];
		DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
		for (int i = 0; i < gestionParqueadero.listarTicket().length; i++) {
			for (int j = 0; j < gestionParqueadero.listarTicket()[0].length; j++) {
				fila[j] = gestionParqueadero.listarTicket()[i][j];
			}
			modelo.addRow(fila);
		}
		tblVehiculos.setModel(modelo);
	}

	public void listarporPlaca() {
		this.limpiarTabla(tblVehiculos);

		Object listaPlacas[][] = gestionParqueadero.listarVehiculo(txtPlaca.getText());
		Object fila[] = new Object[listaPlacas[0].length];
		DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
		for (int i = 0; i < listaPlacas.length; i++) {
			for (int j = 0; j < listaPlacas[0].length; j++) {
				fila[j] = listaPlacas[i][j];
			}
			System.out.println("ingresa la fila");
			modelo.addRow(fila);
		}
		tblVehiculos.setModel(modelo);
	}

	public void limpiarTabla(JTable tabla) {
		try {
			DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
			int filas = tabla.getRowCount();
			for (int i = 0; filas > i; i++) {
				modelo.removeRow(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
		}
	}
}
