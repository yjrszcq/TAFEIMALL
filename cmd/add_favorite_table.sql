create table favorite
(
    favorite_id varchar(36)                        not null comment '收藏ID'
        primary key,
    user_id     varchar(36)                        not null comment '用户ID',
    product_id  varchar(36)                        not null comment '商品ID',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_favorite_user
        foreign key (user_id) references user (user_id),
    constraint fk_favorite_product
        foreign key (product_id) references product (product_id)
)
    comment '收藏表';

create index idx_favorite_user_id
    on favorite (user_id);

create index idx_favorite_product_id
    on favorite (product_id);

-- 创建唯一索引，确保一个用户只能收藏同一商品一次
create unique index uniq_user_product
    on favorite (user_id, product_id); 