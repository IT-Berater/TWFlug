package de.wenzlaff.twflug.action;

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
