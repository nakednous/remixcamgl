package remixlab.remixcamgl;

import java.awt.event.KeyEvent;

import remixlab.remixcam.core.AbstractScene.Button;
import remixlab.remixcam.devices.*;

public class ClickBinding extends AbstractClickBinding {
	public ClickBinding(Button b) {
		super(b);
	}
	
	public ClickBinding(Integer m, Button b) {
		super (m,b);
	}
	
	public ClickBinding(Button b, Integer c) {
		super(b, c);
	}
	
	public ClickBinding(Integer m, Button b, Integer c) {
		super(m,b,c);
	} 

	/**
	 * Returns a textual description of this click shortcut.
	 *  
	 * @return description
	 */
	@Override
	public String description() {
		String description = new String();
		if(mask != 0)
			description += ClickBinding.getModifiersExText(mask) + " + ";
		switch (button) {
		case LEFT :
			description += "Button1";
			break;
		case MIDDLE :
			description += "Button2";
			break;
		case RIGHT :
			description += "Button3";
			break;		
		}
		if(numberOfClicks==1)
		  description += " + " + numberOfClicks.toString() + " click";
		else
			description += " + " + numberOfClicks.toString() + " clicks";
		return description;
	}

	/**
	 * Function that maps characters to virtual keys defined according to
	 * {@code java.awt.event.KeyEvent}.
	 */
	protected static int getVKey(char key) {
	  if(key == '0') return KeyEvent.VK_0;
	  if(key == '1') return KeyEvent.VK_1;
	  if(key == '2') return KeyEvent.VK_2;
	  if(key == '3') return KeyEvent.VK_3;
	  if(key == '4') return KeyEvent.VK_4;
	  if(key == '5') return KeyEvent.VK_5;
	  if(key == '6') return KeyEvent.VK_6;
	  if(key == '7') return KeyEvent.VK_7;
	  if(key == '8') return KeyEvent.VK_8;
	  if(key == '9') return KeyEvent.VK_9;		
	  if((key == 'a')||(key == 'A')) return KeyEvent.VK_A;
	  if((key == 'b')||(key == 'B')) return KeyEvent.VK_B;
	  if((key == 'c')||(key == 'C')) return KeyEvent.VK_C;
	  if((key == 'd')||(key == 'D')) return KeyEvent.VK_D;
	  if((key == 'e')||(key == 'E')) return KeyEvent.VK_E;
	  if((key == 'f')||(key == 'F')) return KeyEvent.VK_F;
	  if((key == 'g')||(key == 'G')) return KeyEvent.VK_G;
	  if((key == 'h')||(key == 'H')) return KeyEvent.VK_H;
	  if((key == 'i')||(key == 'I')) return KeyEvent.VK_I;
	  if((key == 'j')||(key == 'J')) return KeyEvent.VK_J;
	  if((key == 'k')||(key == 'K')) return KeyEvent.VK_K;
	  if((key == 'l')||(key == 'L')) return KeyEvent.VK_L;
	  if((key == 'm')||(key == 'M')) return KeyEvent.VK_M;
	  if((key == 'n')||(key == 'N')) return KeyEvent.VK_N;
	  if((key == 'o')||(key == 'O')) return KeyEvent.VK_O;
	  if((key == 'p')||(key == 'P')) return KeyEvent.VK_P;
	  if((key == 'q')||(key == 'Q')) return KeyEvent.VK_Q;
	  if((key == 'r')||(key == 'R')) return KeyEvent.VK_R;
	  if((key == 's')||(key == 'S')) return KeyEvent.VK_S;
	  if((key == 't')||(key == 'T')) return KeyEvent.VK_T;
	  if((key == 'u')||(key == 'U')) return KeyEvent.VK_U;
	  if((key == 'v')||(key == 'V')) return KeyEvent.VK_V;
	  if((key == 'w')||(key == 'W')) return KeyEvent.VK_W;
	  if((key == 'x')||(key == 'X')) return KeyEvent.VK_X;
	  if((key == 'y')||(key == 'Y')) return KeyEvent.VK_Y;
	  if((key == 'z')||(key == 'Z')) return KeyEvent.VK_Z;
	  return -1;
	}

	/**
	 * Wrapper function that simply returns
	 * {@code java.awt.event.KeyEvent.getKeyText(key)}.
	 */
	public static String getKeyText(int key) {
		return KeyEvent.getKeyText(key);
	}

	/**
	 * Wrapper function that simply returns
	 * {@code java.awt.event.KeyEvent.getModifiersExText(mask)}.
	 */
	public static String getModifiersExText(int mask) {
		return KeyEvent.getModifiersExText(mask);
	}
}
