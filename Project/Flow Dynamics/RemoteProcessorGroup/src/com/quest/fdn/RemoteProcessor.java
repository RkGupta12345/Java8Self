package com.quest.fdn;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.JsonParser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class RemoteProcessor {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	static OkHttpClient client = new OkHttpClient();
	
    public static void main( String[] args ) throws IOException{
    	
    	String rootRemoteGroupId = getRootGroupID();
    	System.out.println("Root Remote Group Id :"+rootRemoteGroupId);
    	String rootGroupId = getRootGroup();
    	System.out.println("Root group Id :"+rootGroupId);

    	String executeSQLId = createSQLProcessor(rootRemoteGroupId);
    	String avroJsonId = createAvroJsonProcessor(rootRemoteGroupId);
    	createSQLAvroJsonConnection(rootRemoteGroupId,executeSQLId,avroJsonId);
    	
    	String controllerServiceId = createControllerService(rootRemoteGroupId); 
    	
    	System.out.println("Controller Service ID :"+controllerServiceId);
    	updateSQLProcessor(executeSQLId,controllerServiceId);
    	updateSQLQueryProcessor(executeSQLId);
    	String DateProcessId = createDateConvertor(rootRemoteGroupId);
        createAvroJsonConcateConnection(rootRemoteGroupId,avroJsonId,DateProcessId);
    	
    	
    	String remoteProcessID = createRemoteProcessGroup(rootRemoteGroupId);
    	
    	createConcateDateConncection(rootRemoteGroupId,DateProcessId,remoteProcessID);
    	
    	updateDateProcessor(DateProcessId);
    	
    	
    	String inportId = createInputPort(rootGroupId);
    	
    	
    	//String genearteFlowFileId = createflowFile(rootRemoteGroupId);
    	
    	
    //	System.out.println("Remote Process Group Id :"+remoteProcessID);
    	
    	
    	String REMOTE_INPUT_PORT = getRemoteInputPortId(remoteProcessID);
    	System.out.println(REMOTE_INPUT_PORT);
    	
    	createRemoteConnection(rootRemoteGroupId,DateProcessId, REMOTE_INPUT_PORT,remoteProcessID);
    	
    	String putFileId = createPutFileProcessor(rootGroupId);
    	
    	createConnection(rootGroupId,inportId,putFileId);
    	
    	updatePutFile(putFileId);
    }

	private static void updatePutFile(String putFileId) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the directory path to store the data :");
		String path = sc.nextLine();
		
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8080/nifi-api/processors/"+putFileId;
		String content = "{\"component\":{\"id\":\""+putFileId+"\",\"name\":\"PutFile\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"0 sec\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"runDurationMillis\":0,\"autoTerminatedRelationships\":[\"failure\",\"success\"],\"properties\":{\"Directory\":\""+path+"\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);
	}

	private static void createConnection(String rootGroupId, String inportId, String putFileId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8080/nifi-api/process-groups/"+rootGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+inportId+"\",\"groupId\":\""+rootGroupId+"\",\"type\":\"INPUT_PORT\"},\"destination\":{\"id\":\""+putFileId+"\",\"groupId\":\""+rootGroupId+"\",\"type\":\"PROCESSOR\"},\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[]}}";
		String response = doPostRequest(url, content);
		System.out.println(response);
		
	}

	private static String createPutFileProcessor(String rootGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8080/nifi-api/process-groups/"+rootGroupId+"/processors";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.standard.PutFile\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-standard-nar\",\"version\":\"1.7.1\"},\"name\":\"PutFile\",\"position\":{\"x\":732,\"y\":91}}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}

	private static void createRemoteConnection(String rootRemoteGroupId, String DateProcessId,
			String REMOTE_INPUT_PORT,String remoteProcessID) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+DateProcessId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"destination\":{\"id\":\""+REMOTE_INPUT_PORT+"\",\"groupId\":\""+remoteProcessID+"\",\"type\":\"REMOTE_INPUT_PORT\"},\"selectedRelationships\":[\"success\"],\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[]}}";
		String response = doPostRequest(url, content);
		System.out.println(response);
	}

	private static String getRemoteInputPortId(String remoteProcessID) throws IOException {
		String url = "http://localhost:8085/nifi-api/remote-process-groups/"+remoteProcessID;
		String response= doGetRequest(url);
		System.out.println("Remote Input Id :"+response);
	//	System.out.println(new JsonParser().parse(response).getAsJsonObject().getAsJsonObject("component").getAsJsonObject("contents").getAsJsonArray("inputPorts").get(0).getAsJsonObject());
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonObject("component").getAsJsonObject("contents").getAsJsonArray("inputPorts").get(0).getAsJsonObject().get("id").getAsString();
	}

	private static String createInputPort(String rootGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8080/nifi-api/process-groups/"+rootGroupId+"/input-ports";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"in\",\"position\":{\"x\":32,\"y\":-11.5}}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}

	private static String getRootGroup() throws IOException {
		String url = "http://localhost:8080/nifi-api/flow/process-groups/root";
		String response = doGetRequest(url);
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonObject("processGroupFlow").get("id")
				.getAsString();

	}

	private static String createRemoteProcessGroup(String rootRemoteGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/remote-process-groups";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"targetUris\":\"http://127.0.0.1:8080/nifi/\",\"position\":{\"x\":286.64325882605476,\"y\":104.89375179378884},\"communicationsTimeout\":\"30 sec\",\"yieldDuration\":\"10 sec\",\"transportProtocol\":\"HTTP\",\"localNetworkInterface\":\"\",\"proxyHost\":\"\",\"proxyPort\":\"\",\"proxyUser\":\"\",\"proxyPassword\":\"\"}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}

//	private static String createflowFile(String rootRemoteGroupId) throws IOException {
//		UUID clientId = UUID.randomUUID();
//		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/processors";
//		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.standard.GenerateFlowFile\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-standard-nar\",\"version\":\"1.7.1\"},\"name\":\"GenerateFlowFile\",\"position\":{\"x\":89,\"y\":179.5}}}";
//		String response = doPostRequest(url, content);
//		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
//	}

	
	private static void createConcateDateConncection(String rootRemoteGroupId, String dateProcessId, String remoteProcessID) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+remoteProcessID+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"destination\":{\"id\":\""+dateProcessId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"selectedRelationships\":[\"success\"],\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[]}}";
		doPostRequest(url, content);
		
	}


	private static void updateDateProcessor(String dateProcessId) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the date format as you want e.g:yyyy-MM-dd :");
		String date = sc.nextLine();
		System.out.println("Date Process Id :"+dateProcessId);
		
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/processors/"+dateProcessId;
		String content = "{\"component\":{\"id\":\""+dateProcessId+"\",\"name\":\"ConvertDateProcessor\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"0 sec\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"autoTerminatedRelationships\":[\"FAILURE\",\"SUCCESS\"],\"properties\":{\"DATE_FIELD\":\"dob\",\"CONVERSION_FORMAT \":\""+date+"\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);
	}


	private static String createDateConvertor(String rootRemoteGroupId) throws IOException {
		
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/processors";
		String content="{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"com.quest.fdn.ConvertDateProcessors.ConvertDateProcessor\",\"bundle\":{\"group\":\"com.quest.fdn.ConvertDateProcessors\",\"artifact\":\"nifi-sample-nar\",\"version\":\"1.0\"},\"name\":\"ConvertDateProcessor\",\"position\":{\"x\":771.3678004940541,\"y\":458.196071815501}}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}


	private static void createAvroJsonConcateConnection(String rootRemoteGroupId, String avroJsonId,
			String DateProcessId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+avroJsonId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"destination\":{\"id\":\""+DateProcessId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"selectedRelationships\":[\"failure\",\"success\"],\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[]}}";
		doPostRequest(url, content);
	//	updateDateProcessor(DateProcessId);
	}

	private static void updateSQLQueryProcessor(String executeSQLId) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the query to fetch data :");
		String query = sc.nextLine();
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/processors/"+executeSQLId;
		String content = "{\"component\":{\"id\":\""+executeSQLId+"\",\"name\":\"ExecuteSQL\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"1 day\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"autoTerminatedRelationships\":[],\"properties\":{\"SQL select query\":\""+query+"\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":2},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);
	}


	private static void createSQLAvroJsonConnection(String rootRemoteGroupId, String executeSQLId, String avroJsonId) throws IOException {
		UUID clientId = UUID.randomUUID();
		System.out.println("SQL ID :"+executeSQLId);
		System.out.println("AvroToJson Id :"+avroJsonId);
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/connections";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"name\":\"\",\"source\":{\"id\":\""+executeSQLId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"destination\":{\"id\":\""+avroJsonId+"\",\"groupId\":\""+rootRemoteGroupId+"\",\"type\":\"PROCESSOR\"},\"selectedRelationships\":[\"failure\",\"success\"],\"flowFileExpiration\":\"0 sec\",\"backPressureDataSizeThreshold\":\"1 GB\",\"backPressureObjectThreshold\":\"10000\",\"bends\":[],\"prioritizers\":[]}}";
		doPostRequest(url, content);
	}

	private static String createAvroJsonProcessor(String rootRemoteGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/processors";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.avro.ConvertAvroToJSON\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-avro-nar\",\"version\":\"1.8.0\"},\"name\":\"ConvertAvroToJSON\",\"position\":{\"x\":474.59488907609966,\"y\":192.6803827997711}}}";
		String response = doPostRequest(url, content);
		System.out.println("create avro :"+response);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}
	private static void updateSQLProcessor(String executeSQLId,String controllerServiceId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/processors/"+executeSQLId;
		String content = "{\"component\":{\"id\":\""+executeSQLId+"\",\"name\":\"ExecuteSQL\",\"config\":{\"concurrentlySchedulableTaskCount\":\"1\",\"schedulingPeriod\":\"1 day\",\"executionNode\":\"ALL\",\"penaltyDuration\":\"30 sec\",\"yieldDuration\":\"1 sec\",\"bulletinLevel\":\"WARN\",\"schedulingStrategy\":\"TIMER_DRIVEN\",\"comments\":\"\",\"autoTerminatedRelationships\":[],\"properties\":{\"Database Connection Pooling Service\":\""+controllerServiceId+"\"}},\"state\":\"STOPPED\"},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1},\"disconnectedNodeAcknowledged\":false}";
		doPutRequest(url, content);
		updateControllerService(controllerServiceId);
	}
	private static void updateControllerService(String controllerServiceId) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the db name :");
		String db = sc.nextLine();
		
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/controller-services/"+controllerServiceId;
		//String content = "{\"disconnectedNodeAcknowledged\":false,\"component\":{\"id\":\""+controllerServiceId+"\",\"name\":\"DBCPConnectionPool\",\"comments\":\"\",\"properties\":{\"Database Connection URL\":\"jdbc:mysql://localhost:3306/"+db+"\",\"Database Driver Class Name\":\"com.mysql.jdbc.Driver\",\"database-driver-locations\":\"/Users/arka.dey/Downloads/jar_files/mysql-connector-java-8.0.13.jar\"}},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1}}";
		//String content = "{\"disconnectedNodeAcknowledged\":false,\"component\":{\"id\":\""+controllerServiceId+"\",\"name\":\"DBCPConnectionPool\",\"comments\":\"\",\"properties\":{\"Database Connection URL\":\" jdbc:mysql://localhost:3306/"+db+"\",\"Database Driver Class Name\":\"com.mysql.jdbc.Driver\",\"database-driver-locations\":\"/Users/arka.dey/Downloads/jar_files/mysql-connector-java-8.0.13.jar\",\"Database User\":\"root\",\"Password\":\"root\"}},\"revision\":{\"clientId\":\""+clientId+"\",\"version\":1}}";
		String content = "{\"disconnectedNodeAcknowledged\":false,\"component\":{\"id\":\"" + controllerServiceId
				+ "\",\"name\":\"DBCPConnectionPool\",\"comments\":\"\",\"properties\":{\"Database Connection URL\":\"jdbc:mysql://localhost:3306/"
				+ db + "\",\"Database Driver Class Name\":\"com.mysql.jdbc.Driver\",\"database-driver-locations\":\"/Users/ravi.kumar/Downloads/jar_files/mysql-connector-java-8.0.13.jar\",\"Database User\":\"root\",\"Password\":\"root\"}},\"revision\":{\"clientId\":\""
				+ clientId + "\",\"version\":1}}";
		doPutRequest(url, content);
		
		enableController(controllerServiceId);
	}
	private static void enableController(String controllerServiceId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/controller-services/" + controllerServiceId;
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":2},\"disconnectedNodeAcknowledged\":false,\"component\":{\"id\":\""+controllerServiceId+"\",\"state\":\"ENABLED\"}}";
		doPutRequest(url, content);
		
	}
	private static String createControllerService(String rootGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootGroupId+"/controller-services";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.dbcp.DBCPConnectionPool\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-dbcp-service-nar\",\"version\":\"1.8.0\"},\"name\":\"DBCPConnectionPool\"}}";
		String response = doPostRequest(url, content);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
		
	}
	private static String createSQLProcessor(String rootRemoteGroupId) throws IOException {
		UUID clientId = UUID.randomUUID();
		String url = "http://127.0.0.1:8085/nifi-api/process-groups/"+rootRemoteGroupId+"/processors";
		//String content="{\"revision\":{\"clientId\":\"+clientId+\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.standard.ExecuteSQL\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-standard-nar\",\"version\":\"1.8.0\"},\"name\":\"ExecuteSQL\",\"position\":{\"x\":729.5867864947159,\"y\":412.83382804479106}}}";
		String content = "{\"revision\":{\"clientId\":\""+clientId+"\",\"version\":0},\"disconnectedNodeAcknowledged\":false,\"component\":{\"type\":\"org.apache.nifi.processors.standard.ExecuteSQL\",\"bundle\":{\"group\":\"org.apache.nifi\",\"artifact\":\"nifi-standard-nar\",\"version\":\"1.8.0\"},\"name\":\"ExecuteSQL\",\"position\":{\"x\":729.5867864947159,\"y\":412.83382804479106}}}";
		String response = doPostRequest(url, content);
		System.out.println("Response---"+response);
		return new JsonParser().parse(response).getAsJsonObject().get("id").getAsString();
	}
	



	private static String getRootGroupID() throws IOException {
		String url = "http://localhost:8085/nifi-api/flow/process-groups/root";
		String response = doGetRequest(url);
		return new JsonParser().parse(response).getAsJsonObject().getAsJsonObject("processGroupFlow").get("id")
				.getAsString();
	}

	private static String doPostRequest(String url, String content) throws IOException {
		RequestBody body = RequestBody.create(JSON, content);
		Request request = new Request.Builder().url(url).post(body).build();
		System.out.println(request);
		Response response = client.newCall(request).execute();
		System.out.println(response + " 2");
		return response.body().string();
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
	

}
