package miniProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * ĳ���� ������ �ϰԵǸ� ������ ����Ͽ� ������ ĳ���� ���� ������ �ѱ��.
 * �������� DB�� �����͵��� �ְԵ�.
 */

public class MakeCharacter extends JFrame {
	
	private final static int c_str = 10, c_dex = 10, c_int = 10; // ĳ������ �ʱ� ����
	// ĳ������ �ʱ� ü��, ����, ����, ����ġ, ��������ġ(1���� 2�� ������ 50 �ʿ�)
	private final static int c_hp = 100, c_mp = 80, c_lv = 1, c_exp = 0, c_next_exp = 50; 
	private final static String c_job = "���谡"; // ĳ���� �ʱ� ���

	/** �����ӿ� �ٴ� ������Ʈ�� **/
	private static JPanel id, lvjob, stats, buttons;
	private static JLabel lbl_cName, cLv, cLv2, cGrade, cGrade2;
	private static JLabel cHp, cHp2, cMp, cMp2, cStr, cDex, cInt;
	private static JTextField txt_cName;
	private static JButton btnCreate, btnCancel;
	
	// ĳ���� ����â
	public void createMakeCharacter() {
		System.out.println("[info] createMakeCharacter() ȣ��");
		
		/** ���� ������ **/
		setTitle("ĳ���� ����");
		setSize(300, 270);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** �гε� **/
		id = new JPanel(null);
		id.setBounds(0, 10, 300, 40);
		
		lvjob = new JPanel(null);
		lvjob.setBounds(0, 70, 300, 40);
		
		stats = new JPanel(null);
		stats.setBounds(0, 100, 300, 70);
		
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttons.setBounds(0, 180, 300, 50);
		
		/** �󺧵� **/
		lbl_cName = new JLabel("ĳ���͸�");
		lbl_cName.setBounds(30, 20, 60, 20);
		lbl_cName.setFont(new Font("����", Font.PLAIN, 14));
		
		cLv = new JLabel("����");
		cLv.setBounds(30, 10, 40, 20);
		cLv.setFont(new Font("����", Font.PLAIN, 14));
		
		cLv2 = new JLabel(String.valueOf(c_lv));
		cLv2.setBounds(70, 10, 20, 20);
		cLv2.setFont(new Font("����", Font.PLAIN, 14));
		
		cGrade = new JLabel("���");
		cGrade.setBounds(110, 10, 40, 20);
		cGrade.setFont(new Font("����", Font.PLAIN, 14));
		
		cGrade2 = new JLabel("���谡");
		cGrade2.setBounds(150, 10, 50, 20);
		cGrade2.setFont(new Font("����", Font.BOLD, 14));
		cGrade2.setForeground(new Color(71, 200, 62));
		
		cHp = new JLabel("ü��");
		cHp.setBounds(30, 10, 40, 20);
		cHp.setFont(new Font("����", Font.PLAIN, 14));
		
		cHp2 = new JLabel(String.valueOf(c_hp));
		cHp2.setBounds(70, 10, 40, 20);
		cHp2.setFont(new Font("����", Font.PLAIN, 14));
		cHp2.setForeground(Color.RED);
		
		cMp = new JLabel("����");
		cMp.setBounds(110, 10, 40, 20);
		cMp.setFont(new Font("����", Font.PLAIN, 14));
		
		cMp2 = new JLabel(String.valueOf(c_mp));
		cMp2.setBounds(150, 10, 40, 20);
		cMp2.setFont(new Font("����", Font.PLAIN, 14));
		cMp2.setForeground(Color.BLUE);
		
		cStr = new JLabel("�� " + String.valueOf(c_str));
		cStr.setBounds(30, 40, 60, 20);
		cStr.setFont(new Font("����", Font.PLAIN, 14));
		
		cDex = new JLabel("��ø " + String.valueOf(c_dex));
		cDex.setBounds(90, 40, 60, 20);
		cDex.setFont(new Font("����", Font.PLAIN, 14));
		
		cInt = new JLabel("���� " + String.valueOf(c_int));
		cInt.setBounds(160, 40, 60, 20);
		cInt.setFont(new Font("����", Font.PLAIN, 14));
		
		/** �ؽ�Ʈ�ʵ� **/
		txt_cName = new JTextField(5);
		txt_cName.setBounds(100, 20, 100, 20);
		
		/** ��ư **/
		btnCreate = new JButton("�����ϱ�");
		btnCreate.setPreferredSize(new Dimension(90, 40));
		btnCreate.setFont(new Font("����", Font.PLAIN, 14));
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txt_cName.getText().isEmpty()) { // ĳ���͸��� �������� �ƴ��� üũ��. ������ ��� �Է��ϰ� ����� errormessageâ ���.
					System.out.println("[Error] ĳ���͸��� �����Դϴ�.");
					JLabel warntext = new JLabel("<html><font style='font-family:���� ���; font-size:15pt;'>ĳ���� ���� �Է��ϼ���</font></html>");
					JOptionPane.showMessageDialog(null, warntext, "ĳ���� ����", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					// ĳ���͸��� ������ �ƴ� ��� �������� �Ѱܼ� createGameScreen() ȣ��
					new GameScreen().createGameScreen(c_str, c_dex, c_int, c_hp, c_mp, c_lv, c_exp, c_next_exp, txt_cName.getText(), c_job);
					dispose();
				}
			}
		});

		btnCancel = new JButton("����ϱ�");
		btnCancel.setPreferredSize(new Dimension(90, 40));
		btnCancel.setFont(new Font("����", Font.PLAIN, 14));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainScreen().createMainScreen();
				dispose();
			}
		});
		
		/** ������Ʈ add **/
		id.add(lbl_cName);
		id.add(txt_cName);
		
		lvjob.add(cLv);
		lvjob.add(cLv2);
		lvjob.add(cGrade);
		lvjob.add(cGrade2);
		
		stats.add(cHp);
		stats.add(cHp2);
		stats.add(cMp);
		stats.add(cMp2);
		stats.add(cStr);
		stats.add(cDex);
		stats.add(cInt);
		
		buttons.add(btnCreate);
		buttons.add(btnCancel);
		
		/** ���������ӿ� �г� add **/
		add(id);
		add(lvjob);
		add(stats);
		add(buttons);
		
		/* ���������� setVisible�� ��� ������Ʈ�� ���� �ڿ� true�� ����� ��. �ȱ׷��� �Ⱥ��̴� ������Ʈ�� ���� �� ���� */
		setVisible(true);
	}
}
