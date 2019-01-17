package miniProject;

/** 
 * 몬스터의 정보들을 담고 있는 클래스 
 * (이름, 공격력, 방어력, 경험치, 드랍템)
 * 해당 클래스를 통해 몬스터 정보들이 담겨있는 객체를 생성하게 됨.
 **/ 
public class DungeonMonster {
	private String monstername; // 몹 이름
	private int monsterhp; // 몹 체력
	private int monsteratk; // 몹 공격력
	private int monsterdef; // 몹 방어력
	private int monsterexp; // 몹 경험치
	private String monsterdrop; // 몹 드랍템
	
	// 생성자
	public DungeonMonster(String name, int hp ,int atk, int def, int exp, String drop) {
		this.monstername = name;
		this.monsterhp = hp;
		this.monsteratk = atk;
		this.monsterdef = def;
		this.monsterexp = exp;
		this.monsterdrop = drop; // DB로 부터 가져온 데이터는 String 형식. 해당 String을 콤마(,) 단위로 쪼개서 배열로 다시 저장함
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

	// String 으로 받아온 드랍리스트를 콤마(,)를 기준으로 쪼개서 String[] 으로 가져옴
	public String[] getMonsterdrop() {
		String[] drop = monsterdrop.split(",");
		return drop;
	}
	
	// 아이템 드랍
	public String[] getitemDrop() {
		String[] droplist = monsterdrop.split(",");
		String[] dropitem = new String[2];
		System.out.println("[info] droplist length is : " + droplist.length);
		for(int i=0; i<dropitem.length; i++) {
			int dropindex = (int)(Math.random() * droplist.length); // 0 ~ droplist size() 까지
			dropitem[i] = droplist[dropindex];
			for(int j=0; j<i; j++) {
				if(dropitem[i] == dropitem[j]) { // 드랍아이템 중복 제거 (똑같은거 2개 나오는거 방지)
					i--;
				}
			}
		}
		return dropitem;
	}
}
