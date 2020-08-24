package com.lhsj.police.core.regex;

public class ReIdCardsApp {

    public static void main(String[] args) {
        String idCard = "42092219731015461X";
        System.out.println(ReIdCards.match(idCard));
        System.out.println(ReIdCards.match(idCard, true));
        System.out.println(ReIdCards.match(idCard, false));
    }

}
