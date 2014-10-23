package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;
import in.parapengu.glacier.handler.utils.ChatFormatter;

import java.io.IOException;

public class PacketPlayInChatMessage extends IncomingPacket {

    private String input;
    private String chatMessage;

    public PacketPlayInChatMessage() {
        super(0x02, ProtocolState.PLAY);
    }

    public String getInput() {
        return input;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.input = input.readString();
        this.chatMessage = ChatFormatter.parse(this.input);
    }

}
