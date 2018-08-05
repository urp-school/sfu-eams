<table width="100%" align="center" class="infoTable">
	<tr>
		<td class="title" style="width:25%"><@msg.message key="attr.stdNo"/> ：</td>
		<td>${studyProduct.student?if_exists.code?if_exists}</td>
		<td class="title" style="width:25%"><@msg.message key="attr.personName"/> ：</td>
		<td>${studyProduct.student?if_exists.name}</td>
	</tr>
   <tr>
     <td class="title">会议名称：</td>
     <td>${studyProduct.name?if_exists} </td>
     <td class="title">会议类别：</td>
     <td><@i18nName studyProduct.meetingType/></td>
   </tr>
   <tr>
     <td class="title" >获邀论文题目：</td>
     <td>${studyProduct.topicName}</td>
     <td class="title">举办单位：</td>
     <td>${studyProduct.openDepart}</td>
   </tr>
   <tr>
     <td class="title" >会议时间：</td>
     <td><#if studyProduct.meetingOn?exists>${studyProduct.meetingOn?string("yyyy-MM-dd")}</#if></td>
     <td class="title" >会议地点：</td>
     <td>${studyProduct.meetingAddress?if_exists}</td>
   </tr>  
   <tr>  
     <td class="title">备注信息：</td>
     <td></td>
   </tr>		   	   
   </table>