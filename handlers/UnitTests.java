package handlers;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;

import server.logic.model.*;
import server.logic.tables.*;
import utilities.Config;


public class UnitTests {

	
	
	InputHandler handler =new InputHandler();
	
	
	
	//Unit Tests
	@Test
	public void determineRole() {
	
		assertEquals("Ask if librarian or user.",handler.processInput(" ",InputHandler.WAITING).getOutput(), "Are you a librarian or a User?" );
		assertEquals("Check if librarian.",handler.processInput("librarian",InputHandler.ROLEDETERMINED).getState(), InputHandler.LIBRARIAN  );
		assertEquals("Check if User.",handler.processInput("user",InputHandler.ROLEDETERMINED).getState(), InputHandler.USER  );
		assertEquals("Handle Incorrect Input.",handler.processInput("teacher",InputHandler.ROLEDETERMINED).getOutput(), "Role not recognized. Are you a librarian or a User?");
	
	
	}
	
	@Test
	public void librarianInitializationTests() {
		
				assertEquals("Prompt for password.",handler.processInput("librarian",InputHandler.ROLEDETERMINED).getOutput(), "Please Input The Password." );
				assertEquals("Librarian login",handler.processInput("admin",InputHandler.LIBRARIAN).getState(), InputHandler.LIBRARIANLOGIN );
				assertEquals("Check if password is incorrect.",handler.processInput("sdcmslkd",InputHandler.LIBRARIAN).getState(), InputHandler.LIBRARIAN );
				assertEquals("Display Librarian Terminal",handler.processInput("admin",InputHandler.LIBRARIAN).getOutput(),"What would you like to do?"
						
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
				
				
				
				assertEquals("Prompting librarian for username and password.",handler.processInput("create user",InputHandler.LIBRARIANLOGIN).getOutput(),"Please Input User Info: username,password:");
				assertEquals("Prompting librarian for Title.",handler.processInput("create title",InputHandler.LIBRARIANLOGIN).getOutput(),"Please Input Title Info:'ISBN,title'");
				assertEquals("Prompting librarian for Item.",handler.processInput("create item",InputHandler.LIBRARIANLOGIN).getOutput(),"Please Input Item Info:'ISBN'");
				
				assertEquals("Prompting librarian for username and password to delete",handler.processInput("delete user",InputHandler.LIBRARIANLOGIN).getOutput(),"TO DELETE -> Please Input User Info: useremail");
				assertEquals("Prompting librarian for Title to delete",handler.processInput("delete title",InputHandler.LIBRARIANLOGIN).getOutput(),"TO DELETE -> Please Input Title Info:'ISBN'");
				assertEquals("Prompting librarian for Item to delete",handler.processInput("delete item",InputHandler.LIBRARIANLOGIN).getOutput(),"TO DELETE -> Please Input Item Info:'ISBN',copynumber");		
	}
	@Test
	public void UserCreationTests() {

			User testUser = new User (0,"jim@carleton.ca","jim",true);
			assertEquals("get UserId", testUser.getUserid(),0);
			assertEquals("get username", testUser.getUsername(),"jim@carleton.ca");
			assertEquals("get password", testUser.getPassword(),"jim");
			
			assertTrue( "Check if UserTable Initialized correctly", UserTable.getInstance().getUserTable().get(0).sameUser(testUser));			
			UserTable.getInstance();

			assertEquals("Add User to User Table.",handler.processInput("sun@carleton.ca,sun",InputHandler.CREATEUSER).getOutput(),"Success!");		
			assertEquals("Check if User has correct password or username ",handler.processInput("sun^carleton.ca,sun,hey",InputHandler.CREATEUSER).getOutput(),"Your input should in this format:'username,password'");	
			assertEquals("Check if User already exists",handler.processInput("jim@carleton.ca,jim",InputHandler.CREATEUSER).getOutput(),"The User Already Exists!");	

			
			//System.out.println(handler.processInput("sun@carleton.ca",InputHandler.DELETEUSER).getOutput());
			
			assertEquals("Delete User from User Table.",handler.processInput("sun@carleton.ca",InputHandler.DELETEUSER).getOutput(),"Success!");		
			assertEquals("Delete User with incorrect username.",handler.processInput("jim^carleton.ca",InputHandler.DELETEUSER).getOutput(),"Your input should in this format:'useremail'");		
			assertEquals("Delete User that does not exist",handler.processInput("timmy@carleton.ca",InputHandler.DELETEUSER).getOutput(),"The User Does Not Exist!");		

		}
	
	
	@Test
	public void TitleCreationTests() {

		Title testTitle = new Title("9781442668584","TestBook");
		assertEquals("get ISBN", testTitle.getISBN(),"9781442668584");
		assertEquals("get Title", testTitle.getBooktitle(),"TestBook");
		
		assertTrue( "Initializing TitleTable Class", TitleTable.getInstance().getTitleTable().get(0).sameTitle(testTitle));	
		
		TitleTable.getInstance();
		
		
		assertEquals("Add incorrect title to Title Table",handler.processInput("3242342423423424,incorrectBook",InputHandler.CREATETITLE).getOutput(),"Your input should in this format:'ISBN,title',ISBN should be a 13-digit number");		
		assertEquals("Add title that already exists to Title Table",handler.processInput("9781442668584,TestBook",InputHandler.CREATETITLE).getOutput(),"The Title Already Exists!");		

		assertEquals("Delete Title from User Table.",handler.processInput("9781442668584",InputHandler.DELETETITLE).getOutput(),"Success!");		
		assertEquals("Delete Title with incorrect ISBN.",handler.processInput("32423424234234243242",InputHandler.DELETETITLE).getOutput(),"Your input should in this format:'ISBN',ISBN should be a 13-digit number");
		assertEquals("Delete Title that does not exist",handler.processInput("1111111111111",InputHandler.DELETETITLE).getOutput(),"The Title Does Not Exist!");
		
	}
	
	@Test
	public void ItemCreationTests() {
		
		Item testItem = new Item(0,"9781442668584","1",false);
		handler.processInput("9781442668584,TestBook",InputHandler.CREATETITLE);

		
		assertEquals("get id", testItem.getItemid(),0);
		assertEquals("get ISBN", testItem.getISBN(),"9781442668584");
		assertEquals("get Title", testItem.getCopynumber(),"1");
		assertEquals("get Reservation", testItem.getReservation(),false);
		
		
		assertTrue( "Initializing ItemTable Class", ItemTable.getInstance().getItemTable().get(0).sameItem(testItem));
		
		
		
		assertEquals("Add item to Item Table.",handler.processInput("9781442668584",InputHandler.CREATEITEM).getOutput(),"Success!");	
		assertEquals("Add incorrect item to Item Table",handler.processInput("9781455344556368584",InputHandler.CREATEITEM).getOutput(),"Your input should in this format:'ISBN',ISBN should be a 13-digit number");
		assertEquals("Add item that already exists to Item Table",handler.processInput("9781452668584",InputHandler.CREATEITEM).getOutput(),"The title does not exist. Would you like to add it? Yes/No?");		

		
		assertEquals("Delete item with incorrect ISBN.",handler.processInput("32423424234234243242,1",InputHandler.DELETEITEM).getOutput(),"Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
		assertEquals("Delete item with incorrect copynumber.",handler.processInput("9781442668584,asd",InputHandler.DELETEITEM).getOutput(),"Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
		assertEquals("Delete item that does not exist",handler.processInput("9781452668584,1",InputHandler.DELETEITEM).getOutput(),"The Item Does Not Exist!");

		}

	@Test
	public void UserInitializationTests() {
		
		handler.processInput("jim@carleton.ca,jim",InputHandler.CREATEUSER);
		assertEquals("Prompt for Username and Password.",handler.processInput("User",InputHandler.ROLEDETERMINED).getOutput(), "Please Input User Info: username,password:" );

				
		assertEquals("User Login",handler.processInput("jim@carleton.ca,jim",InputHandler.USER).getState(), InputHandler.USERLOGIN );
		assertEquals("Check if User format is correct",handler.processInput("tim&carleton.ca,tim",InputHandler.USER).getOutput(),  "Your input should in this format:'username,password'");
		assertEquals("User Incorrect Passowrd",handler.processInput("jim@carleton.ca,tim",InputHandler.USER).getOutput(),  "Wrong Password!Please Input Username and Password:'username,password'");
		assertEquals("User does not exist",handler.processInput("tim@carleton.ca,tim",InputHandler.USER).getOutput(),  "The User Does Not Exist!Please Enter The Username and Password:'username,password'");

		assertEquals("Display User Terminal",handler.processInput("jim@carleton.ca,jim",InputHandler.USER).getOutput(),"What can I do for you?"
    					+ "Menu:"
    					+ "Borrow"
    					+ "Renew"
    					+ "Return"
    					+ "Pay Fine." );
	assertEquals("Prompting user for borrow info",handler.processInput("borrow",InputHandler.USERLOGIN).getState(),InputHandler.BORROW);
	assertEquals("Prompting user for renew info",handler.processInput("renew",InputHandler.USERLOGIN).getState(), InputHandler.RENEW);
	assertEquals("Prompting user for return info",handler.processInput("return",InputHandler.USERLOGIN).getState(), InputHandler.RETURN);
	assertEquals("Prompting user for fine info",handler.processInput("collect fine",InputHandler.USERLOGIN).getState(), InputHandler.PAYFINE);
	}
	
	
	@Test
	public void UserBorrowRenewReturnTests() {
		
		
		handler.processInput("jim@carleton.ca,jim",InputHandler.CREATEUSER);
		handler.processInput("9781442668583,TestBook2",InputHandler.CREATETITLE);
		handler.processInput("9781442668583",InputHandler.CREATEITEM);

		

		Loan testLoan = new Loan(0,"9781442668584","1", new Date(100)  ,0);
		
		Date date  = new Date();
		
		Long time  = date.getTime()-Config.STIMULATED_DAY*7;
		Loan testLoan2 = new Loan(0,"9876543211234","1", new Date(time)  ,0);
		
		LoanTable.getInstance().getLoanTable().add(testLoan2);

		
		assertEquals("get id", testLoan.getUserid(),0);
		assertEquals("get ISBN", testLoan.getIsbn(),"9781442668584");
		assertEquals("get Copynumber", testLoan.getCopynumber(),"1");
		assertEquals("get renewstate", testLoan.getRenewstate(),0);

		assertTrue( "Initializing LoanTable Class", LoanTable.getInstance().getLoanTable().get(0).sameLoan(testLoan));
		
		
		
		
	//	System.out.println(handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.BORROW).getOutput());
		//System.out.println(handler.processInput("9781442668583",InputHandler.CREATEITEM).getOutput());
		
		//System.out.println("\n"+handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.BORROW).getOutput());

	
		assertEquals("Create a loan by borrowing",handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.BORROW).getOutput(),"Success!");
		
		
		assertEquals("Create a loan by burrowing with invlaid user",handler.processInput("kjnknk@carleton.ca,9781442668583,1",InputHandler.BORROW).getOutput(), "The User Does Not Exist!");	
		assertEquals("Create a loan by burrowing with invlaid isbn",handler.processInput("jim@carleton.ca,9781234234234583,1",InputHandler.BORROW).getOutput(), "Your input should in this format:'useremail,ISBN,copynumber'");	
		assertEquals("Create a loan by burrowing with invlaid copynumber",handler.processInput("jim@carleton.ca,9781442668583,3",InputHandler.BORROW).getOutput(), "Copynumber Invalid!");	
		assertEquals("Create a loan by borrowing an item that was already borrowed",handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.BORROW).getOutput(),"Already been loaned!");

		
		//System.out.println(handler.processInput("monitor system",InputHandler.LIBRARIANLOGIN).getOutput());

		
	//	System.out.println(handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.RENEW).getOutput());

		
		
		assertEquals("Create a loan by renewing",handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.RENEW).getOutput(),"Success!");			
		assertEquals("Create a loan by renewing with invalid user",handler.processInput("tim@carleton.ca,9781442668584,1",InputHandler.RENEW).getOutput(),"The User Does Not Exist!");	
		assertEquals("Create a loan by renewing with invalid isbn",handler.processInput("jim@carleton.ca,9781234234234584,1",InputHandler.RENEW).getOutput(), "Your input should in this format:'useremail,ISBN,copynumber'");	
		
		
		//System.out.println(handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.RETURN).getOutput().contains("success!"));
		
		
		assertTrue("return an item",handler.processInput("jim@carleton.ca,9781442668583,1",InputHandler.RETURN).getOutput().contains("success!"));	
		assertTrue("returning with invalid user",handler.processInput("tim@carleton.ca,9781442668584,1",InputHandler.RETURN).getOutput().contains("The User Does Not Exist!"));
		assertTrue("returning with invalid isbn",handler.processInput("jim@carleton.ca,9781234234234584,1",InputHandler.RETURN).getOutput().contains("Your input should in this format:'useremail,ISBN,copynumber'"));	
		assertTrue("Create a loan by renewing an overdue book",handler.processInput("jim@carleton.ca,9876543211234,1",InputHandler.RETURN).getOutput().contains("This item is overdue!"));	
		assertTrue("Create a loan by renewing an overdue book with a user who does not have privlage",handler.processInput("jim@carleton.ca,9781442668584,1",InputHandler.RETURN).getOutput().contains("privilege revoked!"));	

		}
	
