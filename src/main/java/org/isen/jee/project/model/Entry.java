package org.isen.jee.project.model;

import java.io.Serializable;

import javax.persistence.*;

import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;
import org.isen.jee.project.model.Value;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the entry database table.
 * 
 */
@Entity
@Table(name="entry")
@NamedQuery(name="Entry.findAll", query="SELECT e FROM Entry e")
public class Entry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Lob
	private String notes;

	private String title;

	@Lob
	private String url;

	private String username;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="ref_folder")
	private Folder folder;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ref_user")
	private User user;

	//bi-directional many-to-one association to Value
	@OneToMany(mappedBy="entry")
	private List<Value> values;

	public Entry() {
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Value> getValues() {
		return this.values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Value addValue(Value value) {
		getValues().add(value);
		value.setEntry(this);

		return value;
	}

	public Value removeValue(Value value) {
		getValues().remove(value);
		value.setEntry(null);

		return value;
	}

}