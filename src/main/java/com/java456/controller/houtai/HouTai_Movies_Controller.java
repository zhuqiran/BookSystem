package com.java456.controller.houtai;

import com.java456.dao.MoviesDao;
import com.java456.dao.MoviesTypeDao;
import com.java456.entity.Movies;
import com.java456.entity.MoviesType;
import com.java456.service.MoviesService;
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
@RequestMapping("/houtai/movies")
public class HouTai_Movies_Controller {
    @Resource
    private MoviesDao moviesDao;
    @Resource
    private MoviesService moviesService;
    @Resource
    private MoviesTypeDao moviesTypeDao;


    /**
     * /houtai/movies/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "电影管理");
        mav.setViewName("/admin/page/movies/movies_manage");
        return mav;
    }

    /**
     * /houtai/movies/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<MoviesType> list = moviesTypeDao.findAll(pageable);
        List<MoviesType> moviesTypeList = list.getContent(); //拿到list集合
        mav.addObject("moviesTypeList", moviesTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/movies/add");
        mav.setViewName("/admin/page/movies/add_update");
        return mav;
    }


    /**
     * /houtai/movies/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<MoviesType> list = moviesTypeDao.findAll(pageable);
        List<MoviesType> moviesTypeList = list.getContent();//拿到list集合
        mav.addObject("moviesTypeList", moviesTypeList);


        Movies movies = moviesDao.findId(id);
        mav.addObject("movies", movies);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/movies/update?id=" + id);
        mav.setViewName("/admin/page/movies/add_update");
        return mav;
    }
}
