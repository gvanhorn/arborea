import java.awt.Color;


@SuppressWarnings("serial")
/*
 * This class defines all the colors used for painting the screen.
 */
public class Palette implements java.io.Serializable{
	Color green;
	Color orange;
	Color lightOrange;
	Color white;
	Color lightGreen;
	Color darkGreen;
	Color red;
	Color bloodRed;
	Color gold;
	
	Palette(){
		green = new Color(0x2FBF22);
		orange = new Color(0xFABB32);
		lightOrange = new Color(0xFFCB59);
		white = Color.WHITE;
		lightGreen = new Color(0x1FED64);
		red = Color.red;
		darkGreen = new Color(0x24911A);
		bloodRed = new Color(0x8A0707);
		gold = new Color(0xF5E727);
	}

}
