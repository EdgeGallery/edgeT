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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.edget.manager.db.HibernateUtil;
import org.edget.manager.db.SqlQuery;
import org.edget.manager.model.Execution;
import org.edget.manager.model.ExecutionsResult;
import org.edget.manager.model.TestCase;
import org.edget.manager.model.TsResult;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/executions")
public class Executions {

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handlePost(@Context HttpServletRequest request) throws IOException {

	Gson g = new Gson();
	String sql = "";
	List<ExecutionsResult> exeutionResultList = new ArrayList<>();
	InputStream input = request.getInputStream();
    String reqParam = IOUtils.toString(input);

    JsonParser parser = new JsonParser();
    JsonObject o = parser.parse(reqParam).getAsJsonObject();
    
    String str = g.toJson(o);
    
    JSONObject json = new JSONObject(reqParam);


    String execution_id = json.getString("execution_id");
    int tester_id = json.getInt("tester_id");
    int testcase_id = json.getInt("testcase_id");

    SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
	Session session = sessionfatory.openSession();
	Criteria cr = session.createCriteria(TestCase.class);
	cr.add(Restrictions.eq("id", testcase_id));
	List<TestCase> testCases = cr.list();
	if(!testCases.isEmpty()) {
		Execution execution = new Execution();
		execution.setTestCase(testCases.get(0));
		execution.setTester(testCases.get(0).getTester());
		execution.setExecutionId(execution_id);
		session.beginTransaction();
		session.saveOrUpdate(execution);
		session.getTransaction().commit();
		
		Criteria cr1 = session.createCriteria(Execution.class);
		
		cr1.add(Restrictions.eq("executionId", execution_id));
		List<Execution> executions = cr1.list();
		
		for(Execution p : executions) {
			ExecutionsResult eachExecution = new ExecutionsResult();
			eachExecution.setExecution_id(p.getExecutionId());
			eachExecution.setId(p.getId());
			eachExecution.setTestcase_id(p.getTestCase().getId());
			eachExecution.setTester_id(p.getTester().getId());
			exeutionResultList.add(eachExecution);
		}
		return Response.ok(exeutionResultList, MediaType.APPLICATION_JSON).build();
	}
	/*
	 * sql =
	 * "INSERT INTO execution (execution_id, tester_id, testcase_id) VALUES( '" +
	 * execution_id + "','" + tester_id + "','" + testcase_id + "');";
	 * SqlQuery.Insert(sql);
	 * 
	 * sql = "SELECT * FROM execution WHERE execution_id='" + execution_id + "';";
	 * ResultSet rs = SqlQuery.Select(sql); ExecutionsResult[] executionResult =
	 * parseExecutionRs(rs);
	 */
	return Response.ok(exeutionResultList, MediaType.APPLICATION_JSON).build();
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response handleGet(@QueryParam("execution_id") String execution_id, @QueryParam("tester_id") String tester_id,
                          @QueryParam("index") String index, @QueryParam("count") String count) {
	Logger logger = LoggerFactory.getLogger("SampleLogger");
	logger.info("from LOGGER");
	/*String sql;
	Connection c = null;
    Statement stmt = null;
    JSONArray jsonarray = new JSONArray();

    String append_query = "", pagination_query = "";

    if(null != tester_id && null != execution_id) {
       append_query = " WHERE tester_id='" + tester_id + "' AND execution_id='" + execution_id + "'";
    }
    else if(null != tester_id) {
       append_query = " WHERE tester_id='" + tester_id + "'";
    }
    else if(null != execution_id) {
       append_query = " WHERE execution_id='" + execution_id + "'";
    }

    if(null != index) {
       pagination_query += " OFFSET " + index;
    }
    if(null != count) {
       pagination_query += " LIMIT " + count ;
    }*/
    List<ExecutionsResult> results = new ArrayList<ExecutionsResult>();

    SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
	Session session = sessionfatory.openSession();
	@SuppressWarnings("deprecation")
	Criteria cr = session.createCriteria(Execution.class);
	Criterion criteriaTesterId;
	Criterion criteriaExecutionId;
	if (null != tester_id && null != execution_id) {
		criteriaTesterId = Restrictions.eq("tester.id", Integer.valueOf(tester_id));
		criteriaExecutionId = Restrictions.ilike("executionId", execution_id, MatchMode.EXACT);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(criteriaTesterId);
		conjunction.add(criteriaExecutionId);
		cr.add(conjunction);
		
	} else if (null != tester_id) {
		criteriaTesterId = Restrictions.eq("tester.id", Integer.valueOf(tester_id));
		cr.add(criteriaTesterId);
	} else if (null != execution_id) {
		criteriaExecutionId = Restrictions.ilike("executionId", execution_id, MatchMode.EXACT);
		cr.add(criteriaExecutionId);
	}
	if (null != index) {
		cr.setFirstResult(Integer.valueOf(index));
	}
	if (null != count) {
		cr.setMaxResults(Integer.valueOf(count));
	}
	List<Execution> executions = cr.list();
	if(!executions.isEmpty()) {
		 for(Execution execution : executions) {
		    	ExecutionsResult result = new ExecutionsResult();
		    	result.setExecution_id(execution.getExecutionId());
		    	result.setId(execution.getId());
		    	result.setTestcase_id(execution.getTestCase().getId());
		    	result.setTester_id(execution.getTester().getId());
		    	results.add(result);
		    }

	}
   
	/*
	 * sql = "SELECT * FROM execution " + append_query + pagination_query + ";";
	 * logger.info("from LOGGER"); ResultSet rs = SqlQuery.Select(sql);
	 * ExecutionsResult[] executionResult = parseExecutionRs(rs);
	 */
	return Response.ok(results, MediaType.APPLICATION_JSON).build();
}

public ExecutionsResult[] parseExecutionRs(ResultSet rs) {
   	JSONArray jsonarray = new JSONArray();
   	try {
    while (rs.next()) {
        JSONObject obj=new JSONObject();
        obj.put("id",rs.getInt("id"));
        obj.put("tester_id",rs.getInt("tester_id"));
        obj.put("execution_id",rs.getString("execution_id"));
        obj.put("testcase_id",rs.getInt("testcase_id"));
        jsonarray.put(obj);
        }
    } catch ( Exception e ) {
       System.out.println( e.getClass().getName()+": "+ e.getMessage());
       System.exit(0);
	}

    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    ExecutionsResult[] executionResult = gson.fromJson(jsonarray.toString(), ExecutionsResult[].class);
    return executionResult;
}

}
