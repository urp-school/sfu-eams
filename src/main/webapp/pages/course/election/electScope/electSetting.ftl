<#include "/templates/head.ftl"/>
<body>
<table id="settingBar" width="100%"></table>
<table width="100%" align="center" class="listTable" >
     <form name="settingForm" action="electScope.do?method=batchUpdateEelectInfo" method="post" onsubmit="return false;">
       <input type="hidden"  name="updateSelected" value="${RequestParameters['updateSelected']}"/>
       <input type="hidden"  name="taskIds" value="${RequestParameters['taskIds']?if_exists}"/>
		<#list RequestParameters['params']?if_exists?split("&") as param>
		    <#if (param?length>2)>
		    <input  type="hidden" name="${param[0..param?index_of('=')-1]}" value="${param[param?index_of('=')+1..param?length-1]}"/>
		    </#if>
		</#list>
       <input type="hidden" name="setting.isElectable" value="${RequestParameters['isElectable']?if_exists}">
	   <tr class="darkColumn" >
	     <td align="center" colspan="2">设置可选参数</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="15%">删除选课范围</td>
	     <td class="grayStyle">
	      &nbsp;<input type="checkbox" name="setting.removeExistedScope"/>清除已有选课范围
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">教学班</td>
	     <td class="grayStyle">
	       <input type="hidden" name="setting.initByTeachClass" value=""/>
          &nbsp;<input type="checkbox" name="initByTeachClassCtl" onclick="changeInitByTeachClass(this)"/>&nbsp;将教学班的信息作为选课范围,请选择教学班复制到选课范围的项目(学生类别和部门为必选项)<br>
	      &nbsp;<input type="checkbox" name="initByTeachClass" value="1"/>所在年级
	      <input type="checkbox" name="initByTeachClass" value="2"/><@msg.message key="entity.studentType"/>
	      <input type="checkbox" name="initByTeachClass" value="4"/><@msg.message key="entity.department"/>
	      <input type="checkbox" name="initByTeachClass" value="8"/><@msg.message key="entity.speciality"/>
	      <input type="checkbox" name="initByTeachClass" value="16"/>方向
	      <input type="checkbox" name="initByTeachClass" value="32"/>班级
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">行政班</td>
	     <td class="grayStyle">
	        <table  class="formTable">
	           <tr><td colspan="5">&nbsp;&nbsp;根据行政班，同一课程代码的选课范围.下面范围指除同一课程代码之外的其他条件。<input type="hidden" name="setting.addScopeByAdminClass" value=""/></td>
	           <tr><td>范围<br>
		          <select name="shareCondition" id="shareCondition" multiple size=5 style="width:100px" onDblClick="JavaScript:moveSelectedOption(this.form['shareCondition'], this.form['allCondition'])">
			      </select>
	               </td>
	               <td>
			        <input OnClick="JavaScript:moveSelectedOption(this.form['shareCondition'], this.form['allCondition'])" type="button" value="&gt;">
			        <br> 
			        <input OnClick="JavaScript:moveSelectedOption(this.form['allCondition'], this.form['shareCondition'])" type="button" value="&lt;"> 
                   </td>
                   <td>可选<br>
		          <select name="allCondition" id="allCondition" multiple size=5 style="width:100px" >
				    <option value="1">所在年级</option>
				    <option value="2"><@msg.message key="entity.studentType"/></option>
				    <option value="4"><@msg.message key="entity.department"/></option>
				    <option value="8"><@msg.message key="entity.speciality"/></option>
				    <option value="16">方向</option>
			      </select>
	               </td>
	               <td>
			        <input OnClick="JavaScript:moveSelectedOption(this.form['allCondition'], this.form['shareScope'])" type="button" value="&gt;"> 
			        <br>
			        <input OnClick="JavaScript:moveSelectedOption(this.form['shareScope'], this.form['allCondition'])" type="button" value="&lt;">
		          </td>
		          <td>共享<br>
          <select name="shareScope" id="shareScope" multiple size=5 style="width:100px" onDblClick="JavaScript:moveSelectedOption(this.form['shareScope'], this.form['allCondition'])">
	      </select>
	      </td>
            </tr>
            </table>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">批量操作</td>
	     <td class="grayStyle">
	       <input type="radio" name="setting.batchAction" value="add" checked/>添加
	       <input type="radio" name="setting.batchAction" value="remove"/><@msg.message key="action.delete"/>
	       <input type="radio" name="setting.batchAction" value="merge"/>合并
	       <table width="100%" class="formTable">
	       <tr class="infoTitle">
	         <td align="center">所在年级(空不做限制)</td>
	         <td colspan="2"><input type="text" name="scope.enrollTurns" value="" style="width:50%" maxlength="25"/></td>
	       </tr>
	       <tr class="infoTitle">
		       <td width="25%" align="center"><font color="red">*</font><@msg.message key="entity.studentType"/>(必填)</td>
		       <td>
		           <input type="hidden" name="scope.stdTypeIds" value=""/>
		           <input type="text" name="Namescope.stdTypeIds" maxlength="200" readOnly value="" style="width:80%"/>
			       <input type="button" value="搜索" class="buttonStyle" onClick="displaySelector('scope.stdTypeIds')"/>
		       </td>
	       </tr>
	       <tr class="infoTitle">
	       	   <td align="center"><font color="red">*</font><@msg.message key="entity.department"/>(全校代表不限制)</td>
		       <td>
			       <input type="hidden" name="scope.departIds" value=""/>
			       <input type="text" name="Namescope.departIds" value="" readOnly style="width:80%" maxlength="200"/>
			       <input type="button" value="搜索" class="buttonStyle" onClick="displaySelector('scope.departIds')"/>
		       </td>
	       </tr>
	       <tr class="infoTitle">
	       	   <td align="center"><@msg.message key="entity.speciality"/></td>
		       <td>
		       	   <input type="hidden" name="scope.specialityIds" value=""/>
		           <input type="text" name="Namescope.specialityIds" value="" readOnly style="width:80%" maxlength="200"/>
		           <input type="button" value="搜索" class="buttonStyle" onClick="displaySelector('scope.specialityIds')"/>
		       </td>
	       </tr>
	       <tr class="infoTitle">
	       	   <td align="center">方向</td>
		       <td>
      		       <input type="hidden" name="scope.aspectIds" value=""/>
		           <input type="text" name="Namescope.aspectIds" value="" maxlength="200" readOnly style="width:80%"/>
		           <input type="button" value="搜索" class="buttonStyle" onClick="displaySelector('scope.aspectIds')"/>
		       </td>
	       </tr>
	       <tr class="infoTitle">
	       	   <td align="center">班级</td>
		       <td>
		           <input type="hidden" name="scope.adminClassIds" value=""/>
		           <input type="text" name="Namescope.adminClassIds" value="" maxlength="200" readOnly style="width:80%"/>
		           <input type="button" value="搜索" class="buttonStyle" onClick="displaySelector('scope.adminClassIds')"/>
               </td>
	       </tr>
	       </table>
	     </td>
	   </tr>
	   <tr>
	     <td rowspan="2" class="grayStyle">人数上下限</td>
		   <td class="grayStyle">
		      &nbsp;<input type="radio" id="model0" onclick="changeStatus('model0');" name="setting.updateStdMaxCountByRoomConfig"/>按照安排的教室容量设置人数上限<br>
		      &nbsp;<input type="radio" id="model1" onclick="changeStatus('model1');" name="setting.updateStdMaxCountByPlanCount"/>按照任务的计划人数设置人数上限<br>
		      &nbsp;<input type="radio" id="model2" onclick="changeStatus('model2');" name="setting.updateMinWithRoomConfigAndPlanCount"/>按照以上人数上限最小值进行<br>
		      &nbsp;<input type="radio" id="model3" onclick="changeStatus('model3');" name="setting.settingMaxStdCount"/>统一设置人数上限
		      &nbsp;<input type="text" name="setting.maxStdCount" maxlength="5" onfocus="document.getElementById('model3').checked=true;changeStatus('model3');"/>
		   </td>
	   </tr>
	   <tr>
		   <td class="grayStyle">
		      &nbsp;<input type="checkbox" name="setting.settingMinStdCount"/>统一设置人数下限
		      &nbsp;<input type="text" name="setting.minStdCount" maxlength="5"/>
		   </td>
	   </tr>
	   <tr>
		   <td class="grayStyle">设置是否允许退课</td>
		   <td>
		    <select name="setting.isCancelable" style="width:100px">
             <option value="" selected>保持不变</option>
             <option value="1"><@bean.message key="common.yes"/></option>
             <option value="0"><@bean.message key="common.no"/></option>
            </select>
		   </td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="6" align="center">
	       <input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
	   
	 </form>
