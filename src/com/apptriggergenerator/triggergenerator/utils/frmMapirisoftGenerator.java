package com.apptriggergenerator.triggergenerator.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.apptriggergenerator.listcontrollers.utils.ConfigManager;

// VS4E -- DO NOT REMOVE THIS LINE!
public class frmMapirisoftGenerator extends JFrame {
	private static final Logger LOGGER = ConfigManager.getLogger(frmMapirisoftGenerator.class);
	private Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private clsConexion clsConexion;
	private clsBase clsBase;
	private JComboBox jcb_bases_datos;
	private JComboBox jcb_tablas;
	private Vector<String> vec_aux_tablas;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JList jListAtributos;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private JButton jButton1;
	private JList jList_updateAtributes;
	private JScrollPane jScrollPane1;
	private String[] auxFields = { "iEliminado_fl", "iEstado_fl", "sCreated_by", "iConcurrencia_id" };
	private List<String> controlFields = new ArrayList<String>(Arrays.asList(auxFields));
	private List<String[]> fieldsForUpdate, fieldsForJSP;
	private List<String[]> fieldsOfTable, fieldsOfTableForJSP;
	DefaultListModel atributesDefaultListModel, notSelectedAtributesDefaultListModel, jspAtributesDefaultListModel, jspNotSelectedAtributesDefaultListModel;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private List<String[]> primaryKey;
	private JButton jButton5;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JTextField domainPackage;
	private JLabel jLabel4;
	private JTextField ejbPackage;
	private JLabel jLabel5;
	private JTextField webPackage;
	private JLabel jLabel6;
	private JTextField constantsClassName;
	private JLabel jLabel7;
	private JTextField applicationName;
	private JTextField txtDestinationFolder;
	private JLabel jLabel9;
	private JButton jButton6;
	private File destinationFolder = new File(""), fileWebDestinationFolder = new File("");
	private File destinationDomainFolder = new File("");
	private JLabel jLabel10;
	private JTextField dataSourceName;
	private JLabel jLabel11;
	private JTextField webDestinationFolder;
	private JButton jButton7;
	private String moduleName, delegateName;
	private JLabel jLabel12;
	private JList jList2;
	private JScrollPane jScrollPane2;
	private JButton jButton9;
	private JButton jButton8;
	private JList jList3;
	private JScrollPane jScrollPane3;
	private JButton jButton10;
	private JButton jButton11;
	private String javascriptDirectory = "WebContent" + System.getProperty("file.separator") + "scripts" + System.getProperty("file.separator");
	private String tilesDirectory = "WebContent" + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "config" + System.getProperty("file.separator") + "tiles"
			+ System.getProperty("file.separator");
	private String jspDirectory = "WebContent" + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "jsp" + System.getProperty("file.separator");
	private String resourcesDirectory = "resources" + System.getProperty("file.separator");
	private String webApplicationConfigFile = "WebContent" + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "config" + System.getProperty("file.separator");
	private JTextArea jTextArea0;
	private JScrollPane jScrollPane4;
	private JLabel jLabel13;
	private JComboBox jcbModulos;
	private Map<String, String> auxModulos, auxMenusByModule;
	private JComboBox jcbMenusByModule;
	private JLabel jLabel14;
	private JCheckBox jchbDetail;
	private JComboBox jcbMasterName;
	private JTextField jTextField3;
	private JLabel jLabel15;
	private JLabel jLabel16;
	private JTextField domainFolder;
	private JButton jButton12;
	private JComboBox jcbColumnForJoin;
	private JLabel jLabel8;
	Vector<Vector<String>> vc_auxDatos;
	private JCheckBox jchEJB;
	private JCheckBox jchDomain;
	private JCheckBox jchWeb;
	private JPanel jPanel0;
	private JTextField jtfCommonsUtilsName;
	private JLabel jLabel17;
	private JTextField jtfUtilsWebClassName;
	private JTextField jtfConstantsWebClassName;
	private JLabel jLabel18;
	private JLabel jLabel19;
	private JPanel jPanel1;
	private JButton jButton13;
	private JTextField controllerName;
	private JLabel jLabel20;

	public frmMapirisoftGenerator(String host, String user, String password) {
		initComponents();
		Conectar_Base_Datos(host, user, password);
		clsBase = new clsBase(clsConexion);
		Cargar_Bases_Datos();
	}

	public void Conectar_Base_Datos(String host, String user, String password) {
		try {
			clsConexion = new clsConexion();
			clsConexion.conexion = null;
			clsConexion.usuario = user;
			clsConexion.clave = password;
			clsConexion.database = "information_schema";
			clsConexion.host = host;
			clsConexion.Conectar2();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void Cargar_Bases_Datos() {
		jcb_bases_datos.removeAllItems();
		Vector<Vector<String>> aux_datos = clsBase.Consulta_General("select schema_name from schemata");
		jcb_bases_datos.removeAllItems();
		for (int i = 0; i < aux_datos.size(); i++) {
			jcb_bases_datos.addItem(((Vector<?>) aux_datos.get(i)).get(0));
		}
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJcb_bases_datos(), new Constraints(new Leading(136, 199, 10, 10), new Leading(21, 10, 10)));
		add(getJComboBox1(), new Constraints(new Leading(138, 197, 12, 12), new Leading(52, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(34, 87, 10, 10), new Leading(21, 25, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(33, 87, 12, 12), new Leading(52, 25, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(140, 195, 12, 12), new Leading(83, 181, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(345, 10, 10), new Leading(86, 10, 10)));
		add(getJButton1(), new Constraints(new Leading(343, 10, 10), new Leading(120, 10, 10)));
		add(getJScrollPane1(), new Constraints(new Leading(440, 196, 10, 10), new Leading(83, 182, 10, 10)));
		add(getJButton3(), new Constraints(new Leading(642, 42, 12, 12), new Leading(113, 10, 10)));
		add(getJButton5(), new Constraints(new Leading(642, 42, 12, 12), new Leading(81, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(440, 193, 10, 10), new Leading(61, 12, 12)));
		add(getJScrollPane2(), new Constraints(new Leading(747, 192, 12, 12), new Leading(132, 194, 10, 10)));
		add(getJButton9(), new Constraints(new Leading(945, 12, 12), new Leading(170, 10, 10)));
		add(getJButton8(), new Constraints(new Leading(945, 12, 12), new Leading(135, 10, 10)));
		add(getJScrollPane3(), new Constraints(new Leading(1014, 188, 10, 10), new Leading(134, 197, 10, 10)));
		add(getJLabel12(), new Constraints(new Leading(1014, 12, 12), new Leading(113, 18, 12, 12)));
		add(getJButton10(), new Constraints(new Leading(1208, 12, 12), new Leading(134, 12, 12)));
		add(getJButton11(), new Constraints(new Leading(1208, 12, 12), new Leading(164, 12, 12)));
		add(getJCheckBox0(), new Constraints(new Leading(747, 8, 8), new Leading(345, 10, 10)));
		add(getJcbMasterName(), new Constraints(new Leading(833, 237, 10, 10), new Leading(345, 12, 12)));
		add(getJTextField3(), new Constraints(new Leading(1115, 94, 10, 10), new Leading(343, 25, 12, 12)));
		add(getJLabel15(), new Constraints(new Leading(1094, 10, 10), new Leading(347, 12, 12)));
		add(getJcbColumnForJoin(), new Constraints(new Leading(440, 236, 12, 12), new Leading(24, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(343, 98, 12, 12), new Leading(24, 24, 12, 12)));
		add(getJPanel0(), new Constraints(new Leading(114, 106, 12, 12), new Leading(283, 12, 12)));
		add(getJLabel13(), new Constraints(new Leading(57, 81, 10, 10), new Leading(451, 10, 10)));
		add(getJComboBox0(), new Constraints(new Leading(114, 204, 12, 12), new Leading(449, 12, 12)));
		add(getJLabel14(), new Constraints(new Leading(326, 141, 10, 10), new Leading(451, 10, 10)));
		add(getJcbMenusByModule(), new Constraints(new Leading(464, 180, 10, 10), new Leading(449, 12, 12)));
		add(getJScrollPane4(), new Constraints(new Leading(52, 1157, 12, 12), new Leading(486, 81, 12, 12)));
		add(getJPanel1(), new Constraints(new Leading(741, 461, 12, 12), new Leading(381, 94, 12, 12)));
		add(getJButton4(), new Constraints(new Leading(685, 94, 10, 10), new Leading(579, 12, 12)));
		add(getJButton2(), new Constraints(new Leading(570, 94, 10, 10), new Leading(579, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(912, 235, 10, 10), new Leading(86, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(749, 10, 10), new Leading(89, 12, 12)));
		add(getJLabel11(), new Constraints(new Leading(771, 10, 10), new Leading(58, 12, 12)));
		add(getJButton7(), new Constraints(new Leading(1150, 10, 10), new Leading(57, 20, 12, 12)));
		add(getWebDestinationFolder(), new Constraints(new Leading(912, 232, 12, 12), new Leading(58, 12, 12)));
		add(getTxtDestinationFolder(), new Constraints(new Leading(381, 249, 10, 10), new Leading(280, 16, 12, 12)));
		add(getJLabel9(), new Constraints(new Leading(239, 133, 12, 12), new Leading(280, 12, 12)));
		add(getJButton6(), new Constraints(new Leading(633, 84, 10, 10), new Leading(280, 16, 12, 12)));
		add(getEjbPackage(), new Constraints(new Leading(381, 249, 12, 12), new Leading(298, 16, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(265, 12, 12), new Leading(300, 10, 10)));
		add(getJLabel7(), new Constraints(new Leading(266, 12, 12), new Leading(320, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(383, 249, 12, 12), new Leading(319, 16, 12, 12)));
		add(getJTextField2(), new Constraints(new Leading(382, 249, 12, 12), new Leading(338, 16, 12, 12)));
		add(getJLabel10(), new Constraints(new Leading(258, 12, 12), new Leading(342, 12, 12)));
		add(getDomainPackage(), new Constraints(new Leading(385, 251, 12, 12), new Leading(406, 16, 10, 10)));
		add(getDomainFolder(), new Constraints(new Leading(385, 250, 12, 12), new Leading(388, 16, 12, 12)));
		add(getJButton12(), new Constraints(new Leading(641, 80, 12, 12), new Leading(388, 16, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(245, 12, 12), new Leading(408, 10, 10)));
		add(getJLabel16(), new Constraints(new Leading(289, 12, 12), new Leading(389, 12, 12)));
		add(getJButton13(), new Constraints(new Leading(404, 12, 12), new Leading(579, 12, 12)));
		add(getControllerName(), new Constraints(new Leading(912, 232, 12, 12), new Leading(26, 12, 12)));
		add(getJLabel20(), new Constraints(new Leading(804, 96, 10, 10), new Leading(30, 12, 12)));
		setSize(1287, 617);
	}

	private JLabel getJLabel20() {
		if (jLabel20 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("Controller Name");
		}
		return jLabel20;
	}

	private JTextField getControllerName() {
		if (controllerName == null) {
			controllerName = new JTextField();
		}
		return controllerName;
	}

	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setText("Guardar Preferences");
			jButton13.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton13ActionActionPerformed(event);
				}
			});
		}
		return jButton13;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBackground(new Color(255, 128, 64));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJtfUtilsWebClassName(), new Constraints(new Leading(185, 252, 10, 10), new Leading(68, 16, 10, 10)));
			jPanel1.add(getJtfConstantsWebClassName(), new Constraints(new Leading(184, 252, 12, 12), new Leading(50, 16, 12, 12)));
			jPanel1.add(getJtfCommonsUtilsName(), new Constraints(new Leading(184, 252, 12, 12), new Leading(31, 16, 12, 12)));
			jPanel1.add(getConstantsClassName(), new Constraints(new Leading(184, 252, 12, 12), new Leading(12, 16, 12, 12)));
			jPanel1.add(getJLabel17(), new Constraints(new Leading(84, 10, 10), new Leading(31, 12, 12)));
			jPanel1.add(getJLabel6(), new Constraints(new Leading(50, 12, 12), new Leading(12, 12, 12)));
			jPanel1.add(getJLabel18(), new Constraints(new Leading(21, 12, 12), new Leading(52, 10, 10)));
			jPanel1.add(getJLabel19(), new Constraints(new Leading(54, 12, 12), new Leading(68, 12, 12)));
		}
		return jPanel1;
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Utils Web ClassName");
		}
		return jLabel19;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Constants Web ClassName");
		}
		return jLabel18;
	}

	private JTextField getJtfConstantsWebClassName() {
		if (jtfConstantsWebClassName == null) {
			jtfConstantsWebClassName = new JTextField();
			jtfConstantsWebClassName.setText("AppBaseWebConstants");
		}
		return jtfConstantsWebClassName;
	}

	private JTextField getJtfUtilsWebClassName() {
		if (jtfUtilsWebClassName == null) {
			jtfUtilsWebClassName = new JTextField();
			jtfUtilsWebClassName.setText("AppBaseWebUtils");
		}
		return jtfUtilsWebClassName;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("Utils ClassName");
		}
		return jLabel17;
	}

	private JTextField getJtfCommonsUtilsName() {
		if (jtfCommonsUtilsName == null) {
			jtfCommonsUtilsName = new JTextField();
			jtfCommonsUtilsName.setText("AppBaseUtils");
		}
		return jtfCommonsUtilsName;
	}

	private JCheckBox getJchWeb() {
		if (jchWeb == null) {
			jchWeb = new JCheckBox();
			jchWeb.setSelected(true);
			jchWeb.setText("Web");
		}
		return jchWeb;
	}

	private JCheckBox getJchDomain() {
		if (jchDomain == null) {
			jchDomain = new JCheckBox();
			jchDomain.setSelected(true);
			jchDomain.setText("Domain");
		}
		return jchDomain;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBackground(new Color(64, 128, 128));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJchEJB(), new Constraints(new Leading(21, 10, 10), new Leading(8, 8, 8)));
			jPanel0.add(getJchDomain(), new Constraints(new Leading(21, 8, 8), new Leading(36, 8, 8)));
			jPanel0.add(getJchWeb(), new Constraints(new Leading(23, 10, 10), new Leading(66, 10, 10)));
		}
		return jPanel0;
	}

