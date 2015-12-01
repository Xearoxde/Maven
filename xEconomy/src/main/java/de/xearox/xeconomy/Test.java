package de.xearox.xeconomy;

public class Test {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerObject players = new PlayerObject();
		fillPlayers(players);
		printPlayers(players);
	}

	public static PlayerObject fillPlayers(PlayerObject players){
		players.playerUUId = "PlayerUUID";
		players.playerName = "Xearox";
		players.pocketCurrency = 100;
		players.premiumCurrency = 10;
		players.employedAt = "Xearox Corp";
		players.employedAs = "CEO";
		players.bankAccountAt = "Central Bank";
		return players;
	}
	
	public static void printPlayers(PlayerObject players){
		System.out.println(players.playerUUId);
		System.out.println(players.playerName);
		System.out.println(players.pocketCurrency);
		System.out.println(players.premiumCurrency);
		System.out.println(players.employedAt);
		System.out.println(players.employedAs);
		System.out.println(players.bankAccountAt);
	}
}
