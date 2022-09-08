package com.gmail.fursovych20.entity.dto;


import com.gmail.fursovych20.entity.LocaleType;

import java.util.Map;
import java.util.Objects;

public class LocalizedTypeDTO {
	
	private int id;
	private String defaultName;
	private Map<LocaleType, String> localizedNames;
	
	public LocalizedTypeDTO() {
		super();
	}

	/**
	 * Builder for build locale type
	 */
	public static class Builder{
		private final LocalizedTypeDTO localizedTypeDTO;

		public Builder() {
			this.localizedTypeDTO = new LocalizedTypeDTO();
		}

		public LocalizedTypeDTO.Builder setId(int id){
			localizedTypeDTO.setId(id);
			return this;
		}

		public LocalizedTypeDTO.Builder setDefaultName(String defaultName){
			localizedTypeDTO.setDefaultName(defaultName);
			return this;
		}

		public LocalizedTypeDTO.Builder setLocalizedNames(Map<LocaleType, String> localizedNames) {
			localizedTypeDTO.setLocalizedNames(localizedNames);
			return this;
		}

		public LocalizedTypeDTO build(){
			return localizedTypeDTO;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Map<LocaleType, String> getLocalizedNames() {
		return localizedNames;
	}

	public void setLocalizedNames(Map<LocaleType, String> localizedNames) {
		this.localizedNames = localizedNames;
	}

	@Override
	public String toString() {
		return "LocalizedType{" +
				"id=" + id +
				", defaultName='" + defaultName + '\'' +
				", localizedNames=" + localizedNames +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocalizedTypeDTO)) return false;
		LocalizedTypeDTO that = (LocalizedTypeDTO) o;
		return id == that.id && Objects.equals(defaultName, that.defaultName) && Objects.equals(localizedNames, that.localizedNames);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, defaultName, localizedNames);
	}
}
