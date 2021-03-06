package handlers;
import handlers.OutputHandler;
import logic.handler.model.Output;
import logic.handler.model.ServerOutput;

public class InputHandler {

	public static final int WAITING = 0;
	public static final int ROLEDETERMINED=1;
	public static final int LIBRARIAN = 2;
	public static final int USER = 3;
	public static final int LIBRARIANLOGIN = 4;
	public static final int USERLOGIN = 11;

	
	
	public static final int CREATEUSER = 5;
	public static final int CREATETITLE = 6;
	public static final int CREATEITEM = 7;
	
	
	public static final int DELETEUSER = 8;
	public static final int	DELETETITLE = 9;
	public static final int	DELETEITEM = 10;
	
	
	public static final int BORROW = 12;
    public static final int RENEW=13;
    public static final int RETURN=14;
    public static final int PAYFINE=15;
    public static final int ADDCOPY=16;

    

	

	
    
    OutputHandler outputHandler=new OutputHandler();


	public ServerOutput processInput(String input, int state) {
		 String output = "";
		 Output o = new Output("",0);
		 
		 ServerOutput oo = new ServerOutput(output,o.getState());
		 
	
	        if (state == WAITING) {
	        	o = outputHandler.determineRole();
	        	output = o.getOutput();
	        	state = o.getState();
		        oo.setOutput(output);
		        oo.setState(state);
		        		        
	         }else if (state == ROLEDETERMINED) {
	        	 
	        	 if (input.equalsIgnoreCase("librarian")) {	            
			            o = outputHandler.promptPassword();
			            
			            
			            output = o.getOutput();
			            state=LIBRARIAN;
			            oo.setState(state);
			            oo.setOutput(output);
			         
			            
		            }
	        	 else if (input.equalsIgnoreCase("user")) {
		            	
			            o = outputHandler.promptUserInfo();
			            output = o.getOutput();
			            
			            state=USER;
			            oo.setState(state);
			            
			            oo.setOutput(output);
		            }
	        	 
	        	 else{
	        		 	o = outputHandler.determineRole();
	 	        		output = "Role not recognized. "+o.getOutput();
	 	        		oo.setOutput(output);
	 	        		oo.setState(state);
		            }
	        	 
	        }else if(state==LIBRARIAN){
		        o=outputHandler.librarianLogin(input);
		        state=o.getState();
		        oo.setState(state);
		            
		        output = o.getOutput();
		        oo.setOutput(output);
		        
		        }else if(state==USER){
		        	o=outputHandler.userLogin(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
		           		            
		        }
	        
	        
		        else if(state == ADDCOPY ){
		        	if(input.equalsIgnoreCase("yes")) {
		        		
		        		o = outputHandler.promptTitleInfo();
			        	output = o.getOutput();
			        	
			        	state = o.getState();
		            	oo.setState(state);
		            	oo.setOutput(output);
		        	}
		        	else{
		        		output = "What would you like to do?"
								
								+ "\n Menu:"
								+ "\n Create User"
								+ "\n Create Title"
								+ "\n Create Item"
								+ "\n\n Delete User"
								+ "\n Delete Title"
								+ "\n Delete Item."
								+ "\n\n Borrow"
								+ "\n Renew"
								+ "\n Return"
								+ "\n Collect Fine"
								+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
		        		
		        		
		        	}
		         }
		         
	        
	        else if(state == LIBRARIANLOGIN) {
	         if (input.equalsIgnoreCase("create user") ){
	        	
		        	o = outputHandler.promptUserInfo();
		        	state = o.getState();
		        	output = o.getOutput();
		        	
	            	oo.setState(state);
	            	oo.setOutput(output);
		        }
	         
	      
	         else if (input.equalsIgnoreCase("create title")) {
	        		
		        	o = outputHandler.promptTitleInfo();
		        	output = o.getOutput();
		        	
		        	state = o.getState();

		        	
	            	oo.setState(state);
	            	oo.setOutput(output);
		        
		        }
	         
	         else if (input.equalsIgnoreCase("create item")) {
	        	 
	        		
		        	o = outputHandler.promptItemInfo();
		        	output = o.getOutput();
		        		    
		        	state = o.getState();

	            	oo.setState(state);
	            	oo.setOutput(output);
		        
		        }
	         
	         else if (input.equalsIgnoreCase("delete user") ){
		        	o = outputHandler.promptUserInfo();
		    
		        	output = "Please Input User Info: useremail";
		        	output = "TO DELETE -> " + output;
		        	
		        	state=DELETEUSER;
	            	oo.setState(state);
	            	oo.setOutput(output);
		        }
	         else if (input.equalsIgnoreCase("delete title") ){
		        	o = outputHandler.promptTitleInfo();
	
		        	output = "Please Input Title Info:'ISBN'";
					state=DELETETITLE;
					output = "TO DELETE -> " + output;
					
					oo.setState(state);
					oo.setOutput(output);
		        }
	         
	         else if (input.equalsIgnoreCase("delete item") ){
		        	o = outputHandler.promptItemInfo();
	
		        	output = "Please Input Item Info:'ISBN',copynumber";
					state=DELETEITEM;
					output = "TO DELETE -> " + output;
					
					oo.setState(state);
					oo.setOutput(output);
		        }
	         
	         else if (input.equalsIgnoreCase("borrow")) {
        		o=outputHandler.promptInputInfo();
        		output=o.getOutput();
        		
        		state = BORROW;
        		
        		oo.setOutput(output);
	            oo.setState(state);
            }
	         else if (input.equalsIgnoreCase("renew")) {

                o=outputHandler.promptInputInfo();
                output=o.getOutput();
                state=RENEW;
                oo.setOutput(output);
                oo.setState(state);

        	}else if (input.equalsIgnoreCase("return")) {
                o=outputHandler.promptInputInfo();
        		output=o.getOutput();

                
	            state=RETURN;
	            oo.setOutput(output);
	            oo.setState(state);
            }
        	else if (input.equalsIgnoreCase("collect fine")) {
        		
        		o = outputHandler.promptPayFine();
	        	state = o.getState();
	        	output = o.getOutput();
	        	
            	oo.setState(state);
            	oo.setOutput(output);
            }
        	else if(input.equalsIgnoreCase("log out")){
            	output = "Successfully Log Out!";
                state = WAITING;
                oo.setOutput(output);
	            oo.setState(state);
        	}else if(input.equalsIgnoreCase("main menu")){
        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
                state = LIBRARIANLOGIN;
                oo.setOutput(output);
	            oo.setState(state);
        	}else if (input.equalsIgnoreCase("monitor system")){
        		
        		
        		o = outputHandler.monitorSystem();
	        	state = o.getState();
	        	output = o.getOutput();
	        	
            	oo.setState(state);
            	oo.setOutput(output);
        		
        		
        		
        	}
        	
        	
        	else{
        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
                state = LIBRARIANLOGIN;
                oo.setOutput(output);
	            oo.setState(state);
	     	
	        	}
	        }
	        else if (state==USERLOGIN){
	        	if (input.equalsIgnoreCase("borrow")) {
	        		o=outputHandler.promptInputInfo();
	        		output=o.getOutput();
	        		
	        		state = BORROW;
	        		
	        		oo.setOutput(output);
		            oo.setState(state);
	            }
	        	else if (input.equalsIgnoreCase("renew")) {

	                o=outputHandler.promptInputInfo();
	                output=o.getOutput();
	                state=RENEW;
	                oo.setOutput(output);
	                oo.setState(state);

	        	}else if (input.equalsIgnoreCase("return")) {
	                o=outputHandler.promptInputInfo();
		            state=RETURN;
		            oo.setOutput(output);
		            oo.setState(state);
	            }
	        	else if (input.equalsIgnoreCase("collect fine")) {
	        		
	        		o = outputHandler.promptPayFine();
		        	state = o.getState();
		        	output = o.getOutput();
		        	
	            	oo.setState(state);
	            	oo.setOutput(output);
	            }
	        	
	        	
	        }
	        
	        
	        else if(state == CREATEUSER) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        	o=outputHandler.createUser(input);
        		output=o.getOutput();
        		
        		
        		
        		
        		state=o.getState();
        		oo.setOutput(output);
	            oo.setState(state);
	            
	            
	            
	        	}
	        }
	        
