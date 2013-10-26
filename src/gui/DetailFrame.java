package gui;

import gui.cuscomp.HintTextField;
import gui.cusui.CustomScrollBarUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;

import util.Utility;
import data.DiaryManager;
import data.VoiceManager;
import data.model.DetailedDiary;

@SuppressWarnings("serial")
public class DetailFrame extends JFrame implements ActionListener {

	private JPanel pContainer;
	private ImageIcon iiBackground;
	private JLabel lBackground;
	private JTextField tfTitle;
	private JLabel lTime;
	private JButton bTime;
	private JLabel lTag;
	private JTextField tfTag;
	private JButton bRecord;
	private JButton bPlay;
	private JButton bStop;
	private JTextPane tpContent;
	private JScrollPane spContent;
	private JButton bEdit;
	private JButton bDelete;
	private JButton bBack;
	private JButton bInsert;
	private JButton bVoiceoff;
	private JButton bSave;
	private JButton bCancel;
	private JButton bShare;
	private JButton bPrivate;
	private JScrollPane spComment;
	private HintTextField tfComment;
	private JButton bComment;

	private DetailedDiary dd;
	private boolean voiceWorking = false;
	private boolean isCreate;

	public DetailFrame(DetailedDiary dd, boolean editable, boolean isCreate) {
		this.dd = dd;
		this.isCreate = isCreate;

		setTitle(Utility.TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(Utility.ICON).getImage());

		iiBackground = new ImageIcon(Utility.DETAIL_FRAME_BACKGROUND);
		lBackground = new JLabel(iiBackground);
		lBackground.setBounds(0, 0, iiBackground.getIconWidth(), iiBackground.getIconHeight());
		getLayeredPane().setLayout(null);
		getLayeredPane().add(lBackground, new Integer(Integer.MIN_VALUE));

		setSize(iiBackground.getIconWidth() + 6, iiBackground.getIconHeight() + 29);
		setResizable(false);

		pContainer = (JPanel) getContentPane();
		pContainer.setOpaque(false);
		pContainer.setLayout(null);

		tfTitle = new JTextField(dd.getTitle());
		tfTitle.setBounds(100, 60, 620, 40);
		setStyle(tfTitle);
		tfTitle.setFont(new Font(Utility.DETAIL_FRAME_FONT, 0, 36));
		pContainer.add(tfTitle);

		lTime = new JLabel(dd.getTimeString());
		lTime.setBounds(100, 110, 300, 24);
		lTime.setHorizontalAlignment(JButton.LEFT);
		setStyle(lTime);

		bTime = new JButton(dd.getTimeString());
		bTime.setBounds(100, 110, 260, 24);
		bTime.setHorizontalAlignment(JButton.LEFT);
		setStyle(bTime);
		bTime.addActionListener(this);

		lTag = new JLabel("From " + dd.getUsername() + "(" + dd.getShareOrPrivate() + ")    Tag :");
		lTag.setBounds(101, 135, 264, 26);
		setStyle(lTag);
		pContainer.add(lTag);

		tfTag = new JTextField(dd.getTag());
		tfTag.setBounds(365, 135, 135, 26);
		setStyle(tfTag);
		pContainer.add(tfTag);

		tpContent = new JTextPane();
		setStyle(tpContent);
		setTextPane(dd.getContentURL());
		spContent = new JScrollPane(tpContent);
		spContent.setBounds(100, 190, 630, 300);
		spContent.setOpaque(false);
		spContent.getViewport().setOpaque(false);
		spContent.setBorder(null);
		spContent.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		spContent.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		pContainer.add(spContent);

		bRecord = new JButton(new ImageIcon(Utility.DETAIL_FRAME_RECORD));
		bRecord.setBounds(669, 110, 51, 51);
		setStyle(bRecord);
		bRecord.addActionListener(this);

		bPlay = new JButton(new ImageIcon(Utility.DETAIL_FRAME_PLAY));
		bPlay.setBounds(669, 110, 51, 51);
		setStyle(bPlay);
		bPlay.addActionListener(this);

		bStop = new JButton(new ImageIcon(Utility.DETAIL_FRAME_STOP));
		bStop.setBounds(669, 110, 51, 51);
		setStyle(bStop);
		bStop.addActionListener(this);

		bEdit = new JButton(new ImageIcon(Utility.DETAIL_FRAME_EDIT));
		bEdit.setBounds(850, 80, 40, 40);
		setStyle(bEdit);
		bEdit.addActionListener(this);

		bDelete = new JButton(new ImageIcon(Utility.DETAIL_FRAME_DELETE));
		bDelete.setBounds(850, 180, 40, 40);
		setStyle(bDelete);
		bDelete.addActionListener(this);

		bBack = new JButton(new ImageIcon(Utility.DETAIL_FRAME_BACK));
		bBack.setBounds(850, 280, 40, 40);
		setStyle(bBack);
		bBack.addActionListener(this);

		bInsert = new JButton(new ImageIcon(Utility.DETAIL_FRAME_INSERT));
		bInsert.setBounds(850, 80, 40, 40);
		setStyle(bInsert);
		bInsert.addActionListener(this);

		bVoiceoff = new JButton(new ImageIcon(Utility.DETAIL_FRAME_VOICEOFF));
		bVoiceoff.setBounds(850, 180, 40, 40);
		setStyle(bVoiceoff);
		bVoiceoff.addActionListener(this);

		bSave = new JButton(new ImageIcon(Utility.DETAIL_FRAME_SAVE));
		bSave.setBounds(850, 280, 40, 40);
		setStyle(bSave);
		bSave.addActionListener(this);

		bCancel = new JButton(new ImageIcon(Utility.DETAIL_FRAME_CANCEL));
		bCancel.setBounds(850, 380, 40, 40);
		setStyle(bCancel);
		bCancel.addActionListener(this);

		bShare = new JButton(new ImageIcon(Utility.DETAIL_FRAME_SHARE));
		bShare.setBounds(609, 110, 51, 51);
		setStyle(bShare);
		bShare.addActionListener(this);

		bPrivate = new JButton(new ImageIcon(Utility.DETAIL_FRAME_PRIVATE));
		bPrivate.setBounds(609, 110, 51, 51);
		setStyle(bPrivate);
		bPrivate.addActionListener(this);

		JTextArea taComment = new JTextArea(dd.getComment());
		taComment.setEditable(false);
		taComment.setFont(new Font(Utility.DETAIL_FRAME_FONT, 0, 16));
		taComment.setForeground(Utility.DETAIL_FRAME_FONT_COLOR);
		taComment.setOpaque(false);
		spComment = new JScrollPane(taComment);
		spComment.setBounds(100, 495, 630, 40);
		spComment.setOpaque(false);
		spComment.getViewport().setOpaque(false);
		spComment.setBorder(null);
		spComment.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		spComment.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		pContainer.add(spComment);

		tfComment = new HintTextField("comment here ...");
		tfComment.setFont(new Font(Utility.TITLE_CONTENT_FONT, 0, 16));
		tfComment.setColor(Utility.START_FRAME_FONT_COLOR, Utility.START_FRAME_HINT_COLOR);
		tfComment.setBorder(BorderFactory.createLineBorder(Utility.START_FRAME_BORDER_COLOR));
		tfComment.setBounds(100, 540, 500, 20);
		pContainer.add(tfComment);

		bComment = new JButton("Comment");
		bComment.setBounds(610, 540, 120, 20);
		setStyle(bComment);
		bComment.setFont(new Font(Utility.DETAIL_FRAME_FONT, 0, 16));
		bComment.addActionListener(this);
		pContainer.add(bComment);

		setEditable(editable);
	}

