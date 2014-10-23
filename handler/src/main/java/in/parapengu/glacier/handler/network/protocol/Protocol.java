package in.parapengu.glacier.handler.network.protocol;

import in.parapengu.glacier.handler.network.connection.Connection;

public abstract class Protocol {

    private Connection connection;

    protected Protocol() {}

    protected Protocol(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract void handle();

}
