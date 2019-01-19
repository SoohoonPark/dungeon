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

// �κ��丮(����) ȭ�� �����ִ� Ŭ����
@SuppressWarnings("serial")
public class InventoryScreen extends JFrame {
	private static JList<String> inventory; // �κ��丮 ����Ʈ(���)
	private LinkedList<String> inventorydata = new LinkedList<String>(); // �κ��丮 ����Ʈ ������
	private DefaultListModel<String> model = null;
	
	public InventoryScreen(LinkedList<String> inventory) {
		System.out.println("[info] �κ��丮 ����");
		this.inventorydata = inventory;
		createInventoryScreen();
	}
	
	public void createInventoryScreen() {
		setTitle("�κ��丮");
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
		
		JButton btnUseitem = new JButton("���");
		btnUseitem.setBounds(10, 10, 80, 30);
		btnUseitem.setFont(new Font("����", Font.PLAIN, 14));
		btnUseitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// ������ ��� �޼ҵ� ȣ��
				useItem(inventory.getSelectedValue());
			}
		});
		
		JButton btnDropitem = new JButton("������");
		btnDropitem.setBounds(10, 50, 80, 30);
		btnDropitem.setFont(new Font("����", Font.PLAIN, 14));
		btnDropitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("[info] ������ ������ �� ũ�� : "+inventorydata.size());
					inventorydata.remove(inventory.getSelectedIndex());
					System.out.println("[info] ������ ������ ���� �� : "+inventorydata.size());
					model.clear(); // ������ ���Ű� �Ǹ� �ϴ� model ���� ���빰�� clear ��Ŵ
					for(int i=0; i<inventorydata.size(); i++) {
						model.addElement(inventorydata.get(i)); // �ݺ����� ���� model�� ���� ������ add
						System.out.println(model.get(i));
					}
					inventory.setModel(model); // JList�� model�� set�ؼ� ������ ���� �� �κ��丮 ������
				}catch(Exception exception) {
					System.out.println("[Error] �κ��丮�� ����־ �����⸦ �� �� �����ϴ�.");
				}
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
				// GameScreen �� �κ��丮 ��ư�� ������
				JButton btninventory = GameScreen.getInventorybutton();
				// GameScreen �� �κ��丮 ������ ���� �κ��丮 ���빰�� �ѱ��
				GameScreen.setInventory(inventorydata);
				System.out.println("[info] ���� �κ��丮 ���� ���� �Ϸ�");
				// GameScreen�� �κ��丮 ��ư Ȱ��ȭ
				btninventory.setEnabled(true);
			}
		});
	}
	
	// ������ ���
	public void useItem(String selecteditem) {
		GameScreen.addLog(selecteditem);
	}
}
