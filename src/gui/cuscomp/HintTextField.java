package gui.cuscomp;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import util.Utility;

@SuppressWarnings("serial")
public class HintTextField extends JTextField implements FocusListener {

	  private final String hint;
	  private boolean showingHint;
	  private Color textColor = Utility.START_FRAME_FONT_COLOR;
	  private Color hintColor = Utility.START_FRAME_HINT_COLOR;

	  public HintTextField(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.showingHint = true;
	    this.setBorder(null);
	    this.setOpaque(false);
	    this.setForeground(hintColor);
	    super.addFocusListener(this);
	  }
	  
	  public void setColor(Color textColor, Color hintColor) {
		  this.textColor = textColor;
		  this.hintColor = hintColor;
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText("");
	      super.setForeground(textColor);
	      showingHint = false;
	    }
	  }
	  
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText(hint);
	      super.setForeground(hintColor);
	      showingHint = true;
	    }
	  }

	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }
	}