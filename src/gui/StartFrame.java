package gui;

import gui.cuscomp.HintTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.Utility;
import data.DiaryManager;
import data.event.TaskFinishEvent;
import data.event.UpdateProgressEvent;

//Welcome window with a progress bar showing data loading
//Dispose and open window of diary list after data is loaded
@SuppressWarnings("serial")
public class StartFrame extends JFrame implements Observer, ActionListener{

	private JPanel pContainer;
	private ImageIcon iiBackground;
	private JLabel lBackground;
	private JLabel lInfo;
	private String mark = "";
	private JLabel lUserName;
	private HintTextField tfUserName;
	private JLabel lPassword;
	private HintTextField tfPassword;
	private JLabel lConfirmPassword;
	private HintTextField tfConfirmPassword;
	private JButton bSignin;
	private JButton bRegister;

	private boolean registering;

	public StartFrame() {
		init();
	}

	private void init() {
		setTitle(Utility.TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(Utility.ICON).getImage());

		iiBackground = new ImageIcon(Utility.START_FRAME_BACKGROUND);
		lBackground = new JLabel(iiBackground);
		lBackground.setBounds(0, 0, iiBackground.getIconWidth(), iiBackground.getIconHeight());
		getLayeredPane().setLayout(null);
		getLayeredPane().add(lBackground, new Integer(Integer.MIN_VALUE));

		setSize(iiBackground.getIconWidth() + 6, iiBackground.getIconHeight() + 29);
		setResizable(false);

		pContainer = (JPanel) getContentPane();
		pContainer.setOpaque(false);
		pContainer.setLayout(null);

		lInfo = new JLabel();
		lInfo.setText(Utility.START_FRAME_INFO + mark);
		lInfo.setFont(new Font(Utility.TITLE_FONT, 0, 20));
		lInfo.setBounds(getWidth() - 350, getHeight() / 3, 250, 50);
		lInfo.setForeground(Color.WHITE);
		pContainer.add(lInfo);
	}

	private void initSignin() {
		lUserName = new JLabel();
		lUserName.setText("Username");
		lUserName.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		lUserName.setForeground(Utility.START_FRAME_FONT_COLOR);
		lUserName.setBounds(getWidth() - 450, getHeight() / 3, 100, 20);
		pContainer.add(lUserName);

		tfUserName = new HintTextField("input username ...");
		tfUserName.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		tfUserName.setColor(Utility.START_FRAME_FONT_COLOR, Utility.START_FRAME_HINT_COLOR);
		tfUserName.setBorder(BorderFactory.createLineBorder(Utility.START_FRAME_BORDER_COLOR));
		tfUserName.setBounds(getWidth() - 350, getHeight() / 3, 200, 20);
		pContainer.add(tfUserName);

		lPassword = new JLabel();
		lPassword.setText("Password");
		lPassword.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		lPassword.setForeground(Utility.START_FRAME_FONT_COLOR);
		lPassword.setBounds(getWidth() - 450, getHeight() / 3 + 30, 100, 20);
		pContainer.add(lPassword);

		tfPassword = new HintTextField("input password ...");
		tfPassword.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		tfPassword.setColor(Utility.START_FRAME_FONT_COLOR, Utility.START_FRAME_HINT_COLOR);
		tfPassword.setBorder(BorderFactory.createLineBorder(Utility.START_FRAME_BORDER_COLOR));
		tfPassword.setBounds(getWidth() - 350, getHeight() / 3 + 30, 200, 20);
		pContainer.add(tfPassword);

		lConfirmPassword = new JLabel();
		lConfirmPassword.setText("Confirm");
		lConfirmPassword.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		lConfirmPassword.setForeground(Utility.START_FRAME_FONT_COLOR);
		lConfirmPassword.setBounds(getWidth() - 450, getHeight() / 3 + 60, 100, 20);
		//pContainer.add(lConfirmPassword);

		tfConfirmPassword = new HintTextField("confirm password ...");
		tfConfirmPassword.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		tfConfirmPassword.setColor(Utility.START_FRAME_FONT_COLOR, Utility.START_FRAME_HINT_COLOR);
		tfConfirmPassword.setBorder(BorderFactory.createLineBorder(Utility.START_FRAME_BORDER_COLOR));
		tfConfirmPassword.setBounds(getWidth() - 350, getHeight() / 3 + 60, 200, 20);
		//pContainer.add(tfConfirmPassword);

		bSignin = new JButton(new ImageIcon(Utility.START_FRAME_SIGNIN));
		bSignin.setBorder(null);
		bSignin.setOpaque(false);
		bSignin.setBackground(new Color(0, 0, 0, 0));
		bSignin.setBounds(getWidth() - 240, getHeight() / 3 + 70, 40, 40);
		bSignin.addActionListener(this);
		pContainer.add(bSignin);

		bRegister = new JButton(new ImageIcon(Utility.START_FRAME_REGISTER));
		bRegister.setBorder(null);
		bRegister.setOpaque(false);
		bRegister.setBackground(new Color(0, 0, 0, 0));
		bRegister.setBounds(getWidth() - 190, getHeight() / 3 + 70, 40, 40);
		bRegister.addActionListener(this);
		pContainer.add(bRegister);

		registering = false;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof UpdateProgressEvent) {
			if (mark.length() == 3)
				mark = "";
			else
				mark += ".";
			lInfo.setText(Utility.START_FRAME_INFO + mark);
		} else if (o instanceof TaskFinishEvent) {
			pContainer.remove(lInfo);
			initSignin();
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bSignin) {
			if (registering) {
				pContainer.remove(lConfirmPassword);
				pContainer.remove(tfConfirmPassword);
				bSignin.setBounds(getWidth() - 240, getHeight() / 3 + 70, 40, 40);
				bRegister.setBounds(getWidth() - 190, getHeight() / 3 + 70, 40, 40);
				registering = false;
				repaint();
			} else {
				if (DiaryManager.getInstance().signin(tfUserName.getText(), tfPassword.getText())) {
					DiaryManager.getInstance().loadDiaryList();
					ListFrame window = new ListFrame();
					dispose();
					window.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Username or Password !");
				}
			}
		} else if (e.getSource() == bRegister) {
			if (!registering) {
				pContainer.add(lConfirmPassword);
				pContainer.add(tfConfirmPassword);
				bSignin.setBounds(getWidth() - 240, getHeight() / 3 + 100, 40, 40);
				bRegister.setBounds(getWidth() - 190, getHeight() / 3 + 100, 40, 40);
				registering = true;
				repaint();
			} else {
				if (!tfPassword.getText().equals(tfConfirmPassword.getText())) {
					JOptionPane.showMessageDialog(null, "Please Conform Password !");
				} else if (DiaryManager.getInstance().register(tfUserName.getText(), tfPassword.getText())) {
					DiaryManager.getInstance().loadDiaryList();
					ListFrame window = new ListFrame();
					dispose();
					window.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Username !");
				}
			}
		}
	}

	public static void main(String args[]) {
		new StartFrame().setVisible(true);
	}
}
