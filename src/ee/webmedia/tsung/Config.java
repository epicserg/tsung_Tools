package ee.webmedia.tsung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	//
	private static final String TSUNG_CONFIG_PATH = new String(
			"/root/.tsung/tsung.xml");

	public static String readConfig() {
		File file = new File(TSUNG_CONFIG_PATH);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		String text = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return contents.toString();
	}

	public static void saveConfig(String text) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					TSUNG_CONFIG_PATH));
			out.write(text);
			out.close();
		} catch (IOException e) {
			System.out.println("Exception " + e);
		}
	}

}
