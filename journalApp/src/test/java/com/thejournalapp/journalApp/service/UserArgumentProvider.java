package com.thejournalapp.journalApp.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.thejournalapp.journalApp.entity.User;

public class UserArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of(new User("abc", "abc")),
            Arguments.of(new User("cde", "cde")),
            Arguments.of(new User("def", "def"))
        );
    }
}
