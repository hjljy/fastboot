package cn.hjljy.fastboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @author 海加尔金鹰
 * @apiNote 自定义基础Service
 * @since 2020/11/9 21:44
 */
public class BaseService<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> {
    /**
     * 根据实体属性进行查询
     * @param t 对应实体
     * @return 集合
     */
    public T selectOne(T t){
        QueryWrapper<T> queryWrapper =new QueryWrapper<>();
        queryWrapper.setEntity(t);
        return this.baseMapper.selectOne(queryWrapper);
    }
    /**
     * 根据实体属性进行查询
     * @param t 对应实体
     * @return 集合
     */
    public List<T> selectList(T t){
        QueryWrapper<T> queryWrapper =new QueryWrapper<>();
        queryWrapper.setEntity(t);
        return this.baseMapper.selectList(queryWrapper);
    }
}
