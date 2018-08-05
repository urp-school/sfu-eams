<#include "/templates/head.ftl"/>
<BODY>
    <table id="backBar" width="100%"></table>
    <table class="frameTable_title" width="100%">
        <tr>
           <form method="post"  name="actionForm" onsubmit="return false;">
                <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
	<table align="center" width="100%" class="listTable">
            <input type="hidden" name="statistic" value="statistic"/>
            <input type="hidden" name="departIdSeq" value=""/>
            <input type="hidden" name="stdTypeIdSeq" value=""/>
            <input type="hidden" name="calendarIdSeq" value="${calendar.id}"/>
		  	<tr>
		  		<td class="darkColumn" colspan="2" align="center">统计评教结果</td>
		  	</tr>
		  	<tr>
		  		<td  align="center" class="grayStyle" width="10%"><@msg.message key="entity.studentType"/></td>
		  		<td class="grayStyle">
		  			<table>
	                    <tr>
	                        <td>
	                            <select name="StdTypes" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])">
						        	<#list calendarStdTypes as stdType>
						        		<option value="${stdType.id}">${stdType.name}</option>
						        	</#list>
					        	</select>
				        	</td>
				           	<td>
					            <input OnClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" type="button" value="&gt;"> 
					            <br>
					            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])" type="button" value="&lt;"> 
				            </td>
				            <td>
						        <select name="SelectedStdType" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])"></select>
	            	        </td>
				        </tr>
			         </table>
		         </td>
		  	</tr>
		  	<tr>
		  		<td align="center" class="grayStyle">学年度学期</td>
		  		<td class="brightStyle">
		  			<#if stdType?exists>${stdType.name}<#else>${calendar.studentType.name}</#if>,${calendar.year},${calendar.term}
		  		</td>
		  	</tr>
		  	<input type="hidden" name="searchFormFlag" value=""/>
		  	<tr>
		  		<td align="center" class="grayStyle">部门名称</td>
		  		<td class="brightStyle">
		  			<table>
			  			<tr>
				  			<td>
				  				<select name="departments" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" >
			        				<#list departmentList?sort_by("name") as department>
				        				<option value="${department.id}">${department.name}</option>
			        				</#list>
				        		</select>
				  			</td>
				  			<td>
				  				<input OnClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" type="button" value="&gt;"> 
				            		<br>
				            	<input OnClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])" type="button" value="&lt;"> 
				  			</td>
				  			<td>
				  				<select name="Selecteddepartments" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])">
				         		</select>
				  			</td>
			  			</tr>
		  			</table>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td align="center" colspan="2" class="darkColumn">
		  			<input type="button" value="<@bean.message key="field.questionnaireStatistic.statisticEvaluateResults"/>" name="button1" onClick="doStatistic()" class="buttonStyle" title="非明细评教统计。"/>
		  		</td>
		  	</tr>
	  	</form>
    </table>
    <script>
        var bar = new ToolBar('backBar','统计评教结果',null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("评教明细统计", "initStat()", "action.gif", "评教结果管理的统计（即，初始化/重新统计）。");

        var form = document.actionForm;
        
        // 初始化统计
        function initStat() {
            clearBarInfo();
            $("message").innerHTML = "正在进入初始化/重新统计页面，请稍候……";
            form.action = "evaluateDetailStat.do?method=index";
            form.target = "_self";
            form["searchFormFlag"].value = "noStat";
            form.submit();
        }

        function clearBarInfo() {
            if ("" != $("message").innerHTML) {
                $("message").innerHTML = null;
                $("message").style.display = "none";
            }
            if ("" != $("error").innerHTML) {
                $("error").innerHTML = null;
                $("error").style.display = "none";
            }
        }

	    function doStatistic(){
	        form.departIdSeq.value=getAllOptionValue(form.Selecteddepartments);
	        form.stdTypeIdSeq.value=getAllOptionValue(form.SelectedStdType);
	        if(""==form.stdTypeIdSeq.value){
	            alert("请选择学生类别");
	            return;
	        }
	        if(""==form.departIdSeq.value){
	            alert("请选择部门");
	            return 
	        }
	        if(confirm("<@bean.message key="field.questionnaireStatistic.statisticAffirm"/>")){
	        	form["button1"].disabled = true;
	            form.action ="questionnaireStat.do?method=stat";
	            message.innerHTML+='<font color="red" size="5"><@bean.message key="workload.statisticInfo"/></font>'; 
	            form.submit();
	        }
	    }
    </script>
</body>
<#include "/templates/foot.ftl"/>