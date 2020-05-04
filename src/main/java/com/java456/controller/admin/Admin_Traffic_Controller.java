package com.java456.controller.admin;

import com.java456.dao.MessageDao;
import com.java456.dao.TrafficDao;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Traffic;
import com.java456.service.MessageService;
import com.java456.service.TrafficService;
import net.sf.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/admin/traffic")
public class Admin_Traffic_Controller {

    @Resource
    private TrafficDao trafficDao;
    @Resource
    private TrafficService trafficService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;

    /**
     * /admin/traffic/add
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid Traffic traffic,@Valid Message message, BindingResult bindingResult, HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if (bindingResult.hasErrors()) {
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
            traffic.setCreateDateTime(new Date());
            trafficDao.save(traffic);
            message.setOrderNo(traffic.getId());
            message.setAddrString(traffic.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(traffic.getImageUrl());
            message.setSource(traffic.getSource());
            message.setPrice(traffic.getPrice());
            message.setUrlString(traffic.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(6);
            
            message.setMessageType(messageType);
    
            messageDao.save(message);
            
            
            
            
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }


    /**
     *  /admin/traffic/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid Traffic traffic,@Valid Message message,BindingResult bindingResult, HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            traffic.setUpdateDateTime(new Date());
            trafficService.update(traffic);
			message.setUpdateDateTime(new Date());
            message.setOrderNo(traffic.getId());
            message.setAddrString(traffic.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(traffic.getImageUrl());
            message.setSource(traffic.getSource());
            message.setPrice(traffic.getPrice());
            message.setUrlString(traffic.getUrlString());
            messageService.update(message);
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }


    /**
     * /admin/traffic/list
     * @param page    默认1
     * @param limit   数据多少
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Traffic> list = trafficService.list(map, page-1, limit);
        long total = trafficService.getTotal(map);

        map.put("data", list);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }


    /**
     * /admin/traffic/delete
     */
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            trafficDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }
}
