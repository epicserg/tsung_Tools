package ee.webmedia.tsung;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Report {

	private static final String TSUNG_STATS = new String("/tsung_stats.pl");
	private static final String TSUNG_ROOT_PATH = new String("/root/.tsung/");
	private static final String TSUNG_LOG_PATH = new String("/root/.tsung/log/");
	
	//
	private static final String TOMCAT_WEB_PATH = new String(
			"/home/md/devsoft/apache-tomcat-7.0.29/webapps/");
	private static final String TSUNG_START = new String("sudo perl tsung_stats.pl");

	public static boolean moveLogFolder() {
		String rf = getRecentFolder(TSUNG_LOG_PATH).getName();
		File reportForder = new File(TSUNG_LOG_PATH + rf);
		File jungoWeb = new File(TOMCAT_WEB_PATH + "tsungreports/" + rf);
		try {
			FileUtils.copyDirectory(reportForder, jungoWeb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void generateReport() throws IOException {
		String rf = getRecentFolder(TSUNG_LOG_PATH).getName();
		File tsungStats = new File(TSUNG_ROOT_PATH + TSUNG_STATS);
		File newTsungStats = new File(TSUNG_LOG_PATH + rf + TSUNG_STATS);
		File logFolder = new File(TSUNG_LOG_PATH + rf);
		try {
			copyFile(tsungStats, newTsungStats);
		} catch (Exception e) {
			System.out.println("Can't copy config file: " + e.toString());
		}
		if (newTsungStats.exists() && logFolder.exists()) {
			try {
				String[] sz_env = { "" };
				Process p = Runtime.getRuntime().exec(
						TSUNG_START, sz_env, logFolder);
				p.waitFor();
				showStreams(p);
			} catch (Exception e) {
				System.out.println("Exception: " + e.toString());
			}
		} else {
			System.out.println("Can't generate report, files doesn't exist");
		}

	}

	public static void showStreams(Process p) {
		try {
			System.out.println("..................");
			InputStream op = p.getInputStream();
			List<String> readLines;
			readLines = IOUtils.readLines(op);
			for (String string : readLines) {
				System.out.println(string);
			}
			System.out.println("..................");
			InputStream errorStream = p.getErrorStream();
			readLines = IOUtils.readLines(errorStream);
			for (String string : readLines) {
				System.out.println(string);
			}
			op.close();
			errorStream.close();
			System.out.println("..................");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void copyFile(File from, File to) throws IOException {
		Files.copy(from.toPath(), to.toPath());
	}

	private static File getRecentFolder(String path) {
		File file = null;
		for (String x : new File(path).list()) {
			File monitor = new File(path + x);
			if (file == null) {
				file = monitor;
				continue;
			}
			if (monitor.lastModified() > file.lastModified()
					&& monitor.isDirectory()) {
				file = monitor;
			}
		}
		return file;
	}

}
