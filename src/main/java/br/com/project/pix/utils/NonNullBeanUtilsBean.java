package br.com.project.pix.utils;

import br.com.project.pix.exception.CopyPropertyException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class NonNullBeanUtilsBean extends BeanUtilsBean {

    protected static final String ERROR_PROP_MSG = "Unable to copy property {0} with value {1} into {2}!";

    @Override
    public void copyProperty(Object dest, String name, Object value) {
        try {
            if (value != null) {
                callCopyOnSuperClass(dest, name, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new CopyPropertyException(format(ERROR_PROP_MSG, name, value, dest), e);
        }
    }

    protected void callCopyOnSuperClass(Object dest, String name, Object value) throws ReflectiveOperationException {
        super.copyProperty(dest, name, value);
    }
}
