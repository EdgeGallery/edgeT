/*
* Copyright 2020 Huawei Technologies Co., Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*     http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.edget.manager.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.edget.manager.model.TesterResult;
import org.edget.manager.db.SqlQuery;

@Path("/tester")
public class Tester {
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handlePost(@Context HttpServletRequest request) throws IOException {

	Gson g = new Gson();
	String sql = "";
    int tester_id = 0;
    
	InputStream input = request.getInputStream();
    String reqParam = IOUtils.toString(input);
    System.out.println(reqParam);
    
    JsonParser parser = new JsonParser();
    JsonObject o = parser.parse(reqParam).getAsJsonObject();
    
    String str = g.toJson(o);
    
    JSONObject json = new JSONObject(reqParam);

    String tester_ip = json.getString("ip");
    String tester_port = json.getString("port");

    sql = "INSERT INTO tester (ip, port) VALUES( '" + tester_ip + "'," +  tester_port + ");";
    System.out.println(sql);
    SqlQuery.Insert(sql);

    sql = "SELECT id FROM tester WHERE ip='" + tester_ip + "' AND port='" + tester_port + "';";
    System.out.println(sql);
    ResultSet rs = SqlQuery.Select(sql);
   	try {
    while ( rs.next() ) {
       tester_id = rs.getInt("id");
    }
	} catch ( Exception e ) {
	       System.out.println(e.getClass().getName()+": "+ e.getMessage());
	       System.exit(0);
	 }

    JSONArray a = (JSONArray) json.getJSONArray("test_case_list");

    String scenario,testsuite,testcase;
    for(int i = 0; i < a.length(); i++) 
    {
    	JSONObject list_entry = a.getJSONObject(i);
    	scenario = list_entry.getString("scenario");
    	testsuite = list_entry.getString("testsuite");
    	testcase = list_entry.getString("testcase");
        sql = "INSERT INTO testcase (TESTER_ID,SCENARIO,TESTSUITE,TESTCASE) VALUES( '" + tester_id + "','" + scenario + "','" + testsuite + "','" + testcase  + "');";
        System.out.println(sql);
        SqlQuery.Insert(sql);
    }

    return Response.ok(reqParam,MediaType.APPLICATION_JSON).build();
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGet() {
	String sql;
    JSONArray jsonarray = new JSONArray();

    sql = "SELECT * FROM tester;";
    ResultSet rs = SqlQuery.Select(sql);

    TesterResult[] testerResult = parseTesternRs(rs);
    return Response.ok(testerResult, MediaType.APPLICATION_JSON).build();
}

public TesterResult[] parseTesternRs(ResultSet rs) {
   	JSONArray jsonarray = new JSONArray();
   	  	try {
    while ( rs.next() ) {
    	   JSONObject obj=new JSONObject();
    	   obj.put("id",rs.getInt("id"));
    	   obj.put("ip", rs.getString("ip"));
    	   obj.put("port", rs.getInt("port"));
    	   jsonarray.put(obj);
    	   }
	} catch ( Exception e ) {
	       System.out.println(e.getClass().getName()+": "+ e.getMessage());
	       System.exit(0);
	 }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    TesterResult[] testerResult = gson.fromJson(jsonarray.toString(), TesterResult[].class);
    return testerResult;
}

@GET
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response getTesterById(@PathParam("id") String tester_id) {
	String sql;
	JSONObject obj=new JSONObject();

	sql = "SELECT * FROM tester WHERE id=" + tester_id + ";";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
	   while ( rs.next() ) {
    	   obj.put("id",rs.getInt("id"));
    	   obj.put("ip", rs.getString("ip"));
    	   obj.put("port", rs.getInt("port"));
    	   }
    } catch ( Exception e ) {
	       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	       System.exit(0);
	}

    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 

    Gson gson = builder.create();
    TesterResult testerResult = gson.fromJson(obj.toString(), TesterResult.class); 
    
    return Response.ok(testerResult, MediaType.APPLICATION_JSON).build();
}

@DELETE
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response delTesterById(@PathParam("id") String tester_id) {
	String sql;
    sql = "DELETE FROM tester WHERE id=" + tester_id + ";";
    SqlQuery.Insert(sql);
    sql = "DELETE FROM testcase WHERE id=" + tester_id + ";";
    SqlQuery.Insert(sql);
	return Response.ok(MediaType.APPLICATION_JSON).build();
}

}
