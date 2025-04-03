create table TAFEI_MALL.user
(
    user_id    varchar(36)                        not null comment '用户ID'
        primary key,
    username   varchar(50)                        not null comment '用户名',
    password   varchar(100)                       not null comment '密码',
    email      varchar(100)                       not null comment '邮箱',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_email
        unique (email),
    constraint uniq_username
        unique (username)
)
    comment '用户表';

create table TAFEI_MALL.address
(
    address_id  varchar(36)                        not null comment '地址ID'
        primary key,
    user_id     varchar(36)                        not null comment '用户ID',
    address     varchar(200)                       not null comment '详细地址',
    city        varchar(50)                        not null comment '城市',
    postal_code varchar(10)                        not null comment '邮政编码',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_address_user
        foreign key (user_id) references TAFEI_MALL.user (user_id)
)
    comment '用户地址表';

create index idx_user_id
    on TAFEI_MALL.address (user_id);

create table TAFEI_MALL.cart
(
    cart_id    varchar(36)                        not null comment '购物车ID'
        primary key,
    user_id    varchar(36)                        not null comment '用户ID',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_user_id
        unique (user_id),
    constraint fk_cart_user
        foreign key (user_id) references TAFEI_MALL.user (user_id)
)
    comment '购物车表';

create table TAFEI_MALL.`order`
(
    order_id            varchar(36)                           not null comment '订单ID'
        primary key,
    user_id             varchar(36)                           not null comment '用户ID',
    seller_id           varchar(36)                           not null,
    total_amount        decimal(10, 2)                        null comment '总金额',
    payment_method      varchar(20)                           null comment '支付方式',
    shipping_address_id varchar(36)                           not null comment '收货地址ID',
    status              varchar(20) default 'pending'         not null comment '订单状态',
    created_at          datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at          datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_order_address
        foreign key (shipping_address_id) references TAFEI_MALL.address (address_id),
    constraint fk_order_seller
        foreign key (seller_id) references TAFEI_MALL.user (user_id),
    constraint fk_order_user
        foreign key (user_id) references TAFEI_MALL.user (user_id)
)
    comment '订单表';

create index idx_user_id
    on TAFEI_MALL.`order` (user_id);

create table TAFEI_MALL.product
(
    product_id       varchar(36)                          not null comment '商品ID'
        primary key,
    name             varchar(100)                         not null comment '商品名称',
    description      text                                 null comment '商品描述',
    price            decimal(10, 2)                       not null comment '价格',
    stock            int        default 0                 not null comment '库存',
    is_free_shipping tinyint(1) default 0                 not null comment '是否包邮',
    seller_id        varchar(36)                          not null comment '卖家ID',
    created_at       datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at       datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_product_seller
        foreign key (seller_id) references TAFEI_MALL.user (user_id)
)
    comment '商品表';

create table TAFEI_MALL.cart_item
(
    cart_item_id varchar(36)                        not null comment '购物车项ID'
        primary key,
    cart_id      varchar(36)                        not null comment '购物车ID',
    product_id   varchar(36)                        not null comment '商品ID',
    quantity     int                                not null comment '数量',
    created_at   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_cart_item_cart
        foreign key (cart_id) references TAFEI_MALL.cart (cart_id),
    constraint fk_cart_item_product
        foreign key (product_id) references TAFEI_MALL.product (product_id)
)
    comment '购物车项表';

create index idx_cart_id
    on TAFEI_MALL.cart_item (cart_id);

create table TAFEI_MALL.order_item
(
    order_item_id varchar(36)                        not null comment '订单项ID'
        primary key,
    order_id      varchar(36)                        not null comment '订单ID',
    product_id    varchar(36)                        not null comment '商品ID',
    quantity      int                                not null comment '数量',
    price         decimal(10, 2)                     not null comment '单价',
    created_at    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_order_item_order
        foreign key (order_id) references TAFEI_MALL.`order` (order_id),
    constraint fk_order_item_product
        foreign key (product_id) references TAFEI_MALL.product (product_id)
)
    comment '订单项表';

create index idx_order_id
    on TAFEI_MALL.order_item (order_id);

create index idx_seller_id
    on TAFEI_MALL.product (seller_id);

create table TAFEI_MALL.session
(
    session_id    varchar(36)                        not null comment '会话ID'
        primary key,
    user_id       varchar(36)                        not null comment '用户ID',
    session_token varchar(100)                       not null comment '会话令牌',
    expires_at    datetime                           not null comment '过期时间',
    created_at    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint fk_session_user
        foreign key (user_id) references TAFEI_MALL.user (user_id)
)
    comment '会话表';

create index idx_user_id
    on TAFEI_MALL.session (user_id);

create table TAFEI_MALL.t_image
(
    image_id   varchar(255)                        not null
        primary key,
    image_path varchar(255)                        not null,
    product_id varchar(255)                        not null,
    created_at timestamp default (now())           not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint fk_image_product
        foreign key (product_id) references TAFEI_MALL.product (product_id)
);

