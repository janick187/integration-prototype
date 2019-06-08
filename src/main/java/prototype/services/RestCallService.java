package prototype.services;

	import java.io.IOException;

	import okhttp3.MediaType;
	import okhttp3.OkHttpClient;
	import okhttp3.Request;
	import okhttp3.Request.Builder;
	import okhttp3.RequestBody;
	import okhttp3.Response;
	import okhttp3.ResponseBody;

	import prototype.services.HelperService;
	import prototype.json.parsing.CallApiException;

	/**
	 * <b>Purpose:</b> Provide a possibility to send RestCalls and access data over API's whcih are porvided by external systems.
	 * @author Janick Spirig
	 */
	public abstract class RestCallService extends HelperService {
		
		private static final MediaType JSON = null;
		private final OkHttpClient client;
		private final Builder builder;
		
		public RestCallService() {
			// assign the already in the Main class existing okhttpclient objects. Creating new objects every time would cause a lot of java threads.
			client = GlobalConfigService.getInstance().getOkhttpclient();
			builder = GlobalConfigService.getInstance().getReqbuilder();
		}
		
		/** 	
		 * <b>Purpose:</b> Send GET Rest Call to external API.
		 * @param url the url under which the resource can be accessed
		 * @param loginrequired a boolean value if authorisation at the external API is required. If true, authorisation is required.
		 * @param logintoken a string which represents the login token in case authorisation is required (loginrequired = true)
		 * @return a <code>string</code> which contains the answer received from the external API
		 * @throws CallApiException
		 */
		public String sendGetRequest (String url, boolean loginrequired, String logintoken) throws CallApiException {
			
			Request req = null;
			
			if (loginrequired) {
				// build request object -> Help ID: 5, Source: https://www.stubbornjava.com/posts/okhttp-example-rest-client
				req = builder.url(url).get().header("Authorization", "Bearer " + logintoken).build();
			} else {
				req = builder.url(url).build();
			}
			return executeRequest(req);
		}
		
		/** 	
		 * <b>Purpose:</b> Send POST Rest Call to external API.
		 * @param url the uri under which the data should be saved
		 * @param json the data which should be created in JSON format
		 * @param loginrequired a boolean value if authorisation at the external API is required. If true, authorisation is required.
		 * @param logintoken a string which represents the login token in case authorisation is required (loginrequired = true)
		 * @return a <code>string</code> which contains the answer received from the external API
		 * @throws CallApiException
		 */
		public String sendPostRequest (String url, String json, boolean loginrequired, String logintoken) throws CallApiException {
			
			Request req = null;
			
			// build request object -> Help ID: 6, Source: https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/guide/GetExample.java
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			RequestBody body = RequestBody.create(JSON, json);
			
			if (loginrequired) {
				req = builder.url(url).post(body).header("Authorization", "Bearer " + logintoken).build();
			} else {
				req = builder.url(url).post(body).removeHeader("Authorization").build();
			}
			return executeRequest(req);
		}
		
		/**
		 * <b>Purpose:</b> Send PUT Rest Call to external API in modify existing data.
		 * @return a <code>string</code> which contains the answer received from the external API
		 * @throws Exception
		 */
		
		/** 	
		 * <b>Purpose:</b> Send PUT Rest Call to external API.
		 * @param url the uri under which the data should be modified
		 * @param json the data which should be saved in JSON format
		 * @param loginrequired a boolean value if authorisation at the external API is required. If true, authorisation is required.
		 * @param logintoken a string which represents the login token in case authorisation is required (loginrequired = true)
		 * @return a <code>string</code> which contains the answer received from the external API
		 * @throws CallApiException in case an error occurred during API call
		 */
		public String sendPutRequest (String url, String json, boolean loginrequired, String logintoken) throws CallApiException  {
			
			Request req = null;
			
			// build request object -> Help ID: 5, Source: https://www.stubbornjava.com/posts/okhttp-example-rest-client
			RequestBody body = RequestBody.create(JSON, json);
			
			if (loginrequired) {
				req = builder.url(url).put(body).header("Authorization", "Bearer " + logintoken).build();
			} else {
				req = builder.url(url).put(body).removeHeader("Authorization").build();
			}
			return executeRequest(req);
		}
		
		/**
		 * <b>Purpose: </b>Execute request to an external system.
		 * @param request the OkHttp request object
		 * @return a <code>string</code> which contains the answer received from the external API
		 * @throws CallApiException
		 */
		public String executeRequest (Request request) throws CallApiException {
			
			System.out.println("Request to URL: " + request.url());
		
			Response response = null;
			String responsecontent = "";
			
			// execute request and save answer in responsecontent
			try {
				// execute call and save responce content as string -> Help ID: 5, Source: https://www.stubbornjava.com/posts/okhttp-example-rest-client
				response = client.newCall(request).execute();
				ResponseBody responseBody = response.body();
				responsecontent = responseBody.string();
				// check for external server error
				if (!(response.code() == 200)) {
					CallApiException cae = new CallApiException (new Exception(), "Received NOK response from server: " + response.code() + " Content " + responsecontent + " when calling " + request.url().toString() + " with method " + request.method(), response.code());
					this.handleException(cae);
					throw cae;
				}
			// check for connection and other errors
			} catch (IOException | IllegalStateException e) {
				CallApiException cae = new CallApiException (e, "Technical error occured during call of " + request.url().toString() + " with method " + request.method(), response.code());
				this.handleException(cae);
				throw cae;
			} finally {
				request = null;
			}
			return responsecontent;
		}
	}
