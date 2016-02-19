package org.isen.jee.project.model;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String name;

	//bi-directional many-to-one association to Entry
	@OneToMany(mappedBy="folder")
	private List<Entry> entries;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_owner")
	private User owner;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="folders")
	private List<User> users;

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

	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Entry addEntry(Entry entry) {
		getEntries().add(entry);
		entry.setFolder(this);

		return entry;
	}

	public Entry removeEntry(Entry entry) {
		getEntries().remove(entry);
		entry.setFolder(null);

		return entry;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}