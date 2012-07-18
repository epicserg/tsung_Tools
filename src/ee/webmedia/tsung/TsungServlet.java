package ee.webmedia.tsung;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TsungServlet
 */

public class TsungServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  protected boolean testRunning;
  protected int entries = 0;

  /**
   * Default constructor.
   */
  public TsungServlet() {
    testRunning = false;
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userAction = request.getQueryString();
    if (userAction != null)
      executeAction(userAction);
    PrintWriter out = response.getWriter();
    setButtons(out, request);
    entries++;

  }

  protected void startTest() {
    System.out.println("starting test");
    Runtime rt = Runtime.getRuntime();
    try {
      rt.exec("echo lol");
      // rt.exec("TODO type your tsung start script");
      setTestRunning(true);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      // TODO check if test has started
      setTestRunning(true);
    }
  }

  protected void stopTest() {
    System.out.println("stopping test");
    Runtime rt = Runtime.getRuntime();
    try {
      rt.exec("echo lol");
      // rt.exec("TODO type your tsung stop script");
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      setTestRunning(false);
    }
  }

  protected void openFile() {

  }

  protected void generateReport() {
    stopTest();
    System.out.println("generating report");
    Runtime rt = Runtime.getRuntime();
    openFile();
    try {
      rt.exec("notepad");
      // rt.exec("TODO type your tsung generate report script");
      // TODO open external html
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  protected void editXml() {
    System.out.println("editing XML");
    // TODO Stream xml over internet and ask browser for changes
  }

  protected void executeAction(String userAction) {
    System.out.println("executing action " + userAction);
    if (userAction.equals("action=start")) {
      startTest();
    }
    if (userAction.equals("action=stop")) {
      stopTest();
    }
    if (userAction.equals("action=report")) {
      generateReport();
    }
    if (userAction.equals("action=edit")) {
      editXml();
    }

  }

  synchronized void setTestRunning(boolean bo) {
    this.testRunning = bo;
  }

  protected void setButtons(PrintWriter out, HttpServletRequest req) {

    out.println("<br/> Select your action" + "<br /> ");

    String form = "<div> times server entered before ";
    form += entries;
    form += "  test running :" + this.testRunning;
    form += " </br>";
    form += "action selected " + req.getQueryString();
    if (!testRunning) {
      form += "<form name='frm' method='post' action='TsungServlet?action=start'> ";
      form += "<p><input type='submit' name='Start' value='Start'><span><strong>";
      form += "</strong></span></button></p>";
      form += "</form>";
    }
    else {
      form += "<form name='frm' method='post' action='TsungServlet?action=stop'> ";
      form += "<p><input type='submit' name='Stop' value='Stop'><span><strong>";
      form += "</strong></span></button></p>";
      form += "</form>";
    }
    form += "<form name='frm' method='post' action='TsungServlet?action=report'> ";
    form += "<p><input type='submit' name='Report' value='Report'><span><strong>";
    form += "</strong></span></button></p>";
    form += "</form>";

    form += "<form name='frm' method='post' action='TsungServlet?action=edit'> ";
    form += "<p><input type='submit' name='Edit' value='Edit'><span><strong>";
    form += "</strong></span></button></p>";
    form += "</form>";

    form += "</div>";
    form += "</form>";
    form += "<script type='text/javascript' src='Javascript/jQuery.js'></script>";
    form += "<script type='text/javascript' src='Javascript/Connector.js'></script>";
    out.print(form);

  }

}
