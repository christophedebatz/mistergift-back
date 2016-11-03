CREATE DATABASE mistergift DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE mistergift;

CREATE TABLE ShemaVersion (
  version int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY  (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELIMITER $$
DROP PROCEDURE IF EXISTS runDeltas$$
CREATE PROCEDURE runDeltas(OUT res char(190))
  BEGIN
    /*
    -- DECLARE EXIT HANDLER FOR SQLEXCEPTION
    -- test:BEGIN
    --  set res = 'Error - deltas not applied. Check the syntax of queries !';
    -- ROLLBACK;
    -- END test;
    */

    START TRANSACTION;
    set res = '';
    select 'DELTAS START' as ' ';

    set @currentSchemaVersion = 0;
    select version into @currentSchemaVersion from SchemaVersion;
    if (@currentSchemaVersion = 0) then
      insert into SchemaVersion set version = 0;
    end if;

    set @prevSchemaVersion = @currentSchemaVersion;

    /*--------------------------------------------------*/
    /* Version 1                                        */
    /*--------------------------------------------------*/

    if (@currentSchemaVersion = 0) then

      CREATE TABLE `events` (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        creation_date date NOT NULL,
        modification_date date NOT NULL,
        `date` datetime DEFAULT NULL,
        description varchar(255) NOT NULL,
        `name` varchar(75) NOT NULL,
        address varchar(255) DEFAULT NULL,
        end_date datetime DEFAULT NULL,
        start_date datetime DEFAULT NULL,
        `status` varchar(255) NOT NULL,
        cover_id bigint(20) DEFAULT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE event_participants (
        event_id bigint(20) NOT NULL,
        participants_event_id bigint(20) NOT NULL,
        participants_user_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE files (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        url varchar(255) NOT NULL,
        user_id bigint(20) DEFAULT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE gifts (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        creation_date date NOT NULL,
        modification_date date NOT NULL,
        event_id bigint(20) NOT NULL,
        product_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

      CREATE TABLE landing_users (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        creation_date date NOT NULL,
        email varchar(100) NOT NULL,
        city varchar(255) DEFAULT NULL,
        country varchar(255) DEFAULT NULL,
        ip varchar(255) DEFAULT NULL,
        region varchar(255) DEFAULT NULL
      ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
      
      CREATE TABLE links (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        url varchar(255) NOT NULL,
        product_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE products (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        creation_date date NOT NULL,
        modification_date date NOT NULL,
        brand varchar(75) NOT NULL,
        description longtext,
        `name` varchar(75) NOT NULL,
        reference varchar(25) DEFAULT NULL,
        picture_id bigint(20) DEFAULT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

      CREATE TABLE tokens (
        id varchar(75) NOT NULL AUTO_INCREMENT,
        expire_at date NOT NULL,
        user_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE users (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        creation_date date NOT NULL,
        modification_date date NOT NULL,
        email varchar(150) NOT NULL,
        first_name varchar(255) NOT NULL,
        last_name varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
        role int(11) NOT NULL,
        picture_id bigint(20) DEFAULT NULL,
        thumbnail_id bigint(20) DEFAULT NULL,
        token_id varchar(75) DEFAULT NULL,
        `name` varchar(255) NOT NULL
      ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

      CREATE TABLE users_events (
        can_see_mine bit(1) DEFAULT NULL,
        can_see_others bit(1) DEFAULT NULL,
        is_admin bit(1) DEFAULT NULL,
        event_id bigint(20) NOT NULL,
        user_id bigint(20) NOT NULL,
        userEvents_event_id bigint(20) NOT NULL,
        userEvents_user_id bigint(20) NOT NULL,
        can_see_mines bit(1) DEFAULT NULL,
        is_invitation bit(1) DEFAULT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE users_gifts (
        gift_id bigint(20) NOT NULL,
        owners_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE user_events (
        user_id bigint(20) NOT NULL,
        userEvents_event_id bigint(20) NOT NULL,
        userEvents_user_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE whishlist (
        user_id bigint(20) NOT NULL,
        wishLists_product_id bigint(20) NOT NULL,
        wishLists_user_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      CREATE TABLE whishlists (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        product_id bigint(20) NOT NULL,
        user_id bigint(20) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
      
      ALTER TABLE events
       ADD PRIMARY KEY (id), ADD UNIQUE KEY UK_fn2r8jg0sm5v6vhoa7yqw55vy (`name`), ADD KEY FK_bf4p5r6yk3ky76sleo6n22fvo (cover_id);
      
      ALTER TABLE event_participants
       ADD UNIQUE KEY UK_qisg2bjyi69uoumlhrxq8sob7 (participants_event_id,participants_user_id), ADD KEY FK_a8rqc0j41hob7otw4wc88parn (event_id);
      
      ALTER TABLE files
       ADD PRIMARY KEY (id), ADD KEY FK_6g4ifscitfdsrpe3l9gf6bcou (user_id);
      
      ALTER TABLE gifts
       ADD PRIMARY KEY (id), ADD KEY FK_9qga09gyahe3mjts9ue18bfcr (event_id), ADD KEY FK_f7yvi53yosdccq9oykfww1o7h (product_id);
      
      ALTER TABLE landing_users
       ADD PRIMARY KEY (id);
      
      ALTER TABLE links
       ADD PRIMARY KEY (id), ADD KEY FK_55nm93ibousyofi4mn80tuhcm (product_id);
      
      ALTER TABLE products
       ADD PRIMARY KEY (id), ADD KEY FK_sf0fmmkxr4pnbbgy4f7gq3300 (picture_id);
      
      ALTER TABLE tokens
       ADD PRIMARY KEY (id), ADD KEY FK_lgokc3vw1rct83pdwryntacb9 (user_id);
      
      ALTER TABLE users
       ADD PRIMARY KEY (id), ADD UNIQUE KEY UK_6dotkott2kjsp8vw4d0m25fb7 (email), ADD KEY FK_b8f1qvjjhpbvq8bsm6oh5ro81 (picture_id), ADD KEY FK_eg0fheiknh9c0y773g1oac7v9 (thumbnail_id), ADD KEY FK_e8xd6tjf4fgnq0xcwg8bktkvm (token_id);
      
      ALTER TABLE users_events
       ADD PRIMARY KEY (event_id,user_id), ADD UNIQUE KEY UK_258ab24fu2y7g69soalf96agt (userEvents_event_id,userEvents_user_id), ADD KEY FK_fj4whto1l0j1gb18oc62sugr6 (user_id);
      
      ALTER TABLE users_gifts
       ADD UNIQUE KEY UK_k29cojk1dmr2hc9sn827nf10o (owners_id), ADD KEY FK_42r91rok9288e59mf7b7fu5n1 (gift_id);
      
      ALTER TABLE user_events
       ADD UNIQUE KEY UK_ilxxi5qd0gynqvcc81o8hf35x (userEvents_event_id,userEvents_user_id), ADD KEY FK_jjl048nk4ntflcsyve9u5cxdf (user_id);

      ALTER TABLE whishlist
       ADD UNIQUE KEY UK_n2my9ugobnkh30qyghghgrniv (wishLists_product_id,wishLists_user_id), ADD KEY FK_r86ec1e9bxfsn54enmtd8wsl1 (user_id);
      
      ALTER TABLE whishlists
       ADD PRIMARY KEY (id), ADD KEY FK_86k9grw3ve30lixhvhm9n3it2 (product_id), ADD KEY FK_97xl5w4f6u1vyt6cqhv9da006 (user_id);

      ALTER TABLE events
      ADD CONSTRAINT FK_bf4p5r6yk3ky76sleo6n22fvo FOREIGN KEY (cover_id) REFERENCES `files` (id);

      ALTER TABLE event_participants
      ADD CONSTRAINT FK_a8rqc0j41hob7otw4wc88parn FOREIGN KEY (event_id) REFERENCES `events` (id),
      ADD CONSTRAINT FK_qisg2bjyi69uoumlhrxq8sob7 FOREIGN KEY (participants_event_id, participants_user_id) REFERENCES users_events (event_id, user_id);
      
      ALTER TABLE files
      ADD CONSTRAINT FK_6g4ifscitfdsrpe3l9gf6bcou FOREIGN KEY (user_id) REFERENCES `users` (id);
      
      ALTER TABLE gifts
      ADD CONSTRAINT FK_9qga09gyahe3mjts9ue18bfcr FOREIGN KEY (event_id) REFERENCES `events` (id),
      ADD CONSTRAINT FK_f7yvi53yosdccq9oykfww1o7h FOREIGN KEY (product_id) REFERENCES products (id);
      
      ALTER TABLE links
      ADD CONSTRAINT FK_55nm93ibousyofi4mn80tuhcm FOREIGN KEY (product_id) REFERENCES products (id);
      
      ALTER TABLE products
      ADD CONSTRAINT FK_sf0fmmkxr4pnbbgy4f7gq3300 FOREIGN KEY (picture_id) REFERENCES `files` (id);
      
      ALTER TABLE tokens
      ADD CONSTRAINT FK_lgokc3vw1rct83pdwryntacb9 FOREIGN KEY (user_id) REFERENCES `users` (id);
      
      ALTER TABLE users
      ADD CONSTRAINT FK_b8f1qvjjhpbvq8bsm6oh5ro81 FOREIGN KEY (picture_id) REFERENCES `files` (id),
      ADD CONSTRAINT FK_e8xd6tjf4fgnq0xcwg8bktkvm FOREIGN KEY (token_id) REFERENCES tokens (id),
      ADD CONSTRAINT FK_eg0fheiknh9c0y773g1oac7v9 FOREIGN KEY (thumbnail_id) REFERENCES `files` (id);
      
      ALTER TABLE users_events
      ADD CONSTRAINT FK_258ab24fu2y7g69soalf96agt FOREIGN KEY (userEvents_event_id, userEvents_user_id) REFERENCES users_events (event_id, user_id),
      ADD CONSTRAINT FK_fj4whto1l0j1gb18oc62sugr6 FOREIGN KEY (user_id) REFERENCES `users` (id),
      ADD CONSTRAINT FK_t697jurb8rfqs660dgi2ul56n FOREIGN KEY (event_id) REFERENCES `events` (id);
      
      ALTER TABLE users_gifts
      ADD CONSTRAINT FK_42r91rok9288e59mf7b7fu5n1 FOREIGN KEY (gift_id) REFERENCES gifts (id),
      ADD CONSTRAINT FK_k29cojk1dmr2hc9sn827nf10o FOREIGN KEY (owners_id) REFERENCES `users` (id);
      
      ALTER TABLE user_events
      ADD CONSTRAINT FK_ilxxi5qd0gynqvcc81o8hf35x FOREIGN KEY (userEvents_event_id, userEvents_user_id) REFERENCES users_events (event_id, user_id),
      ADD CONSTRAINT FK_jjl048nk4ntflcsyve9u5cxdf FOREIGN KEY (user_id) REFERENCES `users` (id);
      
      ALTER TABLE whishlist
      ADD CONSTRAINT FK_r86ec1e9bxfsn54enmtd8wsl1 FOREIGN KEY (user_id) REFERENCES `users` (id);
      
      ALTER TABLE whishlists
      ADD CONSTRAINT FK_86k9grw3ve30lixhvhm9n3it2 FOREIGN KEY (product_id) REFERENCES products (id),
      ADD CONSTRAINT FK_97xl5w4f6u1vyt6cqhv9da006 FOREIGN KEY (user_id) REFERENCES `users` (id);

      set @currentSchemaVersion = @currentSchemaVersion + 1;

    end if;

    set res = concat(res, 'DELTAS FINISHED - # changes ', (@currentSchemaVersion - @prevSchemaVersion), ', prev version: ', @prevSchemaVersion, ', current version: ', @currentSchemaVersion);

    if (@currentSchemaVersion > @prevSchemaVersion) then
      update SchemaVersion set version = @currentSchemaVersion;
    end if;

    COMMIT;

  END$$
DELIMITER ;

call runDeltas(@res);
select @res as ' ';
DROP PROCEDURE IF EXISTS runDeltas;

