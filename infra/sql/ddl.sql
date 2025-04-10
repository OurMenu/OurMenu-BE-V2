SET FOREIGN_KEY_CHECKS = 0;

create table home_question_answer (
                                      id bigint not null auto_increment,
                                      user_id bigint,
                                      answer enum ('DISLIKE','LIKE','MOUNTAIN','RAINY','SEA','SPICY','SUMMER','SUNNY','SWEET','WINTER'),
                                      question enum ('FEEL','SEASON','STRESS','TRIP','WEATHER'),
                                      primary key (id)
) engine=InnoDB;

create table map (
                     created_at datetime(6),
                     id bigint not null auto_increment,
                     modified_at datetime(6),
                     location POINT SRID 4326 not null,
                     primary key (id)
) engine=InnoDB;

create table meal_time (
                           meal_time time(6),
                           created_at datetime(6),
                           id bigint not null auto_increment,
                           modified_at datetime(6),
                           user_id bigint,
                           primary key (id)
) engine=InnoDB;

create table menu (
                      is_crawled bit,
                      pin tinyint check (pin between 0 and 22),
                      price integer not null,
                      created_at datetime(6),
                      id bigint not null auto_increment,
                      modified_at datetime(6),
                      store_id bigint,
                      user_id bigint not null,
                      memo_content varchar(255),
                      memo_title varchar(255),
                      title varchar(255) not null,
                      primary key (id)
) engine=InnoDB;

create table menu_folder (
                             custom_index integer not null,
                             icon tinyint check (icon between 0 and 31),
                             created_at datetime(6),
                             id bigint not null auto_increment,
                             modified_at datetime(6),
                             user_id bigint not null,
                             img_url varchar(255),
                             title varchar(255) not null,
                             primary key (id)
) engine=InnoDB;

create table menu_menu_folder (
                                  created_at datetime(6),
                                  folder_id bigint,
                                  id bigint not null auto_increment,
                                  menu_id bigint,
                                  modified_at datetime(6),
                                  primary key (id)
) engine=InnoDB;

create table menu_img (
                          created_at datetime(6),
                          id bigint not null auto_increment,
                          menu_id bigint,
                          modified_at datetime(6),
                          img_url varchar(255),
                          primary key (id)
) engine=InnoDB;

create table menu_tag (
                          created_at datetime(6),
                          id bigint not null auto_increment,
                          menu_id bigint not null,
                          modified_at datetime(6),
                          tag enum ('ASIA','BREAD','BUSINESS','BUY_FOOD','CAFE','CHINA','COOL','DATE','DESSERT','FAST_FOOD','FISH','HOT','HOT_SPICY','JAPAN','KOREA','MEAT','NOODLE','ORGANIZATION','PROMISE','RICE','SOLO','SPICY','SWEET','WESTERN'),
                          primary key (id)
) engine=InnoDB;

create table not_owned_search_history (
                                          created_at datetime(6),
                                          id bigint not null auto_increment,
                                          modified_at datetime(6),
                                          user_id bigint not null,
                                          address varchar(255) not null,
                                          title varchar(255) not null,
                                          primary key (id)
) engine=InnoDB;

create table not_found_store (
                                 mapx float(53) not null,
                                 mapy float(53) not null,
                                 created_at datetime(6),
                                 id bigint not null auto_increment,
                                 modified_at datetime(6),
                                 address varchar(255) not null,
                                 store_id varchar(255),
                                 title varchar(255) not null,
                                 primary key (id)
) engine=InnoDB;

create table owned_menu_search (
                                   created_at datetime(6),
                                   id bigint not null auto_increment,
                                   menu_id bigint not null,
                                   modified_at datetime(6),
                                   user_id bigint not null,
                                   menu_title varchar(255) not null,
                                   store_address varchar(255) not null,
                                   store_title varchar(255) not null,
                                   primary key (id)
) engine=InnoDB;

create table store (
                       created_at datetime(6),
                       id bigint not null auto_increment,
                       map_id bigint,
                       modified_at datetime(6),
                       address varchar(255),
                       title varchar(255) not null,
                       primary key (id)
) engine=InnoDB;

create table user (
                      created_at datetime(6),
                      id bigint not null auto_increment,
                      modified_at datetime(6),
                      email varchar(255),
                      password varchar(255),
                      sign_in_type enum ('EMAIL','KAKAO'),
                      primary key (id)
) engine=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;