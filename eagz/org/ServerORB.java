package eagz.org;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

import Calculator.*; //importar la interfaz idl
import Calculator.CalcPOA;
import eagz.org.CalcImpl;
import Calculator.Calc;
import Calculator.CalcHelper;

class CalcImpl extends CalcPOA {
	private ORB orb;
	
	public void setORB(ORB orb_val) { 
		orb = orb_val;
	}//setear el orb para generar las conecciones
	
	public float sum(float x, float y) { 
		return x + y; 
	}
	
	public float rest(float x, float y) { 
		return x - y; 
	}
	
	public float mult(float x, float y) {
		return x * y; 
	}
	
	public float divi(float x, float y) { 
		return x / y; 
	}
	
	public float pot(float x, float y) { 
		return (float) Math.pow(x, y); 
	}
	
	public float square(float x, float y) { 
		return (float) Math.pow(x, 1/y); 
	}
	
	public void shutdown() { 
		orb.shutdown(false); 
	}
}

public class ServerORB {
	
	public static void main(String args[]) {
	try {
		ORB orb = ORB.init(args, null); //inicializacion de conexion
		POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));//abrimos el root del POA
		rootpoa.the_POAManager().activate();
		
		CalcImpl calcImpl = new CalcImpl(); //implementamos referencia
		calcImpl.setORB(orb); //seteamos ORB
		
		org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcImpl);
		Calc href = CalcHelper.narrow(ref);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");//le damos nombre al servicio
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		NameComponent path[] = ncRef.to_name("calc");//se envia el nombre para que reciba la interface
		ncRef.rebind(path, href);
		System.out.println("Server ORB waiting");//confirmacion de servidor conectado
		orb.run();
	}
	catch (Exception e) {
		System.err.println("Error: " + e);
		e.printStackTrace(System.out);
	}	
	}
}