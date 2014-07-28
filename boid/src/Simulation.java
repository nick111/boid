import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

public class Simulation extends JFrame implements Runnable {

	Thread th;

	static int width;
	static int height;
	World world;

	public Simulation() {
		init();
	}

	public void init() {
		world = new World();
		width = world.width;
		height = world.height;
	    th  =  new  Thread(this);//スレッドインスタンス化
	    th.start();

	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < world.m_fish_kosuh; i++) {
			g2.draw(new Rectangle2D.Double(world.m_fish[i].m_x0
					- world.m_fish[i].pointwidth / 2, world.m_fish[i].m_y0
					- world.m_fish[i].pointwidth / 2,
					world.m_fish[i].pointwidth, world.m_fish[i].pointwidth));
		}
	}

	public static void main(String[] args) {
		Simulation f = new Simulation();
		f.setTitle("群れのシミュレーション");
		f.setSize(width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.WHITE);
		f.setVisible(true);
	}

	@Override
	public void run() {

		while (true) {

			world.changeSituation();

			repaint();

			int i=0;
			i++;
			
			if(i >10){
				i++;
			}
			
			
			try {
				th.sleep(100);// 0.01秒ＳＴＯＰ
			} catch (InterruptedException e) {

			}
		}
	}

}
