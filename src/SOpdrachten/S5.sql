-- opdracht 1
SELECT naam
FROM inschrijvingen
INNER JOIN medewerkers ON inschrijvingen.cursist=medewerkers.mnr
WHERE cursus='XML' AND cursist IN
(SELECT cursist FROM inschrijvingen WHERE cursus='JAV');

-- opdracht 2
SELECT mnr
FROM medewerkers
WHERE afd!=20;

-- opdracht 3
SELECT i.cursist
FROM inschrijvingen as i
WHERE i.cursist NOT IN (SELECT inschrijvingen.cursist FROM inschrijvingen WHERE cursus='JAV' AND NOT inschrijvingen.cursus IS NULL)
GROUP BY i.cursist;

-- opdracht 4
-- hebben ondergeschikten met inner join
SELECT chef.naam AS hebben_ondergeschikten
FROM medewerkers AS chef
INNER JOIN medewerkers AS onderling ON chef.mnr=onderling.chef
GROUP BY chef.naam;
-- hebben ondergeschikten met subquery
SELECT naam AS hebben_ondergeschikten
FROM medewerkers
WHERE mnr IN (SELECT medewerkers.chef FROM medewerkers GROUP BY chef);
-- hebben geen ondergeschikten met subquery
SELECT naam AS heeft_geen_onderlingen
FROM medewerkers as m
WHERE m.mnr NOT IN (SELECT medewerkers.chef FROM medewerkers WHERE NOT chef IS NULL GROUP BY chef);
-- lijst met alle medewerkers en of ze ondergeschikten hebben
SELECT m.mnr ,naam AS naam, (SELECT true as heeft_onderlingen WHERE m.mnr IN (SELECT medewerkers.chef FROM medewerkers GROUP BY chef))
FROM medewerkers as m
GROUP BY naam, m.mnr;

-- opdracht 5
SELECT uitvoeringen.cursus, uitvoeringen.begindatum
FROM uitvoeringen
INNER JOIN cursussen ON uitvoeringen.cursus=cursussen.code
WHERE cursussen.type='BLD' AND EXTRACT(YEAR FROM uitvoeringen.begindatum)='2020';

-- opdracht 6
SELECT uitvoeringen.cursus, uitvoeringen.begindatum, count(*)
FROM uitvoeringen
INNER JOIN inschrijvingen ON uitvoeringen.cursus=inschrijvingen.cursus AND uitvoeringen.begindatum=inschrijvingen.begindatum
GROUP BY uitvoeringen.cursus, uitvoeringen.begindatum
ORDER BY uitvoeringen.begindatum;

-- opdracht 7
SELECT d.voorl, d.naam
FROM medewerkers AS d
INNER JOIN uitvoeringen ON d.mnr=uitvoeringen.docent
INNER JOIN inschrijvingen AS i ON uitvoeringen.cursus=i.cursus AND uitvoeringen.begindatum=i.begindatum
WHERE d.chef=i.cursist
GROUP BY d.voorl, d.naam;

-- opdracht 8
SELECT m.naam
FROM medewerkers AS m
where m.mnr NOT IN (SELECT u.docent FROM uitvoeringen AS u where NOT u.docent IS NULL)