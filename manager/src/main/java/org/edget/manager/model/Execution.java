package org.edget.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "Execution")
@Table(name = "EXECUTION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Execution {
	
	


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne
	private Tester tester;
	
	@ManyToOne
	private TestCase testCase;


	@Column(name = "EXECUTION_ID")
	private String executionId;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getExecutionId() {
		return executionId;
	}


	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public Tester getTester() {
		return tester;
	}


	public void setTester(Tester tester) {
		this.tester = tester;
	}


	public TestCase getTestCase() {
		return testCase;
	}


	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
	

}
