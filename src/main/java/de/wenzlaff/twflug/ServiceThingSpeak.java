package de.wenzlaff.twflug;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * https://github.com/angryelectron/thingspeak-java/
 * 
 * https://github.com/Mashape/unirest-java
 * 
 * JAR ins lokale Repo:
 * 
 * mvn install:install-file -Dfile=thingspeak-1.1.1.jar -DgroupId=thingspeak -DartifactId=thingspeak -Dversion=1.1.1 -Dpackaging=jar
 * 
 * 
 * <pre>
 *    <dependency>
 *		<groupId>thingspeak</groupId>
 *		<artifactId>thingspeak</artifactId>
 *		<version>1.1.1</version>
 *   </dependency>
 * </pre>
 * 
 * @author Thomas Wenzlaff
 *
 */
public class ServiceThingSpeak {

	/**
	 * Testmethode.
	 * 
	 * Aufruf: ServiceThingSpeak Feld1 ApiWriteKey ChannelId Status
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Aufruf: ServiceThingSpeak Feld1 ApiWriteKey ChannelId Status");
		}

		String anzahlFlugzeuge = args[0];
		String apiWriteKey = args[1];
		Integer channelId = Integer.valueOf(args[2]);
		String status = "";
		if (args != null) {
			status = args[3];
		}

		send(anzahlFlugzeuge, apiWriteKey, channelId, status);
	}

	public static void send(String anzahlFlugzeuge, String apiWriteKey, Integer channelId, String status) throws UnirestException, ThingSpeakException {

		Channel channel = new Channel(channelId, apiWriteKey);

		Entry entry = new Entry();
		entry.setField(1, anzahlFlugzeuge);
		entry.setStatus(status);
		System.out.println(status);
		channel.update(entry);

		System.out.println("Update ok");
	}

}
