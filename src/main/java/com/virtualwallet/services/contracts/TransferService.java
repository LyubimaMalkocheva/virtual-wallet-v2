package com.virtualwallet.services.contracts;

import com.virtualwallet.models.Card;

public interface TransferService {
    String sendTransferRequest(Card card);
}
