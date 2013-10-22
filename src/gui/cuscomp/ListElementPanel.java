package gui.cuscomp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.Utility;
import data.event.ListClickEvent;
import data.model.SimpleDiary;

@SuppressWarnings("serial")
public class ListElementPanel extends JPanel implements MouseListener {

	private JLabel lTime;
	private JLabel lTitle;
	private JLabel lTag;
	private JLabel lHasVoice;
	
	private SimpleDiary sd;
	private ListClickEvent listClickEvent = new ListClickEvent();

	public ListElementPanel(SimpleDiary sd) {
		this.sd = sd;
		
		setSize(300, 120);
		setPreferredSize(new Dimension(300, 120));
		setLayout(null);
		setBackground(Utility.LIST_ELEMENT_BACKGROUND_COLOR);
		addMouseListener(this);

		lTime = new JLabel(sd.getTimeString());
		lTime.setBounds(10, 10, 280, 20);
		lTime.setFont(new Font(Utility.LIST_ELEMENT_FONT, 0, 16));
		//lTime.setBackground(new Color(52, 73, 94));
		add(lTime);

		lTitle = new JLabel(sd.getTitle());
		lTitle.setBounds(10, 35, 280, 40);
		lTitle.setFont(new Font(Utility.LIST_ELEMENT_FONT, 0, 24));
		//lTitle.setBackground(new Color(52, 73, 94));
		add(lTitle);

		lTag = new JLabel(sd.getTag());
		lTag.setBounds(10, 90, 200, 20);
		lTag.setFont(new Font(Utility.LIST_ELEMENT_FONT, 0, 16));
		//lTag.setBackground(new Color(52, 73, 94));
		add(lTag);

		if (sd.isHasVoice()) {
			lHasVoice = new JLabel(new ImageIcon(Utility.LIST_ELEMENT_HAS_VOICE));
			lHasVoice.setBounds(270, 90, 20, 20);
			add(lHasVoice);
		}
	}

	public static void main(String args[]) {
		JFrame f = new JFrame("Test");
		f.setSize(800, 600);
		f.setLayout(null);
		//f.getContentPane().add(new ListElementPanel(new SimpleDiary("", "这是一个测试条目", "测试", "2013年10月10日 21:55", true, true)));
		JPanel p = new JPanel();
		//p.setSize(610, 1290);
		p.setLayout(new GridLayout(10, 2, 10, 10));
		for (int i = 0; i < 20; i ++)
			p.add(new ListElementPanel(new SimpleDiary(111, "这是一个测试条目", "测试", 201310102155L, true)));
		JPanel pp = new JPanel(new FlowLayout());
		//pp.setBounds(0, 0, 610, 1290);
		pp.add(p);
		JLabel l = new JLabel();
		l.setOpaque(false);
		l.setPreferredSize(new Dimension(10, 10));
		pp.add(l);
		JScrollPane sp = new JScrollPane(pp);
		sp.setBounds(10, 10, 650, 500);
		f.add(sp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void addObserver(Observer observer) {
		listClickEvent.addObserver(observer);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		setBackground(new Color(189, 195, 199));
		listClickEvent.changed(sd);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
