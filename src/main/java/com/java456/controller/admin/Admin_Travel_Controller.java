package com.java456.controller.admin;

import com.java456.dao.MessageDao;
import com.java456.dao.TravelDao;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Travel;
import com.java456.service.MessageService;
import com.java456.service.TravelService;
import net.sf.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/travel")
public class Admin_Travel_Controller {

    @Resource
    private TravelDao travelDao;
    @Resource
    private TravelService travelService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;
    /**
     * /admin/travel/add
     */
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(@Valid Travel travel,@Valid Message message, BindingResult bindingResult, HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();

        if (bindingResult.hasErrors()) {
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
            travel.setCreateDateTime(new Date());
            travelDao.save(travel);
            message.setAddrString(travel.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(travel.getImageUrl());
            message.setSource(travel.getSource());
            message.setPrice(travel.getPrice());
            message.setUrlString(travel.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(2);
            
            message.setMessageType(messageType);
    
            messageDao.save(message);
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }


    /**
     *  /admin/travel/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid Travel travel,@Valid Message message, BindingResult bindingResult, HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            travel.setUpdateDateTime(new Date());
            travelService.update(travel);
			message.setUpdateDateTime(new Date());
            message.setOrderNo(travel.getId());
            message.setAddrString(travel.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(travel.getImageUrl());
            message.setSource(travel.getSource());
            message.setPrice(travel.getPrice());
            message.setUrlString(travel.getUrlString());
            messageService.update(message);
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }


    /**
     * /admin/travel/list
     * @param page    默认1
     * @param limit   数据多少
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Travel> list = travelService.list(map, page-1, limit);
        long total = travelService.getTotal(map);

        map.put("data", list);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }


    /**
     * /admin/travel/delete
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            travelDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }
}
