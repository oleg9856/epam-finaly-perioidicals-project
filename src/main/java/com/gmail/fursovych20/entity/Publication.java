package com.gmail.fursovych20.entity;

import java.io.Serializable;

public class Publication implements Serializable{

	private static final long serialVersionUID = 5393763787035100980L;
	
	private int id;
	private String name;
	private String description;
	private short themeId;
	private short typeId;
	private double price;
	private String picturePath;

	public Publication() {
		super();
	}

	/**
	 * Builder for build publication
	 */
	public static class Builder{
		private final Publication publication;

		public Builder() {
			this.publication = new Publication();
		}

		public Publication.Builder setId(int id){
			publication.setId(id);
			return this;
		}

		public Publication.Builder setName(String name){
			publication.setName(name);
			return this;
		}

		public Publication.Builder setDescription(String description){
			publication.setDescription(description);
			return this;
		}

		public Publication.Builder setThemeId(short themeId){
			publication.setThemeId(themeId);
			return this;
		}

		public Publication.Builder setTypeId(short typeId) {
			publication.setTypeId(typeId);
			return this;
		}

		public Publication.Builder setPrice(double price) {
			publication.setPrice(price);
			return this;
		}

		public Publication.Builder setPicturePath(String picturePath) {
			publication.setPicturePath(picturePath);
			return this;
		}

		public Publication build(){
			return publication;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getThemeId() {
		return themeId;
	}

	public void setThemeId(short themeId) {
		this.themeId = themeId;
	}

	public short getTypeId() {
		return typeId;
	}

	public void setTypeId(short typeId) {
		this.typeId = typeId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + themeId;
		result = prime * result + typeId;
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
		Publication other = (Publication) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (themeId != other.themeId)
			return false;
		return typeId == other.typeId;
	}

	@Override
	public String toString() {
		return "Publication [id=" + id + ", name=" + name + ", description=" + description + ", themeId=" + themeId + ", typeID=" + typeId + ", price=" + price + "]";
	}

}
