package se.iths;

import java.net.Socket;

public class AuthorHandler implements UrlHandler {
    private Socket socket;

    public AuthorHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void handlerUrl() {

    }

}
