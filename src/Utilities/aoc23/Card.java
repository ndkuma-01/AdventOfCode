package Utilities.aoc23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Card implements Comparable {

    int s;
    String card;
    public Long bid;
    ArrayList<String> types = new ArrayList<>(List.of("", "high", "one", "two", "three", "full", "four", "five"));
    String cardMap;

    public Card(String card, Long bid, boolean p2) {
        cardMap = (!p2) ? "0023456789TJQKA" : "00J23456789TQKA";
        this.card = card;
        this.bid = bid;
        int numOfJ = 0;
        HashMap<Character, Long> map = new HashMap<>();
        for (int i = 0; i < card.length(); i++) {
            char curr = card.charAt(i);
            if (curr == 'J' && p2) {
                numOfJ++;
            }

            map.put(curr, map.getOrDefault(curr, 0L) + 1);
//            if(curr != 'J' || !p2 ){map.put(curr, map.get(curr) + 1);}
        }
//        System.out.println(numOfJ);
        if (p2 && !card.equals("JJJJJ")) {
            map.remove('J');
        }
        ArrayList<Long> vals = (ArrayList<Long>) map.values().stream().sorted().collect(Collectors.toList());
//        System.out.println(card);
//        System.out.println(vals);
        if (p2 && !card.equals("JJJJJ")) {
            vals.set(vals.size() - 1, vals.get(vals.size() - 1) + numOfJ);
        }
        if (vals.size() == 1 && vals.get(0) == 5) {
            s = types.indexOf("five");
        } else if (vals.size() == 2 && vals.get(1) == 4) {
            s = types.indexOf("four");
        } else if (vals.size() == 2 && vals.get(1) == 3) {
            s = types.indexOf("full");
        } else if (vals.size() == 3 && vals.get(2) == 3) {
            s = types.indexOf("three");
        } else if (vals.size() == 3 && vals.get(2) == 2) {
            s = types.indexOf("two");
        } else if (vals.size() == 4 && vals.get(3) == 2) {
            s = types.indexOf("one");
        } else if (vals.size() == 5) {
            s = types.indexOf("high");
        }
    }

    @Override
    public int compareTo(Object o) {
        Card other = (Card) o;
        if (this.s != other.s) {
            return this.s - other.s;
        } else {
            for (int i = 0; i < this.card.length(); i++) {
                if (this.card.charAt(i) != other.card.charAt(i)) {
                    return cardMap.indexOf(this.card.charAt(i)) - cardMap.indexOf(other.card.charAt(i));
                }

            }
            return 0;
        }
    }

}
