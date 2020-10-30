package com.jee.round44.account;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jee.round44.ApiErrorResponse;
import com.jee.round44.ApiSuccessResponse;
import com.jee.round44.CommonController;
import com.jee.round44.exception.CustomException;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController implements CommonController<UserModel> {
	UserDao userRepo;

	public UserController() {
		userRepo = new UserDao();
	}

	@GET
	@Path("/index")
	@Override
	public Response index() {
		List<String> errors = new ArrayList();
		List<UserModel> userList;
		try {
			userList = userRepo.getAllDoc();
			return Response.status(200).entity(new ApiSuccessResponse("success", "Get data successfully", userList))
					.build();
		} catch (CustomException e) {
			errors.add(e.getLocalizedMessage());
			return Response.status(412).entity(new ApiErrorResponse("error", "Save failed!", errors, null)).build();
		}

	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response save(UserModel body) {
		List<String> errors = new ArrayList();
		UserModel entityModel;
		try {
			entityModel = userRepo.save(body);
			if (entityModel != null) {
				return Response.status(200).entity(new ApiSuccessResponse("success", "Save successful", entityModel))
						.build();
			}
			errors.add("Something wrong!, please try again");
			return Response.status(412).entity(new ApiErrorResponse("error", "Save failed!", errors, entityModel))
					.build();
		} catch (CustomException e) {
			errors.add(e.getLocalizedMessage());
			return Response.status(412).entity(new ApiErrorResponse("error", "Get data failed!", errors, null)).build();
		}

	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response update(UserModel body) {
		List<String> errors = new ArrayList();
		try {
			body = userRepo.update(body);
			if (body != null) {
				return Response.status(200).entity(new ApiSuccessResponse("success", "Update successful", body))
						.build();
			}
			errors.add("Something wrong!, please try again");
			return Response.status(412).entity(new ApiErrorResponse("error", "Update failed!", errors, body)).build();
		} catch (CustomException e) {
			errors.add(e.getLocalizedMessage());
			return Response.status(412).entity(new ApiErrorResponse("error", "Update failed!", errors, body)).build();
		}

	}

	@GET
	@Path("/one/{id}")
	@Override
	public Response getById(@PathParam("id") int id) {
		List<String> errors = new ArrayList();
		UserModel entityModel;
		try {
			entityModel = userRepo.getById(id);
			if (entityModel != null) {
				return Response.status(200)
						.entity(new ApiSuccessResponse("success", "Get data successfully", entityModel)).build();
			}
			errors.add("Something wrong!, please try again");
			return Response.status(412).entity(new ApiErrorResponse("error", "Not found data", errors, entityModel))
					.build();
		} catch (CustomException e) {
			errors.add(e.getLocalizedMessage());
			return Response.status(412).entity(new ApiErrorResponse("error", "Get data failed!", errors, null)).build();
		}

	}

	@DELETE
	@Path("/delete/{id}")
	@Override
	public Response delete(@PathParam("id") int id) {
		List<String> errors = new ArrayList();
		UserModel entityModel;
		boolean isDelete;
		try {
			isDelete = userRepo.delete(id);
			if (isDelete) {
				return Response.status(200).entity(new ApiSuccessResponse("success", "Data deleted", null)).build();
			}
			errors.add("Something wrong!, please try again");
			return Response.status(412).entity(new ApiErrorResponse("error", "Not deleted! Please try again", null, null)).build();
		} catch (CustomException e) {
			errors.add(e.getLocalizedMessage());
			return Response.status(412).entity(new ApiErrorResponse("error", "Delete failed!", errors, null)).build();
		}

	}

	@Override
	public Response getWithPagination(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
