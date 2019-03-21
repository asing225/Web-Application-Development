package edu.asupoly.ser422.lab3.api;

/**
 * @author amanjotsingh
 * Date - 03/12/2019
 * 
 * Java class to implement Phone Entry Resource for the REST API.
 * */

import java.util.List;
import java.util.Map;

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

@Path("/phoneEntry")
public class PhoneEntryResource implements ExceptionMapper<JsonParsingException> {
	
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
	
	@Context
	private UriInfo _uriInfo;
	
	/**
     * @api {get}phoneEntry/:phone GET phone entry
     * @apiName GetPhoneEntry
     * @apiGroup PhoneEntry
     * @apiDescription Returns PhoneEntry resource based on phone.
     * 
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     * 
     * @apiUse CommonHeaders
     * @apiHeader (Response Headers) {int} Content-Length Length of the content
     * @apiUse ActivityNotFoundError
     * @apiUse InternalServerError
     * 
     * @apiParam (Path Parameter) {int} phone Integer value less than 10 digits.
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phoneEntry/99
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<phoneEntry>
     * 		<firstname>King</firstname>
     * 		<lastname>Jeofrey</lastname>
     * 		<phone>99</phone>
     * 	</phoneEntry>
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	{
     * 		"firstname": "King",
     * 		"lastname": "Jeofrey",
     * 		"phone": 99
     * 	}
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  {
     *  	"message": "NO SUCH ENTRY FOUND! "
     *  }
     *  
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND 
     *  <message>NO SUCH ENTRY FOUND!</message>
     * */
	
