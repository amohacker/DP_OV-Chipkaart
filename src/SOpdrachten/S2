select code, omschrijving from cursussen where lengte = 4;


select * from medewerkers
order by functie, gbdatum desc;


select cursus, begindatum from uitvoeringen
where locatie='UTRECHT' OR locatie='MAASTRICHT';


select voorl, naam from medewerkers
where not (naam='JANSEN' and voorl='R');


insert into uitvoeringen (docent, cursus, begindatum, locatie)
values ((select mnr from medewerkers where naam='SMIT' and voorl='N'), 'S02', '02-03-2021', 'LEERDAM');


insert into medewerkers (mnr, naam, voorl, functie, gbdatum, maandsal)
values(8001, 'TEN HAAKEN', 'S', 'STAGIAIR', '15-06-1998', 1520);

update schalen
set ondergrens=4001, snr=6
where snr=5;
insert into schalen (snr, ondergrens, bovengrens, toelage)
values (5, 3001, 4000, 500);


insert into cursussen (code, omschrijving, lengte, type)
values ('D&P', 'Data & Persistency', 6, 'BLD');
insert into uitvoeringen (cursus, locatie, begindatum)
values ('D&P', 'LEERDAM', '03-10-2020');
insert into uitvoeringen (cursus, locatie, begindatum)
values ('D&P', 'LEERDAM', '13-11-2020');
insert into inschrijvingen (cursist, cursus, begindatum)
values (7499, 'D&P', '03-10-2020');
insert into inschrijvingen (cursist, cursus, begindatum)
values (7788, 'D&P', '03-10-2020');
insert into inschrijvingen (cursist, cursus, begindatum)
values (7902, 'D&P', '13-11-2020');


update medewerkers
set maandsal=(1.055*(select maandsal from medewerkers where mnr=7499))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7499;
update medewerkers
set maandsal=(1.055*(select maandsal from medewerkers where mnr=7521))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7521;
update medewerkers
set maandsal=(1.055*(select maandsal from medewerkers where mnr=7654))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7654;
update medewerkers
set maandsal=(1.07*(select maandsal from medewerkers where mnr=7698))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7698;
update medewerkers
set maandsal=(1.055*(select maandsal from medewerkers where mnr=7900))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7900;
update medewerkers
set maandsal=(1.055*(select maandsal from medewerkers where mnr=7844))
where afd=(select anr from afdelingen where naam='VERKOOP') and mnr=7844;


delete from inschrijvingen
where cursist=(select mnr from medewerkers where naam='MARTENS' and functie='VERKOPER');
delete from medewerkers
where mnr=(select mnr from medewerkers where naam='MARTENS' and functie='VERKOPER');

delete from inschrijvingen
where cursist=(select mnr from medewerkers where naam='ALDERS');
delete from medewerkers
where mnr=(select mnr from medewerkers where naam='ALDERS');
-- dit lukt omdat ik eerst alle instanties waar de mnr word gebruikt als foreign key verwijder voordat ik de colom waar het de primary key is verweider


insert into afdelingen (anr, naam, locatie)
values (50, 'FINANCIEN', 'LEERDAM');
insert into medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, afd)
values (8012, 'VROOMANS', 'T', 'MANAGER',(select mnr from medewerkers where naam='DE KONING') ,'04-05-2002', 2500, (select anr from afdelingen where naam='FINANCIEN'));
update afdelingen
set hoofd=(select mnr from medewerkers where naam='VROOMANS')
where naam='FINANCIEN';