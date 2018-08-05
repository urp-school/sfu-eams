package com.ekingstar.eams.course.election;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.model.course.task.TeachTask;

public interface ElectCheck {

	public boolean canElect(final TeachTask task, final ElectState state,
			final CourseTakeType takeType);
}
