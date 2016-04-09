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

/**
 * Die 8 möglichen Transmission Message.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public enum TransmissionMessage {

	// ID
	//
	// Type
	//
	// Description

	// ES Identification and Category
	//
	// DF17 BDS 0,8
	MSG_1,

	// ES Surface Position Message
	// DF17 BDS 0,6
	// Triggered by nose gear squat switch.
	MSG_2,

	// ES Airborne Position Message
	// DF17 BDS 0,5
	MSG_3,

	// ES Airborne Velocity Message
	// DF17 BDS 0,9
	MSG_4,

	// Surveillance Alt Message
	// DF4, DF20
	// Triggered by ground radar. Not CRC secured.
	// MSG,5 will only be output if the aircraft has previously sent a
	// MSG,1, 2, 3, 4 or 8 signal.
	MSG_5,

	// Surveillance ID Message
	// DF5, DF21
	// Triggered by ground radar. Not CRC secured.
	// MSG,6 will only be output if the aircraft has previously sent a
	// MSG,1, 2, 3, 4 or 8 signal.
	MSG_6,

	// Air To Air Message
	// DF16
	// Triggered from TCAS.
	MSG_7,

	// is now included in the SBS socket output.
	// All Call Reply
	// DF11
	// Broadcast but also triggered by ground radar
	MSG_8

}
