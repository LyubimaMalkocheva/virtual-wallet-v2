package com.virtualwallet.model_mappers;

import com.virtualwallet.models.CardToWalletTransaction;
import com.virtualwallet.models.TransactionCategory;
import com.virtualwallet.models.WalletToWalletTransaction;
import com.virtualwallet.models.response_model_dto.TransactionResponseDto;
import com.virtualwallet.repositories.contracts.CardRepository;
import com.virtualwallet.repositories.contracts.TransactionCategoryRepository;
import com.virtualwallet.repositories.contracts.WalletRepository;
import com.virtualwallet.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.virtualwallet.model_helpers.ModelConstantHelper.*;

@Component
public class TransactionResponseMapper {
    private final CardRepository cardRepository;
    private final WalletRepository walletRepository;

    private final TransactionCategoryRepository transactionCategoryRepository;
    @Autowired
    public TransactionResponseMapper(CardRepository cardRepository,
                                     WalletRepository walletRepository, TransactionCategoryRepository transactionCategoryRepository) {
        this.cardRepository = cardRepository;
        this.walletRepository = walletRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public TransactionResponseDto convertToDto(WalletToWalletTransaction walletToWalletTransaction) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setTransactionType(walletToWalletTransaction
                .getTransactionTypeId() == INCOMING_TRANSACTION_TYPE_ID ? INCOMING_TRANSACTION : OUTGOING_TRANSACTION);
        dto.setTransactionId(walletToWalletTransaction.getTransactionId());
        dto.setAmount(walletToWalletTransaction.getAmount());
        dto.setSender(walletToWalletTransaction.getSender().getUsername());
        dto.setRecipient(walletRepository.getById(walletToWalletTransaction.getRecipientWalletId()).getIban());
        dto.setTime(walletToWalletTransaction.getTime());
        dto.setStatus(walletToWalletTransaction.getStatus().getName());
        dto.setCategory(walletToWalletTransaction.getCategory());
        return dto;
    }

    public TransactionResponseDto convertToDto(CardToWalletTransaction cardToWalletTransaction) throws Exception {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setTransactionType(cardToWalletTransaction
                .getTransactionTypeId() == INCOMING_TRANSACTION_TYPE_ID ? INCOMING_TRANSACTION : OUTGOING_TRANSACTION);
        dto.setTransactionId(cardToWalletTransaction.getTransactionId());
        dto.setAmount(cardToWalletTransaction.getAmount());
        dto.setSender(HIDDEN_CARD_DIGITS.concat
                (AESUtil.decrypt(cardRepository.getById(cardToWalletTransaction.getCardId()).getNumber())
                        .substring(CARD_LAST_4_DIGIT_START_INDEX, CARD_LAST_4_DIGIT_LAST_INDEX)));
        dto.setRecipient(walletRepository.getById(cardToWalletTransaction.getWalletId()).getIban());
        dto.setTime(cardToWalletTransaction.getTime());
        dto.setStatus(cardToWalletTransaction.getStatus().getName());
        dto.setCategory(transactionCategoryRepository.findById(7));
        return dto;
    }

    public List<TransactionResponseDto> convertWalletTransactionsToDto
            (List<WalletToWalletTransaction> walletToWalletTransactions) {
        List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();
        for (WalletToWalletTransaction walletToWalletTransaction : walletToWalletTransactions) {
            transactionResponseDtos.add(convertToDto(walletToWalletTransaction));
        }
        return transactionResponseDtos;
    }

    public List<TransactionResponseDto> convertCardTransactionsToDto
            (List<CardToWalletTransaction> cardToWalletTransactions) throws Exception {
        List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();
        for (CardToWalletTransaction cardToWalletTransaction : cardToWalletTransactions) {
            transactionResponseDtos.add(convertToDto(cardToWalletTransaction));
        }
        return transactionResponseDtos;
    }
}
