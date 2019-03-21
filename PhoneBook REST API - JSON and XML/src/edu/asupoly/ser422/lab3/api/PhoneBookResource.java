package edu.asupoly.ser422.lab3.api;

import java.util.List;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

import edu.asupoly.ser422.lab3.model.PhoneBook;
import edu.asupoly.ser422.lab3.model.PhoneEntry;
import edu.asupoly.ser422.lab3.services.PhoneBookService;
import edu.asupoly.ser422.lab3.services.PhoneBookServiceFactory;

@Path("/phonebook")
public class PhoneBookResource extends Throwable implements ExceptionMapper<JsonParsingException> {
	
	@Context
	private UriInfo _uriInfo;
	
	/**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */
	/**
     * @apiDefine ForbiddenError
     * @apiError (Error 4xx) {403} Forbidden Server understood the request but refuses to fulfill it
     * */
    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Resource cannot be found
     * */
	/**
     * @apiDefine MethodNotAllowedError
     * @apiError (Error 4xx) {405} MethodNotAllowed This method is not allowed.
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 405 MethodNotAllowed
     *  { "message": "METHOD NOT ALLOWED! " }
     * */
	/** @apiDefine ConflictError
     * @apiError (Error 4xx) {409} Conflict Conflict with the current state of the resource
     * */
	/**
     * @apiDefine UnsupportedMediaTypeError
     * @apiError (Error 4xx) {415} UnsupportedMediaType MediaType not supported
     * */
    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */
    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */
	/**
	 * @apiDefine CommonHeaders
	 * @apiHeader (Response Headers) {String} Content-Type Content type of the response e.g. application/json
	 * @apiHeader (Response Headers) {String} Content-Language Language of the response e.g. en
	 * @apiHeader (Response Headers) {String} Server Server name e.g. Apache Tomcat 8.5
	 * @apiHeader (Response Headers) {String} Connection Type of connection e.g. close
	 * @apiHeader (Response Headers) {DateTime} Date Date and time the response was sent e.g. Thu, 14 Mar 2019 09:39:54 GMT
	 * */

