package de.wenzlaff.twflug;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.Parameter;

/**
 * Klasse schreibt die Anzahl der Flugzeuge in eine Datei.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 13.12.2014
 *
 */
public class WriteAction extends TimerTask {

	private static final Logger LOG = LogManager.getLogger(WriteAction.class.getName());

	private FlugInfos flugzeuge;
	private Parameter parameter;

	public WriteAction(FlugInfos flugzeuge, Parameter parameter) {
		this.flugzeuge = flugzeuge;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		Util.writeFlugdaten(flugzeuge, parameter.getOutputDatei());
		flugzeuge.setMaxAnzahlFlugzeugeAufNull();
	}

}
