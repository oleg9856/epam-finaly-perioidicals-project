package com.gmail.fursovych20.entity.dto;

import com.gmail.fursovych20.entity.LocaleType;

import java.util.Map;
import java.util.Objects;

public class LocalizedPublicationDTO {
	
	private int id;
	private Map<LocaleType, String> names;
	private Map<LocaleType, String> descriptions;
	private short themeId;
	private short typeID;
	private double price;
	private String picturePath;

	public LocalizedPublicationDTO(int id, Map<LocaleType, String> names, Map<LocaleType, String> descriptions, short themeId, short typeID, double price, String picturePath) {
		super();
		this.id = id;
		this.names = names;
		this.descriptions = descriptions;
		this.themeId = themeId;
		this.typeID = typeID;
		this.price = price;
		this.picturePath = picturePath;
	}

	public LocalizedPublicationDTO() {
		super();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<LocaleType, String> getNames() {
		return names;
	}
	
	public void setNames(Map<LocaleType, String> names) {
		this.names = names;
	}
	
	public Map<LocaleType, String> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(Map<LocaleType, String> descriptions) {
		this.descriptions = descriptions;
	}
	
	public short getThemeId() {
		return themeId;
	}
	
	public void setThemeId(short themeId) {
		this.themeId = themeId;
	}
	
	public short getTypeID() {
		return typeID;
	}
	
	public void setTypeID(short typeID) {
		this.typeID = typeID;
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
	public String toString() {
		return "LocalizedPublication{" +
				"id=" + id +
				", names=" + names +
				", descriptions=" + descriptions +
				", themeId=" + themeId +
				", typeID=" + typeID +
				", price=" + price +
				", picturePath='" + picturePath + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocalizedPublicationDTO)) return false;
		LocalizedPublicationDTO that = (LocalizedPublicationDTO) o;
		return id == that.id && themeId == that.themeId && typeID == that.typeID && Double.compare(that.price, price) == 0 && Objects.equals(names, that.names) && Objects.equals(descriptions, that.descriptions) && Objects.equals(picturePath, that.picturePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, names, descriptions, themeId, typeID, price, picturePath);
	}
}
