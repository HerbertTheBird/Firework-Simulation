import java.awt.*;
import java.util.*;
public class Star {
	double x, y, vx, vy;
	Color c;
	double brightness;
	double dimRate;
	boolean exist = true;
	int numDust;
	int size;
	ArrayList<Dust> dust = new ArrayList<Dust>();
	ArrayList<Star> secondary = new ArrayList<Star>();
	int type;
	double airResistance;
	double flicker;
	int explode = -1;
	public Star(double x, double y, double vx, double vy, Color c, int type) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		brightness = 4;
		this.c = c;
		this.type = type;
		if(type == 1) {
			numDust = 0;
			dimRate = 0.015;
			size = 4;
			airResistance = 0.04;
			flicker = 0.9;
		}
		if(type == 2) {
			numDust = 20;
			dimRate = 0.015;
			size = 2;
			airResistance = 0.04;
			flicker = 0.9;
		}
		if(type == 3) {
			numDust = 50;
			dimRate = 0.0075;
			size = 4;
			airResistance = 0.02;
			flicker = 1;
		}
		if(type == 4) {
			numDust = 10;
			dimRate = 0.01;
			size = 0;
			airResistance = 0.02;
			flicker = 0.9;
		}
		if(type == 5) {
			numDust = 2;
			dimRate = 0.015;
			size = 6;
			airResistance = 0.04;
			flicker = 0.5;
		}
		if(type == 6) {
			explode = 40;
			numDust = 2;
			dimRate = 0.02;
			size = 7;
			airResistance = 0.04;
			flicker = 1;
		}
		if(type == 7) {
			numDust = 2;
			dimRate = 0.02;
			size = 2;
			airResistance = 0.04;
			flicker = 1;
		}
	}
	public Color percievedColor(int alpha) {
		int red = Math.min(255, (int)(c.getRed()*brightness));
		int green = Math.min(255, (int)(c.getGreen()*brightness));
		int blue = Math.min(255, (int)(c.getBlue()*brightness));
		return new Color(red, green, blue, alpha);
	}
	public void paint(Graphics g) {
		for(Dust d:dust) {
			d.paint(g);
		}
		for(Star s:secondary) {
			s.paint(g);
		}
		if(exist) {
			if(Math.random() < flicker) {
				for(int i = 1; i <= size; i++) {
					g.setColor(percievedColor((size+1-i)*255/size));
					g.fillOval((int)x-i, (int)y-i, i*2, i*2);
				}
			}
			else {
				double flickerSize = (type == 5)?0:size*.9;
				for(int i = 1; i <= flickerSize; i++) {
					g.setColor(percievedColor(((int)(flickerSize)+1-i)*255/size));
					g.fillOval((int)x-i, (int)y-i, i*2, i*2);
				}
			}
		}
	}
	public void update() {
		if(exist) {
			Color percieved = percievedColor(0);
			FireworkShow.numStars++;
			FireworkShow.red += percieved.getRed();
			FireworkShow.green += percieved.getGreen();
			FireworkShow.blue += percieved.getBlue();
			for(int i = 0; i < numDust; i++) {
				x += vx/numDust;
				y += vy/numDust;
				dust.add(Dust.generateDust(this));
			}
			if(numDust == 0) {
				x += vx;
				y += vy;
			}
			vy += FireworkShow.g;
			vx *= (1-airResistance);
			vy *= (1-airResistance);
			brightness *= (1-dimRate);
			if(brightness < Math.random()*.75) {
				exist = false;
			}
			
			if(explode != -1) {
				explode --;
				if(explode == 0) {
					exist = false;
					double startTheta = Math.random()*Math.PI/2;
					for(int i = 0; i < 4; i++) {
						double theta = Math.PI*i/2+startTheta;
						double vx = Math.sin(theta)*2;
						double vy = Math.cos(theta)*2;
						
						secondary.add(new Star(x, y, vx, vy, c, 7));
					}
				}
			}
		}
		for(Dust d:dust) {
			d.update();
		}
		for(Star s:secondary) {
			s.update();
		}
		secondary.removeIf(s -> !s.exist);
		dust.removeIf(d -> !d.exist);
	}
	public static Star generateStar(Firework firework, int i) {
		double theta = 2 * Math.PI * i / (1 + Math.sqrt(5));
		double phi = Math.acos(1 - 2.0 * (i + 0.5) / firework.numStars);
		double vx = Math.sin(phi)*Math.cos(theta)*firework.starSpeed+firework.vx+Math.random()*.4-.2;
		double vy = Math.sin(phi)*Math.sin(theta)*firework.starSpeed+firework.vy+Math.random()*.4-.2;
		return new Star(firework.x, firework.y, vx, vy, firework.c, firework.type);
	}
}
