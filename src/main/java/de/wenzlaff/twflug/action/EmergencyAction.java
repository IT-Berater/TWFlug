package de.wenzlaff.twflug.action;

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
