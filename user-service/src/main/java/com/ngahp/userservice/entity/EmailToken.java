package com.ngahp.userservice.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ngahp.userservice.entity.abstractentity.AbstractAuditingEntity;
import com.ngahp.userservice.enums.TokenType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class EmailToken extends AbstractAuditingEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 6985286556880104547L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "token")
	private String token;
	
    @ManyToOne
    private User user;
    
    @Column(name = "token_type", columnDefinition = "varchar(32)")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "expiration")
    private Instant expiration;

    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
    private boolean deleted;
}
