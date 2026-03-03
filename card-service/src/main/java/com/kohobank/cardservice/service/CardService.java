package com.kohobank.cardservice.service;

import com.kohobank.cardservice.dto.CardDto;

public interface CardService {

    void createCard(String mobileNumber);

    CardDto getCard(String mobileNumber);

    boolean updateCard(CardDto cardsDto);

    boolean deleteCard(String mobileNumber);

}
