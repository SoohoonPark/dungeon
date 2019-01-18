package miniProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class StatusScreen extends JFrame{
	private String c_name, c_grade;
	private int c_lv, c_exp, c_nextexp;
	private int c_str, c_dex, c_int, c_atk, c_def; 
	
	// ������
	public StatusScreen(String name, String grade, int lv, int exp, int nexp, int s, int d, int i) {
		System.out.println("[info] StatusScreen() ����");
		this.c_name = name;
		this.c_grade = grade;
		this.c_lv = lv;
		this.c_exp = exp;
		this.c_nextexp = nexp;
		this.c_str = s;
		this.c_dex = d;
		this.c_int = i;
		this.c_atk = s/2;
		this.c_def = d/5;
		System.out.println("[info] StatusScreen() �ʵ� �ʱ�ȭ �Ϸ�");
		createStatusScreen();
	}
	
	// ����â ȭ�� ����� �޼ҵ�
	public void createStatusScreen() {
		setTitle("�������ͽ�");
		setSize(255, 320);
		setResizable(false);
		setLocation(1210, 170);
		setLayout(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNameGrade = new JPanel(null);
		panelNameGrade.setBounds(10, 10, 225, 115);
		panelNameGrade.setBorder(new LineBorder(Color.BLACK));
		
		JLabel lbl_cName = new JLabel(c_name,SwingConstants.CENTER);
		lbl_cName.setBounds(10, 10, 100, 20);
		lbl_cName.setBorder(new LineBorder(Color.red));
		lbl_cName.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cLv = new JLabel("���� "+c_lv,SwingConstants.CENTER);
		lbl_cLv.setBounds(10, 35, 80, 20);
		lbl_cLv.setBorder(new LineBorder(Color.GREEN));
		lbl_cLv.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cGrade = new JLabel(c_grade,SwingConstants.CENTER);
		lbl_cGrade.setBounds(95, 35, 100, 20);
		lbl_cGrade.setBorder(new LineBorder(Color.BLUE));
		lbl_cGrade.setFont(new Font("����",Font.BOLD, 14));
		
		DecimalFormat df = new DecimalFormat("#,###");
		JLabel lbl_cExp = new JLabel("���� ����ġ "+df.format(c_exp));
		lbl_cExp.setBounds(10, 60, 140, 20);
		lbl_cExp.setBorder(new LineBorder(Color.WHITE));
		lbl_cExp.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cNextexp = new JLabel("���� ����ġ "+df.format(c_nextexp));
		lbl_cNextexp.setBounds(10, 85, 140, 20);
		lbl_cNextexp.setBorder(new LineBorder(Color.WHITE));
		lbl_cNextexp.setFont(new Font("����",Font.PLAIN, 14));
		
		panelNameGrade.add(lbl_cName);
		panelNameGrade.add(lbl_cLv);
		panelNameGrade.add(lbl_cGrade);
		panelNameGrade.add(lbl_cExp);
		panelNameGrade.add(lbl_cNextexp);
		
		JPanel panelDetailStatus = new JPanel(null);
		panelDetailStatus.setBounds(10, 130, 225, 100);
		panelDetailStatus.setBorder(new LineBorder(Color.BLACK));
		
		JLabel lbl_cStr = new JLabel("�� "+c_str,SwingConstants.RIGHT);
		lbl_cStr.setBounds(40, 10, 60, 20);
		lbl_cStr.setBorder(new LineBorder(Color.RED));
		lbl_cStr.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cAtk = new JLabel("���ݷ� "+c_atk, SwingConstants.CENTER);
		lbl_cAtk.setBounds(105, 10, 80, 20);
		lbl_cAtk.setBorder(new LineBorder(Color.RED));
		lbl_cAtk.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cDex = new JLabel("��ø "+c_dex,SwingConstants.RIGHT);
		lbl_cDex.setBounds(40, 40, 60, 20);
		lbl_cDex.setBorder(new LineBorder(Color.GREEN));
		lbl_cDex.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cDef = new JLabel("���� "+c_def, SwingConstants.CENTER);
		lbl_cDef.setBounds(105, 40, 80, 20);
		lbl_cDef.setBorder(new LineBorder(Color.GREEN));
		lbl_cDef.setFont(new Font("����",Font.PLAIN, 14));
		
		JLabel lbl_cInt = new JLabel("���� "+c_int,SwingConstants.RIGHT);
		lbl_cInt.setBounds(40, 70, 60, 20);
		lbl_cInt.setBorder(new LineBorder(Color.BLUE));
		lbl_cInt.setFont(new Font("����",Font.PLAIN, 14));
		
		panelDetailStatus.add(lbl_cStr);
		panelDetailStatus.add(lbl_cDex);
		panelDetailStatus.add(lbl_cInt);
		panelDetailStatus.add(lbl_cAtk);
		panelDetailStatus.add(lbl_cDef);
		
		JPanel panelButton = new JPanel(null);
		panelButton.setBounds(10, 235, 225, 45);
		panelButton.setBorder(new LineBorder(Color.BLACK));
		
		JButton btnClose = new JButton("�ݱ�");
		btnClose.setBounds(60, 10, 100, 25);
		btnClose.setFont(new Font("����",Font.PLAIN, 14));
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panelButton.add(btnClose);
		
		add(panelNameGrade);
		add(panelDetailStatus);
		add(panelButton);
		
		// �����츮���� (�ش� â�� ������ �����ʿ��� GameScreen�� �ɷ�ġ ��ư�� Ȱ��ȭ(setEnabled(true)) ����.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				JButton btnstatus = GameScreen.getStatusbutton();
				btnstatus.setEnabled(true);
			}
		});

		setVisible(true);
	}
}
