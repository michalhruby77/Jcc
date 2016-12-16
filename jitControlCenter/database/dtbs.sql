create table presence (
  id serial primary key,
  shift_begin timestamp not null,
  shift_end  timestamp not null,
  shift_name varchar(10) not null,
  band_name varchar(8) not null,
  last_change timestamp not null default now(),
  plan text not null
);

-- insert test data
insert into presence (shift_begin, shift_end, shift_name, band_name, last_change, plan) values
('1970-01-01 06:00:00', '1970-01-01 00:00:00', '', 'F991LL', 'now', ''),
('1970-01-01 14:00:00', '1970-01-01 21:45:00', '', 'F991LL', 'now', ''),
('1970-01-01 06:00:00', '1970-01-01 14:00:00', '', 'F981LL', 'now', ''),
('1970-01-01 06:00:00', '1970-01-01 14:00:00', '', 'F9X1RL', 'now', ''),
('1970-01-01 06:00:00', '1970-01-01 14:00:00', '', 'ZADOBL', 'now', '');
