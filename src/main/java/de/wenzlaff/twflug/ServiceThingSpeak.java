package de.wenzlaff.twflug;

import java.util.Date;

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
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String anzahlFlugzeuge = "25";
		String apiWriteKey = "eintragen";
		Integer channelId = 44177;

		send(anzahlFlugzeuge, apiWriteKey, channelId);
	}

	public static void send(String anzahlFlugzeuge, String apiWriteKey, Integer channelId) throws UnirestException, ThingSpeakException {

		Channel channel = new Channel(channelId, apiWriteKey);

		Entry entry = new Entry();
		entry.setField(1, anzahlFlugzeuge);
		String nachricht = "Update: " + anzahlFlugzeuge + " Flugzeuge erfasst am " + new Date(System.currentTimeMillis());
		entry.setStatus(nachricht);
		System.out.println(nachricht);
		channel.update(entry);

		System.out.println("Update ok");
	}

}
