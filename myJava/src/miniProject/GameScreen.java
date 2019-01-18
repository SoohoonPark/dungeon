package miniProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/** ������ ����Ǵ� ȭ�� **/
public class GameScreen extends JFrame {
	private final static Font TEXTFONT = new Font("����", Font.PLAIN, 14);
	private final static Image bgimg = new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/images/dungeonbackground.png")).getImage();
	private final static Image tresureimg = new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/images/gettresure.png")).getImage();
	private static BackgroundPanel bgpanel;
	private static JTextArea log = new JTextArea();
	private static JScrollPane logscroll;
	private static JButton btnSearch, btnAttack, btnSkill,btnEquip, btnExit;
	private static JButton btnShowStatus,btnInventory;
	private static JPanel btnPanel, statusPanel;
	private static JLabel clv, cname, cgrade, cexp, cnextexp;
	private static JProgressBar hpbar, mpbar;

	private static int c_str, c_dex, c_int; // ĳ������ ����
	private static int c_hp, c_mp, c_lv, c_exp, c_next_exp; // ĳ������ ü��, ����, ����, �������ġ, ��������ġ
	private static String c_name, c_job; // ĳ���͸�, ���

	private static boolean battle = false; // ���� ���� �� true��
	private static int buttonindex; // � ��ư�� ���ȴ��� üũ
	private static int enemy_health; // ������ ���� ���� ü��
	private static int current_enemy_health; // ������ ���� ���� ü��
	private static int current_player_health; // �÷��̾��� ���� ü��
	private static int current_player_mana; // �÷��̾��� ���� ����
	private static int playeratk; // �÷��̾��� ���ݷ�
	private static int playerdef; // �÷��̾��� ����
	private static int mobatk, mobdef; // ������ ���ݷ� & ����
	private static String[] dropitem; // ������ ��������� (DungeonMonster Ŭ������ getdropitem()�� ���� �����.)
	private static Thread m_check, p_check; // �÷��̾� & ���� ���� üũ�ϴ� Thread
	private static LinkedList<String> inventory = new LinkedList<String>(); // ĳ���� �κ��丮 
	private static Map<String, DungeonMonster> monsters = new HashMap<String, DungeonMonster>(); // ���� ���� �ؽø�
	private static Map<Integer, DungeonExpTable> exptable = new HashMap<Integer, DungeonExpTable>(); // ĳ���� ����ġ ���̺�
	private static String mobkey; // ������ �� �̸� (���� ���� �ؽø��� key)

	// default ������
	public GameScreen() {
		super();
	}

	// ����ȭ�� ����
	public void createGameScreen(int s, int d, int i, int hp, int mp, int lv, int exp, int nextexp, String name, String job) {
		System.out.println("[info] createGameScreen() ����");
		c_str = s;
		c_dex = d;
		c_int = i;
		c_hp = hp; // �÷��̾��� �� ü��
		c_mp = mp;
		c_lv = lv;
		c_exp = exp;
		c_next_exp = nextexp;
		c_name = name;
		c_job = job;
		current_player_health = hp; // �÷��̾��� ���� ü�� (���� ����ǰų� Ư�� �̺�Ʈ�� ���ؼ� ��� �ٲ�)
		current_player_mana = mp; // �÷��̾��� ���� ���� (���� ����ǰų� Ư�� �̺�Ʈ�� ���ؼ� ��� �ٲ�)
		
		System.out.println("[info] GameScreen �ʵ� �ʱ�ȭ �Ϸ�");
		
		setTitle("�������� ��Ƴ���");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);

		/** ������ �̹��� ���� (�г�) **/
		bgpanel = new BackgroundPanel(bgimg);
		add(bgpanel);

		log.setEditable(false);
		log.setFont(TEXTFONT);
		log.setText("[System] '"+c_name+"' (��/��) ������ ���Խ��ϴ�.\n������ ��Ƴ�������\n");
		logscroll = new JScrollPane(log);
		logscroll.setBounds(23, 230, 450, 200);
		add(logscroll);

