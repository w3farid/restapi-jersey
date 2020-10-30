package com.jee.round44;

import java.util.List;
import java.util.Map;

import com.jee.round44.exception.CustomException;

public interface CommonDao<T> {
	List<T> getAllDoc() throws CustomException;
	T save(T entity) throws CustomException;
	T getById(int id) throws CustomException;
	T update(T entity) throws CustomException;
	boolean delete(int id) throws CustomException;
	Map<String, Object> getWithPagination(int pageNum);
	
}
