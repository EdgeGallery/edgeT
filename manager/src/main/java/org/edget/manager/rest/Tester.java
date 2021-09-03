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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.edget.manager.db.HibernateUtil;
import org.edget.manager.model.TestCase;
import org.edget.manager.model.TesterResult;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vladmihalcea.hibernate.type.basic.Inet;

@Path("/tester")
public class Tester {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handlePost(@Context HttpServletRequest request) throws IOException {

		Gson g = new Gson();
		String sql = "";
		int tester_id = 0;
		Set<TestCase> testCases = new HashSet<>();
		InputStream input = request.getInputStream();
		String reqParam = IOUtils.toString(input);
		System.out.println(reqParam);

		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(reqParam).getAsJsonObject();

		String str = g.toJson(o);

		JSONObject json = new JSONObject(reqParam);

		String tester_ip = json.getString("ip");
		String tester_port = json.getString("port");
		JSONArray a = (JSONArray) json.getJSONArray("test_case_list");
		for (int i = 0; i < a.length(); i++) {

			JSONObject list_entry = a.getJSONObject(i);
			TestCase testCase = new TestCase();
			testCase.setScenario(list_entry.getString("scenario"));
			testCase.setTestSuite(list_entry.getString("testsuite"));
			testCase.setTestCase(list_entry.getString("testcase"));
			testCases.add(testCase);
		}
		SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
		Session session = sessionfatory.openSession();
		session.beginTransaction();
		org.edget.manager.model.Tester tester = new org.edget.manager.model.Tester();
		Inet inet = new Inet(tester_ip);
		tester.setIp(inet);
		tester.setPort(Integer.valueOf(tester_port));
		tester.setTestCases(testCases);
		session.save(tester);
		session.getTransaction().commit();
		return Response.ok(reqParam, MediaType.APPLICATION_JSON).build();

	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleGet() {
		String sql;
		JSONArray jsonarray = new JSONArray();

		List<TesterResult> testerResult = new ArrayList<>();
		SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
		Session session = sessionfatory.openSession();
		@SuppressWarnings("deprecation")
		Criteria cr = session.createCriteria(org.edget.manager.model.Tester.class);
		List<org.edget.manager.model.Tester> listTester = cr.list();
		if (!listTester.isEmpty()) {
			for (org.edget.manager.model.Tester k : listTester) {
				TesterResult result = new TesterResult();
				result.setId(k.getId());
				result.setName(k.getIp().getAddress());
				result.setPort(k.getPort());
				testerResult.add(result);
			}

		}

		return Response.ok(testerResult, MediaType.APPLICATION_JSON).build();

	}

	public TesterResult[] parseTesternRs(ResultSet rs) {
		JSONArray jsonarray = new JSONArray();
		try {
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getInt("id"));
				obj.put("ip", rs.getString("ip"));
				obj.put("port", rs.getInt("port"));
				jsonarray.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
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

		SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
		Session session = sessionfatory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<org.edget.manager.model.Tester> query = builder.createQuery(org.edget.manager.model.Tester.class);
		Root<org.edget.manager.model.Tester> root = query.from(org.edget.manager.model.Tester.class);
		query.select(root).where(builder.equal(root.get("id"), Integer.valueOf(tester_id)));
		Query<org.edget.manager.model.Tester> q = session.createQuery(query);
		List<org.edget.manager.model.Tester> listTester = q.getResultList();

		TesterResult result = new TesterResult();
		if (!listTester.isEmpty()) {
			org.edget.manager.model.Tester tester = listTester.get(0);
			result.setId(tester.getId());
			result.setName(tester.getIp().getAddress());
			result.setPort(tester.getPort());
		}

		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delTesterById(@PathParam("id") String tester_id) {

		SessionFactory sessionfatory = HibernateUtil.getSessionFactory();
		Session session = sessionfatory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		org.edget.manager.model.Tester b = em.find(org.edget.manager.model.Tester.class, Integer.valueOf(tester_id));
		if (b != null) {

			em.getTransaction().begin();
			em.remove(b);
			em.getTransaction().commit();

		}

		return Response.ok(MediaType.APPLICATION_JSON).build();

	}

}
