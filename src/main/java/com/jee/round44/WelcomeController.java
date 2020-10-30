package com.jee.round44;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jee.round44.account.UserModel;

@Path("author-info")
@Produces(MediaType.APPLICATION_JSON)
public class WelcomeController {
	
    @GET
    public Response  index() {
    	UserModel user = new UserModel();
    	user.setId(1);
    	user.setName("Farid Ahmed");
    	user.setAddress("Dhaka");
        return Response
        		.status(Response.Status.OK)
        		.entity(user)
        		.header("status", "success")
        		.build();
    }
    
    
}
