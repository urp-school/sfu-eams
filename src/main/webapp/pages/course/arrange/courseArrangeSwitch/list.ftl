<#include "/templates/head.ftl"/>
 <body>
 <table id="myBar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="courseArrangeSwitchId"/>
           <@table.sortTd width="30%" text="学生类别" id="courseArrangeSwitch.calendar.studentType.name"/>
          <@table.sortTd width="30%" text="学年" id="courseArrangeSwitch.calendar.year"/>
           <@table.sortTd width="20%" text="学期" id="courseArrangeSwitch.calendar.term"/>
 	      <td width="20%">是否发布</td>
   </@>
   <@table.tbody datas=courseArrangeSwitchList;courseArrangeSwitch>
      <@table.selectTd id="courseArrangeSwitchId" value=courseArrangeSwitch.id/>
      <td>${(courseArrangeSwitch.calendar.studentType.name)?if_exists}</td>
      <td>${(courseArrangeSwitch.calendar.year)?if_exists}</td>
	  <td>${(courseArrangeSwitch.calendar.term)?if_exists}</td>
	  <td> 
	    ${(courseArrangeSwitch.isPublished)?string("是","否")?default('')}
	</td>
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="courseArrangeSwitch.do" entity="courseArrangeSwitch">
     <input type="hidden"  name ="params" value="&courseArrangeSwitch.isPublished=${(isOpen)?if_exists}"/>
  </@>
 <script>
   var bar = new ToolBar('myBar','&nbsp;排课发布信息',null,true,true);
   var form =document.actionForm;
   bar.setMessage('<@getMessage/>');
   bar.addItem("<#if "true"==isOpen>关闭<#else>开放</#if>","openedOrClosed()");
   function openedOrClosed() {
			form.action = "courseArrangeSwitch.do?method=openedOrClosed";
			submitId(form, "courseArrangeSwitchId", true);
	}
 </script>

 </body>
 <#include "/templates/foot.ftl"/>