</table>
     <div id="selectorDIV" style="backGround-Color:white;display:none;width:200px;height:180px;position:absolute;top:100px;left:200px;border:solid;border-width:1px; ">
      <table id="selectBar"></table>
      <script>
       var selectToolBar = new ToolBar("selectBar","请选择",null,true,true);
       selectToolBar.addItem("确定","setSelectedItem()");
       selectToolBar.addItem("关闭","closeSelectDIV()");
      </script>
      <table width="100%">
      <tr>
	      <td colspan="3">
	      <select id="select" multiple size="15" style="width:100%"></select>
	      </td>
      </tr>
      </table>
     </div>
<script>
    function changeStatus(radioInputId){
        var checked =  document.getElementById(radioInputId).checked;
        for(var i=0;i<4;i++){
            document.getElementById("model"+i).checked=!checked;
        }
        document.getElementById(radioInputId).checked=checked;
    }
    
    function save(){
       var form = document.settingForm;
       form.action="electScope.do?method=batchUpdateEelectInfo";
       var errorInfo="";
       errorInfo+=validEnrollTurns(form['scope.enrollTurns'].value);

       if(form['setting.settingMinStdCount'].checked)
          if(!/^\d+$/.test(form['setting.minStdCount'].value))
             errorInfo+="下限格式不正确\n";
       if(form['setting.settingMaxStdCount'].checked)
          if(!/^\d+$/.test(form['setting.maxStdCount'].value))
             errorInfo+="上限格式不正确\n";
       if(errorInfo!="") {alert(errorInfo);return;}
       var initByTeachClass=0;
       var initByTeachClassInputs=document.getElementsByName('initByTeachClass');

       for(var i=0;i<6;i++){
        input=initByTeachClassInputs[i];
          if(input.checked){
          	initByTeachClass+=parseInt(input.value);
          }
       }
       
       form['setting.initByTeachClass'].value=initByTeachClass;
       var shareConditionVal=0;
       shareCondition=document.getElementById("shareCondition");
       for(var i=0;i<shareCondition.options.length;i++){
         shareConditionVal+=parseInt(shareCondition.options[i].value);
       }
       var shareScopeVal=0;
       shareScope=document.getElementById("shareScope");
       for(var i=0;i<shareScope.options.length;i++){
         shareScopeVal+=parseInt(shareScope.options[i].value);
       }
       addInput(form,"setting.shareScopeCondition",shareConditionVal);
       addInput(form,"setting.addScopeByAdminClass",shareScopeVal);
       form.submit();
    }
    function validEnrollTurns(turns){
      var form = document.settingForm;
      var errorInfo="";
      if(turns!=""){
	       if(turns.indexOf(',')!=0)
	          turns = ','+turns;
	       if(turns.lastIndexOf(',')!=turns.length-1)
	          turns = turns +',';
	       form['scope.enrollTurns'].value=turns;
	       var turnArray = turns.substring(1,turns.lastIndexOf(',')).split(",");
	       for(var j=0;j<turnArray.length;j++)
	          if(!/^\d\d\d\d-[1-9]$/.test(turnArray[j]))
	             errorInfo+=turnArray[j]+"<@bean.message key="error.enrollTurn"/>"+"\n";
       }
       return errorInfo;
    }
    function changeInitByTeachClass(obj){
       var initByTeachClassInputs=document.getElementsByName('initByTeachClass');
       for(var i=0;i<6;i++){
          input=initByTeachClassInputs[i];
          input.checked=obj.checked;
       }
    }
    var stdTypeArray = new Array();
    <#list stdTypeList?sort_by("code") as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var departArray = new Array();
    <#list sort_byI18nName(departmentList) as depart>
    departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    var specialities = new Array();
    <#list sort_byI18nName(specialityList) as specilaity>
        specialities[${specilaity_index}]={'id':'${specilaity.id}','code':'${specilaity.code}','name':'<@i18nName specilaity/>','departId':'${specilaity.department?if_exists.id?if_exists}','stdTypeId':'${specilaity.stdType.id}'};
    </#list>
    var aspects = new Array();
    <#list sort_byI18nName(specialityAspectList) as aspect>
        aspects[${aspect_index}]={'id':'${aspect.id}','code':'${aspect.code}','name':'<@i18nName aspect/>','specialityId':'${aspect.speciality?if_exists.id?if_exists}'};
    </#list>
    var adminClasses = new Array();
    <#list sort_byI18nName(adminClassList) as adminClass>
        adminClasses[${adminClass_index}]={'id':'${adminClass.id}','code':'${adminClass.code}','name':'${adminClass.name}','departId':'${adminClass.department.id}','specialityId':'${adminClass.speciality?if_exists.id?if_exists}','aspectId':'${adminClass.aspect?if_exists.id?if_exists}','enrollTurn':'${adminClass.enrollYear}','stdTypeId':'${adminClass.stdType.id}'};
    </#list>
    var  selectedCatalog ="";
    function displaySelector(catalog){
        selectedCatalog=catalog;
        var form=document.settingForm;
        DWRUtil.removeAllOptions('select');
        DWRUtil.addOptions('select',[{'id':'','name':'请选择..'}],'id','name');
        if(catalog=="scope.stdTypeIds"){
            DWRUtil.addOptions('select',stdTypeArray,'id','name');
        }else if(catalog=="scope.departIds"){
            DWRUtil.addOptions('select',departArray,'id','name');
        }else if(catalog=="scope.specialityIds"){
            var departIds = form['scope.departIds'].value;
            var stdTypeIds=form['scope.stdTypeIds'].value;
            if(""!=departIds){
                var thisSpecialities=new Array();
	            for(var i=0;i<specialities.length;i++){
	                if(departIds.indexOf(','+specialities[i].departId+',')!=-1&&
	                   stdTypeIds.indexOf(','+specialities[i].stdTypeId+',')!=-1){
	                    thisSpecialities.push(specialities[i]);
	                }
	            }
                DWRUtil.addOptions('select',thisSpecialities,'id','name');
            }
        }else if(catalog=="scope.aspectIds"){
            var specialityIds = form['scope.specialityIds'].value;
            if(""!=specialityIds){
                var thisAspects=new Array();
	            for(var i=0;i<aspects.length;i++){
	                if(specialityIds.indexOf(','+aspects[i].specialityId+',')!=-1){
	                    thisAspects.push(aspects[i]);
	                }
	            }
                DWRUtil.addOptions('select',thisAspects,'id','name');
            }
        }else if(catalog=="scope.adminClassIds"){
            var enrollTurns =form['scope.enrollTurns'].value;
            var stdTypeIds=form['scope.stdTypeIds'].value;
            var departIds = form['scope.departIds'].value;
            var specialityIds = form['scope.specialityIds'].value;
            var aspectIds = form['scope.aspectIds'].value;
            
            if(""!=enrollTurns||""!=departIds||""!=specialityIds|""!=aspectIds){
                var thisClassess=new Array();
	            for(var i=0;i<adminClasses.length;i++){
	                if(enrollTurns!=""&&enrollTurns.indexOf(adminClasses[i].enrollTurn)==-1) continue;
	                if(stdTypeIds!=""&&stdTypeIds.indexOf(','+adminClasses[i].stdTypeId+',')==-1) continue;
	                if(departIds!=""&&departIds.indexOf(','+adminClasses[i].departId+',')==-1) continue;
	                if(specialityIds!=""&&specialityIds.indexOf(','+adminClasses[i].specialityId+',')==-1) continue;
	                if(aspectIds!=""&&aspectIds.indexOf(','+adminClasses[i].aspectId)==-1+',') continue;
	                thisClassess.push(adminClasses[i]);
	            }
                DWRUtil.addOptions('select',thisClassess,'id','name');
            }
        }
        
        setSelectedSeq($('select'),form[catalog].value);
        $('selectorDIV').style.display="block";
    }
    function closeSelectDIV(){
       selectorDIV.style.display='none';
    }
    function setSelectedItem(){
        var options = select.options;
        var form=document.settingForm;
        var id=",";
        var name="";
        for(var i=0;i<options.length;i++){
            if(options[i].selected&&options[i].value!=""){
                id+=options[i].value+",";
                name+=options[i].text+" ";
            }
        }
        form[selectedCatalog].value=id;
        form['Name'+selectedCatalog].value=name;
        $('selectorDIV').style.display="none";
    }
    var bar = new ToolBar("settingBar","选课范围,人数上限,是否自由退课设置",null,true,true);
    bar.addBack("<@msg.message key="action.back"/>");
</script>
<body>
<#include "/templates/foot.ftl"/>