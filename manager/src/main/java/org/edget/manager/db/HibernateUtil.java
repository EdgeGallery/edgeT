package org.edget.manager.db;

import java.util.Properties;

import org.edget.manager.model.Execution;
import org.edget.manager.model.TestCase;
import org.edget.manager.model.Tester;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	   private static SessionFactory sessionFactory;
	    public static SessionFactory getSessionFactory() {
	        if (sessionFactory == null) {
	            try {
	                Configuration configuration = new Configuration();

	                // Hibernate settings equivalent to hibernate.cfg.xml's properties
	                Properties settings = new Properties();
	                settings.put(Environment.DRIVER, "org.postgresql.Driver");
	                
					
					  settings.put(Environment.URL, "jdbc:postgresql://edget-db:5432/edgeT");
					  settings.put(Environment.USER, "postgres"); settings.put(Environment.PASS,
					  "123");
					 
					
					 
					   
					
					  
						
						/*
						 * settings.put(Environment.URL,"jdbc:postgresql://127.0.0.1:5432/postgres");
						 * settings.put(Environment.USER, "postgres");
						 * settings.put(Environment.PASS,"root");
						 */
						 
					
	                
	                
	                
	                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

	                settings.put(Environment.SHOW_SQL, "true");

	                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

	                settings.put(Environment.HBM2DDL_AUTO, "none");

	                configuration.setProperties(settings);

	               configuration.addAnnotatedClass(Tester.class)
	               				.addAnnotatedClass(TestCase.class)
	               				.addAnnotatedClass(Execution.class);

	                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	                    .applySettings(configuration.getProperties()).build();

	                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return sessionFactory;
	    }
	}


