# Wzorce-Projektowe-Markiewicz-Koszorz

# Wzorce Projektowe - Backend

## Wprowadzenie
Projekt backendowy realizujący różne wzorce projektowe, takie jak Strategia, Dekorator i Fabryka. Aplikacja korzysta z bazy danych PostgreSQL, która działa w kontenerze Dockera. Poniżej znajdziesz instrukcje, jak uruchomić środowisko.

---

## Wymagania wstępne
Przed rozpoczęciem pracy upewnij się, że na Twoim komputerze są zainstalowane:
1. **Docker**: Wersja minimalna 20.10+  
   [Instrukcja instalacji Dockera](https://www.docker.com/get-started)
2. **Java JDK**: Wersja 17 lub wyższa.  
   Sprawdź wersję Javy:
   ```bash
   java -version
   ```
3. **Gradle** (jeśli budujesz lokalnie)

---

## Krok 1: Klonowanie repozytorium
Najpierw sklonuj repozytorium:
```bash
git clone https://github.com/Matix57/Wzorce-Projektowe-Markiewicz-Koszorz.git
cd Wzorce-Projektowe-Markiewicz-Koszorz
```

---

## Krok 2: Konfiguracja bazy danych PostgreSQL
W tym projekcie baza danych PostgreSQL jest uruchamiana w kontenerze Dockera. Aby skonfigurować środowisko:

### 2.1 Uruchomienie kontenera PostgreSQL
W terminalu (z zainstalowanym Dockerem) wpisz poniższą komendę:
```bash
docker run --name postgres-blog-app \
  -e POSTGRES_USER=blog_user \
  -e POSTGRES_PASSWORD=blog_password \
  -e POSTGRES_DB=blog_db \
  -p 5432:5432 \
  -d postgres
```

**Parametry kontenera:**
- `POSTGRES_USER=blog_user`: Użytkownik bazy danych.
- `POSTGRES_PASSWORD=blog_password`: Hasło użytkownika.
- `POSTGRES_DB=blog_db`: Domyślna baza danych.
- `-p 5432:5432`: Mappowanie portu lokalnego na port kontenera.

- Mogą się przydac do połączenia np. przez Postmana

---

## Krok 3: Uruchom projekt backendowy w IntelliJ
### Możesz testować aplikację np. przez Postmana :) 


