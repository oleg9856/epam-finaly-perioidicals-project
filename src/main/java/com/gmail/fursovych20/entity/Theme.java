package com.gmail.fursovych20.entity;

import java.io.Serializable;

public class Theme implements Serializable{
	
	private static final long serialVersionUID = 1421835447041309991L;
	
	private short id;
	private String name;

	/**
	 * Builder for build theme
	 */
	public static class Builder{
		private final Theme theme;

		public Builder() {
			this.theme = new Theme();
		}

		public Theme.Builder setId(short id) {
			theme.setId(id);
			return this;
		}

		public Theme.Builder setName(String name) {
			theme.setName(name);
			return this;
		}

		public Theme build() {
			return theme;
		}
	}

	public Theme() {
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
		Theme other = (Theme) obj;
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