	private JCheckBox getJchEJB() {
		if (jchEJB == null) {
			jchEJB = new JCheckBox();
			jchEJB.setSelected(true);
			jchEJB.setText("EJB");
		}
		return jchEJB;
	}

	private JComboBox getJcbColumnForJoin() {
		if (jcbColumnForJoin == null) {
			jcbColumnForJoin = new JComboBox();
			jcbColumnForJoin.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcbColumnForJoin.setDoubleBuffered(false);
			jcbColumnForJoin.setBorder(null);
			jcbColumnForJoin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcbColumnForJoinActionActionPerformed(event);
				}
			});
		}
		return jcbColumnForJoin;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Column For Join");
		}
		return jLabel8;
	}

	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setText("Browse");
			jButton12.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton12ActionActionPerformed(event);
				}
			});
		}
		return jButton12;
	}

	private JTextField getDomainFolder() {
		if (domainFolder == null) {
			domainFolder = new JTextField();
		}
		return domainFolder;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Domain Folder");
		}
		return jLabel16;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("PK");
		}
		return jLabel15;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEnabled(false);
		}
		return jTextField3;
	}

	private JComboBox getJcbMasterName() {
		if (jcbMasterName == null) {
			jcbMasterName = new JComboBox();
			jcbMasterName.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcbMasterName.setDoubleBuffered(false);
			jcbMasterName.setBorder(null);
			jcbMasterName.setEnabled(false);
			jcbMasterName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcbMasterNameActionActionPerformed(event);
				}
			});
		}
		return jcbMasterName;
	}

	private JCheckBox getJCheckBox0() {
		if (jchbDetail == null) {
			jchbDetail = new JCheckBox();
			jchbDetail.setText("Detail ?");
			jchbDetail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jCheckBox0ActionActionPerformed(event);
				}
			});
		}
		return jchbDetail;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText(">>>> Menus by Module");
		}
		return jLabel14;
	}

	private JComboBox getJcbMenusByModule() {
		if (jcbMenusByModule == null) {
			jcbMenusByModule = new JComboBox();
			jcbMenusByModule.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcbMenusByModule.setDoubleBuffered(false);
			jcbMenusByModule.setBorder(null);
			jcbMenusByModule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcbMenusByModuleActionActionPerformed(event);
				}
			});
		}
		return jcbMenusByModule;
	}

	private JComboBox getJComboBox0() {
		if (jcbModulos == null) {
			jcbModulos = new JComboBox();
			jcbModulos.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcbModulos.setDoubleBuffered(false);
			jcbModulos.setBorder(null);
			jcbModulos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcbModulosActionActionPerformed(event);
				}
			});
		}
		return jcbModulos;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Modules");
		}
		return jLabel13;
	}

	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTextArea0());
		}
		return jScrollPane4;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
		}
		return jTextArea0;
	}

	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setText("V");
			jButton11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton11ActionActionPerformed(event);
				}
			});
		}
		return jButton11;
	}

	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setText("/\\");
			jButton10.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton10ActionActionPerformed(event);
				}
			});
		}
		return jButton10;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJList3());
		}
		return jScrollPane3;
	}

	private JList getJList3() {
		if (jList3 == null) {
			jList3 = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jList3.setModel(listModel);
		}
		return jList3;
	}

	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText(">>>");
			jButton8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton8ActionActionPerformed(event);
				}
			});
		}
		return jButton8;
	}

	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("<<<");
			jButton9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton9ActionActionPerformed(event);
				}
			});
		}
		return jButton9;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJList2());
		}
		return jScrollPane2;
	}

	private JList getJList2() {
		if (jList2 == null) {
			jList2 = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jList2.setModel(listModel);
		}
		return jList2;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("FIelds to Show on JSP");
		}
		return jLabel12;
	}

	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("Browse");
			jButton7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton7ActionActionPerformed(event);
				}
			});
		}
		return jButton7;
	}

	private JTextField getWebDestinationFolder() {
		if (webDestinationFolder == null) {
			webDestinationFolder = new JTextField();
		}
		return webDestinationFolder;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("Web Destination Folder");
		}
		return jLabel11;
	}

	private JTextField getJTextField2() {
		if (dataSourceName == null) {
			dataSourceName = new JTextField();
			dataSourceName.setText("Cromisoft");
		}
		return dataSourceName;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Data Source Name");
		}
		return jLabel10;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("Browse");
			jButton6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton6ActionActionPerformed(event);
				}
			});
		}
		return jButton6;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("EJB Destination Folder");
		}
		return jLabel9;
	}

	private JTextField getTxtDestinationFolder() {
		if (txtDestinationFolder == null) {
			txtDestinationFolder = new JTextField();
		}
		return txtDestinationFolder;
	}

	private JTextField getJTextField1() {
		if (applicationName == null) {
			applicationName = new JTextField();
			applicationName.setText("Application");
			applicationName.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent event) {
					applicationNameFocusFocusLost(event);
				}
			});
		}
		return applicationName;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Application Name");
		}
		return jLabel7;
	}

	private JTextField getConstantsClassName() {
		if (constantsClassName == null) {
			constantsClassName = new JTextField();
			constantsClassName.setText("AppBaseConstants");
		}
		return constantsClassName;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Constants ClassName");
		}
		return jLabel6;
	}

	private JTextField getJTextField0() {
		if (webPackage == null) {
			webPackage = new JTextField();
		}
		return webPackage;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Paquete para Presentacion");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Paquete para EJB");
		}
		return jLabel4;
	}

	private JTextField getDomainPackage() {
		if (domainPackage == null) {
			domainPackage = new JTextField();
		}
		return domainPackage;
	}

	private JTextField getEjbPackage() {
		if (ejbPackage == null) {
			ejbPackage = new JTextField();
		}
		return ejbPackage;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Paquete para Dominio");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Fields for Update");
		}
		return jLabel2;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("/\\");
			jButton5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton5ActionActionPerformed(event);
				}
			});
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("Salir");
			jButton4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton4ActionActionPerformed(event);
				}
			});
		}
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("\\/");
			jButton3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton3ActionActionPerformed(event);
				}
			});
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Generar");
			jButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton2ActionActionPerformed(event);
				}
			});
		}
		return jButton2;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
	}

	private JList getJList1() {
		if (jList_updateAtributes == null) {
			jList_updateAtributes = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jList_updateAtributes.setModel(listModel);
		}
		return jList_updateAtributes;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("<<<<<<");
			jButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(">>>>>>");
			jButton0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jListAtributos == null) {
			jListAtributos = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jListAtributos.setModel(listModel);
		}
		return jListAtributos;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("Table");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel0.setText("Data Base");
		}
		return jLabel0;
	}

	private JComboBox getJComboBox1() {
		if (jcb_tablas == null) {
			jcb_tablas = new JComboBox();
			jcb_tablas.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcb_tablas.setDoubleBuffered(false);
			jcb_tablas.setBorder(null);
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
			jcb_bases_datos.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jcb_bases_datos.setDoubleBuffered(false);
			jcb_bases_datos.setBorder(null);
			jcb_bases_datos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jcb_bases_datosActionActionPerformed(event);
				}
			});
		}
		return jcb_bases_datos;
	}

	private void jcb_bases_datosActionActionPerformed(ActionEvent event) {
		Cargar_Tablas();
		Cargar_Modulos();
		loadPreferencesValuesToForm(String.valueOf(jcb_bases_datos.getSelectedItem()));
	}

	public void Cargar_Tablas() {
		if (jcb_bases_datos.getSelectedIndex() != -1) {
			jcb_tablas.removeAllItems();
			Vector<Vector<String>> aux_datos = clsBase.Consulta_General("select table_name from tables where table_schema = '" + jcb_bases_datos.getSelectedItem() + "'");
			vec_aux_tablas = new Vector<String>();
			jcb_tablas.removeAllItems();
			for (int i = 0; i < aux_datos.size(); i++) {
				jcb_tablas.addItem(((Vector<?>) aux_datos.get(i)).get(0));
				vec_aux_tablas.add(((Vector<?>) aux_datos.get(i)).get(0).toString());
			}
			vec_aux_tablas.add(0, "");
		}
	}

	public void Cargar_Modulos() {
		if (jcb_bases_datos.getSelectedIndex() != -1 && !jcb_bases_datos.getSelectedItem().toString().toLowerCase().equals("information_schema")) {
			jcbModulos.removeAllItems();
			Vector<Vector<String>> aux_datos = clsBase.Consulta_General("SELECT iModulo_id, sModulo_nm FROM " + jcb_bases_datos.getSelectedItem()
					+ ".ms_tblModulos WHERE iEstado_fl = '01' AND  iEliminado_fl = '01'");
			auxModulos = new LinkedHashMap<String, String>();
			jcbModulos.removeAllItems();
			jcbModulos.addItem("");
			for (int i = 0; i < aux_datos.size(); i++) {
				jcbModulos.addItem(aux_datos.get(i).get(1));
				auxModulos.put(aux_datos.get(i).get(1), aux_datos.get(i).get(0));
			}
		}
	}

	public void CargarMenusByModule() {
		if (jcbModulos.getSelectedIndex() != -1) {
			jcbMenusByModule.removeAllItems();
			Vector<Vector<String>> aux_datos = clsBase.Consulta_General("SELECT iPrograma_id, sPrograma_nm FROM " + jcb_bases_datos.getSelectedItem()
					+ ".ms_tblProgramas WHERE iTPrograma_fl = '02' AND iModulo_id = " + auxModulos.get(jcbModulos.getSelectedItem()) + " ORDER BY iPrograma_order");
			auxMenusByModule = new LinkedHashMap<String, String>();
			jcbMenusByModule.removeAllItems();
			jcbMenusByModule.addItem("");
			for (int i = 0; i < aux_datos.size(); i++) {
				jcbMenusByModule.addItem(aux_datos.get(i).get(1));
				auxMenusByModule.put(aux_datos.get(i).get(1), aux_datos.get(i).get(0));
			}
		}
	}

	public void refreshAtributesForJoinAtribute(Vector<Vector<String>> data) {
		if (jcb_tablas.getSelectedIndex() != -1) {
			jcbColumnForJoin.removeAllItems();
			for (int i = 0; i < data.size(); i++) {
				if (!controlFields.contains(data.get(i).get(0))) {
					jcbColumnForJoin.addItem(data.get(i).get(0));
				}
			}
		}
	}

	public frmMapirisoftGenerator() {
		initComponents();
	}

	public void reloadAtributesList() {
		if (jcb_bases_datos.getSelectedIndex() != -1 && jcb_tablas.getSelectedIndex() != -1) {
			atributesDefaultListModel = new DefaultListModel();
			notSelectedAtributesDefaultListModel = new DefaultListModel();
			jspAtributesDefaultListModel = new DefaultListModel();
			jspNotSelectedAtributesDefaultListModel = new DefaultListModel();
			primaryKey = new ArrayList<String[]>();
			fieldsForUpdate = new ArrayList<String[]>();
			fieldsOfTable = new ArrayList<String[]>();
			fieldsForJSP = new ArrayList<String[]>();
			fieldsOfTableForJSP = new ArrayList<String[]>();
			String[] auxRecord;
			for (Vector<String> vector : vc_auxDatos) {
				if (!controlFields.contains(vector.get(0))) {
					auxRecord = new String[7];
					auxRecord[0] = vector.get(0);
					auxRecord[1] = vector.get(1);
					auxRecord[2] = vector.get(2);
					auxRecord[3] = vector.get(3);
					auxRecord[4] = vector.get(4);
					auxRecord[5] = vector.get(5);
					auxRecord[6] = vector.get(6);
					if (!auxRecord[6].equals("PRI")) {
						atributesDefaultListModel.addElement(vector.get(0));
						fieldsForUpdate.add(auxRecord);
						jspAtributesDefaultListModel.addElement(vector.get(0));
						fieldsForJSP.add(auxRecord);
					} else {
						primaryKey.add(auxRecord);
					}
				}
			}
			jList_updateAtributes.setModel(atributesDefaultListModel);
			jListAtributos.setModel(notSelectedAtributesDefaultListModel);
			jList2.setModel(jspNotSelectedAtributesDefaultListModel);
			jList3.setModel(jspAtributesDefaultListModel);
			cargarTablasMaster();
			recargarQueries();
		}
	}

	private void jcb_tablasActionActionPerformed(ActionEvent event) {
		if (jcb_bases_datos.getSelectedIndex() != -1 && jcb_tablas.getSelectedIndex() != -1) {
			vc_auxDatos = getColumnsOfTable(jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString());
			refreshAtributesForJoinAtribute(vc_auxDatos);
			controllerName.setText(createDomainName(jcb_tablas.getSelectedItem().toString()));
		}
	}

	public Vector<Vector<String>> getColumnsOfTable(String dataBase, String tableName) {
		if (dataBase != null && tableName != null) {
			return clsBase.Consulta_General("SELECT c.column_name, CONCAT(LCASE(LEFT(c.column_name,1)),SUBSTRING(c.column_name,2)),\n"
					+ "CASE c.is_nullable WHEN 'NO' THEN 'false' ELSE 'true' END nullable, c.data_type, IFNULL(IFNULL(c.CHARACTER_MAXIMUM_LENGTH, c.NUMERIC_PRECISION),0) longitud,\n"
					+ "IFNULL(c.numeric_scale,'0'), c.column_key\n" + "FROM COLUMNS c WHERE c.table_schema = '" + dataBase + "' AND c.table_name = '" + tableName + "' ORDER BY c.ORDINAL_POSITION");
		}
		return null;
	}

	private void jButton1ActionActionPerformed(ActionEvent event) {
		int[] auxUpdatesIndexes = jList_updateAtributes.getSelectedIndices();
		for (int i = 0; i < auxUpdatesIndexes.length; i++) {
			notSelectedAtributesDefaultListModel.addElement(atributesDefaultListModel.get((int) (auxUpdatesIndexes[i] - i)));
			atributesDefaultListModel.remove((int) (auxUpdatesIndexes[i] - i));
			fieldsOfTable.add(fieldsForUpdate.get((int) (auxUpdatesIndexes[i] - i)));
			fieldsForUpdate.remove((int) (auxUpdatesIndexes[i] - i));
		}
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		int[] auxUpdatesIndexes = jListAtributos.getSelectedIndices();
		for (int i = 0; i < auxUpdatesIndexes.length; i++) {
			atributesDefaultListModel.addElement(notSelectedAtributesDefaultListModel.get((int) (auxUpdatesIndexes[i] - i)));
			notSelectedAtributesDefaultListModel.remove((int) (auxUpdatesIndexes[i] - i));
			fieldsForUpdate.add(fieldsOfTable.get((int) (auxUpdatesIndexes[i] - i)));
			fieldsOfTable.remove((int) (auxUpdatesIndexes[i] - i));
		}
	}

	private void jButton2ActionActionPerformed(ActionEvent event) {
		// fieldsForUpdate
		if (destinationFolder != null) {
			/* for (String[] string : fieldsForUpdate) { for (String string2 : string) { System.out.print(string2 + "\t"); } System.out.println(); } */
			createFile(jcb_tablas.getSelectedItem().toString());
		} else {
			JOptionPane.showMessageDialog(this, "Seleccione un Destination Folder ...", "Error", JOptionPane.ERROR_MESSAGE);
			jButton6.requestFocus();
		}
	}

	private void jButton4ActionActionPerformed(ActionEvent event) {
		System.exit(0);
	}

	private void jButton5ActionActionPerformed(ActionEvent event) {
		int auxSelectedIndex = jList_updateAtributes.getSelectedIndex();
		if (auxSelectedIndex != -1 && auxSelectedIndex > 0) {
			String[] auxFieldsForUpdate = fieldsForUpdate.get(auxSelectedIndex - 1);
			fieldsForUpdate.set(auxSelectedIndex - 1, fieldsForUpdate.get(auxSelectedIndex));
			fieldsForUpdate.set(auxSelectedIndex, auxFieldsForUpdate);
			String auxSelectedAtribute = atributesDefaultListModel.get(auxSelectedIndex - 1).toString();
			atributesDefaultListModel.set(auxSelectedIndex - 1, atributesDefaultListModel.get(auxSelectedIndex));
			atributesDefaultListModel.set(auxSelectedIndex, auxSelectedAtribute);
			jList_updateAtributes.setSelectedIndex(auxSelectedIndex - 1);
		}
	}

	private void jButton3ActionActionPerformed(ActionEvent event) {
		int auxSelectedIndex = jList_updateAtributes.getSelectedIndex();
		if (auxSelectedIndex != -1 && auxSelectedIndex < atributesDefaultListModel.size()) {
			String[] auxFieldsForUpdate = fieldsForUpdate.get(auxSelectedIndex + 1);
			fieldsForUpdate.set(auxSelectedIndex + 1, fieldsForUpdate.get(auxSelectedIndex));
			fieldsForUpdate.set(auxSelectedIndex, auxFieldsForUpdate);
			String auxSelectedAtribute = atributesDefaultListModel.get(auxSelectedIndex + 1).toString();
			atributesDefaultListModel.set(auxSelectedIndex + 1, atributesDefaultListModel.get(auxSelectedIndex));
			atributesDefaultListModel.set(auxSelectedIndex, auxSelectedAtribute);
			jList_updateAtributes.setSelectedIndex(auxSelectedIndex + 1);
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

	public void createFile(String tableName) {
		try {
			String domainName = controllerName.getText();
			// values for Primary Key
			String parentDomainName = tableName;
			String tableNameDomainName = tableName;
			if (jchbDetail.isSelected() && jcbMasterName.getSelectedIndex() != -1) {
				parentDomainName = String.valueOf(jcbMasterName.getSelectedItem());
				parentDomainName = parentDomainName.substring(0, 1).toUpperCase() + parentDomainName.substring(1, parentDomainName.length());
			}
			String domainTableName = createDomainName(tableName);
			String[] pkValues = getPkName(primaryKey);
			Properties properties = new Properties();
			properties.setProperty("${tableNameDomainName}", tableNameDomainName);
			properties.setProperty("${table_name}", domainTableName);
			properties.setProperty("${domain_name}", domainName);
			properties.setProperty("${parent_domain_name}", parentDomainName);
			properties.setProperty("${table_pk_name}", pkValues[0]);
			properties.setProperty("${pk_name}", pkValues[1]);
			properties.setProperty("${pk_type}", dataBaseTypeToJavaType(pkValues[3], pkValues[4]));
			properties.setProperty("${pk_method}", pkValues[1].substring(0, 1).toUpperCase() + pkValues[1].substring(1, pkValues[1].length()));
			properties.setProperty("${domainPackage}", domainPackage.getText());
			properties.setProperty("${commonPackage}", domainPackage.getText().substring(0, domainPackage.getText().lastIndexOf(".")));
			properties.setProperty("${ejbPackage_name}", ejbPackage.getText());
			properties.setProperty("${commonConstantsName}", constantsClassName.getText());
			properties.setProperty("${application_name}", applicationName.getText());
			properties.setProperty("${low_caps_application_name}", applicationName.getText().toLowerCase());
			properties.setProperty("${caps_application_name}", applicationName.getText().toUpperCase());
			properties.setProperty("${application_data_source}", dataSourceName.getText());
			properties.setProperty("${constantsIdEJB_name}", applicationName.getText().toUpperCase() + "_" + moduleName.toUpperCase());
			properties.setProperty("${webPackage_name}", webPackage.getText().substring(0, webPackage.getText().lastIndexOf(".")));
			properties.setProperty("${webModule_name}", moduleName);
			properties.setProperty("${module_name_caps}", moduleName.toUpperCase());
			properties.setProperty("${commons_utils_name}", jtfCommonsUtilsName.getText());
			properties.setProperty("${web_constants_name}", jtfConstantsWebClassName.getText());
			properties.setProperty("${web_utils_name}", jtfUtilsWebClassName.getText());
			properties.setProperty("${controllerName}", controllerName.getText());
			properties.setProperty("${controller_Name}", controllerName.getText());
			List<Properties> columnsJSP = new ArrayList<Properties>();
			List<Properties> columnsJSPList = new ArrayList<Properties>();
			List<Properties> columns = new ArrayList<Properties>();
			String updateFieldsList = "";
			String updateFieldsListSimple = "";
			String updateFieldsListQuery = "";
			String auxJavaType = "";
			int counter = 0;
			for (String[] auxFieldsForUpdate : fieldsForUpdate) {
				auxJavaType = dataBaseTypeToJavaType(auxFieldsForUpdate[3], auxFieldsForUpdate[4]);
				Properties auxProperties = new Properties();
				auxProperties.setProperty("${table_column_name}", auxFieldsForUpdate[0]);
				auxProperties.setProperty("${column_nullable}", auxFieldsForUpdate[2]);
				auxProperties.setProperty("${column_length}", auxFieldsForUpdate[4]);
				auxProperties.setProperty("${column_type}", auxJavaType);
				auxProperties.setProperty("${column_name}", auxFieldsForUpdate[1]);
				auxProperties.setProperty("${method_name}", auxFieldsForUpdate[0].substring(0, 1).toUpperCase() + auxFieldsForUpdate[0].substring(1, auxFieldsForUpdate[0].length()));
				columns.add(auxProperties);
				updateFieldsList += ", " + auxJavaType + " " + auxFieldsForUpdate[1];
				updateFieldsListSimple += ", " + auxFieldsForUpdate[1];
				updateFieldsListQuery += ",\n\t" + auxFieldsForUpdate[1] + "=?" + (counter + 4);
				counter++;
			}
			// (obj${domain_name}.get${domain_method_name}()
			String javascriptDatepickerFormat = "";
			boolean calendarValueFound = false;
			boolean doubleValueFound = false;
			boolean intValueFound = false;
			boolean modifyLabelAdded = false;
			String addJspFormContent = "";
			String addJspFormRequiredLabel = "";
			String comboboxDisplayValue = "";
			String javascriptNotNullFields = "";
			String javascriptVerifyFields = "";
			for (String[] auxFieldsForJSP : fieldsForJSP) {
				comboboxDisplayValue = "";
				auxJavaType = dataBaseTypeToJavaType(auxFieldsForJSP[3], auxFieldsForJSP[4]);
				Properties auxProperties = new Properties();
				auxProperties.setProperty("${table_column_name}", auxFieldsForJSP[0]);
				auxProperties.setProperty("${column_nullable}", auxFieldsForJSP[2]);
				auxProperties.setProperty("${column_length}", auxFieldsForJSP[4]);
				auxProperties.setProperty("${column_type}", auxJavaType);
				auxProperties.setProperty("${column_name}", auxFieldsForJSP[1]);
				//
				//
				String methodName, domainMethodName, columnDisplayFormat, columnRecoveryFormat, columnNameDisplayList;
				// Values for Null
				addJspFormRequiredLabel = "";
				columnNameDisplayList = "${record." + auxFieldsForJSP[1] + "}";
				if (!modifyLabelAdded && (!(auxFieldsForJSP[1].equalsIgnoreCase(String.valueOf(jcbColumnForJoin.getSelectedItem()))))) {
					modifyLabelAdded = true;
					// columnNameDisplayList = "<a href=\"update${domainName}.html?pkValue=${record.pkValue}&posInList=${status.index}\"> " + columnNameDisplayList + "</a>";
					columnNameDisplayList = getModifyLabelCode(domainName, columnNameDisplayList);
				}
				if (String.valueOf(auxFieldsForJSP[2]).equals("false")) {
					addJspFormRequiredLabel = "<spring:message code=\"common.label.requiredField.value\" />";
					if (javascriptNotNullFields.length() > 0) {
						javascriptNotNullFields += ", ";
					}
					javascriptNotNullFields += "'" + auxFieldsForJSP[1] + "'";
				}
				// End Values For Null
				methodName = getMethodName(auxFieldsForJSP[1]);
				auxProperties.setProperty("${method_name}", methodName);
				domainMethodName = auxFieldsForJSP[0].substring(0, 1).toUpperCase() + auxFieldsForJSP[0].substring(1, auxFieldsForJSP[0].length());
				auxProperties.setProperty("${domain_method_name}", domainMethodName);
				columnDisplayFormat = "obj" + domainName + ".get" + domainMethodName + "()";
				columnRecoveryFormat = "objBean.get" + methodName + "()";
				String contentForFieldAddJSPStart = "                <tr>\r\n" + "                    <td>\r\n" + "                        <label id=\"" + auxFieldsForJSP[1]
						+ "_label\"><spring:message code=\"" + domainName + ".table.label." + auxFieldsForJSP[1] + "\" /></label>${add_jsp_form_required_label}\r\n"
						+ "                    </td>\r\n                </tr>\r\n                <tr>\r\n                    ";
				String contentForFieldAddJSPEnd = "\r\n                </tr>\r\n";
				if (auxJavaType.equals("Calendar")) {
					columnDisplayFormat = jtfCommonsUtilsName.getText() + ".CalendarToString(" + columnDisplayFormat + ", " + jtfConstantsWebClassName.getText() + ".DATEFORMAT_DD_MM_YYYY)";
					columnRecoveryFormat = jtfCommonsUtilsName.getText() + ".StringToCalendar(" + columnRecoveryFormat + ", " + jtfConstantsWebClassName.getText() + ".DATEFORMAT_DD_MM_YYYY)";
					auxProperties.setProperty("${beanRecordColumnType}", "String");
					if (!calendarValueFound) {
						calendarValueFound = true;
						javascriptDatepickerFormat += "            $(\"input.datepickerMapiriClass\").datepicker();\r\n";
					}
					addJspFormContent = "<td class=\"datepickerMapiriClassContainer\"><form:input path=\"" + auxFieldsForJSP[1]
							+ "\" cssClass=\"datepickerMapiriClass\"/><small><spring:message code=\"common.datepicker.format_label.value\" /></small></td>";
					if (javascriptVerifyFields.length() > 0) {
						javascriptVerifyFields += ", ";
					}
					javascriptVerifyFields += "'" + auxFieldsForJSP[1] + "'";
				} else {
					String columnTerminationText = auxFieldsForJSP[1].substring(auxFieldsForJSP[1].length() - 3, auxFieldsForJSP[1].length()).toLowerCase();
					if (auxFieldsForJSP[1].equalsIgnoreCase(String.valueOf(jcbColumnForJoin.getSelectedItem()))) {
						contentForFieldAddJSPStart = contentForFieldAddJSPEnd = "";
						addJspFormContent = "<form:hidden path=\"" + auxFieldsForJSP[1] + "\"  />";
					} else {
						String comboBoxInitialValue = "";
						if (columnTerminationText.equals("_id") || columnTerminationText.equals("_fl")) {
							String auxComboBoxType = jtfConstantsWebClassName.getText() + ".DEFAULT_COMBO_BOX_OPTIONS";
							if ((auxJavaType.equals("int") || auxJavaType.equals("long")) && auxFieldsForJSP[4].equals("11")) {
								comboBoxInitialValue = "0";
								auxComboBoxType = jtfConstantsWebClassName.getText() + ".DEFAULT_COMBO_BOX_OPTIONS";
							} else {
								if (auxJavaType.equals("String") && auxFieldsForJSP[4].equals("2")) {
									auxComboBoxType = jtfConstantsWebClassName.getText() + ".DEFAULT_STRING_COMBO_BOX_OPTIONS";
								}
							}
							addJspFormContent = "<td><form:select path=\"" + auxFieldsForJSP[1] + "\">" + "	<form:option value=\"" + comboBoxInitialValue + "\">"
									+ "		<spring:message code=\"common.text.blank_value\" />" + "	</form:option>" + "	<form:options items=\"${Options" + auxFieldsForJSP[1] + "}\" />"
									+ "</form:select></td>";
							comboboxDisplayValue = "model.addAttribute(\"Options" + auxFieldsForJSP[1] + "\", " + auxComboBoxType + ");";
						} else {
							int maxLength = 0;
							if (auxFieldsForJSP[4].indexOf(",") == -1) {
								maxLength = Integer.parseInt(auxFieldsForJSP[4]);
							}
							if (maxLength > 100) {
								addJspFormContent = "<td><form:textarea path=\"" + auxFieldsForJSP[1] + "\" rows=\"2\" maxlength=\"" + maxLength + "\" /></td>";
								columnNameDisplayList = "<textarea readonly=\"readonly\" rows=\"2\" >" + columnNameDisplayList + "</textarea>";
							} else {
								if (auxJavaType.equals("double") || auxJavaType.equals("BigDecimal")) {
									if (!doubleValueFound) {
										doubleValueFound = true;
										// javascriptDatepickerFormat += "            $(\"input.decimalMapiriClass\").autoNumeric();\r\n";
									}
									addJspFormContent = "<td><form:input path=\"" + auxFieldsForJSP[1] + "\" cssClass=\"decimalMapiriClass\" alt=\"p9x0p2S\" maxlength=\"14\" /></td>";
									if (javascriptVerifyFields.length() > 0) {
										javascriptVerifyFields += ", ";
									}
									javascriptVerifyFields += "'" + auxFieldsForJSP[1] + "'";
								} else {
									if (auxJavaType.equals("long") || auxJavaType.equals("int")) {
										if (!intValueFound) {
											intValueFound = true;
											// javascriptDatepickerFormat += "            $(\"input.integerMapiriClass\").ForceNumericOnly();\r\n";
										}
										addJspFormContent = "<td><form:input path=\"" + auxFieldsForJSP[1] + "\" cssClass=\"integerMapiriClass\" maxlength=\"" + maxLength + "\" /></td>";
										if (javascriptVerifyFields.length() > 0) {
											javascriptVerifyFields += ", ";
										}
										javascriptVerifyFields += "'" + auxFieldsForJSP[1] + "'";
									} else {
										addJspFormContent = "<td><form:input path=\"" + auxFieldsForJSP[1] + "\" maxlength=\"" + maxLength + "\" /></td>";
									}
								}
							}
						}
					}
					auxProperties.setProperty("${beanRecordColumnType}", auxJavaType);
				}
				addJspFormContent = contentForFieldAddJSPStart + addJspFormContent + contentForFieldAddJSPEnd;
				auxProperties.setProperty("${column_name_display_list}", columnNameDisplayList);
				auxProperties.setProperty("${combobox_display_value}", comboboxDisplayValue);
				auxProperties.setProperty("${column_display_format}", columnDisplayFormat);
				auxProperties.setProperty("${column_recovery_format}", columnRecoveryFormat);
				auxProperties.setProperty("${add_jsp_form_content}", addJspFormContent);
				auxProperties.setProperty("${add_jsp_form_required_label}", addJspFormRequiredLabel);
				columnsJSP.add(auxProperties);
				if (!(auxFieldsForJSP[1].equalsIgnoreCase(String.valueOf(jcbColumnForJoin.getSelectedItem())))) {
					columnsJSPList.add(auxProperties);
				}
			}
			String auxJavascriptDatepickerFormat = "";
			if (calendarValueFound || doubleValueFound || intValueFound) {
				auxJavascriptDatepickerFormat = "$(document).ready(\r\n" + "        function() {";
				if (calendarValueFound) {
					auxJavascriptDatepickerFormat += " $.datepicker.setDefaults({\r\n" + "        changeYear : true,\r\n" + "        changeMonth : true,\r\n"
							+ "        yearRange : param.datepickerMapiriClass.yearRange.value,\r\n" + "        maxDate : param.datepickerMapiriClass.maxDate.value,\r\n"
							+ "        dateFormat : param.common.date_format.codes,\r\n" + "        numberOfMonths : (param.datepickerMapiriClass.numberOfMonths.value * 1),\r\n"
							+ "        showOn : \"button\",\r\n" + "        buttonImage : param.datepicker.button_image.value,\r\n" + "        buttonImageOnly : true\r\n" + "    });";
				}
				auxJavascriptDatepickerFormat += javascriptDatepickerFormat + "        });";
			}
			properties.setProperty("${javascript_verify_fields}", javascriptVerifyFields);
			properties.setProperty("${javascript_not_null_fields}", javascriptNotNullFields);
			properties.setProperty("${updateFieldsList}", updateFieldsList);
			properties.setProperty("${javascript_datepicker_format}", auxJavascriptDatepickerFormat);
			properties.setProperty("${updateFieldsList}", updateFieldsList);
			properties.setProperty("${updateFieldsListSimple}", updateFieldsListSimple);
			properties.setProperty("${updateFieldsListQuery}", updateFieldsListQuery);
			properties.setProperty("${delegate_name}", delegateName);
			properties.setProperty("${delegate_name_caps}", delegateName.toUpperCase());
			properties.setProperty("${master_detail_fk}", String.valueOf(jcbColumnForJoin.getSelectedItem()));
			EditTextFiles editTextFiles = new EditTextFiles();
			String newDirectory = ejbPackage.getText().replace(".", System.getProperty("file.separator"));
			String webDirectory = webPackage.getText().replace(".", System.getProperty("file.separator"));
			String domainDirectory = domainPackage.getText().replace(".", System.getProperty("file.separator"));
			if (jchDomain.isSelected()) {
				// Domain
				editTextFiles.readTemplateAndCreateFileDomains(destinationDomainFolder, null, properties, "domainFormat.txt",
						"src" + System.getProperty("file.separator") + domainDirectory + System.getProperty("file.separator") + domainName + ".java", columns);
				String textToAddPersistence = "<class>" + domainPackage.getText() + "." + domainTableName + "</class>";
				editTextFiles.searchAndAddToFile(
						new File(destinationDomainFolder, "src" + System.getProperty("file.separator") + "META-INF" + System.getProperty("file.separator") + "persistence.xml"),
						"<!-- Reserved for Domains -->", textToAddPersistence, textToAddPersistence);
			}
			if (jchEJB.isSelected()) {
				// Named QUery
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "ejbModule" + System.getProperty("file.separator") + "META-INF", properties, "namedQueryFormat.txt", "named-query-"
						+ domainName + ".xml", null);
				// Persistence
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "ejbModule" + System.getProperty("file.separator") + "META-INF", properties, "persistenceFormat.txt", "persistence.xml",
						null);
				// Logger
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "ejbModule", properties, "log4jFormat.txt", "log4j" + delegateName + ".properties", null);
				// Abstract
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "common", properties,
						"abstractBaseDaoFormat.txt", "AbstractBaseDao.java", null);
				// AbstractJpaDao
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "common", properties,
						"abstractJpaDaoFormat.txt", "AbstractJpaDao.java", null);
				// EJB Constants
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "constants",
						properties, "constantsFormat.txt", delegateName + "Constants.java", null);
				// EJB Utils
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "utility", properties,
						"utilsFormat.txt", delegateName + "Utils.java", null);
				// Dao
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "utility", properties,
						"configManagerFormat.txt", delegateName + "ConfigManager.java", null);
				// Dao
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "dao", properties,
						"daoFormat.txt", domainName + "Dao.java", null);
				// Dao Implementation
				editTextFiles.readTemplateAndCreateFile(destinationFolder,
						"src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "dao" + System.getProperty("file.separator") + "impls", properties,
						"daoImplFormat.txt", domainName + "DaoImpl.java", null);
				// Service
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "service", properties,
						"serviceFormat.txt", domainName + "Service.java", null);
				// Service Implementation
				editTextFiles.readTemplateAndCreateFile(destinationFolder,
						"src" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "service" + System.getProperty("file.separator") + "impls", properties,
						"serviceImplFormat.txt", domainName + "ServiceImpl.java", columns);
				// EJB Remote
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "ejbModule" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "ejb",
						properties, "ejbRemoteFormat.txt", applicationName.getText() + delegateName + "EjbRemote.java", null);
				// EJB
				editTextFiles.readTemplateAndCreateFile(destinationFolder, "ejbModule" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "ejb",
						properties, "ejbFormat.txt", applicationName.getText() + delegateName + "Ejb.java", null);
				// Adding to EJB imports
				String textForAddingImportsServices = "import " + properties.getProperty("${ejbPackage_name}") + ".service." + domainName + "Service;\r\n" + "import "
						+ properties.getProperty("${ejbPackage_name}") + ".service.impls." + domainName + "ServiceImpl;";
				editTextFiles.searchAndAddToFile(
						new File(destinationFolder, "ejbModule" + System.getProperty("file.separator") + newDirectory + System.getProperty("file.separator") + "ejb"
								+ System.getProperty("file.separator") + applicationName.getText() + delegateName + "Ejb.java"), "//Reserved For Imports", textForAddingImportsServices,
						textForAddingImportsServices);
				// Delegates
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder,
						moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator") + "delegates", properties, "delegateFormat.txt", delegateName
								+ "Delegate.java", null);
				String constantValueForEjb = properties.getProperty("${application_name}") + delegateName + "Ejb";
				String textForEJBConfigurationCall = "<!-- " + delegateName + " -->\r\n      <bean id=\"" + constantValueForEjb + "\" class=\"" + properties.getProperty("${webPackage_name}")
						+ ".common.utility.AppEjbConfigurationBean\">\r\n" + "        <property name=\"providerUrl\" value=\"jnp://localhost:1099\" />\r\n"
						+ "        <property name=\"lookupName\"\r\n" + "            value=\"" + properties.getProperty("${application_name}") + properties.getProperty("${delegate_name}")
						+ "Ejb/remote-" + properties.getProperty("${ejbPackage_name}") + ".ejb." + properties.getProperty("${application_name}") + properties.getProperty("${delegate_name}")
						+ "EjbRemote\" />\r\n" + "     </bean>";
				// Adding to ejbConfigurationContext.xml
				editTextFiles.searchAndAddToFile(new File(fileWebDestinationFolder, "resources" + System.getProperty("file.separator") + "ejbConfigurationContext.xml"),
						"<!-- Reserved for New EJB's configuration -->", "<!-- " + delegateName + " -->", textForEJBConfigurationCall);
				// Adding to WebConstants.java
				String textToAddInWebConstants = "public static String EJB_" + properties.getProperty("${constantsIdEJB_name}") + " = \"" + constantValueForEjb + "\";";
				editTextFiles.searchAndAddToFile(
						new File(fileWebDestinationFolder, "src" + System.getProperty("file.separator")
								+ properties.getProperty("${webPackage_name}").replace(".", System.getProperty("file.separator")) + System.getProperty("file.separator") + "common"
								+ System.getProperty("file.separator") + "constants" + System.getProperty("file.separator") + jtfConstantsWebClassName.getText() + ".java"),
						"// Reserved for EJB Application Constants", textToAddInWebConstants, textToAddInWebConstants);
			}
			if (jchWeb.isSelected()) {
				// Bean
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator") + "beans",
						properties, "beanFormat.txt", domainName + "Bean.java", columnsJSP);
				// Bean Record
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator") + "beans",
						properties, "beanRecordFormat.txt", domainName + "BeanRecord.java", columnsJSP);
				// Controller
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator")
						+ "controllers", properties, "controllerFormat.txt", domainName + "Controller.java", columnsJSP);
				if (jchbDetail.isSelected()) {
					String vl_ParentDomain = createDomainName(String.valueOf(jcbMasterName.getSelectedItem()));
					editTextFiles.searchLineCOntentAndAddToFile(
							new File(fileWebDestinationFolder, moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator") + "controllers"
									+ System.getProperty("file.separator") + vl_ParentDomain + "Controller.java"), "/* Reserved for Details Domains */", ", \"" + domainName + "\"", ", \""
									+ domainName + "\"");
					String valueToAddDetail = "objBean.set" + getMethodName(String.valueOf(jcbColumnForJoin.getSelectedItem())) + "(" + jtfUtilsWebClassName.getText()
							+ ".getFKValueFromSession(request, controllerName));";
					editTextFiles.searchAndAddToFile(new File(fileWebDestinationFolder, moduleName + System.getProperty("file.separator") + webDirectory + System.getProperty("file.separator")
							+ "controllers" + System.getProperty("file.separator") + domainName + "Controller.java"), "//Reserved for FK", valueToAddDetail, valueToAddDetail);
				}
				// tiles list
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, tilesDirectory + moduleName + System.getProperty("file.separator"), properties, "tilesFormat.txt", "tiles-"
						+ domainName + ".xml", columnsJSP);
				// jsp list
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, jspDirectory + moduleName + System.getProperty("file.separator"), properties, "jspListFormat.txt", "list"
						+ domainName + ".jsp", columnsJSPList);
				// jsp Add
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, jspDirectory + moduleName + System.getProperty("file.separator"), properties, "jspAddFormat.txt", "add" + domainName
						+ ".jsp", columnsJSP);
				// properties
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, resourcesDirectory + moduleName + System.getProperty("file.separator"), properties, "propertiesFormat.txt",
						domainName + ".properties", columnsJSP);
				// javascript
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, javascriptDirectory + moduleName + System.getProperty("file.separator"), properties, "javascriptFormat.txt",
						domainName + ".js", columnsJSP);
				// web-application-config.xml Tiles Config File
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, webApplicationConfigFile, properties, "web-application-configFormat.txt", "web-application-config.xml", columnsJSP);
				// web-application-config.xml Properties Config File
				editTextFiles.readTemplateAndCreateFile(fileWebDestinationFolder, webApplicationConfigFile, properties, "web-application-configPropertiesFormat.txt", "web-application-config.xml",
						columnsJSP);
			}
			savePreferencesValuesToForm(String.valueOf(jcb_bases_datos.getSelectedItem()));
			JOptionPane.showMessageDialog(this, "Operacion realizada exitosamente");
			// Runtime.getRuntime().exec("C:\\Windows\\explorer.exe " + destinationFolder.getAbsolutePath());
			// Runtime.getRuntime().exec("C:\\Windows\\explorer.exe " + fileWebDestinationFolder.getAbsolutePath());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception Ocurred:\n" + e.getMessage(), "Exception Found", JOptionPane.ERROR_MESSAGE);
			ConfigManager.printException(LOGGER, e);
		}
	}

	public String createDomainName(String tableName) {
		return tableName.substring(0, 1).toUpperCase() + tableName.substring(1, tableName.length());
	}

	public String[] getPkName(List<String[]> primaryKey) {
		return primaryKey.get(0);
	}

	public String dataBaseTypeToJavaType(String dataBaseType, String fieldLength) {
		if (dataBaseType.equals("varchar")) {
			return "String";
		} else {
			if (dataBaseType.equals("int")) {
				return "int";
			} else {
				if (dataBaseType.equals("bigint")) {
					return "long";
				} else {
					if (dataBaseType.equals("datetime")) {
						return "Calendar";
					} else {
						if (dataBaseType.equals("decimal")) {
							if (Integer.parseInt(fieldLength) > 10) {
								return "BigDecimal";
							} else {
								return "double";
							}
						}
					}
				}
			}
		}
		return "";
	}

	public String getMethodName(String columnName) {
		if (!Character.isUpperCase(columnName.charAt(1))) {
			return columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
		} else {
			return columnName;
		}
	}

	private void jButton6ActionActionPerformed(ActionEvent event) {
		destinationFolder = Leer_Archivo(destinationFolder);
		if (destinationFolder != null) {
			txtDestinationFolder.setText(destinationFolder.getAbsolutePath());
			String aplicationName = txtDestinationFolder.getText();
			String packageAplicationName = txtDestinationFolder.getText().toLowerCase();
			moduleName = txtDestinationFolder.getText().toLowerCase();
			delegateName = txtDestinationFolder.getText();
			int moduleMatch = moduleName.indexOf("mod");
			int lastPathSeparator = aplicationName.lastIndexOf(System.getProperty("file.separator"));
			delegateName = delegateName.substring(moduleMatch + 3, moduleName.length());
			moduleName = moduleName.substring(moduleMatch + 3, moduleName.length());
			delegateName = delegateName.substring(0, 1).toUpperCase() + delegateName.substring(1, delegateName.length());
			aplicationName = aplicationName.substring(lastPathSeparator + 1, moduleMatch);
			packageAplicationName = packageAplicationName.substring(lastPathSeparator + 1, moduleMatch);
			int ejbAlicationName = aplicationName.toLowerCase().indexOf("ejb");
			aplicationName = aplicationName.substring(ejbAlicationName + 3, aplicationName.length());
			applicationName.setText(aplicationName);
			// Setting Variables Names
			setVariablesNames(aplicationName);
			dataSourceName.setText(aplicationName);
			ejbPackage.setText("com." + packageAplicationName + "." + moduleName);
		}
	}

	private void setVariablesNames(String aplicationName) {
		if (constantsClassName.getText().length() == 0) {
			constantsClassName.setText(aplicationName + "Constants");
		}
		if (jtfCommonsUtilsName.getText().length() == 0) {
			jtfCommonsUtilsName.setText(aplicationName + "Utils");
		}
		if (jtfConstantsWebClassName.getText().length() == 0) {
			jtfConstantsWebClassName.setText(aplicationName + "WebConstants");
		}
		if (jtfUtilsWebClassName.getText().length() == 0) {
			jtfUtilsWebClassName.setText(aplicationName + "WebUtils");
		}
	}

	private void applicationNameFocusFocusLost(FocusEvent event) {
		String appName = applicationName.getText();
		// Setting Variables Names
		setVariablesNames(appName);
		dataSourceName.setText(appName);
	}

	private void jButton7ActionActionPerformed(ActionEvent event) {
		fileWebDestinationFolder = Leer_Archivo(fileWebDestinationFolder);
		if (fileWebDestinationFolder != null) {
			webDestinationFolder.setText(fileWebDestinationFolder.getAbsolutePath());
			webPackage.setText("com." + fileWebDestinationFolder.getName().toLowerCase() + "." + moduleName);
		}
	}

	private void jButton8ActionActionPerformed(ActionEvent event) {
		int[] auxUpdatesIndexes = jList2.getSelectedIndices();
		if (auxUpdatesIndexes != null && auxUpdatesIndexes.length > 0) {
			for (int i = 0; i < auxUpdatesIndexes.length; i++) {
				jspAtributesDefaultListModel.addElement(jspNotSelectedAtributesDefaultListModel.get((int) (auxUpdatesIndexes[i] - i)));
				jspNotSelectedAtributesDefaultListModel.remove((int) (auxUpdatesIndexes[i] - i));
				fieldsForJSP.add(fieldsOfTableForJSP.get((int) (auxUpdatesIndexes[i] - i)));
				fieldsOfTableForJSP.remove((int) (auxUpdatesIndexes[i] - i));
			}
		}
	}

	private void jButton9ActionActionPerformed(ActionEvent event) {
		int[] auxUpdatesIndexes = jList3.getSelectedIndices();
		if (auxUpdatesIndexes != null && auxUpdatesIndexes.length > 0) {
			for (int i = 0; i < auxUpdatesIndexes.length; i++) {
				jspNotSelectedAtributesDefaultListModel.addElement(jspAtributesDefaultListModel.get((int) (auxUpdatesIndexes[i] - i)));
				jspAtributesDefaultListModel.remove((int) (auxUpdatesIndexes[i] - i));
				fieldsOfTableForJSP.add(fieldsForJSP.get((int) (auxUpdatesIndexes[i] - i)));
				fieldsForJSP.remove((int) (auxUpdatesIndexes[i] - i));
			}
		}
	}

	private void jButton10ActionActionPerformed(ActionEvent event) {
		int auxSelectedIndex = jList3.getSelectedIndex();
		if (auxSelectedIndex != -1 && auxSelectedIndex > 0) {
			String[] auxFieldsForUpdate = fieldsForJSP.get(auxSelectedIndex - 1);
			fieldsForJSP.set(auxSelectedIndex - 1, fieldsForJSP.get(auxSelectedIndex));
			fieldsForJSP.set(auxSelectedIndex, auxFieldsForUpdate);
			String auxSelectedAtribute = jspAtributesDefaultListModel.get(auxSelectedIndex - 1).toString();
			jspAtributesDefaultListModel.set(auxSelectedIndex - 1, jspAtributesDefaultListModel.get(auxSelectedIndex));
			jspAtributesDefaultListModel.set(auxSelectedIndex, auxSelectedAtribute);
			jList3.setSelectedIndex(auxSelectedIndex - 1);
		}
	}

	private void jButton11ActionActionPerformed(ActionEvent event) {
		int auxSelectedIndex = jList3.getSelectedIndex();
		if (auxSelectedIndex != -1 && auxSelectedIndex < jspAtributesDefaultListModel.size()) {
			String[] auxFieldsForUpdate = fieldsForJSP.get(auxSelectedIndex + 1);
			fieldsForJSP.set(auxSelectedIndex + 1, fieldsForJSP.get(auxSelectedIndex));
			fieldsForJSP.set(auxSelectedIndex, auxFieldsForUpdate);
			String auxSelectedAtribute = jspAtributesDefaultListModel.get(auxSelectedIndex + 1).toString();
			jspAtributesDefaultListModel.set(auxSelectedIndex + 1, jspAtributesDefaultListModel.get(auxSelectedIndex));
			jspAtributesDefaultListModel.set(auxSelectedIndex, auxSelectedAtribute);
			jList3.setSelectedIndex(auxSelectedIndex + 1);
		}
	}

	public String getModifyLabelCode(String domainName, String columnName) {
		String textResult = "                               <c:if test=\"${!(empty updateControl" + domainName + " && empty deleteByIdControl" + domainName + " && empty optionsController"
				+ domainName + ")}\">\r\n" + "                                    <div class=\"dropdown\">\r\n" + "                                        <ul>\r\n"
				+ "                                            <li><span>\r\n" + "                                                    <a href=\"#\">" + columnName + "</a>\r\n"
				+ "                                                </span>\r\n" + "                                                <ul class=\"no_rotate\">\r\n"
				+ "                                                    <li>\r\n" + "                                                        <h5>\r\n"
				+ "                                                            <spring:message code=\"general.label.general.optionsController\" />\r\n"
				+ "                                                        </h5>\r\n" + "                                                    </li>\r\n"
				+ "                                                    <c:if test=\"${!empty updateControl" + domainName + "}\">\r\n"
				+ "                                                        <li>\r\n" + "                                                            <div onclick=\"openLocation('update" + domainName
				+ ".html?pkValue=${record.pkValue}&posInList=${status.index}')\" title=\"<spring:message code=\"" + domainName + ".title.button.modify\" />\">\r\n"
				+ "                                                                <input class=\"asignarf\" type=\"button\" onclick=\"openLocation('update" + domainName
				+ ".html?pkValue=${record.pkValue}&posInList=${status.index}')\" value=\"\"\r\n"
				+ "                                                                    title=\"<spring:message code=\"" + domainName + ".title.button.modify\" />\" />\r\n"
				+ "                                                                <p>\r\n"
				+ "                                                                    <spring:message code=\"general.label.general.modificar\" />\r\n"
				+ "                                                                </p>\r\n" + "                                                            </div>\r\n"
				+ "                                                        </li>\r\n" + "                                                    </c:if>\r\n"
				+ "                                                    <c:if test=\"${!record.selected && !empty deleteByIdControl" + domainName + "}\">\r\n"
				+ "                                                        <li>\r\n" + "                                                            <div onclick=\"confirmDeleteById('" + domainName
				+ "','${record.pkValue}','${status.index}')\" title=\"<spring:message code=\"" + domainName + ".title.button.delete\" />\">\r\n"
				+ "                                                                <input class=\"delete\" type=\"button\" onclick=\"confirmDeleteById('" + domainName
				+ "','${record.pkValue}','${status.index}')\" value=\"\"\r\n" + "                                                                    title=\"<spring:message code=\"" + domainName
				+ ".title.button.delete\" />\" />\r\n" + "                                                                <p>\r\n"
				+ "                                                                    <spring:message code=\"general.label.general.delete\" />\r\n"
				+ "                                                                </p>\r\n" + "                                                            </div>\r\n"
				+ "                                                        </li>\r\n" + "                                                    </c:if>\r\n"
				+ "                                                    <c:if test=\"${!empty optionsController" + domainName + "}\">\r\n"
				+ "                                                        <c:forEach items=\"${optionsController" + domainName + "}\" var=\"optionsControllerRecord\">\r\n"
				+ "                                                            <li>\r\n"
				+ "                                                                <div title=\"<spring:message code=\"${optionsControllerRecord}.label.list.title\" />\"\r\n"
				+ "                                                                    onclick=\"openLocation('optionsController.html?currentDomainName=" + domainName
				+ "&detailDomainName=${optionsControllerRecord}&pkValue=${record.pkValue}&pkLabel=" + columnName + "&posInList=${status.index}')\">\r\n"
				+ "                                                                    <input class=\"optionButton${optionsControllerRecord}\" type=\"button\"\r\n"
				+ "                                                                        onclick=\"openLocation('optionsController.html?currentDomainName=" + domainName
				+ "&detailDomainName=${optionsControllerRecord}&pkValue=${record.pkValue}&pkLabel=" + columnName + "&posInList=${status.index}')\"\r\n"
				+ "                                                                        value=\"\" title=\"<spring:message code=\"${optionsControllerRecord}.label.list.title\" />\" />\r\n"
				+ "                                                                    <p><spring:message code=\"${optionsControllerRecord}.label.options.parent\" /></p>\r\n"
				+ "                                                                </div>\r\n" + "                                                            </li>\r\n"
				+ "                                                        </c:forEach>\r\n" + "                                                    </c:if>\r\n"
				+ "                                                </ul></li>\r\n" + "                                        </ul>\r\n" + "                                    </div>\r\n"
				+ "                                </c:if>\r\n" + "                                <c:if test=\"${empty updateControl" + domainName + " && empty deleteByIdControl" + domainName
				+ " && empty optionsController" + domainName + "}\">\r\n" + "                                    " + columnName + "\r\n" + "                                </c:if>";
		return textResult;
	}

	public int getNextOrder() {
		Vector<Vector<String>> aux_datos = clsBase.Consulta_General("SELECT IFNULL(MAX(iPrograma_order),0) FROM " + jcb_bases_datos.getSelectedItem()
				+ ".ms_tblProgramas WHERE iTPrograma_fl = '04' AND iModulo_id = " + auxModulos.get(jcbModulos.getSelectedItem()));
		return Integer.parseInt(aux_datos.get(0).get(0)) + 1;
	}

	public int getNextOrderParent(String iPrograma_parent) {
		Vector<Vector<String>> aux_datos = clsBase.Consulta_General("SELECT IFNULL(MAX(iPrograma_order),0) FROM " + jcb_bases_datos.getSelectedItem() + ".ms_tblProgramas WHERE iPrograma_parent = "
				+ iPrograma_parent);
		return Integer.parseInt(aux_datos.get(0).get(0)) + 1;
	}

	// 01 Listar
	// 02 Insertar
	// 03 Modificar
	// 04 Eliminar
	// 07 Guardar
	// 12 Listar Back
	// 13 Eiminar por Id
	public String generateInsertsForController(String lModulo_id, String database, String tableName, String iTOperation_fl) {
		String vl_domainName = controllerName.getText();
		String vl_name = "list";
		String vl_description = "listar";
		String vl_TipoPrograma = "04";
		String vl_iEstado_fl = "01";
		if (jchbDetail.isSelected()) {
			vl_TipoPrograma = "01";
		}
		if (!iTOperation_fl.equals("01")) {
			vl_TipoPrograma = "01";
			if (iTOperation_fl.equals("02")) {
				vl_name = "add";
				vl_description = "adicionar";
			}
			if (iTOperation_fl.equals("03")) {
				vl_name = "update";
				vl_description = "modificar";
			}
			if (iTOperation_fl.equals("04")) {
				vl_name = "delete";
				vl_description = "eliminar";
				vl_iEstado_fl = "02";
			}
			if (iTOperation_fl.equals("07")) {
				vl_name = "save";
				vl_description = "guardar";
			}
			if (iTOperation_fl.equals("12")) {
				vl_name = "back";
				vl_description = "cancelar";
			}
			if (iTOperation_fl.equals("13")) {
				vl_name = "deleteById";
				vl_description = "eliminar por id";
			}
		}
		String vl_parent = "0";
		int auxOrder = 1;
		if (vl_TipoPrograma.equals("04")) {
			if (jcbMenusByModule.getSelectedIndex() > 0) {
				vl_parent = auxMenusByModule.get(jcbMenusByModule.getSelectedItem());
				auxOrder = getNextOrderParent(vl_parent);
			} else {
				auxOrder = getNextOrder();
			}
		}
		vl_name += vl_domainName;
		vl_description += " " + vl_domainName;
		String result;
		result = "INSERT INTO "
				+ database
				+ ".ms_tblProgramas"
				+ " (iPrograma_id,   iModulo_id,   sPrograma_nm,   sPrograma_desc,   sPrograma_url,   iTPrograma_fl,   sTable_nm,   iTOperation_fl,   iLanguage_fl,   sPrograma_tooltip,   sPrograma_label,   iPrograma_order,   iPrograma_parent,   iEstado_fl,   iEliminado_fl,   sCreated_by,   iConcurrencia_id  )  "
				+ "\nVALUES  (getNextTableId('ms_tblProgramas'),   '" + lModulo_id + "',   '" + vl_name + "',   '" + vl_description + "',   '" + vl_name + ".html',   '" + vl_TipoPrograma + "',   '"
				+ tableName + "',   '" + iTOperation_fl + "', '01',   '" + vl_description + "',   '" + vl_domainName + "',   '" + auxOrder + "',   '" + vl_parent + "',   '" + vl_iEstado_fl
				+ "',   '01',   'psoliz',   '0'  );";
		return result;
	}

	private void jcbModulosActionActionPerformed(ActionEvent event) {
		CargarMenusByModule();
	}

	// 01 Listar
	// 02 Insertar
	// 03 Modificar
	// 04 Eliminar
	// 07 Guardar
	// 12 Listar Back
	// 13 Eiminar por Id
	private void jcbMenusByModuleActionActionPerformed(ActionEvent event) {
		recargarQueries();
	}

	public void cargarTablasMaster() {
		if (jcb_bases_datos.getSelectedIndex() != -1) {
			jcbMasterName.removeAllItems();
			Vector<Vector<String>> aux_datos = clsBase.Consulta_General("select table_name from tables where table_schema = '" + jcb_bases_datos.getSelectedItem() + "' and table_name <> '"
					+ jcb_tablas.getSelectedItem() + "'");
			jcbMasterName.removeAllItems();
			for (int i = 0; i < aux_datos.size(); i++) {
				jcbMasterName.addItem(aux_datos.get(i).get(0));
			}
			vec_aux_tablas.add(0, "");
		}
	}

	private void jCheckBox0ActionActionPerformed(ActionEvent event) {
		jcbMasterName.setEnabled(jchbDetail.isSelected());
		jTextField3.setEnabled(jchbDetail.isSelected());
		recargarQueries();
	}

	public void recargarQueries() {
		if (auxModulos != null && jcb_bases_datos.getSelectedIndex() != -1 && jcb_tablas.getSelectedIndex() != -1 && jcbModulos.getSelectedIndex() != -1 && jcbMenusByModule.getSelectedIndex() != -1) {
			String auxText = "";
			if (jchbDetail.isSelected()) {
				String vl_ParentDomain = createDomainName(String.valueOf(jcbMasterName.getSelectedItem()));
				String domainName = createDomainName(String.valueOf(jcb_tablas.getSelectedItem()));
				String vl_fkName = String.valueOf(jcbColumnForJoin.getSelectedItem());
				auxText = "INSERT INTO " + jcb_bases_datos.getSelectedItem().toString() + ".ms_tblAppTablesRel(`matr_master_domain_name`,`matr_target_domain_name`,`matr_attribute_name`) VALUES ( '"
						+ vl_ParentDomain + "','" + domainName + "','" + vl_fkName + "');\n";
			}
			// INSERT INTO `ms_tblAppTablesRel`(`matr_master_domain_name`,`matr_target_domain_name`,`matr_attribute_name`) VALUES ( 'Gatbl_IAcademicas','Gatbl_IAcadRevMinisterio','lIAcademica_id');
			auxText += generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "01");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "02");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "03");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "04");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "07");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "12");
			auxText += "\n" + generateInsertsForController(auxModulos.get(jcbModulos.getSelectedItem()), jcb_bases_datos.getSelectedItem().toString(), jcb_tablas.getSelectedItem().toString(), "13");
			jTextArea0.setText(auxText);
		}
	}

	public String getPKFromTable(String dataBase, String tableName) {
		Vector<Vector<String>> aux_datos = clsBase.Consulta_General("SELECT COLUMN_NAME FROM COLUMNS WHERE TABLE_SCHEMA = '" + dataBase + "' AND TABLE_NAME = '" + tableName
				+ "' AND COLUMN_KEY = 'PRI'");
		String pk = "";
		if (aux_datos.size() > 0) {
			pk = aux_datos.get(0).get(0);
		}
		return pk;
	}

	private void jcbMasterNameActionActionPerformed(ActionEvent event) {
		if (jcbMasterName.getSelectedIndex() > 0) {
			jTextField3.setText(getPKFromTable(jcb_bases_datos.getSelectedItem().toString(), jcbMasterName.getSelectedItem().toString()));
		}
	}

	private void jButton12ActionActionPerformed(ActionEvent event) {
		destinationDomainFolder = Leer_Archivo(destinationDomainFolder);
		if (destinationDomainFolder != null) {
			domainFolder.setText(destinationDomainFolder.getAbsolutePath());
			domainPackage.setText("com." + destinationDomainFolder.getName().toLowerCase() + ".common.domains");
		}
	}

	private void jcbColumnForJoinActionActionPerformed(ActionEvent event) {
		reloadAtributesList();
	}

	private void loadPreferencesValuesToForm(String dataBaseName) {
		txtDestinationFolder.setText(preferences.get(dataBaseName + "txtDestinationFolder", ""));
		applicationName.setText(preferences.get(dataBaseName + "applicationName", ""));
		constantsClassName.setText(preferences.get(dataBaseName + "constantsClassName", ""));
		jtfCommonsUtilsName.setText(preferences.get(dataBaseName + "utilsClassName", ""));
		jtfConstantsWebClassName.setText(preferences.get(dataBaseName + "constantsWebClassName", ""));
		jtfUtilsWebClassName.setText(preferences.get(dataBaseName + "utilsWebClassName", ""));
		dataSourceName.setText(preferences.get(dataBaseName + "dataSourceName", ""));
		domainFolder.setText(preferences.get(dataBaseName + "domainFolder", ""));
		domainPackage.setText(preferences.get(dataBaseName + "domainPackage", ""));
		ejbPackage.setText(preferences.get(dataBaseName + "ejbPackage", ""));
		webPackage.setText(preferences.get(dataBaseName + "webPackage", ""));
		webDestinationFolder.setText(preferences.get(dataBaseName + "webDestinationFolder", ""));
		destinationFolder = new File(txtDestinationFolder.getText());
		destinationDomainFolder = new File(domainFolder.getText());
		fileWebDestinationFolder = new File(webDestinationFolder.getText());
		moduleName = txtDestinationFolder.getText().toLowerCase();
		delegateName = txtDestinationFolder.getText();
		int moduleMatch = moduleName.indexOf("mod");
		if (moduleMatch != -1) {
			delegateName = delegateName.substring(moduleMatch + 3, moduleName.length());
			moduleName = moduleName.substring(moduleMatch + 3, moduleName.length());
			delegateName = delegateName.substring(0, 1).toUpperCase() + delegateName.substring(1, delegateName.length());
		} else {
			delegateName = "";
			moduleName = "";
		}
	}

	private void savePreferencesValuesToForm(String dataBaseName) {
		preferences.put(dataBaseName + "txtDestinationFolder", txtDestinationFolder.getText());
		preferences.put(dataBaseName + "applicationName", applicationName.getText());
		preferences.put(dataBaseName + "constantsClassName", constantsClassName.getText());
		preferences.put(dataBaseName + "utilsClassName", jtfCommonsUtilsName.getText());
		preferences.put(dataBaseName + "constantsWebClassName", jtfConstantsWebClassName.getText());
		preferences.put(dataBaseName + "utilsWebClassName", jtfUtilsWebClassName.getText());
		preferences.put(dataBaseName + "dataSourceName", dataSourceName.getText());
		preferences.put(dataBaseName + "domainFolder", domainFolder.getText());
		preferences.put(dataBaseName + "domainPackage", domainPackage.getText());
		preferences.put(dataBaseName + "ejbPackage", ejbPackage.getText());
		preferences.put(dataBaseName + "webPackage", webPackage.getText());
		preferences.put(dataBaseName + "webDestinationFolder", webDestinationFolder.getText());
	}

	private void jButton13ActionActionPerformed(ActionEvent event) {
		savePreferencesValuesToForm(String.valueOf(jcb_bases_datos.getSelectedItem()));
	}
}
