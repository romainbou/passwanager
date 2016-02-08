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

	//bi-directional many-to-one association to Entry
	@ManyToOne
	@JoinColumn(name="ref_entry")
	private Entry entry;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_user")
	private User user;

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

	public Entry getEntry() {
		return this.entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}