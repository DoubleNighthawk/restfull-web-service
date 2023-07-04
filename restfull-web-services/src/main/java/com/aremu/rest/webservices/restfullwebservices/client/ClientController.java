package com.aremu.rest.webservices.restfullwebservices.client;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
	
	private ClientRepository repository ;
	
	private ClientModelAssembler assembler;

	ClientController(ClientRepository repository, ClientModelAssembler assembler) {

	    this.repository = repository;
	    this.assembler = assembler;
	  }



	  // Aggregate root
	  // tag::get-aggregate-root[]
	  @GetMapping("/clients")
	  //List<Client> all() {
	    //return repository.findAll();
	  
	  CollectionModel<EntityModel<Client>> all() {

		  List<EntityModel<Client>> employees = repository.findAll().stream() //
		      .map(assembler::toModel) //
		      .collect(Collectors.toList());

		  return CollectionModel.of(employees, linkTo(methodOn(ClientController.class).all()).withSelfRel());
		}
	  
	  // end::get-aggregate-root[]

	  @PostMapping("/clients")
	  ResponseEntity<?> newClient(@RequestBody Client newClient) {
	  
	  EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

	  return ResponseEntity //
	      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	      .body(entityModel);
	}

	  // Single item
	  
	  @GetMapping("/clients/{id}")
	  
	  public  EntityModel<Client> one(@PathVariable int id) {

		  Client client = repository.findById(id) //
		      .orElseThrow(() -> new ClientNotFoundException(id));
		  
		  return assembler.toModel(client);

		  //return EntityModel.of(client, //
				  //linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
		      //linkTo(methodOn(ClientController.class).all()).withRel("clients"));
	  }

	  @PutMapping("/clients/{id}")
	  ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable int id) {

		  Client updatedClient = repository.findById(id) //
		      .map(client -> {
		    	  client.setName(newClient.getName());
		    	  client.setRole(newClient.getRole());
		        return repository.save(client);
		      }) //
		      .orElseGet(() -> {
		        newClient.setId(id);
		        return repository.save(newClient);
		      });

		  EntityModel<Client> entityModel = assembler.toModel(updatedClient);

		  return ResponseEntity //
		      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
		      .body(entityModel);
	  }

	  @DeleteMapping("/clients/{id}")
	  ResponseEntity<?> deleteClient(@PathVariable Integer id) {

	    repository.deleteById(id);

	    return ResponseEntity.noContent().build();
	  }
}
