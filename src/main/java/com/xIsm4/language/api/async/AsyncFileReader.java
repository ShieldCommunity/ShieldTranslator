package com.xIsm4.language.api.async;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AsyncFileReader {

    private Object LanguageAPI;
    AsynchronousFileChannel asyncFileChannel = AsynchronousFileChannel.open((Path) LanguageAPI, StandardOpenOption.WRITE);

    public AsynchronousFileChannel getAsyncFileChannel() {
        return asyncFileChannel;
    }

    public AsyncFileReader(Object languageAPI) throws IOException {
        LanguageAPI = languageAPI;
    }

}