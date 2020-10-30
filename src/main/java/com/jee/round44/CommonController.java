package com.jee.round44;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public interface CommonController<T>{
	Response index();
	
	Response save(T body); 
	Response update(T body);
	Response getById(int id);
	Response delete(int id);
	Response getWithPagination(int pageNum);
	
}
