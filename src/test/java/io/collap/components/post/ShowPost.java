package io.collap.components.post;

import io.collap.components.Component;

public class ShowPost implements Component<Post, String> {

    @Override
    public String apply(Post value) {
        return  "<html>" +
                    "<head>" +
                        "<title>" + value.getTitle() + "</title>" +
                    "</head>" +
                    "<body>" +
                        "<h1>" + value.getTitle() + "</h1>" +
                        "<div>" + value.getContent() + "</div>" +
                        "<i>ID: " + value.getId() + "</i>" +
                    "</body>" +
                "</html>";
    }

}
