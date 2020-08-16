package com.lhsj.police.core.validate;

import com.lhsj.police.core.base.ReValidates;
import com.lhsj.police.core.constant.Constants;
import com.lhsj.police.core.exception.CommonException;
import com.lhsj.police.core.lambda.ReStreams;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.lhsj.police.core.constant.Constants.COMMA;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * jsr303 validate util
 */
public class Validators {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T t, Class<?>... groups) {
        ReValidates.isTrue(nonNull(t), "param is null");

        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(t, groups);

        if (isEmpty(violations)) {
            return;
        }

        throw new CommonException(Constants.CODE_PARAM_ERROR, msg(violations));
    }

    private static <T> String msg(Set<ConstraintViolation<T>> violations) {
        return ReStreams.nonNull(violations)
                .map(ConstraintViolation::getMessage)
                .filter(StringUtils::isNotBlank)
                .collect(joining(COMMA));
    }

}