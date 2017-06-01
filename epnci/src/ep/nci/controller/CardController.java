package ep.nci.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ep.nci.enums.EPERROR;
import ep.nci.enums.INPUT;
import ep.nci.utils.Constants;
import ep.nci.utils.StatusCodes;
import epx.exception.SystemException;
import ep.nci.Service.CardService;

@RestController
public class CardController {
	
	private Logger logger = Logger.getLogger(CardController.class);
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    CardService cardService;
	
	@RequestMapping(value = "cardLoad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadcard(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of cardLoad");
		ObjectNode outputNode ;
		try { 
			ObjectNode inputNode = (ObjectNode) mapper.readTree(jsonInputString);

			outputNode = cardService.cardLoad(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("cardLoad:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("cardLoad:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("cardLoad:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of cardLoad");
		return outputNode.toString();
	}
	
	
	
	@RequestMapping(value = "txnhistory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String gettxnHistory(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of txnhistory");
		ObjectNode outputNode ;
		try { 
			ObjectNode inputNode = (ObjectNode) mapper.readTree(jsonInputString);

			outputNode = cardService.txnHistory(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("txnhistory:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("txnhistory:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("txnhistory:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of txnhistory");
		String output = outputNode.toString().replace("\\", "");
			
		return output;
		
	}
	
	@RequestMapping(value = "/checkBalance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String checkBalance(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of txnhistory");
		ObjectNode outputNode ;
		try { 
			ObjectNode inputNode = (ObjectNode) mapper.readTree(jsonInputString);

			outputNode = cardService.checkBalance(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("txnhistory:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("txnhistory:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("txnhistory:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of txnhistory");
		return outputNode.toString();
	}


}
