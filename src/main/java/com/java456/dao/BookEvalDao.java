package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.BookEval;


public interface BookEvalDao  extends JpaRepository<BookEval, Integer>, JpaSpecificationExecutor<BookEval> {
    
	@Query(value = "select * from t_a_Book_eval where id = ?1", nativeQuery = true)
    public BookEval findId(Integer id);
}