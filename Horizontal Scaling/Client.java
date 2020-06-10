public class Client{
  
    public void sendRequest(Request request){
        try{
           
			//send request
			long processId = ServerBase.sendRequest(request);
			//make the wait time, a background process, depedning on the complexity and responsiveness of system
			System.out.print('Waiting for response from Server');
            }
            else{
               System.out.print('Server is unreachable.');
            }
        }catch(Exception e){
            System.out.print('An Error occured while connecting to Server.'+e.getmessage());
        }
    }
}