package io.matthijs.really.springioinaction.boardgamebuddy;

public record GameTitle(String title) {

    public String getNormalizedTitle() {
        return title.toLowerCase().replace(" ", "_");
    }

}
