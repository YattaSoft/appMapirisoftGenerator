package com.apptriggergenerator.triggergenerator.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frm_Generador extends JFrame {
	private JLabel jLabel1 = new JLabel();
	private JTextField jtf_user = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jtf_host = new JTextField();
	private JLabel jLabel3 = new JLabel();
	private JLabel jLabel4 = new JLabel();
	private JPasswordField jtf_password = new JPasswordField();
	private JButton jbt_bases_datos = new JButton();
	clsConexion clsConexion;
	clsBase clsBase;
	private JComboBox jcb_bases_datos = new JComboBox();
	private JLabel jLabel5 = new JLabel();
	private JComboBox jcb_tablas = new JComboBox();
	private JLabel jLabel6 = new JLabel();
	private JScrollPane jsp_atributos = new JScrollPane();
	private JTable jtb_atributos = new JTable();
	Vector vec_op_tipo_control, vec_op_tipo_dato, vec_op_nullable, vec_op_tabla_referenciada, vec_op_visibilidad, vec_datos, vec_aux_encabezado, vec_aux_column_size, vec_aux_tablas;
	private JButton jbt_generar = new JButton();
	private JCheckBox jcb_generar_clase = new JCheckBox();
	private JCheckBox jcb_generar_formulario = new JCheckBox();
	private JTextField jtf_directorio_clase = new JTextField();
	private JTextField jtf_directorio_formulario = new JTextField();
	private JButton jbt_buscar_directorio_clase = new JButton();
	private JButton jbt_buscar_directotio_formulario = new JButton();
	private File vg_fl_directorio_clases, vg_fl_directorio_formularios;
	String str_initial_path;
	JComboBox vg_jcb_aux_true_false, vg_jcb_aux_true_false_2;
	cls_generador cls_generador;
	private JCheckBox cb_maestro_detalle = new JCheckBox();
	private JComboBox jcb_llave = new JComboBox();
	private JLabel jLabel8 = new JLabel();
	Vector vg_aux_master_detail;
	Vector vg_aux_headerfields;
	private JComboBox jcb_headerfields = new JComboBox();
	private JTextField jtf_newfield = new JTextField();
	private JButton jbt_addfield = new JButton();
	private JButton jbt_deletefield = new JButton();
	private JCheckBox cb_textarea = new JCheckBox();
	private JTextField jtf_etiqueta = new JTextField();
	private JLabel jLabel9 = new JLabel();
	private JScrollPane jscp_add_master_detail = new JScrollPane();
	private JScrollPane jscp_add_item = new JScrollPane();
	private JTextArea jta_add_master_detail = new JTextArea();
	private JTextArea jta_add_item = new JTextArea();
	private JCheckBox cb_complex_query = new JCheckBox();
	private JComboBox jcb_master_atributes = new JComboBox();
	private JButton jButton1 = new JButton();

	public frm_Generador(clsConexion clsConexion) {
		this.clsConexion = clsConexion;
		clsBase = new clsBase(clsConexion);
		cls_generador = new cls_generador();
		vec_op_tipo_dato = new Vector();
		vec_op_nullable = new Vector();
		vec_op_visibilidad = new Vector();
		vec_aux_column_size = new Vector();
		vec_aux_encabezado = new Vector();
		vec_op_tipo_control = new Vector();
		vec_op_tipo_dato.add("int");
		vec_op_tipo_dato.add("double");
		vec_op_tipo_dato.add("String");
		vec_op_tipo_dato.add("Date");
		vec_op_tipo_dato.add("Time");
		vec_op_tipo_dato.add("Datetime");
		vec_op_nullable.add("true");
		vec_op_nullable.add("false");
		vec_op_visibilidad.add("visible");
		vec_op_visibilidad.add("readonly");
		vec_op_visibilidad.add("hidden");
		vec_aux_encabezado.add("Nombre Columna");
		vec_aux_encabezado.add("Etiqueta");
		vec_aux_encabezado.add("Null");
		vec_aux_encabezado.add("Tipo");
		vec_aux_encabezado.add("Limite");
		vec_aux_encabezado.add("Tabla Referenciada");
		vec_aux_encabezado.add("Visibilidad");
		vec_aux_encabezado.add("Control");
		vec_aux_encabezado.add("Lista");
		vec_aux_encabezado.add("Ancho");
		vec_aux_encabezado.add("Combo");
		vec_aux_column_size.add("150");
		vec_aux_column_size.add("150");
		vec_aux_column_size.add("50");
		vec_aux_column_size.add("50");
		vec_aux_column_size.add("50");
		vec_aux_column_size.add("150");
		vec_aux_column_size.add("75");
		vec_aux_column_size.add("75");
		vec_aux_column_size.add("50");
		vec_aux_column_size.add("50");
		vec_aux_column_size.add("50");
		vec_op_tipo_control.add("");
		vec_op_tipo_control.add("combobox");
		vec_op_tipo_control.add("textfield");
		vec_op_tipo_control.add("textarea");
		vec_op_tipo_control.add("datepicker");
		vec_op_tipo_control.add("timepicker");
		vec_op_tipo_control.add("datetimepicker");
		vec_op_tipo_control.add("autoincrement");
		str_initial_path = "C:\\Generador";
		vg_fl_directorio_clases = new File(str_initial_path);
		vg_fl_directorio_clases.mkdir();
		vg_fl_directorio_formularios = new File(str_initial_path);
		jtf_directorio_clase.setText(str_initial_path);
		jtf_directorio_formulario.setText(str_initial_path);
		try {
			MyInit();
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void MyInit() {
		vg_jcb_aux_true_false = new JComboBox(vec_op_nullable);
		vg_jcb_aux_true_false.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg_jcb_aux_true_false(e);
			}
		});
		vg_jcb_aux_true_false_2 = new JComboBox(vec_op_nullable);
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(923, 691));
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		jLabel1.setText("User :");
		jLabel1.setBounds(new Rectangle(145, 60, 85, 20));
		jLabel1.setFont(new Font("Tahoma", 1, 12));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
		jtf_user.setBounds(new Rectangle(235, 60, 220, 20));
		jtf_user.setHorizontalAlignment(JTextField.LEFT);
		jtf_user.setFont(new Font("Tahoma", 0, 12));
		jtf_user.setText("cromisoft");
		jLabel2.setText("Host :");
		jLabel2.setBounds(new Rectangle(145, 35, 85, 20));
		jLabel2.setFont(new Font("Tahoma", 1, 12));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
		jtf_host.setBounds(new Rectangle(235, 35, 220, 20));
		jtf_host.setHorizontalAlignment(JTextField.LEFT);
		jtf_host.setFont(new Font("Tahoma", 0, 12));
		jtf_host.setText("192.168.2.111");
		jLabel3.setText("Conexion con MySQL");
		jLabel3.setBounds(new Rectangle(10, 5, 900, 20));
		jLabel3.setFont(new Font("Tahoma", 1, 12));
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		jLabel4.setText("Password :");
		jLabel4.setBounds(new Rectangle(145, 85, 85, 20));
		jLabel4.setFont(new Font("Tahoma", 1, 12));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setHorizontalTextPosition(SwingConstants.RIGHT);
		jtf_password.setBounds(new Rectangle(235, 85, 220, 20));
		jtf_password.setHorizontalAlignment(JTextField.LEFT);
		jtf_password.setText("cromisoft");
		jbt_bases_datos.setText("Cargar Bases de Datos");
		jbt_bases_datos.setBounds(new Rectangle(560, 35, 220, 20));
		jbt_bases_datos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_bases_datos_actionPerformed(e);
			}
		});
		jcb_bases_datos.setBounds(new Rectangle(560, 60, 220, 20));
		jcb_bases_datos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_bases_datos_actionPerformed(e);
			}
		});
		jLabel5.setText("Database :");
		jLabel5.setBounds(new Rectangle(470, 60, 85, 20));
		jLabel5.setFont(new Font("Tahoma", 1, 12));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setHorizontalTextPosition(SwingConstants.RIGHT);
		jcb_tablas.setBounds(new Rectangle(560, 85, 220, 20));
		jcb_tablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_tablas_actionPerformed(e);
			}
		});
		jLabel6.setText("Table :");
		jLabel6.setBounds(new Rectangle(470, 85, 85, 20));
		jLabel6.setFont(new Font("Tahoma", 1, 12));
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setHorizontalTextPosition(SwingConstants.RIGHT);
		jsp_atributos.setBounds(new Rectangle(10, 335, 900, 230));
		jsp_atributos.setSize(new Dimension(900, 230));
		jbt_generar.setText("Generar");
		jbt_generar.setBounds(new Rectangle(270, 630, 95, 20));
		jbt_generar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_generar_actionPerformed(e);
			}
		});
		jcb_generar_clase.setText("Generar Clase?(Negocio)");
		jcb_generar_clase.setBounds(new Rectangle(105, 575, 150, 20));
		jcb_generar_clase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_generar_clase_actionPerformed(e);
			}
		});
		jcb_generar_formulario.setText("Generar Formulario?");
		jcb_generar_formulario.setBounds(new Rectangle(105, 600, 150, 20));
		jcb_generar_formulario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_generar_formulario_actionPerformed(e);
			}
		});
		jtf_directorio_clase.setBounds(new Rectangle(270, 575, 410, 20));
		jtf_directorio_clase.setFocusable(false);
		jtf_directorio_clase.setEnabled(false);
		jtf_directorio_formulario.setBounds(new Rectangle(270, 600, 410, 20));
		jtf_directorio_formulario.setFocusable(false);
		jtf_directorio_formulario.setEnabled(false);
		jbt_buscar_directorio_clase.setText("Buscar");
		jbt_buscar_directorio_clase.setBounds(new Rectangle(685, 575, 70, 20));
		jbt_buscar_directorio_clase.setEnabled(false);
		jbt_buscar_directorio_clase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_buscar_directorio_clase_actionPerformed(e);
			}
		});
		jbt_buscar_directotio_formulario.setText("Buscar");
		jbt_buscar_directotio_formulario.setBounds(new Rectangle(685, 600, 70, 20));
		jbt_buscar_directotio_formulario.setEnabled(false);
		jbt_buscar_directotio_formulario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_buscar_directotio_formulario_actionPerformed(e);
			}
		});
		cb_maestro_detalle.setText("Maestro Detalle");
		cb_maestro_detalle.setBounds(new Rectangle(560, 110, 220, 20));
		cb_maestro_detalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb_maestro_detalle_actionPerformed(e);
			}
		});
		jcb_llave.setBounds(new Rectangle(110, 125, 220, 20));
		jcb_llave.setEnabled(false);
		jcb_llave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_llave_actionPerformed(e);
			}
		});
		jLabel8.setText("Foranea :");
		jLabel8.setBounds(new Rectangle(20, 125, 85, 20));
		jLabel8.setFont(new Font("Tahoma", 1, 12));
		jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel8.setHorizontalTextPosition(SwingConstants.RIGHT);
		jcb_headerfields.setBounds(new Rectangle(10, 200, 875, 20));
		jcb_headerfields.setEnabled(false);
		jtf_newfield.setBounds(new Rectangle(10, 175, 875, 20));
		jtf_newfield.setEnabled(false);
		jbt_addfield.setText("+");
		jbt_addfield.setBounds(new Rectangle(10, 150, 20, 20));
		jbt_addfield.setMargin(new Insets(0, 0, 0, 0));
		jbt_addfield.setFont(new Font("Tahoma", 1, 11));
		jbt_addfield.setEnabled(false);
		jbt_addfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_addfield_actionPerformed(e);
			}
		});
		jbt_deletefield.setText("-");
		jbt_deletefield.setBounds(new Rectangle(890, 200, 20, 20));
		jbt_deletefield.setMargin(new Insets(0, 0, 0, 0));
		jbt_deletefield.setFont(new Font("Tahoma", 1, 11));
		jbt_deletefield.setEnabled(false);
		jbt_deletefield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_deletefield_actionPerformed(e);
			}
		});
		cb_textarea.setText("textarea?");
		cb_textarea.setBounds(new Rectangle(315, 150, 75, 20));
		cb_textarea.setEnabled(false);
		cb_textarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb_textarea_actionPerformed(e);
			}
		});
		jtf_etiqueta.setBounds(new Rectangle(110, 150, 200, 20));
		jtf_etiqueta.setEnabled(false);
		jLabel9.setText("Etiqueta :");
		jLabel9.setBounds(new Rectangle(20, 150, 85, 20));
		jLabel9.setFont(new Font("Tahoma", 1, 12));
		jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel9.setHorizontalTextPosition(SwingConstants.RIGHT);
		jscp_add_master_detail.setBounds(new Rectangle(10, 225, 445, 105));
		jscp_add_master_detail.setEnabled(false);
		jscp_add_item.setBounds(new Rectangle(465, 225, 445, 105));
		jta_add_master_detail.setEnabled(false);
		jta_add_master_detail.setEditable(false);
		jta_add_item.setEditable(false);
		cb_complex_query.setText("Consulta compleja?");
		cb_complex_query.setBounds(new Rectangle(395, 130, 135, 15));
		cb_complex_query.setEnabled(false);
		cb_complex_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb_complex_query_actionPerformed(e);
			}
		});
		jcb_master_atributes.setBounds(new Rectangle(395, 150, 230, 20));
		jcb_master_atributes.setEnabled(false);
		jcb_master_atributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcb_master_atributes_actionPerformed(e);
			}
		});
		jButton1.setText("jButton1");
		jscp_add_item.getViewport().add(jta_add_item, null);
		this.getContentPane().add(jcb_master_atributes, null);
		this.getContentPane().add(cb_complex_query, null);
		this.getContentPane().add(jscp_add_item, null);
		jscp_add_master_detail.getViewport().add(jta_add_master_detail, null);
		this.getContentPane().add(jscp_add_master_detail, null);
		this.getContentPane().add(jLabel9, null);
		this.getContentPane().add(jtf_etiqueta, null);
		this.getContentPane().add(cb_textarea, null);
		this.getContentPane().add(jbt_deletefield, null);
		this.getContentPane().add(jbt_addfield, null);
		this.getContentPane().add(jtf_newfield, null);
		this.getContentPane().add(jcb_headerfields, null);
		this.getContentPane().add(jLabel8, null);
		this.getContentPane().add(jcb_llave, null);
		this.getContentPane().add(cb_maestro_detalle, null);
		this.getContentPane().add(jbt_buscar_directotio_formulario, null);
		this.getContentPane().add(jbt_buscar_directorio_clase, null);
		this.getContentPane().add(jtf_directorio_formulario, null);
		this.getContentPane().add(jtf_directorio_clase, null);
		this.getContentPane().add(jcb_generar_formulario, null);
		this.getContentPane().add(jcb_generar_clase, null);
		this.getContentPane().add(jbt_generar, null);
		jsp_atributos.getViewport().add(jtb_atributos, null);
		this.getContentPane().add(jsp_atributos, null);
		this.getContentPane().add(jLabel6, null);
		this.getContentPane().add(jcb_tablas, null);
		this.getContentPane().add(jLabel5, null);
		this.getContentPane().add(jcb_bases_datos, null);
		this.getContentPane().add(jbt_bases_datos, null);
		this.getContentPane().add(jtf_password, null);
		this.getContentPane().add(jLabel4, null);
		this.getContentPane().add(jLabel3, null);
		this.getContentPane().add(jtf_host, null);
		this.getContentPane().add(jLabel2, null);
		this.getContentPane().add(jtf_user, null);
		this.getContentPane().add(jLabel1, null);
	}

	public void Conectar_Base_Datos() {
		try {
			clsConexion.conexion = null;
			clsConexion.usuario = jtf_user.getText();
			clsConexion.clave = jtf_password.getText();
			clsConexion.database = "information_schema";
			clsConexion.host = jtf_host.getText();
			clsConexion.Conectar2();
			Cargar_Bases_Datos();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public void Cargar_Bases_Datos() {
		jcb_bases_datos.removeAllItems();
		Vector aux_datos = clsBase.Consulta_General("select schema_name from schemata");
		jcb_bases_datos.removeAllItems();
		for (int i = 0; i < aux_datos.size(); i++) {
			jcb_bases_datos.addItem(((Vector) aux_datos.get(i)).get(0));
		}
	}

	public void Cargar_Tablas() {
		if (jcb_bases_datos.getSelectedIndex() != -1) {
			jcb_tablas.removeAllItems();
			Vector aux_datos = clsBase.Consulta_General("select table_name from tables where table_schema = '" + jcb_bases_datos.getSelectedItem() + "'");
			vec_aux_tablas = new Vector();
			jcb_tablas.removeAllItems();
			for (int i = 0; i < aux_datos.size(); i++) {
				jcb_tablas.addItem(((Vector) aux_datos.get(i)).get(0));
				vec_aux_tablas.add(((Vector) aux_datos.get(i)).get(0));
			}
			vec_aux_tablas.add(0, "");
		}
	}

	public void Cargar_Atributos() {
		if (jcb_tablas.getSelectedIndex() != -1) {
			Vector vc_aux_datos = clsBase.Consulta_General("SELECT c.column_name, CONCAT(UCASE(LEFT(c.column_name,1)),SUBSTRING(c.column_name,2)),\n"
			        + "CASE c.is_nullable WHEN 'NO' THEN 'false' ELSE 'true' END nullable, c.data_type, IFNULL(IFNULL(c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION),0) longitud,\n"
			        + "IFNULL((SELECT REFERENCED_TABLE_NAME FROM KEY_COLUMN_USAGE WHERE REFERENCED_TABLE_NAME IS NOT NULL AND table_schema = c.TABLE_SCHEMA AND table_name = c.table_name\n"
			        + "AND column_name = c.column_name),'') foranea, IFNULL(c.numeric_scale,'0')\n" + "FROM COLUMNS c WHERE c.table_schema = '" + jcb_bases_datos.getSelectedItem()
			        + "' AND c.table_name = '" + jcb_tablas.getSelectedItem() + "' ORDER BY c.ORDINAL_POSITION");
			vec_datos = ActualizarInformacion(vc_aux_datos);
			int[] no_editable_cols = { 4 };
			cls_MyDefaultTableModel dtm_aux_tabla = new cls_MyDefaultTableModel(vec_datos, vec_aux_encabezado, no_editable_cols);
			jtb_atributos.setModel(dtm_aux_tabla);
			int ancho_tabla = 0;
			int aux_ancho_tabla = 0;
			for (int i = 0; i < vec_aux_column_size.size(); i++) {
				aux_ancho_tabla = Integer.parseInt(vec_aux_column_size.get(i).toString());
				jtb_atributos.getColumnModel().getColumn(i).setPreferredWidth(aux_ancho_tabla);
				ancho_tabla += aux_ancho_tabla;
			}
			jtb_atributos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(vec_op_nullable)));
			jtb_atributos.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox(vec_op_tipo_dato)));
			jtb_atributos.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox(vec_aux_tablas)));
			jtb_atributos.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox(vec_op_visibilidad)));
			jtb_atributos.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JComboBox(vec_op_tipo_control)));
			jtb_atributos.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(vg_jcb_aux_true_false));
			jtb_atributos.getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(vg_jcb_aux_true_false_2));
			cb_maestro_detalle.setSelected(false);
			jcb_llave.removeAllItems();
			activate_master_detail_options();
			jta_add_item.setText("");
		}
	}

	public void Cargar_Foraneas() {
		if (jcb_tablas.getSelectedIndex() != -1) {
			/*
			 * vg_aux_master_detail = clsBase.Consulta_General(
			 * "SELECT aux.column_name, aux.foranea FROM \n" +
			 * "(SELECT LCASE(c.column_name) column_name,\n" +
			 * "(SELECT REFERENCED_TABLE_NAME FROM KEY_COLUMN_USAGE \n" +
			 * "WHERE REFERENCED_TABLE_NAME IS NOT NULL AND table_schema = c.TABLE_SCHEMA \n"
			 * + "AND table_name = c.table_name\n" +
			 * "AND column_name = c.column_name) foranea\n" +
			 * "FROM COLUMNS c WHERE c.table_schema = '" +
			 * jcb_bases_datos.getSelectedItem() + "' \n" +
			 * "AND c.table_name = '" + jcb_tablas.getSelectedItem() +
			 * "' ORDER BY c.ORDINAL_POSITION) aux\n" +
			 * "WHERE aux.foranea IS NOT NULL");
			 */
			vg_aux_master_detail = new Vector();
			for (int i = 0; i < vec_datos.size(); i++) {
				if (((Vector) vec_datos.get(i)).get(5).toString().length() > 0) {
					Vector aux_vg_aux_master_detail = new Vector();
					aux_vg_aux_master_detail.add(((Vector) vec_datos.get(i)).get(0).toString());
					aux_vg_aux_master_detail.add(((Vector) vec_datos.get(i)).get(5).toString());
					aux_vg_aux_master_detail.add(i);
					vg_aux_master_detail.add(aux_vg_aux_master_detail);
				}
			}
			jcb_llave.removeAllItems();
			for (int i = 0; i < vg_aux_master_detail.size(); i++) {
				jcb_llave.addItem(((Vector) vg_aux_master_detail.get(i)).get(0));
			}
			activate_master_detail_options();
		}
	}

	public void Cargar_Atributos_Padre() {
		if (jcb_llave.getSelectedIndex() != -1) {
			Vector vl_aux_master_atributes = clsBase.Consulta_General("SELECT column_name FROM COLUMNS WHERE table_schema = '" + jcb_bases_datos.getSelectedItem() + "' \n" + "AND table_name = '"
			        + ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(1) + "' AND column_name <> '" + jcb_llave.getSelectedItem() + "' \n"
			        + "AND column_name <> 'i_concurrencia' ORDER BY ORDINAL_POSITION");
			jcb_master_atributes.removeAllItems();
			for (int i = 0; i < vl_aux_master_atributes.size(); i++) {
				jcb_master_atributes.addItem(((Vector) vl_aux_master_atributes.get(i)).get(0));
			}
		} else {
			jtf_etiqueta.setText("");
		}
	}

	public String Transform_to_label(String vl_aux_column_name) {
		String vl_aux_etiqueta = vl_aux_column_name;
		vl_aux_etiqueta = vl_aux_etiqueta.substring(vl_aux_etiqueta.indexOf("_") + 1, vl_aux_etiqueta.length());
		vl_aux_etiqueta = vl_aux_etiqueta.substring(0, 1).toUpperCase() + vl_aux_etiqueta.substring(1, vl_aux_etiqueta.length());
		vl_aux_etiqueta = vl_aux_etiqueta.replaceAll("_id", "");
		vl_aux_etiqueta = vl_aux_etiqueta.replaceAll("_", " ");
		return vl_aux_etiqueta;
	}

	public Vector ActualizarInformacion(Vector vec_vl_data) {
		Vector vec_vl_tabla = new Vector();
		Vector vec_vl_aux_data;
		for (int i = 0; i < vec_vl_data.size(); i++) {
			vec_vl_aux_data = (Vector) vec_vl_data.get(i);
			vec_vl_aux_data.setElementAt(Transform_to_label(vec_vl_aux_data.get(1).toString()), 1);
			vec_vl_aux_data.setElementAt(ConvertirTipoDato(vec_vl_aux_data.get(3).toString(), vec_vl_aux_data.get(6).toString()), 3);
			vec_vl_aux_data.setElementAt(Modificar_Longitud(vec_vl_aux_data.get(3).toString(), vec_vl_aux_data.get(4).toString()), 4);
			String vl_aux_visibility = vec_vl_aux_data.get(0).toString();
			boolean primaria = es_primaria(vl_aux_visibility);
			boolean concurrencia = es_concurrencia(vl_aux_visibility);
			if (primaria || concurrencia) {
				vl_aux_visibility = "hidden";
			} else {
				vl_aux_visibility = "visible";
			}
			boolean es_codigo = es_combo(vec_vl_aux_data.get(0).toString(), "codigo", vec_vl_aux_data.get(0).toString().length() - 6, 6);
			if (es_codigo) {
				vl_aux_visibility = "readonly";
			}
			vec_vl_aux_data.setElementAt(vl_aux_visibility, 6);
			vec_vl_tabla.add(vec_vl_aux_data);
		}
		for (int i = 0; i < vec_vl_tabla.size(); i++) {
			vec_vl_aux_data = (Vector) vec_vl_data.get(i);
			boolean primaria = es_primaria(vec_vl_aux_data.get(0).toString());
			boolean concurrencia = es_concurrencia(vec_vl_aux_data.get(0).toString());
			boolean es_combo = es_combo(vec_vl_aux_data.get(0).toString(), "activo", vec_vl_aux_data.get(0).toString().length() - 6, 6)
			        || es_combo(vec_vl_aux_data.get(0).toString(), "activa", vec_vl_aux_data.get(0).toString().length() - 6, 6);
			boolean es_codigo = es_combo(vec_vl_aux_data.get(0).toString(), "codigo", vec_vl_aux_data.get(0).toString().length() - 6, 6);
			if (primaria && i != 0) {
				Vector vl_aux_transferencia = (Vector) vec_vl_tabla.get(0);
				vec_vl_tabla.setElementAt(vec_vl_aux_data, 0);
				vec_vl_tabla.setElementAt(vl_aux_transferencia, i);
			} else {
				if (concurrencia && i != (vec_vl_tabla.size() - 1)) {
					Vector vl_aux_transferencia = (Vector) vec_vl_tabla.get(vec_vl_tabla.size() - 1);
					vec_vl_tabla.setElementAt(vec_vl_aux_data, vec_vl_data.size() - 1);
					vec_vl_tabla.setElementAt(vl_aux_transferencia, i);
				} else {
					if (primaria || concurrencia) {
						vec_vl_aux_data.add("");
						vec_vl_aux_data.add("false");
						vec_vl_aux_data.add("");
						vec_vl_aux_data.add("false");
					} else {
						if (es_combo) {
							vec_vl_aux_data.add(AsignarControl(vec_vl_aux_data.get(3).toString(), Integer.parseInt(vec_vl_aux_data.get(4).toString()), "*"));
							vec_vl_aux_data.add("true");
							vec_vl_aux_data.add(AsignarAncho(vec_vl_aux_data.get(3).toString(), Integer.parseInt(vec_vl_aux_data.get(4).toString()), "*"));
							vec_vl_aux_data.add("true");
						} else {
							if (es_codigo) {
								vec_vl_aux_data.add("autoincrement");
								vec_vl_aux_data.add("true");
								vec_vl_aux_data.add("50");
								vec_vl_aux_data.add("true");
							} else {
								vec_vl_aux_data.add(AsignarControl(vec_vl_aux_data.get(3).toString(), Integer.parseInt(vec_vl_aux_data.get(4).toString()), vec_vl_aux_data.get(5).toString()));
								vec_vl_aux_data.add("true");
								vec_vl_aux_data.add(AsignarAncho(vec_vl_aux_data.get(3).toString(), Integer.parseInt(vec_vl_aux_data.get(4).toString()), vec_vl_aux_data.get(5).toString()));
								vec_vl_aux_data.add("true");
							}
						}
					}
				}
			}
		}
		return vec_vl_tabla;
	}

	public boolean es_primaria(String vl_nombre_variable) {
		return vl_nombre_variable.substring(3, vl_nombre_variable.length() - 3).compareTo(jcb_tablas.getSelectedItem().toString().substring(4, jcb_tablas.getSelectedItem().toString().length() - 1)) == 0
		        || vl_nombre_variable.substring(3, vl_nombre_variable.length() - 3).compareTo(
		                jcb_tablas.getSelectedItem().toString().substring(4, jcb_tablas.getSelectedItem().toString().length() - 2)) == 0;
	}

	public boolean es_combo(String vl_nombre_variable, String vl_valor_comparar, int vl_int_posini, int vl_int_posiciones) {
		return vl_nombre_variable.substring(vl_int_posini, vl_int_posini + vl_int_posiciones).compareTo(vl_valor_comparar) == 0;
	}

	public boolean es_concurrencia(String vl_nombre_variable) {
		return vl_nombre_variable.compareTo("i_concurrencia") == 0;
	}

	public String ConvertirTipoDato(String sl_tipo_atributo, String sl_precision) {
		String nuevo_tipo = "default";
		if ((sl_tipo_atributo.compareTo("bigint") == 0 || sl_tipo_atributo.compareTo("decimal") == 0) && (sl_precision.compareTo("0") == 0 || sl_precision.compareTo("") == 0)) {
			nuevo_tipo = "int";
		} else {
			if (sl_tipo_atributo.compareTo("decimal") == 0) {
				nuevo_tipo = "double";
			} else {
				if (sl_tipo_atributo.compareTo("varchar") == 0) {
					nuevo_tipo = "String";
				} else {
					if (sl_tipo_atributo.compareTo("date") == 0) {
						nuevo_tipo = "Date";
					} else {
						if (sl_tipo_atributo.compareTo("time") == 0) {
							nuevo_tipo = "Time";
						} else {
							if (sl_tipo_atributo.compareTo("datetime") == 0) {
								nuevo_tipo = "Datetime";
							}
						}
					}
				}
			}
		}
		return nuevo_tipo;
	}

	public String AsignarControl(String vl_tipo_atributo, int vl_limite, String vl_s_tabla_foranea) {
		String control = "textfield";
		if (vl_tipo_atributo.compareTo("int") == 0) {
			if (vl_s_tabla_foranea.length() > 0) {
				control = "combobox";
			}
		} else {
			if (vl_tipo_atributo.compareTo("String") == 0 && vl_limite > 250) {
				control = "textarea";
			} else {
				if (vl_tipo_atributo.compareTo("Date") == 0) {
					control = "datepicker";
				} else {
					if (vl_tipo_atributo.compareTo("Time") == 0) {
						control = "timepicker";
					} else {
						if (vl_tipo_atributo.compareTo("Datetime") == 0) {
							control = "datetimepicker";
						}
					}
				}
			}
		}
		return control;
	}

	public String AsignarAncho(String vl_tipo_atributo, int vl_limite, String tabla) {
		String ancho = "" + vl_limite;
		if (vl_tipo_atributo.compareTo("int") == 0) {
			if (tabla.length() > 0) {
				ancho = "200";
			} else {
				ancho = "75";
			}
		} else {
			if (vl_tipo_atributo.compareTo("double") == 0) {
				ancho = "100";
			} else {
				if (vl_tipo_atributo.compareTo("String") == 0) {
					if (vl_limite <= 250) {
						ancho = "200";
					} else {
						ancho = "300";
					}
				} else {
					if (vl_tipo_atributo.compareTo("Date") == 0) {
						ancho = "125";
					} else {
						if (vl_tipo_atributo.compareTo("Time") == 0) {
							ancho = "100";
						} else {
							if (vl_tipo_atributo.compareTo("Datetime") == 0) {
								ancho = "200";
							}
						}
					}
				}
			}
		}
		return ancho;
	}

	public String Modificar_Longitud(String sl_tipo_atributo, String sl_longitud) {
		String vl_nueva_longitud = sl_longitud;
		if (sl_tipo_atributo.compareTo("Date") == 0) {
			vl_nueva_longitud = "10";
		} else {
			if (sl_tipo_atributo.compareTo("Time") == 0) {
				vl_nueva_longitud = "8";
			} else {
				if (sl_tipo_atributo.compareTo("Datetime") == 0) {
					vl_nueva_longitud = "19";
				}
			}
		}
		return vl_nueva_longitud;
	}

	private void jbt_bases_datos_actionPerformed(ActionEvent e) {
		Conectar_Base_Datos();
	}

	private void jcb_bases_datos_actionPerformed(ActionEvent e) {
		Cargar_Tablas();
	}

	private void jcb_tablas_actionPerformed(ActionEvent e) {
		Cargar_Atributos();
	}

	private void vg_jcb_aux_true_false(ActionEvent e) {
		int vl_int_fila = jtb_atributos.getSelectedRow();
		int vl_int_columna = jtb_atributos.getSelectedColumn();
		if (vl_int_columna == 8) {
			if (!Boolean.parseBoolean(vg_jcb_aux_true_false.getSelectedItem().toString())) {
				jtb_atributos.setValueAt("", vl_int_fila, 9);
			} else {
				jtb_atributos.setValueAt(
				        AsignarAncho(jtb_atributos.getValueAt(vl_int_fila, 3).toString(), Integer.parseInt(jtb_atributos.getValueAt(vl_int_fila, 4).toString()),
				                jtb_atributos.getValueAt(vl_int_fila, 5).toString()), vl_int_fila, 9);
			}
		}
	}

	private void jcb_generar_clase_actionPerformed(ActionEvent e) {
		jtf_directorio_clase.setEnabled(jcb_generar_clase.isSelected());
		jbt_buscar_directorio_clase.setEnabled(jcb_generar_clase.isSelected());
	}

	private void jcb_generar_formulario_actionPerformed(ActionEvent e) {
		jtf_directorio_formulario.setEnabled(jcb_generar_formulario.isSelected());
		jbt_buscar_directotio_formulario.setEnabled(jcb_generar_formulario.isSelected());
	}

	private void jbt_generar_actionPerformed(ActionEvent e) {
		if (jcb_generar_clase.isSelected()) {
			m_generar_clase(jtf_directorio_clase.getText(), jcb_tablas.getSelectedItem().toString(), m_cargar_valores());
		}
		if (jcb_generar_formulario.isSelected()) {
			m_generar_lista(jtf_directorio_formulario.getText(), jcb_tablas.getSelectedItem().toString(), m_cargar_valores());
		}
	}

	public File Leer_Archivo(File vl_directorio_inicial) {
		File vl_fl_archivo = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Seleccione un directorio");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(vl_directorio_inicial);
		int resp = fc.showOpenDialog(this);
		if (resp == JFileChooser.APPROVE_OPTION) {
			vl_fl_archivo = fc.getSelectedFile();
		}
		return vl_fl_archivo;
	}

	private void jbt_buscar_directorio_clase_actionPerformed(ActionEvent e) {
		vg_fl_directorio_clases = Leer_Archivo(vg_fl_directorio_clases);
		if (vg_fl_directorio_clases != null) {
			jtf_directorio_clase.setText(vg_fl_directorio_clases.getAbsolutePath());
			jtf_directorio_formulario.setText(jtf_directorio_clase.getText().replace("Negocio", "Presentacion"));
		}
	}

	private void jbt_buscar_directotio_formulario_actionPerformed(ActionEvent e) {
		vg_fl_directorio_formularios = Leer_Archivo(vg_fl_directorio_formularios);
		if (vg_fl_directorio_formularios != null) {
			jtf_directorio_formulario.setText(vg_fl_directorio_formularios.getAbsolutePath());
		}
	}

	public String[][] m_cargar_valores() {
		String[][] vl_String_aux_valores = new String[jtb_atributos.getRowCount()][jtb_atributos.getColumnCount()];
		for (int i = 0; i < vl_String_aux_valores.length; i++) {
			for (int j = 0; j < vl_String_aux_valores[i].length; j++) {
				vl_String_aux_valores[i][j] = jtb_atributos.getValueAt(i, j).toString();
			}
		}
		return vl_String_aux_valores;
	}

	public void m_generar_clase(String vl_s_path, String vl_s_nombre_tabla, String[][] vl_s_data) {
		if (cb_maestro_detalle.isSelected()) {
			m_escribir_archivo(vl_s_path, vl_s_nombre_tabla,
			        cls_generador.Generate_class(vl_s_nombre_tabla, vl_s_data, ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(0).toString()));
		} else {
			m_escribir_archivo(vl_s_path, vl_s_nombre_tabla, cls_generador.Generate_class(vl_s_nombre_tabla, vl_s_data, ""));
		}
	}

	public void m_generar_lista(String vl_s_path, String vl_s_nombre_tabla, String[][] vl_s_data) {
		String vl_str_aux_nombre_atributo_foranea = "";
		String vl_str_aux_nombre_tabla_foranea = "";
		if (cb_maestro_detalle.isSelected()) {
			vl_str_aux_nombre_tabla_foranea = ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(1).toString();
			vl_str_aux_nombre_atributo_foranea = ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(0).toString();
			m_escribir_archivo(vl_s_path, "frm" + vl_s_nombre_tabla, cls_generador.Generate_lista(vl_s_nombre_tabla, vl_str_aux_nombre_tabla_foranea, vg_aux_headerfields,
			        ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(2).toString()));
			jta_add_master_detail.setText(cls_generador.Generate_master_detail_conection_code(vl_s_nombre_tabla, vl_str_aux_nombre_tabla_foranea));
		} else {
			m_escribir_archivo(vl_s_path, "frm" + vl_s_nombre_tabla, cls_generador.Generate_lista(vl_s_nombre_tabla));
			jta_add_item.setText(cls_generador.Generate_tab_code(vl_s_nombre_tabla, vl_str_aux_nombre_tabla_foranea));
		}
		m_escribir_archivo(vl_s_path, "frmabm" + vl_s_nombre_tabla, cls_generador.Generate_frmabm(vl_s_nombre_tabla, vl_s_data, vl_str_aux_nombre_atributo_foranea, vl_str_aux_nombre_tabla_foranea));
	}

	public void m_escribir_archivo(String vl_s_path, String vl_s_file_name, String vl_s_file_content) {
		try {
			FileWriter fw = new FileWriter(vl_s_path + "/" + vl_s_file_name + ".java");
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.print(vl_s_file_content);
			salida.close();
			JOptionPane.showMessageDialog(this, vl_s_file_name + ".java escrito satisfactoriamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void activate_master_detail_options() {
		vg_aux_headerfields = new Vector();
		repaint_cb_headerfields();
		jtf_etiqueta.setEnabled(cb_maestro_detalle.isSelected());
		jcb_llave.setEnabled(cb_maestro_detalle.isSelected());
		jcb_headerfields.setEnabled(cb_maestro_detalle.isSelected());
		jtf_newfield.setEnabled(false);
		jbt_addfield.setEnabled(cb_maestro_detalle.isSelected());
		jbt_deletefield.setEnabled(cb_maestro_detalle.isSelected());
		cb_textarea.setEnabled(cb_maestro_detalle.isSelected());
		jscp_add_master_detail.setEnabled(cb_maestro_detalle.isSelected());
		jta_add_master_detail.setEnabled(cb_maestro_detalle.isSelected());
		jcb_master_atributes.setEnabled(cb_maestro_detalle.isSelected());
		cb_complex_query.setEnabled(cb_maestro_detalle.isSelected());
		jta_add_master_detail.setText("");
	}

	public void repaint_cb_headerfields() {
		jcb_headerfields.removeAllItems();
		String vl_aux_query = "";
		for (int i = 0; i < vg_aux_headerfields.size(); i++) {
			if (Boolean.parseBoolean(((Vector) vg_aux_headerfields.get(i)).get(2).toString())) {
				vl_aux_query = "(jta) ";
			} else {
				vl_aux_query = "(jtf) ";
			}
			vl_aux_query += ((Vector) vg_aux_headerfields.get(i)).get(0).toString() + " : ";
			vl_aux_query += ((Vector) vg_aux_headerfields.get(i)).get(1).toString();
			jcb_headerfields.addItem(vl_aux_query);
		}
		if (vg_aux_headerfields.size() > 0) {
			jcb_headerfields.setSelectedIndex(vg_aux_headerfields.size() - 1);
			jcb_headerfields.requestFocus();
		}
	}

	private void cb_maestro_detalle_actionPerformed(ActionEvent e) {
		if (cb_maestro_detalle.isSelected()) {
			Cargar_Foraneas();
		} else {
			jcb_llave.removeAllItems();
			activate_master_detail_options();
		}
	}

	private void jbt_addfield_actionPerformed(ActionEvent e) {
		String vl_new_query = "";
		if (cb_complex_query.isSelected()) {
			vl_new_query = jtf_newfield.getText();
		} else {
			vl_new_query = Generate_simple_query();
		}
		if (vl_new_query.length() > 0) {
			Vector vl_new_field = new Vector();
			vl_new_field.add(jtf_etiqueta.getText());
			vl_new_field.add(vl_new_query);
			vl_new_field.add(cb_textarea.isSelected());
			vg_aux_headerfields.add(vl_new_field);
			cb_textarea.setSelected(false);
			repaint_cb_headerfields();
			jtf_newfield.setText("");
			jtf_etiqueta.setText("");
		} else {
			JOptionPane.showMessageDialog(this, "No ha ingresado la consulta para el nuevo campo.");
		}
	}

	public String Generate_simple_query() {
		String vl_str_resultado = "";
		vl_str_resultado = "select " + jcb_master_atributes.getSelectedItem() + " from " + ((Vector) vg_aux_master_detail.get(jcb_llave.getSelectedIndex())).get(1) + " where "
		        + jcb_llave.getSelectedItem() + " = padre_id";
		return vl_str_resultado;
	}

	private void cb_textarea_actionPerformed(ActionEvent e) {
	}

	private void jbt_deletefield_actionPerformed(ActionEvent e) {
		if (jcb_headerfields.getSelectedIndex() != -1) {
			vg_aux_headerfields.removeElementAt(jcb_headerfields.getSelectedIndex());
			repaint_cb_headerfields();
		}
	}

	private void cb_complex_query_actionPerformed(ActionEvent e) {
		jtf_newfield.setEnabled(cb_complex_query.isSelected());
		jcb_master_atributes.setEnabled(!cb_complex_query.isSelected());
	}

	private void jcb_llave_actionPerformed(ActionEvent e) {
		Cargar_Atributos_Padre();
	}

	private void jcb_master_atributes_actionPerformed(ActionEvent e) {
		if (jcb_master_atributes.getSelectedIndex() != -1) {
			jtf_etiqueta.setText(Transform_to_label(jcb_master_atributes.getSelectedItem().toString()));
		}
	}
}
