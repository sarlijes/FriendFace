Tässä siis custom login-pulmainen projekti. Olen siis yrittänyt koostaa omaa sisäänkirjautumissivua (siksi, että siinä pitää olla linkki, jota seuraamalla pääsee rekisteröitymään palveluun). Springin oletus login ei siis käy, sillä siihen ei saa linkkiä "sign up". Olen yrittänyt soveltaa tätä ohjetta: https://www.baeldung.com/spring-security-login soveltuvin osin.

1. UserAccountController 1. metodin (nyt kommentoituna pois): authista haettavat asiat ovat NULL, mikä kaataa ohjelman. Olen rakentanut nyt ohjelmalogiikkaa kiertämällä ongelmaa niin, että profileCode (esim. facebook.com/pentti5656 : profilecode on pentti5656) haetaan PathVariablena, mutta tässähän ei tietenkään ole authorisointia ollenkaan. On siis vain väliaikainen viritys. Tällaisenaan toimii (jos osa toiminnallisuuksista on hard codattu tietokannan esimerkki-käyttätunnuksella), mutta ei tokikaan voi olla näin jotta toimisi oikein.

2. Haluaisin päästä eroon DevelopmentSecurityConfigurationista, mutta ilman sitä kirjautuminen päätyy "too many redirects"- pulmaan. Nyt siis sekä DevelopmentSecurityConfiguration että SecurityConfiguration ajetaan, en tiedä voiko se sekoittaa.

Ongelma vaikuttaa olevan se, että loadUserByUsername-metodi CustomUserDetailsService- luokassa ei "käynnisty" ollenkaan. Tätä vikaa en osaa jäljittää, sillä en kutsu metodia itse vaan Spring tekee sen. 

Pulmia on 15.4. tutkittu pajassa kahdenkin ohjaajan voimin. Tarvitsisin apua, jotta saan työtä eteenpäin. Suurkiitos :)

