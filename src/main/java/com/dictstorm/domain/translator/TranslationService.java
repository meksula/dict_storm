package com.dictstorm.domain.translator;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
class TranslationService {
    private Translate translate;

    TranslationService() {
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    @Bean
    RouterFunction routes() {
        return route()
                .POST("/api/v1/translator", this::processTranslate).build();
    }

    private Mono<ServerResponse> processTranslate(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TranslateRequest.class)
                .map(this::processRequest)
                .flatMap(translation -> ok().body(fromObject(translation)));
    }

    private Translation processRequest(TranslateRequest translateRequest) {
        return this.translate
                .translate(translateRequest.getValue(),
                        Translate.TranslateOption.sourceLanguage(translateRequest.getFrom().getValue()),
                        Translate.TranslateOption.targetLanguage(translateRequest.getTo().getValue()));
    }
}
