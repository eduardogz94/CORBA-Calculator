package eagz.org;

import Calc.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;
import java.lang.*;

public class ClientORB{
  static Calculator CalcImpl;
  static int flag=1;
  static double x=0.0d;
  static double y=0.0d;
  
  public static void main(String args[]){
	  try{
		  System.out.println("\n----------------------------------------------");
		  System.out.println("Calc Client: Looking up Calc Server...");
		  ORB orb = ORB.init(args, null);
		  org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		  NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		  String name = "CalculatorOperations";
		  CalcImpl = CalculatorHelper.narrow(ncRef.resolve_str(name));
		  System.out.print("Cacl Client: Obtained a handle on server object: \n\n");
		  Scanner sc=new Scanner(System.in);
		  flag=1;
		  do{
			  System.out.print("\nCaclulator Client: Enter First Number: ");
			  x=sc.nextDouble();

			  System.out.print("\nCaclulator Client: Enter Second Number: ");
			  y=sc.nextDouble();
			  System.out.println("--------------------------------------------------");
			  System.out.println("\nCaclulator Client: Addition= "+CalcImpl.add(x,y));
			  System.out.println("\nCaclulator Client: Subtraction= "+CalcImpl.subtract(x,y));
			  System.out.println("\nCaclulator Client: Multiplication= "+CalcImpl.multiply(x,y));
			  System.out.println("\nCaclulator Client: Division= "+CalcImpl.divide(x,y));
			  System.out.println("\nCaclulator Client: Random()= "+CalcImpl.random());

			  System.out.println("--------------------------------------------------");
			  System.out.println("Continue?[1:Yes|0:No]: ");
			  flag=sc.nextInt();
		  }
		  while (flag!=0);
		  CalcImpl.shutdown();
	  }
	  catch (Exception e) 
	  {   
		  System.out.println("\n----------------------------------------------");
		  System.out.println("Calculator Client: Some Error Has Occurred !");
		  System.out.println("\n----------------------------------------------");
	  }
	  System.out.println("\nCaclulator Server: Exiting...");
	  System.out.println("\n----------------------------------------------");
   }
}