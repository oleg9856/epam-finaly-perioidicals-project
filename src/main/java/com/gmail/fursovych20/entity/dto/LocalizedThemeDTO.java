package com.gmail.fursovych20.entity.dto;

import com.gmail.fursovych20.entity.LocaleType;

import java.util.Map;
import java.util.Objects;

public class LocalizedThemeDTO {
	
	private int id;
	private String defaultName;
	private Map<LocaleType, String> localizedNames;
	
	public LocalizedThemeDTO() {
		super();
	}

	/**
	 * Builder for build locale theme
	 */
	public static class Builder{
		private final LocalizedThemeDTO localizedThemeDTO;

		public Builder() {
			this.localizedThemeDTO = new LocalizedThemeDTO();
		}

		public LocalizedThemeDTO.Builder setId(int id){
			localizedThemeDTO.setId(id);
			return this;
		}

		public LocalizedThemeDTO.Builder setDefaultName(String defaultName){
			localizedThemeDTO.setDefaultName(defaultName);
			return this;
		}

		public LocalizedThemeDTO.Builder setLocalizedNames(Map<LocaleType, String> localizedNames){
			localizedThemeDTO.setLocalizedNames(localizedNames);
			return this;
		}

		public LocalizedThemeDTO build(){
			return localizedThemeDTO;
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
		return "LocalizedTheme{" +
				"id=" + id +
				", defaultName='" + defaultName + '\'' +
				", localizedNames=" + localizedNames +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocalizedThemeDTO)) return false;
		LocalizedThemeDTO that = (LocalizedThemeDTO) o;
		return id == that.id && Objects.equals(defaultName, that.defaultName) && Objects.equals(localizedNames, that.localizedNames);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, defaultName, localizedNames);
	}
}
