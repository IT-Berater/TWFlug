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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.Parameter;

public class EmergencyAction {

	private static final Logger LOG = LogManager.getLogger(EmergencyAction.class.getName());

	private Parameter parameter;

	public EmergencyAction(FieldDataRaw fd, Parameter parameter) {
		this.parameter = parameter;
		String meldung = "TODO: Notfall eingetreten: " + fd;
		LOG.error(meldung);
		System.out.println(meldung);
	}

}
