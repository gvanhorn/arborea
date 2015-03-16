import java.awt.Color;


public class Palette implements java.io.Serializable{
	Color green;
	Color orange;
	Color lightOrange;
	Color white;
	Color lightGreen;
	Color darkGreen;
	Color red;
	
	Palette(){
		green = new Color(0x2FBF22);
		orange = new Color(0xFABB32);
		lightOrange = new Color(0xFFCB59);
		white = Color.WHITE;
		lightGreen = new Color(0x1FED64);
		red = Color.red;
		darkGreen = new Color(0x24911A);
	}

}
