//$Id: RoomPrice.java,v 1.1 2007-3-10 下午06:29:52 chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-10         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.resource;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;

/**
 * 各类教室的收费价格
 * 
 * @author chaostone
 * 
 */
public class RoomPriceConfig extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399351895442905413L;

	private ClassroomType roomConfigType = new ClassroomType();

	private RoomPriceCatalogue catalogue = new RoomPriceCatalogue();

	private Integer minSeats;

	private Integer maxSeats;

	private Float price;

	public Integer getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(Integer maxSeats) {
		this.maxSeats = maxSeats;
	}

	public Integer getMinSeats() {
		return minSeats;
	}

	public void setMinSeats(Integer minSeats) {
		this.minSeats = minSeats;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public ClassroomType getRoomConfigType() {
		return roomConfigType;
	}

	public void setRoomConfigType(ClassroomType roomConfigType) {
		this.roomConfigType = roomConfigType;
	}

	public RoomPriceCatalogue getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(RoomPriceCatalogue catalogue) {
		this.catalogue = catalogue;
	}

}
