package com.java456.service;

import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.java456.dao.MessageDao;
import com.java456.entity.Bank;
import com.java456.entity.Message;
import com.java456.entity.User;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService{
	  @Resource
	  private MessageDao messageDao;
	  
	@Override
	public List<Message> seachMessage(String source){
		return  messageDao.seachMessage(source);
	}
	@Override
	public List<Message> searchNewMessages(){
		return	messageDao.searchNewMessage();
				
	}
	@Override
    public void update(Message message) {
    	Message origin = messageDao.findId(message.getId());
        message = repalce(message, origin);
        messageDao.save(message);
    }
    /**
     * @param curr  当前更新的数据
     * @param origin   源数据  以前的数据
     * @return  curr
     */
    public Message repalce(Message curr, Message origin){

        if(curr.getAddrString()==null){
            curr.setAddrString(origin.getAddrString());
        }
        if(curr.getImageUrl()==null){
            curr.setImageUrl(origin.getImageUrl());
        }
        if(curr.getPrice()==null){
            curr.setPrice(origin.getPrice());
        }
        if(curr.getSource()==null){
            curr.setSource(origin.getSource());
        }


        if(curr.getUrlString()==null){
            curr.setUrlString(origin.getUrlString());
        }
        if(curr.getOrderNo()==null){
            curr.setOrderNo(origin.getOrderNo());
        }
        if(curr.getCreateDateTime()==null){
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if(curr.getUpdateDateTime()==null){
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }
        if(curr.getUrlString() ==null){
            curr.setUrlString(origin.getUrlString());
        }
        return curr;
    }
}
