package com.wegroceries.wegroceriesapi.users;

public enum ERole {
    ROLE_ADMIN,             // For administrators with full access
    ROLE_CUSTOMER,          // For regular customers who can place orders
    ROLE_DELIVERY_PERSON,   // For delivery personnel
    ROLE_MANAGER,           // For store or inventory managers
    ROLE_SUPPORT,           // For customer support representatives
    ROLE_GUEST,             // For unauthenticated or guest users
    ROLE_VENDOR,            // For vendors who supply products
    ROLE_ANALYST,           // For data analysts who analyze sales and trends
    ROLE_MARKETER,          // For marketing personnel who manage promotions
    ROLE_DEVELOPER          // For developers who maintain the system
}