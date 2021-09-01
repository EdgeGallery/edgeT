package org.edget.manager.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "TestCase")
@Table(name = "TESTCASE")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TestCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	
	@ManyToOne
	private Tester tester;


	@Column(name = "SCENARIO")
	private String scenario;

	@Column(name = "TESTSUITE")
	private String testSuite;

	@Column(name = "TESTCASE")
	private String testCase;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="TESTCASE_ID")
	private Set<Execution> executions;
	
	public Tester getTester() {
		return tester;
	}

	public void setTester(Tester tester) {
		this.tester = tester;
	}
	 
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

}
