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
	private static JList<String> inventorylist; // �κ��丮 ����Ʈ(���)
	private static LinkedList<String> inventory = new LinkedList<String>(); // �κ��丮 ����Ʈ
	private static String[] inventorydata; // �κ��丮 ����Ʈ ������
	private static Thread checkinventory; // �κ��丮 Ȯ�� Thread

	public InventoryScreen(LinkedList<String> inventory) {
		System.out.println("[info] �κ��丮 ����");
		InventoryScreen.inventory = inventory;
		createInventoryScreen();
		runThread();
	}

	public void createInventoryScreen() {
		setTitle("�κ��丮");
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
		
		JButton btnUseitem = new JButton("���");
		btnUseitem.setBounds(10, 10, 80, 30);
		btnUseitem.setFont(new Font("����", Font.PLAIN, 14));
		btnUseitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inventorylist.getSelectedValue().equals("ü�� ����")) {
					System.out.println("[info] ü�� ���� ���");
				}else if(inventorylist.getSelectedValue().indexOf("���� ����") != 0) {
					System.out.println("[info] ���� ���� ���");
				}
			}
		});
		
		JButton btnDropitem = new JButton("������");
		btnDropitem.setBounds(10, 50, 80, 30);
		btnDropitem.setFont(new Font("����", Font.PLAIN, 14));
		btnDropitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnClose = new JButton("�ݱ�");
		btnClose.setBounds(10, 90, 80, 30);
		btnClose.setFont(new Font("����", Font.PLAIN, 14));
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
	
		// �����츮���� (�ش� â�� ������ �����ʿ��� GameScreen�� �ɷ�ġ ��ư�� Ȱ��ȭ(setEnabled(true)) ����.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				JButton btninventory = GameScreen.getInventorybutton();
				LinkedList<String> inventory = getInventory();
				System.out.println("[info] ���� �κ��丮 ũ�� : "+ inventory.size());
				GameScreen.setInventory(inventory);
				System.out.println("[info] ���� �κ��丮 ���� ���� �Ϸ�");
				checkinventory.interrupt();
				if(checkinventory.isInterrupted()) {
					System.out.println("[info] �κ��丮 Ȯ�� Thread ����");
				}
				btninventory.setEnabled(true);
			}
		});
	}
	
	// GameScreen ���� �κ��丮 ���� �������� ���� �޼ҵ�(�κ��丮 �ֽ�ȭ)
	public LinkedList<String> getInventory(){
		return inventory;
	}
	
	// �κ��丮 Ȯ�� Thread
	void runThread() {
		System.out.println("[info] �κ��丮 Ȯ�� Thread ����");
		checkinventory = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						System.out.println("[info] �κ��丮 ũ�� : " + inventory.size());
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
