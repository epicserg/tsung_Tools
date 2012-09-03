package ee.webmedia.tsung;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Log {
	//
	private static final String TSUNG_REPORTS_PATH = new String(
			"/home/md/devsoft/apache-tomcat-7.0.29/webapps/tsungreports/");

	public static List<String> getFileNamesFromFolder() {
		File tsungReportsFolder = new File(TSUNG_REPORTS_PATH);
		File[] listOfFiles = tsungReportsFolder.listFiles();
		List<String> logs = new ArrayList<String>();
		// TODO: is TsungReportFolder empty check
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				logs.add(i, listOfFiles[i].getName());
			}
		}
		return logs;
	}

}
