package com.apptriggergenerator.triggergenerator.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

//VS4E -- DO NOT REMOVE THIS LINE!
public class frmTriggerGenerator extends JFrame {
	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane0;
	private clsConexion clsConexion;
	private JComboBox jcb_bases_datos;
	private clsBase clsBase;
	private JComboBox jcb_tablas;
	private Vector vec_aux_tablas, vec_datos;
	private JTextArea jTextArea0;
	private JCheckBox jCheckBox0;
	private JButton jButton0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JRadioButton jRadioButton2;
	private ButtonGroup group;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	public frmTriggerGenerator() {
		initComponents();
		// Group the radio buttons.
		group = new ButtonGroup();
		group.add(jRadioButton0);
		group.add(jRadioButton1);
		group.add(jRadioButton2);
		Conectar_Base_Datos();
		clsBase = new clsBase(clsConexion);
		Cargar_Bases_Datos();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJcb_tablas(), new Constraints(new Leading(231, 213, 10, 10), new Leading(14, 12, 12)));
		add(getJCheckBox0(), new Constraints(new Leading(456, 12, 12), new Leading(14, 12, 12)));
		add(getJcb_bases_datos(), new Constraints(new Leading(18, 207, 12, 12), new Leading(12, 28, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(881, 12, 12), new Leading(12, 40, 37)));
		add(getJRadioButton0(), new Constraints(new Leading(690, 10, 10), new Leading(12, 40, 37)));
		add(getJRadioButton1(), new Constraints(new Leading(752, 10, 10), new Leading(12, 40, 37)));
		add(getJRadioButton2(), new Constraints(new Leading(822, 10, 10), new Leading(12, 40, 37)));
		add(getJScrollPane0(), new Constraints(new Leading(21, 940, 12, 12), new Bilateral(50, 12, 22)));
		initGroup();
		setSize(977, 426);
	}

