package com.apptriggergenerator.triggergenerator.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.apptriggergenerator.listcontrollers.utils.ConfigManager;

public class clsConexion {
	private static final Logger LOGGER = ConfigManager.getLogger(frmMapirisoftGenerator.class);
	public Connection conexion = null;
	Statement sentencia = null;
	ResultSet resultados = null;
	String cadenaConexion;
	public String usuario;
	public String clave;
	public String database;
	public String host;

	public clsConexion() {
	}

	public void Conectar() {
		try {
			if (conexion == null || conexion.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				cadenaConexion = "jdbc:mysql://" + host + "/" + database;
				conexion = DriverManager.getConnection(cadenaConexion, usuario, clave);
				sentencia = conexion.createStatement();
			}
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void Conectar2() throws Exception {
		if (conexion == null || conexion.isClosed()) {
			Class.forName("com.mysql.jdbc.Driver");
			cadenaConexion = "jdbc:mysql://" + host + "/" + database;
			conexion = DriverManager.getConnection(cadenaConexion, usuario, clave);
			sentencia = conexion.createStatement();
		}
	}

	public void Desconectar() {
		try {
			conexion.close();
			sentencia = null;
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public boolean Ejecutar(String sql) {
		LOGGER.info(sql);
		boolean exitoso = true;
		try {
			Conectar();
			sentencia.execute(sql);
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
			exitoso = false;
		}
		return exitoso;
	}

	public ResultSet Consultar(String sql) {
		LOGGER.info(sql);
		try {
			Conectar();
			resultados = sentencia.executeQuery(sql);
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
		return resultados;
	}

	public boolean getPermisosUsuario(String sUsuario_nm, String sPrograma_nm, String sPrograma_desc) {
		int cant = Integer.parseInt(((Vector) Consulta_General(
		        "SELECT COUNT(*) FROM tblprograma \n" + "WHERE lprograma_id IN (SELECT DISTINCT lPrograma_id FROM tblprogramarol \n"
		                + "WHERE lRol_id IN (SELECT DISTINCT lRol_id FROM tblrolusuario \n" + "WHERE lUsuario_id = (SELECT DISTINCT lUsuario_id \n" + "FROM tblusuario WHERE sUsuario_nm = '"
		                + sUsuario_nm + "')))\n" + "AND sPrograma_nm = '" + sPrograma_nm + "' AND sPrograma_desc = '" + sPrograma_desc + "'").get(0)).get(0).toString());
		return cant > 0;
		// return true;
	}

	public Vector Consulta_General(String Consulta) {
		Vector nombre = new Vector();
		try {
			resultados = Consultar(Consulta);
			Vector contenedor_auxiliar;
			int longitud = resultados.getMetaData().getColumnCount();
			while (resultados.next()) {
				contenedor_auxiliar = new Vector();
				for (int i = 1; i <= longitud; i++) {
					String aux = resultados.getString(i);
					if (aux == null) {
						aux = "";
					}
					contenedor_auxiliar.add(aux);
				}
				nombre.add(contenedor_auxiliar);
			}
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
		return nombre;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public Connection getConexion() {
		return conexion;
	}
}
