package miniProject;

/** 
 * ������ �������� ��� �ִ� Ŭ���� 
 * (�̸�, ���ݷ�, ����, ����ġ, �����)
 * �ش� Ŭ������ ���� ���� �������� ����ִ� ��ü�� �����ϰ� ��.
 **/ 
public class DungeonMonster {
	private String monstername; // �� �̸�
	private int monsterhp; // �� ü��
	private int monsteratk; // �� ���ݷ�
	private int monsterdef; // �� ����
	private int monsterexp; // �� ����ġ
	private String monsterdrop; // �� �����
	
	// ������
	public DungeonMonster(String name, int hp ,int atk, int def, int exp, String drop) {
		this.monstername = name;
		this.monsterhp = hp;
		this.monsteratk = atk;
		this.monsterdef = def;
		this.monsterexp = exp;
		this.monsterdrop = drop; // DB�� ���� ������ �����ʹ� String ����. �ش� String�� �޸�(,) ������ �ɰ��� �迭�� �ٽ� ������
	}
	
	public String getMonstername() {
		return monstername;
	}
	
	public int getMonsterhp() {
		return monsterhp;
	}
	
	public int getMonsteratk() {
		return monsteratk;
	}

	public int getMonsterdef() {
		return monsterdef;
	}

	public int getMonsterexp() {
		return monsterexp;
	}

	// String ���� �޾ƿ� �������Ʈ�� �޸�(,)�� �������� �ɰ��� String[] ���� ������
	public String[] getMonsterdrop() {
		String[] drop = monsterdrop.split(",");
		return drop;
	}
	
	// ������ ���
	public String[] getitemDrop() {
		String[] droplist = monsterdrop.split(",");
		String[] dropitem = new String[2];
		System.out.println("[info] droplist length is : " + droplist.length);
		for(int i=0; i<dropitem.length; i++) {
			int dropindex = (int)(Math.random() * droplist.length); // 0 ~ droplist size() ����
			dropitem[i] = droplist[dropindex];
			for(int j=0; j<i; j++) {
				if(dropitem[i] == dropitem[j]) { // ��������� �ߺ� ���� (�Ȱ����� 2�� �����°� ����)
					i--;
				}
			}
		}
		return dropitem;
	}
}
