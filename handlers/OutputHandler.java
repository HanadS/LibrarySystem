package handlers;



import java.util.Date;

import logic.handler.model.Output;
import server.logic.tables.FeeTable;
import server.logic.tables.ItemTable;
import server.logic.tables.LoanTable;
import server.logic.tables.TitleTable;
import server.logic.tables.UserTable;




public class OutputHandler {
	
	
	public static final int WAITING = 0;
	public static final int ROLEDETERMINED = 1;
	public static final int LIBRARIAN = 2;
	public static final int USER = 3;
	public static final int LIBRARIANLOGIN = 4;
	public static final int USERLOGIN = 11;

	
	public static final int CREATEUSER = 5;
	public static final int CREATETITLE = 6;
	public static final int CREATEITEM = 7;
	public static final int  DELETEUSER = 8;
	public static final int  DELETETITLE = 9;
	public static final int  DELETEITEM = 10;
	
	public static final int BORROW = 12;
    public static final int RENEW=13;
    public static final int RETURN=14;
    public static final int PAYFINE=15;

    public static final int ADDCOPY=16;

    
    
	

	
	public Output determineRole() {
		
		Output output=new Output("",0);
		output.setOutput("Are you a librarian or a User?");
		output.setState(ROLEDETERMINED);
		
		return output;
	}
	
		
	public Output promptPassword() {
			
			Output output=new Output("",0);
			output.setOutput("Please Input The Password.");
			return output;
		}
	
	public Output promptUserInfo() {
		
		Output output=new Output("",0);
		output.setOutput("Please Input User Info: username,password:");
		output.setState(CREATEUSER);
		return output;
	}
	
	public Output promptTitleInfo() {
	
		Output output=new Output("",0);
		output.setOutput("Please Input Title Info:'ISBN,title'");
		output.setState(CREATETITLE);
		return output;
	
	}

	public Output promptItemInfo() {

		Output output=new Output("",0);
		output.setOutput("Please Input Item Info:'ISBN'");
		output.setState(CREATEITEM);
		return output;

	}

	public Output promptInputInfo() {

		Output output=new Output("",0);
		output.setOutput("Please Input Info:'useremail,ISBN,copynumber'");
		return output;

	}
	
	public Output promptPayFine() {

		Output output=new Output("",0);
		output.setOutput("Please Input User Info:'useremail'");
		output.setState(PAYFINE);
		return output;

	}
	
	
	public Output createUser(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
	    strArray = input.split(",");
	    
        Object result="";

	    
	    boolean email=strArray[0].contains("@");
	    boolean dot  = strArray[0].contains(".");
	    
		  
	    
	    if(strArray.length!=2 || email!=true || dot != true ){
        	output.setOutput("Your input should in this format:'username,password'");
        	output.setState(CREATEUSER);
        }else{
	    
		result=UserTable.getInstance().createuser(strArray[0], strArray[1]);
		    
		if(result.equals(true)){
		   output.setOutput("Success!");
		 }else{
     		output.setOutput("The User Already Exists!");
     	}
		 output.setState(LIBRARIANLOGIN);
        }
		return output;
	}

	public Output deleteUser(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        int userid=UserTable.getInstance().lookup(strArray[0]);
        boolean email=strArray[0].contains("@");

        Object result="";
       
        if(strArray.length!=1 || email!=true){
        		output.setOutput("Your input should in this format:'useremail'");
        		output.setState(DELETEUSER);
        }else if(userid==-1){
        	output.setOutput("The User Does Not Exist!");
        	output.setState(DELETEUSER);
        }
        
        else{
        		result=UserTable.getInstance().delete(userid);
        		if(result.equals("success")){
        			output.setOutput("Success!");
        		}else{
        			output.setOutput(result+"!");
        		}
        		output.setState(LIBRARIANLOGIN);
        		
		
        }
        
        return output;
	}

	
		
