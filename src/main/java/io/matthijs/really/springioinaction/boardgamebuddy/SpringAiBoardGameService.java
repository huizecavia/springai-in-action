package io.matthijs.really.springioinaction.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient chatClient;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Value("classpath:/promptTemplates/questionPromptTemplate.st")
    Resource questionPromptTemplate;

    @Override
    public Answer askQuestion(Question question) {
        String prompt =
                "Answer this question about " + question.gameTitle() +
                        ": " + question.question();

        String answerText = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(questionPromptTemplate)
                        .param("game", question.gameTitle())
                        .param("question", question.question()))
                .call()
                .content();

        return new Answer(question.gameTitle(), answerText);
    }

}