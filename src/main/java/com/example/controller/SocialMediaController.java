package com.example.controller;
import com.example.entity.*;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.MessageCreationException;
import com.example.exception.UsernamePasswordException;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
 
@PostMapping(value="/register")
public ResponseEntity<?> postRegister(@RequestBody Map<String, String> accountMap){
    try{
        Account account=accountService.registerAccount(accountMap.get("username"),accountMap.get("password"));
        return ResponseEntity.status(200).body(account);
    }
    catch (DuplicateUsernameException e){
        return ResponseEntity.status(409).body(Map.of("error",e.getMessage()));
    }
    catch (UsernamePasswordException e){
        return ResponseEntity.status(400).body(Map.of("error",e.getMessage()));
    }
    
    
}
@PostMapping(value="/login")
public ResponseEntity<?> postLogin(@RequestBody Map<String, String> accountMap){
    try{
        Account account=accountService.loginAccount(accountMap.get("username"),accountMap.get("password"));
        return ResponseEntity.status(200).body(account);
    }
    catch (UsernamePasswordException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }
}
@PostMapping(value="/messages")
public ResponseEntity<?> postMessage(@RequestBody Map<String, String> messageMap){
    try{
        Message message=messageService.postMessage(messageMap.get("postedBy"),messageMap.get("messageText"),messageMap.get("timePostedEpoch"));
        return ResponseEntity.status(200).body(message);
    }
    catch (MessageCreationException e){
        return ResponseEntity.status(400).body(Map.of("error",e.getMessage()));
    }
}
@GetMapping(value="/messages")
public ResponseEntity<?> getMessages(){
    List<Message> messages=messageService.getMessages();
    return ResponseEntity.status(200).body(messages);

}
@RequestMapping(value="/messages/{message_id}")
public ResponseEntity<?> getMessageId(@PathVariable (value="message_id") Integer message_id){
    Message message=messageService.getMessageId(message_id);
    return ResponseEntity.status(200).body(message);
}
@DeleteMapping(value="/messages/{message_id}")
public ResponseEntity<?> deleteMessageId(@PathVariable (value="message_id") Integer message_id){
    Integer delete=messageService.deleteMessageId(message_id);
    return ResponseEntity.status(200).body(delete);
}
@PatchMapping(value="/messages/{message_id}")
public ResponseEntity<?> updateMessageId(@PathVariable (value="message_id") Integer message_id,@RequestBody Map<String, String> messageMap){
    try{
    Integer delete=messageService.updateMessageId(message_id,messageMap.get("messageText"));
    return ResponseEntity.status(200).body(delete);
    }
    catch (MessageCreationException e){
        return ResponseEntity.status(400).body(Map.of("error",e.getMessage()));
    }
    catch (IllegalArgumentException e){
        return ResponseEntity.status(400).body(Map.of("error",e.getMessage()));

    }
}
@GetMapping(value="/accounts/{account_id}/messages")
public ResponseEntity<?> getMessageAccountId(@PathVariable (value="account_id") Integer account_id){
    List<Message> messages= messageService.getMessageAccountId(account_id);
    return ResponseEntity.status(200).body(messages);
}
}
