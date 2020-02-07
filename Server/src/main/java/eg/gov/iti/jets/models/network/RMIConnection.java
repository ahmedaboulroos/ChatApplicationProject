package eg.gov.iti.jets.models.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static RMIConnection instance;
    private Registry registry;

    private RMIConnection() { }

    public boolean initConnection() {
//        try {
//            this.registry = LocateRegistry.createRegistry(1995);
//            // TODO:  rebind services here
//
//            System.out.println(">> RMI-Registry Connection Established...");
//            return true;
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public void stopConnection() {
//        try {
//            for (String service : this.registry.list()) {
//                this.registry.unbind(service);
//            }
//        } catch (RemoteException | NotBoundException e) {
//            e.printStackTrace();
//        }
    }

    public static synchronized RMIConnection getInstance() {
        if (instance == null)
            instance = new RMIConnection();
        return instance;
    }
}
