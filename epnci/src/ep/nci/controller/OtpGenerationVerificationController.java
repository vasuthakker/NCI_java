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

import ep.nci.enums.EPERROR;
import ep.nci.enums.INPUT;
import ep.nci.Service.*;
import ep.nci.enums.*;
import ep.nci.utils.*;
import ep.nci.utils.StatusCodes;
import epx.exception.SystemException;
import ep.nci.Service.*;
import ep.nci.model.*;

@RestController
public class OtpGenerationVerificationController {
	private Logger logger = Logger.getLogger(OtpGenerationVerificationController.class);

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    OTPGenVerService otpGenVerService;

	@RequestMapping(value = "/OtpGenerationStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getGenerationStatus(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of OtpGenerationController");
		ObjectNode outputNode=null;
		DeviceMst usrotp;
		try {
			JsonNode inputNode = mapper.readTree(jsonInputString);

			outputNode=otpGenVerService.generateOtp(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("loginStatus:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("loginStatus:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("loginStatus:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of LoginController");
		return outputNode.toString();
	}
	
	
	@RequestMapping(value = "/OtpVerifyStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getOtpVerifyStatus(@RequestBody String jsonInputString, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Start of OtpVerificationController");
		ObjectNode outputNode ;
		try {
			JsonNode inputNode = mapper.readTree(jsonInputString);

			outputNode = otpGenVerService.verifyOtp(inputNode);

			// TODO process input node
		} catch (IOException ie) {
			logger.error("VerificationStatus:: Invalid json input", ie);
			return StatusCodes.createStatusNode(new SystemException(INPUT.INVALID_JSON_FORMAT)).toString();
		} catch (SystemException se) {
			logger.fatal("VerificationStatus:: Server Error", se);
			return StatusCodes.createStatusNode(se).toString();
		} catch (Exception e) {
			logger.fatal("VerificationStatus:: Server Error", e);
			return StatusCodes.createStatusNode(new SystemException(EPERROR.SERVER_ERROR)).toString();
		}
		logger.info("End of OtpVerificationController");
		return outputNode.toString();
	}


}
