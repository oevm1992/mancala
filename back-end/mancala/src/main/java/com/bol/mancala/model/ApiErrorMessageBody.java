package com.bol.mancala.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorMessageBody {

        private String errorMessage;

        public ApiErrorMessageBody(String errorMessage) {
            this.errorMessage = errorMessage;
        }
}
