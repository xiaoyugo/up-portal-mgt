<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.lang.management.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<title>JVM Memory Monitor</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
</head>
<body>
	<h1 style="text-decoration: underline;">JVM Memory Monitor[<%=new Date()%>]</h1>
	<h2>Memory MXBean</h2>
	<b>Heap Memory Usage:</b> <%= ManagementFactory.getMemoryMXBean().getHeapMemoryUsage() %><br>
	<b>Non-Heap Memory Usage: </b><%= ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage() %><br>
	<h2>Memory Pool MXBeans</h2>
	<%
		Iterator iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
		while (iter.hasNext()) {
		    MemoryPoolMXBean item = (MemoryPoolMXBean) iter.next();
	%>
	<div style="border-bottom: 1px solid #000000">
	<b>- Name:</b> <%= item.getName() %><br>
	<b>- Type:</b> <%= item.getType() %><br>
	<b>- Usage:</b> <%= item.getUsage() %><br>
	<b>- Peak Usage:</b> <%= item.getPeakUsage() %><br>
	<b>- Collection Usage:</b> <%= item.getCollectionUsage() %><br>
	</div>
	<%
	}
	%>
	</body>
</html>
