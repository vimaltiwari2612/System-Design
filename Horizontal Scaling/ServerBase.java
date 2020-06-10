//class contains server
public class ServerBase{
    
    //servers list which are active
    private static Map<Server,Integer> activeServersVsBusyCount = new Map<Server,Integer>();
    //modes avaible
    Enum Mode {NEXT,BEST,FIRST}
    //current mode of serverbase
    private static String ALLOCATION_MODE = null;
    
    //private constructor
    private ServerBase(){
       //get all servers
       this.getAllServers();
       //set ALLOCATION_MODE depending upon logic
       ALLOCATION_MODE = Mode.BEST.name(); // putting round robin for this instance
    }
    
    private void getAllServers(){
        //we need to have the list of servers which are present in the system
        //Server Registry can be a data base call to get the servers 
        /*
            ServerId   ServerState
               1        Active
               2        InActive
               3        Active
        */
        //the processing of converting db values to list can be handled here
        List<Server> activeServers = ServerRegistry.getAllServers();
        for(Server server : activeServers){
            //right now, no work, so set busy count to 0
            activeServersVsBusyCount.put(server,0);
        }
    }
    
    public static long sendRequest throw ServerNotFoundException(Request request){
        //find a free server or any server depending on ALLOCATION_MODE
        Server server = (ServerBase.ALLOCATION_MODE.equals(Mode.NEXT))?this.findNextActiveServer():findAnyActiveServer();
        // if no server found
        if(server  == null){
            //notify client
            throw new ServerNotFoundException("All Servers are busy. Please try later");
        }
        //send request to the active server
        return server.sendRequest(request);
        
    }
    
    
    //checks whether Next server is active and able to process the request
    public Boolean findNextActiveServer(){
        //take all servers
        for(Server server : activeServersVsBusyCount.keySet()){
            //if a server is live
            if(server.isLive 
                && 
                //and it's not fully occupied
             activeServersVsBusyCount.get(server) < server.MAX_REQUEST_LIMIT){
                 //return the instance;
                return server;
             }
        }
          
        return null;
    }
    
    
    //checks whether any server is active and has minimum busy count
    public Boolean findBestActiveServer(){
        int min = Integer.MAX_VALUE;
        Server s = null;
        //take all servers
        for(Server server : activeServersVsBusyCount.keySet()){
            //if a server is live
            if(server.isLive 
                && 
                //and it's not fully occupied
             activeServersVsBusyCount.get(server) < server.MAX_REQUEST_LIMIT){
                //check if it is less busy
                if(min > activeServersVsBusyCount.get(server)){
                    s = server;
					min = activeServersVsBusyCount.get(server);
                } 
             }
        }
          
        return s;
    }

}