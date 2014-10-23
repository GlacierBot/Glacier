package in.parapengu.glacier.handler.network.protocol.exception;

import java.io.IOException;

public class PacketException extends IOException {

    private static final long serialVersionUID = 2861527376715073690L;

    public PacketException(String message) {
        super(message);
    }

}
