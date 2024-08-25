import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Display extends JPanel implements ActionListener, MouseListener, KeyListener{
	Timer t;
	public Display() {
		t = new Timer(15, this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		FireworkShow.paint(g);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		FireworkShow.update();
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		FireworkShow.mousePressed(e.getX(), e.getY());
	}
	@Override
	public void keyPressed(KeyEvent e) {
		FireworkShow.keyPressed(e.getKeyChar());
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
