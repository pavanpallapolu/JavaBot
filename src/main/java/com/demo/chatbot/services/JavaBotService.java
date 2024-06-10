package com.demo.chatbot.services;


import com.demo.chatbot.models.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class JavaBotService {

    private int[][] board;

    public Integer placePawn(PawnPlacementRequest pawnPlacementRequest) {
        board = new int[4][4];
        for (int i = 0; i < 4; i++){
            for(int j=0;j<4;j++){
                board[i][j]=-1;
            }
        }
        List<Integer> boardPositions= pawnPlacementRequest.getBoard();
        for(int i=0;i<boardPositions.size();i+=2){
            int piece=boardPositions.get(i);
            int position=boardPositions.get(i+1);
            board[position/4][position%4]=piece;
        }
        BoardSummary preBoardSummary=inspectBoard();
        TreeSet<PairIntInt> goodPositions=new TreeSet<>();
        for(int position:pawnPlacementRequest.getAvailablePositions()){
            board[position/4][position%4]=pawnPlacementRequest.getSelectedPawn();
            BoardSummary curBoardSummary=inspectBoard();
            if(curBoardSummary.getNumberOfWins()>0) return position;
            goodPositions.add(new PairIntInt(curBoardSummary.getNumberOfTriples()-preBoardSummary.getNumberOfTriples(),position));
            board[position/4][position%4]=-1;
        }
        return goodPositions.first().getSecond();
    }

    public Integer selectPawn(PawnSelectionRequest pawnSelectionRequest){
        board = new int[4][4];
        for (int i = 0; i < 4; i++){
            for(int j=0;j<4;j++){
                board[i][j]=-1;
            }
        }
        List<Integer> boardPositions= pawnSelectionRequest.getBoard();
        for(int i=0;i<boardPositions.size();i+=2){
            int piece=boardPositions.get(i);
            int position=boardPositions.get(i+1);
            board[position/4][position%4]=piece;
        }
        TreeSet<TupleDbDbInt>positionMetrics=new TreeSet<>();
        for(int pawn:pawnSelectionRequest.getAvailablePawns()){
            double opponentWinProbability=0,tripleProbability=0;
            BoardSummary preBoardSummary=inspectBoard();
            int emptyCells=0;
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++) emptyCells+=(board[i][j]==-1?1:0);
            }
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(board[i][j]!=-1) continue;
                    board[i][j]=pawn;
                    BoardSummary curBoardSummary=inspectBoard();
                    opponentWinProbability+=1.0*(curBoardSummary.getNumberOfWins()-preBoardSummary.getNumberOfWins())/emptyCells;
                    tripleProbability+=1.0*(curBoardSummary.getNumberOfWins()-preBoardSummary.getNumberOfWins())/emptyCells;
                }
            }
            positionMetrics.add(new TupleDbDbInt(opponentWinProbability,tripleProbability,pawn));
        }
        return positionMetrics.getFirst().getThird();
    }


    private BoardSummary inspectBoard(){
        int numOfWins=0,numOfTriples=0,numOfDoubles=0;
        for(int i=0;i<4;i++){
            for(int k=0;k<4;k++){
                int num=(1<<k),cnt=0,cnt2=0;
                for(int j=0;j<4;j++){
                    if(board[i][j]==-1) continue;
                    if((num&board[i][j])>0) cnt++;
                    else cnt2++;
                }
                if(cnt==4) numOfWins++;
                if(cnt==3&&cnt2==-1){
                    numOfTriples++;
                }
                if(cnt==2&&cnt2==-2){
                    numOfDoubles++;
                }
            }
            for(int k=0;k<4;k++){
                int num=(1<<k),cnt=0,cnt2=0;
                for(int j=0;j<4;j++){
                    if(board[j][i]==-1) continue;
                    if((num&board[j][i])>0) cnt++;
                    else cnt2++;
                }
                if(cnt==4) numOfWins++;
                if(cnt==3&&cnt2==-1){
                    numOfTriples++;
                }
                if(cnt==2&&cnt2==-2){
                    numOfDoubles++;
                }
            }
        }
        for(int k=0;k<4;k++) {
            int num = (1 << k), cnt = 0, cnt2 = 0;
            for (int i = 0; i < 4; i++) {
                if (board[i][i] == -1) continue;
                if ((num & board[i][i]) > 0) cnt++;
                else cnt2++;
            }
            if (cnt == 4) numOfWins++;
            if (cnt == 3 && cnt2 == -1) {
                numOfTriples++;
            }
            if (cnt == 2 && cnt2 == -2) {
                numOfDoubles++;
            }
        }
        for(int k=0;k<4;k++) {
            int num = (1 << k), cnt = 0, cnt2 = 0;
            for (int i = 0; i < 4; i++) {
                if (board[i][3-i] == -1) continue;
                if ((num & board[i][3-i]) > 0) cnt++;
                else cnt2++;
            }
            if (cnt == 4) numOfWins++;
            if (cnt == 3 && cnt2 == -1) {
                numOfTriples++;
            }
            if (cnt == 2 && cnt2 == -2) {
                numOfDoubles++;
            }
        }
        BoardSummary boardSummary=new BoardSummary();
        boardSummary.setNumberOfDoubles(numOfDoubles);
        boardSummary.setNumberOfWins(numOfWins);
        boardSummary.setNumberOfTriples(numOfTriples);
        return boardSummary;
    }

}
