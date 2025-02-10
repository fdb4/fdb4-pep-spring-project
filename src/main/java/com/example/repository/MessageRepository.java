package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    Message findByMessageId(Integer messageId);
    void deleteByMessageId(Integer messageId);
    List<Message> findByPostedBy(Integer postedBy);
}
