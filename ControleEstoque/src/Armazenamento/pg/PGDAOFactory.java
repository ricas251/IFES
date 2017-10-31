package Armazenamento.pg;

import controleestoque.Armazenamento.DAOFactory;
import java.sql.Connection;;

public class PGDAOFactory extends DAOFactory{

	private Connection con;
	private static final String DB_URL = "jdbc:postgresql;//localhost:5433/estoque";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "postgres";
}
