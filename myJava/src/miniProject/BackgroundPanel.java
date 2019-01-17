package miniProject;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	private static Image img;

	public BackgroundPanel(Image img) {
		BackgroundPanel.img = img;
		setBounds(0, 20, img.getWidth(null), img.getHeight(null)); // 패널 사이즈
		setLayout(null); // 패널 레이아웃매니저 null
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public static void setImg(Image img) {
		BackgroundPanel.img = img;
	}

	public Image getImg() {
		return img;
	}
}
