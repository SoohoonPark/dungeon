package miniProject;

public class DungeonExpTable {
	private int level; // ����
	private int sumexp; // ��������ġ
	private int nextexp; // ��������ġ
	
	public DungeonExpTable(int sumexp, int nextexp) {
		this.sumexp = sumexp;
		this.nextexp = nextexp;
	}

	public int getSumexp() {
		return sumexp;
	}

	public int getNextexp() {
		return nextexp;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
}
