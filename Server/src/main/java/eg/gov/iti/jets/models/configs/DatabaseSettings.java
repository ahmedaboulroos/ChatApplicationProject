package eg.gov.iti.jets.models.configs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "database_settings")
@XmlType
public class DatabaseSettings {
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String encryptionType;
    private String checksumType;

    public DatabaseSettings() {
    }

    public DatabaseSettings(String databaseUrl, String databaseUsername, String databasePassword, String encryptionType, String checksumType) {
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.encryptionType = encryptionType;
        this.checksumType = checksumType;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    @XmlElement(name = "database_url")
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
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

    public String getEncryptionType() {
        return encryptionType;
    }

    @XmlElement(name = "encryption_type")
    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getChecksumType() {
        return checksumType;
    }

    @XmlElement(name = "checksum_type")
    public void setChecksumType(String checksumType) {
        this.checksumType = checksumType;
    }

    @Override
    public String toString() {
        return "DatabaseSettings{" +
                "databaseUrl='" + databaseUrl + '\'' +
                ", databaseUsername='" + databaseUsername + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", encryptionType='" + encryptionType + '\'' +
                ", checksumType='" + checksumType + '\'' +
                '}';
    }
}
