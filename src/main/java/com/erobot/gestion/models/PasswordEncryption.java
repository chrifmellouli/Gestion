package com.erobot.gestion.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ENCRYCPTION")
public class PasswordEncryption {

	@Id
	@Column(name = "ENCRYCPTION")
	private String encryption = getRandomStr(25);
	@Transient
	private static PasswordEncryption instance;

	private PasswordEncryption() {
		this.encryption = getRandomStr(25);
	}

	public static synchronized PasswordEncryption getInstance() {
		if (instance == null) {
			instance = new PasswordEncryption();
		}
		return instance;
	}

	private String getRandomStr(int n) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder s = new StringBuilder(n);

		for (int i = 0; i < n; i++) {
			double index = str.length() * Math.random();
			s.append(str.charAt((int) index));
		}
		return s.toString();
	}

	public String encrypt(String password) {
		return encryption + password + encryption;
	}

}
