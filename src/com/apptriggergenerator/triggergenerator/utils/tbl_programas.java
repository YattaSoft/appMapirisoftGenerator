package com.apptriggergenerator.triggergenerator.utils;

public class tbl_programas extends clsBase {
	public tbl_programas() {
		super();
		Agregar_Atributos();
	}

	public tbl_programas(clsConexion con) {
		super(con);
		Agregar_Atributos();
	}

	public void Agregar_Atributos() {
		Titulo = "PROGRAMAS";
		nombre_tabla = "tbl_programas";
		Inicializar_Vectores(4);
		Agregar_atributo(0, "Programa", "pk_programa_id", 11, dd_int, "0", n_null_false, td_hidden, null);
		Agregar_atributo(1, "Formulario", "s_formulario", 250, dd_String, "", n_null_false, td_visible, null);
		Agregar_atributo(2, "Nombre", "s_nombre", 250, dd_String, "", n_null_false, td_visible, null);
		Agregar_atributo(3, "Concurrencia", "i_concurrencia", 11, dd_int, "0", n_null_false, td_hidden, null);
		campos_mostrar_tabla.add(1);// show in list - s_formulario
		campos_mostrar_tabla.add(2);// show in list - s_nombre
		ancho_columnas_tabla.add(200);// column width - s_formulario
		ancho_columnas_tabla.add(200);// column width - s_nombre
		campos_ordenar_tabla.add(1);// order by - s_formulario
		campos_ordenar_tabla.add(2);// order by - s_nombre
		campos_mostrar_combo.add(1);// combobox - s_formulario
		campos_mostrar_combo.add(2);// combobox - s_nombre
	}
}