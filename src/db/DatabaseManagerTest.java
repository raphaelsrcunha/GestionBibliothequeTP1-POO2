package db;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseManagerTest {

    private DatabaseManager databaseManager;

    @BeforeAll
    void setUp() throws Exception {
        databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();

        // Configuração inicial da base de dados
        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            // Criar uma tabela de teste, se não existir
            statement.execute("CREATE TABLE IF NOT EXISTS TestTable (" +
                              "id INT AUTO_INCREMENT PRIMARY KEY, " +
                              "name VARCHAR(255) NOT NULL)");
        }
    }

    @Test
    void testSingleton() {
        DatabaseManager instance1 = DatabaseManager.getInstance();
        DatabaseManager instance2 = DatabaseManager.getInstance();

        assertSame(instance1, instance2, "As instâncias devem ser as mesmas (Singleton).");
    }

    @Test
    void testConnect() throws Exception {
        databaseManager.disconnect();
        databaseManager.connect();

        Connection connection = databaseManager.getConnection();
        assertNotNull(connection, "A conexão não deve ser nula após a chamada ao método connect().");
        assertFalse(connection.isClosed(), "A conexão deve estar aberta após a chamada ao método connect().");
    }

    @Test
    void testDisconnect() throws Exception {
        databaseManager.connect();
        databaseManager.disconnect();

        Connection connection = databaseManager.getConnection();
        assertNotNull(connection, "A conexão deve ser recriada ao chamar getConnection() após desconectar.");
    }

    @Test
    void testExecuteInsertAndQuery() throws Exception {
        databaseManager.connect();
        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            // Inserir dados de teste
            int rowsInserted = statement.executeUpdate("INSERT INTO TestTable (name) VALUES ('TestName')");
            assertEquals(1, rowsInserted, "Uma linha deve ser inserida na tabela.");

            // Consultar os dados inseridos
            var resultSet = statement.executeQuery("SELECT name FROM TestTable WHERE name = 'TestName'");
            assertTrue(resultSet.next(), "Deve haver pelo menos um resultado.");
            assertEquals("TestName", resultSet.getString("name"), "O nome inserido deve ser 'TestName'.");
        }
    }

    @AfterAll
    void tearDown() throws Exception {
        // Limpeza da tabela de teste
        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            // Remover tabela de teste
            statement.execute("DROP TABLE IF EXISTS TestTable");
        }

        databaseManager.disconnect();
    }
}