	        else if(state==DELETEUSER){
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        	
	        		o=outputHandler.deleteUser(input);
	        		output=o.getOutput();
	        		 
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	}
	        
	        else if(state==CREATETITLE){
	        	
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        	
	        		o=outputHandler.createTitle(input);
	        		output=o.getOutput();
	        		 
	        		
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	}

	        else if(state==DELETETITLE){	  
	        	if(input.equalsIgnoreCase("log out")){
            	output = "Successfully Log Out!";
                state = WAITING;
                oo.setOutput(output);
	            oo.setState(state);
        	}else if(input.equalsIgnoreCase("main menu")){
        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
                state = LIBRARIANLOGIN;
                
                
                oo.setOutput(output);
	            oo.setState(state);
        	}else{      	
	        		o=outputHandler.deleteTitle(input);
	        		output=o.getOutput();
	        		 
	        		
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
        		}
	        }
	        else if(state==CREATEITEM){
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{

	        		o=outputHandler.createItem(input);
	        		output=o.getOutput();
	        		 
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        }
	        
	        else if(state==DELETEITEM){
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        	
	        		o=outputHandler.deleteItem(input);
	        		output=o.getOutput();
	        		
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
		            
	        		}
	        	}
	        

	        
	        
	        
	        else if(state==BORROW){
	        	
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	else{
	        	
	        	
	        		o=outputHandler.borrow(input);
	        		output=o.getOutput();
	        		
	        		
	        		state=o.getState();
	        		
	            //    state = LIBRARIANLOGIN;

	        		oo.setOutput(output);
		            oo.setState(state);
		            
		            
	        	}
	        }  else if(state==RENEW){

	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	else{
	        	
	        		o=outputHandler.renew(input);
	        		output=o.getOutput();
	        		state=o.getState();
//	                state = LIBRARIANLOGIN;

	        		
	        		oo.setOutput(output);
		            oo.setState(state);
		            
	        	}
	        }
	        else if(state==RETURN){
	        	
	        	
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	else{
	        	
	        		o=outputHandler.returnBook(input);
	        		output=o.getOutput();
	        		state=o.getState();
	                //state = LIBRARIANLOGIN;

	        		
	        		oo.setOutput(output);
		            oo.setState(state);
		            
		            
		            
	        	}
	        }else if(state==PAYFINE){
	        	
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = "What would you like to do?"
						
						+ "\n Menu:"
						+ "\n Create User"
						+ "\n Create Title"
						+ "\n Create Item"
						+ "\n\n Delete User"
						+ "\n Delete Title"
						+ "\n Delete Item."
						+ "\n\n Borrow"
						+ "\n Renew"
						+ "\n Return"
						+ "\n Collect Fine"
						+ "\n Monitor System";
	                state = LIBRARIANLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}
	        	else{
	        		 o = outputHandler.payFine(input);
	        		output=o.getOutput();
	        		state=o.getState();
	                //state = LIBRARIANLOGIN;

	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
		           		            
	        }
	        
	        
	        
	        
	        
	        
	        return oo;
	}
	 



	
	   
}

