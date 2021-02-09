SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE OR REPLACE FUNCTION bytea_import(p_path TEXT, p_result OUT BYTEA)
    LANGUAGE plpgsql AS
$$
DECLARE
    l_oid OID;
BEGIN
    SELECT lo_import(p_path) INTO l_oid;
    SELECT lo_get(l_oid) INTO p_result;
    PERFORM lo_unlink(l_oid);
END;
$$;

DO
$BODY$
    DECLARE
        user_1_uuid    uuid        := uuid_generate_v4();
        user_2_uuid    uuid        := uuid_generate_v4();
        user_3_uuid    uuid        := uuid_generate_v4();
        user_1_email   varchar(16) := 'owner1@email.com';
        user_2_email   varchar(16) := 'owner2@email.com';
        user_3_email   varchar(16) := 'user1@email.com';
       /* admin_role_id  int         := nextval('auth.role_id_seq'::regclass);
        user_role_id   int         := nextval('auth.role_id_seq'::regclass);
        viewer_role_id int         := nextval('auth.role_id_seq'::regclass);*/

    BEGIN
        INSERT INTO auth.app_user(id, email, first_name, last_name, password, avatar_path, role, is_enabled)
        VALUES (user_1_uuid, user_1_email, 'Ludwig', 'Beethoven',
                crypt('user1', gen_salt('bf', 12)), null, 'ADMIN', true),

               (user_2_uuid, user_2_email, 'Johann', 'Sebastian',
                concat('{bcrypt}', crypt('user2', gen_salt('bf', 12))), null, 'USER',true),

               (user_3_uuid, user_3_email, 'Johannes', 'Brahms',
                concat('{bcrypt}', crypt('user3', gen_salt('bf', 12))), null, 'USER', true);

       /* INSERT INTO auth.role(id, name)
        VALUES (admin_role_id, 'ROLE_ADMIN'),
               (user_role_id, 'ROLE_USER'),
               (viewer_role_id, 'ROLE_DOCUMENT_VIEWER');*/

    END;
$BODY$ LANGUAGE plpgsql;