	public Output createTitle(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean number=isInteger(strArray[0]);
        Object result="";
        if(strArray.length!=2 || number!=true){
        	output.setOutput("Your input should in this format:'ISBN,title',ISBN should be a 13-digit number");
        	output.setState(CREATETITLE);
        }else{
        	result=TitleTable.getInstance().createtitle(strArray[0], strArray[1]);
        	if(result.equals(true)){
        		output.setOutput("Success!");
        	}else{
        		output.setOutput("The Title Already Exists!");
        	}
        	
        	output.setState(LIBRARIANLOGIN);
        }
        
        
		return output;
	}
	
	
	public Output monitorSystem() {
		Output output=new Output("",0);
		
		String monitorSystem = "";
		
		
		monitorSystem += "User \n";
		monitorSystem += UserTable.getInstance().print();
		
		monitorSystem += "\nTitle \n";
		monitorSystem += TitleTable.getInstance().print();
		
		monitorSystem += "\nItems \n";
		monitorSystem += ItemTable.getInstance().print();
		
		monitorSystem += "\nFee \n";
		monitorSystem += FeeTable.getInstance().print();
		
		monitorSystem += "\nLoan \n";
		monitorSystem += LoanTable.getInstance().print();
		
		
		
		
        output.setOutput(monitorSystem);
        
        output.setState(LIBRARIANLOGIN);

        
		return output;
	}
	

	public Output deleteTitle(String input) {

		Output output=new Output("",0);
		String[] strArray = null;   
		strArray = input.split(",");
		boolean number=isInteger(strArray[0]);
		Object result="";
		if(strArray.length!=1 || number!=true){
			output.setOutput("Your input should in this format:'ISBN',ISBN should be a 13-digit number");
			output.setState(DELETETITLE);
		}else{

       result=TitleTable.getInstance().delete(strArray[0]);

       if(result.equals("success")){
        	output.setOutput("Success!");
        }else{
    		output.setOutput(result+"!");
    	}
            output.setState(LIBRARIANLOGIN);
		 }

			return output;

	}

	public Output createItem(String input) {

		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean number=isInteger(strArray[0]);
        Object result="";
        if(strArray.length!=1 || number!=true){
        	output.setOutput("Your input should in this format:'ISBN',ISBN should be a 13-digit number");
        	output.setState(CREATEITEM);

        }else{

        	result=ItemTable.getInstance().createitem(strArray[0]);
        	if(result.equals(true)){

        		output.setOutput("Success!");
            	output.setState(LIBRARIANLOGIN);
        	}else{
        		output.setOutput("The title does not exist. Would you like to add it? Yes/No?");
            	output.setState(ADDCOPY);

        		
        	}
        }
		return output;
	}
	
