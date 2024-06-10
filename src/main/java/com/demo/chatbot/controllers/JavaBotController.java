package com.demo.chatbot.controllers;


import com.demo.chatbot.enums.Pawn;
import com.demo.chatbot.enums.Position;
import com.demo.chatbot.models.PawnPlacementRequest;
import com.demo.chatbot.models.PawnPlacementResponse;
import com.demo.chatbot.models.PawnSelectionRequest;
import com.demo.chatbot.models.PawnSelectionResponse;
import com.demo.chatbot.services.JavaBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java_bot")
public class JavaBotController {

    private final JavaBotService javaBotService;

    @Autowired
    public JavaBotController(JavaBotService javaBotService) {
        this.javaBotService = javaBotService;
    }

    @PostMapping("/game/select_pawn")
    public ResponseEntity<PawnSelectionResponse> selectPawn(@RequestBody PawnSelectionRequest pawnSelectionRequest) {
        Integer pawn = javaBotService.selectPawn(pawnSelectionRequest);
        return ResponseEntity.ok(new PawnSelectionResponse());
    }

    @PostMapping("/game/place_pawn")
    public ResponseEntity<PawnPlacementResponse> placePawn(@RequestBody PawnPlacementRequest pawnPlacementRequest) {
        Integer position = javaBotService.placePawn(pawnPlacementRequest);
        return ResponseEntity.ok(new PawnPlacementResponse());
    }

}

