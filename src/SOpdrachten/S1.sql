ALTER TABLE medewerkers
ADD geslacht CHAR;
ALTER TABLE medewerkers
ADD CONSTRAINT m_geslacht_chk CHECK (geslacht='M' OR geslacht='V');

INSERT INTO afdelingen
VALUES (50, 'ONDERZOEK', 'ZWOLLE');
INSERT INTO medewerkers (mnr,naam,voorl,functie,chef,gbdatum,maandsal,afd)
VALUES (8000,'DONK','A','MANAGER',7839,'1983-03-12',1200,50);
UPDATE afdelingen
SET hoofd=8000
WHERE anr = 50;


CREATE SEQUENCE seq_afdelingen
Start 60
increment 10
minvalue 10
owned by afdelingen.anr;

insert into afdelingen (anr, naam, locatie)
values (nextval('seq_afdelingen'), 'EXPORT', 'ROTTERDAM');
insert into afdelingen (anr, naam, locatie)
values (nextval('seq_afdelingen'), 'IMPORT', 'SCHIPHOL');
insert into afdelingen (anr, naam, locatie)
values (nextval('seq_afdelingen'), 'FABRICATIE', 'BRABAND');
insert into afdelingen (anr, naam, locatie)
values (nextval('seq_afdelingen'), 'RECLAME', 'AMSTERDAM');
alter table afdelingen
alter column anr type numeric(3);
insert into afdelingen (anr, naam, locatie)
values (nextval('seq_afdelingen'), 'TRAINING', 'SOEST');


create table adressen(
   postcode char(6) not null check(length(postcode) = 6 and postcode ~* '^[0-9]{4}[a-z]{2}$'),
   huisnummer char(5) not null,
   ingangsdatum date not null,
   einddatum Date not null check(einddatum > ingangsdatum),
   telefoon char(10) check(telefoon ~ '^[0-9]{10}$') unique,
   med_mnr numeric(4) not null references medewerkers(mnr),
   primary key (postcode, huisnummer, ingangsdatum)
);
INSERT INTO adressen (postcode, huisnummer, ingangsdatum, einddatum, telefoon, med_mnr) VALUES('1234AB', '112  ', '2002-01-01', '2030-01-01', '0123456789', 8000);


alter table public.medewerkers add constraint m_comm_chk check((functie='VERKOPER' and not comm isnull) or (functie!='VERKOPER' and comm isnull));
