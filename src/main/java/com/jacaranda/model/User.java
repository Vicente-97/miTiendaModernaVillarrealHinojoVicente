package com.jacaranda.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	@Id
	private String username;
	private String password;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="date_of_birth")
//	private LocalDateTime dateOfBirth;
	private LocalDate dateOfBirth;
	private String genre; 
	private boolean admin;
	@OneToMany(mappedBy= "user", cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Purchase> purchases = new ArrayList<>();

	public User() {
		
	}
	
	/**
	 * Constructor de la clase User con todos los parámetros menos si será admin o no, porque por defecto
	 * no lo será
	 * @param username nombre de usuario del usuario
	 * @param password contraseña ya encriptada
	 * @param firstName nombre del usuario
	 * @param lastName apellido/s del usuario
	 * @param dateOfBirth fecha de nacimiento
	 * @param genre género
	 * @throws UserException lanza esta exception propagada de los setters si alguno de los parámetros no es correcto
	 */
	public User(String username, String password, String firstName, String lastName, LocalDate dateOfBirth,
			String genre) throws UserException {
		this.setUsername(username);
		this.setPassword(password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDateOfBirth(dateOfBirth);
		this.setGenre(genre);
		this.admin = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws UserException {
		if(username == null || username.isEmpty()) {
			throw new UserException("El nombre de usuario no es válido");
		}else {
			this.username = username;
		}
	}

	public String getPassword() { 
		return password;
	}
	
	/**
	 * Función que establece la contraseña del usuario comprobando que no esté vacía o sea nula
	 * @param password contraseña ya codificada
	 * @throws UserException lanza la exception si la contraseña está vacía o es nula
	 */
	public void setPassword(String password) throws UserException { 
		if(password == null || password.isEmpty()) {
			throw new UserException("La contraseña no es válida");
		}else {
			this.password = password;
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws UserException {
		if(firstName == null || firstName.isEmpty()) {
			throw new UserException("El nombre no es válido");
		}else {
			this.firstName = firstName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws UserException {
		if(lastName == null || lastName.isEmpty()) {
			throw new UserException("El apellido/s no son válidos");
		}else {
			this.lastName = lastName;
		}
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) throws UserException {
//		LocalDateTime MIN = LocalDateTime.parse("1920-01-01T00:00:00.00");
//		LocalDateTime MAX = LocalDateTime.parse("2010-01-01T00:00:00.00");
		LocalDate MIN = LocalDate.parse("1920-01-01");
		LocalDate MAX = LocalDate.of((LocalDate.now().getYear()-18), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
		if(dateOfBirth == null) {
			throw new UserException("La fecha introducida no es válida");
		}else {
			if(dateOfBirth.isBefore(MIN) || dateOfBirth.isAfter(MAX)) {
				throw new UserException("La fecha introducida no puede ser menor que " + MIN + " ni mayor que " + MAX);
			}else {
				this.dateOfBirth = dateOfBirth;
			}
		}
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) throws UserException {
		if(genre.isEmpty() || genre == null) {
			throw new UserException("El género no es correcto");
		}else {
			this.genre = genre;
		}
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", dateOfBirth=" + dateOfBirth + ", genre=" + genre + ", admin=" + admin + "]";
	}
	

}
