
package com.book.store.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.book.store.util.LocalDateTimeConverter;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
@Data
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// Email id as username
	private String username;

	private String name;

	private String password;

	private String role;

	// User will active after email verification
	private Boolean isActive;

	// Random token for email verification
	private String confirmToken;

	// Random token for forgot password
	private String resetToken;

	@CreationTimestamp
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdAt;

	private String fcmId;

	@UpdateTimestamp
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime lastUpdatedAt;

	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_issue_details", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<Book> books;

}
