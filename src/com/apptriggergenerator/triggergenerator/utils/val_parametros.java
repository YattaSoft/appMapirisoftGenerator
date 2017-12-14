package com.apptriggergenerator.triggergenerator.utils;

public class val_parametros extends clsBase {
	public val_parametros() {
		super();
		Agregar_Atributos();
	}

	public val_parametros(clsConexion con) {
		super(con);
		Agregar_Atributos();
	}

	public void Agregar_Atributos() {
		Titulo = "PARAMETROS";
		nombre_tabla = "val_parametros";
		Inicializar_Vectores(4);
		Agregar_atributo(0, "Parametro", "pk_parametro_id", 11, dd_int, "0", n_null_false, td_hidden, null);
		Agregar_atributo(1, "Nombre", "s_nombre", 250, dd_String, "", n_null_false, td_visible, null);
		Agregar_atributo(2, "Valor", "s_valor", 500, dd_String, "", n_null_false, td_visible, null);
		Agregar_atributo(3, "Concurrencia", "i_concurrencia", 11, dd_int, "0", n_null_false, td_hidden, null);
		campos_mostrar_tabla.add(1);
		ancho_columnas_tabla.add(200);// s_nombre
		campos_mostrar_tabla.add(2);
		ancho_columnas_tabla.add(300);// s_valor
		campos_ordenar_tabla.add(1);// order by - s_nombre
		campos_ordenar_tabla.add(2);// order by - s_valor
		campos_mostrar_combo.add(1);// combobox - s_nombre
		campos_mostrar_combo.add(2);// combobox - s_valor
	}
}