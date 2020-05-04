package com.java456.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.java456.entity.Bank;
import com.java456.entity.Message;


public interface MessageService {
	 public void update(Message message);
	/**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */

    public List<Message> seachMessage(String source);
    public List<Message> searchNewMessages();
}