	/*
	 * Get method to return specific phone entry based on phone number,
	 * takes phone number as path parameter.
	 */	
	@GET
	@Path("/{phone}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getPhoneEntry(@PathParam("phone") int phone, 
			@DefaultValue("json") @HeaderParam("Accept") String accept) {
		Map<String, PhoneEntry> entry = _bservice.getPhoneEntry(phone);
		if(accept.contains("xml")) {
			if(entry == null) {
				return Response.status(500)
						.entity("<message>ERROR OCCURRED IN DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.containsKey("Empty")) {
				return Response.status(404)
						.entity("<message>NO SUCH ENTRY FOUND!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.containsKey("Exception")) {
				return Response.status(500)
						.entity("<message>EXCEPTION OCCURRED IN DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(entry == null) {
				return Response.status(500)
						.entity("{ \"message\" : \"ERROR OCCURRED IN DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.containsKey("Empty")) {
				return Response.status(404)
						.entity("{ \"message\" : \"NO SUCH ENTRY FOUND! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.containsKey("Exception")) {
				return Response.status(500)
						.entity("{ \"message\" : \"EXCEPTION OCCURRED IN DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(entry.get("Data"))
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
	
	/**
     * @api {get}phoneEntry/unlisted GET unlisted entries
     * @apiName GetUnlistedEntries
     * @apiGroup PhoneEntry
     * @apiDescription Returns all unlisted PhoneEntry resources
     * 
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     * 
     * @apiUse CommonHeaders
     * @apiHeader (Response Headers) {int} Content-Length Length of the content
     * @apiUse ActivityNotFoundError
     * @apiUse InternalServerError
     * 
     * @apiParam (Path Parameter) {int} phone Integer value less than 10 digits.
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phoneEntry/unlisted
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	[
     * 		{
     * 			"firstname": "Nik",
     * 			"lastname": "Ralph",
     * 			"phone": 97
     * 		},
     * 		{
     * 			"firstname": "Maisy",
     * 			"lastname": "Williams",
     * 			"phone": 560
     * 		},
     * 		{
     * 			"firstname": "Amanjot",
     * 			"lastname": "Singh",
     * 			"phone": 19287
     * 		}
     * 	]
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<phoneBook>
     * 		<entries>
     * 			<firstname>Iron</firstname>
     * 			<lastname>Man</lastname>
     * 			<phone>11</phone>
     * 		</entries>
     * 		<entries>
     * 			<firstname>Nik</firstname>
     * 			<lastname>Ralph</lastname>
     * 			<phone>97</phone>
     * 		</entries>
     * 		<entries>
     * 			<firstname>Maisy</firstname>
     * 			<lastname>Williams</lastname>
     * 			<phone>560</phone>
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
	
	//Get method to return all entries not listed in any phone book,
	@GET
	@Path("/unlisted")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getUnlistedEntries(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		List<PhoneEntry> unlisted = _bservice.getUnlistedEntries();
		PhoneBook book = new PhoneBook();
		book.setEntries(unlisted);
		if(accept.contains("xml")) {
			if(unlisted == null) {
				return Response.status(500).entity("<message>EXCEPTION RETRIEVING FROM DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			if(unlisted.isEmpty()) {
				return Response.status(404).entity("<message>NO RESOURCE FOUND!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		if(unlisted == null) {
			return Response.status(500).entity("{ \"message\" : \"EXCEPTION RETRIEVING FROM DATABASE! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		if(unlisted.isEmpty()) {
			return Response.status(404).entity("{ \"message\" : \"NO RESOURCE FOUND! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		return Response.status(Response.Status.OK).entity(book)
				.build();
	}
	
	/**
     * @api {post}phoneEntry Create phone entry
     * @apiName CreateEntry
     * @apiGroup PhoneEntry
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phoneEntry
     * 
     * @apiParam (Request message body) {JSON} PayloadJSON Phone Entry object in JSON format e.g.
     * ```
     * { "firstname": "Lord Of", "lastname": "Rings", "phone": 543987 }
     * ```
     * @apiParam (Request message body) {XML} PayloadXML Phone Entry object in XML format e.g.
     * ```
     * <phoneEntry> <firstname>Maisy</firstname> <lastname>Williams</lastname> <phone>560</phone> </phoneEntry>
     * ```
     * @apiUse InternalServerError
     * @apiUse BadRequestError
     * @apiUse ForbiddenError
     * @apiUse ConflictError
     * @apiUse UnsupportedMediaTypeError
     * 
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     *
     * @apiHeader (Response Headers) {url} Location Location of the resource updated e.g.
     * ```
     * http://localhost:8080/lab3ec1/rest/phoneEntry/12345
     * ```
	 * @apiUse CommonHeaders
	 * @apiHeader (Response Headers) {int} Content-Length Length of the content
	 * @apiHeader (Response Headers) {url} Location Location of the resource changed e.g.
     * ```
     * http://localhost:8080/lab3ec1/rest/phoneEntry/83479
     * ```
	 * 
     * @apiSuccessExample {json} Success-Response:
     * 	HTTP/1.1 201 CREATED
     * 	{
     * 		"message": "ENTRY CREATED!"
     * 	}
     * @apiSuccessExample {json} Success-Response:
     * 	HTTP/1.1 201 CREATED
     * 	<message>ENTRY CREATED!</message>
     * 
     * 
     * 
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 409 Conflict
     *  { 
     *  	"message": "ENTRY ALREADY EXISTS!" 
     *  }
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 409 Conflict 
     *  <message>ENTRY ALREADY EXISTS!</message>
     * 
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 400 BadRequest
     *  { 
     *  	"message": "PLEASE PROVIDE FIRSTNAME IN REQUEST!" 
     *  }
     * @apiErrorExample Error-Response:
     *  HTTP/1.1 400 BadRequest 
     *  <message>PLEASE PROVIDE FIRSTNAME IN REQUEST!</message>
     *  
     *  
     * */
	
	//POST method to create a new phone entry.
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createPhoneEntry(
			PhoneEntry entry, 
			@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			if(entry.getFirstname() == null || entry.getFirstname().length() == 0) {
				return Response.status(400).entity("<message>PLEASE PROVIDE FIRSTNAME IN REQUEST!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.getLastname() == null || entry.getLastname().length() == 0) {
				return Response.status(400).entity("<message>PLEASE PROVIDE LASTNAME IN REQUEST!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.getPhone() == 0) {
				return Response.status(403).entity("<message>PHONE CAN NOT BE 0!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else{
			if(entry.getFirstname() == null || entry.getFirstname().length() == 0) {
				return Response.status(400).entity("{ \"message\" : \"PLEASE PROVIDE FIRSTNAME IN REQUEST! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.getLastname() == null || entry.getLastname().length() == 0) {
				return Response.status(400).entity("{ \"message\" : \"PLEASE PROVIDE LASTNAME IN REQUEST! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(entry.getPhone() == 0) {
				return Response.status(403).entity("{ \"message\" : \"PHONE CAN NOT BE 0! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		
		int res = _bservice.createPhoneEntry(entry.getFirstname(), 
				entry.getLastname(), entry.getPhone());
		if(accept.contains("xml")) {
			if (res == -3) {
				// response code 409 for conflict
				return Response.status(409).entity("<message>ENTRY ALREADY EXISTS!</message>")
						.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), entry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if (res == -2) {
				// response code 500 for internal server error
				return Response.status(500).entity("<message>EXCEPTION INSERTING INTO DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if (res == -1) {
				// response code 500 for internal server error
				return Response.status(500).entity("<message>ERROR INSERTING INTO DATABASE!</message>")
						.header("Content-Language", "en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			return Response.status(201)
					.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), res))
					.entity("<message>ENTRY CREATED!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			if (res == -3) {
				// response code 409 for conflict
				return Response.status(409).entity("{ \"message\" : \"ENTRY ALREADY EXISTS! \"}")
						.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), entry.getPhone()))
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if (res == -2) {
				// response code 500 for internal server error
				return Response.status(500).entity("{ \"message\" : \"EXCEPTION INSERTING INTO DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if (res == -1) {
				// response code 500 for internal server error
				return Response.status(500).entity("{ \"message\" : \"ERROR INSERTING INTO DATABASE! \"}")
						.header("Content-Language", "en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			return Response.status(201)
					.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), res))
					.entity("{ \"message\" : \"ENTRY CREATED! \"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	/**
     * @api {patch} phoneEntry Change firstname and/or lastname
     * @apiName PatchPhoneEntry
     * @apiGroup PhoneEntry
     * 
	 * @apiParam (Request message body) {JSON} PayloadJSON Phone Entry object in JSON format e.g.
     * ```
     * { "firstname": "The", "lastname": "Mountain", "phone": 83479 }
     * ```
     * @apiParam (Request message body) {XML} PayloadXML Phone Entry object in XML format e.g.
     * ```
     * <phoneEntry> <firstname>Maisy</firstname> <lastname>Williams</lastname> <phone>83479</phone> </phoneEntry>
     * ```
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3ec1/rest/phoneEntry
	 * @apiUse CommonHeaders
	 * @apiHeader (Response Headers) {int} Content-Length Length of the content
	 * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
	 * 
	 * @apiUse InternalServerError
	 * @apiUse BadRequestError
	 * @apiUse ActivityNotFoundError
	 * @apiUse UnsupportedMediaTypeError
     *
     * @apiHeader (Response Headers) {url} Location Location of the resource changed e.g.
     * ```
     * http://localhost:8080/lab3ec1/rest/phoneEntry/83479
     * ```
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	{
     * 		"firstname": "Strong",
     * 		"lastname": "Man",
     * 		"phone": 83479
     * 	}
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<phoneEntry>
     * 		<firstname>Arya</firstname>
     * 		<lastname>Stark</lastname>
     * 		<phone>560</phone>
     * 	</phoneEntry>
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  {
     *  	"message": "NO ENTRY with phone 8347932! "
     *  }
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND 
     *  <message>NO ENTRY WITH PHONE 83435479!</message>
     * */
	
	@PATCH
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updatePhoneEntry(PhoneEntry updateEntry, @DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			if(updateEntry.getFirstname() == null || updateEntry.getFirstname().length() == 0) {
				return Response.status(400).entity("<message>PLEASE PROVIDE FIRSTNAME IN REQUEST!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(updateEntry.getLastname() == null || updateEntry.getLastname().length() == 0) {
				return Response.status(400).entity("<message>PLEASE PROVIDE LASTNAME IN REQUEST!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(updateEntry.getPhone() == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("<message>PHONE CANNOT BE 0!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(updateEntry.getFirstname() == null || updateEntry.getFirstname().length() == 0) {
				return Response.status(400).entity("{ \"message\" : \"PLEASE PROVIDE FIRSTNAME IN REQUEST! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(updateEntry.getLastname() == null || updateEntry.getLastname().length() == 0) {
				return Response.status(400).entity("{ \"message\" : \"PLEASE PROVIDE LASTNAME IN REQUEST! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(updateEntry.getPhone() == 0) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("{ \"message\" : \"PHONE CANNOT BE 0! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		int res = _bservice.updatePhoneEntry(new PhoneEntry(updateEntry.getFirstname(), updateEntry.getLastname(), updateEntry.getPhone()));
		if(accept.contains("xml")) {
			if(res == -1) {
				return Response.status(500).entity("<message>EXCEPTION INSERTING INTO DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(res == 0) {
				return Response.status(404).entity("<message>NO ENTRY WITH PHONE " + updateEntry.getPhone() + "!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		else {
			if(res == -1) {
				return Response.status(500).entity("{ \"message\" : \"EXCEPTION INSERTING INTO DATABASE! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else if(res == 0) {
				return Response.status(404).entity("{ \"message\" : \"NO ENTRY WITH PHONE "+ updateEntry.getPhone() +"! \"}")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		return Response.status(200).entity(updateEntry)
				.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), updateEntry.getPhone()))
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
	
	/**
     * @api {delete} phoneEntry?phone={param} Delete an entry
     * @apiName DeletePhoneEntry
     * @apiGroup PhoneEntry
     * 
     * @apiHeader (Request Headers) {application/xml} Accept XML response based on request headers
     * @apiHeader (Request Headers) {application/json} Content-Type JSON response is default response type
     * 
	 * @apiParam (Query Parameter) {int} phone Provide the phone to be deleted. Eg: 
	 * ```
	 * phone=23456
	 * ```
     * @apiExample Example usage:
	 *	http://localhost:8080/lab3/rest/phoneEntry?phone=67890
	 * @apiUse CommonHeaders
	 * @apiUse ActivityNotFoundError
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 204 NO CONTENT
     * 
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND
     *  { 
     *  	"message " : "NO ENTRY WITH PHONE 67890."
     *  }
     * @apiErrorExample Error-Response
     *  HTTP/1.1 404 NOT FOUND  
     *  <message>NO ENTRY WITH PHONE 67890</message>
     * */
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deletePhoneEntry(@QueryParam("phone") int toBeDeleted, 
			@DefaultValue("json") @HeaderParam("Accept") String accept) {
		int res = _bservice.deletePhoneEntry(toBeDeleted);
		if(res == 1) {
			return Response.status(204)
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		if(accept.contains("xml")) {
			if(res == -1){
				return Response.status(404).entity("<message>NO ENTRY WITH PHONE " + toBeDeleted +"</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
			else {
				return Response.status(500).entity("<message>EXCEPTION OCCURRED IN DATABASE!</message>")
						.language("en")
						.header("Connection", "close")
						.header("Server", "Apache Tomcat 8")
						.build();
			}
		}
		if(res == -1){
			return Response.status(404).entity("{ \"message \" : \"NO ENTRY WITH PHONE " + toBeDeleted +".\"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		else {
			return Response.status(500).entity("{ \"message \" : \"EXCEPTION OCCURRED IN DATABASE.\"}")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doPut(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>METHOD NOT ALLOWED!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		return Response.status(405).entity("{ \"message\" : \"METHOD NOT ALLOWED! \"}")
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGet(@DefaultValue("json") @HeaderParam("Accept") String accept) {
		if(accept.contains("xml")) {
			return Response.status(405).entity("<message>PROVIDE PHONE AS PATH PARAMETER TO GET PHONE ENTRY RESOURCE!</message>")
					.language("en")
					.header("Connection", "close")
					.header("Server", "Apache Tomcat 8")
					.build();
		}
		return Response.status(405).entity("{ \"message\" : \"PROVIDE PHONE AS PATH PARAMETER TO GET PHONE ENTRY RESOURCE! \"}")
				.language("en")
				.header("Connection", "close")
				.header("Server", "Apache Tomcat 8")
				.build();
	}
}
