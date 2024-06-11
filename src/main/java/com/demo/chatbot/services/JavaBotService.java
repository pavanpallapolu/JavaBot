package com.demo.chatbot.services;


import com.demo.chatbot.models.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaBotService {

    private int[][] board;

    private Set<Integer> ourPiecesLeft;

    private Set<Integer> opponentPiecesLeft;


    public Integer placePawn(PawnPlacementRequest pawnPlacementRequest) {
        board = new int[4][4];
        for (int i = 0; i < 4; i++){
            for(int j=0;j<4;j++){
                board[i][j]=-1;
            }
        }
        ourPiecesLeft=new HashSet<>();
        opponentPiecesLeft=new HashSet<>();
        for(int i=0;i<16;i++){
            ourPiecesLeft.add(i);
            opponentPiecesLeft.add(i);
        }
        for(Move move:pawnPlacementRequest.getMoves()){
            Move.SelectionMove selectionMove=move.getSelectionMove();
            Move.PlacementMove placementMove=move.getPlacementMove();
            Integer selectedPawn=selectionMove.getSelectedPawn();
            if(placementMove.getPlacingPlayer()==1){
                ourPiecesLeft.remove(selectedPawn);
            }else {
                opponentPiecesLeft.remove(selectedPawn);
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
        TreeSet<BoardSummary> positionSummary=new TreeSet<>();
        for(int position:pawnPlacementRequest.getAvailablePositions()){
            board[position/4][position%4]=pawnPlacementRequest.getSelectedPawn();
            if(isWon()) return position;
            BoardSummary curBoardSummary=inspectBoard();
            curBoardSummary.setPosition(position);
            curBoardSummary.setPiece(-1);
            positionSummary.add(curBoardSummary);
            board[position/4][position%4]=-1;
        }
        return positionSummary.first().getPosition();
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
        Comparator<BoardSummary> boardSummaryComparator = new Comparator<BoardSummary>() {
            @Override
            public int compare(BoardSummary b1, BoardSummary b2) {
                if(b1.getOurWinProbability()<b2.getOurWinProbability()) return 1;
                if(b1.getOurWinProbability()>b2.getOurWinProbability()) return -1;
                if(b1.getOpponentWinProbability()>b2.getOpponentWinProbability()) return 1;
                if(b1.getOpponentWinProbability()<b2.getOpponentWinProbability()) return -1;
                if(b1.getOurFavouriteTriplesProbability()<b2.getOurFavouriteTriplesProbability()) return 1;
                if(b1.getOurFavouriteTriplesProbability()>b2.getOurFavouriteTriplesProbability()) return -1;
                if(b1.getOpponentFavouriteTriplesProbability()>b2.getOpponentFavouriteTriplesProbability()) return 1;
                if(b1.getOpponentFavouriteTriplesProbability()<b2.getOpponentFavouriteTriplesProbability()) return -1;
                if(b1.getPosition()>b2.getPosition()) return 1;
                if(b1.getPosition()<b2.getPosition()) return -1;
                if(b1.getPiece()>b2.getPiece()) return 1;
                if(b1.getPiece()<b2.getPiece()) return -1;
                return 0;
            }
        };
        TreeSet<BoardSummary>positionMetrics=new TreeSet<>(boardSummaryComparator);
        TreeMap<Integer,Integer> opponentWinningPieces=new TreeMap<>();
        for(int pawn:pawnSelectionRequest.getAvailablePawns()){
            BoardSummary preBoardSummary=inspectBoard();
            int emptyCells=0;
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++) emptyCells+=(board[i][j]==-1?1:0);
            }
            boolean discard=Boolean.FALSE;
            double opponentWinProbability=0,ourWinProbability=0,ourFavouriteTriplesProbabilty=0,opponentFavouriteTriplesProbabilty=0;
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(board[i][j]!=-1) continue;
                    board[i][j]=pawn;
                    if(isWon()){
                        discard=Boolean.TRUE;
                        opponentWinningPieces.merge(pawn, 1, Integer::sum);
                    }
                    BoardSummary tempBoardSummary=inspectBoard();
                    opponentWinProbability+=tempBoardSummary.getOpponentWinProbability();
                    ourWinProbability+=tempBoardSummary.getOurWinProbability();
                    ourFavouriteTriplesProbabilty+=tempBoardSummary.getOurFavouriteTriplesProbability();
                    opponentFavouriteTriplesProbabilty+=tempBoardSummary.getOpponentFavouriteTriplesProbability();
                    board[i][j]=-1;
                }
            }
            if(discard) continue;
            BoardSummary curBoardSummary=new BoardSummary();
            curBoardSummary.setPiece(pawn);
            curBoardSummary.setPosition(-1);
            curBoardSummary.setOpponentWinProbability(opponentWinProbability);
            curBoardSummary.setOurWinProbability(ourWinProbability);
            curBoardSummary.setOurFavouriteTriplesProbability(ourFavouriteTriplesProbabilty);
            curBoardSummary.setOpponentFavouriteTriplesProbability(opponentFavouriteTriplesProbabilty);
            positionMetrics.add(curBoardSummary);
        }
        if(positionMetrics.isEmpty()){
            Integer piece=-1,wins=1000;
            for(Map.Entry<Integer,Integer> entry:opponentWinningPieces.entrySet()){
                if(entry.getValue()<wins){
                    wins=entry.getValue();
                    piece= entry.getKey();
                }
            }
            return piece;
        }
        return positionMetrics.getFirst().getPiece();
    }

    private Boolean isWon() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                int num = (1 << k), cnt = 0, cnt2 = 0;
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == -1) continue;
                    if ((num & board[i][j]) > 0) cnt++;
                    else cnt2++;
                }
                if (cnt == 4) return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    private BoardSummary inspectBoard(){
        double opponentWinProbability=0,ourWinProbability=0,ourFavouriteTriplesProbabilty=0,opponentFavouriteTriplesProbabilty=0;
        int emptyCells=0;
        List<Integer> availablePositions=new ArrayList<>();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++) {
                emptyCells+=(board[i][j]==-1?1:0);
                if(board[i][j]==-1) availablePositions.add(i*4+j);
            }
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j]!=-1) continue;
                for(Integer pawn:ourPiecesLeft){
                    board[i][j]=pawn;
                    if(isWon()){
                        ourWinProbability+=1.0/ourPiecesLeft.size()*(1-1.0/emptyCells);
                    }
                    board[i][j]=-1;
                }
            }
        }

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j]!=-1) continue;
                for(Integer pawn:opponentPiecesLeft){
                    board[i][j]=pawn;
                    if(isWon()){
                        opponentWinProbability+=1.0/opponentPiecesLeft.size()*(1-1.0/emptyCells);
                    }
                    board[i][j]=-1;
                }
            }
        }

        for(Integer pawn1:ourPiecesLeft){
            for(Integer pawn2:ourPiecesLeft){
                if(pawn1.equals(pawn2)) continue;
                for(Integer position1:availablePositions){
                    for (Integer position2:availablePositions){
                        if(position1.equals(position2)) continue;
                        board[position1/4][position1%4]=pawn1;
                        board[position2/4][position2%4]=pawn2;
                        if(isWon()) {
                            ourFavouriteTriplesProbabilty+=1.0/ourPiecesLeft.size()/(ourPiecesLeft.size()-1);
                        }
                    }
                }
            }
        }

        for(Integer pawn1:opponentPiecesLeft){
            for(Integer pawn2:opponentPiecesLeft){
                if(pawn1.equals(pawn2)) continue;
                for(Integer position1:availablePositions){
                    for (Integer position2:availablePositions){
                        if(position1.equals(position2)) continue;
                        board[position1/4][position1%4]=pawn1;
                        board[position2/4][position2%4]=pawn2;
                        if(isWon()) {
                            opponentFavouriteTriplesProbabilty+=1.0/opponentPiecesLeft.size()/(opponentPiecesLeft.size()-1);
                        }
                    }
                }
            }
        }
        BoardSummary boardSummary=new BoardSummary();
        boardSummary.setOpponentWinProbability(opponentWinProbability);
        boardSummary.setOurWinProbability(ourWinProbability);
        boardSummary.setOurFavouriteTriplesProbability(ourFavouriteTriplesProbabilty);
        boardSummary.setOpponentFavouriteTriplesProbability(opponentFavouriteTriplesProbabilty);
        return boardSummary;
    }

}
