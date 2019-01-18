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

/** 게임이 진행되는 화면 **/
public class GameScreen extends JFrame {
	private final static Font TEXTFONT = new Font("돋움", Font.PLAIN, 14);
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

	private static int c_str, c_dex, c_int; // 캐릭터의 스탯
	private static int c_hp, c_mp, c_lv, c_exp, c_next_exp; // 캐릭터의 체력, 마력, 레벨, 현재경험치, 다음경험치
	private static String c_name, c_job; // 캐릭터명, 등급

	private static boolean battle = false; // 전투 생성 시 true로
	private static int buttonindex; // 어떤 버튼을 눌렸는지 체크
	private static int enemy_health; // 등장한 적의 원래 체력
	private static int current_enemy_health; // 등장한 적의 현재 체력
	private static int current_player_health; // 플레이어의 현재 체력
	private static int current_player_mana; // 플레이어의 현재 마력
	private static int playeratk; // 플레이어의 공격력
	private static int playerdef; // 플레이어의 방어력
	private static int mobatk, mobdef; // 몬스터의 공격력 & 방어력
	private static String[] dropitem; // 몬스터의 드랍아이템 (DungeonMonster 클래스의 getdropitem()를 통해 저장됨.)
	private static Thread m_check, p_check; // 플레이어 & 몬스터 상태 체크하는 Thread
	private static LinkedList<String> inventory = new LinkedList<String>(); // 캐릭터 인벤토리 
	private static Map<String, DungeonMonster> monsters = new HashMap<String, DungeonMonster>(); // 몬스터 정보 해시맵
	private static Map<Integer, DungeonExpTable> exptable = new HashMap<Integer, DungeonExpTable>(); // 캐릭터 경험치 테이블
	private static String mobkey; // 생성된 몹 이름 (몬스터 정보 해시맵의 key)

	// default 생성자
	public GameScreen() {
		super();
	}

	// 게임화면 생성
	public void createGameScreen(int s, int d, int i, int hp, int mp, int lv, int exp, int nextexp, String name, String job) {
		System.out.println("[info] createGameScreen() 실행");
		c_str = s;
		c_dex = d;
		c_int = i;
		c_hp = hp; // 플레이어의 총 체력
		c_mp = mp;
		c_lv = lv;
		c_exp = exp;
		c_next_exp = nextexp;
		c_name = name;
		c_job = job;
		current_player_health = hp; // 플레이어의 현재 체력 (전투 진행되거나 특정 이벤트에 의해서 계속 바뀜)
		current_player_mana = mp; // 플레이어의 현재 마력 (전투 진행되거나 특정 이벤트에 의해서 계속 바뀜)
		
		System.out.println("[info] GameScreen 필드 초기화 완료");
		
		setTitle("던전에서 살아남기");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);

		/** 진행배경 이미지 삽입 (패널) **/
		bgpanel = new BackgroundPanel(bgimg);
		add(bgpanel);

		log.setEditable(false);
		log.setFont(TEXTFONT);
		log.setText("[System] '"+c_name+"' (이/가) 던전에 들어왔습니다.\n무사히 살아남으세요\n");
		logscroll = new JScrollPane(log);
		logscroll.setBounds(23, 230, 450, 200);
		add(logscroll);

		statusPanel = new JPanel(null);
		statusPanel.setBounds(35, 445, 425, 100);

		JLabel lbl_lv = new JLabel("레벨", SwingConstants.CENTER);
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

		JLabel lbl_currentexp = new JLabel("현재 경험치", SwingConstants.CENTER);
		lbl_currentexp.setBounds(220, 5, 80, 20);
		lbl_currentexp.setFont(TEXTFONT);

		// 경험치 세 자리 마다 콤마(,) 찍기 위한 DecimalFormat 형식 지정.
		DecimalFormat df = new DecimalFormat("#,###");
		cexp = new JLabel(df.format(c_exp), SwingConstants.RIGHT);
		cexp.setBounds(300, 5, 70, 20);
		cexp.setFont(TEXTFONT);
		
		JLabel lbl_nextexp = new JLabel("다음 경험치", SwingConstants.CENTER);
		lbl_nextexp.setBounds(220, 30, 80, 20);
		lbl_nextexp.setFont(TEXTFONT);
		
