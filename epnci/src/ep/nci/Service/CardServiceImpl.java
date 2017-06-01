package ep.nci.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Strings;

import ep.nci.enums.CARDSERVICE;
import ep.nci.enums.BILLER;
import ep.nci.model.InputStreamToString;
import ep.nci.utils.Constants;
import ep.nci.utils.ToGetToken;
import ep.nci.utils.Utils;
import epx.exception.SystemException;

@Service
public class CardServiceImpl implements CardService {
	static final Logger logger = Logger.getLogger(CardServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();
	private static final String CHARSET_UTF8 = "UTF-8";
	Utils utils = new Utils();

	InputStreamToString inputStreamToString = new InputStreamToString();

	@Override
	@Transactional
	public ObjectNode cardLoad(ObjectNode inputNode) {
		return generalServiceCore(inputNode, Constants.CALLABLE_SERVICE_NAME_CARDLOAD, Constants.EPCORESERVICES, null);
	}

	@Override
	@Transactional
	public ObjectNode txnHistory(ObjectNode inputNode) {
		ObjectNode dataNode = mapper.createObjectNode();
		dataNode = (ObjectNode) inputNode.get("DATA");
		String mobileNumber = dataNode.get("mobileNumber").asText();
		if(mobileNumber==null || mobileNumber.isEmpty()){throw new SystemException(CARDSERVICE.MOBILENUMBER_NOT_PROVIDED);}
		return generalServiceCore(inputNode, Constants.CALLABLE_SERVICE_NAME_TXNHISTORY, Constants.EPCORESERVICES,
				null);
	}
	
	@Override
	@Transactional
	public ObjectNode checkBalance(ObjectNode inputNode) {
		return generalServiceCore(inputNode, Constants.CALLABLE_SERVICE_NAME_CHECKBALANCE, Constants.EPCORESERVICES,
				null);
	}

	@Transactional
	public ObjectNode generalServiceCore(JsonNode inputNode, String callableServiceName, String routeServiceName,
			String checksum) throws SystemException {
		logger.info("Start of" + callableServiceName);

		ObjectNode respNode = mapper.createObjectNode();
		ObjectNode dataNode = mapper.createObjectNode();
		ObjectNode headerNode = mapper.createObjectNode();
		ObjectNode requestNode = mapper.createObjectNode();

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = null;
		try {

			// Utils utils = Utils.getInstance();

			logger.info("calling " + routeServiceName + " " + callableServiceName);
			URIBuilder builder = new URIBuilder();

			String host = utils.getInstance().getSetting(Constants.HOST);
			String protocol = utils.getInstance().getSetting(Constants.PROTOCOL);
			String port = utils.getInstance().getSetting(Constants.PORT);
			String path = utils.getInstance().getSetting(Constants.PATH);
			/*
			 * String username = utils .getSetting(PROP_TXN_ROUT_PREFIX +
			 * ROUTEMIDDLEWARESHORTNAME + PROP_TXN_ROUT_USERNAME); String
			 * password = utils .getSetting(PROP_TXN_ROUT_PREFIX +
			 * ROUTEMIDDLEWARESHORTNAME + PROP_TXN_ROUT_PASSWORD);
			 */

			builder = builder.setScheme(protocol).setHost(host).setPort(Integer.valueOf(port))
					.setPath(path + callableServiceName);

			URI uri = builder.build();
			logger.debug("uri ::" + uri);

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			;
			HttpPost request = new HttpPost(uri);

			requestNode = (ObjectNode) inputNode;
			logger.info("requestNode::" + requestNode);

			headerNode = (ObjectNode) requestNode.get("HEADER");

			headerNode.put(utils.getInstance().getSetting(Constants.PARTNER_CODE), utils.getInstance().getSetting(Constants.PARTNER_CODE_VALUE));
			headerNode.put("ST", "CARDLOAD" );
			headerNode.put("OP", "NCINUC");
			headerNode.put("AID", "");

			requestNode.put("HEADER", headerNode);
			logger.info("requestNode::" + requestNode);

			postParameters.add(new BasicNameValuePair(Constants.JSONDATA, requestNode.toString()));

			String accessTokenCore = null;

			if (Strings.isNullOrEmpty(accessTokenCore)) {
				// Set access token in class variable
				String accessTokenCoreData = ToGetToken.getAccessTokenCore(routeServiceName);
				try {
					dataNode = (ObjectNode) mapper.readTree(accessTokenCoreData);
				} catch (JsonProcessingException e) {
					logger.debug("exception", e);
					e.printStackTrace();
				} catch (IOException e) {
					logger.debug("exception", e);
					e.printStackTrace();
				}
				accessTokenCore = dataNode.get("access_token").asText();
			}

			// StringEntity params = new StringEntity(inputNode.toString());

			/*
			 * String accessTokenData=getAccessTokenCore(routeServiceName); try
			 * { dataNode = (ObjectNode) mapper.readTree(accessTokenData); }
			 * catch (JsonProcessingException e) { logger.debug("exception",e);
			 * e.printStackTrace(); } catch (IOException e) {
			 * logger.debug("exception",e); e.printStackTrace(); } accessToken =
			 * dataNode.get("access_token").asText();
			 */
			// accessTokenCore
			logger.debug("accessTokenCore::" + accessTokenCore);
			request.setHeader("Authorization", "Bearer " + accessTokenCore);

			request.addHeader("content-type", "application/x-www-form-urlencoded");
			if (checksum != null) {
				request.addHeader("CHECKSUM", checksum);
			}
			try {
				request.setEntity(new UrlEncodedFormEntity(postParameters));
				HttpResponse httpResponse = httpClient.execute(request);

				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					result = inputStreamToString.convertStreamToString(instream);
					respNode = (ObjectNode) mapper.readTree(result);

					if (respNode.has("ERROR") && "INVALID_TOKEN".equals(respNode.get("ERROR").asText())) {

						logger.info("Token invalid re-generating the token ");
						String accessTokenCoreData = ToGetToken.getAccessTokenCore(routeServiceName);
						try {
							dataNode = (ObjectNode) mapper.readTree(accessTokenCoreData);
						} catch (JsonProcessingException e) {
							logger.debug("exception", e);
							e.printStackTrace();
						} catch (IOException e) {
							logger.debug("exception", e);
							e.printStackTrace();
						}
						accessTokenCore = dataNode.get("access_token").asText();
						request.setHeader("Authorization", "Bearer " + accessTokenCore);

						request.addHeader("content-type", "application/x-www-form-urlencoded");

						request.setEntity(new UrlEncodedFormEntity(postParameters));
						httpResponse = httpClient.execute(request);

						entity = httpResponse.getEntity();
						if (entity != null) {
							instream = entity.getContent();
							result = inputStreamToString.convertStreamToString(instream);
							respNode = (ObjectNode) mapper.readTree(result);

						}
					}
				}
			} catch (final HttpClientErrorException httpClientErrorException) {

				logger.info("Token Expired re-generating the token ");
				ToGetToken.getAccessTokenCore(routeServiceName);
				request.setHeader("Authorization", "Bearer " + accessTokenCore);

				request.addHeader("content-type", "application/x-www-form-urlencoded");

				request.setEntity(new UrlEncodedFormEntity(postParameters));
				HttpResponse httpResponse = httpClient.execute(request);

				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					result = inputStreamToString.convertStreamToString(instream);
					respNode = (ObjectNode) mapper.readTree(result);

				}

			}
		} catch (IOException | URISyntaxException ex) {
			logger.fatal("error", ex);
			throw new SystemException(BILLER.BILLER_SERVICE_NOT_REACHABLE);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.debug("End of " + callableServiceName);
		return respNode;
	}

}
