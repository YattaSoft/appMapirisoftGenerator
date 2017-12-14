package com.apptriggergenerator.triggergenerator.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class AppPrincipal {
	clsConexion clsConexion;
	String App_name = "Base Application";

	public AppPrincipal() {
		Conectar_Base_Datos();
		JFrame frame = new frm_Generador(clsConexion);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void Conectar_Base_Datos() {
		try {
			clsConexion = new clsConexion();
			clsConexion.conexion = null;
			clsConexion.usuario = "cromisoft";
			clsConexion.clave = "cromisoft";
			clsConexion.database = "appModuloBasico";
			clsConexion.host = "192.168.2.111";
			clsConexion.Conectar2();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new AppPrincipal();
	}
}
