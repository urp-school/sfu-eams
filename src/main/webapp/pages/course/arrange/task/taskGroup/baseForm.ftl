   <div style="display: block;margin: 0;" class="tab-page" id="tabPage1" >
   <h2 class="tab"><a href="#"><@bean.message key="common.baseInfo"/></a></h2>
   <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
     <form name="taskGroupForm" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="taskGroup.suggest.time.id" value="${taskGroup.suggest.time.id}"/>
     <input type="hidden" name="taskGroup.suggest.time.available" value=""/>
     <input type="hidden" name="task.course.code" value="${taskGroup.course?if_exists.code?if_exists}"/>
     <input type="hidden" name="classroomIds" value=""/>
	 <table width="100%"align="center" class="listTable" >
	   <tr>
	      <input type="hidden" name="taskGroup.id" value="${taskGroup.id}"/>
	     <td class="grayStyle" width="15%" id="f_name">&nbsp;<@bean.message key="attr.infoname" />:</td>	     
	     <td class="brightStyle" ><input type="text" name="taskGroup.name" maxlength="25" value="${taskGroup.name}" /></td>
         <td class="grayStyle" width="3%" rowspan="5" id="f_name"><@bean.message key="attr.groupRooms"/>:</td>	  	
	     <td class="grayStyle" rowspan="5"  align="center">
		    <table align="left" height="100%">
	          <tr>
	           <td>
	            <select name="Rooms" MULTIPLE size="10" style="height:140px;width:150px" onDblClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRoom'])" >
	             <#list roomList?sort_by("name") as room>
	              <option value="${room.id}"><@i18nName room/>/[<@i18nName room.configType/>]/${room.capacityOfCourse}</option>
	             </#list>
	            </select>
	           </td>
	           <td align="center" valign="middle">
	            <br><br>
	            <input OnClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRoom'])" type="button" value="&gt;"/>
	            <br><br>
	            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedRoom'], this.form['Rooms'])" type="button" value="&lt;"/>
	            <br>
	           </td> 
	           <td align="center" class="normalTextStyle">
	            <select name="SelectedRoom" MULTIPLE size="10" style="height:140px;width:150px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedRoom'], this.form['Rooms'])">
	             <#list taskGroup.suggest.rooms?if_exists as room>
	              <option value="${room.id}">${room.name}/[<@i18nName room.configType/>]/${room.capacityOfCourse}</option>
	             </#list>
	            </select>
	           </td>
	          </tr>
	         </table>
         </td>	
	   </tr>
	   <tr>
	     <td class="grayStyle" id="f_priority">&nbsp;<@bean.message key="attr.priority" />：</td>
         <td class="brightStyle">
               <select name="taskGroup.priority" style="width:50px" >
                   <#list 1..9 as i>
                   <option value=${i} <#if i == taskGroup.priority> selected</#if>>${i}</option>
                   </#list>
               </select>[1-9]<@bean.message key="common.increase"/>
         </td>   
	   </tr>
	   <tr bgcolor="#ffffff">
	     <td class="grayStyle"  id="f_isSameTime">&nbsp;时间要求:</td>
	     <td class="brightStyle">
	         <input type="radio" name="taskGroup.isSameTime" <#if taskGroup.isSameTime ==true>checked </#if> value="1"/>同一时间
	         <input type="radio" name="taskGroup.isSameTime" <#if taskGroup.isSameTime !=true>checked </#if> value="0"/>无要求
	     </td>
	   </tr>
	   <#--
	   <tr>
	     <td class="grayStyle" id="f_isClassChange">&nbsp;允许班级变动：</td>
	     <td class="brightStyle">
	         <input type="radio" name="taskGroup.isClassChange" <#if taskGroup.isClassChange ==true>checked </#if> value="1"/>是
	         <input type="radio" name="taskGroup.isClassChange" <#if taskGroup.isClassChange !=true>checked </#if> value="0"/>否
	        </td>
	   </tr>
	   -->
	   <tr>
	     <td class="grayStyle"  id="f_remark">&nbsp;<@bean.message key="attr.arrangeCount"/>：</td>
	     <td class="brightStyle" >${taskGroup.arrangedTaskCount}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" id="f_remark">&nbsp;<@bean.message key="attr.remark"/>：</td>
	     <td class="brightStyle"><input name="taskGroup.suggest.time.remark" value="${taskGroup.suggest.time.remark?if_exists}" maxlength="50"/></td> 	          
	   </tr>
	 </table>
	 <#assign availTime=taskGroup.suggest.time/>
	 <#include "availTimeTable.ftl"/>
	 </form>
   </div>