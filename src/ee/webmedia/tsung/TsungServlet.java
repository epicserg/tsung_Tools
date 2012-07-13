package ee.webmedia.tsung;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TsungServlet
 */

public class TsungServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public TsungServlet() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub

  }

  protected Map generateRequestMap(HttpServletRequest request) {
    Map<String, String> requestPar = new HashMap<String, String>();
    requestPar.put("ip", request.getParameter("ip"));
    requestPar.put("login", request.getParameter("login"));
    requestPar.put("userNumber", request.getParameter("userNumber"));
    requestPar.put("scenario", request.getParameter("scenario"));
    return requestPar;
  }

  protected boolean validateInput(Map requestPar) {
    Iterator it = requestPar.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry) it.next();
      if (pairs.getValue() == "")
        return false;
    }
    return true;
  }

  protected void printInfo(Map requestPar, PrintWriter out) {
    out.println("You have enterd Ip :" + requestPar.get("ip") + "<br />");
    out.println("Login was :" + requestPar.get("login") + "<br />");
    out.println("Number of users " + requestPar.get("userNumber") + "<br />");
    out.println("scenario selected :" + requestPar.get("scenario") + "<br />");
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    Map<String, String> requestPar = generateRequestMap(request);
    PrintWriter out = response.getWriter();
    out.println("request was :" + request.toString() + "<br />");
    printInfo(requestPar, out);
    boolean fastNodeEnabled = (request.getParameter("FastNode") != null);
    out.println("fast node enabled :" + fastNodeEnabled);
    if (!validateInput(requestPar)) {
      out.println("Some info was incorect , please check your input " + "<br />");
    }

    else
      setButtons(requestPar, out, request);
  }

  protected void setButtons(Map requestPar, PrintWriter out, HttpServletRequest request) {
    out.println("Select your action" + "<br />");

    String form = "<div>";
    // "<div class=\"send-sms-phone\"><input class='clientNumber' type=\"text\" name=\"phone\" /></div>";
    form += "<p><button type=\"button\" class=\"start\"><span><strong>";
    form += "Start";
    form += "</strong></span></button></p>";
    form += "<p><button type=\"button\" class=\"stop\"><span><strong>";
    form += "Stop";
    form += "</strong></span></button></p>";

    form += "<p><button type=\"button\" class=\"log\"><span><strong>";
    form += "Log";
    form += "</strong></span></button></p>";

    form += "<p><button type=\"button\" class=\"show\"><span><strong>";
    form += "Show Scripts";
    form += "</strong></span></button></p>";

    form += "</div>";
    form += "</form>";
    form += "<script type='text/javascript' src='Javascript/Connector.js'></script>";
    out.print(form);

  }

}
