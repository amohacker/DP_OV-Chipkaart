-- opdracht 1
select cursussen.code, uitvoeringen.begindatum, cursussen.lengte, medewerkers.naam
from cursussen
inner join uitvoeringen on cursussen.code=uitvoeringen.cursus
inner join medewerkers on uitvoeringen.docent=medewerkers.mnr;

-- opdracht 2
select cursist.naam as naam_cursist, docent.naam as naam_docent
from inschrijvingen

inner join medewerkers as cursist on inschrijvingen.cursist=cursist.mnr

inner join uitvoeringen on inschrijvingen.cursus=uitvoeringen.cursus and inschrijvingen.begindatum=uitvoeringen.begindatum
inner join medewerkers as docent on uitvoeringen.docent=docent.mnr;

-- opdracht 3
select afdelingen.naam, medewerkers.naam
from afdelingen
inner join medewerkers on afdelingen.hoofd=medewerkers.mnr;

-- opdracht 4
select medewerkers.naam, afdelingen.naam, afdelingen.locatie
from medewerkers
inner join afdelingen on medewerkers.afd=afdelingen.anr;

-- opdracht 5
-- met de andere methode om te demonstreren dat het kan
select medewerkers.naam
from inschrijvingen
inner join medewerkers on inschrijvingen.cursist=medewerkers.mnr
where inschrijvingen.cursus='S02' and inschrijvingen.begindatum='12-4-2019';

-- opdracht 6
select p.naam, schalen.toelage
from medewerkers as p
inner join schalen on ondergrens<p.maandsal and bovengrens>p.maandsal;