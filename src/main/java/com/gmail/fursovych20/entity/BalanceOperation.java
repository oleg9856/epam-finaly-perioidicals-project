package com.gmail.fursovych20.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BalanceOperation implements Serializable{

	private static final long serialVersionUID = -4049851962249400644L;
	
	private int id;
	private int idUser;
	private LocalDate localDate;
	private BigDecimal sum;
	private BalanceOperationType type;

	public BalanceOperation() {
		super();
	}

	/**
	 * Builder for build balance operation
	 */
	public static class Builder{
		private final BalanceOperation balanceOperation;

		public Builder() {
			this.balanceOperation = new BalanceOperation();
		}

		public BalanceOperation.Builder setId(int id) {
			balanceOperation.setId(id);
			return this;
		}

		public BalanceOperation.Builder setIdUser(int idUser) {
			balanceOperation.setIdUser(idUser);
			return this;
		}

		public BalanceOperation.Builder setLocalDate(LocalDate date) {
			balanceOperation.setLocalDate(date);
			return this;
		}

		public BalanceOperation.Builder setSum(BigDecimal sum) {
			balanceOperation.setSum(sum);
			return this;
		}

		public BalanceOperation.Builder setType(BalanceOperationType type) {
			balanceOperation.setType(type);
			return this;
		}

		public BalanceOperation build(){
			return balanceOperation;
		}

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate date) {
		this.localDate = date;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public BalanceOperationType getType() {
		return type;
	}
	public void setType(BalanceOperationType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localDate == null) ? 0 : localDate.hashCode());
		result = prime * result + id;
		result = prime * result + idUser;
		long temp;
		temp = sum.longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		BalanceOperation other = (BalanceOperation) obj;
		if (localDate == null) {
			if (other.localDate != null)
				return false;
		} else if (!localDate.equals(other.localDate))
			return false;
		if (id != other.id)
			return false;
		if (idUser != other.idUser)
			return false;
		if (!sum.equals(other.sum))
			return false;
		return type == other.type;
	}
	
	@Override
	public String toString() {
		return "BalanceOperation [id=" + id + ", idUser=" + idUser + ", date=" + localDate + ", sum=" + sum + ", type="
				+ type + "]";
	}

}
