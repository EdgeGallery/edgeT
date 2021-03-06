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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.edget.manager.model.TestCaseResult;
import org.edget.manager.db.SqlQuery;

@Path("/testcase")
public class Testcase {

@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGet(@QueryParam("scenario") String scenario, @QueryParam("testsuite") String testsuite, @QueryParam("testcase") String testcase) {
	String sql;
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();

	if(null == scenario || null == testsuite || null == testcase) {
        throw new IllegalArgumentException("{\"error\":\"At least one parameter is invalid or not supplied\"}");
    }

    sql = "SELECT * FROM testcase WHERE scenario='" + scenario + "' AND testsuite='" + testsuite  + "' AND testcase='" + testcase + "';";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
        while ( rs.next() ) {
    	   JSONObject obj=new JSONObject();
    	   obj.put("id",rs.getInt("id"));
    	   obj.put("testerId", rs.getInt("tester_id"));
    	   obj.put("scenario", rs.getString("scenario"));
    	   obj.put("testsuite", rs.getString("testsuite"));
    	   obj.put("testcase", rs.getString("testcase"));
    	   jsonarray.put(obj);
    	    }
    	} catch ( Exception e ) {
	       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	       System.exit(0);
	    }

    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 

    Gson gson = builder.create();
    TestCaseResult[] testcaseResult = gson.fromJson(jsonarray.toString(), TestCaseResult[].class);

    return Response.ok(testcaseResult, MediaType.APPLICATION_JSON).build();
	}
}
