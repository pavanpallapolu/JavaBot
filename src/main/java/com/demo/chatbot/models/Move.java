package com.demo.chatbot.models;

import com.demo.chatbot.enums.Pawn;
import com.demo.chatbot.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Move {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectionMove {
        private Integer selectingPlayer;
        private Integer selectedPawn;
        private Long selectionTimeMs;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlacementMove {
        private Integer placingPlayer;
        private Position position;
        private Long placingTimeMs;
    }

    private SelectionMove selectionMove;
    private PlacementMove placementMove;
}


