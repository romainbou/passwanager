package org.isen.jee.project;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String email;

	private String firstname;

	private String lastname;

	private String password;

	@Column(name="public_key")
	private Object publicKey;

	private String username;

	//bi-directional many-to-one association to Folder
	@OneToMany(mappedBy="user")
	private List<Folder> folders1;

	//bi-directional many-to-many association to Folder
	@ManyToMany
	@JoinTable(
		name="user_folder_link"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="folder_id")
			}
		)
	private List<Folder> folders2;

	//bi-directional many-to-one association to Entity
	@OneToMany(mappedBy="user")
	private List<Entity> entities;

	//bi-directional many-to-one association to Value
	@OneToMany(mappedBy="user")
	private List<Value> values;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(Object publicKey) {
		this.publicKey = publicKey;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Folder> getFolders1() {
		return this.folders1;
	}

	public void setFolders1(List<Folder> folders1) {
		this.folders1 = folders1;
	}

	public Folder addFolders1(Folder folders1) {
		getFolders1().add(folders1);
		folders1.setUser(this);

		return folders1;
	}

	public Folder removeFolders1(Folder folders1) {
		getFolders1().remove(folders1);
		folders1.setUser(null);

		return folders1;
	}

	public List<Folder> getFolders2() {
		return this.folders2;
	}

	public void setFolders2(List<Folder> folders2) {
		this.folders2 = folders2;
	}

	public List<Entity> getEntities() {
		return this.entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Entity addEntity(Entity entity) {
		getEntities().add(entity);
		entity.setUser(this);

		return entity;
	}

	public Entity removeEntity(Entity entity) {
		getEntities().remove(entity);
		entity.setUser(null);

		return entity;
	}

	public List<Value> getValues() {
		return this.values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Value addValue(Value value) {
		getValues().add(value);
		value.setUser(this);

		return value;
	}

	public Value removeValue(Value value) {
		getValues().remove(value);
		value.setUser(null);

		return value;
	}

}