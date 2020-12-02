package com.swain.common.utils.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 对象转换
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {

//    @Mapping(source = "fieldId", target = "id")
//    DiyFieldInfo toModel(ActivitySingupDiyFieldVo activitySingupDiyFieldVo);
}
