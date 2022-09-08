package com.gmail.fursovych20.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Issue implements Serializable{

	private static final long serialVersionUID = 9051604494988006331L;
	
	private int id;
	private LocalDate localDateOfPublication;
	private int publicationId;
	private String description;
	private String file;

	public Issue() {
		super();
	}

	/**
	 * Builder for build issue
	 */
	public static class Builder{
		private final Issue issue;

		public Builder() {
			this.issue = new Issue();
		}

		public Issue.Builder setId(int id){
			issue.setId(id);
			return this;
		}

		public Issue.Builder setLocalDateOfPublication(LocalDate dateOfPublication){
			issue.setLocalDateOfPublication(dateOfPublication);
			return this;
		}

		public Issue.Builder setPublicationId(int publicationId){
			issue.setPublicationId(publicationId);
			return this;
		}

		public Issue.Builder setFile(String file){
			issue.setFile(file);
			return this;
		}

		public Issue.Builder setDescription(String description) {
			issue.setDescription(description);
			return this;
		}

		public Issue build(){
			return issue;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getLocalDateOfPublication() {
		return localDateOfPublication;
	}

	public void setLocalDateOfPublication(LocalDate dateOfPublication) {
		this.localDateOfPublication = dateOfPublication;
	}

	public int getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(int publicationId) {
		this.publicationId = publicationId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localDateOfPublication == null) ? 0 : localDateOfPublication.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + id;
		result = prime * result + publicationId;
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
		Issue other = (Issue) obj;
		if (localDateOfPublication == null) {
			if (other.localDateOfPublication != null)
				return false;
		} else if (!localDateOfPublication.equals(other.localDateOfPublication))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (id != other.id)
			return false;
		return publicationId == other.publicationId;
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", dateOfPublication=" + localDateOfPublication + ", publicationId=" + publicationId
				+ ", description=" + description + ", file=" + file + "]";
	}	
}
