package com.noaats.eunchae.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseCategory {

    FOOD("식비"),
    TRANSPORT("교통"),
    ENTERTAINMENT("여가/오락"),
    HOUSING("주거/통신"),
    SHOPPING("쇼핑"),
    HEALTH("의료/건강"),
    EDUCATION("교육/자기개발"),
    OTHER("기타");

    private final String displayName;
}
