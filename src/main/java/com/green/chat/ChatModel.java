package com.green.chat;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class ChatModel {

    @NonNull
    private String id;
}
