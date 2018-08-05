<#include "/templates/head.ftl"/>
<#import "../cycleType.ftl" as RoomApply/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" width="100%" align="center">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="roomApplyId" value="${roomApply.id}"/>
        <input type="hidden" name="roomApply.id" value="${roomApply.id}"/>
        	<@searchParams/>
        </form>
        <tr>
            <td class="darkColumn" colspan="2" align="center"><b>申请要求</b></td>
        </tr>
        <tr>
	        <td class="title" width="30%">时间要求：</td>
	        <td>从&nbsp;${roomApply.applyTime.dateBegin?string('yyyy年MM月dd日')}&nbsp;到&nbsp;${roomApply.applyTime.dateEnd?string('yyyy年MM月dd日')}，<@RoomApply.cycleValue count=roomApply.applyTime.cycleCount type=roomApply.applyTime.cycleType?string/>&nbsp;${roomApply.applyTime.timeBegin}&nbsp;－&nbsp;${roomApply.applyTime.timeEnd}</td>
        </tr>
        <tr>
	        <td class="title">教室要求：</td>
	        <td>${roomApply.roomRequest?default('')}</td>
        </tr>
        <tr>
	        <td class="title">校区要求：</td>
	        <td><@i18nName (roomApply.schoolDistrict)?if_exists/></td>
        </tr>
        <#if roomApply.classrooms?size!=0>
        	<tr>
        		<td class="title" width="30%">已分配地点：</td>
        		<td><#list roomApply.classrooms?if_exists as classroom><@i18nName classroom/>&nbsp;</#list></td>
        	</tr>
        </#if>
        <tr>
        	<td class="title">已选教室：</td>
        	<td >
				<input id="selectedIds" type="hidden" name="selectedIds">
				<textarea id="selectedNames" name="selectedNames" rows="2" cols="60" readonly style="font-size:10pt"></textarea>
				<button onclick="selectedIds.value='';selectedNames.value='';return false;">清空</button>
			</td>
        </tr>
        <tr>
        	<td colspan="2">
        		<iframe name="contentFrame" src="roomApplyApprove.do?method=getFreeRooms&roomApplyId=${roomApply.id}" width="100%" frameborder="0" scrolling="no"></iframe>
        	</td>
        </tr>
        <tr>
            <td class="darkColumn" colspan="2" align="center"><#if (roomApply.classrooms)?size == 0><button accesskey="A" onclick="approve()">批准审核通过(<u>A</u>)<#else><button accesskey="M" onclick="approve()"><@msg.message key="action.edit"/>(<u>M</u>)</#if></button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", <#if (roomApply.classrooms)?size == 0>"批准审核教室的申请"<#else>"<@msg.message key="action.edit"/>教室分配"</#if>, null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem(<#if (roomApply.classrooms)?size == 0>"批准审核通过"<#else>"<@msg.message key="action.edit"/>"</#if>, "approve()"<#if (roomApply.classrooms)?size!=0>,"update.gif"</#if>);
        bar.addItem("<@msg.message key="action.back"/>", "parent.search()", "backward.gif");

        function approve() {
            var roomIds = document.getElementById("selectedIds").value;
            if(""==roomIds){alert("至少选择一个教室");return;}
            actionForm.action = "roomApplyApprove.do?method=approve&roomIds="+roomIds;
            actionForm.submit();
        }

    	function addValue(id,name){
			if(id!=""){
   		    	var inputIds = document.getElementById("selectedIds");
				addInputValue(id,inputIds);
			}
			if(name!=""){
   		  		var inputNames = document.getElementById("selectedNames");
				addInputValue(name,inputNames);
			}
		}
	
	    function removeValue(id,name){
			if(id!=""){
	   		    var inputIds = document.getElementById("selectedIds");
				removeInputValue(id,inputIds);
			}
			if(name!=""){
	   		   var inputNames = document.getElementById("selectedNames");
				removeInputValue(name,inputNames);
			}
	    }
		function removeInputValue(value,input){
		   if(input.value.indexOf(value)!=-1){
		      var index = input.value.indexOf(value);
		      input.value=input.value.substr(0,index)+input.value.substr(index+value.length+1);
		   }
		}
		function addInputValue(value,input){
			if(input.value.indexOf(value)==-1){
	           input.value+=value+",";
	        }
		}	
    </script>
</BODY>
<#include "/templates/foot.ftl"/>
