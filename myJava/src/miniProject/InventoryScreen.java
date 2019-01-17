package miniProject;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class InventoryScreen extends JFrame {
	private List<String> inventory = new LinkedList<String>();

	public InventoryScreen(List<String> inventory) {
		System.out.println("[info] 인벤토리 열림");
		this.inventory = inventory;
		//System.out.println("[info] 인벤토리 크기 : " + inventory.size());
		createInventoryScreen();
	}

	public void createInventoryScreen() {
		setTitle("인벤토리");
		setBounds(1210, 495, 300, 210);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DefaultListModel<String> inventorydata = new DefaultListModel<String>();
		JList<String> inventorylist = new JList<String>(inventorydata);
		JScrollPane scroll = new JScrollPane(inventorylist);
		scroll.setBounds(10, 10, 200, 160);
		
		
		JPanel panelButtons = new JPanel(null);
		panelButtons.setBounds(220, 10, 60, 160);
		panelButtons.setBorder(new LineBorder(Color.RED));
		
		JButton btnUseitem = new JButton("사용");
		JButton btnDropitem = new JButton("버리기");
		JButton btnClose = new JButton("닫기");
		
		add(scroll);
		add(panelButtons);
		setVisible(true);
	
		// 윈도우리스너 (해당 창이 닫히면 리스너에서 GameScreen의 능력치 버튼을 활성화(setEnabled(true)) 해줌.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.out.println("앗");
				GameScreen.btnInventory.setEnabled(true);
			}
		});
	}
	
	public static void main(String[] args) {
		new InventoryScreen(null);
	}
}
