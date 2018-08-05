<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#include "/pages/system/calendar/timeFunction.ftl">
<table id="examTurnBar"></table>
	<table  width="80%" border="0" class="formTable" align="center">
	<form name="examTurnForm" method="post" action="" onsubmit="return false;">
	 <input name="examTurn.id" value="${examTurn.id?if_exists}" type="hidden"/>
	 <input name="examTurn.stdType.id" value="${(examTurn.stdType.id)?if_exists}" type="hidden"/>
 	 <tr class="darkColumn" >
      <td width="15%" class="grayStyle" id="f_name"><font color="red">*</font>场次名称</td>
      <td class="brightStyle"><input name="examTurn.name" value="<@i18nName examTurn?if_exists/>" maxlength="15"/>
     </tr>
     <tr class="darkColumn">
      <td class="grayStyle" id="f_beginTime"><font color="red">*</font>开始时间</td>
      <td class="brightStyle"><input name="beginTime" value="<@getTimeStr examTurn.beginTime?default("")/>" maxLength="5"/>格式(24小时),如:08:00
      </td>
     </tr>
     <tr class="darkColumn">
      <td class="grayStyle" id="f_endTime"><font color="red">*</font>结束时间</td>
      <td class="brightStyle"><input name="endTime" value="<@getTimeStr examTurn.endTime?default("")/>" maxLength="5"/>格式(24小时),如:15:15
      </td>
      </tr>
    </table>
    </form>
 <script>
   function save(){
     var form=document.examTurnForm;
     var a_fields = {
         'examTurn.name':{'l':'场次名称', 'r':true, 't':'f_name'},
         'beginTime':{'l':'开始时间', 'r':true, 't':'f_beginTime','f':'shortTime'},
         'endTime':{'l':'结束时间', 'r':true, 't':'f_endTime','f':'shortTime'}
     };
	 var v = new validator(form , a_fields, null);
	 if (v.exec()) {
         if(form['beginTime'].value>=form['endTime'].value){
           alert("起始时间应晚于结束时间");
           return;
         }
         form.action = "examTurn.do?method=save";
         form.submit();
     }
   }
   var bar = new ToolBar('examTurnBar','<#if examTurn.stdType?exists><@i18nName examTurn.stdType/><#else>系统默认</#if>考试场次信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.save"/>",save,'save.gif');
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/>