	/*
	 * Method to override the Jersey Exception error 5xx in case of wrong JSON input by client
	 * The wrong input should result in 4xx error response code
	 * */
	@Override
    public Response toResponse(JsonParsingException exception)
    {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getLocalizedMessage())
                .build();
    }
	
	private PhoneBookService _bservice = PhoneBookServiceFactory.getInstance();
	
	/**
     * @api {get}phonebook/:bookId GET a specific PhoneBook
     * @apiName GetPhoneBook
     * @apiGroup PhoneBook
     * @apiDescription Returns all the Phone Entries present in a PhoneBook.
     * 
     * @apiUse CommonHeaders
     * @apiHeader (Response Headers) {int} Content-Length Length of the content
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     * 
     * @apiUse InternalServerError
     * @apiUse ForbiddenError
     * @apiUse ActivityNotFoundError
     * 
     * @apiParam (Path Parameter) {int} bookId Integer value less than 10 digits.
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phonebook/1
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	[
     * 		{
     * 			"firstname": "King",
     * 			"lastname": "Jeofrey",
     * 			"phone": 99
     * 		},
     * 		{
     * 			"firstname": "WreckIt",
     * 			"lastname": "Ralph",
     * 			"phone": 1234
     * 		}
     * 	]
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<phoneBook>
     * 		<entries>
     * 			<firstname>Abhi</firstname>
     * 			<lastname>Bach</lastname>
     * 			<phone>1</phone>
     * 		</entries>
     * 		<entries>
     * 			<firstname>King</firstname>
     * 			<lastname>Jeofrey</lastname>
     * 			<phone>99</phone>
     * 		</entries>
     * 	</phoneBook>
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  <message>NO RESOURCE FOUND!</message>
     *  
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  {
     *  	"message": "NO RESOURCE FOUND! "
     *  }
     * */
	
	
	/**
     * @api {get} phonebook/:bookId?firstname={param}&lastname={param}  GET subset of the PhoneEntry resources in a PhoneBook
     * @apiName GetSubSetPhoneBook
     * @apiGroup PhoneBook
     * 
	 * @apiParam (Query Parameters) {String} [firstname] Provide the firstname to be searched. Eg: 
	 * ```
	 * firstname=abh
	 * ```
     * @apiParam (Query Parameters) {String} [lastname] Provide the lastname to be searched. Eg: 
	 * ```
	 * lastname=bach
	 *```
	 * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phonebook/1?firstname=abh&lastname=bach
	 * @apiUse CommonHeaders
	 * @apiHeader (Response Headers) {int} Content-Length Length of the content
	 * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
	 * 
	 * @apiUse InternalServerError
	 * @apiUse ForbiddenError
     * @apiUse ActivityNotFoundError
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	{
     * 		"entries": [
     * 			{
     * 				"firstname": "Jaya",
     * 				"lastname": "Bachhan",
     * 				"phone": 12
     * 			},
     * 			{
     * 				"firstname": "Ant",
     * 				"lastname": "Man",
     * 				"phone": 1357
     * 			}
     * 		]
     * 	}
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<phoneBook>
     * 		<entries>
     * 			<firstname>Jaya</firstname>
     * 			<lastname>Bachhan</lastname>
     * 			<phone>12</phone>
     * 		</entries>
     * 		<entries>
     * 			<firstname>Ant</firstname>
     * 			<lastname>Man</lastname>
     * 			<phone>1357</phone>
     * 		</entries>
     * 	</phoneBook>
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  {
     *  	"message": "NO RESOURCE FOUND! "
     *  }
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  <message>NO RESOURCE FOUND!</message>
     * */
	
	@GET
	@Path("/{bookId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSubPhoneBook(@PathParam("bookId") int bookid, 
			@DefaultValue("") @QueryParam("firstname") String fname, 
			@DefaultValue("") @QueryParam("lastname") String lname,
			@DefaultValue("json") @HeaderParam("Accept") String accept) 
	{
		if(accept.contains("xml")){
			if(bookid == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("<message>BOOKID CANNOT BE 0!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(bookid == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("{ \"message\" : \"BOOKID CANNOT BE 0! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		List<PhoneEntry> list = _bservice.getPhoneBookSubset(bookid, fname, lname);
		PhoneBook book = new PhoneBook();
		book.setEntries(list);
		if(accept.contains("xml")) {
			if(list == null) {
				return Response.status(500).entity("<message>EXCEPTION RETRIEVING FROM DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(list.isEmpty()) {
				return Response.status(404).entity("<message>NO RESOURCE FOUND!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(list == null) {
				return Response.status(500).entity("{ \"message\" : \"EXCEPTION RETRIEVING FROM DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(list.isEmpty()) {
				return Response.status(404).entity("{ \"message\" : \"NO RESOURCE FOUND! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(book)
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
	
	/**
     * @api {put}phonebook/:bookId PUT existing entry to a phonebook
     * @apiName AddEntryToBook
     * @apiGroup PhoneBook
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phonebook/1
     * 
     * @apiParam (Request message body) {JSON} PayloadJSON Phone Entry object in JSON format e.g.
     * ```
     * { "firstname": "Lord Of", "lastname": "Rings", "phone": 543987 }
     * ```
     * @apiParam (Request message body) {XML} PayloadXML Phone Entry object in XML format e.g.
     * ```
     * <phoneEntry> <firstname>King</firstname> <lastname>Jeofrey</lastname> <phone>99</phone> </phoneEntry>
     * ``` 
     * @apiParam (Path Parameters) {int} bookId (Required) Provide the bookId in which phone entry has to be added. Eg: 
	 * ```
	 * http://localhost:8080/lab3ec1/rest/phonebook/1
	 * ```
     * @apiUse InternalServerError
     * @apiUse BadRequestError
     * @apiUse ForbiddenError
     * @apiUse ConflictError
     * @apiUse UnsupportedMediaTypeError
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     *
     * @apiHeader (Response Headers) {url} Location Location of the resource updated e.g.
     * ```
     * http://localhost:8080/lab3ec1/rest/phoneEntry/12
     * ```
	 * @apiUse CommonHeaders
	 * @apiHeader (Response Headers) {int} Content-Length Length of the content
	 * 
     * @apiSuccessExample {json} Success-Response:
     * 	HTTP/1.1 200 OK
     * 	{ "Phone entry inserted in book": "2" }
     * 
     * @apiSuccessExample {json} Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<message>Phone entry inserted in book : 2</message>
     * 
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 409 Conflict
     *  { 
     *  	"message": "ENTRY ALREADY IN THE PHONEBOOK 2! " 
     *  }
     *  
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 409 Conflict 
     *  <message>ENTRY ALREADY IN THE PHONEBOOK 2</message>
     *  
     * */
	
	@PUT
	@Path("/{bookId}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addEntryToBook(@PathParam("bookId") int bookid, 
			PhoneEntry newEntry,
			@DefaultValue("json") @HeaderParam("Accept") String accept) 
	{
		if(accept.contains("xml")) {
			if(bookid == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("<message>PLEASE PROVIDE PHONEBOOK ID OTHER THAN 0!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(bookid == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("{ \"message\" : \"PLEASE PROVIDE PHONEBOOK ID OTHER THAN 0! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		int res = _bservice.addPhoneBookEntry(bookid, newEntry);
		if(accept.contains("xml")) {
			if(res == -2) {
				return Response.status(400).entity("<message>ENTRY ALREADY IN THE PHONEBOOK "+bookid+"</message>")
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -1) {
				return Response.status(500).entity("<message>ERROR INSERTING INTO DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -4) {
				return Response.status(404).entity("<message>ENTRY DOES NOT EXIST!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -5) {
				return Response.status(409).entity("<message>ENTRY FOUND UNDER DIFFERENT NAME!</message>")
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -3) {
				return Response.status(500).entity("<message>EXCEPTION INSERTING IN DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -6) {
				return Response.status(200)
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.entity("<message>Phone entry inserted in book : " + bookid + "</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			return Response.status(409).entity("<message>ENTRY ALREADY IN THE PHONEBOOK "+res+"</message>")
					.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			if(res == -2) {
				return Response.status(400).entity("{ \"message\" : \"ENTRY ALREADY IN THE PHONEBOOK "+bookid+"! \"}")
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -1) {
				return Response.status(500).entity("{ \"message\" : \"ERROR INSERTING INTO DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -4) {
				return Response.status(404).entity("{ \"message\" : \"ENTRY DOES NOT EXIST! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -5) {
				return Response.status(409).entity("{ \"message\" : \"ENTRY FOUND UNDER DIFFERENT NAME! \"}")
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -3) {
				return Response.status(500).entity("{ \"message\" : \"EXCEPTION INSERTING IN DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(res == -6) {
				return Response.status(200)
						.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
						.entity("{ \"Phone entry inserted in book\" : \"" + bookid + "\"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			return Response.status(409).entity("{ \"message\" : \"ENTRY ALREADY IN THE PHONEBOOK "+res+"! \"}")
					.header("Location", String.format("%s/%s",_uriInfo.getBaseUri().toString() + "phoneEntry", newEntry.getPhone()))
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doPost(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>METHOD NOT ALLOWED!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			return Response.status(405).entity("{ \"message\" : \"METHOD NOT ALLOWED! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doDelete(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>METHOD NOT ALLOWED!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			return Response.status(405).entity("{ \"message\" : \"METHOD NOT ALLOWED! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	@PATCH
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doPatch(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>METHOD NOT ALLOWED!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			return Response.status(405).entity("{ \"message\" : \"METHOD NOT ALLOWED! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGet(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>PROVIDE BOOK AS PATH PARAMETER TO GET PHONEBOOK RESOURCE!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		return Response.status(405).entity("{ \"message\" : \"PROVIDE BOOK AS PATH PARAMETER TO GET PHONEBOOK RESOURCE! \"}")
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
}