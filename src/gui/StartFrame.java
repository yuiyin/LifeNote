package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import event.TaskFinishEvent;
import event.UpdateProgressEvent;
import util.Utility;

//Welcome window with a progress bar showing data loading
//Dispose and open window of diary list after data is loaded
@SuppressWarnings("serial")
public class StartFrame extends JFrame implements Observer{

	private JPanel pContainer;
	private ImageIcon iiBackground;
	private JLabel lBackground;
	private JLabel lInfo;
	private String mark = "";
	
	public StartFrame() {
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

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof UpdateProgressEvent) {
			if (mark.length() == 3)
				mark = "";
			else
				mark += ".";
			lInfo.setText(Utility.START_FRAME_INFO + mark);
		} else if (o instanceof TaskFinishEvent) {
			dispose();
			ListFrame window = new ListFrame();
			window.setVisible(true);
		}
	}
}
