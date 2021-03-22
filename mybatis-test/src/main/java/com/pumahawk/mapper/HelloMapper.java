package com.pumahawk.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.pumahawk.HelloDAO;

@Repository
public interface HelloMapper {

	@Select("select * from hello where id = #{id}")
	public HelloDAO getHelloById(BigDecimal id);
}
