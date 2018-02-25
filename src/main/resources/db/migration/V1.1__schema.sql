create sequence default_sequence;

create table parents
(
  id       bigint default nextval('default_sequence') not null constraint parents_pkey primary key,
  name     varchar(32),
  children jsonb
)