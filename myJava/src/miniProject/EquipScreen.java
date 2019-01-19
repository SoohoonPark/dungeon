package miniProject;

import javax.swing.JButton;
import javax.swing.JFrame;

// 장비창 보여주는 클래스
@SuppressWarnings("serial")
public class EquipScreen extends JFrame{
	private String helmet, armor, weapon, boots, shield, glove;
	private JButton btnClose;
	
	public EquipScreen(String helmet, String armor, String weapon, String boots, String shield, String glove) {
		this.helmet = helmet;
		this.armor = armor;
		this.weapon = weapon;
		this.boots = boots;
		this.shield = shield;
		this.glove = glove;
		createEquipScreen();
	}
	
	void createEquipScreen() {
		setSize(300, 400);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new EquipScreen("없음", "없음", "없음", "없음", "없음", "없음");
	}
}
