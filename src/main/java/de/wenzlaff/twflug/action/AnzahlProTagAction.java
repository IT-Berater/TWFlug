package de.wenzlaff.twflug.action;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.Util;
import de.wenzlaff.twflug.be.FlugInfosProTag;
import de.wenzlaff.twflug.be.Parameter;

/**
 * Die Aktion die einmal am Tag ausgeführt wird. Speichert die anzahl Flugzeuge pro Tag in Logdatei.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 04.01.2015
 *
 */
public class AnzahlProTagAction extends TimerTask {

	private static final Logger LOG = LogManager.getLogger(AnzahlProTagAction.class.getName());

	private Parameter parameter;

	private FlugInfosProTag flugInfosProTag;

	public AnzahlProTagAction(FlugInfosProTag flugInfosProTag, Parameter parameter) {
		this.flugInfosProTag = flugInfosProTag;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		String meldung = "Speichere die Anzahl aller Flugzeuge pro Tag. Wird nur einmal am Tag gespeicher. Anzahl der ermittelten Flugzeuge pro Tag = "
				+ this.flugInfosProTag.getAnzahlFlugzeugeProTag();
		LOG.info(meldung);
		System.out.println(meldung);

		Util.writeFlugdatenProTag(flugInfosProTag, parameter);
		flugInfosProTag.setMaxAnzahlFlugzeugeAufNull();
	}

}
