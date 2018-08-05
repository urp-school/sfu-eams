<table width="100%" border="0" id="tableId">
	<TR>
		<TD colspan="3">
			一：政治思想:<BR>
			<textarea name="studyInfo.polityIdea" rows="3" style="width:100%">${studyInfo?if_exists.polityIdea?if_exists}</textarea>
		</TD>
		<TD colspan="3">
			二:外语水平:<BR>
			<textarea name="studyInfo.foreignLanguage" rows="3" style="width:100%">${studyInfo?if_exists.foreignLanguage?if_exists}</textarea>
		</TD>
		<TD colspan="3">
			三：理论知识、科研业务能力及获奖情况:<BR>
			<textarea name="studyInfo.theoryKnowledge" rows="3" style="width:100%">${studyInfo?if_exists.theoryKnowledge?if_exists}</textarea>
		</TD>
		<TD colspan="3">
			四：参加国际会议、国际交流活动情况:<BR>
			<textarea name="studyInfo.activity" rows="3" style="width:100%">${studyInfo?if_exists.activity?if_exists}</textarea>
		</TD>
	</TR>
</table>