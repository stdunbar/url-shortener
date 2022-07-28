-- vim: set ts=4 in .exrc

--
-- Postgres version of the shortner database.
--

--
-- this function is taken directly from
-- https://stackoverflow.com/questions/22908499/how-to-generate-random-unique-number-in-postgresql-using-function#22909407
-- it ultimately converts an integer (which will be a sequence) into a number that is somewhat random looking
--
create or replace function pseudo_encrypt(value int) returns int as
$$
declare
    l1 int;
    l2 int;
    r1 int;
    r2 int;
    i  int := 0;
begin
    l1 := (value >> 16) & 65535;
    r1 := value & 65535;
    while i < 3
        loop
            l2 := r1;
            r2 := l1 # ((((1366 * r1 + 150889) % 714025) / 714025.0) * 32767)::int;
            l1 := l2;
            r1 := r2;
            i := i + 1;
        end loop;
    return ((r1 << 16) + l1);
end;
$$ language plpgsql strict
                    immutable;


drop sequence if exists short_url_id_seq cascade;

create sequence short_url_id_seq start 1000;


drop sequence if exists short_url_encrypted_id_seq cascade;

create sequence short_url_encrypted_id_seq start 10000;

drop table if exists short_url cascade;

create table short_url
(
    short_url_id           integer                  not null default nextval('short_url_id_seq'),
    short_url_encrypted_id integer                  not null default pseudo_encrypt(nextval('short_url_encrypted_id_seq')::integer),
    original_url           varchar(2048)            not null,
    click_limit            integer                  not null default 0,
    click_count            integer                  not null default 0,
    issue_date             timestamp with time zone not null default now(),
    expiration_date        timestamp with time zone null,

    primary key (short_url_id),
    unique (short_url_encrypted_id)
);

drop sequence if exists click_tracker_id_seq cascade;

create sequence click_tracker_id_seq;

drop table if exists click_tracker cascade;

create table click_tracker
(
    click_tracker_id integer                  not null default nextval('click_tracker_id_seq'),
    remote_host_addr varchar(64)              not null,
    user_agent       varchar(128)             null,
    short_url_id     integer                  not null,
    create_date      timestamp with time zone not null default now(),

    primary key (click_tracker_id),
    foreign key (short_url_id) references short_url (short_url_id)
);