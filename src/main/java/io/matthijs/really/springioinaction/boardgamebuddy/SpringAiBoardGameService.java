package io.matthijs.really.springioinaction.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient chatClient;
    private final GameRulesService gameRulesService;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder, GameRulesService gameRulesService) {
        this.chatClient = chatClientBuilder.build();
        this.gameRulesService = gameRulesService;
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    @Override
    public Answer askQuestion(Question question) {
        String prompt =
                "Answer this question about " + question.gameTitle() +
                        ": " + question.question();

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptTemplate)
                        .param("game", question.gameTitle())
                        .param("question", question.question())
                        .param("rules", gameRulesService.getRulesFor(question.gameTitle())))
                .call()
                .entity(Answer.class);

    }

}