package com.java456.controller.houtai;

import com.java456.dao.BankDao;
import com.java456.dao.BankTypeDao;
import com.java456.dao.MessageTypeDao;
import com.java456.entity.Bank;
import com.java456.entity.BankType;
import com.java456.entity.MessageType;
import com.java456.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/houtai/bank")
public class HouTai_Bank_Controller {

    @Resource
    private BankDao bankDao;
    @Resource
    private BankService bankService;
    @Resource
    private BankTypeDao bankTypeDao;
    @Resource
    private MessageTypeDao messageTypeDao;

    /**
     * /houtai/bank/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "银行管理");
        mav.setViewName("/admin/page/bank/bank_manage");
        return mav;
    }

    /**
     * /houtai/bank/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<BankType> list = bankTypeDao.findAll(pageable);
        List<BankType> bankTypeList = list.getContent(); //拿到list集合
        mav.addObject("bankTypeList", bankTypeList);
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/bank/add");
        mav.setViewName("/admin/page/bank/add_update");
        return mav;
    }


    /**
     * /houtai/bank/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<BankType> list = bankTypeDao.findAll(pageable);
        List<BankType> bankTypeList = list.getContent();//拿到list集合
        mav.addObject("bankTypeList", bankTypeList);


        Bank bank = bankDao.findId(id);
        mav.addObject("bank", bank);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/bank/update?id=" + id);
        mav.setViewName("/admin/page/bank/add_update");
        return mav;
    }
}
