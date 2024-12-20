package de.wenzlaff.twflug;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Die Hauptanwendung die gestartet wird.
 * 
 * Damit es im rPI läuft:
 * 
 * In your SSH session type:
 *
 * export DISPLAY=:0.0
 * 
 * or
 * 
 * If you put that line into /etc/profile (or ~/.bashrc) it'll set it
 * automatically at startup / when you log in.
 * 
 * @author Thomas Wenzlaff
 */
public class TWFlug {

	static {
		System.setProperty(XmlConfigurationFactory.LOG4J1_CONFIGURATION_FILE_PROPERTY.getName(), "./log4j2.xml");
	}
	/**
	 * Log4j2 siehe
	 * http://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration
	 */
	private static final Logger LOG = LogManager.getLogger(TWFlug.class.getName());

	/**
	 * Die Methodt für das Aufrufen der TWFlug Anwendung mit oder ohne Gui.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LOG.debug("Starte TWFlug ...");

		Parameter parameter = Kommandozeile.parseCommandline(args);
		if (parameter == null) {
			return;
		}
		if (!parameter.isNoGui()) {
			OSXAppearance.applyIfApplicable();
		}
		try {
			Client client = new Client();
			client.start(parameter);
		} catch (IOException e) {
			LOG.error("Fehler beim Starten des Client: " + e.getMessage());
		}
	}

}
