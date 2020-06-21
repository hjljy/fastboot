package cn.hjljy.fastboot.autoconfig;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yichaofan
 * @date 2020/6/21 19:45
 * @apiNote service层全局事务管理器  通过切面的方式进行事务的处理。这个指定了具体的方法名称
 */
@Configuration
public class TransactionConfiguration {

    /**
     * 配置全局事务的切点为service层的所有方法  AOP切面表达式 可参考（https://blog.csdn.net/ycf921244819/article/details/106599489）
     * TODO 设置service层所在位置
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* cn.hjljy..*.service..*.*(..))";

    /**
     * 注入事务管理器
     */
    @Autowired
    private TransactionManager transactionManager;


    /**
     * 配置事务拦截器
     */
    @Bean
    public TransactionInterceptor txAdvice() {

        RuleBasedTransactionAttribute txAttr_REQUIRED = new RuleBasedTransactionAttribute();
        txAttr_REQUIRED.setName("REQUIRED事务");
        //设置事务传播机制，默认是PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //设置异常回滚为Exception  默认是RuntimeException
        List<RollbackRuleAttribute> rollbackRuleAttributes = new ArrayList<>();
        rollbackRuleAttributes.add(new RollbackRuleAttribute(Exception.class));
        txAttr_REQUIRED.setRollbackRules(rollbackRuleAttributes);

        RuleBasedTransactionAttribute txAttr_REQUIRED_READONLY = new RuleBasedTransactionAttribute();
        txAttr_REQUIRED_READONLY.setName("SUPPORTS事务");
        //设置事务传播机制，PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        //设置异常回滚为Exception  默认是RuntimeException
        txAttr_REQUIRED_READONLY.setRollbackRules(rollbackRuleAttributes);
        txAttr_REQUIRED_READONLY.setReadOnly(true);

        /*事务管理规则，声明具备事务管理的方法名*/
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        //方法名规则限制，必须以下列开头才会加入事务管理当中
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("insert*", txAttr_REQUIRED);
        source.addTransactionalMethod("submit*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("del*", txAttr_REQUIRED);
        source.addTransactionalMethod("remove*", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        source.addTransactionalMethod("exec*", txAttr_REQUIRED);
        source.addTransactionalMethod("set*", txAttr_REQUIRED);

        //对于查询方法，根据实际情况添加事务管理 可能存在查询多个数据时，已查询出来的数据刚好被改变的情况
        source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("select*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("is*", txAttr_REQUIRED_READONLY);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
