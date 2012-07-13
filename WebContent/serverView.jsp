<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Watch your tsung traffik</title>
</head>
<body>
<p> Here you can connect to tsung on host machine </p>

<form name="frm" method="post" action="TsungServlet">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td width="8%">&nbsp;</td>
    <td width="92%">&nbsp;</td>
    </tr>
  <tr>
    <td>ip </td>
    <td><input type="text" name="ip"></td>
  </tr>
  <tr>
    <td>login</td>
    <td><input type="text" name="login"></td>
  </tr>
  <tr>
    <td>number of users</td>
    <td><input type="text" name="userNumber"></td>
  </tr>
  
  <tr>
   	<td>Fast Node</td>
    <td> <input type="checkbox" name="FastNode" value="FastNode"></td>
    	
  </tr>
   <tr>
   		<td>scenario</td>
   		<td>
  		<select name="scenario">
 		 <option value="Bingo">Bingo</option>
 		 <option value="Coral">coral</option>
 		 <option value="Portal">Portal</option>
	</select>      
  </td>
  </tr>
  
  
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="Submit"></td>
    </tr>
  <tr>
    
</table>
</form>

</body>
</html>