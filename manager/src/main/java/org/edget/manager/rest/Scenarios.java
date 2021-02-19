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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.edget.manager.model.TesterIdResult;
import org.edget.manager.model.SuiteResult;
import org.edget.manager.model.TsResult;
import org.edget.manager.model.ScenariosResult;
import org.edget.manager.db.SqlQuery;

@Path("/scenarios")
public class Scenarios {

@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGet() {
	String sql;
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();

	sql = "SELECT DISTINCT scenario FROM testcase;";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
    	   while ( rs.next() ) {
    	   JSONObject obj=new JSONObject();
    	   obj.put("name",rs.getString("scenario"));
    	   obj.put("description", "");
    	   jsonarray.put(obj);
    	   }
    	} catch ( Exception e ) {
	       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	       System.exit(0);
	    }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    ScenariosResult[] scenariosResult = gson.fromJson(jsonarray.toString(), ScenariosResult[].class);

    return Response.ok(scenariosResult, MediaType.APPLICATION_JSON).build();
}

@GET
@Path("{scenario_name}"+"/testsuites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGetTestsuites(@PathParam("scenario_name") String scenario_name) {
	String sql;
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();

    sql = "SELECT DISTINCT testsuite FROM testcase WHERE scenario='" + scenario_name + "';";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
       while ( rs.next() ) {
       JSONObject obj=new JSONObject();
       obj.put("name",rs.getString("testsuite"));
       obj.put("description","");
       jsonarray.put(obj);
       }
    } catch ( Exception e ) {
       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
       System.exit(0);
    }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    SuiteResult[] testcaseResult = gson.fromJson(jsonarray.toString(), SuiteResult[].class);

    return Response.ok(testcaseResult, MediaType.APPLICATION_JSON).build();
	}

@GET
@Path("{scenario_name}"+"/testcases")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGet(@PathParam("scenario_name") String scenario_name, @QueryParam("testSuiteName") String testSuiteName) {

	String sql, append_query= "";
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();

    if(null != testSuiteName) {
       append_query = "' AND testsuite='" + testSuiteName;
    }

	sql = "SELECT * FROM testcase WHERE scenario='" + scenario_name + append_query + "';";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
           while ( rs.next() ) {
    	   JSONObject obj=new JSONObject();
    	   obj.put("testCaseName",rs.getString("testcase"));
    	   obj.put("testSuiteName", rs.getString("testsuite"));
    	   jsonarray.put(obj);
    	   }
    } catch ( Exception e ) {
       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
       System.exit(0);
    }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    TsResult[] testcaseResult = gson.fromJson(jsonarray.toString(), TsResult[].class);

    return Response.ok(testcaseResult, MediaType.APPLICATION_JSON).build();

	}

@GET
@Path("{scenario_name}"+"/testsuites/"+"{testsuite_name}"+"/testcases/"+"{testcase_name}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGetTestcase(@PathParam("scenario_name") String scenario_name,
@PathParam("testsuite_name") String testsuite_name, @PathParam("testcase_name") String testcase_name ) {

	String sql;
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();


    sql = "SELECT tester_id FROM testcase WHERE scenario='" + scenario_name + "' AND testsuite='" + testsuite_name + "' AND testcase='" + testcase_name + "';";
    ResultSet rs = SqlQuery.Select(sql);

   	try {
    while ( rs.next() ) {
    	   JSONObject obj=new JSONObject();
    	   obj.put("testerId",rs.getInt("tester_id"));
    	   jsonarray.put(obj);
        }
    } catch ( Exception e ) {
       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
       System.exit(0);
    }

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    TesterIdResult[] testcaseResult = gson.fromJson(jsonarray.toString(), TesterIdResult[].class);

    return Response.ok(testcaseResult, MediaType.APPLICATION_JSON).build();

	}
}
