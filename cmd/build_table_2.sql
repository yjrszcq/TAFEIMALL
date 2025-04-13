-- -----------------------------
-- 用户核心模块
-- -----------------------------
CREATE TABLE user (
                      user_id    VARCHAR(36) PRIMARY KEY,
                      username   VARCHAR(50) UNIQUE NOT NULL,
                      password   VARCHAR(100) NOT NULL,
                      email      VARCHAR(100) UNIQUE NOT NULL,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '用户表';

-- -----------------------------
-- 商品模块
-- -----------------------------
CREATE TABLE product (
                         product_id       VARCHAR(36) PRIMARY KEY,
                         name             VARCHAR(100) NOT NULL,
                         description      TEXT,
                         base_price       DECIMAL(10,2) NOT NULL COMMENT '基准价格', -- 原 price 字段重命名
                         current_price    DECIMAL(10,2) NOT NULL COMMENT '当前售价',
                         stock            INT DEFAULT 0 NOT NULL,
                         is_on_promotion  BOOLEAN DEFAULT FALSE,
                         is_free_shipping TINYINT(1) DEFAULT 0 NOT NULL,
                         seller_id        VARCHAR(36) NOT NULL,
                         created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (seller_id) REFERENCES user(user_id)
) COMMENT '商品表';

-- -----------------------------
-- 促销模块
-- -----------------------------
CREATE TABLE t_promotions (
                              promotion_id VARCHAR(36) PRIMARY KEY,
                              start_date   TIMESTAMP NOT NULL,
                              end_date     TIMESTAMP NOT NULL,
                              is_active    BOOLEAN DEFAULT TRUE
);

CREATE TABLE t_promotion_products (
                                      promotion_id  VARCHAR(36) REFERENCES t_promotions(promotion_id),
                                      product_id    VARCHAR(36) REFERENCES product(product_id),
                                      discount_rate DECIMAL(5,2) NOT NULL, -- 每个商品的独立折扣率
                                      PRIMARY KEY (promotion_id, product_id)
);

-- -----------------------------
-- 用户行为模块
-- -----------------------------
CREATE TABLE t_product_reviews (
                                   review_id  VARCHAR(36) PRIMARY KEY,
                                   product_id VARCHAR(36) REFERENCES product(product_id),
                                   order_id   VARCHAR(36) REFERENCES t_order(order_id),
                                   rating     INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                   comment    TEXT,
                                   created_at TIMESTAMP DEFAULT NOW(),
                                   updated_at TIMESTAMP
);

CREATE TABLE t_user_favorites (
                                  user_id    VARCHAR(36) REFERENCES user(user_id),
                                  product_id VARCHAR(36) REFERENCES product(product_id),
                                  created_at TIMESTAMP DEFAULT NOW(),
                                  PRIMARY KEY (user_id, product_id)
);

-- -----------------------------
-- 订单模块
-- -----------------------------
CREATE TABLE t_order (
                         order_id            VARCHAR(36) PRIMARY KEY,
                         user_id             VARCHAR(36) NOT NULL,
                         seller_id           VARCHAR(36) NOT NULL,
                         total_amount        DECIMAL(10,2),
                         payment_method      VARCHAR(20),
                         shipping_address_id VARCHAR(36) NOT NULL,
                         status              VARCHAR(20) DEFAULT 'pending',
                         created_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES user(user_id),
                         FOREIGN KEY (seller_id) REFERENCES user(user_id),
                         FOREIGN KEY (shipping_address_id) REFERENCES address(address_id)
);

CREATE TABLE order_item (
                            order_item_id VARCHAR(36) PRIMARY KEY,
                            order_id      VARCHAR(36) NOT NULL,
                            product_id    VARCHAR(36) NOT NULL,
                            quantity      INT NOT NULL,
                            price         DECIMAL(10,2) NOT NULL,
                            created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (order_id) REFERENCES t_order(order_id),
                            FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- -----------------------------
-- 其他模块
-- -----------------------------
CREATE TABLE address (
                         address_id  VARCHAR(36) PRIMARY KEY,
                         user_id     VARCHAR(36) NOT NULL,
                         address     VARCHAR(200) NOT NULL,
                         city        VARCHAR(50) NOT NULL,
                         postal_code VARCHAR(10) NOT NULL,
                         created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE cart (
                      cart_id    VARCHAR(36) PRIMARY KEY,
                      user_id    VARCHAR(36) UNIQUE NOT NULL,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE cart_item (
                           cart_item_id VARCHAR(36) PRIMARY KEY,
                           cart_id      VARCHAR(36) NOT NULL,
                           product_id   VARCHAR(36) NOT NULL,
                           quantity     INT NOT NULL,
                           created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
                           FOREIGN KEY (product_id) REFERENCES product(product_id)
);
