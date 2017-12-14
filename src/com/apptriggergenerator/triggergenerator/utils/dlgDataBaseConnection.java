package com.apptriggergenerator.triggergenerator.utils;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.apptriggergenerator.listcontrollers.utils.ConfigManager;

//VS4E -- DO NOT REMOVE THIS LINE!
public class dlgDataBaseConnection extends JDialog {
	private static final Logger LOGGER = ConfigManager.getLogger(dlgDataBaseConnection.class);
	private Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private JButton jButton1;
	private JButton jButton0;
	private JTextField jTextField2;
	private JTextField jTextField0;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPasswordField jPasswordField0;

	public dlgDataBaseConnection() {
		initComponents();
	}

	public dlgDataBaseConnection(Frame parent) {
		super(parent);
		initComponents();
	}

	public dlgDataBaseConnection(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public dlgDataBaseConnection(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public dlgDataBaseConnection(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public dlgDataBaseConnection(Frame parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public dlgDataBaseConnection(Dialog parent) {
		super(parent);
		initComponents();
	}

	public dlgDataBaseConnection(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public dlgDataBaseConnection(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public dlgDataBaseConnection(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public dlgDataBaseConnection(Dialog parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public dlgDataBaseConnection(Window parent) {
		super(parent);
		initComponents();
	}

	public dlgDataBaseConnection(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public dlgDataBaseConnection(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public dlgDataBaseConnection(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public dlgDataBaseConnection(Window parent, String title, ModalityType modalityType, GraphicsConfiguration arg) {
		super(parent, title, modalityType, arg);
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setResizable(false);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJButton1(), new Constraints(new Leading(246, 10, 10), new Leading(117, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(152, 12, 12), new Leading(117, 10, 10)));
		add(getJLabel0(), new Constraints(new Leading(85, 81, 10, 10), new Leading(42, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(85, 81, 10, 10), new Leading(64, 10, 10)));
		add(getJTextField2(), new Constraints(new Leading(171, 193, 10, 10), new Leading(64, 16, 10, 10)));
		add(getJTextField0(), new Constraints(new Leading(171, 193, 10, 10), new Leading(42, 16, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(85, 81, 10, 10), new Leading(86, 10, 10)));
		add(getJPasswordField0(), new Constraints(new Leading(171, 193, 10, 10), new Leading(86, 16, 10, 10)));
		setSize(475, 233);
	}

	private JPasswordField getJPasswordField0() {
		if (jPasswordField0 == null) {
			jPasswordField0 = new JPasswordField();
			jPasswordField0.setText(preferences.get("pwd", "pwd"));
			jPasswordField0.setEchoChar('•');
			jPasswordField0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jPasswordField0ActionActionPerformed(event);
				}
			});
		}
		return jPasswordField0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("Password");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("Usuario");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel0.setText("Host");
		}
		return jLabel0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText(preferences.get("host", "192.168.2.111"));
		}
		return jTextField0;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setText(preferences.get("user", "user"));
		}
		return jTextField2;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Conectar");
			jButton0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Salir");
			jButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		preferences.put("host", jTextField0.getText());
		preferences.put("user", jTextField2.getText());
		preferences.put("pwd", jPasswordField0.getText());
		frmMapirisoftGenerator frame = new frmMapirisoftGenerator(jTextField0.getText(), jTextField2.getText(), jPasswordField0.getText());
		frame.setDefaultCloseOperation(frmMapirisoftGenerator.EXIT_ON_CLOSE);
		frame.setTitle("frmMapirisoftGenerator");
		frame.getContentPane().setPreferredSize(frame.getSize());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.dispose();
	}

	public static void main(String arg[]) {
		LOGGER.info("Initializing Application ...");
		dlgDataBaseConnection dlgDataBaseConnection = new dlgDataBaseConnection();
		dlgDataBaseConnection.setLocationRelativeTo(null);
		dlgDataBaseConnection.setVisible(true);
	}

	private void jButton1ActionActionPerformed(ActionEvent event) {
		System.exit(0);
	}

	private void jPasswordField0ActionActionPerformed(ActionEvent event) {
		jButton0ActionActionPerformed(event);
	}
}
