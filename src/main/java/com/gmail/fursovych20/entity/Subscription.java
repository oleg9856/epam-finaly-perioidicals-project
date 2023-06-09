package com.gmail.fursovych20.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Subscription implements Serializable{

	private static final long serialVersionUID = -6271585104911424635L;

	private int id;
	private int userId;
	private int publicationId;
	private LocalDate startLocaleDate;
	private LocalDate endLocaleDate;
	private double price;

	public Subscription() {
		super();
	}
	private SubscriptionStatus status;

	/**
	 * Builder for build subscription
	 */
	public static class Builder{

		private final Subscription subscription;

		public Builder() {
			this.subscription = new Subscription();
		}

		public Subscription.Builder setId(int id){
			subscription.setId(id);
			return this;
		}

		public Subscription.Builder setUserId(int userId){
			subscription.setUserId(userId);
			return this;
		}

		public Subscription.Builder setPublicationId(int publicationId){
			subscription.setPublicationId(publicationId);
			return this;
		}

		public Subscription.Builder setStartLocalDate(LocalDate startDate){
			subscription.setStartLocalDate(startDate);
			return this;
		}

		public Subscription.Builder setEndLocalDate(LocalDate endDate){
			subscription.setEndLocalDate(endDate);
			return this;
		}

		public Subscription.Builder setPrice(double price){
			subscription.setPrice(price);
			return this;
		}

		public Subscription.Builder setStatus(SubscriptionStatus status){
			subscription.setStatus(status);
			return this;
		}

		public Subscription build(){
			return subscription;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(int publicationId) {
		this.publicationId = publicationId;
	}

	public LocalDate getStartLocalDate() {
		return startLocaleDate;
	}

	public void setStartLocalDate(LocalDate startDate) {
		this.startLocaleDate = startDate;
	}

	public LocalDate getEndLocalDate() {
		return endLocaleDate;
	}

	public void setEndLocalDate(LocalDate endDate) {
		this.endLocaleDate = endDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public SubscriptionStatus getStatus() {
		return status;
	}

	public void setStatus(SubscriptionStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endLocaleDate == null) ? 0 : endLocaleDate.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + publicationId;
		result = prime * result + ((startLocaleDate == null) ? 0 : startLocaleDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + userId;
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
		Subscription other = (Subscription) obj;
		if (endLocaleDate == null) {
			if (other.endLocaleDate != null)
				return false;
		} else if (!endLocaleDate.equals(other.endLocaleDate))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (publicationId != other.publicationId)
			return false;
		if (startLocaleDate == null) {
			if (other.startLocaleDate != null)
				return false;
		} else if (!startLocaleDate.equals(other.startLocaleDate))
			return false;
		if (status != other.status)
			return false;
		return userId == other.userId;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", publicationId=" + publicationId + ", startDate="
				+ startLocaleDate + ", endDate=" + endLocaleDate + ", price=" + price + ", status=" + status + "]";
	}

}
