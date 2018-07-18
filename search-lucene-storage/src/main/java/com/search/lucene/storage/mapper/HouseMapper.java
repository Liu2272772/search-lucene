package com.search.lucene.storage.mapper;

import java.util.List;

import com.search.lucene.beans.AnJuKeHoseEvt;
import com.search.lucene.beans.PageEvt;

public interface HouseMapper {
	
	public AnJuKeHoseEvt findHouseInfoById(Integer id);
	
	public Integer addHouseInfo(AnJuKeHoseEvt houseEvt);
	
	public List<AnJuKeHoseEvt> queryHoseList(PageEvt page);
	
}
