package miniProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

// 인벤토리(가방) 화면 보여주는 클래스
@SuppressWarnings("serial")
public class InventoryScreen extends JFrame {
	private static JList<String> inventory; // 인벤토리 리스트(출력)
	private LinkedList<String> inventorydata = new LinkedList<String>(); // 인벤토리 리스트 데이터
	private DefaultListModel<String> model = null;
	
	public InventoryScreen(LinkedList<String> inventory) {
		System.out.println("[info] 인벤토리 열림");
		this.inventorydata = inventory;
		createInventoryScreen();
	}
	
	public void createInventoryScreen() {
		setTitle("인벤토리");
		setBounds(1210, 495, 350, 210);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		model = new DefaultListModel<String>();
		for(int i=0; i<inventorydata.size(); i++) {
			model.addElement(inventorydata.get(i));
		}
		
		inventory = new JList<String>(model);
		JScrollPane scroll = new JScrollPane(inventory);
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
				// 아이템 사용 메소드 호출
				useItem(inventory.getSelectedValue());
			}
		});
		
		JButton btnDropitem = new JButton("버리기");
		btnDropitem.setBounds(10, 50, 80, 30);
		btnDropitem.setFont(new Font("굴림", Font.PLAIN, 14));
		btnDropitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("[info] 아이템 버리기 전 크기 : "+inventorydata.size());
					inventorydata.remove(inventory.getSelectedIndex());
					System.out.println("[info] 아이템 데이터 제거 후 : "+inventorydata.size());
					model.clear(); // 아이템 제거가 되면 일단 model 안의 내용물을 clear 시킴
					for(int i=0; i<inventorydata.size(); i++) {
						model.addElement(inventorydata.get(i)); // 반복문을 통해 model에 남은 아이템 add
						System.out.println(model.get(i));
					}
					inventory.setModel(model); // JList에 model을 set해서 아이템 버린 후 인벤토리 보여줌
				}catch(Exception exception) {
					System.out.println("[Error] 인벤토리가 비어있어서 버리기를 할 수 없습니다.");
				}
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
				// GameScreen 의 인벤토리 버튼을 가져옴
				JButton btninventory = GameScreen.getInventorybutton();
				// GameScreen 의 인벤토리 정보에 현재 인벤토리 내용물을 넘긴다
				GameScreen.setInventory(inventorydata);
				System.out.println("[info] 현재 인벤토리 상태 저장 완료");
				// GameScreen의 인벤토리 버튼 활성화
				btninventory.setEnabled(true);
			}
		});
	}
	
	// 아이템 사용
	public void useItem(String selecteditem) {
		GameScreen.addLog(selecteditem);
	}
}
