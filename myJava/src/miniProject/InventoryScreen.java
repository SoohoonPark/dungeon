package miniProject;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class InventoryScreen extends JFrame {
	private List<String> inventory = new LinkedList<String>();

	public InventoryScreen(List<String> inventory) {
		System.out.println("[info] 인벤토리 열림");
		this.inventory = inventory;
		System.out.println("[info] 인벤토리 크기 : " + inventory.size());
		createInventoryScreen();
	}

	public void createInventoryScreen() {
		setTitle("인벤토리");
		setBounds(1210, 495, 200, 200);
		setVisible(true);

		// 윈도우리스너 (해당 창이 닫히면 리스너에서 GameScreen의 능력치 버튼을 활성화(setEnabled(true)) 해줌.
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				GameScreen.btnInventory.setEnabled(true);
			}
		});
	}
}
