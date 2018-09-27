package com.nexaas.desafio.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COMERCIANTE")
public class Merchant implements Serializable {

	private static final long serialVersionUID = 8318857773230573530L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	private Long id;

	@NotNull(message = "Não pode ser nulo.")
	@Size(min = 5, max = 50, message="Nome deve ter no mínimo 5 caracteres e no máximo 50.")
	@Column(name = "NOME", columnDefinition = "VARCHAR2", length = 50)
	private String name;

	@NotNull(message = "Não pode ser nulo.")
	@Size(min = 5, max = 255, message="Endereço deve ter no mínimo 5 caracteres e no máximo 255.")
	@Column(name = "ENDERECO", columnDefinition = "VARCHAR2", length = 255)
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Merchant other = (Merchant) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Merchant [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
