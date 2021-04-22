package cn.hjljy.fastboot.pojo.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author hjljy
 * 会员详细信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberDto对象", description = "会员具体详情信息")
public class MemberDto extends MemberBaseInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
}
