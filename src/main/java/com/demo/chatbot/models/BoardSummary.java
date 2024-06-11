package com.demo.chatbot.models;

import lombok.Data;

@Data
public class BoardSummary implements Comparable {
    private Integer numberOfWins;
    private Integer numberOfTriples;
    private Integer numberOfDoubles;


    private Double opponentWinProbability;
    private Double ourWinProbability;
    private Double opponentFavouriteTriplesProbability;
    private Double ourFavouriteTriplesProbability;
    private Integer position;
    private Integer piece;

    @Override
    public int compareTo(Object o) {
        BoardSummary boardSummary2=(BoardSummary) o;
        if(opponentWinProbability>boardSummary2.getOpponentWinProbability()) return 1;
        if(opponentWinProbability<boardSummary2.getOpponentWinProbability()) return -1;
        if(ourWinProbability<boardSummary2.getOurWinProbability()) return 1;
        if(ourWinProbability>boardSummary2.getOurWinProbability()) return -1;
        if(ourFavouriteTriplesProbability<boardSummary2.getOurFavouriteTriplesProbability()) return 1;
        if(ourFavouriteTriplesProbability>boardSummary2.getOurFavouriteTriplesProbability()) return -1;
        if(opponentFavouriteTriplesProbability>boardSummary2.getOpponentFavouriteTriplesProbability()) return 1;
        if(opponentFavouriteTriplesProbability<boardSummary2.getOpponentFavouriteTriplesProbability()) return -1;
        if(position>boardSummary2.getPosition()) return 1;
        if(position<boardSummary2.getPosition()) return -1;
        if(piece>boardSummary2.getPiece()) return 1;
        if(piece<boardSummary2.getPiece()) return -1;
        return 0;
    }
}
