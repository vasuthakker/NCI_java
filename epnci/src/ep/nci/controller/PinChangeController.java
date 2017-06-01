package ep.nci.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ep.nci.Service.*;
import ep.nci.enums.*;
import ep.nci.utils.*;
import ep.nci.utils.StatusCodes;
import epx.exception.SystemException;

@RestController
public class PinChangeController {
	private Logger logger = Logger.getLogger(LoginController.class);

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private PinChangeService pinChangeService;

	@RequestMapping(value = "/pinChange", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String PinChange(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of PinChangeController");
		JsonNode outputNode ;
		try {
			JsonNode inputNode = mapper.readTree(jsonInputString);

			outputNode = pinChangeService.PinChange(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("PinChangeStatus:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("PinChangeStatus:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("PinChangeStatus:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of PinChangeController");
		return outputNode.toString();
	}

}


