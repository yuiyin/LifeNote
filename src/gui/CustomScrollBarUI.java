package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {

	protected JButton createZeroButton() {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0,0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}


	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(new Color(52, 73, 94));
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.translate(thumbBounds.x, thumbBounds.y);
		g.setColor(new Color(44, 62, 80));
		g.fillRect(0, 0, thumbBounds.width, thumbBounds.height);
	}
}
