package com.apptriggergenerator.triggergenerator.utils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class clsBase {
	public clsConexion con;
	public String nombre_tabla;
	public String sql;
	public ResultSet resultados;
	public String[] valor_inicial;
	public String[] nombres_columnas;
	public int[] tipos_columnas;
	public boolean[] son_nulos;
	public String[] etiquetas_columnas;
	public String[] datos_fila;
	public int[] longitud_maxima;
	public int[] display;
	public Object[] vg_obj_foranea;
	public int cantidad_registros;
	public int numero_columnas;
	public val_tablaids val_tablaids;
	public DateFormat formatoFecha, formatoString;
	public String Titulo;
	public Vector id, etiquetas, campos_mostrar_tabla, ancho_columnas_tabla, campos_ordenar_tabla, campos_mostrar_combo;
	public Vector tipo_lista;
	public String param_nm_cod, param_value_cod;
	public final int dd_int = 1;
	public final int dd_double = 2;
	public final int dd_String = 3;
	public final int dd_Date = 4;
	public final int dd_Time = 5;
	public final int dd_Datetime = 6;
	public final int td_visible = 1;
	public final int td_readonly = 2;
	public final int td_hidden = 3;
	public final boolean n_null_true = true;
	public final boolean n_null_false = false;

	public clsBase() {
		con = new clsConexion();
		Agregar_Atributos_Base();
	}

	public clsBase(clsConexion con) {
		this.con = con;
		Agregar_Atributos_Base();
	}

	public void Agregar_Atributos_Base() {
		formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		formatoString = new SimpleDateFormat("yyyy-MM-dd");
		sql = "";
		nombre_tabla = "";
		cantidad_registros = 0;
		numero_columnas = 0;
		campos_mostrar_tabla = new Vector();
		campos_ordenar_tabla = new Vector();
		ancho_columnas_tabla = new Vector();
		campos_mostrar_combo = new Vector();
	}

	public Date Transformar_Texto_a_Fecha(String texto) {
		try {
			return formatoFecha.parse(texto);
		} catch (Exception ex) {
			return null;
		}
	}

	public Date Transformar_Texto_a_Fecha_inv(String texto) {
		try {
			return formatoString.parse(texto);
		} catch (Exception ex) {
			return null;
		}
	}

	public String Lista_Combo() {
		String sql_combo = Preparar_consulta_combo_new();
		return sql_combo;
	}

	public String Transformar_h_m_s_hora(String texto, int tipo) {
		String result = "";
		if (texto.length() == 8) {
			switch (tipo) {
			case 1:
				result = texto.substring(0, 2);
				break;
			case 2:
				result = texto.substring(3, 5);
				break;
			case 3:
				result = texto.substring(6, 8);
				break;
			}
		}
		return result;
	}

	public String Transformar_Fecha_a_Texto(Date fecha) {
		try {
			return formatoString.format(fecha);
		} catch (Exception ex) {
			return null;
		}
	}

	public void Inicializar_Vectores(int numero) {
		numero_columnas = numero;
		nombres_columnas = new String[numero_columnas];
		datos_fila = new String[numero_columnas];
		tipos_columnas = new int[numero_columnas];
		etiquetas_columnas = new String[numero_columnas];
		son_nulos = new boolean[numero_columnas];
		longitud_maxima = new int[numero_columnas];
		valor_inicial = new String[numero_columnas];
		display = new int[numero_columnas];
		vg_obj_foranea = new Object[numero_columnas];
	}

	public void Agregar_atributo(int orden, String etiqueta, String nombre, int longitud, int tipo, String valor_inicial, boolean nulos, int modo_pantalla, Object tabla_foranea) {
		this.etiquetas_columnas[orden] = etiqueta;
		this.nombres_columnas[orden] = nombre;
		this.tipos_columnas[orden] = tipo;
		this.son_nulos[orden] = nulos;
		this.longitud_maxima[orden] = longitud;
		this.datos_fila[orden] = valor_inicial;
		this.valor_inicial[orden] = valor_inicial;
		this.display[orden] = modo_pantalla;
		this.vg_obj_foranea[orden] = tabla_foranea;
	}

	public int Total_Ancho_Tabla() {
		int total = 0;
		for (int i = 0; i < ancho_columnas_tabla.size(); i++) {
			total += (Integer) ancho_columnas_tabla.get(i);
		}
		return total;
	}

	public void Resetear_valores() {
		for (int i = 0; i < numero_columnas; i++) {
			datos_fila[i] = valor_inicial[i];
		}
	}

	public String Ordenar_Atributos_Insertar() {
		String atributos = "";
		for (int i = 0; i < nombres_columnas.length; i++) {
			atributos += nombres_columnas[i];
			if (i < (numero_columnas - 1)) {
				atributos += ",";
			}
		}
		return atributos;
	}

	public String Ordenar_Atributos_Actualizar() {
		String atributos = "";
		for (int i = 0; i < nombres_columnas.length; i++) {
			atributos += nombres_columnas[i] + " = " + Preparar_Un_Atributos(i);
			if (i < (numero_columnas - 1)) {
				atributos += " ,";
			}
		}
		return atributos;
	}

	public String Ordenar_Atributos_Actualizar_2(int[] ids) {
		String atributos = "";
		for (int i = 0; i < ids.length; i++) {
			int aux = ids[i];
			atributos += nombres_columnas[aux] + " = " + Preparar_Un_Atributos(aux);
			if (i < (ids.length - 1)) {
				atributos += " ,";
			}
		}
		return atributos;
	}

	public String Ordenar_Formato_Fecha(String fecha) {
		String nueva_fecha = null;
		if (fecha != null && fecha.length() > 0) {
			nueva_fecha = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
		}
		return nueva_fecha;
	}

	public String Preparar_consulta_lista(int vl_filter_field, String vl_keywords) {
		String vl_select = "";
		String vl_order = "";
		String vl_where = "";
		String vl_from = "" + nombre_tabla;
		String vl_aux_consulta = "";
		String sql_consulta = "select " + getNombre_Columna(0) + ",";
		for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
			clsBase vl_aux_clsBase = (clsBase) vg_obj_foranea[(Integer) campos_mostrar_tabla.get(i)];
			if (vl_aux_clsBase != null) {
				vl_aux_consulta = getNombre_Columna((Integer) campos_mostrar_tabla.get(i)).replace("pk_", "");
				vl_from += ", (" + vl_aux_clsBase.Lista_Combo() + ") " + vl_aux_consulta;
				if (vl_where.length() == 0) {
					vl_where += " where ";
				} else {
					vl_where += " and ";
				}
				vl_where += vl_aux_consulta + "." + vl_aux_clsBase.getNombre_Columna(0) + " = " + nombre_tabla + "." + getNombre_Columna((Integer) campos_mostrar_tabla.get(i));
				vl_aux_consulta += ".descripcion";
			} else {
				vl_aux_consulta = Preparar_Atributo_Consultar((Integer) campos_mostrar_tabla.get(i), true);
			}
			if (vl_filter_field == (Integer) campos_mostrar_tabla.get(i)) {
				if (vl_where.length() == 0) {
					vl_where += " where ";
				} else {
					vl_where += " and ";
				}
				vl_where += vl_aux_consulta + " like '%" + vl_keywords + "%'";
			}
			if (i < (campos_mostrar_tabla.size() - 1)) {
				vl_aux_consulta += ",";
			}
			vl_select += vl_aux_consulta;
		}
		for (int i = 0; i < campos_ordenar_tabla.size(); i++) {
			clsBase vl_aux_clsBase = (clsBase) vg_obj_foranea[(Integer) campos_ordenar_tabla.get(i)];
			if (vl_aux_clsBase != null) {
				vl_aux_consulta = getNombre_Columna((Integer) campos_ordenar_tabla.get(i)).replace("pk_", "");
				vl_aux_consulta += ".descripcion";
			} else {
				vl_aux_consulta = Preparar_Atributo_Consultar((Integer) campos_ordenar_tabla.get(i), true);
			}
			if (i < (campos_ordenar_tabla.size() - 1)) {
				vl_aux_consulta += ",";
			}
			vl_order += vl_aux_consulta;
		}
		sql_consulta += vl_select + " from " + vl_from + vl_where + " order by " + vl_order;
		return sql_consulta;
	}

	public String Preparar_consulta_lista(int vl_pos_foranea, String vl_s_valor_foranea, int vl_filter_field, String vl_keywords) {
		String vl_select = "";
		String vl_order = "";
		String vl_where = "";
		String vl_from = "" + nombre_tabla;
		String vl_aux_consulta = "";
		String sql_consulta = "select " + getNombre_Columna(0) + ",";
		for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
			clsBase vl_aux_clsBase = (clsBase) vg_obj_foranea[(Integer) campos_mostrar_tabla.get(i)];
			if (vl_aux_clsBase != null) {
				vl_aux_consulta = getNombre_Columna((Integer) campos_mostrar_tabla.get(i)).replace("pk_", "");
				vl_from += ", (" + vl_aux_clsBase.Lista_Combo() + ") " + vl_aux_consulta;
				if (vl_where.length() == 0) {
					vl_where += " where " + nombre_tabla + "." + nombres_columnas[vl_pos_foranea] + " = " + vl_s_valor_foranea + " and ";
				} else {
					vl_where += " and ";
				}
				vl_where += vl_aux_consulta + "." + vl_aux_clsBase.getNombre_Columna(0) + " = " + nombre_tabla + "." + getNombre_Columna((Integer) campos_mostrar_tabla.get(i));
				vl_aux_consulta += ".descripcion";
			} else {
				vl_aux_consulta = Preparar_Atributo_Consultar((Integer) campos_mostrar_tabla.get(i), true);
			}
			if (vl_filter_field == (Integer) campos_mostrar_tabla.get(i)) {
				if (vl_where.length() == 0) {
					vl_where += " where ";
				} else {
					vl_where += " and ";
				}
				vl_where += vl_aux_consulta + " like '%" + vl_keywords + "%'";
			}
			if (i < (campos_mostrar_tabla.size() - 1)) {
				vl_aux_consulta += ",";
			}
			vl_select += vl_aux_consulta;
		}
		for (int i = 0; i < campos_ordenar_tabla.size(); i++) {
			clsBase vl_aux_clsBase = (clsBase) vg_obj_foranea[(Integer) campos_ordenar_tabla.get(i)];
			if (vl_aux_clsBase != null) {
				vl_aux_consulta = getNombre_Columna((Integer) campos_ordenar_tabla.get(i)).replace("pk_", "");
				vl_aux_consulta += ".descripcion";
			} else {
				vl_aux_consulta = Preparar_Atributo_Consultar((Integer) campos_ordenar_tabla.get(i), true);
			}
			if (i < (campos_ordenar_tabla.size() - 1)) {
				vl_aux_consulta += ",";
			}
			vl_order += vl_aux_consulta;
		}
		sql_consulta += vl_select + " from " + vl_from + vl_where + " order by " + vl_order;
		return sql_consulta;
	}

	public String Preparar_consulta_combo_new() {
		String vl_select = "";
		String vl_where = "";
		String vl_from = "" + nombre_tabla;
		String vl_aux_consulta = "";
		String sql_consulta = "select " + getNombre_Columna(0) + ", concat(";
		for (int i = 0; i < campos_mostrar_combo.size(); i++) {
			clsBase vl_aux_clsBase = (clsBase) vg_obj_foranea[(Integer) campos_mostrar_combo.get(i)];
			if (vl_aux_clsBase != null) {
				vl_aux_consulta = getNombre_Columna((Integer) campos_mostrar_combo.get(i)).replace("pk_", "");
				vl_from += ", (" + vl_aux_clsBase.Lista_Combo() + ") " + vl_aux_consulta;
				if (vl_where.length() == 0) {
					vl_where += " where ";
				} else {
					vl_where += " and ";
				}
				vl_where += vl_aux_consulta + "." + vl_aux_clsBase.getNombre_Columna(0) + " = " + nombre_tabla + "." + getNombre_Columna((Integer) campos_mostrar_combo.get(i));
				vl_aux_consulta += ".descripcion";
			} else {
				vl_aux_consulta = Preparar_Atributo_Consultar((Integer) campos_mostrar_combo.get(i), true);
			}
			if (i < (campos_mostrar_combo.size() - 1)) {
				vl_aux_consulta += ", ' - ',";
			}
			vl_select += vl_aux_consulta;
		}
		sql_consulta += vl_select + ") descripcion from " + vl_from + vl_where + " order by descripcion";
		return sql_consulta;
	}

	public String Preparar_Un_Atributos(int num) {
		String atributo = "";
		switch (tipos_columnas[num]) {
		// int
		case 1:
			atributo = datos_fila[num];
			break;
		// double
		case 2:
			atributo = datos_fila[num];
			break;
		// String
		case 3:
			atributo = "'" + datos_fila[num] + "'";
			break;
		// Date
		case 4:
			if (datos_fila[num].compareTo("sysdate") != 0) {
				if (datos_fila[num].length() == 0) {
					atributo = "null";
				} else {
					atributo = "'" + datos_fila[num] + "'";
				}
			} else {
				atributo = "sysdate()";
			}
			break;
		// Time
		case 5:
			if (datos_fila[num].compareTo("sysdate") != 0) {
				if (datos_fila[num].length() == 0) {
					atributo = "null";
				} else {
					atributo = "'" + datos_fila[num] + "'";
				}
			} else {
				atributo = "sysdate()";
			}
			break;
		// Datetime
		case 6:
			if (datos_fila[num].compareTo("sysdate") != 0) {
				if (datos_fila[num].length() == 0) {
					atributo = "null";
				} else {
					atributo = "'" + datos_fila[num] + "'";
				}
			} else {
				atributo = "sysdate()";
			}
			break;
		default:
			atributo = datos_fila[num];
			break;
		}
		return atributo;
	}

	public DefaultTableModel Consultar_Lista_Tabla(int vl_file_filter, String vl_str_keywords) {
		DefaultTableModel modelo = null;
		try {
			sql = Preparar_consulta_lista(vl_file_filter, vl_str_keywords);
			ResultSet rs = con.Consultar(sql);
			Vector headers = new Vector();
			for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
				headers.add(getEtiqueta_Columna((Integer) campos_mostrar_tabla.get(i)));
			}
			Vector resultado = new Vector();
			id = new Vector();
			Vector aux;
			while (rs.next()) {
				aux = new Vector();
				id.add(rs.getString(1));
				for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
					aux.add(rs.getString(i + 2));
				}
				resultado.add(aux);
			}
			modelo = new DefaultTableModel(resultado, headers);
		} catch (Exception ex) {
		}
		return modelo;
	}

	public DefaultTableModel Consultar_Lista_Tabla(int vl_pos_foranea, String pk_padre_id, int vl_file_filter, String vl_str_keywords) {
		DefaultTableModel modelo = null;
		try {
			sql = Preparar_consulta_lista(vl_pos_foranea, pk_padre_id, vl_file_filter, vl_str_keywords);
			ResultSet rs = con.Consultar(sql);
			Vector headers = new Vector();
			for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
				headers.add(getEtiqueta_Columna((Integer) campos_mostrar_tabla.get(i)));
			}
			Vector resultado = new Vector();
			id = new Vector();
			Vector aux;
			while (rs.next()) {
				aux = new Vector();
				id.add(rs.getString(1));
				for (int i = 0; i < campos_mostrar_tabla.size(); i++) {
					aux.add(rs.getString(i + 2));
				}
				resultado.add(aux);
			}
			modelo = new DefaultTableModel(resultado, headers);
		} catch (Exception ex) {
		}
		return modelo;
	}

	public Vector Consultar_Lista_Combo() {
		Vector lista = new Vector();
		try {
			sql = Preparar_consulta_combo_new();
			ResultSet rs = con.Consultar(sql);
			Vector ids_query = new Vector();
			Vector labels_query = new Vector();
			while (rs.next()) {
				ids_query.add(rs.getString(1));
				labels_query.add(rs.getString(2));
			}
			lista.add(ids_query);
			lista.add(labels_query);
		} catch (Exception ex) {
		}
		return lista;
	}

	public String Preparar_Valores_Insertar() {
		String atributos = "";
		for (int i = 0; i < numero_columnas; i++) {
			atributos += Preparar_Un_Atributos(i);
			if (i < (numero_columnas - 1)) {
				atributos += ",";
			}
		}
		return atributos;
	}

	public String Get_Siguiente_Id() {
		int siguiente_id = -1;
		if (val_tablaids == null) {
			val_tablaids = new val_tablaids(this.con);
		}
		val_tablaids.setValor(0, nombre_tabla);
		if (val_tablaids.Consultar(0)) {
			siguiente_id = Integer.parseInt(val_tablaids.getValor(1));
			val_tablaids.setValor(1, "" + (siguiente_id + 1));
			val_tablaids.setValor(2, "" + (Integer.parseInt(val_tablaids.getValor(2)) + 1));
		} else {
			siguiente_id = 1;
			val_tablaids.setValor(1, "" + (siguiente_id + 1));
			val_tablaids.setValor(2, "1");
		}
		return "" + siguiente_id;
	}

	public void Update_Siguiente_Id() {
		int siguiente_id = -1;
		if (val_tablaids == null) {
			val_tablaids = new val_tablaids(this.con);
		}
		val_tablaids.setValor(0, nombre_tabla);
		if (val_tablaids.Consultar(0)) {
			siguiente_id = Integer.parseInt(val_tablaids.getValor(1));
			val_tablaids.setValor(1, "" + (siguiente_id + 1));
			val_tablaids.setValor(2, "" + (Integer.parseInt(val_tablaids.getValor(2)) + 1));
			val_tablaids.Actualizar(0);
		} else {
			siguiente_id = 1;
			val_tablaids.setValor(1, "" + (siguiente_id + 1));
			val_tablaids.setValor(2, "1");
			val_tablaids.Insertar_Table_id();
		}
	}

	public boolean Insertar_Table_id() {
		boolean insertado = true;
		sql = "INSERT INTO " + nombre_tabla + "(" + Ordenar_Atributos_Insertar() + ") VALUES(" + Preparar_Valores_Insertar() + ")";
		insertado = con.Ejecutar(sql);
		if (insertado) {
			cantidad_registros++;
		}
		return insertado;
	}

	public boolean Insertar() {
		boolean insertado = true;
		sql = "INSERT INTO " + nombre_tabla + "(" + Ordenar_Atributos_Insertar() + ") VALUES(" + Preparar_Valores_Insertar() + ")";
		insertado = con.Ejecutar(sql);
		if (insertado) {
			Update_Siguiente_Id();
			cantidad_registros++;
		}
		return insertado;
	}

	public boolean Insertar2() {
		boolean insertado = true;
		sql = "INSERT INTO " + nombre_tabla + "(" + Ordenar_Atributos_Insertar() + ") VALUES(" + Preparar_Valores_Insertar() + ")";
		insertado = con.Ejecutar(sql);
		if (insertado) {
			cantidad_registros++;
		}
		return insertado;
	}

	public String Preparar_Atributo_Consultar(int num, boolean lista) {
		String atributo = "";
		switch (tipos_columnas[num]) {
		// int
		case 1:
			if (longitud_maxima[num] == 12) {
				if (lista) {
					atributo = "getnombre" + nombres_columnas[num].substring(1, nombres_columnas[num].length() - 3) + "(" + nombres_columnas[num] + ")";
				} else {
					atributo = nombres_columnas[num];
				}
			} else {
				if (longitud_maxima[num] == 2) {
					if (lista) {
						atributo = getConsulta_booleans(nombres_columnas[num]);
					} else {
						atributo = nombres_columnas[num];
					}
				} else {
					atributo = nombres_columnas[num];
				}
			}
			break;
		// double
		case 2:
			atributo = "round(" + nombres_columnas[num] + ",2)";
			break;
		// String
		case 3:
			atributo = nombres_columnas[num];
			break;
		// Date
		case 4:
			atributo = "DATE_FORMAT(" + nombres_columnas[num] + ",'%d/%m/%Y')";
			break;
		// Time
		case 5:
			// atributo = "time_format("+nombres_columnas[num]+",'%H:%i:%s')";
			atributo = "time_format(" + nombres_columnas[num] + ",'%H:%i:%s')";
			break;
		// Datetime
		case 6:
			atributo = "concat(date_format(" + nombres_columnas[num] + ",'%d/%m/%Y'),' ',time_format(" + nombres_columnas[num] + ",'%H:%i:%s'))";
			break;
		default:
			atributo = nombres_columnas[num];
			break;
		}
		return atributo;
	}

	public String Ordenar_Atributos_Consultar(boolean lista) {
		String atributos = "";
		for (int i = 0; i < nombres_columnas.length; i++) {
			atributos += Preparar_Atributo_Consultar(i, lista);
			if (i < (numero_columnas - 1)) {
				atributos += ",";
			}
		}
		return atributos;
	}

	public String Ordenar_Atributos_Consultar_Tabla(Vector lista_atributos, boolean lista) {
		String atributos = "";
		for (int i = 0; i < lista_atributos.size(); i++) {
			atributos += Preparar_Atributo_Consultar((Integer) lista_atributos.get(i), lista);
			if (i < (lista_atributos.size() - 1)) {
				atributos += ",";
			}
		}
		return atributos;
	}

	public String Ordenar_Atributos_Consultar_Combo(Vector lista_atributos, boolean lista) {
		String atributos = "concat(";
		for (int i = 0; i < lista_atributos.size(); i++) {
			atributos += Preparar_Atributo_Consultar((Integer) lista_atributos.get(i), lista);
			if (i < (lista_atributos.size() - 1)) {
				atributos += ",' ',";
			}
		}
		atributos += ")";
		return atributos;
	}

	public boolean Consultar(int id) {
		boolean exitoso = true;
		try {
			sql = "SELECT " + Ordenar_Atributos_Consultar(false) + " FROM " + nombre_tabla + " WHERE " + nombres_columnas[id] + " = " + Preparar_Un_Atributos(id);
			ResultSet rs = con.Consultar(sql);
			if (rs.next()) {
				for (int i = 0; i < numero_columnas; i++) {
					datos_fila[i] = rs.getString((i + 1));
				}
			} else {
				exitoso = false;
			}
		} catch (Exception ex) {
			exitoso = false;
		}
		return exitoso;
	}

	public boolean Actualizar(int numero) {
		boolean actualizado = true;
		sql = "UPDATE " + nombre_tabla + " SET " + Ordenar_Atributos_Actualizar() + " WHERE " + nombres_columnas[numero] + " = " + Preparar_Un_Atributos(numero);
		actualizado = con.Ejecutar(sql);
		return actualizado;
	}

	public boolean Actualizar_por_ids(int numero, int[] ids) {
		boolean actualizado = true;
		sql = "UPDATE " + nombre_tabla + " SET " + Ordenar_Atributos_Actualizar_2(ids) + " WHERE " + nombres_columnas[numero] + " = " + Preparar_Un_Atributos(numero);
		actualizado = con.Ejecutar(sql);
		return actualizado;
	}

	public boolean Borrar(int columna_id) {
		boolean eliminado = true;
		sql = "DELETE FROM " + nombre_tabla + " WHERE " + nombres_columnas[columna_id] + "=" + Preparar_Un_Atributos(columna_id);
		// sql = "call delete" + nombre_tabla + "(" + Preparar_Un_Atributos(columna_id) + ")";
		eliminado = con.Ejecutar(sql);
		if (eliminado) {
			cantidad_registros--;
		}
		return eliminado;
	}

	public int Buscar_posicion_de_un_id(int id_buscar) {
		int resultado = -1;
		int cont = 0;
		while (resultado == -1 && cont < id.size()) {
			if (Integer.parseInt("" + id.get(cont)) == id_buscar) {
				resultado = cont;
			}
			cont++;
		}
		return resultado;
	}

	public int Buscar_posicion_de_un_id_producto_servicio(int id_buscar, int tipo) {
		int resultado = -1;
		int cont = 0;
		while (resultado == -1 && cont < id.size()) {
			if (Integer.parseInt("" + id.get(cont)) == id_buscar && Integer.parseInt("" + tipo_lista.get(cont)) == tipo) {
				resultado = cont;
			}
			cont++;
		}
		return resultado;
	}

	public int Buscar_posicion_de_un_valor(Object id_buscar, Vector Object_list) {
		int resultado = -1;
		int cont = 0;
		while (resultado == -1 && cont < Object_list.size()) {
			if (Object_list.get(cont).equals(id_buscar)) {
				resultado = cont;
			}
			cont++;
		}
		return resultado;
	}

	public boolean Listar_Atributos(int columna_id, int columna_dsc) {
		boolean exitoso = true;
		try {
			sql = "SELECT " + nombres_columnas[columna_id] + "," + nombres_columnas[columna_dsc] + " FROM " + nombre_tabla + " ORDER BY " + nombres_columnas[columna_dsc];
			ResultSet rs = con.Consultar(sql);
			id = new Vector();
			etiquetas = new Vector();
			while (rs.next()) {
				id.add(rs.getString(1));
				etiquetas.add(rs.getString(2));
			}
		} catch (Exception ex) {
			exitoso = false;
		}
		return exitoso;
	}

	public boolean es_entero(String num) {
		boolean es = true;
		try {
			Integer.parseInt(num);
		} catch (Exception ex) {
			es = false;
		}
		return es;
	}

	public boolean es_decimal(String num) {
		boolean es = true;
		try {
			Double.parseDouble(num);
		} catch (Exception ex) {
			es = false;
		}
		return es;
	}

	public boolean es_fecha(String texto) {
		boolean es = false;
		if (texto.compareTo("sysdate") == 0) {
			es = true;
		} else {
			if (texto.length() == 10) {
				if (texto.substring(4, 5).equals("-") & texto.substring(7, 8).equals("-")) {
					if (es_entero(texto.substring(0, 4)) && es_entero(texto.substring(5, 7)) && es_entero(texto.substring(8, 10))) {
						es = true;
					}
				}
			}
		}
		return es;
	}

	public boolean es_hora(String texto) {
		boolean es = false;
		if (texto.compareTo("sysdate") == 0) {
			es = true;
		} else {
			if (texto.length() == 8) {
				if (texto.substring(2, 3).equals(":") && texto.substring(5, 6).equals(":")) {
					if (es_entero(texto.substring(0, 2)) && es_entero(texto.substring(3, 5)) && es_entero(texto.substring(6, 8))) {
						es = true;
					}
				}
			}
		}
		return es;
	}

	public boolean es_fecha_hora(String texto) {
		boolean es = false;
		if (texto.compareTo("sysdate") == 0) {
			es = true;
		} else {
			if (texto.length() == 19) {
				if (texto.substring(4, 5).equals("-") & texto.substring(7, 8).equals("-")) {
					if (es_entero(texto.substring(0, 4)) && es_entero(texto.substring(5, 7)) && es_entero(texto.substring(8, 10))) {
						if (texto.substring(10, 11).equals(":")) {
							if (texto.substring(13, 14).equals(":") && texto.substring(16, 17).equals(":")) {
								if (es_entero(texto.substring(11, 13)) && es_entero(texto.substring(14, 16)) && es_entero(texto.substring(17, 19))) {
									es = true;
								}
							}
						}
					}
				}
			}
		}
		return es;
	}

	public boolean Validar_Concurrencia(int id_concurrencia) {
		boolean exitoso = false;
		try {
			sql = "SELECT " + nombres_columnas[id_concurrencia] + " FROM " + nombre_tabla + " WHERE " + nombres_columnas[0] + " = " + Preparar_Un_Atributos(0);
			ResultSet rs = con.Consultar(sql);
			if (rs.next()) {
				if (rs.getInt(1) == Integer.parseInt(datos_fila[id_concurrencia])) {
					datos_fila[id_concurrencia] = "" + (Integer.parseInt(datos_fila[id_concurrencia]) + 1);
					exitoso = true;
				}
			}
		} catch (Exception ex) {
			exitoso = false;
		}
		return exitoso;
	}

	public int getCantidad_registros() {
		return cantidad_registros;
	}

	public int getNumero_columnas() {
		return numero_columnas;
	}

	public void setValor(int num, String valor) {
		datos_fila[num] = valor;
	}

	public String getValor(int num) {
		return datos_fila[num];
	}

	public int getTipo(int num) {
		return tipos_columnas[num];
	}

	public int getLongitud(int num) {
		return longitud_maxima[num];
	}

	public String getNombre_Columna(int num) {
		return nombres_columnas[num];
	}

	public String getEtiqueta_Columna(int num) {
		return etiquetas_columnas[num];
	}

	public String get_Fecha_Actual(int tipo_dato) {
		String fecha = "";
		try {
			String sql = "";
			ResultSet rs;
			if (tipo_dato == 4) {
				sql = "select DATE_FORMAT(curdate(),'%d/%m/%Y') from dual";
			} else {
				if (tipo_dato == 5) {
					sql = "select time_format(curtime(),'%H:%i:%s') from dual";
				} else {
					if (tipo_dato == 6) {
						sql = "select concat(date_format(curdate(),'%d/%m/%Y'),' ',time_format(curtime(),'%H:%i:%s')) from dual";
					}
				}
			}
			rs = con.Consultar(sql);
			if (rs.next()) {
				fecha = rs.getString(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fecha;
	}

	public Vector getId() {
		return id;
	}

	public Vector getEtiquetas() {
		return etiquetas;
	}

	public int getPosicion_Id(Vector vector, int num) {
		int pos = -1;
		for (int i = 0; i < vector.size(); i++) {
			if (Integer.parseInt("" + vector.get(i)) == num) {
				pos = i;
				i = vector.size();
			}
		}
		return pos;
	}

	public boolean Validar(int num) {
		boolean aceptado = false;
		if (datos_fila[num] == null || datos_fila[num].length() == 0) {
			if (son_nulos[num]) {
				aceptado = true;
				datos_fila[num] = "";
			}
		} else {
			String texto_joined = datos_fila[num];
			if (tipos_columnas[num] == 2) {
				texto_joined = texto_joined.replaceAll(".", "");
			}
			if (texto_joined.length() <= longitud_maxima[num]) {
				switch (tipos_columnas[num]) {
				// int
				case 1:
					aceptado = es_entero(datos_fila[num]);
					break;
				// double
				case 2:
					aceptado = es_decimal(datos_fila[num]);
					break;
				// String
				case 3:
					aceptado = true;
					break;
				// Date
				case 4:
					aceptado = es_fecha(datos_fila[num]);
					break;
				// Time
				case 5:
					aceptado = es_hora(datos_fila[num]);
					break;
				// Datetime
				case 6:
					aceptado = es_fecha_hora(datos_fila[num]);
					break;
				default:
					aceptado = true;
					break;
				}
			}
		}
		return aceptado;
	}

	public int Validar_Datos(int[] indices) {
		int erroneo = -1;
		for (int i = 0; i < indices.length; i++) {
			if (!Validar(indices[i])) {
				erroneo = indices[i];
				i = indices.length;
			}
		}
		return erroneo;
	}

	public int Validar(Vector campos) {
		int erroneo = -1;
		for (int i = 0; i < campos.size(); i++) {
			int numero = Integer.parseInt("" + campos.get(i));
			if (!Validar(numero)) {
				erroneo = numero;
				i = campos.size();
			}
		}
		return erroneo;
	}

	public int Validar_Datos() {
		int erroneo = -1;
		for (int i = 0; i < datos_fila.length; i++) {
			if (!Validar(i)) {
				erroneo = i;
				i = datos_fila.length;
			}
		}
		return erroneo;
	}

	public int Validar_Datos_PedidosServicios() {
		int erroneo = -1;
		for (int i = 0; i < datos_fila.length; i++) {
			if (!Validar(i)) {
				erroneo = i;
				i = datos_fila.length;
			}
		}
		boolean precio_dentro_rango = (Integer.parseInt(((Vector) Consulta_General("select getpreciodentrorango(" + datos_fila[1] + ",1," + datos_fila[3] + ")").get(0)).get(0).toString()) == 1);
		if (precio_dentro_rango) {
			precio_dentro_rango = (Integer.parseInt(((Vector) Consulta_General("select getpreciodentrorango(" + datos_fila[1] + ",2," + datos_fila[4] + ")").get(0)).get(0).toString()) == 1);
			if (!precio_dentro_rango) {
				erroneo = 4;
			}
		} else {
			erroneo = 3;
		}
		return erroneo;
	}

	public int Validar_Datos_Horario_Personal() {
		int erroneo = -1;
		for (int i = 0; i < datos_fila.length; i++) {
			if (!Validar(i)) {
				erroneo = i;
				i = datos_fila.length;
			}
		}
		if (erroneo == -1) {
			if ((Integer.parseInt(((Vector) Consulta_General(
			        "SELECT CASE " + datos_fila[3] + "*24*3600+TIME_TO_SEC('" + datos_fila[5] + "') > " + datos_fila[4] + "*24*3600+TIME_TO_SEC('" + datos_fila[6]
			                + "') WHEN TRUE THEN 1 ELSE 0 END resultado FROM DUAL ").get(0)).get(0).toString()) == 1)) {
				erroneo = -2;
			}
		}
		return erroneo;
	}

	public Vector<Vector<String>> Consulta_General(String Consulta) {
		Vector<Vector<String>> nombre = new Vector<Vector<String>>();
		try {
			resultados = con.Consultar(Consulta);
			Vector<String> contenedor_auxiliar;
			int longitud = resultados.getMetaData().getColumnCount();
			while (resultados.next()) {
				contenedor_auxiliar = new Vector<String>();
				for (int i = 1; i <= longitud; i++) {
					String aux = resultados.getString(i);
					if (aux == null) {
						aux = "";
					}
					contenedor_auxiliar.add(aux);
				}
				nombre.add(contenedor_auxiliar);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nombre;
	}

	public Vector getAncho_columnas_tabla() {
		return ancho_columnas_tabla;
	}

	public void setLongitud_maxima(int pos, int longitud) {
		this.longitud_maxima[pos] = longitud;
	}

	public int getLongitud_maxima(int pos) {
		return longitud_maxima[pos];
	}

	public clsConexion getCon() {
		return con;
	}

	public String getTitulo() {
		return Titulo;
	}

	// extras
	public Vector getValores_TipoPersonal() {
		Vector result_sexo = new Vector();
		Vector ids_result_sexo = new Vector();
		ids_result_sexo.add("1");
		ids_result_sexo.add("2");
		ids_result_sexo.add("3");
		Vector labels_result_sexo = new Vector();
		labels_result_sexo.add("Interno");
		labels_result_sexo.add("Externo");
		labels_result_sexo.add("Interno - Externo");
		result_sexo.add(ids_result_sexo);
		result_sexo.add(labels_result_sexo);
		return result_sexo;
	}

	public Vector getValores_Sexo() {
		Vector result_sexo = new Vector();
		Vector ids_result_sexo = new Vector();
		ids_result_sexo.add("1");
		ids_result_sexo.add("2");
		Vector labels_result_sexo = new Vector();
		labels_result_sexo.add("Masculino");
		labels_result_sexo.add("Femenino");
		result_sexo.add(ids_result_sexo);
		result_sexo.add(labels_result_sexo);
		return result_sexo;
	}

	public String getConsulta_booleans(String atributo) {
		String query = atributo;
		int guion_bajo = -1;
		for (int i = 0; i < atributo.length(); i++) {
			if (atributo.charAt(i) == '_') {
				guion_bajo = i;
				i = atributo.length();
			}
		}
		String aux_atributo = atributo.substring(guion_bajo + 1, atributo.length());
		if (guion_bajo != -1) {
			Vector opciones = new Vector();
			if (aux_atributo.matches("sexo")) {
				opciones = getValores_Sexo();
			} else {
				if (aux_atributo.matches("activo")) {
					opciones = getValores_Activo();
				} else {
					if (aux_atributo.matches("tipo")) {
						opciones = getValores_tipos_movimientos();
					} else {
						if (aux_atributo.matches("forma_pago")) {
							opciones = getValores_Forma_pago();
						} else {
							if (aux_atributo.matches("estado")) {
								opciones = getValores_estado();
							} else {
								if (aux_atributo.matches("incluido")) {
									opciones = getValores_Incluido();
								} else {
									if (aux_atributo.matches("dia_ingreso") || aux_atributo.matches("dia_salida")) {
										opciones = getValores_Dia();
									} else {
										if (aux_atributo.matches("ingreso_egreso")) {
											opciones = getValores_ingreso_egreso();
										} else {
										}
									}
								}
							}
						}
					}
				}
			}
			Vector id = (Vector) opciones.get(0);
			Vector etiquetas = (Vector) opciones.get(1);
			query = "case " + atributo;
			for (int i = 0; i < id.size(); i++) {
				query += " when " + id.get(i) + " then '" + etiquetas.get(i) + "'";
			}
			query += " else " + atributo + " end";
		}
		return query;
	}

	public Vector getValores_Activo() {
		Vector result_sexo = new Vector();
		Vector ids_result_sexo = new Vector();
		ids_result_sexo.add("1");
		ids_result_sexo.add("2");
		Vector labels_result_sexo = new Vector();
		labels_result_sexo.add("Activo");
		labels_result_sexo.add("Inactivo");
		result_sexo.add(ids_result_sexo);
		result_sexo.add(labels_result_sexo);
		return result_sexo;
	}

	public Vector getValores_Forma_pago() {
		Vector result_sexo = new Vector();
		Vector ids_result_sexo = new Vector();
		ids_result_sexo.add("1");
		ids_result_sexo.add("2");
		ids_result_sexo.add("3");
		ids_result_sexo.add("4");
		Vector labels_result_sexo = new Vector();
		labels_result_sexo.add("Efectivo");
		labels_result_sexo.add("Tarjeta de Credito");
		labels_result_sexo.add("Cheque");
		labels_result_sexo.add("Intercambio");
		result_sexo.add(ids_result_sexo);
		result_sexo.add(labels_result_sexo);
		return result_sexo;
	}

	public Vector getValores_Estado_Sesion() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		ids_result.add("3");
		Vector labels_result = new Vector();
		labels_result.add("Reserva");
		labels_result.add("Confirmado");
		labels_result.add("Completado");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public Vector getValores_Incluido() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		ids_result.add("3");
		ids_result.add("4");
		Vector labels_result = new Vector();
		labels_result.add("Incluido en Servicio");
		labels_result.add("Comprado Anteriormente");
		labels_result.add("Cargo Extra");
		labels_result.add("Ya Pagado");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public Vector getValores_ingreso_egreso() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		Vector labels_result = new Vector();
		labels_result.add("Ingreso");
		labels_result.add("Egreso");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public Vector getValores_estado() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		ids_result.add("3");
		Vector labels_result = new Vector();
		labels_result.add("Completado");
		labels_result.add("Pendiente");
		labels_result.add("Cancelado");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public Vector getValores_tipos_movimientos() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		ids_result.add("3");
		ids_result.add("4");
		ids_result.add("5");
		Vector labels_result = new Vector();
		labels_result.add("Compra");
		labels_result.add("Salida");
		labels_result.add("Preparar Alimento");
		labels_result.add("Realizar Prestamo");
		labels_result.add("Adquirir Prestamo");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public Vector getValores_Hora() {
		Vector result = new Vector();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				result.add("0" + i);
			} else {
				result.add("" + i);
			}
		}
		return result;
	}

	public Vector getValores_Minuto() {
		Vector result = new Vector();
		for (int i = 0; i < 60; i += 15) {
			if (i < 10) {
				result.add("0" + i);
			} else {
				result.add("" + i);
			}
		}
		return result;
	}

	public Vector getValores_Segundo() {
		Vector result = new Vector();
		for (int i = 0; i < 1; i++) {
			if (i < 10) {
				result.add("0" + i);
			} else {
				result.add("" + i);
			}
		}
		return result;
	}

	public Vector getValores_Dia() {
		Vector result = new Vector();
		Vector ids_result = new Vector();
		ids_result.add("1");
		ids_result.add("2");
		ids_result.add("3");
		ids_result.add("4");
		ids_result.add("5");
		ids_result.add("6");
		ids_result.add("7");
		Vector labels_result = new Vector();
		labels_result.add("Lunes");
		labels_result.add("Martes");
		labels_result.add("Miercoles");
		labels_result.add("Jueves");
		labels_result.add("Viernes");
		labels_result.add("Sabado");
		labels_result.add("Domingo");
		result.add(ids_result);
		result.add(labels_result);
		return result;
	}

	public String getValor_Parametro(String vl_str_nombre) {
		String vl_param_value = "";
		val_parametros val_parametros = new val_parametros(this.con);
		val_parametros.setValor(1, vl_str_nombre);
		if (val_parametros.Consultar(1)) {
			vl_param_value = val_parametros.getValor(2);
		} else {
			vl_param_value = "0";
			val_parametros.setValor(0, val_parametros.Get_Siguiente_Id());
			val_parametros.setValor(1, vl_str_nombre);
			val_parametros.setValor(2, vl_param_value);
			val_parametros.setValor(3, "0");
			if (!val_parametros.Insertar()) {
				vl_param_value = "";
			}
		}
		return vl_param_value;
	}

	public void update_param_value(String vl_str_nombre) {
		val_parametros val_parametros = new val_parametros(this.con);
		val_parametros.setValor(1, vl_str_nombre);
		if (val_parametros.Consultar(1)) {
			val_parametros.setValor(2, "" + (Integer.parseInt(val_parametros.getValor(2)) + 1));
			if (val_parametros.Validar_Concurrencia(3)) {
				val_parametros.Actualizar(0);
			}
		}
	}

	public String getNext_Registro(String param_value) {
		int vl_num_registro = 0;
		if (es_entero(param_value)) {
			vl_num_registro = Integer.parseInt(param_value);
			vl_num_registro++;
		}
		String vl_next_param_value = "" + vl_num_registro;
		param_value_cod = vl_next_param_value;
		for (int i = vl_next_param_value.length(); i < 5; i++) {
			vl_next_param_value = "0" + vl_next_param_value;
		}
		return vl_next_param_value;
	}

	public String getSiguienteNumeroSesion(String lServicioPedido_id) {
		String siguiente_num = "1";
		Vector aux_num = Consulta_General("SELECT MAX(CAST(lSesion_num AS UNSIGNED)) FROM tblsesion WHERE lServicioPedido_id = " + lServicioPedido_id);
		if (aux_num.size() != 0) {
			if (((Vector) aux_num.get(0)).get(0).toString().length() != 0) {
				siguiente_num = "" + (Integer.parseInt(((Vector) aux_num.get(0)).get(0).toString()) + 1);
			}
		}
		return siguiente_num;
	}

	public boolean is_autentification_active() {
		boolean vl_bool_result = false;
		val_parametros val_parametros = new val_parametros(this.con);
		val_parametros.setValor(1, "authentication");
		if (Integer.parseInt(((Vector) Consulta_General("SELECT COUNT(*) FROM val_parametros WHERE s_nombre = 'authentication'").get(0)).get(0).toString()) == 0) {
			val_parametros.setValor(0, val_parametros.Get_Siguiente_Id());
			val_parametros.setValor(2, "0");
			val_parametros.setValor(3, "0");
			if (val_parametros.Validar_Datos() == -1) {
				val_parametros.Insertar();
			}
		} else {
			if (val_parametros.Consultar(1)) {
				vl_bool_result = Integer.parseInt(val_parametros.getValor(2)) == 1;
			}
		}
		return vl_bool_result;
	}

	public boolean is_control_active(String vl_str_usuario_id, String vl_str_form_name, String vl_str_control_name) {
		boolean vl_bool_result = false;
		if (vl_str_usuario_id != null && vl_str_usuario_id.length() > 0 && vl_str_form_name != null && vl_str_form_name.length() > 0 && vl_str_control_name != null && vl_str_control_name.length() > 0) {
			if (Integer.parseInt(((Vector) Consulta_General("SELECT COUNT(*) FROM tbl_programas WHERE s_formulario = '" + vl_str_form_name + "' AND s_nombre = '" + vl_str_control_name + "'").get(0))
			        .get(0).toString()) == 0) {
				tbl_programas tbl_programas = new tbl_programas(this.con);
				tbl_programas.setValor(0, tbl_programas.Get_Siguiente_Id());
				tbl_programas.setValor(1, vl_str_form_name);
				tbl_programas.setValor(2, vl_str_control_name);
				tbl_programas.setValor(3, "0");
				if (tbl_programas.Validar_Datos() == -1) {
					tbl_programas.Insertar();
				}
			} else {
				vl_bool_result = Integer.parseInt(((Vector) Consulta_General(
				        "SELECT COUNT(*) FROM tbl_proroles \n" + "WHERE pk_rol_id IN(SELECT DISTINCT pk_rol_id \n" + "FROM tbl_rolusuarios WHERE pk_usuario_id = " + vl_str_usuario_id + ")\n"
				                + "AND pk_programa_id = (SELECT pk_programa_id \n" + "FROM tbl_programas WHERE s_formulario = '" + vl_str_form_name + "' AND s_nombre = '" + vl_str_control_name
				                + "')\n").get(0)).get(0).toString()) != 0;
			}
		}
		return vl_bool_result;
	}

	public boolean is_editable(int id) {
		boolean vl_b_editable = true;
		if (this.display[id] == this.td_readonly) {
			vl_b_editable = false;
		}
		return vl_b_editable;
	}

	public Vector getCampos_mostrar_tabla() {
		return campos_mostrar_tabla;
	}

	public String[] getEtiquetas_columnas() {
		return etiquetas_columnas;
	}

	public double Redondear(double num, int decimales) {
		int exp = 0;
		if (decimales > 0) {
			exp = 10;
			for (int i = 0; i < decimales; i++) {
				exp *= 10;
			}
		}
		return Math.rint(num * exp) / exp;
	}
}
