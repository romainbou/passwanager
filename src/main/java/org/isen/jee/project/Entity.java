package org.isen.jee.project;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the entity database table.
 * 
 */
@Entity
@Table(name="entity")
@NamedQuery(name="Entity.findAll", query="SELECT e FROM Entity e")
public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_user")
	private User user;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="ref_folder")
	private Folder folder;

	//bi-directional many-to-one association to Value
	@OneToMany(mappedBy="entity")
	private List<Value> values;

	public Entity() {
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public List<Value> getValues() {
		return this.values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Value addValue(Value value) {
		getValues().add(value);
		value.setEntity(this);

		return value;
	}

	public Value removeValue(Value value) {
		getValues().remove(value);
		value.setEntity(null);

		return value;
	}

}