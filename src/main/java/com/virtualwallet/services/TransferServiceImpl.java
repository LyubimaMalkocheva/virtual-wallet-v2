package com.virtualwallet.services;

import com.virtualwallet.model_mappers.CardMapper;
import com.virtualwallet.models.Card;
import com.virtualwallet.models.input_model_dto.CardForAddingMoneyToWalletDto;
import com.virtualwallet.services.contracts.TransferService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import static com.virtualwallet.model_helpers.ModelConstantHelper.DUMMY_API_COMPLETE_URL;

@Service
public class TransferServiceImpl  implements TransferService {
    private final CardMapper cardMapper;
    private final WebClient dummyApiWebClient;

    public TransferServiceImpl(CardMapper cardMapper, WebClient dummyApiWebClient) {
        this.cardMapper = cardMapper;
        this.dummyApiWebClient = dummyApiWebClient;
    }

    private WebClient.ResponseSpec populateResponseSpec(WebClient.RequestHeadersSpec<?> headersSpec) {
        return headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
    }
    @Override
    public String sendTransferRequest(Card card) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = dummyApiWebClient.method(HttpMethod.POST);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(DUMMY_API_COMPLETE_URL));
        CardForAddingMoneyToWalletDto cardDto = cardMapper.toDummyApiDto(card);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(cardDto);
        WebClient.ResponseSpec responseSpec = populateResponseSpec(headersSpec);
        Mono<String> response = headersSpec.retrieve().bodyToMono(String.class);
        return response.block();
    }
}
