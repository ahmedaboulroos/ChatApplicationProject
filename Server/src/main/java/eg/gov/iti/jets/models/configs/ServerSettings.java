package eg.gov.iti.jets.models.configs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "server_settings")
@XmlType
public class ServerSettings {
    private String serverUsername;
    private String serverPassword;
    private String databaseUrl;
    private int databasePort;
    private String databaseUsername;
    private String databasePassword;

    public ServerSettings() {

    }

    public ServerSettings(String serverUsername, String serverPassword, String databaseUrl, int databasePort, String databaseUsername, String databasePassword) {
        this.serverUsername = serverUsername;
        this.serverPassword = serverPassword;
        this.databaseUrl = databaseUrl;
        this.databasePort = databasePort;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    public static void main(String[] args) {

    }

    @Override
    public String toString() {
        return "ServerSettings{" +
                "serverUsername='" + serverUsername + '\'' +
                ", serverPassword='" + serverPassword + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", databasePort=" + databasePort +
                ", databaseUsername='" + databaseUsername + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                '}';
    }

    public String getServerUsername() {
        return serverUsername;
    }

    @XmlElement(name = "server_username")
    public void setServerUsername(String serverUsername) {
        this.serverUsername = serverUsername;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    @XmlElement(name = "server_password")
    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    @XmlElement(name = "database_url")
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    @XmlElement(name = "database_port")
    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    @XmlElement(name = "database_username")
    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    @XmlElement(name = "database_password")
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
}
