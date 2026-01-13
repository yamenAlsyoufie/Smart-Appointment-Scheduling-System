package com.example.demo.Security;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 🔴 REQUIRED
@Documented
public @interface JwtSecured {
}
