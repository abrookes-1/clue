package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	@Override
	public String toString() {
		if (person.equals("") && weapon.equals("") && room.equals("")) {
			return "";
		}
		return this.person + " with " + this.weapon + " in " + this.room;
	}
	
}
