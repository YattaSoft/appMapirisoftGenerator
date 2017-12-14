package com.apptriggergenerator.triggergenerator.utils;

public class val_tablaids extends clsBase {
	public val_tablaids() {
		super();
		Agregar_Atributos();
	}

	public val_tablaids(clsConexion con) {
		super(con);
		Agregar_Atributos();
	}

	public void Agregar_Atributos() {
		Titulo = "TABLAIDS";
		nombre_tabla = "val_tablaids";
		Inicializar_Vectores(3);
		Agregar_atributo(0, "pk_tablaid_id", "pk_tablaid_id", 250, dd_String, "", n_null_false, td_hidden, null);
		Agregar_atributo(1, "n_siguiente", "n_siguiente", 11, dd_int, "0", n_null_false, td_visible, null);
		Agregar_atributo(2, "i_concurrencia", "i_concurrencia", 11, dd_int, "0", n_null_false, td_hidden, null);
		campos_mostrar_tabla.add(1);// show in list - n_siguiente
		ancho_columnas_tabla.add(100);// column width - n_siguiente
		campos_ordenar_tabla.add(1);// order by - n_siguiente
		campos_mostrar_combo.add(1);// combobox - n_siguiente
	}
}