	public Output deleteItem(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean number=isInteger(strArray[0]);
        Object result="";
        if(strArray.length!=2 || number!=true){
        	output.setOutput("Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
        	output.setState(DELETEITEM);
        }else{
        	boolean copynumber=isNumber(strArray[1]);
        	if(copynumber!=true){
        		output.setOutput("Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
            	output.setState(DELETEITEM);
        	}else{
        		result=ItemTable.getInstance().delete(strArray[0], strArray[1]);
            	if(result.equals("success")){
            		output.setOutput("Success!");
            	}else{
            		output.setOutput(result+"!");
            	}
            	output.setState(LIBRARIANLOGIN);
        	}
        }
		return output;
	}
	
	
public Output librarianLogin(String input) {
	Output output = new Output("",0);
	if(input.equalsIgnoreCase("admin")){
			output.setState(LIBRARIANLOGIN);
			output.setOutput("What would you like to do?"
					
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
					+ "\n Monitor System");
	}
	
	else{
			output.setState(LIBRARIAN);
			output.setOutput("Wrong password");
		}

	return output;
}


public Output borrow(String input) {
	Output output=new Output("",0);
	String[] strArray = null;   
    strArray = input.split(",");
    boolean email=strArray[0].contains("@");
    int userid=UserTable.getInstance().lookup(strArray[0]);
    
    
    Object result="";
    if(strArray.length!=3 || email!=true){
    	output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
    	output.setState(BORROW);
    }else if(userid==-1){
    	output.setOutput("The User Does Not Exist!");
    	output.setState(BORROW);
    }else{
    	boolean ISBN=isInteger(strArray[1]);
    	boolean copynumber=isNumber(strArray[2]);
    	if(ISBN!=true || copynumber!=true){
    		output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
        	output.setState(BORROW);
    	}else{
    		result=LoanTable.getInstance().createloan(userid, strArray[1], strArray[2], new Date());
    		if(result.equals("success")){
        		output.setOutput("Success!");
        	}else{
        		output.setOutput(result+"!");
        	}
    	}
    	output.setState(LIBRARIANLOGIN);
    }
    
    
	return output;
		
}

public Output renew(String input) {
	Output output=new Output("",0);
	String[] strArray = null;   
    strArray = input.split(",");
    boolean email=strArray[0].contains("@");
    int userid=UserTable.getInstance().lookup(strArray[0]);
    Object result="";
    if(strArray.length!=3 || email!=true){
    	output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
    	output.setState(RENEW);
    }else if(userid==-1){
    	output.setOutput("The User Does Not Exist!");
    	output.setState(RENEW);
    }else{
    	boolean ISBN=isInteger(strArray[1]);
    	boolean copynumber=isNumber(strArray[2]);
    	if(ISBN!=true || copynumber!=true){
    		output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
        	output.setState(RENEW);
    	}else{
    		result=LoanTable.getInstance().renewal(userid, strArray[1], strArray[2], new Date());
    		if(result.equals("success")){
        		output.setOutput("Success!");
        	}else if (result.equals("privilege revoked")){
        		
        		
        		String  resultString = "privilege revoked";
        		 resultString += "\nIn order to collect the fine type collect fine.";
        		
        		output.setOutput(resultString);
        	}else{
        		output.setOutput(result+"!");
        	}
    	}
    	output.setState(LIBRARIANLOGIN);
    }
	return output;
}


public Output returnBook(String input) {
	
	
	Output output=new Output("",0);
	String[] strArray = null;   
    strArray = input.split(",");
    boolean email=strArray[0].contains("@");
    int userid=UserTable.getInstance().lookup(strArray[0]);
    Object result="";
    if(strArray.length!=3 || email!=true){
    	output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
    	output.setState(RETURN);
    }else if(userid==-1){
    	output.setOutput("The User Does Not Exist!");
    	output.setState(RETURN);
    }else{
    	boolean ISBN=isInteger(strArray[1]);
    	boolean copynumber=isNumber(strArray[2]);
    	if(ISBN!=true || copynumber!=true){
    		output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
        	output.setState(RETURN);
    	}else{
    		result=LoanTable.getInstance().returnItem(userid, strArray[1], strArray[2], new Date());
    		if(result.equals("success")){
        		output.setOutput("Success!");
        	}else{
        		output.setOutput(result+"!");
        	}
    	}
    	output.setState(LIBRARIANLOGIN);
    }
	return output;
    
   
}
	





public Output userLogin(String input) {
	Output output=new Output("",0);
	String[] strArray = null;   
    strArray = input.split(",");
    boolean email=strArray[0].contains("@");
    int result=0;
    if(strArray.length!=2 || email!=true){
    	output.setOutput("Your input should in this format:'username,password'");
    	output.setState(USER);
    }else{
    	result=UserTable.getInstance().checkUser(strArray[0], strArray[1]);
    	if(result==0){
    		output.setOutput("What can I do for you?"
    					+ "Menu:"
    					+ "Borrow"
    					+ "Renew"
    					+ "Return"
    					+ "Pay Fine.");
        	output.setState(USERLOGIN);
    	}else if(result==1){
    		output.setOutput("Wrong Password!Please Input Username and Password:'username,password'");
        	output.setState(USER);
    	}else{
    		output.setOutput("The User Does Not Exist!Please Enter The Username and Password:'username,password'");
        	output.setState(USER);
    	}
    }
    
    
	return output;
    }
public Output payFine(String input) {
	Output output=new Output("",0);
	String[] strArray = null;   
    strArray = input.split(",");
    boolean email=strArray[0].contains("@");
    int userid=UserTable.getInstance().lookup(strArray[0]);
    Object result="";
    if(strArray.length!=1 || email!=true){
    	output.setOutput("Your input should in this format:'useremail'");
    	output.setState(PAYFINE);
    }else if(userid==-1){
    	output.setOutput("The User Does Not Exist!");
    	output.setState(PAYFINE);
    }else{
    	result=FeeTable.getInstance().payfine(userid);	
    	if(result.equals("success")){
    		output.setOutput("Success!");
    		}else{
        		output.setOutput(result+"!");
        	}
    		output.setState(LIBRARIANLOGIN);
    	}
    	
	return output;
	
}



public static boolean isInteger(String value) {
	char[] ch = value.toCharArray();
	boolean isNumber=true;
	if(value.length()==13){
		for (int i = 0; i < ch.length; i++) {
			isNumber = Character.isDigit(ch[i]);
		}
	}else{
		isNumber=false;
	}
	return isNumber;
	 }

public boolean isNumber(String value) {
	char[] ch = value.toCharArray();
	boolean isNumber=true;
		for (int i = 0; i < ch.length; i++) {
			isNumber = Character.isDigit(ch[i]);
		}
	return isNumber;
}


	
}
