package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
백준 1315 - RPG
계산은 정확하나, 메모이제이션이 부족해 메모리 초과.
결국 검색을 해서 메모이제이션이 무엇인지, 어떤 부분을 메모이제이션 할 수 있었는데 못했는지 알게 됨.
다이나믹 프로그래밍을 좀 더 공부해서 돌아오자.


*/
public class Main {
    
    public static void main(String[] args) throws IOException {
        //String s38 = "38\n1 1 1\n2 100 2\n100 3 3\n5 100 4\n100 7 5\n10 100 6\n100 13 7\n17 100 8\n100 21 1\n1 1 1\n10 5 1\n1 1 1\n2 8 2\n16 3 1\n12 5 1\n13 3 2\n19 16 3\n12 19 5\n8 20 1\n1 1 1\n2 100 2\n100 3 3\n5 100 4\n100 7 5\n10 100 6\n100 13 7\n17 100 8\n100 21 1\n1 1 1\n10 5 1\n1 1 1\n2 8 2\n16 3 1\n12 5 1\n13 3 2\n19 16 3\n12 19 5\n8 20 1";
        //String s7 = "10\n1 1 1\n10 5 1\n1 1 1\n2 8 2\n16 3 1\n12 5 1\n13 3 2\n19 16 3\n12 19 5\n8 20 1";
        //String s0 = "1\n3 2 1";
        //StringReader test = new StringReader(sss);
        ArrayList<Quest> allQuests = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //(test);
    	int lines = Integer.parseInt(br.readLine());
        for(int i = 0; i < lines; i++) {
            String line = br.readLine();
            String split[] = line.split("\\s");
        	Quest q = new Quest(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            allQuests.add(q);
        }
        br.close();
        
        int totalPoint = 0;
        for(Quest q : allQuests) {
            totalPoint += q.reward;
        }
        LinkedList<Quest> toRemove = new LinkedList<>();
        for(Quest q: allQuests) {
            if(Math.min(q.strength, q.intelli) > totalPoint+1) {
                toRemove.add(q);
            }
        }
        for(Quest q: toRemove) {
            allQuests.remove(q);
        }
        toRemove.clear();
        
        int qn = 0;
        for(Quest q: allQuests) {
            q.num = qn++;
        }
        
        /*
        allQuests.sort((a,b)-> {
            int minA = Math.min(a.strength, a.intelli);
            int minB = Math.min(b.strength, b.intelli);
            if(minA < minB) {
                return 1;
            } if(minB > minA) {
                return -1;
            } return 0; 
        });
        */
        /*
        0. 현재 스탯으로 해결가능한건 일괄 처리 -> 이 0번째 발상을 못해서 메모리가 계속 초과됨. 이 블로그를 참고함 https://glanceyes.tistory.com/entry/BOJ-%EB%B0%B1%EC%A4%80-1315%EB%B2%88-RPG 
        1. 선정
        2. 포인트 적립
        3. 선정 분기(선점한 퀘스트에 필요한 만큼만 포인트 사용)
        4. goto 0
        */
        SearchHelper firstHelper = new SearchHelper();
        firstHelper.player = new Player();
        QuestPlan[] allPlans = new QuestPlan[allQuests.size()*2];
        int index = 0;
        for(Quest q : allQuests) {
            allPlans[index++] = (new QuestPlan(true, q));
            allPlans[index++] = (new QuestPlan(false, q));
        }
        ArrayList<SearchHelper> list = new ArrayList<SearchHelper>(allPlans.length);
        list.add(firstHelper);
        
        int maxCleared = 0;
        
        loop1:
        while(!list.isEmpty()) {
            //d("DEPTH: " + list.size());
            int depth = list.size()-1;
            SearchHelper helper = list.get(list.size()-1);
            //System.out.println("depth: " + depth);
            //System.out.println("[ START ] - " + helper.questPlan);
            
			if(depth > maxCleared) {
                maxCleared = depth;
            }
            

            // 쓸모없는 연산 방지
            // ERR HERE
            //HashSet<Integer> searched;
            int canBeSearched = 0;
            for(Quest q : allQuests) {
                QuestPlan plan = allPlans[q.num*2];
                if(!helper.contains(plan) || !helper.contains(allPlans[q.num*2+1])) {
               		if(helper.questPlan != null) {
                        if(helper.questPlan.quest.num == q.num) {
                            continue;
                        }
                    }
                    canBeSearched++;  
                }
            }
            if(canBeSearched+1 <= maxCleared) {
                //System.out.println("cut");
                list.remove(list.size()-1);
                //if(!list.isEmpty()) {
                 //   list.get(list.size()-1).cleared.add(helper.questPlan);
               // }
                continue;
            }
            
            loop2:
            for(QuestPlan uncleared : allPlans) {
                // System.out.println(helper.questPlan + " vs " + uncleared);
                
                boolean inspectSelf = helper.contains(uncleared);
                if(inspectSelf) {
                    continue loop2;
                }
                
                int inspectedContext = helper.inspectContextIncludeParent(uncleared);
                if(inspectedContext != -1) {
                    continue loop2;
                }
                //System.out.println("NOT CONTINUED");
                
                
                SearchHelper copiedHelper = helper.createChild();
                copiedHelper.questPlan = uncleared;
                helper.cleared.add(uncleared);
                if(uncleared.quest.tryClear(copiedHelper.player, uncleared.plan)) {
                    list.add(copiedHelper);
                    continue loop1;
                }
            }
            
            list.remove(list.size()-1);
        }
        System.out.println(maxCleared);
    }
    
    static void d(String s) {
        //System.out.println(s);
    }
    
    static class SearchHelper {
        Player player;
        QuestPlan questPlan;
        LinkedList<QuestPlan> cleared = new LinkedList<>();
        SearchHelper parent;
        int bonus = 0;
        
        SearchHelper createChild() {
            SearchHelper copy = new SearchHelper();
            copy.player = player.copy();
            copy.parent = this;
            return copy;
        }
        
        /**
        -1: 포함하지 않음
        0: 퀘스트만 일치
        1: 모두 일치
        */
        int inspectContextIncludeParent(QuestPlan qp) {
         	SearchHelper h = this;
            do{
                QuestPlan p = h.questPlan;
                if(p == null) return -1;
                if(p.quest == qp.quest) {
                    if(p.plan == qp.plan) {
                        return 1;
                    }
                    return 0;
                }
            } while((h=h.parent)!=null);
        	return -1;
        }
        
        boolean contains(QuestPlan qp) {
            return this.cleared.contains(qp);
        }
        
    }
    
    static class QuestPlan {
        final boolean plan; // true: str, false: intelli
        final Quest quest;
        QuestPlan(boolean plan, Quest quest) {
            this.plan = plan;
            this.quest = quest;
        }

        public String toString() {
            return "{" + quest.num + ": " + plan + "}";
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
    
        int num = 0;
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
    재귀함수 쓰면 메모리 초과날것 같아서 일단 보류
    void work(Player p, HashSet<Quest> uncleared) {
        for(Quest q : uncleared) {
            Player copy = p.copy();
            
        }
    }
    */
}
