   <div style="display: block;" id="view0">
	  <table class="searchTable" onKeyDown="javascript:searchCredit(document.stdCreditForm);">
	  <form name="stdCreditForm" method="post" action="creditConstraint.do" onsubmit="return false;">
	    <input type="hidden" name="creditConstraint.calendar.id" value="${calendar.id}"/>
	    <input type="hidden" name="isMajorConstraint" value="0"/>
	    <tr>
	      <td colspan="2" class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchStudent"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="2" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	    </tr>
	    <tr> 
	     <td class="infoTitle"/><@bean.message key="attr.stdNo"/>:</td>
	     <td><input name="creditConstraint.std.code" type="text" value="" style="width:80px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"  ><@msg.message key="attr.personName"/>:</td>
	     <td><input name="creditConstraint.std.name" type="text" value="" style="width:80px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"  ><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input name="creditConstraint.std.enrollYear" type="text" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	      <td> 
	        <select id="stdType2" name="creditConstraint.std.type.id"  style="width:100px;"/>
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="entity.college"/>:</td>
	      <td > 
	        <select id="department2" name="creditConstraint.std.department.id"  style="width:100px;"/>
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	     </td>
	    </tr>
	    <tr>
          <td class="infoTitle"/><@bean.message key="entity.speciality"/>:</td>	    
	      <td align="left"/>         
	        <select id="speciality2" name="creditConstraint.std.firstMajor.id"  style="width:100px;">
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
        <tr>
	      <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	      <td align="left">             
	        <select id="specialityAspect2" name="creditConstraint.std.firstAspect.id"  style="width:100px;">        
	         <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
        </tr>
        <tr>
	      <td class="infoTitle">上限范围:</td>
	      <td align="left">
	        <input name="maxFrom" value="" style="width:40px">-<input name="maxTo" value="" style="width:40px">
	      </td>
        </tr>
        <tr>
	      <td class="infoTitle">已选范围:</td>
	      <td align="left">
	        <input name="electedFrom" value="" style="width:40px">-<input name="electedTo" value="" style="width:40px">
	      </td>
        </tr>
        <tr>
	      <td class="infoTitle">奖励范围:</td>
	      <td align="left">
	        <input name="awardFrom" value="" style="width:40px">-<input name="awardTo" value="" style="width:40px">
	      </td>
        </tr>
        <tr>
	      <td class="infoTitle">绩点范围:</td>
	      <td align="left">
	        <input name="gpaFrom" value="" style="width:40px">-<input name="gpaTo" value="" style="width:40px">
	      </td>
        </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search(this.form,1)" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>               
	    </tr>
	    </form>
	  </table>   
	 </div>
	 
    <div style="display: none;" id="view1">
	  <table class="searchTable"  onKeyDown="javascript:searchCredit(document.majorCreditForm);">	  
	  <form name="majorCreditForm" method="post" action="creditConstraint.do" onsubmit="return false;">
	    <input type="hidden" name="creditConstraint.calendar.id" value="${calendar.id}"/>
	    <input type="hidden" name="isMajorConstraint" value="1"/>
	     <tr>
	      <td  class="infoTitle" align="left" valign="bottom" colspan="2">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchSpeciality"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td  style="font-size:0px" colspan="2">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	    <tr>
	     <td class="infoTitle"  ><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input name="creditConstraint.enrollTurn" type="text" maxlength="7" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle" id="f_department"><@bean.message key="entity.studentType"/>:</td>
	      <td> 
	        <select id="stdType1" name="creditConstraint.stdType.id" style="width:100px;"/>
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="entity.college"/>:</td>
	      <td> 
	        <select id="department1" name="creditConstraint.depart.id" style="width:100px;"/>
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
	    <tr>
          <td class="infoTitle"/><@bean.message key="entity.speciality"/>:</td>	    
	      <td align="left">         
	        <select id="speciality1" name="creditConstraint.speciality.id" style="width:100px;">
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
        <tr>
	      <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	      <td align="left" class="brightStyle">             
	        <select id="specialityAspect1" name="creditConstraint.aspect.id" style="width:100px;">        
	         <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
        </tr>
        <tr>
	      <td class="infoTitle">上限范围:</td>
	      <td align="left">
	        <input name="creditConstraintMaxFrom" value="" style="width:40px" maxlength="3"/>-<input name="creditConstraintMaxTo" value="" style="width:40px" maxlength="3"/>
	      </td>
        </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search(this.form,1)" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>               
	    </tr>
        </form>
	  </table>
    </div>     
     
	  <script>
	     function searchCredit(form) {          
              if (window.event.keyCode == 13){
                   search(form,1);                  
               }
        }
	  </script>