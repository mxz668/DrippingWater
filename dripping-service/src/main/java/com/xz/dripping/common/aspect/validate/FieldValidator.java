package com.xz.dripping.common.aspect.validate;

import com.xz.dripping.common.dto.BaseResponse;
import com.xz.dripping.common.exception.BusinessException;
import com.xz.dripping.common.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 功能: 字段基础校验器
 * 日期: 2017/2/10 0010 09:27
 * 版本: V1.0
 */
@Component
public class FieldValidator {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> BaseResponse validate(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            for (ConstraintViolation<T> cv : set) {
                return BaseResponse.build(Constants.PARAM_VALIDATE_ERROR_CODE, cv.getMessage());
            }
        }
        return null;
    }

    public <T> BaseResponse validateField(T obj) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            for (ConstraintViolation<T> cv : set) {
                throw new BusinessException(Constants.PARAM_VALIDATE_ERROR_CODE, cv.getMessage());
            }
        }
        return null;
    }

    public <T> List<String> validateErrors(T obj) {
        List<String> errorList = new ArrayList<>();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            for (ConstraintViolation<T> cv : set) {
                errorList.add(cv.getMessage());
            }
        }
        return errorList;
    }

//    public static <T> ValidatorResult validateProperty(T obj,String propertyName){
//        ValidatorResult result = new ValidatorResult();
//        Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
//        if( CollectionUtils.isNotEmpty(set) ){
//            result.setHasErrors(true);
//            Map<String,String> errorMsg = new HashMap<String,String>();
//            for(ConstraintViolation<T> cv : set){
//                errorMsg.put(propertyName, cv.getMessage());
//            }
//            result.setErrorMsg(errorMsg);
//        }
//        return result;
//    }
}
