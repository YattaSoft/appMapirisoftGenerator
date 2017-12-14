package com.apptriggergenerator.triggergenerator.utils;

import java.util.Vector;

public class cls_generador {
	int vg_i_pos_y;

	public cls_generador() {
		vg_i_pos_y = 20;
	}

	public String Generate_master_detail_conection_code(String lv_str_nombre_tabla, String lv_str_nombre_padre) {
		String vl_s_table_source = "          case 5 : \n" + "              if(jTable1.getSelectedRow() != -1){\r\n" + "                  lClase_id = " + lv_str_nombre_padre
		        + ".getId().get(jTable1.getSelectedRow()).toString();\r\n" + "                  frm" + lv_str_nombre_tabla + " frm" + lv_str_nombre_tabla + " = new frm" + lv_str_nombre_tabla + "("
		        + lv_str_nombre_padre + ".getCon(),this);\r\n" + "                  frm" + lv_str_nombre_tabla + ".setLocationRelativeTo(this);\r\n" + "                  frm" + lv_str_nombre_tabla
		        + ".setVisible(true);\r\n" + "              }else{\r\n" + "                  Pop_up_error_message(\"No ha seleccionado ningun registro.\");\r\n" + "              }\r\n"
		        + "          break;\r\n";
		return vl_s_table_source;
	}

	public String Generate_tab_code(String lv_str_nombre_tabla, String lv_str_nombre_padre) {
		String vl_s_table_source = "    //variables de clase\r\n" + "    frm" + lv_str_nombre_tabla + " frm" + lv_str_nombre_tabla + ";\r\n" + "      //constructor\r\n" + "      frm"
		        + lv_str_nombre_tabla + " = new frm" + lv_str_nombre_tabla + "(clsConexion,this);\r\n" + "      vg_obj_aux_internal_frames[x] = frm" + lv_str_nombre_tabla + ";\r\n"
		        + "      //action performed\r\n" + "      llamar_de_lista(x);\r\n";
		return vl_s_table_source;
	}

	public String Generate_class(String lv_str_nombre_tabla, String[][] vl_str_data, String vl_foranea) {
		String vl_str_clase = "";
		String Agregar_Atributos = "";
		String campos_mostrar_tabla = "";
		String campos_ordenar_tabla = "";
		String campos_mostrar_combo = "";
		for (int i = 0; i < vl_str_data.length; i++) {
			String vl_aux_tabla_foranea = "null";
			if (vl_str_data[i][5].length() > 0) {
				vl_aux_tabla_foranea = "new " + vl_str_data[i][5] + "(con)";
			}
			Agregar_Atributos += "       Agregar_atributo(" + (i) + ", \"" + vl_str_data[i][1] + "\", \"" + vl_str_data[i][0] + "\", " + vl_str_data[i][4] + ", dd_" + vl_str_data[i][3] + ", "
			        + m_get_valor_inicial(vl_str_data[i][3], Boolean.parseBoolean(vl_str_data[i][2])) + ", n_null_" + vl_str_data[i][2] + ", td_" + vl_str_data[i][6] + ", " + vl_aux_tabla_foranea
			        + ");\r\n";
			if (Boolean.parseBoolean(vl_str_data[i][8]) && (vl_str_data[i][0].compareTo(vl_foranea) != 0)) {
				campos_mostrar_tabla += "       campos_mostrar_tabla.add(" + i + "); ancho_columnas_tabla.add(" + vl_str_data[i][9] + ");//" + vl_str_data[i][0] + "\r\n";
				campos_ordenar_tabla += "       campos_ordenar_tabla.add(" + i + ");//order by - " + vl_str_data[i][0] + "\r\n";
			}
			if (Boolean.parseBoolean(vl_str_data[i][10])) {
				campos_mostrar_combo += "       campos_mostrar_combo.add(" + i + ");//combobox - " + vl_str_data[i][0] + "\r\n";
			}
		}
		vl_str_clase = "package Negocio;\r\n" + "\r\n" + "import Datos.clsConexion;\r\n" + "import java.sql.ResultSet;\r\n" + "import java.util.Vector;\r\n"
		        + "import javax.swing.table.DefaultTableModel;\r\n" + "\r\n" + "\r\n" + "public class "
		        + lv_str_nombre_tabla
		        + " extends clsBase{\r\n"
		        + "    public "
		        + lv_str_nombre_tabla
		        + "() {\r\n"
		        + "        super();\r\n"
		        + "        Agregar_Atributos();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    public "
		        + lv_str_nombre_tabla
		        + "(clsConexion con) {\r\n"
		        + "        super(con);\r\n"
		        + "        Agregar_Atributos();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    public void Agregar_Atributos(){\r\n"
		        + "       Titulo = \""
		        + (lv_str_nombre_tabla.substring(4, lv_str_nombre_tabla.length())).toUpperCase()
		        + "\";\r\n"
		        + "       nombre_tabla = \""
		        + lv_str_nombre_tabla
		        + "\";\r\n"
		        + "       Inicializar_Vectores("
		        + vl_str_data.length
		        + ");\r\n"
		        + Agregar_Atributos
		        + campos_mostrar_tabla
		        + "\r\n"
		        + campos_ordenar_tabla + "\r\n" + campos_mostrar_combo + "   }\r\n" + "}";
		return vl_str_clase;
	}

	public String m_get_valor_inicial(String vl_s_tipo_dato, boolean vl_b_nullable) {
		String vl_s_nuevo_tipo_dato = "\"\"";
		if (!vl_b_nullable) {
			if (vl_s_tipo_dato.compareTo("int") == 0) {
				vl_s_nuevo_tipo_dato = "\"0\"";
			} else {
				if (vl_s_tipo_dato.compareTo("double") == 0) {
					vl_s_nuevo_tipo_dato = "\"0.0\"";
				} else {
					if (vl_s_tipo_dato.compareTo("String") == 0) {
						vl_s_nuevo_tipo_dato = "\"\"";
					} else {
						if (vl_s_tipo_dato.compareTo("Date") == 0) {
							vl_s_nuevo_tipo_dato = "get_Fecha_Actual(dd_" + vl_s_tipo_dato + ")";
						} else {
							if (vl_s_tipo_dato.compareTo("Time") == 0) {
								vl_s_nuevo_tipo_dato = "get_Fecha_Actual(dd_" + vl_s_tipo_dato + ")";
							} else {
								if (vl_s_tipo_dato.compareTo("Datetime") == 0) {
									vl_s_nuevo_tipo_dato = "get_Fecha_Actual(dd_" + vl_s_tipo_dato + ")";
								}
							}
						}
					}
				}
			}
		}
		return vl_s_nuevo_tipo_dato;
	}

