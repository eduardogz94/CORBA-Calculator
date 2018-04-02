package eagz.org;

import org.omg.CosNaming.*;

import java.util.Scanner;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;

import Calculator.*; //importar la interfaz idl
import Calculator.CalcPOA;
import eagz.org.CalcImpl;
import Calculator.Calc;
import Calculator.CalcHelper;


public class ClientORB {
	static Calc CalcImpl;
	public static void main(String args[]) {
		Scanner sc = new Scanner (System.in);
		try {
			ORB orb = ORB.init(args, null);//inicializar orb para enviar peticion
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			CalcImpl = CalcHelper.narrow(ncRef.resolve_str("calc"));
			boolean x;
			do{
				x = true;
				float a,b;
				System.out.println("\n\nCORBA Calculator" + "\n1.Add.\n2.Rest.\n3.Multiplication.\n4.Division.\n"
						+ "5.Pot.\n6.Square.\n0.Exit.\nOption-> ");
				a = sc.nextInt();
				
				switch((int)a) {
					case 0 : x = false; break;
					
					case 1 :
						System.out.println("\nSUM.\n1st value: ");
						a = sc.nextFloat();
						System.out.println("\n2nd value: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.sum(a, b)));
						break;
					
					case 2 :
						System.out.println("\nREST.\n1st value: ");
						a = sc.nextFloat();
						System.out.println("\n2nd value: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.rest(a, b)));
						break;
					
					case 3 :
						System.out.println("\nMULTIPLICATION.\n1st value: ");
						a = sc.nextFloat();
						System.out.println("\n2nd value: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.mult(a, b)));
						break;
					
					case 4 :
						System.out.println("\nDIVITION.\n1st value: ");
						a = sc.nextFloat();
						System.out.println("\n2nd value: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.divi(a, b)));
						break;
					
					case 5 :
						System.out.println("\nPOT.\n"+ "Base number: ");
						a = sc.nextFloat();
						System.out.println("\nPotency value: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.pot(a, b)));
						break;
					
					case 6 :
						System.out.println("\nRAIZ.\n"+ "Number value: ");
						a = sc.nextFloat();
						System.out.println("\nBase: ");
						b = sc.nextFloat();
						System.out.println("\nResult: "+Float.toString(CalcImpl.square(a, b)));
						break;
				}	
			}
			
			while(x);
			CalcImpl.shutdown();
		} 
		
		catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace(System.out);
		}
	}
}