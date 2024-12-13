package de.wenzlaff.twflug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Scp Copy von http://www.jcraft.com/.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 21.12.2014
 */
public class ScpTo {

	private static final Logger LOG = LogManager.getLogger(ScpTo.class.getName());

	/**
	 * Kopiert eine lokale Datei auf das Zielsystem per scp copy.
	 * 
	 * @param parameter
	 */
	public static void copyFile(Parameter parameter) {

		String zielUser = parameter.getZielUser();
		String zielPasswort = parameter.getZielPasswort();
		String zielIp = parameter.getZielIp();

		File lokaleOutputDatei = Util.getLokaleOutputDatei();
		File entfernteOutputDatei = Util.getEntfernteOutputDatei();

		// String khfile = "/home/pi/.ssh/known_hosts";
		// String khfile = "/Users/thomaswenzlaff/.ssh/known_hosts";
		// String identityfile = "/home/pi/.ssh/id_rsa";
		// String identityfile = "/Users/thomaswenzlaff/.ssh/id_rsa";

		if (lokaleOutputDatei != null && zielUser != null && zielPasswort != null && zielIp != null && entfernteOutputDatei != null) {

			if (parameter.isDebug()) {
				LOG.debug("Starte kopieren mit lokaleDatendatei=" + lokaleOutputDatei + ", user=" + zielUser + ", passwort=" + zielPasswort + ", host=" + zielIp + ", zielDatei="
						+ entfernteOutputDatei);
			}
			// [flugdaten-2014-12.log, pi@pi-home:/home/pi/fhem/log/flugdaten-2014-12.log]

			FileInputStream fis = null;
			try {

				String lokalDateiPath = lokaleOutputDatei.getAbsolutePath();

				if (!lokaleOutputDatei.exists()) {
					LOG.info("Konnte Datei " + lokalDateiPath + " nicht kopieren, da sie nicht vorhanden ist.");
					return;
				}

				String zielDateiName = entfernteOutputDatei.getAbsolutePath();

				JSch jsch = new JSch();

				Session session = jsch.getSession(zielUser, zielIp, 22);

				// jsch.setKnownHosts(khfile);
				// jsch.removeAllIdentity();
				// jsch.addIdentity(identityfile);

				UserInfo ui = new PrivateUserInfo();
				session.setUserInfo(ui);
				session.setPassword(zielPasswort);

				// TODO: über Parameter als default einsteuern
				// nur zum Testen das ist unsicher
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				//
				// oder jsch.setKnownHosts(/Users/thomaswenzlaff/.ssh/known_hosts)
				// in der known_host Datei den mit
				// ssh-keyscan -t rsa sftp_server_ip_address_or_hostname
				// z.B.: ssh-keyscan -t rsa pi-home
				// erzeugten key des Zielsystems aufnehmen und optional den RSA Key setzen
				// jsch.addIdentity("/Users/thomaswenzlaff/.ssh/id_rsa");
				// zuvor aber alle Schlüssel löschen mit
				// jsch.removeAllIdentity();

				session.connect();

				boolean ptimestamp = true;

				// exec 'scp -t rfile' remotely
				String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + zielDateiName;
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);

				// get I/O streams for remote scp
				OutputStream out = channel.getOutputStream();
				InputStream in = channel.getInputStream();

				channel.connect();

				if (checkAck(in) != 0) {
					LOG.error("ERROR 1, kein kopieren erfolgt");
					return;
				}

				File _lfile = new File(lokalDateiPath);

				if (ptimestamp) {
					command = "T " + (_lfile.lastModified() / 1000) + " 0";
					// The access time should be sent here,
					// but it is not accessible with JavaAPI ;-<
					command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
					out.write(command.getBytes());
					out.flush();
					if (checkAck(in) != 0) {
						LOG.error("ERROR 2, kein kopieren erfolgt");
						return;
					}
				}

				// send "C0644 filesize filename", where filename should not include '/'
				long filesize = _lfile.length();
				command = "C0644 " + filesize + " ";
				if (lokalDateiPath.lastIndexOf('/') > 0) {
					command += lokalDateiPath.substring(lokalDateiPath.lastIndexOf('/') + 1);
				} else {
					command += lokalDateiPath;
				}
				command += "\n";
				out.write(command.getBytes());
				out.flush();
				if (checkAck(in) != 0) {
					LOG.error("ERROR 3, kein kopieren erfolgt");
					return;
				}

				// send a content of lfile
				fis = new FileInputStream(lokalDateiPath);
				byte[] buf = new byte[1024];
				while (true) {
					int len = fis.read(buf, 0, buf.length);
					if (len <= 0)
						break;
					out.write(buf, 0, len); // out.flush();
				}
				fis.close();
				fis = null;
				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
				if (checkAck(in) != 0) {
					LOG.error("ERROR 4, kein kopieren erfolgt");
					return;
				}
				out.close();

				channel.disconnect();
				session.disconnect();
				if (parameter.isDebug()) {
					LOG.info("Ok, Datei " + zielDateiName + " auf Zielsystem IP: " + parameter.getZielIp() + " kopiert");
				}
			} catch (Exception e) {
				LOG.error(e);
				try {
					if (fis != null)
						fis.close();
				} catch (Exception ee) {
				}
			}
			if (parameter.isDebug()) {
				LOG.info("Ok, Datei " + entfernteOutputDatei.getAbsolutePath() + " auf Zielsystem IP: " + parameter.getZielIp() + " kopiert");
			}

		} else {
			LOG.error("Konnte Datei nicht kopieren, da ein Parameter null ist. lokaleDatendatei=" + lokaleOutputDatei + ", zielUser=" + zielUser + ", passwort=" + zielPasswort
					+ ", host=" + zielIp + ", zielDatei" + entfernteOutputDatei);
		}
	}

	public static class PrivateUserInfo implements UserInfo {

		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public String getPassword() {
			return null;
		}

		@Override
		public boolean promptPassword(String message) {
			return false;
		}

		@Override
		public boolean promptPassphrase(String message) {
			return false;
		}

		@Override
		public boolean promptYesNo(String message) {
			return false;
		}

		@Override
		public void showMessage(String message) {
		}
	}

	private static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) {
				LOG.error("Error" + sb.toString());
			}
			if (b == 2) {
				LOG.error("Fatal error" + sb.toString());
			}
		}
		return b;
	}

}