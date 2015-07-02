package de.wenzlaff.twflug.be;

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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Klasse die alle Felder aus dem JSon aircraft.json File enthält.
 * 
 * @author Thomas Wenzlaff
 */
public class Aircraft {

	/**
	 * It is a counter of the total number of valid Mode S messages processed since dump1090 started; it is used on the webmap to show a message rate. Same as
	 * the sum of data/stats.json: total.local.accepted[0..n] + total.remote.accepted[0..n], it is just in aircraft.json so the map doesn't have to fetch an
	 * extra file all the time.
	 */
	private Long messages;

	/** "now" is seconds-since-Jan-1-1970 when the JSON was generated. */
	private LocalDateTime now;

	private List<FieldDataRaw> aircraft;

	public Aircraft() {
		this.aircraft = new ArrayList<FieldDataRaw>();
	}

	public Aircraft(Long messages, LocalDateTime now) {
		this.messages = messages;
		this.now = now;
		this.aircraft = new ArrayList<FieldDataRaw>();
	}

	public Long getMessages() {
		return messages;
	}

	public void setMessages(Long messages) {
		this.messages = messages;
	}

	public LocalDateTime getNow() {
		return now;
	}

	public void setNow(LocalDateTime now) {
		this.now = now;
	}

	public void setNow(Long nowSekunden) {
		Instant instant = Instant.ofEpochMilli(nowSekunden);
		this.now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	public List<FieldDataRaw> getAircraft() {
		return aircraft;
	}

	public void setAircraft(List<FieldDataRaw> aircraft) {
		this.aircraft = aircraft;
	}

	public void addAircraft(FieldDataRaw aircraft) {
		this.aircraft.add(aircraft);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Aircraft [");
		if (messages != null) {
			builder.append("messages=");
			builder.append(messages);
			builder.append(", ");
		}
		if (now != null) {
			builder.append("now=");
			builder.append(now);
			builder.append(", ");
		}
		if (aircraft != null) {
			builder.append("aircraft=");
			builder.append(aircraft);
		}
		builder.append("]");
		return builder.toString();
	}

}
