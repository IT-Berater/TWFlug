package de.wenzlaff.twflug;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class CheckDir {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		WatchService watchService = FileSystems.getDefault().newWatchService();
		System.out.println(watchService);
		WatchKey watchKey = Paths.get("/Temp").register(watchService,
				new Kind<?>[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE });

		while (true) {
			watchKey = watchService.take();

			for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
				System.out.println(watchEvent.kind() + " " + (watchEvent.context()));
			}

			watchKey.reset();
		}

	}
}
