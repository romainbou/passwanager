package org.isen.jee.project;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the value database table.
 * 
 */
@Entity
@Table(name="value")
@NamedQuery(name="Value.findAll", query="SELECT v FROM Value v")
public class Value implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private Object value;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_user")
	private User user;

	//bi-directional many-to-one association to Entity
	@ManyToOne
	@JoinColumn(name="ref_entity")
	private Entity entity;

	public Value() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}