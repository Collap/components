package io.collap.components.test.post;

import io.collap.components.Component;
import io.collap.components.Entry;

public class ShowPost implements Component {

    @Entry
    public String render(Post value) {
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
