import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class FireworkShow {
	static int width = 1710, height = 1080;
	static double g = 0.02;
	static double airResistance = 0.02;
	static double dustResistance = 0.1;
	static ArrayList<Firework> fireworks = new ArrayList<Firework>();
	static int red = 0, green = 0, blue = 0;
	static int numStars;
	static int fireworkType = 1;
	static int frame = 0;
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setSize(width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Display display = new Display();
		f.add(display);
		f.addMouseListener(display);
		f.addKeyListener(display);
		display.t.start();
	}
	public static void update() {
		red = 0;
		green = 0;
		blue = 0;
		numStars = 0;
		for(Firework firework:fireworks) {
			firework.update();
		}
		fireworks.removeIf(firework -> (firework.exploded && firework.dust.size() == 0 && firework.stars.size() == 0));
		if(numStars > 0) {
			red /= 5000;
			green /= 5000;
			blue /= 5000;
			red = Math.min(255, red);
			green = Math.min(255, green);
			blue = Math.min(255, blue);
		}
	}
	public static void paint(Graphics g) {
		g.setColor(new Color(red, green, blue));
		g.fillRect(0, 0, width, height);
		for(Firework firework:fireworks) {
			firework.paint(g);
		}
	}
	public static void mousePressed(int x, int y) {
		fireworks.add(new Firework(x, Math.sqrt(height-y)/-1.5, randomColor(), fireworkType));
	}
	public static void keyPressed(char c) {
		if(c >= 48 && c <= 55) {
			fireworkType = c-48;
		}
	}
	public static Color randomColor() {
		return new Color(Color.HSBtoRGB((float)Math.random(), 0.7f, 1f));
	}
}
