package miniProject;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * 프로그램 첫 실행시 보게되는 창
 * 버튼에 각각 리스너를 달아서 클릭 시 이벤트 처리를 하도록 한다.
 */
@SuppressWarnings("serial")
public class MainScreen extends JFrame{
	private Image titleimg = Toolkit.getDefaultToolkit().createImage("resources/images/main/title.png");
	
	public static void main(String[] args) {
		System.out.println("[info] 프로그램 실행");
		new MainScreen().createMainScreen();
	}
	
	// 메인화면
	public void createMainScreen(){
		setTitle("던전에서 살아남기");
		setLayout(null);
		setSize(400, 540);
		// setLocationRelativeTo(null) 을 하게되면 현재 모니터 중앙에 frame이 뜬다.
		setLocationRelativeTo(null); 
		setResizable(false);
		
		JLabel lblTitle = new JLabel();
		ImageIcon icon = new ImageIcon(titleimg);
		lblTitle.setBounds(60, 40, icon.getIconWidth(), icon.getIconHeight());	
		lblTitle.setIcon(icon);
	
		JButton btnStart = new JButton("시작하기");
		btnStart.setBounds(120, 280, 150, 50);
		btnStart.setFont(new Font("굴림", Font.PLAIN, 14));
		
		// 시작하기 버튼을 눌렸을때 캐릭터 생성화면 창 띄움
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MakeCharacter().createMakeCharacter();
				dispose();
			}
		});
	
		JButton btnQuit = new JButton("종료하기");
		btnQuit.setBounds(120, 360, 150, 50);
		btnQuit.setFont(new Font("굴림", Font.PLAIN, 14));
		
		// 종료하기 버튼을 눌렸을때 창 닫는 이벤트
		btnQuit.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});	
		
		add(lblTitle);
		add(btnStart);
		add(btnQuit);
		setVisible(true);
	}	
}
