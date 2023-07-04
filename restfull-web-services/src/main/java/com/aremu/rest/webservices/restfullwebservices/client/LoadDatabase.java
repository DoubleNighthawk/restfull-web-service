package com.aremu.rest.webservices.restfullwebservices.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase{

	
	private static Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ClientRepository clientRepository, OrderRepository orderRepository) {

		return args -> {
			clientRepository.save(new Client("Aremu", "Adebola", "new client"));
			clientRepository.save(new Client("Tayo", "Ajasa", "old client"));

			clientRepository.findAll().forEach(client -> log.info("Preloaded " + client));

			orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
			orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

			orderRepository.findAll().forEach(order -> {
				log.info("Preloaded " + order);
			});

		};
	}
}
