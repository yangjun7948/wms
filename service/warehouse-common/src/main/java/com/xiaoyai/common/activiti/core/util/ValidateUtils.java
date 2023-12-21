package com.xiaoyai.common.activiti.core.util;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.xiaoyai.common.activiti.core.constant.SecurityConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 校验参数工具类
 */
@Component
public class ValidateUtils {
  private static final String ZYKJ_OAUTH_ACCESS = SecurityConstants.zykj_PREFIX + SecurityConstants.OAUTH_PREFIX + "auth";
  private static ValidateUtils validateUtils;
  @Autowired
  private RedisTemplate redisTemplate;

  @PostConstruct
  public void init(){
    validateUtils = this;
    validateUtils.redisTemplate =this.redisTemplate;
  }
  /**
   * 校验token是否合法
   * @param token
   * @return
   */
  public static Boolean checkToken(String token){
    validateUtils.redisTemplate.setKeySerializer(new StringRedisSerializer());
    String key = String.format("%s:%s",ZYKJ_OAUTH_ACCESS,token);
    return validateUtils.redisTemplate.hasKey(key);
  }

  /**
   * 校验参数是否正确，如果不正确的话，则抛出一个异常给前端
   * 在obj为空/false/""的情况下会校验失败抛出异常
   *
   * @param obj     需要校验的参数
   * @param failMsg 异常提示信息
   */
  public static void validate(Object obj, String failMsg) {
    if (obj == null) {
      throw new ApiException(failMsg);
    }
    if (obj instanceof Boolean && !(Boolean) obj) {
      throw new ApiException(failMsg);
    }
    if (obj instanceof String && "".equals(obj)) {
      throw new ApiException(failMsg);
    }
  }

  /**
   * 校验字符串长度
   *
   * @param str 字符串
   * @param min 最小长度 null 为不校验最小长度
   * @param max 最大长度 null 为不校验最大长度
   * @return 校验结果 true/false
   */
  public static boolean validateLength(String str, Integer min, Integer max) {
    return !(str == null || (min != null && str.length() < min) || (max != null && str.length() > max));
  }

  /**
   * Model子类参数重复校验校验
   * 注意：这里的Model必须重写父类的pkVal方法
   *
   * @param key     主键ID，新增有ID，编辑没有ID
   * @param list    校验的字段查询的对象list，这里必须是Model的子类并且重写了父类的pkVal()方法
   * @param failMsg 校验失败返回给前端的提示语
   */
  public static void paramRepeatedValidate(String key, List<? extends Model> list, String failMsg) {
    // 如果id为空，则是新增，只有size =0 才成功
    if (StringUtils.isEmpty(key) && list.size() != 0) {
      throw new ApiException(failMsg);
    }
    // id 不为空，是编辑，>1 是重复的  =1 得看是不是同一个id
    if (!StringUtils.isEmpty(key)) {
      if (list.size() > 1) {
        throw new ApiException(failMsg);
      } else if (list.size() == 1) {
        Model model = list.get(0);
        Object pkValObj = null;
        try {
          Method method = model.getClass().getDeclaredMethod("pkVal");
          //设置为可访问
          method.setAccessible(true);
          pkValObj = method.invoke(model);
        } catch (Exception e) {
          throw new ApiException("服务异常！");
        }

        if (!key.equals(pkValObj.toString())) {
          throw new ApiException(failMsg);
        }
      }
    }
  }

  /**
   * 校验参数是否重复
   *
   * @param key
   * @param list
   * @return 返回校验结果 true 校验成功，没有重复的 false:校验失败，存在重复
   */
  public static boolean paramRepeatedValidate(String key, List<? extends Model> list) {
    // 如果id为空，则是新增，只有size =0 才成功
    if (StringUtils.isEmpty(key) && list.size() != 0) {
      return false;
    }
    // id 不为空，是编辑，>1 是重复的  =1 得看是不是同一个id
    if (!StringUtils.isEmpty(key)) {
      if (list.size() > 1) {
        return false;
      } else if (list.size() == 1) {
        Model model = list.get(0);
        Object pkValObj = null;
        try {
          Method method = model.getClass().getDeclaredMethod("pkVal");
          //设置为可访问
          method.setAccessible(true);
          pkValObj = method.invoke(model);
        } catch (Exception e) {
          return false;
        }

        if (!key.equals(pkValObj.toString())) {
          return false;
        }
      }
    }
    return true;
  }

}