		cnextexp = new JLabel(df.format(c_next_exp), SwingConstants.RIGHT);
		cnextexp.setBounds(300, 30, 70, 20);
		cnextexp.setFont(TEXTFONT);
		
		btnShowStatus = new JButton("능력치");
		btnShowStatus.setBounds(220, 60, 80, 30);
		btnShowStatus.setFont(TEXTFONT);
		btnShowStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().toString().indexOf("능력치") != 0) {
					btnShowStatus.setEnabled(false);
				}
				new StatusScreen(c_name, c_job, c_lv, c_exp, c_next_exp, c_str, c_dex, c_int);
			}
		});
		
		JButton btnRun = new JButton("도망치기");
		btnRun.setBounds(305, 60, 100, 30);
		btnRun.setFont(TEXTFONT);
		btnRun.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(battle) {
					addLog("[System] '"+c_name+"' (은/는) 죽을 힘을 다해 도망쳤다!");
					addLog("["+c_name+"] : 헉헉... 죽을 뻔 했네... 휴..\n");
					battle = false;
				}else {
					buttonindex = 3;
					createAlertWindow(buttonindex);
				}
			}
		});
		
		// 현재 체력
		JLabel lbl_health = new JLabel("H P", SwingConstants.CENTER);
		lbl_health.setBounds(10, 30, 30, 20);
		lbl_health.setFont(TEXTFONT);

		hpbar = new JProgressBar(0, c_hp);
		hpbar.setBounds(50, 30, 160, 20);
		hpbar.setStringPainted(true);
		hpbar.setString(String.valueOf(current_player_health) + " / " + String.valueOf(c_hp));
		hpbar.setFont(new Font("굴림", Font.BOLD, 13));
		hpbar.setForeground(Color.RED);
		hpbar.setValue(current_player_health);

		// 현재 마나
		JLabel lbl_mana = new JLabel("M P", SwingConstants.CENTER);
		lbl_mana.setBounds(10, 55, 30, 20);
		lbl_mana.setFont(TEXTFONT);

		mpbar = new JProgressBar(0, c_mp);
		mpbar.setBounds(50, 55, 160, 20);
		mpbar.setStringPainted(true);
		mpbar.setString(String.valueOf(current_player_mana) + " / " + String.valueOf(c_mp));
		mpbar.setFont(new Font("굴림", Font.BOLD, 13));
		mpbar.setForeground(Color.BLUE);
		mpbar.setValue(current_player_mana);

		/** 버튼 패널 & 버튼들 설정 **/
		btnPanel = new JPanel(null);
		btnPanel.setBounds(35, 550, 425, 100);

		btnSearch = new JButton("탐색하기");
		btnSearch.setBounds(15, 18, 90, 60);
		btnSearch.setFont(new Font("굴림", Font.PLAIN, 14));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonindex = 1; // 1은 탐색하기 버튼
				if(battle) { // 전투가 발생했을때 탐색하기 버튼을 눌리면 알림창 띄움.
					createAlertWindow(buttonindex);
					return;
				}
				int encount = encountnum();
				createEncount(encount); // 생성된 숫자를 통해 인카운트 생성
			}
		});

		btnAttack = new JButton("공격");
		btnAttack.setBounds(120, 5, 90, 40);
		btnAttack.setFont(new Font("굴림", Font.PLAIN, 14));
		btnAttack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonindex = 2; // 2는 공격 버튼
				if (battle) {
					attack_player();
					attack_monster();
				}else {
					createAlertWindow(buttonindex);
				}
			}
		});

		btnSkill = new JButton("스킬");
		btnSkill.setBounds(120, 55, 90, 40);
		btnSkill.setFont(new Font("굴림", Font.PLAIN, 14));
		btnSkill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				current_player_mana -= 10;
			}
		});

		btnInventory = new JButton("인벤토리");
		btnInventory.setBounds(215, 5, 90, 40);
		btnInventory.setFont(new Font("굴림", Font.PLAIN, 14));
		btnInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().toString().indexOf("인벤토리") != 0) {
					btnInventory.setEnabled(false);
				}
				new InventoryScreen(inventory);
			}
		});

		btnEquip = new JButton("장비");
		btnEquip.setBounds(215, 55, 90, 40);
		btnEquip.setFont(new Font("굴림", Font.PLAIN, 14));

		btnExit = new JButton("게임종료");
		btnExit.setBounds(320, 18, 90, 60);
		btnExit.setFont(new Font("굴림", Font.PLAIN, 14));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonindex = 6;
				createAlertWindow(buttonindex);
			}
		});

		/** 패널에 컴포넌트들 add **/
		// 능력치 패널
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

		// 버튼 패널
		btnPanel.add(btnSearch);
		btnPanel.add(btnAttack);
		btnPanel.add(btnExit);
		btnPanel.add(btnInventory);
		btnPanel.add(btnSkill);
		btnPanel.add(btnEquip);

		/** 프레임에 패널 add **/
		add(btnPanel);
		add(statusPanel);
		
		setVisible(true);
		setmonsters(); // 몬스터 정보들 저장
		setexptable(); // 경험치 테이블 정보 저장
		checkplayerstatus(); // 캐릭터 현재 상태 확인 Thread
		checkmonsterstatus(); // 몹 현재 상태 확인 Thread
	}

	// JTextArea에 로그를 덧붙이는 메소드
	private static void addLog(String txt) {
		log.append(txt+"\n"+"");
		int txtlength = log.getText().length(); // JTextarea 문자열 총 길이
		log.setCaretPosition(txtlength); // 구한 문자열 길이를 caretposition 설정함
		log.requestFocus(); // 맨 끝으로 이동한 caret 을 기준으로 focus 재설정 하는듯..?
	}

	// 현재 플레이어 상태 체크.
	private void checkplayerstatus() {
		p_check = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("[info] 플레이어 상태 확인 Thread 동작");
					while (!Thread.currentThread().isInterrupted()) {

						/** 플레이어의 공격력 & 방어력 세팅 **/
						playeratk = c_str/2; // 플레이어 공격력(힘 / 2)
						playerdef = c_dex/5; // 플레이어 방어력(민첩 / 5)
						cexp.setText(String.valueOf(c_exp));
						cnextexp.setText(String.valueOf(c_next_exp));
						
						/** Thread가 실행되면서 hpbar & mpbar 에 setValue() 해줌. (setText()도 마찬가지) **/
						hpbar.setString(String.valueOf(current_player_health) + " / " + String.valueOf(c_hp));
						hpbar.setMaximum(c_hp);
						hpbar.setValue(current_player_health);
						mpbar.setString(String.valueOf(current_player_mana) + " / " + String.valueOf(c_mp));
						mpbar.setMaximum(c_mp);
						mpbar.setValue(current_player_mana);
						
						/** 캐릭터 레벨업 체크 **/
						if(c_exp >= c_next_exp) {
							addLog("[System] '"+c_name+"' 의 레벨업!");
							c_lv+=1;
							clv.setText(String.valueOf(c_lv));
							addLog("[System] '"+c_name+"' (은/는) 레벨 " + c_lv+" 가 되었다!\n");
							c_next_exp = exptable.get(c_lv).getNextexp();
							cnextexp.setText(String.valueOf(c_next_exp));
							int strup = (int)(Math.random()*5)+1; // 1 ~ 5 힘 능력치 업
							int dexup = (int)(Math.random()*5)+1; // 1 ~ 5 민첩 능력치 업
							int intup = (int)(Math.random()*5)+1; // 1 ~ 5 지능 능력치 업
							// 나온 능력치 수치만큼 기존 능력치에 + 시킴
							addLog("[System] 힘이 "+strup+" 올랐다!");
							addLog("[System] 민첩이 "+dexup+" 올랐다!");
							addLog("[System] 지능이 "+intup+" 올랐다!\n");
							c_str += strup;
							c_dex += dexup;
							c_int += intup;
							
							// 10 레벨 이전까지는 레벨업하면 체력에 + 50, 마나에 + 30
							if(c_lv <= 10) {
								c_hp += 50;
								c_mp += 30;
								current_player_health = c_hp; // 레벨업하고 늘어난 총 hp 만큼 현재 hp로 (풀피 회복)
								current_player_mana = c_mp; // (풀 마나 회복)
							}
						}

						/** 현재 플레이어 체력이 0이되면 사망. 게임 종료 **/
						if (current_player_health <= 0) {
							addLog("[System] '"+c_name+"' (이/가) 죽었습니다.");
							createAlertWindow(9);
							Thread.currentThread().interrupt();
						}
					}
				} catch (Exception e) {
					System.out.println("[Error] 플레이어 상태 체크 스레드 에러");
					e.printStackTrace();
				}
			}
		});
		p_check.start();
	}

	// 몬스터의 상태 check 몬스터가 사망하면 아이템 획득 & 경험치 획득 등의 처리를 함
	public static void checkmonsterstatus() {
		m_check = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						Thread.sleep(300);
						if(mobkey != null) {
							if(current_enemy_health <= 0) { // 몹의 현재 체력이 0보다 작거나 같을 때 (= 몹 사망)
								mobdead();
							}
						}else {
							System.out.println("[info] 몹 생성이 안돼있음");
						}
					}
				}catch(Exception e) {
					System.out.println("[Error] 몹 확인 Thread 에러");
					e.printStackTrace();	
				}
			}

			
		});
		m_check.setPriority(6);
		m_check.start();
	}

	// 몹 사망 시 실행되는 메소드
	private static void mobdead() {
		addLog("[System] "+mobkey+" (이/가) 쓰러졌다.");
		addLog("[System] 경험치를 "+ monsters.get(mobkey).getMonsterexp() + " 얻었다.\n");
		c_exp += monsters.get(mobkey).getMonsterexp();
		cexp.setText(String.valueOf(c_exp));
		for(int i=0; i<dropitem.length; i++) {
			addLog("[System] '"+mobkey+"' (이/가) "+dropitem[i]+ " (을/를) 떨어뜨렸다.");
			addLog("[System] '"+c_name+"' (은/는) "+dropitem[i]+" (을/를) 가방에 넣었다.");
			inventory.add(dropitem[i]);
		}
		System.out.println("[info] 인벤토리 크기 : "+dropitem.length);
		mobkey = null;
		battle = false;
	}
	
	// 몬스터 정보 저장(DB 연동)
	private static void setmonsters() {
		System.out.println("[info] setmonsters() 실행");
		Connection conn = DBConnection.connectDB();
		String query = "SELECT * FROM MONSTERS";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs != null) {
				System.out.println("[info] DB로부터 몬스터 정보 가져오기 완료");
				while(rs.next()) {
					monsters.put(rs.getString(1),new DungeonMonster(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
				}
				System.out.println("[info] 몬스터 정보 저장 완료");
				System.out.println("[info] 저장된 몬스터 정보 사이즈 : " +monsters.size());
				Set<String> keys = monsters.keySet();
				System.out.println("[info] 저장된 몬스터 정보 이름 : " + keys.toString());
			}
		}catch(SQLException e) {
			System.out.println("[Error] setmonsters() : SQL 실행 에러");
		}finally {
			DBConnectClose.connectClose(conn, pstmt, rs);
			System.out.println("[info] setmonsters() 완료\n");
		}
	}

	// 경험치 테이블 정보 저장(DB 연동)
	private static void setexptable() {
		System.out.println("[info] setexptable() 실행");
		Connection conn = DBConnection.connectDB();
		String query = "SELECT * FROM EXPTABLE";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs != null) {
				System.out.println("[info] DB로부터 경험치 테이블 정보 가져오기 완료");
				while(rs.next()) {
					exptable.put(rs.getInt(1), new DungeonExpTable(rs.getInt(2), rs.getInt(3)));
				}
				System.out.println("[info] 경험치 테이블 정보 저장 완료");
				System.out.println("[info] 경험치 테이블 사이즈 : " +exptable.size());
			}
		}catch(SQLException e) {
			System.out.println("[Error] setexptable() : SQL 실행 에러");
		}finally {
			DBConnectClose.connectClose(conn, pstmt, rs);
			System.out.println("[info] setexptable() 완료\n");
		}
	}
	
	// 인카운터 발생에 필요한 랜덤 숫자 만드는 메소드
	private static int encountnum() {
		int encount = (int) (Math.random() * 3) + 1; // 1 ~ 3 랜덤
		return encount;
	}

	// 인카운트 생성
	public void createEncount(int encount) {
		System.out.println("[info] createEncount() 실행");
		changeBackgroundImage(bgpanel, bgimg);
		System.out.println("[info] 발생된 인카운트 : " + encount);
		switch (encount) {
		case 1:
			battle = true; // 전투가 발생
			createbattle(); // 전투 생성
			System.out.println("[info] 전투 생성완료");
			break;
		case 2:
			changeBackgroundImage(bgpanel, tresureimg);
			addLog("신기해보이는 상자가 있다.\n왠지 열어보면 괜찮은 아이템이 나올 것 같다!\n");
			break;
		case 3:
			createEvent();
			System.out.println("[info] 특수 이벤트 생성 완료");
		}
	}

	// 특수 이벤트 생성
	private static void createEvent() {
		int encount = (int)(Math.random() * 10)+1; // 1 ~ 10 까지 발생
		System.out.println("[info] 특수 이벤트 발생");
		System.out.println("[info] 이벤트 인카운트 : " + encount);
		switch (encount) {
		case 1: case 2: case 3:
			addLog("[" + c_name + "] : 거기 누구 있어요?");
			addLog("[System] ('" + c_name + "'" + "은/는 뒤를 돌아 봤지만 아무도 없었다..)");
			addLog("[" + c_name + "] : 소름 돋네...\n");
			break;
		case 5: case 6:
			addLog("[" + c_name + "] : 집 나오면 개고생이라더니..");
			addLog("[" + c_name + "] : 빨리 집에 가고 싶다..");
			current_player_health--;
			addLog("[System] 피로로 인해 체력이 1 감소했다.\n");
			break;
		case 4: case 7:
			addLog("[" + c_name + "] : 배고프다..");
			addLog("[" + c_name + "] : 여기서 굶어죽으면 엄청 억울할 거 같은데..");
			int hungry = (int) (Math.random() * 3) + 1; // 1 ~ 3 랜덤으로 hp 감소
			current_player_health -= hungry;
			addLog("[System] '" + c_name + "' 의 체력이 " + hungry + " 만큼 감소했다.\n");
			break;
			
		case 8: case 9:
			addLog("["+c_name+"] : ...??");
			addLog("[System] '"+c_name+"' (은/는) 걷다가 문득 깨달음을 얻었다.\n"+"[System] '"+c_name+"' 의"+"경험치가 50 올랐다.\n");
			c_exp += 50;
			break;
			
		case 10:
			addLog("[System] 신기해 보이는 돌멩이를 만져봤다.");
			addLog("[System] '"+c_name+"' 의 경험치가 100 올랐다.");
			c_exp += 100;
		}
	}

	// 전투 생성
	private static void createbattle() {
		System.out.println("[info] createbattle() 실행");
		if (battle) { // 전투가 true 일 경우 전투 시작
			Set<String> mobkeys = monsters.keySet();
			List<String> mobs = new ArrayList<String>(mobkeys);
			int encountmob = (int)(Math.random() * mobs.size()); //
			if(mobs.get(encountmob).equals("햄버거왕")) { // 최강몹
				addLog("[System] '"+c_name+"' (은/는) 위험해 보여서 그 자리서 도망쳤다.\n");
				addLog("["+c_name+"] : 근데 왠지 맛있어 보였는데...");
				battle = false;
				return;
			}
			mobkey = mobs.get(encountmob); // 생성된 몹 키 값을 저장함 (해당 값으로 몹 정보를 가져올 수 있음)
			addLog("[System] 던전에서 헤매던 중 돌아다니던 "+mobkey+" 을/를 발견했다!");
			
			/** 생성될 몬스터가 정해졌으면 해당 몬스터의 체력/공격력/방어력을 각각의 변수에 저장함. **/
			enemy_health = monsters.get(mobkey).getMonsterhp(); // 몹의 원래 체력
			current_enemy_health = enemy_health; // 몹의 현재 체력
			mobatk = monsters.get(mobkey).getMonsteratk(); // 공격력
			mobdef = monsters.get(mobkey).getMonsterdef(); // 방어력
			dropitem = monsters.get(mobkey).getitemDrop(); // 드랍아이템 결정
			
			addLog("[System] "+mobkey+" 의 정보\n체력 : "+monsters.get(mobkey).getMonsterhp()+
					"\n공격력 : "+monsters.get(mobkey).getMonsteratk()+"\n방어력 : "+monsters.get(mobkey).getMonsterdef()+"\n");
			addLog("[System] '"+c_name+"'의 정보\n공격력 : "+playeratk+"\n방어력 : "+playerdef+"\n");
		}
	}

	// 플레이어 공격
	private void attack_player() {
		addLog("[System] '"+c_name+"' 의 공격!");
		int damage = playeratk - mobdef; // 데미지는 플레이어 공격력 - 몹 방어력 으로 결정
		if(damage <= 0) { // 플레이어 공격력 - 몹 방어력 의 결과가 0보다 작거나 같을 경우(= 몹의 방어력이 플레이어 공격력보다 높을 경우)
			damage = 1; // 데미지는 1로 고정됨. (최소 데미지는 무조건 1)
			current_enemy_health-=damage; // damage 수치만큼 몹 현재 체력 감소
			addLog("[System] '"+c_name+"' (은/는) "+mobkey+" 에게 "+damage+" 만큼 피해를 입혔다!");
			addLog("[System] "+mobkey+"의 현재 남은 체력 " + current_enemy_health +" / "+enemy_health+"\n");
		}else {
			int randomdamage = (int)(Math.random() * damage)+1; // 1 ~ 플레이어 데미지 사이의 랜덤데미지 결정
			current_enemy_health-=randomdamage; // randomdamage 수치만큼 몹 현재 체력 감소
			addLog("[System] '"+c_name+"' (은/는) "+mobkey+" 에게 "+randomdamage+" 만큼 피해를 입혔다!");
			addLog("[System] "+mobkey+"의 현재 남은 체력 " + current_enemy_health +" / "+enemy_health+"\n");
		}
	}

	// 몹 공격
	private void attack_monster() {
		addLog("[System] '"+mobkey+"' 의 공격!");
		int damage = mobatk - playerdef;
		if(damage <= 0) {
			damage = 1; // 데미지는 1로 고정됨. (최소 데미지는 무조건 1)
			current_player_health-=damage; // damage 수치만큼 몹 현재 체력 감소
			addLog("[System] '"+mobkey+"' (은/는) '"+c_name+"' 에게 "+ damage + " 만큼 피해를 입혔다!\n");
		}else {
			int randomdamage = (int)(Math.random() * damage)+1; // 1 ~ 플레이어 데미지 사이의 랜덤데미지 결정
			current_player_health-=randomdamage;
			addLog("[System] '"+mobkey+"' (은/는) '"+c_name+"' 에게 "+ randomdamage + " 만큼 피해를 입혔다!\n");
		}
	}

	// 알림창 띄우는 메소드
	private void createAlertWindow(int btnindex) {
		JLabel warntext;
		int result;
		switch (btnindex) {
		case 1: // 탐색하기 버튼
			warntext = new JLabel("<html>전투 중에는 탐색이 불가능합니다.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "탐색하기", JOptionPane.ERROR_MESSAGE);
			break;
		case 2: // 공격버튼
			if(!battle) {
				warntext = new JLabel("<html>공격할 몬스터가 없습니다</html>");
				warntext.setFont(TEXTFONT);
				JOptionPane.showMessageDialog(this, warntext, "공격", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 3: // 도망치기 버튼
			warntext = new JLabel("<html>몬스터가 존재하지 않습니다.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "도망치기", JOptionPane.ERROR_MESSAGE);
			break;
		case 6: // 게임종료 버튼
			warntext = new JLabel("<html>게임을 종료하시겠습니까?<br/>진행상황은 저장되지 않습니다.</html>");
			warntext.setFont(TEXTFONT);
			result = JOptionPane.showConfirmDialog(null, warntext, "게임종료", 
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
			warntext = new JLabel("<html>캐릭터가 사망하였습니다.<br/>게임을 종료합니다.</html>");
			warntext.setFont(TEXTFONT);
			JOptionPane.showMessageDialog(this, warntext, "게임종료", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			break;
		}
	}

	// 배경화면 바꾸는 메소드
	private void changeBackgroundImage(JPanel p, Image img) {
		BackgroundPanel.setImg(img);
		p.repaint();
	}
	
	// 인벤토리 버튼 활성화 & 비활성화 위한 getbutton
	public static JButton getInventorybutton() {
		return btnInventory;
	}
	
	public static JButton getStatusbutton() {
		return btnShowStatus;
	}
	
	// 인벤토리 정보 저장
	public static void setInventory(LinkedList<String> inventory){
		GameScreen.inventory = inventory;
	}
}