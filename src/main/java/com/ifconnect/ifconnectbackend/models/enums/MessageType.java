package com.ifconnect.ifconnectbackend.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    CHAT,
    JOIN,
    LEAVER
}