	@Test
	public void feeTests() {
		
		Fee testFee = new Fee(100,5);
		assertEquals("get id", testFee.getUserid(),100);
		assertEquals("get Fee", testFee.getFee(),5);
		assertTrue( "Initializing FeeTable Class", FeeTable.getInstance().getFeeTable().get(0).sameFee(testFee));
		
		
		//System.out.println(handler.processInput("@carleton.ca",InputHandler.PAYFINE).getOutput());
		
		//assertEquals("Pay fee",handler.processInput("jim@carleton.ca",InputHandler.PAYFINE).getOutput(),"Success!");	
		assertEquals("user that deos not exists pays fee",handler.processInput("tim@carleton.ca",InputHandler.PAYFINE).getOutput(),"The User Does Not Exist!");	
		assertEquals("improper email format ",handler.processInput("tim^&arleton.ca",InputHandler.PAYFINE).getOutput(),"Your input should in this format:'useremail'");	
	
	}
	
	@Test
	public void logOutanddMainMenuPrompts() {
		
		assertEquals("main menu",handler.processInput("main menu",InputHandler.DELETEUSER).getOutput(),"What would you like to do?"
				
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
		
		assertEquals("main menu",handler.processInput("main menu",InputHandler.BORROW).getOutput(),"What would you like to do?"
				
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
		
		assertEquals("log out.",handler.processInput("log out",InputHandler.CREATEITEM).getOutput(),"Successfully Log Out!");	
		assertEquals("log out",handler.processInput("log out",InputHandler.DELETEITEM).getOutput(),"Successfully Log Out!");		
		assertEquals("Log out",handler.processInput("log out",InputHandler.RETURN).getOutput(),"Successfully Log Out!");	
		assertEquals("Log out",handler.processInput("log out",InputHandler.PAYFINE).getOutput(),"Successfully Log Out!");
		
		
		
	}
	

	
	
	
	

	
	

}
	


