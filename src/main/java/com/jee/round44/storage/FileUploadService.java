package com.jee.round44.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * This example shows how to build Java REST web-service to upload files
 * accepting POST requests with encoding type "multipart/form-data". For more
 * details please read the full tutorial on
 * https://javatutorial.net/java-file-upload-rest-service
 * 
 * @author javatutorial.net
 */
@Path("/file")
public class FileUploadService {
	/** The path to the folder where we want to store the uploaded files */
	private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
	static String[] ALLOWED_EXTENSION_PHOTO = {"jpg", "jpeg", "png", "gif"};

	public FileUploadService() {
	}

	@Context
	private UriInfo context;

	/**
	 * Returns text response to caller containing uploaded file location
	 * 
	 * @return error response in case of missing parameters an internal exception or
	 *         success response if file has been stored successfully
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(500).entity("Can not create destination folder on server").build();
		}
		String id = UUID.randomUUID().toString()+System.currentTimeMillis();
		String uploadedFileLocation = UPLOAD_FOLDER + id+ "."+FilenameUtils.getExtension(fileDetail.getFileName());
		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(500).entity("Can not save file").build();
		}
		File image = new File(uploadedFileLocation);
		
		String path = context.getBaseUri()+"file/data/" +id+ "."+FilenameUtils.getExtension(fileDetail.getFileName());
		return Response.status(200).entity(path).build();
	}
	
	@POST
	@Path("/upload/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFilePhoto(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		
		
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid form data").build();
		String filename = fileDetail.getFileName();
		System.out.println(filename);
		if(!isAllowedExtention(filename))
			return Response.status(400).entity("Not allowed extension. Please try this "+Arrays.toString(ALLOWED_EXTENSION_PHOTO)).build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(500).entity("Can not create destination folder on server").build();
		}
		String id = UUID.randomUUID().toString()+System.currentTimeMillis();
		String uploadedFileLocation = UPLOAD_FOLDER + id+ "."+FilenameUtils.getExtension(fileDetail.getFileName());
		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(500).entity("Can not save file").build();
		}
		File image = new File(uploadedFileLocation);
		
		String path = context.getBaseUri()+"file/data/" +id+ "."+FilenameUtils.getExtension(fileDetail.getFileName());
		return Response.status(200).entity(path).build();
	}

	@GET
	@Path("data/{imgFilename}")
	@Produces("image/png")
	public Response getImage(@PathParam("imgFilename") String fileName) {
		File image = new File(UPLOAD_FOLDER + fileName);
		return Response.ok(image).build();
	}

	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream - InputStream to be saved
	 * @param target   - full path to destination file
	 */
	private void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName - full path to the folder
	 * @throws SecurityException - in case you don't have permission to create the
	 *                           folder
	 */
	private void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	private boolean isAllowedExtention(String filename) {
		String extension = FilenameUtils.getExtension(filename);
		System.out.println(extension+"::::::::::::extension:::::::::::::");
		for (String el : ALLOWED_EXTENSION_PHOTO) {
			if(extension.equalsIgnoreCase(el)) return true;
		}
		System.out.println("::::::::::::not allowed:::::::::::::");
		return false;	
	}
	
	
}