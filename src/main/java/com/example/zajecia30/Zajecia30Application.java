package com.example.zajecia30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class Zajecia30Application {

	public static void main(String[] args) {
		SpringApplication.run(Zajecia30Application.class, args);

	}

//	@Scheduled(fixedDelay = 2000)
//	public void hello(){
//		System.out.println("Hello");
//	}

//	TRANSACTIONAL
//  1)Jesli @ToString.Exclude to w Dto musi byc jako String a nie Set<Visit>? SPRAWDZIC
//	-> jesli nie mam w stringu to sie robi jakby zapetlenie!
//	2)czy wtedy musze wszystkie nadpisywac w repository z @Lock? Jesli nadpisze to w serwisie musi byc transactional!


//	TESTY
//	1)Zeby z testow nie dodawalo nowych obiektow to powinnismy Transactional czy inna baza?
//	do sprawdzenia @DirtiesContext

//	HATEOAS
//	1) gdzie sie daje representationModel?


}