	private void refresh(boolean editable) {
		tfTitle.setText(dd.getTitle());
		tfTag.setText(dd.getTag());
		bTime.setText(dd.getTimeString());
		lTime.setText(dd.getTimeString());
		setTextPane(dd.getContentURL());
		setEditable(editable);
	}

	private void setStyle(JComponent c) {
		c.setOpaque(false);
		c.setFont(new Font(Utility.DETAIL_FRAME_FONT, 0, 20));
		c.setForeground(Utility.DETAIL_FRAME_FONT_COLOR);
		c.setBackground(new Color(0, 0, 0, 0));
		c.setBorder(null);
	}

	private void setEditable(boolean editable) {
		tfTitle.setEditable(editable);
		tfTag.setEditable(editable);
		tpContent.setEditable(editable);
		if (editable) {
			pContainer.remove(lTime);
			pContainer.add(bTime);
			if (dd.getVoiceURL() == null || dd.getVoiceURL().equals("")) {
				pContainer.add(bRecord);
				pContainer.remove(bPlay);
			} else {
				pContainer.remove(bRecord);
				pContainer.add(bPlay);
			}
			pContainer.add(bInsert);
			pContainer.add(bVoiceoff);
			pContainer.add(bSave);
			pContainer.add(bCancel);
			pContainer.remove(bEdit);
			pContainer.remove(bDelete);
			pContainer.remove(bBack);
			if (dd.getShareOrPrivate().equals("share")) {
				pContainer.add(bPrivate);
			} else if (dd.getShareOrPrivate().equals("private")) {
				pContainer.add(bShare);
			}
			pContainer.remove(spComment);
			pContainer.remove(tfComment);
			pContainer.remove(bComment);
			spContent.setBounds(100, 190, 630, 370);
		} else {
			pContainer.add(lTime);
			pContainer.remove(bTime);
			if (dd.getVoiceURL() == null || dd.getVoiceURL().equals("")) {
				pContainer.remove(bRecord);
				pContainer.remove(bPlay);
			} else {
				pContainer.remove(bRecord);
				pContainer.add(bPlay);
			}
			pContainer.remove(bInsert);
			pContainer.remove(bVoiceoff);
			pContainer.remove(bSave);
			pContainer.remove(bCancel);
			pContainer.add(bEdit);
			pContainer.add(bDelete);
			pContainer.add(bBack);
			pContainer.remove(bShare);
			pContainer.remove(bPrivate);
			if (!dd.getUsername().equals(DiaryManager.getInstance().getUser().getUsername())) {
				pContainer.remove(bEdit);
				pContainer.remove(bDelete);
				bBack.setBounds(850, 80, 40, 40);
			}
			pContainer.add(spComment);
			pContainer.add(tfComment);
			pContainer.add(bComment);
			spContent.setBounds(100, 190, 630, 300);
		}
		repaint();
	}

