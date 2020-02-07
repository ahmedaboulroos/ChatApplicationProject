package eg.gov.iti.jets.models.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static RMIConnection instance;
    private Registry registry;

    private RMIConnection() {
        initConnection();
    }

    public boolean initConnection() {
        try {
            this.registry = LocateRegistry.createRegistry(1234);
            // TODO:  rebind services here
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean stopConnection() {
        try {
            for (String service : this.registry.list()) {
                this.registry.unbind(service);
            }
            return true;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static synchronized RMIConnection getInstance() {
        if (instance == null)
            instance = new RMIConnection();
        return instance;
    }
}
