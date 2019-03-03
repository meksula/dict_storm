package com.dictstorm.domain.translator;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
//@RestController
//@RequestMapping("/api/v1/translator")
class TranslatorController {

//    @GetMapping("/{word}")
//    @ResponseStatus(HttpStatus.OK)
//    Mono<Translation> translate(/*@PathVariable */String word) {
//        Translate translate = TranslateOptions.getDefaultInstance().getService();
//
//        Translation translation = translate.translate(word,
//                Translate.TranslateOption.sourceLanguage("en"),
//                Translate.TranslateOption.targetLanguage("pl"));
//
//        return Mono.just(translation);
//    }

    TranslationService translationService = new TranslationService();

    @Bean
    @SuppressWarnings("all")
    RouterFunction routes() {
        return route()
                .GET("/api/v1/translator/{word}", translationService::translate)
                .build();
    }
}

class TranslationService {

    Mono<ServerResponse> translate(ServerRequest serverRequest) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation = translate.translate(serverRequest.pathVariable("word"),
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("pl"));


        return ServerResponse.ok().body(fromObject(translation));
    }
}