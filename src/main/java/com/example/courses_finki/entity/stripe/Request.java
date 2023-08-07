package com.example.courses_finki.entity.stripe;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotNull
    @Min(3)
    private Long amount;

    private String email;

    @NotBlank
    private List<String> productName;


    public String getProductNameAsString() {
        if (productName.size() > 1) {
            return String.join(",", productName);
        } else if (productName.size() == 1) {
            return productName.get(0);
        } else {
            return ""; // or some other default value if the list is empty
        }
    }
}