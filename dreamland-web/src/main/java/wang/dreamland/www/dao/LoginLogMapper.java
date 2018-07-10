package wang.dreamland.www.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import wang.dreamland.www.entity.LoginLog;

public interface LoginLogMapper extends Mapper<LoginLog>{

}