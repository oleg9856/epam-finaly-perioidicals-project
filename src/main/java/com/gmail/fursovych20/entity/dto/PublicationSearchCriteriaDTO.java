package com.gmail.fursovych20.entity.dto;

import com.gmail.fursovych20.entity.LocaleType;

import java.util.Objects;

public class PublicationSearchCriteriaDTO {
	
	private LocaleType locale;
	private int themeId;
	private int typeId;
	private int orderId;
	private int currentPage;
	private int itemsPerPage;
	private int pageCount;

	public PublicationSearchCriteriaDTO(LocaleType locale, int themeId, int typeId, int orderId, int currentPage, int itemsPerPage, int pageCount) {
		this.locale = locale;
		this.themeId = themeId;
		this.typeId = typeId;
		this.orderId = orderId;
		this.currentPage = currentPage;
		this.itemsPerPage = itemsPerPage;
		this.pageCount = pageCount;
	}

	public PublicationSearchCriteriaDTO() {
		super();
	}
	
	public LocaleType getLocale() {
		return locale;
	}
	
	public void setLocale(LocaleType locale) {
		this.locale = locale;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "PublicationSearchCriteria{" +
				"locale=" + locale +
				", themeId=" + themeId +
				", typeId=" + typeId +
				", orderId=" + orderId +
				", currentPage=" + currentPage +
				", itemsPerPage=" + itemsPerPage +
				", pageCount=" + pageCount +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PublicationSearchCriteriaDTO)) return false;
		PublicationSearchCriteriaDTO criteria = (PublicationSearchCriteriaDTO) o;
		return themeId == criteria.themeId && typeId == criteria.typeId && orderId == criteria.orderId && currentPage == criteria.currentPage && itemsPerPage == criteria.itemsPerPage && pageCount == criteria.pageCount && locale == criteria.locale;
	}

	@Override
	public int hashCode() {
		return Objects.hash(locale, themeId, typeId, orderId, currentPage, itemsPerPage, pageCount);
	}
}
