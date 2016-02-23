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
	private String publicKey;

	private String username;

	//bi-directional many-to-one association to Entry
	@OneToMany(mappedBy="user")
	private List<Entry> entries;

	//bi-directional many-to-one association to Folder
	@OneToMany(mappedBy="owner")
	private List<Folder> ownedFolders;

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
	private List<Folder> folders;

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

	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Entry addEntry(Entry entry) {
		getEntries().add(entry);
		entry.setUser(this);

		return entry;
	}

	public Entry removeEntry(Entry entry) {
		getEntries().remove(entry);
		entry.setUser(null);

		return entry;
	}

	public List<Folder> getOwnedFolders() {
		return this.ownedFolders;
	}

	public void setOwnedFolders(List<Folder> ownedFolders) {
		this.ownedFolders = ownedFolders;
	}

	public Folder addOwnedFolder(Folder ownedFolder) {
		getOwnedFolders().add(ownedFolder);
		ownedFolder.setOwner(this);
		return ownedFolder;
	}

	public Folder removeOwnedFolder(Folder ownedFolder) {
		getOwnedFolders().remove(ownedFolder);
		ownedFolder.setOwner(null);

		return ownedFolder;
	}

	public List<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
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