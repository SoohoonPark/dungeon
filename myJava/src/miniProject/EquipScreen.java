package miniProject;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

// ÀåºñÃ¢ º¸¿©ÁÖ´Â Å¬·¡½º
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
		setTitle("Àåºñ");
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelEquip = new JPanel(null);
		panelEquip.setBounds(23, 10, 250, 280);
		panelEquip.setBorder(new LineBorder(Color.BLACK));
		
		JLabel lblHelmet = new JLabel();
		lblHelmet.setBounds(100, 10, 50, 50);
		lblHelmet.setBorder(new LineBorder(Color.RED));
		
		JLabel helmet = new JLabel("<html><p style='font-family:¸¼Àº °íµñ; color:blue;'>Åõ±¸</p></html>",SwingConstants.CENTER);
		helmet.setBounds(100, 60, 50, 20);
		
		JLabel lblArmor = new JLabel();
		lblArmor.setBounds(100, 85, 50, 50);
		lblArmor.setBorder(new LineBorder(Color.RED));
		
		JLabel armor = new JLabel("<html><p style='font-family:¸¼Àº °íµñ; color:blue;'>°©¿Ê</p></html>",SwingConstants.CENTER);
		armor.setBounds(100, 135, 50, 20);
		
		JLabel lblWeapon = new JLabel();
		lblWeapon.setBounds(40, 85, 50, 50);
		lblWeapon.setBorder(new LineBorder(Color.RED));
		
		JLabel weapon = new JLabel("<html><p style='font-family:¸¼Àº °íµñ; color:blue;'>¹«±â</p></html>",SwingConstants.CENTER);
		weapon.setBounds(40, 135, 50, 20);
		
		JLabel lblShield = new JLabel();
		lblShield.setBounds(160, 85, 50, 50);
		lblShield.setBorder(new LineBorder(Color.RED));
		
		JLabel shield = new JLabel("<html><p style='font-family:¸¼Àº °íµñ; color:blue;'>¹æÆÐ</p></html>",SwingConstants.CENTER);
		shield.setBounds(160, 135, 50, 20);
		
		JLabel lblBoots = new JLabel();
		lblBoots.setBounds(100, 160, 50, 50);
		lblBoots.setBorder(new LineBorder(Color.RED));
		
		JLabel boots = new JLabel("<html><p style='font-family:¸¼Àº °íµñ; color:blue;'>½Å¹ß</p></html>",SwingConstants.CENTER);
		boots.setBounds(100, 210, 50, 20);
		
		panelEquip.add(lblHelmet);
		panelEquip.add(helmet);
		panelEquip.add(lblArmor);
		panelEquip.add(armor);
		panelEquip.add(lblWeapon);
		panelEquip.add(weapon);
		panelEquip.add(lblShield);
		panelEquip.add(shield);
		panelEquip.add(lblBoots);
		panelEquip.add(boots);
		
		add(panelEquip);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new EquipScreen("¾øÀ½", "¾øÀ½", "¾øÀ½", "¾øÀ½", "¾øÀ½", "¾øÀ½");
	}
}
