<#include "/templates/head.ftl"/>
    <table id="bar"></table>
    <form method="post" action="" target="contentFrame" name="actionForm" onsubmit="return false;">
    <table class="frameTable_title">
 		<tr>
		<td>
			<input type="hidden" name="attendStaticReport.calendar.id" value="${calendar.id}" />
          	<input type="hidden" name="calendar.id" value="${calendar.id}" />
			<#include "/pages/course/calendar.ftl"/>
		</td>
		</tr>				       
	</table>
    <table class="frameTable">
        <tr valign="top">
            <td class="frameTable_view" width="20%" align="center">
                    <table width="100%" class="searchTable" >
					    <input type="hidden" name="pageNo" value="1"/>
					    	<tr>
						     <td class="infoTitle" width="40%"><@msg.message key="attr.stdNo"/>:</td>
						     <td>
						      <input type="text" name="attendStaticReport.student.code" value="${RequestParameters['std.code']?if_exists}" maxlength="32" style="width:100px"/>
						     </td>
							</tr>
					    	<tr>
						     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
						     <td>
						      <input type="text" name="attendStaticReport.student.name" value="${RequestParameters['std.name']?if_exists}" maxlength="20" style="width:100px"/>
						     </td>
							</tr>
						   <tr>
						     <td class="infoTitle">班级:</td>
						     <td><input type="text" name="student.adminClasses" value="${RequestParameters['std.enrollYear']?if_exists}" id='std.enrollYear' style="width:100px;"/></td>
						   </tr>
					       <tr>
						     <td class="infoTitle">辅导员:</td>
						     <td>
						          <input  name="attendStaticReport.teacher.name" style="width:100px;" />
					         </td>
							</tr>
					    	<tr>
						     <td class="infoTitle">年份:</td>
						     <td>
						     	<select id="month" name="attendStaticReport.attendYear" style="width:100px;">
						          	<option value=""><@msg.message key="filed.choose"/>...</option>
						          	<#if dateMin?? && dateMax??>
						          	<#list dateMin..dateMax as year>
						            <option value="${year}">${year}年</option>
						            </#list>
						            </#if>
						    	</select>
					         </td> 
					        </tr>
					    	<tr>
						     <td class="infoTitle">月份:</td>
						     <td>
						     	<select id="month" name="attendStaticReport.attendMonth" style="width:100px;">
						          	<option value=""><@msg.message key="filed.choose"/>...</option>
						            <option value="1">1月</option>
						            <option value="2">2月</option>
						            <option value="3">3月</option>
						            <option value="4">4月</option>
						            <option value="5">5月</option>
						            <option value="6">6月</option>
						            <option value="7">7月</option>
						            <option value="8">8月</option>
						            <option value="9">9月</option>
						            <option value="10">10月</option>
						            <option value="11">11月</option>
						            <option value="12">12月</option>
						          </select>
					         </td> 
					        </tr> 
						   <tr>
						     <td >月缺勤率:</td>
						     <td >
					           <input name="monthAbesenceStart" style="width:40" />%-<input name="monthAbesenceEnd" style="width:40" />%
					        </tr>
						   <tr>
						     <td >学期缺勤率:</td>
						     <td>
					           <input name="termAbesenceStart" style="width:40" />%-<input name="termAbesenceEnd" style="width:40" />%
					         </td>
					        </tr>
						    <tr align="center" height="30">
						     <td colspan="2">
							     <button style="width:60px" onClick="search(1)"><@msg.message key="action.query"/></button>
						     </td>
						    </tr>
					  </table>
                </form>
            </td>
            <td>
                <iframe name="contentFrame" id="contentFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "学生考勤报表（教务处）", null, true, true);
        bar.setMessage('<@getMessage/>');
        function search() {
           var form = document.actionForm;
           form.action="attendReportFunc.do?method=search";
           form.target="contentFrame";
           form.submit();
        }
        search();
        
    </script>
<#include "/templates/foot.ftl"/>