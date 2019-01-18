package miniProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class InventoryScreen extends JFrame {
	private static JList<String> inventorylist; // 인벤토리 리스트(출력)
	private static LinkedList<String> inventory = new LinkedList<String>(); // 인벤토리 리스트
	private static String[] inventorydata; // 인벤토리 리스트 데이터
	private static Thread checkinventory; // 인벤토리 확인 Thread

	public InventoryScreen(LinkedList<String> inventory) {
		System.out.println("[info] 인벤토리 열림");
		InventoryScreen.inventory = inventory;
		createInventoryScreen();
		runThread();
	}

	public void createInventoryScreen() {
		setTitle("인벤토리");
		setBounds(1210, 495, 350, 210);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		inventorydata = inventory.toArray(new String[inventory.size()]);
		inventorylist = new JList<String>(inventorydata);
		JScrollPane scroll = new JScrollPane(inventorylist);
		scroll.setBounds(10, 10, 200, 160);
		
		JPanel panelButtons = new JPanel(null);
		panelButtons.setBounds(220, 10, 100, 160);
		panelButtons.setBorder(new LineBorder(Color.RED));
		
		JButton btnUseitem = new JButton("사용");
		btnUseitem.setBounds(10, 10, 80, 30);
		btnUseitem.setFont(new Font("굴림", Font.PLAIN, 14));
		btnUseitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inventorylist.getSelectedValue().equals("체력 물약")) {
					System.out.println("[info] 체력 물약 사용");
				}else if(inventorylist.getSelectedValue().indexOf("마나 물약") != 0) {
					System.out.println("[info] 마나 물약 사용");
				}
			}
		});
		
		JButton btnDropitem = new JButton("버리기");
		btnDropitem.setBounds(10, 50, 80, 30);
		btnDropitem.setFont(new Font("굴림", Font.PLAIN, 14));
		btnDropitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(10, 90, 80, 30);
		btnClose.setFont(new Font("굴림", Font.PLAIN, 14));
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		panelButtons.add(btnUseitem);
		panelButtons.add(btnDropitem);
		panelButtons.add(btnClose);
		
		add(scroll);
		add(panelButtons);
		setVisible(true);
	
		// 윈도우리스너 (해당 창이 닫히면 리스너에서 GameScreen의 능력치 버튼을 활성화(setEnabled(true)) 해줌.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				JButton btninventory = GameScreen.getInventorybutton();
				LinkedList<String> inventory = getInventory();
				System.out.println("[info] 현재 인벤토리 크기 : "+ inventory.size());
				GameScreen.setInventory(inventory);
				System.out.println("[info] 현재 인벤토리 상태 저장 완료");
				checkinventory.interrupt();
				if(checkinventory.isInterrupted()) {
					System.out.println("[info] 인벤토리 확인 Thread 종료");
				}
				btninventory.setEnabled(true);
			}
		});
	}
	
	// GameScreen 에서 인벤토리 내용 가져오기 위한 메소드(인벤토리 최신화)
	public LinkedList<String> getInventory(){
		return inventory;
	}
	
	// 인벤토리 확인 Thread
	void runThread() {
		System.out.println("[info] 인벤토리 확인 Thread 동작");
		checkinventory = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						System.out.println("[info] 인벤토리 크기 : " + inventory.size());
						inventorydata = inventory.toArray(new String[inventory.size()]);
						inventorylist = new JList<String>(inventorydata);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		checkinventory.start();
	}
}
