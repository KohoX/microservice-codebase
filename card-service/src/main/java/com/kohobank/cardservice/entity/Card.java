package com.kohobank.cardservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@Column(name = "card_number", nullable = false)
	private String cardNumber;

	@Column(name = "card_type", nullable = false)
	private String cardType;

	@Column(name = "total_limit", nullable = false)
	private int totalLimit;

	@Column(name = "amount_used", nullable = false)
	private int amountUsed;

	@Column(name = "available_amount", nullable = false)
	private int availableAmount;
	
}
