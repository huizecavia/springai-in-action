package io.matthijs.really.springioinaction.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopCitiesController {

    @Value("classpath:/top-cities-prompt.st")
    Resource topSongPromptTemplate;

    private final ChatClient chatClient;

    public TopCitiesController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "/topCities", produces = "application/json")
    public String topCities(@RequestParam("country") String country) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(topSongPromptTemplate)
                        .param("country", country))
                .call()
                .content();
    }

}