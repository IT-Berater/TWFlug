package de.wenzlaff.twflug;

/**
 * This program will demonstrate the file transfer from local to remote.
 * $ CLASSPATH=.:../build javac ScpTo.java
 * $ CLASSPATH=.:../build java ScpTo file1 user@remotehost:file2
 * You will be asked passwd.
 * If everything works fine, a local file 'file1' will copied to
 * 'file2' on 'remotehost'.
 *
 * http://www.jcraft.com/
 */
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
 * flugdaten.log pi@pi-home:/home/pi/fhem/log/flugdaten.log
 * 
 * @author Thomas Wenzlaff
 *
 */
public class ScpTo {

	private static final Logger LOG = LogManager.getLogger(ScpTo.class.getName());

	public static void copyFile(Parameter parameter) {

		File lokaleDatendatei = parameter.getOutputDatei();
		String zielUser = parameter.getZielUser();
		String passwort = parameter.getZielPasswort();
		String host = parameter.getZielIp();
		File zielDatei = parameter.getZielDatei();

		// String khfile = "/home/pi/.ssh/known_hosts";
		String khfile = "/Users/thomaswenzlaff/.ssh/known_hosts";
		// String identityfile = "/home/pi/.ssh/id_rsa";
		String identityfile = "/Users/thomaswenzlaff/.ssh/id_rsa";

		if (lokaleDatendatei != null && zielUser != null && passwort != null && host != null && zielDatei != null) {

			System.out.println("Starte kopieren mit lokaleDatendatei=" + lokaleDatendatei + ", user=" + zielUser + ", passwort=" + passwort + ", host=" + host
					+ ", zielDatei=" + zielDatei);

			// [flugdaten-2014-12.log, pi@pi-home:/home/pi/fhem/log/flugdaten-2014-12.log]

			FileInputStream fis = null;
			try {

				String lokalDateiPath = lokaleDatendatei.getAbsolutePath();
				String zielDateiName = zielDatei.getAbsolutePath();

				JSch jsch = new JSch();

				Session session = jsch.getSession(zielUser, host, 22);

				// jsch.setKnownHosts(khfile);
				// jsch.removeAllIdentity();
				// jsch.addIdentity(identityfile);

				UserInfo ui = new PrivateUserInfo();
				session.setUserInfo(ui);
				session.setPassword(passwort);

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
					System.out.println("ERROR 1, kein kopieren erfolgt");
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
						System.out.println("ERROR 2, kein kopieren erfolgt");
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
					System.out.println("ERROR 3, kein kopieren erfolgt");
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
					System.out.println("ERROR 4, kein kopieren erfolgt");
					return;
				}
				out.close();

				channel.disconnect();
				session.disconnect();
				System.out.println("Ok, kopiert");
			} catch (Exception e) {
				System.out.println(e);
				try {
					if (fis != null)
						fis.close();
				} catch (Exception ee) {
				}
			}
			System.out.println("OK, erfolgreich kopiert");
		} else {
			System.out.println("Konnte Datei nicht kopieren, da ein Parameter null ist. lokaleDatendatei=" + lokaleDatendatei + ", zielUser=" + zielUser
					+ ", passwort=" + passwort + ", host=" + host + ", zielDatei" + zielDatei);
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
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

}