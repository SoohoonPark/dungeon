package miniProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private LinkedList<String> inventory = new LinkedList<String>();

	public InventoryScreen(LinkedList<String> inventory) {
		System.out.println("[info] �κ��丮 ����");
		this.inventory = inventory;
		System.out.println("[info] �κ��丮 ũ�� : " + this.inventory.size());
		createInventoryScreen();
	}

	public void createInventoryScreen() {
		setTitle("�κ��丮");
		setBounds(1210, 495, 350, 210);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] inventorydata = inventory.toArray(new String[inventory.size()]);
		JList<String> inventorylist = new JList<String>(inventorydata);
		JScrollPane scroll = new JScrollPane(inventorylist);
		scroll.setBounds(10, 10, 200, 160);
		
		JPanel panelButtons = new JPanel(null);
		panelButtons.setBounds(220, 10, 100, 160);
		panelButtons.setBorder(new LineBorder(Color.RED));
		
		JButton btnUseitem = new JButton("���");
		btnUseitem.setBounds(10, 10, 80, 30);
		btnUseitem.setFont(new Font("����", Font.PLAIN, 14));
		
		JButton btnDropitem = new JButton("������");
		btnDropitem.setBounds(10, 50, 80, 30);
		btnDropitem.setFont(new Font("����", Font.PLAIN, 14));
		btnDropitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
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
				
				GameScreen.btnInventory.setEnabled(true);
			}
		});
	}
}
