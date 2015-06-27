package de.wenzlaff.twflug;

import java.util.Date;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;

/**
 * https://github.com/angryelectron/thingspeak-java/
 * 
 * @author thomaswenzlaff
 *
 */
public class SendToThingSpeak {

	//

	public static void main(String[] args) throws Exception {

		String anzahlFlugzeuge = "20";

		String apiWriteKey = "TKCAVQ7HJ2XDPP3G";
		Integer channelId = 44177;
		Channel channel = new Channel(channelId, apiWriteKey);

		Entry entry = new Entry();
		entry.setField(1, anzahlFlugzeuge);
		entry.setStatus("Anzahl " + anzahlFlugzeuge + " update um " + new Date(System.currentTimeMillis()));
		channel.update(entry);

		System.out.println("OK");

	}

}
