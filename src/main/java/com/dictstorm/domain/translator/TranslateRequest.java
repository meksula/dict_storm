package com.dictstorm.domain.translator;

import lombok.Data;

@Data
public class TranslateRequest {
    private SupportedLanguage from;
    private SupportedLanguage to;
    private String value;
}
