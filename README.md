# Simple eksempler på MySQL i Java vha. JDBC
*Kodeeksemplerne er baseret på eksemplerne i Liangs bog.*

- **SimpleJdbcBASIC.java** viser den enkelste opskrift på, hvordan vi kan tilgå en MySQL-database fra Java.
- **SimpleJdbc.java** viser et lille program, som kan finde population i de forskellige lande. 
- **CitiesAndLangaugesGUI.java** viser, hvordan man kan lave et simpelt GUI for databaser
- **DB_Settings.java** viser, hvordan du kan undgå at sende dit SQL-kodeord op på GitHub.


## Forudsætninger
1. Du skal have en MySQL-server, der kører.
2. Serveren skal have *world* databasen installeret.  
3. Du skal bruge en "external libarary" med navnet [Connector/J](https://dev.mysql.com/downloads/connector/j/) fra MySQL, for at kunne connecte til MySQL. (Brug den Platform Independendent .zip fil)
4. Tilføj Connector/J som Library under Project Structure. OBS: Du skal ikke uploade denne fil på GitHUB! (Dvs. den skal måske sættes i .gitignore)

