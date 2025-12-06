package io.matthijs.really.springioinaction.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient chatClient;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    private static final String questionPromptTemplate = """
      Answer this question about {game}: {question}
      """;


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