	private void initGroup() {
		group = new ButtonGroup();
		group.add(getJRadioButton0());
		group.add(getJRadioButton1());
		group.add(getJRadioButton2());
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("both");
			jRadioButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jRadioButton0ActionActionPerformed(event);
				}
			});
		}
		return jRadioButton2;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("update");
			jRadioButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jRadioButton0ActionActionPerformed(event);
				}
			});
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("insert");
			jRadioButton0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jRadioButton0ActionActionPerformed(event);
				}
			});
		}
		return jRadioButton0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Generar");
			jButton0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(true);
			jCheckBox0.setText("Todos");
			jCheckBox0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jCheckBox0ActionActionPerformed(event);
				}
			});
		}
		return jCheckBox0;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
			jTextArea0.setBackground(Color.white);
			jTextArea0.setLineWrap(true);
			jTextArea0.setText("jTextArea0");
			jTextArea0.setEnabled(false);
		}
		return jTextArea0;
	}

	private JComboBox getJcb_tablas() {
		if (jcb_tablas == null) {
			jcb_tablas = new JComboBox();
			jcb_tablas.setModel(new DefaultComboBoxModel(new Object[] { "CHARACTER_SETS", "COLLATIONS", "COLLATION_CHARACTER_SET_APPLICABILITY", "COLUMNS", "COLUMN_PRIVILEGES", "ENGINES", "EVENTS",
			        "FILES", "GLOBAL_STATUS", "GLOBAL_VARIABLES", "KEY_COLUMN_USAGE", "PARAMETERS", "PARTITIONS", "PLUGINS", "PROCESSLIST", "PROFILING", "REFERENTIAL_CONSTRAINTS", "ROUTINES",
			        "SCHEMATA", "SCHEMA_PRIVILEGES", "SESSION_STATUS", "SESSION_VARIABLES", "STATISTICS", "TABLES", "TABLESPACES", "TABLE_CONSTRAINTS", "TABLE_PRIVILEGES", "TRIGGERS",
			        "USER_PRIVILEGES", "VIEWS", "INNODB_CMP_RESET", "INNODB_TRX", "INNODB_CMPMEM_RESET", "INNODB_LOCK_WAITS", "INNODB_CMPMEM", "INNODB_CMP", "INNODB_LOCKS" }));
			jcb_tablas.setDoubleBuffered(false);
			jcb_tablas.setBorder(null);
			jcb_tablas.setEnabled(false);
			jcb_tablas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcb_tablasActionActionPerformed(event);
				}
			});
		}
		return jcb_tablas;
	}

	private JComboBox getJcb_bases_datos() {
		if (jcb_bases_datos == null) {
			jcb_bases_datos = new JComboBox();
			jcb_bases_datos.setEditable(true);
			jcb_bases_datos.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcb_bases_datos.setDoubleBuffered(false);
			jcb_bases_datos.setBorder(null);
			jcb_bases_datos.setRequestFocusEnabled(false);
			jcb_bases_datos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcb_bases_datosActionActionPerformed(event);
				}
			});
		}
		return jcb_bases_datos;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTextArea0());
		}
		return jScrollPane0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL + " on this platform:" + e.getMessage());
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

	public void Conectar_Base_Datos() {
		try {
			clsConexion = new clsConexion();
			clsConexion.conexion = null;
			clsConexion.usuario = "root";
			clsConexion.clave = "cromisoft";
			clsConexion.database = "information_schema";
			clsConexion.host = "192.168.2.111";
			clsConexion.Conectar2();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
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
			jTextArea0.setText(createTriggerForTable(jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString()));
		}
	}

	public String getTriggerType() {
		if (jRadioButton0.isSelected()) {
			return "01";
		} else {
			if (jRadioButton1.isSelected()) {
				return "02";
			}
		}
		return "00";
	}

	public boolean containsScreated_by(Vector<Vector<String>> vc_aux_datos) {
		for (Vector<String> vector : vc_aux_datos) {
			if (vector.get(0).equals("sCreated_by")) {
				return true;
			}
		}
		return false;
	}

	public String createTriggerForTable(String dataBase, String tableName) {
		String operationType = getTriggerType();
		String operationDescription = "INSERT";
		String auxFields = "";
		Vector<Vector<String>> vc_aux_datos = clsBase.Consulta_General("SELECT c.column_name, CONCAT(UCASE(LEFT(c.column_name,1)),SUBSTRING(c.column_name,2)),\n"
		        + "CASE c.is_nullable WHEN 'NO' THEN 'false' ELSE 'true' END nullable, c.data_type, IFNULL(IFNULL(c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION),0) longitud,\n"
		        + "IFNULL((SELECT REFERENCED_TABLE_NAME FROM KEY_COLUMN_USAGE WHERE REFERENCED_TABLE_NAME IS NOT NULL AND table_schema = c.TABLE_SCHEMA AND table_name = c.table_name\n"
		        + "AND column_name = c.column_name),'') foranea, IFNULL(c.numeric_scale,'0')\n" + "FROM COLUMNS c WHERE c.table_schema = '" + dataBase + "' AND c.table_name = '" + tableName
		        + "' ORDER BY c.ORDINAL_POSITION");
		if (containsScreated_by(vc_aux_datos)) {
			if (operationType.equals("00")) {
				operationType = "01";
				auxFields = "DELIMITER $$\r\n" + "\r\n" + "USE `" + dataBase + "`$$\r\n" + "\r\n" + "DROP TRIGGER IF EXISTS `th_" + operationDescription.toLowerCase() + "_" + tableName + "`$$\r\n"
				        + "\r\n" + "CREATE DEFINER = 'deltacargos'@'192.168.1.250' TRIGGER `th_" + operationDescription.toLowerCase() + "_" + tableName + "` AFTER " + operationDescription + " ON `"
				        + tableName + "` \r\n" + "    FOR EACH ROW BEGIN\r\n" + "	DECLARE NEXT_ID INT;\r\n" + "	DECLARE LOGGING_FL VARCHAR(250);\r\n" + "	\r\n"
				        + "	SELECT isLoggingActive() INTO LOGGING_FL FROM DUAL;\r\n" + "	\r\n" + "	IF LOGGING_FL IS NOT NULL AND LOGGING_FL = '01' THEN\r\n"
				        + "		SELECT getNextTableId('ms_tblTHistory') INTO NEXT_ID FROM DUAL;\r\n" + "		INSERT INTO " + dataBase
				        + ".ms_tblTHistory (lTHistory_id,tmRegistered_tm, sUser_id, iTTransaction_fl, sTable_nm)\r\n" + "		VALUES(NEXT_ID, SYSDATE(), new.sCreated_by, '" + operationType + "', '"
				        + tableName + "');\r\n" + "	\r\n";
				for (Vector vector : vc_aux_datos) {
					auxFields += "		INSERT INTO " + dataBase + ".ms_tblDetTHistory (lDetTHistory_id, lTHistory_id, sDetTHistory_field, sDetTHistory_val)\r\n"
					        + "		VALUES (getNextTableId('ms_tblDetTHistory'), NEXT_ID, '" + vector.get(0) + "', new." + vector.get(0) + ");\r\n";
				}
				auxFields += "		END IF;\r\n" + "    END;\r\n" + "$$\r\n" + "\r\n" + "DELIMITER ;";
				auxFields += "\r\n\r\n";
				operationDescription = "UPDATE";
				operationType = "02";
				auxFields += "DELIMITER $$\r\n" + "\r\n" + "USE `" + dataBase + "`$$\r\n" + "\r\n" + "DROP TRIGGER IF EXISTS `th_" + operationDescription.toLowerCase() + "_" + tableName + "`$$\r\n"
				        + "\r\n" + "CREATE TRIGGER `th_" + operationDescription.toLowerCase() + "_" + tableName + "` AFTER " + operationDescription + " ON `" + tableName + "` \r\n"
				        + "    FOR EACH ROW BEGIN\r\n" + "	DECLARE NEXT_ID INT;\r\n" + "	DECLARE LOGGING_FL VARCHAR(250);\r\n" + "	\r\n" + "	SELECT isLoggingActive() INTO LOGGING_FL FROM DUAL;\r\n"
				        + "	\r\n" + "	IF LOGGING_FL IS NOT NULL AND LOGGING_FL = '01' THEN\r\n" + "		SELECT getNextTableId('ms_tblTHistory') INTO NEXT_ID FROM DUAL;\r\n" + "		INSERT INTO " + dataBase
				        + ".ms_tblTHistory (lTHistory_id,tmRegistered_tm, sUser_id, iTTransaction_fl, sTable_nm)\r\n" + "		VALUES(NEXT_ID, SYSDATE(), new.sCreated_by, '" + operationType + "', '"
				        + tableName + "');\r\n" + "	\r\n";
				for (Vector vector : vc_aux_datos) {
					auxFields += "		INSERT INTO " + dataBase + ".ms_tblDetTHistory (lDetTHistory_id, lTHistory_id, sDetTHistory_field, sDetTHistory_val)\r\n"
					        + "		VALUES (getNextTableId('ms_tblDetTHistory'), NEXT_ID, '" + vector.get(0) + "', new." + vector.get(0) + ");\r\n";
				}
				auxFields += "		END IF;\r\n" + "    END;\r\n" + "$$\r\n" + "\r\n" + "DELIMITER ;";
			} else {
				if (operationType.equals("02")) {
					operationDescription = "UPDATE";
				}
				auxFields = "DELIMITER $$\r\n" + "\r\n" + "USE `" + dataBase + "`$$\r\n" + "\r\n" + "DROP TRIGGER IF EXISTS `th_" + operationDescription.toLowerCase() + "_" + tableName + "`$$\r\n"
				        + "\r\n" + "CREATE TRIGGER `th_" + operationDescription.toLowerCase() + "_" + tableName + "` AFTER " + operationDescription + " ON `" + tableName + "` \r\n"
				        + "    FOR EACH ROW BEGIN\r\n" + "	DECLARE NEXT_ID INT;\r\n" + "	DECLARE LOGGING_FL VARCHAR(250);\r\n" + "	\r\n" + "	SELECT isLoggingActive() INTO LOGGING_FL FROM DUAL;\r\n"
				        + "	\r\n" + "	IF LOGGING_FL IS NOT NULL AND LOGGING_FL = '01' THEN\r\n" + "		SELECT getNextTableId('ms_tblTHistory') INTO NEXT_ID FROM DUAL;\r\n" + "		INSERT INTO " + dataBase
				        + ".ms_tblTHistory (lTHistory_id,tmRegistered_tm, sUser_id, iTTransaction_fl, sTable_nm)\r\n" + "		VALUES(NEXT_ID, SYSDATE(), new.sCreated_by, '" + operationType + "', '"
				        + tableName + "');\r\n" + "	\r\n";
				for (Vector vector : vc_aux_datos) {
					auxFields += "		INSERT INTO " + dataBase + ".ms_tblDetTHistory (lDetTHistory_id, lTHistory_id, sDetTHistory_field, sDetTHistory_val)\r\n"
					        + "		VALUES (getNextTableId('ms_tblDetTHistory'), NEXT_ID, '" + vector.get(0) + "', new." + vector.get(0) + ");\r\n";
				}
				auxFields += "		END IF;\r\n" + "    END;\r\n" + "$$\r\n" + "\r\n" + "DELIMITER ;";
			}
		}
		return auxFields;
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frmTriggerGenerator frame = new frmTriggerGenerator();
				frame.setDefaultCloseOperation(frmTriggerGenerator.EXIT_ON_CLOSE);
				frame.setTitle("frmTriggerGenerator");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jcb_bases_datosActionActionPerformed(ActionEvent event) {
		if (jcb_tablas.isEnabled()) {
			Cargar_Tablas();
		}
	}

	private void jcb_tablasActionActionPerformed(ActionEvent event) {
		Cargar_Atributos();
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		File file = showFileChooser();
		if (file != null) {
			if (jcb_tablas.isEnabled()) {
				writeFiles(file, createTriggerForTable(jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString()));
			} else {
				String sql = "";
				Vector aux_datos = clsBase.Consulta_General("select table_name from tables where table_schema = '" + jcb_bases_datos.getSelectedItem() + "'");
				for (int i = 0; i < aux_datos.size(); i++) {
					sql += createTriggerForTable(jcb_bases_datos.getSelectedItem().toString(), ((Vector) aux_datos.get(i)).get(0).toString());
					sql += "\r\n";
				}
				writeFiles(file, sql);
			}
		}
	}

	public File showFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File("test.sql"));
		int option = chooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile() != null) {
				File fileName = new File(chooser.getSelectedFile().toString());
				if (fileName.exists()) {
					int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						return chooser.getSelectedFile();
					}
				} else {
					return chooser.getSelectedFile();
				}
			}
		}
		return null;
	}

	public void writeFiles(File fileName, String text) {
		try {
			BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
			outFile.write(text);
			outFile.flush();
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jCheckBox0ActionActionPerformed(ActionEvent event) {
		jcb_tablas.setEnabled(!jCheckBox0.isSelected());
		jTextArea0.setEnabled(!jCheckBox0.isSelected());
		if (jcb_tablas.isEnabled()) {
			Cargar_Tablas();
		}
	}

	private void jRadioButton0ActionActionPerformed(ActionEvent event) {
		if (jcb_tablas.isEnabled()) {
			Cargar_Tablas();
		}
	}
}
