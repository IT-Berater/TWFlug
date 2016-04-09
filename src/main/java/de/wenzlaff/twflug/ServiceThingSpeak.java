package de.wenzlaff.twflug;

/*
 * #%L
 * twflug
 * %%
 * Copyright (C) 2015 Thomas Wenzlaff
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
