package io.matthijs.really.springioinaction.boardgamebuddy;

import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class DocLoader {

    @Value("file://${HOME}/documents/my-document.txt")
    private Resource documentResource;

//    public void loadDocument(VectorStore vectorStore) {
//        DocumentReader reader = new TextReader(documentResource);
//        TextSplitter splitter = TokenTextSplitter.builder().build();
//        vectorStore.accept(splitter.apply(reader.get()));
//    }
}
