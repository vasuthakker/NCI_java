package ep.nci.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import ep.nci.model.InputStreamToString;
import epx.exception.SystemException;


public class ToGetToken {
	
	
	static final Logger logger = Logger.getLogger(ToGetToken.class);
	
	@SuppressWarnings({"unused" })
	public static String getAccessTokenCore(String routeServiceName) throws SystemException {
		
		InputStreamToString inputStreamToString = new InputStreamToString();
		
		Utils utils = null;
		try {
			utils = Utils.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = null;
		try {

			URIBuilder builder = new URIBuilder();

			String host     = utils.getSetting(Constants.HOST1);
			String protocol = utils.getSetting(Constants.PROTOCOL);
			String port     = utils.getSetting(Constants.PORT1);
			String path     = utils.getSetting(Constants.PATH);
		//	String username = utils.getSetting(PROP_TXN_ROUT_PREFIX + routeServiceName + PROP_TXN_ROUT_USERNAME);
		//	String password = utils.getSetting(PROP_TXN_ROUT_PREFIX + routeServiceName + PROP_TXN_ROUT_PASSWORD);
			String oauthpath = utils.getSetting(Constants.OAUTH_PATH);

			builder = builder.setScheme(protocol).setHost(host).setPort(Integer.valueOf(port)).setPath(path + oauthpath)
					.setParameter(Constants.GRANT_TYPE, Constants.PARTNER_GRANT_TYPE_VALUE);
					/*.setParameter(Constants.CLIENT_ID,
							utils.getSetting(Constants.CLIENT_ID_VALUE))
					.setParameter(Constants.CLIENT_SECRET,
							utils.getSetting(Constants.CLIENT_SECRET_VALUE));*/

			URI uri = builder.build();
			logger.debug("uri ::" + uri);
			HttpPost request = new HttpPost(uri);

			StringEntity params = new StringEntity("");

			Base64 b = new Base64();
			String userBaseTokenString = utils.getSetting(Constants.CLIENT_ID_VALUE) + ":" + utils.getSetting(Constants.CLIENT_SECRET_VALUE);
			String encoding = b.encodeAsString(new String(userBaseTokenString).getBytes());

			request.setHeader("Authorization", "Basic " + encoding);

			request.addHeader("content-type", "application/json");

			request.setEntity(params);
			HttpResponse httpResponse = httpClient.execute(request);

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = inputStreamToString.convertStreamToString(instream);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}


}
