package de.wenzlaff.twflug.action;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.ServiceThingSpeak;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.Parameter;

/**
 * Klasse schreibt die Anzahl der Flugzeuge zu dem Web Service Thing Speak.
 * 
 * https://thingspeak.com/
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 27.06.2015
 */
public class ThingSpeakAction extends TimerTask {

	private static final Logger LOG = LogManager.getLogger(ThingSpeakAction.class.getName());

	private FlugInfos flugzeuge;
	private Parameter parameter;

	public ThingSpeakAction(FlugInfos flugzeuge, Parameter parameter) {
		this.flugzeuge = flugzeuge;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		try {
			ServiceThingSpeak.send("" + flugzeuge.getMaxAnzahlFlugzeuge(), parameter.getThingSpeakApiWriteKey(), parameter.getThingSpeakChannelId());
		} catch (Exception e) {
			System.err.println(e);
			LOG.error(e.getMessage());
		}
	}

}
