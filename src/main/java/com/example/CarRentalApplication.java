package com.example;

import com.example.config.CarRentalConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CarRentalApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}
}
//		EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//		try{
//			entityManager.getTransaction().begin();
//
//			entityManager.getTransaction().commit();
//		}catch(Exception exception){
//			System.out.println("Something went wrong...");
//			entityManager.getTransaction().rollback();
//		}finally{
//			entityManager.close();
//		}

