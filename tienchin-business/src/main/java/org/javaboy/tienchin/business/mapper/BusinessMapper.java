package org.javaboy.tienchin.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.tienchin.business.domain.Business;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javaboy.tienchin.business.domain.vo.BusinessSummary;
import org.javaboy.tienchin.business.domain.vo.BusinessVO;
import org.javaboy.tienchin.common.core.domain.model.EchartsPoint;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author javaboy
 * @since 2025-02-07
 */
@Mapper
public interface BusinessMapper extends BaseMapper<Business> {

    List<BusinessSummary> selectBusinessList(BusinessVO businessVO);

    List<EchartsPoint> increaseBusiness(BusinessVO businessVO);

    List<EchartsPoint> totalBusiness(BusinessVO businessVO);
}
