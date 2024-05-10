package com.virtualwallet.services;

import com.virtualwallet.model_helpers.CardTransactionModelFilterOptions;
import com.virtualwallet.models.*;
import com.virtualwallet.repositories.contracts.CardToWalletTransactionRepository;
import com.virtualwallet.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.virtualwallet.model_helpers.ModelConstantHelper.*;

@Service
public class CardToWalletTransactionServiceImpl implements CardTransactionService {

    private final CardToWalletTransactionRepository cardTransactionRepository;
    private final StatusService statusService;

    private final WalletService walletService;

    private final CardService cardService;

    private final TransferService transferService;

    @Autowired
    public CardToWalletTransactionServiceImpl(CardToWalletTransactionRepository cardTransactionRepository,
                                              StatusService statusService, WalletService walletService, CardService cardService, TransferService transferService) {
        this.cardTransactionRepository = cardTransactionRepository;
        this.statusService = statusService;
        this.walletService = walletService;
        this.cardService = cardService;
        this.transferService = transferService;
    }

    @Override
    public List<CardToWalletTransaction> getAllCardTransactions() {
        return cardTransactionRepository.getAll();
    }

    @Override
    public List<CardToWalletTransaction> getAllCardTransactionsWithFilter
            (User user, CardTransactionModelFilterOptions transactionFilter) {

        return cardTransactionRepository.getAllCardTransactionsWithFilter(user, transactionFilter);
    }

    @Override
    public List<CardToWalletTransaction> getUserCardTransactions(int walletId,
                                                                 User user,
                                                                 CardTransactionModelFilterOptions transactionFilter) {
        return new ArrayList<>(cardTransactionRepository.getAllUserCardTransactions(walletId, user, transactionFilter));
    }

    @Override
    public CardToWalletTransaction getCardTransactionById(int cardTransactionId) {
        return cardTransactionRepository.get(cardTransactionId);
    }

    @Override
    public void updateCardTransaction(CardToWalletTransaction cardTransaction, User user) {
        cardTransactionRepository.update(cardTransaction);
    }

    @Override
    public void approveTransaction(CardToWalletTransaction cardTransaction,
                                   User user, Card card, Wallet wallet) {
        populateCardTransactionDetails(cardTransaction, user, card, statusService
                .getStatus(CONFIRMED_TRANSACTION_ID), wallet);
        cardTransactionRepository.create(cardTransaction);
        wallet.getCardTransactions().add(cardTransaction);
    }

    @Override
    public void declineTransaction(CardToWalletTransaction cardTransaction,
                                   User user, Card card, Wallet wallet) {
        populateCardTransactionDetails(cardTransaction, user, card, statusService
                .getStatus(DECLINED_TRANSACTION_ID), wallet);
        cardTransactionRepository.create(cardTransaction);
        wallet.getCardTransactions().add(cardTransaction);
    }

    private void populateCardTransactionDetails(CardToWalletTransaction cardTransaction,
                                                User user, Card card, Status status, Wallet wallet) {
        cardTransaction.setCardId(card.getId());
        cardTransaction.setUserId(user.getId());
        cardTransaction.setWalletId(wallet.getWalletId());
        cardTransaction.setTransactionTypeId(INCOMING_TRANSACTION_TYPE_ID);
        cardTransaction.setStatus(status);
    }


    @Override
    public CardToWalletTransaction transactionWithCard(User user, int card_id, int wallet_id,
                                                       CardToWalletTransaction cardTransaction) {
        Wallet wallet = walletService.getWalletById(user, wallet_id);
        Card card = cardService.getCard(card_id, user, user.getId());
        String responseResult = transferService.sendTransferRequest(card);
        if (responseResult.equals(APPROVED_TRANSFER)) {
            wallet.setBalance(cardTransaction.getAmount() + wallet.getBalance());
            approveTransaction(cardTransaction, user, card, wallet);
//            walletService.updateWallet(user, wallet);
        } else if (responseResult.equals(DECLINED_TRANSFER)) {
            declineTransaction(cardTransaction, user, card, wallet);
//            walletService.updateWallet(user, wallet);
        }
        walletService.updateWallet(user, wallet);
        return cardTransaction;
    }

}
