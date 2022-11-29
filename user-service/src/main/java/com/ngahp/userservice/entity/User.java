package com.ngahp.userservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ngahp.userservice.entity.abstractentity.AbstractAuditingEntity;
import com.ngahp.userservice.enums.UserStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class User extends AbstractAuditingEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -475899665573520386L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(columnDefinition = "varchar(32) default 'UNVERIFIED'")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus = UserStatus.UNVERIFIED;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_detail")
	private UserDetail userDetail;

	@OneToMany(mappedBy = "user")
	private List<EmailToken> emailOtps;

	@Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;
}
