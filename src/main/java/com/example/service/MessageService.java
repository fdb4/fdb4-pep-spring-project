package com.example.service;

import com.example.exception.MessageCreationException;
import com.example.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MessageRepository messageRepository;
    public Message postMessage(String postedBy, String messageText,String timePostedEpoch){
        if (messageText.isBlank() || messageText.length()>255){
            throw new MessageCreationException("Message should be between 0 and 255 characters");//I should make error specifc to issues but time makes me do this
        }
        Account othAccount=accountRepository.findByAccountId(Integer.parseInt(postedBy));
        if(othAccount==null){
            throw new MessageCreationException("Account for message does not exist");
        }
        Message message=new Message();
        message.setPostedBy(Integer.parseInt(postedBy));
        message.setMessageText(messageText);
        message.setTimePostedEpoch(Long.parseLong(timePostedEpoch));
        return messageRepository.save(message);

    }
    public List<Message> getMessages(){
        return messageRepository.findAll();
    }
    public Message getMessageId(Integer message_id){
        return messageRepository.findByMessageId(message_id);
    }
    public Integer deleteMessageId(Integer message_id){
        Message message=getMessageId(message_id);
        if(message!=null){
            messageRepository.delete(message);
            return 1;
        }
        
        return null;//its always only gonna delete 1 row
    }
    public Integer updateMessageId(Integer message_id, String messageText){
        Message message=getMessageId(message_id);
        if(message==null){
            throw new IllegalArgumentException("Account does not exist");
        }
        if (messageText.isBlank() || messageText.length()>255){
            throw new MessageCreationException("Message should be between 0 and 255 characters");
        }
        Message upmessage = new Message();
        upmessage.setMessageId(message_id);
        upmessage.setMessageText(messageText);
        upmessage.setPostedBy(message.getPostedBy());
        upmessage.setTimePostedEpoch(message.getTimePostedEpoch());
        messageRepository.save(upmessage);
        return 1;
    }
    public List<Message> getMessageAccountId(Integer account_Id){
        return messageRepository.findByPostedBy(account_Id);
    }
}
