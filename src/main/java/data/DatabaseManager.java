package data;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:ucanaccess://C:/Users/jeank/Desktop/JONAS/cheminBD/Bibliotheque_Gestion.accdb";
    private Connection connection;

    // Connexion à la base de données
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
    }

    // Fermer la connexion
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Ajouter un lecteur dans la base de données
    public void addLecteur(Lecteur lecteur) {
        try {
            connect();
            String query = "INSERT INTO Lecteur (nom, numero_abonnement, adresse) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lecteur.getNom());
            statement.setInt(2, lecteur.getNumAbonnement());
            statement.setString(3, lecteur.getAdresse());
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ajouter un livre dans la base de données
    public void addLivre(Livre livre) {
        try {
            connect();
            String query = "INSERT INTO Livre (titre, disponible, auteur, isbn, DatePublication) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, livre.getTitre());
            statement.setBoolean(2, livre.isDisponible());
            statement.setString(3, livre.getAuteur());
            statement.setString(4, livre.getIsbn());

            // Conversion de java.util.Date en java.sql.Date
            java.util.Date utilDate = livre.getDatePublication();
            if (utilDate != null) {
                Date sqlDate = new Date(utilDate.getTime());  // Convertit la date en java.sql.Date
                statement.setDate(5, sqlDate);  // Ajout de la date dans la requête SQL
            } else {
                statement.setNull(5, Types.DATE);  // Si la date est nulle, on insère NULL
            }

            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un livre dans la base de données
    public void removeLivre(Livre livre) {
        try {
            connect();
            String query = "DELETE FROM Livre WHERE titre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, livre.getTitre());
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mettre à jour la disponibilité d'un livre
    public void updateLivreEmprunté(Livre livre, boolean emprunté) {
        try {
            connect();
            String query = "UPDATE Livre SET disponible = ? WHERE titre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, !emprunté);
            statement.setString(2, livre.getTitre());
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lire tous les livres depuis la base de données
    public void lireLivres() {
        try {
            connect();
            String query = "SELECT * FROM Livre";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                boolean disponible = resultSet.getBoolean("disponible");
                String auteur = resultSet.getString("auteur");
                String isbn = resultSet.getString("isbn");
                Date datePublication = resultSet.getDate("date_publication");

                // Affichage des informations sur le livre
                System.out.println("Titre: " + titre + ", Disponible: " + disponible + ", Auteur: " + auteur + ", ISBN: " + isbn + ", Date de Publication: " + datePublication);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
