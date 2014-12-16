package de.wenzlaff.twflug;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Klasse für das Kopieren per Scp auf das Zielsystem.
 * 
 * @author Thomas Wenzlaff
 */
public class CopyAction extends TimerTask {

	private static final Logger LOG = LogManager.getLogger(CopyAction.class.getName());

	private Parameter parameter;

	public CopyAction(Parameter parameter) {

		this.parameter = parameter;
	}

	@Override
	public void run() {
		try {
			ScpTo.copyFile(parameter);
		} catch (Exception e) {
			System.out.println("Fehler beim kopieren auf den Zielhost." + e.getLocalizedMessage());
		}
	}

}
