CREATE TABLE `user` (
                        `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
                        `username` VARCHAR(50) NOT NULL COMMENT '用户名',
                        `password` VARCHAR(100) NOT NULL COMMENT '密码',
                        `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
                        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uniq_username` (`username`),
                        UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 地址表
CREATE TABLE `address` (
                           `address_id` VARCHAR(36) NOT NULL COMMENT '地址ID',
                           `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
                           `address` VARCHAR(200) NOT NULL COMMENT '详细地址',
                           `city` VARCHAR(50) NOT NULL COMMENT '城市',
                           `postal_code` VARCHAR(10) NOT NULL COMMENT '邮政编码',
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`address_id`),
                           KEY `idx_user_id` (`user_id`),
                           CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表';

-- 商品表
CREATE TABLE `product` (
                           `product_id` VARCHAR(36) NOT NULL COMMENT '商品ID',
                           `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                           `description` TEXT COMMENT '商品描述',
                           `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
                           `stock` INT NOT NULL DEFAULT 0 COMMENT '库存',
                           `is_free_shipping` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否包邮',
                           `seller_id` VARCHAR(36) NOT NULL COMMENT '卖家ID',
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`product_id`),
                           KEY `idx_seller_id` (`seller_id`),
                           CONSTRAINT `fk_product_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE `cart` (
                        `cart_id` VARCHAR(36) NOT NULL COMMENT '购物车ID',
                        `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
                        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`cart_id`),
                        UNIQUE KEY `uniq_user_id` (`user_id`),
                        CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 购物车项表
CREATE TABLE `cart_item` (
                             `cart_item_id` VARCHAR(36) NOT NULL COMMENT '购物车项ID',
                             `cart_id` VARCHAR(36) NOT NULL COMMENT '购物车ID',
                             `product_id` VARCHAR(36) NOT NULL COMMENT '商品ID',
                             `quantity` INT NOT NULL COMMENT '数量',
                             `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`cart_item_id`),
                             KEY `idx_cart_id` (`cart_id`),
                             CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
                             CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车项表';

-- 订单表
CREATE TABLE `order` (
                         `order_id` VARCHAR(36) NOT NULL COMMENT '订单ID',
                         `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
                         `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
                         `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式',
                         `shipping_address_id` VARCHAR(36) NOT NULL COMMENT '收货地址ID',
                         `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '订单状态',
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`order_id`),
                         KEY `idx_user_id` (`user_id`),
                         CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
                         CONSTRAINT `fk_order_address` FOREIGN KEY (`shipping_address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单项表
CREATE TABLE `order_item` (
                              `order_item_id` VARCHAR(36) NOT NULL COMMENT '订单项ID',
                              `order_id` VARCHAR(36) NOT NULL COMMENT '订单ID',
                              `product_id` VARCHAR(36) NOT NULL COMMENT '商品ID',
                              `quantity` INT NOT NULL COMMENT '数量',
                              `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
                              `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`order_item_id`),
                              KEY `idx_order_id` (`order_id`),
                              CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
                              CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- 会话表
CREATE TABLE `session` (
                           `session_id` VARCHAR(36) NOT NULL COMMENT '会话ID',
                           `user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
                           `session_token` VARCHAR(100) NOT NULL COMMENT '会话令牌',
                           `expires_at` DATETIME NOT NULL COMMENT '过期时间',
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`session_id`),
                           KEY `idx_user_id` (`user_id`),
                           CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表';