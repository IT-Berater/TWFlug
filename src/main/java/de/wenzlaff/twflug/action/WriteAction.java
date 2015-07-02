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

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(WriteAction.class.getName());

	private FlugInfos flugzeuge;
	private Parameter parameter;

	public WriteAction(FlugInfos flugzeuge, Parameter parameter) {
		this.flugzeuge = flugzeuge;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		Util.writeFlugdaten(flugzeuge, parameter);
		flugzeuge.setMaxAnzahlFlugzeugeAufNull();
	}

}
