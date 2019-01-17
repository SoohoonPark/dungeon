package miniProject;

public class DungeonExpTable {
	private int level; // 레벨
	private int sumexp; // 누적경험치
	private int nextexp; // 다음경험치
	
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
