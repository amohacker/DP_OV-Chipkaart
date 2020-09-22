-- opdracht 1
select medewerkers.mnr, medewerkers.functie, medewerkers.gbdatum
from medewerkers
where medewerkers.gbdatum<'1980-01-01' and (medewerkers.functie='TRAINER' or medewerkers.functie='VERKOPER');

-- opdracht 2
select naam
from medewerkers
where naam similar to '% %';

-- opdracht 3
select uitvoeringen.cursus, uitvoeringen.begindatum, count(*) as aantal_inschrijvingen
from uitvoeringen
left join inschrijvingen on uitvoeringen.cursus=inschrijvingen.cursus and uitvoeringen.begindatum=inschrijvingen.begindatum
group by uitvoeringen.cursus, uitvoeringen.begindatum
having count(*)>=3 and EXTRACT(YEAR FROM uitvoeringen.begindatum)=2019;

-- opdracht 4
select inschrijvingen.cursist, inschrijvingen.cursus
from inschrijvingen
group by inschrijvingen.cursist, inschrijvingen.cursus
having count(*)>1;

-- opdracht 5
select cursus, count(*)
from uitvoeringen
group by cursus

-- opdracht 6
select ((max(gbdatum))-(min(gbdatum)))/365 as verschil
from medewerkers;

-- opdracht 7 ik kan bij commissie_verkopers gewoon avg(comm) gebruiken omdat de tabel medewerkers een constraint heeft die zegt dat alle verkopers een comm value moeten hebben en alle andere functies een comm van null moeten hebben
select (select count(mnr)) as aantal_medewerkers, (sum(comm)/count(mnr)) as commissie_medewerkers, avg(comm) as commissie_verkopers
from medewerkers;