	private void setTextPane(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			ObjectInputStream ois = new ObjectInputStream(fis);
			StyledDocument doc = (StyledDocument) ois.readObject();
			ois.close();
			tpContent.setStyledDocument (doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTextPane(String url) {
		try {
			FileOutputStream fos = new FileOutputStream(url);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tpContent.getStyledDocument());
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (voiceWorking && o != bTime && o != bStop && o != bInsert) {
			VoiceManager.getInstance().stopVoice();
			pContainer.remove(bStop);
			pContainer.add(bPlay);
			voiceWorking = false;
		}
		if (o == bTime) {
			String t = JOptionPane.showInputDialog("Time", bTime.getText());
			if (!t.equals("")) {
				dd.setTime(Utility.restoreFormattedTime(t));
				bTime.setText(t);
				lTime.setText(t);
			}
		} else if (o == bRecord) {
			String url = "./diary/voice/" + dd.getId();
			dd.setVoiceURL(url);
			pContainer.remove(bRecord);
			pContainer.add(bStop);
			voiceWorking = true;
			VoiceManager.getInstance().recordVoice(url);
		} else if (o == bPlay) {
			String url = dd.getVoiceURL();
			pContainer.remove(bPlay);
			pContainer.add(bStop);
			voiceWorking = true;
			VoiceManager.getInstance().playVoice(url);
		} else if (o == bStop) {
			VoiceManager.getInstance().stopVoice();
			pContainer.remove(bStop);
			pContainer.add(bPlay);
			voiceWorking = false;
		} else if (o == bEdit) {
			setEditable(true);
		} else if (o == bDelete) {
			DiaryManager.getInstance().deleteDiary(dd);
			DiaryManager.getInstance().loadDiaryList();
			dispose();
			ListFrame window = new ListFrame();
			window.setVisible(true);
		} else if (o == bBack) {
			DiaryManager.getInstance().loadDiaryList();
			dispose();
			ListFrame window = new ListFrame();
			window.setVisible(true);
		} else if (o == bInsert) {
			JFileChooser fc = new JFileChooser(".");
			FileNameExtensionFilter fef = new FileNameExtensionFilter(null, "jpg", "png");
			fc.setFileFilter(fef);
			int option = fc.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				tpContent.insertIcon(new ImageIcon(path));
			}
		} else if (o == bVoiceoff) {
			dd.setVoiceURL("");
			pContainer.remove(bPlay);
			pContainer.add(bRecord);
		} else if (o == bSave) {
			dd.setTitle(tfTitle.getText());
			dd.setTag(tfTag.getText());
			saveTextPane(dd.getContentURL());
			if (isCreate) {
				DiaryManager.getInstance().saveCreate(dd);
			} else {
				DiaryManager.getInstance().saveDiary(dd);
			}
			refresh(false);
		} else if (o == bCancel) {
			DiaryManager.getInstance().loadDiary(dd.getId());
			dd = DiaryManager.getInstance().getDiary();
			if (dd == null) {
				DiaryManager.getInstance().loadDiaryList();
				dispose();
				ListFrame window = new ListFrame();
				window.setVisible(true);
			} else {				
				refresh(false);
			}
		} else if (o == bShare) {
			dd.setShareOrPrivate("share");
			pContainer.remove(bShare);
			pContainer.add(bPrivate);
			lTag.setText("From " + dd.getUsername() + "(" + dd.getShareOrPrivate() + ")    Tag :");
		} else if (o == bPrivate) {
			dd.setShareOrPrivate("private");
			pContainer.remove(bPrivate);
			pContainer.add(bShare);
			lTag.setText("From " + dd.getUsername() + "(" + dd.getShareOrPrivate() + ")    Tag :");
		} else if (o == bComment) {
			String comment = tfComment.getText();
			comment = DiaryManager.getInstance().getUser().getUsername() + " : " + comment + "\n";
			dd.setComment(dd.getComment() + comment);
			DiaryManager.getInstance().saveComment(dd);
		}
		repaint();
	}

	public static void main(String args[]) {

	}
}
