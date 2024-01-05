# Architectuur

## Thema
Voetbal, mijn hobby is voetballen dus ik dacht meteen hieraan om mijn project rond te maken.

## Microservices
Voor mijn microservices heb ik spelers, clubs en matchen gekozen. Dit zijn zowat de 3 belangrijkste dingen die beheerd moeten kunnen worden om matchen tegen andere clubs te spelen.

## Componenten
1. Gateway met authenticatie en rate limiting.

## Plaatje

![SoccerMicroservices](https://github.com/JannesVdB/microservices-project/assets/91123262/0e382758-1585-4389-8e9c-db2bd318ce2f)

# Uitbreidingen

1. Implementeer rate-limiting op je Spring Cloud Gateway

# Endpoints

## Player

### Speler toevoegen
![player_post](https://github.com/JannesVdB/microservices-project/assets/91123262/3638d79c-7013-49e0-ba43-b5660591bf46)

### Speler opvragen met skuCode

![player_get_skucode](https://github.com/JannesVdB/microservices-project/assets/91123262/57098d6f-e506-4401-b8ed-814fe1bc74f9)

### Alle spelers opvragen

![player_get_all](https://github.com/JannesVdB/microservices-project/assets/91123262/5a3f9fc9-2ec2-42b7-8ea7-f50fa73058ea)

### Speler updaten

![player_update](https://github.com/JannesVdB/microservices-project/assets/91123262/fbfe135b-9ec0-4880-a060-9339b299afb6)

#### Bewijs
![player_update_get](https://github.com/JannesVdB/microservices-project/assets/91123262/d16918c3-9de7-4ec6-9e12-3847239bd1cd)

## Club

### Club toevoegen

![club_post](https://github.com/JannesVdB/microservices-project/assets/91123262/37c840a1-ae49-49a7-b15e-bcf0336849da)

### Club opvragen met skuCode

![club_get](https://github.com/JannesVdB/microservices-project/assets/91123262/326f16c7-2127-4486-9f72-69484ee520d5)

### Squad aanmaken voor een club

![club_post_squad](https://github.com/JannesVdB/microservices-project/assets/91123262/d53db191-e111-411d-8e12-585d46a3c403)

#### Bewijs

![club_squad_get](https://github.com/JannesVdB/microservices-project/assets/91123262/91685a4d-1834-448f-a7ee-76b03edf4bcc)

### Squad updaten

![club_update_squad](https://github.com/JannesVdB/microservices-project/assets/91123262/ade9c826-349d-496e-9f0d-6b8ef8b22c5f)

#### Bewijs

![club_update_squad_get](https://github.com/JannesVdB/microservices-project/assets/91123262/bb266c75-0e69-4079-9ce2-92e2beab5d5c)

### Speler toevoegen aan een squad

![club_post_player](https://github.com/JannesVdB/microservices-project/assets/91123262/dfbd2779-851a-4e3d-a7ee-b7303bd674e7)

#### Bewijs

![club_post_player_get](https://github.com/JannesVdB/microservices-project/assets/91123262/7ae187c2-4217-4254-8b99-e9029860b21d)


### Speler verwijderen van een squad

![club_delete_player](https://github.com/JannesVdB/microservices-project/assets/91123262/b2bc1ca3-ec74-47f2-af7b-b0b987e9551b)

#### Bewijs

![club_delete_player_get](https://github.com/JannesVdB/microservices-project/assets/91123262/4c24deb9-a9f4-4a8a-86f3-3c7bf380556e)

## Match

### Match aanmaken

![match_post](https://github.com/JannesVdB/microservices-project/assets/91123262/07a2530d-3007-4e7d-b155-036106176969)

### Match opvragen met skuCode

![match_get](https://github.com/JannesVdB/microservices-project/assets/91123262/bb1e8d1d-777c-4636-a64f-cbefb23be1ba)

### Score van een team updaten

![match_update](https://github.com/JannesVdB/microservices-project/assets/91123262/9aedaf4a-3e95-4ba2-bac9-ff6e90e55050)

#### Bewijs

![match_update_get](https://github.com/JannesVdB/microservices-project/assets/91123262/f659cc37-dd2d-45ec-91c3-7ecd3fd8a393)

### Alle matchen van een club opvragen

![match_get_club](https://github.com/JannesVdB/microservices-project/assets/91123262/8186223f-026e-426f-9c2e-6f25256744f4)
