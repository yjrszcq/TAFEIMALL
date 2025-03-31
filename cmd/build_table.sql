CREATE TABLE `address` (
                           `address_id` varchar(36) NOT NULL COMMENT '地址ID',
                           `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                           `address` varchar(200) NOT NULL COMMENT '详细地址',
                           `city` varchar(50) NOT NULL COMMENT '城市',
                           `postal_code` varchar(10) NOT NULL COMMENT '邮政编码',
                           `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`address_id`),
                           KEY `idx_user_id` (`user_id`),
                           CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表'

CREATE TABLE `cart` (
                        `cart_id` varchar(36) NOT NULL COMMENT '购物车ID',
                        `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`cart_id`),
                        UNIQUE KEY `uniq_user_id` (`user_id`),
                        CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车表'

CREATE TABLE `cart_item` (
                             `cart_item_id` varchar(36) NOT NULL COMMENT '购物车项ID',
                             `cart_id` varchar(36) NOT NULL COMMENT '购物车ID',
                             `product_id` varchar(36) NOT NULL COMMENT '商品ID',
                             `quantity` int NOT NULL COMMENT '数量',
                             `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`cart_item_id`),
                             KEY `idx_cart_id` (`cart_id`),
                             KEY `fk_cart_item_product` (`product_id`),
                             CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
                             CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车项表'

CREATE TABLE `order` (
                         `order_id` varchar(36) NOT NULL COMMENT '订单ID',
                         `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                         `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
                         `payment_method` varchar(20) DEFAULT NULL COMMENT '支付方式',
                         `shipping_address_id` varchar(36) NOT NULL COMMENT '收货地址ID',
                         `status` varchar(20) NOT NULL DEFAULT 'pending' COMMENT '订单状态',
                         `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`order_id`),
                         KEY `idx_user_id` (`user_id`),
                         KEY `fk_order_address` (`shipping_address_id`),
                         CONSTRAINT `fk_order_address` FOREIGN KEY (`shipping_address_id`) REFERENCES `address` (`address_id`),
                         CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表'

CREATE TABLE `order_item` (
                              `order_item_id` varchar(36) NOT NULL COMMENT '订单项ID',
                              `order_id` varchar(36) NOT NULL COMMENT '订单ID',
                              `product_id` varchar(36) NOT NULL COMMENT '商品ID',
                              `quantity` int NOT NULL COMMENT '数量',
                              `price` decimal(10,2) NOT NULL COMMENT '单价',
                              `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`order_item_id`),
                              KEY `idx_order_id` (`order_id`),
                              KEY `fk_order_item_product` (`product_id`),
                              CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
                              CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单项表'

CREATE TABLE `product` (
                           `product_id` varchar(36) NOT NULL COMMENT '商品ID',
                           `name` varchar(100) NOT NULL COMMENT '商品名称',
                           `description` text COMMENT '商品描述',
                           `price` decimal(10,2) NOT NULL COMMENT '价格',
                           `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
                           `is_free_shipping` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否包邮',
                           `seller_id` varchar(36) NOT NULL COMMENT '卖家ID',
                           `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`product_id`),
                           KEY `idx_seller_id` (`seller_id`),
                           CONSTRAINT `fk_product_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表'

CREATE TABLE `session` (
                           `session_id` varchar(36) NOT NULL COMMENT '会话ID',
                           `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                           `session_token` varchar(100) NOT NULL COMMENT '会话令牌',
                           `expires_at` datetime NOT NULL COMMENT '过期时间',
                           `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`session_id`),
                           KEY `idx_user_id` (`user_id`),
                           CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会话表'

CREATE TABLE `user` (
                        `user_id` varchar(36) NOT NULL COMMENT '用户ID',
                        `username` varchar(50) NOT NULL COMMENT '用户名',
                        `password` varchar(100) NOT NULL COMMENT '密码',
                        `email` varchar(100) NOT NULL COMMENT '邮箱',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uniq_username` (`username`),
                        UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'

