
package DataSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerClass 
{
	private static Logger Logger=LogManager.getLogger(LoggerClass.class);
	public static void main(String[] args) 
	{
	System.out.println("Hellow word");
	Logger.info("This is information message");
	Logger.error("This is error message");

	}

}
