package ssteinkellner;

/**
 * eine klasse, die je nach argumenten entweder server oder client startet
 * @author Steinkellner Sebastian
 * @version 2014.10.18
 */
public class starter {
	public static void main(String[] args) {
		if(args.length!=2){
			System.err.println("Usage: java -jar <jarname>.jar -server <port>\n"
							 + "   or: java -jar <jarname>.jar <hostName/IP> <port>");
			System.exit(1);
		}
		
		if(args[0].equalsIgnoreCase("-server")){
			new KnockKnockServer().start(Integer.parseInt(args[1]));
		}else{
			new KnockKnockClient().start(args[0],Integer.parseInt(args[1]));
		}
	}
}
