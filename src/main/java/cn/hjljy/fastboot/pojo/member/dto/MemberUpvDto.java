package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.pojo.member.po.MemberUpv;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 会员权益成长值获取扣减计算规则表
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberUpvDto对象", description = "会员权益成长值获取扣减计算规则表")
public class MemberUpvDto extends MemberUpv implements Serializable {

    private static final long serialVersionUID = 1L;
}
