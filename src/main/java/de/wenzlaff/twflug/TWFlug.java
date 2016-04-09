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
 * If you put that line into /etc/profile (or ~/.bashrc) it'll set it automatically at startup / when you log in.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 21.12.2014
 */
public class TWFlug {

	static {
		System.setProperty(XmlConfigurationFactory.CONFIGURATION_FILE_PROPERTY, "./log4j2.xml");
	}
	/** Log4j2 siehe http://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration */
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
