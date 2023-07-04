package com.aremu.rest.webservices.restfullwebservices.client;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {

	protected Client() {

	}

	private @Id @GeneratedValue int id;
	private String firstName;
	private String lastName;
	private String role;

	Client(String firstName, String lastName, String role) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public String getRole() {
		return this.role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setName(String name) {
		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Client))
			return false;
		Client client = (Client) o;
		return Objects.equals(this.id, client.id) && Objects.equals(this.firstName, client.firstName)
				&& Objects.equals(this.lastName, client.lastName) && Objects.equals(this.role, client.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.firstName, this.lastName, this.role);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role + "]";
	}

}
