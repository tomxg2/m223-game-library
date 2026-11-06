# Game Library

Projektarbeit Modul 223 (Quarkus). Benutzer verwalten ihre persönliche Spielesammlung: Games, Genres, Plattformen und Bibliothekseinträge mit Spielzeit, Status und Bewertung.

## Starten

1. Projekt im Dev Container öffnen (`Dev Containers: Reopen in Container`).
2. `./mvnw quarkus:dev`
3. Swagger UI: http://localhost:8080/q/swagger-ui/

Im Dev-Profil werden automatisch Testdaten geladen (`ExampleDataService`).

## Entitäten

| Entität | Beziehungen |
|---|---|
| ApplicationUser | 1:n LibraryEntry |
| LibraryEntry | n:1 ApplicationUser, n:1 Game, n:1 Platform |
| Game | n:1 Genre, n:m Platform (Join-Tabelle `game_platform`) |
| Genre | 1:n Game |
| Platform | n:m Game, 1:n LibraryEntry |

## Endpunkte

| Ressource | Endpunkte |
|---|---|
| Genres | `GET/POST /genres`, `GET/PUT/DELETE /genres/{id}` |
| Platforms | `GET/POST /platforms`, `GET/PUT/DELETE /platforms/{id}` |
| Games | `GET/POST /games`, `GET/PUT/DELETE /games/{id}` |
| Users | `GET/POST /users`, `GET/PUT/DELETE /users/{id}` |
| Library Entries | `GET /library-entries?userId=`, `POST /library-entries`, `GET/PUT/DELETE /library-entries/{id}` |
