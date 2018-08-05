        <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>查询条件</B></td>
	   </tr>
	    <tr>
	        <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
	    </tr>
	   <tr>
	   	  <td width="40%"><@msg.message key="attr.stdNo"/>：</td>
		  <td><input type="text" name="student.code" maxlength="32" size="10" value="${RequestParameters['student.code']?if_exists}" style="width:100px"/>
		  </td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="attr.personName"/>：</td>
		  <td><input type="text" name="student.name" maxlength="20" size="10" value="${RequestParameters['student.name']?if_exists}" style="width:100px"/>
		  </td>
	   </tr>
	   <tr>
		  <td><@msg.message key="entity.studentType"/>：</td>
		  <td align="bottom">
			  <select id="<#if stdTypeName?exists>${stdTypeName}<#else>stdType</#if>" name="student.type.id" style="width:100px">
			    <option value=""><@msg.message key="common.selectPlease"/>.....</option>
			  </select>
		  </td>
	 </tr>
	   <tr>
	   	  <td><@msg.message key="attr.enrollTurn"/>：</td>
		  <td><input type="text" id="erollYear" name="student.enrollYear" value="${RequestParameters['student.enrollYear']?if_exists}" style="width:100px"></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.college"/>：</td>
	      <td>
	     	<select id="<#if departName?exists>${departName}<#else>department</#if>" name="student.department.id" style="width:100px;">
	         	<option value="${RequestParameters['student.department.id']?if_exists}"><@msg.message key="common.selectPlease"/>...</option>
	        </select>
	      </td>
	   </tr>
	   <tr>
	   		<td><@msg.message key="entity.speciality"/>：</td>
		    <td><select id="<#if specialityName?exists>${specialityName}<#else>speciality</#if>" name="student.firstMajor.id" style="width:100px">
		         	  <option value="${RequestParameters['student.firstMajor.id']?if_exists}"><@msg.message key="common.selectPlease"/>...</option>
		         </select>
		    </td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.specialityAspect"/>：</td>
		  <td><select id="<#if aspectName?exists>${aspectName}<#else>specialityAspect</#if>" name="student.firstAspect.id" style="width:100px">
		         	  <option value="${RequestParameters['student.firstAspect.id']?if_exists}"><@msg.message key="common.selectPlease"/>...</option>
		       </select>
		  </td>
	   </tr>