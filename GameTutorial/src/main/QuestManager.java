package main;

import java.util.ArrayList;

import entity.Player;

import java.util.*;

public class QuestManager {
	
	//public ArrayList<String> completedQuests = new ArrayList<String>();
	public boolean quest1Complete;
	public boolean quest1Claimed;
	
	public HashMap<Integer, String> currentQuests;// = new HashMap<Integer, String>();
	public HashMap<Integer, String> completedQuests;// = new HashMap<Integer, String>();
	

	public QuestManager() {
		currentQuests = new HashMap<Integer, String>();
		completedQuests = new HashMap<Integer, String>();
	}
	
	public void quest1(int slimeKillCount,Player player) {
		if(currentQuests.get(1)==null) {
			return;
		}
		if(slimeKillCount>=2) {
			quest1Complete = true;
			String questName = currentQuests.remove(1);
			completedQuests.put(1,questName);
			System.out.println("-->" + completedQuests.get(1));
			player.hammerUnlock=true;
		}
		
	}
}
