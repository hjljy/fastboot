package cn.lsbly.bootService.service.demo.impl;

import cn.lsbly.bootService.pojo.demo.po.DemoPo;
import cn.lsbly.bootService.mapper.demo.DemoMapper;
import cn.lsbly.bootService.service.demo.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-07-03
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoPo> implements IDemoService {

}
