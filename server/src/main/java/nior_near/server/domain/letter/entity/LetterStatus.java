package nior_near.server.domain.letter.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LetterStatus {

    READ("열람 완료"), UNREAD("미열람");

    private final String value;
}
