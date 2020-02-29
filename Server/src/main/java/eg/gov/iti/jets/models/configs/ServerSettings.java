package eg.gov.iti.jets.models.configs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "server_settings")
@XmlType
public class ServerSettings {
    private String serverUsername;
    private String serverPassword;


    public ServerSettings() {

    }

    public ServerSettings(String serverUsername, String serverPassword) {
        this.serverUsername = serverUsername;
        this.serverPassword = serverPassword;
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

    @Override
    public String toString() {
        return "ServerSettings{" +
                "serverUsername='" + serverUsername + '\'' +
                ", serverPassword='" + serverPassword + '\'' +
                '}';
    }
}
