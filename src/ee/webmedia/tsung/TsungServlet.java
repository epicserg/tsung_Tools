package ee.webmedia.tsung;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsungServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//
	private static final String serverName = "localhost";
	protected boolean isTestRunning;
	protected boolean showReports;
	protected boolean editConfig;
	protected int entries = 0;
	private static final Logger log = LoggerFactory
			.getLogger(TsungServlet.class);
	Process tsungProcess;
	private List<String> logs = new ArrayList<String>();
	private String configText;
	private String newConfigText = " ";
	

	public TsungServlet() {
		isTestRunning = false;
		showReports = false;
		editConfig = false;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userAction = request.getQueryString();
		if (userAction != null)
			executeAction(userAction);
		PrintWriter out = response.getWriter();
		setButtons(out, request);
		entries++;
		if (request.getParameter("newConfigText") != null) {
			String newConfigText = request.getParameter("newConfigText");
			Config.saveConfig(newConfigText);
		} else {
			newConfigText = Config.readConfig();

			if (newConfigText == null) {
				newConfigText = "put your tsung config here";
				Config.saveConfig(newConfigText);
			}
		}
	}

	protected void startTest() {
		if (!isTestRunning) {
			System.out.println("starting test");
			try {
				tsungProcess = Runtime.getRuntime().exec("tsung start");
				setTestRunning(true);
				Report.showStreams(tsungProcess);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				log.error("err :" + ioe);
			}
		} else {
			System.out.println("Test is already running");
		}
	}

	protected void stopTest() {
		if (isTestRunning) {
			System.out.println("stopping test");
			try {
				tsungProcess = Runtime.getRuntime().exec("tsung stop");
				Report.showStreams(tsungProcess);
				tsungProcess.waitFor();
				tsungProcess.destroy();
				Report.generateReport();
				Report.moveLogFolder();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				log.error("error :" + ioe);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("error :" + e);
			} finally {
				setTestRunning(false);
			}
		}else{
			System.out.println("Can't stop test, is wasn't started");
		}
	}

	protected void showReports() {
		logs = Log.getFileNamesFromFolder();
		Comparator<String> comparator = Collections.reverseOrder();
		Collections.sort(logs, comparator);

	}

	protected void executeAction(String userAction) {
		System.out.println("executing action " + userAction);
		if (userAction.equals("action=start")) {
			startTest();
			showReports = false;
		}
		if (userAction.equals("action=stop")) {
			stopTest();
		}
		if (userAction.equals("action=showreports")) {
			editConfig = false;
			showReports = true;
			showReports();
		}
		if (userAction.equals("action=hidereports")) {
			showReports = false;
		}
		if (userAction.equals("action=showEdit")) {
			editConfig = true;
			showReports = false;
			configText = Config.readConfig();
		}
		if (userAction.equals("action=saveEdit")) {
			editConfig = false;
		}
		if (userAction.equals("action=cancelEdit")) {
			editConfig = false;
		}

	}

	synchronized void setTestRunning(boolean bo) {
		this.isTestRunning = bo;
	}

	protected void setButtons(PrintWriter out, HttpServletRequest req) {

		out.println("<br/> Select your action" + "<br /> ");

		String form = "test running :" + this.isTestRunning;

		if (!isTestRunning) {
			form += "<form name='startTest' method='post' action='TsungServlet?action=start'> ";
			form += "<p><input type='submit' name='Start' value='Start'><span><strong>";
			form += "</strong></span></button></p>";
			form += "</form>";
			if (!showReports) {
				form += "<form name='showReports' method='post' action='TsungServlet?action=showreports'> ";
				form += "<p><input type='submit' name='showReport' value='Show reports'><span><strong>";
				form += "</strong></span></button></p>";
				form += "</form>";
			} else {
				form += "<form name='hideReports' method='post' action='TsungServlet?action=hidereports'> ";
				form += "<p><input type='submit' name='hideReport' value='Hide reports'><span><strong>";
				form += "</strong></span></button></p>";
				form += "</form>";
				for (int i = 0; i < logs.size(); i++) {
					// form += i + ": " + logs.get(i) + "</br>";
					form += "<a href='http://"+ serverName +"/tsungreports/"
							+ logs.get(i) + "/report.html'>"
							+ logs.get(i).substring(6, 8) + "."
							+ logs.get(i).substring(4, 6) + "."
							+ logs.get(i).substring(0, 4) + " "
							+ logs.get(i).substring(9, 11) + ":"
							+ logs.get(i).substring(11, 13) + "</a></br>";
				}
			}
			if (!editConfig) {

				form += "<form name='showEdit' method='post' action='TsungServlet?action=showEdit'> ";
				form += "<p><input type='submit' name='Edit' value='Edit XML config'><span><strong>";
				form += "</strong></span></button></p>";
				form += "</form>";
			} else {
				form += "<form name='saveEdit' method='post' action='TsungServlet?action=saveEdit'> ";
				form += "<textarea name='newConfigText' cols='200' rows='40'>"
						+ configText + "</textarea></br>";
				form += "<p><input type='submit' name='SaveXml' value='Save XML config'><span><strong>";
				form += "</strong></span></button></p>";
				form += "</form>";

				form += "<form name='cancelEdit' method='post' action='TsungServlet?action=cancelEdit'> ";
				form += "<p><input type='submit' name='Cancel' value='Cancel Editing'><span><strong>";
				form += "</strong></span></button></p>";
				form += "</form>";
			}
		} else {
			form += "<form name='stopTest' method='post' action='TsungServlet?action=stop'> ";
			form += "<p><input type='submit' name='Stop' value='Stop'><span><strong>";
			form += "</strong></span></button></p>";
			form += "</form>";
		}
		form += "</div>";
		form += "</form>";
		form += "<script type='text/javascript' src='Javascript/jQuery.js'></script>";
		form += "<script type='text/javascript' src='Javascript/Connector.js'></script>";
		out.print(form);
	}

}
