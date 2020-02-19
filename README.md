# Hello Shop: E-commerce application with Spring boot

## Summary
I implemented a simple e-commerce application.
Basically, the user will be able to add/remove products from a product list to/from a shopping cart and to place an order.
##### [Go to Hello Shop]: http://ec2-52-78-71-38.ap-northeast-2.compute.amazonaws.com/

## Development Environment
- Spring Boot
- Spring Batch
- Spring Data JPA[Hibernate]
- Spring Security
- Thymeleaf
- MariaDB
- JQuery
- Bootstrap
- AWS (EC2, RDS, S3, CodeDeploy)
- Git
- Travis
- Nginx

## Implementation
* User site
    * Authentication & Authorization
        * Basic authentication
        * Social Login
    * Shopping Cart
        * Put items in the cart
        * Remove items from the cart
        * Empty carts an hour if not login
    * Order
        * Checkout
        * List
        * Cancel

* Admin site
    * Authentication & Authorization
        * Basic authentication
    * Item
        * List
        * Add items