		statusPanel = new JPanel(null);
		statusPanel.setBounds(35, 445, 425, 100);

		JLabel lbl_lv = new JLabel("����", SwingConstants.CENTER);
		lbl_lv.setBounds(10, 5, 30, 20);
		lbl_lv.setFont(TEXTFONT);

		clv = new JLabel();
		clv.setBounds(50, 5, 30, 20);
		clv.setText(String.valueOf(c_lv));
		clv.setFont(TEXTFONT);

		cname = new JLabel();
		cname.setBounds(80, 5, 50, 20);
		cname.setFont(TEXTFONT);
		cname.setText(c_name);

		cgrade = new JLabel();
		cgrade.setBounds(135, 5, 50, 20);
		cgrade.setFont(TEXTFONT);
		cgrade.setText(c_job);

		JLabel lbl_currentexp = new JLabel("���� ����ġ", SwingConstants.CENTER);
		lbl_currentexp.setBounds(220, 5, 80, 20);
		lbl_currentexp.setFont(TEXTFONT);

		// ����ġ �� �ڸ� ���� �޸�(,) ��� ���� DecimalFormat ���� ����.
		DecimalFormat df = new DecimalFormat("#,###");
		cexp = new JLabel(df.format(c_exp), SwingConstants.RIGHT);
		cexp.setBounds(300, 5, 70, 20);
		cexp.setFont(TEXTFONT);
		
		JLabel lbl_nextexp = new JLabel("���� ����ġ", SwingConstants.CENTER);
		lbl_nextexp.setBounds(220, 30, 80, 20);
		lbl_nextexp.setFont(TEXTFONT);
		
		cnextexp = new JLabel(df.format(c_next_exp), SwingConstants.RIGHT);
		cnextexp.setBounds(300, 30, 70, 20);
		cnextexp.setFont(TEXTFONT);
		
