package com.ngahp.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ngahp.userservice.entity.abstractentity.AbstractAuditingEntity;
import com.ngahp.userservice.enums.TwoFaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class UserDetail extends AbstractAuditingEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5941807402770380460L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(columnDefinition = "varchar(32) default 'NONE'")
	@Enumerated(EnumType.STRING)
	private TwoFaType twoFaType = TwoFaType.NONE;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "first_name", columnDefinition = "varchar(512)")
	private String firstName;

	@Column(name = "middle_name", columnDefinition = "varchar(512)")
	private String middleName;

	@Column(name = "last_name", columnDefinition = "varchar(512)")
	private String lastName;

	@Column(name = "full_name", columnDefinition = "varchar(512)")
	private String fullName;

	@OneToOne(mappedBy = "userDetail")
	private User user;
}
