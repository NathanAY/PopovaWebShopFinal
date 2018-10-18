package net.proselyte.springsecurityapp.validator;

import net.proselyte.springsecurityapp.model.Product;
import net.proselyte.springsecurityapp.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link net.proselyte.springsecurityapp.model.Product} class,
 * implements {@link Validator} interface.
 *
 * @author AY
 * @version 1.0
 */

@Component
public class ProductValidator implements Validator {

    @Autowired
    ProductService productService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Product product = (Product) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (product.getName().length() < 1 || product.getName().length() > 32) {
            errors.rejectValue("name", "Size.productForm.name");
        }

        if (productService.findByName(product.getName()) != null) {
            errors.rejectValue("name", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");
        if (product.getDescription().length() < 1 || product.getDescription().length() > 32) {
            errors.rejectValue("description", "Size.productForm.description");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "Required");
        if (product.getCategoryName().length() < 1 || product.getCategoryName().length() > 32) {
            errors.rejectValue("categoryName", "Size.productForm.description");
        }
    }
}
