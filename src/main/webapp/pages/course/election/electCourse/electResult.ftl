<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title><@bean.message key="system.title" /></title>
  <link href="${static_base}/css/default.css" rel="stylesheet" type="text/css">
 </head>
<body onload="refreshCourseTable();" style="background-color:white">
<table  width="100%" align="center">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B><@bean.message key="course.electionResult"/></B>
      </td>
    </tr>
    <tr>
      <td style="font-size:0px" >
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <tr align="center">
     <td id="message_td"></td>
   </tr>
   <tr align="center">
     <td id="timeElapsed"></td>
   </tr>
  </table>
  <script>
    <#assign messages><font color="${(electResultInfo?index_of("error")==0)?string("red", "green")}"><@bean.message key=electResultInfo/></font></#assign>
    document.getElementById("message_td").innerHTML = "${messages?js_string}";
    
  	function refreshCourseTable(){
  	 if (null == parent.refreshCourseTable) {
  	     return;
  	 }
  	    <#if electResultInfo?index_of("error")==-1>
  	    	parent.refreshCourseTable(true);
  	    <#else>
  	    	parent.refreshCourseTable(false);
  	    </#if>
  	}
  <#if RequestParameters['submitTime']?exists>
  	var submitTime = ${RequestParameters['submitTime']};
  	var curTime = (new Date()).getTime();
  	var timeElapsedMillseconds =curTime-submitTime;
  	timeElapsedMillseconds= timeElapsedMillseconds/1000;
	var minute = Math.floor(timeElapsedMillseconds / 60);
    var second = timeElapsedMillseconds % 60;

    var msg ="<@bean.message key="common.timeConsuming"/>:";
    if(minute!=0)
        msg+=minute+" m ";
    if(second!=0)
        msg+=second+" s ";
  	document.getElementById("timeElapsed").innerHTML=msg;
  	setTimeout("parent.closeElectResult()",3000);
  </#if>
  </script>
  </body>
  <#include "/templates/foot.ftl"/> 