	public String Generate_lista(String lv_str_nombre_tabla, String lv_str_nombre_padre, Vector vl_vec_header_fields, String vl_str_pos_padre) {
		String vl_str_campos_adicionar = "";
		String vl_str_constructor_campos_adicionales = "";
		String vl_str_add_to_extra_information = "";
		String vl_str_consultas = "";
		int vl_pos_y = 10;
		for (int i = 0; i < vl_vec_header_fields.size(); i++) {
			Vector vl_aux_field = (Vector) vl_vec_header_fields.get(i);
			vl_str_consultas += "        header_field_" + (i + 1) + ".setText(((Vector)" + lv_str_nombre_tabla + ".Consulta_General(\""
			        + vl_aux_field.get(1).toString().replaceAll("padre_id", "\" + frm" + lv_str_nombre_padre + ".lClase_id") + ").get(0)).get(0).toString());\r\n";
			vl_str_campos_adicionar += "    private JLabel header_label_" + (i + 1) + " = new JLabel();\r\n";
			vl_str_constructor_campos_adicionales += "        header_label_" + (i + 1) + ".setText(\"" + vl_aux_field.get(0) + " : \");\r\n" + "        header_label_" + (i + 1)
			        + ".setBounds(new Rectangle(5, " + vl_pos_y + ", 150, 30));\r\n" + "        header_label_" + (i + 1) + ".setFont(new Font(\"Times New Roman\", 1, 12));\r\n"
			        + "        header_label_" + (i + 1) + ".setHorizontalAlignment(SwingConstants.RIGHT);\r\n";
			vl_str_add_to_extra_information += "        extra_information.add(header_label_" + (i + 1) + ", null);";
			if ((Boolean) vl_aux_field.get(2)) {
				vl_str_campos_adicionar += "    private JScrollPane header_sc_" + (i + 1) + " = new JScrollPane();\n" + "    private JTextArea header_field_" + (i + 1) + " = new JTextArea();\r\n";
				vl_str_constructor_campos_adicionales += "        header_sc_" + (i + 1) + ".setBounds(new Rectangle(155, " + vl_pos_y + ", 300, 60));\r\n" + "        header_sc_" + (i + 1)
				        + ".setSize(new Dimension(300, 60));\r\n" + "        header_field_" + (i + 1) + ".setSize(new Dimension(296, 60));\r\n" + "        header_field_" + (i + 1)
				        + ".setRows(2);\r\n" + "        header_field_" + (i + 1) + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        header_field_" + (i + 1)
				        + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        header_field_" + (i + 1) + ".setLineWrap(true);\r\n" + "        header_field_" + (i + 1) + ".setFocusable(false)\r\n;"
				        + "        header_sc_" + (i + 1) + ".getViewport().add(header_field_" + (i + 1) + ", null);\r\n";
				vl_str_add_to_extra_information += "        extra_information.add(header_sc_" + (i + 1) + ", null);";
				vl_pos_y += 30;
			} else {
				vl_str_campos_adicionar += "    private JTextField header_field_" + (i + 1) + " = new JTextField();\r\n";
				vl_str_constructor_campos_adicionales += "        header_field_" + (i + 1) + ".setBounds(new Rectangle(155, " + vl_pos_y + ", 300, 30));\r\n" + "        header_field_" + (i + 1)
				        + ".setSize(new Dimension(300, 30));\r\n" + "        header_field_" + (i + 1) + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        header_field_" + (i + 1)
				        + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        header_field_" + (i + 1) + ".setFocusable(false);\r\n";
				vl_str_add_to_extra_information += "        extra_information.add(header_field_" + (i + 1) + ", null);";
			}
			vl_pos_y += 35;
		}
		vl_pos_y += 5;
		String vl_s_table_source = "package Presentacion;\r\n" + "\r\n" + "import Negocio." + lv_str_nombre_tabla + ";\r\n" + "import Datos.clsConexion;\r\n" + "import java.awt.BorderLayout;\r\n"
		        + "import java.awt.Color;\r\n" + "import java.awt.Dimension;\r\n" + "import java.awt.Font;\r\n" + "import java.awt.Insets;\r\n" + "import java.awt.Rectangle;\r\n"
		        + "import java.awt.event.ActionEvent;\r\n" + "import java.awt.event.ActionListener;\r\n" + "import javax.swing.BorderFactory;\r\n" + "import javax.swing.ImageIcon;\r\n"
		        + "import javax.swing.JButton;\r\n" + "import javax.swing.JDialog;\r\n" + "import javax.swing.JFrame;\r\n" + "import javax.swing.JOptionPane;\r\n" + "import javax.swing.JPanel;\r\n"
		        + "import javax.swing.JScrollPane;\r\n" + "import javax.swing.JTable;\r\n" + "import javax.swing.JTextArea;\r\n" + "import javax.swing.JTextField;\r\n"
		        + "import javax.swing.JLabel;\r\n" + "import javax.swing.SwingConstants;\r\n" + "import Utilitarios.*;\r\n" + "import java.util.Vector;\r\n" + "import javax.swing.JMenu;\r\n"
		        + "import javax.swing.JMenuItem;\r\n" + "\r\n" + "public class frm" + lv_str_nombre_tabla + " extends JDialog {\r\n" + "    private BorderLayout layoutMain = new BorderLayout();\r\n"
		        + "    private JPanel panel_header = new JPanel();\r\n" + "    private JScrollPane jScrollPane1 = new JScrollPane();\r\n" + "    public JTable jTable1 = new JTable();\r\n"
		        + "    private JButton Add = new JButton();\r\n" + "    private JButton Modify = new JButton();\r\n" + "    private JButton Delete = new JButton();\r\n"
		        + "    private JButton Refresh = new JButton();\r\n" + "    private ImageIcon add = new ImageIcon(getClass().getResource(\"/add.png\"));\r\n"
		        + "    private ImageIcon add_over = new ImageIcon(getClass().getResource(\"/add_over.png\"));\r\n"
		        + "    private ImageIcon add_pressed = new ImageIcon(getClass().getResource(\"/add_pressed.png\"));\r\n"
		        + "    private ImageIcon modify = new ImageIcon(getClass().getResource(\"/modify.png\"));\r\n"
		        + "    private ImageIcon modify_over = new ImageIcon(getClass().getResource(\"/modify_over.png\"));\r\n"
		        + "    private ImageIcon modify_pressed = new ImageIcon(getClass().getResource(\"/modify_pressed.png\"));\r\n"
		        + "    private ImageIcon delete = new ImageIcon(getClass().getResource(\"/delete.png\"));\r\n"
		        + "    private ImageIcon delete_over = new ImageIcon(getClass().getResource(\"/delete_over.png\"));\r\n"
		        + "    private ImageIcon delete_pressed = new ImageIcon(getClass().getResource(\"/delete_pressed.png\"));\r\n"
		        + "    private ImageIcon refresh = new ImageIcon(getClass().getResource(\"/refresh.png\"));\r\n"
		        + "    private ImageIcon refresh_over = new ImageIcon(getClass().getResource(\"/refresh_over.png\"));\r\n"
		        + "    private ImageIcon refresh_pressed = new ImageIcon(getClass().getResource(\"/refresh_pressed.png\"));\r\n"
		        + "    private ImageIcon search = new ImageIcon(getClass().getResource(\"/search.png\"));\r\n"
		        + "    private ImageIcon search_over = new ImageIcon(getClass().getResource(\"/search_over.png\"));\r\n"
		        + "    private ImageIcon search_pressed = new ImageIcon(getClass().getResource(\"/search_pressed.png\"));\r\n" + "    private JPanel panel_menu = new JPanel();\r\n"
		        + "    private JButton Search = new JButton();\r\n" + "    private BorderLayout borderLayout1 = new BorderLayout();\r\n" + "    private JPanel extra_information = new JPanel();\r\n"
		        + "    " + lv_str_nombre_tabla + " " + lv_str_nombre_tabla + ";\r\n" + "    frmabm" + lv_str_nombre_tabla + " frmabm" + lv_str_nombre_tabla + ";\r\n" + "    JFrame padre;\r\n"
		        + "    String lClase_id\r\n;" + "    int vg_int_filter_column;\r\n" + "    String vg_str_keywords;\r\n" + "    boolean vg_bool_filtered;\r\n" + "    Vector vg_obj_controls_list;\r\n"
		        + "    String vg_str_usuario_id;\r\n" + "    frm" + lv_str_nombre_padre + " frm" + lv_str_nombre_padre + ";\r\n" + vl_str_campos_adicionar + "\r\n" + "    public frm"
		        + lv_str_nombre_tabla + "(clsConexion clsConexion, frm" + lv_str_nombre_padre + " frm" + lv_str_nombre_padre + ") {\r\n" + "        super(frm" + lv_str_nombre_padre + ".padre);\r\n"
		        + "        this.padre = frm" + lv_str_nombre_padre + ".padre;\r\n" + "        " + lv_str_nombre_tabla + " = new " + lv_str_nombre_tabla + "(clsConexion);\r\n" + "        this.frm"
		        + lv_str_nombre_padre + " = frm" + lv_str_nombre_padre + ";\r\n" + "        vg_int_filter_column = -1;\r\n" + "        vg_str_keywords = \"\";\r\n"
		        + "        vg_bool_filtered = false;\r\n" + "        try {\r\n" + "            jbInit();\r\n" + "        } catch (Exception e) {\r\n" + "            e.printStackTrace();\r\n"
		        + "        }\r\n" + "        Recargar(-1);\r\n" + "        if(" + lv_str_nombre_tabla + ".is_autentification_active()){\r\n" + "          this.vg_str_usuario_id = this.frm"
		        + lv_str_nombre_padre + ".vg_str_usuario_id;\r\n" + "          vg_obj_controls_list = new Vector();\r\n" + "          vg_obj_controls_list.add(Add);\r\n"
		        + "          vg_obj_controls_list.add(\"Add\");\r\n" + "          vg_obj_controls_list.add(Modify);\r\n" + "          vg_obj_controls_list.add(\"Modify\");\r\n"
		        + "          vg_obj_controls_list.add(Delete);\r\n" + "          vg_obj_controls_list.add(\"Delete\");\r\n" + "          vg_obj_controls_list.add(Refresh);\r\n"
		        + "          vg_obj_controls_list.add(\"Refresh\");\r\n" + "          vg_obj_controls_list.add(Search);\r\n" + "          vg_obj_controls_list.add(\"Search\");\r\n"
		        + "          CargarControlesFormulario();\r\n" + "        }\r\n" + "    }\r\n" + "\r\n" + "    private void jbInit() throws Exception {\r\n"
		        + "        this.getContentPane().setLayout( layoutMain );\r\n" + "        this.setSize(new Dimension(1000, 600));\r\n" + "        this.setTitle(" + lv_str_nombre_tabla
		        + ".getTitulo());\r\n" + "        panel_header.setPreferredSize(new Dimension(1, " + (50 + vl_pos_y) + "));\r\n" + "        panel_header.setLayout(borderLayout1);\r\n"
		        + "        panel_header.setBackground(Color.white);\r\n" + "        Add.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Add.setBackground(Color.white);\r\n"
		        + "        Add.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        Add.setBounds(new Rectangle(5, 5, 40, 40));\r\n"
		        + "        Add.setSize(new Dimension(40, 40));\r\n" + "        Add.setIcon(add);\r\n" + "        Add.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Add.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Add.setToolTipText(\"Nuevo\");\r\n" + "        Add.setPressedIcon(add_pressed);\r\n"
		        + "        Add.setRolloverIcon(add_over);\r\n" + "        Add.addActionListener(new ActionListener() {\r\n" + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Add_actionPerformed(e);\r\n" + "                    }\r\n" + "                });\r\n" + "        Modify.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Modify.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        Modify.setBackground(Color.white);\r\n"
		        + "        Modify.setBounds(new Rectangle(50, 5, 40, 40));\r\n" + "        Modify.setIcon(modify);\r\n" + "        Modify.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Modify.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Modify.setToolTipText(\"Modificar\");\r\n" + "        Modify.setSize(new Dimension(40, 40));\r\n"
		        + "        Modify.setRolloverIcon(modify_over);\r\n" + "        Modify.setPressedIcon(modify_pressed);\r\n" + "        Modify.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n" + "                        Modify_actionPerformed(e);\r\n" + "                    }\r\n"
		        + "                });\r\n" + "        Delete.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Delete.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Delete.setBackground(Color.white);\r\n" + "        Delete.setBounds(new Rectangle(95, 5, 40, 40));\r\n" + "        Delete.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Delete.setIcon(delete);\r\n" + "        Delete.setToolTipText(\"Eliminar\");\r\n" + "        Delete.setHorizontalTextPosition(SwingConstants.CENTER);\r\n"
		        + "        Delete.setSize(new Dimension(40, 40));\r\n" + "        Delete.setPressedIcon(delete_pressed);\r\n" + "        Delete.setRolloverIcon(delete_over);\r\n"
		        + "        Delete.addActionListener(new ActionListener() {\r\n" + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Delete_actionPerformed(e);\r\n" + "                    }\r\n" + "                });\r\n"
		        + "        Refresh.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        Refresh.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Refresh.setBackground(Color.white);\r\n" + "        Refresh.setBounds(new Rectangle(140, 5, 40, 40));\r\n" + "        Refresh.setSize(new Dimension(40, 40));\r\n"
		        + "        Refresh.setMargin(new Insets(0, 0, 0, 0));\r\n" + "        Refresh.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Refresh.setIcon(refresh);\r\n"
		        + "        Refresh.setToolTipText(\"Recargar Lista\");\r\n" + "        Refresh.setRolloverIcon(refresh_over);\r\n" + "        Refresh.setPressedIcon(refresh_pressed);\r\n"
		        + "        Refresh.addActionListener(new ActionListener() {\r\n" + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Refresh_actionPerformed(e);\r\n" + "                    }\r\n" + "                });\r\n" + "        panel_menu.setLayout(null);\r\n"
		        + "        panel_menu.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        panel_menu.setBackground(new Color(153, 211, 138));\r\n"
		        + "        panel_menu.setPreferredSize(new Dimension(1, 50));\r\n" + "        Search.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Search.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Search.setBackground(Color.white);\r\n" + "        Search.setBounds(new Rectangle(185, 5, 40, 40));\r\n"
		        + "        Search.setSize(new Dimension(40, 40));\r\n" + "        Search.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Search.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Search.setIcon(search);\r\n" + "        Search.setToolTipText(\"Filtrar\");\r\n"
		        + "        Search.setPressedIcon(search_pressed);\r\n" + "        Search.setRolloverIcon(search_over);\r\n" + "        Search.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n" + "                        Search_actionPerformed(e);\r\n" + "                    }\r\n"
		        + "                });\r\n" + "        extra_information.setLayout(null);\r\n" + "        extra_information.setPreferredSize(new Dimension(1, " + vl_pos_y + "));\r\n"
		        + "        extra_information.setBackground(Color.white);\r\n" + "        extra_information.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + vl_str_constructor_campos_adicionales + "        panel_menu.add(Search, null);\r\n" + "        panel_menu.add(Modify, null);\r\n" + "        panel_menu.add(Add, null);\r\n"
		        + "        panel_menu.add(Delete, null);\r\n" + "        panel_menu.add(Refresh, null);\r\n" + vl_str_add_to_extra_information
		        + "        panel_header.add(panel_menu, BorderLayout.CENTER);\r\n" + "        panel_header.add(extra_information, BorderLayout.NORTH);\r\n"
		        + "        this.getContentPane().add(panel_header, BorderLayout.NORTH);\r\n" + "        jScrollPane1.getViewport().add(jTable1, null);\r\n"
		        + "        this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);\r\n" + "    }\r\n" + "    \r\n" + "    public void Recargar(int id_buscar){\r\n" + vl_str_consultas
		        + "        jTable1.setModel(" + lv_str_nombre_tabla + ".Consultar_Lista_Tabla(" + vl_str_pos_padre + ", frm" + lv_str_nombre_padre
		        + ".lClase_id, vg_int_filter_column, vg_str_keywords));\r\n" + "        int ancho_tabla = 0;\r\n" + "        for (int i = 0; i < jTable1.getColumnCount(); i++)  {\r\n"
		        + "            jTable1.getColumnModel().getColumn(i).setPreferredWidth((Integer)" + lv_str_nombre_tabla + ".getAncho_columnas_tabla().get(i));\r\n"
		        + "            ancho_tabla += (Integer)" + lv_str_nombre_tabla + ".getAncho_columnas_tabla().get(i);\r\n" + "        }\r\n" + "        if(id_buscar != -1){\r\n"
		        + "            jTable1.changeSelection(" + lv_str_nombre_tabla + ".Buscar_posicion_de_un_id(id_buscar),0,false,false);\r\n" + "        }\r\n"
		        + "        this.setSize(new Dimension(ancho_tabla+10,this.getHeight()));\r\n" + "        jScrollPane1.setSize(new Dimension(ancho_tabla,jScrollPane1.getHeight()));\r\n"
		        + "        jTable1.requestFocus();\r\n" + "    }\r\n" + "    \r\n" + "    public void Administrate(int option){\r\n" + "        switch(option){\r\n" + "            case 1 : \r\n"
		        + "                Open_abm_form(\"-1\");\r\n" + "                if(frmabm" + lv_str_nombre_tabla + ".getSelected_option() == 1){\r\n"
		        + "                    Recargar(Integer.parseInt(frmabm" + lv_str_nombre_tabla + ".getlClase_id()));\r\n" + "                }\r\n" + "            break;\r\n"
		        + "            case 2 : \r\n" + "                if(jTable1.getSelectedRow() != -1){\r\n" + "                    Open_abm_form(" + lv_str_nombre_tabla
		        + ".getId().get(jTable1.getSelectedRow()).toString());\r\n" + "                    if(frmabm" + lv_str_nombre_tabla + ".getSelected_option() == 1 || frmabm" + lv_str_nombre_tabla
		        + ".getSelected_option() == 2){\r\n" + "                        Recargar(Integer.parseInt(frmabm" + lv_str_nombre_tabla + ".getlClase_id()));\r\n" + "                    }\r\n"
		        + "                }else{\r\n" + "                    Pop_up_error_message(\"No ha seleccionado ningun registro.\");\r\n" + "                }\r\n" + "            break;\r\n"
		        + "            case 3 : \r\n" + "                if(jTable1.getSelectedRow() != -1){\r\n" + "                    Delete();\r\n" + "                }else{\r\n"
		        + "                    Pop_up_error_message(\"No ha seleccionado ningun registro.\");\r\n" + "                }\r\n" + "            break;\r\n" + "            case 4 :\r\n"
		        + "              if(vg_bool_filtered){\r\n" + "                Search.setIcon(search);\r\n" + "                Search.setToolTipText(\"Filtrar\");\r\n"
		        + "                vg_bool_filtered = false;\r\n" + "                vg_int_filter_column = -1;\r\n" + "                vg_str_keywords = \"\";\r\n"
		        + "                Recargar(-1);\r\n" + "              }else{\r\n" + "                jdg_search jdg_search = new jdg_search(" + lv_str_nombre_tabla + ".getCampos_mostrar_tabla(), "
		        + lv_str_nombre_tabla + ".getEtiquetas_columnas());\r\n" + "                jdg_search.setLocationRelativeTo(this);\r\n" + "                jdg_search.setVisible(true);\r\n"
		        + "                if(jdg_search.isVg_b_filtrado()){\r\n" + "                  Search.setIcon(search_pressed);\r\n" + "                  Search.setToolTipText(\"Romper filtro\");\r\n"
		        + "                  vg_int_filter_column = jdg_search.get_selected_field();\r\n" + "                  vg_str_keywords = jdg_search.getVg_str_filtro();\r\n"
		        + "                  vg_bool_filtered = true;\r\n" + "                  Recargar(-1);\r\n" + "                }\r\n" + "              }\r\n" + "            break;\r\n"
		        + "        }\r\n" + "    }\r\n" + "    \r\n" + "    public void Open_abm_form(String id){\r\n" + "        frmabm" + lv_str_nombre_tabla + " = new frmabm" + lv_str_nombre_tabla + "("
		        + lv_str_nombre_tabla + ".getCon(),id,this);\r\n" + "        frmabm" + lv_str_nombre_tabla + ".setLocationRelativeTo(this);\r\n" + "        frmabm" + lv_str_nombre_tabla
		        + ".setVisible(true);\r\n" + "    }\r\n" + "    \r\n" + "    public void Delete(){\r\n"
		        + "        int i =JOptionPane.showConfirmDialog(this,\"¿REALMENTE DESEA ELIMINAR ESTE REGISTRO?\",\"ELIMINAR REGISTRO\",JOptionPane.YES_NO_OPTION);\r\n" + "        if(i == 0){\r\n"
		        + "            " + lv_str_nombre_tabla + ".setValor(0," + lv_str_nombre_tabla + ".getId().get(jTable1.getSelectedRow()).toString());\r\n" + "            if(" + lv_str_nombre_tabla
		        + ".Borrar(0)){\r\n" + "                int id_recargar = -1;\r\n" + "                if(" + lv_str_nombre_tabla + ".getId().size() > 1){\r\n"
		        + "                    if(jTable1.getSelectedRow() == 0){\r\n" + "                        id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(1).toString());\r\n"
		        + "                    }else{\r\n" + "                        id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(jTable1.getSelectedRow()-1).toString());\r\n"
		        + "                    }\r\n" + "                }\r\n" + "                Recargar(id_recargar);\r\n" + "            }\r\n" + "        }\r\n" + "    }\r\n" + "\r\n" + "    public "
		        + lv_str_nombre_tabla + " get" + lv_str_nombre_tabla + "() {\r\n" + "        return " + lv_str_nombre_tabla + ";\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Add_actionPerformed(ActionEvent e) {\r\n" + "        Administrate(1);\r\n" + "    }\r\n" + "\r\n" + "    private void Modify_actionPerformed(ActionEvent e) {\r\n"
		        + "        Administrate(2);\r\n" + "    }\r\n" + "    \r\n" + "    public void Pop_up_error_message(String message){\r\n"
		        + "        JOptionPane.showMessageDialog(this,message,\"Error\",JOptionPane.ERROR_MESSAGE);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Delete_actionPerformed(ActionEvent e) {\r\n" + "        Administrate(3);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Refresh_actionPerformed(ActionEvent e) {\r\n" + "        int id_recargar = jTable1.getSelectedRow();\r\n" + "        if(id_recargar != -1){\r\n"
		        + "            id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(id_recargar).toString());\r\n" + "        }\r\n" + "        Recargar(id_recargar);\r\n"
		        + "    }\r\n" + "\r\n" + "    private void Search_actionPerformed(ActionEvent e) {\r\n" + "      Administrate(4);\r\n" + "    }\r\n" + "\r\n"
		        + "    public void CargarControlesFormulario(){\r\n" + "        if(vg_str_usuario_id != null && vg_str_usuario_id.length() > 0){\r\n"
		        + "          for (int i = 1; i < vg_obj_controls_list.size(); i += 2) {\r\n" + "              String vl_form_name = \"frm" + lv_str_nombre_tabla + "\";\r\n"
		        + "              boolean vl_bool_active = " + lv_str_nombre_tabla + ".is_control_active(vg_str_usuario_id,vl_form_name,vg_obj_controls_list.get(i).toString());\r\n"
		        + "              if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JButton\")){\r\n"
		        + "                ((JButton)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "              }else{\r\n"
		        + "                if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JMenu\")){\r\n"
		        + "                  ((JMenu)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "                }else{\r\n"
		        + "                  if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JMenuItem\")){\r\n"
		        + "                    ((JMenuItem)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "                  }else{\r\n"
		        + "                    System.out.println(\"No encontrado : \" + vg_obj_controls_list.get(i).getClass().toString());\r\n" + "                  }\r\n" + "                }\r\n"
		        + "              }\r\n" + "          }\r\n" + "        }else{\r\n" + "          System.exit(0);\r\n" + "        }\r\n" + "    }\r\n" + "}\r\n";
		return vl_s_table_source;
	}

	public String Generate_lista(String lv_str_nombre_tabla) {
		String vl_s_table_source = "package Presentacion;\r\n" + "\r\n" + "import Negocio." + lv_str_nombre_tabla + ";\r\n" + "import Datos.clsConexion;\r\n" + "import java.awt.BorderLayout;\r\n"
		        + "import java.awt.Color;\r\n" + "import java.awt.Dimension;\r\n" + "import java.awt.Font;\r\n" + "import java.awt.Insets;\r\n" + "import java.awt.Rectangle;\r\n"
		        + "import java.awt.event.ActionEvent;\r\n" + "import java.awt.event.ActionListener;\r\n" + "import javax.swing.BorderFactory;\r\n" + "import javax.swing.ImageIcon;\r\n"
		        + "import javax.swing.JButton;\r\n" + "import javax.swing.JFrame;\r\n" + "import javax.swing.JInternalFrame;\r\n" + "import javax.swing.JOptionPane;\r\n"
		        + "import javax.swing.JPanel;\r\n" + "import javax.swing.JScrollPane;\r\n" + "import javax.swing.JTable;\r\n" + "import javax.swing.SwingConstants;\r\n"
		        + "import javax.swing.event.InternalFrameAdapter;\r\n" + "import javax.swing.event.InternalFrameEvent;\r\n" + "import Utilitarios.*;\r\n" + "import java.util.Vector;\r\n"
		        + "import javax.swing.JMenu;\r\n" + "import javax.swing.JMenuItem;\r\n" + "\r\n" + "public class frm" + lv_str_nombre_tabla + " extends JInternalFrame {\r\n"
		        + "    private BorderLayout layoutMain = new BorderLayout();\r\n" + "    private JPanel panel_header = new JPanel();\r\n"
		        + "    private JScrollPane jScrollPane1 = new JScrollPane();\r\n" + "    public JTable jTable1 = new JTable();\r\n" + "    private JButton Add = new JButton();\r\n"
		        + "    private JButton Modify = new JButton();\r\n" + "    private JButton Delete = new JButton();\r\n" + "    private JButton Refresh = new JButton();\r\n"
		        + "    private ImageIcon add = new ImageIcon(getClass().getResource(\"/add.png\"));\r\n"
		        + "    private ImageIcon add_over = new ImageIcon(getClass().getResource(\"/add_over.png\"));\r\n"
		        + "    private ImageIcon add_pressed = new ImageIcon(getClass().getResource(\"/add_pressed.png\"));\r\n"
		        + "    private ImageIcon modify = new ImageIcon(getClass().getResource(\"/modify.png\"));\r\n"
		        + "    private ImageIcon modify_over = new ImageIcon(getClass().getResource(\"/modify_over.png\"));\r\n"
		        + "    private ImageIcon modify_pressed = new ImageIcon(getClass().getResource(\"/modify_pressed.png\"));\r\n"
		        + "    private ImageIcon delete = new ImageIcon(getClass().getResource(\"/delete.png\"));\r\n"
		        + "    private ImageIcon delete_over = new ImageIcon(getClass().getResource(\"/delete_over.png\"));\r\n"
		        + "    private ImageIcon delete_pressed = new ImageIcon(getClass().getResource(\"/delete_pressed.png\"));\r\n"
		        + "    private ImageIcon refresh = new ImageIcon(getClass().getResource(\"/refresh.png\"));\r\n"
		        + "    private ImageIcon refresh_over = new ImageIcon(getClass().getResource(\"/refresh_over.png\"));\r\n"
		        + "    private ImageIcon refresh_pressed = new ImageIcon(getClass().getResource(\"/refresh_pressed.png\"));\r\n"
		        + "    private ImageIcon search = new ImageIcon(getClass().getResource(\"/search.png\"));\r\n"
		        + "    private ImageIcon search_over = new ImageIcon(getClass().getResource(\"/search_over.png\"));\r\n"
		        + "    private ImageIcon search_pressed = new ImageIcon(getClass().getResource(\"/search_pressed.png\"));\r\n" + "    private JPanel panel_menu = new JPanel();\r\n"
		        + "    private JButton Search = new JButton();\r\n" + "    private BorderLayout borderLayout1 = new BorderLayout();\r\n" + "    private JPanel extra_information = new JPanel();\r\n"
		        + "    " + lv_str_nombre_tabla + " " + lv_str_nombre_tabla + ";\r\n" + "    frmabm" + lv_str_nombre_tabla + " frmabm" + lv_str_nombre_tabla + ";\r\n" + "    JFrame padre;\r\n"
		        + "    String lClase_id;\r\n" + "    int vg_int_filter_column;\r\n" + "    String vg_str_keywords;\r\n" + "    boolean vg_bool_filtered;\r\n" + "    Vector vg_obj_controls_list;\r\n"
		        + "    String vg_str_usuario_id;\r\n" + "\r\n" + "    public frm" + lv_str_nombre_tabla + "(clsConexion clsConexion,frmPrincipal padre) {\r\n" + "        this.padre = padre;\r\n"
		        + "        " + lv_str_nombre_tabla + " = new " + lv_str_nombre_tabla + "(clsConexion);\r\n" + "        vg_int_filter_column = -1;\r\n" + "        vg_str_keywords = \"\";\r\n"
		        + "        vg_bool_filtered = false;\r\n" + "        try {\r\n" + "            jbInit();\r\n" + "        } catch (Exception e) {\r\n" + "            e.printStackTrace();\r\n"
		        + "        }\r\n" + "        Recargar(-1);\r\n" + "        if(" + lv_str_nombre_tabla + ".is_autentification_active()){\r\n"
		        + "          this.vg_str_usuario_id = padre.vg_str_usuario_id;\r\n" + "          vg_obj_controls_list = new Vector();\r\n" + "          vg_obj_controls_list.add(Add);\r\n"
		        + "          vg_obj_controls_list.add(\"Add\");\r\n" + "          vg_obj_controls_list.add(Modify);\r\n" + "          vg_obj_controls_list.add(\"Modify\");\r\n"
		        + "          vg_obj_controls_list.add(Delete);\r\n" + "          vg_obj_controls_list.add(\"Delete\");\r\n" + "          vg_obj_controls_list.add(Refresh);\r\n"
		        + "          vg_obj_controls_list.add(\"Refresh\");\r\n" + "          vg_obj_controls_list.add(Search);\r\n" + "          vg_obj_controls_list.add(\"Search\");\r\n"
		        + "          CargarControlesFormulario();\r\n" + "        }\r\n" + "    }\r\n" + "\r\n" + "    private void jbInit() throws Exception {\r\n"
		        + "        this.getContentPane().setLayout( layoutMain );\r\n" + "        this.setSize(new Dimension(1000, 600));\r\n" + "        this.setTitle(" + lv_str_nombre_tabla
		        + ".getTitulo());\r\n" + "        this.setClosable(true);\r\n" + "        this.setIconifiable(true);\r\n" + "        this.setInheritsPopupMenu(true);\r\n"
		        + "        this.addInternalFrameListener(new InternalFrameAdapter() {\r\n" + "                public void internalFrameActivated(InternalFrameEvent e) {\r\n"
		        + "                    this_internalFrameActivated(e);\r\n" + "                }\r\n" + "\r\n" + "                public void internalFrameDeiconified(InternalFrameEvent e) {\r\n"
		        + "                    this_internalFrameDeiconified(e);\r\n" + "                }\r\n" + "            });\r\n" + "        panel_header.setPreferredSize(new Dimension(240, 60));\r\n"
		        + "        panel_header.setLayout(borderLayout1);\r\n" + "        panel_header.setBackground(Color.white);\r\n" + "        Add.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Add.setBackground(Color.white);\r\n" + "        Add.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Add.setBounds(new Rectangle(5, 5, 40, 40));\r\n" + "        Add.setSize(new Dimension(40, 40));\r\n" + "        Add.setIcon(add);\r\n"
		        + "        Add.setMargin(new Insets(0, 0, 0, 0));\r\n" + "        Add.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Add.setToolTipText(\"Nuevo\");\r\n"
		        + "        Add.setPressedIcon(add_pressed);\r\n" + "        Add.setRolloverIcon(add_over);\r\n" + "        Add.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n" + "                        Add_actionPerformed(e);\r\n" + "                    }\r\n"
		        + "                });\r\n" + "        Modify.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Modify.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Modify.setBackground(Color.white);\r\n" + "        Modify.setBounds(new Rectangle(50, 5, 40, 40));\r\n" + "        Modify.setIcon(modify);\r\n"
		        + "        Modify.setMargin(new Insets(0, 0, 0, 0));\r\n" + "        Modify.setHorizontalTextPosition(SwingConstants.CENTER);\r\n"
		        + "        Modify.setToolTipText(\"Modificar\");\r\n" + "        Modify.setSize(new Dimension(40, 40));\r\n" + "        Modify.setRolloverIcon(modify_over);\r\n"
		        + "        Modify.setPressedIcon(modify_pressed);\r\n" + "        Modify.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n" + "                        Modify_actionPerformed(e);\r\n" + "                    }\r\n"
		        + "                });\r\n" + "        Delete.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Delete.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Delete.setBackground(Color.white);\r\n" + "        Delete.setBounds(new Rectangle(95, 5, 40, 40));\r\n" + "        Delete.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Delete.setIcon(delete);\r\n" + "        Delete.setToolTipText(\"Eliminar\");\r\n" + "        Delete.setHorizontalTextPosition(SwingConstants.CENTER);\r\n"
		        + "        Delete.setSize(new Dimension(40, 40));\r\n" + "        Delete.setPressedIcon(delete_pressed);\r\n" + "        Delete.setRolloverIcon(delete_over);\r\n"
		        + "        Delete.addActionListener(new ActionListener() {\r\n" + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Delete_actionPerformed(e);\r\n" + "                    }\r\n" + "                });\r\n"
		        + "        Refresh.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        Refresh.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Refresh.setBackground(Color.white);\r\n" + "        Refresh.setBounds(new Rectangle(140, 5, 40, 40));\r\n" + "        Refresh.setSize(new Dimension(40, 40));\r\n"
		        + "        Refresh.setMargin(new Insets(0, 0, 0, 0));\r\n" + "        Refresh.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Refresh.setIcon(refresh);\r\n"
		        + "        Refresh.setToolTipText(\"Recargar Lista\");\r\n" + "        Refresh.setRolloverIcon(refresh_over);\r\n" + "        Refresh.setPressedIcon(refresh_pressed);\r\n"
		        + "        Refresh.addActionListener(new ActionListener() {\r\n" + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Refresh_actionPerformed(e);\r\n" + "                    }\r\n" + "                });\r\n" + "        panel_menu.setLayout(null);\r\n"
		        + "        panel_menu.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n" + "        panel_menu.setBackground(new Color(153, 211, 138));\r\n"
		        + "        panel_menu.setPreferredSize(new Dimension(1, 50));\r\n" + "        Search.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Search.setFont(new Font(\"Dialog\", 1, 11));\r\n" + "        Search.setBackground(Color.white);\r\n" + "        Search.setBounds(new Rectangle(185, 5, 40, 40));\r\n"
		        + "        Search.setSize(new Dimension(40, 40));\r\n" + "        Search.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Search.setHorizontalTextPosition(SwingConstants.CENTER);\r\n" + "        Search.setIcon(search);\r\n" + "        Search.setToolTipText(\"Filtrar\");\r\n"
		        + "        Search.setPressedIcon(search_pressed);\r\n" + "        Search.setRolloverIcon(search_over);\r\n" + "        Search.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n" + "                        Search_actionPerformed(e);\r\n" + "                    }\r\n"
		        + "                });\r\n" + "        extra_information.setLayout(null);\r\n" + "        extra_information.setPreferredSize(new Dimension(1, 10));\r\n"
		        + "        extra_information.setBackground(Color.white);\r\n" + "        extra_information.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        panel_menu.add(Search, null);\r\n" + "        panel_menu.add(Modify, null);\r\n" + "        panel_menu.add(Add, null);\r\n" + "        panel_menu.add(Delete, null);\r\n"
		        + "        panel_menu.add(Refresh, null);\r\n" + "        panel_header.add(panel_menu, BorderLayout.CENTER);\r\n"
		        + "        panel_header.add(extra_information, BorderLayout.NORTH);\r\n" + "        this.getContentPane().add(panel_header, BorderLayout.NORTH);\r\n"
		        + "        jScrollPane1.getViewport().add(jTable1, null);\r\n" + "        this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);\r\n" + "    }\r\n" + "    \r\n"
		        + "    public void Recargar(int id_buscar){\r\n" + "        jTable1.setModel(" + lv_str_nombre_tabla + ".Consultar_Lista_Tabla(vg_int_filter_column, vg_str_keywords));\r\n"
		        + "        int ancho_tabla = 0;\r\n" + "        for (int i = 0; i < jTable1.getColumnCount(); i++)  {\r\n"
		        + "            jTable1.getColumnModel().getColumn(i).setPreferredWidth((Integer)" + lv_str_nombre_tabla + ".getAncho_columnas_tabla().get(i));\r\n"
		        + "            ancho_tabla += (Integer)" + lv_str_nombre_tabla + ".getAncho_columnas_tabla().get(i);\r\n" + "        }\r\n" + "        if(id_buscar != -1){\r\n"
		        + "            jTable1.changeSelection(" + lv_str_nombre_tabla + ".Buscar_posicion_de_un_id(id_buscar),0,false,false);\r\n" + "        }\r\n"
		        + "        this.setSize(new Dimension(ancho_tabla+10,this.getHeight()));\r\n" + "        jScrollPane1.setSize(new Dimension(ancho_tabla,jScrollPane1.getHeight()));\r\n"
		        + "        jTable1.requestFocus();\r\n" + "    }\r\n" + "    \r\n" + "    public void Administrate(int option){\r\n" + "        switch(option){\r\n" + "            case 1 : \r\n"
		        + "                Open_abm_form(\"-1\");\r\n" + "                if(frmabm" + lv_str_nombre_tabla + ".getSelected_option() == 1){\r\n"
		        + "                    Recargar(Integer.parseInt(frmabm" + lv_str_nombre_tabla + ".getlClase_id()));\r\n" + "                }\r\n" + "            break;\r\n"
		        + "            case 2 : \r\n" + "                if(jTable1.getSelectedRow() != -1){\r\n" + "                    Open_abm_form(" + lv_str_nombre_tabla
		        + ".getId().get(jTable1.getSelectedRow()).toString());\r\n" + "                    if(frmabm" + lv_str_nombre_tabla + ".getSelected_option() == 1 || frmabm" + lv_str_nombre_tabla
		        + ".getSelected_option() == 2){\r\n" + "                        Recargar(Integer.parseInt(frmabm" + lv_str_nombre_tabla + ".getlClase_id()));\r\n" + "                    }\r\n"
		        + "                }else{\r\n" + "                    Pop_up_error_message(\"No ha seleccionado ningun registro.\");\r\n" + "                }\r\n" + "            break;\r\n"
		        + "            case 3 : \r\n" + "                if(jTable1.getSelectedRow() != -1){\r\n" + "                    Delete();\r\n" + "                }else{\r\n"
		        + "                    Pop_up_error_message(\"No ha seleccionado ningun registro.\");\r\n" + "                }\r\n" + "            break;\r\n" + "            case 4 :\r\n"
		        + "              if(vg_bool_filtered){\r\n" + "                Search.setIcon(search);\r\n" + "                Search.setToolTipText(\"Filtrar\");\r\n"
		        + "                vg_bool_filtered = false;\r\n" + "                vg_int_filter_column = -1;\r\n" + "                vg_str_keywords = \"\";\r\n"
		        + "                Recargar(-1);\r\n" + "              }else{\r\n" + "                jdg_search jdg_search = new jdg_search(" + lv_str_nombre_tabla + ".getCampos_mostrar_tabla(), "
		        + lv_str_nombre_tabla + ".getEtiquetas_columnas());\r\n" + "                jdg_search.setLocationRelativeTo(this);\r\n" + "                jdg_search.setVisible(true);\r\n"
		        + "                if(jdg_search.isVg_b_filtrado()){\r\n" + "                  Search.setIcon(search_pressed);\r\n" + "                  Search.setToolTipText(\"Romper filtro\");\r\n"
		        + "                  vg_int_filter_column = jdg_search.get_selected_field();\r\n" + "                  vg_str_keywords = jdg_search.getVg_str_filtro();\r\n"
		        + "                  vg_bool_filtered = true;\r\n" + "                  Recargar(-1);\r\n" + "                }\r\n" + "              }\r\n" + "            break;\r\n"
		        + "        }\r\n" + "    }\r\n" + "    \r\n" + "    public void Open_abm_form(String id){\r\n" + "        frmabm" + lv_str_nombre_tabla + " = new frmabm" + lv_str_nombre_tabla + "("
		        + lv_str_nombre_tabla + ".getCon(),id,this);\r\n" + "        frmabm" + lv_str_nombre_tabla + ".setLocationRelativeTo(this);\r\n" + "        frmabm" + lv_str_nombre_tabla
		        + ".setVisible(true);\r\n" + "    }\r\n" + "    \r\n" + "    public void Delete(){\r\n"
		        + "        int i =JOptionPane.showConfirmDialog(this,\"¿REALMENTE DESEA ELIMINAR ESTE REGISTRO?\",\"ELIMINAR REGISTRO\",JOptionPane.YES_NO_OPTION);\r\n" + "        if(i == 0){\r\n"
		        + "            " + lv_str_nombre_tabla + ".setValor(0," + lv_str_nombre_tabla + ".getId().get(jTable1.getSelectedRow()).toString());\r\n" + "            if(" + lv_str_nombre_tabla
		        + ".Borrar(0)){\r\n" + "                int id_recargar = -1;\r\n" + "                if(" + lv_str_nombre_tabla + ".getId().size() > 1){\r\n"
		        + "                    if(jTable1.getSelectedRow() == 0){\r\n" + "                        id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(1).toString());\r\n"
		        + "                    }else{\r\n" + "                        id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(jTable1.getSelectedRow()-1).toString());\r\n"
		        + "                    }\r\n" + "                }\r\n" + "                Recargar(id_recargar);\r\n" + "            }\r\n" + "        }\r\n" + "    }\r\n" + "\r\n" + "    public "
		        + lv_str_nombre_tabla + " get" + lv_str_nombre_tabla + "() {\r\n" + "        return " + lv_str_nombre_tabla + ";\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Add_actionPerformed(ActionEvent e) {\r\n" + "        Administrate(1);\r\n" + "    }\r\n" + "\r\n" + "    private void Modify_actionPerformed(ActionEvent e) {\r\n"
		        + "        Administrate(2);\r\n" + "    }\r\n" + "    \r\n" + "    public void Pop_up_error_message(String message){\r\n"
		        + "        JOptionPane.showMessageDialog(this,message,\"Error\",JOptionPane.ERROR_MESSAGE);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Delete_actionPerformed(ActionEvent e) {\r\n" + "        Administrate(3);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void Refresh_actionPerformed(ActionEvent e) {\r\n" + "        int id_recargar = jTable1.getSelectedRow();\r\n" + "        if(id_recargar != -1){\r\n"
		        + "            id_recargar = Integer.parseInt(" + lv_str_nombre_tabla + ".getId().get(id_recargar).toString());\r\n" + "        }\r\n" + "        Recargar(id_recargar);\r\n"
		        + "    }\r\n" + "\r\n" + "    private void Search_actionPerformed(ActionEvent e) {\r\n" + "       Administrate(4);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void this_internalFrameActivated(InternalFrameEvent e) {\r\n" + "      Recargar(-1);\r\n" + "    }\r\n" + "\r\n"
		        + "    private void this_internalFrameDeiconified(InternalFrameEvent e) {\r\n" + "      Refresh_actionPerformed(null);\r\n" + "    }\r\n" + "\r\n"
		        + "    public void CargarControlesFormulario(){\r\n" + "        if(vg_str_usuario_id != null && vg_str_usuario_id.length() > 0){\r\n"
		        + "          for (int i = 1; i < vg_obj_controls_list.size(); i += 2) {\r\n" + "              String vl_form_name = \"frm" + lv_str_nombre_tabla + "\";\r\n"
		        + "              boolean vl_bool_active = " + lv_str_nombre_tabla + ".is_control_active(vg_str_usuario_id,vl_form_name,vg_obj_controls_list.get(i).toString());\r\n"
		        + "              if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JButton\")){\r\n"
		        + "                ((JButton)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "              }else{\r\n"
		        + "                if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JMenu\")){\r\n"
		        + "                  ((JMenu)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "                }else{\r\n"
		        + "                  if(vg_obj_controls_list.get(i-1).getClass().toString().equals(\"class javax.swing.JMenuItem\")){\r\n"
		        + "                    ((JMenuItem)vg_obj_controls_list.get(i-1)).setEnabled(vl_bool_active);\r\n" + "                  }else{\r\n"
		        + "                    System.out.println(\"No encontrado : \" + vg_obj_controls_list.get(i).getClass().toString());\r\n" + "                  }\r\n" + "                }\r\n"
		        + "              }\r\n" + "          }\r\n" + "        }else{\r\n" + "          System.exit(0);\r\n" + "        }\r\n" + "    }\r\n" + "}\r\n";
		return vl_s_table_source;
	}

	public boolean m_b_visible(String vl_s_visibility) {
		boolean vl_b_visible = true;
		if (vl_s_visibility.compareTo("hidden") == 0) {
			vl_b_visible = false;
		}
		return vl_b_visible;
	}

	public String m_add_label(String vl_s_nombre) {
		String vl_s_component = "";
		vl_s_component = "    private JLabel jLabel_" + vl_s_nombre + " = new JLabel();\r\n";
		return vl_s_component;
	}

	public String m_set_label_position(String vl_s_nombre_tabla, String vl_s_nombre, int vl_i_id_atributo, boolean vl_b_null) {
		String vl_s_component = "";
		if (vl_b_null) {
			vl_s_component += "        jLabel_" + vl_s_nombre + ".setText(" + vl_s_nombre_tabla + ".getEtiqueta_Columna(" + vl_i_id_atributo + ")+\" : \");\r\n";
		} else {
			vl_s_component += "        jLabel_" + vl_s_nombre + ".setText(" + vl_s_nombre_tabla + ".getEtiqueta_Columna(" + vl_i_id_atributo + ")+\"(*) : \");\r\n";
		}
		vl_s_component += "        jLabel_" + vl_s_nombre + ".setBounds(new Rectangle(35, " + vg_i_pos_y + ", 150, 30));\r\n" + "        jLabel_" + vl_s_nombre
		        + ".setFont(new Font(\"Times New Roman\", 1, 12));\r\n" + "        jLabel_" + vl_s_nombre + ".setHorizontalAlignment(SwingConstants.RIGHT);\r\n" + "        panelCenter.add(jLabel_"
		        + vl_s_nombre + ", null);\r\n";
		return vl_s_component;
	}

	public String m_add_component(String vl_s_component_type, String vl_s_component_name, String vl_s_tabla_foranea, String vl_str_visibility) {
		String vl_s_component = "";
		if (vl_s_component_type.compareTo("combobox") == 0) {
			vl_s_component += "    private JComboBox " + vl_s_component_name + " = new JComboBox();\r\n";
			if (vl_s_tabla_foranea.length() > 0) {
				vl_s_component += "    " + vl_s_tabla_foranea + " " + vl_s_tabla_foranea + ";\r\n";
			}
			vl_s_component += "    Vector ids_" + vl_s_component_name + ", etiquetas_" + vl_s_component_name + ";\r\n";
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
				vl_s_component += "    private JTextField " + vl_s_component_name + " = new JTextField();\r\n";
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
					vl_s_component += "    private JScrollPane sc" + vl_s_component_name + " = new JScrollPane();\r\n";
					vl_s_component += "    private JTextArea " + vl_s_component_name + " = new JTextArea();\r\n";
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
						if (vl_str_visibility.compareTo("readonly") == 0) {
							vl_s_component += "    private JTextField " + vl_s_component_name + " = new JTextField();\r\n";
						} else {
							vl_s_component += "    private JDateChooser " + vl_s_component_name + " = new JDateChooser();\r\n";
						}
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component += "    private JComboBox " + vl_s_component_name + "_h = new JComboBox();\r\n" + "    private JComboBox " + vl_s_component_name
							        + "_m = new JComboBox();\r\n" + "    private JComboBox " + vl_s_component_name + "_s = new JComboBox();\r\n" + "    private JLabel " + vl_s_component_name
							        + "_hh = new JLabel();\r\n" + "    private JLabel " + vl_s_component_name + "_mm = new JLabel();\r\n" + "    private JLabel " + vl_s_component_name
							        + "_ss = new JLabel();\r\n" + "    Vector etiquetas_" + vl_s_component_name + "_h;\r\n" + "    Vector etiquetas_" + vl_s_component_name + "_m;\r\n"
							        + "    Vector etiquetas_" + vl_s_component_name + "_s;\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component += "//Componente para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
									vl_s_component += "    private JTextField " + vl_s_component_name + " = new JTextField();\r\n";
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public boolean es_combo(String vl_nombre_variable, String vl_valor_comparar, int vl_int_posini, int vl_int_posiciones) {
		return vl_nombre_variable.substring(vl_int_posini, vl_int_posini + vl_int_posiciones).compareTo(vl_valor_comparar) == 0;
	}

	public String m_add_contructor(String vl_s_component_type, String vl_s_nombre_tabla, String vl_s_component_name, String vl_s_tabla_foranea) {
		String vl_s_component = "";
		if (vl_s_component_type.compareTo("combobox") == 0) {
			if (vl_s_tabla_foranea.length() > 0) {
				vl_s_component += "        " + vl_s_tabla_foranea + " = new " + vl_s_tabla_foranea + "(clsConexion);\r\n" + "        Vector opciones_" + vl_s_component_name + " = "
				        + vl_s_tabla_foranea + ".Consultar_Lista_Combo();\r\n";
			} else {
				if (es_combo(vl_s_component_name, "activo", vl_s_component_name.length() - 6, 6)) {
					vl_s_component += "        Vector opciones_" + vl_s_component_name + " = " + vl_s_nombre_tabla + ".getValores_Activo();\r\n";
				} else {
					vl_s_component += "        Vector opciones_" + vl_s_component_name + " = new Vector();\r\n";
				}
			}
			vl_s_component += "        ids_" + vl_s_component_name + " = (Vector)opciones_" + vl_s_component_name + ".get(0);\r\n" + "        etiquetas_" + vl_s_component_name
			        + " = (Vector)opciones_" + vl_s_component_name + ".get(1);\r\n" + "        " + vl_s_component_name + " = new JComboBox(etiquetas_" + vl_s_component_name + ");\r\n";
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component += "        etiquetas_" + vl_s_component_name + "_h = " + vl_s_nombre_tabla + ".getValores_Hora();\r\n" + "        etiquetas_" + vl_s_component_name
							        + "_m = " + vl_s_nombre_tabla + ".getValores_Minuto();\r\n" + "        etiquetas_" + vl_s_component_name + "_s = " + vl_s_nombre_tabla
							        + ".getValores_Segundo();\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component += "//Constructor para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public String m_add_position(String vl_s_component_type, String vl_s_nombre_tabla, String vl_s_component_name, int vl_i_id_atributo, String vl_str_visibility) {
		String vl_s_component = "";
		if (vl_s_component_type.compareTo("combobox") == 0) {
			vl_s_component += "        " + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name + ".setSize(new Dimension(300, 30));\r\n"
			        + "        " + vl_s_component_name + ".setBounds(new Rectangle(185, " + vg_i_pos_y + ", 300, 30));\r\n" + "        " + vl_s_component_name + ".setFocusable(" + vl_s_nombre_tabla
			        + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        panelCenter.add(" + vl_s_component_name + ", null);\r\n";
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
				vl_s_component += "        " + vl_s_component_name + ".setBounds(new Rectangle(185, " + vg_i_pos_y + ", 250, 30));\r\n" + "        " + vl_s_component_name
				        + ".setSize(new Dimension(300, 30));\r\n" + "        " + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name
				        + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        " + vl_s_component_name + ".setDocument(new LimitadorCaracteres(" + vl_s_component_name + ",null," + vl_s_nombre_tabla
				        + ".getLongitud(" + vl_i_id_atributo + ")," + vl_s_nombre_tabla + ".getTipo(" + vl_i_id_atributo + ")));\r\n" + "        " + vl_s_component_name + ".setFocusable("
				        + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        panelCenter.add(" + vl_s_component_name + ", null);\r\n";
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
					vl_s_component += "        " + vl_s_component_name + ".setSize(new Dimension(290, 60));\r\n" + "        " + vl_s_component_name + ".setRows(2);\r\n" + "        "
					        + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        "
					        + vl_s_component_name + ".setLineWrap(true);\r\n" + "        " + vl_s_component_name + ".setDocument(new LimitadorCaracteres(null," + vl_s_component_name + ","
					        + vl_s_nombre_tabla + ".getLongitud(" + vl_i_id_atributo + ")," + vl_s_nombre_tabla + ".getTipo(" + vl_i_id_atributo + ")));\r\n" + "        " + vl_s_component_name
					        + ".setFocusable(" + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        sc" + vl_s_component_name + ".setBounds(new Rectangle(185, "
					        + vg_i_pos_y + ", 300, 55));\r\n" + "        sc" + vl_s_component_name + ".setSize(new Dimension(300, 60));\r\n" + "        sc" + vl_s_component_name
					        + ".getViewport().add(" + vl_s_component_name + ", null);\r\n" + "        panelCenter.add(sc" + vl_s_component_name + ", null);\r\n";
					vg_i_pos_y += 30;
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
						if (vl_str_visibility.compareTo("readonly") == 0) {
							vl_s_component += "        " + vl_s_component_name + ".setBounds(new Rectangle(185, " + vg_i_pos_y + ", 250, 30));\r\n" + "        " + vl_s_component_name
							        + ".setSize(new Dimension(300, 30));\r\n" + "        " + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        "
							        + vl_s_component_name + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        " + vl_s_component_name + ".setDocument(new LimitadorCaracteres("
							        + vl_s_component_name + ",null," + vl_s_nombre_tabla + ".getLongitud(" + vl_i_id_atributo + ")," + vl_s_nombre_tabla + ".getTipo(" + vl_i_id_atributo + ")));\r\n"
							        + "        " + vl_s_component_name + ".setFocusable(" + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        panelCenter.add("
							        + vl_s_component_name + ", null);\r\n";
						} else {
							vl_s_component += "        " + vl_s_component_name + ".setBounds(new Rectangle(185, " + vg_i_pos_y + ", 290, 30));\r\n" + "        " + vl_s_component_name
							        + ".setSize(new Dimension(300, 30));\r\n" + "        " + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        "
							        + vl_s_component_name + ".setDateFormatString(\"dd/MM/yyyy\");\r\n" + "        " + vl_s_component_name + ".setFocusable(" + vl_s_nombre_tabla + ".is_editable("
							        + vl_i_id_atributo + "));\r\n" + "        panelCenter.add(" + vl_s_component_name + ", null);\r\n";
						}
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component += "        " + vl_s_component_name + "_h = new JComboBox(etiquetas_" + vl_s_component_name + "_h);\r\n" + "        " + vl_s_component_name
							        + "_h.setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name + "_h.setSize(new Dimension(300, 30));\r\n" + "        "
							        + vl_s_component_name + "_h.setBounds(new Rectangle(185, " + vg_i_pos_y + ", 50, 30));\r\n" + "        " + vl_s_component_name + "_m = new JComboBox(etiquetas_"
							        + vl_s_component_name + "_m);\r\n" + "        " + vl_s_component_name + "_m.setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name
							        + "_m.setSize(new Dimension(300, 30));\r\n" + "        " + vl_s_component_name + "_m.setBounds(new Rectangle(265, " + vg_i_pos_y + ", 50, 30));\r\n" + "        "
							        + vl_s_component_name + "_s = new JComboBox(etiquetas_" + vl_s_component_name + "_s);\r\n" + "        " + vl_s_component_name
							        + "_s.setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        " + vl_s_component_name + "_s.setSize(new Dimension(300, 30));\r\n" + "        "
							        + vl_s_component_name + "_s.setBounds(new Rectangle(345, " + vg_i_pos_y + ", 50, 30));\r\n" + "        " + vl_s_component_name + "_hh.setText(\"(h) : \");\r\n"
							        + "        " + vl_s_component_name + "_hh.setBounds(new Rectangle(235, " + vg_i_pos_y + ", 30, 30));\r\n" + "        " + vl_s_component_name
							        + "_hh.setFont(new Font(\"Times New Roman\", 1, 12));\r\n" + "        " + vl_s_component_name + "_hh.setHorizontalAlignment(SwingConstants.LEFT);\r\n" + "        "
							        + vl_s_component_name + "_mm.setText(\"(m) : \");\r\n" + "        " + vl_s_component_name + "_mm.setBounds(new Rectangle(315, " + vg_i_pos_y + ", 30, 30));\r\n"
							        + "        " + vl_s_component_name + "_mm.setFont(new Font(\"Times New Roman\", 1, 12));\r\n" + "        " + vl_s_component_name
							        + "_mm.setHorizontalAlignment(SwingConstants.LEFT);\r\n" + "        " + vl_s_component_name + "_ss.setText(\"(s)\");\r\n" + "        " + vl_s_component_name
							        + "_ss.setBounds(new Rectangle(395, " + vg_i_pos_y + ", 30, 30));\r\n" + "        " + vl_s_component_name
							        + "_ss.setFont(new Font(\"Times New Roman\", 1, 12));\r\n" + "        " + vl_s_component_name + "_ss.setHorizontalAlignment(SwingConstants.LEFT);\r\n" + "        "
							        + vl_s_component_name + "_h.setFocusable(" + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        " + vl_s_component_name
							        + "_m.setFocusable(" + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        " + vl_s_component_name + "_s.setFocusable("
							        + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n" + "        panelCenter.add(" + vl_s_component_name + "_ss, null);\r\n"
							        + "        panelCenter.add(" + vl_s_component_name + "_mm, null);\r\n" + "        panelCenter.add(" + vl_s_component_name + "_hh, null);\r\n"
							        + "        panelCenter.add(" + vl_s_component_name + "_s, null);\r\n" + "        panelCenter.add(" + vl_s_component_name + "_m, null);\r\n"
							        + "        panelCenter.add(" + vl_s_component_name + "_h, null);\r\n;";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component += "//Position para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
									vl_s_component += "        " + vl_s_component_name + ".setBounds(new Rectangle(185, " + vg_i_pos_y + ", 250, 30));\r\n" + "        " + vl_s_component_name
									        + ".setSize(new Dimension(300, 30));\r\n" + "        " + vl_s_component_name + ".setFont(new Font(\"Times New Roman\", 0, 14));\r\n" + "        "
									        + vl_s_component_name + ".setMargin(new Insets(5, 3, 5, 2));\r\n" + "        " + vl_s_component_name + ".setDocument(new LimitadorCaracteres("
									        + vl_s_component_name + ",null," + vl_s_nombre_tabla + ".getLongitud(" + vl_i_id_atributo + ")," + vl_s_nombre_tabla + ".getTipo(" + vl_i_id_atributo
									        + ")));\r\n" + "        " + vl_s_component_name + ".setFocusable(" + vl_s_nombre_tabla + ".is_editable(" + vl_i_id_atributo + "));\r\n"
									        + "        panelCenter.add(" + vl_s_component_name + ", null);\r\n";
								}
							}
						}
					}
				}
			}
		}
		vl_s_component += "\r\n";
		return vl_s_component;
	}

	public String m_add_Cargar(String vl_s_component_type, String vl_s_nombre_tabla, String vl_s_component_name, int vl_i_id_atributo, String vl_str_visibility) {
		String vl_s_component = "";
		if (vl_s_component_type.compareTo("combobox") == 0) {
			vl_s_component += "        int id_cargar_" + vl_s_component_name + " = " + vl_s_nombre_tabla + ".Buscar_posicion_de_un_valor(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo
			        + "),ids_" + vl_s_component_name + ");\r\n" + "        if(id_cargar_" + vl_s_component_name + " != -1){\r\n" + "            " + vl_s_component_name
			        + ".setSelectedIndex(id_cargar_" + vl_s_component_name + ");\r\n" + "        }\r\n";
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
				vl_s_component += "        " + vl_s_component_name + ".setText(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo + "));\r\n";
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
					vl_s_component += "        " + vl_s_component_name + ".setText(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo + "));\r\n";
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
						if (vl_str_visibility.compareTo("readonly") == 0) {
							vl_s_component += "        " + vl_s_component_name + ".setText(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo + "));\r\n";
						} else {
							vl_s_component += "        " + vl_s_component_name + ".setDate(" + vl_s_nombre_tabla + ".Transformar_Texto_a_Fecha(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo
							        + ")));\r\n";
						}
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component += "        " + vl_s_component_name + "_h.setSelectedItem(" + vl_s_nombre_tabla + ".Transformar_h_m_s_hora(" + vl_s_nombre_tabla + ".getValor("
							        + vl_i_id_atributo + "),1));\r\n" + "        " + vl_s_component_name + "_m.setSelectedItem(" + vl_s_nombre_tabla + ".Transformar_h_m_s_hora(" + vl_s_nombre_tabla
							        + ".getValor(" + vl_i_id_atributo + "),2));\r\n" + "        " + vl_s_component_name + "_s.setSelectedItem(" + vl_s_nombre_tabla + ".Transformar_h_m_s_hora("
							        + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo + "),3));\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component += "//Cargar para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
									vl_s_component += "        " + vl_s_component_name + ".setText(" + vl_s_nombre_tabla + ".getValor(" + vl_i_id_atributo + "));\r\n" + "        if(insert){\r\n"
									        + "          " + vl_s_component_name + ".setText(" + vl_s_nombre_tabla + ".getNext_Registro(" + vl_s_nombre_tabla + ".getValor_Parametro(\"frmabm"
									        + vl_s_nombre_tabla + "." + vl_s_component_name + "\")));\r\n" + "        }\r\n";
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public String m_add_Recuperar(String vl_s_component_type, String vl_s_nombre_tabla, String vl_s_component_name, int vl_i_id_atributo, String vl_str_visibility) {
		String vl_s_component = "";
		if (vl_s_component_type.compareTo("combobox") == 0) {
			vl_s_component += "        int id_recuperado_" + vl_s_component_name + " = " + vl_s_component_name + ".getSelectedIndex();\r\n" + "        String valor_recuperado_" + vl_s_component_name
			        + " = \"\";\r\n" + "        if(id_recuperado_" + vl_s_component_name + " >= 0){\r\n" + "          valor_recuperado_" + vl_s_component_name + " = ids_" + vl_s_component_name
			        + ".get(id_recuperado_" + vl_s_component_name + ").toString();\r\n" + "        }\r\n" + "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + ",valor_recuperado_"
			        + vl_s_component_name + ");\r\n";
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
				vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_component_name + ".getText());\r\n";
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
					vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_component_name + ".getText());\r\n";
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
						if (vl_str_visibility.compareTo("readonly") == 0) {
							vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_nombre_tabla + ".Transformar_Fecha_a_Texto(" + vl_s_nombre_tabla
							        + ".Transformar_Texto_a_Fecha(" + vl_s_component_name + ".getText())));\r\n";
						} else {
							vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_nombre_tabla + ".Transformar_Fecha_a_Texto(" + vl_s_component_name
							        + ".getDate()));\r\n";
						}
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_component_name + "_h.getSelectedItem()+\":\"+" + vl_s_component_name
							        + "_m.getSelectedItem()+\":\"+" + vl_s_component_name + "_s.getSelectedItem());\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component += "//Recuperar para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
									vl_s_component += "        " + vl_s_nombre_tabla + ".setValor(" + vl_i_id_atributo + "," + vl_s_component_name + ".getText());\r\n";
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public String m_add_Desmarcar(String vl_s_component_type, String vl_s_component_name) {
		String vl_s_component = "        " + vl_s_component_name + ".setBackground(Color.white);\r\n";
		if (vl_s_component_type.compareTo("combobox") == 0) {
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component = "        " + vl_s_component_name + "_h.setBackground(Color.white);\r\n" + "        " + vl_s_component_name + "_m.setBackground(Color.white);\r\n"
							        + "        " + vl_s_component_name + "_s.setBackground(Color.white);\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component = "//Desmarcar para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public String m_add_Error(String vl_s_component_type, String vl_s_component_name, int vl_i_id_atributo) {
		String vl_s_component = "                case " + vl_i_id_atributo + " : " + vl_s_component_name + ".requestFocus(); " + vl_s_component_name + ".setBackground(Color.yellow); break;\r\n";
		if (vl_s_component_type.compareTo("combobox") == 0) {
		} else {
			if (vl_s_component_type.compareTo("textfield") == 0) {
			} else {
				if (vl_s_component_type.compareTo("textarea") == 0) {
				} else {
					if (vl_s_component_type.compareTo("datepicker") == 0) {
					} else {
						if (vl_s_component_type.compareTo("timepicker") == 0) {
							vl_s_component = "                case " + vl_i_id_atributo + " : \r\n" + "                      " + vl_s_component_name + "_h.requestFocus(); " + vl_s_component_name
							        + "_h.setBackground(Color.yellow);\r\n" + "                      " + vl_s_component_name + "_m.setBackground(Color.yellow);\r\n" + "                      "
							        + vl_s_component_name + "_s.setBackground(Color.yellow);\r\n" + "                break;\r\n";
						} else {
							if (vl_s_component_type.compareTo("datetimepicker") == 0) {
								vl_s_component = "//Error para Datetimepicker\r\n";
							} else {
								if (vl_s_component_type.compareTo("autoincrement") == 0) {
								}
							}
						}
					}
				}
			}
		}
		return vl_s_component;
	}

	public String Generate_frmabm(String vl_s_nombre_tabla, String[][] vl_str_data, String vl_foranea, String vl_str_tabla_padre) {
		String vl_s_table_source = "";
		String componentes = "";
		String constructor = "";
		String posiciones = "";
		String datos_cargar = "";
		String datos_recuperar = "";
		String desmarcar = "";
		String error = "";
		String vl_str_update_autoincrements = "";
		vg_i_pos_y = 20;
		for (int i = 0; i < vl_str_data.length; i++) {
			if (vl_foranea.compareTo(vl_str_data[i][0]) != 0) {
				if (m_b_visible(vl_str_data[i][6])) {
					componentes += m_add_label(vl_str_data[i][0]);
					posiciones += m_set_label_position(vl_s_nombre_tabla, vl_str_data[i][0], i, Boolean.parseBoolean(vl_str_data[i][2]));
					componentes += m_add_component(vl_str_data[i][7], vl_str_data[i][0], vl_str_data[i][5], vl_str_data[i][6]);
					constructor += m_add_contructor(vl_str_data[i][7], vl_s_nombre_tabla, vl_str_data[i][0], vl_str_data[i][5]);
					posiciones += m_add_position(vl_str_data[i][7], vl_s_nombre_tabla, vl_str_data[i][0], i, vl_str_data[i][6]);
					datos_cargar += m_add_Cargar(vl_str_data[i][7], vl_s_nombre_tabla, vl_str_data[i][0], i, vl_str_data[i][6]);
					desmarcar += m_add_Desmarcar(vl_str_data[i][7], vl_str_data[i][0]);
					datos_recuperar += m_add_Recuperar(vl_str_data[i][7], vl_s_nombre_tabla, vl_str_data[i][0], i, vl_str_data[i][6]);
					error += m_add_Error(vl_str_data[i][7], vl_str_data[i][0], i);
					vg_i_pos_y += 35;
					if (vl_str_data[i][7].compareTo("autoincrement") == 0) {
						vl_str_update_autoincrements += "                " + vl_s_nombre_tabla + ".update_param_value(\"frmabm" + vl_s_nombre_tabla + "." + vl_str_data[i][0] + "\");\r\n";
					}
				}
			} else {
				datos_recuperar += "        " + vl_s_nombre_tabla + ".setValor(" + i + ",frm" + vl_s_nombre_tabla + ".frm" + vl_str_tabla_padre + ".lClase_id);\r\n";
			}
		}
		vg_i_pos_y += 110;
		int vl_i_id_concurrencia = vl_str_data.length - 1;
		vl_s_table_source = "package Presentacion;\r\n" + "\r\n" + "import Datos.clsConexion;\r\n" + "import Negocio.*;\r\n" + "import Utilitarios.*;\r\n"
		        + "import com.toedter.calendar.JDateChooser;\r\n" + "import java.awt.BorderLayout;\r\n" + "import java.awt.Color;\r\n" + "import java.awt.Dimension;\r\n" + "import java.awt.Font;\r\n"
		        + "import java.awt.Insets;\r\n" + "import java.awt.Rectangle;\r\n" + "import java.awt.event.ActionEvent;\r\n" + "import java.awt.event.ActionListener;\r\n"
		        + "import java.util.Vector;\r\n" + "import javax.swing.BorderFactory;\r\n" + "import javax.swing.JOptionPane;\r\n" + "import javax.swing.ImageIcon;\r\n"
		        + "import javax.swing.JButton;\r\n" + "import javax.swing.JComboBox;\r\n" + "import javax.swing.JDialog;\r\n" + "import javax.swing.JLabel;\r\n" + "import javax.swing.JPanel;\r\n"
		        + "import javax.swing.JScrollPane;\r\n" + "import javax.swing.JTextArea;\r\n" + "import javax.swing.JTextField;\r\n" + "import javax.swing.SwingConstants;\r\n" + "\r\n"
		        + "public class frmabm"
		        + vl_s_nombre_tabla
		        + " extends JDialog {\r\n"
		        + "    private BorderLayout layoutMain = new BorderLayout();\r\n"
		        + "    private JPanel panelCenter = new JPanel();\r\n"
		        + "    private JLabel statusBar = new JLabel();\r\n"
		        + "    private JPanel menu_bar = new JPanel();\r\n"
		        + "    private JButton Save = new JButton();\r\n"
		        + "    private JButton Cancel = new JButton();\r\n"
		        + "    private ImageIcon save = new ImageIcon(getClass().getResource(\"/save.png\"));\r\n"
		        + "    private ImageIcon save_over = new ImageIcon(getClass().getResource(\"/save_over.png\"));\r\n"
		        + "    private ImageIcon save_pressed = new ImageIcon(getClass().getResource(\"/save_pressed.png\"));\r\n"
		        + "    private ImageIcon cancel = new ImageIcon(getClass().getResource(\"/cancel.png\"));\r\n"
		        + "    private ImageIcon cancel_over = new ImageIcon(getClass().getResource(\"/cancel_over.png\"));\r\n"
		        + "    private ImageIcon cancel_pressed = new ImageIcon(getClass().getResource(\"/cancel_pressed.png\"));\r\n"
		        + "\r\n"
		        + componentes
		        + "\r\n"
		        + "    String lClase_id,iConcurrency_id;\r\n"
		        + "    frm"
		        + vl_s_nombre_tabla
		        + " frm"
		        + vl_s_nombre_tabla
		        + ";\r\n"
		        + "    "
		        + vl_s_nombre_tabla
		        + " "
		        + vl_s_nombre_tabla
		        + ";\r\n"
		        + "    int selected_option;\r\n"
		        + "    boolean insert;\r\n"
		        + "\r\n"
		        + "    public frmabm"
		        + vl_s_nombre_tabla
		        + "(clsConexion clsConexion,String lClase_id,frm"
		        + vl_s_nombre_tabla
		        + " frm"
		        + vl_s_nombre_tabla
		        + ") {\r\n"
		        + "        super(frm"
		        + vl_s_nombre_tabla
		        + ".padre);\r\n"
		        + "        "
		        + vl_s_nombre_tabla
		        + " = new "
		        + vl_s_nombre_tabla
		        + "(clsConexion);\r\n"
		        + "        this.lClase_id = lClase_id;\r\n"
		        + "        this.frm"
		        + vl_s_nombre_tabla
		        + " = frm"
		        + vl_s_nombre_tabla
		        + ";\r\n"
		        + "        insert = false;\r\n"
		        + "        selected_option = 0;\r\n"
		        + constructor
		        + "        try {\r\n"
		        + "            jbInit();\r\n"
		        + "        } catch (Exception e) {\r\n"
		        + "            e.printStackTrace();\r\n"
		        + "        }\r\n"
		        + "        Desmarcar();\r\n"
		        + "        CargarDatos();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    private void jbInit() throws Exception {\r\n"
		        + "        this.getContentPane().setLayout( layoutMain );\r\n"
		        + "        panelCenter.setLayout( null );\r\n"
		        + "        this.setTitle(\"ADMINISTRAR \"+"
		        + vl_s_nombre_tabla
		        + ".getTitulo());\r\n"
		        + "        this.setResizable(false);\r\n"
		        + "        this.setModal(true);\r\n"
		        + "        statusBar.setText( \"\" );\r\n"
		        + "        menu_bar.setBackground(new Color(153, 211, 138));\r\n"
		        + "        menu_bar.setPreferredSize(new Dimension(71, 50));\r\n"
		        + "        menu_bar.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        menu_bar.setLayout(null);\r\n"
		        + "        Save.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Save.setBackground(Color.white);\r\n"
		        + "        Save.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Save.setPreferredSize(new Dimension(40, 40));\r\n"
		        + "        Save.setBounds(new Rectangle(5, 5, 40, 40));\r\n"
		        + "        Save.setMargin(new Insets(0, 0, 0, 0));\r\n"
		        + "        Save.setIcon(save);\r\n"
		        + "        Save.setPressedIcon(save_pressed);\r\n"
		        + "        Save.setRolloverIcon(save_over);\r\n"
		        + "        Save.setToolTipText(\"Guardar\");\r\n"
		        + "        Save.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Save_actionPerformed(e);\r\n"
		        + "                    }\r\n"
		        + "                });\r\n"
		        + "        Cancel.setBorder(BorderFactory.createLineBorder(Color.black, 1));\r\n"
		        + "        Cancel.setBackground(Color.white);\r\n"
		        + "        Cancel.setFont(new Font(\"Dialog\", 1, 11));\r\n"
		        + "        Cancel.setBounds(new Rectangle(50, 5, 40, 40));\r\n"
		        + "        Cancel.setSize(new Dimension(40, 40));\r\n"
		        + "        Cancel.setRolloverIcon(cancel_over);\r\n"
		        + "        Cancel.setPressedIcon(cancel_pressed);\r\n"
		        + "        Cancel.setIcon(cancel);\r\n"
		        + "        Cancel.setToolTipText(\"Cancelar\");\r\n"
		        + "        Cancel.addActionListener(new ActionListener() {\r\n"
		        + "                    public void actionPerformed(ActionEvent e) {\r\n"
		        + "                        Cancel_actionPerformed(e);\r\n"
		        + "                    }\r\n"
		        + "                });\r\n"
		        + "        menu_bar.add(Save, null);\r\n"
		        + "        menu_bar.add(Cancel, null);\r\n"
		        + "        this.getContentPane().add(statusBar, BorderLayout.SOUTH );\r\n"
		        + "        this.getContentPane().add(menu_bar, BorderLayout.NORTH);\r\n"
		        + "\r\n"
		        + posiciones
		        + "\r\n"
		        + "        this.setSize(new Dimension(569, "
		        + vg_i_pos_y
		        + "));\r\n"
		        + "        this.getContentPane().add(panelCenter, BorderLayout.CENTER);\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    private void jButton2_actionPerformed(ActionEvent e) {\r\n"
		        + "        Cerrar();\r\n"
		        + "    }\r\n"
		        + "    \r\n"
		        + "    public void CargarDatos(){\r\n"
		        + "        if(Integer.parseInt(lClase_id) != -1){\r\n"
		        + "            "
		        + vl_s_nombre_tabla
		        + ".setValor(0,lClase_id);\r\n"
		        + "            "
		        + vl_s_nombre_tabla
		        + ".Consultar(0);\r\n"
		        + "        }else{\r\n"
		        + "            "
		        + vl_s_nombre_tabla
		        + ".setValor(0, "
		        + vl_s_nombre_tabla
		        + ".Get_Siguiente_Id());\r\n"
		        + "            lClase_id = "
		        + vl_s_nombre_tabla
		        + ".getValor(0);\r\n"
		        + "            insert = true;\r\n"
		        + "        }\r\n"
		        + datos_cargar
		        + "        iConcurrency_id = "
		        + vl_s_nombre_tabla
		        + ".getValor("
		        + vl_i_id_concurrencia
		        + ");\r\n"
		        + "    }\r\n"
		        + "    \r\n"
		        + "    public void RecuperarDatos(){\r\n"
		        + datos_recuperar
		        + "        "
		        + vl_s_nombre_tabla
		        + ".setValor("
		        + vl_i_id_concurrencia
		        + ",iConcurrency_id);\r\n"
		        + "    }\r\n"
		        + "    \r\n"
		        + "    public void Guardar(){\r\n"
		        + "        RecuperarDatos();\r\n"
		        + "        Desmarcar();\r\n"
		        + "        int validar = "
		        + vl_s_nombre_tabla
		        + ".Validar_Datos();\r\n"
		        + "        if(validar == -1){\r\n"
		        + "            if(insert){\r\n"
		        + vl_str_update_autoincrements
		        + "                "
		        + vl_s_nombre_tabla
		        + ".Insertar();\r\n"
		        + "                Cerrar();\r\n"
		        + "            }else{\r\n"
		        + "                if("
		        + vl_s_nombre_tabla
		        + ".Validar_Concurrencia("
		        + vl_i_id_concurrencia
		        + ")){\r\n"
		        + "                    "
		        + vl_s_nombre_tabla
		        + ".Actualizar(0);\r\n"
		        + "                }else{\r\n"
		        + "                    JOptionPane.showMessageDialog(this, \"El registro ha sido modificado por otro usuario.\\nLa ventana se cerrara.\", \"Error de concurrencia\", JOptionPane.ERROR_MESSAGE);\r\n"
		        + "                    selected_option = 2;\r\n"
		        + "                }\r\n"
		        + "                Cerrar();\r\n"
		        + "            }\r\n"
		        + "        }else{\r\n"
		        + "            switch(validar){\r\n"
		        + error
		        + "            }\r\n"
		        + "            JOptionPane.showMessageDialog(this, \"Se ha encontrado un error en el campo \"+"
		        + vl_s_nombre_tabla
		        + ".getEtiqueta_Columna(validar)+\".\\nFavor corrija el error para continuar con la operacion.\", \"Error de validacion\", JOptionPane.ERROR_MESSAGE);\r\n"
		        + "        }\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    public void Desmarcar(){\n"
		        + desmarcar
		        + "    }"
		        + "\r\n"
		        + "    public void Cerrar(){\r\n"
		        + "        this.dispose();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    private void Save_actionPerformed(ActionEvent e) {\r\n"
		        + "        selected_option = 1;\r\n"
		        + "        Guardar();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    private void Cancel_actionPerformed(ActionEvent e) {\r\n"
		        + "        selected_option = 0;\r\n"
		        + "        Cerrar();\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    public int getSelected_option() {\r\n"
		        + "        return selected_option;\r\n"
		        + "    }\r\n"
		        + "\r\n"
		        + "    public String getlClase_id() {\r\n"
		        + "        return lClase_id;\r\n" + "    }\r\n" + "}\r\n";
		return vl_s_table_source;
	}
}