		btnShowStatus = new JButton("�ɷ�ġ");
		btnShowStatus.setBounds(220, 60, 80, 30);
		btnShowStatus.setFont(TEXTFONT);
		btnShowStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().toString().indexOf("�ɷ�ġ") != 0) {
					btnShowStatus.setEnabled(false);
				}
				new StatusScreen(c_name, c_job, c_lv, c_exp, c_next_exp, c_str, c_dex, c_int);
			}
		});
		
		JButton btnRun = new JButton("����ġ��");
		btnRun.setBounds(305, 60, 100, 30);
		btnRun.setFont(TEXTFONT);
		btnRun.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(battle) {
					addLog("[System] '"+c_name+"' (��/��) ���� ���� ���� �����ƴ�!");
					addLog("["+c_name+"] : ����... ���� �� �߳�... ��..\n");
					battle = false;
				}else {
					buttonindex = 3;
					createAlertWindow(buttonindex);
				}
			}
		});
		
		// ���� ü��
		JLabel lbl_health = new JLabel("H P", SwingConstants.CENTER);
		lbl_health.setBounds(10, 30, 30, 20);
		lbl_health.setFont(TEXTFONT);

		hpbar = new JProgressBar(0, c_hp);
		hpbar.setBounds(50, 30, 160, 20);
		hpbar.setStringPainted(true);
		hpbar.setString(String.valueOf(current_player_health) + " / " + String.valueOf(c_hp));
		hpbar.setFont(new Font("����", Font.BOLD, 13));
		hpbar.setForeground(Color.RED);
		hpbar.setValue(current_player_health);

		// ���� ����
		JLabel lbl_mana = new JLabel("M P", SwingConstants.CENTER);
		lbl_mana.setBounds(10, 55, 30, 20);
		lbl_mana.setFont(TEXTFONT);

		mpbar = new JProgressBar(0, c_mp);
		mpbar.setBounds(50, 55, 160, 20);
		mpbar.setStringPainted(true);
		mpbar.setString(String.valueOf(current_player_mana) + " / " + String.valueOf(c_mp));
		mpbar.setFont(new Font("����", Font.BOLD, 13));
		mpbar.setForeground(Color.BLUE);
		mpbar.setValue(current_player_mana);

		/** ��ư �г� & ��ư�� ���� **/
		btnPanel = new JPanel(null);
		btnPanel.setBounds(35, 550, 425, 100);

		btnSearch = new JButton("Ž���ϱ�");
		btnSearch.setBounds(15, 18, 90, 60);
		btnSearch.setFont(new Font("����", Font.PLAIN, 14));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonindex = 1; // 1�� Ž���ϱ� ��ư
				if(battle) { // ������ �߻������� Ž���ϱ� ��ư�� ������ �˸�â ���.
					createAlertWindow(buttonindex);
					return;
				}
				int encount = encountnum();
				createEncount(encount); // ������ ���ڸ� ���� ��ī��Ʈ ����
			}
		});

		btnAttack = new JButton("����");
		btnAttack.setBounds(120, 5, 90, 40);
		btnAttack.setFont(new Font("����", Font.PLAIN, 14));
		btnAttack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonindex = 2; // 2�� ���� ��ư
				if (battle) {
					attack_player();
					attack_monster();
				}else {
					createAlertWindow(buttonindex);
				}
			}
		});

		btnSkill = new JButton("��ų");
		btnSkill.setBounds(120, 55, 90, 40);
		btnSkill.setFont(new Font("����", Font.PLAIN, 14));
		btnSkill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				current_player_mana -= 10;
			}
		});

		btnInventory = new JButton("�κ��丮");
		btnInventory.setBounds(215, 5, 90, 40);
		btnInventory.setFont(new Font("����", Font.PLAIN, 14));
		btnInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().toString().indexOf("�κ��丮") != 0) {
					btnInventory.setEnabled(false);
				}
				new InventoryScreen(inventory);
			}
		});

		btnEquip = new JButton("���");
		btnEquip.setBounds(215, 55, 90, 40);
		btnEquip.setFont(new Font("����", Font.PLAIN, 14));

		btnExit = new JButton("��������");
		btnExit.setBounds(320, 18, 90, 60);
		btnExit.setFont(new Font("����", Font.PLAIN, 14));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonindex = 6;
				createAlertWindow(buttonindex);
			}
		});

		/** �гο� ������Ʈ�� add **/
		// �ɷ�ġ �г�
		statusPanel.add(lbl_lv);
		statusPanel.add(clv);
		statusPanel.add(cname);
		statusPanel.add(cgrade);
		statusPanel.add(lbl_currentexp);
		statusPanel.add(cexp);
		statusPanel.add(lbl_nextexp);
		statusPanel.add(cnextexp);

		statusPanel.add(lbl_health);
		statusPanel.add(hpbar);

		statusPanel.add(lbl_mana);
		statusPanel.add(mpbar);
		
		statusPanel.add(btnShowStatus);
		statusPanel.add(btnRun);

		// ��ư �г�
		btnPanel.add(btnSearch);
		btnPanel.add(btnAttack);
		btnPanel.add(btnExit);
		btnPanel.add(btnInventory);
		btnPanel.add(btnSkill);
		btnPanel.add(btnEquip);

		/** �����ӿ� �г� add **/
		add(btnPanel);
		add(statusPanel);
		
		setVisible(true);
		setmonsters(); // ���� ������ ����
		setexptable(); // ����ġ ���̺� ���� ����
		checkplayerstatus(); // ĳ���� ���� ���� Ȯ�� Thread
		checkmonsterstatus(); // �� ���� ���� Ȯ�� Thread
	}

	// JTextArea�� �α׸� �����̴� �޼ҵ�
	private static void addLog(String txt) {
		log.append(txt+"\n"+"");
		int txtlength = log.getText().length(); // JTextarea ���ڿ� �� ����
		log.setCaretPosition(txtlength); // ���� ���ڿ� ���̸� caretposition ������
		log.requestFocus(); // �� ������ �̵��� caret �� �������� focus �缳�� �ϴµ�..?
	}

	// ���� �÷��̾� ���� üũ.
	private void checkplayerstatus() {
		p_check = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("[info] �÷��̾� ���� Ȯ�� Thread ����");
					while (!Thread.currentThread().isInterrupted()) {

						/** �÷��̾��� ���ݷ� & ���� ���� **/
						playeratk = c_str/2; // �÷��̾� ���ݷ�(�� / 2)
						playerdef = c_dex/5; // �÷��̾� ����(��ø / 5)
						cexp.setText(String.valueOf(c_exp));
						cnextexp.setText(String.valueOf(c_next_exp));
						
						/** Thread�� ����Ǹ鼭 hpbar & mpbar �� setValue() ����. (setText()�� ��������) **/
						hpbar.setString(String.valueOf(current_player_health) + " / " + String.valueOf(c_hp));
						hpbar.setMaximum(c_hp);
						hpbar.setValue(current_player_health);
						mpbar.setString(String.valueOf(current_player_mana) + " / " + String.valueOf(c_mp));
						mpbar.setMaximum(c_mp);
						mpbar.setValue(current_player_mana);
						
						/** ĳ���� ������ üũ **/
						if(c_exp >= c_next_exp) {
							addLog("[System] '"+c_name+"' �� ������!");
							c_lv+=1;
							clv.setText(String.valueOf(c_lv));
							addLog("[System] '"+c_name+"' (��/��) ���� " + c_lv+" �� �Ǿ���!\n");
							c_next_exp = exptable.get(c_lv).getNextexp();
							cnextexp.setText(String.valueOf(c_next_exp));
							int strup = (int)(Math.random()*5)+1; // 1 ~ 5 �� �ɷ�ġ ��
							int dexup = (int)(Math.random()*5)+1; // 1 ~ 5 ��ø �ɷ�ġ ��
							int intup = (int)(Math.random()*5)+1; // 1 ~ 5 ���� �ɷ�ġ ��
							// ���� �ɷ�ġ ��ġ��ŭ ���� �ɷ�ġ�� + ��Ŵ
							addLog("[System] ���� "+strup+" �ö���!");
							addLog("[System] ��ø�� "+dexup+" �ö���!");
							addLog("[System] ������ "+intup+" �ö���!\n");
							c_str += strup;
							c_dex += dexup;
							c_int += intup;
							
							// 10 ���� ���������� �������ϸ� ü�¿� + 50, ������ + 30
							if(c_lv <= 10) {
								c_hp += 50;
								c_mp += 30;
								current_player_health = c_hp; // �������ϰ� �þ �� hp ��ŭ ���� hp�� (Ǯ�� ȸ��)
								current_player_mana = c_mp; // (Ǯ ���� ȸ��)
							}
						}

						/** ���� �÷��̾� ü���� 0�̵Ǹ� ���. ���� ���� **/
						if (current_player_health <= 0) {
							addLog("[System] '"+c_name+"' (��/��) �׾����ϴ�.");
							createAlertWindow(9);
							Thread.currentThread().interrupt();
						}
					}
				} catch (Exception e) {
					System.out.println("[Error] �÷��̾� ���� üũ ������ ����");
					e.printStackTrace();
				}
			}
		});
		p_check.start();
	}

	// ������ ���� check ���Ͱ� ����ϸ� ������ ȹ�� & ����ġ ȹ�� ���� ó���� ��
	public static void checkmonsterstatus() {
		m_check = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						Thread.sleep(300);
						if(mobkey != null) {
							if(current_enemy_health <= 0) { // ���� ���� ü���� 0���� �۰ų� ���� �� (= �� ���)
								mobdead();
							}
						}else {
							System.out.println("[info] �� ������ �ȵ�����");
						}
					}
				}catch(Exception e) {
					System.out.println("[Error] �� Ȯ�� Thread ����");
					e.printStackTrace();	
				}
			}

			
		});
		m_check.setPriority(6);
		m_check.start();
	}

	// �� ��� �� ����Ǵ� �޼ҵ�
	private static void mobdead() {
		addLog("[System] "+mobkey+" (��/��) ��������.");
		addLog("[System] ����ġ�� "+ monsters.get(mobkey).getMonsterexp() + " �����.\n");
		c_exp += monsters.get(mobkey).getMonsterexp();
		cexp.setText(String.valueOf(c_exp));
		for(int i=0; i<dropitem.length; i++) {
			addLog("[System] '"+mobkey+"' (��/��) "+dropitem[i]+ " (��/��) ����߷ȴ�.");
			addLog("[System] '"+c_name+"' (��/��) "+dropitem[i]+" (��/��) ���濡 �־���.");
			inventory.add(dropitem[i]);
		}
		System.out.println("[info] �κ��丮 ũ�� : "+dropitem.length);
		mobkey = null;
		battle = false;
	}
	
	// ���� ���� ����(DB ����)
	private static void setmonsters() {
		System.out.println("[info] setmonsters() ����");
		Connection conn = DBConnection.connectDB();
		String query = "SELECT * FROM MONSTERS";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs != null) {
				System.out.println("[info] DB�κ��� ���� ���� �������� �Ϸ�");
				while(rs.next()) {
					monsters.put(rs.getString(1),new DungeonMonster(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
				}
				System.out.println("[info] ���� ���� ���� �Ϸ�");
				System.out.println("[info] ����� ���� ���� ������ : " +monsters.size());
				Set<String> keys = monsters.keySet();
				System.out.println("[info] ����� ���� ���� �̸� : " + keys.toString());
			}
		}catch(SQLException e) {
			System.out.println("[Error] setmonsters() : SQL ���� ����");
		}finally {
			DBConnectClose.connectClose(conn, pstmt, rs);
			System.out.println("[info] setmonsters() �Ϸ�\n");
		}
	}

	// ����ġ ���̺� ���� ����(DB ����)
	private static void setexptable() {
		System.out.println("[info] setexptable() ����");
		Connection conn = DBConnection.connectDB();
		String query = "SELECT * FROM EXPTABLE";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs != null) {
				System.out.println("[info] DB�κ��� ����ġ ���̺� ���� �������� �Ϸ�");
				while(rs.next()) {
					exptable.put(rs.getInt(1), new DungeonExpTable(rs.getInt(2), rs.getInt(3)));
				}
				System.out.println("[info] ����ġ ���̺� ���� ���� �Ϸ�");
				System.out.println("[info] ����ġ ���̺� ������ : " +exptable.size());
			}
		}catch(SQLException e) {
			System.out.println("[Error] setexptable() : SQL ���� ����");
		}finally {
			DBConnectClose.connectClose(conn, pstmt, rs);
			System.out.println("[info] setexptable() �Ϸ�\n");
		}
	}
	
	// ��ī���� �߻��� �ʿ��� ���� ���� ����� �޼ҵ�
	private static int encountnum() {
		int encount = (int) (Math.random() * 3) + 1; // 1 ~ 3 ����
		return encount;
	}

	// ��ī��Ʈ ����
	public void createEncount(int encount) {
		System.out.println("[info] createEncount() ����");
		changeBackgroundImage(bgpanel, bgimg);
		System.out.println("[info] �߻��� ��ī��Ʈ : " + encount);
		switch (encount) {
		case 1:
			battle = true; // ������ �߻�
			createbattle(); // ���� ����
			System.out.println("[info] ���� �����Ϸ�");
			break;
		case 2:
			changeBackgroundImage(bgpanel, tresureimg);
			addLog("�ű��غ��̴� ���ڰ� �ִ�.\n���� ����� ������ �������� ���� �� ����!\n");
			break;
		case 3:
			createEvent();
			System.out.println("[info] Ư�� �̺�Ʈ ���� �Ϸ�");
		}
	}

	// Ư�� �̺�Ʈ ����
	private static void createEvent() {
		int encount = (int)(Math.random() * 10)+1; // 1 ~ 10 ���� �߻�
		System.out.println("[info] Ư�� �̺�Ʈ �߻�");
		System.out.println("[info] �̺�Ʈ ��ī��Ʈ : " + encount);
		switch (encount) {
		case 1: case 2: case 3:
			addLog("[" + c_name + "] : �ű� ���� �־��?");
			addLog("[System] ('" + c_name + "'" + "��/�� �ڸ� ���� ������ �ƹ��� ������..)");
			addLog("[" + c_name + "] : �Ҹ� ����...\n");
			break;
		case 5: case 6:
			addLog("[" + c_name + "] : �� ������ ������̶����..");
			addLog("[" + c_name + "] : ���� ���� ���� �ʹ�..");
			current_player_health--;
			addLog("[System] �Ƿη� ���� ü���� 1 �����ߴ�.\n");
			break;
		case 4: case 7:
			addLog("[" + c_name + "] : �������..");
			addLog("[" + c_name + "] : ���⼭ ���������� ��û ����� �� ������..");
			int hungry = (int) (Math.random() * 3) + 1; // 1 ~ 3 �������� hp ����
			current_player_health -= hungry;
			addLog("[System] '" + c_name + "' �� ü���� " + hungry + " ��ŭ �����ߴ�.\n");
			break;
			
		case 8: case 9:
			addLog("["+c_name+"] : ...??");
			addLog("[System] '"+c_name+"' (��/��) �ȴٰ� ���� �������� �����.\n"+"[System] '"+c_name+"' ��"+"����ġ�� 50 �ö���.\n");
			c_exp += 50;
			break;
			
		case 10:
			addLog("[System] �ű��� ���̴� �����̸� �����ô�.");
			addLog("[System] '"+c_name+"' �� ����ġ�� 100 �ö���.");
			c_exp += 100;
		}
	}

	// ���� ����
	private static void createbattle() {
		System.out.println("[info] createbattle() ����");
		if (battle) { // ������ true �� ��� ���� ����
			Set<String> mobkeys = monsters.keySet();
			List<String> mobs = new ArrayList<String>(mobkeys);
			int encountmob = (int)(Math.random() * mobs.size()); //
			if(mobs.get(encountmob).equals("�ܹ��ſ�")) { // �ְ���
				addLog("[System] '"+c_name+"' (��/��) ������ ������ �� �ڸ��� �����ƴ�.\n");
				addLog("["+c_name+"] : �ٵ� ���� ���־� �����µ�...");
				battle = false;
				return;
			}
			mobkey = mobs.get(encountmob); // ������ �� Ű ���� ������ (�ش� ������ �� ������ ������ �� ����)
			addLog("[System] �������� ��Ŵ� �� ���ƴٴϴ� "+mobkey+" ��/�� �߰��ߴ�!");
			
			/** ������ ���Ͱ� ���������� �ش� ������ ü��/���ݷ�/������ ������ ������ ������. **/
			enemy_health = monsters.get(mobkey).getMonsterhp(); // ���� ���� ü��
			current_enemy_health = enemy_health; // ���� ���� ü��
			mobatk = monsters.get(mobkey).getMonsteratk(); // ���ݷ�
			mobdef = monsters.get(mobkey).getMonsterdef(); // ����
			dropitem = monsters.get(mobkey).getitemDrop(); // ��������� ����
			
			addLog("[System] "+mobkey+" �� ����\nü�� : "+monsters.get(mobkey).getMonsterhp()+
					"\n���ݷ� : "+monsters.get(mobkey).getMonsteratk()+"\n���� : "+monsters.get(mobkey).getMonsterdef()+"\n");
			addLog("[System] '"+c_name+"'�� ����\n���ݷ� : "+playeratk+"\n���� : "+playerdef+"\n");
		}
	}

	// �÷��̾� ����
	private void attack_player() {
		addLog("[System] '"+c_name+"' �� ����!");
		int damage = playeratk - mobdef; // �������� �÷��̾� ���ݷ� - �� ���� ���� ����
		if(damage <= 0) { // �÷��̾� ���ݷ� - �� ���� �� ����� 0���� �۰ų� ���� ���(= ���� ������ �÷��̾� ���ݷº��� ���� ���)
			damage = 1; // �������� 1�� ������. (�ּ� �������� ������ 1)
			current_enemy_health-=damage; // damage ��ġ��ŭ �� ���� ü�� ����
			addLog("[System] '"+c_name+"' (��/��) "+mobkey+" ���� "+damage+" ��ŭ ���ظ� ������!");
			addLog("[System] "+mobkey+"�� ���� ���� ü�� " + current_enemy_health +" / "+enemy_health+"\n");
		}else {
			int randomdamage = (int)(Math.random() * damage)+1; // 1 ~ �÷��̾� ������ ������ ���������� ����
			current_enemy_health-=randomdamage; // randomdamage ��ġ��ŭ �� ���� ü�� ����
			addLog("[System] '"+c_name+"' (��/��) "+mobkey+" ���� "+randomdamage+" ��ŭ ���ظ� ������!");
			addLog("[System] "+mobkey+"�� ���� ���� ü�� " + current_enemy_health +" / "+enemy_health+"\n");
		}
	}

	// �� ����
	private void attack_monster() {
		addLog("[System] '"+mobkey+"' �� ����!");
		int damage = mobatk - playerdef;
		if(damage <= 0) {
			damage = 1; // �������� 1�� ������. (�ּ� �������� ������ 1)
			current_player_health-=damage; // damage ��ġ��ŭ �� ���� ü�� ����
			addLog("[System] '"+mobkey+"' (��/��) '"+c_name+"' ���� "+ damage + " ��ŭ ���ظ� ������!\n");
		}else {
			int randomdamage = (int)(Math.random() * damage)+1; // 1 ~ �÷��̾� ������ ������ ���������� ����
			current_player_health-=randomdamage;
			addLog("[System] '"+mobkey+"' (��/��) '"+c_name+"' ���� "+ randomdamage + " ��ŭ ���ظ� ������!\n");
		}
	}

	// �˸�â ���� �޼ҵ�
	private void createAlertWindow(int btnindex) {
		JLabel warntext;
		int result;
		switch (btnindex) {
		case 1: // Ž���ϱ� ��ư
			warntext = new JLabel("<html>���� �߿��� Ž���� �Ұ����մϴ�.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "Ž���ϱ�", JOptionPane.ERROR_MESSAGE);
			break;
		case 2: // ���ݹ�ư
			if(!battle) {
				warntext = new JLabel("<html>������ ���Ͱ� �����ϴ�</html>");
				warntext.setFont(TEXTFONT);
				JOptionPane.showMessageDialog(this, warntext, "����", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 3: // ����ġ�� ��ư
			warntext = new JLabel("<html>���Ͱ� �������� �ʽ��ϴ�.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "����ġ��", JOptionPane.ERROR_MESSAGE);
			break;
		case 6: // �������� ��ư
			warntext = new JLabel("<html>������ �����Ͻðڽ��ϱ�?<br/>�����Ȳ�� ������� �ʽ��ϴ�.</html>");
			warntext.setFont(TEXTFONT);
			result = JOptionPane.showConfirmDialog(null, warntext, "��������", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(1);
			} else if (result == JOptionPane.NO_OPTION) {
				return;
			} else {
				return;
			}
			break;
		case 9:
			warntext = new JLabel("<html>ĳ���Ͱ� ����Ͽ����ϴ�.<br/>������ �����մϴ�.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "��������", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			break;
		}
	}

	// ���ȭ�� �ٲٴ� �޼ҵ�
	private void changeBackgroundImage(JPanel p, Image img) {
		BackgroundPanel.setImg(img);
		p.repaint();
	}
	
	// �κ��丮 ��ư Ȱ��ȭ & ��Ȱ��ȭ ���� getbutton
	public static JButton getInventorybutton() {
		return btnInventory;
	}
	
	public static JButton getStatusbutton() {
		return btnShowStatus;
	}
	
	// �κ��丮 ���� ����
	public static void setInventory(LinkedList<String> inventory){
		GameScreen.inventory = inventory;
	}
}