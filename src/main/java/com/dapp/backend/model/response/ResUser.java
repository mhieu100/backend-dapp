package com.dapp.backend.model.response;
import com.dapp.backend.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResUser {
    User user;
}
