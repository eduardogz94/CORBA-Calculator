package eagz.org;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import Calc.*;

import java.util.Properties;

class CalculatorInterfaceImpl extends CalculatorPOA{
    private ORB orb;

    public void setORB(ORB orb_val) {   
        orb = orb_val;
    }
    /* List of functions specified in IDL file
        string add(in double x, in double y);
        string subtract(in double a, in double b);
        string multiply(in double x, in double y);
        string divide(in double a, in double b);
        string random();
        oneway void shutdown();
        
     if IDL file would have following function
     void multiply(in double x, in double y, out double result);
     then implement it here like
     
    public void multiply(double x, double y, org.omg.CORBA.DoubleHolder result) 
    {  
      result.value = x * y;  
    }
    */
    public String add(double x, double y){
        double res= x+y;
        return Double.toString(res);
    }

    public String subtract(double x, double y){
        double res= x-y;
        return Double.toString(res);
    }

    public String multiply(double x, double y){
        double res= x*y;
        return Double.toString(res);
    }

    public String divide(double x, double y) {
        double res= x/y;
        return Double.toString(res);
    }
    
    public String random() {
        double res=Math.random();
        return Double.toString(res);
    }

    public void shutdown(){
            orb.shutdown(false);
    }
}

public class ServerORB{
    public static void main(String args[]) {
    try{
        ORB orb = ORB.init(args, null);  
        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootpoa.the_POAManager().activate();
        CalculatorInterfaceImpl CalcImpl = new CalculatorInterfaceImpl();
        CalcImpl.setORB(orb);
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(CalcImpl);
        Calculator href = CalculatorHelper.narrow(ref);
        org.omg.CORBA.Object objRef =orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        String name = "CalculatorOperations";
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, href);
        System.out.println("\n----------------------------------------------");
        System.out.println("Calc Server: Ready and waiting...");
        System.out.println("\n----------------------------------------------");
        orb.run();
        }
            catch (Exception e){   
       System.out.println("\n----------------------------------------------");
       System.out.println("Calc Server: Some Error Has Occurred !");
       System.out.println("\n----------------------------------------------");
            }
      System.out.println("\nCalc Server: Exiting...");
      System.out.println("\n----------------------------------------------");
        }
    }