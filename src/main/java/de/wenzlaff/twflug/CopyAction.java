package de.wenzlaff.twflug;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Klasse für das Kopieren per Scp auf das Zielsystem.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 21.12.2014
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
			LOG.error("Fehler beim kopieren auf den Zielhost." + e.getLocalizedMessage(), e);
		}
	}

}
