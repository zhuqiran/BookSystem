package com.java456.service;

import com.java456.dao.EntertainmentDao;
import com.java456.entity.Entertainment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Service(value = "entertainmentService")
public class EntertainmentServiceImpl implements EntertainmentService {

    @Resource
    private EntertainmentDao entertainmentDao;

    @Override
    public void update(Entertainment entertainment) {
        Entertainment origin = entertainmentDao.findId(entertainment.getId());
        entertainment = repalce(entertainment, origin);
        entertainmentDao.save(entertainment);
    }

    @Override
    public List<Entertainment> list(Map<String, Object> map, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Entertainment> pages = entertainmentDao.findAll(new Specification<Entertainment>() {
            @Override
            public Predicate toPredicate(Root<Entertainment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 加入 等于
                if (map.get("entertainmentType") != null) {
                    predicate.getExpressions().add(cb.equal(root.get("entertainmentType"), map.get("entertainmentType")));
                }

                return predicate;
            }
        }, pageable);

        return pages.getContent();
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        Long count = entertainmentDao.count(new Specification<Entertainment>() {
            @Override
            public Predicate toPredicate(Root<Entertainment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 加入 等于
                if (map.get("entertainmentType") != null) {
                    predicate.getExpressions().add(cb.equal(root.get("entertainmentType"), map.get("entertainmentType")));
                }

                return predicate;
            }
        });
        return count;
    }

    /**
     * @param curr  当前更新的数据
     * @param origin   源数据  以前的数据
     * @return  curr
     */
    public Entertainment repalce(Entertainment curr, Entertainment origin){

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
        if(curr.getEntertainmentType()==null){
            curr.setEntertainmentType(origin.getEntertainmentType());
        }

        return curr;
    }
}
