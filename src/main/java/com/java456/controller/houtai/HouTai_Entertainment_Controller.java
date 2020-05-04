package com.java456.controller.houtai;

import com.java456.dao.EntertainmentDao;
import com.java456.dao.EntertainmentTypeDao;
import com.java456.entity.Entertainment;
import com.java456.entity.EntertainmentType;
import com.java456.service.EntertainmentService;
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
@RequestMapping("/houtai/entertainment")
public class HouTai_Entertainment_Controller {

    @Resource
    private EntertainmentDao entertainmentDao;
    @Resource
    private EntertainmentService entertainmentService;
    @Resource
    private EntertainmentTypeDao entertainmentTypeDao;


    /**
     * /houtai/entertainment/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "娱乐管理");
        mav.setViewName("/admin/page/entertainment/entertainment_manage");
        return mav;
    }

    /**
     * /houtai/entertainment/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
        List<EntertainmentType> entertainmentTypeList = list.getContent(); //拿到list集合
        mav.addObject("entertainmentTypeList", entertainmentTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/entertainment/add");
        mav.setViewName("/admin/page/entertainment/add_update");
        return mav;
    }


    /**
     * /houtai/entertainment/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
        List<EntertainmentType> entertainmentTypeList = list.getContent();//拿到list集合
        mav.addObject("entertainmentTypeList", entertainmentTypeList);


        Entertainment entertainment = entertainmentDao.findId(id);
        mav.addObject("entertainment", entertainment);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/entertainment/update?id=" + id);
        mav.setViewName("/admin/page/entertainment/add_update");
        return mav;
    }
}
