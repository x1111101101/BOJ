package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
백준 1315 - RPG
*/
public class Main {
    
    public static void main(String[] args) throws IOException {
        // StringReader test = new StringReader("10\n1 1 1\n10 5 1\n1 1 1\n2 8 2\n16 3 1\n12 5 1\n13 3 2\n19 16 3\n12 19 5\n8 20 1");
        ArrayList<Quest> allQuests = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //(test);
    	int lines = Integer.parseInt(br.readLine());
        for(int i = 0; i < lines; i++) {
            String line = br.readLine();
            String split[] = line.split("\\s");
        	Quest q = new Quest(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            allQuests.add(q);
        }
        /*
        1. 선정
        2. 포인트 적립
        3. 선정 분기(선점한 퀘스트에 필요한 만큼만 포인트 사용)
        4. goto 1
        */
        SearchHelper firstHelper = new SearchHelper();
        firstHelper.player = new Player();
        firstHelper.uncleared = new HashSet();
        for(Quest q : allQuests) {
            firstHelper.uncleared.add(new QuestPlan(true, q));
            firstHelper.uncleared.add(new QuestPlan(false, q));
        }
        ArrayList<SearchHelper> list = new ArrayList<SearchHelper>();
        list.add(firstHelper);
        //dfs
        /*
        {1,2}
        {2}
        {}
        
        */
        
        
        /*
        {1,2,3}
        {3} (1)
        
        
        */
        
        int maxCleared = 0;
        
        loop1:
        while(!list.isEmpty()) {
            // d("DEPTH: " + list.size());
            SearchHelper helper = list.get(list.size()-1);
            // d("Uncleared: " + join(", ", helper.uncleared));
			
            
            Iterator<QuestPlan> quests = helper.uncleared.iterator();
            while(quests.hasNext()) {
                QuestPlan uncleared = quests.next();
                if(helper.cleared.contains(uncleared.quest)) {
                    continue;
                }
                SearchHelper copiedHelper = helper.copy();
                copiedHelper.questPlan = uncleared;
                if(uncleared.quest.tryClear(copiedHelper.player, uncleared.plan)) {
                    copiedHelper.uncleared.remove(uncleared);
                    copiedHelper.cleared.add(uncleared.quest);
                    list.add(copiedHelper);
                    int clearedCount = copiedHelper.cleared.size();
                    if(maxCleared < clearedCount) {
                        maxCleared = clearedCount;
                    }
                    continue loop1;
                }
            } 
            list.remove(list.size()-1);
            if(!list.isEmpty()) {
             	list.get(list.size()-1).uncleared.remove(helper.questPlan);   
            }
            
        }
        System.out.println(maxCleared);
    }
    
    static String join(String s, Collection<?> c) {
        StringJoiner j = new StringJoiner(s);
        c.forEach(e->{
            j.add(e.toString());
        });
        return j.toString();
    }
    
    static class SearchHelper {
        HashSet<QuestPlan> uncleared;
        HashSet<Quest> cleared = new HashSet();
        Player player;
        QuestPlan questPlan;
        
        SearchHelper copy() {
            SearchHelper copy = new SearchHelper();
        	copy.uncleared = new HashSet();
			copy.uncleared.addAll(uncleared);
            copy.player = player.copy();
            copy.questPlan = questPlan;
            copy.cleared = new HashSet();
            copy.cleared.addAll(cleared);
            return copy;
        }
    }
    
    static class QuestPlan {
        final boolean plan; // true: str, false: intelli
        final Quest quest;
        QuestPlan(boolean plan, Quest quest) {
            this.plan = plan;
            this.quest = quest;
        }
    }
    
    static class Player {
        int strength = 1;
        int intelli = 1;
        int point = 0;
        
        Player copy() {
            Player p = new Player();
            p.strength = strength;
            p.intelli = intelli;
            p.point = point;
            return p;
        }
    }
    
    
    static class Quest {
    
        final int reward;
        final int strength;
        final int intelli;
        
        Quest(int str, int intelligence, int reward) {
            this.reward = reward;
            this.strength = str;
            this.intelli = intelligence;
        }
        
        boolean tryClear(Player p, boolean strFirst) {
            if(strength <= p.strength) {
                p.point += reward;
                return true;
            }
            if(intelli <= p.intelli) {
                p.point += reward;
                return true;
            }
            
            if(strFirst) {
            	int requireMore = strength - p.strength;
                if(p.point < requireMore) {
                    return false;
                }
                p.point -= requireMore;
                p.strength += requireMore;
                p.point += reward;
            } else {
                int requireMore = intelli - p.intelli;
                if(p.point < requireMore) {
                    return false;
                }
                p.point -= requireMore;
                p.intelli += requireMore;
                p.point += reward;
            }
            return true;
        }
    }
    
    /*
    
    void work(Player p, HashSet<Quest> uncleared) {
        for(Quest q : uncleared) {
            Player copy = p.copy();
            
        }
    }
    */
}
