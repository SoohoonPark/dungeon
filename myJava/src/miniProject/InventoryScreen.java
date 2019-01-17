package miniProject;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class InventoryScreen extends JFrame {
	private List<String> inventory = new LinkedList<String>();

	public InventoryScreen(List<String> inventory) {
		System.out.println("[info] �κ��丮 ����");
		this.inventory = inventory;
		System.out.println("[info] �κ��丮 ũ�� : " + inventory.size());
		createInventoryScreen();
	}

	public void createInventoryScreen() {
		setTitle("�κ��丮");
		setBounds(1210, 495, 200, 200);
		setVisible(true);

		// �����츮���� (�ش� â�� ������ �����ʿ��� GameScreen�� �ɷ�ġ ��ư�� Ȱ��ȭ(setEnabled(true)) ����.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				GameScreen.btnInventory.setEnabled(true);
			}
		});
	}
}
