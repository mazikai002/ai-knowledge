package com.itheima.backend.controller.v1;

import com.itheima.backend.model.dto.CreateConversationDTO;
import com.itheima.backend.model.dto.SendMessageDTO;
import com.itheima.backend.model.vo.ConversationVO;
import com.itheima.backend.model.vo.MessageVO;
import com.itheima.backend.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversation")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @GetMapping("/list")
    public ResponseEntity<List<ConversationVO>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }

    @PostMapping("/create")
    public ResponseEntity<ConversationVO> createConversation(@RequestBody CreateConversationDTO request) {
        return ResponseEntity.ok(conversationService.createConversation(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<MessageVO> sendMessage(
            @PathVariable Long id,
            @RequestBody SendMessageDTO request) {
        return ResponseEntity.ok(conversationService.sendMessage(id, request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConversationVO>> searchConversations(@RequestParam String keyword) {
        return ResponseEntity.ok(conversationService.searchConversations(keyword));
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<String> exportConversation(@PathVariable Long id) {
        return ResponseEntity.ok(conversationService.exportConversation(id));
    }
} 