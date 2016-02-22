package org.isen.jee.project;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the folder database table.
 * 
 */
@Entity
@Table(name="folder")
@NamedQuery(name="Folder.findAll", query="SELECT f FROM Folder f")
public class Folder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String name;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_owner")
	private User user;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="folders2")
	private List<User> users;

	//bi-directional many-to-one association to Entity
	@OneToMany(mappedBy="folder")
	private List<Entity> entities;

	public Folder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Entity> getEntities() {
		return this.entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Entity addEntity(Entity entity) {
		getEntities().add(entity);
		entity.setFolder(this);

		return entity;
	}

	public Entity removeEntity(Entity entity) {
		getEntities().remove(entity);
		entity.setFolder(null);

		return entity;
	}

}