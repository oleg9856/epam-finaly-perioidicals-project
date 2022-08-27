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

	public Publication(int id, String name, String description, short themeId, short typeId, double price, String picturePath) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.themeId = themeId;
		this.typeId = typeId;
		this.price = price;
		this.picturePath = picturePath;
	}

	public Publication() {
		super();
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
