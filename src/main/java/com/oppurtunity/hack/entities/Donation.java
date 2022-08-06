package com.oppurtunity.hack.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "donation")
public class Donation {

	private long id;

	private String donor;

	public String getDonor() {
		return donor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Donation donation = (Donation) o;
		return id == donation.id &&
				Objects.equals(donor, donation.donor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, donor);
	}

	public void setDonor(String donor) {
		this.donor = donor;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Donation{" +
				"id=" + id +
				", donor='" + donor + '\'' +
				'}';
	}

	public void setId(long id) {
		this.id = id;
	}

}