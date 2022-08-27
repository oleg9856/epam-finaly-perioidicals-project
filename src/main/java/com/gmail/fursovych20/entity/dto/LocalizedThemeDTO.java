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

	public LocalizedThemeDTO(int id, String defaultName, Map<LocaleType, String> localizedNames) {
		super();
		this.id = id;
		this.defaultName = defaultName;
		this.localizedNames = localizedNames;
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
