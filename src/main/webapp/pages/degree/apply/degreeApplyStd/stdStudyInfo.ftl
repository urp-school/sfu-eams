<tr>
    <td colspan="<#if colspans?exists>${colspans}<#else>3</#if>" rowspan="2" class="title">发表论文数</td>
    <td class="title" colspan="2">国内刊物</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.inSideMagzas" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.inSideMagzas)?if_exists}<#else>${studyResults["inside"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.inSideMagzas)?if_exists}<#else>${studyResults["inside"]?if_exists}</#if></td>
    <td class="title" colspan="2">国外刊物</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.outSideMagzas" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.outSideMagzas)?if_exists}<#else>${studyResults["abroad"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.outSideMagzas)?if_exists}<#else>${studyResults["abroad"]?if_exists}</#if></td>
    <td width="7%" class="title">国内会议</td>
    <td width="7%"><input type="hidden" name="degreeApply.thesisNumStandard.inSideMeeting" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.inSideMeeting)?if_exists}<#else>${studyResults["meeting1"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.inSideMeeting)?if_exists}<#else>${studyResults["meeting1"]?if_exists}</#if></td>
    <td class="title" colspan="2">国际会议</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.outSideMeeting" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.outSideMeeting)?if_exists}<#else>${studyResults["meeting2"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.outSideMeeting)?if_exists}<#else>${studyResults["meeting2"]?if_exists}</#if></td>
  </tr>
  <tr> 
    <td class="title" colspan="2">SCI收录数</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.scicount" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.scicount)?if_exists}<#else>${studyResults["index1"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.scicount)?if_exists}<#else>${studyResults["index1"]?if_exists}</#if></td>
    <td colspan="2"class="title">EI收录数</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.eicount" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.eicount)?if_exists}<#else>${studyResults["index2"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.eicount)?if_exists}<#else>${studyResults["index2"]?if_exists}</#if></td>
    <td class="title">ISTP收录数</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.istpcount" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.istpcount)?if_exists}<#else>${studyResults["index3"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.istpcount)?if_exists}<#else>${studyResults["index3"]?if_exists}</#if></td>
    <td colspan="2" class="title"></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="<#if colspans?exists>${colspans}<#else>3</#if>" class="title">获得国家级奖次数</td>
    <td colspan="2" class="title">一等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.nationNO1count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO1count)?if_exists}<#else>${studyResults["awards1"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO1count)?if_exists}<#else>${studyResults["awards1"]?if_exists}</#if></td>
    <td colspan="2" class="title">二等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.nationNO2count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO2count)?if_exists}<#else>${studyResults["awards3"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO2count)?if_exists}<#else>${studyResults["awards3"]?if_exists}</#if></td>
    <td class="title">三等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.nationNO3count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO3count)?if_exists}<#else>${studyResults["awards41"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO3count)?if_exists}<#else>${studyResults["awards41"]?if_exists}</#if></td>
    <td colspan="2" class="title">四等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.nationNO4count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO4count)?if_exists}<#else>${studyResults["awards44"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.nationNO4count)?if_exists}<#else>${studyResults["awards44"]?if_exists}</#if></td>
  </tr>
  <tr>
    <td colspan="<#if colspans?exists>${colspans}<#else>3</#if>" class="title">获得省部级奖次数</td>
    <td colspan="2" class="title">一等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.provinceNo1count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo1count)?if_exists}<#else>${studyResults["awards43"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo1count)?if_exists}<#else>${studyResults["awards43"]?if_exists}</#if></td>
    <td colspan="2" class="title">二等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.provinceNo2count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo2count)?if_exists}<#else>${studyResults["awards8"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo2count)?if_exists}<#else>${studyResults["awards8"]?if_exists}</#if></td>
    <td  class="title">三等奖</td>
    <td><input type="hidden" name="degreeApply.thesisNumStandard.provinceNo3count" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo3count)?if_exists}<#else>${studyResults["awards11"]?if_exists}</#if>"><#if (degreeApply.id)?exists>${(degreeApply.thesisNumStandard.provinceNo3count)?if_exists}<#else>${studyResults["awards11"]?if_exists}</#if></td>
    <td colspan="2" class="title" colspan="2">获专利数</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="title" colspan="<#if colspans?exists>${colspans}<#else>3</#if>">备注</td>
    <td colspan="11"><input type="text" style="width:100%;border: 0px solid ;height:100%" name="degreeApply.stdRemark" value="${(degreeApply.stdRemark)?if_exists}" maxlength="200"/>${(degreeApply.stdRemark)?if_exists}</td>
  </tr>
  <#if "std"==flag?if_exists>
     <#if !colspans?exists>
  	 <input type="hidden" name="degreeApply.totalMark" value="${totleMark}">
  	 </#if>
  	 <input type="hidden" name="degreeApply.specialityMark" value="${speciaMark}">
  	 <input type="hidden" name="degreeApply.level1Subject.id" value="${(level1Subject.id)?if_exists}">
  	 <input type="hidden" name="degreeApply.student.id" value="<#if (degreeApply.id)?exists>${(degreeApply.student.id)?if_exists}<#else>${student.id}</#if>">
  	 <input type="hidden" name="degreeApply.thesisManage.id" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisManage.id)?if_exists}<#else>${(thesisManage.id)?if_exists}</#if>">
  	 <input type="hidden" name="degreeApply.id"  value="${((degreeApply.id))?if_exists}">
  </#if>