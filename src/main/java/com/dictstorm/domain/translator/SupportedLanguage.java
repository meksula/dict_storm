package com.dictstorm.domain.translator;

public enum SupportedLanguage {
    ENGLISH {
        @Override
        public String getValue() {
            return "en";
        }
    },
    POLISH {
        @Override
        public String getValue() {
            return "pl";
        }
    };

    public abstract String getValue();
}
