package cn.hjljy.fastboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author yichaofan
 * @apiNote todo
 * @since 2020/11/9 21:44
 */
public class BaseService<M extends BaseMapper<T>,T> extends ServiceImpl {

    public T selectOne(T t){
        QueryWrapper<T> queryWrapper =new QueryWrapper<>();
        queryWrapper.setEntity(t);
        return (T) this.baseMapper.selectOne(queryWrapper);
    }
}
