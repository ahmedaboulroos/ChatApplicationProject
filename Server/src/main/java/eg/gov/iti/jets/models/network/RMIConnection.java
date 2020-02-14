package eg.gov.iti.jets.models.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static RMIConnection instance;
    private Registry registry;

    private RMIConnection() {
    }

    public boolean startConnection(int port) {
        try {
            this.registry = LocateRegistry.createRegistry(port);
            // TODO:  rebind services here
            System.out.println(">> RMI-Registry Connection Established...");
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stopConnection() {
        if (registry != null) {
            try {
                for (String service : this.registry.list()) {
                    this.registry.unbind(service);
                }
                System.out.println(">> RMI-Registry Connection Terminated...");
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">> RMI-Registry Connection Already Closed");
        }
    }

    public static synchronized RMIConnection getInstance() {
        if (instance == null)
            instance = new RMIConnection();
        return instance;
    }
}
