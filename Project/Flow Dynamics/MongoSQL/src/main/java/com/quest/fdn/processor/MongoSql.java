package com.quest.fdn.processor;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Hello world!
 *
 */
public class MongoSql {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	static OkHttpClient client = new OkHttpClient();
	static Scanner sc = new Scanner(System.in);
    public static void main( String[] args ) throws IOException{
        
    	String rootGroupId = getRootGroupID();
    	
    	String getMongoId = createGetMongoProcessor(rootGroupId);
    	System.out.println(getMongoId);
    	updateGetMongo(getMongoId);
    	
    	String evaluateJsonId = createEvaluateJsonPathProcessor(rootGroupId);
    	
    	createMongoJsonPathProcessorConnection(rootGroupId,getMongoId,evaluateJsonId);
    	
    	updateEvaluateJson(evaluateJsonId);
    	
    //	getPropertyEvaluateJson(evaluateJsonId);
    	
    }

	
	private static void getPropertyEvaluateJson(String evaluateJsonId) {
		System.out.println("Enter the ");
		
	}


	private static void createMongoJsonPathProcessorConnection(String rootGroupId, String getMongoId,
			String evaluateJsonId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://localhost:8085/nifi-api/process-groups/"+rootGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+getMongoId+"\",\"groupId\":\""+rootGroupId+"\",\"type\":\"PROCESSOR\"},\"destination\":{\"id\":\""+evaluateJsonId+"\",\"groupId\":\""+rootGroupId+"\",\"type\":\"PROCESSOR\"},\"selectedRelationships\":[\"failure\",\"original\",\"success\"],\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[],\"loadBalanceStrategy\":\"DO_NOT_LOAD_BALANCE\",\"loadBalancePartitionAttribute\":\"\",\"loadBalanceCompression\":\"DO_NOT_COMPRESS\"}}";
		doPostRequest(url, content);
		
	}


	private static void updateEvaluateJson(String evaluateJsonId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://localhost:8085/nifi-api/processors/"+evaluateJsonId;
		String content = "{\"component\":{\"id\":\""+evaluateJsonId+"\",\"name\":\"EvaluateJsonPath\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"0 sec\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"runDurationMillis\":0,\"autoTerminatedRelationships\":[],\"properties\":{\"dob\":\"$.dob\",\"name\":\"$.name\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":3},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);
	}


	private static String createEvaluateJsonPathProcessor(String rootGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://localhost:8085/nifi-api/process-groups/"+rootGroupId+"/processors";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.standard.EvaluateJsonPath\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-standard-nar\",\"version\":\"1.8.0\"},\"name\":\"EvaluateJsonPath\",\"position\":{\"x\":2299.646728515625,\"y\":671.4864807128906}}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonPrimitive("id").getAsString();
	}


	private static void updateGetMongo(String getMongoId) throws IOException {
		
		
		System.out.println("Enter Mongo DB URL :");
		String dbUrl = sc.nextLine();
		
		System.out.println("Eneter DB name :");
		String db = sc.nextLine();
		
		System.out.println("Enter collection name :");
		String collection = sc.nextLine();
		
		UUID clientId = UUID.randomUUID();
		String url = "http://localhost:8085/nifi-api/processors/"+getMongoId;
	//	String content = "{\"component\":{\"id\":\""+getMongoId+"\",\"name\":\"GetMongo\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"0 sec\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"autoTerminatedRelationships\":[],\"properties\":{\"Mongo URI\":\""+dbUrl+"\",\"Mongo Database Name\":\""+db+"\",\"Mongo Collection Name\":\""+collection+"\",\"Projection\":\"{\\\"_id\\\":0}\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1},\"disconnectedNodeAcknowledged\":false}";
		String content = "{\"component\":{\"id\":\""+getMongoId+"\",\"name\":\"GetMongo\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"0 sec\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"autoTerminatedRelationships\":[\"failure\",\"original\",\"success\"],\"properties\":{\"Mongo URI\":\""+dbUrl+"\",\"Mongo Database Name\":\""+db+"\",\"Mongo Collection Name\":\""+collection+"\",\"Projection\":\"{\\\"_id\\\":0}\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);	
		
	}


	private static String createGetMongoProcessor(String rootGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://localhost:8085/nifi-api/process-groups/"+rootGroupId+"/processors";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.mongodb.GetMongo\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-mongodb-nar\",\"version\":\"1.8.0\"},\"name\":\"GetMongo\",\"position\":{\"x\":1798.646728515625,\"y\":609.4864807128906}}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonPrimitive("id").getAsString();
	}

	private static String getRootGroupID() throws IOException {
		String url = "http://localhost:8085/nifi-api/flow/process-groups/root";
		String response = doGetRequest(url);
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonObject("processGroupFlow").get("id")
				.getAsString();
	}

	public static String doPutRequest(String url, String content) throws IOException {

		RequestBody body = RequestBody.create(JSON, content);
		Request request = new Request.Builder().url(url).put(body).build();
		Response response = client.newCall(request).execute();
		System.out.println("doPut :"+response);
		return response.body().string();
	}
	private static String doGetRequest(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		System.out.println(response + " 1");
		return response.body().string();
	}
	private static String doPostRequest(String url, String content) throws IOException {
		RequestBody body = RequestBody.create(JSON, content);
		Request request = new Request.Builder().url(url).post(body).build();
		System.out.println(request);
		Response response = client.newCall(request).execute();
		System.out.println(response + " 2");
		return response.body().string();
	}
}
