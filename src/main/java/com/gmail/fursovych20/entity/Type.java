package com.gmail.fursovych20.entity;

import java.io.Serializable;

public class Type implements Serializable{
	
	private static final long serialVersionUID = 1421835447041309991L;
	
	private short id;
	private String name;

	public Type(short id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Type() {
		super();
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Type other = (Type) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			return other.name == null;
		} else return name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return "Theme [id=" + id + ", name=" + name + "]";
	}
	

}
