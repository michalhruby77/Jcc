create table presence (
  id serial primary key,
  shift_begin timestamp not null,
  shift_end  timestamp not null,
  shift_name varchar(10) not null,
  band_name varchar(8) not null,
  last_change timestamp not null default now(),
  plan text not null,
);

-- insert test data
insert into presence (shift_begin, shift_end, shift_name, band_name, last_change, plan) values
('1970-01-01 06:00:00', '1970-01-01 00:00:00', 'MODRA', 'F991LL', '2013-02-15 09:37:17.294', '<?xml version=1.0" encoding="UTF-8" standalone="yes"?><workplaces><wp n="Grund" id="1A"><p id="A1">Dano</p><p id="A2">Matej</p><p id="A3">Oskar</p></wp><wp n="DME" id="2B"><p id="A4">Dano</p><p id="B5">Matej</p><p id="B6">Oskar</p></wp><wp n="Tulle_Bug" id="3B"><p id="A7"></p><p id="A8"></p><p id="A9"></p><p id=""></p></wp><wp n="RM" id="4B"><p id="A10"></p><p id="B11"></p></wp><wp n="RM" id="5A"><p id="A12"></p><p id="B12"></p></wp><wp n="RM" id="5B"><p id="B13"></p><p id="B14"></p></wp><wp n="RM" id="6A"><p id="A13"></p><p id="A12"></p></wp><wp n="RM" id="6B"><p id="B15"></p><p id="B16"></p></wp><wp n="RM" id="7A"><p id=""></p><p id=""></p></wp><wp n="RM" id="7B"><p id=""></p><p id=""></p></wp><wp n="RM" id="8A"><p id=""></p><p id=""></p></wp></workplaces>'),
('1970-01-01 14:00:00', '1970-01-01 22:00:00', 'ORANZOVA', 'F991LL' '2013-02-15 07:25:57.239', '<?xml version=1.0" encoding="UTF-8" standalone="yes"?><workplaces><wp n="Grund" id="1A"><p id="A1">Dano</p><p id="A2">Matej</p><p id="A3">Oskar</p><p id="A4">Milan</p></wp><wp n="DME" id="2B"><p id="A4">Dano</p><p id="B5">Matej</p><p id="B6">Oskar</p></wp><wp n="Tulle_Bug" id="3B"><p id="A7">Lajlo</p><p id="A8">Hercegova</p></wp></workplaces>'),
('1970-01-01 06:00:00', '1970-01-01 14:00:00', 'ZELENA', 'F981LL' '2013-02-08 09:17:23.152273' '<?xml version=1.0" encoding="UTF-8" standalone="yes"?><workplaces><wp n=""Grund"" id=""1A""><p id=""A1"">Dano</p><p id=""A2"">Matej</p><p id=""A3"">Oskar</p></wp><wp n=""DME"" id=""2B""><p id=""A4"">Dano</p><p id=""B5"">Matej</p><p id=""B6"">Oskar</p></wp></workplaces>'),
('1970-01-01 06:00:00', '1970-01-01 14:00:00', 'MODRA', 'F9X1RL', '2013-02-08 09:18:22.681389', '<?xml version=1.0" encoding="UTF-8" standalone="yes"?><workplaces><wp n=""Grund"" id=""1A""><p id=""A1"">Dano</p><p id=""A2"">Matej</p><p id=""A3"">Oskar</p></wp></workplaces>');
