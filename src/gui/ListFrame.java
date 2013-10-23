package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.SortType;
import util.Utility;
import data.DiaryManager;
import data.event.ListClickEvent;
import data.model.DetailedDiary;
import data.model.SimpleDiary;
import gui.cuscomp.ListElementPanel;
import gui.cusui.CustomScrollBarUI;

@SuppressWarnings("serial")
public class ListFrame extends JFrame implements ActionListener, Observer{

	private JPanel pContainer;
	private ImageIcon iiBackground;
	private JLabel lBackground;
	private JScrollPane spDiaryList;
	private JButton bCreate;
	private JButton bSearch;
	private JButton bExit;
	private JTextField tfTitle;
	private JTextField tfTag;
	private JButton bStartTime;
	private JButton bEndTime;
	
	private boolean searching = false;

	public ListFrame() {
		setTitle(Utility.TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(Utility.ICON).getImage());

		iiBackground = new ImageIcon(Utility.LIST_FRAME_BACKGROUND);
		lBackground = new JLabel(iiBackground);
		lBackground.setBounds(0, 0, iiBackground.getIconWidth(), iiBackground.getIconHeight());
		getLayeredPane().setLayout(null);
		getLayeredPane().add(lBackground, new Integer(Integer.MIN_VALUE));

		setSize(iiBackground.getIconWidth() + 6, iiBackground.getIconHeight() + 29);
		setResizable(false);

		pContainer = (JPanel) getContentPane();
		pContainer.setOpaque(false);
		pContainer.setLayout(null);

		initDiaryListUI();
		pContainer.add(spDiaryList);
		
		bCreate = new JButton(new ImageIcon(Utility.LIST_FRAME_CREATE));
		bCreate.setBounds(850, 80, 40, 40);
		setStyle(bCreate);
		pContainer.add(bCreate);
		bCreate.addActionListener(this);
		
		bSearch = new JButton(new ImageIcon(Utility.LIST_FRAME_SEARCH));
		bSearch.setBounds(850, 180, 40, 40);
		setStyle(bSearch);
		pContainer.add(bSearch);
		bSearch.addActionListener(this);
		
		bExit = new JButton(new ImageIcon(Utility.LIST_FRAME_EXIT));
		bExit.setBounds(850, 280, 40, 40);
		setStyle(bExit);
		pContainer.add(bExit);
		bExit.addActionListener(this);
		
		tfTitle = new JTextField("Title");
		tfTitle.setBounds(770, 200, 200, 30);
		tfTitle.setHorizontalAlignment(JTextField.CENTER);
		setStyle(tfTitle);
		
		tfTag = new JTextField("Tag");
		tfTag.setBounds(770, 250, 200, 30);
		tfTag.setHorizontalAlignment(JTextField.CENTER);
		setStyle(tfTag);
		
		bStartTime = new JButton("NULL");
		bStartTime.setBounds(770, 300, 200, 30);
		bStartTime.setHorizontalAlignment(JButton.CENTER);
		setStyle(bStartTime);
		bStartTime.addActionListener(this);
		
		bEndTime = new JButton("NULL");
		bEndTime.setBounds(770, 350, 200, 30);
		bEndTime.setHorizontalAlignment(JButton.CENTER);
		setStyle(bEndTime);
		bEndTime.addActionListener(this);
	}

	private void initDiaryListUI() {
		JPanel p = new JPanel();
		///////
		//p.setLayout(new GridLayout(10, 2, 10, 10));
		//for (int i = 0; i < 20; i ++) 
		//	p.add(new ListElementPanel(new SimpleDiary(111, "这是一个测试条目", "测试", 201310102155L, true)));
		///////
		ArrayList<SimpleDiary> diaryList = DiaryManager.getInstance().getDiaryList();
		int line = (diaryList.size() + 1) / 2;
		p.setLayout(new GridLayout(line, 2, 10, 10));
		for (int i = 0; i < diaryList.size(); i ++) {
			ListElementPanel lep = new ListElementPanel(diaryList.get(i));
			lep.addObserver(this);
			p.add(lep);
		}
		p.setOpaque(false);
		JPanel pp = new JPanel(new FlowLayout());
		pp.setOpaque(false);
		pp.add(p);
		JLabel l = new JLabel();
		l.setOpaque(false);
		l.setPreferredSize(new Dimension(5, 5));
		pp.add(p);
		spDiaryList = new JScrollPane(pp);
		spDiaryList.setBounds(100, 60, 637, 500);
		spDiaryList.setBorder(null);
		spDiaryList.setOpaque(false);
		spDiaryList.getViewport().setOpaque(false);
		JScrollBar sb = new JScrollBar();
		sb.setUI(new CustomScrollBarUI());
		spDiaryList.setVerticalScrollBar(sb);
	}
	
	private void setStyle(JComponent c) {
		c.setOpaque(false);
		c.setFont(new Font(Utility.LIST_FRAME_FONT, 0, 20));
		c.setForeground(Utility.LIST_FRAME_FONT_COLOR);
		c.setBackground(new Color(0, 0, 0, 0));
		c.setBorder(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bCreate) {
			dispose();
			DetailFrame window = new DetailFrame(new DetailedDiary(), true);
			window.setVisible(true);
		} else if (e.getSource() == bSearch) {
			if (!searching) {
				pContainer.remove(bCreate);
				pContainer.remove(bExit);
				bSearch.setLocation(bSearch.getLocation().x, bSearch.getLocation().y - 100);
				pContainer.add(tfTitle);
				pContainer.add(tfTag);
				pContainer.add(bStartTime);
				pContainer.add(bEndTime);
				searching = true;
			} else {
				try {
					assert tfTitle.getText().length() <= 20 : "Length of title exceed 20";
					assert tfTag.getText().length() <= 10 : "Length of tag exceed 10";
				} catch (AssertionError err) {
					err.printStackTrace();
				}
				DiaryManager.getInstance().loadDiaryList(tfTitle.getText(), tfTag.getText(), Utility.restoreFormattedTime(bStartTime.getText()), Utility.restoreFormattedTime(bEndTime.getText()), SortType.SORT_BY_TIME);
				dispose();
				ListFrame window = new ListFrame();
				window.setVisible(true);
			}
		} else if (e.getSource() == bExit) {
			System.exit(0);
		} else if (e.getSource() == bStartTime) {
			String t;
			if (bStartTime.getText().equals("NULL"))
				t = JOptionPane.showInputDialog("Time", Utility.formatTime(Utility.getTime()));
			else
				t = JOptionPane.showInputDialog("Time", bStartTime.getText());
			bStartTime.setText(t);
		} else if (e.getSource() == bEndTime) {
			String t;
			if (bEndTime.getText().equals("NULL"))
				t = JOptionPane.showInputDialog("Time", Utility.formatTime(Utility.getTime()));
			else
				t = JOptionPane.showInputDialog("Time", bEndTime.getText());
			if (!t.equals(""))
				bEndTime.setText(t);
		}
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ListClickEvent) {
			SimpleDiary sd = (SimpleDiary) arg;
			DiaryManager.getInstance().loadDiary(sd.getId());
			dispose();
			DetailFrame window = new DetailFrame(DiaryManager.getInstance().getDiary(), false);
			window.setVisible(true);
		}
	}
}
