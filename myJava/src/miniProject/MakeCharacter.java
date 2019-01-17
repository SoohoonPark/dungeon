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
 * 캐릭터 생성을 하게되면 서버와 통신하여 서버로 캐릭터 생성 정보를 넘긴다.
 * 서버에서 DB에 데이터들을 넣게됨.
 */

public class MakeCharacter extends JFrame {
	
	private final static int c_str = 10, c_dex = 10, c_int = 10; // 캐릭터의 초기 스탯
	// 캐릭터의 초기 체력, 마나, 레벨, 경험치, 다음경험치(1에서 2로 가려면 50 필요)
	private final static int c_hp = 100, c_mp = 80, c_lv = 1, c_exp = 0, c_next_exp = 50; 
	private final static String c_job = "모험가"; // 캐릭터 초기 등급

	/** 프레임에 붙는 컴포넌트들 **/
	private static JPanel id, lvjob, stats, buttons;
	private static JLabel lbl_cName, cLv, cLv2, cGrade, cGrade2;
	private static JLabel cHp, cHp2, cMp, cMp2, cStr, cDex, cInt;
	private static JTextField txt_cName;
	private static JButton btnCreate, btnCancel;
	
	// 캐릭터 생성창
	public void createMakeCharacter() {
		System.out.println("[info] createMakeCharacter() 호출");
		
		/** 메인 프레임 **/
		setTitle("캐릭터 생성");
		setSize(300, 270);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** 패널들 **/
		id = new JPanel(null);
		id.setBounds(0, 10, 300, 40);
		
		lvjob = new JPanel(null);
		lvjob.setBounds(0, 70, 300, 40);
		
		stats = new JPanel(null);
		stats.setBounds(0, 100, 300, 70);
		
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttons.setBounds(0, 180, 300, 50);
		
		/** 라벨들 **/
		lbl_cName = new JLabel("캐릭터명");
		lbl_cName.setBounds(30, 20, 60, 20);
		lbl_cName.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cLv = new JLabel("레벨");
		cLv.setBounds(30, 10, 40, 20);
		cLv.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cLv2 = new JLabel(String.valueOf(c_lv));
		cLv2.setBounds(70, 10, 20, 20);
		cLv2.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cGrade = new JLabel("등급");
		cGrade.setBounds(110, 10, 40, 20);
		cGrade.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cGrade2 = new JLabel("모험가");
		cGrade2.setBounds(150, 10, 50, 20);
		cGrade2.setFont(new Font("굴림", Font.BOLD, 14));
		cGrade2.setForeground(new Color(71, 200, 62));
		
		cHp = new JLabel("체력");
		cHp.setBounds(30, 10, 40, 20);
		cHp.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cHp2 = new JLabel(String.valueOf(c_hp));
		cHp2.setBounds(70, 10, 40, 20);
		cHp2.setFont(new Font("굴림", Font.PLAIN, 14));
		cHp2.setForeground(Color.RED);
		
		cMp = new JLabel("마력");
		cMp.setBounds(110, 10, 40, 20);
		cMp.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cMp2 = new JLabel(String.valueOf(c_mp));
		cMp2.setBounds(150, 10, 40, 20);
		cMp2.setFont(new Font("굴림", Font.PLAIN, 14));
		cMp2.setForeground(Color.BLUE);
		
		cStr = new JLabel("힘 " + String.valueOf(c_str));
		cStr.setBounds(30, 40, 60, 20);
		cStr.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cDex = new JLabel("민첩 " + String.valueOf(c_dex));
		cDex.setBounds(90, 40, 60, 20);
		cDex.setFont(new Font("굴림", Font.PLAIN, 14));
		
		cInt = new JLabel("지능 " + String.valueOf(c_int));
		cInt.setBounds(160, 40, 60, 20);
		cInt.setFont(new Font("굴림", Font.PLAIN, 14));
		
		/** 텍스트필드 **/
		txt_cName = new JTextField(5);
		txt_cName.setBounds(100, 20, 100, 20);
		
		/** 버튼 **/
		btnCreate = new JButton("시작하기");
		btnCreate.setPreferredSize(new Dimension(90, 40));
		btnCreate.setFont(new Font("굴림", Font.PLAIN, 14));
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txt_cName.getText().isEmpty()) { // 캐릭터명이 공백인지 아닌지 체크함. 공백인 경우 입력하고 오라고 errormessage창 띄움.
					System.out.println("[Error] 캐릭터명이 공백입니다.");
					JLabel warntext = new JLabel("<html><font style='font-family:맑은 고딕; font-size:15pt;'>캐릭터 명을 입력하세요</font></html>");
					JOptionPane.showMessageDialog(null, warntext, "캐릭터 생성", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					// 캐릭터명이 공백이 아닌 경우 정보들을 넘겨서 createGameScreen() 호출
					new GameScreen().createGameScreen(c_str, c_dex, c_int, c_hp, c_mp, c_lv, c_exp, c_next_exp, txt_cName.getText(), c_job);
					dispose();
				}
			}
		});

		btnCancel = new JButton("취소하기");
		btnCancel.setPreferredSize(new Dimension(90, 40));
		btnCancel.setFont(new Font("굴림", Font.PLAIN, 14));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainScreen().createMainScreen();
				dispose();
			}
		});
		
		/** 컴포넌트 add **/
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
		
		/** 메인프레임에 패널 add **/
		add(id);
		add(lvjob);
		add(stats);
		add(buttons);
		
		/* 메인프레임 setVisible은 모든 컴포넌트가 붙은 뒤에 true로 해줘야 함. 안그러면 안보이는 컴포넌트가 있을 수 있음 */
		setVisible(true);
	}
}
