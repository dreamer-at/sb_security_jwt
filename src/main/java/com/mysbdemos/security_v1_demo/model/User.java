package com.mysbdemos.security_v1_demo.model;

import javax.persistence.Id;
import java.util.UUID;

public class User extends BaseEntity {

    @Id
    private UUID id;
}
